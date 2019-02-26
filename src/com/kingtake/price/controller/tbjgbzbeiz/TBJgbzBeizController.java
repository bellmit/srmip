package com.kingtake.price.controller.tbjgbzbeiz;
import com.kingtake.price.entity.tbjgbzbeiz.TBJgbzBeizEntity;
import com.kingtake.price.service.tbjgbzbeiz.TBJgbzBeizServiceI;
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
 * @Description: 价格库备注
 * @author onlineGenerator
 * @date 2016-08-10 09:39:17
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzBeizController")
public class TBJgbzBeizController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzBeizController.class);

	@Autowired
	private TBJgbzBeizServiceI tBJgbzBeizService;
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
	 * 价格库备注列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzBeiz")
	public ModelAndView tBJgbzBeiz(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/price/tbjgbzbeiz/tBJgbzBeizList");
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
	public void datagrid(TBJgbzBeizEntity tBJgbzBeiz,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzBeiz, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzBeizService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除价格库备注
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzBeizEntity tBJgbzBeiz, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzBeiz = systemService.getEntity(TBJgbzBeizEntity.class, tBJgbzBeiz.getId());
		message = "价格库备注删除成功";
		try{
			tBJgbzBeizService.delete(tBJgbzBeiz);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格库备注删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除价格库备注
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "价格库备注删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgbzBeizEntity tBJgbzBeiz = systemService.getEntity(TBJgbzBeizEntity.class, 
				id
				);
				tBJgbzBeizService.delete(tBJgbzBeiz);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "价格库备注删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加价格库备注
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzBeizEntity tBJgbzBeiz, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格库备注添加成功";
		try{
			tBJgbzBeizService.save(tBJgbzBeiz);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格库备注添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新价格库备注
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzBeizEntity tBJgbzBeiz, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "备注更新成功";
		
		try {
			if (StringUtils.isEmpty(tBJgbzBeiz.getId())) {
				tBJgbzBeizService.save(tBJgbzBeiz);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzBeizEntity t = tBJgbzBeizService.get(TBJgbzBeizEntity.class, tBJgbzBeiz.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzBeiz, t);
				tBJgbzBeizService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "备注更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(tBJgbzBeiz);
		return j;
	}
	

	/**
	 * 价格库备注新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzBeizEntity tBJgbzBeiz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzBeiz.getId())) {
			tBJgbzBeiz = tBJgbzBeizService.getEntity(TBJgbzBeizEntity.class, tBJgbzBeiz.getId());
			req.setAttribute("tBJgbzBeizPage", tBJgbzBeiz);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzbeiz/tBJgbzBeiz-add");
	}
	/**
	 * 价格库备注编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzBeizEntity tBJgbzBeiz, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzBeiz.getId())) {
			tBJgbzBeiz = tBJgbzBeizService.getEntity(TBJgbzBeizEntity.class, tBJgbzBeiz.getId());
			req.setAttribute("tBJgbzBeizPage", tBJgbzBeiz);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzbeiz/tBJgbzBeiz-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzbeiz/tBJgbzBeizUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzBeizEntity tBJgbzBeiz,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzBeiz, request.getParameterMap());
		List<TBJgbzBeizEntity> tBJgbzBeizs = this.tBJgbzBeizService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"价格库备注");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzBeizEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("价格库备注列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzBeizs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzBeizEntity tBJgbzBeiz,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "价格库备注");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzBeizEntity.class);
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
				List<TBJgbzBeizEntity> listTBJgbzBeizEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzBeizEntity.class,params);
				for (TBJgbzBeizEntity tBJgbzBeiz : listTBJgbzBeizEntitys) {
					tBJgbzBeizService.save(tBJgbzBeiz);
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
