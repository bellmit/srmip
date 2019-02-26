package com.kingtake.price.service.tbjgbzhjsymx;
import com.kingtake.price.entity.tbjgbzhjsymx.TBJgbzHjsymxEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBJgbzHjsymxServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgbzHjsymxEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgbzHjsymxEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgbzHjsymxEntity t);
}
