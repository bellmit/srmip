package com.kingtake.project.service.impl.appraisal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.appraisal.TBAppraisalApprovalEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalProjectEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appraisal.TBAppraisalApprovalServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tBAppraisaApprovalService")
@Transactional
public class TBAppraisalApprovalServiceImpl extends CommonServiceImpl implements TBAppraisalApprovalServiceI,
        ProjectListServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    public <T> void delete(T entity) {
        super.delete(entity);
        // 执行删除操作配置的sql增强
        this.doDelSql((TBAppraisalApprovalEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        // 执行新增操作配置的sql增强
        this.doAddSql((TBAppraisalApprovalEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        // 执行更新操作配置的sql增强
        this.doUpdateSql((TBAppraisalApprovalEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TBAppraisalApprovalEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TBAppraisalApprovalEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TBAppraisalApprovalEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBAppraisalApprovalEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{t_b_id}", String.valueOf(t.getAppraisalProject().getId()));
        sql = sql.replace("#{project_character}", String.valueOf(t.getProjectCharacter()));
        sql = sql.replace("#{appraisa_condition}", String.valueOf(t.getAppraisaCondition()));
        sql = sql.replace("#{begin_time}", String.valueOf(t.getBeginTime()));
        sql = sql.replace("#{end_time}", String.valueOf(t.getEndTime()));
        sql = sql.replace("#{total_amount}", String.valueOf(t.getTotalAmount()));
        sql = sql.replace("#{appraisa_time}", String.valueOf(t.getAppraisaTime()));
        sql = sql.replace("#{appraisa_address}", String.valueOf(t.getAppraisaAddress()));
        sql = sql.replace("#{contact_userid}", String.valueOf(t.getContactUserid()));
        sql = sql.replace("#{contact_username}", String.valueOf(t.getContactUsername()));
        sql = sql.replace("#{contact_phone}", String.valueOf(t.getContactPhone()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void doAddOrUpdate(TBAppraisalApprovalEntity tBAppraisaApproval, String sendIds) {
        if (StringUtil.isNotEmpty(tBAppraisaApproval.getId())) {
            TBAppraisalApprovalEntity t = this.get(TBAppraisalApprovalEntity.class, tBAppraisaApproval.getId());

            try {
                MyBeanUtils.copyBeanNotNull2Bean(tBAppraisaApproval, t);
                if (StringUtil.isNotEmpty(sendIds)) {
                    if (ApprovalConstant.APPRSTATUS_UNSEND.equals(t.getAuditStatus())
                            || ApprovalConstant.APPRSTATUS_REBUT.equals(t.getAuditStatus())) {
                        t.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
                    }
                }
            } catch (Exception e) {
                throw new BusinessException("拷贝对象出错", e);
            }
            this.updateEntitie(t);
        } else {
            // 初始状态
            if (StringUtil.isNotEmpty(sendIds)) {
                tBAppraisaApproval.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
            } else {
                tBAppraisaApproval.setAuditStatus(ApprovalConstant.APPRSTATUS_UNSEND);
            }

            this.save(tBAppraisaApproval);
        }

        //将发送和接收记录添加业务表主键
        if (StringUtil.isNotEmpty(sendIds)) {
            CriteriaQuery cq = new CriteriaQuery(TPmApprSendLogsEntity.class);
            cq.isNull("apprId");
            cq.add();
            List<TPmApprSendLogsEntity> sendLogs = this.getListByCriteriaQuery(cq, false);
            if (sendLogs != null && sendLogs.size() > 0) {
                for (TPmApprSendLogsEntity sendLog : sendLogs) {
                    if (sendIds.indexOf(sendLog.getId()) != -1) {
                        sendLog.setApprId(tBAppraisaApproval.getId());
                        this.updateEntitie(sendLog);

                        List<TPmApprReceiveLogsEntity> receiveLogs = sendLog.getApprReceiveLogs();
                        if (receiveLogs != null && receiveLogs.size() > 0) {
                            for (TPmApprReceiveLogsEntity receiveLog : receiveLogs) {
                                receiveLog.setApprId(tBAppraisaApproval.getId());
                                this.updateEntitie(receiveLog);
                            }
                        }
                    } else {
                        super.delete(sendLog);
                    }
                }
            }
        }
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_APPRAISAL);
    }

    @Override
    public AjaxJson updateFinishFlag(String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TBAppraisalApprovalEntity t = commonDao.get(TBAppraisalApprovalEntity.class, id);
            if (t != null) {
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(t.getAuditStatus())) {
                    //操作：取消完成流程(流程状态为已完成才能进行该操作)
                    t.setFinishTime(null);
                    t.setFinishUserid(null);
                    t.setFinishUsername(null);
                    t.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);//只有状态是已发送的时候才有完成按钮
                    commonDao.saveOrUpdate(t);
                    j.setSuccess(true);
                    j.setMsg("成功将审批流程状态设置为未完成");
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getAuditStatus())) {
                    //操作：完成流程操作(流程状态为已完成才能进行该操作)
                    CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                    cq.eq("apprId", id);
                    cq.eq("operateStatus", SrmipConstants.NO);
                    cq.eq("validFlag", SrmipConstants.YES);
                    cq.add();
                    List<TPmApprReceiveLogsEntity> tPmContractReceiveLogs = commonDao.getListByCriteriaQuery(cq,
                            false);
                    if (tPmContractReceiveLogs != null && tPmContractReceiveLogs.size() > 0) {
                        j.setSuccess(false);
                        j.setMsg("还有审批意见未处理，确定完成审批流程吗？");
                        j.setObj("1");//失败情况一
                    } else {
                        TSUser user = ResourceUtil.getSessionUserName();
                        //操作：完成流程
                        t.setAuditStatus(ApprovalConstant.APPRSTATUS_FINISH);
                        t.setFinishTime(new Date());
                        t.setFinishUserid(user.getId());
                        t.setFinishUsername(user.getRealName());
                        this.commonDao.saveOrUpdate(t);
                        updateAppraisalProject(t);
                        j.setSuccess(true);
                        j.setMsg("成功将审批流程状态设置为完成");
                        tPmApprLogsService.finishApprLogs(id);
                    }
                } else {
                    //其他人操作改变了finishFlag属性，需要刷新重新操作
                    j.setSuccess(false);
                    j.setMsg("审批流程状态设置失败，请刷新列表再进行操作");
                    j.setObj("2");//失败情况二
                }
            } else {
                j.setSuccess(false);
                j.setMsg("审批流程状态设置失败");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("审批流程状态设置失败");
        }
        return j;
    }

    /**
     * 将未处理的审批记录设置为已处理
     * 
     * @param id
     */


    /**
     * 更新鉴定计划的状态
     */
    private void updateAppraisalProject(TBAppraisalApprovalEntity approvalEntity) {
        TBAppraisalProjectEntity appraisalProject = approvalEntity.getAppraisalProject();
        appraisalProject.setState(ApprovalConstant.APPRAISAL_PROJECT_APPROVAL_FINISH);
        this.commonDao.updateEntitie(appraisalProject);
    }

    @Override
    public AjaxJson updateFinishFlag2(String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TBAppraisalApprovalEntity t = commonDao.get(TBAppraisalApprovalEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getAuditStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setAuditStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
                updateAppraisalProject(t);
                j.setSuccess(true);
                j.setMsg("设置流程审批状态成功！");
                tPmApprLogsService.finishApprLogs(id);
            } else {
                j.setSuccess(false);
                j.setMsg("审批流程状态设置失败，清刷新列表再进行操作");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("审批流程状态设置失败");
        }
        return j;
    }
}