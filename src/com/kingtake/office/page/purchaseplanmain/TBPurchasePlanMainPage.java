
package com.kingtake.office.page.purchaseplanmain;

import java.util.ArrayList;
import java.util.List;

import com.kingtake.office.entity.purchaseplandetail.TBPurchasePlanDetailEntity;

/**
 * @Title: Entity
 * @Description: 科研采购计划
 * @author onlineGenerator
 * @date 2016-05-31 18:50:15
 * @version V1.0
 *
 */
public class TBPurchasePlanMainPage implements java.io.Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 3403647921190746669L;
  /** 保存-科研采购计划明细 */
  private List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList = new ArrayList<TBPurchasePlanDetailEntity>();

  public List<TBPurchasePlanDetailEntity> getTBPurchasePlanDetailList() {
    return tBPurchasePlanDetailList;
  }

  public void setTBPurchasePlanDetailList(List<TBPurchasePlanDetailEntity> tBPurchasePlanDetailList) {
    this.tBPurchasePlanDetailList = tBPurchasePlanDetailList;
  }

  /** 主键 */
  private java.lang.String id;
  /** 责任单位id */
  private java.lang.String dutyDepartId;
  /** 责任单位名称 */
  private java.lang.String dutyDepartName;
  /** 负责人id */
  private java.lang.String managerId;
  /** 负责人 */
  private java.lang.String managerName;
  /** 项目编码 */
  private java.lang.String projectCode;
  /** 项目名称 */
  private java.lang.String projectName;
  /** 总经费 */
  private java.math.BigDecimal totalFunds;
  /** 采购计划名称 */
  private java.lang.String planName;
  /** 采购概算 */
  private java.math.BigDecimal purchaseEstimates;
  /** 采购方式 */
  private java.lang.String purchaseMode;
  /** 采购来源 */
  private java.lang.String purchaseSource;
  /** 创建人 */
  private java.lang.String createBy;
  /** 创建人姓名 */
  private java.lang.String createName;
  /** 创建时间 */
  private java.util.Date createDate;
  /** 修改人 */
  private java.lang.String updateBy;
  /** 修改人姓名 */
  private java.lang.String updateName;
  /** 修改时间 */
  private java.util.Date updateDate;

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 主键
   */
  public java.lang.String getId() {
    return this.id;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           主键
   */
  public void setId(java.lang.String id) {
    this.id = id;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 责任单位id
   */
  public java.lang.String getDutyDepartId() {
    return this.dutyDepartId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           责任单位id
   */
  public void setDutyDepartId(java.lang.String dutyDepartId) {
    this.dutyDepartId = dutyDepartId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 责任单位名称
   */
  public java.lang.String getDutyDepartName() {
    return this.dutyDepartName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           责任单位名称
   */
  public void setDutyDepartName(java.lang.String dutyDepartName) {
    this.dutyDepartName = dutyDepartName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 负责人id
   */
  public java.lang.String getManagerId() {
    return this.managerId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           负责人id
   */
  public void setManagerId(java.lang.String managerId) {
    this.managerId = managerId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 负责人
   */
  public java.lang.String getManagerName() {
    return this.managerName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           负责人
   */
  public void setManagerName(java.lang.String managerName) {
    this.managerName = managerName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 项目编码
   */
  public java.lang.String getProjectCode() {
    return this.projectCode;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           项目编码
   */
  public void setProjectCode(java.lang.String projectCode) {
    this.projectCode = projectCode;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 项目名称
   */
  public java.lang.String getProjectName() {
    return this.projectName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           项目名称
   */
  public void setProjectName(java.lang.String projectName) {
    this.projectName = projectName;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 总经费
   */
  public java.math.BigDecimal getTotalFunds() {
    return this.totalFunds;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           总经费
   */
  public void setTotalFunds(java.math.BigDecimal totalFunds) {
    this.totalFunds = totalFunds;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 采购计划名称
   */
  public java.lang.String getPlanName() {
    return this.planName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           采购计划名称
   */
  public void setPlanName(java.lang.String planName) {
    this.planName = planName;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 采购概算
   */
  public java.math.BigDecimal getPurchaseEstimates() {
    return this.purchaseEstimates;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           采购概算
   */
  public void setPurchaseEstimates(java.math.BigDecimal purchaseEstimates) {
    this.purchaseEstimates = purchaseEstimates;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 采购方式
   */
  public java.lang.String getPurchaseMode() {
    return this.purchaseMode;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           采购方式
   */
  public void setPurchaseMode(java.lang.String purchaseMode) {
    this.purchaseMode = purchaseMode;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 采购来源
   */
  public java.lang.String getPurchaseSource() {
    return this.purchaseSource;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           采购来源
   */
  public void setPurchaseSource(java.lang.String purchaseSource) {
    this.purchaseSource = purchaseSource;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 创建人
   */
  public java.lang.String getCreateBy() {
    return this.createBy;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           创建人
   */
  public void setCreateBy(java.lang.String createBy) {
    this.createBy = createBy;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 创建人姓名
   */
  public java.lang.String getCreateName() {
    return this.createName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           创建人姓名
   */
  public void setCreateName(java.lang.String createName) {
    this.createName = createName;
  }

  /**
   * 方法: 取得java.util.Date
   * 
   * @return: java.util.Date 创建时间
   */
  public java.util.Date getCreateDate() {
    return this.createDate;
  }

  /**
   * 方法: 设置java.util.Date
   * 
   * @param: java.util.Date
   *           创建时间
   */
  public void setCreateDate(java.util.Date createDate) {
    this.createDate = createDate;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 修改人
   */
  public java.lang.String getUpdateBy() {
    return this.updateBy;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           修改人
   */
  public void setUpdateBy(java.lang.String updateBy) {
    this.updateBy = updateBy;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 修改人姓名
   */
  public java.lang.String getUpdateName() {
    return this.updateName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           修改人姓名
   */
  public void setUpdateName(java.lang.String updateName) {
    this.updateName = updateName;
  }

  /**
   * 方法: 取得java.util.Date
   * 
   * @return: java.util.Date 修改时间
   */
  public java.util.Date getUpdateDate() {
    return this.updateDate;
  }

  /**
   * 方法: 设置java.util.Date
   * 
   * @param: java.util.Date
   *           修改时间
   */
  public void setUpdateDate(java.util.Date updateDate) {
    this.updateDate = updateDate;
  }
}
