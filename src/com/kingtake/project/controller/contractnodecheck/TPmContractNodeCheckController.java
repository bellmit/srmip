package com.kingtake.project.controller.contractnodecheck;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
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

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.contractnodecheck.NodeCheckStep;
import com.kingtake.project.entity.contractnodecheck.TPmContractNchkFlowLogsEntity;
import com.kingtake.project.entity.contractnodecheck.TPmContractNchkReciLogsEntity;
import com.kingtake.project.entity.contractnodecheck.TPmContractNodeCheckEntity;
import com.kingtake.project.entity.contractnodecheck.TPmOutContractNodeCheckEntity;
import com.kingtake.project.entity.m2income.TPmIncomeNodeEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.payapply.TPmPayApplyEntity;
import com.kingtake.project.entity.supplier.TPmQualitySupplierEntity;
import com.kingtake.project.page.contractnode.TPmContractNodePage;
import com.kingtake.project.service.appr.ApprServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.contractnodecheck.TPmContractNodeCheckServiceI;
import com.kingtake.project.service.contractnodecheck.TPmOutContractNodeCheckServiceI;

/**
 * @Title: Controller
 * @Description: ?????????????????????????????????
 * @author onlineGenerator
 * @date 2015-08-28 15:00:11
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractNodeCheckController")
public class TPmContractNodeCheckController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractNodeCheckController.class);

    @Autowired
    private TPmContractNodeCheckServiceI tPmContractNodeCheckService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    private String message;
    
    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
    
    @Autowired
    private ApprServiceI apprService;
    
    @Autowired
    private TPmOutContractNodeCheckServiceI outcomeContractNodeCheckService;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @RequestMapping(params = "tPmContractNodeCheckReceiveTab")
    public ModelAndView tPmContractNodeCheckReceiveTab(HttpServletRequest request) {
        request.setAttribute("untreated", ProjectConstant.OPERATE_UNTREATED);
        request.setAttribute("treated", ProjectConstant.OPERATE_TREATED);
        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheckList-receiveTab");
    }

    /**
     * ????????????????????????tab?????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "outcomeContractNodeCheckReceiveTab")
    public ModelAndView outcomeContractNodeCheckReceiveTab(HttpServletRequest request) {
        request.setAttribute("untreated", ProjectConstant.OPERATE_UNTREATED);
        request.setAttribute("treated", ProjectConstant.OPERATE_TREATED);
        return new ModelAndView("com/kingtake/project/contractnodecheck/outcomeContractNodeCheckList-receiveTab");
    }

    /**
     * ??????????????????tab?????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "payApplyCheckReceiveTab")
    public ModelAndView payApplyCheckReceiveTab(HttpServletRequest request) {
        request.setAttribute("untreated", ProjectConstant.OPERATE_UNTREATED);
        request.setAttribute("treated", ProjectConstant.OPERATE_TREATED);
        return new ModelAndView("com/kingtake/project/contractnodecheck/payApplyCheckList-receiveTab");
    }

    /**
     * ??????????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmContractNodeCheck")
    public ModelAndView tPmContractNodeCheck(HttpServletRequest request) {
        //????????????
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheckList");
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
            return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheckList-receive");
        }

        return new ModelAndView("common/404.jsp");
    }

    /**
     * ??????????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "outcomeContractNodeCheck")
    public ModelAndView outcomeContractNodeCheck(HttpServletRequest request) {
        //????????????
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            return new ModelAndView("com/kingtake/project/contractnodecheck/outcomeContractNodeCheckList");
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
            return new ModelAndView("com/kingtake/project/contractnodecheck/outcomeContractNodeCheckList-receive");
        }

        return new ModelAndView("common/404.jsp");
    }

    /**
     * ??????????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "payApplyCheckReceiveList")
    public ModelAndView payApplyCheckReceiveList(HttpServletRequest request) {
        //????????????
        //0???????????????1????????????
        String type = request.getParameter("type");
        
        if (StringUtil.isNotEmpty(type)) {
            request.setAttribute("type", type);
            request.setAttribute("YES", SrmipConstants.YES);
            request.setAttribute("NO", SrmipConstants.NO);
            return new ModelAndView("com/kingtake/project/contractnodecheck/payApplyCheckList-receive");
        }

        return new ModelAndView("common/404.jsp");
    }

    @RequestMapping(params = "contractNodeRefToInOrPayNode")
    public ModelAndView contractNodeRefToInOrPayNode(HttpServletRequest request) {
        String inOutFlag = request.getParameter("inOutFlag");
        if ("i".equals(inOutFlag)) {
            request.setAttribute("inOutFlag", ProjectConstant.INCOME_CONTRACT);
        } else if ("o".equals(inOutFlag)) {
            request.setAttribute("inOutFlag", ProjectConstant.OUTCOME_CONTRACT);
        }
        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeRefToInOrPayNode");
    }

    @RequestMapping(params = "contractAndContractNode")
    public ModelAndView contractAndContractNode(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);

        String inOutFlag = request.getParameter("inOutFlag");
        request.setAttribute("inOutFlag", inOutFlag);

        if (ProjectConstant.INCOME_CONTRACT.equals(inOutFlag)) {
            request.setAttribute("income", true);
        } else {
            request.setAttribute("income", false);
        }

        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractAndContractNodeList");
    }

    @RequestMapping(params = "goContractNodeCheckList")
    public ModelAndView goContractNodeCheckList(HttpServletRequest req) {

        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheckList");
    }

    @RequestMapping(params = "nodedatagrid")
    public void nodedatagrid(TPmContractNodeEntity tPmContractNode, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractNodeEntity.class, dataGrid);
        //?????????????????????
        //        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractNode,
        //                request.getParameterMap());
        try {
            //???????????????????????????
            String projectId = request.getParameter("projectId");
            if (StringUtil.isNotEmpty(projectId)) {
                String sql1 = "SELECT ID FROM T_PM_INCOME_CONTRACT_APPR WHERE T_P_ID = '" + projectId + "'";
                //                String sql2 = "SELECT ID FROM T_PM_OUTCOME_CONTRACT_APPR WHERE T_P_ID = '" + projectId + "'";
                List<String> list1 = systemService.findListbySql(sql1);
                //                List<String> list2 = systemService.findListbySql(sql2);
                //                list1.addAll(list2);
                if (list1 != null && list1.size() > 0) {
                    cq.in("inOutContractid", list1.toArray());
                } else {
                    cq.eq("id", "index");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TPmContractNodeEntity> list3 = new ArrayList<TPmContractNodeEntity>();
        list3 = systemService.getListByCriteriaQuery(cq, false);
        List<TPmContractNodePage> list4 = new ArrayList<TPmContractNodePage>();
        for (TPmContractNodeEntity node : list3) {
            Long i = systemService
                    .getCountForJdbc("select count(1) from t_pm_contract_node_check t where t.CONTRACT_NODE_ID = '"
                            + node.getId() + "'");
            TPmContractNodePage nodePage = new TPmContractNodePage();
            if (i > 0) {
                nodePage.setCheckFlag("1");
            } else {
                nodePage.setCheckFlag("0");
            }
            nodePage.setId(node.getId());
            nodePage.setProjPayNodeId(node.getProjPayNodeId());
            nodePage.setContractNodeName(node.getContractNodeName());
            nodePage.setPrjType(node.getPrjType());
            nodePage.setPlanContractFlag(node.getPlanContractFlag());
            nodePage.setInOutFlag(node.getInOutFlag());
            nodePage.setResultForm(node.getResultForm());
            nodePage.setEvaluationMethod(node.getEvaluationMethod());
            nodePage.setCompleteDate(node.getCompleteDate());
            nodePage.setRemarks(node.getRemarks());
            nodePage.setCreateName(node.getCreateName());
            nodePage.setCreateDate(node.getCreateDate());
            if (ProjectConstant.INCOME_CONTRACT.equals(node.getInOutFlag())) {//??????
                nodePage.setContractName(systemService
                        .get(TPmIncomeContractApprEntity.class, node.getInOutContractid()).getContractName());
            } /*
               * else {//?????? nodePage.setContractName(systemService.get(TPmOutcomeContractApprEntity.class,
               * node.getInOutContractid()).getContractName()); }
               */
            list4.add(nodePage);
        }
        dataGrid.setTotal(list4.size());
        dataGrid.setResults(list4);
        //        systemService.getDataGridReturn(cq, true);
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

    @RequestMapping(params = "datagrid")
    public void datagrid(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        try {
            String datagridType = request.getParameter("datagridType");
            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//????????????
                String projectId = request.getParameter("projectId");

                if (StringUtil.isNotEmpty(projectId)) {
                    StringBuffer resultSql = new StringBuffer();
                    resultSql.append("SELECT NODE.ID, CONTRACT.CONTRACT_NAME, NODE.NODE_NAME, NODE.COMPLETE_DATE, \n");
                    resultSql.append("NODE.IN_OUT_CONTRACTID, NODECHECK.ID AS CHECK_ID, \n");
                    resultSql.append("NVL(NODECHECK.OPERATION_STATUS, '" + ApprovalConstant.APPRSTATUS_UNAPPR
                            + "') AS CHECK_STATUS \n");
                    StringBuffer temp = new StringBuffer();
                    temp.append("FROM T_PM_CONTRACT_NODE NODE \n");
                    temp.append("LEFT JOIN T_PM_CONTRACT_NODE_CHECK NODECHECK ON NODE.ID = NODECHECK.CONTRACT_NODE_ID \n");
                    temp.append("JOIN T_PM_INCOME_CONTRACT_APPR CONTRACT ON NODE.IN_OUT_CONTRACTID = CONTRACT.ID \n");
                    temp.append("WHERE CONTRACT.T_P_ID = ? and CONTRACT.FINISH_FLAG=? ORDER BY NODE.COMPLETE_DATE");

                    List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(),
                            new Object[] { projectId, ApprovalConstant.APPRSTATUS_FINISH });
                    Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), new String[] {
                            projectId, ApprovalConstant.APPRSTATUS_FINISH });

                    dataGrid.setResults(result);
                    dataGrid.setTotal(count.intValue());
                }
            } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//????????????
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
                        + "AND RECEIVE.RECEIVE_USERID = ?  ";

                if (SrmipConstants.YES.equals(operateStatus)) {
                    //?????????
                    temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
                } else if (SrmipConstants.NO.equals(operateStatus)) {
                    //??????????????????????????????
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

                temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
                //???????????????????????????
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
     * ????????????????????????????????????
     * 
     * @param tPmContractNodeCheck
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "outcomeContractNodeDatagrid")
    public void outcomeContractNodeDatagrid(TPmContractNodeCheckEntity tPmContractNodeCheck,
            HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        try {
            String datagridType = request.getParameter("datagridType");
            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//????????????
                String projectId = request.getParameter("projectId");

                if (StringUtil.isNotEmpty(projectId)) {
                    StringBuffer resultSql = new StringBuffer();
                    resultSql.append("SELECT NODE.ID, CONTRACT.CONTRACT_NAME, NODE.NODE_NAME, NODE.COMPLETE_DATE, \n");
                    resultSql.append("NODE.IN_OUT_CONTRACTID, NODECHECK.ID AS CHECK_ID, \n");
                    resultSql.append("NVL(NODECHECK.AUDIT_FLAG, '" + ApprovalConstant.APPRSTATUS_UNAPPR
                            + "') AS CHECK_STATUS, \n");
                    resultSql.append("NVL(NODECHECK.PAY_FLAG, '" + ApprovalConstant.APPRSTATUS_UNAPPR
                            + "') AS PAY_STATUS \n");
                    StringBuffer temp = new StringBuffer();
                    temp.append("FROM T_PM_CONTRACT_NODE NODE \n");
                    temp.append("LEFT JOIN T_PM_OUTCONTRACT_NODE_CHECK NODECHECK ON NODE.ID = NODECHECK.CONTRACT_NODE_ID \n");
                    temp.append("JOIN T_PM_OUTCOME_CONTRACT_APPR CONTRACT ON NODE.IN_OUT_CONTRACTID = CONTRACT.ID \n");
                    temp.append("WHERE CONTRACT.T_P_ID = ? ORDER BY CONTRACT.Create_Date desc,NODE.COMPLETE_DATE");

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
                        .append("SELECT APPR.ID AS APPR_ID, APPR.CONTRACT_NAME, APPR.Contract_Total_Funds, APPR.Jdje, \n");
                resultSql
                        .append("APPR.Finish_Time, APPR.audit_flag, \n");
                resultSql
                        .append("APPR.CONTRACT_NODE_ID, APPR.PROJECT_NAME, NODE.NODE_NAME, NODE.IN_OUT_CONTRACTID, \n");
                resultSql.append("CONTRACT.T_P_ID AS PROJECTID, \n");
                resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
                resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
                resultSql
                        .append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

                String temp = "FROM T_PM_CONTRACT_NODE NODE, T_PM_OUTCONTRACT_NODE_CHECK APPR, t_pm_outcome_contract_appr CONTRACT, "
                        + "T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                        + "WHERE NODE.ID = APPR.CONTRACT_NODE_ID AND NODE.IN_OUT_CONTRACTID = CONTRACT.ID "
                        + "AND APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                        + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                        + "AND RECEIVE.RECEIVE_USERID = ?  ";

                if (SrmipConstants.YES.equals(operateStatus)) {
                    //?????????
                    temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
                } else if (SrmipConstants.NO.equals(operateStatus)) {
                    //??????????????????????????????
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

                temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
                //???????????????????????????
                String[] params = new String[] { user.getId() };

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
     * ??????????????????????????????
     * 
     * @param tPmContractNodeCheck
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "payApplyCheckDatagrid")
    public void payApplyCheckDatagrid(TPmOutContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String datagridType = request.getParameter("datagridType");
        CriteriaQuery cq = new CriteriaQuery(TPmOutContractNodeCheckEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractNodeCheck,
                                request.getParameterMap());
        if ("0".equals(datagridType)) {
            cq.eq("payFlag", "0");//?????????
        } else if ("1".equals(datagridType)) {
            cq.notEq("payFlag", "0");//?????????
        }
        cq.eq("jhcshrId", user.getId());
        cq.add();
        List<TPmOutContractNodeCheckEntity> result = this.systemService.getListByCriteriaQuery(cq, false);
        dataGrid.setResults(result);
        dataGrid.setTotal(result.size());
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "ifEdit")
    @ResponseBody
    public AjaxJson ifEdit(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String editFlag = "0";//?????????
        if (StringUtil.isNotEmpty(tPmContractNodeCheck.getId())) {
            tPmContractNodeCheck = systemService.getEntity(TPmContractNodeCheckEntity.class,
                    tPmContractNodeCheck.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            if (!tPmContractNodeCheck.getOperationStatus().equals(ApprovalConstant.APPRSTATUS_FINISH)
                    && user.getUserName().equals(tPmContractNodeCheck.getCreateBy())) {//???????????????????????????????????????
                editFlag = "2";//??????????????????
                CriteriaQuery rcq = new CriteriaQuery(TPmContractNchkReciLogsEntity.class);
                rcq.eq("contractNodeId", tPmContractNodeCheck.getId());
                rcq.eq("receiveUserid", user.getId());
                rcq.eq("operateStatus", ProjectConstant.OPERATE_UNTREATED);
                rcq.add();
                List<TPmContractNchkReciLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
                if (rlist.size() > 0) {
                    if (rlist.get(0).getOperateStatus().equals(ProjectConstant.OPERATE_UNTREATED)) {//??????????????????????????????????????????????????????????????????????????????
                        editFlag = "1";//????????????????????????
                    }
                }
            } else {//??????????????????????????????
                if (!tPmContractNodeCheck.getOperationStatus().equals(ApprovalConstant.APPRSTATUS_FINISH)
                        && req.getParameter("operateStatus").equals(ProjectConstant.OPERATE_UNTREATED)) {//???????????????????????????????????????????????????
                    editFlag = "1";
                }
            }
        }
        j.setObj(editFlag);
        j.setSuccess(true);
        return j;
    }

    @RequestMapping(params = "goOperate")
    public ModelAndView Operate(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmContractNodeCheck.getId())) {
            tPmContractNodeCheck = systemService.getEntity(TPmContractNodeCheckEntity.class,
                    tPmContractNodeCheck.getId());
            /*
             * CriteriaQuery rcq = new CriteriaQuery(TPmContractNodeCheckEntity.class); rcq.eq("contractNodeId",
             * tPmContractNodeCheck.getId()); rcq.eq("operateStatus", ProjectConstant.OPERATE_UNTREATED);
             * rcq.eq("validFlag", SrmipConstants.YES);
             */

            req.setAttribute("tPmContractNodeCheckPage", tPmContractNodeCheck);
        }
        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheck-operate");
    }

    @RequestMapping(params = "goInput")
    public ModelAndView goInput(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmContractNodeCheck.getId())) {
            tPmContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmContractNodeCheckEntity.class,
                    tPmContractNodeCheck.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery rcq = new CriteriaQuery(TPmContractNchkReciLogsEntity.class);
            rcq.eq("contractNodeId", tPmContractNodeCheck.getId());
            rcq.eq("receiveUserid", user.getId());
            rcq.eq("operateStatus", ProjectConstant.OPERATE_UNTREATED);
            rcq.eq("validFlag", SrmipConstants.YES);
            rcq.add();
            List<TPmContractNchkReciLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            if (rlist.size() > 0) {
                String suggestionType = "";
                if (rlist.get(0).getSuggestionType().equals(ProjectConstant.AUDIT_RESEARCH)) {
                    suggestionType = ProjectConstant.reviewMap.get(ProjectConstant.AUDIT_RESEARCH);
                } else if (rlist.get(0).getSuggestionType().equals(ProjectConstant.AUDIT_DUTY)) {
                    suggestionType = ProjectConstant.reviewMap.get(ProjectConstant.AUDIT_DUTY);
                } else {
                    suggestionType = ProjectConstant.reviewMap.get(ProjectConstant.AUDIT_SCIENTIFIC);
                }
                req.setAttribute("receive", rlist.get(0));
                req.setAttribute("suggestionType", suggestionType);
            }
        }
        return new ModelAndView("com/kingtake/project/contractnodecheck/inputSuggestion");
    }

    @RequestMapping(params = "doSuggestion")
    @ResponseBody
    public AjaxJson doSuggestion(String rid, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????";
        if (StringUtil.isNotEmpty(rid)) {
            try {
                TPmContractNchkReciLogsEntity receive = systemService.get(TPmContractNchkReciLogsEntity.class, rid);
                receive.setSuggestionContent(req.getParameter("suggestionContent"));
                receive.setSuggestionCode(req.getParameter("suggestionCode"));
                TPmContractNchkFlowLogsEntity send = systemService.get(TPmContractNchkFlowLogsEntity.class,
                        receive.getNchkFlowId());
                TPmContractNodeCheckEntity rb = systemService.get(TPmContractNodeCheckEntity.class,
                        receive.getContractNodeId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (req.getParameter("suggestionCode").equals(ReceiveBillConstant.AUDIT_REBUT)) {
                    //???????????????????????????????????????????????????????????????.
                    TPmContractNodeCheckEntity check = systemService.get(TPmContractNodeCheckEntity.class,
                            receive.getContractNodeId());
                    check.setOperationStatus(ApprovalConstant.APPRSTATUS_REBUT);
                    /*
                     * TSUser user = ResourceUtil.getSessionUserName(); check.setBackUserid(user.getId());
                     * check.setBackUsername(user.getRealName());
                     */
                    systemService.updateEntitie(check);
                    CriteriaQuery cq = new CriteriaQuery(TPmContractNchkReciLogsEntity.class);
                    cq.eq("contractNodeId", receive.getContractNodeId());
                    cq.add();
                    List<TPmContractNchkReciLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
                    if (list.size() > 0) {
                        for (TPmContractNchkReciLogsEntity fr : list) {
                            fr.setValidFlag(SrmipConstants.NO);
                            systemService.updateEntitie(fr);
                            tOMessageService.sendMessage(
                                    fr.getReceiveUserid(),
                                    "??????????????????????????????",
                                    "??????????????????",
                                    "????????????[" + rb.getContractName() + "]???????????????\n????????????" + receive.getReceiveUsername()
                                            + "\n???????????????" + receive.getSuggestionContent() + "\n???????????????"
                                            + sdf.format(receive.getOperateTime()), null);
                        }
                    }
                }
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
                receive.setOperateTime(new Date());
                systemService.updateEntitie(receive);
                tOMessageService.sendMessage(send.getOperateUserid(), "??????????????????????????????", "??????????????????",
                        "??????????????????[" + rb.getContractName()
                        + "]?????????\n????????????" + receive.getReceiveUsername() + "\n???????????????" + receive.getSuggestionContent()
                        + "\n???????????????" + sdf.format(receive.getOperateTime()), receive.getReceiveUserid());
            } catch (Exception e) {
                message = "?????????????????????";
                e.printStackTrace();
            }
        } else {
            message = "?????????????????????";
        }
        return j;
    }

    @RequestMapping(params = "getStepList")
    public ModelAndView getStepList(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest req) {
        tPmContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmContractNodeCheckEntity.class,
                tPmContractNodeCheck.getId());
        CriteriaQuery scq = new CriteriaQuery(TPmContractNchkFlowLogsEntity.class);
        scq.eq("contractNodeId", tPmContractNodeCheck.getId());
        scq.addOrder("operateDate", SortDirection.asc);
        scq.add();
        List<TPmContractNchkFlowLogsEntity> slist = systemService.getListByCriteriaQuery(scq, false);
        List<NodeCheckStep> stepList = new ArrayList<NodeCheckStep>();
        for (int i = 0; i < slist.size(); i++) {
            NodeCheckStep step = new NodeCheckStep();
            TPmContractNchkFlowLogsEntity send = slist.get(i);
            step.setOperateUsername(send.getOperateUsername());
            step.setOperateDate(send.getOperateDate());
            step.setOperateDepartname(send.getOperateDepartname());
            step.setOperateIp(send.getOperateIp());
            step.setSenderTip(send.getSenderTip());
            CriteriaQuery rcq = new CriteriaQuery(TPmContractNchkReciLogsEntity.class);
            rcq.eq("nchkFlowId", send.getId());
            rcq.or(Restrictions.eq("validFlag", SrmipConstants.YES),
                    Restrictions.eq("operateStatus", ReceiveBillConstant.OPERATE_TREATED));//??????????????????????????????????????????
            //            rcq.eq("validFlag", SrmipConstants.YES);
            rcq.addOrder("operateStatus", SortDirection.desc);
            rcq.addOrder("operateTime", SortDirection.desc);
            rcq.add();
            List<TPmContractNchkReciLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            step.setRlist(rlist);
            stepList.add(step);
        }
        return new ModelAndView("com/kingtake/project/contractnodecheck/nodeCheckStepList", "stepList", stepList);

    }

    @RequestMapping(params = "archive")
    @ResponseBody
    public AjaxJson archive(TPmContractNodeCheckEntity nodeCheck, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "???????????????";
        try {
            if (StringUtil.isNotEmpty(nodeCheck.getId())) {
                TSUser user = ResourceUtil.getSessionUserName();
                nodeCheck = systemService.get(TPmContractNodeCheckEntity.class, nodeCheck.getId());
                nodeCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_FINISH);
                nodeCheck.setFinishTime(new Date());
                nodeCheck.setFinishUserid(user.getId());
                nodeCheck.setFinishUsername(user.getRealName());
                systemService.updateEntitie(nodeCheck);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = "???????????????";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmContractNodeCheck = systemService.getEntity(TPmContractNodeCheckEntity.class, tPmContractNodeCheck.getId());
        message = "?????????????????????????????????????????????";
        try {
            tPmContractNodeCheckService.deleteAddAttach(tPmContractNodeCheck);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????????????????";
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
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmContractNodeCheckEntity tPmContractNodeCheck = systemService.getEntity(
                        TPmContractNodeCheckEntity.class, id);
                tPmContractNodeCheckService.deleteAddAttach(tPmContractNodeCheck);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????????????????";
        try {
            if (StringUtil.isEmpty(tPmContractNodeCheck.getOperationStatus())) {
                tPmContractNodeCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_UNSEND);
            }
            tPmContractNodeCheckService.save(tPmContractNodeCheck);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tPmContractNodeCheck);
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????????????????????????????";
        try {
            if (StringUtils.isEmpty(tPmContractNodeCheck.getId())) {
                if (StringUtil.isEmpty(tPmContractNodeCheck.getOperationStatus())) {
                    tPmContractNodeCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                }
                this.systemService.save(tPmContractNodeCheck);
            } else {
                TPmContractNodeCheckEntity t = tPmContractNodeCheckService.get(TPmContractNodeCheckEntity.class,
                        tPmContractNodeCheck.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmContractNodeCheck, t);
                tPmContractNodeCheckService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
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
            TPmContractNodeCheckEntity tPmContractNodeCheck = systemService.get(TPmContractNodeCheckEntity.class, id);
            if (tPmContractNodeCheck != null) {
                String apprStatus = tPmContractNodeCheck.getOperationStatus();
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
     * ???????????????????????????????????????????????????
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updatePayFlag")
    @ResponseBody
    public AjaxJson updatePayFlag(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmPayApplyEntity payApply = systemService.get(TPmPayApplyEntity.class, id);
            if (payApply != null) {
                String apprStatus = payApply.getOperateStatus();
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(apprStatus)) {
                    j.setSuccess(false);
                    j.setMsg("?????????????????????????????????????????????????????????????????????????????????");
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                    j.setSuccess(false);
                    j.setMsg("???????????????????????????????????????????????????????????????????????????");
                }
            }
        }
        return j;
    }

    @RequestMapping(params = "updateOperateStatus")
    @ResponseBody
    public AjaxJson updateOperateStatus(String id, HttpServletRequest request, HttpServletResponse response) {
        return this.tPmContractNodeCheckService.updateOperateStatus(id);
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
        return this.tPmContractNodeCheckService.updateOperateStatus2(id);
    }

    @RequestMapping(params = "updatePayApplyOperateStatus")
    @ResponseBody
    public AjaxJson updatePayApplyOperateStatus(String id, HttpServletRequest request, HttpServletResponse response) {
        return this.tPmContractNodeCheckService.updatePayApplyOperateStatus(id);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????? ????????????????????????????????????
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updatePayApplyOperateStatus2")
    @ResponseBody
    public AjaxJson updatePayApplyOperateStatus2(String id, HttpServletRequest request, HttpServletResponse response) {
        return this.tPmContractNodeCheckService.updatePayApplyOperateStatus2(id);
    }

    /**
     * ???????????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest req) {
        String contractNodeId = req.getParameter("contractNodeId");
        if (StringUtil.isNotEmpty(contractNodeId)) {
            /*
             * CriteriaQuery cq = new CriteriaQuery(TPmContractNodeCheckEntity.class); cq.eq("contractNodeId",
             * contractNodeId); cq.add(); List<TPmContractNodeCheckEntity> list =
             * systemService.getListByCriteriaQuery(cq, false); if (list.size() > 0) { tPmContractNodeCheck =
             * list.get(0); }
             */
            TPmContractNodeEntity node = systemService.get(TPmContractNodeEntity.class, contractNodeId);
            req.setAttribute("inOutFlag", node.getInOutFlag());
            if ("i".equals(node.getInOutFlag())) {
                TPmIncomeContractApprEntity iappr = systemService.get(TPmIncomeContractApprEntity.class,
                        node.getInOutContractid());
                req.setAttribute("projectId", iappr.getProject().getId());
                req.setAttribute("contractAppr", iappr);
            } else {
                TPmOutcomeContractApprEntity iappr = systemService.get(TPmOutcomeContractApprEntity.class,
                        node.getInOutContractid());
                req.setAttribute("projectId", iappr.getProject().getId());
                req.setAttribute("contractAppr", iappr);
            }
            if (StringUtil.isNotEmpty(node.getProjPayNodeId())) {
                BigDecimal cost = new BigDecimal(0);
                String[] pnids = node.getProjPayNodeId().split(",");
                for (String pnid : pnids) {
                    TPmIncomeNodeEntity inode = systemService.get(TPmIncomeNodeEntity.class, pnid);
                    cost = cost.add(inode.getIncomeAmount());
                }
                req.setAttribute("cost", cost);
            }
            tPmContractNodeCheck.setNodeTime(node.getCompleteDate());
            tPmContractNodeCheck.setContractNodeId(contractNodeId);
        }
        if (StringUtil.isNotEmpty(tPmContractNodeCheck.getId())) {
            tPmContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmContractNodeCheckEntity.class,
                    tPmContractNodeCheck.getId());
        }
        if (StringUtils.isEmpty(tPmContractNodeCheck.getAttachmentCode())) {
            tPmContractNodeCheck.setAttachmentCode(UUIDGenerator.generate());//??????????????????
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tPmContractNodeCheck
.getAttachmentCode(), "");
            tPmContractNodeCheck.setAttachments(fileList);
        }
        req.setAttribute("tPmContractNodeCheckPage", tPmContractNodeCheck);
        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheck-add");
    }

    @RequestMapping(params = "goSend")
    public ModelAndView goSend(String id, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(id)) {
            req.setAttribute("nodeCheckId", id);
        }
        return new ModelAndView("com/kingtake/project/contractnodecheck/sendOrg");
    }

    @RequestMapping(params = "doSend")
    @ResponseBody
    public AjaxJson send(String nodeCheckId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String rid = request.getParameter("receiverid");
        String leaderReview = request.getParameter("leaderReview");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        String suggestionType = request.getParameter("suggestionType");
        String[] rids = rid.split(",");
        String[] dids = departId.split(",");
        String[] dnames = departName.split(",");
        message = "?????????????????????????????????";
        try {

            TSUser user = ResourceUtil.getSessionUserName();
            TPmContractNchkFlowLogsEntity send = new TPmContractNchkFlowLogsEntity();
            send.setOperateUserid(user.getId());
            send.setOperateUsername(user.getRealName());
            send.setOperateDate(new Date());
            String ip = "";
            if (request.getHeader("x-forwarded-for") == null) {
                ip = request.getRemoteAddr();
            } else {
                ip = request.getHeader("x-forwarded-for");
            }
            send.setContractNodeId(nodeCheckId);
            send.setOperateIp(ip);
            send.setOperateDepartid(user.getCurrentDepart().getId());
            send.setOperateDepartname(user.getCurrentDepart().getDepartname());
            send.setSenderTip(leaderReview);
            systemService.save(send);
            for (int i = 0; i < rids.length; i++) {
                TPmContractNchkReciLogsEntity receive = new TPmContractNchkReciLogsEntity();
                receive.setNchkFlowId(send.getId());
                receive.setContractNodeId(nodeCheckId);
                TSUser receiver = systemService.get(TSUser.class, rids[i]);
                receive.setReceiveUserid(receiver.getId());
                receive.setReceiveDepartid(dids[i]);
                receive.setValidFlag(SrmipConstants.YES);//????????????
                receive.setReceiveDepartname(dnames[i]);
                receive.setSuggestionType(suggestionType);
                receive.setReceiveUsername(receiver.getRealName());
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
                systemService.save(receive);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tOMessageService.sendMessage(rid, "??????????????????????????????", "??????????????????", "???????????????????????????????????????\n????????????" + send.getOperateUsername()
                    + "\n???????????????" + sdf.format(send.getOperateDate()) + "\n??????:" + leaderReview, null);
            TPmContractNodeCheckEntity nodeCheck = systemService.get(TPmContractNodeCheckEntity.class, nodeCheckId);
            nodeCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);
            systemService.updateEntitie(nodeCheck);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmContractNodeCheck.getId())) {
            tPmContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmContractNodeCheckEntity.class,
                    tPmContractNodeCheck.getId());
            if (StringUtils.isEmpty(tPmContractNodeCheck.getAttachmentCode())) {
                tPmContractNodeCheck.setAttachmentCode(UUIDGenerator.generate());//??????????????????
            } else {
                List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                        tPmContractNodeCheck.getAttachmentCode(), "");
                tPmContractNodeCheck.setAttachments(fileList);
            }
            req.setAttribute("tPmContractNodeCheckPage", tPmContractNodeCheck);
            String contractNodeId = tPmContractNodeCheck.getContractNodeId();
            TPmContractNodeEntity nodeEntity = this.systemService.get(TPmContractNodeEntity.class, contractNodeId);
            String inOutFlag = nodeEntity.getInOutFlag();
            req.setAttribute("inOutFlag", inOutFlag);
            if ("i".equals(nodeEntity.getInOutFlag())) {
                TPmIncomeContractApprEntity iappr = systemService.get(TPmIncomeContractApprEntity.class,
                        nodeEntity.getInOutContractid());
                req.setAttribute("projectId", iappr.getProject().getId());
            } else {
                TPmOutcomeContractApprEntity iappr = systemService.get(TPmOutcomeContractApprEntity.class,
                        nodeEntity.getInOutContractid());
                req.setAttribute("projectId", iappr.getProject().getId());
            }
        }
        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheck-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheckUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractNodeCheckEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractNodeCheck,
                request.getParameterMap());
        List<TPmContractNodeCheckEntity> tPmContractNodeChecks = this.tPmContractNodeCheckService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmContractNodeCheckEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmContractNodeChecks);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmContractNodeCheckEntity tPmContractNodeCheck, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmContractNodeCheckEntity.class);
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
                List<TPmContractNodeCheckEntity> listTPmContractNodeCheckEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmContractNodeCheckEntity.class, params);
                for (TPmContractNodeCheckEntity tPmContractNodeCheck : listTPmContractNodeCheckEntitys) {
                    tPmContractNodeCheckService.save(tPmContractNodeCheck);
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
     * ??????????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "projectNodeCheck")
    public ModelAndView projectNodeCheck(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            String type = getProjectNodeType(project);
            request.setAttribute("type", type);
        }
        return new ModelAndView("com/kingtake/project/contractnodecheck/projectNodeCheckList");
    }

    /**
     * ????????????????????????
     * 
     * @param project
     * @return
     */
    private String getProjectNodeType(TPmProjectEntity project) {
        String type = null;
        //?????????????????????????????????????????????????????????????????????????????????????????????
        String contractNodeCountSql = "select count(1) from t_pm_contract_node n"
                + " join t_pm_income_contract_appr a  on n.in_out_contractid = a.id"
                + " and n.in_out_flag = 'i' join t_pm_project p on p.id = a.t_p_id" + " where p.id='" + project.getId()
                + "' and a.finish_flag='" + ApprovalConstant.APPRSTATUS_FINISH + "'";
        Long contractNodeCount = this.systemService.getCountForJdbc(contractNodeCountSql);
        if (contractNodeCount > 0) {
            type = "1";
            return type;
        }
        String taskNodeCountSql = "select count(1) from t_pm_task_node n join"
                + " t_pm_task t on n.t_p_id=t.id join t_pm_project p on p.id=t.t_p_id" + " where p.id='"
                + project.getId() + "' and t.audit_status='" + ApprovalConstant.APPRSTATUS_FINISH + "'";
        Long taskNodeCount = this.systemService.getCountForJdbc(taskNodeCountSql);
        if(taskNodeCount>0){
            type="2";
            return type;
        }
        if (ProjectConstant.PROJECT_CONTRACT.equals(project.getPlanContractFlag())) {
            type = "1";
        } else {
            type = "2";
        }
        return type;
    }

    /**
     * ??????????????????tab???
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "projectNodeCheckReceiveTab")
    public ModelAndView tPmProjectNodeCheckReceiveTab(HttpServletRequest request) {
        request.setAttribute("untreated", ProjectConstant.OPERATE_UNTREATED);
        request.setAttribute("treated", ProjectConstant.OPERATE_TREATED);
        return new ModelAndView("com/kingtake/project/contractnodecheck/projectNodeCheckList-receiveTab");
    }

    /**
     * ??????????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "projectNodeCheckReceiveList")
    public ModelAndView projectNodeCheckReceiveList(HttpServletRequest request) {
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
            return new ModelAndView("com/kingtake/project/contractnodecheck/projectNodeCheckList-receive");
        }

        return new ModelAndView("common/404.jsp");
    }

    /**
     * ????????????????????????????????????
     * 
     * @param tPmContractNodeCheck
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "projectNodeCheckDatagrid")
    public void projectNodeCheckDatagrid(HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        try {
            String datagridType = request.getParameter("datagridType");
            if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//????????????
                String operateStatus = request.getParameter("operateStatus");
                int page = Integer.parseInt(request.getParameter("page"));
                int rows = Integer.parseInt(request.getParameter("rows"));
                int start = (page-1)*rows+1;
                int end = (page)*rows;
                Map<String,Object> param = new HashMap<String,Object>();
                TSUser user = ResourceUtil.getSessionUserName();

                param.put("operateStatus", operateStatus);

                String projectname = request.getParameter("project.name");
                if (StringUtil.isNotEmpty(projectname)) {
                    param.put("projectName", projectname);
                }

                String contractName = request.getParameter("contract.name");
                if (StringUtil.isNotEmpty(contractName)) {
                    param.put("contractName", contractName);
                }

                String nodeName = request.getParameter("node.name");
                if (StringUtil.isNotEmpty(nodeName)) {
                    param.put("nodeName", nodeName);
                }
                param.put("userId", user.getId());
                param.put("departId", user.getCurrentDepart().getId());
                List<Map<String, Object>> result = this.tPmContractNodeCheckService.getProjectNodeApprReceiveList(
                        start, end, param);
                Integer count = tPmContractNodeCheckService.getProjectNodeCheckCount(param);
                dataGrid.setResults(result);
                dataGrid.setTotal(count.intValue());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        TagUtil.datagrid(response, dataGrid);
    }
    
    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpateOutContractNodeCheck")
    public ModelAndView goUpateOutContractNodeCheck(TPmOutContractNodeCheckEntity tPmOutContractNodeCheck,
            HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmOutContractNodeCheck.getId())) {
            tPmOutContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmOutContractNodeCheckEntity.class,
                    tPmOutContractNodeCheck.getId());
        } else{
            String importantFlag = "0";
            String contractNodeId = tPmOutContractNodeCheck.getContractNodeId();
            TPmContractNodeEntity nodeEntity = this.systemService.get(TPmContractNodeEntity.class, contractNodeId);
            TPmOutcomeContractApprEntity iappr = systemService.get(TPmOutcomeContractApprEntity.class,
                    nodeEntity.getInOutContractid());
            if (iappr != null) {
                TPmProjectEntity project = iappr.getProject();
                req.setAttribute("projectId", project.getId());
                if("1".equals(project.getGreatSpecialFlag())){//??????????????????
                    importantFlag = "1";
                }
                BigDecimal total = new BigDecimal(200000);//???????????????20W
                if(total.compareTo(iappr.getTotalFunds())<0){
                    importantFlag = "1";
                }
                tPmOutContractNodeCheck.setProjectId(project.getId());
                tPmOutContractNodeCheck.setProjectName(project.getProjectName());
                tPmOutContractNodeCheck.setContractId(iappr.getId());
                tPmOutContractNodeCheck.setContractCode(iappr.getContractCode());
                tPmOutContractNodeCheck.setContractName(iappr.getContractName());
                if(project.getDutyDepart()!=null){
                    tPmOutContractNodeCheck.setZrdw(project.getDutyDepart().getDepartname());
                }
                tPmOutContractNodeCheck.setXmzz(project.getProjectManager());
                tPmOutContractNodeCheck.setXmzzPhone(project.getManagerPhone());
                tPmOutContractNodeCheck.setLxr(project.getContact());
                tPmOutContractNodeCheck.setLxrPhone(project.getContactPhone());
                tPmOutContractNodeCheck.setHtlx(iappr.getContractType());
                tPmOutContractNodeCheck.setContractTotalFunds(iappr.getTotalFunds());
                CriteriaQuery cq = new CriteriaQuery(TPmContractBasicEntity.class);
                cq.eq("inOutContractid", iappr.getId());
                cq.add();
                List<TPmContractBasicEntity> contractBasicList = this.systemService.getListByCriteriaQuery(cq, false);
                if(contractBasicList.size()>0){
                    TPmContractBasicEntity basicEntity = contractBasicList.get(0);
                    tPmOutContractNodeCheck.setYfdz(basicEntity.getAddressB());//????????????
                    tPmOutContractNodeCheck.setYflxr(basicEntity.getNameB());
                    tPmOutContractNodeCheck.setYflxdh(basicEntity.getTelB());
                }
                tPmOutContractNodeCheck.setYfmc(iappr.getApprovalUnit());
                String supplierId = iappr.getApprovalUnitId();
                if(StringUtils.isNotEmpty(supplierId)){
                    TPmQualitySupplierEntity supplier = this.systemService.get(TPmQualitySupplierEntity.class, supplierId);
                    if("1".equals(supplier.getIsTemp())){
                        tPmOutContractNodeCheck.setYfzl("2");
                    }else{
                        tPmOutContractNodeCheck.setYfzl("1");
                    }
                }
            }
            if(nodeEntity!=null){
                tPmOutContractNodeCheck.setContractNodeId(nodeEntity.getId());
                tPmOutContractNodeCheck.setJdmc(nodeEntity.getContractNodeName());
                tPmOutContractNodeCheck.setWcsj(nodeEntity.getCompleteDate());
                tPmOutContractNodeCheck.setJdje(nodeEntity.getPayAmount());
                tPmOutContractNodeCheck.setPsqywcgz(nodeEntity.getResultForm());//?????????????????????????????????????????????
            }
            tPmOutContractNodeCheck.setImportantFlag(importantFlag);//????????????
        }
        if (StringUtils.isEmpty(tPmOutContractNodeCheck.getAttachmentCode())) {
            tPmOutContractNodeCheck.setAttachmentCode(UUIDGenerator.generate());//??????????????????
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                    tPmOutContractNodeCheck.getAttachmentCode(), "tPmOutcomeContractNodeCheck");
            tPmOutContractNodeCheck.setAttachments(fileList);
            List<TSFilesEntity> psjgFj = this.systemService.getAttachmentByCode(
                    tPmOutContractNodeCheck.getAttachmentCode(), "tPmOutcomeContractNodeCheckPsjg");
            tPmOutContractNodeCheck.setPsjgFj(psjgFj);
        }
        req.setAttribute("tPmContractNodeCheckPage", tPmOutContractNodeCheck);
        if("1".equals(tPmOutContractNodeCheck.getImportantFlag())){
            return new ModelAndView("com/kingtake/project/contractnodecheck/outcomeContractNodeCheck-important");
        }else{
            return new ModelAndView("com/kingtake/project/contractnodecheck/outcomeContractNodeCheck-common");
        }
    }
    
    /**
     * ???????????????????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateOutcomeNodeCheck")
    @ResponseBody
    public AjaxJson doUpdateOutcomeNodeCheck(TPmOutContractNodeCheckEntity tPmContractNodeCheck,
            HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????????????????";
        try {
            if (StringUtils.isNotEmpty(tPmContractNodeCheck.getId())) {
                TPmOutContractNodeCheckEntity t = systemService.get(TPmOutContractNodeCheckEntity.class,
                        tPmContractNodeCheck.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmContractNodeCheck, t);
                systemService.saveOrUpdate(t);
            } else {
                tPmContractNodeCheck.setAuditFlag(ApprovalConstant.APPRSTATUS_UNSEND);//?????????
                this.systemService.save(tPmContractNodeCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    
    
    @RequestMapping(params = "updateFinishFlag")
    @ResponseBody
    public AjaxJson updateFinishFlag(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
           this.outcomeContractNodeCheckService.finishAppr(request);
           json.setMsg("???????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("???????????????????????????");
        }
        return json;
    }
    
    /**
     * ????????????
     * @return
     */
    @RequestMapping(params = "doPassPay")
    @ResponseBody
    public AjaxJson doPassPay(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        try {
            this.outcomeContractNodeCheckService.doPassPay(id);
            j.setMsg("?????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("?????????????????????");
        }
        return j;
    }
    
    /**
     * ?????????????????????
     * @param req
     * @return
     */
    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TPmOutContractNodeCheckEntity apply = this.systemService.get(TPmOutContractNodeCheckEntity.class, id);
            req.setAttribute("apply", apply);
        }
        return new ModelAndView("com/kingtake/project/appraisal/proposePage");
    }
    
    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "???????????????";
        try {
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            if (StringUtil.isNotEmpty(id)) {
                TPmOutContractNodeCheckEntity apply = systemService.get(TPmOutContractNodeCheckEntity.class, id);
                apply.setMsgText(msgText);
                apply.setPayFlag("2");//???????????????
                this.outcomeContractNodeCheckService.doReject(apply);
            }
        } catch (Exception e) {
            message = "???????????????";
            e.printStackTrace();
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
        List<Map<String, Object>> apprList = this.getAppr(apprId);//??????????????????
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
     * ????????????????????????
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//????????????
        StringBuffer resultSql = new StringBuffer();
        resultSql
                .append("SELECT APPR.ID AS APPR_ID, APPR.CONTRACT_NAME, APPR.Contract_Total_Funds, APPR.Jdje, \n");
        resultSql
                .append("APPR.Finish_Time, APPR.audit_flag, \n");
        resultSql
                .append("APPR.CONTRACT_NODE_ID, APPR.PROJECT_NAME, NODE.NODE_NAME, NODE.IN_OUT_CONTRACTID, \n");
        resultSql.append("CONTRACT.T_P_ID AS PROJECTID, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql
                .append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_PM_CONTRACT_NODE NODE, T_PM_OUTCONTRACT_NODE_CHECK APPR, t_pm_outcome_contract_appr CONTRACT, "
                + "T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE NODE.ID = APPR.CONTRACT_NODE_ID AND NODE.IN_OUT_CONTRACTID = CONTRACT.ID "
                + "AND APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID AND EXT.ID='0501' \n"
                + "AND RECEIVE.RECEIVE_USERID = ?  ";

        //??????????????????????????????
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                    + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };

        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
    
    @RequestMapping(params = "checkIsProjectNodeAppr")
    @ResponseBody
    public JSONObject checkIsIncomeContractNodeAppr(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("isAppr", "0");
        String apprId = request.getParameter("apprId");
        List<Map<String, Object>> apprList = this.getIncomeContractNodeAppr(apprId);//??????????????????
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
     * ????????????????????????
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getIncomeContractNodeAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//????????????
        StringBuffer resultSql = new StringBuffer();
        resultSql
        .append("SELECT APPR.ID AS APPR_ID, APPR.CONTRACT_NAME, \n");
        resultSql
        .append("APPR.Finish_Time, APPR.OPERATION_STATUS, \n");
        resultSql
        .append("APPR.CONTRACT_NODE_ID, APPR.PROJECT_NAME, NODE.NODE_NAME, NODE.IN_OUT_CONTRACTID, \n");
        resultSql.append("CONTRACT.T_P_ID AS PROJECTID, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql
        .append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");
        
        String temp = "FROM T_PM_CONTRACT_NODE NODE, T_PM_CONTRACT_NODE_CHECK APPR, t_pm_income_contract_appr CONTRACT, "
                + "T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE NODE.ID = APPR.CONTRACT_NODE_ID AND NODE.IN_OUT_CONTRACTID = CONTRACT.ID "
                + "AND APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID AND EXT.ID='1801' \n"
                + "AND RECEIVE.RECEIVE_USERID = ?  ";
        
        //??????????????????????????????
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };
        
        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
    
    /**
	 * ??????word
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportWord")
	public String exportWord(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
		//????????????ID
        String contractNodeId = request.getParameter("contractNodeId");
        //??????????????????ID
        String checkId = request.getParameter("checkId");
        //?????????0-??????????????????1-????????????
        String importantFlag = "0";       
        if (StringUtil.isNotEmpty(checkId)) {
            TPmOutContractNodeCheckEntity tPmOutContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmOutContractNodeCheckEntity.class,
            		checkId);
            importantFlag = tPmOutContractNodeCheck.getImportantFlag();
        } else{            
            TPmContractNodeEntity nodeEntity = this.systemService.get(TPmContractNodeEntity.class, contractNodeId);
            TPmOutcomeContractApprEntity iappr = systemService.get(TPmOutcomeContractApprEntity.class,
                    nodeEntity.getInOutContractid());
            if (iappr != null) {
                TPmProjectEntity project = iappr.getProject();
                if("1".equals(project.getGreatSpecialFlag())){//??????????????????
                    importantFlag = "1";
                }
                BigDecimal total = new BigDecimal(200000);//???????????????20W
                if(total.compareTo(iappr.getTotalFunds())<0){
                    importantFlag = "1";
                }
            }
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        //??????????????????
    	TPmContractNodeEntity nodeEntity = this.systemService.get(TPmContractNodeEntity.class, contractNodeId);
    	if(nodeEntity != null){
    		map.put("jdmc", nodeEntity.getContractNodeName() == null ? "" : nodeEntity.getContractNodeName());
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy???MM???dd???");
    		if(nodeEntity.getCompleteDate() != null){
    			String wcsj = formatter.format(nodeEntity.getCompleteDate());
    			map.put("wcsj", wcsj);
    		}
        	map.put("jdje", nodeEntity.getPayAmount() == null ? "" : nodeEntity.getPayAmount());
    	}
    	//??????????????????
    	TPmOutcomeContractApprEntity iappr = systemService.get(TPmOutcomeContractApprEntity.class,
                nodeEntity.getInOutContractid());
    	if(iappr != null){
    		map.put("htmc", iappr.getContractName() == null ? "" : iappr.getContractName());
    		map.put("htbh", iappr.getContractCode() == null ? "" : iappr.getContractCode());
    		map.put("htje", iappr.getTotalFunds() == null ? "" : iappr.getTotalFunds());
    		StringBuffer htlxBuffer = new StringBuffer();
			ArrayList<String> htlxlist = new ArrayList<String>();
			htlxlist.add("????????????");
			htlxlist.add("????????????");
			htlxlist.add("??????????????????");
			htlxlist.add("??????????????????");
			htlxlist.add("??????????????????");
			htlxlist.add("??????????????????");  
			int i = 0;
    		if(iappr.convertGetContractType() != null){        			
    			for (String htleStr : htlxlist) {
    				if(htleStr == iappr.convertGetContractType()){
    					htlxBuffer.append(SrmipConstants.CHECKBOX_TRUE);
    					htlxBuffer.append(htleStr);
    					htlxBuffer.append("\t\t\t");
    				}else{
    					htlxBuffer.append(SrmipConstants.CHECKBOX_FALSE);
    					htlxBuffer.append(htleStr);
    					htlxBuffer.append("\t\t\t");
    				}
    				if(i == 2){
    					htlxBuffer.append("\n");
    				}
    				i++;
				}        			
    		}else{
    			for (String htleStr : htlxlist) {
    				htlxBuffer.append(SrmipConstants.CHECKBOX_FALSE);
    				htlxBuffer.append(htleStr);
    				htlxBuffer.append("\t\t\t");
    				if(i == 2){
    					htlxBuffer.append("\n");
    				}
    				i++;
				}
    		}
    		map.put("htlx", htlxBuffer.toString());	        		
    	}
    	//????????????????????????
    	TPmOutContractNodeCheckEntity tPmOutContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmOutContractNodeCheckEntity.class,
        		checkId);
    	StringBuffer yfzlBuffer = new StringBuffer();
    	yfzlBuffer.append(SrmipConstants.CHECKBOX_FALSE);
		yfzlBuffer.append("????????????");
		yfzlBuffer.append("\t\t\t");
		yfzlBuffer.append(SrmipConstants.CHECKBOX_FALSE);
		yfzlBuffer.append("????????????");

		StringBuffer psfsBuffer = new StringBuffer();
		psfsBuffer.append(SrmipConstants.CHECKBOX_FALSE);
		psfsBuffer.append("????????????");
		psfsBuffer.append("\t\t\t");
		psfsBuffer.append(SrmipConstants.CHECKBOX_FALSE);
		psfsBuffer.append("????????????");

    	if(tPmOutContractNodeCheck != null){
    		map.put("yfmc", tPmOutContractNodeCheck.getYfmc() == null ? "" : tPmOutContractNodeCheck.getYfmc());
    		map.put("yfdz", tPmOutContractNodeCheck.getYfdz() == null ? "" : tPmOutContractNodeCheck.getYfdz());
    		
    		if("1".equals(tPmOutContractNodeCheck.getYfzl())){
    			yfzlBuffer.delete(0,yfzlBuffer.length());
    			yfzlBuffer.append(SrmipConstants.CHECKBOX_TRUE);
    			yfzlBuffer.append("????????????");
    			yfzlBuffer.append("\t\t\t");
    			yfzlBuffer.append(SrmipConstants.CHECKBOX_FALSE);
    			yfzlBuffer.append("????????????");
    		}else if("2".equals(tPmOutContractNodeCheck.getYfzl())){
    			yfzlBuffer.delete(0,yfzlBuffer.length());
    			yfzlBuffer.append(SrmipConstants.CHECKBOX_FALSE);
    			yfzlBuffer.append("????????????");
    			yfzlBuffer.append("\t\t\t");
    			yfzlBuffer.append(SrmipConstants.CHECKBOX_TRUE);
    			yfzlBuffer.append("????????????");
    		}      		
       		
    		map.put("yflxr", tPmOutContractNodeCheck.getYflxr() == null ? "" : tPmOutContractNodeCheck.getYflxr());
    		map.put("yfdh", tPmOutContractNodeCheck.getYflxdh() == null ? "" : tPmOutContractNodeCheck.getYflxdh());
    		map.put("psqywcgz", tPmOutContractNodeCheck.getPsqywcgz() == null ? "" : tPmOutContractNodeCheck.getPsqywcgz()); 
    		
    		if("1".equals(tPmOutContractNodeCheck.getPsfs())){
    			psfsBuffer.delete(0,psfsBuffer.length());
    			psfsBuffer.append(SrmipConstants.CHECKBOX_TRUE);
    			psfsBuffer.append("????????????");
    			psfsBuffer.append("\t\t\t");
    			psfsBuffer.append(SrmipConstants.CHECKBOX_FALSE);
    			psfsBuffer.append("????????????");
    		}else if("2".equals(tPmOutContractNodeCheck.getYfzl())){
    			psfsBuffer.delete(0,psfsBuffer.length());
    			psfsBuffer.append(SrmipConstants.CHECKBOX_FALSE);
    			psfsBuffer.append("????????????");
    			psfsBuffer.append("\t\t\t");
    			psfsBuffer.append(SrmipConstants.CHECKBOX_TRUE);
    			psfsBuffer.append("????????????");
    		}       

    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy???MM???dd???");
    		if(tPmOutContractNodeCheck.getPssj() != null){
    			String pssj = formatter.format(tPmOutContractNodeCheck.getPssj());
    			map.put("pssj", pssj);
    		}
    		map.put("psdd", tPmOutContractNodeCheck.getPsdd() == null ? "" : tPmOutContractNodeCheck.getPsdd());  
    		map.put("cpry", tPmOutContractNodeCheck.getPscpry() == null ? "" : tPmOutContractNodeCheck.getPscpry());  
    	}      
		map.put("yfzl", yfzlBuffer.toString());
		map.put("psfs", psfsBuffer.toString());
		
    	//??????????????????
    	TPmProjectEntity project = iappr.getProject();
    	if(project != null){
    		map.put("zrdw", project.getDutyDepart() == null ? "" : project.getDutyDepart().getDepartname());
    		map.put("xmmc", project.getProjectName() == null ? "" : project.getProjectName());
    		String sql = "select name from t_pm_project_member where T_P_ID = '" + project.getId() + "' and PROJECT_MANAGER = '1'";
    		Map<String, Object> projectMember = tPmContractNodeCheckService.findOneForJdbc(sql);
    		if(projectMember != null){
    			map.put("xmzc", projectMember.get("name") == null ? "" : projectMember.get("name"));
    		}
    		map.put("xmzcdh", "");
    		map.put("lxr", project.getContact() == null ? "" : project.getContact());
    		map.put("lxrdh", project.getContactPhone() == null ? "" : project.getContactPhone());
    	}
        
        //?????????????????????????????????????????????WORD??????
        if("0".equals(importantFlag)){        	
        	modelMap.put(TemplateWordConstants.FILE_NAME, "?????????????????????????????????????????????_" + DateUtils.formatDate());        	
        	modelMap.put(TemplateWordConstants.URL, "export/template/htjdkhspb(ybht).docx");
        }else if("1".equals(importantFlag)){
        	modelMap.put(TemplateWordConstants.FILE_NAME, "?????????????????????????????????????????????_" + DateUtils.formatDate());        	
        	modelMap.put(TemplateWordConstants.URL, "export/template/htjdkhspb(zyht).docx");
        }
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;                   
	}
	
	/**
     * ???????????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goIncomeContractCheckUpdate")
    public ModelAndView goIncomeContractCheckUpdate(TPmContractNodeCheckEntity tPmContractNodeCheck,
            HttpServletRequest req) {
        String contractNodeId = req.getParameter("contractNodeId");
        if(StringUtils.isNotEmpty(tPmContractNodeCheck.getId())){
            tPmContractNodeCheck = this.systemService.get(TPmContractNodeCheckEntity.class, tPmContractNodeCheck.getId());
        } else if (StringUtil.isNotEmpty(contractNodeId)) {
            TPmContractNodeEntity node = systemService.get(TPmContractNodeEntity.class, contractNodeId);
            req.setAttribute("inOutFlag", node.getInOutFlag());
            if ("i".equals(node.getInOutFlag())) {
                TPmIncomeContractApprEntity iappr = systemService.get(TPmIncomeContractApprEntity.class,
                        node.getInOutContractid());
                req.setAttribute("projectId", iappr.getProject().getId());
                req.setAttribute("contractAppr", iappr);
                tPmContractNodeCheck.setContactNum(iappr.getContractCode());
                tPmContractNodeCheck.setAccountingCode(iappr.getProject().getAccountingCode());
                tPmContractNodeCheck.setSecretDegree(iappr.getProject().getSecretDegree());
                tPmContractNodeCheck.setProjectName(iappr.getProjectName());
                tPmContractNodeCheck.setContractName(iappr.getContractName());
                tPmContractNodeCheck.setContractType(iappr.getContractType());
                tPmContractNodeCheck.setContactSecondParty(iappr.getApplyUnit());
                tPmContractNodeCheck.setContactAmount(iappr.getTotalFunds());
                tPmContractNodeCheck.setContractSigningTime(iappr.getContractSigningTime());
            } 
            if (StringUtil.isNotEmpty(node.getProjPayNodeId())) {
                BigDecimal cost = new BigDecimal(0);
                String[] pnids = node.getProjPayNodeId().split(",");
                for (String pnid : pnids) {
                    TPmIncomeNodeEntity inode = systemService.get(TPmIncomeNodeEntity.class, pnid);
                    cost = cost.add(inode.getIncomeAmount());
                }
                tPmContractNodeCheck.setNodeAmount(cost);
            }
            tPmContractNodeCheck.setNodeTime(node.getCompleteDate());
            tPmContractNodeCheck.setContractNodeId(contractNodeId);
        }
        if (StringUtil.isNotEmpty(tPmContractNodeCheck.getId())) {
            tPmContractNodeCheck = tPmContractNodeCheckService.getEntity(TPmContractNodeCheckEntity.class,
                    tPmContractNodeCheck.getId());
        }
        if (StringUtils.isEmpty(tPmContractNodeCheck.getAttachmentCode())) {
            tPmContractNodeCheck.setAttachmentCode(UUIDGenerator.generate());//??????????????????
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                    tPmContractNodeCheck.getAttachmentCode(), "");
            tPmContractNodeCheck.setAttachments(fileList);
        }
        req.setAttribute("tPmContractNodeCheckPage", tPmContractNodeCheck);
        return new ModelAndView("com/kingtake/project/contractnodecheck/incomeContractNodeCheck-update");
    }
    
    /**
     * ??????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSendPayApply")
    @ResponseBody
    public AjaxJson doSendPayApply(TPmOutContractNodeCheckEntity contractNodeCheck, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            String userId = request.getParameter("userId");
            String realname = request.getParameter("realname");
            TSUser user = ResourceUtil.getSessionUserName();
            contractNodeCheck = this.systemService.get(TPmOutContractNodeCheckEntity.class, contractNodeCheck.getId());
            contractNodeCheck.setJhcshr(realname);
            contractNodeCheck.setJhcshrId(userId);
            contractNodeCheck.setPayFlag("0");//?????????
            contractNodeCheck.setJhcshfsr(user.getRealName());
            contractNodeCheck.setJhcshfsrId(user.getId());
            contractNodeCheck.setJhcshfssj(new Date());
            systemService.updateEntitie(contractNodeCheck);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
