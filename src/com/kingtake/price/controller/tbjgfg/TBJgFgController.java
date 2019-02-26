package com.kingtake.price.controller.tbjgfg;
import com.kingtake.price.entity.tbjgfg.TBJgFgEntity;
import com.kingtake.price.service.tbjgfg.TBJgFgServiceI;
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
 * @Description: 价格法规库
 * @author onlineGenerator
 * @date 2016-07-25 16:44:36
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgFgController")
public class TBJgFgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgFgController.class);

	@Autowired
	private TBJgFgServiceI tBJgFgService;
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
	 * 价格法规库列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgFg")
	public ModelAndView tBJgFg(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/price/tbjgfg/tBJgFgList");
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
	public void datagrid(TBJgFgEntity tBJgFg,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgFgEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgFg, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgFgService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除价格法规库
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgFgEntity tBJgFg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgFg = systemService.getEntity(TBJgFgEntity.class, tBJgFg.getId());
		message = "价格法规库删除成功";
		try{
			tBJgFgService.delete(tBJgFg);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格法规库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除价格法规库
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "价格法规库删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgFgEntity tBJgFg = systemService.getEntity(TBJgFgEntity.class, 
				id
				);
				tBJgFgService.delete(tBJgFg);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "价格法规库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加价格法规库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgFgEntity tBJgFg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格法规库添加成功";
		try{
			tBJgFgService.save(tBJgFg);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "价格法规库添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新价格法规库
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgFgEntity tBJgFg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "价格法规库更新成功";
		
		try {			
			 if (StringUtils.isEmpty(tBJgFg.getId())) {
					tBJgFgService.save(tBJgFg);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			 }
			 else
			 {
				 TBJgFgEntity t = tBJgFgService.get(TBJgFgEntity.class, tBJgFg.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgFg, t);
				tBJgFgService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			 }			
		} catch (Exception e) {
			e.printStackTrace();
			message = "价格法规库更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setObj(tBJgFg);
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 价格法规库新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgFgEntity tBJgFg, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgFg.getId())) {
			tBJgFg = tBJgFgService.getEntity(TBJgFgEntity.class, tBJgFg.getId());
			req.setAttribute("tBJgFgPage", tBJgFg);
		}
		return new ModelAndView("com/kingtake/price/tbjgfg/tBJgFg-add");
	}
	/**
	 * 价格法规库编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgFgEntity tBJgFg, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgFg.getId())) {
			tBJgFg = tBJgFgService.getEntity(TBJgFgEntity.class, tBJgFg.getId());
			req.setAttribute("tBJgFgPage", tBJgFg);
		}
		return new ModelAndView("com/kingtake/price/tbjgfg/tBJgFg-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgfg/tBJgFgUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgFgEntity tBJgFg,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgFgEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgFg, request.getParameterMap());
		List<TBJgFgEntity> tBJgFgs = this.tBJgFgService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"价格法规库");
		modelMap.put(NormalExcelConstants.CLASS,TBJgFgEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("价格法规库列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgFgs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgFgEntity tBJgFg,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "价格法规库");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgFgEntity.class);
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
				List<TBJgFgEntity> listTBJgFgEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgFgEntity.class,params);
				for (TBJgFgEntity tBJgFg : listTBJgFgEntitys) {
					tBJgFgService.save(tBJgFg);
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
