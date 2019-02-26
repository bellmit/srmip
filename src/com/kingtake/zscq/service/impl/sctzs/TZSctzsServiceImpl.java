package com.kingtake.zscq.service.impl.sctzs;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ZlConstants;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.zscq.entity.sctzs.TZSctzsEntity;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.service.sctzs.TZSctzsServiceI;

@Service("tZSctzsService")
@Transactional
public class TZSctzsServiceImpl extends CommonServiceImpl implements TZSctzsServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Autowired
    private CommonMessageServiceI commonMessageService;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TZSctzsEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TZSctzsEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TZSctzsEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TZSctzsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TZSctzsEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TZSctzsEntity t) {
        return true;
    }

    @Override
    public void saveSctzs(String opt, TZSctzsEntity tZSctzs) {
        try {
            if (StringUtils.isEmpty(tZSctzs.getId())) {
                if (tZSctzs.getYqhfsj() != null) {
                    tZSctzs.setQrzt("1");//待回复
                }
                this.commonDao.save(tZSctzs);
                if (StringUtils.isNotEmpty(tZSctzs.getZlsqId())) {
                    TZZlsqEntity zlsq = this.commonDao.get(TZZlsqEntity.class, tZSctzs.getZlsqId());
                    switch (tZSctzs.getTzlx()) {//录入时即修改状态
                    case ZlConstants.ZLSCTZSLX_FYJHSP:
                        changeZlzt(zlsq, ZlConstants.ZLZT_FYJHSP);//费用减缓审批
                        break;
                    case ZlConstants.ZLSCTZSLX_BZ:
                        changeZlzt(zlsq, ZlConstants.ZLZT_BZ);//补正
                        break;
                    case ZlConstants.ZLSCTZSLX_CSHG:
                        changeZlzt(zlsq, ZlConstants.ZLZT_CSHG);//初审合格
                        break;
                    case ZlConstants.ZLSCTZSLX_JRSS:
                        changeZlzt(zlsq, ZlConstants.ZLZT_JRSS);//进入实审
                        break;
                    case ZlConstants.ZLSCTZSLX_YS:
                        changeZlzt(zlsq, ZlConstants.ZLZT_YS);//一审
                        break;
                    case ZlConstants.ZLSCTZSLX_ES:
                        changeZlzt(zlsq, ZlConstants.ZLZT_ES);//二审
                        break;
                    case ZlConstants.ZLSCTZSLX_BH:
                        changeZlzt(zlsq, ZlConstants.ZLZT_BH);//驳回
                        break;
                    case ZlConstants.ZLSCTZSLX_SC:
                        changeZlzt(zlsq, ZlConstants.ZLZT_SC);//试撤
                        break;
                    case ZlConstants.ZLSCTZSLX_JF:
                        changeZlzt(zlsq, ZlConstants.ZLZT_JF);//缴费
                        break;
                    }
                    this.commonDao.updateEntitie(zlsq);
                }
            } else {
                TZSctzsEntity t = this.commonDao.get(TZSctzsEntity.class, tZSctzs.getId());
                t.setSffq(tZSctzs.getSffq());
                if ("reply".equals(opt)) {
                    if ("1".equals(tZSctzs.getSffq())) {//放弃，则更新放弃原因
                        t.setFqyy(tZSctzs.getFqyy());
                    } else {//不放弃，则更新附件
                        t.setScyj(tZSctzs.getScyj());
                    }
                    t.setQrzt("2");//已回复
                } else {
                    MyBeanUtils.copyBeanNotNull2Bean(tZSctzs, t);
                    if (tZSctzs.getYqhfsj() == null) {
                        t.setYqhfsj(null);//手动置空
                        t.setQrzt(null);
                    } else if (t.getQrzt() == null) {
                        t.setQrzt("1");
                    }
                    if (StringUtils.isNotEmpty(t.getSffq())) {
                        t.setQrzt("2");//已回复
                    }
                }
                this.commonDao.saveOrUpdate(t);
            }
        } catch (Exception e) {
            throw new BusinessException("保存审查通知书失败！");
        }
    }

    /**
     * 
     * 修改专利状态
     * 
     * @param zlsq
     * @param zlzt
     */
    public void changeZlzt(TZZlsqEntity zlsq, String zlzt) {
        String zlztc = zlsq.getZlzt();
        if (StringUtils.isEmpty(zlztc)) {
            zlztc = "0";
        }
        int o = Integer.valueOf(zlztc);
        int n = Integer.valueOf(zlzt);
        if (n > o) {
            zlsq.setZlzt(zlzt);
        }
    }

    /**
     * 删除审查通知书
     */
    @Override
    public void delSctzs(TZSctzsEntity tZSctzs) {
        this.delAttachementByBid(tZSctzs.getFjbm());//删除附件
        this.delAttachementByBid(tZSctzs.getScyj());//删除附件
        this.commonDao.delete(tZSctzs);
    }

}