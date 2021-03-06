package com.kingtake.price.controller.tbjgbzhjsymx;
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.price.entity.tbjgbzhjsymx.TBJgbzHjsymxEntity;
import com.kingtake.price.service.tbjgbzhjsymx.TBJgbzHjsymxServiceI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;


import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: ??????????????????????????????
 * @author onlineGenerator
 * @date 2016-07-29 11:02:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzHjsymxController")
public class TBJgbzHjsymxController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzHjsymxController.class);

	@Autowired
	private TBJgbzHjsymxServiceI tBJgbzHjsymxService;
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
	 * ???????????????????????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzHjsymx")
	public ModelAndView tBJgbzHjsymx(HttpServletRequest request) {		
		return new ModelAndView("com/kingtake/price/tbjgbzhjsymx/tBJgbzHjsymxList");
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
	public void datagrid(TBJgbzHjsymxEntity tBJgbzHjsymx,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzHjsymxEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzHjsymx, request.getParameterMap());		
		
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzHjsymxService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzHjsymxEntity tBJgbzHjsymx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzHjsymx = systemService.getEntity(TBJgbzHjsymxEntity.class, tBJgbzHjsymx.getId());
		message = "??????????????????????????????????????????";
		try{
			tBJgbzHjsymxService.delete(tBJgbzHjsymx);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????????????????????????????????????????
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????????????????";
		try{
			for(String id:ids.split(",")){
				TBJgbzHjsymxEntity tBJgbzHjsymx = systemService.getEntity(TBJgbzHjsymxEntity.class, 
				id
				);
				tBJgbzHjsymxService.delete(tBJgbzHjsymx);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * ????????????????????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzHjsymxEntity tBJgbzHjsymx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????????????????";
		try{
			tBJgbzHjsymxService.save(tBJgbzHjsymx);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ????????????????????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzHjsymxEntity tBJgbzHjsymx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????????????????";
		
		try {
			if (StringUtils.isEmpty(tBJgbzHjsymx.getId())) {
				tBJgbzHjsymxService.save(tBJgbzHjsymx);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzHjsymxEntity t = tBJgbzHjsymxService.get(TBJgbzHjsymxEntity.class, tBJgbzHjsymx.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzHjsymx, t);
				tBJgbzHjsymxService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			message = "??????????????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzHjsymxEntity tBJgbzHjsymx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzHjsymx.getId())) {
			tBJgbzHjsymx = tBJgbzHjsymxService.getEntity(TBJgbzHjsymxEntity.class, tBJgbzHjsymx.getId());
			req.setAttribute("tBJgbzHjsymxPage", tBJgbzHjsymx);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzhjsymx/tBJgbzHjsymx-add");
	}
	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzHjsymxEntity tBJgbzHjsymx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzHjsymx.getId())) {
			tBJgbzHjsymx = tBJgbzHjsymxService.getEntity(TBJgbzHjsymxEntity.class, tBJgbzHjsymx.getId());
		}
		req.setAttribute("tBJgbzHjsymxPage", tBJgbzHjsymx);
		return new ModelAndView("com/kingtake/price/tbjgbzhjsymx/tBJgbzHjsymx-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzhjsymx/tBJgbzHjsymxUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzHjsymxEntity tBJgbzHjsymx,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzHjsymxEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzHjsymx, request.getParameterMap());
		List<TBJgbzHjsymxEntity> tBJgbzHjsymxs = this.tBJgbzHjsymxService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"??????????????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzHjsymxEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("????????????????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzHjsymxs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzHjsymxEntity tBJgbzHjsymx,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzHjsymxEntity.class);
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
				List<TBJgbzHjsymxEntity> listTBJgbzHjsymxEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzHjsymxEntity.class,params);
				for (TBJgbzHjsymxEntity tBJgbzHjsymx : listTBJgbzHjsymxEntitys) {
					tBJgbzHjsymxService.save(tBJgbzHjsymx);
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
