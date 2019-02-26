package com.kingtake.officeonline.service;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

public interface TOOfficeOnlineFilesServiceI extends CommonService {

    public <T> void delete(T entity);

    public <T> Serializable save(T entity);

    public <T> void saveOrUpdate(T entity);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOOfficeOnlineFilesEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOOfficeOnlineFilesEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOOfficeOnlineFilesEntity t);

    public boolean deleteFileAndEntity(TOOfficeOnlineFilesEntity t);
}
