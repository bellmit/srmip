package com.kingtake.project.controller.declare;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
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
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.pojo.base.TPBpmLog;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.declare.TPmDeclareEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;

/**
 * @Title: Controller
 * @Description: ????????????
 * @author onlineGenerator
 * @date 2015-07-21 17:00:42
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmDeclareController")
public class TPmDeclareController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmDeclareController.class);

    @Autowired
    private TPmDeclareServiceI tPmDeclareService;
    @Autowired
    private ActivitiService activitiService;
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
     * ??????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmDeclareForResearchGroup")
    public ModelAndView tPmDeclareForResearchGroup(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        if (project != null && project.getProjectType() != null) {
            String declareTye = project.getProjectType().getDeclareType();
            if (ProjectConstant.PROJECT_DECALRE_TYPE_ARMY.equals(declareTye)) {
                return new ModelAndView(
                        "redirect:tBPmDeclareArmyResearchController.do?tBPmDeclareArmyResearch&projectId=" + projectId);
            } else if (ProjectConstant.PROJECT_DECALRE_TYPE_TECHNOLOGY.equals(declareTye)) {
                return new ModelAndView("redirect:tBPmDeclareTechnologyController.do?tBPmDeclareTechnology&projectId="
                        + projectId);
            } else if (ProjectConstant.PROJECT_DECALRE_TYPE_REPAIR.equals(declareTye)) {
                return new ModelAndView("redirect:tBPmDeclareRepairController.do?tBPmDeclareRepair&projectId="
                        + projectId);
            } else if (ProjectConstant.PROJECT_DECALRE_TYPE_BACK.equals(declareTye)) {
                return new ModelAndView("redirect:tPmDeclareBackController.do?tPmDeclareBack&projectId=" + projectId);
            }
        }
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        request.setAttribute("projectName", project.getProjectName());
        
        /**
         * ?????????4????????????????????????????????????????????????????????????
         */
        TPmDeclareEntity declare;
        // ????????????id????????????????????????????????????????????????????????????
        List<TPmDeclareEntity> declareList = systemService.findByProperty(TPmDeclareEntity.class, "project.id", projectId);
        if (declareList == null || declareList.size() == 0) {
          declare = tPmDeclareService.firstSave(projectId);
        } else {
          declare = declareList.get(0);
          String processInstId = getProcessInstance(declare.getId());
          declare.setProcessInstId(processInstId);
        }
      //????????????
        if(StringUtils.isEmpty(declare.getAttachmentCode())){
            declare.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(declare.getAttachmentCode(), "");
            declare.setAttachments(attachments);
        }
        request.setAttribute("declare", declare);
        Map<String, Object> dataMap = activitiService.getProcessInstance(declare.getId());
            if (dataMap != null) {
                if (dataMap.containsKey("processInstId")) {
                    String processInstId = (String) dataMap.get("processInstId");
                    request.setAttribute("processInstId", processInstId);
                }
                if (dataMap.containsKey("taskId")) {
                    String taskId = (String) dataMap.get("taskId");
                    request.setAttribute("taskId", taskId);
            }
        }
        String read = request.getParameter("read");
        if(StringUtil.isEmpty(read)){
          // ???????????????1--?????????
                if (SrmipConstants.YES.equals(declare.getBpmStatus())
                    || WorkFlowGlobals.BPM_BUS_STATUS_5.equals(declare.getBpmStatus())
                    || "2".equals(declare.getPlanStatus())) {
            // ?????????
            read = SrmipConstants.NO;
          }else{
            // ????????????
            read = SrmipConstants.YES;
          }
        }
        String opt = request.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            request.setAttribute("opt", opt);
        }
        request.setAttribute("read", read);
        String declareStatus = this.tPmDeclareService.getDeclareStatus(declare.getBpmStatus(), declare.getPlanStatus());
        request.setAttribute("declareStatus", declareStatus);
        return new ModelAndView("com/kingtake/project/declare/tPmDeclare4Execute");
    }

    /**
     * ????????????????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmDeclareForResearchGroupInfo")
    public ModelAndView tPmDeclareForResearchGroupInfo(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        if (project != null && project.getProjectType() != null) {
            String declareTye = project.getProjectType().getDeclareType();
            if (ProjectConstant.PROJECT_DECALRE_TYPE_ARMY.equals(declareTye)) {
                return new ModelAndView(
                        "redirect:tBPmDeclareArmyResearchController.do?tBPmDeclareArmyResearchInfo&projectId="
                                + projectId);
            } else if (ProjectConstant.PROJECT_DECALRE_TYPE_TECHNOLOGY.equals(declareTye)) {
                return new ModelAndView(
                        "redirect:tBPmDeclareTechnologyController.do?tBPmDeclareTechnologyInfo&projectId="
                        + projectId);
            } else if (ProjectConstant.PROJECT_DECALRE_TYPE_REPAIR.equals(declareTye)) {
                return new ModelAndView("redirect:tBPmDeclareRepairController.do?tBPmDeclareRepairInfo&projectId="
                        + projectId);
            } else if (ProjectConstant.PROJECT_DECALRE_TYPE_BACK.equals(declareTye)) {
                return new ModelAndView("redirect:tPmDeclareBackController.do?tPmDeclareBackInfo&projectId="
                        + projectId);
            }
        }
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        request.setAttribute("projectName", project.getProjectName());
        

        /**
         * ?????????4????????????????????????????????????????????????????????????
         */
        TPmDeclareEntity declare;
        // ????????????id????????????????????????????????????????????????????????????
        List<TPmDeclareEntity> declareList = systemService.findByProperty(TPmDeclareEntity.class, "project.id", projectId);
        if (declareList == null || declareList.size() == 0) {
          declare = tPmDeclareService.firstSave(projectId);
        } else {
          declare = declareList.get(0);
          String processInstId = getProcessInstance(declare.getId());
          declare.setProcessInstId(processInstId);
        }
      //????????????
        if(StringUtils.isEmpty(declare.getAttachmentCode())){
            declare.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(declare.getAttachmentCode(), "");
            declare.setAttachments(attachments);
        }
        request.setAttribute("declare", declare);
        
        String read = request.getParameter("read");
        if (StringUtil.isEmpty(read)) {
            // ???????????????1--?????????
            if (SrmipConstants.YES.equals(declare.getBpmStatus())) {
                // ?????????
                read = SrmipConstants.NO;
            } else {
                // ????????????
                read = SrmipConstants.YES;
            }
        }
        request.setAttribute("read", read);
        String declareStatus = this.tPmDeclareService.getDeclareStatus(declare.getBpmStatus(), declare.getPlanStatus());
        request.setAttribute("declareStatus", declareStatus);
        return new ModelAndView("com/kingtake/project/declare/tPmDeclare-updateForResearchGroup");
    }

    /**
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmDeclareForDepartment")
    public ModelAndView tPmDeclareForDepartment(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/declare/tPmDeclareListForDepartment");
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TPmDeclareEntity tPmDeclare, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmDeclareEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmDeclare, request.getParameterMap());
        try {
            String projectId = request.getParameter("projectId");
            if (StringUtils.isNotEmpty(projectId)) {
                cq.eq("project.id", projectId);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("id", SortDirection.desc);
        cq.add();
        this.tPmDeclareService.getDataGridReturn(cq, true);
        for (Object entity : dataGrid.getResults()) {
            TPmDeclareEntity tmp = (TPmDeclareEntity) entity;
            if (!"1".equals(tmp.getBpmStatus())) {
                Map<String, Object> dataMap = activitiService.getProcessInstance(tmp.getId());
                String processInstId = (String) dataMap.get("processInstId");
                String taskId = (String) dataMap.get("taskId");
                tmp.setTaskId(taskId);
                tmp.setProcessInstId(processInstId);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmDeclareEntity tPmDeclare, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmDeclare = systemService.getEntity(TPmDeclareEntity.class, tPmDeclare.getId());
        message = "????????????????????????";
        try {
            tPmDeclareService.deleteAddAttach(tPmDeclare);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmDeclareEntity tPmDeclare = systemService.getEntity(TPmDeclareEntity.class, id);
                tPmDeclareService.deleteAddAttach(tPmDeclare);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
     * ??????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TPmDeclareEntity tPmDeclare, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TPmDeclareEntity t = tPmDeclareService.get(TPmDeclareEntity.class, tPmDeclare.getId());
        try {
            String tpId = tPmDeclare.getProject().getId();
            if (StringUtils.isNotEmpty(tpId)) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tpId);
                tPmDeclare.setProject(project);
            }
            if (StringUtils.isNotEmpty(tPmDeclare.getId())) {
                MyBeanUtils.copyBeanNotNull2Bean(tPmDeclare, t);
                tPmDeclareService.saveOrUpdate(t);
                message = "????????????????????????";
            } else {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tpId);
                if("1".equals(project.getAssignFlag())){//???????????????????????????
                    tPmDeclare.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_6);
                }else{
                    tPmDeclare.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
                }
                tPmDeclareService.save(tPmDeclare);
                message = "????????????????????????";
            }
            j.setObj(tPmDeclare);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????? ??????/????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateForResearchGroup")
    public ModelAndView goUpdateForResearchGroup(TPmDeclareEntity tPmDeclare, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmDeclare.getId())) {
            tPmDeclare = tPmDeclareService.getEntity(TPmDeclareEntity.class, tPmDeclare.getId());
        }
        String projectId = req.getParameter("projectId");
        req.setAttribute("projectId", projectId);
        if (StringUtils.isNotEmpty(projectId)) {
            tPmDeclare.setTpId(projectId);
        }
        req.setAttribute("tPmDeclarePage", tPmDeclare);
        return new ModelAndView("com/kingtake/project/declare/tPmDeclare-updateForResearchGroup");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goEditAudit")
    public ModelAndView goEditAudit(TPmDeclareEntity tPmDeclare, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            if (project != null && project.getProjectType() != null) {
                String typeName = project.getProjectType().getProjectTypeName();
                if (typeName.indexOf("????????????") != -1) {
                    return new ModelAndView(
                            "redirect:tBPmDeclareArmyResearchController.do?tBPmDeclareArmyResearch&projectId="
                                    + projectId + "&read=0");
                } else if (typeName.indexOf("????????????") != -1) {
                    return new ModelAndView(
                            "redirect:tBPmDeclareTechnologyController.do?tBPmDeclareTechnology&projectId=" + projectId
                                    + "&read=0");
                } else if (typeName.indexOf("????????????") != -1) {
                    return new ModelAndView("redirect:tBPmDeclareRepairController.do?tBPmDeclareRepair&projectId="
                            + projectId + "&read=0");
                } else if (typeName.indexOf("????????????") != -1) {
                    return new ModelAndView("redirect:tPmDeclareBackController.do?tPmDeclareBack&projectId="
                            + projectId + "&read=0");
                }
            }
        }
        if (StringUtil.isNotEmpty(tPmDeclare.getId())) {
            tPmDeclare = tPmDeclareService.getEntity(TPmDeclareEntity.class, tPmDeclare.getId());
        }
        req.setAttribute("projectId", projectId);
        req.setAttribute("tPmDeclarePage", tPmDeclare);
        return new ModelAndView("com/kingtake/project/declare/tPmDeclare-updateForResearchGroupAudit");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goDetailAudit")
    public ModelAndView goDetailAudit(TPmDeclareEntity tPmDeclare, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmDeclare.getId())) {
            tPmDeclare = tPmDeclareService.getEntity(TPmDeclareEntity.class, tPmDeclare.getId());
        }
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            tPmDeclare.setTpId(projectId);
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            if (project != null && project.getProjectType() != null) {

                String declareTye = project.getProjectType().getDeclareType();
                if (ProjectConstant.PROJECT_DECALRE_TYPE_ARMY.equals(declareTye)) {
                    return new ModelAndView(
                            "redirect:tBPmDeclareArmyResearchController.do?tBPmDeclareArmyResearch&projectId="
                                    + projectId + "&read=1&opt=audit");
                } else if (ProjectConstant.PROJECT_DECALRE_TYPE_TECHNOLOGY.equals(declareTye)) {
                    return new ModelAndView(
                            "redirect:tBPmDeclareTechnologyController.do?tBPmDeclareTechnology&projectId=" + projectId
                                    + "&read=1&opt=audit");
                } else if (ProjectConstant.PROJECT_DECALRE_TYPE_REPAIR.equals(declareTye)) {
                    return new ModelAndView("redirect:tBPmDeclareRepairController.do?tBPmDeclareRepair&projectId="
                            + projectId + "&read=1&opt=audit");
                } else if (ProjectConstant.PROJECT_DECALRE_TYPE_BACK.equals(declareTye)) {
                    return new ModelAndView("redirect:tPmDeclareBackController.do?tPmDeclareBack&projectId="
                            + projectId + "&read=1&opt=audit");
                }
            }
        }else{
          projectId = tPmDeclare.getProject().getId();
          tPmDeclare.setTpId(projectId);
        }
        req.setAttribute("projectId", projectId);
        req.setAttribute("tPmDeclarePage", tPmDeclare);
        req.setAttribute("declare", tPmDeclare);
        req.setAttribute("read", '1');
        
        /**
         * ?????????4????????????????????????????????????????????????????????????
         */
        TPmDeclareEntity declare;
        // ????????????id????????????????????????????????????????????????????????????
        List<TPmDeclareEntity> declareList = systemService.findByProperty(TPmDeclareEntity.class, "project.id", projectId);
        if (declareList == null || declareList.size() == 0) {
          declare = tPmDeclareService.firstSave(projectId);
        } else {
          declare = declareList.get(0);
          String processInstId = getProcessInstance(declare.getId());
          declare.setProcessInstId(processInstId);
        }
      //????????????
        if(StringUtils.isEmpty(declare.getAttachmentCode())){
            declare.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(declare.getAttachmentCode(), "");
            declare.setAttachments(attachments);
        }
        req.setAttribute("declare", declare);
        if ("5".equals(declare.getBpmStatus())) {
          Map<String, Object> dataMap = activitiService.getProcessInstance(declare.getId());
          String processInstId = (String) dataMap.get("processInstId");
          String taskId = (String) dataMap.get("taskId");
          req.setAttribute("processInstId", processInstId);
          req.setAttribute("taskId", taskId);
        }
        String read = req.getParameter("read");
        if(StringUtil.isEmpty(read)){
          // ???????????????1--?????????
                if (SrmipConstants.YES.equals(declare.getBpmStatus())
                        || WorkFlowGlobals.BPM_BUS_STATUS_5.equals(declare.getBpmStatus())) {
            // ?????????
            read = SrmipConstants.NO;
          }else{
            // ????????????
            read = SrmipConstants.YES;
          }
        }
        req.setAttribute("opt", "audit");
        req.setAttribute("read", read);
        String declareStatus = this.tPmDeclareService.getDeclareStatus(declare.getBpmStatus(), declare.getPlanStatus());
        req.setAttribute("declareStatus", declareStatus);
        return new ModelAndView("com/kingtake/project/declare/tPmDeclare4Execute");
        
        
        //return new ModelAndView("com/kingtake/project/declare/tPmDeclare-updateForResearchGroup");
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateForDepartment")
    public ModelAndView goUpdateForDepartment(TPmDeclareEntity tPmDeclare, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmDeclare.getId())) {
            tPmDeclare = tPmDeclareService.getEntity(TPmDeclareEntity.class, tPmDeclare.getId());
            req.setAttribute("tPmDeclarePage", tPmDeclare);
        }
        return new ModelAndView("com/kingtake/project/declare/tPmDeclare-updateForDepartment");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/declare/tPmDeclareUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmDeclareEntity tPmDeclare, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmDeclareEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmDeclare, request.getParameterMap());
        List<TPmDeclareEntity> tPmDeclares = this.tPmDeclareService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmDeclareEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmDeclares);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    
    /**
     * ????????????????????????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportProjectXls")
    public void exportProjectXls(TPmDeclareEntity tPmDeclare, HttpServletRequest request,
            HttpServletResponse response) {
        try {
        	String id = request.getParameter("id");
            Workbook wb = this.tPmDeclareService.exportProject(id);
            String fileName = "??????????????????????????????.xls";
            fileName = POIPublicUtil
                    .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmDeclareEntity tPmDeclare, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmDeclareEntity.class);
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
            MultipartFile file = entity.getValue();// ????????????????????????
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TPmDeclareEntity> listTPmDeclareEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TPmDeclareEntity.class, params);
                for (TPmDeclareEntity tPmDeclare : listTPmDeclareEntitys) {
                    tPmDeclareService.save(tPmDeclare);
                }
                j.setMsg("?????????????????????");
            } catch (Exception e) {
                j.setMsg("?????????????????????");
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
     * ??????id??????????????????
     * 
     * @param id
     * @param request
     * @param response
     */
    private String getProcessInstance(String id) {
        String instanceId = null;
        String sql = "select t.proc_inst_id_ from ACT_HI_PROCINST t where t.business_key_=? ";
        List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql, id);
        if (resultList.size() > 0) {
            Map<String, Object> map = resultList.get(0);
            instanceId = (String) map.get("PROC_INST_ID_");
        }
        return instanceId;
    }

    /**
     * ???????????????????????????(???????????????)
     */
    @RequestMapping(params = "goTaskListTab")
    public ModelAndView goTaskListTab(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/declare/taskList-tab");
    }

    /**
     * ???????????????????????????- ????????????????????????
     */
    @RequestMapping(params = "goMyTaskList")
    public ModelAndView goMyTaskList(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/declare/taskList-person");
    }

    /**
     * ??????????????????????????? - ???????????????????????????????????????
     */
    @RequestMapping(params = "goGroupTaskList")
    public ModelAndView goGroupTaskList(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/declare/taskList-group");
    }

    /**
     * ???????????????????????????- ??????????????????????????????
     */
    @RequestMapping(params = "goHistoryTaskList")
    public ModelAndView goHistoryTaskList(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/declare/taskList-history");
    }

    /**
     * ??????????????????-?????????????????????
     */

    @RequestMapping(params = "taskAllList")
    public void taskAllList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List taskList = tPmDeclareService.findPriTodoTasks(user.getUserName(), request);
        dataGrid.setResults(taskList);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * ??????????????????-?????????
     */

    @RequestMapping(params = "taskGroupList")
    public void taskGroupList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        //???????????????????????????
        List<TSRoleUser> roles = systemService.findByProperty(TSRoleUser.class, "TSUser", user);
        List taskList = this.tPmDeclareService.findGroupTodoTasks(roles, request);
        dataGrid.setResults(taskList);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * ??????????????????-????????????
     */

    @RequestMapping(params = "taskHistoryList")
    public void taskHistoryList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List taskList = tPmDeclareService.findHistoryTasks(user.getUserName(), request);
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
        	for(int a=0;a<taskList.size();a++){
        		Map map = (Map)taskList.get(a);
        		map.put("businessCode", businessCode);
        	}
        }
        Long taskSize = tPmDeclareService.countHistoryTasks(user.getUserName(), request);
        dataGrid.setTotal(taskSize.intValue());
        dataGrid.setResults(taskList);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * ?????????????????????
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goDeclareAudit")
    public ModelAndView goDeclareAudit(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            request.setAttribute("id", id);
        }
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        String read = request.getParameter("read");
        if (StringUtils.isNotEmpty(read)) {
            request.setAttribute("read", read);
        }
        return new ModelAndView("com/kingtake/project/declare/tPmDeclareAudit");
    }

    /**
     * ????????????????????????????????????
     * 
     * @param tPmDeclare
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goDepartmentList")
    public ModelAndView goDepartmentList(TPmDeclareEntity tPmDeclare, HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("com/kingtake/project/declare/tPmDeclareListForDepartment");
    }

    /**
     * ????????????????????????????????????
     * 
     * @param tPmDeclare
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goCheckListForDepartment")
    public ModelAndView checkListForDepartment(TPmDeclareEntity tPmDeclare, HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("com/kingtake/project/declare/checkListForDepartment");
    }

    /**
     * ??????????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "checkListDepartement")
    @ResponseBody
    public JSONArray checkListDepartement(TPmDeclareEntity tPmDeclare, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List<TSBaseBusQuery> taskList = this.tPmDeclareService.findPriTodoTasks(user.getUserName(), request);
        if (taskList.size() == 0) {//????????????????????????????????????????????????
            return new JSONArray();
        }
        Map<String, TSBaseBusQuery> taskMap = new LinkedHashMap<String, TSBaseBusQuery>();
        for (TSBaseBusQuery query : taskList) {
            taskMap.put(query.getId(), query);
        }
        String projectName = request.getParameter("projectName");
        JSONArray jsonArray = new JSONArray();
        Set<Entry<String, TSBaseBusQuery>> entrySet = taskMap.entrySet();
        for (Entry<String, TSBaseBusQuery> entry : entrySet) {
            TSBaseBusQuery query = entry.getValue();
            String tableName = query.getTSBusConfig().getTSTable().getTableName();

            String sql = "select t.id,t.t_p_id projectId,t.bpm_status bpmStatus,p.project_name projectName,tp.project_type_name projectType from "
                    + tableName
                    + " t join t_pm_project p  on t.t_p_id=p.id join t_b_project_type tp on tp.id=p.project_type where t.id='"
                    + entry.getKey() + "'";
            if (StringUtils.isNotEmpty(projectName)) {
                sql = sql + " and p.project_name like '%" + projectName + "%'";
            }
            List<Map<String, Object>> dataList = this.systemService.findForJdbc(sql);
            if (dataList.size() > 0) {
                Map<String, Object> dataMap = dataList.get(0);
                JSONObject json = new JSONObject();
                json.put("id", dataMap.get("id"));
                json.put("projectId", dataMap.get("projectId"));
                json.put("projectName", dataMap.get("projectName"));
                json.put("projectType", dataMap.get("projectType"));
                json.put("submitUser", query.getUserRealName());
                json.put("submitTime", query.getCreatetime());
                json.put("bpmStatus", dataMap.get("bpmStatus"));
                jsonArray.add(json);
            }
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject tmp = (JSONObject) jsonArray.get(i);
            TSBaseBusQuery query = taskMap.get(tmp.getString("id"));
            if (StringUtils.isEmpty(query.getAssigneeName())) {
                tmp.put("assigneeName", "");
            } else {
                tmp.put("assigneeName", query.getAssigneeName());
            }
            if (query != null) {
                org.jeecgframework.workflow.model.activiti.Process pro = query.getProcess();
                if (pro != null) {
                    tmp.put("taskId", pro.getTask().getId());
                    tmp.put("taskName", pro.getTask().getName());
                    tmp.put("processInstId", pro.getProcessInstance().getId());
                }
            }
        }
        return jsonArray;
    }

    /**
     * ????????????????????????????????????
     * 
     * @param tPmDeclare
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goHistoryListForDepartment")
    public ModelAndView goHistoryListForDepartment(TPmDeclareEntity tPmDeclare, HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("com/kingtake/project/declare/historyListForDepartment");
    }

    /**
     * ????????????????????????????????????
     * 
     * @param tPmDeclare
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goListForDepartmentTab")
    public ModelAndView goListForDepartmentTab(TPmDeclareEntity tPmDeclare, HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView("com/kingtake/project/declare/declareListForDepartment-tab");
    }


    /**
     * ??????????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "historyListDepartement")
    @ResponseBody
    public JSONObject historyListDepartement(TPmDeclareEntity tPmDeclare, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        JSONObject dataJson = new JSONObject();
        TSUser user = ResourceUtil.getSessionUserName();
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        String businessCode = request.getParameter("businessCode");
        String sql = "select n.businessKey,s.business_name businessName from (select m.business_key_ businessKey,max(end_time_) operateTime from (select o.proc_inst_id_,o.end_time_,b.business_key_ "
                + "from act_hi_taskinst o join act_hi_procinst b on o.proc_inst_id_ = b.proc_inst_id_ and o.assignee_ = '"
                + user.getUserName()
                + "' ) m group by m.business_key_)n "
                + "join t_s_basebus s on n.businessKey=s.id where s.business_code='"
                + businessCode
                + "' order by n.operateTime";
        List<Map<String, Object>> listMap = this.tPmDeclareService.findForJdbc(sql, page, rows);
        String countSql = "select count(1) count from (" + sql + ")";
        List<Map<String, Object>> countListMap = tPmDeclareService.findForJdbc(countSql);
        int count = 0;
        if (countListMap.size() > 0) {
            Map<String, Object> dataMap = countListMap.get(0);
            count = Integer.parseInt(dataMap.get("count").toString());
        }
        if (count == 0) {//????????????????????????????????????????????????
            dataJson.put("rows", new JSONArray());
            dataJson.put("total", 0);
            return dataJson;
        }
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> entry : listMap) {
            String businessKey = (String) entry.get("businessKey");
            String businessName = (String) entry.get("businessName");
            Map<String, Object> dataMap = getDeclare(businessKey);
            if (dataMap == null) {
                continue;
            }
            Map<String, Object> processMap = activitiService.getProcessInstance(businessKey);
            String assignee = (String) processMap.get("assignee");
            String realName = getRealName(assignee);
            JSONObject json = new JSONObject();
            json.put("id", businessKey);
            json.put("processInstId", processMap.get("processInstId"));
            json.put("projectName", businessName);
            json.put("taskName", processMap.get("taskName"));
            json.put("taskId", processMap.get("taskId"));
            json.put("assignee", realName);
            json.put("bpmStatus", dataMap.get("bpm_status"));
            json.put("projectId", dataMap.get("projectId"));
            jsonArray.add(json);
        }
        dataJson.put("rows", jsonArray);
        dataJson.put("total", count);
        return dataJson;
    }

    /**
     * ??????????????????????????????
     * 
     * @param userName
     * @return
     */
    private String getRealName(String userName) {
        String realName = "";
        List<TSBaseUser> userList = this.systemService.findByProperty(TSBaseUser.class, "userName", userName);
        if (userList.size() > 0) {
            TSBaseUser user = userList.get(0);
            realName = user.getRealName();
        }
        return realName;
    }

    /**
     * ??????????????????.
     * 
     * @param businessKey
     * @return
     */
    private Map<String, Object> getDeclare(String businessKey) {
        Map<String, Object> dataMap = null;
        String sql = "select tb.tablename from t_s_basebus t join t_s_busconfig b on t.busconfigid = b.id join t_s_table tb on b.tableid = tb.id where t.id = ?";
        Map<String, Object> map = this.systemService.findOneForJdbc(sql, businessKey);
        if (map != null && map.size() > 0) {
            String tableName = (String) map.get("tablename");
            String bpmStatusSql = "select t.bpm_status,t.t_p_id projectId,t.id from " + tableName + " t where id='"
                    + businessKey
                    + "'";
            List<Map<String, Object>> dataList = this.systemService.findForJdbc(bpmStatusSql);
            if (dataList.size() > 0) {
                dataMap = dataList.get(0);
            }
        }
        return dataMap;
    }

    /**
     * ?????????????????????????????????
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goViewProcess")
    public ModelAndView goViewProcess(HttpServletRequest request) {
        String processInstId = request.getParameter("processInstId");
        if (StringUtils.isNotEmpty(processInstId)) {
            request.setAttribute("processInstId", processInstId);
        }
        return new ModelAndView("com/kingtake/project/declare/viewProcessList");
    }

    /**
     * ??????????????????????????????
     * 
     * @param tPmDeclare
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "processOprateDatagrid")
    @ResponseBody
    public JSONObject processOprateDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String processInstId = request.getParameter("processInstId");
        CriteriaQuery cq = new CriteriaQuery(TPBpmLog.class, dataGrid);
        if (StringUtils.isNotEmpty(processInstId)) {
            cq.eq("bpm_id", processInstId);
        }
        cq.addOrder("op_time", SortDirection.asc);
        cq.add();
        List<TPBpmLog> bpmLogList = this.systemService.getListByCriteriaQuery(cq, false);
        JSONObject json = new JSONObject();
        json.put("total", bpmLogList.size());
        json.put("rows", bpmLogList);
        return json;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goSelectOperator")
    public ModelAndView goSelectOperator(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = requestMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            request.setAttribute(entry.getKey(), entry.getValue()[0]);
        }
        return new ModelAndView("com/kingtake/project/declare/selectOperator");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goSelectOperator2")
    public ModelAndView goSelectOperator2(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/declare/selectOperator2");
    }

    /**
     * ????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "doResubmit")
    @ResponseBody
    public AjaxJson doResubmit(TPmPlanDetailEntity planDetail, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        this.tPmDeclareService.doResubmit(planDetail);
        return j;
    }
}
