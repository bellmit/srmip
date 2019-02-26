package com.kingtake.zscq.service.impl.sltzs;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ZlConstants;
import com.kingtake.zscq.entity.sltzs.TZSltzsEntity;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.service.sltzs.TZSltzsServiceI;

@Service("tZSltzsService")
@Transactional
public class TZSltzsServiceImpl extends CommonServiceImpl implements TZSltzsServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TZZlsqEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TZZlsqEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TZZlsqEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TZZlsqEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TZZlsqEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TZZlsqEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TZZlsqEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{gdh}", String.valueOf(t.getGdh()));
        sql = sql.replace("#{wcdw}", String.valueOf(t.getWcdw()));
        sql = sql.replace("#{lx}", String.valueOf(t.getLx()));
        sql = sql.replace("#{glxm}", String.valueOf(t.getGlxm()));
        sql = sql.replace("#{mc}", String.valueOf(t.getMc()));
        sql = sql.replace("#{fmr}", String.valueOf(t.getFmr()));
        sql = sql.replace("#{dyfmrsfzh}", String.valueOf(t.getDyfmrsfzh()));
        sql = sql.replace("#{dljg_id}", String.valueOf(t.getDljgId()));
        sql = sql.replace("#{lxr}", String.valueOf(t.getLxr()));
        sql = sql.replace("#{lxrdh}", String.valueOf(t.getLxrdh()));
        sql = sql.replace("#{bz}", String.valueOf(t.getBz()));
        sql = sql.replace("#{fjbm}", String.valueOf(t.getFjbm()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 保存受理通知书
     */
    @Override
    public void saveSltzs(TZSltzsEntity tZSltzs) {
        try {
            if (StringUtils.isEmpty(tZSltzs.getId())) {
                this.commonDao.save(tZSltzs);
                if (StringUtils.isNotEmpty(tZSltzs.getZlsqId())) {
                    TZZlsqEntity zlsq = this.commonDao.get(TZZlsqEntity.class, tZSltzs.getZlsqId());
                    zlsq.setZlzt(ZlConstants.ZLZT_SL);//专利状态改为受理
                    this.commonDao.updateEntitie(zlsq);
                }
            } else {
                TZSltzsEntity t = this.commonDao.get(TZSltzsEntity.class, tZSltzs.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tZSltzs, t);
                this.commonDao.saveOrUpdate(t);
            }
        } catch (Exception e) {
            throw new BusinessException("保存受理通知书失败！");
        }
    }

}