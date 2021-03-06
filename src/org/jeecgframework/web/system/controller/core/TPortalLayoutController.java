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
 * @Description: ???????????????
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
	 * ????????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPortalLayout")
	public ModelAndView tPortalLayout(HttpServletRequest request) {
		return new ModelAndView("system/portal/tPortalLayoutList");
	}

	/**
	 * easyui AJAX????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPortalLayoutEntity tPortalLayout,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPortalLayoutEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPortalLayout, request.getParameterMap());
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPortalLayoutService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPortalLayoutEntity tPortalLayout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPortalLayout = systemService.getEntity(TPortalLayoutEntity.class, tPortalLayout.getId());
		message = "???????????????????????????";
		try{
			tPortalLayoutService.delete(tPortalLayout);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "???????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "???????????????????????????";
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
			message = "???????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * ?????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPortalLayoutEntity tPortalLayout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "???????????????????????????";
		try{
			tPortalLayoutService.save(tPortalLayout);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "???????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ?????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPortalLayoutEntity tPortalLayout, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "???????????????????????????";
		TPortalLayoutEntity t = tPortalLayoutService.get(TPortalLayoutEntity.class, tPortalLayout.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPortalLayout, t);
			tPortalLayoutService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "???????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * ?????????????????????????????????
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
	 * ?????????????????????????????????
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
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("system/portal/tPortalLayoutUpload");
	}
	
	/**
	 * ??????excel
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
		modelMap.put(NormalExcelConstants.FILE_NAME,"???????????????");
		modelMap.put(NormalExcelConstants.CLASS,TPortalLayoutEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("?????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPortalLayouts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPortalLayoutEntity tPortalLayout,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "???????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
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
			MultipartFile file = entity.getValue();// ????????????????????????
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TPortalLayoutEntity> listTPortalLayoutEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPortalLayoutEntity.class,params);
				for (TPortalLayoutEntity tPortalLayout : listTPortalLayoutEntitys) {
					tPortalLayoutService.save(tPortalLayout);
				}
				j.setMsg("?????????????????????");
			} catch (Exception e) {
				j.setMsg("?????????????????????");
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
