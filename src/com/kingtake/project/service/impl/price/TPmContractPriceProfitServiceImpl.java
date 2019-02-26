package com.kingtake.project.service.impl.price;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.price.TPmContractPriceProfitEntity;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;
import com.kingtake.project.service.price.TPmContractPriceProfitServiceI;

@Service("tPmContractPriceProfitService")
@Transactional
public class TPmContractPriceProfitServiceImpl extends CommonServiceImpl implements TPmContractPriceProfitServiceI {
	@Autowired
	private TPmContractPriceMasterServiceI tPmContractPriceMasterService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractPriceProfitEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractPriceProfitEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractPriceProfitEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceProfitEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceProfitEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceProfitEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPriceProfitEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{serial_num}",String.valueOf(t.getSerialNum()));
 		sql  = sql.replace("#{type_id}",String.valueOf(t.getTypeId()));
 		sql  = sql.replace("#{type_name}",String.valueOf(t.getTypeName()));
 		sql  = sql.replace("#{price_explain}",String.valueOf(t.getPriceExplain()));
 		sql  = sql.replace("#{price_percent}",String.valueOf(t.getPricePercent()));
 		sql  = sql.replace("#{price_amount}",String.valueOf(t.getPriceAmount()));
 		sql  = sql.replace("#{audit_percent}",String.valueOf(t.getAuditPercent()));
 		sql  = sql.replace("#{audit_amount}",String.valueOf(t.getAuditAmount()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{add_child_flag}",String.valueOf(t.getAddChildFlag()));
 		sql  = sql.replace("#{parent_typeid}",String.valueOf(t.getParentTypeid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public void update(TPmContractPriceProfitEntity newData, AjaxJson j) throws Exception {
		// 获得未更新之前的数据
		TPmContractPriceProfitEntity oldData = commonDao.get(TPmContractPriceProfitEntity.class, newData.getId());
		
		// 更新主表:计价栏、审价栏
		Double[] priceAudit = new Double[]{
				(newData.getPriceAmount() == null ? 0 : newData.getPriceAmount())
						- (oldData.getPriceAmount() == null ? 0 : oldData.getPriceAmount()),
				(newData.getAuditAmount() == null ? 0 : newData.getAuditAmount()) 
					- (oldData.getAuditAmount() == null ? 0 : oldData.getAuditAmount())
		};
		tPmContractPriceMasterService.updateMaster(oldData.getTpId(), oldData.getTypeId(), priceAudit);
					
		MyBeanUtils.copyBeanNotNull2Bean(newData, oldData);
		commonDao.saveOrUpdate(oldData);
	}
}