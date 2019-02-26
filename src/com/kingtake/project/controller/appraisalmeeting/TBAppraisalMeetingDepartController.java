package com.kingtake.project.controller.appraisalmeeting;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartEntity;
import com.kingtake.project.service.appraisalmeeting.TBAppraisalMeetingDepartServiceI;
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
 * @Description: 鉴定会单位信息表
 * @author onlineGenerator
 * @date 2016-01-22 14:27:34
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalMeetingDepartController")
public class TBAppraisalMeetingDepartController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAppraisalMeetingDepartController.class);

	@Autowired
	private TBAppraisalMeetingDepartServiceI tBAppraisalMeetingDepartService;
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
	 * 鉴定会单位信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBAppraisalMeetingDepart")
	public ModelAndView tBAppraisalMeetingDepart(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepartList");
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
	public void datagrid(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingDepartEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMeetingDepart, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAppraisalMeetingDepartService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除鉴定会单位信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAppraisalMeetingDepart = systemService.getEntity(TBAppraisalMeetingDepartEntity.class, tBAppraisalMeetingDepart.getId());
		message = "鉴定会单位信息表删除成功";
		try{
			tBAppraisalMeetingDepartService.delete(tBAppraisalMeetingDepart);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "鉴定会单位信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除鉴定会单位信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "鉴定会单位信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart = systemService.getEntity(TBAppraisalMeetingDepartEntity.class, 
				id
				);
				tBAppraisalMeetingDepartService.delete(tBAppraisalMeetingDepart);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "鉴定会单位信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加鉴定会单位信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "鉴定会单位信息表添加成功";
		try{
			tBAppraisalMeetingDepartService.save(tBAppraisalMeetingDepart);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "鉴定会单位信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新鉴定会单位信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "鉴定会单位信息表更新成功";
		TBAppraisalMeetingDepartEntity t = tBAppraisalMeetingDepartService.get(TBAppraisalMeetingDepartEntity.class, tBAppraisalMeetingDepart.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalMeetingDepart, t);
			tBAppraisalMeetingDepartService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "鉴定会单位信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 鉴定会单位信息表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisalMeetingDepart.getId())) {
			tBAppraisalMeetingDepart = tBAppraisalMeetingDepartService.getEntity(TBAppraisalMeetingDepartEntity.class, tBAppraisalMeetingDepart.getId());
			req.setAttribute("tBAppraisalMeetingDepartPage", tBAppraisalMeetingDepart);
		}
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepart-add");
	}
	/**
	 * 鉴定会单位信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisalMeetingDepart.getId())) {
			tBAppraisalMeetingDepart = tBAppraisalMeetingDepartService.getEntity(TBAppraisalMeetingDepartEntity.class, tBAppraisalMeetingDepart.getId());
			req.setAttribute("tBAppraisalMeetingDepartPage", tBAppraisalMeetingDepart);
		}
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepart-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepartUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingDepartEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMeetingDepart, request.getParameterMap());
		List<TBAppraisalMeetingDepartEntity> tBAppraisalMeetingDeparts = this.tBAppraisalMeetingDepartService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"鉴定会单位信息表");
		modelMap.put(NormalExcelConstants.CLASS,TBAppraisalMeetingDepartEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("鉴定会单位信息表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAppraisalMeetingDeparts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "鉴定会单位信息表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBAppraisalMeetingDepartEntity.class);
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
				List<TBAppraisalMeetingDepartEntity> listTBAppraisalMeetingDepartEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBAppraisalMeetingDepartEntity.class,params);
				for (TBAppraisalMeetingDepartEntity tBAppraisalMeetingDepart : listTBAppraisalMeetingDepartEntitys) {
					tBAppraisalMeetingDepartService.save(tBAppraisalMeetingDepart);
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
