package com.kingtake.project.controller.apprcontractcheck;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
import com.kingtake.project.entity.apprcontractcheck.TPmContractCheckEntity;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.m2income.TPmIncomeNodeEntity;
import com.kingtake.project.service.apprcontractcheck.TPmContractCheckServiceI;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2015-08-31 15:26:43
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractCheckController")
public class TPmContractCheckController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractCheckController.class);

    @Autowired
    private TPmContractCheckServiceI tPmContractCheckService;
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
     * ??????--?????????--?????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "projectAndContractcheck")
    public ModelAndView projectAndContractcheck(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/apprcontractcheck/projectAndContractcheck");
    }

    /**
     * ??????tab??????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tPmContractCheckReceiveTab")
    public ModelAndView tPmContractCheckReceiveTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheckList-receiveTab");
    }

    /**
     * ????????????+??????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tPmContractCheck")
    public ModelAndView tPmContractCheck(HttpServletRequest request) {

        //????????????
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheckList-send");
        }

        //????????????
        //0???????????????1????????????
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
            return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheckList-receive");
        }

        return new ModelAndView("common/404.jsp");
    }

    /**
     * ???????????????????????????????????????????????????????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TPmContractCheckEntity tPmContractCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        try {
            String datagridType = request.getParameter("datagridType");
            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//????????????
                String projectId = request.getParameter("projectId");

                if (StringUtil.isNotEmpty(projectId)) {
                    StringBuffer resultSql = new StringBuffer();
                    resultSql.append("SELECT A.ID, A.CONTRACT_NAME, A.APPLY_UNIT, ");
                    resultSql.append("A.APPROVAL_UNIT, A.START_TIME, A.END_TIME, ");
                    resultSql.append("A.TOTAL_FUNDS, A.FINISH_FLAG AS CONTRACT_STATUS, \n");
                    resultSql.append("C.ID AS CHECK_ID, NVL(C.OPERATION_STATUS, '" + ApprovalConstant.APPRSTATUS_UNAPPR
                            + "') AS CHECK_STATUS, C.CREATE_BY \n");

                    String temp = "FROM T_PM_INCOME_CONTRACT_APPR A LEFT JOIN T_PM_CONTRACT_CHECK C \n"
                            + "ON A.ID = C.CONTRACT_ID \n" + "WHERE A.T_P_ID = ?";

                    List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(),
                            projectId);
                    Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(),
                            new String[] { projectId });

                    dataGrid.setResults(result);
                    dataGrid.setTotal(count.intValue());
                }
            } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//????????????
                String operateStatus = request.getParameter("operateStatus");

                TSUser user = ResourceUtil.getSessionUserName();
                StringBuffer resultSql = new StringBuffer();
                resultSql
                        .append("SELECT APPR.ID AS APPR_ID, APPR.PROJECT_NAME, APPR.CONTRACT_NAME, APPR.ORGANIZATION_UNITNAME, \n");
                resultSql
                        .append("APPR.CONTRACT_AMOUNT, APPR.PAID_MONEY, APPR.WAIT_MONEY, APPR.OPERATION_STATUS, APPR.CONTRACT_ID, \n");
                resultSql.append("APPR.DUTY_DEPARTNAME, APPR.CHECK_TIME, \n");
                resultSql.append("CONTRACT.T_P_ID AS PROJECTID, \n");
                resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
                resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
                resultSql
                        .append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

                String temp = "FROM T_PM_CONTRACT_CHECK APPR, T_PM_INCOME_CONTRACT_APPR CONTRACT, "
                        + "T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                        + "WHERE APPR.CONTRACT_ID = CONTRACT.ID "
                        + "AND APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                        + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                        + "AND RECEIVE.RECEIVE_USERID = ?  ";
                //+ "AND RECEIVE.TABLE_NAME = ? AND RECEIVE.RECEIVE_USERID = ?";

                if (SrmipConstants.YES.equals(operateStatus)) {
                    //?????????
                    temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
                } else if (SrmipConstants.NO.equals(operateStatus)) {
                    //??????????????????????????????
                    temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                            + SrmipConstants.YES;
                }

                temp += "ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
                //???????????????????????????
                /*
                 * String[] params = new String[]{
                 * ApprovalConstant.APPR_TYPE_TABLENAME.get(ApprovalConstant.APPR_TYPE_CHECK), user.getId() };
                 */
                String[] params = new String[] { user.getId()};

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
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmContractCheckEntity tPmContractCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmContractCheck = systemService.getEntity(TPmContractCheckEntity.class, tPmContractCheck.getId());
        message = "??????????????????????????????";
        try {
            tPmContractCheckService.deleteAddAttach(tPmContractCheck);
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
        message = "??????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmContractCheckEntity tPmContractCheck = systemService.getEntity(TPmContractCheckEntity.class, id);
                tPmContractCheckService.deleteAddAttach(tPmContractCheck);
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
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmContractCheckEntity tPmContractCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            tPmContractCheckService.save(tPmContractCheck);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TPmContractCheckEntity tPmContractCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            String checkId = null;
            if (!StringUtil.isNotEmpty(tPmContractCheck.getId())) {
                tPmContractCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                tPmContractCheckService.save(tPmContractCheck);
                checkId = tPmContractCheck.getId();
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } else {
                checkId = tPmContractCheck.getId();
                TPmContractCheckEntity t = tPmContractCheckService.get(TPmContractCheckEntity.class, checkId);
                MyBeanUtils.copyBeanNotNull2Bean(tPmContractCheck, t);
                //??????????????????????????????????????????
                tPmContractCheckService.updateOperateState(t);

                tPmContractCheckService.saveOrUpdate(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }

            //?????????id??????
            j.setObj(checkId);

            //????????????
            String fileKeys = request.getParameter("fileKeys");
            if (StringUtil.isNotEmpty(fileKeys)) {
                CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
                cq.in("id", fileKeys.substring(0, fileKeys.length() - 1).split(","));
                cq.isNull("bid");
                cq.add();
                List<TSFilesEntity> files = systemService.getListByCriteriaQuery(cq, false);
                if (files != null && files.size() > 0) {
                    for (TSFilesEntity file : files) {
                        file.setBid(checkId);
                        systemService.updateEntitie(file);
                    }
                }
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
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmContractCheckEntity tPmContractCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        TPmContractCheckEntity t = tPmContractCheckService.get(TPmContractCheckEntity.class, tPmContractCheck.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmContractCheck, t);
            tPmContractCheckService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "updateOperateStatus")
    @ResponseBody
    public AjaxJson updateOperateStatus(String id, HttpServletRequest request, HttpServletResponse response) {
        return this.tPmContractCheckService.updateOperateStatus(id);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????? ????????????????????????????????????
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateOperateStatus2")
    @ResponseBody
    public AjaxJson updateOperateStatus2(String id, HttpServletRequest request, HttpServletResponse response) {
        return this.tPmContractCheckService.updateOperateStatus2(id);
    }

    /**
     * ???????????????????????????????????????????????????
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
            TPmContractCheckEntity tPmContractCheck = systemService.get(TPmContractCheckEntity.class, id);
            if (tPmContractCheck != null) {
                String apprStatus = tPmContractCheck.getOperationStatus();
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(apprStatus)) {
                    j.setSuccess(false);
                    j.setMsg("?????????????????????????????????????????????????????????????????????????????????????????????");
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                    j.setSuccess(false);
                    j.setMsg("????????????????????????????????????????????????????????????????????????????????????");
                }
            }
        }
        return j;
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goTabAdd")
    public ModelAndView goTabAdd(HttpServletRequest req) {
        String contractId = req.getParameter("contractId");
        req.setAttribute("contractId", contractId);
        return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheckTab-add");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goTabUpdate")
    public ModelAndView goTabUpdate(HttpServletRequest req) {
        String checkId = req.getParameter("checkId");
        req.setAttribute("checkId", checkId);

        TSUser user = ResourceUtil.getSessionUserName();

        String roleType = ApprovalConstant.APPR_ROLE_OTHER;
        String datagridType = req.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {
            String creatBy = req.getParameter("creatBy");
            if (user.getUserName().equals(creatBy)) {
                roleType = ApprovalConstant.APPR_ROLE_FAQI;
            }
        } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {
            String handlerType = req.getParameter("handlerType");
            if (ApprovalConstant.HANDLER_TYPE_JIGUAN.equals(handlerType)) {
                roleType = ApprovalConstant.APPR_ROLE_JIGUAN;
            } else if (ApprovalConstant.HANDLER_TYPE_YANXI.equals(handlerType)) {
                roleType = ApprovalConstant.APPR_ROLE_YUANXI;
            }

        }
        req.setAttribute("roleType", roleType);

        return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheckTab-update");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmContractCheckEntity tPmContractCheck, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmContractCheck.getId())) {
            tPmContractCheck = tPmContractCheckService
                    .getEntity(TPmContractCheckEntity.class, tPmContractCheck.getId());
            req.setAttribute("tPmContractCheckPage", tPmContractCheck);
        }
        String contractId = tPmContractCheck.getContractId();
        if (StringUtil.isNotEmpty(contractId)) {
            TPmIncomeContractApprEntity contract = systemService.get(TPmIncomeContractApprEntity.class, contractId);

            if (contract != null) {
                req.setAttribute("contract", contract);

                //???????????????????????????
                BigDecimal payAmounts = new BigDecimal(0);
                List<TPmContractNodeEntity> nodes = contract.getTPmContractNodes();
                if (nodes != null && nodes.size() > 0) {
                    for (TPmContractNodeEntity node : nodes) {
                        String incomeIds = node.getProjPayNodeId();
                        if (StringUtil.isNotEmpty(incomeIds)) {
                            BigDecimal payAmount = (BigDecimal) systemService.getSession()
                                    .createCriteria(TPmIncomeNodeEntity.class)
                                    .setProjection(Projections.sum("incomeAmount"))
                                    .add(Restrictions.in("id", incomeIds.split(","))).uniqueResult();
                            payAmounts.add(payAmount);
                        }
                    }
                }
                req.setAttribute("paidMoney", payAmounts);//????????????
                req.setAttribute("waitMoney", contract.getTotalFunds().subtract(payAmounts));//????????????

                //????????????
                String contractType = contract.getContractType();
                req.setAttribute("contractTypeArray", this.contractMap(contractType));

                //????????????????????????????????????????????????
                List<Map<String, Object>> apprTypes = tPmContractCheckService.getSendButtons(
                        ApprovalConstant.APPR_TYPE_CHECK, ApprovalConstant.APPR_ROLE_FAQI);
                req.setAttribute("apprTypes", apprTypes);
                req.setAttribute("HANDLER_TYPE_HANDLER", ApprovalConstant.HANDLER_TYPE_HANDLER);
                req.setAttribute("HANDLER_TYPE_JIGUAN", ApprovalConstant.HANDLER_TYPE_JIGUAN);
                req.setAttribute("HANDLER_TYPE_YANXI", ApprovalConstant.HANDLER_TYPE_YANXI);
            }
        }
        req.setAttribute("attachmentCode", UUIDGenerator.generate());
        return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheck-add");
    }

    @RequestMapping(params = "goAddLogs")
    public ModelAndView goAddLogs(TPmApprSendLogsEntity tPmApprSendLogs, HttpServletRequest req) {
        String apprId = tPmApprSendLogs.getApprId();
        if (StringUtil.isNotEmpty(apprId)) {
            //?????????????????????????????????????????????
            TPmContractCheckEntity tPmContractCheck = tPmContractCheckService.getEntity(TPmContractCheckEntity.class,
                    apprId);
            String tip = tPmContractCheckService.addBackLog(tPmContractCheck.getBackLog(), "??????????????????");
            tPmApprSendLogs.setSenderTip(tip);

            //???????????????
            tPmApprSendLogs.setTableName(ApprovalConstant.APPR_TYPE_TABLENAME.get(ApprovalConstant.APPR_TYPE_CHECK));

            req.setAttribute("tPmApprSendLogsPage", tPmApprSendLogs);

            //????????????????????????
            Map<String, Object> apprTypeInfo = tPmContractCheckService.selectApprTypeInfo(req.getParameter("apprType"));
            req.setAttribute("apprTypeInfo", apprTypeInfo);

            TSUser receiver = new TSUser();
            //??????????????????????????????????????????
            TPmIncomeContractApprEntity contract = systemService.get(TPmIncomeContractApprEntity.class,
                    tPmContractCheck.getContractId());
            String ifStaff = "yes";
            if (ApprovalConstant.HANDLER_TYPE_JIGUAN.equals(apprTypeInfo.get("HANDLER_TYPE"))
                    && StringUtil.isNotEmpty(contract.getProject().getOfficeStaff())) {
                receiver = systemService.get(TSUser.class, contract.getProject().getOfficeStaff());
            } else if (ApprovalConstant.HANDLER_TYPE_YANXI.equals(apprTypeInfo.get("HANDLER_TYPE"))
                    && StringUtil.isNotEmpty(contract.getProject().getDepartStaff())) {
                receiver = systemService.get(TSUser.class, contract.getProject().getDepartStaff());
            } else {
                ifStaff = "no";
            }
            req.setAttribute("ifStaff", ifStaff);
            req.setAttribute("receiver", receiver);
        }

        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogs-add");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmContractCheckEntity tPmContractCheck, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmContractCheck.getId())) {
            tPmContractCheck = tPmContractCheckService
                    .getEntity(TPmContractCheckEntity.class, tPmContractCheck.getId());
            req.setAttribute("tPmContractCheckPage", tPmContractCheck);
            if (tPmContractCheck != null) {
                //????????????
                String contractType = tPmContractCheck.getContractType();
                req.setAttribute("contractTypeArray", this.contractMap(contractType));
            }
            TPmIncomeContractApprEntity contract = systemService.get(TPmIncomeContractApprEntity.class, tPmContractCheck.getContractId());
            req.setAttribute("contract", contract);

            if(StringUtils.isEmpty(tPmContractCheck.getAttachmentCode())){
                tPmContractCheck.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmContractCheck.getAttachmentCode(), "");
                tPmContractCheck.setCertificates(certificates);
            }
        }
        return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheck-update");
    }

    public String[] contractMap(String contractType) {

        String[] fields = null;

        if (SrmipConstants.CONTRACT_YANZHI.equals(contractType)) {
            fields = SrmipConstants.CONTRACT_TYPE.get(SrmipConstants.CONTRACT_YANZHI);
        } else if (SrmipConstants.CONTRACT_YANJIU.equals(contractType)) {
            fields = SrmipConstants.CONTRACT_TYPE.get(SrmipConstants.CONTRACT_YANJIU);
        } else if (SrmipConstants.CONTRACT_JISHUFUWU.equals(contractType)) {
            fields = SrmipConstants.CONTRACT_TYPE.get(SrmipConstants.CONTRACT_JISHUFUWU);
        } else {
            fields = SrmipConstants.CONTRACT_TYPE.get(SrmipConstants.CONTRACT_OTHER);
        }

        return fields;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheckUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmContractCheckEntity tPmContractCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractCheckEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractCheck,
                request.getParameterMap());
        List<TPmContractCheckEntity> tPmContractChecks = this.tPmContractCheckService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmContractCheckEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmContractChecks);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmContractCheckEntity tPmContractCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmContractCheckEntity.class);
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
                List<TPmContractCheckEntity> listTPmContractCheckEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmContractCheckEntity.class, params);
                for (TPmContractCheckEntity tPmContractCheck : listTPmContractCheckEntitys) {
                    tPmContractCheckService.save(tPmContractCheck);
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

}
