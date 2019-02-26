package com.kingtake.project.entity.declare;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 项目申报
 * @author onlineGenerator
 * @date 2015-07-21 17:00:42
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_pm_declare", schema = "")
@SuppressWarnings("serial")
@LogEntity("项目申报书管理")
public class TPmDeclareEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 项目基_主键 */
    private java.lang.String tpId;
    /** 研究时限开始时间 */
    @FieldDesc("研究时限开始时间")
    @Excel(name = "研究时限开始时间", format = "yyyy-MM-dd")
    private java.util.Date beginDate;
    /** 研究时限结束时间 */
    @FieldDesc("研究时限结束时间")
    @Excel(name = "研究时限结束时间", format = "yyyy-MM-dd")
    private java.util.Date endDate;
    /** 项目来源 */
    @FieldDesc("项目来源")
    @Excel(name = "项目来源")
    private java.lang.String projectSrc;
    /** 主要研究内容 */
    @FieldDesc("主要研究内容")
    @Excel(name = "主要研究内容")
    private java.lang.String researchContent;
    /** 研究进度及成果形式 */
    @FieldDesc("研究进度及成果形式")
    @Excel(name = "研究进度及成果形式")
    private java.lang.String scheduleAndAchieve;

    /**
     * 所属项目
     */
    private TPmProjectEntity project;

    /**
     * 项目名称
     */
//    @Excel(name = "所属项目")
//    private String projectName;

    /**
     * 流程实例id
     */
    private String processInstId;
    
    /**
     * 院系审核标志，1表示已审核，0表示未审核
     */
    private String departAudited;
    
    /**
     * 附件关联
     */
    private String attachmentCode;

    /** 流程流转状态 */
    @Excel(name = "流程流转状态", isExportConvert = true)
    private java.lang.String bpmStatus;
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
     * 新增字段，zhangls，2016-03-08
     */
    @FieldDesc("密级")
    @Excel(name = "密级")
    private String secretCode;
    
    @FieldDesc("项目编号")
    @Excel(name = "项目编号")
    private String projectNo;
    
    @FieldDesc("项目名称")
    @Excel(name = "项目名称")
    private String projectName;
    
    @FieldDesc("项目类型")
    private String projectTypeId;
    
    @FieldDesc("指南id")
    private String guidId;
    
    @FieldDesc("指南")
    private String guidName;
    
    @FieldDesc("建设单位id")
    private String buildUnitId;
    
    @FieldDesc("建设单位")
    @Excel(name = "建设单位")
    private String buildUnitName;

    @FieldDesc("编制日期")
    @Excel(name = "编制日期", format = "yyyy-MM-dd")
    private java.util.Date applyDate;
    
    @FieldDesc("通信地址")
    @Excel(name = "通信地址")
    private String mailingAddress;
    
    @FieldDesc("邮政编码")
    @Excel(name = "邮政编码")
    private String postalCode;
    
    @FieldDesc("负责人id")
    private String projectManagerId;
    
    @FieldDesc("负责人")
    @Excel(name = "负责人")
    private String projectManagerName;
    
    @FieldDesc("分管单位id")
    private String branchUnitId;
    
    @FieldDesc("分管单位")
    @Excel(name = "分管单位")
    private String branchUnitName;
    
    @FieldDesc("合作单位")
    @Excel(name = "合作单位")
    private String coorperationUnit;
    
    @FieldDesc("联系电话")
    @Excel(name = "联系电话")
    private String contactPhone;
    
    @FieldDesc("建议等级")
    @Excel(name = "建议等级")
    private String suggestLevel;
    
    @FieldDesc("建设地点")
    @Excel(name = "建设地点")
    private String constructionAddr;

    /**
     * 任务id
     */
    private String taskId;
    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主键
     */
    

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;
    
    /**
     * 计划状态，1 已通过 ，2 已驳回 ，3 重新提交
     */
    private String planStatus;
    
    
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
     * @param: java.lang.String 主键
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 研究时限开始时间
     */
    @Column(name = "BEGIN_DATE", nullable = true)
    public java.util.Date getBeginDate() {
        return this.beginDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 研究时限开始时间
     */
    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 研究时限结束时间
     */
    @Column(name = "END_DATE", nullable = true)
    public java.util.Date getEndDate() {
        return this.endDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 研究时限结束时间
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目来源
     */
    @Column(name = "PROJECT_SRC", nullable = true, length = 100)
    public java.lang.String getProjectSrc() {
        return this.projectSrc;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目来源
     */
    public void setProjectSrc(java.lang.String projectSrc) {
        this.projectSrc = projectSrc;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主要研究内容
     */
    @Column(name = "RESEARCH_CONTENT", nullable = true, length = 2000)
    public java.lang.String getResearchContent() {
        return this.researchContent;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 主要研究内容
     */
    public void setResearchContent(java.lang.String researchContent) {
        this.researchContent = researchContent;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 研究进度及成果形式
     */
    @Column(name = "SCHEDULE_AND_ACHIEVE", nullable = true, length = 2000)
    public java.lang.String getScheduleAndAchieve() {
        return this.scheduleAndAchieve;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 研究进度及成果形式
     */
    public void setScheduleAndAchieve(java.lang.String scheduleAndAchieve) {
        this.scheduleAndAchieve = scheduleAndAchieve;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 流程流转状态
     */
    @Column(name = "BPM_STATUS", nullable = true, length = 1)
    public java.lang.String getBpmStatus() {
        return this.bpmStatus;
    }

    public java.lang.String convertGetBpmStatus() {
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        String sql = "select t.typename from t_s_type t join t_s_typegroup g on t.typegroupid=g.id where g.typegroupcode='bpm_status' and t.typecode=?";
        Map<String, Object> map = systemService.findOneForJdbc(sql, this.bpmStatus);
        if (map.size() > 0) {
            return (String) map.get("TYPENAME");
        }
        return "";
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 流程流转状态
     */
    public void setBpmStatus(java.lang.String bpmStatus) {
        this.bpmStatus = bpmStatus;
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
     * @param: java.lang.String 创建人
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
     * @param: java.lang.String 创建人姓名
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
     * @param: java.util.Date 创建时间
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
     * @param: java.lang.String 修改人
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
     * @param: java.lang.String 修改人姓名
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
     * @param: java.util.Date 修改时间
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public java.lang.String getTpId() {
        return tpId;
    }

    public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_P_ID")
    @JsonIgnore
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @Column(name = "PROJECT_NAME")
    public String getProjectName() {
        /*String projectNameStr = "";
        if (this.project != null) {
            projectNameStr = this.project.getProjectName();
        }
        return projectNameStr;*/
      return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Transient
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @Column(name = "DEPART_AUDITED")
    public String getDepartAudited() {
        return departAudited;
    }

    public void setDepartAudited(String departAudited) {
        this.departAudited = departAudited;
    }

    @Transient
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(name = "SECRET_CODE")
    public String getSecretCode() {
      return secretCode;
    }

    public void setSecretCode(String secretCode) {
      this.secretCode = secretCode;
    }

    @Column(name = "PROJECT_NO")
    public String getProjectNo() {
      return projectNo;
    }

    public void setProjectNo(String projectNo) {
      this.projectNo = projectNo;
    }

    @Column(name = "PROJECT_TYPE_ID")
    public String getProjectTypeId() {
      return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
      this.projectTypeId = projectTypeId;
    }

    @Column(name = "GUID_ID")
    public String getGuidId() {
      return guidId;
    }

    public void setGuidId(String guidId) {
      this.guidId = guidId;
    }

    @Column(name = "GUID_NAME")
    public String getGuidName() {
      return guidName;
    }

    public void setGuidName(String guidName) {
      this.guidName = guidName;
    }

    @Column(name = "BUILD_UNIT_ID")
    public String getBuildUnitId() {
      return buildUnitId;
    }

    public void setBuildUnitId(String buildUnitId) {
      this.buildUnitId = buildUnitId;
    }

    @Column(name = "BUILD_UNIT_NAME")
    public String getBuildUnitName() {
      return buildUnitName;
    }

    public void setBuildUnitName(String buildUnitName) {
      this.buildUnitName = buildUnitName;
    }

    @Column(name = "APPLY_DATE")
    public java.util.Date getApplyDate() {
      return applyDate;
    }

    public void setApplyDate(java.util.Date applyDate) {
      this.applyDate = applyDate;
    }

    @Column(name = "MAILING_ADDRESS")
    public String getMailingAddress() {
      return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
      this.mailingAddress = mailingAddress;
    }

    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
      return postalCode;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }

    @Column(name = "PROJECT_MANAGER_ID")
    public String getProjectManagerId() {
      return projectManagerId;
    }

    public void setProjectManagerId(String projectManagerId) {
      this.projectManagerId = projectManagerId;
    }

    @Column(name = "PROJECT_MANAGER_NAME")
    public String getProjectManagerName() {
      return projectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
      this.projectManagerName = projectManagerName;
    }

    @Column(name = "BRANCH_UNIT_ID")
    public String getBranchUnitId() {
      return branchUnitId;
    }

    public void setBranchUnitId(String branchUnitId) {
      this.branchUnitId = branchUnitId;
    }

    @Column(name = "BRANCH_UNIT_NAME")
    public String getBranchUnitName() {
      return branchUnitName;
    }

    public void setBranchUnitName(String branchUnitName) {
      this.branchUnitName = branchUnitName;
    }

    @Column(name = "COORPERATION_UNIT")
    public String getCoorperationUnit() {
      return coorperationUnit;
    }

    public void setCoorperationUnit(String coorperationUnit) {
      this.coorperationUnit = coorperationUnit;
    }

    @Column(name = "CONTACT_PHONE")
    public String getContactPhone() {
      return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
      this.contactPhone = contactPhone;
    }

    @Column(name = "SUGGEST_LEVEL")
    public String getSuggestLevel() {
      return suggestLevel;
    }

    public void setSuggestLevel(String suggestLevel) {
      this.suggestLevel = suggestLevel;
    }

    @Column(name = "CONSTRUCTION_ADDR")
    public String getConstructionAddr() {
      return constructionAddr;
    }

    public void setConstructionAddr(String constructionAddr) {
      this.constructionAddr = constructionAddr;
    }
    
    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name = "PLAN_STATUS")
    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    
}
