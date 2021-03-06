package com.kingtake.office.controller.purchaseplanmain;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.purchaseplandetail.TBPurchasePlanDetailEntity;
import com.kingtake.office.entity.purchaseplanmain.TBPurchasePlanMainEntity;
import com.kingtake.office.page.purchaseplanmain.TBPurchasePlanMainPage;
import com.kingtake.office.service.purchaseplanmain.TBPurchasePlanMainServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2016-05-31 18:50:15
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPurchasePlanMainController")
public class TBPurchasePlanMainController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TBPurchasePlanMainController.class);

  @Autowired
  private TBPurchasePlanMainServiceI tBPurchasePlanMainService;
  @Autowired
  private SystemService systemService;

  /**
   * ???????????????????????? ????????????
   * 
   * @return
   */
  @RequestMapping(params = "tBPurchasePlanMain")
  public ModelAndView tBPurchasePlanMain(HttpServletRequest request) {
    return new ModelAndView("com/kingtake/office/purchaseplanmain/tBPurchasePlanMainListForDepart");
  }
  
  /**
   * ???????????????????????? ????????????
   * 
   * @return
   */
  @RequestMapping(params = "tBPurchasePlanMainForResearch")
  public ModelAndView tBPurchasePlanMainForResearch(HttpServletRequest request) {
      String projectId = request.getParameter("projectId");
      if(StringUtils.isNotEmpty(projectId)){
          request.setAttribute("projectId", projectId);
      }
    return new ModelAndView("com/kingtake/office/purchaseplanmain/tBPurchasePlanMainListForResearch");
  }
  
  /**
   * ???????????????????????? ????????????
   * 
   * @return
   */
    @RequestMapping(params = "goAuditForDepartment")
    public ModelAndView goAuditForDepartment(HttpServletRequest request) {
        String type = request.getParameter("type");
        if (StringUtils.isNotEmpty(type)) {
            request.setAttribute("type", type);
        }
        return new ModelAndView("com/kingtake/office/purchaseplanmain/tBPurchasePlanMainList4Audit");
    }

    /**
     * ????????????????????????tab ????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAuditTab")
    public ModelAndView goAuditTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/purchaseplanmain/purchasePlanAudit-tab");
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
  public void datagrid(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TBPurchasePlanMainEntity.class, dataGrid);
    // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPurchasePlanMain,
                request.getParameterMap());
        TSUser user = ResourceUtil.getSessionUserName();
        cq.eq("createBy", user.getUserName());
    cq.addOrder("createDate", SortDirection.desc);
    cq.add();
    this.tBPurchasePlanMainService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  
    /**
     * ?????????????????????
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goForDetail")
    public ModelAndView goForDetail(HttpServletRequest request, HttpServletResponse response) {
        String purchasePlanId = request.getParameter("purchasePlanId");
        if (StringUtils.isNotEmpty(purchasePlanId)) {
            request.setAttribute("purchasePlanId", purchasePlanId);
        }
        return new ModelAndView("com/kingtake/office/purchaseplandetail/purchasePlanDetailList");
    }
    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForDetail")
    public void datagridForDetail(TBPurchasePlanDetailEntity tBPurchasePlanDetail, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBPurchasePlanDetailEntity.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPurchasePlanDetail,
                request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagridForAudit")
    public void datagridForAudit(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String type = request.getParameter("type");
        CriteriaQuery cq = new CriteriaQuery(TBPurchasePlanMainEntity.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPurchasePlanMain,
                request.getParameterMap());
        TSUser user = ResourceUtil.getSessionUserName();
        if ("2".equals(type)) {
            cq.eq("checkUserId", user.getId());
        } else {
            cq.notEq("submitFlag", "0");//????????????????????????
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBPurchasePlanMainService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

  /**
   * ????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBPurchasePlanMain = systemService.getEntity(TBPurchasePlanMainEntity.class, tBPurchasePlanMain.getId());
    String message = "??????????????????????????????";
    try {
      tBPurchasePlanMainService.delMain(tBPurchasePlanMain);
      systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "??????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ??????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doBatchDel")
  @ResponseBody
  public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    String message = "??????????????????????????????";
    try {
      for (String id : ids.split(",")) {
        TBPurchasePlanMainEntity tBPurchasePlanMain = systemService.getEntity(TBPurchasePlanMainEntity.class, id);
        tBPurchasePlanMainService.delMain(tBPurchasePlanMain);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
      }
    } catch (Exception e) {
      e.printStackTrace();
      message = "??????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDetailDel")
    @ResponseBody
    public AjaxJson doBatchDetailDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "????????????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TBPurchasePlanDetailEntity tBPurchasePlanDetail = systemService.getEntity(
                        TBPurchasePlanDetailEntity.class,
                        id);
                systemService.delete(tBPurchasePlanDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????????????????";
            j.setSuccess(false);
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
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TBPurchasePlanMainEntity tBPurchasePlanMain, TBPurchasePlanMainPage tBPurchasePlanMainPage, HttpServletRequest request) {
    List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList = tBPurchasePlanMainPage.getTBPurchasePlanDetailList();
    AjaxJson j = new AjaxJson();
    String message = "????????????";
    try {
            tBPurchasePlanMain.setFinishFlag("2");
            tBPurchasePlanMain.setInputRole("2");//????????????????????????
      tBPurchasePlanMainService.addMain(tBPurchasePlanMain, tBPurchasePlanDetailList);
    } catch (Exception e) {
      e.printStackTrace();
      message = "??????????????????????????????";
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
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TBPurchasePlanMainEntity tBPurchasePlanMain, TBPurchasePlanMainPage tBPurchasePlanMainPage, HttpServletRequest request) {
    List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList = tBPurchasePlanMainPage.getTBPurchasePlanDetailList();
    AjaxJson j = new AjaxJson();
    String message = "??????????????????????????????";
    try {
            TBPurchasePlanMainEntity t = this.tBPurchasePlanMainService.get(TBPurchasePlanMainEntity.class,
                    tBPurchasePlanMain.getId());
            MyBeanUtils.copyBeanNotNull2Bean(tBPurchasePlanMain, t);
            tBPurchasePlanMainService.updateMain(t, tBPurchasePlanDetailList);
    } catch (Exception e) {
      e.printStackTrace();
      message = "??????????????????????????????";
            j.setSuccess(false);
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
  @RequestMapping(params = "doUpdateForResearch")
  @ResponseBody
  public AjaxJson doUpdateForResearch(TBPurchasePlanMainEntity tBPurchasePlanMain,HttpServletRequest request) {
      AjaxJson j = new AjaxJson();
      String message = "??????????????????????????????";
      try {
          this.tBPurchasePlanMainService.savePlan(tBPurchasePlanMain);
      } catch (Exception e) {
          e.printStackTrace();
          message = "??????????????????????????????";
          j.setSuccess(false);
      }
      j.setMsg(message);
      return j;
  }

    /**
     * ??????????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateDetail")
    @ResponseBody
    public AjaxJson doUpdateDetail(TBPurchasePlanDetailEntity tBPurchasePlanDetail, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "????????????????????????????????????";
        try {
            if (StringUtils.isEmpty(tBPurchasePlanDetail.getId())) {
                this.systemService.save(tBPurchasePlanDetail);
            } else {
                TBPurchasePlanDetailEntity t = this.systemService.get(TBPurchasePlanDetailEntity.class,
                        tBPurchasePlanDetail.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBPurchasePlanDetail, t);
                systemService.updateEntitie(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????????????????";
            j.setSuccess(false);
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
    @RequestMapping(params = "doForwardDetail")
    @ResponseBody
    public AjaxJson doForwardDetail(TBPurchasePlanDetailEntity tBPurchasePlanDetail, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setObj(tBPurchasePlanDetail);
        return j;
    }

  /**
   * ????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPurchasePlanMain.getId())) {
      tBPurchasePlanMain = tBPurchasePlanMainService.getEntity(TBPurchasePlanMainEntity.class, tBPurchasePlanMain.getId());
      req.setAttribute("tBPurchasePlanMainPage", tBPurchasePlanMain);
    }
    return new ModelAndView("com/kingtake/office/purchaseplanmain/tBPurchasePlanMain-add");
  }

  /**
   * ????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPurchasePlanMain.getId())) {
      tBPurchasePlanMain = tBPurchasePlanMainService.getEntity(TBPurchasePlanMainEntity.class, tBPurchasePlanMain.getId());
      req.setAttribute("tBPurchasePlanMainPage", tBPurchasePlanMain);
    }
    return new ModelAndView("com/kingtake/office/purchaseplanmain/tBPurchasePlanMain-update");
  }
  
  /**
   * ????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdateForResearch")
  public ModelAndView goUpdateForResearch(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest req) {
      String projectId = req.getParameter("projectId");
      if(StringUtils.isNotEmpty(projectId)){
          TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
          tBPurchasePlanMain.setProjectName(project.getProjectName());
          tBPurchasePlanMain.setPlanDate(new Date());
          if(project.getDutyDepart()!=null){
              tBPurchasePlanMain.setDutyDepartId(project.getDutyDepart().getId());
              tBPurchasePlanMain.setDutyDepartName(project.getDutyDepart().getDepartname());
          }
          tBPurchasePlanMain.setManagerId(project.getProjectManagerId());
          tBPurchasePlanMain.setManagerName(project.getProjectManager());
          tBPurchasePlanMain.setProjectCode(project.getProjectNo());
          tBPurchasePlanMain.setProjectId(project.getId());
      }else if (StringUtil.isNotEmpty(tBPurchasePlanMain.getId())) {
          tBPurchasePlanMain = tBPurchasePlanMainService.getEntity(TBPurchasePlanMainEntity.class, tBPurchasePlanMain.getId());
      }
      req.setAttribute("tBPurchasePlanMainPage", tBPurchasePlanMain);
      String load = req.getParameter("opt");
      if(StringUtils.isNotEmpty(load)){
          req.setAttribute("load", load);
      }
      return new ModelAndView("com/kingtake/office/purchaseplanmain/tBPurchasePlanMain-updateForResearch");
  }

    /**
     * ??????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateDetail")
    public ModelAndView goUpdateDetail(TBPurchasePlanDetailEntity tBPurchasePlanDetail, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBPurchasePlanDetail.getId())) {
            tBPurchasePlanDetail = tBPurchasePlanMainService.getEntity(TBPurchasePlanDetailEntity.class,
                    tBPurchasePlanDetail.getId());
        }
        req.setAttribute("tBPurchasePlanDetailPage", tBPurchasePlanDetail);
        return new ModelAndView("com/kingtake/office/purchaseplandetail/tBPurchasePlanDetail-update");
    }
    
    
    /**
     * ??????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateDetailForResearch")
    public ModelAndView goUpdateDetailForResearch(TBPurchasePlanDetailEntity tBPurchasePlanDetail, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBPurchasePlanDetail.getId())) {
            tBPurchasePlanDetail = tBPurchasePlanMainService.getEntity(TBPurchasePlanDetailEntity.class,
                    tBPurchasePlanDetail.getId());
        }
        req.setAttribute("tBPurchasePlanDetailPage", tBPurchasePlanDetail);
        return new ModelAndView("com/kingtake/office/purchaseplandetail/tBPurchasePlanDetail-updateForResearch");
    }
    
    /**
     * ??????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAddDetailForResearch")
    public ModelAndView goAddDetailForResearch(TBPurchasePlanDetailEntity tBPurchasePlanDetail, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBPurchasePlanDetail.getId())) {
            tBPurchasePlanDetail = tBPurchasePlanMainService.getEntity(TBPurchasePlanDetailEntity.class,
                    tBPurchasePlanDetail.getId());
        }
        req.setAttribute("tBPurchasePlanDetailPage", tBPurchasePlanDetail);
        return new ModelAndView("com/kingtake/office/purchaseplandetail/tBPurchasePlanDetail-addForResearch");
    }

  /**
   * ??????????????????[????????????????????????]
   * 
   * @return
   */
  @RequestMapping(params = "tBPurchasePlanDetailList")
  public ModelAndView tBPurchasePlanDetailList(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest req) {

    // ===================================================================================
    // ????????????
    Object id0 = tBPurchasePlanMain.getId();
    // ===================================================================================
    // ??????-????????????????????????
    String hql0 = "from TBPurchasePlanDetailEntity where 1 = 1 AND pURCHASE_PLAN_ID = ? ";
    try {
      List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailEntityList = systemService.findHql(hql0, id0);
      req.setAttribute("tBPurchasePlanDetailList", tBPurchasePlanDetailEntityList);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return new ModelAndView("com/kingtake/office/purchaseplandetail/tBPurchasePlanDetailList");
  }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createBy", "??????");
        modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS,
 new TemplateExportParams(
"export/template/purchasePlanTemplate.xls"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        modelMap.put(TemplateExcelConstants.CLASS, TBPurchasePlanMainEntity.class);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBPurchasePlanMainEntity tBPurchasePlanMain, HttpServletRequest request,
            HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBPurchasePlanMainEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPurchasePlanMain,
                request.getParameterMap());
        TSUser user = ResourceUtil.getSessionUserName();
        String type = request.getParameter("type");
/*        
        if ("myAudit".equals(type)) {
            cq.eq("checkUserId", user.getId());
        } else {
            cq.eq("createBy", user.getUserName());
        }*/
        
        if ("2".equals(type)) {
            cq.eq("checkUserId", user.getId());
        } else {
            cq.notEq("submitFlag", "0");//????????????????????????
        }
                
        cq.add();
        cq.addOrder("createDate", SortDirection.desc);
        List<TBPurchasePlanMainEntity> purchasePlanList = tBPurchasePlanMainService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TBPurchasePlanMainEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("2016??????**?????????????????????????????????????????????", "????????????:"
                + ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, purchasePlanList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "goSearch")
    public ModelAndView goSearch(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/purchaseplanmain/tBPurchasePlanMainList-read");
    }
    
    /**
     * ??????????????????????????????tab????????????
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
      return new ModelAndView("com/kingtake/office/purchaseplanmain/purchaseReceiveTab");
    }
    
    /**
     * ????????????????????????
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
        return new ModelAndView("com/kingtake/office/purchaseplanmain/applyApprList-receive");
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
    public void auditList(TBPurchasePlanMainEntity purchasePlan, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
      String datagridType = request.getParameter("datagridType");
      if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {// ????????????

        String operateStatus = request.getParameter("operateStatus");
        TSUser user = ResourceUtil.getSessionUserName();// ???????????????????????????

        StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT APPR.ID AS APPR_ID, APPR.Plan_Date PLANDATE, APPR.PROJECT_NAME AS PROJECTNAME,"
                + " APPR.FINISH_FLAG  auditStatus, APPR.PROJECT_CODE   AS PROJECTCODE, "
                + "APPR.DUTY_DEPART_NAME AS DUTYDEPARTNAME, APPR.DUTY_DEPART_ID AS DUTYDEPARTID,"
                + " APPR.MANAGER_NAME as MANAGERNAME, APPR.MANAGER_ID AS MANAGERID, APPR.TOTAL_FUNDS   "
                + " AS TOTALFUNDS, APPR.CREATE_NAME createname,APPR.CREATE_DATE applyDate, \n");

        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_B_PURCHASE_PLAN APPR, T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
            + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n" + "AND RECEIVE.RECEIVE_USERID = ?  ";

        if (SrmipConstants.YES.equals(operateStatus)) {
          // ?????????
          temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
        } else if (SrmipConstants.NO.equals(operateStatus)) {
          // ??????????????????????????????
          temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
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
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "goCode")
    public ModelAndView goCode(TBPurchasePlanDetailEntity tBPurchasePlanDetail, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year =  sdf.format(new Date());
        request.setAttribute("year", year);
        return new ModelAndView("com/kingtake/office/purchaseplanmain/purchasePlan-code");
    }
    
    /**
     * ??????????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doCreateCode")
    @ResponseBody
    public AjaxJson doCreateCode(TBPurchasePlanMainEntity tBPurchasePlanMain,
            TBPurchasePlanMainPage tBPurchasePlanMainPage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "??????????????????????????????";
        try {

            String planIds = request.getParameter("planIds");
            String batch = request.getParameter("batch");
            String year = request.getParameter("year");
            tBPurchasePlanMainService.generateCode(planIds, year, batch);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
