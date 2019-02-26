package com.kingtake.office.controller.vehicle;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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
import com.kingtake.office.entity.vehicle.TOVehicleExpenseEntity;
import com.kingtake.office.entity.vehicle.TOVehicleUseEntity;
import com.kingtake.office.service.vehicle.TOVehicleExpenseServiceI;



/**
 * @Title: Controller
 * @Description: 车辆使用费用信息表
 * @author onlineGenerator
 * @date 2015-07-09 20:14:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOVehicleExpenseController")
public class TOVehicleExpenseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOVehicleExpenseController.class);

	@Autowired
	private TOVehicleExpenseServiceI tOVehicleExpenseService;
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
     * 车辆使用费用信息表列表 页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "tOVehicleExpense")
	public ModelAndView tOVehicleExpense(HttpServletRequest request) {
        String vehicleId = request.getParameter("vehicleId");
        request.setAttribute("vehicleId", vehicleId);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleExpenseList");
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
	public void datagrid(TOVehicleExpenseEntity tOVehicleExpense,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleExpenseEntity.class, dataGrid);
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleExpense, request.getParameterMap());
		try{
            //自定义追加查询条件
            /* String vehicleId = request.getParameter("vehicleId"); */
            String query_payTime_begin = request.getParameter("payTime_begin");
            String query_payTime_end = request.getParameter("payTime_end");

            /*
             * if (StringUtil.isNotEmpty(vehicleId)) { cq.eq("vehicleId", vehicleId); }
             */
            if (StringUtil.isNotEmpty(query_payTime_begin)) {
                cq.ge("payTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_payTime_begin));
            }
            if (StringUtil.isNotEmpty(query_payTime_end)) {
                cq.le("payTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_payTime_end));
            }
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOVehicleExpenseService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * 删除车辆使用费用信息表
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOVehicleExpenseEntity tOVehicleExpense, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOVehicleExpense = systemService.getEntity(TOVehicleExpenseEntity.class, tOVehicleExpense.getId());
        message = "车辆使用费用信息表删除成功";
		try{
			tOVehicleExpenseService.delete(tOVehicleExpense);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆使用费用信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * 批量删除车辆使用费用信息表
     * 
     * @return
     */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "车辆使用费用信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TOVehicleExpenseEntity tOVehicleExpense = systemService.getEntity(TOVehicleExpenseEntity.class, 
				id
				);
				tOVehicleExpenseService.delete(tOVehicleExpense);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆使用费用信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    /**
     * 添加车辆使用费用信息表
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TOVehicleExpenseEntity tOVehicleExpense, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "车辆使用费用信息表添加成功";
		try{
			tOVehicleExpenseService.save(tOVehicleExpense);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "车辆使用费用信息表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * 更新车辆使用费用信息表
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TOVehicleExpenseEntity tOVehicleExpense, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "车辆使用费用信息表更新成功";
		TOVehicleExpenseEntity t = tOVehicleExpenseService.get(TOVehicleExpenseEntity.class, tOVehicleExpense.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tOVehicleExpense, t);
			tOVehicleExpenseService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "车辆使用费用信息表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

    /**
     * 车辆使用费用信息表新增页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOVehicleExpenseEntity tOVehicleExpense, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleExpense.getId())) {
			tOVehicleExpense = tOVehicleExpenseService.getEntity(TOVehicleExpenseEntity.class, tOVehicleExpense.getId());
        } else {
            //存入车辆信息表id、车牌号
            String vehicleId = req.getParameter("vehicleId");
            tOVehicleExpense.setVehicleId(vehicleId);
            TOVehicleEntity tOVehicleEntity = tOVehicleExpenseService.getEntity(TOVehicleEntity.class, vehicleId);
            if (tOVehicleEntity != null) {
            	req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
            }
		}
        req.setAttribute("tOVehicleExpensePage", tOVehicleExpense);
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleExpense-add");
	}
	
    /**
     * 车辆使用费用信息表编辑页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOVehicleExpenseEntity tOVehicleExpense, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOVehicleExpense.getId())) {
			tOVehicleExpense = tOVehicleExpenseService.getEntity(TOVehicleExpenseEntity.class, tOVehicleExpense.getId());
			req.setAttribute("tOVehicleExpensePage", tOVehicleExpense);

            //存入车牌号和使用记录信息
            if (StringUtil.isNotEmpty(tOVehicleExpense.getVehicleId())) {
                TOVehicleEntity tOVehicleEntity = tOVehicleExpenseService.getEntity(TOVehicleEntity.class,
                        tOVehicleExpense.getVehicleId());
                if (tOVehicleEntity != null) {
                    req.setAttribute("vehicleLicenseNo", tOVehicleEntity.getLicenseNo());
                }
            }
            if (StringUtil.isNotEmpty(tOVehicleExpense.getUseId())) {
                TOVehicleUseEntity tOVehicleUseEntity = tOVehicleExpenseService.getEntity(TOVehicleUseEntity.class,
                        tOVehicleExpense.getUseId());
                if (tOVehicleUseEntity != null) {
                    if (tOVehicleUseEntity.getOutTime() != null) {
                        String outTime = DateUtils.date2Str(tOVehicleUseEntity.getOutTime(), DateUtils.date_sdf);
                        req.setAttribute("useInfo", tOVehicleUseEntity.getUseName() + outTime + "用车");
                    } else {
                        req.setAttribute("useInfo", tOVehicleUseEntity.getUseName() + "用车");
                    }
                }
            }
		}
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleExpense-update");
	}
	
    /**
     * 导入功能跳转
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/office/vehicle/tOVehicleExpenseUpload");
	}
	
    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TOVehicleExpenseEntity tOVehicleExpense,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TOVehicleExpenseEntity.class, dataGrid);
		try {
			tOVehicleExpense.setPayerName(URLDecoder.decode(tOVehicleExpense.getPayerName(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOVehicleExpense, request.getParameterMap());
		List<TOVehicleExpenseEntity> tOVehicleExpenses = this.tOVehicleExpenseService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "车辆使用费用信息表");
		modelMap.put(NormalExcelConstants.CLASS,TOVehicleExpenseEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("车辆使用费用信息表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tOVehicleExpenses);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TOVehicleExpenseEntity tOVehicleExpense,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "车辆使用费用信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TOVehicleExpenseEntity.class);
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
				List<TOVehicleExpenseEntity> listTOVehicleExpenseEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TOVehicleExpenseEntity.class,params);
				for (TOVehicleExpenseEntity tOVehicleExpense : listTOVehicleExpenseEntitys) {
					tOVehicleExpenseService.save(tOVehicleExpense);
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
