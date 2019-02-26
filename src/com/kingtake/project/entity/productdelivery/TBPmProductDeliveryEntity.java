package com.kingtake.project.entity.productdelivery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 产品交接清单信息
 * @author onlineGenerator
 * @date 2016-03-01 16:24:20
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_pm_product_delivery", schema = "")
@SuppressWarnings("serial")
public class TBPmProductDeliveryEntity implements java.io.Serializable {
  /** 主键 */
  private java.lang.String id;
  /** 项目基本信息主键 */
  private java.lang.String projectId;
  /** 关联合同id */
  private java.lang.String contractId;
  /** 合同编号 */
  private java.lang.String contractCode;
  /** 合同名称 */
  private java.lang.String contractName;
  /** 交付单位 */
  private java.lang.String deliverUnit;
  /** 交付人 */
  private java.lang.String deliverName;
  /** 交付日期 */
  private java.util.Date deliverDate;
  /** 接收单位 */
  private java.lang.String receiveUnit;
  /** 接收人 */
  private java.lang.String receiveName;
  /** 接收日期 */
  private java.util.Date receiveDate;
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
  @Id
  @GeneratedValue(generator = "paymentableGenerator")
  @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

  @Column(name = "ID", nullable = false, length = 32)
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
   * @return: java.lang.String 项目基本信息主键
   */

  @Column(name = "PROJECT_ID", nullable = true, length = 32)
  public java.lang.String getProjectId() {
    return this.projectId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           项目基本信息主键
   */
  public void setProjectId(java.lang.String projectId) {
    this.projectId = projectId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 关联合同id
   */

  @Column(name = "CONTRACT_ID", nullable = true, length = 32)
  public java.lang.String getContractId() {
    return this.contractId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           关联合同id
   */
  public void setContractId(java.lang.String contractId) {
    this.contractId = contractId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 合同编号
   */

  @Column(name = "CONTRACT_CODE", nullable = true, length = 30)
  public java.lang.String getContractCode() {
    return this.contractCode;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           合同编号
   */
  public void setContractCode(java.lang.String contractCode) {
    this.contractCode = contractCode;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 合同名称
   */

  @Column(name = "CONTRACT_NAME", nullable = true, length = 100)
  public java.lang.String getContractName() {
    return this.contractName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           合同名称
   */
  public void setContractName(java.lang.String contractName) {
    this.contractName = contractName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 交付单位
   */

  @Column(name = "DELIVER_UNIT", nullable = true, length = 120)
  public java.lang.String getDeliverUnit() {
    return this.deliverUnit;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           交付单位
   */
  public void setDeliverUnit(java.lang.String deliverUnit) {
    this.deliverUnit = deliverUnit;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 交付人
   */

  @Column(name = "DELIVER_NAME", nullable = true, length = 36)
  public java.lang.String getDeliverName() {
    return this.deliverName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           交付人
   */
  public void setDeliverName(java.lang.String deliverName) {
    this.deliverName = deliverName;
  }

  /**
   * 方法: 取得java.util.Date
   * 
   * @return: java.util.Date 交付日期
   */

  @Column(name = "DELIVER_DATE", nullable = true)
  public java.util.Date getDeliverDate() {
    return this.deliverDate;
  }

  /**
   * 方法: 设置java.util.Date
   * 
   * @param: java.util.Date
   *           交付日期
   */
  public void setDeliverDate(java.util.Date deliverDate) {
    this.deliverDate = deliverDate;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 接收单位
   */

  @Column(name = "RECEIVE_UNIT", nullable = true, length = 120)
  public java.lang.String getReceiveUnit() {
    return this.receiveUnit;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           接收单位
   */
  public void setReceiveUnit(java.lang.String receiveUnit) {
    this.receiveUnit = receiveUnit;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 接收人
   */

  @Column(name = "RECEIVE_NAME", nullable = true, length = 36)
  public java.lang.String getReceiveName() {
    return this.receiveName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           接收人
   */
  public void setReceiveName(java.lang.String receiveName) {
    this.receiveName = receiveName;
  }

  /**
   * 方法: 取得java.util.Date
   * 
   * @return: java.util.Date 接收日期
   */

  @Column(name = "RECEIVE_DATE", nullable = true)
  public java.util.Date getReceiveDate() {
    return this.receiveDate;
  }

  /**
   * 方法: 设置java.util.Date
   * 
   * @param: java.util.Date
   *           接收日期
   */
  public void setReceiveDate(java.util.Date receiveDate) {
    this.receiveDate = receiveDate;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 创建人
   */

  @Column(name = "CREATE_BY", nullable = true, length = 50)
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

  @Column(name = "CREATE_NAME", nullable = true, length = 50)
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

  @Column(name = "CREATE_DATE", nullable = true)
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

  @Column(name = "UPDATE_BY", nullable = true, length = 50)
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

  @Column(name = "UPDATE_NAME", nullable = true, length = 50)
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

  @Column(name = "UPDATE_DATE", nullable = true)
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
