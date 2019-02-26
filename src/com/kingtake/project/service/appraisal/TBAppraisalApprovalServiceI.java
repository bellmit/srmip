package com.kingtake.project.service.appraisal;
import java.io.Serializable;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.appraisal.TBAppraisalApprovalEntity;

public interface TBAppraisalApprovalServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalApprovalEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalApprovalEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalApprovalEntity t);
 	
 	public void doAddOrUpdate(TBAppraisalApprovalEntity tBAppraisaApproval, String sendIds);

    /**
     * 更新审批状态
     * 
     * @param id
     * @return
     */
    public AjaxJson updateFinishFlag(String id);

    /**
     * 更新审批状态2
     * 
     * @param id
     */
    public AjaxJson updateFinishFlag2(String id);
}
