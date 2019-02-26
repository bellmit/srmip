package com.kingtake.zscq.service.impl.sqwj;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.zscq.entity.sqwj.TZSqwjEntity;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.service.sqwj.TZSqwjServiceI;

@Service("tZSqwjService")
@Transactional
public class TZSqwjServiceImpl extends CommonServiceImpl implements TZSqwjServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Autowired
    private CommonMessageServiceI commonMessageService;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TZZlsqEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TZZlsqEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TZZlsqEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TZZlsqEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TZZlsqEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TZZlsqEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TZZlsqEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{gdh}", String.valueOf(t.getGdh()));
        sql = sql.replace("#{wcdw}", String.valueOf(t.getWcdw()));
        sql = sql.replace("#{lx}", String.valueOf(t.getLx()));
        sql = sql.replace("#{glxm}", String.valueOf(t.getGlxm()));
        sql = sql.replace("#{mc}", String.valueOf(t.getMc()));
        sql = sql.replace("#{fmr}", String.valueOf(t.getFmr()));
        sql = sql.replace("#{dyfmrsfzh}", String.valueOf(t.getDyfmrsfzh()));
        sql = sql.replace("#{dljg_id}", String.valueOf(t.getDljgId()));
        sql = sql.replace("#{lxr}", String.valueOf(t.getLxr()));
        sql = sql.replace("#{lxrdh}", String.valueOf(t.getLxrdh()));
        sql = sql.replace("#{bz}", String.valueOf(t.getBz()));
        sql = sql.replace("#{fjbm}", String.valueOf(t.getFjbm()));
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
    public AjaxJson updateFinishFlag(String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TZZlsqEntity t = commonDao.get(TZZlsqEntity.class, id);
            if (t != null) {
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(t.getApprStatus())) {
                    //操作：取消完成流程(流程状态为已完成才能进行该操作)
                    t.setApprFinishTime(null);
                    t.setApprStatus(ApprovalConstant.APPRSTATUS_SEND);//只有状态是已发送的时候才有完成按钮
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
                    j.setMsg("成功将专利申请审批流程状态设置为未完成");
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getApprStatus())) {
                    //操作：完成流程操作(流程状态为已完成才能进行该操作)
                    CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                    cq.eq("apprId", id);
                    cq.eq("operateStatus", SrmipConstants.NO);
                    cq.eq("validFlag", SrmipConstants.YES);
                    cq.add();
                    List<TPmApprReceiveLogsEntity> tPmContractReceiveLogs = commonDao.getListByCriteriaQuery(cq, false);
                    if (tPmContractReceiveLogs != null && tPmContractReceiveLogs.size() > 0) {
                        j.setSuccess(false);
                        j.setMsg("该流程还有审批意见未处理，确定完成审批流程吗？");
                        j.setObj("1");//失败情况一
                    } else {
                        TSUser user = ResourceUtil.getSessionUserName();
                        //操作：完成流程
                        t.setApprStatus(ApprovalConstant.APPRSTATUS_FINISH);
                        t.setApprFinishTime(new Date());
                        t.setApprFinishUserId(user.getId());
                        t.setApprFinishUserName(user.getRealName());
                        commonDao.saveOrUpdate(t);
                        j.setSuccess(true);
                        j.setMsg("成功将专利申请审批流程状态设置为完成");
                        tPmApprLogsService.finishApprLogs(id);
                    }
                } else {
                    //其他人操作改变了finishFlag属性，需要刷新重新操作
                    j.setSuccess(false);
                    j.setMsg("专利申请流程状态设置失败，清刷新列表再进行操作");
                    j.setObj("2");//失败情况二
                }
            }
        } else {
            j.setSuccess(false);
            j.setMsg("专利申请流程状态设置失败");
        }
        return j;
    }

    @Override
    public AjaxJson updateFinishFlag2(String id) {
        AjaxJson j = new AjaxJson();

        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TZZlsqEntity t = commonDao.get(TZZlsqEntity.class, id);
            if (t != null) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setApprStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setApprFinishTime(new Date());
                t.setApprFinishUserId(user.getId());
                t.setApprFinishUserName(user.getRealName());
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
                j.setMsg("成功将专利申请流程状态设置为完成");
                tPmApprLogsService.finishApprLogs(id);
            }
        } else {
            j.setSuccess(false);
            j.setMsg("专利申请流程状态设置失败");
        }

        return j;
    }

    @Override
    public void delZlsq(TZZlsqEntity tZZlsq) {
        this.delAttachementByBid(tZZlsq.getFjbm());
        tZZlsq = commonDao.getEntity(TZZlsqEntity.class, tZZlsq.getId());
        this.commonDao.delete(tZZlsq);
    }

    @Override
    public void doSubmit(TZSqwjEntity sqwj) {
        TSUser user = ResourceUtil.getSessionUserName();
        sqwj = this.commonDao.get(TZSqwjEntity.class, sqwj.getId());
        sqwj.setApplyStatus(SrmipConstants.SUBMIT_YES);//已提交
        sqwj.setSubmitTime(DateUtils.getDate());
        //sqwj.setCheckUserId(receiverId);
        //sqwj.setCheckUserName(receiverName);
        this.commonDao.updateEntitie(sqwj);
        //        String zlsqId = sqwj.getZlsqId();
        //        TZZlsqEntity zlsq = this.commonDao.get(TZZlsqEntity.class, zlsqId);
        //        String messageType = "专利申请提交文件";
        //        String messageTitle = "专利申请提交文件";
        //        String messageContent = "您有一批专利申请文件需要审查！<a href=\"#\" style=\"color:skyblue;\" onclick='addTab(\"专利申请文件审查\",\"tZSqwjController.do?goApplyAuditTab\");'>"
        //                + zlsq.getMc() + "</a> ";
        //        commonMessageService.sendMessage(receiverId, messageType, messageTitle, messageContent);
    }
}