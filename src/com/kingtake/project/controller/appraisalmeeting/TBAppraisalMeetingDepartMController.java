package com.kingtake.project.controller.appraisalmeeting;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartMEntity;
import com.kingtake.project.service.appraisalmeeting.TBAppraisalMeetingDepartMServiceI;
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
 * @Description: ??????????????????????????????
 * @author onlineGenerator
 * @date 2016-01-22 14:27:45
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalMeetingDepartMController")
public class TBAppraisalMeetingDepartMController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAppraisalMeetingDepartMController.class);

	@Autowired
	private TBAppraisalMeetingDepartMServiceI tBAppraisalMeetingDepartMService;
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
	@RequestMapping(params = "tBAppraisalMeetingDepartM")
	public ModelAndView tBAppraisalMeetingDepartM(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepartMList");
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
	public void datagrid(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingDepartMEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMeetingDepartM, request.getParameterMap());
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBAppraisalMeetingDepartMService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAppraisalMeetingDepartM = systemService.getEntity(TBAppraisalMeetingDepartMEntity.class, tBAppraisalMeetingDepartM.getId());
		message = "??????????????????????????????????????????";
		try{
			tBAppraisalMeetingDepartMService.delete(tBAppraisalMeetingDepartM);
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
				TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM = systemService.getEntity(TBAppraisalMeetingDepartMEntity.class, 
				id
				);
				tBAppraisalMeetingDepartMService.delete(tBAppraisalMeetingDepartM);
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
	public AjaxJson doAdd(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????????????????";
		try{
			tBAppraisalMeetingDepartMService.save(tBAppraisalMeetingDepartM);
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
	public AjaxJson doUpdate(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "??????????????????????????????????????????";
		TBAppraisalMeetingDepartMEntity t = tBAppraisalMeetingDepartMService.get(TBAppraisalMeetingDepartMEntity.class, tBAppraisalMeetingDepartM.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalMeetingDepartM, t);
			tBAppraisalMeetingDepartMService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
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
	public ModelAndView goAdd(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisalMeetingDepartM.getId())) {
			tBAppraisalMeetingDepartM = tBAppraisalMeetingDepartMService.getEntity(TBAppraisalMeetingDepartMEntity.class, tBAppraisalMeetingDepartM.getId());
			req.setAttribute("tBAppraisalMeetingDepartMPage", tBAppraisalMeetingDepartM);
		}
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepartM-add");
	}
	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAppraisalMeetingDepartM.getId())) {
			tBAppraisalMeetingDepartM = tBAppraisalMeetingDepartMService.getEntity(TBAppraisalMeetingDepartMEntity.class, tBAppraisalMeetingDepartM.getId());
			req.setAttribute("tBAppraisalMeetingDepartMPage", tBAppraisalMeetingDepartM);
		}
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepartM-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingDepartMUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingDepartMEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMeetingDepartM, request.getParameterMap());
		List<TBAppraisalMeetingDepartMEntity> tBAppraisalMeetingDepartMs = this.tBAppraisalMeetingDepartMService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"??????????????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TBAppraisalMeetingDepartMEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("????????????????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAppraisalMeetingDepartMs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBAppraisalMeetingDepartMEntity.class);
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
				List<TBAppraisalMeetingDepartMEntity> listTBAppraisalMeetingDepartMEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBAppraisalMeetingDepartMEntity.class,params);
				for (TBAppraisalMeetingDepartMEntity tBAppraisalMeetingDepartM : listTBAppraisalMeetingDepartMEntitys) {
					tBAppraisalMeetingDepartMService.save(tBAppraisalMeetingDepartM);
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
