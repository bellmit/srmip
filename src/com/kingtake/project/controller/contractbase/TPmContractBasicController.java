package com.kingtake.project.controller.contractbase;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import com.kingtake.project.service.contractbase.TPmContractBasicServiceI;
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
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2015-08-14 17:18:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractBasicController")
public class TPmContractBasicController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmContractBasicController.class);

	@Autowired
	private TPmContractBasicServiceI tPmContractBasicService;
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
	 * ???????????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmContractBasic")
	public ModelAndView tPmContractBasic(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/project/contractbase/tPmContractBasicList");
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
	public void datagrid(TPmContractBasicEntity tPmContractBasic,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPmContractBasicEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractBasic, request.getParameterMap());
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPmContractBasicService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmContractBasicEntity tPmContractBasic, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmContractBasic = systemService.getEntity(TPmContractBasicEntity.class, tPmContractBasic.getRid());
		message = "??????????????????????????????";
		try{
			tPmContractBasicService.delete(tPmContractBasic);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????";
		try{
			for(String id:ids.split(",")){
				TPmContractBasicEntity tPmContractBasic = systemService.getEntity(TPmContractBasicEntity.class, 
				id
				);
				tPmContractBasicService.delete(tPmContractBasic);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * ????????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmContractBasicEntity tPmContractBasic, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????";
		try{
			tPmContractBasicService.save(tPmContractBasic);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "??????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmContractBasicEntity tPmContractBasic, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????";
		TPmContractBasicEntity t = tPmContractBasicService.get(TPmContractBasicEntity.class, tPmContractBasic.getRid());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPmContractBasic, t);
			tPmContractBasicService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "??????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * ????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPmContractBasicEntity tPmContractBasic, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmContractBasic.getRid())) {
			tPmContractBasic = tPmContractBasicService.getEntity(TPmContractBasicEntity.class, tPmContractBasic.getRid());
			req.setAttribute("tPmContractBasicPage", tPmContractBasic);
		}
		return new ModelAndView("com/kingtake/project/contractbase/tPmContractBasic-add");
	}
	/**
	 * ????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmContractBasicEntity tPmContractBasic, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmContractBasic.getRid())) {
			tPmContractBasic = tPmContractBasicService.getEntity(TPmContractBasicEntity.class, tPmContractBasic.getRid());
			req.setAttribute("tPmContractBasicPage", tPmContractBasic);
		}
		return new ModelAndView("com/kingtake/project/contractbase/tPmContractBasic-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/contractbase/tPmContractBasicUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmContractBasicEntity tPmContractBasic,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmContractBasicEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractBasic, request.getParameterMap());
		List<TPmContractBasicEntity> tPmContractBasics = this.tPmContractBasicService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"??????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TPmContractBasicEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmContractBasics);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmContractBasicEntity tPmContractBasic,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmContractBasicEntity.class);
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
				List<TPmContractBasicEntity> listTPmContractBasicEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmContractBasicEntity.class,params);
				for (TPmContractBasicEntity tPmContractBasic : listTPmContractBasicEntitys) {
					tPmContractBasicService.save(tPmContractBasic);
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
