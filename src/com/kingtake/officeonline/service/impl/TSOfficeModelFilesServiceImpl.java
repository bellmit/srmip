package com.kingtake.officeonline.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.officeonline.entity.TSOfficeModelFilesEntity;
import com.kingtake.officeonline.service.TSOfficeModelFilesServiceI;

@Service("tSOfficeModelFilesService")
@Transactional
public class TSOfficeModelFilesServiceImpl extends CommonServiceImpl implements TSOfficeModelFilesServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TSOfficeModelFilesEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TSOfficeModelFilesEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TSOfficeModelFilesEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TSOfficeModelFilesEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TSOfficeModelFilesEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TSOfficeModelFilesEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TSOfficeModelFilesEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{codedetailid}", String.valueOf(t.getCodeDetailId()));
        sql = sql.replace("#{extend}", String.valueOf(t.getExtend()));
        sql = sql.replace("#{realpath}", String.valueOf(t.getRealpath()));
        sql = sql.replace("#{templatefileid}", String.valueOf(t.getTemplateFileId()));
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
    public boolean deleteTemplateFile(TSOfficeModelFilesEntity t) {
        // [1].删除附件

        // 附件相对路径
        String realpath = t.getRealpath();

        // 获取部署项目绝对地址
        String realPath = new File(ContextHolderUtils.getSession().getServletContext().getRealPath("/")).getParent()
                + "/";
        FileUtils.delete(realPath + realpath);
        // [2].删除数据
        delete(t);

        return true;
    }

}