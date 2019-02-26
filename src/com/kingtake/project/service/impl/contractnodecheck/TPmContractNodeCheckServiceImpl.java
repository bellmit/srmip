package com.kingtake.project.service.impl.contractnodecheck;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.dao.TPmProjectNodeCheckDao;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.contractnodecheck.TPmContractNodeCheckEntity;
import com.kingtake.project.entity.payapply.TPmPayApplyEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.contractnodecheck.TPmContractNodeCheckServiceI;

@Service("tPmContractNodeCheckService")
@Transactional
public class TPmContractNodeCheckServiceImpl extends CommonServiceImpl implements TPmContractNodeCheckServiceI {
    @Autowired
    private TPmProjectNodeCheckDao tPmProjectNodeCheckDao;

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractNodeCheckEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractNodeCheckEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractNodeCheckEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmContractNodeCheckEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmContractNodeCheckEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmContractNodeCheckEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractNodeCheckEntity t){
 		sql  = sql.replace("#{finish_time}",String.valueOf(t.getFinishTime()));
 		sql  = sql.replace("#{finish_userid}",String.valueOf(t.getFinishUserid()));
 		sql  = sql.replace("#{finish_username}",String.valueOf(t.getFinishUsername()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{contract_node_id}",String.valueOf(t.getContractNodeId()));
 		sql  = sql.replace("#{contact_num}",String.valueOf(t.getContactNum()));
 		sql  = sql.replace("#{accounting_code}",String.valueOf(t.getAccountingCode()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{secret_degree}",String.valueOf(t.getSecretDegree()));
 		sql  = sql.replace("#{contact_id}",String.valueOf(t.getContactId()));
 		sql  = sql.replace("#{contract_name}",String.valueOf(t.getContractName()));
 		sql  = sql.replace("#{duty_departid}",String.valueOf(t.getDutyDepartid()));
 		sql  = sql.replace("#{duty_departname}",String.valueOf(t.getDutyDepartname()));
 		sql  = sql.replace("#{contact_second_party}",String.valueOf(t.getContactSecondParty()));
 		sql  = sql.replace("#{contact_amount}",String.valueOf(t.getContactAmount()));
 		sql  = sql.replace("#{contract_signing_time}",String.valueOf(t.getContractSigningTime()));
 		sql  = sql.replace("#{node_amount}",String.valueOf(t.getNodeAmount()));
 		sql  = sql.replace("#{node_time}",String.valueOf(t.getNodeTime()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{check_time}",String.valueOf(t.getCheckTime()));
 		sql  = sql.replace("#{organization_unitid}",String.valueOf(t.getOrganizationUnitid()));
 		sql  = sql.replace("#{organization_unitname}",String.valueOf(t.getOrganizationUnitname()));
 		sql  = sql.replace("#{node_content}",String.valueOf(t.getNodeContent()));
 		sql  = sql.replace("#{node_result}",String.valueOf(t.getNodeResult()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{contract_type}",String.valueOf(t.getContractType()));
 		sql  = sql.replace("#{operation_status}",String.valueOf(t.getOperationStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public List<Map<String, Object>> getProjectNodeApprReceiveList(int start, int end, Map<String, Object> param){
        return tPmProjectNodeCheckDao.getProjectNodeApprReceiveList(start, end, param);
    }

    @Override
    public Integer getProjectNodeCheckCount(Map<String, Object> param){
        return tPmProjectNodeCheckDao.getProjectNodeCheckCount(param);
    }

    @Override
    public AjaxJson updateOperateStatus(String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmContractNodeCheckEntity t = commonDao.get(TPmContractNodeCheckEntity.class, id);
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
                    tPmApprLogsService.finishApprLogs(id);
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
            TPmContractNodeCheckEntity t = commonDao.get(TPmContractNodeCheckEntity.class, id);
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
                tPmApprLogsService.finishApprLogs(id);
            } else {
                j.setSuccess(false);
                j.setMsg("合同验收报告审批流程状态设置失败，清刷新列表再进行操作");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("合同验收报告审批流程状态设置失败");
        }

        return j;
    }

    @Override
    public AjaxJson updatePayApplyOperateStatus(String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmPayApplyEntity t = commonDao.get(TPmPayApplyEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_FINISH.equals(t.getOperateStatus())) {
                //操作：取消完成流程(流程状态为已完成才能进行该操作)
                t.setFinishTime(null);
                t.setOperateStatus(ApprovalConstant.APPRSTATUS_SEND);//只有状态是已发送的时候才有完成按钮
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
                j.setMsg("成功将支付申请审批流程状态设置为未完成");
            } else if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperateStatus())) {
                //操作：完成流程操作(流程状态为已完成才能进行该操作)
                CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                cq.eq("apprId", id);
                cq.eq("operateStatus", SrmipConstants.NO);
                cq.eq("validFlag", SrmipConstants.YES);
                cq.add();
                List<TPmApprReceiveLogsEntity> tPmFundsReceiveLogs = commonDao.getListByCriteriaQuery(cq, false);
                if (tPmFundsReceiveLogs != null && tPmFundsReceiveLogs.size() > 0) {
                    j.setSuccess(false);
                    j.setMsg("该支付申请还有审批意见未处理，确定完成审批流程吗？");
                    j.setObj("1");//失败情况一
                } else {
                    TSUser user = ResourceUtil.getSessionUserName();
                    //操作：完成流程
                    t.setOperateStatus(ApprovalConstant.APPRSTATUS_FINISH);
                    t.setFinishTime(new Date());
                    t.setFinishUserid(user.getId());
                    t.setFinishUsername(user.getRealName());
                    commonDao.saveOrUpdate(t);
                    j.setSuccess(true);
                    j.setMsg("成功将支付申请审批流程状态设置为完成");
                    tPmApprLogsService.finishApprLogs(id);
                }
            } else {
                //其他人操作改变了finishFlag属性，需要刷新重新操作
                j.setSuccess(false);
                j.setMsg("支付申请审批流程状态设置失败，请刷新列表再进行操作");
                j.setObj("2");//失败情况二
            }
        } else {
            j.setSuccess(false);
            j.setMsg("支付申请审批流程状态设置失败");
        }
        return j;
    }

    @Override
    public AjaxJson updatePayApplyOperateStatus2(String id) {
        AjaxJson j = new AjaxJson();

        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmPayApplyEntity t = commonDao.get(TPmPayApplyEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getOperateStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setOperateStatus(ApprovalConstant.APPRSTATUS_FINISH);
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
                j.setMsg("成功将支付申请流程状态设置为完成");
                tPmApprLogsService.finishApprLogs(id);
            } else {
                j.setSuccess(false);
                j.setMsg("支付申请审批流程状态设置失败，清刷新列表再进行操作");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("支付申请审批流程状态设置失败");
        }

        return j;
    }

    @Override
    public void deleteAddAttach(TPmContractNodeCheckEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

}