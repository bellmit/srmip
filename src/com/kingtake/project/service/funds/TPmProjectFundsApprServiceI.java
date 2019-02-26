package com.kingtake.project.service.funds;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;

public interface TPmProjectFundsApprServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
 	
    public void delMain(TPmProjectFundsApprEntity tPmProjectFundsApprEntity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmProjectFundsApprEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmProjectFundsApprEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmProjectFundsApprEntity t);
 	
 	/**
 	 * 初始化预算管理的同时，初始化预算
 	 * @param t
 	 */
 	public void init(TPmProjectFundsApprEntity t);

 	/**
     * 初始化预算基本信息经费预算表格
     * @param projApproval 立项形式
     * @param showFlag 预算信息表显示标志
     * @return 合同类经费预算汇总 list
     */
 	public  List<Map<String, Object>> initField(String projApproval );
    
 	/**
     * 初始化预算基本信息经费预算表格
     * @param projApproval 立项形式
     * @param showFlag 预算信息表显示标志
     * @param tpId 预算信息的id
     * @return 合同类经费预算汇总 list
     */
    public  List<Map<String, Object>> initFieldAndVal( String projApproval, String tpId);
    
    /**
     * 获取导入模板
     * 
     * @param planContractFlag
     * @return
     */
    public HSSFWorkbook getFundsTemplate(String tpId, String planContractFlag);

    /**
     * 保存预算审批表
     * @param tPmProjectFundsAppr
     */
    public void saveFundsAppr(TPmProjectFundsApprEntity tPmProjectFundsAppr);

    /**
     * 查询项目是否已做总预算
     * @param tPmProjectFundsAppr
     */
	public HashMap<String,Object> checkBudget(String projectId);

	/**
     * 查询项目的零基预算状态
     * @param tPmProjectFundsAppr
     */
	public List getProjectId(String projectId);

	
	/**
     * 修改零基预算状态
     * @param tPmProjectFundsAppr
     */
	public int editProjectLjysStatus(String projectId,int tzys_status);

	public void saveFundsDetail(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request);

}
