package com.kingtake.solr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.memo.TOPrivateMemoEntity;


@Component
public class SolrSchedule
{
	@Resource
	SystemService systemService;
	
	
	// 更新索引，每分钟执行一次
	//格式:   [秒] [分] [小时] [日] [月] [周] [年]
	//是否必填：是          是          是            是         是         是         否
	// /--表示增量值，例如：在秒字段中5/15表示从第五秒开始，每隔15s执行一次
	//@Scheduled(cron="0 * * * * ?") 
	public void updateIndex(){
		// 需要添加的索引的容器
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		
		for(int i = 0; i < SrmipConstants.SOLR_TABLE_LIST.size(); i++){
			Map<String, Object> map = SrmipConstants.SOLR_TABLE_LIST.get(i);
			String table = map.get(SrmipConstants.SOLR_KEY_TABLE).toString();
			String tableName = map.get(SrmipConstants.SOLR_KEY_TABLENAME).toString();
			List<Map<String, String>> attrMethods = (List<Map<String, String>>) map.get(SrmipConstants.SOLR_TABLE_ATTR+SrmipConstants.SOLR_TABLE_METHOD);
			
			// 判断实体是否存在
			Class clazz = null;
			try {
				 clazz = Class.forName(table);
			} catch (ClassNotFoundException e) {
				System.out.println(table+"实体类不存在");
				e.printStackTrace();
			} 
				
			// 从数据库中查询数据
			CriteriaQuery cq = new CriteriaQuery(clazz);
			cq.ge("updateDate", new Date());
			cq.add();
			List list = systemService.getListByCriteriaQuery(cq, false);
			
			for(int k = 0; k < list.size(); k++){
				SolrInputDocument solrDoc = new SolrInputDocument();
				solrDoc.addField(SrmipConstants.SOLR_KEY_TABLE, table);
				solrDoc.addField(SrmipConstants.SOLR_KEY_TABLENAME, table);
				
				Object obj = list.get(k);
				for(int j = 0; j < attrMethods.size(); j++){
					Map<String, String> attrMethod = attrMethods.get(j);
					try {
						Method method = clazz.getMethod(attrMethod.get(SrmipConstants.SOLR_TABLE_METHOD));
						Object value = method.invoke(obj);
						solrDoc.addField(attrMethod.get(SrmipConstants.SOLR_TABLE_ATTR), value);
					} catch (NoSuchMethodException e1) {
						System.out.println("属性不存在");
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
					}
					docs.add(solrDoc);
				}
			}
		}
		SolrOperate.saveOrUpdateIndex(docs);
	}



}
	