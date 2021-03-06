package com.kingtake.price.service.impl.tbjgbzhjsymx;
import com.kingtake.price.service.tbjgbzhjsymx.TBJgbzHjsymxServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.price.entity.tbjgbzhjsymx.TBJgbzHjsymxEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBJgbzHjsymxService")
@Transactional
public class TBJgbzHjsymxServiceImpl extends CommonServiceImpl implements TBJgbzHjsymxServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBJgbzHjsymxEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBJgbzHjsymxEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBJgbzHjsymxEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgbzHjsymxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgbzHjsymxEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgbzHjsymxEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBJgbzHjsymxEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{syxmid}",String.valueOf(t.getSyxmid()));
 		sql  = sql.replace("#{sfbz}",String.valueOf(t.getSfbz()));
 		sql  = sql.replace("#{sfdw}",String.valueOf(t.getSfdw()));
 		sql  = sql.replace("#{sysb}",String.valueOf(t.getSysb()));
 		sql  = sql.replace("#{beiz}",String.valueOf(t.getBeiz()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}