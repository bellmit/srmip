package com.kingtake.solr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.solr.common.SolrInputDocument;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kingtake.common.constant.SrmipConstants;


/**
 * solr初始化监听器,添加索引
 * @author laien
 *
 */
public class SolrInitListener  implements javax.servlet.ServletContextListener {

	
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		SystemService systemService = (SystemService) webApplicationContext.getBean("systemService");

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
			List list = systemService.findHql("from " + table);
			for(int k = 0; k < list.size(); k++){
				SolrInputDocument solrDoc = new SolrInputDocument();
				solrDoc.addField(SrmipConstants.SOLR_KEY_TABLE, table);
				solrDoc.addField(SrmipConstants.SOLR_KEY_TABLENAME, tableName);
				
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
