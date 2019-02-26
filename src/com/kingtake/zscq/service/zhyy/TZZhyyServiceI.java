package com.kingtake.zscq.service.zhyy;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.zscq.entity.zhyy.TZZhyyEntity;

public interface TZZhyyServiceI extends CommonService {

    public <T> void delete(T entity);

    public <T> Serializable save(T entity);

    public <T> void saveOrUpdate(T entity);

    /**
     * 保存转化应用
     * 
     * @param zhyy
     */
    public void saveZhyy(TZZhyyEntity zhyy);

    /**
     * 删除转化应用
     * 
     * @param zhyy
     */
    public void delZhyy(TZZhyyEntity zhyy);

}
