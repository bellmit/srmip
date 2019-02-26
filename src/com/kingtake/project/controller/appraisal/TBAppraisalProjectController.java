package com.kingtake.project.controller.appraisal;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.TreeModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApprovalEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalProjectEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.reportmaterial.TBAppraisalReportMaterialEntity;
import com.kingtake.project.service.appraisal.TBAppraisalProjectServiceI;

/**
 * @Title: Controller
 * @Description: 成果鉴定计划
 * @author onlineGenerator
 * @date 2015-09-07 15:11:32
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalProjectController")
public class TBAppraisalProjectController extends BaseController {
    private static final Logger logger = Logger.getLogger(TBAppraisalProjectController.class);

    @Autowired
    private TBAppraisalProjectServiceI tBAppraisalProjectService;
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
     * 列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisalProject")
    public ModelAndView tBAppraisalProject(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
        request.setAttribute("projectName", project.getProjectName());
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalProjectList");
    }

    /**
     * 列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisalProjectProcess")
    public ModelAndView tBAppraisalProjectProcess(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
        request.setAttribute("projectName", project.getProjectName());
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalProjectListProcess");
    }

    @RequestMapping(params = "goAppraisalTab")
    public ModelAndView goAppraisalTab(HttpServletRequest request, String id) {
        if (StringUtil.isNotEmpty(id)) {
            TBAppraisalProjectEntity tBAppraisalProject = systemService.get(TBAppraisalProjectEntity.class, id);

            request.setAttribute("tBAppraisalProject", tBAppraisalProject);
        }
        return new ModelAndView("com/kingtake/project/appraisal/appraisalTab");
    }

    @RequestMapping(params = "loadApprTree")
    @ResponseBody
    public JSONArray loadApprTree(HttpServletRequest req, String apprid) {
        List<TreeModel> list = new ArrayList<TreeModel>();
        //鉴定计划
        TreeModel appraisalTree = new TreeModel();
        appraisalTree.setId("appraisalProject");
        appraisalTree.setText("鉴定计划");
        Map<String, Object> appraisalMap = getAppraisalProjectStatus(apprid);
        TBAppraisalProjectEntity appraisalProject = this.systemService.get(TBAppraisalProjectEntity.class, apprid);
        String state = appraisalProject.getState();
        String apprUrl = "tBAppraisalProjectController.do?goUpdate&id=" + apprid;
        appraisalMap.put("url", apprUrl);
        appraisalTree.setAttributes(appraisalMap);

        //鉴定申请审批
        TreeModel approvalTree = new TreeModel();
        approvalTree.setId("approval");
        approvalTree.setText("鉴定申请审批");
        Map<String, Object> apprLogsMap = getApprovalOperateStatus(apprid);
        approvalTree.setAttributes(apprLogsMap);
        //鉴定申请
        TreeModel applyTree = new TreeModel();
        applyTree.setId("apply");
        applyTree.setText("鉴定申请");
        Map<String, Object> applyMap = getApplyOperateStatus(apprid);
        applyTree.setAttributes(applyMap);

        //鉴定会
        TreeModel meetingTree = new TreeModel();
        meetingTree.setId("meeting");
        meetingTree.setText("鉴定会");
        Map<String, Object> meetingMap = getMeetingStatus(apprid);
        meetingTree.setAttributes(meetingMap);
        String meetingStatus = (String) meetingMap.get("apprStatus");
        //上报材料
        TreeModel appearTree = new TreeModel();
        appearTree.setId("appear");
        appearTree.setText("上报材料");
        Map<String, Object> appearMap = getReportStatus(apprid);
        appearTree.setAttributes(appearMap);
        if (ApprovalConstant.APPRAISAL_PROJECT_UNSEND.equals(state)) {//鉴定计划已发送
            appraisalTree.setIconCls("icon-cancel");
            list.add(appraisalTree);
        } else if (ApprovalConstant.APPRAISAL_PROJECT_SEND.equals(state)) {//鉴定计划已发送
            appraisalTree.setIconCls("icon-ok");
            list.add(appraisalTree);
            approvalTree.setIconCls("icon-cancel");
            list.add(approvalTree);
        } else if (ApprovalConstant.APPRAISAL_PROJECT_UNSEND.equals(state)) {
            list.add(appraisalTree);
            approvalTree.setIconCls("icon-cancel");
        } else if (ApprovalConstant.APPRAISAL_PROJECT_APPROVAL_FINISH.equals(state)) {
            appraisalTree.setIconCls("icon-ok");
            list.add(appraisalTree);
            approvalTree.setIconCls("icon-ok");
            list.add(approvalTree);
            applyTree.setIconCls("icon-cancel");
            list.add(applyTree);
        } else if (ApprovalConstant.APPRAISAL_PROJECT_APPLY_FINISH.equals(state)) {
            appraisalTree.setIconCls("icon-ok");
            list.add(appraisalTree);
            approvalTree.setIconCls("icon-ok");
            list.add(approvalTree);
            applyTree.setIconCls("icon-ok");
            list.add(applyTree);
            if (ApprovalConstant.APPLYSTATUS_FINISH.equals(meetingStatus)) {
                meetingTree.setIconCls("icon-ok");
            } else {
                meetingTree.setIconCls("icon-cancel");
            }
            list.add(meetingTree);
            appearTree.setIconCls("icon-cancel");
            list.add(appearTree);
        } else if (ApprovalConstant.APPRAISAL_PROJECT_MATERIA_SEND.equals(state)) {
            appraisalTree.setIconCls("icon-ok");
            list.add(appraisalTree);
            approvalTree.setIconCls("icon-ok");
            list.add(approvalTree);
            applyTree.setIconCls("icon-ok");
            list.add(applyTree);
            if (ApprovalConstant.APPLYSTATUS_FINISH.equals(meetingStatus)) {
                meetingTree.setIconCls("icon-ok");
            } else {
                meetingTree.setIconCls("icon-cancel");
            }
            list.add(meetingTree);
            appearTree.setIconCls("icon-cancel");
            list.add(appearTree);
        } else if (ApprovalConstant.APPRAISAL_PROJECT_MATERIA_FINISH.equals(state)) {
            appraisalTree.setIconCls("icon-ok");
            list.add(appraisalTree);
            approvalTree.setIconCls("icon-ok");
            list.add(approvalTree);
            applyTree.setIconCls("icon-ok");
            list.add(applyTree);
            if (ApprovalConstant.APPLYSTATUS_FINISH.equals(meetingStatus)) {
                meetingTree.setIconCls("icon-ok");
            } else {
                meetingTree.setIconCls("icon-cancel");
            }
            list.add(meetingTree);
            appearTree.setIconCls("icon-ok");
            list.add(appearTree);
        } else if (ApprovalConstant.APPRAISAL_PROJECT_UNFINISH.equals(state)) {

        } else if (ApprovalConstant.APPRAISAL_PROJECT_FINISHED.equals(state)) {
            appraisalTree.setIconCls("icon-ok");
            list.add(appraisalTree);
            approvalTree.setIconCls("icon-ok");
            list.add(approvalTree);
            applyTree.setIconCls("icon-ok");
            list.add(applyTree);
            meetingTree.setIconCls("icon-ok");
            if (ApprovalConstant.APPLYSTATUS_FINISH.equals(meetingStatus)) {
                meetingTree.setIconCls("icon-ok");
            } else {
                meetingTree.setIconCls("icon-cancel");
            }
            list.add(meetingTree);
            appearTree.setIconCls("icon-ok");
            list.add(appearTree);
        }
        JSONArray array = new JSONArray();
        array = (JSONArray) JSONArray.toJSON(list);
        return array;
    }

    public Map<String, Object> getAppraisalProjectStatus(String tBAppraisalProjectId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(tBAppraisalProjectId)) {
            TBAppraisalProjectEntity appraisalProject = systemService.get(TBAppraisalProjectEntity.class,
                    tBAppraisalProjectId);
            String apprMsg = null;
            String apprStatus = appraisalProject.getState();
            String apprId = tBAppraisalProjectId;
            /*
             * if (ApprovalConstant.APPRSTATUS_FINISH.equals(apprStatus)) { //已完成 apprMsg = "鉴定计划已完成,不能编辑！"; } else if
             * (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) { //已发送 apprMsg = "鉴定计划已提交,不能编辑！"; } else if
             * (ApprovalConstant.APPRSTATUS_REBUT.equals(apprStatus)) { //未完成 apprMsg = "鉴定计划未完成，查看未完成情况说明！"; }
             */
            map.put("apprStatus", apprStatus);
            map.put("apprId", apprId);
            //map.put("apprMsg", apprMsg);
        }
        return map;
    }

    /**
     * 鉴定申请审批提交之后根据状态刷新表单
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "getApprovalOperateStatus")
    @ResponseBody
    public AjaxJson getApprovalOperateStatus(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String tBAppraisalProjectId = req.getParameter("tBAppraisalProjectId");
        j.setAttributes(getApprovalOperateStatus(tBAppraisalProjectId));
        return j;
    }

    public Map<String, Object> getApprovalOperateStatus(String tBAppraisalProjectId) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(tBAppraisalProjectId)) {
            List<TBAppraisalApprovalEntity> approvalList = systemService.findByProperty(
                    TBAppraisalApprovalEntity.class, "appraisalProject.id", tBAppraisalProjectId);
            String apprMsg = null;
            String apprStatus = null;
            String apprId = null;
            String url = "tPmApprLogsController.do?goApprTab&appraisalProjectId=" + tBAppraisalProjectId + "&apprType="
                    + ApprovalConstant.APPR_TYPE_APPRAISAL;
            if (approvalList != null && approvalList.size() > 0) {
                apprStatus = approvalList.get(0).getAuditStatus();
                apprId = approvalList.get(0).getId();
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(apprStatus)) {
                    //审批状态为已完成（取消完成按钮/不可编辑/tip）
                    url += "&load=detail&tip=true&send=false&apprId=" + apprId;
                    apprMsg = "该成果鉴定审批流程已完成，审批信息不能编辑且无法发送审批！";
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                    //审批状态为已发送（完成流程按钮/不可编辑/tip）
                    url += "&load=detail&tip=true&send=true&apprId=" + apprId;
                    apprMsg = "该成果鉴定审批已发送，审批信息不能编辑！";
                } else if (ApprovalConstant.APPRSTATUS_REBUT.equals(apprStatus)) {
                    //审批状态为被驳回（确定按钮/可编辑/tip）
                    url += "&tip=true&send=true&idFlag=false&apprId=" + apprId;
                    apprMsg = "流程被驳回，请修改审批信息！";
                } else if (ApprovalConstant.APPRSTATUS_UNSEND.equals(apprStatus)) {
                    url += "&tip=true&apprId=" + apprId;
                    apprMsg = "该成果鉴定审批未发送，审批信息可以编辑！";
                }
            } else {
                apprStatus = ApprovalConstant.APPRSTATUS_UNAPPR;
            }
            attributes.put("apprStatus", apprStatus);
            attributes.put("apprId", apprId);
            attributes.put("apprMsg", apprMsg);
            attributes.put("url", url);
        }
        return attributes;

    }

    public Map<String, Object> getApplyOperateStatus(String tBAppraisalProjectId) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(tBAppraisalProjectId)) {
            String apprStatus = null;
            String apprId = null;
            String apprMsg = null;
            String url = "tBAppraisalApplyController.do?goUpdate&appraisalProject.id=" + tBAppraisalProjectId;
            // 查询是否已生成鉴定申请表
            List<TBAppraisalApplyEntity> list = systemService.findByProperty(TBAppraisalApplyEntity.class,
                    "appraisalProject.id", tBAppraisalProjectId);
            if (list != null && list.size() > 0) {
                apprStatus = list.get(0).getAuditStatus();
                //                bpmStatus = list.get(0).getBpmStatus();
                apprId = list.get(0).getId();
                if (ApprovalConstant.APPLYSTATUS_SEND.equals(apprStatus)) {//未审核
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "该成果鉴定申请未审核，请通知审核人审核！";
                } else if (ApprovalConstant.APPLYSTATUS_AUDITED.equals(apprStatus)) {//已审核
                    url += "&load=detail&tip=true&id=" + apprId;
                    apprMsg = "该成果鉴定申请已审核，申请表信息不能编辑！";
                } else if (ApprovalConstant.APPLYSTATUS_APPROVAL_VIEW.equals(apprStatus)) {//已提交批复意见
                    url += "&load=detail&tip=true&id=" + apprId;
                    apprMsg = "该成果鉴定申请已审核完成，申请表信息不能编辑！";
                } else if (ApprovalConstant.APPLYSTATUS_FINISH.equals(apprStatus)) {//流程完成
                    url += "&load=detail&tip=true&id=" + apprId;
                    apprMsg = "该成果鉴定申请已审核完成，申请表信息不能编辑！";
                }
            } else {
                apprStatus = ApprovalConstant.APPRSTATUS_UNAPPR;
            }
            attributes.put("apprStatus", apprStatus);
            attributes.put("apprId", apprId);
            attributes.put("apprMsg", apprMsg);
            attributes.put("url", url);
        }
        return attributes;

    }

    public Map<String, Object> getMeetingStatus(String tBAppraisalProjectId) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(tBAppraisalProjectId)) {
            String apprStatus = null;
            String apprId = null;
            String apprMsg = null;
            String url = "tBAppraisalMeetingController.do?goUpdate";
            List<TBAppraisalMeetingEntity> list = systemService.findByProperty(TBAppraisalMeetingEntity.class,
                    "apprProjectId", tBAppraisalProjectId);
            if (list.size() > 0) {
                apprStatus = list.get(0).getMeetingStatus();
                apprId = list.get(0).getId();
                if (ApprovalConstant.APPRSTATUS_UNSEND.equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "鉴定会未提交，请填写相关信息后提交！";
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "鉴定会已提交，请通知审核人审核！";
                } else if (ApprovalConstant.APPLYSTATUS_FINISH.equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId + "&load=detail";
                    apprMsg = "鉴定会已完成，鉴定会信息表不能编辑！";
                }
            } else {
                url += "&apprProjectId=" + tBAppraisalProjectId;
                apprStatus = ApprovalConstant.APPRSTATUS_UNAPPR;
            }
            attributes.put("apprStatus", apprStatus);
            attributes.put("apprId", apprId);
            attributes.put("apprMsg", apprMsg);
            attributes.put("url", url);
        }
        return attributes;

    }

    public Map<String, Object> getReportStatus(String tBAppraisalProjectId) {
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(tBAppraisalProjectId)) {
            String apprStatus = null;
            String apprId = null;
            String apprMsg = null;
            String url = "tBAppraisalReportMaterialController.do?goEdit";
            List<TBAppraisalReportMaterialEntity> list = systemService.findByProperty(
                    TBAppraisalReportMaterialEntity.class, "appraisalProjectId", tBAppraisalProjectId);
            if (list.size() > 0) {
                TBAppraisalReportMaterialEntity tmp = list.get(0);
                apprStatus = tmp.getCheckFlag();
                apprId = tmp.getId();
                if (ApprovalConstant.APPRSTATUS_UNSEND.equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "鉴定材料未提交审核，请填写相关信息后提交！";
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "鉴定材料已提交审核，请通知审核人[" + tmp.getCheckUsername() + "]审核！";
                } else if ("2".equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "鉴定材料已审核通过，请完善证书信息！";
                } else if ("3".equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "证书信息已完善，请通知审核人[" + tmp.getCheckUsername() + "]审核！";
                } else if ("4".equals(apprStatus)) {
                    url += "&tip=true&id=" + apprId;
                    apprMsg = "上报材料环节已完成，信息不可编辑！";
                } else {
                    url += "&id=" + apprId;
                }
            } else {
                url += "&appraisalProjectId=" + tBAppraisalProjectId;
                apprStatus = ApprovalConstant.APPRSTATUS_UNAPPR;
            }
            attributes.put("apprStatus", apprStatus);
            attributes.put("apprId", apprId);
            attributes.put("apprMsg", apprMsg);
            attributes.put("url", url);
        }
        return attributes;

    }

    /**
     * 列表请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalProjectEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalProject,
                request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("planYear", SortDirection.desc);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBAppraisalProjectService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBAppraisalProject = systemService.getEntity(TBAppraisalProjectEntity.class, tBAppraisalProject.getId());
        message = "鉴定计划删除成功";
        try {
            tBAppraisalProjectService.delete(tBAppraisalProject);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定计划删除失败";
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
    public AjaxJson doAdd(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "鉴定计划添加成功";
        try {
            // 填报单位为当前登录人单位
            TSUser user = ResourceUtil.getSessionUserName();
            TSDepart depart = user.getCurrentDepart();
            tBAppraisalProject.setPlanUnitid(depart.getId());
            tBAppraisalProject.setPlanUnitname(depart.getDepartname());
            tBAppraisalProject.setPlanDate(new Date());
            // 状态为"未发送"
            tBAppraisalProject.setState(ApprovalConstant.APPRSTATUS_UNSEND);
            tBAppraisalProjectService.save(tBAppraisalProject);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定计划添加失败";
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
    public AjaxJson doUpdate(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "鉴定计划更新成功";
        TBAppraisalProjectEntity t = tBAppraisalProjectService.get(TBAppraisalProjectEntity.class,
                tBAppraisalProject.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalProject, t);
            tBAppraisalProjectService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定计划更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest req) {
        // 获得项目信息
        TPmProjectEntity project = systemService.get(TPmProjectEntity.class, tBAppraisalProject.getProjectId());
        // 将项目信息填写到成果鉴定计划表中
        tBAppraisalProject.setProjectName(project.getProjectName());
        tBAppraisalProject.setProjectBeginDate(project.getBeginDate());
        tBAppraisalProject.setProjectEndDate(project.getEndDate());
        tBAppraisalProject.setProjectDeveloperDepartid(project.getDevDepart() == null ? null : project.getDevDepart()
                .getId());
        tBAppraisalProject.setProjectDeveloperDepartname(project.getDevDepart() == null ? null : project.getDevDepart()
                .getDepartname());
        tBAppraisalProject.setProjectSourceUnit(project.getSourceUnit());
        tBAppraisalProject.setProjectAccordingNum(project.getAccordingNum());
        tBAppraisalProject.setProjectManagerId(project.getProjectManagerId());
        tBAppraisalProject.setProjectManagerName(project.getProjectManager());
        tBAppraisalProject.setProjectManagerPhone(project.getManagerPhone());
        tBAppraisalProject.setTotalAmount(project.getAllFee() == null ? 0 : Double.parseDouble(project.getAllFee()
                .toString()));

        req.setAttribute("tBAppraisalProject", tBAppraisalProject);
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalProject");
    }

    /**
     * 编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalProject.getId())) {
            tBAppraisalProject = tBAppraisalProjectService.getEntity(TBAppraisalProjectEntity.class,
                    tBAppraisalProject.getId());
            req.setAttribute("tBAppraisalProject", tBAppraisalProject);
        }
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalProject");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalProjectEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalProject,
                request.getParameterMap());
        List<TBAppraisalProjectEntity> tBAppraisalProjects = this.tBAppraisalProjectService.getListByCriteriaQuery(cq,
                false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "成果鉴定计划");
        modelMap.put(NormalExcelConstants.CLASS, TBAppraisalProjectEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("成果鉴定计划列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBAppraisalProjects);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 将鉴定计划提交给机关
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        tBAppraisalProject = systemService.getEntity(TBAppraisalProjectEntity.class, tBAppraisalProject.getId());
        message = "鉴定计划提交成功";
        try {
            tBAppraisalProject.setState(ApprovalConstant.APPRSTATUS_SEND);
            systemService.updateEntitie(tBAppraisalProject);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定计划提交失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 完成鉴定计划
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "finish")
    @ResponseBody
    public AjaxJson finish(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        tBAppraisalProject = systemService.getEntity(TBAppraisalProjectEntity.class, tBAppraisalProject.getId());
        message = "鉴定计划已完成";
        try {
            tBAppraisalProject.setState(ApprovalConstant.APPRSTATUS_FINISH);
            systemService.updateEntitie(tBAppraisalProject);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定计划已完成";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "appraisalProjectForDepartment")
    public ModelAndView tBAppraisalProjectForDepartment(HttpServletRequest request) {
        request.setAttribute("projectId", request.getParameter("projectId"));
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalProjectListForDepartment");
    }

    /**
     * 汇总请求表格数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridSummary")
    public void datagridSummary(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalProjectEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalProject,
                request.getParameterMap());
        try {
            //自定义追加查询条件
            cq.notEq("state", ApprovalConstant.APPRSTATUS_UNSEND);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBAppraisalProjectService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "getSelectYear")
    @ResponseBody
    public JSONArray getSelectYear(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String sql = "select t.plan_year from t_b_appraisal_project t "
                + " where t.plan_year is not null group by t.plan_year " + " order by t.plan_year desc";
        List<Map<String, Object>> resultMap = this.systemService.findForJdbc(sql, null);
        JSONArray array = new JSONArray();
        for (Map<String, Object> map : resultMap) {
            array.add(map.get("plan_year"));
        }
        return array;
    }

    /**
     * 汇总导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "summaryExport")
    public String summaryExport(TBAppraisalProjectEntity tBAppraisalProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        String year = request.getParameter("year");
        String name = "";
        try {
            if (StringUtil.isNotEmpty(request.getParameter("name"))) {
                name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //        List<TBAppraisalProjectEntity> tBAppraisalProjects = systemService.getSession()
        //                .createCriteria(TBAppraisalProjectEntity.class)
        //                .add(Restrictions.ne("state", ApprovalConstant.APPRSTATUS_UNSEND))
        //                .add(Restrictions.eq("planYear", year)).add(Restrictions.like("projectName", name, MatchMode.ANYWHERE))
        //                .list();

        CriteriaQuery cq = new CriteriaQuery(TBAppraisalProjectEntity.class);
        cq.notEq("state", ApprovalConstant.APPRSTATUS_UNSEND);
        cq.notEq("planYear", year);
        if (StringUtils.isNotEmpty(name)) {
            cq.like("projectName", name);
        }
        List<TBAppraisalProjectEntity> tBAppraisalProjects = this.systemService.getListByCriteriaQuery(cq, false);
        int count = 0;
        for (TBAppraisalProjectEntity entity : tBAppraisalProjects) {
            count++;
            entity.setNum(String.valueOf(count));
        }
        modelMap.put(NormalExcelConstants.FILE_NAME, year + "年度科技成果鉴定计划汇总表");
        modelMap.put(NormalExcelConstants.CLASS, TBAppraisalProjectEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams(year + "年度科技成果鉴定计划汇总表", "", "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBAppraisalProjects);
        return "appraisalExcelView";
    }

}
