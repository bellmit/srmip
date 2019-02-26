package com.kingtake.expert.controller.reviewtype;

import java.io.IOException;
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

import com.kingtake.expert.entity.reviewtype.TErReviewTypeEntity;
import com.kingtake.expert.service.reviewtype.TErReviewTypeServiceI;



/**   
 * @Title: Controller
 * @Description: 评审内容类型
 * @author onlineGenerator
 * @date 2015-08-18 16:51:20
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tErReviewTypeController")
public class TErReviewTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TErReviewTypeController.class);

	@Autowired
	private TErReviewTypeServiceI tErReviewTypeService;
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
	 * 评审内容类型列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tErReviewType")
	public ModelAndView tErReviewType(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/expert/reviewtype/tErReviewTypeList");
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
	public void datagrid(TErReviewTypeEntity tErReviewType,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TErReviewTypeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErReviewType, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tErReviewTypeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除评审内容类型
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TErReviewTypeEntity tErReviewType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tErReviewType = systemService.getEntity(TErReviewTypeEntity.class, tErReviewType.getId());
		message = "评审内容类型删除成功";
		try{
			tErReviewTypeService.delete(tErReviewType);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "评审内容类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除评审内容类型
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "评审内容类型删除成功";
		try{
			for(String id:ids.split(",")){
				TErReviewTypeEntity tErReviewType = systemService.getEntity(TErReviewTypeEntity.class, 
				id
				);
				tErReviewTypeService.delete(tErReviewType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "评审内容类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加评审内容类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TErReviewTypeEntity tErReviewType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "评审内容类型添加成功";
		try{
			tErReviewTypeService.save(tErReviewType);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "评审内容类型添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新评审内容类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TErReviewTypeEntity tErReviewType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "评审内容类型更新成功";
		TErReviewTypeEntity t = tErReviewTypeService.get(TErReviewTypeEntity.class, tErReviewType.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tErReviewType, t);
			tErReviewTypeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "评审内容类型更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 评审内容类型新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TErReviewTypeEntity tErReviewType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tErReviewType.getId())) {
			tErReviewType = tErReviewTypeService.getEntity(TErReviewTypeEntity.class, tErReviewType.getId());
			req.setAttribute("tErReviewTypePage", tErReviewType);
		}
        return new ModelAndView("com/kingtake/expert/reviewtype/tErReviewType-add");
	}
	/**
	 * 评审内容类型编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TErReviewTypeEntity tErReviewType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tErReviewType.getId())) {
			tErReviewType = tErReviewTypeService.getEntity(TErReviewTypeEntity.class, tErReviewType.getId());
			req.setAttribute("tErReviewTypePage", tErReviewType);
		}
        return new ModelAndView("com/kingtake/expert/reviewtype/tErReviewType-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/expert/reviewtype/tErReviewTypeUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TErReviewTypeEntity tErReviewType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TErReviewTypeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErReviewType, request.getParameterMap());
		List<TErReviewTypeEntity> tErReviewTypes = this.tErReviewTypeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"评审内容类型");
		modelMap.put(NormalExcelConstants.CLASS,TErReviewTypeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("评审内容类型列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tErReviewTypes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TErReviewTypeEntity tErReviewType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "评审内容类型");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TErReviewTypeEntity.class);
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
				List<TErReviewTypeEntity> listTErReviewTypeEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TErReviewTypeEntity.class,params);
				for (TErReviewTypeEntity tErReviewType : listTErReviewTypeEntitys) {
					tErReviewTypeService.save(tErReviewType);
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
