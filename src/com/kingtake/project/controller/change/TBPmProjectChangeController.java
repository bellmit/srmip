package com.kingtake.project.controller.change;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.project.entity.change.TBPmProjectChangeEntity;
import com.kingtake.project.entity.change.TBPmProjectChangePropertyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.change.TBPmProjectChangeServiceI;

/**
 * @Title: Controller
 * @Description: 项目负责人变更信息表
 * @author onlineGenerator
 * @date 2015-09-02 16:57:08
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmProjectChangeController")
public class TBPmProjectChangeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBPmProjectChangeController.class);

    @Autowired
    private TBPmProjectChangeServiceI tBPmProjectChangeService;
    @Autowired
    private SystemService systemService;
    private String message;
    @Autowired
    private ActivitiService activitiService;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 项目基本信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goProjectUpdate")
    public ModelAndView goProjectUpdate(TPmProjectEntity tPmProject, HttpServletRequest req) {
        // 项目状态:申请中
        req.setAttribute("applying", ProjectConstant.PROJECT_STATUS_APPLYING);

        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = tBPmProjectChangeService.getEntity(TPmProjectEntity.class, tPmProject.getId());
        	//List<TPmProjectEntity> projectList = systemService.findByProperty(TPmProjectEntity.class,"glxm.id", tPmProject.getId());
        	//tPmProject = projectList.get(0);
            req.setAttribute("tPmProjectPage", tPmProject);
            req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tPmProject.getPlanContractFlag()));
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectEntity.class);
            mcq.eq("mergeProject.id", tPmProject.getId());
            mcq.add();
            List<TPmProjectEntity> mergeProjList = systemService.getListByCriteriaQuery(mcq, false);
            req.setAttribute("mergeProjList", mergeProjList);

            String userId = tPmProject.getOfficeStaff();
            if (StringUtil.isNotEmpty(userId)) {
                TSUser user = systemService.get(TSUser.class, userId);
                if (user != null) {
                    req.setAttribute("officeStaffRealname", user.getRealName());
                }
            }

            String userId2 = tPmProject.getDepartStaff();
            if (StringUtil.isNotEmpty(userId2)) {
                TSUser user = systemService.get(TSUser.class, userId2);
                if (user != null) {
                    req.setAttribute("departStaffRealname", user.getRealName());
                }
            }
        }
        return new ModelAndView("com/kingtake/project/change/tPmProject-updateForChange");
    }

    /**
     * 生成项目变更信息
     * 
     * @param tPmProject
     * @param request
     * @return
     */
    @RequestMapping(params = "createProjectChangeInfo")
    @ResponseBody
    public AjaxJson createProjectChangeInfo(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        List<TBPmProjectChangePropertyEntity> propertyList = new ArrayList<TBPmProjectChangePropertyEntity>();
        try {
            propertyList = this.tBPmProjectChangeService.createProjectChange(tPmProject);
            if (propertyList.size() > 0) {
                message = "生成项目变更信息成功";
                j.setSuccess(true);
            } else {
                message = "项目信息没有变更，请检查";
                j.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "生成项目变更信息失败";
        }
        j.setMsg(message);
        if (propertyList.size() > 0) {
            JSONArray array = (JSONArray) JSONArray.toJSON(propertyList);
            j.setObj(array);
        }
        return j;
    }

    /**
     * 获取变更列表
     * 
     * @param changePorperyList
     * @return
     */
    /*
     * private List<Map<String, String>> getChangeList(List<TBPmProjectChangePropertyEntity> changePorperyList) {
     * List<Map<String, String>> changeList = new ArrayList<Map<String, String>>(); for (TBPmProjectChangePropertyEntity
     * propertyEntity : changePorperyList) { Map<String, String> dataMap = new HashMap<String, String>();
     * dataMap.put("field", propertyEntity.getPropertyName()); dataMap.put("oldValue", propertyEntity.getOldValue());
     * dataMap.put("newValue", propertyEntity.getNewValue()); changeList.add(dataMap); } return changeList; }
     */

    /**
     * 跳转到
     * 
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goProjectChangeInfo")
    public ModelAndView goProjectChangeInfo(HttpServletRequest req, HttpServletResponse response) {
        String opt = req.getParameter("opt");
        if ("edit".equals(opt)) {
            String changeId = req.getParameter("id");
            TBPmProjectChangeEntity changeEntity = this.systemService.get(TBPmProjectChangeEntity.class, changeId);
            req.setAttribute("projectChangePage", changeEntity);
            CriteriaQuery cq = new CriteriaQuery(TBPmProjectChangePropertyEntity.class);
            cq.eq("changeId", changeId);
            cq.eq("showFlag", "1");
            cq.add();
            List<TBPmProjectChangePropertyEntity> changeProperyList = this.systemService.getListByCriteriaQuery(cq,
                    false);
            String array = JSON.toJSONString(changeProperyList);
            req.setAttribute("propertyChangeStr", array);
        }
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", opt);
        }
        return new ModelAndView("com/kingtake/project/change/tPmProjectChangeInfo");
    }

    /**
     * 更新项目负责人变更信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBPmProjectChangeEntity projectChange, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目信息变更更新成功";
        try {
            this.tBPmProjectChangeService.saveProjectChange(projectChange);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目信息变更更新失败";
            j.setSuccess(false);
        }
        j.setObj(projectChange);
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转到项目变更页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goProjectChangeList")
    public ModelAndView goProjectChangeList(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            List<TPmProjectEntity> projectList = systemService.findByProperty(TPmProjectEntity.class,"glxm.id", projectId);
            TPmProjectEntity project = projectList.get(0);
            request.setAttribute("projectId", project.getId());
        }
        return new ModelAndView("com/kingtake/project/change/tBPmProjectChangeList");
    }

    /**
     * 跳转到项目变更基本信息页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goProjectChangeListInfo")
    public ModelAndView goProjectChangeListInfo(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
        }
        return new ModelAndView("com/kingtake/project/change/tBPmProjectChangeListInfo");
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
    public void datagrid(TBPmProjectChangeEntity tBPmProjectChange, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBPmProjectChangeEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmProjectChange,
                request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        systemService.getDataGridReturn(cq, true);
        for (Object entity : dataGrid.getResults()) {
            TBPmProjectChangeEntity tmp = (TBPmProjectChangeEntity) entity;
            if (!"1".equals(tmp.getBpmStatus())) {
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
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除项目负责人变更信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBPmProjectChangeEntity tBPmProjectChange, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBPmProjectChange = systemService.getEntity(TBPmProjectChangeEntity.class, tBPmProjectChange.getId());
        message = "项目变更信息表删除成功";
        try {
            this.tBPmProjectChangeService.delete(tBPmProjectChange);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目负责人变更信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转到
     * 
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(HttpServletRequest req, HttpServletResponse response) {
        String opt = "edit";
        if ("edit".equals(opt)) {
            String changeId = req.getParameter("id");
            TBPmProjectChangeEntity changeEntity = this.systemService.get(TBPmProjectChangeEntity.class, changeId);
            req.setAttribute("projectChangePage", changeEntity);
            CriteriaQuery cq = new CriteriaQuery(TBPmProjectChangePropertyEntity.class);
            cq.eq("changeId", changeId);
            cq.eq("showFlag", "1");
            cq.add();
            List<TBPmProjectChangePropertyEntity> changeProperyList = this.systemService.getListByCriteriaQuery(cq,
                    false);
            String array = JSON.toJSONString(changeProperyList);
            req.setAttribute("propertyChangeStr", array);
        }
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", opt);
        }
        req.setAttribute("read", "1");
        return new ModelAndView("com/kingtake/project/change/tPmProjectChangeInfo");
    }
    
	/**
	 * 导出word
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "ExportWord")
	public String exportWord(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        
        TBPmProjectChangeEntity tBPmProjectChange = this.systemService.get(TBPmProjectChangeEntity.class, id);
        
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tBPmProjectChange.getProjectId());
        String yjwh = project.getAccordingNum();                
        if(StringUtil.isNotEmpty(yjwh)){
        	map.put("yjwh", yjwh);        
        }
        
        String xmmc = project.getProjectName();
        if(StringUtil.isNotEmpty(xmmc)){
        	map.put("xmmc", xmmc);
        }
        
        CriteriaQuery cq = new CriteriaQuery(TBPmProjectChangePropertyEntity.class);
        cq.eq("changeId", id);
        cq.eq("showFlag", "1");
        cq.add();
        List<TBPmProjectChangePropertyEntity> changeProperyList = this.systemService.getListByCriteriaQuery(cq,false);
           
        StringBuffer bgnr = new StringBuffer();
        StringBuffer bgyy = new StringBuffer();
        
        for(TBPmProjectChangePropertyEntity changePropery : changeProperyList){
        	bgnr.append("变更项：" + changePropery.getPropertyDesc() + "\t");
        	String oldValue = changePropery.getOldValue();
        	if(oldValue == null){
        		oldValue = "";
        	}
        	bgnr.append("原值：" + oldValue + "\t");
        	bgnr.append("新值：" + changePropery.getNewValue() + "\r\n");
        } 
        
        bgyy.append("原因：" + tBPmProjectChange.getChangeReason() + "\t");
        bgyy.append("依据：" + tBPmProjectChange.getChangeAccording() + "\r\n");
        map.put("bgnr", bgnr.toString());
        map.put("bgyy", bgyy.toString());
                
        modelMap.put(TemplateWordConstants.FILE_NAME, "项目变更申请表_" + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "export/template/xmbg.docx");
        
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
        

        
        
//        yfzr.append(tBPmProjectChange.get);
//
//        for (int i = 0;i<tOHandovers.size();i++) {
//            TOHandoverEntity toh = tOHandovers.get(i);
//            bzgznr.append((i + 1) + "."+"\t");
//            bzgznr.append(DateUtils.formatDate(toh.getHandoverTime(), "MM月dd日，"));
//            bzgznr.append(" ");
//            bzgznr.append(getVal(toh.getHandoverContent())+"。");
//            bzgznr.append(" (" + toh.getHandoverName() + ")");
//            bzgznr.append("\r\n");
//
//            xzgzjh.append((i + 1) + ".");
//            xzgzjh.append(getVal(toh.getNextWeekWorkContent())+"。");
//            xzgzjh.append(" (" + toh.getHandoverName() + ")");
//            xzgzjh.append("\r\n");
//
//            zygzqk.append((i + 1) + ".");
//            zygzqk.append(getVal(toh.getMainTask())+"。");
//            zygzqk.append(" (" + toh.getHandoverName() + ")");
//            zygzqk.append("\r\n");
//        }
//        map.put("exportTime", DateUtils.formatDate());
//        map.put("bzgzyd", bzgznr.toString());
//        map.put("xzgzjh", xzgzjh.toString());
//        map.put("cdzyrwqk", zygzqk.toString());
//        modelMap.put(TemplateWordConstants.FILE_NAME, "交班材料_" + DateUtils.formatDate());
//        modelMap.put(TemplateWordConstants.MAP_DATA, map);
//        modelMap.put(TemplateWordConstants.URL, "export/template/jbcl.docx");
        
	}
}
