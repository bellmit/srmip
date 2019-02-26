package com.kingtake.project.controller.appraisalmeeting;

import java.io.IOException;
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
import org.jeecgframework.web.system.pojo.base.TSUser;
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

import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingEntity;
import com.kingtake.project.service.appraisalmeeting.TBAppraisalMeetingServiceI;

/**
 * @Title: Controller
 * @Description: 鉴定会信息表
 * @author onlineGenerator
 * @date 2016-01-22 14:26:13
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalMeetingController")
public class TBAppraisalMeetingController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBAppraisalMeetingController.class);

    @Autowired
    private TBAppraisalMeetingServiceI tBAppraisalMeetingService;
    @Autowired
    private TOMessageServiceI messageService;
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
     * 鉴定会信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisalMeeting")
    public ModelAndView tBAppraisalMeeting(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingList");
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
    public void datagrid(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMeeting,
                request.getParameterMap());
            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("checkUserid", user.getId());
            //cq.eq("checkDepartid", user.getCurrentDepart().getId());
            cq.notEq("meetingStatus", ApprovalConstant.APPRSTATUS_UNSEND);
        cq.add();
        this.tBAppraisalMeetingService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "getMemberListByMeetingId")
    @ResponseBody
    public JSONObject getMemberListByMeetingId(HttpServletRequest req) {
        JSONObject j = new JSONObject();
        String meetingId = req.getParameter("meetingId");
        String memberType = req.getParameter("memberType");
        if (StringUtil.isNotEmpty(meetingId)) {
            CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingDepartEntity.class);
            cq.eq("meetingId", meetingId);
            cq.eq("memberType", memberType);
            cq.add();
            List<TBAppraisalMeetingDepartEntity> list = systemService.getListByCriteriaQuery(cq, false);
            Map<String, Object> result = null;
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            for (TBAppraisalMeetingDepartEntity depart : list) {
                result = new HashMap<String, Object>();
                result.put("id", depart.getId());
                result.put("departname", depart.getDepartname());
                List<Map<String,Object>> memberList = getMemberByDepartId(depart.getId());
                result.put("quota", memberList.size());
                result.put("memberList", memberList);
                resultList.add(result);
            }
            j.put("rows", resultList);
            j.put("total", resultList.size());
        }
        return j;
    }

    public List<Map<String, Object>> getMemberByDepartId(String departId) {
        String sql = "SELECT M.ID,MEMBER_NAME,R.FLAG FROM T_B_APPRAISAL_MEMBER M JOIN T_B_APPRAISAL_MEETING_DEPART_M R ON R.DEPART_ID='"
                + departId + "' AND R.MEMBER_ID = M.ID";
        List<Map<String, Object>> memberList = systemService.findForJdbc(sql, null);
        return memberList;
    }

    @RequestMapping(params = "deleteMember")
    @ResponseBody
    public AjaxJson deleteMember(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String meetingId = req.getParameter("meetingId");
        String memberId = req.getParameter("memberId");
        String departId = req.getParameter("departId");
        if (tBAppraisalMeetingService.deleteMember(meetingId, memberId, departId)) {
            message = "移除成功！";
        } else {
            message = "移除失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 删除鉴定会信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBAppraisalMeeting = systemService.getEntity(TBAppraisalMeetingEntity.class, tBAppraisalMeeting.getId());
        message = "鉴定会信息表删除成功";
        try {
            tBAppraisalMeetingService.deleteAddAttach(tBAppraisalMeeting);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定会信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除鉴定会信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "鉴定会信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBAppraisalMeetingEntity tBAppraisalMeeting = systemService.getEntity(TBAppraisalMeetingEntity.class,
                        id);
                tBAppraisalMeetingService.deleteAddAttach(tBAppraisalMeeting);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定会信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加鉴定会信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "鉴定会信息表添加成功";
        try {
            tBAppraisalMeetingService.save(tBAppraisalMeeting);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定会信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新鉴定会信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "鉴定会信息表更新成功";
        try {
            String role = request.getParameter("role");
            if ("depart".equals(role)) {
                tBAppraisalMeeting.setMeetingStatus(ApprovalConstant.APPLYSTATUS_FINISH);
            }
            this.tBAppraisalMeetingService.saveMeeting(tBAppraisalMeeting);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定会信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(HttpServletRequest req) {
        AjaxJson j = this.tBAppraisalMeetingService.doSubmit(req);
        return j;
    }

    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            if (StringUtil.isNotEmpty(id)) {
                TBAppraisalMeetingEntity metting = systemService.get(TBAppraisalMeetingEntity.class, id);
                metting.setMsgText(msgText);
                metting.setMeetingStatus(ApprovalConstant.APPRSTATUS_REBUT);
                systemService.updateEntitie(metting);
            }
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "doPass")
    @ResponseBody
    public AjaxJson doPass(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            String id = req.getParameter("id");
            if (StringUtil.isNotEmpty(id)) {
                TBAppraisalMeetingEntity metting = systemService.get(TBAppraisalMeetingEntity.class, id);
                metting.setMeetingStatus(ApprovalConstant.APPRSTATUS_FINISH);
                systemService.updateEntitie(metting);
            }
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 鉴定会信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalMeeting.getId())) {
            tBAppraisalMeeting = tBAppraisalMeetingService.getEntity(TBAppraisalMeetingEntity.class,
                    tBAppraisalMeeting.getId());
            req.setAttribute("tBAppraisalMeetingPage", tBAppraisalMeeting);
        }
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeeting-update");
    }

    @RequestMapping(params = "goAddMember")
    public ModelAndView goAddMember(HttpServletRequest req) {
        String meetingId = req.getParameter("meetingId");
        String memberType = req.getParameter("memberType");
        req.setAttribute("memberType", memberType);
        req.setAttribute("meetingId", meetingId);
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeeting-addMember");
    }

    @RequestMapping(params = "addMember")
    @ResponseBody
    public AjaxJson addMember(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String meetingId = req.getParameter("meetingId");
        String memberType = req.getParameter("memberType");
        String workUnit = req.getParameter("workUnit");
        String memberNames = req.getParameter("memberNames");
        memberNames = memberNames.replaceAll("，", ",");
        tBAppraisalMeetingService.addMember(meetingId, memberType, workUnit, memberNames);
        return j;
    }

    /**
     * 鉴定会信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalMeeting.getId())) {
            tBAppraisalMeeting = tBAppraisalMeetingService.getEntity(TBAppraisalMeetingEntity.class,
                    tBAppraisalMeeting.getId());
        }else if(StringUtils.isNotEmpty(tBAppraisalMeeting.getApplyId())){
            TBAppraisalApplyEntity apply = this.systemService.get(TBAppraisalApplyEntity.class,tBAppraisalMeeting.getApplyId());
            req.setAttribute("projectId", apply.getProjectId());
            List<TBAppraisalMeetingEntity> queryList = this.systemService.findByProperty(TBAppraisalMeetingEntity.class, "applyId", tBAppraisalMeeting.getApplyId());
            if(queryList.size()>0){
                tBAppraisalMeeting = queryList.get(0);
            }
        }
        //附件
        if(StringUtils.isEmpty(tBAppraisalMeeting.getAttachmentCode())){
            tBAppraisalMeeting.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBAppraisalMeeting.getAttachmentCode(), "");
            tBAppraisalMeeting.setCertificates(certificates);
        }
        //附件
        if(StringUtils.isEmpty(tBAppraisalMeeting.getDepartAccessoryCode())){
            tBAppraisalMeeting.setDepartAccessoryCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBAppraisalMeeting.getDepartAccessoryCode(), "");
            tBAppraisalMeeting.setDepartAccessorys(certificates);
        }
        //关联编码
        if(StringUtils.isEmpty(tBAppraisalMeeting.getMeetingDeptCode())){
            tBAppraisalMeeting.setMeetingDeptCode(UUIDGenerator.generate());
        }
        
        TBAppraisalApplyAttachedEntity tBAppraisalApplyAttachedPage = null; 
        List<TBAppraisalApplyAttachedEntity> tBAppraisalApplyAttacheds = this.systemService.findByProperty(TBAppraisalApplyAttachedEntity.class, "applyId", tBAppraisalMeeting.getApplyId());
        if(tBAppraisalApplyAttacheds.size()>0){
            tBAppraisalApplyAttachedPage = tBAppraisalApplyAttacheds.get(0);
        }else{
            tBAppraisalApplyAttachedPage = new TBAppraisalApplyAttachedEntity();
            tBAppraisalApplyAttachedPage.setApplyId(tBAppraisalMeeting.getApplyId());
        }
        //机关附件
        if(StringUtils.isEmpty(tBAppraisalApplyAttachedPage.getAttachmentCode())){
            tBAppraisalApplyAttachedPage.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> departAccessorys = systemService.getAttachmentByCode(tBAppraisalApplyAttachedPage.getAttachmentCode(), "");
            tBAppraisalApplyAttachedPage.setCertificates(departAccessorys);
        }
        tBAppraisalMeeting.setApplyAttached(tBAppraisalApplyAttachedPage);
        req.setAttribute("tBAppraisalMeetingPage", tBAppraisalMeeting);
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeeting-update");
    }

    @RequestMapping(params = "goMeetingForDepart")
    public ModelAndView goMeetingForDepart(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalMeeting.getId())) {
            tBAppraisalMeeting = tBAppraisalMeetingService.getEntity(TBAppraisalMeetingEntity.class,
                    tBAppraisalMeeting.getId());
        }else if(StringUtils.isNotEmpty(tBAppraisalMeeting.getApplyId())){
            TBAppraisalApplyEntity apply = this.systemService.get(TBAppraisalApplyEntity.class,tBAppraisalMeeting.getApplyId());
            req.setAttribute("projectId", apply.getProjectId());
            List<TBAppraisalMeetingEntity> queryList = this.systemService.findByProperty(TBAppraisalMeetingEntity.class, "applyId", tBAppraisalMeeting.getApplyId());
            if(queryList.size()>0){
                tBAppraisalMeeting = queryList.get(0);
            }
        }
        //附件
        if(StringUtils.isEmpty(tBAppraisalMeeting.getAttachmentCode())){
            tBAppraisalMeeting.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBAppraisalMeeting.getAttachmentCode(), "");
            tBAppraisalMeeting.setCertificates(certificates);
        }
        //附件
        if(StringUtils.isEmpty(tBAppraisalMeeting.getDepartAccessoryCode())){
            tBAppraisalMeeting.setDepartAccessoryCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBAppraisalMeeting.getDepartAccessoryCode(), "");
            tBAppraisalMeeting.setDepartAccessorys(certificates);
        }
        //关联编码
        if(StringUtils.isEmpty(tBAppraisalMeeting.getMeetingDeptCode())){
            tBAppraisalMeeting.setMeetingDeptCode(UUIDGenerator.generate());
        }
        
        TBAppraisalApplyAttachedEntity tBAppraisalApplyAttachedPage = null; 
        List<TBAppraisalApplyAttachedEntity> tBAppraisalApplyAttacheds = this.systemService.findByProperty(TBAppraisalApplyAttachedEntity.class, "applyId", tBAppraisalMeeting.getApplyId());
        if(tBAppraisalApplyAttacheds.size()>0){
            tBAppraisalApplyAttachedPage = tBAppraisalApplyAttacheds.get(0);
        }else{
            tBAppraisalApplyAttachedPage = new TBAppraisalApplyAttachedEntity();
            tBAppraisalApplyAttachedPage.setApplyId(tBAppraisalMeeting.getApplyId());
        }
        //机关附件
        if(StringUtils.isEmpty(tBAppraisalApplyAttachedPage.getAttachmentCode())){
            tBAppraisalApplyAttachedPage.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> departAccessorys = systemService.getAttachmentByCode(tBAppraisalApplyAttachedPage.getAttachmentCode(), "");
            tBAppraisalApplyAttachedPage.setCertificates(departAccessorys);
        }
        tBAppraisalMeeting.setApplyAttached(tBAppraisalApplyAttachedPage);
        req.setAttribute("tBAppraisalMeetingPage", tBAppraisalMeeting);
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingForDepart");
    }

    /**
     * 选择推荐委员会成员作为参会专家并保存参会/未参会专家
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "selectMember")
    @ResponseBody
    public AjaxJson selectMember(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String checkMemberIds = req.getParameter("checkMemberIds");
        String applyId = req.getParameter("applyId");
        String meetingId = req.getParameter("meetingId");
        tBAppraisalMeetingService.selectMember(meetingId, applyId, checkMemberIds);
        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMeeting,
                request.getParameterMap());
        List<TBAppraisalMeetingEntity> tBAppraisalMeetings = this.tBAppraisalMeetingService.getListByCriteriaQuery(cq,
                false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "鉴定会信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBAppraisalMeetingEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("鉴定会信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBAppraisalMeetings);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "鉴定会信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBAppraisalMeetingEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
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
                List<TBAppraisalMeetingEntity> listTBAppraisalMeetingEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBAppraisalMeetingEntity.class, params);
                for (TBAppraisalMeetingEntity tBAppraisalMeeting : listTBAppraisalMeetingEntitys) {
                    tBAppraisalMeetingService.save(tBAppraisalMeeting);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
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
     * 成果鉴定会申请审批tab页
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisaMeetingReceiveTab")
    public ModelAndView tBAppraisaMeetingReceiveTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingList-receiveTab");
    }
    
    /**
     * 机关审核鉴定会申请
     * 
     * @return
     */
    @RequestMapping(params = "goAppraisaMeetingReceive")
    public ModelAndView goAppraisaMeetingReceive(HttpServletRequest request) {
        //处理审批
        //0：未处理；1：已处理
        String operateStatus = request.getParameter("operateStatus");
        if(StringUtils.isNotEmpty(operateStatus)){
            request.setAttribute("operateStatus", operateStatus);
        }
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMeetingList");
    }
    
    /**
     * 鉴定会申请列表列表请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForApply")
    public void datagridForApply(TBAppraisalMeetingEntity tBAppraisalMeeting, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String operateStatus = request.getParameter("operateStatus");
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMeeting,
                request.getParameterMap());
        if("0".equals(operateStatus)){
           cq.eq("meetingStatus", ApprovalConstant.APPRSTATUS_SEND);//已发送
        }else{
           cq.notEq("meetingStatus", ApprovalConstant.APPRSTATUS_SEND);//已处理
        }
        cq.eq("checkUserid", user.getId());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBAppraisalMeetingService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
}
