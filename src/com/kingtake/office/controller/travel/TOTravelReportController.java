package com.kingtake.office.controller.travel;
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

import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.office.entity.travel.TOTravelReportEntity;
import com.kingtake.office.service.travel.TOTravelReportServiceI;



/**   
 * @Title: Controller
 * @Description: ??????-?????????????????????
 * @author onlineGenerator
 * @date 2015-07-07 11:24:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOTravelReportController")
public class TOTravelReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOTravelReportController.class);

	@Autowired
	private TOTravelReportServiceI tOTravelReportService;
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
	 * ??????-??????????????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tOTravelReport")
	public ModelAndView tOTravelReport(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/office/travel/tOTravelReportList");
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
	public void datagrid(TOTravelReportEntity tOTravelReport,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOTravelReportEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOTravelReport, request.getParameterMap());
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOTravelReportService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ????????????-?????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOTravelReportEntity tOTravelReport, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOTravelReport = systemService.getEntity(TOTravelReportEntity.class, tOTravelReport.getId());
		message = "??????-?????????????????????????????????";
		try{
			tOTravelReportService.delete(tOTravelReport);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "??????-?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????????????????-?????????????????????
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "??????-?????????????????????????????????";
		try{
			for(String id:ids.split(",")){
				TOTravelReportEntity tOTravelReport = systemService.getEntity(TOTravelReportEntity.class, 
				id
				);
				tOTravelReportService.delete(tOTravelReport);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "??????-?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	 /**
	     * ????????????-?????????????????????
	     * 
	     * @param ids
	     * @return
	     */
	 @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TOTravelReportEntity tOTravelReport, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if(StringUtil.isNotEmpty(tOTravelReport.getId())){
            TOTravelReportEntity t = tOTravelReportService.get(TOTravelReportEntity.class, tOTravelReport.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tOTravelReport, t);
                message = "??????-?????????????????????????????????";
                tOTravelReportService.updateEntitie(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "??????-?????????????????????????????????";
                throw new BusinessException(e.getMessage());
            }
        }else{
            try{
                tOTravelReportService.save(tOTravelReport);
                message = "??????-?????????????????????????????????";
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }catch(Exception e){
                e.printStackTrace();
                message = "??????-?????????????????????????????????";
                throw new BusinessException(e.getMessage());
            }
            
        }
        j.setObj(tOTravelReport);
        j.setMsg(message);
        return j;
    }
	/**
	 * ??????-???????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOTravelReportEntity tOTravelReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOTravelReport.getId())) {
			tOTravelReport = tOTravelReportService.getEntity(TOTravelReportEntity.class, tOTravelReport.getId());
			req.setAttribute("tOTravelReportPage", tOTravelReport);
		}
		return new ModelAndView("com/kingtake/office/travel/tOTravelReport");
	}
	/**
	 * ??????-???????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOTravelReportEntity tOTravelReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOTravelReport.getId())||StringUtil.isNotEmpty(tOTravelReport.getToId())) {
			tOTravelReport = tOTravelReportService.getEntity(TOTravelReportEntity.class, tOTravelReport.getId());
			req.setAttribute("tOTravelReportPage", tOTravelReport);
		}
		return new ModelAndView("com/kingtake/office/travel/tOTravelReport");
	}
	
	/**
     * ??????-???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TOTravelLeaveEntity toLeave, HttpServletRequest req) {
	  //????????????
        String toId = toLeave.getId();
        //===================================================================================
        //??????-??????-?????????????????????
        if(StringUtil.isNotEmpty(toId)){
            try {
                TOTravelLeaveEntity toLeaveEntity = systemService.getEntity(TOTravelLeaveEntity.class, toId);
                List<TOTravelReportEntity> toTravelReportEntity = systemService.findByProperty(TOTravelReportEntity.class, "toId", toId);
                if(toTravelReportEntity.size()>0){
                    req.setAttribute("tOTravelReportPage", toTravelReportEntity.get(0));
                }
                req.setAttribute("toId", toId);
                req.setAttribute("leaveName", toLeaveEntity.getLeaveName());
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        return new ModelAndView("com/kingtake/office/travel/tOTravelReport");
	}
    
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/travel/tOTravelReportUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOTravelReportEntity tOTravelReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOTravelReportEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOTravelReport, request.getParameterMap());
		List<TOTravelReportEntity> tOTravelReports = this.tOTravelReportService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"??????-?????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TOTravelReportEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("??????-???????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOTravelReports);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOTravelReportEntity tOTravelReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "??????-?????????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOTravelReportEntity.class);
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
				List<TOTravelReportEntity> listTOTravelReportEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOTravelReportEntity.class,params);
				for (TOTravelReportEntity tOTravelReport : listTOTravelReportEntitys) {
					tOTravelReportService.save(tOTravelReport);
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
