package com.kingtake.zscq.service.zljl;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.zscq.entity.zljl.TZZljlEntity;

public interface TZZljlServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TZZljlEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TZZljlEntity t);

 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TZZljlEntity t);


}
