package com.kingtake.project.service.apprcontractcheck;
import java.io.Serializable;

import org.jeecgframework.core.common.model.json.AjaxJson;

import com.kingtake.project.entity.apprcontractcheck.TPmContractCheckEntity;
import com.kingtake.project.service.appr.ApprServiceI;

public interface TPmContractCheckServiceI extends ApprServiceI{
	
 	@Override
    public <T> void delete(T entity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractCheckEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractCheckEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractCheckEntity t);
 	
 	
 	public boolean updateOperateState(TPmContractCheckEntity t);

    /**
     * 更新审批状态
     * 
     * @param id
     * @return
     */
    public AjaxJson updateOperateStatus(String id);

    /**
     * 更新审批状态
     * 
     * @param id
     * @return
     */
    public AjaxJson updateOperateStatus2(String id);    
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TPmContractCheckEntity t);
}
