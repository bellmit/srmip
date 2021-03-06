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

import com.kingtake.office.entity.vehicle.TOVehicleAccidentEntity;
import com.kingtake.office.entity.vehicle.TOVehicleEntity;
import com.kingtake.office.entity.vehicle.TOVehicleUseEntity;
import com.kingtake.office.service.vehicle.TOVehicleAccidentServiceI;



/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2015-07-10 17:12:49
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOVehicleAccidentController")
public class TOVehicleAccidentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOVehicleAccidentController.class);

	@Autowired
	private TOVehicleAccidentServiceI tOVehicleAccidentService;
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
	@RequestMapping(params = "tOVehicleAccident")
	public ModelAndView tOVehicleAccident(HttpServletRequest request) {
        String vehicleId = request.getParameter("vehicleId");
        request.setAttribute("vehicleId", vehicleId);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleAccidentList");
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
	public void datagrid(TOVehicleAccidentEntity tOVehicleAccident,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleAccidentEntity.class, dataGrid);
        //?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleAccident, request.getParameterMap());
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
		this.tOVehicleAccidentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * ???????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOVehicleAccidentEntity tOVehicleAccident, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOVehicleAccident = systemService.getEntity(TOVehicleAccidentEntity.class, tOVehicleAccident.getId());
        message = "?????????????????????????????????";
		try{
			tOVehicleAccidentService.delete(tOVehicleAccident);
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
				TOVehicleAccidentEntity tOVehicleAccident = systemService.getEntity(TOVehicleAccidentEntity.class, 
				id
				);
				tOVehicleAccidentService.delete(tOVehicleAccident);
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
	public AjaxJson doAdd(TOVehicleAccidentEntity tOVehicleAccident, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
		try{
			tOVehicleAccidentService.save(tOVehicleAccident);
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
	public AjaxJson doUpdate(TOVehicleAccidentEntity tOVehicleAccident, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
		TOVehicleAccidentEntity t = tOVehicleAccidentService.get(TOVehicleAccidentEntity.class, tOVehicleAccident.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOVehicleAccident, t);
			tOVehicleAccidentService.saveOrUpdate(t);
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
	public ModelAndView goAdd(TOVehicleAccidentEntity tOVehicleAccident, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleAccident.getId())) {
			tOVehicleAccident = tOVehicleAccidentService.getEntity(TOVehicleAccidentEntity.class, tOVehicleAccident.getId());
		}else{
            //?????????????????????id????????????
            String vehicleId = req.getParameter("vehicleId");
            tOVehicleAccident.setVehicleId(vehicleId);
            TOVehicleEntity tOVehicleEntity = tOVehicleAccidentService.getEntity(TOVehicleEntity.class, vehicleId);
            if (tOVehicleEntity != null) {
                req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
            }
		}
		req.setAttribute("tOVehicleAccidentPage", tOVehicleAccident);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleAccident-add");
	}
	
    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOVehicleAccidentEntity tOVehicleAccident, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleAccident.getId())) {
			tOVehicleAccident = tOVehicleAccidentService.getEntity(TOVehicleAccidentEntity.class, tOVehicleAccident.getId());
			req.setAttribute("tOVehicleAccidentPage", tOVehicleAccident);

            //????????????????????????????????????
            if (StringUtil.isNotEmpty(tOVehicleAccident.getVehicleId())) {
                TOVehicleEntity tOVehicleEntity = tOVehicleAccidentService.getEntity(TOVehicleEntity.class,
                        tOVehicleAccident.getVehicleId());
                if (tOVehicleEntity != null) {
                    req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
                }
            }
            if (StringUtil.isNotEmpty(tOVehicleAccident.getUseId())) {
                TOVehicleUseEntity tOVehicleUseEntity = tOVehicleAccidentService.getEntity(TOVehicleUseEntity.class,
                        tOVehicleAccident.getUseId());
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
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleAccident-update");
	}
	
    /**
     * ??????????????????
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleAccidentUpload");
	}
	
    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOVehicleAccidentEntity tOVehicleAccident,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleAccidentEntity.class, dataGrid);
		try {
			tOVehicleAccident.setPersonName(URLDecoder.decode(tOVehicleAccident.getPersonName(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleAccident, request.getParameterMap());
		List<TOVehicleAccidentEntity> tOVehicleAccidents = this.tOVehicleAccidentService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TOVehicleAccidentEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOVehicleAccidents);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOVehicleAccidentEntity tOVehicleAccident,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOVehicleAccidentEntity.class);
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
				List<TOVehicleAccidentEntity> listTOVehicleAccidentEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOVehicleAccidentEntity.class,params);
				for (TOVehicleAccidentEntity tOVehicleAccident : listTOVehicleAccidentEntitys) {
					tOVehicleAccidentService.save(tOVehicleAccident);
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
