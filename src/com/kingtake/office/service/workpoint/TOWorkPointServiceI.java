package com.kingtake.office.service.workpoint;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.workpoint.TOWorkPointEntity;

public interface TOWorkPointServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOWorkPointEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOWorkPointEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOWorkPointEntity t);

    /**
     * 提交
     * 
     * @param tOWorkPoint
     */
    public void doSubmit(TOWorkPointEntity tOWorkPoint);
}
