package com.kingtake.project.controller.abatepay;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.ActivitiService;
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

import com.kingtake.project.entity.abatepay.TPmAbateEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.abatepay.TPmAbateServiceI;
import com.kingtake.project.service.manage.TPmProjectServiceI;



/**   
 * @Title: Controller
 * @Description: 减免信息表
 * @author onlineGenerator
 * @date 2015-07-24 11:33:01
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmAbateController")
public class TPmAbateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmAbateController.class);

	@Autowired
	private TPmAbateServiceI tPmAbateService;
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
    private ActivitiService activitiService;
	
	@Autowired
	private TPmProjectServiceI tPmProjectService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 减免信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmAbateListInfo")
	public ModelAndView tPmAbate(HttpServletRequest request) {
	    String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/abatepay/tPmAbateListInfo");
	}

    /**
     * 减免信息表列表 过程管理页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmAbateListProcess")
    public ModelAndView tPmAbateInfo(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        if (StringUtils.isNotEmpty(projectId)) {
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            if (project != null) {
                request.setAttribute("projectName", project.getProjectName());
            }
        }
        return new ModelAndView("com/kingtake/project/abatepay/tPmAbateListProcess");
    }

    /**
     * 减免信息表列表 查询统计页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmAbateListSearch")
    public ModelAndView tPmAbateSearch(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/abatepay/tPmAbateListSearch");
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
	public void datagrid(TPmAbateEntity tPmAbate,HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
	    CriteriaQuery cq = new CriteriaQuery(TPmAbateEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmAbate, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tPmAbateService.getDataGridReturn(cq, true);
		for (Object entity : dataGrid.getResults()) {
		    TPmAbateEntity tmp = (TPmAbateEntity) entity;
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tmp.getProjectId());
            tmp.setProjectName(project.getProjectName());
            if (!"1".equals(tmp.getBpmStatus())) {
                Map<String, Object> dataMap = activitiService.getProcessInstance(tmp.getId());
                if (dataMap != null) {
                    String taskName = (String) dataMap.get("taskName");
                    if (StringUtils.isNotEmpty(taskName)) {
                        tmp.setTaskName(taskName);
                    }
                    String processInstId = (String) dataMap.get("processInstId");
                    if (StringUtils.isNotEmpty(processInstId)) {
                        tmp.setProcessInstId(processInstId);
                    }
                    String taskId = (String) dataMap.get("taskId");
                    if (StringUtils.isNotEmpty(taskId)) {
                        tmp.setTaskId(taskId);
                    }
                    String assignee = (String) dataMap.get("assignee");
                    if (StringUtils.isNotEmpty(assignee)) {
                        tmp.setAssigneeName(assignee);
                    }
                }
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "datagridEasyUI")
	public void datagridEasyUI(TPmAbateEntity tPmAbate,HttpServletRequest request, 
			HttpServletResponse response) {
	    List<TPmAbateEntity> list = systemService.getSession()
	    		.createCriteria(TPmAbateEntity.class)
	    		.add(Restrictions.eq("projectId", tPmAbate.getProjectId()))
	    		.addOrder(Order.desc("createDate")).list();
		TagUtil.listToJson(response, list);
	}
	

	/**
	 * 删除减免信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmAbateEntity tPmAbate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmAbate = systemService.getEntity(TPmAbateEntity.class, tPmAbate.getId());
		message = "减免信息删除成功";
		try{
			tPmAbateService.deleteAddAttach(tPmAbate);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "减免信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除减免信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "减免信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TPmAbateEntity tPmAbate = systemService.getEntity(
						TPmAbateEntity.class, id);
				tPmAbateService.deleteAddAttach(tPmAbate);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "减免信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
     * 更新减免信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmAbateEntity tPmAbate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if(StringUtil.isEmpty(tPmAbate.getId())){
                tPmAbate.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
            }
            this.tPmAbateService.saveAbate(tPmAbate);
            message = "减免信息表保存成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "减免信息表保存失败";
            j.setSuccess(false);
        }
        j.setObj(tPmAbate);
        j.setMsg(message);
        return j;
    }
	

	/**
     * 减免信息表新增编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TPmAbateEntity tPmAbate, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmAbate.getId())) {
        	req.setAttribute("typeUpdate", "1");
            tPmAbate = tPmAbateService.getEntity(TPmAbateEntity.class, tPmAbate.getId());
        } else {
        	req.setAttribute("typeUpdate", "0");
            BigDecimal allFee = tPmProjectService.getAllFee(tPmAbate.getProjectId());
            tPmAbate.setAllFee(allFee);
            tPmAbate.setFcb_ratio("0");
            tPmAbate.setWx_ratio("0");
            tPmAbate.setXnxz_ratio("0");
            tPmAbate.setDxylbl(BigDecimal.ZERO);
            tPmAbate.setXyylbl(BigDecimal.ZERO);
            tPmAbate.setXylbl(BigDecimal.ZERO);
            tPmAbate.setJysylbl(BigDecimal.ZERO);
            tPmAbate.setZdfcbjme(BigDecimal.ZERO);
            tPmAbate.setZdwxjme(BigDecimal.ZERO);
            tPmAbate.setXnxzjme(BigDecimal.ZERO);
            tPmAbate.setDxylje(BigDecimal.ZERO);
            tPmAbate.setXyylje(BigDecimal.ZERO);
            tPmAbate.setXylje(BigDecimal.ZERO);
            tPmAbate.setJysylje(BigDecimal.ZERO);
            tPmAbate.setPayFunds(tPmAbate.getDxylje().add(tPmAbate.getXyylje().add(tPmAbate.getXylje())));
        }
        
        String sql = "select a.id,a.project_name,a.project_manager,a.fee_type,c.* "
        		+ " from t_pm_project a left join t_b_funds_property c on c.id=a.fee_type "
        		+ " where a.id=?";
        List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql, new Object[] { tPmAbate.getProjectId() });
        if(resultList!=null){
        	req.setAttribute("PROFOR_FLAG", resultList.get(0).get("PROFOR_FLAG"));
        	req.setAttribute("PROFOR_RATIO", resultList.get(0).get("PROFOR_RATIO"));
        	req.setAttribute("MOTO_FLAG", resultList.get(0).get("MOTO_FLAG"));
        	req.setAttribute("MOTO_RATIO", resultList.get(0).get("MOTO_RATIO"));
        }
       
      //附件
        if(StringUtils.isEmpty(tPmAbate.getAttachmentCode())){
            tPmAbate.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmAbate.getAttachmentCode(), "");
            tPmAbate.setCertificates(certificates);
        }
        req.setAttribute("tPmAbatePage", tPmAbate);
        return new ModelAndView("com/kingtake/project/abatepay/tPmAbate");
    }
    
    
    /**
     * 减免信息表流程单据页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TPmAbateEntity tPmAbate, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmAbate.getId())) {
        	String taskName = req.getParameter("taskName");
        	req.setAttribute("typeUpdate", "2");
        	if(taskName!=null&&!"".equals(taskName)){
        		req.setAttribute("taskName", taskName);
        	}
            tPmAbate = tPmAbateService.getEntity(TPmAbateEntity.class, tPmAbate.getId());
        }
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmAbate.getProjectId());
        BigDecimal allFee = project.getAllFee();
        if (allFee == null) {
            allFee = BigDecimal.ZERO;
        }
        req.setAttribute("allFee", allFee);
        //附件
        if(StringUtils.isEmpty(tPmAbate.getAttachmentCode())){
            tPmAbate.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmAbate.getAttachmentCode(), "");
            tPmAbate.setCertificates(certificates);
        }
        req.setAttribute("tPmAbatePage", tPmAbate);
        String opt = req.getParameter("opt");
        if(StringUtils.isNotEmpty(opt)){
            req.setAttribute("opt", opt);
        }
        return new ModelAndView("com/kingtake/project/abatepay/tPmAbate");
    }
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/abatepay/tPmAbateUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmAbateEntity tPmAbate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmAbateEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmAbate, request.getParameterMap());
		List<TPmAbateEntity> tPmAbates = this.tPmAbateService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"减免信息表");
		modelMap.put(NormalExcelConstants.CLASS,TPmAbateEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("减免信息表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmAbates);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmAbateEntity tPmAbate,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "减免信息表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmAbateEntity.class);
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
				List<TPmAbateEntity> listTPmAbateEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmAbateEntity.class,params);
				for (TPmAbateEntity tPmAbate : listTPmAbateEntitys) {
					tPmAbateService.save(tPmAbate);
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
