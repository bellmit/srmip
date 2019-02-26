package com.kingtake.project.service.approutcomecontract;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.model.json.AjaxJson;

import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.service.appr.ApprServiceI;

public interface TPmOutcomeContractApprServiceI extends ApprServiceI{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmOutcomeContractApprEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmOutcomeContractApprEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmOutcomeContractApprEntity t);

    /**
     * 导出模板
     * 
     * @param tPmOutcomeContractAppr
     * @return
     */
    public Workbook getPriceTemplate(TPmOutcomeContractApprEntity tPmOutcomeContractAppr);

}
