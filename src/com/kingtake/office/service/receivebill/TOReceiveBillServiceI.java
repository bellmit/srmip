package com.kingtake.office.service.receivebill;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.receivebill.TOReceiveBillEntity;

public interface TOReceiveBillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOReceiveBillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOReceiveBillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOReceiveBillEntity t);

    /**
     * 新增收文
     * 
     * @param tOReceiveBill
     * @param request
     */
    public void addReceiveBill(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request);
}
