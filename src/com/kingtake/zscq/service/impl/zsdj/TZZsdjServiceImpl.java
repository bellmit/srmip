package com.kingtake.zscq.service.impl.zsdj;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.entity.zsdj.TZZsdjEntity;
import com.kingtake.zscq.service.zsdj.TZZsdjServiceI;

@Service("tZZsdjService")
@Transactional
public class TZZsdjServiceImpl extends CommonServiceImpl implements TZZsdjServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

    @Autowired
    private CommonMessageServiceI commonMessageService;

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TZZsdjEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TZZsdjEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TZZsdjEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TZZsdjEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TZZsdjEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TZZsdjEntity t) {
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
    public void saveZsdj(TZZsdjEntity tZZsdj) {
        try {
            if (StringUtils.isEmpty(tZZsdj.getId())) {
                this.commonDao.save(tZZsdj);
            } else {
            	TZZsdjEntity t = this.commonDao.get(TZZsdjEntity.class, tZZsdj.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tZZsdj, t);
                this.commonDao.saveOrUpdate(t);
            }
        } catch (Exception e) {
            throw new BusinessException("证书登记保存失败！");
        }
    }

}