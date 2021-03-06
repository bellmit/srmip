package com.kingtake.project.service.impl.apprlog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.seal.TOSealUseEntity;
import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApprovalEntity;
import com.kingtake.project.entity.apprcontractcheck.TPmContractCheckEntity;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractnodecheck.TPmContractNodeCheckEntity;
import com.kingtake.project.entity.finish.TPmFinishApplyEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.payapply.TPmPayApplyEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDraftEntity;
import com.kingtake.project.entity.resultreward.TBResultRewardEntity;
import com.kingtake.project.entity.task.TPmTaskEntity;
import com.kingtake.project.entity.task.TPmTaskNodeEntity;
import com.kingtake.project.service.appr.ApprFlowServiceContext;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;

@Service("tPmApprLogsService")
@Transactional
public class TPmApprLogsServiceImpl extends CommonServiceImpl implements TPmApprLogsServiceI {

    @Autowired
    private TOMessageServiceI tOMessageService;
    
    @Autowired
    private ApprFlowServiceContext apprFlowServiceContext;

    public Map<String, Object> doAddLogs(TPmApprSendLogsEntity tPmApprSendLogs, String receiveUseIds,
            String receiveUseNames, String receiveUseDepartIds, String receiveUseDepartNames, String rebutFlag) {
        Map<String, Object> map = new HashMap<String, Object>();

        //???????????????
        TSUser user = ResourceUtil.getSessionUserName();
        tPmApprSendLogs.setOperateDate(new Date());
        tPmApprSendLogs.setOperateUserid(user.getId());
        tPmApprSendLogs.setOperateUsername(user.getRealName());
        tPmApprSendLogs.setOperateDepartid(user.getCurrentDepart().getId());
        tPmApprSendLogs.setOperateDepartname(user.getCurrentDepart().getDepartname());
        //??????????????????
        this.save(tPmApprSendLogs);
        map.put("tPmApprSendLogs", tPmApprSendLogs);

        String apprId = tPmApprSendLogs.getApprId();
        String apprType = tPmApprSendLogs.getSuggestionType().substring(0, 2);

        String apprName = "";//????????????????????????
        boolean changeFlag = false;
        if (StringUtil.isNotEmpty(apprId)) {
            //?????????????????????????????????
            if (ApprovalConstant.APPR_TYPE_CHECK.equals(apprType)) {
                TPmContractCheckEntity tPmContractCheck = this.get(TPmContractCheckEntity.class, apprId);
                if (tPmContractCheck != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(tPmContractCheck.getOperationStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(tPmContractCheck.getOperationStatus())) {
                        tPmContractCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(tPmContractCheck);
                        changeFlag = true;
                    }
                    apprName = tPmContractCheck.getContractName();
                }
            } else if (ApprovalConstant.APPR_TYPE_FUNDS.equals(apprType)) {
                TPmProjectFundsApprEntity tPmProjectFundsAppr = this.get(TPmProjectFundsApprEntity.class, apprId);
                TPmProjectEntity project = this.get(TPmProjectEntity.class, tPmProjectFundsAppr.getTpId());

                if (tPmProjectFundsAppr != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(tPmProjectFundsAppr.getFinishFlag())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(tPmProjectFundsAppr.getFinishFlag())) {
                        tPmProjectFundsAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(tPmProjectFundsAppr);
                        changeFlag = true;
                    }
                    apprName = project.getProjectName();
                }
            } else if (ApprovalConstant.APPR_TYPE_INCOME.equals(apprType)) {
                TPmIncomeContractApprEntity tPmIncomeContractAppr = this.get(TPmIncomeContractApprEntity.class, apprId);
                if (tPmIncomeContractAppr != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(tPmIncomeContractAppr.getFinishFlag())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(tPmIncomeContractAppr.getFinishFlag())) {
                        tPmIncomeContractAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(tPmIncomeContractAppr);
                        changeFlag = true;
                    }
                    apprName = tPmIncomeContractAppr.getContractName();
                }
            } else if (ApprovalConstant.APPR_TYPE_OUTCOME.equals(apprType)) {
                TPmOutcomeContractApprEntity tPmOutcomeContractAppr = this.get(TPmOutcomeContractApprEntity.class,
                        apprId);
                if (tPmOutcomeContractAppr != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(tPmOutcomeContractAppr.getFinishFlag())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(tPmOutcomeContractAppr.getFinishFlag())) {
                        tPmOutcomeContractAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(tPmOutcomeContractAppr);
                        changeFlag = true;
                    }
                    apprName = tPmOutcomeContractAppr.getContractName();
                }
            } else if (ApprovalConstant.APPR_TYPE_NODECHECK.equals(apprType)) {
                TPmContractNodeCheckEntity tPmContractNodeCheck = this.get(TPmContractNodeCheckEntity.class, apprId);
                if (tPmContractNodeCheck != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(tPmContractNodeCheck.getOperationStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(tPmContractNodeCheck.getOperationStatus())) {
                        tPmContractNodeCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(tPmContractNodeCheck);
                        changeFlag = true;
                    }
                    apprName = tPmContractNodeCheck.getContractName();
                }
            } else if (ApprovalConstant.APPR_TYPE_APPRAISAL.equals(apprType)) {
                TBAppraisalApprovalEntity tBAppraisalApproval = this.get(TBAppraisalApprovalEntity.class, apprId);
                if (tBAppraisalApproval != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(tBAppraisalApproval.getAuditStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(tBAppraisalApproval.getAuditStatus())) {
                        tBAppraisalApproval.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(tBAppraisalApproval);
                        changeFlag = true;
                    }
                    apprName = tBAppraisalApproval.getAppraisalProject().getProjectName();
                }
            } else if (ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY.equals(apprType)) {
                TBAppraisalApplyEntity tBAppraisalApply = this.get(TBAppraisalApplyEntity.class, apprId);
                if (tBAppraisalApply != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(tBAppraisalApply.getAuditStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(tBAppraisalApply.getAuditStatus())) {
                        tBAppraisalApply.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(tBAppraisalApply);
                        changeFlag = true;
                    }
                }
            } else if (ApprovalConstant.APPR_TYPE_RESULT_REWARD.equals(apprType)) {
                TBResultRewardEntity reward = this.get(TBResultRewardEntity.class, apprId);
                if (reward != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(reward.getFinishFlag())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(reward.getFinishFlag())) {
                        reward.setFinishFlag(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(reward);
                        changeFlag = true;
                    }
                    apprName = reward.getRewardName();
                }
            } else if (ApprovalConstant.APPR_TYPE_TASK.equals(apprType)) {
                TPmTaskEntity task = this.get(TPmTaskEntity.class, apprId);
                if (task != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(task.getAuditStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(task.getAuditStatus())) {
                        task.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(task);
                        changeFlag = true;
                    }
                    apprName = task.getTaskTitle();
                }
            } else if (ApprovalConstant.APPR_TYPE_PAY_APPLY.equals(apprType)) {
                TPmPayApplyEntity payApply = this.get(TPmPayApplyEntity.class, apprId);
                if (payApply != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(payApply.getOperateStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(payApply.getOperateStatus())) {
                        payApply.setOperateStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(payApply);
                        changeFlag = true;
                    }
                    apprName = payApply.getContractName();
                }
            } else if (ApprovalConstant.APPR_TYPE_TASK_NODECHECK.equals(apprType)) {
                TPmTaskNodeEntity taskNode = this.get(TPmTaskNodeEntity.class, apprId);
                if (taskNode != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(taskNode.getOperationStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(taskNode.getOperationStatus())) {
                        taskNode.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(taskNode);
                        changeFlag = true;
                    }
                }
            } else if (ApprovalConstant.APPR_TYPE_FINISH_APPLY.equals(apprType)) {//????????????????????????
                TPmFinishApplyEntity finishApply = this.get(TPmFinishApplyEntity.class, apprId);
                if (finishApply != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(finishApply.getAuditStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(finishApply.getAuditStatus())) {
                        finishApply.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(finishApply);
                        changeFlag = true;
                    }
                }
            } else if (ApprovalConstant.APPR_TYPE_PLAN_DRAFT.equals(apprType)) {//????????????????????????
                TPmPlanDraftEntity planApply = this.get(TPmPlanDraftEntity.class, apprId);
                if (planApply != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(planApply.getPlanStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(planApply.getPlanStatus())) {
                        planApply.setPlanStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(planApply);
                        changeFlag = true;
                    }
                }
            }else if (ApprovalConstant.APPR_TYPE_SEAL_USE.equals(apprType)) {//??????????????????????????????
              TOSealUseEntity entity = this.get(TOSealUseEntity.class, apprId);
              if (entity != null) {
                  if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAuditStatus())
                          || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAuditStatus())) {
                    entity.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
                      this.saveOrUpdate(entity);
                      changeFlag = true;
                  }
              }
            } else if (ApprovalConstant.APPR_TYPE_TRAVEL.equals(apprType)) {//??????????????????????????????
                TOTravelLeaveEntity travelLeave = this.get(TOTravelLeaveEntity.class,
                        apprId);
                if (travelLeave != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(travelLeave.getApprStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(travelLeave.getApprStatus())) {
                        travelLeave.setApprStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(travelLeave);
                        changeFlag = true;
                    }
                }
            } else if (ApprovalConstant.APPR_TYPE_ZLSQ.equals(apprType)) {//??????????????????????????????
                TZZlsqEntity zlsq = this.get(TZZlsqEntity.class, apprId);
                if (zlsq != null) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(zlsq.getApprStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(zlsq.getApprStatus())) {
                        zlsq.setApprStatus(ApprovalConstant.APPRSTATUS_SEND);
                        this.saveOrUpdate(zlsq);
                        changeFlag = true;
                    }
                    apprName = zlsq.getMc();
                }
            }
        }
        map.put("changeFlag", changeFlag);

        //??????????????????
        StringBuffer msg = new StringBuffer();
        String apprTypeString = ApprovalConstant.APPR_TYPE_NAME.get(apprType);
        if (StringUtils.isNotEmpty(apprName)) {
            msg.append("????????????" + apprTypeString + "???????????????");
        } else {
            msg.append("????????????" + apprTypeString + "[" + apprName + "]???????????????");
        }
        if (StringUtil.isNotEmpty(tPmApprSendLogs.getSenderTip())) {
            msg.append("\n?????????" + tPmApprSendLogs.getSenderTip());
        }

        //?????????????????????
        if (StringUtil.isNotEmpty(receiveUseIds)) {
            String[] receiveUseIdArray = receiveUseIds.split(",");

            int UseNameLength = 0;
            String[] receiveUseNameArray = null;
            if (StringUtil.isNotEmpty(receiveUseNames)) {
                receiveUseNameArray = receiveUseNames.split(",");
                UseNameLength = receiveUseNameArray.length;
            }

            int DepartIdLength = 0;
            String[] receiveUseDepartIdArray = null;
            if (StringUtil.isNotEmpty(receiveUseDepartIds)) {
                receiveUseDepartIdArray = receiveUseDepartIds.split(",");
                DepartIdLength = receiveUseDepartIdArray.length;
            }

            int DepartNameLength = 0;
            String[] receiveUseDepartNameArray = null;
            if (StringUtil.isNotEmpty(receiveUseDepartNames)) {
                receiveUseDepartNameArray = receiveUseDepartNames.split(",");
                DepartNameLength = receiveUseDepartNameArray.length;
            }

            List<TPmApprReceiveLogsEntity> tPmApprReceiveLogs = new ArrayList<TPmApprReceiveLogsEntity>();

            for (int i = 0; i < receiveUseIdArray.length; i++) {
                TPmApprReceiveLogsEntity tPmApprReceiveLog = new TPmApprReceiveLogsEntity();

                tPmApprReceiveLog.setApprSendLog(tPmApprSendLogs);
                tPmApprReceiveLog.setApprId(apprId);
                tPmApprReceiveLog.setOperateStatus(SrmipConstants.NO);
                tPmApprReceiveLog.setTableName(tPmApprSendLogs.getTableName());
                tPmApprReceiveLog.setReceiveUserid(receiveUseIdArray[i]);
                if (UseNameLength > i) {
                    tPmApprReceiveLog.setReceiveUsername(receiveUseNameArray[i]);
                }
                if (DepartIdLength > i) {
                    tPmApprReceiveLog.setReceiveDepartid(receiveUseDepartIdArray[i]);
                }
                if (DepartNameLength > i) {
                    tPmApprReceiveLog.setReceiveDepartname(receiveUseDepartNameArray[i]);
                }
                tPmApprReceiveLog.setSuggestionType(tPmApprSendLogs.getSuggestionType());
                /* tPmApprReceiveLog.setHandlerType(handlerType); */
                tPmApprReceiveLog.setValidFlag(SrmipConstants.YES);
                tPmApprReceiveLog.setRebutFlag(rebutFlag);
                this.save(tPmApprReceiveLog);

                tOMessageService.sendMessage(receiveUseIdArray[i], apprTypeString + "??????", apprTypeString,
                        msg.toString(), user.getId());

                tPmApprReceiveLogs.add(tPmApprReceiveLog);
            }

            map.put("send", tPmApprSendLogs);
            map.put("receive", tPmApprReceiveLogs);
            map.put("changeFlag", changeFlag);
        }
        return map;
    }

    public Map<String, Object> doUpdateLog(TPmApprReceiveLogsEntity t, String rebutValidFlag) {
        String sendUseId = t.getApprSendLog().getOperateUserid();//?????????
        String apprType = t.getSuggestionType().substring(0, 2);//????????????
        String apprTypeString = ApprovalConstant.APPR_TYPE_NAME.get(apprType);//????????????????????????

        String apprName = "";//????????????????????????
        StringBuffer messageContent = new StringBuffer("???????????????");//??????????????????

        if (ReceiveBillConstant.AUDIT_REBUT.equals(t.getSuggestionCode())) {//??????
            if (ApprovalConstant.APPR_TYPE_CHECK.equals(apprType)) {
                TPmContractCheckEntity tPmContractCheck = this.get(TPmContractCheckEntity.class, t.getApprId());

                //?????????????????????????????????
                tPmContractCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_REBUT);
                tPmContractCheck.setBackLog(t);
                this.saveOrUpdate(tPmContractCheck);

                apprName = tPmContractCheck.getContractName();
            } else if (ApprovalConstant.APPR_TYPE_FUNDS.equals(apprType)) {
                TPmProjectFundsApprEntity tPmProjectFundsAppr = this
                        .get(TPmProjectFundsApprEntity.class, t.getApprId());
                TPmProjectEntity project = this.get(TPmProjectEntity.class, tPmProjectFundsAppr.getTpId());

                //?????????????????????????????????
                tPmProjectFundsAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
                tPmProjectFundsAppr.setBackLog(t);
                this.saveOrUpdate(tPmProjectFundsAppr);

                apprName = project.getProjectName() + "??????";
            } else if (ApprovalConstant.APPR_TYPE_INCOME.equals(apprType)) {
                TPmIncomeContractApprEntity tPmIncomeContractAppr = this.get(TPmIncomeContractApprEntity.class,
                        t.getApprId());

                //?????????????????????????????????
                tPmIncomeContractAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
                tPmIncomeContractAppr.setBackLog(t);
                this.saveOrUpdate(tPmIncomeContractAppr);

                apprName = tPmIncomeContractAppr.getContractName();
            } else if (ApprovalConstant.APPR_TYPE_OUTCOME.equals(apprType)) {
                TPmOutcomeContractApprEntity tPmOutcomeContractAppr = this.get(TPmOutcomeContractApprEntity.class,
                        t.getApprId());

                //?????????????????????????????????
                tPmOutcomeContractAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
                tPmOutcomeContractAppr.setBackLog(t);
                this.saveOrUpdate(tPmOutcomeContractAppr);

                apprName = tPmOutcomeContractAppr.getContractName();
            } else if (ApprovalConstant.APPR_TYPE_NODECHECK.equals(apprType)) {
                TPmContractNodeCheckEntity tPmContractNodeCheck = this.get(TPmContractNodeCheckEntity.class,
                        t.getApprId());

                //?????????????????????????????????
                tPmContractNodeCheck.setOperationStatus(ApprovalConstant.APPRSTATUS_REBUT);
                tPmContractNodeCheck.setBackLog(t);
                this.saveOrUpdate(tPmContractNodeCheck);

                apprName = tPmContractNodeCheck.getContractName() + "??????????????????";
            } else if (ApprovalConstant.APPR_TYPE_APPRAISAL.equals(apprType)) {
                TBAppraisalApprovalEntity tBAppraisalApproval = this
                        .get(TBAppraisalApprovalEntity.class, t.getApprId());

                //?????????????????????????????????
                tBAppraisalApproval.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
                tBAppraisalApproval.setBackLog(t);
                this.saveOrUpdate(tBAppraisalApproval);

                apprName = tBAppraisalApproval.getAppraisalProject().getProjectName() + "????????????";
            } else if (ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY.equals(apprType)) {
                TBAppraisalApplyEntity tBAppraisalApply = this.get(TBAppraisalApplyEntity.class, t.getApprId());

                //?????????????????????????????????
                tBAppraisalApply.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
                this.saveOrUpdate(tBAppraisalApply);

            } else if (ApprovalConstant.APPR_TYPE_TASK.equals(apprType)) {
                TPmTaskEntity taskApply = this.get(TPmTaskEntity.class, t.getApprId());

                //?????????????????????????????????
                taskApply.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
                this.saveOrUpdate(taskApply);

                apprName = taskApply.getTaskTitle() + "?????????";
            } else if (ApprovalConstant.APPR_TYPE_PAY_APPLY.equals(apprType)) {
                TPmPayApplyEntity taskApply = this.get(TPmPayApplyEntity.class, t.getApprId());

                //?????????????????????????????????
                taskApply.setOperateStatus(ApprovalConstant.APPRSTATUS_REBUT);
                this.saveOrUpdate(taskApply);

                apprName = taskApply.getContractName() + "????????????";
            } else if (ApprovalConstant.APPR_TYPE_TASK_NODECHECK.equals(apprType)) {
                TPmTaskNodeEntity taskNodeApply = this.get(TPmTaskNodeEntity.class, t.getApprId());

                //?????????????????????????????????
                taskNodeApply.setOperationStatus(ApprovalConstant.APPRSTATUS_REBUT);
                this.saveOrUpdate(taskNodeApply);

                apprName = taskNodeApply.getTaskContent() + "??????????????????";
            } else if (ApprovalConstant.APPR_TYPE_FINISH_APPLY.equals(apprType)) {
                TPmFinishApplyEntity finishApply = this.get(TPmFinishApplyEntity.class, t.getApprId());

                //?????????????????????????????????
                finishApply.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
                this.saveOrUpdate(finishApply);

                apprName = finishApply.getProjectName() + "????????????";
            }else if (ApprovalConstant.APPR_TYPE_SEAL_USE.equals(apprType)) {
              TOSealUseEntity finishApply = this.get(TOSealUseEntity.class, t.getApprId());

              //?????????????????????????????????
              finishApply.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
              this.saveOrUpdate(finishApply);

              apprName = "??????????????????";
            } else if (ApprovalConstant.APPR_TYPE_TRAVEL.equals(apprType)) {
                TOTravelLeaveEntity finishApply = this.get(TOTravelLeaveEntity.class, t.getApprId());
                //?????????????????????????????????
                finishApply.setApprStatus(ApprovalConstant.APPRSTATUS_REBUT);
                this.saveOrUpdate(finishApply);
                apprName = "??????????????????";
            } else if (ApprovalConstant.APPR_TYPE_ZLSQ.equals(apprType)) {
                TZZlsqEntity zlsqApply = this.get(TZZlsqEntity.class, t.getApprId());
                //?????????????????????????????????
                zlsqApply.setApprStatus(ApprovalConstant.APPRSTATUS_REBUT);
                this.saveOrUpdate(zlsqApply);
                apprName = zlsqApply.getMc();
            }

            //??????????????????
            messageContent.append(apprTypeString + "[" + apprName + "]????????????????????????");
            messageContent.append("\n????????????" + t.getReceiveUsername() + ",");
            messageContent.append("\n???????????????" + DateUtils.date2Str(t.getOperateTime(), DateUtils.datetimeFormat));
            if (StringUtil.isNotEmpty(t.getSuggestionContent())) {
                messageContent.append("\n???????????????" + t.getSuggestionContent());
            }

            //???????????????????????????????????????
            t.setValidFlag(SrmipConstants.NO);

            //?????????????????????
            CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
            cq.eq("apprId", t.getApprId());
            cq.notEq("validFlag", SrmipConstants.NO);
            cq.notEq("id", t.getId());
            cq.add();
            List<TPmApprReceiveLogsEntity> otherReceiveLogs = this.getListByCriteriaQuery(cq, false);
            if (otherReceiveLogs != null && otherReceiveLogs.size() > 0) {
                for (TPmApprReceiveLogsEntity receiveLog : otherReceiveLogs) {
                    //????????????????????????????????????????????????
                    if (SrmipConstants.YES.equals(receiveLog.getOperateStatus()) && "true".equals(rebutValidFlag)) {
                        receiveLog.setValidFlag(SrmipConstants.NO);
                        this.updateEntitie(receiveLog);
                    }

                    //?????????????????????????????????????????????????????????
                    tOMessageService.sendMessage(receiveLog.getReceiveUserid(), apprTypeString + "????????????",
                            apprTypeString,
                            messageContent.toString(), null);
                }
            }

        } else {
            if (ApprovalConstant.APPR_TYPE_CHECK.equals(apprType)) {
                TPmContractCheckEntity tPmContractCheck = this.get(TPmContractCheckEntity.class, t.getApprId());
                apprName = tPmContractCheck.getContractName();
            } else if (ApprovalConstant.APPR_TYPE_FUNDS.equals(apprType)) {
                TPmProjectFundsApprEntity tPmProjectFundsAppr = this
                        .get(TPmProjectFundsApprEntity.class, t.getApprId());
                TPmProjectEntity project = this.get(TPmProjectEntity.class, tPmProjectFundsAppr.getTpId());
                apprName = project.getProjectName();
            } else if (ApprovalConstant.APPR_TYPE_INCOME.equals(apprType)) {
                TPmIncomeContractApprEntity tPmIncomeContractAppr = this.get(TPmIncomeContractApprEntity.class,
                        t.getApprId());
                apprName = tPmIncomeContractAppr.getContractName();
            } else if (ApprovalConstant.APPR_TYPE_OUTCOME.equals(apprType)) {
                TPmOutcomeContractApprEntity tPmOutcomeContractAppr = this.get(TPmOutcomeContractApprEntity.class,
                        t.getApprId());
                apprName = tPmOutcomeContractAppr.getContractName();
            } else if (ApprovalConstant.APPR_TYPE_NODECHECK.equals(apprType)) {
                TPmContractNodeCheckEntity tPmContractNodeCheck = this.get(TPmContractNodeCheckEntity.class,
                        t.getApprId());
                apprName = tPmContractNodeCheck.getContractName();
            } else if (ApprovalConstant.APPR_TYPE_APPRAISAL.equals(apprType)) {
                TBAppraisalApprovalEntity tBAppraisalApproval = this
                        .get(TBAppraisalApprovalEntity.class, t.getApprId());
                apprName = tBAppraisalApproval.getAppraisalProject().getProjectName();
            } else if (ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY.equals(apprType)) {
                TBAppraisalApplyEntity tBAppraisalApply = this.get(TBAppraisalApplyEntity.class, t.getApprId());
            }

            messageContent.append(t.getReceiveUsername() + "??????" + apprTypeString + "[" + apprName + "]??????");
            messageContent.append("\n???????????????" + DateUtils.date2Str(t.getOperateTime(), DateUtils.datetimeFormat));
            if (StringUtil.isNotEmpty(t.getSuggestionContent())) {
                messageContent.append("\n???????????????" + t.getSuggestionContent());
            }
        }

        //???????????????????????????
        if (StringUtil.isNotEmpty(sendUseId)) {
            tOMessageService.sendMessage(sendUseId, apprTypeString + "????????????", apprTypeString, messageContent.toString(),
                    null);
        }

        this.saveOrUpdate(t);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("receive", t);
        map.put("changeFlag", true);
        return map;
    }

    public TPmApprReceiveLogsEntity goAddGetBackLog(String apprType, String apprId) {
        TPmApprReceiveLogsEntity backLog = new TPmApprReceiveLogsEntity();
        if (ApprovalConstant.APPR_TYPE_CHECK.equals(apprType)) {
            TPmContractCheckEntity tPmContractCheck = this.getEntity(TPmContractCheckEntity.class, apprId);
            if (tPmContractCheck != null) {
                backLog = tPmContractCheck.getBackLog();
            }
        } else if (ApprovalConstant.APPR_TYPE_FUNDS.equals(apprType)) {
            TPmProjectFundsApprEntity tPmProjectFundsAppr = this.getEntity(TPmProjectFundsApprEntity.class, apprId);
            if (tPmProjectFundsAppr != null) {
                backLog = tPmProjectFundsAppr.getBackLog();
            }
        } else if (ApprovalConstant.APPR_TYPE_INCOME.equals(apprType)) {
            TPmIncomeContractApprEntity tPmIncomeContractAppr = this.getEntity(TPmIncomeContractApprEntity.class,
                    apprId);
            if (tPmIncomeContractAppr != null) {
                backLog = tPmIncomeContractAppr.getBackLog();
            }
        } else if (ApprovalConstant.APPR_TYPE_OUTCOME.equals(apprType)) {
            TPmOutcomeContractApprEntity tPmOutcomeContractAppr = this.getEntity(TPmOutcomeContractApprEntity.class,
                    apprId);
            if (tPmOutcomeContractAppr != null) {
                backLog = tPmOutcomeContractAppr.getBackLog();
            }
        } else if (ApprovalConstant.APPR_TYPE_NODECHECK.equals(apprType)) {
            TPmContractNodeCheckEntity tPmContractNodeCheck = this.getEntity(TPmContractNodeCheckEntity.class, apprId);
            if (tPmContractNodeCheck != null) {
                backLog = tPmContractNodeCheck.getBackLog();
            }
        } else if (ApprovalConstant.APPR_TYPE_APPRAISAL.equals(apprType)) {
            TBAppraisalApprovalEntity tBAppraisalApproval = this.getEntity(TBAppraisalApprovalEntity.class, apprId);
            if (tBAppraisalApproval != null) {
                backLog = tBAppraisalApproval.getBackLog();
            }
        } else if (ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY.equals(apprType)) {
            TBAppraisalApplyEntity tBAppraisalApply = this.getEntity(TBAppraisalApplyEntity.class, apprId);
        }
        return backLog;
    }

    public void goAddGetOtherInfo(String apprType, String apprId, String handlerType, TPmApprReceiveLogsEntity backLog,
            TSUser receiver, String ifStaff) {
        ifStaff = "yes";
        TPmProjectEntity project = new TPmProjectEntity();
        if (ApprovalConstant.APPR_TYPE_CHECK.equals(apprType)) {
            TPmContractCheckEntity tPmContractCheck = this.getEntity(TPmContractCheckEntity.class, apprId);
            if (tPmContractCheck != null) {
                backLog = tPmContractCheck.getBackLog();
            }

            TPmIncomeContractApprEntity contract = this.get(TPmIncomeContractApprEntity.class,
                    tPmContractCheck.getContractId());
            project = contract.getProject();
        }

        //??????????????????????????????????????????
        if (ApprovalConstant.HANDLER_TYPE_JIGUAN.equals(handlerType) && StringUtil.isNotEmpty(project.getOfficeStaff())) {
            receiver = this.get(TSUser.class, project.getOfficeStaff());
        } else if (ApprovalConstant.HANDLER_TYPE_YANXI.equals(handlerType)
                && StringUtil.isNotEmpty(project.getDepartStaff())) {
            receiver = this.get(TSUser.class, project.getDepartStaff());
        } else {
            ifStaff = "no";
        }
    }

    @Override
    public int getAuditCount(String apprType) {
        TSUser user = ResourceUtil.getSessionUserName();//????????????
        String[] params = new String[] { user.getId(),  apprType };

        String sql = null;
        Long count = Long.valueOf(0);
        if(ApprovalConstant.APPR_TYPE_FUNDS.equals(apprType)) {
        	sql = "SELECT COUNT(0) FROM T_PM_PROJECT_FUNDS_APPR_AUDIT A, T_PM_PROJECT B  "
        			+ "WHERE A.T_P_ID=B.ID AND A.RECIVE_USERIDS LIKE '%"+user.getId()+"%' AND  A.AUDIT_STATUS IN (1)";
        	count = this.commonDao.getCountForJdbc(sql);
        }else {
            sql = "SELECT COUNT(0) FROM T_PM_APPR_SEND_LOGS SEND,T_PM_APPR_RECEIVE_LOGS RECEIVE,"
                    + "T_PM_FORM_CATEGORY EXT WHERE SEND.ID = RECEIVE.SEND_ID AND RECEIVE.SUGGESTION_TYPE = EXT.ID"
                    + " AND RECEIVE.RECEIVE_USERID = ?  AND RECEIVE.OPERATE_STATUS = '0' "
                    + "AND RECEIVE.VALID_FLAG = '1' and substr(SEND.SUGGESTION_TYPE,0,2)= ?";
            count = this.commonDao.getCountForJdbcParam(sql,params);
        }
 
        return count.intValue();
    }

    /**
     * ??????????????????????????????????????????
     * 
     * @param id
     */
    @Override
    public void finishApprLogs(String id) {
        CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
        cq.eq("apprId", id);
        cq.eq("operateStatus", SrmipConstants.NO);
        cq.eq("validFlag", SrmipConstants.YES);
        cq.add();
        List<TPmApprReceiveLogsEntity> tPmContractReceiveLogs = commonDao.getListByCriteriaQuery(cq, false);
        for (TPmApprReceiveLogsEntity logEntity : tPmContractReceiveLogs) {
            logEntity.setOperateStatus(SrmipConstants.YES);
            logEntity.setOperateTime(new Date());
            this.commonDao.updateEntitie(logEntity);
        }
    }

    @Override
    public void doBack(HttpServletRequest request) {
        String recId = request.getParameter("recId");
        String apprId = request.getParameter("apprId");
        String suggestionCode = request.getParameter("suggestionCode");
        String suggestionContent = request.getParameter("suggestionContent");
        String suggestionType = request.getParameter("suggestionType");
        String receiveUseNames = request.getParameter("receiveUseNames");
        String receiveUseIds = request.getParameter("receiveUseIds");
//        String receiveUseDepartIds = request.getParameter("receiveUseDepartIds");
//        String receiveUseDepartNames = request.getParameter("receiveUseDepartNames");
        TPmApprReceiveLogsEntity recLog = this.commonDao.get(TPmApprReceiveLogsEntity.class, recId);
        recLog.setValidFlag(SrmipConstants.NO);//?????????????????????????????????
        recLog.setOperateStatus(SrmipConstants.YES);
        recLog.setOperateTime(new Date());
        recLog.setSuggestionCode(suggestionCode);
        recLog.setSuggestionContent(suggestionContent);
        this.commonDao.updateEntitie(recLog);
        CriteriaQuery hcq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
        hcq.eq("suggestionType", recLog.getSuggestionType());
        hcq.eq("apprId", recLog.getApprId());
        hcq.eq("validFlag", SrmipConstants.YES);
        hcq.add();
        List<TPmApprReceiveLogsEntity> existList =  this.commonDao.getListByCriteriaQuery(hcq,false);
        if(existList.size()>0){
            for(TPmApprReceiveLogsEntity tmp:existList){//??????????????????????????????????????????????????????????????????
                tmp.setValidFlag(SrmipConstants.NO);
                this.commonDao.updateEntitie(tmp);
            }
        }
        String apprType = recLog.getSuggestionType().substring(0, 2);
        String apprTypeString = ApprovalConstant.APPR_TYPE_NAME.get(apprType);//????????????????????????
        ApprFlowServiceI apprService = apprFlowServiceContext.getApprService(apprType);
        StringBuffer messageContent = new StringBuffer("???????????????");//??????????????????
        messageContent.append(apprTypeString + "[" + apprService.getAppName(apprId) + "]?????????????????????");
        messageContent.append("\n????????????" + recLog.getReceiveUsername() + ",");
        messageContent.append("\n???????????????" + DateUtils.date2Str(recLog.getOperateTime(), DateUtils.datetimeFormat));
        if (StringUtil.isNotEmpty(recLog.getSuggestionContent())) {
            messageContent.append("\n???????????????" + recLog.getSuggestionContent());
        }
        
        String operateStatus = SrmipConstants.NO;
        if ("0000".equals(suggestionType)) {
            apprService.doBack(apprId);
            clearApprLog(apprId);//?????????????????????????????????
        } 
            TPmApprSendLogsEntity sendLog = new TPmApprSendLogsEntity();
            TSUser user = ResourceUtil.getSessionUserName();
            sendLog.setApprId(recLog.getApprId());
            sendLog.setOperateDate(new Date());
            sendLog.setOperateUserid(user.getId());
            sendLog.setOperateUsername(user.getRealName());
            sendLog.setOperateDepartid(user.getCurrentDepart().getId());
            sendLog.setOperateDepartname(user.getCurrentDepart().getDepartname());
            sendLog.setSuggestionType(suggestionType);
            sendLog.setSourceId(recId);
            String operateIp = IpUtil.getIpAddr(request);
            sendLog.setOperateIp(operateIp);
            this.commonDao.save(sendLog);
            if (StringUtil.isNotEmpty(receiveUseIds)) {
            TPmApprReceiveLogsEntity tPmApprReceiveLog = new TPmApprReceiveLogsEntity();
            tPmApprReceiveLog.setApprSendLog(sendLog);
            tPmApprReceiveLog.setApprId(recLog.getApprId());
            tPmApprReceiveLog.setOperateStatus(operateStatus);
            tPmApprReceiveLog.setTableName(sendLog.getTableName());
            tPmApprReceiveLog.setReceiveUserid(receiveUseIds);
            tPmApprReceiveLog.setReceiveUsername(receiveUseNames);
//            tPmApprReceiveLog.setReceiveDepartid(receiveUseDepartIds);
//            tPmApprReceiveLog.setReceiveDepartname(receiveUseDepartNames);
            tPmApprReceiveLog.setSuggestionType(sendLog.getSuggestionType());
            tPmApprReceiveLog.setValidFlag(SrmipConstants.YES);
            this.commonDao.save(tPmApprReceiveLog);
            String execueteSql = "update t_pm_appr_receive_logs t set t.receive_time=sysdate where t.id='"+tPmApprReceiveLog.getId()+"'";
            //??????????????????????????????
            tOMessageService.sendMessage(receiveUseIds, apprTypeString + "????????????", apprTypeString, messageContent.toString(),
                    recLog.getReceiveUserid(), execueteSql);
            }
        
    }
    
    /**
     * ???????????????????????????????????????
     */
    private void clearApprLog(String apprId){
        CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
        cq.eq("apprId", apprId);
        cq.eq("validFlag", SrmipConstants.YES);
        cq.add();
        List<TPmApprReceiveLogsEntity> recLogs = this.commonDao.getListByCriteriaQuery(cq, false);
        for(TPmApprReceiveLogsEntity recLog:recLogs){
            recLog.setValidFlag(SrmipConstants.NO);
            this.commonDao.updateEntitie(recLog);
        }
    }

    @Override
    public void doPass(HttpServletRequest request) {
        String apprId = request.getParameter("apprId");
        String recId = request.getParameter("recId");
        String suggestionType = request.getParameter("suggestionType");
        String suggestionCode = request.getParameter("suggestionCode");
        String suggestionContent = request.getParameter("suggestionContent");
        String receiveUseNames = request.getParameter("receiveUseNames");
        String receiveUseIds = request.getParameter("receiveUseIds");
//        String receiveUseDepartIds = request.getParameter("receiveUseDepartIds");
//        String receiveUseDepartNames = request.getParameter("receiveUseDepartNames");
        String senderTip = request.getParameter("senderTip");
        String apprType = suggestionType.substring(0,2);
        TSUser user = ResourceUtil.getSessionUserName();
        if(StringUtils.isNotEmpty(recId)){
        TPmApprReceiveLogsEntity recLog = this.commonDao.get(TPmApprReceiveLogsEntity.class, recId);
        recLog.setOperateStatus(SrmipConstants.YES);
        recLog.setOperateTime(new Date());
        recLog.setSuggestionCode(suggestionCode);
        recLog.setSuggestionContent(suggestionContent);
        this.commonDao.updateEntitie(recLog);
        }else{
            CriteriaQuery cq = new  CriteriaQuery(TPmApprReceiveLogsEntity.class);
            cq.eq("apprId", apprId);
            cq.eq("operateStatus", SrmipConstants.NO);
            cq.eq("suggestionType", "0000");
            cq.eq("receiveUserid", user.getId());
            cq.add();
            List<TPmApprReceiveLogsEntity> queryRecLogList = this.commonDao.getListByCriteriaQuery(cq, false);
            if(queryRecLogList.size()>0){
                TPmApprReceiveLogsEntity tmp = queryRecLogList.get(0);
                tmp.setOperateStatus(SrmipConstants.YES);
                tmp.setSuggestionCode("");
                tmp.setSuggestionContent("??????????????????");
                tmp.setOperateTime(new Date());
                this.commonDao.updateEntitie(tmp);
            }
        }
        //???????????????
        TPmApprSendLogsEntity sendLog = new TPmApprSendLogsEntity();
        sendLog.setApprId(apprId);
        sendLog.setOperateDate(new Date());
        sendLog.setOperateUserid(user.getId());
        sendLog.setOperateUsername(user.getRealName());
        sendLog.setOperateDepartid(user.getCurrentDepart().getId());
        sendLog.setOperateDepartname(user.getCurrentDepart().getDepartname());
        sendLog.setSuggestionType(suggestionType);
        String operateIp = IpUtil.getIpAddr(request);
        sendLog.setOperateIp(operateIp);
        sendLog.setSenderTip(senderTip);
        sendLog.setSourceId(recId);
        //??????????????????
        this.commonDao.save(sendLog);
        ApprFlowServiceI apprService = apprFlowServiceContext.getApprService(apprType);
        apprService.doPass(apprId);
        String apprName = apprService.getAppName(apprId);
        //??????????????????
        StringBuffer msg = new StringBuffer();
        String apprTypeString = ApprovalConstant.APPR_TYPE_NAME.get(apprType);
        if (StringUtils.isNotEmpty(apprName)) {
            msg.append("????????????" + apprTypeString + "???????????????");
        } else {
            msg.append("????????????" + apprTypeString + "[" + apprName + "]???????????????");
        }
        if (StringUtil.isNotEmpty(senderTip)) {
            msg.append("\n?????????" + senderTip);
        }

        //?????????????????????
        if (StringUtil.isNotEmpty(receiveUseIds)) {
            TPmApprReceiveLogsEntity tPmApprReceiveLog = new TPmApprReceiveLogsEntity();
            tPmApprReceiveLog.setApprSendLog(sendLog);
            tPmApprReceiveLog.setApprId(apprId);
            tPmApprReceiveLog.setOperateStatus(SrmipConstants.NO);
            tPmApprReceiveLog.setTableName(sendLog.getTableName());
            tPmApprReceiveLog.setReceiveUserid(receiveUseIds);
            tPmApprReceiveLog.setReceiveUsername(receiveUseNames);
//            tPmApprReceiveLog.setReceiveDepartid(receiveUseDepartIds);
//            tPmApprReceiveLog.setReceiveDepartname(receiveUseDepartNames);
            tPmApprReceiveLog.setSuggestionType(sendLog.getSuggestionType());
            tPmApprReceiveLog.setValidFlag(SrmipConstants.YES);
            Date d = new Date();
            tPmApprReceiveLog.setReceiveTime(d);
            tPmApprReceiveLog.setOperateTime(d);
            this.save(tPmApprReceiveLog);
            String execueteSql = "update t_pm_appr_receive_logs t set t.receive_time=sysdate where t.id='"+tPmApprReceiveLog.getId()+"'";
            tOMessageService.sendMessage(receiveUseIds, apprTypeString + "??????", apprTypeString, msg.toString(),
                    user.getId(), execueteSql);
        }

    }
    
    @Override
    public List<Map<String, Object>> getNodeLink(String recId, String apprType) {
        String apprTypeSql = "SELECT ID, LABEL, HANDLER_TYPE, FIELD_LABEL FROM T_PM_FORM_CATEGORY "
                + "WHERE SUBSTR(ID, 0, 2) = ? ORDER BY ID";//????????????????????????
        List<Map<String, Object>> apprNodeMap = this.commonDao.findForJdbc(apprTypeSql, apprType);
        Map<String, String> suggestionTypeMap = new HashMap<String, String>();
        for (Map<String, Object> map : apprNodeMap) {
            suggestionTypeMap.put((String) map.get("ID"), (String) map.get("LABEL"));
        }
        TPmApprReceiveLogsEntity recLog = this.commonDao.get(TPmApprReceiveLogsEntity.class, recId);
        String apprId = recLog.getApprId();
        CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
        cq.eq("apprId", apprId);
        cq.addOrder("operateTime", SortDirection.desc);
        cq.add();
        List<TPmApprReceiveLogsEntity> recLogs = this.commonDao.getListByCriteriaQuery(cq, false);

        List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();
        TPmApprSendLogsEntity sendLog = recLog.getApprSendLog();
        while (StringUtils.isNotEmpty(sendLog.getSourceId())) {//?????????????????????
            TPmApprReceiveLogsEntity lastNode = getRecLog(sendLog, recLogs);
            if ("1".equals(lastNode.getValidFlag())) {//?????????????????????????????????
                boolean flag = checkValid(lastNode, nodeList,recLog);
                if (flag) {
                    Map<String, Object> tmpMap = new HashMap<String, Object>();
                    tmpMap.put("senderId", lastNode.getReceiveUserid());
                    tmpMap.put("senderName", lastNode.getReceiveUsername());
//                    tmpMap.put("senderDepartId", lastNode.getReceiveDepartid());
//                    tmpMap.put("senderDepartName", lastNode.getReceiveDepartname());
                    tmpMap.put("suggestionType", lastNode.getSuggestionType());
                    String suggestionTypeName = suggestionTypeMap.get(lastNode.getSuggestionType());
                    tmpMap.put("suggestionTypeName", suggestionTypeName);
                    nodeList.add(tmpMap);
                }
            }
            sendLog = lastNode.getApprSendLog();
        }
        Map<String, Object> tmpMap = new HashMap<String, Object>();
        tmpMap.put("senderId", sendLog.getOperateUserid());
        tmpMap.put("senderName", sendLog.getOperateUsername());
//        tmpMap.put("senderDepartId", sendLog.getOperateDepartid());
//        tmpMap.put("senderDepartName", sendLog.getOperateDepartname());
        tmpMap.put("suggestionType", "0000");
        tmpMap.put("suggestionTypeName", "?????????");
        nodeList.add(tmpMap);
        return nodeList;
    }
    
    /**
     * ????????????????????????????????????
     * @param sendLog
     * @param recLogs
     * @return
     */
    private TPmApprReceiveLogsEntity getRecLog(TPmApprSendLogsEntity sendLog,List<TPmApprReceiveLogsEntity> recLogs){
        String sourceId = sendLog.getSourceId();
        for(TPmApprReceiveLogsEntity recLog:recLogs){
            if (recLog.getId().equals(sourceId)) {
                return recLog;
            }
        }
        return null;
    }
    
    /**
     * ?????????????????????????????????????????????????????????
     * @param lastNode
     * @param nodeList
     * @return
     */
    private boolean checkValid(TPmApprReceiveLogsEntity lastNode,List<Map<String,Object>> nodeList,TPmApprReceiveLogsEntity recLog){
        boolean flag = true;
        boolean flag2 = Integer.parseInt(recLog.getSuggestionType())<=Integer.parseInt(lastNode.getSuggestionType());//?????????????????????
        if(flag2){
            return false;
        }
        for(Map<String,Object> tmpMap:nodeList){
            String senderId = (String) tmpMap.get("senderId");
            String suggestionType = (String) tmpMap.get("suggestionType");
            boolean flag1 = lastNode.getReceiveUserid().equals(senderId)&&lastNode.getSuggestionType().equals(suggestionType);
            if(flag1){
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Override
    public void finishAppr(HttpServletRequest request) {
        String apprType = request.getParameter("apprType");
        String apprId = request.getParameter("apprId");
        //???????????????????????????????????????????????????????????????????????????????????????????????????
        if(StringUtil.isNotEmpty(apprType)){
        	if(apprType.equals(ApprovalConstant.APPR_TYPE_PLAN_DRAFT)){
        		if (StringUtils.isNotEmpty(apprId)) {
                    List<TPmPlanDetailEntity> detailList = this.commonDao.findByProperty(TPmPlanDetailEntity.class,
                            "planId", apprId);
                    for (TPmPlanDetailEntity detail : detailList) {
                        TPmProjectEntity project = this.commonDao.get(TPmProjectEntity.class, detail.getProjectId());
                        project.setReportState(ProjectConstant.RECEIVE_FINISH_APPR);
                        commonDao.updateEntitie(project);
                    }
                } 
        	}
        }
        String suggestionCode = request.getParameter("suggestionCode");
        String suggestionContent = request.getParameter("suggestionContent");
        String recId = request.getParameter("recId");
        TPmApprReceiveLogsEntity recLog = this.commonDao.get(TPmApprReceiveLogsEntity.class, recId);
        recLog.setSuggestionCode(suggestionCode);
        recLog.setSuggestionContent(suggestionContent);
        recLog.setOperateStatus(SrmipConstants.YES);
        recLog.setOperateTime(new Date());
        this.commonDao.updateEntitie(recLog);
        ApprFlowServiceI flowService = apprFlowServiceContext.getApprService(apprType);
            flowService.updateFinishFlag(apprId);
            this.finishApprLogs(apprId);
    }

    @Override
    public void doCacel(String recId) {
        TPmApprReceiveLogsEntity cancelRecLog = this.commonDao.get(TPmApprReceiveLogsEntity.class, recId);
        String sendId = cancelRecLog.getApprSendLog().getId();
        this.commonDao.delete(cancelRecLog);
        TPmApprSendLogsEntity cancelSendLog = this.commonDao.get(TPmApprSendLogsEntity.class, sendId);
        this.commonDao.delete(cancelSendLog);
        String sourceId = cancelSendLog.getSourceId();
        if (StringUtils.isNotEmpty(sourceId)) {
            TPmApprReceiveLogsEntity recLog = this.commonDao.get(TPmApprReceiveLogsEntity.class, sourceId);
            recLog.setOperateStatus("0");
            recLog.setOperateTime(null);
            recLog.setSuggestionCode(null);
            recLog.setSuggestionContent(null);
            this.commonDao.updateEntitie(recLog);
        } else {
            String apprType = cancelRecLog.getSuggestionType().substring(0, 2);
            ApprFlowServiceI service = apprFlowServiceContext.getApprService(apprType);
            service.doCancel(cancelRecLog.getApprId());
        }
    }
    
    /**
     * ????????????
     * @param apprId
     * @return
     */
    @Override
    public int getRebutCount(String apprId){
        CriteriaQuery cq = new CriteriaQuery(TPmApprSendLogsEntity.class);
        cq.eq("apprId", apprId);
        cq.isNull("sourceId");
        cq.add();
        int count = this.commonDao.getCountByCriteriaQuery(cq);
        return (count-1);
    }
    
}