package com.kingtake.zscq.service.impl.zlzz;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ZlConstants;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.entity.zlzz.TZZlzzEntity;
import com.kingtake.zscq.service.zlzz.TZZlzzServiceI;

@Service("tZZlzzService")
@Transactional
public class TZZlzzServiceImpl extends CommonServiceImpl implements TZZlzzServiceI {

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
    public boolean doAddSql(TZZlzzEntity t) {
        return false;
    }

    @Override
    public boolean doUpdateSql(TZZlzzEntity t) {
        return false;
    }

    @Override
    public boolean doDelSql(TZZlzzEntity t) {
        return false;
    }

    /**
     * 保存转化应用
     */
    @Override
    public void saveZlzz(TZZlzzEntity zlzz) {
        try {
            if (StringUtils.isEmpty(zlzz.getId())) {
                this.commonDao.save(zlzz);
                if (StringUtils.isNotEmpty(zlzz.getZlsqId())) {
                    TZZlsqEntity zlsq = this.commonDao.get(TZZlsqEntity.class, zlzz.getZlsqId());
                    if ("1".equals(zlzz.getZzzt())) {
                        zlsq.setZlzt(ZlConstants.ZLZT_ZZ);//专利状态改为终止
                    }else{
                        zlsq.setZlzt(ZlConstants.ZLZT_ZSQ);//专利状态改为授权
                    }
                    this.commonDao.updateEntitie(zlsq);
                }
            } else {
                TZZlzzEntity t = this.commonDao.get(TZZlzzEntity.class, zlzz.getId());
                MyBeanUtils.copyBeanNotNull2Bean(zlzz, t);
                this.commonDao.saveOrUpdate(t);
            }
        } catch (Exception e) {
            throw new BusinessException("保存终止状态失败！", e);
        }
    }

}