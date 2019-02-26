package com.kingtake.project.service.impl.resultreward;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
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
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.resultreward.TBResultRewardEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.resultreward.TBResultRewardServiceI;

@Service("tBResultRewardService")
@Transactional
public class TBResultRewardServiceImpl extends CommonServiceImpl implements TBResultRewardServiceI, ProjectListServiceI,ApprFlowServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TBResultRewardEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TBResultRewardEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TBResultRewardEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TBResultRewardEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TBResultRewardEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TBResultRewardEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBResultRewardEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{reward_name}", String.valueOf(t.getRewardName()));
        sql = sql.replace("#{finish_userid}", String.valueOf(t.getFinishUserid()));
        sql = sql.replace("#{finish_username}", String.valueOf(t.getFinishUsername()));
        sql = sql.replace("#{finish_departid}", String.valueOf(t.getFinishDepartid()));
        sql = sql.replace("#{finish_departname}", String.valueOf(t.getFinishDepartname()));
        sql = sql.replace("#{summary}", String.valueOf(t.getSummary()));
        sql = sql.replace("#{innovation_point}", String.valueOf(t.getInnovationPoint()));
        sql = sql.replace("#{report_reward_level}", String.valueOf(t.getReportRewardLevel()));
        sql = sql.replace("#{report_level}", String.valueOf(t.getReportLevel()));
        sql = sql.replace("#{task_source}", String.valueOf(t.getTaskSource()));
        sql = sql.replace("#{invested_amount}", String.valueOf(t.getInvestedAmount()));
        sql = sql.replace("#{contacts}", String.valueOf(t.getContacts()));
        sql = sql.replace("#{contact_phone}", String.valueOf(t.getContactPhone()));
        sql = sql.replace("#{hgd_devote}", String.valueOf(t.getHgdDevote()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }


    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_RESULT_REWARD);
    }


    /**
     * 更新审批状态
     */
    @Override
    public void updateFinishFlag(String id) {

        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TBResultRewardEntity t = commonDao.get(TBResultRewardEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getFinishFlag())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setFinishFlag(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setOperateFinishUserid(user.getId());
                t.setOperateFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
            }
        }
    }

    @Override
    public void deleteAddAttach(TBResultRewardEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

    @Override
    public void doBack(String id) {
        TBResultRewardEntity t = commonDao.get(TBResultRewardEntity.class, id);
        //将审批状态修改为被驳回
        t.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
        
    }

    @Override
    public void doPass(String id) {
        TBResultRewardEntity entity = this.get(TBResultRewardEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getFinishFlag())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getFinishFlag())) {
              entity.setFinishFlag(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
        
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TBResultRewardEntity t = commonDao.get(TBResultRewardEntity.class, id);
        if(t!=null){
            appName = t.getRewardName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TBResultRewardEntity entity = this.get(TBResultRewardEntity.class, id);
        if (entity != null) {
              entity.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
        
    }

}