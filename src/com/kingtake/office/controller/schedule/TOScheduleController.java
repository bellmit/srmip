package com.kingtake.office.controller.schedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.information.TOHandoverEntity;
import com.kingtake.office.entity.researchactivity.TOReceiveLogEntity;
import com.kingtake.office.entity.schedule.TOResponseEntity;
import com.kingtake.office.entity.schedule.TOScheduleEntity;
import com.kingtake.office.service.schedule.TOScheduleServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: T_O_SCHEDULE
 * @author onlineGenerator
 * @date 2015-07-10 11:16:12
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOScheduleController")
public class TOScheduleController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOScheduleController.class);

    @Autowired
    private TOScheduleServiceI tOScheduleService;
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
     * tab???
     * 
     * @return
     */
    @RequestMapping(params = "tOSchedule")
    public ModelAndView tOSchedule(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/schedule/scheduleTab");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tOScheduleMgr")
    public ModelAndView tOScheduleMgr(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/schedule/tOScheduleList");
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goSheduleList")
    public ModelAndView goSheduleList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/schedule/dayScheduleList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(TOScheduleEntity tOSchedule, HttpServletRequest request,
            HttpServletResponse response,
            DataGrid dataGrid) {
        try {
            String delFlag = request.getParameter("df");
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery cq = new CriteriaQuery(TOScheduleEntity.class, dataGrid);
        //?????????????????????
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSchedule,
                request.getParameterMap());
            cq.eq("userId", user.getId());
            if ("0".equals(delFlag)) {
                cq.or(Restrictions.isNull("deleteFlag"), Restrictions.eq("deleteFlag", "0"));
            } else {
                cq.eq("deleteFlag", "1");
            }
            cq.addOrder("finishedFlag", SortDirection.asc);
            cq.addOrder("createDate", SortDirection.desc);
            cq.add();
            this.tOScheduleService.getDataGridReturn(cq, true);
            List<TOScheduleEntity> results = dataGrid.getResults();
            for (TOScheduleEntity entity : results) {
                if (StringUtils.isEmpty(entity.getParentId())) {
                    entity.setSendReceiveFlag("1");
                } else {
                    CriteriaQuery logCq = new CriteriaQuery(TOReceiveLogEntity.class, dataGrid);
                    logCq.eq("receiverId", user.getId());
                    logCq.eq("researchId", entity.getParentId());
                    logCq.add();
                    List<TOReceiveLogEntity> receiveLogs = this.systemService.getListByCriteriaQuery(logCq, false);
                    boolean isRec = false;
                    for (TOReceiveLogEntity recLog : receiveLogs) {
                        if ("1".equals(recLog.getReceiveFlag())) {
                            isRec = true;
                            break;
                        }
                    }
                    if (isRec) {
                        entity.setSendReceiveFlag("3");//?????????
                    } else {
                        entity.setSendReceiveFlag("2");//?????????
                    }
            }
            }
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "dataList")
    public void dataList(HttpServletRequest request, HttpServletResponse response) {
        TSUser user = ResourceUtil.getSessionUserName();
        List<Map<String, Object>> list = tOScheduleService
                .findForJdbc("select s.id, s.begin_time, s.end_time, s.title,s.color, s.finished_flag,s.create_name "
                        + "from t_o_schedule s where s.user_id = '" + user.getId()
                        + "' and (s.delete_flag is null or s.delete_flag='0')");
        for (Map<String, Object> map : list) {
            String id = (String) map.get("id");
            TOScheduleEntity schedule = this.systemService.get(TOScheduleEntity.class, id);
            if (StringUtils.isNotEmpty(schedule.getParentId())) {
                CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
                cq.eq("researchId", schedule.getParentId());
                cq.eq("receiverId", schedule.getUserId());
                cq.add();
                List<TOReceiveLogEntity> logList = this.systemService.getListByCriteriaQuery(cq, false);
                if (logList.size() > 0) {
                    String receiveFlag = logList.get(0).getReceiveFlag();
                    if ("1".equals(receiveFlag)) {
                        map.put("receiveFlag", "3");
                    }
                }
            }
        }
        TagUtil.listToJson(response, list);
    }

    /**
     * ??????T_O_SCHEDULE
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOScheduleEntity tOSchedule, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOSchedule = systemService.getEntity(TOScheduleEntity.class, tOSchedule.getId());
        message = "??????????????????";
        try {
            CriteriaQuery cq = new CriteriaQuery(TOScheduleEntity.class);
            cq.eq("parentId", tOSchedule.getId());
            cq.add();
            List<TOScheduleEntity> scheduleList = this.systemService.getListByCriteriaQuery(cq, false);
            if (scheduleList.size() > 0) {
                j.setMsg("???????????????????????????????????????????????????");
                return j;
            }
            tOScheduleService.deleteSchedule(tOSchedule);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doUpdateFinishFlag")
    @ResponseBody
    public AjaxJson doUpdateFinishFlag(TOScheduleEntity tOSchedule, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            this.tOScheduleService.doFinish(tOSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????
     * 
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOScheduleEntity tOSchedule, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????";
        try {
            this.tOScheduleService.saveSchedule(tOSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOScheduleEntity tOSchedule, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSchedule.getId())) {
            tOSchedule = tOScheduleService.getEntity(TOScheduleEntity.class, tOSchedule.getId());
            String isOpen = req.getParameter("isOpen");
            if (StringUtils.isNotEmpty(isOpen) && "1".equals(isOpen)) {//?????????????????????
                if (isReceive(tOSchedule) && StringUtils.isNotEmpty(tOSchedule.getParentId())) {
                    this.tOScheduleService.doReceive(tOSchedule);
                }
            }
            // ??????????????????
            if (StringUtil.isNotEmpty(tOSchedule.getProjectId())) {
                TPmProjectEntity project = systemService.get(TPmProjectEntity.class, tOSchedule.getProjectId());
                req.setAttribute("projectName", project.getProjectName());
            }
        }
        req.setAttribute("tOSchedulePage", tOSchedule);
        String select = req.getParameter("select");
        if (StringUtils.isNotEmpty(select)) {
            req.setAttribute("select", select);
        }
        return new ModelAndView("com/kingtake/office/schedule/tOSchedule");
    }
    
    /**
     * ??????????????????
     * 
     * @param tOSchedule
     * @return
     */
    private boolean isReceive(TOScheduleEntity tOSchedule) {
        boolean needReceive = false;
        TSUser user = ResourceUtil.getSessionUserName();
        tOSchedule = this.tOScheduleService.get(TOScheduleEntity.class, tOSchedule.getId());
        CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
        cq.eq("researchId", tOSchedule.getParentId());
        cq.eq("receiverId", user.getId());
        cq.eq("receiveFlag", "0");
        cq.eq("sendType", SrmipConstants.SEND_TYPE_SCHEDULE);
        cq.add();
        List<TOReceiveLogEntity> receiveLogList = this.tOScheduleService.getListByCriteriaQuery(cq, false);
        if (receiveLogList.size() > 0) {
            needReceive = true;
        }
        return needReceive;
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goListUpdate")
    public ModelAndView goListUpdate(TOScheduleEntity tOSchedule, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSchedule.getId())) {
            tOSchedule = tOScheduleService.getEntity(TOScheduleEntity.class, tOSchedule.getId());
            // ??????????????????
            if (StringUtil.isNotEmpty(tOSchedule.getProjectId())) {
                TPmProjectEntity project = systemService.get(TPmProjectEntity.class, tOSchedule.getProjectId());
                req.setAttribute("projectName", project.getProjectName());
            }
        }
        req.setAttribute("tOSchedulePage", tOSchedule);
        return new ModelAndView("com/kingtake/office/schedule/tOSchedule-update");
    }
    
    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goHandover")
    public ModelAndView goHandover(TOScheduleEntity tOSchedule, HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/schedule/handover");
    }
    

    /**
     * ??????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doHandover")
    @ResponseBody
    public AjaxJson doHandover(TOScheduleEntity tOSchedule, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????";
        try {
            TSUser user = ResourceUtil.getSessionUserName();
            Criteria criteria = systemService.getSession().createCriteria(TOScheduleEntity.class)
                    .add(Restrictions.eq("userId", user.getId()))
                    .add(Restrictions.not(Restrictions.or(Restrictions.lt("endTime", tOSchedule.getBeginTime()),
                            Restrictions.gt("beginTime", tOSchedule.getEndTime()))));
            
            Criteria nextcriteria = systemService.getSession().createCriteria(TOScheduleEntity.class)
                    .add(Restrictions.eq("userId", user.getId()))
                    .add(Restrictions.not(Restrictions.or(
                            Restrictions.lt("endTime", tOSchedule.getEndTime()),
                            Restrictions.gt("beginTime",
                                    org.apache.commons.lang3.time.DateUtils.addDays(tOSchedule.getEndTime(), 7)))));
            List<TOScheduleEntity> nextlist = nextcriteria.addOrder(Order.asc("beginTime")).list();
            StringBuffer nextWeekContent = new StringBuffer();
            if (nextlist.size() > 0) {
                for (TOScheduleEntity nextschedule : nextlist) {
                    nextWeekContent.append("?????????"
                            + DateUtils.datetimeFormat.format(nextschedule.getBeginTime())
                            + (nextschedule.getEndTime() == null ? "" : "???"
                                    + DateUtils.datetimeFormat.format(nextschedule.getEndTime())) + "????????????"
                            + (StringUtil.isEmpty(nextschedule.getTitle()) ? "???" : nextschedule.getTitle()) + "????????????"
                            + (StringUtil.isEmpty(nextschedule.getContent()) ? "???" : nextschedule.getContent()) + "\n");
                }
            }

            if (!"all".equals(tOSchedule.getOpenStatus())) {
            	criteria.add(Restrictions.eq("openStatus", tOSchedule.getOpenStatus()));
            }
            if (!"all".equals(tOSchedule.getScheduleType())) {
            	criteria.add(Restrictions.eq("scheduleType", tOSchedule.getScheduleType()));
            }
            List<TOScheduleEntity> list = criteria.addOrder(Order.asc("beginTime")).list();
            if (list != null && list.size() > 0) {
                StringBuffer content = new StringBuffer();
                
                for (int i = 0; i < list.size(); ++i) {
                	TOScheduleEntity schedule = list.get(i);
                	content.append("?????????" + DateUtils.datetimeFormat.format(schedule.getBeginTime()) 
                			+ (schedule.getEndTime() == null ? 
                					"" : "???" + DateUtils.datetimeFormat.format(schedule.getEndTime()))
                			+ "????????????" + (StringUtil.isEmpty(schedule.getTitle()) ? "???" : schedule.getTitle())
                			+ "????????????" + (StringUtil.isEmpty(schedule.getContent()) ? "???" : schedule.getContent()) + "\n");
                }

                TOHandoverEntity handover = new TOHandoverEntity();
                TSDepart depart = user.getCurrentDepart();
                handover.setHandoverId(user.getId());
                handover.setHandoverName(user.getRealName());
                handover.setHandoverTime(new Date());
                handover.setHandoverDepartId(depart.getId());
                handover.setHandoverDepartName(depart.getDepartname());
                handover.setHandoverContent(content.toString());
                handover.setReceiver(tOSchedule.getRelateUserid());
                handover.setSubmitFlag(SrmipConstants.NO);
                handover.setNextWeekWorkContent(nextWeekContent.toString());
                systemService.save(handover);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } else {
                message = "???????????????????????????????????????";
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TOScheduleEntity tOSchedule, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String receiverIds = request.getParameter("receiverId");
        String receiverNames = request.getParameter("receiverName");
        if (StringUtil.isNotEmpty(tOSchedule.getId())) {
            message = "????????????????????????";
            try {
                tOScheduleService.doSend(tOSchedule, receiverIds, receiverNames);
            } catch (Exception e) {
                e.printStackTrace();
                message = "???????????????????????????";
                j.setSuccess(false);
            }
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doRecieve")
    @ResponseBody
    public AjaxJson doRecieve(TOScheduleEntity tOSchedule, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            tOScheduleService.doReceive(tOSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveLogList")
    public ModelAndView goReceiveLogList(HttpServletRequest request) {
        String researchId = request.getParameter("researchId");
        if (StringUtils.isNotEmpty(researchId)) {
            request.setAttribute("researchId", researchId);
        }
        String sendType = request.getParameter("sendType");
        if (StringUtils.isNotEmpty(sendType)) {
            request.setAttribute("sendType", sendType);
        }
        return new ModelAndView("com/kingtake/office/schedule/receiveLogList");
    }

    /**
     * ?????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goResponse")
    public ModelAndView goResponse(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/schedule/response");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doResponse")
    @ResponseBody
    public AjaxJson doResponse(TOScheduleEntity tOSchedule, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String resContent = request.getParameter("resContent");
        String type = request.getParameter("type");
        message = "???????????????????????????";
        try {
            tOScheduleService.doResponse(tOSchedule, resContent, type);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goMyResponseList")
    public ModelAndView goMyResponseList(HttpServletRequest request) {
        String scheduleId = request.getParameter("scheduleId");
        if (StringUtils.isNotEmpty(scheduleId)) {
            request.setAttribute("scheduleId", scheduleId);
        }
        return new ModelAndView("com/kingtake/office/schedule/myResponseList");
    }

    /**
     * ??????????????????
     * 
     * @param tOSchedule
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "myResponseList")
    public void myResponseList(HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        try {
            String scheduleId = request.getParameter("scheduleId");
            TOScheduleEntity tOSchedule = this.systemService.get(TOScheduleEntity.class, scheduleId);
            CriteriaQuery cq = new CriteriaQuery(TOResponseEntity.class, dataGrid);
            cq.eq("resSourceId", tOSchedule.getParentId());
            cq.addOrder("resTime", SortDirection.desc);
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goResponseList")
    public ModelAndView goResponseList(HttpServletRequest request) {
        String scheduleId = request.getParameter("scheduleId");
        if (StringUtils.isNotEmpty(scheduleId)) {
            request.setAttribute("scheduleId", scheduleId);
        }
        return new ModelAndView("com/kingtake/office/schedule/responseList");
    }

    /**
     * ??????????????????
     * 
     * @param tOSchedule
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "responseList")
    public void responseList(TOScheduleEntity tOSchedule, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        try {
            CriteriaQuery cq = new CriteriaQuery(TOResponseEntity.class, dataGrid);
            cq.eq("resSourceId", tOSchedule.getId());
            cq.addOrder("resTime", SortDirection.desc);
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doHide")
    @ResponseBody
    public AjaxJson doHide(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "????????????????????????";
        try {
            this.tOScheduleService.doLogicDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBack")
    @ResponseBody
    public AjaxJson doBack(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "????????????????????????!";
        try {
            this.tOScheduleService.doBack(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????!";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goSendReplyList")
    public ModelAndView goSendReplyList(HttpServletRequest request) {
        String scheduleId = request.getParameter("scheduleId");
        String type = request.getParameter("type");
        if (StringUtils.isNotEmpty(scheduleId)) {
            request.setAttribute("scheduleId", scheduleId);
        }
        if (StringUtils.isNotEmpty(type)) {
            request.setAttribute("type", type);
        }
        return new ModelAndView("com/kingtake/office/schedule/sendReplyList");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goRecycle")
    public ModelAndView goRecycle(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/schedule/recycleScheduleList");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOScheduleEntity tOSchedule, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TOScheduleEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSchedule, request.getParameterMap());
        cq.eq("userId", user.getId());
        cq.or(Restrictions.isNull("deleteFlag"), Restrictions.eq("deleteFlag", "0"));
        cq.addOrder("finishedFlag", SortDirection.asc);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TOScheduleEntity> tPortalLayouts = this.tOScheduleService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TOScheduleEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPortalLayouts);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
}
