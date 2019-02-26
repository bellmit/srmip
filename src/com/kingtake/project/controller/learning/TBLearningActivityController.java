package com.kingtake.project.controller.learning;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.pojo.base.TSBaseBusQuery;
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

import com.kingtake.project.entity.learning.TBLearningActivityEntity;
import com.kingtake.project.entity.learning.TBLearningTeamEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.learning.TBLearningActivityServiceI;



/**   
 * @Title: Controller
 * @Description: 学术活动信息表
 * @author onlineGenerator
 * @date 2015-12-06 10:52:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBLearningActivityController")
public class TBLearningActivityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBLearningActivityController.class);

	@Autowired
	private TBLearningActivityServiceI tBLearningActivityService;
	@Autowired
	private SystemService systemService;

    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TPmDeclareServiceI tPmDeclareService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 学术活动信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBLearningActivity")
    public ModelAndView tBLearningActivity(TBLearningActivityEntity tBLearningActivity, HttpServletRequest request) {
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
		return new ModelAndView("com/kingtake/project/learning/tBLearningActivityList");
	}

    /**
     * 学术论文机关页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goCheckTab")
    public ModelAndView goCheckTab(TBLearningActivityEntity tBLearningActivity, HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/learning/learningActivityCheck-tab");
    }

    /**
     * 学术著作信息表审核列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goCheckList")
    public ModelAndView goCheckList(TBLearningActivityEntity tBLearningActivity, HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/learning/learningActivityCheckList");
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
	public void datagrid(TBLearningActivityEntity tBLearningActivity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String role = request.getParameter("role");
		CriteriaQuery cq = new CriteriaQuery(TBLearningActivityEntity.class, dataGrid);
		//查询条件组装器
        TSUser user = ResourceUtil.getSessionUserName();
        if ("depart".equals(role)) {
            List<Map<String, Object>> listMap = this.tPmDeclareService.findHistoryTasks(user.getUserName(), request);
            if (listMap.size() == 0) {//如果已审核的学术论文，则返回。
                dataGrid.setResults(new ArrayList<TBLearningTeamEntity>());
                dataGrid.setTotal(0);
                TagUtil.datagrid(response, dataGrid);
                return;
            }
            Set<String> set = new HashSet<String>();
            for (Map<String, Object> map : listMap) {
                String id = (String) map.get("business_key");
                set.add(id);
            }
            cq.in("id", set.toArray());
        } else {
            cq.eq("createBy", user.getUserName());
        }
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningActivity, request.getParameterMap());
		try{
		//自定义追加查询条件
            cq.addOrder("createDate", SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBLearningActivityService.getDataGridReturn(cq, true);
        for (Object entity : dataGrid.getResults()) {
            TBLearningActivityEntity tmp = (TBLearningActivityEntity) entity;
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

	    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "checkList")
    public void checkList(TBLearningActivityEntity tBLearningActivity, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List<TSBaseBusQuery> taskList = this.tPmDeclareService.findPriTodoTasks(user.getUserName(), request);
        if (taskList.size() == 0) {//如果没有待审核的学术论文，则返回。
            dataGrid.setResults(new ArrayList<TBLearningActivityEntity>());
            dataGrid.setTotal(0);
            TagUtil.datagrid(response, dataGrid);
            return;
        }
        Map<String, TSBaseBusQuery> taskMap = new HashMap<String, TSBaseBusQuery>();
        for (TSBaseBusQuery query : taskList) {
            taskMap.put(query.getId(), query);
        }
        List<String> ids = new ArrayList<String>();
        for (TSBaseBusQuery bus : taskList) {
            ids.add(bus.getId());
        }
        CriteriaQuery cq = new CriteriaQuery(TBLearningActivityEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningActivity,
                request.getParameterMap());
        try {
            cq.in("id", ids.toArray());
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBLearningActivityService.getDataGridReturn(cq, false);
        for (Object entity : dataGrid.getResults()) {
            TBLearningActivityEntity tmp = (TBLearningActivityEntity) entity;
            TSBaseBusQuery query = taskMap.get(tmp.getId());
            tmp.setAssigneeName(query.getAssigneeName());
            if (query != null) {
                org.jeecgframework.workflow.model.activiti.Process pro = query.getProcess();
                if (pro != null) {
                    tmp.setTaskId(pro.getTask().getId());
                    tmp.setTaskName(pro.getTask().getName());
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除学术活动信息表
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBLearningActivityEntity tBLearningActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBLearningActivity = systemService.getEntity(TBLearningActivityEntity.class, tBLearningActivity.getId());
		message = "学术活动信息表删除成功";
		try{
			tBLearningActivityService.deleteAddAttach(tBLearningActivity);
		}catch(Exception e){
			e.printStackTrace();
			message = "学术活动信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除学术活动信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "学术活动信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TBLearningActivityEntity tBLearningActivity = systemService.getEntity(TBLearningActivityEntity.class, 
				id
				);
				tBLearningActivityService.deleteAddAttach(tBLearningActivity);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "学术活动信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新学术活动信息表
	 * 
	 * @param ids
	 * @return
	 */
    @RequestMapping(params = "doAddUpdate")
	@ResponseBody
    public AjaxJson doAddUpdate(TBLearningActivityEntity tBLearningActivity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "学术活动信息表新增/更新成功";
        try {
            if (StringUtils.isNotEmpty(tBLearningActivity.getId())) {
                TBLearningActivityEntity t = tBLearningActivityService.get(TBLearningActivityEntity.class,
                        tBLearningActivity.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBLearningActivity, t);
                tBLearningActivityService.saveOrUpdate(t);
            } else {
                tBLearningActivity.setBpmStatus("1");
                tBLearningActivityService.save(tBLearningActivity);
            }
            j.setObj(tBLearningActivity);
        } catch (Exception e) {
            e.printStackTrace();
            message = "学术活动信息表新增/更新失败";
            throw new BusinessException(e.getMessage());
        }
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 学术活动信息表编辑页面跳转
	 * 
	 * @return
	 */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TBLearningActivityEntity tBLearningActivity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBLearningActivity.getId())) {
			tBLearningActivity = tBLearningActivityService.getEntity(TBLearningActivityEntity.class, tBLearningActivity.getId());
		}
		if(StringUtils.isEmpty(tBLearningActivity.getAttachmentCode())){
		    tBLearningActivity.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBLearningActivity.getAttachmentCode(), "");
            tBLearningActivity.setUploadFiles(certificates);
        }
        req.setAttribute("tBLearningActivityPage", tBLearningActivity);
		return new ModelAndView("com/kingtake/project/learning/tBLearningActivity-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/learning/tBLearningActivityUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBLearningActivityEntity tBLearningActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBLearningActivityEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningActivity, request.getParameterMap());
		List<TBLearningActivityEntity> tBLearningActivitys = this.tBLearningActivityService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"学术活动信息表");
		modelMap.put(NormalExcelConstants.CLASS,TBLearningActivityEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("学术活动信息表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBLearningActivitys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBLearningActivityEntity tBLearningActivity,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "学术活动信息表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBLearningActivityEntity.class);
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
				List<TBLearningActivityEntity> listTBLearningActivityEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBLearningActivityEntity.class,params);
				for (TBLearningActivityEntity tBLearningActivity : listTBLearningActivityEntitys) {
					tBLearningActivityService.save(tBLearningActivity);
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
     * 学术论文信息表审核页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TBLearningActivityEntity tBLearningActivity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBLearningActivity.getId())) {
            tBLearningActivity = tBLearningActivityService.getEntity(TBLearningActivityEntity.class,
                    tBLearningActivity.getId());
        }
        if (req.getParameter("editFlag") != null) {
            req.setAttribute("editFlag", req.getParameter("editFlag"));
        }
        if (req.getParameter("read") != null) {
            req.setAttribute("read", req.getParameter("read"));
        }
        req.setAttribute("tBLearningActivityPage", tBLearningActivity);
        return new ModelAndView("com/kingtake/project/learning/tBLearningActivity-audit");
    }
}
