package com.kingtake.project.controller.approutcomecontract;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
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
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.pojo.base.TPBpmLog;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.util.PropertiesUtil;
import com.kingtake.office.entity.purchaseplandetail.TBPurchasePlanDetailEntity;
import com.kingtake.office.entity.purchaseplanmain.TBPurchasePlanMainEntity;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.approutcomecontract.TPmOutcomeContractApprServiceI;

import net.sf.json.JSONObject;

/**
 * @Title: Controller
 * @Description: 出账合同审批
 * @author onlineGenerator
 * @date 2015-08-10 18:47:48
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmOutcomeContractApprController")
public class TPmOutcomeContractApprController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmOutcomeContractApprController.class);

    @Autowired
    private TPmOutcomeContractApprServiceI tPmOutcomeContractApprService;
    @Autowired
    private SystemService systemService;
    private ActivitiService activitiService;
    @Autowired
	private TaskJeecgService taskJeecgService;
    
    @Autowired
	private ActivitiDao activitiDao;
    private HistoryService historyService; 
    
    @Autowired
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
    
    @Autowired
	public void setActivitiService(ActivitiService activitiService) {
		this.activitiService = activitiService;
	}
    
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 出账合同审批处理tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprList-receiveTab");
    }

    /**
     * 出账合同审批列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmOutcomeContractAppr")
    public ModelAndView tPmOutcomeContractAppr(HttpServletRequest request) {
        //发起审批流程
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprList-send");
        }

        //处理审批
        //0：未处理；1：已处理
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
            return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprList-receive");
        }

        return new ModelAndView("common/404.jsp");
    }

    /**
     * 出账合同审批列表 过程管理页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "outcomeContractApprProcess")
    public ModelAndView outcomeContractApprProcess(HttpServletRequest request) {
        //发起审批流程
        String projectId = request.getParameter("projectId");
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("projectName", project.getProjectName());
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
        }
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprListProcess");
    }

    /**
     * 查询出账合同列表（产品交接清单中用到）
     * @param tPmOutcomeContractAppr
     * @param request
     * @param response
     * @param dataGrid
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid4Select")
    public void datagrid4Select(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
        HttpServletResponse response, DataGrid dataGrid) {
      CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class, dataGrid);
      //查询条件组装器
      org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutcomeContractAppr,request.getParameterMap());
      cq.add();
      this.tPmOutcomeContractApprService.getDataGridReturn(cq, true);

      TagUtil.datagrid(response, dataGrid);
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
    public void datagrid(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//发起审批
            //"0"：发送给别人审批的； （已添加项目条件）
            CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class, dataGrid);
            //查询条件组装器
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutcomeContractAppr,
                    request.getParameterMap());
            try {
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            cq.add();
            this.tPmOutcomeContractApprService.getDataGridReturn(cq, true);
        } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批
            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();//获取参数

            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            StringBuffer resultSql = new StringBuffer();
            resultSql
                    .append("SELECT APPR.ID AS APPR_ID, APPR.T_P_ID AS PROJECTID, APPR.APPLY_UNIT, APPR.PROJECTNAME_SUBJECTCODE, \n");
            resultSql.append("APPR.CONTRACT_NAME,APPR.CONTRACT_CODE, APPR.APPROVAL_UNIT, APPR.TOTAL_FUNDS, APPR.FINISH_FLAG, \n");
            resultSql.append("APPR.CONTRACT_TYPE, APPR.START_TIME, APPR.END_TIME, APPR.ACQUISITION_METHOD, \n");
            resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
            resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
            resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

            String temp = "FROM T_PM_OUTCOME_CONTRACT_APPR APPR, T_PM_APPR_SEND_LOGS SEND, "
                    + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                    + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                    + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                    + "AND RECEIVE.RECEIVE_USERID = ?  ";

            if (SrmipConstants.YES.equals(operateStatus)) {
                //已处理
                temp += " AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                //未处理：需要是有效的
                temp += " AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + " AND RECEIVE.VALID_FLAG ="
                        + SrmipConstants.YES;
            }

            String projectname = request.getParameter("projectname.subjectcode");
            if (StringUtil.isNotEmpty(projectname)) {
                temp += " AND APPR.PROJECTNAME_SUBJECTCODE LIKE '%" + projectname + "%'";
            }

            String contractName = request.getParameter("contract.name");
            if (StringUtil.isNotEmpty(contractName)) {
                temp += " AND APPR.CONTRACT_NAME LIKE '%" + contractName + "%'";
            }

            temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
            String[] params = new String[] { user.getId() };

            List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                    dataGrid.getPage(), dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除出账合同审批
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmOutcomeContractAppr = systemService.getEntity(TPmOutcomeContractApprEntity.class,
                tPmOutcomeContractAppr.getId());
        message = "出账合同审批删除成功";
        try {
            tPmOutcomeContractApprService.delete(tPmOutcomeContractAppr);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "出账合同审批删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除出账合同审批
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "出账合同审批删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmOutcomeContractApprEntity tPmOutcomeContractAppr = systemService.getEntity(
                        TPmOutcomeContractApprEntity.class, id);
                tPmOutcomeContractApprService.delete(tPmOutcomeContractAppr);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "出账合同审批删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加出账合同审批
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "出账合同审批添加成功";
        try {
            tPmOutcomeContractAppr.setContractBookFlag("1");//未上传合同正本
            tPmOutcomeContractApprService.save(tPmOutcomeContractAppr);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "出账合同审批添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新出账合同审批
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            TPmContractBasicEntity tPmContractBasic, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "出账合同审批更新成功";
        TPmOutcomeContractApprEntity t = tPmOutcomeContractApprService.get(TPmOutcomeContractApprEntity.class,
                tPmOutcomeContractAppr.getId());
        TPmContractBasicEntity b = systemService.get(TPmContractBasicEntity.class, tPmContractBasic.getRid());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmOutcomeContractAppr, t);
            tPmOutcomeContractApprService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);

            MyBeanUtils.copyBeanNotNull2Bean(tPmContractBasic, b);
            systemService.saveOrUpdate(b);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "出账合同审批更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "updateFlag")
    @ResponseBody
    public AjaxJson updateFlag(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmOutcomeContractApprEntity contract = tPmOutcomeContractApprService.get(
                    TPmOutcomeContractApprEntity.class, id);
            if (contract != null) {
                if (ApprovalConstant.APPRSTATUS_SEND.equals(contract.getFinishFlag())) {
                    j.setSuccess(false);
                    j.setMsg("出账合同审批已发送，不能再进行编辑！");
                } else if (ApprovalConstant.APPRSTATUS_FINISH.equals(contract.getFinishFlag())) {
                    j.setSuccess(false);
                    j.setMsg("出账合同审批流程已完成，不能再进行编辑！");
                }
            }
        }
        return j;
    }

    /**
     * 保存出账合同审批
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TPmOutcomeContractApprEntity tPmOutcomeContractAppr,
            TPmContractBasicEntity tPmContractBasic, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "出账合同审批保存成功";
        try {
            Map<String, Object> ids = new HashMap<String, Object>();

            String contractId = null;
            if (!StringUtil.isNotEmpty(tPmOutcomeContractAppr.getId())) {
                tPmOutcomeContractAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
                tPmOutcomeContractApprService.save(tPmOutcomeContractAppr);
                contractId = tPmOutcomeContractAppr.getId();
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } else {
                contractId = tPmOutcomeContractAppr.getId();
                TPmOutcomeContractApprEntity t = tPmOutcomeContractApprService.get(TPmOutcomeContractApprEntity.class,
                        contractId);
                MyBeanUtils.copyBeanNotNull2Bean(tPmOutcomeContractAppr, t);
                tPmOutcomeContractApprService.saveOrUpdate(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }

            //合同基本信息
            String basicId = null;
            if (!StringUtil.isNotEmpty(tPmContractBasic.getRid())) {
                tPmContractBasic.setInOutContractid(contractId);
                systemService.save(tPmContractBasic);
                basicId = tPmContractBasic.getRid();
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } else {
                basicId = tPmContractBasic.getRid();
                TPmContractBasicEntity basic = systemService.get(TPmContractBasicEntity.class, basicId);
                MyBeanUtils.copyBeanNotNull2Bean(tPmContractBasic, basic);
                systemService.saveOrUpdate(basic);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }

            ids.put("contractId", contractId);
            ids.put("basicId", basicId);
            j.setAttributes(ids);

            //关联附件
            String fileKeys = request.getParameter("fileKeys");
            if (StringUtil.isNotEmpty(fileKeys)) {
                CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
                cq.in("id", fileKeys.substring(0, fileKeys.length() - 1).split(","));
                cq.isNull("bid");
                cq.add();
                List<TSFilesEntity> files = systemService.getListByCriteriaQuery(cq, false);
                if (files != null && files.size() > 0) {
                    for (TSFilesEntity file : files) {
                        file.setBid(contractId);
                        systemService.updateEntitie(file);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "出账合同审批保存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 出账合同审批新增tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddTab")
    public ModelAndView goAddTab(String projectId, HttpServletRequest req) {
        req.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractAppr-addTab");
    }

    /**
     * 出账合同审批新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmOutcomeContractAppr.getId())) {
            tPmOutcomeContractAppr = tPmOutcomeContractApprService.getEntity(TPmOutcomeContractApprEntity.class,
                    tPmOutcomeContractAppr.getId());
        }
        if (tPmOutcomeContractAppr.getProject() != null) {
            if (StringUtil.isNotEmpty(tPmOutcomeContractAppr.getProject().getId())) {
                TPmProjectEntity project = systemService.get(TPmProjectEntity.class, tPmOutcomeContractAppr
                        .getProject().getId());
                req.setAttribute("project", project);
                tPmOutcomeContractAppr.setProjectnameSubjectcode(project.getProjectName());
            }
        }
        tPmOutcomeContractAppr.setContractSigningTime(new Date());

        // 获得outInContract初始出账合同基本信息=====================start============================
        Map<String, String> map = PropertiesUtil.read("outInContract.properties");
        List<TPmContractBasicEntity> contractBasicList = new ArrayList<TPmContractBasicEntity>();
        TPmContractBasicEntity contractBasicEntity = new TPmContractBasicEntity();
        contractBasicEntity.setUnitNameA(map.get("unitNameA"));//法人单位名称(甲方)
        contractBasicEntity.setAddressA(map.get("addressA"));//单位地址(甲方)
        contractBasicEntity.setPostalcodeA(map.get("postalcodeA"));//邮政编码(甲方)
        contractBasicEntity.setTelA(map.get("telA"));//联系电话(甲方)
        contractBasicEntity.setAccountNameA(map.get("accountNameA"));// 账户名称(甲方)
        contractBasicEntity.setBankA(map.get("bankA"));//开户银行(甲方)
        contractBasicEntity.setAccountIdA(map.get("accountIdA"));//帐号(甲方)
        contractBasicList.add(contractBasicEntity);
        tPmOutcomeContractAppr.setTpmContractBasics(contractBasicList);
        // 获得outInContract初始出账合同基本信息=====================end============================

        //附件
        if(StringUtils.isEmpty(tPmOutcomeContractAppr.getAttachmentCode())){
            tPmOutcomeContractAppr.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmOutcomeContractAppr.getAttachmentCode(), "tPmOutcomeContractAppr");
            tPmOutcomeContractAppr.setCertificates(certificates);
            List<TSFilesEntity> contractBook = systemService.getAttachmentByCode(tPmOutcomeContractAppr.getAttachmentCode(), "outcomeContractBook");
            tPmOutcomeContractAppr.setContractBook(contractBook);
        }
        req.setAttribute("tPmOutcomeContractApprPage", tPmOutcomeContractAppr);
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprAndBasicContract-add");
    }
    
    /**
     * 查询采购计划
     * @param tPmOutcomeContractAppr
     * @return
     */
    @RequestMapping(params = "getCgjh")
    @ResponseBody
    private JSONArray getCgjh(String projectId) {
        JSONArray array = new JSONArray();
        List<TBPurchasePlanMainEntity> purchaseList = null;
        List<TBPurchasePlanDetailEntity> purchaseDetailList = new ArrayList<TBPurchasePlanDetailEntity>();
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        if (project != null) {
            project = this.systemService.get(TPmProjectEntity.class, project.getId());
            if (StringUtils.isNotEmpty(project.getProjectManagerId())) {
                CriteriaQuery cq = new CriteriaQuery(TBPurchasePlanMainEntity.class);
                cq.eq("managerId", project.getProjectManagerId());
                cq.addOrder("createDate", SortDirection.desc);
                cq.add();
                purchaseList = this.tPmOutcomeContractApprService.getListByCriteriaQuery(cq, false);
            }
        }
        if(purchaseList!=null){
            for(TBPurchasePlanMainEntity plan:purchaseList){
                List<TBPurchasePlanDetailEntity> planDetailList = this.systemService.findByProperty(TBPurchasePlanDetailEntity.class, "purchasePlanId", plan.getId());
                purchaseDetailList.addAll(planDetailList);
            }
            for(TBPurchasePlanDetailEntity detail: purchaseDetailList){
                JSONObject json = new JSONObject();
                json.put("id",detail.getId());
                json.put("planName",detail.getPlanName());
                json.put("cgfs",detail.getPurchaseMode());
                json.put("cgfsStr",detail.convertGetPurchaseMode());
                json.put("cgly",detail.getPurchaseReason());
                array.add(json);
            }
        }
        return array;
    }

    /**
     * 出账合同审批新增tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateTab")
    public ModelAndView goUpdateTab(HttpServletRequest req) {
        String contractId = req.getParameter("id");
        String node = req.getParameter("node");
        String receive = req.getParameter("receive");

        req.setAttribute("contractId", contractId);
        req.setAttribute("node", node);
        req.setAttribute("receive", receive);

        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractAppr-updateTab");
    }

    /**
     * 出账合同审批编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmOutcomeContractAppr.getId())) {
            tPmOutcomeContractAppr = tPmOutcomeContractApprService.getEntity(TPmOutcomeContractApprEntity.class,
                    tPmOutcomeContractAppr.getId());
            //附件
            if(StringUtils.isEmpty(tPmOutcomeContractAppr.getAttachmentCode())){
                tPmOutcomeContractAppr.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmOutcomeContractAppr.getAttachmentCode(), "tPmOutcomeContractAppr");
                tPmOutcomeContractAppr.setCertificates(certificates);
                List<TSFilesEntity> contractBook = systemService.getAttachmentByCode(tPmOutcomeContractAppr.getAttachmentCode(), "outcomeContractBook");
                tPmOutcomeContractAppr.setContractBook(contractBook);
            }
            req.setAttribute("tPmOutcomeContractApprPage", tPmOutcomeContractAppr);
        }
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprAndBasicContract-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutcomeContractAppr,
                request.getParameterMap());
        List<TPmOutcomeContractApprEntity> tPmOutcomeContractApprs = this.tPmOutcomeContractApprService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "出账合同列表");
        modelMap.put(NormalExcelConstants.CLASS, TPmOutcomeContractApprEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("出账合同列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmOutcomeContractApprs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    /**
     * 统计查询中导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls4Statistics")
    public String exportXls4Statistics(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutcomeContractAppr,
                request.getParameterMap());
        cq.eq("finishFlag",ApprovalConstant.APPRSTATUS_FINISH);
        cq.add();
        List<TPmOutcomeContractApprEntity> tPmOutcomeContractApprs = this.tPmOutcomeContractApprService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "出账合同列表");
        modelMap.put(NormalExcelConstants.CLASS, TPmOutcomeContractApprEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("出账合同列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmOutcomeContractApprs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public void exportXlsByT(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            tPmOutcomeContractAppr = this.tPmOutcomeContractApprService.get(TPmOutcomeContractApprEntity.class,
                    tPmOutcomeContractAppr.getId());
            Workbook wb = this.tPmOutcomeContractApprService.getPriceTemplate(tPmOutcomeContractAppr);
            String fileName = "合同【" + tPmOutcomeContractAppr.getContractName() + "】价款计算书导入模板.xls";
            fileName = POIPublicUtil
                    .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            logger.error("导出模板失败", e);
            throw new BusinessException("导出模板失败", e);
        }
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
                List<TPmOutcomeContractApprEntity> listTPmOutcomeContractApprEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmOutcomeContractApprEntity.class, params);
                for (TPmOutcomeContractApprEntity tPmOutcomeContractAppr : listTPmOutcomeContractApprEntitys) {
                    tPmOutcomeContractApprService.save(tPmOutcomeContractAppr);
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
     * 验证合同编号
     * 
     * @param tPmOutcomeContractAppr
     * @param req
     * @return
     */
    @RequestMapping(params = "validformContractCode")
    @ResponseBody
    public ValidForm validformContractCode(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest req) {

        String inputVal = req.getParameter("param");

        ValidForm v = tPmOutcomeContractApprService.validformCode("t_pm_outcome_contract_appr", "contract_code",
                tPmOutcomeContractAppr.getId(), inputVal);

        return v;
    }

    /**
     * 选择列表（过程管理->项目验收与结题->产品类交接单中用到）
     * @param request
     * @return
     */
    @RequestMapping(params = "selectRec")
    public ModelAndView selectRec(HttpServletRequest request) {
      String projectId = request.getParameter("projectId");
      request.setAttribute("projectId", projectId);
      return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprSelect");
    }

    /**
     * 出账合同审批新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goRelatedIncomeList")
    public ModelAndView goRelatedIncomeList(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/approutcomecontract/relatedIncomeList");
    }

    /**
     * 关联进账合同列表
     */
    @RequestMapping(params = "relatedIncomeListDatagrid")
    public void relatedIncomeListDatagrid(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest req,
            HttpServletResponse res, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractApprEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeContractAppr,
                req.getParameterMap());
        cq.add();
        this.tPmOutcomeContractApprService.getDataGridReturn(cq, true);
        TagUtil.datagrid(res, dataGrid);
    }

    @RequestMapping(params = "checkIsAppr")
    @ResponseBody
    public JSONObject checkIsAppr(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("isAppr", "0");
        String apprId = request.getParameter("apprId");
        List<Map<String, Object>> apprList = this.getAppr(apprId);//查询课题组长
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
     * 查询课题组长审批
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//获取参数
        StringBuffer resultSql = new StringBuffer();
        resultSql
                .append("SELECT APPR.ID AS APPR_ID, APPR.T_P_ID AS PROJECTID, APPR.APPLY_UNIT, APPR.PROJECTNAME_SUBJECTCODE, \n");
        resultSql.append("APPR.CONTRACT_NAME, APPR.APPROVAL_UNIT, APPR.TOTAL_FUNDS, APPR.FINISH_FLAG, \n");
        resultSql.append("APPR.CONTRACT_TYPE, APPR.START_TIME, APPR.END_TIME, APPR.ACQUISITION_METHOD, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_PM_OUTCOME_CONTRACT_APPR APPR, T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
 + "AND EXT.ID='0401' \n"
                + "AND RECEIVE.RECEIVE_USERID = ? ";

        //未处理：需要是有效的
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };

        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
    /**
     * 统计查询页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goStatistics")
    public ModelAndView goStatistics(HttpServletRequest req) {
      return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractAppr4Statistics");
    }
    /**
     * 统计查询datagrid
     * @param tPmOutcomeContractAppr
     * @param request
     * @param response
     * @param dataGrid
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid4Statistics")
    public void datagrid4Statistics(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
        HttpServletResponse response, DataGrid dataGrid) {
      CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class, dataGrid);
      //查询条件组装器
      org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutcomeContractAppr,request.getParameterMap());
      cq.eq("finishFlag",ApprovalConstant.APPRSTATUS_FINISH);
      cq.add();
      this.tPmOutcomeContractApprService.getDataGridReturn(cq, true);

      TagUtil.datagrid(response, dataGrid);
    }
    
    /**
	 * 导出word
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "ExportWord")
	public String exportWord(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        
        TPmOutcomeContractApprEntity tPmOutcomeContractAppr = this.systemService.get(TPmOutcomeContractApprEntity.class, id);
        map.put("htbh", tPmOutcomeContractAppr.getContractCode() == null ? "" : tPmOutcomeContractAppr.getContractCode());
        map.put("sqdw", tPmOutcomeContractAppr.getApplyUnit() == null ? "" : tPmOutcomeContractAppr.getApplyUnit());
        map.put("xmmc", tPmOutcomeContractAppr.getProjectnameSubjectcode() == null ? "" : tPmOutcomeContractAppr.getProjectnameSubjectcode());
        map.put("htmc", tPmOutcomeContractAppr.getContractName() == null ? "" : tPmOutcomeContractAppr.getContractName());
        map.put("dfdw", tPmOutcomeContractAppr.getApprovalUnit() == null ? "" : tPmOutcomeContractAppr.getApprovalUnit());
        map.put("htdsf", tPmOutcomeContractAppr.getTheContractThird() == null ? "" : tPmOutcomeContractAppr.getTheContractThird());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        if(tPmOutcomeContractAppr.getStartTime() != null){
        	String kssj = formatter.format(tPmOutcomeContractAppr.getStartTime());
            map.put("kssj", kssj);
        }
        if(tPmOutcomeContractAppr.getEndTime() != null){
        	String jssj = formatter.format(tPmOutcomeContractAppr.getEndTime());
            map.put("jssj", jssj);
        }        
        map.put("zjf", tPmOutcomeContractAppr.getTotalFunds() == null ? "" : tPmOutcomeContractAppr.getTotalFunds());
        
        List<TPmContractNodeEntity> tPmContractNodeList = this.systemService.findByProperty(TPmContractNodeEntity.class, "inOutContractid", id);
        
        StringBuffer jdxx = new StringBuffer();
        
        for(TPmContractNodeEntity tPmContractNode : tPmContractNodeList){
        	jdxx.append("节点名称：" + (tPmContractNode.getContractNodeName() == null ? "" : tPmContractNode.getContractNodeName()) + "\t");
        	jdxx.append("支付金额：" + (tPmContractNode.getPayAmount() == null ? "" : tPmContractNode.getPayAmount()) + "\r\n");
        }      
        map.put("jdxx", jdxx.toString());
                
        modelMap.put(TemplateWordConstants.FILE_NAME, "科研采购合同签订审批表_" + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "export/template/cght.docx");
        
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;                   
	}
	
	/**
	 * 导出word
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportWordSp")
	public String exportWordSp(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String id = request.getParameter("id");
        String sptype = request.getParameter("sptype");
        Map<String, Object> map = new HashMap<String, Object>();
        
        String sql="";
        Map apprMap=null;
        String url="";
        String wjmc = "";
        
        //判断如果是进账合同申请
        if(sptype.equals("jz")){
        	sql = "select * from t_pm_income_contract_appr where id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            map.put("htbh", apprMap.get("CONTRACT_CODE") == null ? "" : apprMap.get("CONTRACT_CODE").toString());
            map.put("htmc", apprMap.get("CONTRACT_NAME") == null ? "" : apprMap.get("CONTRACT_NAME").toString());
            map.put("zjf", apprMap.get("TOTAL_FUNDS") == null ? "" : apprMap.get("TOTAL_FUNDS").toString());
            url = "export/template/jzhtsp.docx";
            wjmc = "进账合同申请审批表_";
        }
        
        //判断如果是出账合同申请
        if(sptype.equals("cz")){
        	sql = "select * from t_pm_outcome_contract_appr where id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECTNAME_SUBJECTCODE") == null ? "" : apprMap.get("PROJECTNAME_SUBJECTCODE").toString());
            map.put("htbh", apprMap.get("CONTRACT_CODE") == null ? "" : apprMap.get("CONTRACT_CODE").toString());
            map.put("htmc", apprMap.get("CONTRACT_NAME") == null ? "" : apprMap.get("CONTRACT_NAME").toString());
            map.put("zjf", apprMap.get("TOTAL_FUNDS") == null ? "" : apprMap.get("TOTAL_FUNDS").toString());
            url = "export/template/czhtsp.docx";
            wjmc = "出账合同申请审批表_";
        }
        
        //判断如果是出账合同申请
        if(sptype.equals("cg")){
        	sql = "select * from t_b_purchase_plan where id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            map.put("xmbm", apprMap.get("PROJECT_CODE") == null ? "" : apprMap.get("PROJECT_CODE").toString());
            map.put("zjf", apprMap.get("TOTAL_FUNDS") == null ? "" : apprMap.get("TOTAL_FUNDS").toString());
            url = "export/template/cgjh.docx";
            wjmc = "采购计划审批表_";
        }
        
        //判断如果是任务书
        if(sptype.equals("rws")){
        	sql = "select * from t_pm_task a left join t_Pm_Project b on a.t_p_id=b.id where a.id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            map.put("xmbm", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            map.put("zjf", apprMap.get("ALL_FEE") == null ? "" : apprMap.get("ALL_FEE").toString());
            url = "export/template/rws.docx";
            wjmc = "任务书审批表_";
        }
        
        //判断如果是项目预算
        if(sptype.equals("ys")){
        	sql = "select * from t_pm_project_funds_appr a left join t_Pm_Project b on a.t_p_id=b.id where a.id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            map.put("xmbm", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            map.put("zjf", apprMap.get("ALL_FEE") == null ? "" : apprMap.get("ALL_FEE").toString());
            url = "export/template/ys.docx";
            wjmc = "项目预算审批表_";
        }
        
        //判断如果是出账节点
        if(sptype.equals("czjd")){
        	sql = "select * from T_PM_OUTCONTRACT_NODE_CHECK a left join T_PM_CONTRACT_NODE b on a.contract_node_id=b.id where a.id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            map.put("htmc", apprMap.get("CONTRACT_NAME") == null ? "" : apprMap.get("CONTRACT_NAME").toString());
            map.put("htbh", apprMap.get("CONTRACT_CODE") == null ? "" : apprMap.get("CONTRACT_CODE").toString());
            map.put("zjf", apprMap.get("CONTRACT_TOTAL_FUNDS") == null ? "" : apprMap.get("CONTRACT_TOTAL_FUNDS").toString());
            url = "export/template/czjd.docx";
            wjmc = "出账节点考核及支付节点审批表_";
        }
        
        //判断如果是项目结题
        if(sptype.equals("jt")){
        	sql = "select * from t_pm_finish_apply where id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            url = "export/template/jt.docx";
            wjmc = "项目结题审批表_";
        }
        
        //判断如果是进账合同验收报告
        if(sptype.equals("jzys")){
        	sql = "select * from t_pm_contract_check where id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            map.put("htmc", apprMap.get("CONTRACT_NAME") == null ? "" : apprMap.get("CONTRACT_NAME").toString());
            map.put("htbh", apprMap.get("CONTRACT_CODE") == null ? "" : apprMap.get("CONTRACT_CODE").toString());
            map.put("qdsj", apprMap.get("CONTRACT_SIGNING_TIME") == null ? "" : apprMap.get("CONTRACT_SIGNING_TIME").toString());
            url = "export/template/jzys.docx";
            wjmc = "提交进账合同验收报告审批表_";
        }
        
        //判断如果是鉴定申请
        if(sptype.equals("jd")){
        	sql = "select * from t_b_appraisal_apply where id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            map.put("cgmc", apprMap.get("ACHIEVEMENT_NAME") == null ? "" : apprMap.get("ACHIEVEMENT_NAME").toString());
            map.put("djbh", apprMap.get("REGISTER_CODE") == null ? "" : apprMap.get("REGISTER_CODE").toString());
            url = "export/template/jd.docx";
            wjmc = "鉴定申请审批表_";
        }
        
        //判断如果是鉴定申请
        if(sptype.equals("jl")){
        	sql = "select * from t_b_result_reward where id=?";
            apprMap  = this.systemService.findOneForJdbc(sql, id);
            map.put("xmmc", apprMap.get("REWARD_NAME") == null ? "" : apprMap.get("REWARD_NAME").toString());
            map.put("dj", apprMap.get("REPORT_LEVEL") == null ? "" : apprMap.get("REPORT_LEVEL").toString());
            map.put("zjf", apprMap.get("INVESTED_AMOUNT") == null ? "" : apprMap.get("INVESTED_AMOUNT").toString());
            url = "export/template/jl.docx";
            wjmc = "成果奖励审批表_";
        }
        
        String sql2 = "select * from t_pm_appr_receive_logs a left join T_PM_FORM_CATEGORY b on a.suggestion_type=b.id where a.APPR_ID = ? ";
        List<Map<String, Object>> spMap  = this.systemService.findForJdbc(sql2, id);
        
        if(sptype.equals("czjd")){
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("项目组长意见")){
            		map.put("xmzz", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("承研单位意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("zlb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("rws")){
        	for(int a=0;a<spMap.size();a++){
        		if(spMap.get(a).get("LABEL").toString().equals("项目负责人意见")){
            		map.put("xmfzr", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("承研单位审核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("科研部业务处室审核意见")){
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kybld", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("ys")){
        	for(int a=0;a<spMap.size();a++){
        		if(spMap.get(a).get("LABEL").toString().equals("承研单位审核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("科研部计划处审核意见")){
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("校务部财务处审核意见")){
            		map.put("xwb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kybld", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("cz")){
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("项目负责人意见")){
            		map.put("xmfzr", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("承研单位审核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("质量管理办公室审核意见")){
            		map.put("zlglbgs", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("经费审查意见")){
            		map.put("jfsc", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("科研部计划处审核意见")){
            		map.put("kybjhc", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("cg")){
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("承研单位审核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("科研部计划处审核意见")){
            		map.put("kybjhc", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("校务部财务处审核意见")){
            		map.put("xwb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("jt")){
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("承研单位审核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("科研部计划处审核意见")){
            		map.put("kybjhc", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("校务部财务处审核意见")){
            		map.put("xwb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("jzys")){
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("承研单位考核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("jd")){
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("项目组长意见")){
            		map.put("xmzz", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("教研室审核意见")){
            		map.put("jys", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("院属系审核意见")){
            		map.put("ysx", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("学院（直属系）审核意见")){
            		map.put("xy", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("计划处审核意见")){
            		map.put("jhc", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("学术成果处审查意见")){
            		map.put("xscg", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else if(sptype.equals("jl")){
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("项目组长意见")){
            		map.put("xmzz", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("承研单位审核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("学术成果处审查意见")){
            		map.put("xscg", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kyb", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }else{
        	for(int a=0;a<spMap.size();a++){
            	if(spMap.get(a).get("LABEL").toString().equals("项目负责人意见")){
            		map.put("xmfzr", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("承研单位审核意见")){
            		map.put("cydw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("责任单位审核意见")){
            		map.put("zrdw", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("质量管理办公室审核意见")){
            		map.put("zlglbgs", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else if(spMap.get(a).get("LABEL").toString().equals("科研部组织计划处审核意见")){
            		map.put("kybjhc", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}else{
            		map.put("kybld", 
                			"审批人：" + (spMap.get(a).get("RECEIVE_USERNAME") == null ? "" : spMap.get(a).get("RECEIVE_USERNAME")) + "\t" +
                			"审批时间：" + (spMap.get(a).get("OPERATE_TIME") == null ? "" : spMap.get(a).get("OPERATE_TIME")) + "\t" +
                			"审批结果：" + (spMap.get(a).get("SUGGESTION_CONTENT") == null ? "" : spMap.get(a).get("SUGGESTION_CONTENT")) + "\t"
                	);
            	}
            }
        }
        
        modelMap.put(TemplateWordConstants.FILE_NAME, wjmc + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, url);
        
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;                   
	}
	
	
	/**
	 * 导出word
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportWordGzlSp")
	public String exportWordGzlSp(@RequestParam("processInstanceId") String processInstanceId,@RequestParam("businessCode") String businessCode,
			HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid,ModelMap modelMap) {
		List<Map<String,Object>> startAndEndlist = activitiDao.getActHiActinst(processInstanceId);
        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc().list();
        String start_user_id = activitiDao.getProcessStartUserId(processInstanceId);
      //update-begin--Author:zhoujf  Date:20150715 for：结束节点处理人获取，多数据库兼容处理
        String end_user_id = "";
        List<Map<String,Object>> taskinstList = activitiDao.getProcessEndUserId(processInstanceId,1,1);
        if(taskinstList!=null){
        	Map<String,Object> map = taskinstList.get(0);
        	end_user_id = map.get("ASSIGNEE_")==null?"":(String)map.get("ASSIGNEE_");
        }
      //update-end--Author:zhoujf  Date:20150715 for：结束节点处理人获取，多数据库兼容处理
    	StringBuffer rows = new StringBuffer();
        
        //开启节点
        for (Map<String, Object> m : startAndEndlist) {
            if ("startEvent".equals(m.get("ACT_TYPE_"))) {
                rows.append("{'name':'" + m.get("act_name_") + "','processInstanceId':'" + m.get("EXECUTION_ID_")
                        + "','startTime':'" + covertDateStr(m.get("START_TIME_")) + "','endTime':'"
                        + covertDateStr(m.get("END_TIME_")) + "','assignee':'"
                        + taskJeecgService.getUserRealName(start_user_id) + "','deleteReason':'"
                        + StringUtils.trimToEmpty("已完成") + "'}=");
            }
        }
        CriteriaQuery cq = new CriteriaQuery(TPBpmLog.class);
        cq.eq("bpm_id", processInstanceId);
        cq.addOrder("op_time", SortDirection.asc);
        cq.add();
        List<TPBpmLog> logList = this.systemService.getListByCriteriaQuery(cq, false);
        for (int i = 0; i < historicTasks.size(); i++) {
            HistoricTaskInstance hi = historicTasks.get(i);
        	String starttime = hi.getStartTime()==null?"":DateFormatUtils.format(hi.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            TPBpmLog log = null;
            if (i < logList.size()) {
                log = logList.get(i);
            }
        	String endtime = hi.getEndTime()==null?"":DateFormatUtils.format(hi.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            String opType = log == null ? "" : log.getOp_type();
			rows.append("{'name':'"+hi.getName()+"','processInstanceId':'"+hi.getProcessInstanceId() +"','startTime':'"+starttime+"','endTime':'"+endtime+"','assignee':'"+ taskJeecgService.getUserRealName(hi.getAssignee()) +"','deleteReason':'"+ ("completed".equals(StringUtils.trimToEmpty(hi.getDeleteReason()))?"已完成":StringUtils.trimToEmpty(hi.getDeleteReason())) +"','opType':'"+opType+"'}=");
        	//System.out.println(hi.getName()+"@"+hi.getAssignee()+"@"+hi.getStartTime()+"@"+hi.getEndTime());
        }
        //结束节点
        for(Map<String,Object> m:startAndEndlist){
        	if("endEvent".equals(m.get("ACT_TYPE_"))){
                rows.append("{'name':'结束','processInstanceId':'" + m.get("EXECUTION_ID_")
                        + "','startTime':'" + m.get("START_TIME_") + "','endTime':'" + m.get("END_TIME_")
                        + "','assignee':'" + taskJeecgService.getUserRealName(end_user_id) + "','deleteReason':'"
                        + StringUtils.trimToEmpty("已完成") + "'}=");
        	}
        }
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), "=");
		
		String [] sj = rowStr.split("=");
		String jg = "";
		Map jgMap = new HashMap();
		String sql="";
        Map apprMap=null;
        String url=""; 
        String wjmc = "";
        
        //申报书
        if(businessCode.equals("declare")){
        	sql = "select * from ACT_HI_PROCINST a left join t_b_pm_declare b on a.business_key_=b.id left join t_pm_project c on b.t_p_id=c.id where a.id_=?";
            apprMap  = this.systemService.findOneForJdbc(sql, processInstanceId);
            url = "export/template/sbs.docx";
            wjmc = "申报书审批表_";
            
            jgMap.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            jgMap.put("xmbh", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            jgMap.put("fzr", apprMap.get("PROJECT_MANAGER") == null ? "" : apprMap.get("PROJECT_MANAGER").toString());
            jgMap.put("lxdh", apprMap.get("CONTACT_PHONE") == null ? "" : apprMap.get("CONTACT_PHONE").toString());
            jgMap.put("txdz", apprMap.get("MAILING_ADDRESS") == null ? "" : apprMap.get("MAILING_ADDRESS").toString());
            jgMap.put("yjnr", apprMap.get("RESEARCH_CONTENT") == null ? "" : apprMap.get("RESEARCH_CONTENT").toString());
            
        }else if(businessCode.equals("reportReq")){ //申报需求
        	sql = "select * from ACT_HI_PROCINST a left join T_B_PM_REPORT_REQ b on a.business_key_=b.id left join t_pm_project c on b.project_id=c.id where a.id_=?";
            apprMap  = this.systemService.findOneForJdbc(sql, processInstanceId);
            url = "export/template/sbxq.docx";
            wjmc = "申报需求审批表_";
            
            jgMap.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            jgMap.put("xmbh", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            jgMap.put("sqr", apprMap.get("APPLICANTOR") == null ? "" : apprMap.get("APPLICANTOR").toString());
            jgMap.put("qzsj", apprMap.get("BEGIN_DATE").toString()+"-"+apprMap.get("END_DATE").toString());
            jgMap.put("lxxq", apprMap.get("RESEARCH_REQ") == null ? "" : apprMap.get("RESEARCH_REQ").toString());
            
        }else if(businessCode.equals("openSubject")){ //开题报告
        	sql = "select * from ACT_HI_PROCINST a left join T_B_PM_OPEN_SUBJECT b on a.business_key_=b.id left join t_pm_project c on b.t_p_project_id=c.id where a.id_=?";
            apprMap  = this.systemService.findOneForJdbc(sql, processInstanceId);
            url = "export/template/ktbg.docx";
            wjmc = "开题报告审批表_";
            
            jgMap.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            jgMap.put("xmbh", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            jgMap.put("js", apprMap.get("TECHNICAL_INDICATOR") == null ? "" : apprMap.get("TECHNICAL_INDICATOR").toString());
            jgMap.put("nr", apprMap.get("RESEARCH_CONTENTS") == null ? "" : apprMap.get("RESEARCH_CONTENTS").toString());
            
        }else if(businessCode.equals("payFirst")){ //垫支申请
        	sql = "select * from ACT_HI_PROCINST a left join T_B_PM_PAYFIRST b on a.business_key_=b.id left join t_pm_project c on b.project_id=c.id where a.id_=?";
            apprMap  = this.systemService.findOneForJdbc(sql, processInstanceId);
            url = "export/template/dzsq.docx";
            wjmc = "垫支申请审批表_";
            
            jgMap.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            jgMap.put("xmbh", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            jgMap.put("jf", apprMap.get("PAY_FUNDS") == null ? "" : apprMap.get("PAY_FUNDS").toString());
            jgMap.put("nd", apprMap.get("PAY_YEAR") == null ? "" : apprMap.get("PAY_YEAR").toString());
            jgMap.put("kmdm", apprMap.get("PAY_SUBJECTCODE") == null ? "" : apprMap.get("PAY_SUBJECTCODE").toString());
            
        }else if(businessCode.equals("abate")){ //减免申请
        	sql = "select * from ACT_HI_PROCINST a left join T_B_PM_ABATE b on a.business_key_=b.id left join t_pm_project c on b.project_id=c.id where a.id_=?";
            apprMap  = this.systemService.findOneForJdbc(sql, processInstanceId);
            url = "export/template/jmsq.docx";
            wjmc = "减免申请审批表_";
            
            jgMap.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            jgMap.put("xmbh", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            jgMap.put("jf", apprMap.get("ALL_FEE") == null ? "" : apprMap.get("ALL_FEE").toString());
            jgMap.put("jmly", apprMap.get("REASON") == null ? "" : apprMap.get("REASON").toString());
            jgMap.put("jmjtyj", apprMap.get("SUGGESTION") == null ? "" : apprMap.get("SUGGESTION").toString());
            
        }else{//项目变更
        	sql = "select * from ACT_HI_PROCINST a left join T_B_PM_PROJECT_CHANGE b on a.business_key_=b.id left join t_pm_project c on b.project_id=c.id where a.id_=?";
            apprMap  = this.systemService.findOneForJdbc(sql, processInstanceId);
            url = "export/template/xmbg.docx";
            wjmc = "项目变更审批表_";
            
            jgMap.put("xmmc", apprMap.get("PROJECT_NAME") == null ? "" : apprMap.get("PROJECT_NAME").toString());
            jgMap.put("xmbh", apprMap.get("PROJECT_NO") == null ? "" : apprMap.get("PROJECT_NO").toString());
            jgMap.put("bgyy", apprMap.get("CHANGE_REASON") == null ? "" : apprMap.get("CHANGE_REASON").toString());
            jgMap.put("bgyj", apprMap.get("CHANGE_ACCORDING") == null ? "" : apprMap.get("CHANGE_ACCORDING").toString());
        }
        
        if(businessCode.equals("declare")){
        	for(int a=0;a<sj.length;a++){
    			JSONObject  jasonObject = JSONObject.fromObject(sj[a]);
    			Map map = (Map)jasonObject;
    			if(map.get("name").toString().equals("院系审核")){
    				jgMap.put("yxsh",
    						"流程节点名称：" + (map.get("name") == null ? "" : map.get("name")) + "\t" +
    						"负责人：" + (map.get("assignee") == null ? "" : map.get("assignee")) + "\t" +
    						"处理结果：" + (map.get("opType") == null ? "" : map.get("opType")) + "\n"
    				);
    			}
    			if(map.get("name").toString().equals("科研部计划处")){
    				jgMap.put("kybjhc",
    						"流程节点名称：" + (map.get("name") == null ? "" : map.get("name")) + "\t" +
    						"负责人：" + (map.get("assignee") == null ? "" : map.get("assignee")) + "\t" +
    						"处理结果：" + (map.get("opType") == null ? "" : map.get("opType")) + "\n"
    				);
    			}
    		};
        }else{
        	for(int a=0;a<sj.length;a++){
    			JSONObject  jasonObject = JSONObject.fromObject(sj[a]);
    			Map map = (Map)jasonObject;
    			if(map.get("name").toString().equals("院系审核")){
    				jgMap.put("yxsh",
    						"流程节点名称：" + (map.get("name") == null ? "" : map.get("name")) + "\t" +
    						"负责人：" + (map.get("assignee") == null ? "" : map.get("assignee")) + "\t" +
    						"处理结果：" + (map.get("opType") == null ? "" : map.get("opType")) + "\n"
    				);
    			}
    			if(map.get("name").toString().equals("机关")){
    				jgMap.put("jg",
    						"流程节点名称：" + (map.get("name") == null ? "" : map.get("name")) + "\t" +
    						"负责人：" + (map.get("assignee") == null ? "" : map.get("assignee")) + "\t" +
    						"处理结果：" + (map.get("opType") == null ? "" : map.get("opType")) + "\n"
    				);
    			}
    			if(map.get("name").toString().equals("科研部领导")){
    				jgMap.put("kybld",
    						"流程节点名称：" + (map.get("name") == null ? "" : map.get("name")) + "\t" +
    						"负责人：" + (map.get("assignee") == null ? "" : map.get("assignee")) + "\t" +
    						"处理结果：" + (map.get("opType") == null ? "" : map.get("opType")) + "\n"
    				);
    			}
    		};
        }
    	
		modelMap.put(TemplateWordConstants.FILE_NAME, wjmc + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, jgMap);
        modelMap.put(TemplateWordConstants.URL, url);
        
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
	}
	
	/**
     * 日期格式转换
     * 
     * @param obj
     * @return
     */
    private String covertDateStr(Object obj) {
        String timeStr = "";
        if (obj != null) {
        Timestamp timestmp = (Timestamp) obj;
        Date time = new Date(timestmp.getTime());
            timeStr = DateUtils.date2Str(time, DateUtils.datetimeFormat);
        }
        return timeStr;
    }
    
 // -----------------------------------------------------------------------------------
 	// 以下各函数可以提成共用部件 (Add by Quainty)
 	// -----------------------------------------------------------------------------------
 	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
 		response.setContentType("application/json");
 		response.setHeader("Cache-Control", "no-store");
 		try {
 			PrintWriter pw=response.getWriter();
 			pw.write(jObject.toString());
 			pw.flush();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
 	}
	
	/**
     * 进账合同审批处理tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goConfirmTab")
    public ModelAndView goConfirmTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprList-confirmTab");
    }

    /**
     * 进账合同确认列表
     * 
     * @return
     */
    @RequestMapping(params = "tPmOutcomeContractListConfirm")
    public ModelAndView tPmOutcomeContractListConfirm(HttpServletRequest request) {
        //处理审批
        //0：未处理；1：已处理
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
        }
        return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractApprList-confirm");
    }

    /**
     * 进账合同确认列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForConfirm")
    public void datagridForConfirm(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批
            TSUser user = ResourceUtil.getSessionUserName();
            List<TPmProjectEntity> projectList = this.systemService.findByProperty(TPmProjectEntity.class,
                    "approvalUserid", user.getId());
            if (projectList.size() > 0) {
                List<String> projectIdList = new ArrayList<String>();
                for (TPmProjectEntity proj : projectList) {
                    projectIdList.add(proj.getId());
                }
                String operateStatus = request.getParameter("operateStatus");
                CriteriaQuery cq = new CriteriaQuery(TPmOutcomeContractApprEntity.class, dataGrid);
                org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOutcomeContractAppr,
                        request.getParameterMap());
                cq.in("project.id", projectIdList.toArray());
                if ("0".equals(operateStatus)) {
                    cq.eq("contractBookFlag", "1");//查询已上传未确认的数据
                } else {
                    cq.eq("contractBookFlag", "2");//查询已确认的数据
                }
                cq.addOrder("createDate", SortDirection.desc);
                cq.add();
                this.tPmOutcomeContractApprService.getDataGridReturn(cq, true);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
    
    /**
     * 确认合同正本
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "confirmContractBook")
    @ResponseBody
    public AjaxJson confirmContractBook(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try{
        if (StringUtil.isNotEmpty(id)) {
            TPmOutcomeContractApprEntity contract = tPmOutcomeContractApprService.get(TPmOutcomeContractApprEntity.class,
                    id);
            if (contract != null) {
                contract.setContractBookFlag("2");//将状态改为已确认
                this.systemService.updateEntitie(contract);
            }
            j.setMsg("合同正本上传标志更新成功！");
        }
        }catch(Exception e){
            e.printStackTrace();
            j.setMsg("合同正本上传标志更新失败！");
        }
        return j;
    }
}
