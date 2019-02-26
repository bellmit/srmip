package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.declare.army.TPmFundsBudgetEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetServiceI;

@Service("tPmFundsBudgetService")
@Transactional
public class TPmFundsBudgetServiceImpl extends CommonServiceImpl implements TPmFundsBudgetServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmFundsBudgetEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmFundsBudgetEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmFundsBudgetEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmFundsBudgetEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmFundsBudgetEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmFundsBudgetEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmFundsBudgetEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{proj_declare_id}",String.valueOf(t.getProjDeclareId()));
 		sql  = sql.replace("#{budget_year}",String.valueOf(t.getBudgetYear()));
 		sql  = sql.replace("#{equip_funds}",String.valueOf(t.getEquipFunds()));
 		sql  = sql.replace("#{material_funds}",String.valueOf(t.getMaterialFunds()));
 		sql  = sql.replace("#{outside_funds}",String.valueOf(t.getOutsideFunds()));
 		sql  = sql.replace("#{business_funds}",String.valueOf(t.getBusinessFunds()));
 		sql  = sql.replace("#{total_funds}",String.valueOf(t.getTotalFunds()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}