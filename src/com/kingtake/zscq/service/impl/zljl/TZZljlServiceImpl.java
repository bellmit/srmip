package com.kingtake.zscq.service.impl.zljl;

import java.io.Serializable;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.zscq.entity.zljl.TZZljlEntity;
import com.kingtake.zscq.service.zljl.TZZljlServiceI;

@Service("tZZljlService")
@Transactional
public class TZZljlServiceImpl extends CommonServiceImpl implements TZZljlServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
    }


    @Override
    public boolean doAddSql(TZZljlEntity t) {
        return false;
    }

    @Override
    public boolean doUpdateSql(TZZljlEntity t) {
        return false;
    }

    @Override
    public boolean doDelSql(TZZljlEntity t) {
        return false;
    }

}