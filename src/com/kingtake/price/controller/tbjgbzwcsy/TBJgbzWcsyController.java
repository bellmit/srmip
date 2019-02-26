package com.kingtake.price.controller.tbjgbzwcsy;
import com.kingtake.price.entity.tbjgbzbeiz.TBJgbzBeizEntity;
import com.kingtake.price.entity.tbjgbzwcsy.TBJgbzWcsyEntity;
import com.kingtake.price.service.tbjgbzwcsy.TBJgbzWcsyServiceI;
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
 * @Description: 价格库系统_外场试验费标准
 * @author onlineGenerator
 * @date 2016-07-25 09:39:21
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzWcsyController")
public class TBJgbzWcsyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzWcsyController.class);

	@Autowired
	private TBJgbzWcsyServiceI tBJgbzWcsyService;
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
	 * 价格库系统_外场试验费标准列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzWcsy")
	public ModelAndView tBJgbzWcsy(HttpServletRequest request) {
		
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class);
		cq.eq("jgkdm", "wcsy");
		cq.add();
		List<TBJgbzBeizEntity> pList = this.systemService.getListByCriteriaQuery(cq, false);
		if (pList.size()>0){
			TBJgbzBeizEntity tbJgbzBeizEntity = pList.get(0);
			request.setAttribute("jgkId", tbJgbzBeizEntity.getId());
			request.setAttribute("jgkBeiz", tbJgbzBeizEntity.getBeiz());
		}
		
		return new ModelAndView("com/kingtake/price/tbjgbzwcsy/tBJgbzWcsyList");
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
	public void datagrid(TBJgbzWcsyEntity tBJgbzWcsy,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzWcsyEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzWcsy, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzWcsyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除价格库系统_外场试验费标准
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzWcsyEntity tBJgbzWcsy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzWcsy = systemService.getEntity(TBJgbzWcsyEntity.class, tBJgbzWcsy.getId());
		message = "价格库系统_外场试验费标准删除成功";
		try{
			tBJgbzWcsyService.delete(tBJgbzWcsy);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格库系统_外场试验费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除价格库系统_外场试验费标准
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "价格库系统_外场试验费标准删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgbzWcsyEntity tBJgbzWcsy = systemService.getEntity(TBJgbzWcsyEntity.class, 
				id
				);
				tBJgbzWcsyService.delete(tBJgbzWcsy);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "价格库系统_外场试验费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加价格库系统_外场试验费标准
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzWcsyEntity tBJgbzWcsy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格库系统_外场试验费标准添加成功";
		try{
			tBJgbzWcsyService.save(tBJgbzWcsy);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格库系统_外场试验费标准添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新价格库系统_外场试验费标准
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzWcsyEntity tBJgbzWcsy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格库系统_外场试验费标准更新成功";
		
		try {
			if (StringUtils.isEmpty(tBJgbzWcsy.getId())) {
				tBJgbzWcsyService.save(tBJgbzWcsy);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzWcsyEntity t = tBJgbzWcsyService.get(TBJgbzWcsyEntity.class, tBJgbzWcsy.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzWcsy, t);
				tBJgbzWcsyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "价格库系统_外场试验费标准更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 价格库系统_外场试验费标准新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzWcsyEntity tBJgbzWcsy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzWcsy.getId())) {
			tBJgbzWcsy = tBJgbzWcsyService.getEntity(TBJgbzWcsyEntity.class, tBJgbzWcsy.getId());
			req.setAttribute("tBJgbzWcsyPage", tBJgbzWcsy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzwcsy/tBJgbzWcsy-add");
	}
	/**
	 * 价格库系统_外场试验费标准编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzWcsyEntity tBJgbzWcsy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzWcsy.getId())) {
			tBJgbzWcsy = tBJgbzWcsyService.getEntity(TBJgbzWcsyEntity.class, tBJgbzWcsy.getId());
			req.setAttribute("tBJgbzWcsyPage", tBJgbzWcsy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzwcsy/tBJgbzWcsy-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzwcsy/tBJgbzWcsyUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzWcsyEntity tBJgbzWcsy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzWcsyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzWcsy, request.getParameterMap());
		List<TBJgbzWcsyEntity> tBJgbzWcsys = this.tBJgbzWcsyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"价格库系统_外场试验费标准");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzWcsyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("价格库系统_外场试验费标准列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzWcsys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzWcsyEntity tBJgbzWcsy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "价格库系统_外场试验费标准");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzWcsyEntity.class);
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
				List<TBJgbzWcsyEntity> listTBJgbzWcsyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzWcsyEntity.class,params);
				for (TBJgbzWcsyEntity tBJgbzWcsy : listTBJgbzWcsyEntitys) {
					tBJgbzWcsyService.save(tBJgbzWcsy);
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
