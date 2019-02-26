package com.kingtake.project.controller.declare.army;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
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

import com.kingtake.project.entity.declare.army.TPmDeclareFundsTechnologyEntity;
import com.kingtake.project.service.declare.army.TPmDeclareFundsTechnologyServiceI;



/**   
 * @Title: Controller
 * @Description: 技术基础项目--申报书--预算经费
 * @author onlineGenerator
 * @date 2015-09-22 11:40:24
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmDeclareFundsTechnologyController")
public class TPmDeclareFundsTechnologyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmDeclareFundsTechnologyController.class);

	@Autowired
	private TPmDeclareFundsTechnologyServiceI tPmDeclareFundsTechnologyService;
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
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology,HttpServletRequest request, 
			HttpServletResponse response) {
		Session session = systemService.getSession();
		List<TPmDeclareFundsTechnologyEntity> list = session.createCriteria(TPmDeclareFundsTechnologyEntity.class).add(
				Restrictions.eq("projDeclareId", tPmDeclareFundsTechnology.getProjDeclareId())).list();
		TagUtil.response(response, JSONHelper.collection2json(list));
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmDeclareFundsTechnology = systemService.getEntity(TPmDeclareFundsTechnologyEntity.class, 
				tPmDeclareFundsTechnology.getId());
		message = "预算删除成功";
		try{
			tPmDeclareFundsTechnologyService.delete(tPmDeclareFundsTechnology);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "预算删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 添加
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "预算添加成功";
		try{
			tPmDeclareFundsTechnologyService.save(tPmDeclareFundsTechnology);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "预算添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "预算更新成功";
		TPmDeclareFundsTechnologyEntity t = tPmDeclareFundsTechnologyService.get(
				TPmDeclareFundsTechnologyEntity.class, 
				tPmDeclareFundsTechnology.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPmDeclareFundsTechnology, t);
			tPmDeclareFundsTechnologyService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "预算更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmDeclareFundsTechnology.getId())) {
			tPmDeclareFundsTechnology = tPmDeclareFundsTechnologyService.getEntity(TPmDeclareFundsTechnologyEntity.class, tPmDeclareFundsTechnology.getId());
		}
		req.setAttribute("tPmDeclareFundsTechnologyPage", tPmDeclareFundsTechnology);
		return new ModelAndView("com/kingtake/project/declare/army/tPmDeclareFundsTechnology-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/test/tPmDeclareFundsTechnologyUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmDeclareFundsTechnologyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmDeclareFundsTechnology, request.getParameterMap());
		List<TPmDeclareFundsTechnologyEntity> tPmDeclareFundsTechnologys = this.tPmDeclareFundsTechnologyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_PM_DECLARE_FUNDS_TECHNOLOGY");
		modelMap.put(NormalExcelConstants.CLASS,TPmDeclareFundsTechnologyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_PM_DECLARE_FUNDS_TECHNOLOGY列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmDeclareFundsTechnologys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_PM_DECLARE_FUNDS_TECHNOLOGY");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmDeclareFundsTechnologyEntity.class);
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
				List<TPmDeclareFundsTechnologyEntity> listTPmDeclareFundsTechnologyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmDeclareFundsTechnologyEntity.class,params);
				for (TPmDeclareFundsTechnologyEntity tPmDeclareFundsTechnology : listTPmDeclareFundsTechnologyEntitys) {
					tPmDeclareFundsTechnologyService.save(tPmDeclareFundsTechnology);
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
