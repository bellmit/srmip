package com.kingtake.project.entity.invoice;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 项目发票信息
 * @author onlineGenerator
 * @date 2016-01-21 20:20:22
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_pm_invoice", schema = "")
@SuppressWarnings("serial")
public class TBPmInvoiceEntity implements java.io.Serializable {
  /** 主键 */
  private java.lang.String id;
  /** 关联项目id */
  private java.lang.String projectId;
  /** 票据年度 */
  @Excel(name = "票据年度")
  private java.lang.String invoiceYear;
  /** 申请时间 */
  @Excel(name = "申请时间",format="yyyy-MM-dd")
  private java.util.Date applyDate;
  /** 发票号1 */
  @Excel(name = "发票号1")
  private java.lang.String invoiceNum1;
  /** 发票号2 */
  @Excel(name = "发票号2")
  private java.lang.String invoiceNum2;
  /** 发票金额 */
  @Excel(name = "发票金额",isAmount = true)
  private java.math.BigDecimal invoiceAmount;
  /** 备注 */
  @Excel(name = "备注")
  private java.lang.String memo;
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
  @FieldDesc("附件")
  private List<TSFilesEntity> certificates;// 附件
  
  /**
   * 附件关联id
   */
  private String attachmentCode;

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
   * @return: java.lang.String 关联项目id
   */
  @Column(name = "PROJECT_ID", nullable = true, length = 32)
  public java.lang.String getProjectId() {
    return this.projectId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           关联项目id
   */
  public void setProjectId(java.lang.String projectId) {
    this.projectId = projectId;
  }
  
  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 票据年度
   */
  @Column(name = "INVOICE_YEAR", nullable = true, length = 12)
  public java.lang.String getInvoiceYear() {
    return this.invoiceYear;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           发票号1
   */
  public void setInvoiceYear(java.lang.String invoiceYear) {
    this.invoiceYear = invoiceYear;
  }

  /**
   * 方法: 取得java.util.Date
   * 
   * @return: java.util.Date 申请时间
   */
  @Column(name = "APPLY_DATE", nullable = true)
  public java.util.Date getApplyDate() {
    return this.applyDate;
  }

  /**
   * 方法: 设置java.util.Date
   * 
   * @param: java.util.Date
   *           申请时间
   */
  public void setApplyDate(java.util.Date applyDate) {
    this.applyDate = applyDate;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 发票号1
   */
  @Column(name = "INVOICE_NUM1", nullable = true, length = 12)
  public java.lang.String getInvoiceNum1() {
    return this.invoiceNum1;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           发票号1
   */
  public void setInvoiceNum1(java.lang.String invoiceNum1) {
    this.invoiceNum1 = invoiceNum1;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 发票号2
   */
  @Column(name = "INVOICE_NUM2", nullable = true, length = 12)
  public java.lang.String getInvoiceNum2() {
    return this.invoiceNum2;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           发票号2
   */
  public void setInvoiceNum2(java.lang.String invoiceNum2) {
    this.invoiceNum2 = invoiceNum2;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 发票金额
   */
  @Column(name = "INVOICE_AMOUNT", nullable = true, scale = 2, length = 13)
  public java.math.BigDecimal getInvoiceAmount() {
    return this.invoiceAmount;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           发票金额
   */
  public void setInvoiceAmount(java.math.BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 备注
   */
  @Column(name = "MEMO", nullable = true, length = 200)
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

  public void setCertificates(List<TSFilesEntity> certificates) {
    this.certificates = certificates;
  }

  @Transient
  public List<TSFilesEntity> getCertificates() {
    return certificates;
  }

  @Column(name="attachment_code")
  public String getAttachmentCode() {
    return attachmentCode;
  }

  public void setAttachmentCode(String attachmentCode) {
    this.attachmentCode = attachmentCode;
  }
  
  
}
