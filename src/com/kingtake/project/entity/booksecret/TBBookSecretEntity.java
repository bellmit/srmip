package com.kingtake.project.entity.booksecret;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.constant.CodeTypeConstant;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 专著保密信息表
 * @author onlineGenerator
 * @date 2016-01-12 10:37:56
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_book_secret", schema = "")
@SuppressWarnings("serial")
public class TBBookSecretEntity implements java.io.Serializable {
  /** 主键 */
  private java.lang.String id;
  /** 编号 */
  @Excel(name = "编号")
  @FieldDesc("编号")
  private java.lang.String reviewNumber;
  /** 著作名称 */
  @Excel(name = "著作名称")
  @FieldDesc("著作名称")
  private java.lang.String bookName;
  /** 所属院系 */
  @Excel(name = "所属院系")
  @FieldDesc("所属院系")
  private java.lang.String subordinateDeptId;
  @Excel(name = "所属院名称")
  @FieldDesc("所属院名称")
  private java.lang.String subordinateDeptName;
  /** 具体单位 */
  @Excel(name = "具体单位")
  @FieldDesc("具体单位ID")
  private java.lang.String concreteDeptId;
  @Excel(name = "具体单位名称")
  @FieldDesc("具体单位名称")
  private java.lang.String concreteDeptName;
  /** 密级 */
  @Excel(name = "密级", isExportConvert = true)
  @FieldDesc("密级")
  private java.lang.String secretDegree;
  /** 出版社 */
  @Excel(name = "出版社")
  @FieldDesc("出版社")
  private java.lang.String press;
  /** 发行范围 */
  @Excel(name = "发行范围")
  @FieldDesc("发行范围")
  private java.lang.String issueScope;
  /** 固定联系电话 */
  @Excel(name = "固定联系电话")
  @FieldDesc("固定联系电话")
  private java.lang.String fixTelephone;
  /** 移动联系电话 */
  @Excel(name = "移动联系电话")
  @FieldDesc("移动联系电话")
  private java.lang.String mobileTelephone;
  /** 第一作者姓名 */
  @Excel(name = "第一作者姓名")
  @FieldDesc("第一作者姓名")
  private java.lang.String firstAuthor;
  /** 其他作者姓名 */
  @Excel(name = "其他作者姓名")
  @FieldDesc("其他作者姓名")
  private java.lang.String otherAuthor;
  /** 著作内容要点 */
  @Excel(name = "著作内容要点")
  @FieldDesc("著作内容要点")
  private java.lang.String bookContentKey;
  /** 备注 */
  @Excel(name = "备注")
  @FieldDesc("备注")
  private java.lang.String memo;
  /** 创建人 */
  @FieldDesc("创建人")
  private java.lang.String createBy;
  /** 创建人姓名 */
  @FieldDesc("创建人姓名")
  private java.lang.String createName;
  /** 创建时间 */
  @FieldDesc("创建时间")
  private java.util.Date createDate;
  /** 修改人 */
  @FieldDesc("修改人")
  private java.lang.String updateBy;
  /** 修改人姓名 */
  @FieldDesc("修改人姓名")
  private java.lang.String updateName;
  /** 修改时间 */
  @FieldDesc("修改时间")
  private java.util.Date updateDate;
  /** 审查状态（0:未审查,1:已审查） */
  @Excel(name = "审查状态",isExportConvert=true)
  @FieldDesc("审查状态")
  private java.lang.String checkFlag;
  /** 第一作者ID */
  @FieldDesc("第一作者ID")
  private java.lang.String firstAuthorId;
  
  /**
   * 附件关联编码
   */
  private String attachmentCode;
  
  @FieldDesc("附件")
  private List<TSFilesEntity> certificates;// 附件
  
  /**
   * 审查专家
   */
  private String checkExpert;
  
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
   * @return: java.lang.String 著作名称
   */
  @Column(name = "BOOK_NAME", nullable = true, length = 200)
  public java.lang.String getBookName() {
    return this.bookName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           著作名称
   */
  public void setBookName(java.lang.String bookName) {
    this.bookName = bookName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 所属院系
   */
  @Column(name = "SUBORDINATE_DEPT_ID", nullable = true, length = 32)
  public java.lang.String getSubordinateDeptId() {
    return this.subordinateDeptId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           所属院系
   */
  public void setSubordinateDeptId(java.lang.String subordinateDeptId) {
    this.subordinateDeptId = subordinateDeptId;
  }

  @Column(name = "SUBORDINATE_DEPT_NAME", nullable = true, length = 60)
  public java.lang.String getConcreteDeptName() {
    return concreteDeptName;
  }

  public void setConcreteDeptName(java.lang.String concreteDeptName) {
    this.concreteDeptName = concreteDeptName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 具体单位
   */
  @Column(name = "CONCRETE_DEPT_ID", nullable = true, length = 32)
  public java.lang.String getConcreteDeptId() {
    return this.concreteDeptId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           具体单位
   */
  public void setConcreteDeptId(java.lang.String concreteDeptId) {
    this.concreteDeptId = concreteDeptId;
  }

  @Column(name = "CONCRETE_DEPT_NAME", nullable = true, length = 60)
  public java.lang.String getSubordinateDeptName() {
    return subordinateDeptName;
  }

  public void setSubordinateDeptName(java.lang.String subordinateDeptName) {
    this.subordinateDeptName = subordinateDeptName;
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
   * @return: java.lang.String 出版社
   */
  @Column(name = "PRESS", nullable = true, length = 120)
  public java.lang.String getPress() {
    return this.press;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           出版社
   */
  public void setPress(java.lang.String press) {
    this.press = press;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 发行范围
   */
  @Column(name = "ISSUE_SCOPE", nullable = true, length = 200)
  public java.lang.String getIssueScope() {
    return this.issueScope;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           发行范围
   */
  public void setIssueScope(java.lang.String issueScope) {
    this.issueScope = issueScope;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 固定联系电话
   */
  @Column(name = "FIX_TELEPHONE", nullable = true, length = 20)
  public java.lang.String getFixTelephone() {
    return this.fixTelephone;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           固定联系电话
   */
  public void setFixTelephone(java.lang.String fixTelephone) {
    this.fixTelephone = fixTelephone;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 移动联系电话
   */
  @Column(name = "MOBILE_TELEPHONE", nullable = true, length = 20)
  public java.lang.String getMobileTelephone() {
    return this.mobileTelephone;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           移动联系电话
   */
  public void setMobileTelephone(java.lang.String mobileTelephone) {
    this.mobileTelephone = mobileTelephone;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 第一作者ID
   */
  @Column(name = "FIRST_AUTHOR_ID", nullable = true, length = 32)
  public java.lang.String getFirstAuthorId() {
    return this.firstAuthorId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           第一作者ID
   */
  public void setFirstAuthorId(java.lang.String firstAuthorId) {
    this.firstAuthorId = firstAuthorId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 第一作者姓名
   */
  @Column(name = "FIRST_AUTHOR", nullable = true, length = 36)
  public java.lang.String getFirstAuthor() {
    return this.firstAuthor;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           第一作者姓名
   */
  public void setFirstAuthor(java.lang.String firstAuthor) {
    this.firstAuthor = firstAuthor;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 其他作者姓名
   */
  @Column(name = "OTHER_AUTHOR", nullable = true, length = 50)
  public java.lang.String getOtherAuthor() {
    return this.otherAuthor;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           其他作者姓名
   */
  public void setOtherAuthor(java.lang.String otherAuthor) {
    this.otherAuthor = otherAuthor;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 著作内容要点
   */
  @Column(name = "BOOK_CONTENT_KEY", nullable = true, length = 500)
  public java.lang.String getBookContentKey() {
    return this.bookContentKey;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           著作内容要点
   */
  public void setBookContentKey(java.lang.String bookContentKey) {
    this.bookContentKey = bookContentKey;
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
   * @return: java.lang.String 编号
   */
  @Column(name = "REVIEW_NUMBER", nullable = true, length = 50)
  public java.lang.String getReviewNumber() {
    return this.reviewNumber;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           编号
   */
  public void setReviewNumber(java.lang.String reviewNumber) {
    this.reviewNumber = reviewNumber;
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

  @Column(name = "CHECK_FLAG", nullable = true, length = 1)
  public java.lang.String getCheckFlag() {
    return checkFlag;
  }

  public void setCheckFlag(java.lang.String checkFlag) {
    this.checkFlag = checkFlag;
  }

  /**
   * 导出密级代码转换
   * @return
   */
  public String convertGetSecretDegree() {
    return ConvertDicUtil.getConvertDic(CodeTypeConstant.CODE_TYPE_BZ, CodeTypeConstant.XMMJ, this.secretDegree);
  }
  /**
   * 导出审核状态代码转换
   * @return
   */
  public String convertGetCheckFlag() {
    return ConvertDicUtil.getConvertDic(CodeTypeConstant.CODE_TYPE_KY, CodeTypeConstant.SCZT, this.checkFlag);
  }

  @Column(name="attachment_code")
public String getAttachmentCode() {
    return attachmentCode;
}

public void setAttachmentCode(String attachmentCode) {
    this.attachmentCode = attachmentCode;
}

@Column(name="check_expert")
public String getCheckExpert() {
    return checkExpert;
}

public void setCheckExpert(String checkExpert) {
    this.checkExpert = checkExpert;
}
  
  
}
