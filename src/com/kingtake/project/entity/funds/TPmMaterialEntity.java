package com.kingtake.project.entity.funds;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 原材料
 * @author onlineGenerator
 * @date 2015-09-17 19:52:15
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_material", schema = "")
@LogEntity("原材料")
@SuppressWarnings("serial")
public class TPmMaterialEntity implements java.io.Serializable {
  /** 主键 */
  @FieldDesc("主键")
  private java.lang.String id;
  /** 材料名称 */
  @Excel(name = "材料名称")
  @FieldDesc("材料名称")
  private java.lang.String materialName;
  /** 规格型号 */
  @Excel(name = "规格型号")
  @FieldDesc("规格型号")
  private java.lang.String materialModel;
  /** 计量单位 */
  @Excel(name = "计量单位")
  @FieldDesc("计量单位")
  private java.lang.String materialUnit;
  /** 单价 */
  @Excel(name = "单价")
  @FieldDesc("单价")
  private Double materialPrice;
  /** 生产厂家 */
  @Excel(name = "生产厂家")
  @FieldDesc("生产厂家")
  private java.lang.String materialFactory;
  /** 年月 */
  @Excel(name = "年月")
  @FieldDesc("年月")
  private java.util.Date supplyDate;
  /** 来源（常量，0：项目自动采集；1：人工录入） */
  @Excel(name = "来源（常量，0：人工录入；1：预算；2：价款计算书）")
  @FieldDesc("来源（常量，0：人工录入；1：预算；2：价款计算书）")
  private java.lang.String materialResource;
  /** 来源id（常量，0：项目自动采集；1：人工录入） */
  @Excel(name = "来源id")
  @FieldDesc("来源id")
  private String resourceId;
  @Excel(name = "来源项目id")
  @FieldDesc("来源项目id")
  private String projectId;
  @Excel(name = "来源项目名称")
  @FieldDesc("来源项目名称")
  private String projectName;

    /**
     * 原材料类型，原材料 辅助材料 外购元器件
     */
    private String materiaType;
    
    /**
     * 采购单位，从价款计算书合同甲方取值
     */
    private String purchaseDept;

    /**
     * 采购时间，从价款计算书合同签订时间
     */
    private Date purchaseTime;

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
   * @return: java.lang.String 材料名称
   */
  @Column(name = "MATERIAL_NAME", nullable = true, length = 200)
  public java.lang.String getMaterialName() {
    return this.materialName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           材料名称
   */
  public void setMaterialName(java.lang.String materialName) {
    this.materialName = materialName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 规格型号
   */
  @Column(name = "MATERIAL_MODEL", nullable = true, length = 80)
  public java.lang.String getMaterialModel() {
    return this.materialModel;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           规格型号
   */
  public void setMaterialModel(java.lang.String materialModel) {
    this.materialModel = materialModel;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 计量单位
   */
  @Column(name = "MATERIAL_UNIT", nullable = true, length = 30)
  public java.lang.String getMaterialUnit() {
    return this.materialUnit;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           计量单位
   */
  public void setMaterialUnit(java.lang.String materialUnit) {
    this.materialUnit = materialUnit;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 单价
   */
  @Column(name = "MATERIAL_PRICE", nullable = true, scale = 2, length = 8)
  public Double getMaterialPrice() {
    return this.materialPrice;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           单价
   */
  public void setMaterialPrice(Double materialPrice) {
    this.materialPrice = materialPrice;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 生产厂家
   */
  @Column(name = "MATERIAL_FACTORY", nullable = true, length = 200)
  public java.lang.String getMaterialFactory() {
    return this.materialFactory;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           生产厂家
   */
  public void setMaterialFactory(java.lang.String materialFactory) {
    this.materialFactory = materialFactory;
  }

  /**
   * 方法: 取得java.util.Date
   * 
   * @return: java.util.Date 年月
   */
  @Column(name = "SUPPLY_DATE", nullable = true)
  public java.util.Date getSupplyDate() {
    return this.supplyDate;
  }

  /**
   * 方法: 设置java.util.Date
   * 
   * @param: java.util.Date
   *           年月
   */
  public void setSupplyDate(java.util.Date supplyDate) {
    this.supplyDate = supplyDate;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 来源（常量，0：项目自动采集；1：人工录入）
   */
  @Column(name = "MATERIAL_RESOURCE", nullable = true, length = 1)
  public java.lang.String getMaterialResource() {
    return this.materialResource;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           来源（常量，0：项目自动采集；1：人工录入）
   */
  public void setMaterialResource(java.lang.String materialResource) {
    this.materialResource = materialResource;
  }

  @Column(name = "RESOURCE_ID", nullable = true, length = 32)
  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  @Column(name = "PROJECT_ID", nullable = true, length = 32)
  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  @Column(name = "PROJECT_NAME", nullable = true, length = 200)
  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

    @Column(name = "material_type")
    public String getMateriaType() {
        return materiaType;
    }

    public void setMateriaType(String materiaType) {
        this.materiaType = materiaType;
    }

    @Column(name = "purchase_dept")
    public String getPurchaseDept() {
        return purchaseDept;
    }

    public void setPurchaseDept(String purchaseDept) {
        this.purchaseDept = purchaseDept;
    }

    @Column(name = "purchase_time")
    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

}
