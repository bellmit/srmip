package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.declare.army.TPmDeclareFundsTechnologyEntity;
import com.kingtake.project.service.declare.army.TPmDeclareFundsTechnologyServiceI;

@Service("tPmDeclareFundsTechnologyService")
@Transactional
public class TPmDeclareFundsTechnologyServiceImpl extends CommonServiceImpl implements TPmDeclareFundsTechnologyServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmDeclareFundsTechnologyEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmDeclareFundsTechnologyEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmDeclareFundsTechnologyEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmDeclareFundsTechnologyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmDeclareFundsTechnologyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmDeclareFundsTechnologyEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmDeclareFundsTechnologyEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{proj_declare_id}",String.valueOf(t.getProjDeclareId()));
 		sql  = sql.replace("#{budget_year}",String.valueOf(t.getBudgetYear()));
 		sql  = sql.replace("#{budget_name}",String.valueOf(t.getBudgetName()));
 		sql  = sql.replace("#{budget_funds}",String.valueOf(t.getBudgetFunds()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}