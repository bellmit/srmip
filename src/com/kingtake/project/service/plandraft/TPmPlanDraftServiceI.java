package com.kingtake.project.service.plandraft;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDraftEntity;

public interface TPmPlanDraftServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmPlanDraftEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmPlanDraftEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmPlanDraftEntity t);

    /**
     * 生成计划草案
     * 
     * @param tPmPlanDraft
     * @param request
     */
    public void savePlanDraft(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request);

    /**
     * 删除计划草案
     * 
     * @param tPmPlanDraft
     */
    public void deletePlan(TPmPlanDraftEntity tPmPlanDraft);


    /**
     * 审批
     * 
     * @param planDetail
     * @param req
     */
    public AjaxJson doAudit(TPmPlanDetailEntity planDetail, HttpServletRequest req);
    
    /**
     * 项目基本信息导出EXCEL
     * 
     * @return
     */
    public Workbook exportProject(String id);

}
