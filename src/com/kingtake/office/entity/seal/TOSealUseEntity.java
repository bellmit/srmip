package com.kingtake.office.entity.seal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 印章使用
 * @author onlineGenerator
 * @date 2016-06-02 19:34:49
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_seal_use", schema = "")
@SuppressWarnings("serial")
public class TOSealUseEntity implements java.io.Serializable {
  /** 主键 */
  private java.lang.String id;
  /** 编号字 */
  @Excel(name = "编号字")
  private java.lang.String numberWord;
  /** 编号号 */
  @Excel(name = "编号号")
  private java.lang.String numberSymbol;
  /** 材料名称 */
  @Excel(name = "材料名称")
  private java.lang.String materialName;
  /** 页数 */
  @Excel(name = "页数")
  private java.lang.Integer pagesNum;
  /** 份数 */
  @Excel(name = "份数")
  private java.lang.Integer copiesNum;
  /** 密级 */
  @Excel(name = "密级")
  private java.lang.String secretDegree;
  /** 印章类型 */
  @Excel(name = "印章类型")
  private java.lang.String sealType;
  /** 经办人单位id */
  private java.lang.String operatorDepartid;
  /** 经办人id */
  private java.lang.String operatorId;
  /** 经办人姓名 */
  @Excel(name = "经办人姓名")
  private java.lang.String operatorName;
  /** 主要内容 */
  @Excel(name = "主要内容")
  private java.lang.String mainContent;
  /** 依据 */
  @Excel(name = "依据")
  private java.lang.String accordings;
  /** 申请时间 */
  @Excel(name = "申请时间")
  private java.util.Date applyDate;
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
   * 审核状态
   */
  private String auditStatus;

  /**
   * 完成时间
   */
  private Date finishTime;

  /**
   * 完成人id
   */
  private String finishUserId;

  /**
   * 完成用户名
   */
  private String finishUserName;
  
  /**
   * 流水号，八位，如20160001
   */
  private String searialNum;
  
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
   * @return: java.lang.String 编号字
   */
  @Column(name = "NUMBER_WORD", nullable = true, length = 30)
  public java.lang.String getNumberWord() {
    return this.numberWord;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           编号字
   */
  public void setNumberWord(java.lang.String numberWord) {
    this.numberWord = numberWord;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 编号号
   */
  @Column(name = "NUMBER_SYMBOL", nullable = true, length = 10)
  public java.lang.String getNumberSymbol() {
    return this.numberSymbol;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           编号号
   */
  public void setNumberSymbol(java.lang.String numberSymbol) {
    this.numberSymbol = numberSymbol;
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
   * 方法: 取得java.lang.Integer
   * 
   * @return: java.lang.Integer 页数
   */
  @Column(name = "PAGES_NUM", nullable = true, length = 4)
  public java.lang.Integer getPagesNum() {
    return this.pagesNum;
  }

  /**
   * 方法: 设置java.lang.Integer
   * 
   * @param: java.lang.Integer
   *           页数
   */
  public void setPagesNum(java.lang.Integer pagesNum) {
    this.pagesNum = pagesNum;
  }

  /**
   * 方法: 取得java.lang.Integer
   * 
   * @return: java.lang.Integer 份数
   */
  @Column(name = "COPIES_NUM", nullable = true, length = 4)
  public java.lang.Integer getCopiesNum() {
    return this.copiesNum;
  }

  /**
   * 方法: 设置java.lang.Integer
   * 
   * @param: java.lang.Integer
   *           份数
   */
  public void setCopiesNum(java.lang.Integer copiesNum) {
    this.copiesNum = copiesNum;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 密级
   */
  @Column(name = "SECRET_DEGREE", nullable = true, length = 1)
  public java.lang.String getSecretDegree() {
    return this.secretDegree;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           密级
   */
  public void setSecretDegree(java.lang.String secretDegree) {
    this.secretDegree = secretDegree;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 印章类型
   */
  @Column(name = "SEAL_TYPE", nullable = true, length = 2)
  public java.lang.String getSealType() {
    return this.sealType;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           印章类型
   */
  public void setSealType(java.lang.String sealType) {
    this.sealType = sealType;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 经办人单位id
   */
  @Column(name = "OPERATOR_DEPARTID", nullable = true, length = 32)
  public java.lang.String getOperatorDepartid() {
    return this.operatorDepartid;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           经办人单位id
   */
  public void setOperatorDepartid(java.lang.String operatorDepartid) {
    this.operatorDepartid = operatorDepartid;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 经办人id
   */
  @Column(name = "OPERATOR_ID", nullable = true, length = 32)
  public java.lang.String getOperatorId() {
    return this.operatorId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           经办人id
   */
  public void setOperatorId(java.lang.String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 经办人姓名
   */
  @Column(name = "OPERATOR_NAME", nullable = true, length = 36)
  public java.lang.String getOperatorName() {
    return this.operatorName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           经办人姓名
   */
  public void setOperatorName(java.lang.String operatorName) {
    this.operatorName = operatorName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 主要内容
   */
  @Column(name = "MAIN_CONTENT", nullable = true, length = 2000)
  public java.lang.String getMainContent() {
    return this.mainContent;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           主要内容
   */
  public void setMainContent(java.lang.String mainContent) {
    this.mainContent = mainContent;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 依据
   */
  @Column(name = "ACCORDINGS", nullable = true, length = 200)
  public java.lang.String getAccordings() {
    return this.accordings;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           依据
   */
  public void setAccordings(java.lang.String accordings) {
    this.accordings = accordings;
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
  

  @Column(name = "audit_status")
  public String getAuditStatus() {
      return auditStatus;
  }

  public void setAuditStatus(String auditStatus) {
      this.auditStatus = auditStatus;
  }

  @Column(name = "finish_time")
  public Date getFinishTime() {
      return finishTime;
  }

  public void setFinishTime(Date finishTime) {
      this.finishTime = finishTime;
  }

  @Column(name = "finish_user_id")
  public String getFinishUserId() {
      return finishUserId;
  }

  public void setFinishUserId(String finishUserId) {
      this.finishUserId = finishUserId;
  }

  @Column(name = "finish_user_name")
  public String getFinishUserName() {
      return finishUserName;
  }

  public void setFinishUserName(String finishUserName) {
      this.finishUserName = finishUserName;
  }

    @Column(name = "searial_num")
    public String getSearialNum() {
        return searialNum;
    }

    public void setSearialNum(String searialNum) {
        this.searialNum = searialNum;
    }
  
}
