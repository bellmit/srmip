package org.jeecgframework.web.system.controller.core;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
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
import org.jeecgframework.web.system.pojo.base.TPortalLayoutEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.TPortalLayoutServiceI;
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



/**   
 * @Title: Controller
 * @Description: 待办布局表
 * @author onlineGenerator
 * @date 2015-06-10 10:53:47
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPortalLayoutController")
public class TPortalLayoutController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPortalLayoutController.class);

	@Autowired
	private TPortalLayoutServiceI tPortalLayoutService;
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
	 * 待办布局表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPortalLayout")
	public ModelAndView tPortalLayout(HttpServletRequest request) {
		return new ModelAndView("system/portal/tPortalLayoutList");
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
	public void datagrid(TPortalLayoutEntity tPortalLayout,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPortalLayoutEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPortalLayout, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPortalLayoutService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除待办布局表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPortalLayoutEntity tPortalLayout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPortalLayout = systemService.getEntity(TPortalLayoutEntity.class, tPortalLayout.getId());
		message = "待办布局表删除成功";
		try{
			tPortalLayoutService.delete(tPortalLayout);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "待办布局表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除待办布局表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "待办布局表删除成功";
		try{
			for(String id:ids.split(",")){
				TPortalLayoutEntity tPortalLayout = systemService.getEntity(TPortalLayoutEntity.class, 
				id
				);
				tPortalLayoutService.delete(tPortalLayout);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "待办布局表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加待办布局表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPortalLayoutEntity tPortalLayout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "待办布局表添加成功";
		try{
			tPortalLayoutService.save(tPortalLayout);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "待办布局表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新待办布局表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPortalLayoutEntity tPortalLayout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "待办布局表更新成功";
		TPortalLayoutEntity t = tPortalLayoutService.get(TPortalLayoutEntity.class, tPortalLayout.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPortalLayout, t);
			tPortalLayoutService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "待办布局表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 待办布局表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPortalLayoutEntity tPortalLayout, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPortalLayout.getId())) {
			tPortalLayout = tPortalLayoutService.getEntity(TPortalLayoutEntity.class, tPortalLayout.getId());
			req.setAttribute("tPortalLayoutPage", tPortalLayout);
		}
		return new ModelAndView("system/portal/tPortalLayout-add");
	}
	/**
	 * 待办布局表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPortalLayoutEntity tPortalLayout, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPortalLayout.getId())) {
			tPortalLayout = tPortalLayoutService.getEntity(TPortalLayoutEntity.class, tPortalLayout.getId());
			req.setAttribute("tPortalLayoutPage", tPortalLayout);
		}
		return new ModelAndView("system/portal/tPortalLayout-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("system/portal/tPortalLayoutUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPortalLayoutEntity tPortalLayout,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPortalLayoutEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPortalLayout, request.getParameterMap());
		List<TPortalLayoutEntity> tPortalLayouts = this.tPortalLayoutService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"待办布局表");
		modelMap.put(NormalExcelConstants.CLASS,TPortalLayoutEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("待办布局表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPortalLayouts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPortalLayoutEntity tPortalLayout,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "待办布局表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPortalLayoutEntity.class);
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
				List<TPortalLayoutEntity> listTPortalLayoutEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPortalLayoutEntity.class,params);
				for (TPortalLayoutEntity tPortalLayout : listTPortalLayoutEntitys) {
					tPortalLayoutService.save(tPortalLayout);
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
