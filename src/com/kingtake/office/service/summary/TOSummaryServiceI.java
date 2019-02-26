package com.kingtake.office.service.summary;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.summary.TOSummaryEntity;

public interface TOSummaryServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TOSummaryEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TOSummaryEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TOSummaryEntity t);

    /**
     * 发送
     * 
     * @param tOSummary
     * @param receiverIds
     * @param receiverNames
     */
    public void doSubmit(TOSummaryEntity tOSummary, String receiverIds, String receiverNames);

    /**
     * 接收
     * 
     * @param tOSummary
     */
    public void doReceive(TOSummaryEntity tOSummary);

    /**
     * 逻辑删除
     * 
     * @param ids
     */
    public void doLogicDelete(String ids);

    /**
     * 恢复
     * 
     * @param ids
     */
    public void doBack(String ids);
}
