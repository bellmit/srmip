package com.kingtake.office.controller.vehicle;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.jeecgframework.core.util.DateUtils;
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

import com.kingtake.office.entity.vehicle.TOVehicleEntity;
import com.kingtake.office.entity.vehicle.TOVehicleUseEntity;
import com.kingtake.office.entity.vehicle.TOVehicleViolationEntity;
import com.kingtake.office.service.vehicle.TOVehicleViolationServiceI;



/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2015-07-10 15:29:34
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOVehicleViolationController")
public class TOVehicleViolationController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOVehicleViolationController.class);

	@Autowired
	private TOVehicleViolationServiceI tOVehicleViolationService;
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
     * ??????????????????????????? ????????????
     * 
     * @return
     */
	@RequestMapping(params = "tOVehicleViolation")
	public ModelAndView tOVehicleViolation(HttpServletRequest request) {
        String vehicleId = request.getParameter("vehicleId");
        request.setAttribute("vehicleId", vehicleId);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleViolationList");
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
	public void datagrid(TOVehicleViolationEntity tOVehicleViolation,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleViolationEntity.class, dataGrid);
        //?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleViolation, request.getParameterMap());
		try{
            //???????????????????????????
            /*
             * String vehicleId = request.getParameter("vehicleId"); if (StringUtil.isNotEmpty(vehicleId)) {
             * cq.eq("vehicleId", vehicleId); }
             */
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOVehicleViolationService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * ???????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOVehicleViolationEntity tOVehicleViolation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOVehicleViolation = systemService.getEntity(TOVehicleViolationEntity.class, tOVehicleViolation.getId());
        message = "?????????????????????????????????";
		try{
			tOVehicleViolationService.delete(tOVehicleViolation);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "?????????????????????????????????";
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
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
		try{
			for(String id:ids.split(",")){
				TOVehicleViolationEntity tOVehicleViolation = systemService.getEntity(TOVehicleViolationEntity.class, 
				id
				);
				tOVehicleViolationService.delete(tOVehicleViolation);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    /**
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOVehicleViolationEntity tOVehicleViolation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
		try{
			tOVehicleViolationService.save(tOVehicleViolation);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOVehicleViolationEntity tOVehicleViolation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
		TOVehicleViolationEntity t = tOVehicleViolationService.get(TOVehicleViolationEntity.class, tOVehicleViolation.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOVehicleViolation, t);
			tOVehicleViolationService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOVehicleViolationEntity tOVehicleViolation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleViolation.getId())) {
			tOVehicleViolation = tOVehicleViolationService.getEntity(TOVehicleViolationEntity.class, tOVehicleViolation.getId());
        } else {
            //?????????????????????id????????????
            String vehicleId = req.getParameter("vehicleId");
            tOVehicleViolation.setVehicleId(vehicleId);
            TOVehicleEntity tOVehicleEntity = tOVehicleViolationService.getEntity(TOVehicleEntity.class, vehicleId);
            if (tOVehicleEntity != null) {
                req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
            }
		}
        req.setAttribute("tOVehicleViolationPage", tOVehicleViolation);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleViolation-add");
	}
	
    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOVehicleViolationEntity tOVehicleViolation, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleViolation.getId())) {
			tOVehicleViolation = tOVehicleViolationService.getEntity(TOVehicleViolationEntity.class, tOVehicleViolation.getId());
			req.setAttribute("tOVehicleViolationPage", tOVehicleViolation);

            //????????????????????????????????????
            if (StringUtil.isNotEmpty(tOVehicleViolation.getVehicleId())) {
                TOVehicleEntity tOVehicleEntity = tOVehicleViolationService.getEntity(TOVehicleEntity.class,
                        tOVehicleViolation.getVehicleId());
                if (tOVehicleEntity != null) {
                    req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
                }
            }
            if (StringUtil.isNotEmpty(tOVehicleViolation.getUseId())) {
                TOVehicleUseEntity tOVehicleUseEntity = tOVehicleViolationService.getEntity(TOVehicleUseEntity.class,
                        tOVehicleViolation.getUseId());
                if (tOVehicleUseEntity != null) {
                    if (tOVehicleUseEntity.getOutTime() != null) {
                        String outTime = DateUtils.date2Str(tOVehicleUseEntity.getOutTime(), DateUtils.date_sdf);
                        req.setAttribute("useInfo", tOVehicleUseEntity.getUseName() + outTime + "??????");
                    } else {
                        req.setAttribute("useInfo", tOVehicleUseEntity.getUseName() + "??????");
                    }
                }
            }
		}
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleViolation-update");
	}
	
    /**
     * ??????????????????
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleViolationUpload");
	}
	
    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOVehicleViolationEntity tOVehicleViolation,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleViolationEntity.class, dataGrid);
		try {
			tOVehicleViolation.setViolationName(URLDecoder.decode(tOVehicleViolation.getViolationName(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleViolation, request.getParameterMap());
		List<TOVehicleViolationEntity> tOVehicleViolations = this.tOVehicleViolationService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TOVehicleViolationEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOVehicleViolations);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOVehicleViolationEntity tOVehicleViolation,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOVehicleViolationEntity.class);
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
				List<TOVehicleViolationEntity> listTOVehicleViolationEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOVehicleViolationEntity.class,params);
				for (TOVehicleViolationEntity tOVehicleViolation : listTOVehicleViolationEntitys) {
					tOVehicleViolationService.save(tOVehicleViolation);
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
