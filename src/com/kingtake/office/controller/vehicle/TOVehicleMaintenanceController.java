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
import com.kingtake.office.entity.vehicle.TOVehicleMaintenanceEntity;
import com.kingtake.office.service.vehicle.TOVehicleMaintenanceServiceI;



/**
 * @Title: Controller
 * @Description: ???????????????????????????
 * @author onlineGenerator
 * @date 2015-07-09 17:40:49
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOVehicleMaintenanceController")
public class TOVehicleMaintenanceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOVehicleMaintenanceController.class);

	@Autowired
	private TOVehicleMaintenanceServiceI tOVehicleMaintenanceService;
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
     * ????????????????????????????????? ????????????
     * 
     * @return
     */
	@RequestMapping(params = "tOVehicleMaintenance")
	public ModelAndView tOVehicleMaintenance(HttpServletRequest request) {
        String vehicleId = request.getParameter("vehicleId");
        request.setAttribute("vehicleId", vehicleId);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleMaintenanceList");
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
	public void datagrid(TOVehicleMaintenanceEntity tOVehicleMaintenance,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleMaintenanceEntity.class, dataGrid);
        //?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleMaintenance, request.getParameterMap());
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
		this.tOVehicleMaintenanceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOVehicleMaintenanceEntity tOVehicleMaintenance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOVehicleMaintenance = systemService.getEntity(TOVehicleMaintenanceEntity.class, tOVehicleMaintenance.getId());
        message = "???????????????????????????????????????";
		try{
			tOVehicleMaintenanceService.delete(tOVehicleMaintenance);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "???????????????????????????????????????";
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
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "???????????????????????????????????????";
		try{
			for(String id:ids.split(",")){
				TOVehicleMaintenanceEntity tOVehicleMaintenance = systemService.getEntity(TOVehicleMaintenanceEntity.class, 
				id
				);
				tOVehicleMaintenanceService.delete(tOVehicleMaintenance);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "???????????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    /**
     * ?????????????????????????????????
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOVehicleMaintenanceEntity tOVehicleMaintenance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "???????????????????????????????????????";
		try{
			tOVehicleMaintenanceService.save(tOVehicleMaintenance);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "???????????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(tOVehicleMaintenance.getId());
		return j;
	}
	
    /**
     * ?????????????????????????????????
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOVehicleMaintenanceEntity tOVehicleMaintenance, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "???????????????????????????????????????";
		TOVehicleMaintenanceEntity t = tOVehicleMaintenanceService.get(TOVehicleMaintenanceEntity.class, tOVehicleMaintenance.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOVehicleMaintenance, t);
			tOVehicleMaintenanceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "???????????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(tOVehicleMaintenance.getId());
		return j;
	}
	

    /**
     * ?????????????????????????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOVehicleMaintenanceEntity tOVehicleMaintenance, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleMaintenance.getId())) {
			tOVehicleMaintenance = tOVehicleMaintenanceService.getEntity(TOVehicleMaintenanceEntity.class, tOVehicleMaintenance.getId());
        } else {
            String vehicleId = req.getParameter("vehicleId");
            tOVehicleMaintenance.setVehicleId(vehicleId);
            TOVehicleEntity tOVehicleEntity = tOVehicleMaintenanceService.getEntity(TOVehicleEntity.class, vehicleId);
            if (tOVehicleEntity != null) {
            	req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
            	tOVehicleMaintenance.setMaintainUserId(tOVehicleEntity.getDriverId());
            	tOVehicleMaintenance.setMaintainUserName(tOVehicleEntity.getDriverName());
            }
		}
        req.setAttribute("tOVehicleMaintenancePage", tOVehicleMaintenance);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleMaintenance-add");
	}
	
    /**
     * ?????????????????????????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOVehicleMaintenanceEntity tOVehicleMaintenance, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleMaintenance.getId())) {
			tOVehicleMaintenance = tOVehicleMaintenanceService.getEntity(TOVehicleMaintenanceEntity.class, tOVehicleMaintenance.getId());
			req.setAttribute("tOVehicleMaintenancePage", tOVehicleMaintenance);
            if (StringUtil.isNotEmpty(tOVehicleMaintenance.getVehicleId())) {
                TOVehicleEntity tOVehicleEntity = tOVehicleMaintenanceService.getEntity(TOVehicleEntity.class,
                        tOVehicleMaintenance.getVehicleId());
                if (tOVehicleEntity != null) {
                    req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
                }
            }
		}
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleMaintenance-update");
	}
	
    /**
     * ??????????????????
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleMaintenanceUpload");
	}
	
    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOVehicleMaintenanceEntity tOVehicleMaintenance,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleMaintenanceEntity.class, dataGrid);
		try {
			tOVehicleMaintenance.setMaintainUserName(URLDecoder.decode(tOVehicleMaintenance.getMaintainUserName(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleMaintenance, request.getParameterMap());
		List<TOVehicleMaintenanceEntity> tOVehicleMaintenances = this.tOVehicleMaintenanceService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "???????????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TOVehicleMaintenanceEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOVehicleMaintenances);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOVehicleMaintenanceEntity tOVehicleMaintenance,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "???????????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOVehicleMaintenanceEntity.class);
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
				List<TOVehicleMaintenanceEntity> listTOVehicleMaintenanceEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOVehicleMaintenanceEntity.class,params);
				for (TOVehicleMaintenanceEntity tOVehicleMaintenance : listTOVehicleMaintenanceEntitys) {
					tOVehicleMaintenanceService.save(tOVehicleMaintenance);
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
