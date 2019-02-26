package com.kingtake.zscq.service.impl.zhyy;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.zscq.entity.zhyy.TZZhyyEntity;
import com.kingtake.zscq.service.zhyy.TZZhyyServiceI;

@Service("tZZhyyService")
@Transactional
public class TZZhyyServiceImpl extends CommonServiceImpl implements TZZhyyServiceI {

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

    /**
     * 保存转化应用
     */
    @Override
    public void saveZhyy(TZZhyyEntity zhyy) {
        try {
            if (StringUtils.isEmpty(zhyy.getId())) {
                zhyy.setQrzt("0");//已生成
                this.commonDao.save(zhyy);
            } else {
                TZZhyyEntity t = this.commonDao.get(TZZhyyEntity.class, zhyy.getId());
                MyBeanUtils.copyBeanNotNull2Bean(zhyy, t);
                this.commonDao.saveOrUpdate(t);
            }
        } catch (Exception e) {
            throw new BusinessException("保存转化应用失败！");
        }
    }

    /**
     * 先删除附件，再删数据
     */
    @Override
    public void delZhyy(TZZhyyEntity zhyy) {
        this.delAttachementByBid(zhyy.getFjbm());
        this.commonDao.delete(zhyy);
    }

}