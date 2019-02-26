package com.kingtake.project.service.impl.funds;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.funds.TPmMaterialEntity;
import com.kingtake.project.service.funds.TPmMaterialServiceI;

@Service("tPmMaterialService")
@Transactional
public class TPmMaterialServiceImpl extends CommonServiceImpl implements TPmMaterialServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmMaterialEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmMaterialEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmMaterialEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmMaterialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmMaterialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmMaterialEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmMaterialEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{material_name}",String.valueOf(t.getMaterialName()));
 		sql  = sql.replace("#{material_model}",String.valueOf(t.getMaterialModel()));
 		sql  = sql.replace("#{material_unit}",String.valueOf(t.getMaterialUnit()));
 		sql  = sql.replace("#{material_price}",String.valueOf(t.getMaterialPrice()));
 		sql  = sql.replace("#{material_factory}",String.valueOf(t.getMaterialFactory()));
 		sql  = sql.replace("#{supply_date}",String.valueOf(t.getSupplyDate()));
 		sql  = sql.replace("#{material_resource}",String.valueOf(t.getMaterialResource()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}