package com.kingtake.price.controller.tbjgbzhjsysf;
import com.kingtake.price.entity.tbjgbzbeiz.TBJgbzBeizEntity;
import com.kingtake.price.entity.tbjgbzhjsysf.TBJgbzHjsysfEntity;
import com.kingtake.price.service.tbjgbzhjsysf.TBJgbzHjsysfServiceI;
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
 * @Description: 环境试验收费标准
 * @author onlineGenerator
 * @date 2016-07-25 15:19:41
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzHjsysfController")
public class TBJgbzHjsysfController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzHjsysfController.class);

	@Autowired
	private TBJgbzHjsysfServiceI tBJgbzHjsysfService;
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
	 * 环境试验收费标准列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzHjsysf")
	public ModelAndView tBJgbzHjsysf(HttpServletRequest request) {
		
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class);
		cq.eq("jgkdm", "hjsysf");
		cq.add();
		List<TBJgbzBeizEntity> pList = this.systemService.getListByCriteriaQuery(cq, false);
		if (pList.size()>0){
			TBJgbzBeizEntity tbJgbzBeizEntity = pList.get(0);
			request.setAttribute("jgkId", tbJgbzBeizEntity.getId());
			request.setAttribute("jgkBeiz", tbJgbzBeizEntity.getBeiz());
		}
		
		return new ModelAndView("com/kingtake/price/tbjgbzhjsysf/tBJgbzHjsysfList");
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
	public void datagrid(TBJgbzHjsysfEntity tBJgbzHjsysf,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzHjsysfEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzHjsysf, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzHjsysfService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除环境试验收费标准
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzHjsysfEntity tBJgbzHjsysf, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzHjsysf = systemService.getEntity(TBJgbzHjsysfEntity.class, tBJgbzHjsysf.getId());
		message = "环境试验收费标准删除成功";
		try{
			tBJgbzHjsysfService.delete(tBJgbzHjsysf);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "环境试验收费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除环境试验收费标准
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "环境试验收费标准删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgbzHjsysfEntity tBJgbzHjsysf = systemService.getEntity(TBJgbzHjsysfEntity.class, 
				id
				);
				tBJgbzHjsysfService.delete(tBJgbzHjsysf);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "环境试验收费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加环境试验收费标准
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzHjsysfEntity tBJgbzHjsysf, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "环境试验收费标准添加成功";
		try{
			tBJgbzHjsysfService.save(tBJgbzHjsysf);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "环境试验收费标准添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新环境试验收费标准
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzHjsysfEntity tBJgbzHjsysf, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "环境试验收费标准更新成功";
		
		try {
			if (StringUtils.isEmpty(tBJgbzHjsysf.getId())) {
				tBJgbzHjsysfService.save(tBJgbzHjsysf);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzHjsysfEntity t = tBJgbzHjsysfService.get(TBJgbzHjsysfEntity.class, tBJgbzHjsysf.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzHjsysf, t);
				tBJgbzHjsysfService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}				
		} catch (Exception e) {
			e.printStackTrace();
			message = "环境试验收费标准更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 环境试验收费标准新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzHjsysfEntity tBJgbzHjsysf, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzHjsysf.getId())) {
			tBJgbzHjsysf = tBJgbzHjsysfService.getEntity(TBJgbzHjsysfEntity.class, tBJgbzHjsysf.getId());
			req.setAttribute("tBJgbzHjsysfPage", tBJgbzHjsysf);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzhjsysf/tBJgbzHjsysf-add");
	}
	/**
	 * 环境试验收费标准编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzHjsysfEntity tBJgbzHjsysf, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzHjsysf.getId())) {
			tBJgbzHjsysf = tBJgbzHjsysfService.getEntity(TBJgbzHjsysfEntity.class, tBJgbzHjsysf.getId());
			req.setAttribute("tBJgbzHjsysfPage", tBJgbzHjsysf);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzhjsysf/tBJgbzHjsysf-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzhjsysf/tBJgbzHjsysfUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzHjsysfEntity tBJgbzHjsysf,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzHjsysfEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzHjsysf, request.getParameterMap());
		List<TBJgbzHjsysfEntity> tBJgbzHjsysfs = this.tBJgbzHjsysfService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"环境试验收费标准");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzHjsysfEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("环境试验收费标准列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzHjsysfs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzHjsysfEntity tBJgbzHjsysf,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "环境试验收费标准");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzHjsysfEntity.class);
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
				List<TBJgbzHjsysfEntity> listTBJgbzHjsysfEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzHjsysfEntity.class,params);
				for (TBJgbzHjsysfEntity tBJgbzHjsysf : listTBJgbzHjsysfEntitys) {
					tBJgbzHjsysfService.save(tBJgbzHjsysf);
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
