package com.kingtake.office.controller.seal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.seal.TOSealUseEntity;
import com.kingtake.office.service.seal.TOSealUseServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;

/**
 * @Title: Controller
 * @Description: ????????????
 * @author onlineGenerator
 * @date 2016-06-02 19:34:49
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOSealUseController")
public class TOSealUseController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TOSealUseController.class);

  @Autowired
  private TOSealUseServiceI tOSealUseService;
  @Autowired
  private SystemService systemService;
  private String message;
  
  @Autowired
  private TPmApprLogsServiceI tPmApprLogsService;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * ?????????????????? ????????????
   * 
   * @return
   */
  @RequestMapping(params = "tOSealUse")
  public ModelAndView tOSealUse(HttpServletRequest request) {
    return new ModelAndView("com/kingtake/office/seal/tOSealUseList");
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
  public void datagrid(TOSealUseEntity tOSealUse, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TOSealUseEntity.class, dataGrid);
    // ?????????????????????
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSealUse, request.getParameterMap());
    try {
      // ???????????????????????????
      TSUser user = ResourceUtil.getSessionUserName();
      cq.add(Restrictions.eq("createBy", user.getUserName()));
      cq.addOrder("createDate", SortDirection.desc);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tOSealUseService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * ??????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TOSealUseEntity tOSealUse, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tOSealUse = systemService.getEntity(TOSealUseEntity.class, tOSealUse.getId());
    message = "????????????????????????";
    try {
      tOSealUseService.delete(tOSealUse);
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
        TOSealUseEntity tOSealUse = systemService.getEntity(TOSealUseEntity.class, id);
        tOSealUseService.delete(tOSealUse);
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
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TOSealUseEntity tOSealUse, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "????????????????????????";
    try {
      tOSealUseService.save(tOSealUse);
      systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
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
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TOSealUseEntity tOSealUse, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "????????????????????????";
    TOSealUseEntity t = tOSealUseService.get(TOSealUseEntity.class, tOSealUse.getId());
    try {
      MyBeanUtils.copyBeanNotNull2Bean(tOSealUse, t);
      tOSealUseService.saveOrUpdate(t);
      systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
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
  @RequestMapping(params = "doSave")
  @ResponseBody
    public AjaxJson doSave(TOSealUseEntity tOSealUse, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            this.tOSealUseService.saveSealUse(tOSealUse);
        } catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

  /**
   * ??????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TOSealUseEntity tOSealUse, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tOSealUse.getId())) {
      tOSealUse = tOSealUseService.getEntity(TOSealUseEntity.class, tOSealUse.getId());
      req.setAttribute("tOSealUsePage", tOSealUse);
    }
    return new ModelAndView("com/kingtake/office/seal/tOSealUse-add");
  }

  /**
   * ??????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TOSealUseEntity tOSealUse, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tOSealUse.getId())) {
      tOSealUse = tOSealUseService.getEntity(TOSealUseEntity.class, tOSealUse.getId());
      req.setAttribute("tOSealUsePage", tOSealUse);
    }
    return new ModelAndView("com/kingtake/office/seal/tOSealUse-update");
  }

  /**
   * ??????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goEdit")
  public ModelAndView goEdit(TOSealUseEntity tOSealUse, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tOSealUse.getId())) {
      tOSealUse = tOSealUseService.getEntity(TOSealUseEntity.class, tOSealUse.getId());
    } else {
      TSUser user = ResourceUtil.getSessionUserName();
      tOSealUse.setOperatorDepartid(user.getCurrentDepart().getId());
      tOSealUse.setOperatorId(user.getId());
      tOSealUse.setOperatorName(user.getRealName());
      tOSealUse.setApplyDate(DateUtils.getDate());
    }
    req.setAttribute("tOSealUsePage", tOSealUse);
    return new ModelAndView("com/kingtake/office/seal/tOSealUseEdit");
  }

  /**
   * ??????????????????
   * 
   * @return
   */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/office/seal/tOSealUseUpload");
  }

  /**
   * ??????excel
   * 
   * @param request
   * @param response
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(params = "exportXls")
  public String exportXls(TOSealUseEntity tOSealUse, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TOSealUseEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSealUse, request.getParameterMap());
    List<TOSealUseEntity> tOSealUses = this.tOSealUseService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "????????????");
    modelMap.put(NormalExcelConstants.CLASS, TOSealUseEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "?????????:" + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tOSealUses);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * ??????excel ?????????
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TOSealUseEntity tOSealUse, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????");
    modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
    modelMap.put(TemplateExcelConstants.MAP_DATA, null);
    modelMap.put(TemplateExcelConstants.CLASS, TOSealUseEntity.class);
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
        List<TOSealUseEntity> listTOSealUseEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(), TOSealUseEntity.class, params);
        for (TOSealUseEntity tOSealUse : listTOSealUseEntitys) {
          tOSealUseService.save(tOSealUse);
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
   * 
   * ?????????????????????
   * 
   * @param tPmFinishApply
   * @param request
   * @return
   */
  @RequestMapping(params = "goAudit")
  public ModelAndView goAudit(TOSealUseEntity tOSealUse, HttpServletRequest request) {
    if (StringUtils.isNotEmpty(tOSealUse.getId())) {
      tOSealUse = this.systemService.get(TOSealUseEntity.class, tOSealUse.getId());
      request.setAttribute("id", tOSealUse.getId());
      if (ApprovalConstant.APPRSTATUS_FINISH.equals(tOSealUse.getAuditStatus())) {
        request.setAttribute("send", "false");
      }
      }
    return new ModelAndView("com/kingtake/office/seal/tOSealUseSendAudit");
  }

//  @RequestMapping(params = "updateFinishFlag")
//  @ResponseBody
//  public AjaxJson updateFinishFlag(String id, HttpServletRequest request, HttpServletResponse response) {
//    AjaxJson j = this.tOSealUseService.updateFinishFlag(id);
//    return j;
//  }

  /**
   * 
   * ??????????????????
   * 
   * @param tPmFinishApply
   * @param request
   * @return
   */
  @RequestMapping(params = "goAuditList")
  public ModelAndView goAuditList(TOSealUseEntity tOSealUse, HttpServletRequest request) {
    if (StringUtils.isNotEmpty(tOSealUse.getId())) {
      tOSealUse = this.systemService.get(TOSealUseEntity.class, tOSealUse.getId());
      request.setAttribute("id", tOSealUse.getId());
      if (ApprovalConstant.APPRSTATUS_FINISH.equals(tOSealUse.getAuditStatus())) {
        request.setAttribute("send", "false");
      }
    }
    return new ModelAndView("com/kingtake/office/seal/tOSealUseSendAudit");
  }

  /**
   * ????????????????????????????????????????????????????????? ??????????????????????????????
   * 
   * @param id
   * @param request
   * @param response
   * @return
   */
//  @RequestMapping(params = "updateFinishFlag2")
//  @ResponseBody
//  public AjaxJson updateFinishFlag2(String id, HttpServletRequest request, HttpServletResponse response) {
//    AjaxJson j = this.tOSealUseService.updateFinishFlag2(id);
//    return j;
//  }

  /**
   * ??????????????????????????????tab????????????
   * 
   * @return
   */
  @RequestMapping(params = "goReceiveTab")
  public ModelAndView goReceiveTab(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/office/seal/tOSealUseReceiveTab");
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
      return new ModelAndView("com/kingtake/office/seal/applyApprList-receive");
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
  public void auditList(TOSealUseEntity tOSealUse, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    String datagridType = request.getParameter("datagridType");
    if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {// ????????????

      String operateStatus = request.getParameter("operateStatus");
      TSUser user = ResourceUtil.getSessionUserName();// ???????????????????????????

      StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
      StringBuffer resultSql = new StringBuffer();
      resultSql.append("SELECT APPR.ID AS APPR_ID, APPR.MATERIAL_NAME AS MATERIALNAME,  APPR.AUDIT_STATUS auditStatus, \n");

      resultSql.append(" APPR.NUMBER_WORD AS NUMBERWORD, APPR.NUMBER_SYMBOL AS NUMBERSYMBOL,  APPR.PAGES_NUM as PAGESNUM, \n");
      resultSql.append(" APPR.COPIES_NUM AS COPIESNUM, APPR.SECRET_DEGREE AS SECRETDEGREE,  APPR.SEAL_TYPE SEALTYPE, \n");
            resultSql
                    .append(" APPR.ACCORDINGS AS ACCORDINGS,APPR.OPERATOR_NAME operatorName, APPR.CREATE_NAME createname,APPR.CREATE_DATE applyDate, \n");

      resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
      resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
      resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

      String temp = "FROM T_O_SEAL_USE APPR, T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
          + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n" + "AND RECEIVE.RECEIVE_USERID = ?  ";

      if (SrmipConstants.YES.equals(operateStatus)) {
        // ?????????
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
      } else if (SrmipConstants.NO.equals(operateStatus)) {
        // ??????????????????????????????
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
      }

      String projectname = request.getParameter("materialName");
      if (StringUtil.isNotEmpty(projectname)) {
        temp += " AND APPR.MATERIAL_NAME LIKE '%" + projectname + "%'";
      }

      temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
      String[] params = new String[] { user.getId() };

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
  @RequestMapping(params = "goUpdateRealUse4Department")
  public ModelAndView goUpdateRealUse4Department(TOSealUseEntity tOSealUse, HttpServletRequest req) {
    if (tOSealUse.getId() != null) {
      tOSealUse = this.systemService.get(TOSealUseEntity.class, tOSealUse.getId());
      req.setAttribute("tOSealUsePage", tOSealUse);
    }
    return new ModelAndView("com/kingtake/office/seal/tOSealUse-update4Department");
  }
  
  
}
