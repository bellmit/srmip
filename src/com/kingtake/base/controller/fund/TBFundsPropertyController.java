package com.kingtake.base.controller.fund;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.quota.TBFundsBudgetRelaEntity;
import com.kingtake.base.service.fund.TBFundsPropertyServiceI;



/**
 * @Title: Controller
 * @Description: 经费类型表
 * @author onlineGenerator
 * @date 2015-07-17 11:30:53
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBFundsPropertyController")
public class TBFundsPropertyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBFundsPropertyController.class);

	@Autowired
	private TBFundsPropertyServiceI tBFundsPropertyService;
	@Autowired
	private SystemService systemService;
//	@Autowired
//	private CommonServiceImpl commonServiceImpl;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	                /**
     * 经费类型表列表 页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "tBFundsProperty")
	public ModelAndView tBFundsProperty(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/base/fund/tBFundsPropertyList");
	}
	@RequestMapping(params = "tBFundsPropertyNew")
	public ModelAndView tBFundsPropertyNew(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/base/fund/tBFundsPropertyList2");
	}
	/**
     * 查询预算类型
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "getbudgetCategory")
    @ResponseBody
    public JSONArray getbudgetCategory(HttpServletRequest req, HttpServletResponse response) {
    	JSONArray jsonArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TBApprovalBudgetRelaEntity.class);
        //cq.eq("validFlag", "1");
        cq.add();
        List<TBApprovalBudgetRelaEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TBApprovalBudgetRelaEntity parent : parentList) {
    	  JSONObject json = new JSONObject();
          json.put("id", parent.getId());
          json.put("text", parent.getBudgetNae());
          //json.put("code", parent.getFundsCode());
          jsonArray.add(json);
        }
        return jsonArray;
    }

	                /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBFundsPropertyEntity tBFundsProperty,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBFundsPropertyEntity.class, dataGrid);
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBFundsProperty, request.getParameterMap());
		try{
            cq.eq("validFlag", "1");
            //自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBFundsPropertyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "datagrid2")
	@ResponseBody
	public void datagrid2(HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid) {
		String id = "";
		if(StringUtil.isNotEmpty(request.getAttribute("id"))){
			id = (String)request.getAttribute("id");
		}
		List<Map<String, Object>> list = getProprertyList(id);
        PageList pageList = new PageList(list,"",10,1,10);
        
        TagUtil.ListtoView(response,pageList);
	}
	private List<Map<String, Object>> getProprertyList(String id){
		 String sql = "select A.ID as \"id\", "+
			"A.FUNDS_NAME as \"fundsName\", "+
			"B.BUDGET_CATEGORY as \"budgetCategory\", "+
			"B.BUDGET_CATEGORY_NAME as \"budgetCategoryName\", "+
			//"B.INDIRECT_FEE_CALU as \"indirectFeeCalu\", "+
			" (case B.INDIRECT_FEE_CALU when '1' then '根据直接费计算间接费' "+
			" when '2' then '根据总经费计算绩效' when '3' then '无间接费' "+
			" when '4' then '合同直接约定间接费' when '5' then '科研管理部门下拨' "+
			" when '6' then '总经费的1/21' end) indirectFeeCalu, "+
			"B.UNIVERSITY_PROP as \"universityProp\", "+
			"B.UNIT_PROP as \"unitProp\", "+
			"B.DEV_UNIT_PROP as \"devUnitProp\", "+
			"B.PROJECTGROUP_PROP as \"projectGroupProp\", "+
			"A.MEMO as \"memo\" " + 
			"from t_b_funds_property A left join T_PM_BUDGET_FUNDS_REL B on A.ID=B.FUNDS_TYPE "+
			"where A.valid_flag=1 ";
        
        if(StringUtil.isNotEmpty(id)){
        	sql += " and A.ID='" + id + "'";
        }
        return systemService.findForJdbc(sql);
	}

	                /**
     * 删除经费类型表
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBFundsPropertyEntity tBFundsProperty, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "经费类型表删除成功";
		try{
            tBFundsProperty = systemService.getEntity(TBFundsPropertyEntity.class, tBFundsProperty.getId());
            //            List<TBFundsBudgetRelaEntity> tBFundsBudgetRelas = systemService.findByProperty(
            //                    TBFundsBudgetRelaEntity.class, "fundsPropertyCode", tBFundsProperty.getId());
            //            if (tBFundsBudgetRelas != null && tBFundsBudgetRelas.size() > 0) {
            //                systemService.deleteAllEntitie(tBFundsBudgetRelas);
            //            }
            tBFundsProperty.setValidFlag("0");//置为无效
            tBFundsPropertyService.updateEntitie(tBFundsProperty);
		}catch(Exception e){
			e.printStackTrace();
            message = "经费类型表删除失败";
            j.setSuccess(false);
		}
		j.setMsg(message);
		return j;
	}
	
	                /**
     * 批量删除经费类型表
     * 
     * @return
     */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "经费类型表删除成功";
		try{
			for(String id:ids.split(",")){
				TBFundsPropertyEntity tBFundsProperty = systemService.getEntity(TBFundsPropertyEntity.class, 
				id
				);
                List<TBFundsBudgetRelaEntity> tBFundsBudgetRelas = systemService.findByProperty(
                        TBFundsBudgetRelaEntity.class, "fundsPropertyCode", id);
                if (tBFundsBudgetRelas != null && tBFundsBudgetRelas.size() > 0) {
                    systemService.deleteAllEntitie(tBFundsBudgetRelas);
                }
				tBFundsPropertyService.delete(tBFundsProperty);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "经费类型表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 @RequestMapping(params = "saveOrUpdate")
	 @ResponseBody
	 public AjaxJson saveOrUpdate(TBFundsPropertyEntity tBFundsProperty, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		
		if(request.getParameter("pageType").equals("add")){
			try{
				message = "经费类型表添加成功";
	            tBFundsProperty.setValidFlag("1");
	            tBFundsPropertyService.save(tBFundsProperty);
	            addFundsRel(tBFundsProperty.getId(),request);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
	            message = "经费类型表添加失败";
				throw new BusinessException(e.getMessage());
			}
		}else if(request.getParameter("pageType").equals("update")){
			message = "经费类型表更新成功";
			TBFundsPropertyEntity t = tBFundsPropertyService.get(TBFundsPropertyEntity.class, tBFundsProperty.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tBFundsProperty, t);
				tBFundsPropertyService.saveOrUpdate(t);
				addOrUpdateFundsRel(tBFundsProperty.getId(),request);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			    message = "经费类型表更新失败";
				throw new BusinessException(e.getMessage());
			}
		}
		
		j.setMsg(message);
		return j;
	 }

	                /**
     * 添加经费类型表
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBFundsPropertyEntity tBFundsProperty, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "经费类型表添加成功";
		try{
            tBFundsProperty.setValidFlag("1");
            tBFundsPropertyService.save(tBFundsProperty);
            addFundsRel(tBFundsProperty.getId(),request);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "经费类型表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	private void addFundsRel(String fundsType,HttpServletRequest request){
		String sql = "INSERT INTO T_PM_BUDGET_FUNDS_REL(ID, FUNDS_TYPE, PROJECT_CATEGORY, BUDGET_CATEGORY, "
				+ "INDIRECT_FEE_CALU, UNIVERSITY_PROP, UNIT_PROP, DEV_UNIT_PROP, PROJECTGROUP_PROP, "
				+ "INDIRECT_FEE_CALU_ID, BUDGET_CATEGORY_NAME) values (?,?,?,?,?,?,?,?,?,?,?)";
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String projectCategory = request.getParameter("projectCategory");
		String budgetCategory = request.getParameter("budgetCategory");
		String indirectFeeCalu = request.getParameter("indirectFeeCalu");
		String universityProp = request.getParameter("universityProp");
		String unitProp = request.getParameter("unitProp");
		String devUnitProp = request.getParameter("devUnitProp");
		String projectgroupProp = request.getParameter("projectgroupProp");
		String indirectFeeCaluId = request.getParameter("indirectFeeCaluId");
		String budgetCategoryName = request.getParameter("budgetCategoryName");
		
		systemService.executeSql(sql, id,fundsType,projectCategory,budgetCategory,
				indirectFeeCalu,universityProp,unitProp,devUnitProp,projectgroupProp,
				indirectFeeCaluId,budgetCategoryName);
	}
	                /**
     * 更新经费类型表
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBFundsPropertyEntity tBFundsProperty, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "经费类型表更新成功";
		TBFundsPropertyEntity t = tBFundsPropertyService.get(TBFundsPropertyEntity.class, tBFundsProperty.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBFundsProperty, t);
			tBFundsPropertyService.saveOrUpdate(t);
			addOrUpdateFundsRel(tBFundsProperty.getId(),request);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "经费类型表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	private void addOrUpdateFundsRel(String fundsType ,HttpServletRequest request){
		String budgetCategory = request.getParameter("budgetCategory");
		String sql = "select * from T_PM_BUDGET_FUNDS_REL where FUNDS_TYPE='" + fundsType + "'";
		if(systemService.findListbySql(sql).size() > 0){
			updateFundsRel(fundsType ,request);
		}else{
			addFundsRel(fundsType,request);
		}
	}
	private void updateFundsRel(String fundsType ,HttpServletRequest request){
		
		StringBuffer sql = new StringBuffer(" update T_PM_BUDGET_FUNDS_REL set ");

		if(!StringUtil.isNotEmpty(fundsType)){
			return;
		}
		
		if(StringUtil.isNotEmpty(request.getParameter("budgetCategory"))){
			String budgetCategory = (String)request.getParameter("budgetCategory");
			sql.append(" BUDGET_CATEGORY='" + budgetCategory + "',");
		}
		if(StringUtil.isNotEmpty(request.getParameter("budgetCategoryName"))){
			String budgetCategoryName = (String)request.getParameter("budgetCategoryName");
			sql.append(" BUDGET_CATEGORY_NAME='" + budgetCategoryName + "',");
		}
		if(StringUtil.isNotEmpty(request.getParameter("indirectFeeCalu"))){
			String indirectFeeCalu = (String)request.getParameter("indirectFeeCalu");
			sql.append(" INDIRECT_FEE_CALU='" + indirectFeeCalu + "',");
		}
		if(StringUtil.isNotEmpty(request.getParameter("universityProp"))){
			String universityProp = (String)request.getParameter("universityProp");
			sql.append(" UNIVERSITY_PROP='" + universityProp + "',");
		}
		if(StringUtil.isNotEmpty(request.getParameter("unitProp"))){
			String unitProp = (String)request.getParameter("unitProp");
			sql.append(" UNIT_PROP='" + unitProp + "',");
		}
		if(StringUtil.isNotEmpty(request.getParameter("devUnitProp"))){
			String devUnitProp = (String)request.getParameter("devUnitProp");
			sql.append(" DEV_UNIT_PROP='" + devUnitProp + "',");
		}
		if(StringUtil.isNotEmpty(request.getParameter("projectGroupProp"))){
			String projectGroupProp = (String)request.getParameter("projectGroupProp");
			sql.append(" PROJECTGROUP_PROP='" + projectGroupProp + "',");
		}
		
		if(StringUtil.isNotEmpty(request.getParameter("projectCategory"))){
			String projectCategory = (String)request.getParameter("projectCategory");
			sql.append(" PROJECT_CATEGORY='" + projectCategory + "',");
		}
		if(StringUtil.isNotEmpty(request.getParameter("indirectFeeCaluId"))){
			String indirectFeeCaluId = (String)request.getParameter("indirectFeeCaluId");
			sql.append(" INDIRECT_FEE_CALU_ID='" + indirectFeeCaluId + "',");
		}
		
		String sqlStr = sql.toString();
		sqlStr = sqlStr.substring(0, sqlStr.lastIndexOf(","));
		
		sqlStr +=" where FUNDS_TYPE='" + fundsType + "'";
		System.out.println(sqlStr);
		systemService.updateBySqlString(sqlStr);
	}
	

	                /**
     * 经费类型表新增页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest req) {
		req.setAttribute("pageType", "add");
		return new ModelAndView("com/kingtake/base/fund/tBFundsProperty-update");
	}
	
    /**
     * 经费类型表编辑页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBFundsPropertyEntity tBFundsProperty, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBFundsProperty.getId())) {
			List<Map<String,Object>> datas = this.getProprertyList(tBFundsProperty.getId());
			if(datas.size() > 0){
				req.setAttribute("tBFundsPropertyPage", datas.get(0));
			}
		}
		req.setAttribute("pageType", "update");
		return new ModelAndView("com/kingtake/base/fund/tBFundsProperty-update");
	}
	
	/**
     * 选择经费类型跳转页面
     * @param request
     * @return
     */
    @RequestMapping(params = "goSelect")
    public ModelAndView goSelect(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/fund/tBFundsPropertySelectList");
    }
	
	                /**
     * 导入功能跳转
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/base/fund/tBFundsPropertyUpload");
	}
	
	                /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBFundsPropertyEntity tBFundsProperty,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBFundsPropertyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBFundsProperty, request.getParameterMap());
		List<TBFundsPropertyEntity> tBFundsPropertys = this.tBFundsPropertyService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "经费类型表");
		modelMap.put(NormalExcelConstants.CLASS,TBFundsPropertyEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("经费类型表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBFundsPropertys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBFundsPropertyEntity tBFundsProperty,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "经费类型表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBFundsPropertyEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TBFundsPropertyEntity> listTBFundsPropertyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBFundsPropertyEntity.class,params);
				for (TBFundsPropertyEntity tBFundsProperty : listTBFundsPropertyEntitys) {
					tBFundsPropertyService.save(tBFundsProperty);
				}
                j.setMsg("文件导入成功！");
			} catch (Exception e) {
                j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
