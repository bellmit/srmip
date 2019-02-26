package com.kingtake.project.service.incomeplan;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;

public interface TPmIncomePlanServiceI extends CommonService {
	
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
    public boolean doAddSql(TPmIncomePlanEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TPmIncomePlanEntity t);
 	
    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPmIncomePlanEntity t);
    
    /**
     * 提交
     * @param tPmIncomeApply
     */
    public void doSubmit(TPmIncomePlanEntity tPmIncomePlan);
    
    /**
     * 审核不通过
     * @param tPmIncomeApply
     */
    public void doReject(TPmIncomePlanEntity tPmIncomePlan);
    /**
     * 计算项目已安排、当年经费、账面余额
     * @param planId
     * @param projectId
     */
    public void sumMoney(String planId,String projectId);
}
