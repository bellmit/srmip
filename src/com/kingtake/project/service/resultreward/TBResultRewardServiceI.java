package com.kingtake.project.service.resultreward;

import java.io.Serializable;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.resultreward.TBResultRewardEntity;

public interface TBResultRewardServiceI extends CommonService {

    @Override
    public <T> void delete(T entity);

    @Override
    public <T> Serializable save(T entity);

    @Override
    public <T> void saveOrUpdate(T entity);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TBResultRewardEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TBResultRewardEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TBResultRewardEntity t);


    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TBResultRewardEntity t);
}
