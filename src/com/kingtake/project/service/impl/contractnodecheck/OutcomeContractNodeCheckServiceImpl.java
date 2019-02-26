package com.kingtake.project.service.impl.contractnodecheck;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.contractnodecheck.TPmOutContractNodeCheckEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.contractnodecheck.TPmContractNodeCheckServiceI;
import com.kingtake.project.service.contractnodecheck.TPmOutContractNodeCheckServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("outcomeContractNodeCheckService")
@Transactional
public class OutcomeContractNodeCheckServiceImpl extends CommonServiceImpl implements TPmOutContractNodeCheckServiceI,ProjectListServiceI,ApprFlowServiceI {

    @Autowired
    private TPmContractNodeCheckServiceI tPmContractNodeCheckService;
    
    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
        TSUser user = ResourceUtil.getSessionUserName();
        String temp = "FROM T_PM_CONTRACT_NODE NODE, T_PM_OUTCONTRACT_NODE_CHECK APPR, t_pm_outcome_contract_appr CONTRACT, "
                + "T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE NODE.ID = APPR.CONTRACT_NODE_ID AND NODE.IN_OUT_CONTRACTID = CONTRACT.ID "
                + "AND APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                + "AND RECEIVE.RECEIVE_USERID = ? ";
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
        temp += " ORDER BY SEND.OPERATE_DATE, RECEIVE.OPERATE_TIME";
        //暂时只根据登录名找
        String[] params = new String[] { user.getId()};
        Long count = this.commonDao.getCountForJdbcParam(countSql.append(temp).toString(), params);
        return count.intValue();
    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmOutContractNodeCheckEntity t = commonDao.get(TPmOutContractNodeCheckEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setAuditFlag(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
                
              //当前登录人
//                TPmApprSendLogsEntity sendLog = new TPmApprSendLogsEntity();
//                sendLog.setApprId(apprId);
//                sendLog.setOperateDate(new Date());
//                sendLog.setOperateUserid(user.getId());
//                sendLog.setOperateUsername(user.getRealName());
//                sendLog.setOperateDepartid(user.getCurrentDepart().getId());
//                sendLog.setOperateDepartname(user.getCurrentDepart().getDepartname());
//                sendLog.setSuggestionType(suggestionType);
//                String operateIp = IpUtil.getIpAddr(request);
//                sendLog.setOperateIp(operateIp);
//                sendLog.setSenderTip(senderTip);
//                sendLog.setSourceId(recId);
//                //保存发送信息
//                this.commonDao.save(sendLog);
//                ApprFlowServiceI apprService = apprFlowServiceContext.getApprService(apprType);
//                apprService.doPass(apprId);
//                String apprName = apprService.getAppName(apprId);
//                //拼接系统消息
//                StringBuffer msg = new StringBuffer();
//                String apprTypeString = ApprovalConstant.APPR_TYPE_NAME.get(apprType);
//                if (StringUtils.isNotEmpty(apprName)) {
//                    msg.append("您有新的" + apprTypeString + "审批需处理");
//                } else {
//                    msg.append("您有新的" + apprTypeString + "[" + apprName + "]审批需处理");
//                }
//                if (StringUtil.isNotEmpty(senderTip)) {
//                    msg.append("\n说明：" + senderTip);
//                }
//
//                //保存接收人信息
//                if (StringUtil.isNotEmpty(receiveUseIds)) {
//                    TPmApprReceiveLogsEntity tPmApprReceiveLog = new TPmApprReceiveLogsEntity();
//                    tPmApprReceiveLog.setApprSendLog(sendLog);
//                    tPmApprReceiveLog.setApprId(apprId);
//                    tPmApprReceiveLog.setOperateStatus(SrmipConstants.NO);
//                    tPmApprReceiveLog.setTableName(sendLog.getTableName());
//                    tPmApprReceiveLog.setReceiveUserid(receiveUseIds);
//                    tPmApprReceiveLog.setReceiveUsername(receiveUseNames);
//                    tPmApprReceiveLog.setSuggestionType(sendLog.getSuggestionType());
//                    tPmApprReceiveLog.setValidFlag(SrmipConstants.YES);
//                    this.save(tPmApprReceiveLog);
//                    String execueteSql = "update t_pm_appr_receive_logs t set t.receive_time=sysdate where t.id='"+tPmApprReceiveLog.getId()+"'";
//                    tOMessageService.sendMessage(receiveUseIds, apprTypeString + "审批", apprTypeString, msg.toString(),
//                            user.getId(), execueteSql);
//                }
//
            }

        }
    }

    @Override
    public void doBack(String id) {
        TPmOutContractNodeCheckEntity t = commonDao.get(TPmOutContractNodeCheckEntity.class, id);
        //将审批状态修改为被驳回
        t.setAuditFlag(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
    }

    @Override
    public void doPass(String id) {
        TPmOutContractNodeCheckEntity entity = this.get(TPmOutContractNodeCheckEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAuditFlag())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAuditFlag())) {
              entity.setAuditFlag(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TPmOutContractNodeCheckEntity t = commonDao.get(TPmOutContractNodeCheckEntity.class, id);
        if(t!=null){
            appName = t.getContractName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmOutContractNodeCheckEntity entity = this.get(TPmOutContractNodeCheckEntity.class, id);
        if (entity != null) {
              entity.setAuditFlag(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

    /**
     * 结束审核
     * @param request
     */
    @Override
    public void finishAppr(HttpServletRequest request) {
        String apprId = request.getParameter("apprId");
        String suggestionCode = request.getParameter("suggestionCode");
        String suggestionContent = request.getParameter("suggestionContent");
        String recId = request.getParameter("recId");
        TPmApprReceiveLogsEntity recLog = this.commonDao.get(TPmApprReceiveLogsEntity.class, recId);
        recLog.setSuggestionCode(suggestionCode);
        recLog.setSuggestionContent(suggestionContent);
        recLog.setOperateStatus(SrmipConstants.YES);
        recLog.setOperateTime(new Date());
        this.commonDao.updateEntitie(recLog);
        tPmApprLogsService.finishApprLogs(apprId);
        this.updateFinishFlag(apprId);
        String receiverId = request.getParameter("receiverId");
        String receiverName = request.getParameter("receiverName");
        TSUser user = ResourceUtil.getSessionUserName();
        TPmOutContractNodeCheckEntity contractNodeCheck = this.commonDao.get(TPmOutContractNodeCheckEntity.class, apprId);
        contractNodeCheck.setJhcshr(receiverName);
        contractNodeCheck.setJhcshrId(receiverId);
        contractNodeCheck.setPayFlag("0");//待审核
        contractNodeCheck.setJhcshfsr(user.getRealName());
        contractNodeCheck.setJhcshfsrId(user.getId());
        contractNodeCheck.setJhcshfssj(new Date());
        this.commonDao.updateEntitie(contractNodeCheck);
    }

    @Override
    public void doPassPay(String id) {
        TPmOutContractNodeCheckEntity contractNodeCheck = this.commonDao.get(TPmOutContractNodeCheckEntity.class, id);
        if(contractNodeCheck!=null){
            contractNodeCheck.setPayFlag("1");//同意付款
            this.commonDao.updateEntitie(contractNodeCheck);
        }
    }

    @Override
    public void doReject(TPmOutContractNodeCheckEntity apply) {
        commonDao.updateEntitie(apply);
    }
}