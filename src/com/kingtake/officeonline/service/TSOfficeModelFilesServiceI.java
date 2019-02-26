package com.kingtake.officeonline.service;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.officeonline.entity.TSOfficeModelFilesEntity;

public interface TSOfficeModelFilesServiceI extends CommonService {

    public <T> void delete(T entity);

    public <T> Serializable save(T entity);

    public <T> void saveOrUpdate(T entity);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TSOfficeModelFilesEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TSOfficeModelFilesEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TSOfficeModelFilesEntity t);

    /**
     * 删除服务器上模板文件
     * 
     * @param realPath
     *            相对路径
     * @return
     */
    public boolean deleteTemplateFile(TSOfficeModelFilesEntity t);
}
