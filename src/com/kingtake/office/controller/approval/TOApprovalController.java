package com.kingtake.office.controller.approval;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
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
import org.jeecgframework.web.system.service.TSFilesService;
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
import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SendBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.approval.TOApprovalEntity;
import com.kingtake.office.entity.approval.TOApprovalProjectSummaryEntity;
import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;
import com.kingtake.office.entity.flow.TOFlowSendLogsEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.service.approval.TOApprovalServiceI;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.office.service.sendReceive.TOSendReceiveRegServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: 呈批件信息表
 * @author onlineGenerator
 * @date 2015-08-12 10:45:49
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOApprovalController")
public class TOApprovalController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOApprovalController.class);

    @Autowired
    private TOApprovalServiceI tOApprovalService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    @Autowired
    private TSFilesService tsFilesService;
    @Autowired
    private TOSendReceiveRegServiceI regService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 公文列表检索页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "archiveList")
    public ModelAndView archiveList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/approval/archiveList");
    }

    /**
     * 呈批件信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOApproval")
    public ModelAndView tOApproval(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/approval/tOApprovalList");
    }

    /**
     * 呈批件接收人列表（已处理、未处理）
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tOApprovalListToMe")
    public ModelAndView tOApprovalListToMe(HttpServletRequest request) {
        request.setAttribute("operateStatus", request.getParameter("operateStatus"));
        request.setAttribute("registerType", request.getParameter("registerType"));
        return new ModelAndView("com/kingtake/office/approval/tOApprovalListToMe");
    }

    @RequestMapping(params = "goFirstSend")
    public ModelAndView goFirstSend(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/approval/firstSendOrg");
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
    public void datagrid(TOApprovalEntity tOApproval, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String operateStatus = request.getParameter("operateStatus");
        String title = request.getParameter("TITLE");
        String unitName = request.getParameter("RECEIVE.UNIT.NAME");
        try {
            //自定义追加查询条件
            TSUser user = ResourceUtil.getSessionUserName();
            String sql = "SELECT T.FILE_NUM_PREFIX||'20'||T.APPROVAL_YEAR||T.APPLICATION_FILENO AS FILE_NUM,T.*,R.id AS RID ";
            String cql = "SELECT COUNT(0) ";
            StringBuffer sb = new StringBuffer();
            sb.append(" FROM T_O_APPROVAL T JOIN T_O_FLOW_RECEIVE_LOGS R ON T.ID = R.SEND_RECEIVE_ID AND R.OPERATE_STATUS = ? AND R.RECEIVE_USERID=? WHERE 1=1 ");
            if (StringUtil.isNotEmpty(title)) {
                sb.append(" AND T.TITLE LIKE '%" + title + "%'");
            }
            if (StringUtil.isNotEmpty(unitName)) {
                sb.append(" AND T.RECEIVE_UNIT_NAME LIKE '%" + unitName + "%'");
            }
            sb.append(" ORDER BY  T.CREATE_DATE DESC");
            String[] params = new String[] { operateStatus, user.getId()};
            List<Map<String, Object>> result = systemService.findForJdbcParam(sql + sb.toString(), dataGrid.getPage(),
                    dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(cql + sb.toString(), params);
            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goOfficePage")
    public ModelAndView goOfficePage(HttpServletRequest req, TOApprovalEntity tOApprovalBill) {
        if (StringUtil.isNotEmpty(tOApprovalBill.getId())) {
            tOApprovalBill = systemService.getEntity(TOApprovalEntity.class, tOApprovalBill.getId());
            req.setAttribute("tOApprovalPage", tOApprovalBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOApprovalBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/approval/onlyOfficePage");
    }

    /**
     * easyui 公文检索
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "queryDatagrid")
    public void queryDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        StringBuffer approvalSql = new StringBuffer();
        approvalSql.append("SELECT 'APPR' AS TYPE, ID, SECRITY_GRADE AS SECURITY_GRADE, UNDERTAKE_UNIT_ID, UNDERTAKE_UNIT_NAME, \n");
        approvalSql
                .append("CONTACT_ID, CONTACT_NAME, CONTACT_PHONE, FILE_NUM_PREFIX||'20'||APPROVAL_YEAR||APPLICATION_FILENO AS FILE_NUM, \n");
        approvalSql.append("TITLE, ARCHIVE_FLAG, ARCHIVE_USERID, ARCHIVE_USERNAME, ARCHIVE_DATE, '1' AS EAUDIT_FLAG, \n");
        approvalSql.append("CREATE_BY, CREATE_NAME, CREATE_DATE, UPDATE_BY, UPDATE_NAME, UPDATE_DATE \n");
        approvalSql.append("FROM T_O_APPROVAL WHERE ARCHIVE_FLAG='" + ReceiveBillConstant.BILL_ARCHIVED + "' \n");
        StringBuffer receiveBillSql = new StringBuffer();
        receiveBillSql
                .append("SELECT 'RECEIVE' AS TYPE, ID, SECRITY_GRADE AS SECURITY_GRADE, DUTY_ID AS UNDERTAKE_UNIT_ID, DUTY_NAME AS UNDERTAKE_UNIT_NAME, \n");
        receiveBillSql
                .append("CONTACT_ID, CONTACT_NAME, CONTACT_TEL AS CONTACT_PHONE, FILE_NUM_PREFIX||'20'||FILE_NUM_YEAR||BILL_NUM AS FILE_NUM, \n");
        receiveBillSql.append("TITLE, ARCHIVE_FLAG, ARCHIVE_USERID, ARCHIVE_USERNAME, ARCHIVE_DATE, '1' AS EAUDIT_FLAG, \n");
        receiveBillSql.append("CREATE_BY, CREATE_NAME, CREATE_DATE, UPDATE_BY, UPDATE_NAME, UPDATE_DATE \n");
        receiveBillSql
                .append("FROM T_O_RECEIVE_BILL WHERE ARCHIVE_FLAG='" + ReceiveBillConstant.BILL_ARCHIVED + "' \n");
        StringBuffer sendBillSql = new StringBuffer();
        sendBillSql.append("SELECT 'SEND' AS TYPE, ID, SECRITY_GRADE AS SECURITY_GRADE, UNDERTAKE_UNIT_ID, UNDERTAKE_UNIT_NAME, \n");
        sendBillSql
                .append("CONTACT_ID, CONTACT_NAME, CONTACT_PHONE, FILE_NUM_PREFIX||'20'||SEND_YEAR||SEND_NUM AS FILE_NUM, SEND_TITLE AS \n");
        sendBillSql.append("TITLE, ARCHIVE_FLAG, ARCHIVE_USERID, ARCHIVE_USERNAME, ARCHIVE_DATE, '1' AS EAUDIT_FLAG, \n");
        sendBillSql.append("CREATE_BY, CREATE_NAME, CREATE_DATE, UPDATE_BY, UPDATE_NAME, UPDATE_DATE \n");
        sendBillSql.append("FROM T_O_SEND_BILL WHERE ARCHIVE_FLAG='" + ReceiveBillConstant.BILL_ARCHIVED + "' \n");
    	StringBuffer sendreceiveRegSql = new StringBuffer();
    	sendreceiveRegSql.append("SELECT 'REG' AS TYPE, ID, SECURITY_GRADE, UNDERTAKE_DEPT_ID AS UNDERTAKE_UNIT_ID, UNDERTAKE_DEPT_NAME AS UNDERTAKE_UNIT_NAME, \n");
    	sendreceiveRegSql
                .append("'' AS CONTACT_ID, CONTACT AS CONTACT_NAME, CONTACT_PHONE, MERGE_FILE_NUM AS FILE_NUM,  \n");
    	sendreceiveRegSql.append("TITLE, '' AS ARCHIVE_FLAG, ARCHIVE_USERID, ARCHIVE_USERNAME, ARCHIVE_DATE, EAUDIT_FLAG, \n");
    	sendreceiveRegSql.append("CREATE_BY, CREATE_NAME, CREATE_DATE, UPDATE_BY, UPDATE_NAME, UPDATE_DATE \n");
    	sendreceiveRegSql.append("FROM T_O_SEND_RECEIVE_REG WHERE EAUDIT_FLAG = '0' \n");
//    	sendreceiveReg.append("SELECT REG_TYPE AS TYPE, ID, SECURITY_GRADE, UNDERTAKE_DEPT_ID, UNDERTAKE_DEPT_NAME, ");
//    	sendreceiveReg.append("CONTACT AS CONTACT_NAME, CONTACT_PHONE, MERGE_FILE_NUM, ");
//    	sendreceiveReg.append("TITLE, ARCHIVE_USERID, ARCHIVE_USERNAME, ARCHIVE_DATE, EAUDIT_FLAG, ");
//    	sendreceiveReg.append("CREATE_BY, CREATE_NAME, CREATE_DATE, UPDATE_BY, UPDATE_NAME, UPDATE_DATE ");
//    	sendreceiveReg.append("FROM T_O_SEND_RECEIVE_REG WHERE EAUDIT_FLAG = '1' AND GENERATION_FLAG = '3' ");
//    	sendreceiveReg.append("UNION ");
//    	sendreceiveReg.append("SELECT REG_TYPE AS TYPE, ID, SECURITY_GRADE, UNDERTAKE_DEPT_ID, UNDERTAKE_DEPT_NAME, ");
//    	sendreceiveReg.append("CONTACT AS CONTACT_NAME, CONTACT_PHONE, FILE_NUM, ");
//    	sendreceiveReg.append("TITLE, ARCHIVE_USERID, ARCHIVE_USERNAME, ARCHIVE_DATE, EAUDIT_FLAG, ");
//    	sendreceiveReg.append("CREATE_BY, CREATE_NAME, CREATE_DATE, UPDATE_BY, UPDATE_NAME, UPDATE_DATE  ");
//    	sendreceiveReg.append("FROM T_O_SEND_RECEIVE_REG WHERE EAUDIT_FLAG = '0' ");
    	
        String fileNum = request.getParameter("file.num");
        if (StringUtil.isNotEmpty(fileNum)) {
        	sendreceiveRegSql.append("AND FILE_NUM LIKE '%" + fileNum + "%' \n");
            approvalSql.append("AND APPLICATION_FILENO LIKE '%" + fileNum + "%' \n");
            receiveBillSql.append("AND BILL_NUM LIKE '%" + fileNum + "%' \n");
            sendBillSql.append("AND SEND_NUM LIKE '%" + fileNum + "%' \n");
        }
        String title = request.getParameter("title");
        if (StringUtil.isNotEmpty(title)) {
        	sendreceiveRegSql.append("AND TITLE LIKE '%" + title + "%' \n");
            approvalSql.append("AND TITLE LIKE '%" + title + "%' \n");
            receiveBillSql.append("AND TITLE LIKE '%" + title + "%' \n");
            sendBillSql.append("AND SEND_TITLE LIKE '%" + title + "%' \n");
        }
        String unitName = request.getParameter("undertake.unit.name");
        if (StringUtil.isNotEmpty(unitName)) {
        	sendreceiveRegSql.append("AND UNDERTAKE_DEPT_NAME LIKE '%" + unitName + "%' \n");
            approvalSql.append("AND UNDERTAKE_UNIT_NAME LIKE '%" + unitName + "%' \n");
            receiveBillSql.append("AND DUTY_NAME LIKE '%" + unitName + "%' \n");
            sendBillSql.append("AND UNDERTAKE_UNIT_NAME LIKE '%" + unitName + "%' \n");
        }
        String secrityGrad = request.getParameter("secrity.grade");
        if (StringUtil.isNotEmpty(secrityGrad)) {
        	sendreceiveRegSql.append("AND SECRITY_GRADE LIKE '%" + secrityGrad + "%' \n");
            approvalSql.append("AND SECRITY_GRADE LIKE '%" + secrityGrad + "%' \n");
            receiveBillSql.append("AND SECRITY_GRADE LIKE '%" + secrityGrad + "%' \n");
            sendBillSql.append("AND SECRITY_GRADE LIKE '%" + secrityGrad + "%' \n");
        }
        String contactName = request.getParameter("contact.name");
        if (StringUtil.isNotEmpty(contactName)) {
        	sendreceiveRegSql.append("AND CONTACT_NAME LIKE '%" + contactName + "%' \n");
            approvalSql.append("AND CONTACT_NAME LIKE '%" + contactName + "%' \n");
            receiveBillSql.append("AND CONTACT_NAME LIKE '%" + contactName + "%' \n");
            sendBillSql.append("AND CONTACT_NAME LIKE '%" + contactName + "%' \n");
        }
        String resultSql = approvalSql.append("UNION ALL \n").append(receiveBillSql).append("UNION ALL \n")
                .append(sendBillSql).append("UNION ALL \n").append(sendreceiveRegSql).append("ORDER BY CREATE_DATE \n").toString();
        List<Map<String, Object>> results = systemService
                .findForJdbc(resultSql, dataGrid.getPage(), dataGrid.getRows());
        Long count = systemService.getCountForJdbc("SELECT count(0) FROM(" + resultSql + ")");
        dataGrid.setResults(results);
        dataGrid.setTotal(count.intValue());
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除呈批件信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOApproval = systemService.getEntity(TOApprovalEntity.class, tOApproval.getId());
        message = "呈批件信息表删除成功";
        try {
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
            List<TOFlowReceiveLogsEntity> rlist = new ArrayList<TOFlowReceiveLogsEntity>();
            List<TOFlowSendLogsEntity> slist = new ArrayList<TOFlowSendLogsEntity>();
            rcq.eq("sendReceiveId", tOApproval.getId());
            rcq.add();
            scq.eq("sendReceiveId", tOApproval.getId());
            scq.add();
            rlist = systemService.getListByCriteriaQuery(rcq, false);
            slist = systemService.getListByCriteriaQuery(scq, false);
            for (TOFlowReceiveLogsEntity r : rlist) {
                systemService.delete(r);
            }
            for (TOFlowSendLogsEntity s : slist) {
                systemService.delete(s);
            }
            tOApprovalService.delete(tOApproval);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "呈批件信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除呈批件信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "呈批件信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TOApprovalEntity tOApproval = systemService.getEntity(TOApprovalEntity.class, id);
                CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
                List<TOFlowReceiveLogsEntity> rlist = new ArrayList<TOFlowReceiveLogsEntity>();
                List<TOFlowSendLogsEntity> slist = new ArrayList<TOFlowSendLogsEntity>();
                rcq.eq("sendReceiveId", tOApproval.getId());
                rcq.add();
                scq.eq("sendReceiveId", tOApproval.getId());
                scq.add();
                rlist = systemService.getListByCriteriaQuery(rcq, false);
                slist = systemService.getListByCriteriaQuery(scq, false);
                for (TOFlowReceiveLogsEntity r : rlist) {
                    systemService.delete(r);
                }
                for (TOFlowSendLogsEntity s : slist) {
                    systemService.delete(s);
                }
                tOApprovalService.delete(tOApproval);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "呈批件信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加呈批件信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "呈批件信息表添加成功";
        try {
            String projectIds = request.getParameter("projectids");
            if (StringUtil.isNotEmpty(projectIds)) {
                tOApproval.setBusinessTablename("t_o_approval_project_summary");
            }
            tOApprovalService.save(tOApproval);
            if (StringUtil.isNotEmpty(tOApproval.getRegId())) {
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, tOApproval.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_FLOWING);
                systemService.updateEntitie(reg);
                regService.copyFile(reg.getId(), tOApproval.getId(), "tOApproval");
            }
            //呈批件第一次发送
            firstSend(tOApproval, request);
            if (StringUtil.isNotEmpty(projectIds)) {
                String[] pids = projectIds.split(",");
                for (String pid : pids) {
                    TOApprovalProjectSummaryEntity tOApprovalProjectSummary = new TOApprovalProjectSummaryEntity();
                    TPmProjectEntity project = systemService.get(TPmProjectEntity.class, pid);
                    tOApprovalProjectSummary.setApprovalId(tOApproval.getId());
                    tOApprovalProjectSummary.setAccordingNum(project.getAccordingNum());
                    tOApprovalProjectSummary.setAllFee(project.getAllFee());
                    tOApprovalProjectSummary.setBeginDate(project.getBeginDate());
                    tOApprovalProjectSummary.setDevDepart(project.getDevDepart());
                    tOApprovalProjectSummary.setEndDate(project.getEndDate());
                    tOApprovalProjectSummary.setFeeType(project.getFeeType());
                    tOApprovalProjectSummary.setManageDepart(project.getManageDepart());
                    tOApprovalProjectSummary.setProjectId(project.getId());
                    tOApprovalProjectSummary.setProjectManager(project.getProjectManager());
                    tOApprovalProjectSummary.setProjectManagerId(project.getProjectManagerId());
                    tOApprovalProjectSummary.setProjectName(project.getProjectName());
                    tOApprovalProjectSummary.setProjectNo(project.getProjectNo());
                    tOApprovalProjectSummary.setProjectSource(project.getProjectSource());
                    tOApprovalProjectSummary.setProjectType(project.getProjectType());
                    systemService.save(tOApprovalProjectSummary);
                }
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "呈批件信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOApproval);
        j.setMsg(message);
        return j;
    }

    public void firstSend(TOApprovalEntity tOApproval, HttpServletRequest request) {
        String receiverid = request.getParameter("receiverid");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        String realName = request.getParameter("realName");
        String leaderReview = request.getParameter("leaderReview");
        //发送信息
        TOFlowSendLogsEntity send = new TOFlowSendLogsEntity();
        TSUser user = ResourceUtil.getSessionUserName();
        send.setSendReceiveId(tOApproval.getId());
        send.setOperateDate(new Date());
        send.setOperateDepartid(user.getCurrentDepart().getId());
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        send.setOperateIp(ip);
        send.setOperateUserid(user.getId());
        send.setOperateUsername(user.getRealName());
        send.setSenderTip("通过，" + leaderReview);
        systemService.save(send);
        //接收信息
        TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
        receive.setOperateStatus(SrmipConstants.NO);
        receive.setReceiveDepartid(departId);
        receive.setReceiveDepartname(departName);
        receive.setReceiveUserid(receiverid);
        receive.setReceiveUsername(realName);
        receive.setSendReceiveId(tOApproval.getId());
        receive.setToFlowSendId(send.getId());
        receive.setValidFlag(SrmipConstants.YES);
        systemService.save(receive);
        //发送消息给核稿人
        tOMessageService.sendMessage(receive.getReceiveUserid(), "您有新的呈批件需要处理！", "呈批件", "您有新的呈批件需要处理！请到协同办公->发文管理中办理！",
                user.getId());
    }

    /**
     * 更新呈批件信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "呈批件信息表更新成功";
        TOApprovalEntity t = tOApprovalService.get(TOApprovalEntity.class, tOApproval.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOApproval, t);
            tOApprovalService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "呈批件信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * 呈批件信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOApprovalEntity tOApproval, HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        Calendar time = Calendar.getInstance();
        tOApproval.setApprovalYear(String.valueOf(time.get(Calendar.YEAR) - 2000));
        tOApproval.setArchiveFlag(ReceiveBillConstant.BILL_FLOWING);
        //tOApproval.setContactId(user.getId());
        //tOApproval.setContactName(user.getRealName());
        //tOApproval.setContactPhone(user.getMobilePhone());
        tOApproval.setSignUnitid(user.getCurrentDepart().getId());
        tOApproval.setSignUnitname(user.getCurrentDepart().getDepartname());
        //tOApproval.setUndertakeUnitId(user.getCurrentDepart().getId());
        //tOApproval.setUndertakeUnitName(user.getCurrentDepart().getDepartname());
        tOApproval.setCreateDate(new Date());
        String regid = req.getParameter("regid");
        if (StringUtil.isNotEmpty(regid)) {
            TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, regid);
            tOApproval.setRegId(reg.getId());
            tOApproval.setApplicationFileno(reg.getFileNum());
            tOApproval.setSecrityGrade(reg.getSecurityGrade());
            tOApproval.setRegisterType(reg.getRegisterType());
            tOApproval.setTitle(reg.getTitle());
            tOApproval.setApprovalYear(reg.getFileNumYear());
            tOApproval.setFileNumPrefix(reg.getFileNumPrefix());
            tOApproval.setContactId(reg.getUndertakeContactId());
            tOApproval.setContactName(reg.getUndertakeContactName());
            tOApproval.setContactPhone(reg.getUndertakeTelephone());
            tOApproval.setUndertakeUnitId(reg.getUndertakeDeptId());
            tOApproval.setUndertakeUnitName(reg.getUndertakeDeptName());
            tOApproval.setContentFileId(reg.getContentFileId());
            tOApproval.setProjectRelaId(reg.getProjectRelaId());
            tOApproval.setProjectRelaName(reg.getProjectRelaName());
            req.setAttribute("regType", reg.getRegType());
            List<TSFilesEntity> files = tsFilesService.getFileListByBid(reg.getId());
            req.setAttribute("files", files);
            List<Map<String, Object>> list = regService.findModelByRegType(reg.getRegType());
            req.setAttribute("list", list);
            if (StringUtils.isNotEmpty(reg.getContentFileId())) {
                TOOfficeOnlineFilesEntity officeFile = systemService.get(TOOfficeOnlineFilesEntity.class,
                        reg.getContentFileId());
                req.setAttribute("officeFile", officeFile);
            }
        }
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = tOApprovalService.getEntity(TOApprovalEntity.class, tOApproval.getId());
        }
        req.setAttribute("tOApprovalPage", tOApproval);
        return new ModelAndView("com/kingtake/office/approval/tOApproval-add");
    }

    /**
     * 发送页面跳转
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goSend")
    public ModelAndView goSend(TOApprovalEntity tOApproval, HttpServletRequest req) {
        String id = req.getParameter("id");
        String rid = req.getParameter("rid");
        if (StringUtil.isNotEmpty(id)) {
            tOApproval = systemService.get(TOApprovalEntity.class, id);
            TOApprovalEntity tmp = new TOApprovalEntity();
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tOApproval, tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tmp.getContactId().contains(",")) {
                tmp.setContactId(tmp.getContactId().split(",")[0]);
                tmp.setContactName(tmp.getContactName().split(",")[0]);
                tmp.setUndertakeUnitId(tmp.getUndertakeUnitId().split(",")[0]);
                tmp.setUndertakeUnitName(tmp.getUndertakeUnitName().split(",")[0]);
            }
            req.setAttribute("tOApprovalPage", tmp);
        }
        req.setAttribute("id", id);
        req.setAttribute("rid", rid);
        return new ModelAndView("com/kingtake/office/approval/sendOrg");
    }

    /**
     * 呈批件发送操作
     * 
     * @param tOApproval
     * @param request
     * @return
     */
    @RequestMapping(params = "send")
    @ResponseBody
    public AjaxJson send(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String rid = request.getParameter("rid");
        String opinion = request.getParameter("opinion");
        String receiverid = request.getParameter("receiverid");
        String leaderReview = request.getParameter("leaderReview");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        message = "呈批件发送成功！";
        try {
            TOApprovalEntity t = tOApprovalService.get(TOApprovalEntity.class, tOApproval.getId());
            MyBeanUtils.copyBeanNotNull2Bean(tOApproval, t);
            TSUser user = ResourceUtil.getSessionUserName();
            if (ReceiveBillConstant.AUDIT_PASS.equals(opinion)) {//通过
                //            tOReceiveBill = systemService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
                TOFlowSendLogsEntity send = new TOFlowSendLogsEntity();
                send.setOperateUserid(user.getId());
                send.setOperateUsername(user.getRealName());
                send.setOperateDate(new Date());
                send.setFlowReceiveId(rid);
                String ip = "";
                if (request.getHeader("x-forwarded-for") == null) {
                    ip = request.getRemoteAddr();
                } else {
                    ip = request.getHeader("x-forwarded-for");
                }
                send.setSendReceiveId(tOApproval.getId());
                send.setOperateIp(ip);
                send.setOperateDepartid(user.getCurrentDepart().getId());
                send.setOperateDepartname(user.getCurrentDepart().getDepartname());
                send.setSenderTip("通过，" + leaderReview);
                systemService.save(send);
                TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
                receive.setToFlowSendId(send.getId());
                receive.setSendReceiveId(tOApproval.getId());
                TSUser receiver = systemService.get(TSUser.class, receiverid);
                receive.setReceiveUserid(receiver.getId());
                receive.setReceiveDepartid(departId);
                receive.setValidFlag(SrmipConstants.YES);//默认有效
                //            receive.setSignInFlag(SrmipConstants.NO);//签收状态默认为未签收
                receive.setReceiveDepartname(departName);
                receive.setReceiveUsername(receiver.getRealName());
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
                systemService.save(receive);
                //                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tOMessageService.sendMessage(receiverid, "新的呈批件消息", "呈批件", "您有新的呈批件需处理\n呈批件标题[" + t.getTitle()
                        + "]\n请到协同办公->发文管理中办理", send.getOperateUserid());
            } else {//驳回
                t.setBackUserid(user.getId());
                t.setBackUsername(user.getRealName());
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);//公文状态置为被驳回
                systemService.updateEntitie(t);
                tOMessageService.sendMessage(t.getContactId(), "呈批件[" + t.getTitle() + "]被驳回！", "呈批件",
                        "驳回人：" + user.getRealName() + "\n意见说明：" + leaderReview, user.getId());
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, t.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_REBUT);
                systemService.updateEntitie(reg);
            }

            //发送给下一步处理人即为办理完成
            TOFlowReceiveLogsEntity receiveLog = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receiveLog.setOperateStatus(SrmipConstants.YES);
            receiveLog.setOperateTime(new Date());
            receiveLog.setSuggestionCode(opinion);
            systemService.updateEntitie(receiveLog);
        } catch (Exception e) {
            e.printStackTrace();
            message = "呈批件发送失败！";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * Ajax请求，呈批件签收
     * 
     * @param tOReceiveBill
     * @param request
     * @return
     */
    @RequestMapping(params = "doSignIn")
    @ResponseBody
    public AjaxJson doSignIn(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "签收成功！";
        try {
            tOApproval = systemService.getEntity(TOApprovalEntity.class, tOApproval.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            cq.eq("sendReceiveId", tOApproval.getId());
            cq.eq("receiveUserid", user.getId());
            cq.eq("signInFlag", SrmipConstants.NO);
            cq.add();
            List<TOFlowReceiveLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TOFlowReceiveLogsEntity receive = list.get(0);
                receive.setSignInFlag(SrmipConstants.YES);
                receive.setSignInTime(new Date());
                systemService.updateEntitie(receive);
            }
            tOApprovalService.save(tOApproval);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = "签收失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 是否可编辑
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "ifEdit")
    @ResponseBody
    public AjaxJson ifEdit(TOApprovalEntity tOApproval, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String editFlag = "0";//仅查看
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = tOApprovalService.getEntity(TOApprovalEntity.class, tOApproval.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            if (!tOApproval.getArchiveFlag().equals(ReceiveBillConstant.BILL_COMPLETE)
                    && user.getUserName().equals(tOApproval.getCreateBy())) {//未归档且当前登陆人为创建人
                editFlag = "2";//显示发送按钮
                CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                rcq.eq("sendReceiveId", tOApproval.getId());
                rcq.eq("receiveUserid", user.getId());
                rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
                rcq.add();
                List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
                if (rlist.size() > 0) {
                    if (rlist.get(0).getOperateStatus().equals(ReceiveBillConstant.OPERATE_UNTREATED)) {//既为创建人又为接收人且处理状态为未处理时显示填写按钮
                        editFlag = "1";//显示批示意见按钮
                    }
                }
            } else {//当前登陆人不为创建人
                if (!tOApproval.getArchiveFlag().equals(ReceiveBillConstant.BILL_COMPLETE)
                        && req.getParameter("operateStatus").equals(ReceiveBillConstant.OPERATE_UNTREATED)) {//未处理且该信件未归档时显示填写按钮
                    editFlag = "1";
                }
            }
        }
        j.setObj(editFlag);
        //        j.setSuccess(true);
        return j;
    }

    /**
     * 呈批件处理时已tab页展示其关联信息
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goOperateTab")
    public ModelAndView goOperateTab(TOApprovalEntity tOApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = tOApprovalService.getEntity(TOApprovalEntity.class, tOApproval.getId());
            String busTname = tOApproval.getBusinessTablename();
            if (StringUtil.isNotEmpty(busTname)) {
                String[] btNames = busTname.split(",");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (String name : btNames) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    if (name.equals("t_o_approval_project_summary")) {
                        map.put("controller", "tOApprovalProjectSummaryController");//在各自业务controller中写goBusinessForApproval方法展示业务信息
                        map.put("title", "项目汇总信息");
                    }
                    list.add(map);
                }
                req.setAttribute("list", list);
            }
            String operateStatus = req.getParameter("operateStatus");
            String editFlag = req.getParameter("editFlag");
            String ifcirculate = req.getParameter("ifcirculate");
            req.setAttribute("operateStatus", operateStatus);
            req.setAttribute("editFlag", editFlag);
            req.setAttribute("ifcirculate", ifcirculate);
            req.setAttribute("id", tOApproval.getId());//公用参数：呈批件主键
        }
        return new ModelAndView("com/kingtake/office/approval/tOApproval-operateTab");
    }

    /**
     * 呈批件处理页面跳转
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goOperate")
    public ModelAndView goOperate(TOApprovalEntity tOApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = systemService.get(TOApprovalEntity.class, tOApproval.getId());
            req.setAttribute("tOApprovalPage", tOApproval);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOApproval.getContentFileId());
            req.setAttribute("file", file);
        }
        String rid = req.getParameter("rid");
        TOFlowReceiveLogsEntity receiveLog = this.systemService.get(TOFlowReceiveLogsEntity.class, rid);
        if (receiveLog != null && receiveLog.getSignInTime() == null) {
            receiveLog.setSignInTime(new Date());
            receiveLog.setSignInFlag("1");
            this.systemService.updateEntitie(receiveLog);
        }
        return new ModelAndView("com/kingtake/office/approval/tOApproval-operate");
    }

    /**
     * 多呈批件查看tab页
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goDetailTab")
    public ModelAndView goDetailTab(TOApprovalEntity tOApproval, HttpServletRequest req) {
        String regId = req.getParameter("regId");
        CriteriaQuery cq = new CriteriaQuery(TOApprovalEntity.class);
        cq.eq("regId", regId);
        cq.add();
        List<TOApprovalEntity> list = systemService.getListByCriteriaQuery(cq, false);
        req.setAttribute("list", list);
        return new ModelAndView("com/kingtake/office/approval/tOApproval-detailTab");
    }

    /**
     * 查看页面跳转
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(TOApprovalEntity tOApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = systemService.get(TOApprovalEntity.class, tOApproval.getId());
            req.setAttribute("tOApprovalPage", tOApproval);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOApproval.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/approval/tOApproval-detail");
    }

    /**
     * 呈批件完成页面跳转(选择归档人)
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goFinish")
    public ModelAndView goFinish(HttpServletRequest req) {
        String id = req.getParameter("id");
        String rid = req.getParameter("rid");
        req.setAttribute("id", id);
        req.setAttribute("rid", rid);
        return new ModelAndView("com/kingtake/office/approval/finishOrg");
    }

    /**
     * 呈批件完成操作
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "doFinish")
    @ResponseBody
    public AjaxJson doFinish(TOApprovalEntity tOApproval, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String receiverid = req.getParameter("receiverid");
        String realName = req.getParameter("realName");
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            String rid = req.getParameter("rid");
            tOApproval = systemService.get(TOApprovalEntity.class, tOApproval.getId());
            tOApproval.setArchiveFlag(ReceiveBillConstant.BILL_COMPLETE);
            systemService.updateEntitie(tOApproval);
            TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, tOApproval.getRegId());
            reg.setGenerationFlag(ReceiveBillConstant.BILL_COMPLETE);
            reg.setArchiveUserid(receiverid);
            reg.setArchiveUsername(realName);
            systemService.updateEntitie(reg);
            TOFlowReceiveLogsEntity receive = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receive.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
            receive.setOperateTime(new Date());
            receive.setSuggestionCode("1");
            systemService.updateEntitie(receive);
            tOMessageService.sendMessage(receiverid, "公文[" + tOApproval.getTitle() + "]完成提醒！", "呈批件",
                    "公文[" + tOApproval.getTitle() + "]已完成，请到协同办公->发文登记中进行归档操作！", tOApproval.getContactId());
        }
        j.setMsg("操作成功！");

        return j;
    }

    /**
     * 呈批件归档操作
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "doArchive")
    @ResponseBody
    public AjaxJson doArchive(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String regId = req.getParameter("regId");
        String fileNumPrefix = req.getParameter("fileNumPrefix");
        String sendYear = req.getParameter("sendYear");
        String sendNum = req.getParameter("sendNum");
        TSUser user = ResourceUtil.getSessionUserName();
        if (StringUtil.isNotEmpty(regId)) {
            TOSendReceiveRegEntity tOSendReceiveReg = systemService.get(TOSendReceiveRegEntity.class, regId);
            CriteriaQuery cq = new CriteriaQuery(TOApprovalEntity.class);
            cq.eq("regId", regId);
            cq.eq("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
            cq.add();
            List<TOApprovalEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TOApprovalEntity tOApproval = list.get(0);
                tOApproval.setArchiveFlag(ReceiveBillConstant.BILL_ARCHIVED);
                tOApproval.setArchiveDate(new Date());
                tOApproval.setArchiveUserid(user.getId());
                tOApproval.setArchiveUsername(user.getRealName());
                if (StringUtils.isNotEmpty(sendNum)) {
                    tOApproval.setApplicationFileno(sendNum);
                    tOSendReceiveReg.setFileNum(sendNum);
                }
                if (StringUtils.isNotEmpty(sendYear)) {
                    tOApproval.setApprovalYear(sendYear);
                    tOSendReceiveReg.setFileNumYear(sendYear);
                }
                if (StringUtils.isNotEmpty(fileNumPrefix)) {
                    tOApproval.setFileNumPrefix(fileNumPrefix);
                    tOSendReceiveReg.setFileNumPrefix(fileNumPrefix);
                }
                systemService.updateEntitie(tOApproval);
                String merge = (StringUtils.isEmpty(tOSendReceiveReg.getFileNumPrefix()) ? "" : tOSendReceiveReg
                        .getFileNumPrefix())
                        + (StringUtils.isEmpty(tOSendReceiveReg.getFileNumYear()) ? "" : ("〔20"
                                + tOSendReceiveReg.getFileNumYear() + "〕")) + tOSendReceiveReg.getFileNum();
                tOSendReceiveReg.setMergeFileNum(merge);
                tOSendReceiveReg.setGenerationFlag(ReceiveBillConstant.BILL_ARCHIVED);
                tOSendReceiveReg.setArchiveUserid(user.getId());
                tOSendReceiveReg.setArchiveDate(new Date());
                tOSendReceiveReg.setArchiveUsername(user.getRealName());
                systemService.updateEntitie(tOSendReceiveReg);
            }

        }
        j.setMsg("归档成功！");
        return j;
    }

    /**
     * 查看是否有附件
     * 
     * @param id
     * @param req
     * @return
     */
    @RequestMapping(params = "ifaccessory")
    @ResponseBody
    public AjaxJson ifaccessory(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
        cq.eq("bid", id);
        cq.add();
        List<TSFilesEntity> flist = systemService.getListByCriteriaQuery(cq, false);
        if (flist.size() > 0) {
            j.setObj(SrmipConstants.YES);
        } else {
            j.setObj(SrmipConstants.NO);
        }
        return j;
    }

    /**
     * 
     * @param rid
     *            呈批件id
     * @param uid
     *            用户id
     * @return false-未签收 true-已签收
     */
    public Boolean ifSignIn(String rid, String uid) {
        CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        cq.eq("sendReceiveId", rid);
        cq.eq("receiveUserid", uid);
        cq.notEq("signInFlag", SrmipConstants.YES);
        cq.add();
        int count = systemService.getCountByCriteriaQuery(cq);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查看是否签收
     * 
     * @param id
     * @param req
     * @return
     */
    @RequestMapping(params = "ifSignIn")
    @ResponseBody
    public AjaxJson ifSignIn(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String signInFlag = "1";
        TSUser user = ResourceUtil.getSessionUserName();
        if (!ifSignIn(id, user.getId())) {
            signInFlag = "0";
        }
        j.setObj(signInFlag);
        return j;
    }

    /**
     * 根据不同处理角色展现不同内容
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "getViewByRole")
    @ResponseBody
    public AjaxJson getViewByRole(TOApprovalEntity tOApproval, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String editFlag = "0";//仅查看 editFlag标识意义：0-仅查看，1-作为接收人，2-作为创建人，3-作为被传阅人
        String ifcirculate = SrmipConstants.NO;
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            TSUser user = ResourceUtil.getSessionUserName();
            tOApproval = tOApprovalService.getEntity(TOApprovalEntity.class, tOApproval.getId());
            if (!tOApproval.getArchiveFlag().equals(ReceiveBillConstant.BILL_COMPLETE)
                    && user.getUserName().equals(tOApproval.getCreateBy())) {//未归档且当前登陆人为创建人
                editFlag = "2";//
                CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                rcq.eq("sendReceiveId", tOApproval.getId());
                rcq.eq("receiveUserid", user.getId());
                rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
                rcq.add();
                List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
                if (rlist.size() > 0) {
                    if (rlist.get(0).getOperateStatus().equals(ReceiveBillConstant.OPERATE_UNTREATED)) {//既为创建人又为接收人且处理状态为未处理时显示填写按钮
                        editFlag = "1";//
                    }
                }
            } else {//当前登陆人不为创建人
                if (!tOApproval.getArchiveFlag().equals(ReceiveBillConstant.BILL_COMPLETE)) {//未处理且该信件未归档时显示填写按钮
                    if (req.getParameter("operateStatus").equals(ReceiveBillConstant.OPERATE_UNTREATED)) {
                        editFlag = "1";
                        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                        rcq.eq("sendReceiveId", tOApproval.getId());
                        rcq.eq("receiveUserid", user.getId());
                        rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
                        rcq.eq("ifcirculate", SrmipConstants.YES);//接收人为被传阅人
                        rcq.add();
                        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
                        if (rlist.size() > 0) {
                            if (rlist.get(0).getOperateStatus().equals(ReceiveBillConstant.OPERATE_UNTREATED)) {//既为创建人又为接收人且处理状态为未处理时显示填写按钮
                                ifcirculate = SrmipConstants.YES;//被传阅人对于审批意见不可越级查看
                                editFlag = "3";
                            }
                        }
                    } else {
                        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                        rcq.eq("sendReceiveId", tOApproval.getId());
                        rcq.eq("receiveUserid", user.getId());
                        rcq.eq("ifcirculate", SrmipConstants.YES);//接收人为被传阅人
                        rcq.add();
                        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
                        if (rlist.size() > 0) {
                            ifcirculate = SrmipConstants.YES;//被传阅人对于审批意见不可越级查看
                            //                                editFlag = "3";
                        }
                    }
                }
            }

        }
        req.setAttribute("ifcirculate", ifcirculate);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ifcirculate", ifcirculate);
        map.put("editFlag", editFlag);
        j.setAttributes(map);
        j.setSuccess(true);
        return j;
    }

    /**
     * 呈批件处理意见输入页面跳转
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goInput")
    public ModelAndView goInput(TOApprovalEntity tOApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = tOApprovalService.getEntity(TOApprovalEntity.class, tOApproval.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            rcq.eq("sendReceiveId", tOApproval.getId());
            rcq.eq("receiveUserid", user.getId());
            rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
            rcq.eq("validFlag", SrmipConstants.YES);
            rcq.add();
            List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            if (rlist.size() > 0) {
                String suggestionType = "";
                if (rlist.get(0).getSuggestionType().equals(ApprovalConstant.OFFICE_REVIEW)) {
                    suggestionType = SendBillConstant.REVIEW_TRANSLATE.get(ApprovalConstant.OFFICE_REVIEW);
                } else if (rlist.get(0).getSuggestionType().equals(ApprovalConstant.LEADER_REVIEW)) {
                    suggestionType = SendBillConstant.REVIEW_TRANSLATE.get(ApprovalConstant.LEADER_REVIEW);
                }
                req.setAttribute("receive", rlist.get(0));
                req.setAttribute("suggestionType", suggestionType);
            }
        }
        return new ModelAndView("com/kingtake/office/approval/inputSuggestion");
    }

    /**
     * 呈批件意见填写操作
     * 
     * @param rid
     * @param req
     * @return
     */
    @RequestMapping(params = "doSuggestion")
    @ResponseBody
    public AjaxJson doSuggestion(String rid, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "意见填写成功！";
        if (StringUtil.isNotEmpty(rid)) {
            try {
                TOFlowReceiveLogsEntity receive = systemService.get(TOFlowReceiveLogsEntity.class, rid);
                receive.setSuggestionContent(req.getParameter("suggestionContent"));
                receive.setSuggestionCode(req.getParameter("suggestionCode"));
                TOFlowSendLogsEntity send = systemService.get(TOFlowSendLogsEntity.class, receive.getToFlowSendId());
                TOApprovalEntity rb = systemService.get(TOApprovalEntity.class, receive.getSendReceiveId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (req.getParameter("suggestionCode").equals(ReceiveBillConstant.AUDIT_REBUT)) {
                    //若有驳回，则将该呈批件所有审批信息置为无效.
                    TOApprovalEntity bill = systemService.get(TOApprovalEntity.class, receive.getSendReceiveId());
                    bill.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);
                    TSUser user = ResourceUtil.getSessionUserName();
                    bill.setBackUserid(user.getId());
                    bill.setBackUsername(user.getRealName());
                    systemService.updateEntitie(bill);
                    CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                    cq.eq("sendReceiveId", receive.getSendReceiveId());
                    cq.add();
                    List<TOFlowReceiveLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
                    if (list.size() > 0) {
                        for (TOFlowReceiveLogsEntity fr : list) {
                            fr.setValidFlag(SrmipConstants.NO);
                            systemService.updateEntitie(fr);
                            tOMessageService.sendMessage(
                                    fr.getReceiveUserid(),
                                    "呈批件驳回提醒",
                                    "呈批件",
                                    "呈批件[" + rb.getTitle() + "]被驳回\n驳回人：" + receive.getReceiveUsername() + "\n驳回意见："
                                            + receive.getSuggestionContent() + "\n驳回时间："
                                            + sdf.format(receive.getOperateTime()), send.getOperateUserid());
                        }
                    }
                }
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
                receive.setOperateTime(new Date());
                systemService.updateEntitie(receive);
                tOMessageService.sendMessage(
                        send.getOperateUserid(),
 "呈批件处理提醒", "呈批件",
                        "呈批件[" + rb.getTitle() + "]已处理\n处理人：" + receive.getReceiveUsername() + "\n处理意见："
                                + receive.getSuggestionContent() + "\n处理时间：" + sdf.format(receive.getOperateTime()),
                        receive.getReceiveUserid());
            } catch (Exception e) {
                message = "意见填写失败！";
                e.printStackTrace();
            }
        } else {
            message = "意见填写失败！";
        }
        return j;
    }

    /**
     * 归档操作
     * 
     * @param tOApproval
     * @param request
     * @return
     */
    @RequestMapping(params = "archive")
    @ResponseBody
    public AjaxJson archive(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "归档成功！";
        try {
            tOApproval = systemService.getEntity(TOApprovalEntity.class, tOApproval.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            tOApproval.setArchiveFlag(ReceiveBillConstant.BILL_COMPLETE);
            tOApproval.setArchiveUserid(user.getId());
            tOApproval.setArchiveUsername(user.getRealName());
            tOApproval.setArchiveDate(new Date());
            tOApprovalService.save(tOApproval);
            CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            cq.eq("sendReceiveId", tOApproval.getId());
            cq.eq("operateStatus", SrmipConstants.NO);
            cq.add();
            List<TOFlowReceiveLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
            for (TOFlowReceiveLogsEntity re : list) {//归档时将未处理接收记录改为无效
                re.setValidFlag(SrmipConstants.NO);
                systemService.updateEntitie(re);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = "归档失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 呈批件信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOApprovalEntity tOApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = tOApprovalService.getEntity(TOApprovalEntity.class, tOApproval.getId());
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            rcq.eq("sendReceiveId", tOApproval.getId());
            rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
            rcq.eq("validFlag", SrmipConstants.YES);
            //读取配置文件中的排序
            PropertiesUtil prop = new PropertiesUtil("sendReceiveBill.properties");
            String orderBy = prop.readProperty("approval.receive.orderBy");
            String[] orderBys = orderBy.split(",");
            String fieldName = "";
            SortDirection direction = null;
            for (int i = 0; i < orderBys.length; i++) {
                fieldName = orderBys[i].split("\\.")[0];
                if (orderBys[i].split("\\.")[1].equals("desc")) {
                    direction = SortDirection.desc;
                } else {
                    direction = SortDirection.asc;
                }
                rcq.addOrder(fieldName, direction);
            }
            rcq.add();
            List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            List<TOFlowReceiveLogsEntity> llist = new ArrayList<TOFlowReceiveLogsEntity>();
            List<TOFlowReceiveLogsEntity> olist = new ArrayList<TOFlowReceiveLogsEntity>();
            //            if (rlist.size() > 0) {
            //                for (TOFlowReceiveLogsEntity receive : rlist) {
            //                    if (receive.getSuggestionType().equals(ReceiveBillConstant.LEADER_REVIEW)) {
            //                        llist.add(receive);
            //                    } else if (receive.getSuggestionType().equals(ReceiveBillConstant.OFFICE_REVIEW)) {
            //                        olist.add(receive);
            //                    }
            //                }
            //                TOReceiveList slist = new TOReceiveList();
            //                slist.setLlist(llist);
            //                slist.setOlist(olist);
            //                req.setAttribute("hh", "\n");
            //                req.setAttribute("slist", slist);
            //            }
            req.setAttribute("tOApprovalPage", tOApproval);
        }
        return new ModelAndView("com/kingtake/office/approval/tOApproval-update");
    }

    /**
     * 呈送上级页面跳转
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPresent")
    public ModelAndView goPresent(HttpServletRequest req) {
        //        String suggestionType = req.getParameter("suggestionType");
        String ifcirculate = req.getParameter("ifcirculate");
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("ifcirculate", ifcirculate);
        //        req.setAttribute("suggestionType", suggestionType);
        return new ModelAndView("com/kingtake/office/approval/presentOrg");
    }

    /**
     * 传阅页面跳转
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goCirculate")
    public ModelAndView goCirculate(HttpServletRequest req) {
        String suggestionType = req.getParameter("suggestionType");
        String ifcirculate = req.getParameter("ifcirculate");
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("ifcirculate", ifcirculate);
        req.setAttribute("suggestionType", suggestionType);
        return new ModelAndView("com/kingtake/office/approval/circulateOrg");
    }

    @RequestMapping(params = "doNothing")
    @ResponseBody
    public AjaxJson doNothing(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        return j;
    }

    /**
     * 处理操作
     * 
     * @param tOApproval
     * @param request
     * @return
     */
    @RequestMapping(params = "doOperate")
    @ResponseBody
    public AjaxJson doOperate(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String suggestionFlag = request.getParameter("suggestionFlag");
        String suggestionType = "";
        String suggestionContent = "";
        if (StringUtil.isNotEmpty(tOApproval.getOfficeReview())) {
            suggestionType = ReceiveBillConstant.OFFICE_REVIEW;
            suggestionContent = tOApproval.getOfficeReview();
        } else if (StringUtil.isNotEmpty(tOApproval.getLeaderReview())) {
            suggestionType = ReceiveBillConstant.LEADER_REVIEW;
            suggestionContent = tOApproval.getLeaderReview();
        }
        message = "呈批件处理成功";
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOApproval.getId());
        rcq.eq("receiveUserid", user.getId());
        rcq.eq("operateStatus", SrmipConstants.NO);
        rcq.eq("validFlag", SrmipConstants.YES);
        rcq.add();
        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        TOApprovalEntity t = tOApprovalService.get(TOApprovalEntity.class, tOApproval.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOApproval, t);
            if (SrmipConstants.NO.equals(suggestionFlag)) {
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);
                tOApprovalService.updateEntitie(t);
            } else {
            }
            if (rlist.size() > 0) {
                TOFlowReceiveLogsEntity receive = rlist.get(0);
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
                receive.setOperateTime(new Date());
                receive.setSuggestionContent(suggestionContent);
                receive.setSuggestionType(suggestionType);
                receive.setSuggestionCode(suggestionFlag);
                systemService.updateEntitie(receive);
                String senderId = systemService.get(TOFlowSendLogsEntity.class, receive.getToFlowSendId())
                        .getOperateUserid();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                CriteriaQuery ucq = new CriteriaQuery(TSUser.class);
                ucq.eq("userName", tOApproval.getCreateBy());
                ucq.add();
                TSUser createUser = (TSUser) systemService.getListByCriteriaQuery(ucq, false).get(0);
                tOMessageService.sendMessage(senderId + "," + createUser.getId(), "呈批件处理提醒", "呈批件",
                        "呈批件[" + t.getTitle() + "]已处理\n处理人:" + receive.getReceiveUsername() + "\n处理意见:"
                                + suggestionContent + "\n处理时间:" + sdf.format(receive.getOperateTime()),
                        receive.getReceiveUserid());
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "呈批件处理失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/approval/tOApprovalUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOApprovalEntity tOApproval, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOApprovalEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOApproval, request.getParameterMap());

        TSUser user = ResourceUtil.getSessionUserName();
        cq.eq("createBy", user.getUserName());

        String operateStatus = request.getParameter("operateStatus");
        // 未处理
        if (SrmipConstants.NO.equals(operateStatus)) {
            cq.notEq("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
        } else {
            cq.eq("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
        }
        cq.add();

        List<TOApprovalEntity> tOApprovals = this.tOApprovalService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "呈批件信息表");
        modelMap.put(NormalExcelConstants.CLASS, TOApprovalEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("呈批件信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tOApprovals);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOApprovalEntity tOApproval, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "呈批件信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOApprovalEntity.class);
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
                List<TOApprovalEntity> listTOApprovalEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TOApprovalEntity.class, params);
                for (TOApprovalEntity tOApproval : listTOApprovalEntitys) {
                    tOApprovalService.save(tOApproval);
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
     * 获取流转步骤列表
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "getStepList")
    public ModelAndView getStepList(TOApprovalEntity tOApproval, HttpServletRequest req) {
        tOApproval = tOApprovalService.getEntity(TOApprovalEntity.class, tOApproval.getId());
        CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
        scq.eq("sendReceiveId", tOApproval.getId());
        scq.addOrder("operateDate", SortDirection.asc);
        scq.add();
        List<TOFlowSendLogsEntity> slist = systemService.getListByCriteriaQuery(scq, false);
        
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOApproval.getId());
        rcq.addOrder("operateTime", SortDirection.asc);
        rcq.add();
        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        TOFlowSendLogsEntity sendLog = slist.get(0);
        TOFlowReceiveLogsEntity recLog = null;
        List<Map<String, Object>> stepList = new ArrayList<Map<String, Object>>();
        while(sendLog!=null) {
            Map<String, Object> flowMap = new HashMap<String, Object>();
            flowMap.put("operateUserName", sendLog.getOperateUsername());
            flowMap.put("operateTime", sendLog.getOperateDate());
            flowMap.put("operateTip", sendLog.getSenderTip());
            recLog = getRecLogFromSend(sendLog,rlist);
            flowMap.put("receiveTime", recLog.getSignInTime());
            stepList.add(flowMap);
            sendLog = getSendLogFromRec(recLog, slist); 
        }
        Map<String, Object> flowMap = new HashMap<String, Object>();
        flowMap.put("operateUserName", recLog.getReceiveUsername());
        flowMap.put("operateTime", recLog.getOperateTime());
        String operate = "";
        if("1".equals(recLog.getOperateStatus())){
            if("1".equals(recLog.getSuggestionCode())){
                operate = "通过";
            }else{
                operate = "驳回";
            }
        }
        flowMap.put("operateTip", operate);
        flowMap.put("receiveTime", recLog.getSignInTime());
        stepList.add(flowMap);
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveStepBillList", "stepList", stepList);
    }
    
  //通过发送记录找接受记录
    private TOFlowReceiveLogsEntity getRecLogFromSend(TOFlowSendLogsEntity sendLog,List<TOFlowReceiveLogsEntity> rlist){
        TOFlowReceiveLogsEntity recLog = null;
        for(TOFlowReceiveLogsEntity tmp:rlist){
            if(sendLog.getId().equals(tmp.getToFlowSendId())){
                recLog = tmp;
                break;
            }
        }
        return recLog;
    }
    
    //通过接受记录找发送记录
    private TOFlowSendLogsEntity getSendLogFromRec(TOFlowReceiveLogsEntity recLog,List<TOFlowSendLogsEntity> slist){
        TOFlowSendLogsEntity sendLog = null;
        for(TOFlowSendLogsEntity tmp:slist){
            if(recLog.getId().equals(tmp.getFlowReceiveId())){
                sendLog = tmp;
                break;
            }
        }
        return sendLog;
    }

    /**
     * 套红套打
     * 
     * @param tOApproval
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "exportDocByT")
    public String exportDocByT(TOApprovalEntity tOApproval, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = systemService.get(TOApprovalEntity.class, tOApproval.getId());
        }
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOApproval.getId());
        rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
        rcq.eq("validFlag", SrmipConstants.YES);
        //读取配置文件中的排序
        PropertiesUtil prop = new PropertiesUtil("sendReceiveBill.properties");
        String orderBy = prop.readProperty("approval.receive.orderBy");
        String[] orderBys = orderBy.split(",");
        String fieldName = "";
        SortDirection direction = null;
        for (int i = 0; i < orderBys.length; i++) {
            fieldName = orderBys[i].split("\\.")[0];
            if (orderBys[i].split("\\.")[1].equals("desc")) {
                direction = SortDirection.desc;
            } else {
                direction = SortDirection.asc;
            }
            rcq.addOrder(fieldName, direction);
        }

        rcq.add();
        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        List<TOFlowReceiveLogsEntity> llist = new ArrayList<TOFlowReceiveLogsEntity>();
        List<TOFlowReceiveLogsEntity> olist = new ArrayList<TOFlowReceiveLogsEntity>();
        //        List<TOFlowReceiveLogsEntity> dlist = new ArrayList<TOFlowReceiveLogsEntity>();
        if (rlist.size() > 0) {
            for (TOFlowReceiveLogsEntity receive : rlist) {
                if (receive.getSuggestionType().equals(SendBillConstant.LEADER_REVIEW)) {
                    llist.add(receive);
                } else if (receive.getSuggestionType().equals(SendBillConstant.OFFICE_REVIEW)) {
                    olist.add(receive);
                } else {
                    //                    dlist.add(receive);
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (llist.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (TOFlowReceiveLogsEntity r : llist) {
                sb.append(r.getReceiveUsername() + ":" + r.getSuggestionContent());
                sb.append("\n");
            }
            map.put("xszps", sb.toString());
        }
        if (olist.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (TOFlowReceiveLogsEntity r : olist) {
                sb.append(r.getReceiveUsername() + ":" + r.getSuggestionContent());
                sb.append("\n");
            }
            map.put("bldps", sb.toString());
        }
        if (StringUtil.isNotEmpty(ApprovalConstant.GRADE_TRANSLATE.get(tOApproval.getSecrityGrade()))) {
            map.put("mj", ApprovalConstant.GRADE_TRANSLATE.get(tOApproval.getSecrityGrade()));
        }
        if (StringUtil.isNotEmpty(tOApproval.getApprovalYear())) {
            map.put("nd", tOApproval.getApprovalYear());
        }
        if (StringUtil.isNotEmpty(tOApproval.getApplicationFileno())) {
            map.put("wh", tOApproval.getApplicationFileno());
        }
        if (StringUtil.isNotEmpty(tOApproval.getUndertakeUnitName())) {
            map.put("cbdw", tOApproval.getUndertakeUnitName());
        }
        if (StringUtil.isNotEmpty(tOApproval.getContactName())) {
            map.put("lxr", tOApproval.getContactName());
        }
        if (StringUtil.isNotEmpty(tOApproval.getContactPhone())) {
            map.put("dh", tOApproval.getContactPhone());
        }
        if (StringUtil.isNotEmpty(tOApproval.getTitle())) {
            map.put("bt", tOApproval.getTitle());
        }
        if (StringUtil.isNotEmpty(tOApproval.getReceiveUnitname())) {
            map.put("jsdw", tOApproval.getReceiveUnitname());
        }

        if (StringUtil.isNotEmpty(tOApproval.getApplicationContent())) {
            map.put("nr", tOApproval.getApplicationContent());
        }
        if (StringUtil.isNotEmpty(tOApproval.getSignUnitname())) {
            map.put("lkdw", tOApproval.getSignUnitname());
        }

        if (StringUtil.isNotEmpty(tOApproval.getCreateDate())) {
            Calendar time = Calendar.getInstance();
            time.clear();
            time.setTime(tOApproval.getCreateDate());
            map.put("n", time.get(Calendar.YEAR));
            map.put("y", time.get(Calendar.MONTH) + 1);
            map.put("r", time.get(Calendar.DAY_OF_MONTH));
        }
        modelMap.put(TemplateWordConstants.FILE_NAME, "呈批件(" + tOApproval.getTitle() + ")");
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        if (request.getParameter("flag").equals("1")) {//标志位为1时使用套打模板
            modelMap.put(TemplateWordConstants.URL, "export/template/cpj1.docx");
        } else {
            modelMap.put(TemplateWordConstants.URL, "export/template/cpj.docx");
        }
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;

    }

    @RequestMapping(params = "goTDOfficePage")
    public ModelAndView goTDOfficePage(HttpServletRequest req, TOApprovalEntity tOApprovalBill) {
        if (StringUtil.isNotEmpty(tOApprovalBill.getId())) {
            tOApprovalBill = systemService.getEntity(TOApprovalEntity.class, tOApprovalBill.getId());
            req.setAttribute("tOApprovalPage", tOApprovalBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOApprovalBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/approval/TDOfficePage");
    }

    /**
     * 查看页面跳转
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goSendReceiveReg")
    public ModelAndView goSendReceiveReg(TOApprovalEntity tOApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = systemService.get(TOApprovalEntity.class, tOApproval.getId());
            String regId = tOApproval.getRegId();
            TOSendReceiveRegEntity sendReceiveReg = this.systemService.get(TOSendReceiveRegEntity.class, regId);
            req.setAttribute("tOSendReceiveRegPage", sendReceiveReg);
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-update");
    }

    /**
     * 查看页面跳转
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goSelectSign")
    public ModelAndView goSelectSign(TOApprovalEntity tOApproval, HttpServletRequest req) {
        String sql = "select t.id,t.name,t.username,t.password,t.filename,t.esppath,t.createdate from t_o_sign t order by t.createdate desc";
        List<Map<String, Object>> mapList = this.systemService.findForJdbc(sql);
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/";
        for (Map<String, Object> map : mapList) {
            String esppath = (String) map.get("esppath");
            map.put("esppath", url + esppath);
        }
        req.setAttribute("signList", mapList);
        return new ModelAndView("com/kingtake/office/approval/selectSign");
    }

    /**
     * 呈批件通过或返回修改
     * 
     * @param tOApproval
     * @param request
     * @return
     */
    @RequestMapping(params = "passReturn")
    @ResponseBody
    public AjaxJson passReturn(TOApprovalEntity tOApproval, HttpServletRequest request) {
        TOApprovalEntity t = tOApprovalService.get(TOApprovalEntity.class, tOApproval.getId());
        AjaxJson j = new AjaxJson();
        String rid = request.getParameter("rid");
        String opinion = request.getParameter("opinion");
        String receiverid = t.getContactId();
        String leaderReview = request.getParameter("leaderReview");
        String departId = t.getUndertakeUnitId();
        String departName = t.getUndertakeUnitName();
        if (StringUtils.isNotEmpty(t.getContactId()) && t.getContactId().contains(",")) {
            receiverid = t.getContactId().split(",")[0];
            departId = t.getUndertakeUnitId().split(",")[0];
            departName = t.getUndertakeUnitName().split(",")[0];
        }
        message = "呈批件发送成功！";
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOApproval, t);
            TSUser user = ResourceUtil.getSessionUserName();
            if (ReceiveBillConstant.AUDIT_PASS.equals(opinion)) {//通过
                //            tOReceiveBill = systemService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
                TOFlowSendLogsEntity send = new TOFlowSendLogsEntity();
                send.setOperateUserid(user.getId());
                send.setOperateUsername(user.getRealName());
                send.setOperateDate(new Date());
                send.setFlowReceiveId(rid);
                String ip = "";
                if (request.getHeader("x-forwarded-for") == null) {
                    ip = request.getRemoteAddr();
                } else {
                    ip = request.getHeader("x-forwarded-for");
                }
                send.setSendReceiveId(tOApproval.getId());
                send.setOperateIp(ip);
                send.setOperateDepartid(user.getCurrentDepart().getId());
                send.setOperateDepartname(user.getCurrentDepart().getDepartname());
                send.setSenderTip("通过，" + leaderReview);
                systemService.save(send);
                TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
                receive.setToFlowSendId(send.getId());
                receive.setSendReceiveId(tOApproval.getId());
                TSUser receiver = systemService.get(TSUser.class, receiverid);
                receive.setReceiveUserid(receiver.getId());
                receive.setReceiveDepartid(departId);
                receive.setValidFlag(SrmipConstants.YES);//默认有效
                //            receive.setSignInFlag(SrmipConstants.NO);//签收状态默认为未签收
                receive.setReceiveDepartname(departName);
                receive.setReceiveUsername(receiver.getRealName());
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
                systemService.save(receive);
                //                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tOMessageService.sendMessage(receiverid, "新的呈批件消息", "呈批件", "您有新的呈批件需处理\n呈批件标题[" + t.getTitle()
                        + "]\n请到协同办公->发文管理中办理", send.getOperateUserid());
            } else {//驳回
                t.setBackUserid(user.getId());
                t.setBackUsername(user.getRealName());
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);//公文状态置为被驳回
                systemService.updateEntitie(t);
                tOMessageService.sendMessage(t.getContactId(), "呈批件[" + t.getTitle() + "]被驳回！", "呈批件",
                        "驳回人：" + user.getRealName() + "\n意见说明：" + leaderReview, user.getId());
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, t.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_REBUT);
                systemService.updateEntitie(reg);
            }

            //发送给下一步处理人即为办理完成
            TOFlowReceiveLogsEntity receiveLog = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receiveLog.setOperateStatus(SrmipConstants.YES);
            receiveLog.setOperateTime(new Date());
            receiveLog.setSuggestionCode(opinion);
            systemService.updateEntitie(receiveLog);
        } catch (Exception e) {
            e.printStackTrace();
            message = "呈批件发送失败！";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
