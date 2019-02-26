package com.kingtake.project.service.impl.apprcontractcheck;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprcontractcheck.TPmContractCheckEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprcontractcheck.TPmContractCheckServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.impl.appr.ApprServiceImpl;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmContractCheckService")
@Transactional
public class TPmContractCheckServiceImpl extends ApprServiceImpl implements TPmContractCheckServiceI,
        ProjectListServiceI,ApprFlowServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TPmContractCheckEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TPmContractCheckEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TPmContractCheckEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TPmContractCheckEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TPmContractCheckEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TPmContractCheckEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TPmContractCheckEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{secret_degree}", String.valueOf(t.getSecretDegree()));
        sql = sql.replace("#{contract_code}", String.valueOf(t.getContractCode()));
        sql = sql.replace("#{accounting_code}", String.valueOf(t.getAccountingCode()));
        sql = sql.replace("#{project_name}", String.valueOf(t.getProjectName()));
        sql = sql.replace("#{contract_id}", String.valueOf(t.getContractId()));
        sql = sql.replace("#{contract_name}", String.valueOf(t.getContractName()));
        sql = sql.replace("#{duty_departid}", String.valueOf(t.getDutyDepartid()));
        sql = sql.replace("#{duty_departname}", String.valueOf(t.getDutyDepartname()));
        sql = sql.replace("#{contract_second_party}", String.valueOf(t.getContractSecondParty()));
        sql = sql.replace("#{check_time}", String.valueOf(t.getCheckTime()));
        sql = sql.replace("#{contract_amount}", String.valueOf(t.getContractAmount()));
        sql = sql.replace("#{contract_signing_time}", String.valueOf(t.getContractSigningTime()));
        sql = sql.replace("#{begin_time}", String.valueOf(t.getBeginTime()));
        sql = sql.replace("#{end_time}", String.valueOf(t.getEndTime()));
        sql = sql.replace("#{paid_money}", String.valueOf(t.getPaidMoney()));
        sql = sql.replace("#{wait_money}", String.valueOf(t.getWaitMoney()));
        sql = sql.replace("#{organization_unitid}", String.valueOf(t.getOrganizationUnitid()));
        sql = sql.replace("#{organization_unitname}", String.valueOf(t.getOrganizationUnitname()));
        sql = sql.replace("#{contract_target}", String.valueOf(t.getContractTarget()));
        sql = sql.replace("#{research_result}", String.valueOf(t.getResearchResult()));
        sql = sql.replace("#{memo}", String.valueOf(t.getMemo()));
        sql = sql.replace("#{contract_type}", String.valueOf(t.getContractType()));
        sql = sql.replace("#{operation_status}", String.valueOf(t.getOperationStatus()));
        sql = sql.replace("#{finish_time}", String.valueOf(t.getFinishTime()));
        sql = sql.replace("#{back_id}", String.valueOf(t.getBackLog().getId()));
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
    public boolean updateOperateState(TPmContractCheckEntity t) {
        if (ApprovalConstant.APPRSTATUS_REBUT.equals(t.getOperationStatus())) {
            t.setOperationStatus(ApprovalConstant.APPRSTATUS_UNSEND);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_CHECK);
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        // TODO Auto-generated method stub

    }

    @Override
    public AjaxJson updateOperateStatus(String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmContractCheckEntity t = commonDao.get(TPmContractCheckEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_FINISH.equals(t.getOperationStatus())) {
                //操作：取消完成流程(流程状态为已完成才能进行该操作)
                t.setFinishTime(null);
                t.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);//只有状态是已发送的时候才有完成按钮
                commonDao.saveOrUpdate(t);
                CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                cq.eq("apprId", t.getId());
                cq.eq("operateStatus", SrmipConstants.NO);
                cq.eq("validFlag", SrmipConstants.NO);
                cq.add();
                List<TPmApprReceiveLogsEntity> list = commonDao.getListByCriteriaQuery(cq, false);
                if (list.size() > 0) {
                    for (TPmApprReceiveLogsEntity r : list) {
                        r.setValidFlag(SrmipConstants.YES);//取消完成操作时将未处理的接收记录置为有效
                        commonDao.updateEntitie(r);
                    }
                }
                j.setSuccess(true);
                j.setMsg("成功将合同验收报告审批流程状态设置为未完成");
            } else if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperationStatus())) {
                //操作：完成流程操作(流程状态为已完成才能进行该操作)
                CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                cq.eq("apprId", id);
                cq.eq("operateStatus", SrmipConstants.NO);
                cq.eq("validFlag", SrmipConstants.YES);
                cq.add();
                List<TPmApprReceiveLogsEntity> tPmFundsReceiveLogs = commonDao.getListByCriteriaQuery(cq, false);
                if (tPmFundsReceiveLogs != null && tPmFundsReceiveLogs.size() > 0) {
                    j.setSuccess(false);
                    j.setMsg("该合同验收报告还有审批意见未处理，确定完成审批流程吗？");
                    j.setObj("1");//失败情况一
                } else {
                    TSUser user = ResourceUtil.getSessionUserName();
                    //操作：完成流程
                    t.setOperationStatus(ApprovalConstant.APPRSTATUS_FINISH);
                    t.setFinishTime(new Date());
                    t.setFinishUserid(user.getId());
                    t.setFinishUsername(user.getRealName());
                    commonDao.saveOrUpdate(t);
                    j.setSuccess(true);
                    j.setMsg("成功将合同验收报告审批流程状态设置为完成");
                    this.tPmApprLogsService.finishApprLogs(id);
                }
            } else {
                //其他人操作改变了finishFlag属性，需要刷新重新操作
                j.setSuccess(false);
                j.setMsg("合同验收报告审批流程状态设置失败，请刷新列表再进行操作");
                j.setObj("2");//失败情况二
            }
        } else {
            j.setSuccess(false);
            j.setMsg("合同验收报告审批流程状态设置失败");
        }
        return j;
    }

    @Override
    public AjaxJson updateOperateStatus2(String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmContractCheckEntity t = commonDao.get(TPmContractCheckEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperationStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setOperationStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
                CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                cq.eq("apprId", t.getId());
                cq.eq("operateStatus", SrmipConstants.NO);
                cq.eq("validFlag", SrmipConstants.YES);
                cq.add();
                List<TPmApprReceiveLogsEntity> list = commonDao.getListByCriteriaQuery(cq, false);
                if (list.size() > 0) {
                    for (TPmApprReceiveLogsEntity r : list) {
                        r.setValidFlag(SrmipConstants.NO);//完成操作时将未处理的接收记录置为无效
                        commonDao.updateEntitie(r);
                    }
                }
                j.setSuccess(true);
                j.setMsg("成功将合同验收报告审批流程状态设置为完成");
                this.tPmApprLogsService.finishApprLogs(id);
            } else {
                j.setSuccess(false);
                j.setMsg("合同验收报告审批流程状态设置失败，清刷新列表再进行操作");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("进账合同审批流程状态设置失败");
        }

        return j;
    }

    @Override
    public void deleteAddAttach(TPmContractCheckEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
    
    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmContractCheckEntity t = commonDao.get(TPmContractCheckEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperationStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setOperationStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
            } 
        } 
        
    }

    @Override
    public void doBack(String id) {
        TPmContractCheckEntity t = commonDao.get(TPmContractCheckEntity.class, id);
        //将审批状态修改为被驳回
        t.setOperationStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
    }

    @Override
    public void doPass(String id) {
        TPmContractCheckEntity entity = this.get(TPmContractCheckEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getOperationStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getOperationStatus())) {
              entity.setOperationStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
        
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TPmContractCheckEntity t = commonDao.get(TPmContractCheckEntity.class, id);
        if(t!=null){
            appName = t.getContractName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmContractCheckEntity entity = this.get(TPmContractCheckEntity.class, id);
        if (entity != null) {
              entity.setOperationStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
        
    }
}