package com.kingtake.project.controller.finish;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.jeecgframework.core.util.DateUtils;
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

import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.finish.TPmFinishApplyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.finish.TPmFinishApplyServiceI;

/**
 * @Title: Controller
 * @Description: ???????????????????????????
 * @author onlineGenerator
 * @date 2016-03-05 15:23:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmFinishApplyController")
public class TPmFinishApplyController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TPmFinishApplyController.class);

  @Autowired
  private TPmFinishApplyServiceI tPmFinishApplyService;
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
   * ????????????????????????????????? ????????????
   * 
   * @return
   */
  @RequestMapping(params = "tPmFinishApply")
  public ModelAndView tPmFinishApply(HttpServletRequest request) {
    return new ModelAndView("com/kingtake/project/finish/tPmFinishApplyList");
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
  public void datagrid(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TPmFinishApplyEntity.class, dataGrid);
    // ?????????????????????
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmFinishApply, request.getParameterMap());
    try {
      // ???????????????????????????
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tPmFinishApplyService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * ?????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tPmFinishApply = systemService.getEntity(TPmFinishApplyEntity.class, tPmFinishApply.getId());
    message = "???????????????????????????????????????";
    try {
      tPmFinishApplyService.deleteAddAttach(tPmFinishApply);
    } catch (Exception e) {
      e.printStackTrace();
      message = "???????????????????????????????????????";
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
  @RequestMapping(params = "doBatchDel")
  @ResponseBody
  public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "???????????????????????????????????????";
    try {
      for (String id : ids.split(",")) {
        TPmFinishApplyEntity tPmFinishApply = systemService.getEntity(TPmFinishApplyEntity.class, id);
        tPmFinishApplyService.deleteAddAttach(tPmFinishApply);
      }
    } catch (Exception e) {
      e.printStackTrace();
      message = "???????????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ?????????????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "???????????????????????????????????????";
    try {
      tPmFinishApplyService.save(tPmFinishApply);
      systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "???????????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ???????????????????????????????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doAddUpdate")
  @ResponseBody
  public AjaxJson doAddUpdate(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    try {
      if (StringUtil.isNotEmpty(tPmFinishApply.getId())) {
        message = "??????????????????";
        TPmFinishApplyEntity t = tPmFinishApplyService.get(TPmFinishApplyEntity.class, tPmFinishApply.getId());
        MyBeanUtils.copyBeanNotNull2Bean(tPmFinishApply, t);
        tPmFinishApplyService.saveOrUpdate(t);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      } else {
        message = "??????????????????";
        tPmFinishApply.setAuditStatus(ApprovalConstant.APPRSTATUS_UNSEND);
        tPmFinishApplyService.save(tPmFinishApply);
        systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
      }
      j.setSuccess(true);
    } catch (Exception e) {
      e.printStackTrace();
      j.setSuccess(false);
      message = "???????????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    j.setObj(tPmFinishApply);
    return j;
  }

  /**
   * ?????????????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "???????????????????????????????????????";
    TPmFinishApplyEntity t = tPmFinishApplyService.get(TPmFinishApplyEntity.class, tPmFinishApply.getId());
    try {
      MyBeanUtils.copyBeanNotNull2Bean(tPmFinishApply, t);
      tPmFinishApplyService.saveOrUpdate(t);
      systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "???????????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ?????????????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tPmFinishApply.getId())) {
      tPmFinishApply = tPmFinishApplyService.getEntity(TPmFinishApplyEntity.class, tPmFinishApply.getId());
    }
    if(StringUtils.isEmpty(tPmFinishApply.getAttachmentCode())){
        tPmFinishApply.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmFinishApply.getAttachmentCode(), "");
        tPmFinishApply.setAttachments(certificates);
    }
    req.setAttribute("tPmFinishApplyPage", tPmFinishApply);
    return new ModelAndView("com/kingtake/project/finish/tPmFinishApply-add");
  }

  /**
   * ?????????????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tPmFinishApply.getId())) {
      tPmFinishApply = tPmFinishApplyService.getEntity(TPmFinishApplyEntity.class, tPmFinishApply.getId());
    }
    if(StringUtils.isEmpty(tPmFinishApply.getAttachmentCode())){
        tPmFinishApply.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmFinishApply.getAttachmentCode(), "");
        tPmFinishApply.setAttachments(certificates);
    }
    req.setAttribute("tPmFinishApplyPage", tPmFinishApply);
    return new ModelAndView("com/kingtake/project/finish/tPmFinishApply-update");
  }

  /**
   * ??????????????????
   * 
   * @return
   */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/project/finish/tPmFinishApplyUpload");
  }

  /**
   * ??????excel
   * 
   * @param request
   * @param response
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(params = "exportXls")
  public String exportXls(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TPmFinishApplyEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmFinishApply, request.getParameterMap());
    List<TPmFinishApplyEntity> tPmFinishApplys = this.tPmFinishApplyService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "???????????????????????????");
    modelMap.put(NormalExcelConstants.CLASS, TPmFinishApplyEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????????????????", "?????????:" + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tPmFinishApplys);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * ??????excel ?????????
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    modelMap.put(TemplateExcelConstants.FILE_NAME, "???????????????????????????");
    modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
    modelMap.put(TemplateExcelConstants.MAP_DATA, null);
    modelMap.put(TemplateExcelConstants.CLASS, TPmFinishApplyEntity.class);
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
        List<TPmFinishApplyEntity> listTPmFinishApplyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(), TPmFinishApplyEntity.class, params);
        for (TPmFinishApplyEntity tPmFinishApply : listTPmFinishApplyEntitys) {
          tPmFinishApplyService.save(tPmFinishApply);
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
   * ????????????-????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "finishApply4ResearchGroup")
  public ModelAndView finishApply4ResearchGroup(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest req) {
    String projectId = req.getParameter("projectId");
    if (StringUtil.isNotEmpty(projectId)) {
      CriteriaQuery cq = new CriteriaQuery(TPmFinishApplyEntity.class);
      cq.eq("project.id", projectId);
      cq.add();
      List<TPmFinishApplyEntity> finishApplyList = tPmFinishApplyService.getListByCriteriaQuery(cq, false);
      if (finishApplyList.size() > 0) {
          tPmFinishApply = finishApplyList.get(0);
      } else {
        TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
        tPmFinishApply.setProject(proj);
        tPmFinishApply.setFinishDate(new Date());
        tPmFinishApply.setProjectId(proj.getId());
        tPmFinishApply.setProjectName(proj.getProjectName());
        tPmFinishApply.setSourceUnit(proj.getSourceUnit());
        tPmFinishApply.setFeeType(proj.getFeeType().getId());
        tPmFinishApply.setFeeTypeName(((TBFundsPropertyEntity) this.systemService.getEntity(TBFundsPropertyEntity.class, proj.getFeeType().getId())).getFundsName());
        tPmFinishApply.setDeveloperDepartId(proj.getDevDepart().getId());
        tPmFinishApply.setDeveloperDepartName(proj.getDevDepart().getDepartname());
        tPmFinishApply.setProjectManagerId(proj.getProjectManagerId());
        tPmFinishApply.setProjectManager(proj.getProjectManager());
        tPmFinishApply.setAllFee(proj.getAllFee());
        tPmFinishApply.setBeginYear(DateUtils.date2Str(proj.getBeginDate(), new SimpleDateFormat("yyyy")));
        tPmFinishApply.setEndYear(DateUtils.date2Str(proj.getEndDate(), new SimpleDateFormat("yyyy")));
      }
    }
    if(StringUtils.isEmpty(tPmFinishApply.getAttachmentCode())){
        tPmFinishApply.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmFinishApply.getAttachmentCode(), "");
        tPmFinishApply.setAttachments(certificates);
    }
    req.setAttribute("tPmFinishApplyPage", tPmFinishApply);
    return new ModelAndView("com/kingtake/project/finish/tPmFinishApply-edit4ResearchGroup");
  }

  /**
   * 
   * ?????????????????????
   * 
   * @param tPmFinishApply
   * @param request
   * @return
   */
  @RequestMapping(params = "goAudit")
  public ModelAndView goAudit(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest request) {
    if (StringUtils.isNotEmpty(tPmFinishApply.getId())) {
      tPmFinishApply = this.systemService.get(TPmFinishApplyEntity.class, tPmFinishApply.getId());
      request.setAttribute("id", tPmFinishApply.getId());
      request.setAttribute("sptype", "jt");
      if (ApprovalConstant.APPRSTATUS_FINISH.equals(tPmFinishApply.getAuditStatus())) {
        request.setAttribute("send", "false");
      }
    }
    return new ModelAndView("com/kingtake/project/finish/tPmFinishApply-sendAudit");
  }
 

  /**
   * ????????????????????????????????????tab????????????
   * 
   * @return
   */
  @RequestMapping(params = "goReceiveTab")
  public ModelAndView goReceiveTab(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/project/finish/tPmFinishApply-receiveTab");
  }

  /**
   * ????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "applyAuditDepartment")
  public ModelAndView applyAuditDepartment(HttpServletRequest request) {
    // ????????????
    // 0???????????????1????????????
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
      return new ModelAndView("com/kingtake/project/finish/applyApprList-receive");
    }
    return new ModelAndView("common/404.jsp");
  }

  /**
   * easyui AJAX???????????? ????????????
   * 
   * @param request
   * @param response
   * @param dataGrid
   * @param user
   */
  @RequestMapping(params = "auditList")
  public void auditList(TPmFinishApplyEntity finishApplyEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    String datagridType = request.getParameter("datagridType");
    if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {// ????????????

      String operateStatus = request.getParameter("operateStatus");
      TSUser user = ResourceUtil.getSessionUserName();// ???????????????????????????

      StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
      StringBuffer resultSql = new StringBuffer();
      resultSql.append("SELECT APPR.ID AS APPR_ID, APPR.PROJECT_ID AS PROJECTID, p.PROJECT_NAME as projectname, APPR.AUDIT_STATUS auditStatus, \n");
      resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
      resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
      resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

      String temp = "FROM t_pm_finish_apply APPR, t_pm_project p ,T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
          + "WHERE APPR.ID = SEND.APPR_ID AND appr.project_id=p.id and SEND.ID = RECEIVE.SEND_ID AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
          + "AND RECEIVE.RECEIVE_USERID = ?  ";

      if (SrmipConstants.YES.equals(operateStatus)) {
        // ?????????
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
      } else if (SrmipConstants.NO.equals(operateStatus)) {
        // ??????????????????????????????
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
      }

      String projectname = request.getParameter("projectName");
      if (StringUtil.isNotEmpty(projectname)) {
        temp += " AND p.PROJECT_NAME LIKE '%" + projectname + "%'";
      }

      temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
      String[] params = new String[] { user.getId()};

      List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(), dataGrid.getPage(), dataGrid.getRows(), params);
      Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

      dataGrid.setResults(result);
      dataGrid.setTotal(count.intValue());
    }

    TagUtil.datagrid(response, dataGrid);
  }
  
  /**
   * ??????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdateFinishApply4Department")
  public ModelAndView goUpdateFinishApply4Department(TPmFinishApplyEntity tPmFinishApply, HttpServletRequest req) {
      if (StringUtils.isNotEmpty(tPmFinishApply.getId())) {
        tPmFinishApply = this.systemService.get(TPmFinishApplyEntity.class, tPmFinishApply.getId());
      }
      if(StringUtils.isEmpty(tPmFinishApply.getAttachmentCode())){
          tPmFinishApply.setAttachmentCode(UUIDGenerator.generate());
      }else{
          List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmFinishApply.getAttachmentCode(), "");
          tPmFinishApply.setAttachments(certificates);
      }
      req.setAttribute("tPmFinishApplyPage", tPmFinishApply);
      return new ModelAndView("com/kingtake/project/finish/tPmFinishApply-update4Department");
  }
}
