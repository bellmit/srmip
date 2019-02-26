package com.kingtake.project.controller.funds;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
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

import com.kingtake.common.util.PriceUtil;
import com.kingtake.project.entity.funds.TPmFundsBudgetAddendumEntity;
import com.kingtake.project.service.funds.TPmFundsBudgetAddendumServiceI;



/**   
 * @Title: Controller
 * @Description: 预算附表
 * @author onlineGenerator
 * @date 2015-08-04 16:16:57
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmFundsBudgetAddendumController")
public class TPmFundsBudgetAddendumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmFundsBudgetAddendumController.class);

	@Autowired
	private TPmFundsBudgetAddendumServiceI tPmFundsBudgetAddendumService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmFundsBudgetAddendum")
	public ModelAndView tPmFundsBudgetAddendum(HttpServletRequest request) {
	    //关联预算基本信息ID
        String tpId = request.getParameter("tpId");
        request.setAttribute("apprId", tpId);
        //记录表类型标记
        String tableFlag = request.getParameter("tableFlag");
        request.setAttribute("tableFlag", tableFlag);
        //是否可编辑
        String edit = request.getParameter("edit");
        if("false".equals(edit)){
        	request.setAttribute("edit", false);
        }else{
        	request.setAttribute("edit", true);
        }
        
		return new ModelAndView("com/kingtake/project/funds/tPmFundsBudgetAddendumList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param tPmFundsBudgetAddendum
	 * @param request
	 * @param response
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum, HttpServletRequest request, 
			HttpServletResponse response) {
		String sql = "SELECT ID, NUM, CONTENT, MODEL, ACCOUNT, PRICE, MONEY, REMARK, ADD_CHILD_FLAG AS ADDCHILDFLAG " + 
				" FROM T_PM_FUNDS_BUDGET_ADDENDUM WHERE PID = ? ORDER BY NUM ASC";
		List<Map<String, Object>> list = systemService.findForJdbc(sql, tPmFundsBudgetAddendum.getPid());
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> root = list.get(list.size()-1);
		if(list.size() > 1){
			List<Map<String, Object>> children = list.subList(0, list.size() - 1);
			root.put("children", children);
		}
		result.add(root);
		TagUtil.listToJson(response, result);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		try{
			tPmFundsBudgetAddendumService.del(tPmFundsBudgetAddendum.getId());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		
		try{
			// 查询数据库的最大序号
			Object max = systemService.getSession().createCriteria(TPmFundsBudgetAddendumEntity.class)
				.add(Restrictions.eq("pid", tPmFundsBudgetAddendum.getPid()))
				.setProjection(Projections.max("num")).uniqueResult();
			tPmFundsBudgetAddendum.setNum(max == null ? "01" : PriceUtil.getNum(max.toString()));
		    // 保存
			tPmFundsBudgetAddendumService.save(tPmFundsBudgetAddendum);
			// 将新建的预算传递到页面
			j.setObj(JSONHelper.bean2json(tPmFundsBudgetAddendum));
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "T_PM_FUNDS_BUDGET_ADDENDUM添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		
		try {
			tPmFundsBudgetAddendumService.update(tPmFundsBudgetAddendum);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/funds/tPmFundsBudgetAddendumUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmFundsBudgetAddendumEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmFundsBudgetAddendum, request.getParameterMap());
		List<TPmFundsBudgetAddendumEntity> tPmFundsBudgetAddendums = this.tPmFundsBudgetAddendumService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_PM_FUNDS_BUDGET_ADDENDUM");
		modelMap.put(NormalExcelConstants.CLASS,TPmFundsBudgetAddendumEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_PM_FUNDS_BUDGET_ADDENDUM列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmFundsBudgetAddendums);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_PM_FUNDS_BUDGET_ADDENDUM");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmFundsBudgetAddendumEntity.class);
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
				List<TPmFundsBudgetAddendumEntity> listTPmFundsBudgetAddendumEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmFundsBudgetAddendumEntity.class,params);
				for (TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum : listTPmFundsBudgetAddendumEntitys) {
					tPmFundsBudgetAddendumService.save(tPmFundsBudgetAddendum);
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
