package com.kingtake.project.service.productdelivery;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.productdelivery.TBPmProductDeliveryEntity;
import com.kingtake.project.entity.productdetail.TBPmProductDetailEntity;

public interface TBPmProductDeliveryServiceI extends CommonService {

  public <T> void delete(T entity);

  /**
   * 添加一对多
   * 
   */
  public void addMain(TBPmProductDeliveryEntity tBPmProductDelivery, List<TBPmProductDetailEntity> tBPmProductDetailList);

  /**
   * 修改一对多
   * 
   */
  public void updateMain(TBPmProductDeliveryEntity tBPmProductDelivery, List<TBPmProductDetailEntity> tBPmProductDetailList);

  public void delMain(TBPmProductDeliveryEntity tBPmProductDelivery);

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  public boolean doAddSql(TBPmProductDeliveryEntity t);

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  public boolean doUpdateSql(TBPmProductDeliveryEntity t);

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  public boolean doDelSql(TBPmProductDeliveryEntity t);
}
