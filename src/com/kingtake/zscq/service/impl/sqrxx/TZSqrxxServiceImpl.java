package com.kingtake.zscq.service.impl.sqrxx;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.zscq.entity.sqrxx.TZSqrxxEntity;
import com.kingtake.zscq.service.sqrxx.TZSqrxxServiceI;

@Service("tZSqrxxService")
@Transactional
public class TZSqrxxServiceImpl extends CommonServiceImpl implements TZSqrxxServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TZSqrxxEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TZSqrxxEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TZSqrxxEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TZSqrxxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TZSqrxxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TZSqrxxEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TZSqrxxEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{xm}",String.valueOf(t.getXm()));
 		sql  = sql.replace("#{dh}",String.valueOf(t.getDh()));
        sql = sql.replace("#{idno}", String.valueOf(t.getIdno()));
 		sql  = sql.replace("#{dzyx}",String.valueOf(t.getDzyx()));
 		sql  = sql.replace("#{gj}",String.valueOf(t.getGj()));
 		sql  = sql.replace("#{jsd}",String.valueOf(t.getJsd()));
 		sql  = sql.replace("#{yzbm}",String.valueOf(t.getYzbm()));
 		sql  = sql.replace("#{xxdz}",String.valueOf(t.getXxdz()));
 		sql  = sql.replace("#{ssbm}",String.valueOf(t.getSsbm()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}