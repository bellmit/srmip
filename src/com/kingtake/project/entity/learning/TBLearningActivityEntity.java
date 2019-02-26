package com.kingtake.project.entity.learning;

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 学术活动信息表
 * @author onlineGenerator
 * @date 2015-12-06 10:52:32
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_learning_activity", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
public class TBLearningActivityEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 活动名称 */
    @Excel(name = "名称")
    private java.lang.String activityName;
    /** 活动级别 */
    @Excel(name = "级别")
    private java.lang.String activityLevel;
    /** 主办单位 */
    @Excel(name = "主办单位")
    private java.lang.String hostUnit;
    /** 举办单位 */
    @Excel(name = "承办单位")
    private java.lang.String holdingUnit;
    /** 举办地点 */
    @Excel(name = "举办地点")
    private java.lang.String address;
    /** 活动规模 */
    @Excel(name = "活动规模")
    private java.lang.String activityScope;
    /** 参加人员姓名 */
    @Excel(name = "参加人员姓名")
    private java.lang.String attendeeName;
    /** 活动时间 */
    @Excel(name = "活动时间", format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date activityTime;
    /** 担任职务 */
    @Excel(name = "担任职务")
    private java.lang.String heldJob;
    /** 活动内容 */
    @Excel(name = "活动内容")
    private java.lang.String activityContent;
    /** 备注 */
    @Excel(name = "备注")
    private java.lang.String memo;
    /**
     * 流程实例id
     */
    private String processInstId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 分配人
     */
    private String assigneeName;

    /**
     * 流程审批状态
     */
    private String bpmStatus;

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
     * 上传附件
     */
    private List<TSFilesEntity> uploadFiles;
    
    /**
     * 附件编码
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
     * @param: java.lang.String 主键
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 活动名称
     */
    @Column(name = "ACTIVITY_NAME", nullable = true, length = 120)
    public java.lang.String getActivityName() {
        return this.activityName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 活动名称
     */
    public void setActivityName(java.lang.String activityName) {
        this.activityName = activityName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 活动级别
     */
    @Column(name = "ACTIVITY_LEVEL", nullable = true, length = 2)
    public java.lang.String getActivityLevel() {
        return this.activityLevel;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 活动级别
     */
    public void setActivityLevel(java.lang.String activityLevel) {
        this.activityLevel = activityLevel;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 举办单位
     */
    @Column(name = "HOLDING_UNIT", nullable = true, length = 120)
    public java.lang.String getHoldingUnit() {
        return this.holdingUnit;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 举办单位
     */
    public void setHoldingUnit(java.lang.String holdingUnit) {
        this.holdingUnit = holdingUnit;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主办单位
     */
    @Column(name = "HOST_UNIT", nullable = true, length = 120)
    public java.lang.String getHostUnit() {
        return this.hostUnit;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 主办单位
     */
    public void setHostUnit(java.lang.String hostUnit) {
        this.hostUnit = hostUnit;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 举办地点
     */
    @Column(name = "ADDRESS", nullable = true, length = 100)
    public java.lang.String getAddress() {
        return this.address;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 举办地点
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 活动规模
     */
    @Column(name = "ACTIVITY_SCOPE", nullable = true, length = 20)
    public java.lang.String getActivityScope() {
        return this.activityScope;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 活动规模
     */
    public void setActivityScope(java.lang.String activityScope) {
        this.activityScope = activityScope;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 参加人员姓名
     */
    @Column(name = "ATTENDEE_NAME")
    public java.lang.String getAttendeeName() {
        return this.attendeeName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 参加人员姓名
     */
    public void setAttendeeName(java.lang.String attendeeName) {
        this.attendeeName = attendeeName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 担任职务
     */
    @Column(name = "HELD_JOB", nullable = true, length = 2)
    public java.lang.String getHeldJob() {
        return this.heldJob;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 担任职务
     */
    public void setHeldJob(java.lang.String heldJob) {
        this.heldJob = heldJob;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 活动内容
     */
    @Column(name = "ACTIVITY_CONTENT", nullable = true, length = 2000)
    public java.lang.String getActivityContent() {
        return this.activityContent;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 活动内容
     */
    public void setActivityContent(java.lang.String activityContent) {
        this.activityContent = activityContent;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 备注
     */
    @Column(name = "MEMO", nullable = true, length = 1000)
    public java.lang.String getMemo() {
        return this.memo;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 备注
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }

    @Column(name = "bpm_status")
    public String getBpmStatus() {
        return bpmStatus;
    }

    public void setBpmStatus(String bpmStatus) {
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
    public List<TSFilesEntity> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<TSFilesEntity> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    @Transient
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @Transient
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Transient
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Transient
    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    @Column(name = "activity_time")
    public java.util.Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(java.util.Date activityTime) {
        this.activityTime = activityTime;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

}
