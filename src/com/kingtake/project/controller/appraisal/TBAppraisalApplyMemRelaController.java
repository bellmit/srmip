package com.kingtake.project.controller.appraisal;
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

import com.kingtake.project.entity.appraisal.TBAppraisalApplyMemRelaEntity;
import com.kingtake.project.service.appraisal.TBAppraisalApplyMemRelaServiceI;



/**   
 * @Title: Controller
 * @Description: T_B_APPRAISA_APPLY_MEM_RELA
 * @author onlineGenerator
 * @date 2015-09-09 09:44:56
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisaApplyMemRelaController")
public class TBAppraisalApplyMemRelaController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAppraisalApplyMemRelaController.class);

	@Autowired
	private TBAppraisalApplyMemRelaServiceI tBAppraisaApplyMemRelaService;
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
	 * T_B_APPRAISA_APPLY_MEM_RELA?????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBAppraisaApplyMemRela")
	public ModelAndView tBAppraisaApplyMemRela(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/test/tBAppraisaApplyMemRelaList");
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
	public void datagrid(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyMemRelaEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisaApplyMemRela, request.getParameterMap());
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAppraisaApplyMemRelaService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ??????T_B_APPRAISA_APPLY_MEM_RELA
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAppraisaApplyMemRela = systemService.getEntity(TBAppraisalApplyMemRelaEntity.class, tBAppraisaApplyMemRela.getId());
		message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
		try{
			tBAppraisaApplyMemRelaService.delete(tBAppraisaApplyMemRela);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ????????????T_B_APPRAISA_APPLY_MEM_RELA
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
		try{
			for(String id:ids.split(",")){
				TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela = systemService.getEntity(TBAppraisalApplyMemRelaEntity.class, 
				id
				);
				tBAppraisaApplyMemRelaService.delete(tBAppraisaApplyMemRela);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * ??????T_B_APPRAISA_APPLY_MEM_RELA
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
		try{
			tBAppraisaApplyMemRelaService.save(tBAppraisaApplyMemRela);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????T_B_APPRAISA_APPLY_MEM_RELA
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
		TBAppraisalApplyMemRelaEntity t = tBAppraisaApplyMemRelaService.get(TBAppraisalApplyMemRelaEntity.class, tBAppraisaApplyMemRela.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAppraisaApplyMemRela, t);
			tBAppraisaApplyMemRelaService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "T_B_APPRAISA_APPLY_MEM_RELA????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * T_B_APPRAISA_APPLY_MEM_RELA??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisaApplyMemRela.getId())) {
			tBAppraisaApplyMemRela = tBAppraisaApplyMemRelaService.getEntity(TBAppraisalApplyMemRelaEntity.class, tBAppraisaApplyMemRela.getId());
			req.setAttribute("tBAppraisaApplyMemRelaPage", tBAppraisaApplyMemRela);
		}
		return new ModelAndView("com/kingtake/test/tBAppraisaApplyMemRela-add");
	}
	/**
	 * T_B_APPRAISA_APPLY_MEM_RELA??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisaApplyMemRela.getId())) {
			tBAppraisaApplyMemRela = tBAppraisaApplyMemRelaService.getEntity(TBAppraisalApplyMemRelaEntity.class, tBAppraisaApplyMemRela.getId());
			req.setAttribute("tBAppraisaApplyMemRelaPage", tBAppraisaApplyMemRela);
		}
		return new ModelAndView("com/kingtake/test/tBAppraisaApplyMemRela-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/test/tBAppraisaApplyMemRelaUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyMemRelaEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisaApplyMemRela, request.getParameterMap());
		List<TBAppraisalApplyMemRelaEntity> tBAppraisaApplyMemRelas = this.tBAppraisaApplyMemRelaService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_B_APPRAISA_APPLY_MEM_RELA");
		modelMap.put(NormalExcelConstants.CLASS,TBAppraisalApplyMemRelaEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_B_APPRAISA_APPLY_MEM_RELA??????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAppraisaApplyMemRelas);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_B_APPRAISA_APPLY_MEM_RELA");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBAppraisalApplyMemRelaEntity.class);
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
				List<TBAppraisalApplyMemRelaEntity> listTBAppraisaApplyMemRelaEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBAppraisalApplyMemRelaEntity.class,params);
				for (TBAppraisalApplyMemRelaEntity tBAppraisaApplyMemRela : listTBAppraisaApplyMemRelaEntitys) {
					tBAppraisaApplyMemRelaService.save(tBAppraisaApplyMemRela);
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
