package com.kingtake.office.service.impl.approval;

import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.approval.TOApprovalProjectSummaryEntity;
import com.kingtake.office.service.approval.TOApprovalProjectSummaryServiceI;

@Service("tOApprovalProjectSummaryService")
@Transactional
public class TOApprovalProjectSummaryServiceImpl extends CommonServiceImpl implements TOApprovalProjectSummaryServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TOApprovalProjectSummaryEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TOApprovalProjectSummaryEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TOApprovalProjectSummaryEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOApprovalProjectSummaryEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOApprovalProjectSummaryEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOApprovalProjectSummaryEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TOApprovalProjectSummaryEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{project_id}", String.valueOf(t.getProjectId()));
        sql = sql.replace("#{project_name}", String.valueOf(t.getProjectName()));
        sql = sql.replace("#{project_no}", String.valueOf(t.getProjectNo()));
        sql = sql.replace("#{project_manager_id}", String.valueOf(t.getProjectManagerId()));
        sql = sql.replace("#{project_manager}", String.valueOf(t.getProjectManager()));
        sql = sql.replace("#{begin_date}", String.valueOf(t.getBeginDate()));
        sql = sql.replace("#{end_date}", String.valueOf(t.getEndDate()));
        sql = sql.replace("#{project_type}", String.valueOf(t.getProjectType()));
        sql = sql.replace("#{fee_type}", String.valueOf(t.getFeeType()));
        sql = sql.replace("#{developer_depart}", String.valueOf(t.getDevDepart()));
        sql = sql.replace("#{manage_depart}", String.valueOf(t.getManageDepart()));
        sql = sql.replace("#{project_source}", String.valueOf(t.getProjectSource()));
        sql = sql.replace("#{according_num}", String.valueOf(t.getAccordingNum()));
        sql = sql.replace("#{all_fee}", String.valueOf(t.getAllFee()));
        sql = sql.replace("#{approval_id}", String.valueOf(t.getApprovalId()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }
}