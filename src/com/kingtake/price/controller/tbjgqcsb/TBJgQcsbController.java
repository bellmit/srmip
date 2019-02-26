package com.kingtake.price.controller.tbjgqcsb;
import com.kingtake.price.entity.tbjgqcsb.TBJgQcsbEntity;
import com.kingtake.price.service.tbjgqcsb.TBJgQcsbServiceI;
import com.kingtake.zscq.entity.dljgxx.TZDljgxxEntity;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;


import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 器材设备价格库
 * @author onlineGenerator
 * @date 2016-07-25 16:44:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgQcsbController")
public class TBJgQcsbController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgQcsbController.class);

	@Autowired
	private TBJgQcsbServiceI tBJgQcsbService;
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
	 * 器材设备价格库列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgQcsb")
	public ModelAndView tBJgQcsb(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/price/tbjgqcsb/tBJgQcsbList");
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
	public void datagrid(TBJgQcsbEntity tBJgQcsb,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgQcsbEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgQcsb, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgQcsbService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除器材设备价格库
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgQcsbEntity tBJgQcsb, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgQcsb = systemService.getEntity(TBJgQcsbEntity.class, tBJgQcsb.getId());
		message = "器材设备价格库删除成功";
		try{
			tBJgQcsbService.delete(tBJgQcsb);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "器材设备价格库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除器材设备价格库
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "器材设备价格库删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgQcsbEntity tBJgQcsb = systemService.getEntity(TBJgQcsbEntity.class, 
				id
				);
				tBJgQcsbService.delete(tBJgQcsb);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "器材设备价格库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加器材设备价格库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgQcsbEntity tBJgQcsb, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "器材设备价格库添加成功";
		try{
			tBJgQcsbService.save(tBJgQcsb);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "器材设备价格库添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新器材设备价格库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgQcsbEntity tBJgQcsb, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "器材设备价格库更新成功";	       
		try {
/*			MyBeanUtils.copyBeanNotNull2Bean(tBJgQcsb, t);
			tBJgQcsbService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);*/
			
	        if (StringUtils.isEmpty(tBJgQcsb.getId())) {
	            this.tBJgQcsbService.save(tBJgQcsb);
	            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
	        } else {
	        	TBJgQcsbEntity t = tBJgQcsbService.get(TBJgQcsbEntity.class, tBJgQcsb.getId());
	            MyBeanUtils.copyBeanNotNull2Bean(tBJgQcsb, t);
	            tBJgQcsbService.saveOrUpdate(t);
	            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
	        }			
		} catch (Exception e) {
			e.printStackTrace();
			message = "器材设备价格库更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
		
		
		
		
/*        AjaxJson j = new AjaxJson();
        message = "代理机构信息更新成功";
        try {
            if (StringUtils.isEmpty(tZDljgxx.getId())) {
                this.tZDljgxxService.save(tZDljgxx);
            } else {
                TZDljgxxEntity t = tZDljgxxService.get(TZDljgxxEntity.class, tZDljgxx.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tZDljgxx, t);
                tZDljgxxService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "代理机构信息更新失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;*/
	}
	

	/**
	 * 器材设备价格库新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgQcsbEntity tBJgQcsb, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgQcsb.getId())) {
			tBJgQcsb = tBJgQcsbService.getEntity(TBJgQcsbEntity.class, tBJgQcsb.getId());
			req.setAttribute("tBJgQcsbPage", tBJgQcsb);
		}
		return new ModelAndView("com/kingtake/price/tbjgqcsb/tBJgQcsb-add");
	}
	/**
	 * 器材设备价格库编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgQcsbEntity tBJgQcsb, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgQcsb.getId())) {
			tBJgQcsb = tBJgQcsbService.getEntity(TBJgQcsbEntity.class, tBJgQcsb.getId());
			req.setAttribute("tBJgQcsbPage", tBJgQcsb);
		}
		return new ModelAndView("com/kingtake/price/tbjgqcsb/tBJgQcsb-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgqcsb/tBJgQcsbUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgQcsbEntity tBJgQcsb,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgQcsbEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgQcsb, request.getParameterMap());
		List<TBJgQcsbEntity> tBJgQcsbs = this.tBJgQcsbService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"器材设备价格库");
		modelMap.put(NormalExcelConstants.CLASS,TBJgQcsbEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("器材设备价格库列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgQcsbs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgQcsbEntity tBJgQcsb,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "器材设备价格库");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgQcsbEntity.class);
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
				List<TBJgQcsbEntity> listTBJgQcsbEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgQcsbEntity.class,params);
				for (TBJgQcsbEntity tBJgQcsb : listTBJgQcsbEntitys) {
					tBJgQcsbService.save(tBJgQcsb);
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
