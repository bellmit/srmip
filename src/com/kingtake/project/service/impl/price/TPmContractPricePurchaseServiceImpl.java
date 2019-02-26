package com.kingtake.project.service.impl.price;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.price.TPmContractPricePurchaseEntity;
import com.kingtake.project.service.price.TPmContractPricePurchaseServiceI;

@Service("tPmContractPricePurchaseService")
@Transactional
public class TPmContractPricePurchaseServiceImpl extends CommonServiceImpl implements TPmContractPricePurchaseServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractPricePurchaseEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractPricePurchaseEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractPricePurchaseEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPricePurchaseEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPricePurchaseEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPricePurchaseEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPricePurchaseEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{supplier_id}",String.valueOf(t.getSupplierId()));
 		sql  = sql.replace("#{supplier_name}",String.valueOf(t.getSupplierName()));
 		sql  = sql.replace("#{serial_num}",String.valueOf(t.getSerialNum()));
 		sql  = sql.replace("#{category_name}",String.valueOf(t.getCategoryName()));
 		sql  = sql.replace("#{produce_unit}",String.valueOf(t.getProduceUnit()));
 		sql  = sql.replace("#{calculate_unit}",String.valueOf(t.getCalculateUnit()));
 		sql  = sql.replace("#{produce_num}",String.valueOf(t.getProduceNum()));
 		sql  = sql.replace("#{price_unitprice}",String.valueOf(t.getPriceUnitprice()));
 		sql  = sql.replace("#{price_amount}",String.valueOf(t.getPriceAmount()));
 		sql  = sql.replace("#{audit_unitprice}",String.valueOf(t.getAuditUnitprice()));
 		sql  = sql.replace("#{audit_amount}",String.valueOf(t.getAuditAmount()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}