package com.kingtake.project.service.impl.change;

import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.change.TBPmChangeProjectmanagerEntity;
import com.kingtake.project.service.change.TBPmChangeProjectmanagerServiceI;

@Service("tBPmChangeProjectmanagerService")
@Transactional
public class TBPmChangeProjectmanagerServiceImpl extends CommonServiceImpl implements TBPmChangeProjectmanagerServiceI {

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TBPmChangeProjectmanagerEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TBPmChangeProjectmanagerEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TBPmChangeProjectmanagerEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TBPmChangeProjectmanagerEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TBPmChangeProjectmanagerEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TBPmChangeProjectmanagerEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBPmChangeProjectmanagerEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{project_id}", String.valueOf(t.getProject()));
        sql = sql.replace("#{before_project_manager}", String.valueOf(t.getBeforeProjectManager()));
        sql = sql.replace("#{after_project_manager}", String.valueOf(t.getAfterProjectManager()));
        sql = sql.replace("#{change_reason}", String.valueOf(t.getChangeReason()));
        sql = sql.replace("#{change_according}", String.valueOf(t.getChangeAccording()));
        sql = sql.replace("#{change_time}", String.valueOf(t.getChangeTime()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{bpm_status}", String.valueOf(t.getBpmStatus()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void deleteAddAttach(TBPmChangeProjectmanagerEntity t) {
        this.delAttachementByBid(t.getId());
        this.commonDao.delete(t);
    }
}