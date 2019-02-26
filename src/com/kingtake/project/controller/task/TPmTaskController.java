package com.kingtake.project.controller.task;

import java.util.Date;
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
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.task.TPmTaskEntity;
import com.kingtake.project.entity.task.TPmTaskNodeEntity;
import com.kingtake.project.page.task.TPmTaskPage;
import com.kingtake.project.service.task.TPmTaskServiceI;

/**
 * @Title: Controller
 * @Description: 任务管理
 * @author onlineGenerator
 * @date 2015-07-21 14:04:20
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmTaskController")
public class TPmTaskController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmTaskController.class);

    @Autowired
    private TPmTaskServiceI tPmTaskService;
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
     * 任务管理列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmTask")
    public ModelAndView tPmTask(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/task/tPmTaskList");
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
    public void datagrid(TPmTaskEntity tPmTask, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmTaskEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmTask);
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmTaskService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除任务管理
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmTaskEntity tPmTask, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmTask = systemService.getEntity(TPmTaskEntity.class, tPmTask.getId());
        String message = "任务管理删除成功";
        try {
            tPmTaskService.delMain(tPmTask);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "任务管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除任务管理
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "任务管理删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmTaskEntity tPmTask = systemService.getEntity(TPmTaskEntity.class, id);
                tPmTaskService.delMain(tPmTask);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "任务管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加任务管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmTaskEntity tPmTask, TPmTaskPage tPmTaskPage, HttpServletRequest request) {
        List<TPmTaskNodeEntity> tPmTaskNodeList = tPmTaskPage.getTPmTaskNodeList();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try {
            tPmTaskService.addMain(tPmTask, tPmTaskNodeList);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "任务管理添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 新增/更新任务管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TPmTaskEntity tPmTask, TPmTaskPage tPmTaskPage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            message = "任务书保存成功";
            if (StringUtil.isNotEmpty(tPmTask.getId())) {
                TPmTaskEntity t = tPmTaskService.get(TPmTaskEntity.class, tPmTask.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmTask, t);
                tPmTaskService.updateEntitie(t);
            } else {
                tPmTask.setAuditStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                tPmTaskService.save(tPmTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "任务书保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 任务管理新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTask.getId())) {
            tPmTask = tPmTaskService.getEntity(TPmTaskEntity.class, tPmTask.getId());
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        return new ModelAndView("com/kingtake/project/task/tPmTask-add");
    }

    /**
     * 任务管理编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTask.getId())) {
            tPmTask = tPmTaskService.getEntity(TPmTaskEntity.class, tPmTask.getId());
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        return new ModelAndView("com/kingtake/project/task/tPmTask-update");
    }

    /**
     * 任务管理编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "taskUpdateForResearchGroup")
    public ModelAndView taskUpdateForResearchGroup(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTask.getId())) {
            tPmTask = tPmTaskService.getEntity(TPmTaskEntity.class, tPmTask.getId());
        }
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            CriteriaQuery cq = new CriteriaQuery(TPmTaskEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmTaskEntity> taskList = tPmTaskService.getListByCriteriaQuery(cq, false);
            if (taskList.size() > 0) {
                tPmTask = taskList.get(0);
            } else {
                TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
                tPmTask.setProject(proj);
            }
            if(StringUtils.isEmpty(tPmTask.getAttachmentCode())){
                tPmTask.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmTask.getAttachmentCode(), "");
                tPmTask.setAttachments(certificates);
            }
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        return new ModelAndView("com/kingtake/project/task/tPmTask-updateForResearchGroup");
    }

    /**
     * 任务管理编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "taskUpdateForResearchGroupInfo")
    public ModelAndView taskUpdateForResearchGroupInfo(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTask.getId())) {
            tPmTask = tPmTaskService.getEntity(TPmTaskEntity.class, tPmTask.getId());
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            CriteriaQuery cq = new CriteriaQuery(TPmTaskEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmTaskEntity> taskList = tPmTaskService.getListByCriteriaQuery(cq, false);
            if (taskList.size() > 0) {
                tPmTask =  taskList.get(0);
            } else {
                TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
                tPmTask.setProject(proj);
            }
            if(StringUtils.isEmpty(tPmTask.getAttachmentCode())){
                tPmTask.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmTask.getAttachmentCode(), "");
                tPmTask.setAttachments(certificates);
            }
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        return new ModelAndView("com/kingtake/project/task/tPmTask-updateForResearchGroupInfo");
    }

    /**
     * 加载明细列表[任务节点管理]
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "tPmTaskNodeList")
    public JSONObject tPmTaskNodeList(TPmTaskEntity tPmTask, HttpServletRequest req) {
        JSONObject json = new JSONObject();
        //获取参数
        Object id0 = tPmTask.getId();
        //查询-任务节点管理
        String hql0 = "from TPmTaskNodeEntity where 1 = 1 AND t_P_ID = ? order by createDate asc";
        try {
            List<TPmTaskNodeEntity> tPmTaskNodeEntityList = systemService.findHql(hql0, id0);
            json.put("rows", tPmTaskNodeEntityList);
            json.put("total", tPmTaskNodeEntityList.size());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return json;
    }

    /**
     * 加载明细列表[任务节点管理]
     * 
     * @return
     */
    @RequestMapping(params = "tPmTaskNodeListForDepartment")
    public ModelAndView tPmTaskNodeListForDepartment(TPmTaskEntity tPmTask, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tPmTask.getId();
        //===================================================================================
        //查询-任务节点管理
        String hql0 = "from TPmTaskNodeEntity where 1 = 1 AND t_P_ID = ? order by createDate asc";
        try {
            List<TPmTaskNodeEntity> tPmTaskNodeEntityList = systemService.findHql(hql0, id0);
            req.setAttribute("tPmTaskNodeList", tPmTaskNodeEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        String type = req.getParameter("type");
        if (StringUtils.isNotEmpty(type)) {
            req.setAttribute("type", type);
        }
        return new ModelAndView("com/kingtake/project/task/tPmTaskNodeListForDepartment");
    }

    /**
     * 跳转到任务书相关的项目列表
     * 
     * @return
     */
    @RequestMapping(params = "goProjectListForTaskPlan")
    public ModelAndView goProjectListForTaskPlan(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTask.getId())) {
            tPmTask = tPmTaskService.getEntity(TPmTaskEntity.class, tPmTask.getId());
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        req.setAttribute("type", "1");
        return new ModelAndView("com/kingtake/project/manage/tPmProjectListForTask");
    }

    /**
     * 跳转到任务书相关的项目列表
     * 
     * @return
     */
    @RequestMapping(params = "goProjectListForTaskQuality")
    public ModelAndView goProjectListForTaskQuality(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTask.getId())) {
            tPmTask = tPmTaskService.getEntity(TPmTaskEntity.class, tPmTask.getId());
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        req.setAttribute("type", "2");
        return new ModelAndView("com/kingtake/project/manage/tPmProjectListForTask");
    }

    /**
     * 弹出任务书编辑框
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateTaskForDepartment")
    public ModelAndView goUpdateTaskForDepartment(TPmTaskEntity tPmTask, HttpServletRequest req) {
        if (tPmTask.getId() != null) {
            tPmTask = this.systemService.get(TPmTaskEntity.class, tPmTask.getId());
            req.setAttribute("tPmTaskPage", tPmTask);
        }
        return new ModelAndView("com/kingtake/project/task/tPmTask-updateForDepartment");
    }

    /**
     * 审核
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(TPmTaskNodeEntity taskNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(taskNode.getId())) {
                TSUser user = ResourceUtil.getSessionUserName();
                if (StringUtils.isNotEmpty(taskNode.getPlanCheckFlag())) {
                    taskNode.setPlanCheckUserid(user.getId());
                    taskNode.setPlanCheckUsername(user.getRealName());
                    taskNode.setPlanCheckTie(new Date());
                    if ("Y".equals(taskNode.getPlanCheckFlag())) {
                        message = "计划处审核任务节点成功！";
                    } else {
                        message = "计划处取消审核任务节点成功！";
                    }
                }
                if (StringUtils.isNotEmpty(taskNode.getQualityCheckFlag())) {
                    taskNode.setQualityCheckUserid(user.getId());
                    taskNode.setQualityCheckUsername(user.getRealName());
                    taskNode.setQualityCheckTime(new Date());
                    if ("Y".equals(taskNode.getQualityCheckFlag())) {
                        message = "质量办审核任务节点成功！";
                    } else {
                        message = "质量办取消审核任务节点成功！";
                    }
                }

                TPmTaskNodeEntity t = tPmTaskService.get(TPmTaskNodeEntity.class, taskNode.getId());
                MyBeanUtils.copyBeanNotNull2Bean(taskNode, t);
                tPmTaskService.updateEntitie(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "审核任务节点失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 完成情况弹出框
     * 
     * @return
     */
    @RequestMapping(params = "goFinishPage")
    public ModelAndView goFinishPage(TPmTaskNodeEntity tPmTaskNode, HttpServletRequest req) {
        tPmTaskNode = this.systemService.get(TPmTaskNodeEntity.class, tPmTaskNode.getId());
        if(StringUtils.isEmpty(tPmTaskNode.getAttachmentCode())){
            tPmTaskNode.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmTaskNode.getAttachmentCode(), "");
            tPmTaskNode.setAttachments(certificates);
        }
        req.setAttribute("tPmTaskNodePage", tPmTaskNode);
        TPmTaskEntity task = this.systemService.get(TPmTaskEntity.class, tPmTaskNode.getTpId());
        req.setAttribute("task", task);
        return new ModelAndView("com/kingtake/project/task/taskNode-finish");
    }

    /**
     * 保存完成信息
     * 
     * @return
     */
    @RequestMapping(params = "doFinish")
    @ResponseBody
    public AjaxJson doFinish(TPmTaskNodeEntity tPmTaskNode, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tPmTaskNode.getId())) {
            try {
                TPmTaskNodeEntity tPmTaskQuery = tPmTaskService.getEntity(TPmTaskNodeEntity.class, tPmTaskNode.getId());
                if (tPmTaskQuery != null) {
                    //未完成时，完成时间置空
                    if (tPmTaskNode.getFinishTime() == null) {
                        tPmTaskQuery.setFinishTime(null);
                    }
                    MyBeanUtils.copyBeanNotNull2Bean(tPmTaskNode, tPmTaskQuery);
                    /*
                     * if (SrmipConstants.YES.equals(tPmTaskQuery.getFinishFlag())) { tPmTaskQuery.setFinishTime(new
                     * Date()); } else { tPmTaskQuery.setFinishTime(null); }
                     */
                    this.systemService.updateEntitie(tPmTaskQuery);
                }
                j.setObj(tPmTaskNode);
                j.setMsg("保存完成信息成功！");
            } catch (Exception e) {
                e.printStackTrace();
                j.setSuccess(false);
                j.setMsg("保存完成信息成功！，原因：" + e.getMessage());
            }
        }
        return j;
    }

    /**
     * 
     * 跳转到审批界面
     * 
     * @param tPmTask
     * @param request
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TPmTaskEntity tPmTask, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(tPmTask.getId())) {
            tPmTask = this.systemService.get(TPmTaskEntity.class, tPmTask.getId());
            request.setAttribute("id", tPmTask.getId());
            request.setAttribute("sptype", "rws");
            if (ApprovalConstant.APPRSTATUS_FINISH.equals(tPmTask.getAuditStatus())) {
                request.setAttribute("send", "false");
            }
        }
        return new ModelAndView("com/kingtake/project/task/taskAudit-send");
    }

    /**
     * 进账合同审批处理tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/task/taskApprList-receiveTab");
    }

    /**
     * 任务书审核列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "taskAuditDepartment")
    public ModelAndView taskAuditDepartment(HttpServletRequest request) {
        //处理审批
        //0：未处理；1：已处理
        String type = request.getParameter("type");
        if (StringUtil.isNotEmpty(type)) {
            if ("0".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.NO);
            } else if ("1".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.YES);
            }
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_RECEIVE);
            request.setAttribute("YES", SrmipConstants.YES);
            request.setAttribute("NO", SrmipConstants.NO);
            request.setAttribute("sptype", "rws");
            return new ModelAndView("com/kingtake/project/task/taskApprList-receive");
        }
        return new ModelAndView("common/404.jsp");

    }

    /**
     * easyui AJAX请求数据 审核列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "auditList")
    public void auditList(TPmTaskEntity taskEntity, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批

            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();//获取参数

            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            StringBuffer resultSql = new StringBuffer();
            resultSql
                    .append("SELECT APPR.ID AS APPR_ID, APPR.T_P_ID AS PROJECTID, APPR.TASK_TITLE taskTitle, p.PROJECT_NAME, APPR.AUDIT_STATUS auditStatus, \n");
            resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
            resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
            resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

            String temp = "FROM t_pm_task APPR, t_pm_project p ,T_PM_APPR_SEND_LOGS SEND, "
                    + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                    + "WHERE APPR.ID = SEND.APPR_ID AND appr.t_p_id=p.id and SEND.ID = RECEIVE.SEND_ID "
                    + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                    + "AND RECEIVE.RECEIVE_USERID = ?  ";

            if (SrmipConstants.YES.equals(operateStatus)) {
                //已处理
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                //未处理：需要是有效的
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                        + SrmipConstants.YES;
            }

            String projectname = request.getParameter("project.name");
            if (StringUtil.isNotEmpty(projectname)) {
                temp += " AND p.PROJECT_NAME LIKE '%" + projectname + "%'";
            }

            String taskTitle = request.getParameter("taskTitle");
            if (StringUtil.isNotEmpty(taskTitle)) {
                temp += " AND APPR.task_title LIKE '%" + taskTitle + "%'";
            }

            temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
            String[] params = new String[] { user.getId() };

            List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                    dataGrid.getPage(), dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        }

        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 跳转到任务节点审批编辑界面
     * 
     * @return
     */
    @RequestMapping(params = "goTaskNodeApprEdit")
    public ModelAndView goTaskNodeApprEdit(HttpServletRequest request, HttpServletResponse response) {
        String taskNodeId = request.getParameter("id");
        if (StringUtils.isNotEmpty(taskNodeId)) {
            TPmTaskNodeEntity taskNode = this.systemService.get(TPmTaskNodeEntity.class, taskNodeId);
            if(StringUtils.isEmpty(taskNode.getAttachmentCode())){
                taskNode.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(taskNode.getAttachmentCode(), "");
                taskNode.setAttachments(certificates);
            }
            request.setAttribute("tPmTaskNodePage", taskNode);
            TPmTaskEntity task = this.systemService.get(TPmTaskEntity.class, taskNode.getTpId());
            request.setAttribute("task", task);
        }
        return new ModelAndView("com/kingtake/project/task/taskNode-appr");
    }

    /**
     * 保存任务节点审批编辑界面
     * 
     * @return
     */
    @RequestMapping(params = "saveTaskNodeAppr")
    @ResponseBody
    public AjaxJson saveTaskNodeAppr(TPmTaskNodeEntity taskNode, HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        message = "保存任务节点完成情况成功！";
        try {
            if (StringUtils.isNotEmpty(taskNode.getId())) {
                TPmTaskNodeEntity t = tPmTaskService.get(TPmTaskNodeEntity.class, taskNode.getId());
                MyBeanUtils.copyBeanNotNull2Bean(taskNode, t);
                t.setOperationStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                this.systemService.updateEntitie(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "保存任务节点完成情况失败！";
            json.setSuccess(false);
        }
        json.setMsg(message);
        json.setObj(taskNode);
        return json;
    }

    /**
     * 更新之前进行判断：是否可以进行更新
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateFlag")
    @ResponseBody
    public AjaxJson updateFlag(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmTaskNodeEntity taskNodeCheck = systemService.get(TPmTaskNodeEntity.class, id);
            if (taskNodeCheck != null) {
                String apprStatus = taskNodeCheck.getOperationStatus();
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(apprStatus)) {
                    j.setSuccess(false);
                    j.setMsg("该任务节点验收审批流程已完成，任务节点验收信息不能再进行编辑！");
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                    j.setSuccess(false);
                    j.setMsg("该任务节点验收报已发送审批，任务节点验收信息不能再进行编辑！");
                }
            }
        }
        return j;
    }

    /**
     * 任务节点审批列表
     * 
     * @param taskNode
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "taskNodeApprDatagrid")
    public void datagrid(TPmTaskNodeEntity taskNode, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        try {
            String datagridType = request.getParameter("datagridType");
            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//发起审批
                String projectId = request.getParameter("projectId");
                String taskTitle = request.getParameter("TASK.TITLE");
                String taskNodeContent = request.getParameter("TASK.CONTENT");
                if (StringUtil.isNotEmpty(projectId)) {
                    StringBuffer resultSql = new StringBuffer();
                    resultSql.append("SELECT NODE.ID,TASK.TASK_TITLE,NODE.PLAN_TIME,NODE.END_TIME,NODE.FINISH_TIME,");
                    resultSql
                            .append("NODE.TASK_CONTENT,NODE.FINISHFLAG,NVL(NODE.OPERATION_STATUS, 'u') AS CHECK_STATUS ");
                    StringBuffer temp = new StringBuffer();
                    temp.append("FROM T_PM_TASK_NODE NODE ");
                    temp.append("JOIN T_PM_TASK  TASK ON NODE.T_P_ID = TASK.ID WHERE TASK.T_P_ID = ? and TASK.AUDIT_STATUS=?");
                    if(StringUtils.isNotEmpty(taskTitle)){
                        temp.append(" and TASK.TASK_TITLE like '%"+taskTitle+"%'");
                    }
                    if(StringUtils.isNotEmpty(taskNodeContent)){
                        temp.append(" and NODE.TASK_CONTENT like '%"+taskNodeContent+"%'");
                    }
                    temp.append(" ORDER BY NODE.CREATE_DATE");
                    List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp.toString())
                            .toString(), new Object[] { projectId, ApprovalConstant.APPRSTATUS_FINISH });
                    Long count = systemService.getCountForJdbcParam(countSql.append(temp.toString()).toString(),
                            new String[] { projectId, ApprovalConstant.APPRSTATUS_FINISH });
                    dataGrid.setResults(result);
                    dataGrid.setTotal(count.intValue());
                }
            } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批
                String operateStatus = request.getParameter("operateStatus");

                TSUser user = ResourceUtil.getSessionUserName();
                StringBuffer resultSql = new StringBuffer();
                resultSql
                        .append("SELECT APPR.ID AS APPR_ID, APPR.CONTRACT_NAME, APPR.CONTRACT_AMOUNT, APPR.NODE_AMOUNT, \n");
                resultSql
                        .append("APPR.NODE_TIME, APPR.CHECK_TIME, APPR.ORGANIZATION_UNITNAME, APPR.OPERATION_STATUS, \n");
                resultSql
                        .append("APPR.CONTRACT_NODE_ID, APPR.PROJECT_NAME, NODE.NODE_NAME, NODE.IN_OUT_CONTRACTID, \n");
                resultSql.append("CONTRACT.T_P_ID AS PROJECTID, \n");
                resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
                resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
                resultSql
                        .append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

                String temp = "FROM T_PM_CONTRACT_NODE NODE, T_PM_CONTRACT_NODE_CHECK APPR, T_PM_INCOME_CONTRACT_APPR CONTRACT, "
                        + "T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                        + "WHERE NODE.ID = APPR.CONTRACT_NODE_ID AND NODE.IN_OUT_CONTRACTID = CONTRACT.ID "
                        + "AND APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                        + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                        + "AND RECEIVE.RECEIVE_USERID = ? AND RECEIVE.RECEIVE_DEPARTID = ? ";

                if (SrmipConstants.YES.equals(operateStatus)) {
                    //已处理
                    temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
                } else if (SrmipConstants.NO.equals(operateStatus)) {
                    //未处理：需要是有效的
                    temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                            + SrmipConstants.YES;
                }

                String projectname = request.getParameter("project.name");
                if (StringUtil.isNotEmpty(projectname)) {
                    temp += " AND APPR.PROJECT_NAME LIKE '%" + projectname + "%'";
                }

                String contractName = request.getParameter("contract.name");
                if (StringUtil.isNotEmpty(contractName)) {
                    temp += " AND APPR.CONTRACT_NAME LIKE '%" + contractName + "%'";
                }

                String nodeName = request.getParameter("node.name");
                if (StringUtil.isNotEmpty(nodeName)) {
                    temp += " AND NODE.NODE_NAME LIKE '%" + nodeName + "%'";
                }

                temp += " ORDER BY SEND.OPERATE_DATE, RECEIVE.OPERATE_TIME";
                //暂时只根据登录名找
                String[] params = new String[] { user.getId(), user.getCurrentDepart().getId() };

                List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                        dataGrid.getPage(), dataGrid.getRows(), params);
                Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

                dataGrid.setResults(result);
                dataGrid.setTotal(count.intValue());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 修改流程审批状态
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateOperateStatus")
    @ResponseBody
    public AjaxJson updateOperateStatus(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmTaskNodeEntity t = systemService.get(TPmTaskNodeEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_FINISH.equals(t.getOperationStatus())) {
                //操作：取消完成流程(流程状态为已完成才能进行该操作)
                t.setFinishTime(null);
                t.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);//只有状态是已发送的时候才有完成按钮
                systemService.saveOrUpdate(t);
                CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                cq.eq("apprId", t.getId());
                cq.eq("operateStatus", SrmipConstants.NO);
                cq.eq("validFlag", SrmipConstants.NO);
                cq.add();
                List<TPmApprReceiveLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
                if (list.size() > 0) {
                    for (TPmApprReceiveLogsEntity r : list) {
                        r.setValidFlag(SrmipConstants.YES);//取消完成操作时将未处理的接收记录置为有效
                        systemService.updateEntitie(r);
                    }
                }
                j.setSuccess(true);
                j.setMsg("成功将任务节点考核审批流程状态设置为未完成");
            } else if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperationStatus())) {
                //操作：完成流程操作(流程状态为已完成才能进行该操作)
                CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                cq.eq("apprId", id);
                cq.eq("operateStatus", SrmipConstants.NO);
                cq.eq("validFlag", SrmipConstants.YES);
                cq.add();
                List<TPmApprReceiveLogsEntity> tPmFundsReceiveLogs = systemService.getListByCriteriaQuery(cq, false);
                if (tPmFundsReceiveLogs != null && tPmFundsReceiveLogs.size() > 0) {
                    j.setSuccess(false);
                    j.setMsg("该任务节点考核还有审批意见未处理，确定完成审批流程吗？");
                    j.setObj("1");//失败情况一
                } else {
                    TSUser user = ResourceUtil.getSessionUserName();
                    //操作：完成流程
                    t.setOperationStatus(ApprovalConstant.APPRSTATUS_FINISH);
                    t.setFinishTime(new Date());
                    t.setFinishUserid(user.getId());
                    t.setFinishUsername(user.getRealName());
                    systemService.saveOrUpdate(t);
                    j.setSuccess(true);
                    j.setMsg("成功将任务节点考核审批流程状态设置为完成");
                }
            } else {
                //其他人操作改变了finishFlag属性，需要刷新重新操作
                j.setSuccess(false);
                j.setMsg("任务节点考核审批流程状态设置失败，请刷新列表再进行操作");
                j.setObj("2");//失败情况二
            }
        } else {
            j.setSuccess(false);
            j.setMsg("任务节点考核审批流程状态设置失败");
        }
        return j;
    }

    /**
     * 进出账合同编辑之前共同的判断方法：是否有已编辑的 有：合同基本信息只能查看
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateOperateStatus2")
    @ResponseBody
    public AjaxJson updateOperateStatus2(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmTaskNodeEntity t = systemService.get(TPmTaskNodeEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperationStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setOperationStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                systemService.saveOrUpdate(t);
                CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                cq.eq("apprId", t.getId());
                cq.eq("operateStatus", SrmipConstants.NO);
                cq.eq("validFlag", SrmipConstants.YES);
                cq.add();
                List<TPmApprReceiveLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
                if (list.size() > 0) {
                    for (TPmApprReceiveLogsEntity r : list) {
                        r.setValidFlag(SrmipConstants.NO);//完成操作时将未处理的接收记录置为无效
                        systemService.updateEntitie(r);
                    }
                }
                j.setSuccess(true);
                j.setMsg("成功将任务节点考核流程状态设置为完成");
            } else {
                j.setSuccess(false);
                message = "任务节点考核流程状态设置失败，清刷新列表再进行操作";
            }
        } else {
            j.setSuccess(false);
            message = "任务节点考核流程状态设置失败";
        }

        return j;
    }
    
    /**
     * 任务管理编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goTaskNodeUpdate")
    public ModelAndView goTaskNodeUpdate(TPmTaskNodeEntity tPmTaskNode, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmTaskNode.getId())) {
            tPmTaskNode = tPmTaskService.getEntity(TPmTaskNodeEntity.class, tPmTaskNode.getId());
        }
        req.setAttribute("tPmTaskNodePage", tPmTaskNode);
        return new ModelAndView("com/kingtake/project/task/tPmTaskNode-update");
    }

    /**
     * 新增/更新任务节点管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doTaskNodeUpdate")
    @ResponseBody
    public AjaxJson doTaskNodeUpdate(TPmTaskNodeEntity tPmTaskNode,  HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if(StringUtils.isEmpty(tPmTaskNode.getId())){
                this.systemService.save(tPmTaskNode);
            }else{
                TPmTaskNodeEntity t = tPmTaskService.get(TPmTaskNodeEntity.class, tPmTaskNode.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmTaskNode, t);
                this.systemService.updateEntitie(t);
            }
            message = "保存任务节点成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "保存任务节点失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 删除任务管理
     * 
     * @return
     */
    @RequestMapping(params = "doDelTaskNode")
    @ResponseBody
    public AjaxJson doDelTaskNode(TPmTaskNodeEntity tPmTaskNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmTaskNode = systemService.getEntity(TPmTaskNodeEntity.class, tPmTaskNode.getId());
        String message = "任务节点删除成功";
        try {
            systemService.delete(tPmTaskNode);
        } catch (Exception e) {
            e.printStackTrace();
            message = "任务节点删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "checkIsAppr")
    @ResponseBody
    public JSONObject checkIsAppr(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("isAppr", "0");
        String apprId = request.getParameter("apprId");
        List<Map<String, Object>> apprList = this.getAppr(apprId);//查询课题组长
        if (apprList.size() > 0) {
            Map<String, Object> map = apprList.get(0);
            if (map.get("APPR_ID") != null) {
                json.put("isAppr", "1");
                json.put("receiveId", map.get("ID"));
            }
        }
        return json;
    }

    /**
     * 查询课题组长审批
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//获取参数
        StringBuffer resultSql = new StringBuffer();
        resultSql
                .append("SELECT APPR.ID AS APPR_ID, APPR.T_P_ID AS PROJECTID, APPR.TASK_TITLE taskTitle, p.PROJECT_NAME, APPR.AUDIT_STATUS auditStatus, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM t_pm_task APPR, t_pm_project p ,T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND appr.t_p_id=p.id and SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID AND EXT.ID='0901' \n"
                + "AND RECEIVE.RECEIVE_USERID = ?  ";

        //未处理：需要是有效的
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };

        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
}
