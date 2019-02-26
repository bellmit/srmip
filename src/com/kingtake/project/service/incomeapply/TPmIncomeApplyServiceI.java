package com.kingtake.project.service.incomeapply;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;

public interface TPmIncomeApplyServiceI extends CommonService {
	
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
    public boolean doAddSql(TPmIncomeApplyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TPmIncomeApplyEntity t);
 	
    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPmIncomeApplyEntity t);

    /**
     * 保存来款申请
     * 
     * @param tPmIncomeApply
     */
    public void saveIncomeApply(TPmIncomeApplyEntity tPmIncomeApply, List<TPmIncomeContractNodeRelaEntity> nodeList);

    /**
     * 来款申请审核通过，则分配到账
     * 
     * @param tPmIncomeApply
     */
    public void doAudit(TPmIncomeApplyEntity tPmIncomeApply);
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TPmIncomeApplyEntity t);

    /**
     * 审核不通过
     * @param tPmIncomeApply
     */
    public void doReject(TPmIncomeApplyEntity tPmIncomeApply);

    /**
     * 删除来款申请
     * @param tPmIncomeApply
     */
    public void deleteApply(TPmIncomeApplyEntity tPmIncomeApply);

    /**
     * 提交
     * @param tPmIncomeApply
     */
    public void doSubmit(TPmIncomeApplyEntity tPmIncomeApply);
}
