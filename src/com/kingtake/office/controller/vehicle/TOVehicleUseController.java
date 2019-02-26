package com.kingtake.office.controller.vehicle;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
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
import com.kingtake.office.entity.vehicle.TOVehicleUseEntity;
import com.kingtake.office.entity.vehicle.TOVehicleViolationEntity;
import com.kingtake.office.service.vehicle.TOVehicleUseServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;



/**
 * @Title: Controller
 * @Description: 车辆使用登记信息表
 * @author onlineGenerator
 * @date 2015-07-08 17:33:46
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOVehicleUseController")
public class TOVehicleUseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOVehicleUseController.class);

	@Autowired
	private TOVehicleUseServiceI tOVehicleUseService;
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
     * 车辆使用登记信息表列表 页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "tOVehicleUse")
	public ModelAndView tOVehicleUse(HttpServletRequest request) {
        String vehicleId = request.getParameter("vehicleId");
        request.setAttribute("vehicleId", vehicleId);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleUseList");
	}

    /**
     * 车辆使用登记信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOVehicleUseChoose")
    public ModelAndView tOVehicleUseChoose(HttpServletRequest request) {
        String vehicleId = request.getParameter("vehicleId");
        request.setAttribute("vehicleId", vehicleId);
        return new ModelAndView("com/kingtake/office/vehicle/tOVehicleUseListChoose");
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
	public void datagrid(TOVehicleUseEntity tOVehicleUse,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleUseEntity.class, dataGrid);
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleUse, request.getParameterMap());
		try{
            //自定义追加查询条件
            /*
             * String vehicleId = request.getParameter("vehicle"); if (StringUtil.isNotEmpty(vehicleId)) {
             * cq.eq("vehicleId", vehicleId); }
             */
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOVehicleUseService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * 删除使用记录之前查看使用费用、违章、事故中是否绑定了改天使用记录，并进行提示
     */
    @RequestMapping(params = "goDel")
    @ResponseBody
    public AjaxJson goDel(TOVehicleUseEntity tOVehicleUse, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        StringBuffer temp = new StringBuffer();
        List<TOVehicleExpenseEntity> tOVehicleExpenseEntitys = systemService.findByProperty(
                TOVehicleExpenseEntity.class, "useId", tOVehicleUse.getId());
        List<TOVehicleViolationEntity> tOVehicleViolationEntitys = systemService.findByProperty(
                TOVehicleViolationEntity.class, "useId", tOVehicleUse.getId());
        List<TOVehicleAccidentEntity> tOVehicleAccidentEntitys = systemService.findByProperty(
                TOVehicleAccidentEntity.class, "useId", tOVehicleUse.getId());
        boolean flag1 = tOVehicleExpenseEntitys == null || tOVehicleExpenseEntitys.size() == 0;
        boolean flag2 = tOVehicleViolationEntitys == null || tOVehicleViolationEntitys.size() == 0;
        boolean flag3 = tOVehicleAccidentEntitys == null || tOVehicleAccidentEntitys.size() == 0;
        if (flag1 && flag2 && flag3) {
            message = "确定删除该条记录吗？";
        }else{
            if (!flag1) {
                temp.append("车辆使用费用表、");
            }
            if (!flag2) {
                temp.append("车辆事故表、");
            }
            if (!flag3) {
                temp.append("车辆违章表、");
            }
            message = temp.substring(0, temp.length() - 1).toString() + "中有记录绑定了该条使用记录，删除后不能查看，确定删除该条使用记录吗？";
        }

        j.setMsg(message);
        return j;
    }

    /**
     * 删除车辆使用登记信息表
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOVehicleUseEntity tOVehicleUse, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOVehicleUse = systemService.getEntity(TOVehicleUseEntity.class, tOVehicleUse.getId());
        message = "车辆使用登记信息表删除成功";
		try{
			tOVehicleUseService.delete(tOVehicleUse);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆使用登记信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * 批量删除车辆使用登记信息表
     * 
     * @return
     */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "车辆使用登记信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TOVehicleUseEntity tOVehicleUse = systemService.getEntity(TOVehicleUseEntity.class, 
				id
				);
				tOVehicleUseService.delete(tOVehicleUse);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆使用登记信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    /**
     * 添加车辆使用登记信息表
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOVehicleUseEntity tOVehicleUse, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "车辆使用登记信息表添加成功";
		try{
			tOVehicleUseService.save(tOVehicleUse);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆使用登记信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * 更新车辆使用登记信息表
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOVehicleUseEntity tOVehicleUse, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "车辆使用登记信息表更新成功";
		TOVehicleUseEntity t = tOVehicleUseService.get(TOVehicleUseEntity.class, tOVehicleUse.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOVehicleUse, t);
			tOVehicleUseService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "车辆使用登记信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

    /**
     * 车辆使用登记信息表新增页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOVehicleUseEntity tOVehicleUse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleUse.getId())) {
			tOVehicleUse = tOVehicleUseService.getEntity(TOVehicleUseEntity.class, tOVehicleUse.getId());
        } else {
            Map<String, Object> tOVehiclePage = new HashMap<String, Object>();
            
            //存入车辆信息表id、车牌号以及司机
            String vehicleId = req.getParameter("vehicleId");
            tOVehicleUse.setVehicleId(vehicleId);
            TOVehicleEntity tOVehicleEntity = tOVehicleUseService.getEntity(TOVehicleEntity.class, vehicleId);
            if (tOVehicleEntity != null) {
                tOVehiclePage.put("licenseNo", tOVehicleEntity.getLicenseNo());
                tOVehiclePage.put("driverName", tOVehicleEntity.getDriverName());
                tOVehiclePage.put("driverId", tOVehicleEntity.getDriverId());
                req.setAttribute("tOVehiclePage", tOVehiclePage);
            }

		}
        req.setAttribute("tOVehicleUsePage", tOVehicleUse);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleUse-add");
	}
	
    /**
     * 车辆使用登记信息表编辑页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOVehicleUseEntity tOVehicleUse, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleUse.getId())) {
			tOVehicleUse = tOVehicleUseService.getEntity(TOVehicleUseEntity.class, tOVehicleUse.getId());
			req.setAttribute("tOVehicleUsePage", tOVehicleUse);

            //存入车牌号和项目名称信息
            Map<String, Object> vehicleAndProjectPage = new HashMap<String, Object>();
            if (StringUtil.isNotEmpty(tOVehicleUse.getVehicleId())) {
                TOVehicleEntity tOVehicleEntity = tOVehicleUseService.getEntity(TOVehicleEntity.class,
                        tOVehicleUse.getVehicleId());
                if (tOVehicleEntity != null) {
                    vehicleAndProjectPage.put("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
                }
            }
            if (StringUtil.isNotEmpty(tOVehicleUse.getProjectId())) {
                TPmProjectEntity tPmProjectEntity = tOVehicleUseService.getEntity(TPmProjectEntity.class,
                        tOVehicleUse.getProjectId());
                if (tPmProjectEntity != null) {
                    vehicleAndProjectPage.put("projectName", tPmProjectEntity.getProjectName());
                }
            }
            req.setAttribute("vehicleAndProjectPage", vehicleAndProjectPage);

		}
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleUse-update");
	}
	
    /**
     * 导入功能跳转
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleUseUpload");
	}
	
    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOVehicleUseEntity tOVehicleUse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleUseEntity.class, dataGrid);
		try {
			tOVehicleUse.setUseName(URLDecoder.decode(tOVehicleUse.getUseName(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleUse, request.getParameterMap());
		List<TOVehicleUseEntity> tOVehicleUses = this.tOVehicleUseService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "车辆使用登记信息表");
		modelMap.put(NormalExcelConstants.CLASS,TOVehicleUseEntity.class);
		
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("车辆使用登记信息表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOVehicleUses);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOVehicleUseEntity tOVehicleUse,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "车辆使用登记信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOVehicleUseEntity.class);
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
				List<TOVehicleUseEntity> listTOVehicleUseEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOVehicleUseEntity.class,params);
				for (TOVehicleUseEntity tOVehicleUse : listTOVehicleUseEntitys) {
					tOVehicleUseService.save(tOVehicleUse);
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
