package com.kingtake.price.controller.tbjgbzjdhy;

import com.kingtake.price.entity.tbjgbzbeiz.TBJgbzBeizEntity;
import com.kingtake.price.entity.tbjgbzjdhy.TBJgbzJdhyEntity;
import com.kingtake.price.service.tbjgbzjdhy.TBJgbzJdhyServiceI;
import java.util.List;
import java.text.SimpleDateFormat;
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
 * @Description: 军队会议费
 * @author onlineGenerator
 * @date 2016-07-22 15:55:09
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzJdhyController")
public class TBJgbzJdhyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzJdhyController.class);

	@Autowired
	private TBJgbzJdhyServiceI tBJgbzJdhyService;
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
	 * 军队会议费列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzJdhy")
	public ModelAndView tBJgbzJdhy(HttpServletRequest request) {
		
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class);
		cq.eq("jgkdm", "jdhy");
		cq.add();
		List<TBJgbzBeizEntity> pList = this.systemService.getListByCriteriaQuery(cq, false);
		if (pList.size()>0){
			TBJgbzBeizEntity tbJgbzBeizEntity = pList.get(0);
			request.setAttribute("jgkId", tbJgbzBeizEntity.getId());
			request.setAttribute("jgkBeiz", tbJgbzBeizEntity.getBeiz());
		}
		
		return new ModelAndView("com/kingtake/price/tbjgbzjdhy/tBJgbzJdhyList");
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
	public void datagrid(TBJgbzJdhyEntity tBJgbzJdhy,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzJdhyEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzJdhy, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_zxsj_begin = request.getParameter("zxsj_begin");
		String query_zxsj_end = request.getParameter("zxsj_end");
		if(StringUtil.isNotEmpty(query_zxsj_begin)){
			cq.ge("zxsj", new SimpleDateFormat("yyyy-MM-dd").parse(query_zxsj_begin));
		}
		if(StringUtil.isNotEmpty(query_zxsj_end)){
			cq.le("zxsj", new SimpleDateFormat("yyyy-MM-dd").parse(query_zxsj_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzJdhyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除军队会议费
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzJdhyEntity tBJgbzJdhy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzJdhy = systemService.getEntity(TBJgbzJdhyEntity.class, tBJgbzJdhy.getId());
		message = "军队会议费标准删除成功";
		try{
			tBJgbzJdhyService.delete(tBJgbzJdhy);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "军队会议费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除军队会议费
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "军队会议费标准删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgbzJdhyEntity tBJgbzJdhy = systemService.getEntity(TBJgbzJdhyEntity.class, 
				id
				);
				tBJgbzJdhyService.delete(tBJgbzJdhy);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "军队会议费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加军队会议费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzJdhyEntity tBJgbzJdhy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "军队会议费标准添加成功";
		try{
			tBJgbzJdhyService.save(tBJgbzJdhy);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "军队会议费标准添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新军队会议费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzJdhyEntity tBJgbzJdhy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "军队会议费标准更新成功";
		
		try {
			if (StringUtils.isEmpty(tBJgbzJdhy.getId())) {
				tBJgbzJdhyService.save(tBJgbzJdhy);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzJdhyEntity t = tBJgbzJdhyService.get(TBJgbzJdhyEntity.class, tBJgbzJdhy.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzJdhy, t);
				tBJgbzJdhyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			message = "军队会议费标准更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 军队会议费新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzJdhyEntity tBJgbzJdhy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzJdhy.getId())) {
			tBJgbzJdhy = tBJgbzJdhyService.getEntity(TBJgbzJdhyEntity.class, tBJgbzJdhy.getId());
			req.setAttribute("tBJgbzJdhyPage", tBJgbzJdhy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzjdhy/tBJgbzJdhy-add");
	}
	/**
	 * 军队会议费编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzJdhyEntity tBJgbzJdhy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzJdhy.getId())) {
			tBJgbzJdhy = tBJgbzJdhyService.getEntity(TBJgbzJdhyEntity.class, tBJgbzJdhy.getId());
			req.setAttribute("tBJgbzJdhyPage", tBJgbzJdhy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzjdhy/tBJgbzJdhy-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzjdhy/tBJgbzJdhyUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzJdhyEntity tBJgbzJdhy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzJdhyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzJdhy, request.getParameterMap());
		List<TBJgbzJdhyEntity> tBJgbzJdhys = this.tBJgbzJdhyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"军队会议费标准");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzJdhyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("军队会议费标准列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzJdhys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzJdhyEntity tBJgbzJdhy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "军队会议费标准");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzJdhyEntity.class);
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
				List<TBJgbzJdhyEntity> listTBJgbzJdhyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzJdhyEntity.class,params);
				for (TBJgbzJdhyEntity tBJgbzJdhy : listTBJgbzJdhyEntitys) {
					tBJgbzJdhyService.save(tBJgbzJdhy);
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
