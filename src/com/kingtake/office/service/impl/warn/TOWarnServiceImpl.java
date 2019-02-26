package com.kingtake.office.service.impl.warn;

import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.entity.warn.TOWarnReceiveEntity;
import com.kingtake.office.service.warn.TOWarnServiceI;

@Service("tOWarnService")
@Transactional
public class TOWarnServiceImpl extends CommonServiceImpl implements TOWarnServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TOWarnEntity) entity);
    }

    public void addMain(TOWarnEntity tOWarn, List<TOWarnReceiveEntity> tOWarnReceiveList) {
        //保存主信息
        this.save(tOWarn);

        /** 保存-公用信息接收人表 */
        for (TOWarnReceiveEntity tOWarnReceive : tOWarnReceiveList) {
            //外键设置
            tOWarnReceive.setToWarn(tOWarn);
            this.save(tOWarnReceive);
        }
        //执行新增操作配置的sql增强
        this.doAddSql(tOWarn);
    }

    public void updateMain(TOWarnEntity tOWarn, List<TOWarnReceiveEntity> tOWarnReceiveList) {
        //保存主表信息
        this.saveOrUpdate(tOWarn);
        //===================================================================================
        //获取参数
        Object id0 = tOWarn.getId();
        //===================================================================================
        //1.查询出数据库的明细数据-公用信息接收人表
        String hql0 = "from TOWarnReceiveEntity where 1 = 1 AND t_O_WARN_ID = ? ";
        List<TOWarnReceiveEntity> tOWarnReceiveOldList = this.findHql(hql0, id0);
        //2.筛选更新明细数据-公用信息接收人表
        for (TOWarnReceiveEntity oldE : tOWarnReceiveOldList) {
            boolean isUpdate = false;
            for (TOWarnReceiveEntity sendE : tOWarnReceiveList) {
                //需要更新的明细数据-公用信息接收人表
                if (oldE.getId().equals(sendE.getId())) {
                    try {
                        MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
                        this.saveOrUpdate(oldE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BusinessException(e.getMessage());
                    }
                    isUpdate = true;
                    break;
                }
            }
            if (!isUpdate) {
                //如果数据库存在的明细，前台没有传递过来则是删除-公用信息接收人表
                super.delete(oldE);
            }

        }
        //3.持久化新增的数据-公用信息接收人表
        for (TOWarnReceiveEntity tOWarnReceive : tOWarnReceiveList) {
            if (oConvertUtils.isEmpty(tOWarnReceive.getId())) {
                //外键设置
                tOWarnReceive.setToWarn(tOWarn);
                this.save(tOWarnReceive);
            }
        }
        //执行更新操作配置的sql增强
        this.doUpdateSql(tOWarn);
    }

    public void delMain(TOWarnEntity tOWarn) {
        //删除主表信息
        this.delete(tOWarn);
        //===================================================================================
        //获取参数
        Object id0 = tOWarn.getId();
        //===================================================================================
        //删除-公用信息接收人表
        String hql0 = "from TOWarnReceiveEntity where 1 = 1 AND t_O_WARN_ID = ? ";
        List<TOWarnReceiveEntity> tOWarnReceiveOldList = this.findHql(hql0, id0);
        this.deleteAllEntitie(tOWarnReceiveOldList);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOWarnEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOWarnEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOWarnEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TOWarnEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{warn_type}", String.valueOf(t.getWarnType()));
        sql = sql.replace("#{warn_content}", String.valueOf(t.getWarnContent()));
        sql = sql.replace("#{form_url}", String.valueOf(t.getFormUrl()));
        sql = sql.replace("#{warn_begin_time}", String.valueOf(t.getWarnBeginTime()));
        sql = sql.replace("#{warn_end_time}", String.valueOf(t.getWarnEndTime()));
        sql = sql.replace("#{warn_status}", String.valueOf(t.getWarnStatus()));
        sql = sql.replace("#{warn_frequency}", String.valueOf(t.getWarnFrequency()));
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
     * 完成提醒
     * 
     * @param receiveEntity
     */
    @Override
    public void finishWarn(TOWarnReceiveEntity receiveEntity) {
        try {
            TOWarnReceiveEntity tmp = this.commonDao.get(TOWarnReceiveEntity.class, receiveEntity.getId());
            MyBeanUtils.copyBeanNotNull2Bean(receiveEntity, tmp);
            this.commonDao.updateEntitie(tmp);//更新子表
            TOWarnEntity warnEntity = this.commonDao.get(TOWarnEntity.class, tmp.getToWarn().getId());
            List<TOWarnReceiveEntity> receiveList = warnEntity.getReceiveList();
            boolean flag = true;
            for (TOWarnReceiveEntity e : receiveList) {
                if ("0".equals(e.getFinishFlag()) || e.getFinishFlag() == null) {
                    flag = false;
                }
            }
            if (flag) {
                warnEntity.setWarnStatus("1");//设置为已完成
                this.commonDao.updateEntitie(warnEntity);
            }
        } catch (Exception e) {
            throw new BusinessException("完成公共提醒报错", e);
        }

    }
}