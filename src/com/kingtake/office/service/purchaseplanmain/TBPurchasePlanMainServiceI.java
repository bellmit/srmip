package com.kingtake.office.service.purchaseplanmain;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.purchaseplandetail.TBPurchasePlanDetailEntity;
import com.kingtake.office.entity.purchaseplanmain.TBPurchasePlanMainEntity;

public interface TBPurchasePlanMainServiceI extends CommonService {

  public <T> void delete(T entity);

  /**
   * 添加一对多
   * 
   */
  public void addMain(TBPurchasePlanMainEntity tBPurchasePlanMain, List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList);

  /**
   * 修改一对多
   * 
   */
  public void updateMain(TBPurchasePlanMainEntity tBPurchasePlanMain, List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList);

  public void delMain(TBPurchasePlanMainEntity tBPurchasePlanMain);

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  public boolean doAddSql(TBPurchasePlanMainEntity t);

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  public boolean doUpdateSql(TBPurchasePlanMainEntity t);

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  public boolean doDelSql(TBPurchasePlanMainEntity t);

    /**
     * 保存采购计划
     * @param tBPurchasePlanMain
     */
    public void savePlan(TBPurchasePlanMainEntity tBPurchasePlanMain);

    /**
     * 生成编号
     * @param planIds
     * @param year
     * @param batch
     */
    public void generateCode(String planIds, String year, String batch);

}
