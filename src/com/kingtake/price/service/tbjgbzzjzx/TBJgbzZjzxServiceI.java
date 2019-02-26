package com.kingtake.price.service.tbjgbzzjzx;
import com.kingtake.price.entity.tbjgbzzjzx.TBJgbzZjzxEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBJgbzZjzxServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgbzZjzxEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgbzZjzxEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgbzZjzxEntity t);
}
