
package com.kingtake.project.controller.reportreq;

import java.io.IOException;
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

import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.reportreq.TBPmReportReqEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.reportreq.TBPmReportReqServiceI;

/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2016-01-24 14:58:37
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmReportReqController")
public class TBPmReportReqController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TBPmReportReqController.class);

  @Autowired
  private TBPmReportReqServiceI tBPmReportReqService;
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
   * ??????????????????????????? ????????????
   * 
   * @return
   */
  @RequestMapping(params = "tBPmReportReq")
  public ModelAndView tBPmReportReq(HttpServletRequest request) {
    return new ModelAndView("com/kingtake/project/reportreq/tBPmReportReqList");
  }

    /**
     * ????????????
     * 
     * @param tBPmReportReq
     * @param request
     * @return
     */
  @RequestMapping(params = "goEdit")
  public ModelAndView goEdit(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            // ????????????id?????????????????????????????????
            CriteriaQuery cq = new CriteriaQuery(TBPmReportReqEntity.class);
            cq.eq("projectId", projectId);
            cq.add();
            List<TBPmReportReqEntity> reportReqList = this.tBPmReportReqService.getListByCriteriaQuery(cq, false);
            if (reportReqList != null && reportReqList.size() > 0) {
                TBPmReportReqEntity queryReportReq = reportReqList.get(0);
                Map<String, Object> dataMap = activitiService.getProcessInstance(queryReportReq.getId());
                if (dataMap != null) {
                    String processInstId = (String) dataMap.get("processInstId");
                    String taskId = (String) dataMap.get("taskId");
                    request.setAttribute("processInstId", processInstId);
                    request.setAttribute("taskId", taskId);
                    queryReportReq.setProcessInstId(processInstId);
                }
                if (StringUtils.isEmpty(queryReportReq.getAttachmentCode())) {
                    queryReportReq.setAttachmentCode(UUIDGenerator.generate());
                } else {
                    List<TSFilesEntity> certificates = systemService.getAttachmentByCode(
                            queryReportReq.getAttachmentCode(), "");
                    queryReportReq.setAttachments(certificates);
                }
                request.setAttribute("tBPmReportReqPage", queryReportReq);
                String declareStatus = tPmDeclareService.getDeclareStatus(queryReportReq.getBpmStatus(),
                        queryReportReq.getPlanStatus());
                request.setAttribute("declareStatus", declareStatus);
            } else {
                TPmProjectEntity projectEntity = this.systemService.get(TPmProjectEntity.class, projectId);
                TBPmReportReqEntity reportReqEntity = new TBPmReportReqEntity();
                reportReqEntity.setProjectId(projectId);
                reportReqEntity.setProjectName(projectEntity.getProjectName());
                reportReqEntity.setBeginDate(projectEntity.getBeginDate());
                reportReqEntity.setEndDate(projectEntity.getEndDate());
                reportReqEntity.setFeeReq(projectEntity.getAllFee());
                reportReqEntity.setReportUnit(projectEntity.getDevDepart());
                reportReqEntity.setManageUnit(projectEntity.getDutyDepart());
                reportReqEntity.setApplicantor(projectEntity.getProjectManager());
//                reportReqEntity.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
                if (StringUtils.isEmpty(reportReqEntity.getAttachmentCode())) {
                    reportReqEntity.setAttachmentCode(UUIDGenerator.generate());
                } else {
                    List<TSFilesEntity> certificates = systemService.getAttachmentByCode(
                            reportReqEntity.getAttachmentCode(), "");
                    reportReqEntity.setAttachments(certificates);
                }
                request.setAttribute("tBPmReportReqPage", reportReqEntity);
                String declareStatus = tPmDeclareService.getDeclareStatus(reportReqEntity.getBpmStatus(),
                        reportReqEntity.getPlanStatus());
                request.setAttribute("declareStatus", declareStatus);
            }
        }
        return new ModelAndView("com/kingtake/project/reportreq/tBPmReportReqEdit");
    }

    /**
     * ????????????
     * 
     * @param tBPmReportReq
     * @param request
     * @return
     */
    @RequestMapping(params = "goEditInfo")
    public ModelAndView goEditInfo(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            // ????????????id?????????????????????????????????
            CriteriaQuery cq = new CriteriaQuery(TBPmReportReqEntity.class);
            cq.eq("projectId", projectId);
            cq.add();
            List<TBPmReportReqEntity> reportReqList = this.tBPmReportReqService.getListByCriteriaQuery(cq, false);
            if (reportReqList != null && reportReqList.size() > 0) {
                TBPmReportReqEntity queryReportReq = reportReqList.get(0);
                if (StringUtils.isEmpty(queryReportReq.getAttachmentCode())) {
                    queryReportReq.setAttachmentCode(UUIDGenerator.generate());
                } else {
                    List<TSFilesEntity> certificates = systemService.getAttachmentByCode(
                            queryReportReq.getAttachmentCode(), "");
                    queryReportReq.setAttachments(certificates);
                }
                request.setAttribute("tBPmReportReqPage", queryReportReq);
                String declareStatus = tPmDeclareService.getDeclareStatus(queryReportReq.getBpmStatus(),
                        queryReportReq.getPlanStatus());
                request.setAttribute("declareStatus", declareStatus);
            } else {
                TPmProjectEntity projectEntity = this.systemService.get(TPmProjectEntity.class, projectId);
                TBPmReportReqEntity reportReqEntity = new TBPmReportReqEntity();
                reportReqEntity.setProjectId(projectId);
                reportReqEntity.setProjectName(projectEntity.getProjectName());
                reportReqEntity.setBeginDate(projectEntity.getBeginDate());
                reportReqEntity.setEndDate(projectEntity.getEndDate());
                reportReqEntity.setFeeReq(projectEntity.getAllFee());
                reportReqEntity.setReportUnit(projectEntity.getDevDepart());
                reportReqEntity.setManageUnit(projectEntity.getDutyDepart());
                reportReqEntity.setApplicantor(projectEntity.getProjectManager());
                reportReqEntity.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
                if (StringUtils.isEmpty(reportReqEntity.getAttachmentCode())) {
                    reportReqEntity.setAttachmentCode(UUIDGenerator.generate());
                } else {
                    List<TSFilesEntity> certificates = systemService.getAttachmentByCode(
                            reportReqEntity.getAttachmentCode(), "");
                    reportReqEntity.setAttachments(certificates);
                }
                request.setAttribute("tBPmReportReqPage", reportReqEntity);
                String declareStatus = tPmDeclareService.getDeclareStatus(reportReqEntity.getBpmStatus(),
                        reportReqEntity.getPlanStatus());
                request.setAttribute("declareStatus", declareStatus);
            }
        }
        return new ModelAndView("com/kingtake/project/reportreq/tBPmReportReqEditInfo");
    }

  /**
   * easyui AJAX????????????
   * 
   * @param request
   * @param response
   * @param dataGrid
   * @param user
   */

  @SuppressWarnings("unchecked")
  @RequestMapping(params = "datagrid")
  public void datagrid(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TBPmReportReqEntity.class, dataGrid);
    // ?????????????????????
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmReportReq, request.getParameterMap());
    try {
      // ???????????????????????????
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tBPmReportReqService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * ???????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBPmReportReq = systemService.getEntity(TBPmReportReqEntity.class, tBPmReportReq.getId());
    message = "?????????????????????????????????";
    try {
      tBPmReportReqService.deleteAddAttach(tBPmReportReq);
    } catch (Exception e) {
      e.printStackTrace();
      message = "?????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ?????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doBatchDel")
  @ResponseBody
  public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    try {
      for (String id : ids.split(",")) {
        TBPmReportReqEntity tBPmReportReq = systemService.getEntity(TBPmReportReqEntity.class, id);
        tBPmReportReqService.deleteAddAttach(tBPmReportReq);
      }
    } catch (Exception e) {
      e.printStackTrace();
      message = "?????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ???????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    try {
      tBPmReportReqService.save(tBPmReportReq);
      systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "?????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ???????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    try {
      if (StringUtil.isNotEmpty(tBPmReportReq.getId())) {
        message = "??????????????????????????????";
        TBPmReportReqEntity t = tBPmReportReqService.get(TBPmReportReqEntity.class, tBPmReportReq.getId());
        MyBeanUtils.copyBeanNotNull2Bean(tBPmReportReq, t);
        tBPmReportReqService.saveOrUpdate(t);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      } else {
                message = "??????????????????????????????";
                tBPmReportReq.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
        tBPmReportReqService.save(tBPmReportReq);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      }
    } catch (Exception e) {
      e.printStackTrace();
      message = "?????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setObj(tBPmReportReq);
    j.setMsg(message);
    return j;
  }

  /**
   * ???????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TBPmReportReqEntity tBPmReportReq, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPmReportReq.getId())) {
      tBPmReportReq = tBPmReportReqService.getEntity(TBPmReportReqEntity.class, tBPmReportReq.getId());
      req.setAttribute("tBPmReportReqPage", tBPmReportReq);
    }
    return new ModelAndView("com/kingtake/project/reportreq/tBPmReportReq-add");
  }

  /**
   * ???????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TBPmReportReqEntity tBPmReportReq, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPmReportReq.getId())) {
      tBPmReportReq = tBPmReportReqService.getEntity(TBPmReportReqEntity.class, tBPmReportReq.getId());
      req.setAttribute("tBPmReportReqPage", tBPmReportReq);
    }
    return new ModelAndView("com/kingtake/project/reportreq/tBPmReportReq-update");
  }

  /**
   * ??????????????????
   * 
   * @return
   */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/project/reportreq/tBPmReportReqUpload");
  }

  /**
   * ??????excel
   * 
   * @param request
   * @param response
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(params = "exportXls")
  public String exportXls(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TBPmReportReqEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmReportReq, request.getParameterMap());
    List<TBPmReportReqEntity> tBPmReportReqs = this.tBPmReportReqService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
    modelMap.put(NormalExcelConstants.CLASS, TBPmReportReqEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????", "?????????:" + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tBPmReportReqs);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * ??????excel ?????????
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
    modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
    modelMap.put(TemplateExcelConstants.MAP_DATA, null);
    modelMap.put(TemplateExcelConstants.CLASS, TBPmReportReqEntity.class);
    modelMap.put(TemplateExcelConstants.LIST_DATA, null);
    return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
  }

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
        List<TBPmReportReqEntity> listTBPmReportReqEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(), TBPmReportReqEntity.class, params);
        for (TBPmReportReqEntity tBPmReportReq : listTBPmReportReqEntitys) {
          tBPmReportReqService.save(tBPmReportReq);
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
     * ????????????
     * 
     * @param tBPmReportReq
     * @param request
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TBPmReportReqEntity tBPmReportReq, HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            // ????????????id?????????????????????????????????
            CriteriaQuery cq = new CriteriaQuery(TBPmReportReqEntity.class);
            cq.eq("projectId", projectId);
            cq.add();
            List<TBPmReportReqEntity> reportReqList = this.tBPmReportReqService.getListByCriteriaQuery(cq, false);
            if (reportReqList != null && reportReqList.size() > 0) {
                TBPmReportReqEntity queryReportReq = reportReqList.get(0);
                request.setAttribute("tBPmReportReqPage", queryReportReq);
                String declareStatus = tPmDeclareService.getDeclareStatus(queryReportReq.getBpmStatus(),
                        queryReportReq.getPlanStatus());
                request.setAttribute("declareStatus", declareStatus);
                if (StringUtils.isEmpty(queryReportReq.getAttachmentCode())) {
                    queryReportReq.setAttachmentCode(UUIDGenerator.generate());
                } else {
                    List<TSFilesEntity> certificates = systemService.getAttachmentByCode(
                            queryReportReq.getAttachmentCode(), "");
                    queryReportReq.setAttachments(certificates);
                }
            }
        }
        request.setAttribute("opt", "audit");
        request.setAttribute("read", "1");
        return new ModelAndView("com/kingtake/project/reportreq/tBPmReportReqEdit");
    }

}
