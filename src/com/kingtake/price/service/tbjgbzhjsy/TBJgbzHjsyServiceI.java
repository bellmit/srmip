package com.kingtake.price.service.tbjgbzhjsy;
import com.kingtake.price.entity.tbjgbzhjsy.TBJgbzHjsyEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TBJgbzHjsyServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgbzHjsyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgbzHjsyEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgbzHjsyEntity t);
}
