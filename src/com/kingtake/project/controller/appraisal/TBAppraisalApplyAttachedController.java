package com.kingtake.project.controller.appraisal;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;
import com.kingtake.project.service.appraisal.TBAppraisalApplyAttachedServiceI;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 鉴定申请附表
 * @author onlineGenerator
 * @date 2016-01-21 16:42:02
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalApplyAttachedController")
public class TBAppraisalApplyAttachedController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAppraisalApplyAttachedController.class);

	@Autowired
	private TBAppraisalApplyAttachedServiceI tBAppraisalApplyAttachedService;
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
	 * 鉴定申请附表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBAppraisalApplyAttached")
	public ModelAndView tBAppraisalApplyAttached(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyAttachedList");
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
	public void datagrid(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyAttachedEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalApplyAttached, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAppraisalApplyAttachedService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除鉴定申请附表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAppraisalApplyAttached = systemService.getEntity(TBAppraisalApplyAttachedEntity.class, tBAppraisalApplyAttached.getId());
		message = "鉴定申请附表删除成功";
		try{
			tBAppraisalApplyAttachedService.delete(tBAppraisalApplyAttached);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "鉴定申请附表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除鉴定申请附表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "鉴定申请附表删除成功";
		try{
			for(String id:ids.split(",")){
				TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached = systemService.getEntity(TBAppraisalApplyAttachedEntity.class, 
				id
				);
				tBAppraisalApplyAttachedService.delete(tBAppraisalApplyAttached);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "鉴定申请附表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加鉴定申请附表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "鉴定申请附表添加成功";
		try{
			tBAppraisalApplyAttachedService.save(tBAppraisalApplyAttached);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "鉴定申请附表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新鉴定申请附表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "鉴定申请附表更新成功";
		TBAppraisalApplyAttachedEntity t = tBAppraisalApplyAttachedService.get(TBAppraisalApplyAttachedEntity.class, tBAppraisalApplyAttached.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalApplyAttached, t);
			tBAppraisalApplyAttachedService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "鉴定申请附表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 鉴定申请附表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisalApplyAttached.getId())) {
			tBAppraisalApplyAttached = tBAppraisalApplyAttachedService.getEntity(TBAppraisalApplyAttachedEntity.class, tBAppraisalApplyAttached.getId());
			req.setAttribute("tBAppraisalApplyAttachedPage", tBAppraisalApplyAttached);
		}
		return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyAttached-add");
	}
	/**
	 * 鉴定申请附表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisalApplyAttached.getId())) {
			tBAppraisalApplyAttached = tBAppraisalApplyAttachedService.getEntity(TBAppraisalApplyAttachedEntity.class, tBAppraisalApplyAttached.getId());
			req.setAttribute("tBAppraisalApplyAttachedPage", tBAppraisalApplyAttached);
		}
		return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyAttached-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyAttachedUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyAttachedEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalApplyAttached, request.getParameterMap());
		List<TBAppraisalApplyAttachedEntity> tBAppraisalApplyAttacheds = this.tBAppraisalApplyAttachedService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"鉴定申请附表");
		modelMap.put(NormalExcelConstants.CLASS,TBAppraisalApplyAttachedEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("鉴定申请附表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAppraisalApplyAttacheds);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "鉴定申请附表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBAppraisalApplyAttachedEntity.class);
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
				List<TBAppraisalApplyAttachedEntity> listTBAppraisalApplyAttachedEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBAppraisalApplyAttachedEntity.class,params);
				for (TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached : listTBAppraisalApplyAttachedEntitys) {
					tBAppraisalApplyAttachedService.save(tBAppraisalApplyAttached);
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
