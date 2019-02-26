package com.kingtake.price.service.impl.tbjgbzcl;

import com.kingtake.price.service.tbjgbzcl.TBJgbzClServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.price.entity.tbjgbzcl.TBJgbzClEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBJgbzClService")
@Transactional
public class TBJgbzClServiceImpl extends CommonServiceImpl implements TBJgbzClServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBJgbzClEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBJgbzClEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBJgbzClEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgbzClEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgbzClEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgbzClEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBJgbzClEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{dq}",String.valueOf(t.getDq()));
 		sql  = sql.replace("#{djqz}",String.valueOf(t.getDjqz()));
 		sql  = sql.replace("#{jz}",String.valueOf(t.getJz()));
 		sql  = sql.replace("#{sz}",String.valueOf(t.getSz()));
 		sql  = sql.replace("#{tz}",String.valueOf(t.getTz()));
 		sql  = sql.replace("#{hsbz}",String.valueOf(t.getHsbz()));
 		sql  = sql.replace("#{snjt}",String.valueOf(t.getSnjt()));
 		sql  = sql.replace("#{zxsj}",String.valueOf(t.getZxsj()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}