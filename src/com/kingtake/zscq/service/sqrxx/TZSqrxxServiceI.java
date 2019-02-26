package com.kingtake.zscq.service.sqrxx;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.zscq.entity.sqrxx.TZSqrxxEntity;

public interface TZSqrxxServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TZSqrxxEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TZSqrxxEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TZSqrxxEntity t);
}
