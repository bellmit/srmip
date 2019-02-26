package com.kingtake.project.service.apprincomecontract;
import java.io.Serializable;

import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.service.appr.ApprServiceI;

public interface TPmIncomeContractApprServiceI extends ApprServiceI{
	
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
 	public boolean doAddSql(TPmIncomeContractApprEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmIncomeContractApprEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmIncomeContractApprEntity t);

   
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TPmIncomeContractApprEntity t);
}
