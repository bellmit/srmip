package com.kingtake.project.controller.plandraft;
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

import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;
import com.kingtake.project.service.plandraft.TPmPlanDetailServiceI;



/**   
 * @Title: Controller
 * @Description: 计划草案明细
 * @author onlineGenerator
 * @date 2016-03-30 10:46:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmPlanDetailController")
public class TPmPlanDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmPlanDetailController.class);

	@Autowired
	private TPmPlanDetailServiceI tPmPlanDetailService;
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
	 * 计划草案明细列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmPlanDetail")
	public ModelAndView tPmPlanDetail(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDetailList");
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
	public void datagrid(TPmPlanDetailEntity tPmPlanDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPmPlanDetailEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPlanDetail, request.getParameterMap());
		try{
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPmPlanDetailService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除计划草案明细
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmPlanDetailEntity tPmPlanDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmPlanDetail = systemService.getEntity(TPmPlanDetailEntity.class, tPmPlanDetail.getId());
		message = "计划草案明细删除成功";
		try{
			tPmPlanDetailService.delete(tPmPlanDetail);
		}catch(Exception e){
			e.printStackTrace();
			message = "计划草案明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除计划草案明细
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "计划草案明细删除成功";
		try{
			for(String id:ids.split(",")){
				TPmPlanDetailEntity tPmPlanDetail = systemService.getEntity(TPmPlanDetailEntity.class, 
				id
				);
				tPmPlanDetailService.delete(tPmPlanDetail);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "计划草案明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加计划草案明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmPlanDetailEntity tPmPlanDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "计划草案明细添加成功";
		try{
			tPmPlanDetailService.save(tPmPlanDetail);
		}catch(Exception e){
			e.printStackTrace();
			message = "计划草案明细添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新计划草案明细
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmPlanDetailEntity tPmPlanDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "计划草案明细更新成功";
		TPmPlanDetailEntity t = tPmPlanDetailService.get(TPmPlanDetailEntity.class, tPmPlanDetail.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPmPlanDetail, t);
			tPmPlanDetailService.saveOrUpdate(t);
		} catch (Exception e) {
			e.printStackTrace();
			message = "计划草案明细更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 计划草案明细新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPmPlanDetailEntity tPmPlanDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmPlanDetail.getId())) {
			tPmPlanDetail = tPmPlanDetailService.getEntity(TPmPlanDetailEntity.class, tPmPlanDetail.getId());
			req.setAttribute("tPmPlanDetailPage", tPmPlanDetail);
		}
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDetail-add");
	}
	/**
	 * 计划草案明细编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmPlanDetailEntity tPmPlanDetail, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmPlanDetail.getId())) {
			tPmPlanDetail = tPmPlanDetailService.getEntity(TPmPlanDetailEntity.class, tPmPlanDetail.getId());
			req.setAttribute("tPmPlanDetailPage", tPmPlanDetail);
		}
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDetail-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDetailUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmPlanDetailEntity tPmPlanDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmPlanDetailEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPlanDetail, request.getParameterMap());
		List<TPmPlanDetailEntity> tPmPlanDetails = this.tPmPlanDetailService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"计划草案明细");
		modelMap.put(NormalExcelConstants.CLASS,TPmPlanDetailEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("计划草案明细列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmPlanDetails);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmPlanDetailEntity tPmPlanDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "计划草案明细");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmPlanDetailEntity.class);
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
				List<TPmPlanDetailEntity> listTPmPlanDetailEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmPlanDetailEntity.class,params);
				for (TPmPlanDetailEntity tPmPlanDetail : listTPmPlanDetailEntitys) {
					tPmPlanDetailService.save(tPmPlanDetail);
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

    /**
     * 获取修改意见
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getMsgText")
    @ResponseBody
    public AjaxJson getMsgText(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String declareId = request.getParameter("id");
        List<TPmPlanDetailEntity> planDetailList = this.systemService.findByProperty(TPmPlanDetailEntity.class,
                "declareId", declareId);
        if (planDetailList.size() > 0) {
            TPmPlanDetailEntity detail = planDetailList.get(0);
            j.setMsg(detail.getMsgText());
        }
        return j;
    }
}
