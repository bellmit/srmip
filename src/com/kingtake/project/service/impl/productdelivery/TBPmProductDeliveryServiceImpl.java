package com.kingtake.project.service.impl.productdelivery;

import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.productdelivery.TBPmProductDeliveryEntity;
import com.kingtake.project.entity.productdetail.TBPmProductDetailEntity;
import com.kingtake.project.service.productdelivery.TBPmProductDeliveryServiceI;

@Service("tBPmProductDeliveryService")
@Transactional
public class TBPmProductDeliveryServiceImpl extends CommonServiceImpl implements TBPmProductDeliveryServiceI {

  public <T> void delete(T entity) {
    super.delete(entity);
    // 执行删除操作配置的sql增强
    this.doDelSql((TBPmProductDeliveryEntity) entity);
  }

  public void addMain(TBPmProductDeliveryEntity tBPmProductDelivery, List<TBPmProductDetailEntity> tBPmProductDetailList) {
    // 保存主信息
    this.save(tBPmProductDelivery);

    /** 保存-产品交接清单明细 */
    for (TBPmProductDetailEntity tBPmProductDetail : tBPmProductDetailList) {
      // 外键设置
      tBPmProductDetail.setProeuctDeliveryId(tBPmProductDelivery.getId());
      this.save(tBPmProductDetail);
    }
    // 执行新增操作配置的sql增强
    this.doAddSql(tBPmProductDelivery);
  }

  public void updateMain(TBPmProductDeliveryEntity tBPmProductDelivery, List<TBPmProductDetailEntity> tBPmProductDetailList) {
    // 保存主表信息
    this.saveOrUpdate(tBPmProductDelivery);
    // ===================================================================================
    // 获取参数
    Object id0 = tBPmProductDelivery.getId();
    // ===================================================================================
    // 1.查询出数据库的明细数据-产品交接清单明细
    String hql0 = "from TBPmProductDetailEntity where 1 = 1 AND pROEUCT_DELIVERY_ID = ? ";
    List<TBPmProductDetailEntity> tBPmProductDetailOldList = this.findHql(hql0, id0);
    // 2.筛选更新明细数据-产品交接清单明细
    for (TBPmProductDetailEntity oldE : tBPmProductDetailOldList) {
      boolean isUpdate = false;
      for (TBPmProductDetailEntity sendE : tBPmProductDetailList) {
        // 需要更新的明细数据-产品交接清单明细
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
        // 如果数据库存在的明细，前台没有传递过来则是删除-产品交接清单明细
        super.delete(oldE);
      }

    }
    // 3.持久化新增的数据-产品交接清单明细
    for (TBPmProductDetailEntity tBPmProductDetail : tBPmProductDetailList) {
      if (oConvertUtils.isEmpty(tBPmProductDetail.getId())) {
        // 外键设置
        tBPmProductDetail.setProeuctDeliveryId(tBPmProductDelivery.getId());
        this.save(tBPmProductDetail);
      }
    }
    // 执行更新操作配置的sql增强
    this.doUpdateSql(tBPmProductDelivery);
  }

  public void delMain(TBPmProductDeliveryEntity tBPmProductDelivery) {
    // 获取参数
    Object id0 = tBPmProductDelivery.getId();
    // ===================================================================================
    // 删除-产品交接清单明细
    String hql0 = "from TBPmProductDetailEntity where 1 = 1 AND pROEUCT_DELIVERY_ID = ? ";
    List<TBPmProductDetailEntity> tBPmProductDetailOldList = this.findHql(hql0, id0);
    this.deleteAllEntitie(tBPmProductDetailOldList);
    // 删除主表信息
    this.delete(tBPmProductDelivery);
    // ===================================================================================
  }

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  public boolean doAddSql(TBPmProductDeliveryEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  public boolean doUpdateSql(TBPmProductDeliveryEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  public boolean doDelSql(TBPmProductDeliveryEntity t) {
    return true;
  }

  /**
   * 替换sql中的变量
   * 
   * @param sql
   * @return
   */
  public String replaceVal(String sql, TBPmProductDeliveryEntity t) {
    sql = sql.replace("#{id}", String.valueOf(t.getId()));
    sql = sql.replace("#{project_id}", String.valueOf(t.getProjectId()));
    sql = sql.replace("#{contract_id}", String.valueOf(t.getContractId()));
    sql = sql.replace("#{contract_code}", String.valueOf(t.getContractCode()));
    sql = sql.replace("#{contract_name}", String.valueOf(t.getContractName()));
    sql = sql.replace("#{deliver_unit}", String.valueOf(t.getDeliverUnit()));
    sql = sql.replace("#{deliver_name}", String.valueOf(t.getDeliverName()));
    sql = sql.replace("#{deliver_date}", String.valueOf(t.getDeliverDate()));
    sql = sql.replace("#{receive_unit}", String.valueOf(t.getReceiveUnit()));
    sql = sql.replace("#{receive_name}", String.valueOf(t.getReceiveName()));
    sql = sql.replace("#{receive_date}", String.valueOf(t.getReceiveDate()));
    sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
    sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
    sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
    sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
    sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
    sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
    sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
    return sql;
  }
}