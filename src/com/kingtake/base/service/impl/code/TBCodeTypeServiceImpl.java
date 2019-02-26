package com.kingtake.base.service.impl.code;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;

@Service("tBCodeTypeService")
@Transactional
public class TBCodeTypeServiceImpl extends CommonServiceImpl implements TBCodeTypeServiceI {

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        // 执行删除操作配置的sql增强
        this.doDelSql((TBCodeTypeEntity) entity);
    }

    @Override
    public void addMain(TBCodeTypeEntity tBCodeType, List<TBCodeDetailEntity> tBCodeDetailList) {
        // 保存主信息
        this.save(tBCodeType);

        /** 保存-基础标准代码参数值 */
        for (TBCodeDetailEntity tBCodeDetail : tBCodeDetailList) {
            // 外键设置
            tBCodeDetail.setCodeTypeId(tBCodeType.getId());
            this.save(tBCodeDetail);
        }
        // 执行新增操作配置的sql增强
        this.doAddSql(tBCodeType);
    }

    @Override
    public void updateMain(TBCodeTypeEntity tBCodeType, List<TBCodeDetailEntity> tBCodeDetailList) {
        // 保存主表信息
        this.saveOrUpdate(tBCodeType);
        // ===================================================================================
        // 获取参数
        Object id0 = tBCodeType.getId();
        // ===================================================================================
        // 1.查询出数据库的明细数据-基础标准代码参数值
        String hql0 = "from TBCodeDetailEntity where 1 = 1 AND cODE_TYPE_ID = ? ";
        List<TBCodeDetailEntity> tBCodeDetailOldList = this.findHql(hql0, id0);
        // 2.筛选更新明细数据-基础标准代码参数值
        for (TBCodeDetailEntity oldE : tBCodeDetailOldList) {
            boolean isUpdate = false;
            for (TBCodeDetailEntity sendE : tBCodeDetailList) {
                // 需要更新的明细数据-基础标准代码参数值
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
                // 如果数据库存在的明细，前台没有传递过来则是删除-基础标准代码参数值
                super.delete(oldE);
            }

        }
        // 3.持久化新增的数据-基础标准代码参数值
        for (TBCodeDetailEntity tBCodeDetail : tBCodeDetailList) {
            if (oConvertUtils.isEmpty(tBCodeDetail.getId())) {
                // 外键设置
                tBCodeDetail.setCodeTypeId(tBCodeType.getId());
                this.save(tBCodeDetail);
            }
        }
        // 执行更新操作配置的sql增强
        this.doUpdateSql(tBCodeType);
    }

    @Override
    public void delMain(TBCodeTypeEntity tBCodeType) {
        // 删除主表信息
        this.delete(tBCodeType);
        // ===================================================================================
        // 获取参数
        Object id0 = tBCodeType.getId();
        // ===================================================================================
        // 删除-基础标准代码参数值
        String hql0 = "from TBCodeDetailEntity where 1 = 1 AND cODE_TYPE_ID = ? ";
        List<TBCodeDetailEntity> tBCodeDetailOldList = this.findHql(hql0, id0);
        this.deleteAllEntitie(tBCodeDetailOldList);
    }

    /**
     * 默认按钮-sql增强-新增操作
     *
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TBCodeTypeEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     *
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TBCodeTypeEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     *
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TBCodeTypeEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     *
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBCodeTypeEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{code}", String.valueOf(t.getCode()));
        sql = sql.replace("#{name}", String.valueOf(t.getName()));
        sql = sql.replace("#{code_type}", String.valueOf(t.getCodeType()));
        sql = sql.replace("#{valid_flag}", String.valueOf(t.getValidFlag()));
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
     * 根据code查询代码参数值
     *
     * @return
     */
    @Override
    public TBCodeDetailEntity findCodeDetailByCode(String codeTypeId, String code) {
        TBCodeDetailEntity codeDetailEntity = null;
        CriteriaQuery cq = new CriteriaQuery(TBCodeDetailEntity.class);
        cq.eq("codeTypeId", codeTypeId);
        cq.eq("code", code);
        cq.eq("validFlag", "1");
        cq.add();
        List<TBCodeDetailEntity> list = this.commonDao.getListByCriteriaQuery(cq, false);
        if (list.size() > 0) {
            codeDetailEntity = list.get(0);
        }
        return codeDetailEntity;
    }

    /**
     * 保存代码类型
     */
    @Override
    public void saveCodeTypeForImport(List<TBCodeTypeEntity> codeTypeEntitys) {
        for (TBCodeTypeEntity codeType : codeTypeEntitys) {
            List<TBCodeDetailEntity> codeDetailList = codeType.getTbCodeDetails();
            if (codeDetailList != null) {
                for (TBCodeDetailEntity codeDetail : codeDetailList) {
                    if (StringUtils.isNotEmpty(codeDetail.getParentCode())) {
                        TBCodeDetailEntity parentCodeDetail = this.findCodeDetailByCode(codeDetail.getCodeTypeId(),
                                codeDetail.getParentCode());
                        codeDetail.setParentCodeDetail(parentCodeDetail);
                    }
                    codeDetail.setValidFlag("1");
                    this.commonDao.save(codeDetail);
                }
            }
            codeType.setValidFlag("1");
            this.commonDao.save(codeType);
        }
    }

    /**
     * 逻辑删除代码类别
     *
     * @param codeTypeEntity
     */
    @Override
    public void deleteMain(TBCodeTypeEntity codeTypeEntity) {
        codeTypeEntity = this.commonDao.get(TBCodeTypeEntity.class, codeTypeEntity.getId());
        codeTypeEntity.setValidFlag("0");
        this.commonDao.saveOrUpdate(codeTypeEntity);
    }

    /**
     * 逻辑删除代码值
     *
     * @param codeTypeEntity
     */
    @Override
    public void deleteDetail(TBCodeDetailEntity codeDetailEntity) {
        codeDetailEntity = this.commonDao.get(TBCodeDetailEntity.class, codeDetailEntity.getId());
        codeDetailEntity.setValidFlag("0");
        this.commonDao.saveOrUpdate(codeDetailEntity);
    }

    @Override
    public List<TBCodeDetailEntity> getCodeDetailByCodeType(TBCodeTypeEntity codeTypeEntity) {
        List<TBCodeDetailEntity> codeDetaiList = new ArrayList<TBCodeDetailEntity>();
        CriteriaQuery cq = new CriteriaQuery(TBCodeTypeEntity.class);
        cq.eq("codeType", codeTypeEntity.getCodeType());
        cq.eq("code", codeTypeEntity.getCode());
        cq.eq("validFlag", "1");
        cq.add();
        List<TBCodeTypeEntity> typeList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (typeList.size() > 0) {
            TBCodeTypeEntity queryCodeTypeEntity = typeList.get(0);
            CriteriaQuery cqDet = new CriteriaQuery(TBCodeDetailEntity.class);
            cqDet.eq("codeTypeId", queryCodeTypeEntity.getId());
            cqDet.eq("validFlag", "1");
            cqDet.addOrder("sortFlag", SortDirection.asc);
            if (codeTypeEntity.getId() == null) {
                cqDet.isNull("parentCodeDetail");
            } else {
                cqDet.createAlias("parentCodeDetail", "p");
                cqDet.eq("p.code", codeTypeEntity.getId());
            }
            cqDet.add();
            codeDetaiList = this.commonDao.getListByCriteriaQuery(cqDet, false);
        }
        return codeDetaiList;
    }
    
    @Override
    public List<TBCodeDetailEntity> getCodeByCodeType(TBCodeTypeEntity codeTypeEntity) {
        List<TBCodeDetailEntity> codeDetaiList = new ArrayList<TBCodeDetailEntity>();
        CriteriaQuery cq = new CriteriaQuery(TBCodeTypeEntity.class);
        cq.eq("codeType", codeTypeEntity.getCodeType());
        cq.eq("code", codeTypeEntity.getCode());
        cq.eq("validFlag", "1");
        cq.add();
        List<TBCodeTypeEntity> typeList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (typeList.size() > 0) {
            TBCodeTypeEntity queryCodeTypeEntity = typeList.get(0);
            CriteriaQuery cqDet = new CriteriaQuery(TBCodeDetailEntity.class);
            cqDet.eq("codeTypeId", queryCodeTypeEntity.getId());
            cqDet.eq("validFlag", "1");
            cqDet.addOrder("sortFlag", SortDirection.asc);
            cqDet.add();
            codeDetaiList = this.commonDao.getListByCriteriaQuery(cqDet, false);
        }
        return codeDetaiList;
    }

    @Override
    public TBCodeTypeEntity getCodeTypeByCode(TBCodeTypeEntity codeTypeEntity) {
        TBCodeTypeEntity entity = null;
        CriteriaQuery cq = new CriteriaQuery(TBCodeTypeEntity.class);
        cq.eq("codeType", codeTypeEntity.getCodeType());
        cq.eq("code", codeTypeEntity.getCode());
        cq.eq("validFlag", "1");
        cq.add();
        List<TBCodeTypeEntity> codeTypeList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (codeTypeList != null && codeTypeList.size() > 0) {
            entity = codeTypeList.get(0);
        }
        return entity;
    }
}