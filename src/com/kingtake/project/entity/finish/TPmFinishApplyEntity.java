package com.kingtake.project.entity.finish;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 项目结题申请信息表
 * @author onlineGenerator
 * @date 2016-03-05 15:23:05
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_finish_apply", schema = "")
@SuppressWarnings("serial")
@LogEntity("项目结题申请信息表")
public class TPmFinishApplyEntity implements java.io.Serializable {
  /** 主键 */
  private java.lang.String id;
  /** 项目基本信息表_主键 */
  private java.lang.String projectId;
  /** 项目实体 */
  private TPmProjectEntity project;
  /** 结题时间 */
  @Excel(name = "结题时间")
    @FieldDesc("结题时间")
  private java.util.Date finishDate;
  /** 项目名称 */
  @Excel(name = "项目名称")
    @FieldDesc("项目名称")
  private java.lang.String projectName;
  /** 项目来源 */
  @Excel(name = "项目来源")
    @FieldDesc("项目来源")
  private java.lang.String sourceUnit;
  /** 起始年度 */
  @Excel(name = "起始年度")
    @FieldDesc("起始年度")
  private java.lang.String beginYear;
  /** 截止年度 */
  @Excel(name = "截止年度")
    @FieldDesc("截止年度")
  private java.lang.String endYear;
  /** 经费性质 */
    @FieldDesc("经费性质")
  private java.lang.String feeType;
  @Excel(name = "经费性质")
    @FieldDesc("经费性质")
  private java.lang.String feeTypeName;
  /** 研究单位id */
  @Excel(name = "研究单位id")
    @FieldDesc("研究单位id")
  private java.lang.String developerDepartId;
  /** 研究单位名称 */
  @Excel(name = "研究单位名称")
    @FieldDesc("研究单位名称")
  private java.lang.String developerDepartName;
  /** 负责人id */
  @Excel(name = "负责人id")
    @FieldDesc("负责人id")
  private java.lang.String projectManagerId;
  /** 负责人姓名 */
  @Excel(name = "负责人姓名")
    @FieldDesc("负责人姓名")
  private java.lang.String projectManager;
  /** 总经费 */
  @Excel(name = "总经费",isAmount = true)
    @FieldDesc("总经费")
  private java.math.BigDecimal allFee;
  /** 剩余经费 */
  @Excel(name = "剩余经费",isAmount = true)
    @FieldDesc("剩余经费")
  private java.math.BigDecimal extraFee;
  /** 项目完成情况 */
  @Excel(name = "项目完成情况")
    @FieldDesc("项目完成情况")
  private java.lang.String projectFinishInfo;
  /** 剩余经费处理意见 */
  @Excel(name = "剩余经费处理意见")
    @FieldDesc("剩余经费处理意见")
  private java.lang.String extraFeeSuggestion;
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

  /** 与审批相关字段 */
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

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;
    
    /**
     * 关联附件
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
   * @return: java.lang.String 项目基本信息表_主键
   */
  @Transient
  public java.lang.String getProjectId() {
    return this.projectId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           项目基本信息表_主键
   */
  public void setProjectId(java.lang.String projectId) {
    this.projectId = projectId;
  }

  /**
   * 方法: 取得java.util.Date
   * 
   * @return: java.util.Date 结题时间
   */
  @Column(name = "FINISH_DATE", nullable = true)
  public java.util.Date getFinishDate() {
    return this.finishDate;
  }

  /**
   * 方法: 设置java.util.Date
   * 
   * @param: java.util.Date
   *           结题时间
   */
  public void setFinishDate(java.util.Date finishDate) {
    this.finishDate = finishDate;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 项目名称
   */
  @Column(name = "PROJECT_NAME", nullable = true, length = 100)
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
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 项目来源
   */
  @Column(name = "SOURCE_UNIT", nullable = true, length = 60)
  public java.lang.String getSourceUnit() {
    return this.sourceUnit;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           项目来源
   */
  public void setSourceUnit(java.lang.String sourceUnit) {
    this.sourceUnit = sourceUnit;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 起始年度
   */
  @Column(name = "BEGIN_YEAR", nullable = true, length = 4)
  public java.lang.String getBeginYear() {
    return this.beginYear;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           起始年度
   */
  public void setBeginYear(java.lang.String beginYear) {
    this.beginYear = beginYear;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 截止年度
   */
  @Column(name = "END_YEAR", nullable = true, length = 4)
  public java.lang.String getEndYear() {
    return this.endYear;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           截止年度
   */
  public void setEndYear(java.lang.String endYear) {
    this.endYear = endYear;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 经费性质
   */
  @Column(name = "FEE_TYPE", nullable = true, length = 32)
  public java.lang.String getFeeType() {
    return this.feeType;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           经费性质
   */
  public void setFeeType(java.lang.String feeType) {
    this.feeType = feeType;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 研究单位id
   */
  @Column(name = "DEVELOPER_DEPART_ID", nullable = true, length = 32)
  public java.lang.String getDeveloperDepartId() {
    return this.developerDepartId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           研究单位id
   */
  public void setDeveloperDepartId(java.lang.String developerDepartId) {
    this.developerDepartId = developerDepartId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 研究单位名称
   */
  @Column(name = "DEVELOPER_DEPART_NAME", nullable = true, length = 60)
  public java.lang.String getDeveloperDepartName() {
    return this.developerDepartName;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           研究单位名称
   */
  public void setDeveloperDepartName(java.lang.String developerDepartName) {
    this.developerDepartName = developerDepartName;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 负责人id
   */
  @Column(name = "PROJECT_MANAGER_ID", nullable = true, length = 32)
  public java.lang.String getProjectManagerId() {
    return this.projectManagerId;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           负责人id
   */
  public void setProjectManagerId(java.lang.String projectManagerId) {
    this.projectManagerId = projectManagerId;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 负责人姓名
   */
  @Column(name = "PROJECT_MANAGER", nullable = true, length = 36)
  public java.lang.String getProjectManager() {
    return this.projectManager;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           负责人姓名
   */
  public void setProjectManager(java.lang.String projectManager) {
    this.projectManager = projectManager;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 总经费
   */
  @Column(name = "ALL_FEE", nullable = true, scale = 2, length = 13)
  public java.math.BigDecimal getAllFee() {
    return this.allFee;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           总经费
   */
  public void setAllFee(java.math.BigDecimal allFee) {
    this.allFee = allFee;
  }

  /**
   * 方法: 取得java.math.BigDecimal
   * 
   * @return: java.math.BigDecimal 剩余经费
   */
  @Column(name = "EXTRA_FEE", nullable = true, scale = 2, length = 13)
  public java.math.BigDecimal getExtraFee() {
    return this.extraFee;
  }

  /**
   * 方法: 设置java.math.BigDecimal
   * 
   * @param: java.math.BigDecimal
   *           剩余经费
   */
  public void setExtraFee(java.math.BigDecimal extraFee) {
    this.extraFee = extraFee;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 项目完成情况
   */
  @Column(name = "PROJECT_FINISH_INFO", nullable = true, length = 800)
  public java.lang.String getProjectFinishInfo() {
    return this.projectFinishInfo;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           项目完成情况
   */
  public void setProjectFinishInfo(java.lang.String projectFinishInfo) {
    this.projectFinishInfo = projectFinishInfo;
  }

  /**
   * 方法: 取得java.lang.String
   * 
   * @return: java.lang.String 剩余经费处理意见
   */
  @Column(name = "EXTRA_FEE_SUGGESTION", nullable = true, length = 800)
  public java.lang.String getExtraFeeSuggestion() {
    return this.extraFeeSuggestion;
  }

  /**
   * 方法: 设置java.lang.String
   * 
   * @param: java.lang.String
   *           剩余经费处理意见
   */
  public void setExtraFeeSuggestion(java.lang.String extraFeeSuggestion) {
    this.extraFeeSuggestion = extraFeeSuggestion;
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

  @ManyToOne
  @JsonIgnore(true)
  @JoinColumn(name = "PROJECT_ID")
  public TPmProjectEntity getProject() {
    return project;
  }

  public void setProject(TPmProjectEntity project) {
    this.project = project;
  }

  @Transient
  public java.lang.String getFeeTypeName() {
    return feeTypeName;
  }

  public void setFeeTypeName(java.lang.String feeTypeName) {
    this.feeTypeName = feeTypeName;
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

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
    
}
