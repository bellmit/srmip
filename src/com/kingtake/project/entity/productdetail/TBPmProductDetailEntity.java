package com.kingtake.project.entity.productdetail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 产品交接清单明细
 * @author onlineGenerator
 * @date 2016-03-01 16:24:20
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_pm_product_detail", schema = "")
@SuppressWarnings("serial")
public class TBPmProductDetailEntity implements java.io.Serializable {
  /** 主键 */
  private java.lang.String id;
  /** 产品交接清单信息表主键id */
  @Excel(name = "产品交接清单信息表主键id")
  private java.lang.String proeuctDeliveryId;
  /** 序号 */
  @Excel(name = "序号")
  private java.lang.Integer serialNum;
  /** 产品名称 */
  @Excel(name = "产品名称")
  private java.lang.String productName;
  /** 规格型号 */
  @Excel(name = "规格型号")
  private java.lang.String specificationModel;
  /** 单位 */
  @Excel(name = "单位")
  private java.lang.String productUnit;
  /** 交付数量 */
  @Excel(name = "交付数量")
  private java.math.BigDecimal deliverNum;
  /** 接收数量 */
  @Excel(name = "接收数量")
  private java.math.BigDecimal receiveNum;
  /** 备注 */
  @Excel(name = "备注")
  private java.lang.String memo;
  /** 创建人 */
  @Excel(name = "创建人")
  private java.lang.String createBy;
  /** 创建人姓名 */
  @Excel(name = "创建人姓名")
  private java.lang.String createName;
  /** 创建时间 */
  @Excel(name = "创建时间")
  private java.util.Date createDate;
  /** 修改人 */
  @Excel(name = "修改人")
  private java.lang.String updateBy;
  /** 修改人姓名 */
  @Excel(name = "修改人姓名")
  private java.lang.String updateName;
  /** 修改时间 */
  @Excel(name = "修改时间")
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
   * @return: java.lang.String 产品交接清单信息表主键id
   */
  @Column(name = "PROEUCT_DELIVERY_ID", nullable = true, length = 32)
  public java.lang.String getProeuctDeliveryId() {
    return this.proeuctDeliveryId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           产品交接清单信息表主键id
   */
  public void setProeuctDeliveryId(java.lang.String proeuctDeliveryId) {
    this.proeuctDeliveryId = proeuctDeliveryId;
  }

  /**
   * 方法: 取得java.lang.Integer
   * 
   * @return: java.lang.Integer 序号
   */
  @Column(name = "SERIAL_NUM", nullable = true, length = 4)
  public java.lang.Integer getSerialNum() {
    return this.serialNum;
  }

  /**
   * 方法: 设置java.lang.Integer
   * 
   * @param: java.lang.Integer
   *           序号
   */
  public void setSerialNum(java.lang.Integer serialNum) {
    this.serialNum = serialNum;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 产品名称
   */
  @Column(name = "PRODUCT_NAME", nullable = true, length = 100)
  public java.lang.String getProductName() {
    return this.productName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           产品名称
   */
  public void setProductName(java.lang.String productName) {
    this.productName = productName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 规格型号
   */
  @Column(name = "SPECIFICATION_MODEL", nullable = true, length = 80)
  public java.lang.String getSpecificationModel() {
    return this.specificationModel;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           规格型号
   */
  public void setSpecificationModel(java.lang.String specificationModel) {
    this.specificationModel = specificationModel;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 单位
   */
  @Column(name = "PRODUCT_UNIT", nullable = true, length = 20)
  public java.lang.String getProductUnit() {
    return this.productUnit;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           单位
   */
  public void setProductUnit(java.lang.String productUnit) {
    this.productUnit = productUnit;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 交付数量
   */
  @Column(name = "DELIVER_NUM", nullable = true, scale = 2, length = 12)
  public java.math.BigDecimal getDeliverNum() {
    return this.deliverNum;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           交付数量
   */
  public void setDeliverNum(java.math.BigDecimal deliverNum) {
    this.deliverNum = deliverNum;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 接收数量
   */
  @Column(name = "RECEIVE_NUM", nullable = true, scale = 2, length = 12)
  public java.math.BigDecimal getReceiveNum() {
    return this.receiveNum;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           接收数量
   */
  public void setReceiveNum(java.math.BigDecimal receiveNum) {
    this.receiveNum = receiveNum;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 备注
   */
  @Column(name = "MEMO", nullable = true, length = 500)
  public java.lang.String getMemo() {
    return this.memo;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           备注
   */
  public void setMemo(java.lang.String memo) {
    this.memo = memo;
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
