package com.kingtake.price.controller.tbjgbzwzjgxx;
import com.kingtake.price.entity.tbjgbzwzjgxx.TBJgbzWzjgxxEntity;
import com.kingtake.price.service.tbjgbzwzjgxx.TBJgbzWzjgxxServiceI;
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
 * @Description: 物资价格信息
 * @author onlineGenerator
 * @date 2016-08-18 09:57:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzWzjgxxController")
public class TBJgbzWzjgxxController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzWzjgxxController.class);

	@Autowired
	private TBJgbzWzjgxxServiceI tBJgbzWzjgxxService;
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
	 * 物资价格信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzWzjgxx")
	public ModelAndView tBJgbzWzjgxx(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/price/tbjgbzwzjgxx/tBJgbzWzjgxxList");
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
	public void datagrid(TBJgbzWzjgxxEntity tBJgbzWzjgxx,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzWzjgxxEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzWzjgxx, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzWzjgxxService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除物资价格信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzWzjgxxEntity tBJgbzWzjgxx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzWzjgxx = systemService.getEntity(TBJgbzWzjgxxEntity.class, tBJgbzWzjgxx.getId());
		message = "物资价格信息删除成功";
		try{
			tBJgbzWzjgxxService.delete(tBJgbzWzjgxx);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "物资价格信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除物资价格信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "物资价格信息删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgbzWzjgxxEntity tBJgbzWzjgxx = systemService.getEntity(TBJgbzWzjgxxEntity.class, 
				id
				);
				tBJgbzWzjgxxService.delete(tBJgbzWzjgxx);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "物资价格信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加物资价格信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzWzjgxxEntity tBJgbzWzjgxx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "物资价格信息添加成功";
		try{
			tBJgbzWzjgxxService.save(tBJgbzWzjgxx);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "物资价格信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新物资价格信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzWzjgxxEntity tBJgbzWzjgxx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "物资价格信息更新成功";
		
		try {
			if (StringUtils.isEmpty(tBJgbzWzjgxx.getId())) {
				tBJgbzWzjgxxService.save(tBJgbzWzjgxx);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzWzjgxxEntity t = tBJgbzWzjgxxService.get(TBJgbzWzjgxxEntity.class, tBJgbzWzjgxx.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzWzjgxx, t);
				tBJgbzWzjgxxService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "物资价格信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setObj(tBJgbzWzjgxx);
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 物资价格信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzWzjgxxEntity tBJgbzWzjgxx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzWzjgxx.getId())) {
			tBJgbzWzjgxx = tBJgbzWzjgxxService.getEntity(TBJgbzWzjgxxEntity.class, tBJgbzWzjgxx.getId());
			req.setAttribute("tBJgbzWzjgxxPage", tBJgbzWzjgxx);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzwzjgxx/tBJgbzWzjgxx-add");
	}
	/**
	 * 物资价格信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzWzjgxxEntity tBJgbzWzjgxx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzWzjgxx.getId())) {
			tBJgbzWzjgxx = tBJgbzWzjgxxService.getEntity(TBJgbzWzjgxxEntity.class, tBJgbzWzjgxx.getId());
			req.setAttribute("tBJgbzWzjgxxPage", tBJgbzWzjgxx);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzwzjgxx/tBJgbzWzjgxx-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzwzjgxx/tBJgbzWzjgxxUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzWzjgxxEntity tBJgbzWzjgxx,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzWzjgxxEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzWzjgxx, request.getParameterMap());
		List<TBJgbzWzjgxxEntity> tBJgbzWzjgxxs = this.tBJgbzWzjgxxService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"物资价格信息");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzWzjgxxEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("物资价格信息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzWzjgxxs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzWzjgxxEntity tBJgbzWzjgxx,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "物资价格信息");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzWzjgxxEntity.class);
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
				List<TBJgbzWzjgxxEntity> listTBJgbzWzjgxxEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzWzjgxxEntity.class,params);
				for (TBJgbzWzjgxxEntity tBJgbzWzjgxx : listTBJgbzWzjgxxEntitys) {
					tBJgbzWzjgxxService.save(tBJgbzWzjgxx);
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
