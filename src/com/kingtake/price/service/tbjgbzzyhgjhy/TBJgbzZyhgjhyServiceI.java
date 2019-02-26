package com.kingtake.price.service.tbjgbzzyhgjhy;

import com.kingtake.price.entity.tbjgbzzyhgjhy.TBJgbzZyhgjhyEntity;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface TBJgbzZyhgjhyServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBJgbzZyhgjhyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBJgbzZyhgjhyEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBJgbzZyhgjhyEntity t);
}
