package com.kingtake.base.controller.warnmessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

import com.kingtake.base.entity.warnmessage.TBWarnPropertiesEntity;
import com.kingtake.base.service.warnmessage.TBWarnPropertiesServiceI;



/**   
 * @Title: Controller
 * @Description: 提醒配置
 * @author onlineGenerator
 * @date 2016-01-08 15:32:30
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBWarnPropertiesController")
public class TBWarnPropertiesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBWarnPropertiesController.class);

	@Autowired
	private TBWarnPropertiesServiceI tBWarnPropertiesService;
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
	 * 提醒配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBWarnProperties")
	public ModelAndView tBWarnProperties(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/warnmessage/tBWarnPropertiesList");
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
	public void datagrid(TBWarnPropertiesEntity tBWarnProperties,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBWarnPropertiesEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBWarnProperties, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBWarnPropertiesService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除提醒配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBWarnPropertiesEntity tBWarnProperties, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBWarnProperties = systemService.getEntity(TBWarnPropertiesEntity.class, tBWarnProperties.getId());
		message = "提醒配置删除成功";
		try{
			tBWarnPropertiesService.delete(tBWarnProperties);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "提醒配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除提醒配置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "提醒配置删除成功";
		try{
			for(String id:ids.split(",")){
				TBWarnPropertiesEntity tBWarnProperties = systemService.getEntity(TBWarnPropertiesEntity.class, 
				id
				);
				tBWarnPropertiesService.delete(tBWarnProperties);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "提醒配置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加提醒配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBWarnPropertiesEntity tBWarnProperties, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "提醒配置添加成功";
		try{
			tBWarnPropertiesService.save(tBWarnProperties);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "提醒配置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新提醒配置
	 * 
	 * @param ids
	 * @return
	 */
    @RequestMapping(params = "doAddUpdate")
	@ResponseBody
    public AjaxJson doAddUpdate(TBWarnPropertiesEntity tBWarnProperties, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "提醒配置添加/更新成功";
        try {
            if (StringUtils.isEmpty(tBWarnProperties.getId())) {
                tBWarnPropertiesService.save(tBWarnProperties);
            } else {
                TBWarnPropertiesEntity t = tBWarnPropertiesService.get(TBWarnPropertiesEntity.class,
                        tBWarnProperties.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBWarnProperties, t);
                tBWarnPropertiesService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "提醒配置添加/更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
	
	/**
	 * 提醒配置编辑页面跳转
	 * 
	 * @return
	 */
    @RequestMapping(params = "goAddUpdate")
	public ModelAndView goUpdate(TBWarnPropertiesEntity tBWarnProperties, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBWarnProperties.getId())) {
			tBWarnProperties = tBWarnPropertiesService.getEntity(TBWarnPropertiesEntity.class, tBWarnProperties.getId());
			req.setAttribute("tBWarnPropertiesPage", tBWarnProperties);
		}
        return new ModelAndView("com/kingtake/base/warnmessage/tBWarnProperties-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/base/warnmessage/tBWarnPropertiesUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBWarnPropertiesEntity tBWarnProperties,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBWarnPropertiesEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBWarnProperties, request.getParameterMap());
		List<TBWarnPropertiesEntity> tBWarnPropertiess = this.tBWarnPropertiesService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"提醒配置");
		modelMap.put(NormalExcelConstants.CLASS,TBWarnPropertiesEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("提醒配置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBWarnPropertiess);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBWarnPropertiesEntity tBWarnProperties,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "提醒配置");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBWarnPropertiesEntity.class);
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
				List<TBWarnPropertiesEntity> listTBWarnPropertiesEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBWarnPropertiesEntity.class,params);
				for (TBWarnPropertiesEntity tBWarnProperties : listTBWarnPropertiesEntitys) {
					tBWarnPropertiesService.save(tBWarnProperties);
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
