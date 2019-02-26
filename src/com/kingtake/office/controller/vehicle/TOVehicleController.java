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

import com.kingtake.office.entity.vehicle.TOVehicleAccidentEntity;
import com.kingtake.office.entity.vehicle.TOVehicleEntity;
import com.kingtake.office.entity.vehicle.TOVehicleExpenseEntity;
import com.kingtake.office.entity.vehicle.TOVehicleMaintenanceEntity;
import com.kingtake.office.entity.vehicle.TOVehicleUseEntity;
import com.kingtake.office.entity.vehicle.TOVehicleViolationEntity;
import com.kingtake.office.service.vehicle.TOVehicleServiceI;



/**
 * @Title: Controller
 * @Description: 车辆基本信息
 * @author onlineGenerator
 * @date 2015-07-08 10:00:34
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOVehicleController")
public class TOVehicleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOVehicleController.class);

	@Autowired
	private TOVehicleServiceI tOVehicleService;
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
     * 车辆基本信息列表 页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "tOVehicle")
	public ModelAndView tOVehicle(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleList");
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
	public void datagrid(TOVehicleEntity tOVehicle,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleEntity.class, dataGrid);
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicle, request.getParameterMap());
		try{
            //自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOVehicleService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	                                                                                                                                                                                                /**
     * 删除车辆基本信息
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOVehicleEntity tOVehicle, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOVehicle = systemService.getEntity(TOVehicleEntity.class, tOVehicle.getId());
        List<TOVehicleUseEntity> tOVehicleUseEntitys = systemService.findByProperty(TOVehicleUseEntity.class,
                "vehicleId", tOVehicle.getId());
        List<TOVehicleExpenseEntity> tOVehicleExpenseEntitys = systemService.findByProperty(
                TOVehicleExpenseEntity.class, "vehicleId", tOVehicle.getId());
        List<TOVehicleMaintenanceEntity> tOVehicleMaintenanceEntitys = systemService.findByProperty(
                TOVehicleMaintenanceEntity.class, "vehicleId", tOVehicle.getId());
        List<TOVehicleViolationEntity> tOVehicleViolationEntitys = systemService.findByProperty(
                TOVehicleViolationEntity.class, "vehicleId", tOVehicle.getId());
        List<TOVehicleAccidentEntity> tOVehicleAccidentEntitys = systemService.findByProperty(
                TOVehicleAccidentEntity.class, "vehicleId", tOVehicle.getId());
        message = "车辆基本信息删除成功";
		try{
            if (tOVehicleUseEntitys != null && tOVehicleUseEntitys.size() > 0) {
                tOVehicleService.deleteAllEntitie(tOVehicleUseEntitys);
            }
            if (tOVehicleExpenseEntitys != null && tOVehicleExpenseEntitys.size() > 0) {
                tOVehicleService.deleteAllEntitie(tOVehicleExpenseEntitys);
            }
            if (tOVehicleMaintenanceEntitys != null && tOVehicleMaintenanceEntitys.size() > 0) {
                tOVehicleService.deleteAllEntitie(tOVehicleMaintenanceEntitys);
            }
            if (tOVehicleViolationEntitys != null && tOVehicleViolationEntitys.size() > 0) {
                tOVehicleService.deleteAllEntitie(tOVehicleViolationEntitys);
            }
            if (tOVehicleAccidentEntitys != null && tOVehicleAccidentEntitys.size() > 0) {
                tOVehicleService.deleteAllEntitie(tOVehicleAccidentEntitys);
            }
            tOVehicleService.delete(tOVehicle);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆基本信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	                                                                                                                                                                                                /**
     * 批量删除车辆基本信息
     * 
     * @return
     */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "车辆基本信息删除成功";
		try{
			for(String id:ids.split(",")){
				TOVehicleEntity tOVehicle = systemService.getEntity(TOVehicleEntity.class, 
				id
				);
				tOVehicleService.delete(tOVehicle);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆基本信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	                                                                                                                                                                                                /**
     * 添加车辆基本信息
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOVehicleEntity tOVehicle, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "车辆基本信息添加成功";
		try{
			tOVehicleService.save(tOVehicle);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆基本信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	                                                                                                                                                                                                /**
     * 更新车辆基本信息
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOVehicleEntity tOVehicle, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "车辆基本信息更新成功";
		TOVehicleEntity t = tOVehicleService.get(TOVehicleEntity.class, tOVehicle.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOVehicle, t);
			tOVehicleService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "车辆基本信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	                                                                                                                                                                                                /**
     * 车辆基本信息新增页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOVehicleEntity tOVehicle, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicle.getId())) {
			tOVehicle = tOVehicleService.getEntity(TOVehicleEntity.class, tOVehicle.getId());
			req.setAttribute("tOVehiclePage", tOVehicle);
		}
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicle-add");
	}
	
    /**
     * 车辆基本信息编辑页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOVehicleEntity tOVehicle, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicle.getId())) {
			tOVehicle = tOVehicleService.getEntity(TOVehicleEntity.class, tOVehicle.getId());
			req.setAttribute("tOVehiclePage", tOVehicle);
		}
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicle-update");
	}
	
	                                                                                                                                                                                                /**
     * 导入功能跳转
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleUpload");
	}
	
	                                                                                                                                                                                                /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOVehicleEntity tOVehicle,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleEntity.class, dataGrid);
		try {
			tOVehicle.setLicenseNo(URLDecoder.decode(tOVehicle.getLicenseNo(),"utf-8"));
			tOVehicle.setSectionName(URLDecoder.decode(tOVehicle.getSectionName(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicle, request.getParameterMap());
		List<TOVehicleEntity> tOVehicles = this.tOVehicleService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "车辆基本信息");
        modelMap.put(NormalExcelConstants.CLASS, TOVehicleEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("车辆基本信息列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tOVehicles);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOVehicleEntity tOVehicle,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "车辆基本信息");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOVehicleEntity.class);
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
				List<TOVehicleEntity> listTOVehicleEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOVehicleEntity.class,params);
				for (TOVehicleEntity tOVehicle : listTOVehicleEntitys) {
					tOVehicleService.save(tOVehicle);
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
