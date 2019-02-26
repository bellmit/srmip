package com.kingtake.project.entity.task;

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
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 任务节点管理
 * @author onlineGenerator
 * @date 2015-07-21 14:04:20
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_pm_task_node", schema = "")
@SuppressWarnings("serial")
@LogEntity("任务节点信息")
public class TPmTaskNodeEntity implements java.io.Serializable {
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 项目任_主键 */
    @FieldDesc("任务_主键")
    private java.lang.String tpId;
    /** 计划时间 */
    @FieldDesc("开始时间")
    @Excel(name = "开始时间")
    private java.util.Date planTime;
    /** 计划结束时间 */
    @FieldDesc("结束时间")
    @Excel(name = "结束时间")
    private java.util.Date endTime;
    /** 完成时间 */
    @FieldDesc("完成时间")
    @Excel(name = "完成时间")
    private java.util.Date finishTime;
    
    /**
     * 
     * 任务节点名称
     */
    private String taskNodeName;
    /** 任务内容 */
    @FieldDesc("任务内容")
    @Excel(name = "任务内容")
    private java.lang.String taskContent;
    /** 计划处检查标记 */
    @FieldDesc("计划处检查标记")
    @Excel(name = "计划处检查标记")
    private java.lang.String planCheckFlag;
    /** 计划处检查人id */
    @FieldDesc("计划处检查人id")
    private java.lang.String planCheckUserid;
    /** 计划处检查人姓名 */
    @FieldDesc("计划处检查人姓名")
    @Excel(name = "计划处检查人姓名")
    private java.lang.String planCheckUsername;
    /** 计划处检查时间 */
    @FieldDesc("计划处检查时间")
    @Excel(name = "计划处检查时间")
    private java.util.Date planCheckTie;
    /** 质量办检查标记 */
    @FieldDesc("质量办检查标记")
    @Excel(name = "质量办检查标记")
    private java.lang.String qualityCheckFlag;
    /** 质量办检查人id */
    @FieldDesc("质量办检查人id")
    private java.lang.String qualityCheckUserid;
    /** 质量办检查人姓名 */
    @FieldDesc("质量办检查人姓名")
    @Excel(name = "质量办检查人姓名")
    private java.lang.String qualityCheckUsername;
    /** 质量办检查时间 */
    @FieldDesc("质量办检查时间")
    @Excel(name = "质量办检查时间")
    private java.util.Date qualityCheckTime;
    /** 完成标志 */
    @FieldDesc("完成标志")
    @Excel(name = "完成标志")
    private String finishFlag;
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注")
    private String remark;
    
    /**
     * 成果形式
     */
    private String cgxs;
    /**
     * 评价方法
     */
    private String pjff;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;
    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    private java.lang.String createName;
    /** 创建时间 */
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /**
     * 审批状态
     */
    private String operationStatus;

    private String finishUserid;

    private String finishUsername;
    
    /**
     * 附件关联编码
     */
    private String attachmentCode;

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
     * @return: java.lang.String 项目任_主键
     */
    @Column(name = "T_P_ID", nullable = true, length = 32)
    public java.lang.String getTpId() {
        return this.tpId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目任_主键
     */
    public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 计划时间
     */
    @Column(name = "PLAN_TIME", nullable = true)
    public java.util.Date getPlanTime() {
        return this.planTime;
    }

    @Transient
    public String getPlanTimeStr() {
        if (this.planTime != null) {
            return DateUtils.formatDate(this.planTime, "yyyy-MM-dd");
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 计划时间
     */
    public void setPlanTime(java.util.Date planTime) {
        this.planTime = planTime;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 计划时间
     */
    @Column(name = "END_TIME", nullable = true)
    public java.util.Date getEndTime() {
        return endTime;
    }
    
    @Transient
    public String getEndTimeStr() {
        if (this.endTime != null) {
            return DateUtils.formatDate(this.endTime, "yyyy-MM-dd");
        } else {
            return "";
        }
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 完成时间
     */
    @Column(name = "FINISH_TIME", nullable = true)
    public java.util.Date getFinishTime() {
        return this.finishTime;
    }

    @Transient
    public String getFinishTimeStr() {
        if (this.finishTime != null) {
            return DateUtils.formatDate(this.finishTime, "yyyy-MM-dd");
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 完成时间
     */
    public void setFinishTime(java.util.Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 任务内容
     */
    @Column(name = "TASK_CONTENT", nullable = true, length = 400)
    public java.lang.String getTaskContent() {
        return this.taskContent;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 任务内容
     */
    public void setTaskContent(java.lang.String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 计划处检查标记
     */
    @Column(name = "PLAN_CHECK_FLAG", nullable = true, length = 1)
    public java.lang.String getPlanCheckFlag() {
        return this.planCheckFlag;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 计划处检查标记
     */
    public void setPlanCheckFlag(java.lang.String planCheckFlag) {
        this.planCheckFlag = planCheckFlag;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 计划处检查人id
     */
    @Column(name = "PLAN_CHECK_USERID", nullable = true, length = 32)
    public java.lang.String getPlanCheckUserid() {
        return this.planCheckUserid;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 计划处检查人id
     */
    public void setPlanCheckUserid(java.lang.String planCheckUserid) {
        this.planCheckUserid = planCheckUserid;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 计划处检查人姓名
     */
    @Column(name = "PLAN_CHECK_USERNAME", nullable = true, length = 50)
    public java.lang.String getPlanCheckUsername() {
        return this.planCheckUsername;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 计划处检查人姓名
     */
    public void setPlanCheckUsername(java.lang.String planCheckUsername) {
        this.planCheckUsername = planCheckUsername;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 计划处检查时间
     */
    @Column(name = "PLAN_CHECK_TIE", nullable = true)
    public java.util.Date getPlanCheckTie() {
        return this.planCheckTie;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 计划处检查时间
     */
    public void setPlanCheckTie(java.util.Date planCheckTie) {
        this.planCheckTie = planCheckTie;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 质量办检查标记
     */
    @Column(name = "QUALITY_CHECK_FLAG", nullable = true, length = 1)
    public java.lang.String getQualityCheckFlag() {
        return this.qualityCheckFlag;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 质量办检查标记
     */
    public void setQualityCheckFlag(java.lang.String qualityCheckFlag) {
        this.qualityCheckFlag = qualityCheckFlag;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 质量办检查人id
     */
    @Column(name = "QUALITY_CHECK_USERID", nullable = true, length = 32)
    public java.lang.String getQualityCheckUserid() {
        return this.qualityCheckUserid;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 质量办检查人id
     */
    public void setQualityCheckUserid(java.lang.String qualityCheckUserid) {
        this.qualityCheckUserid = qualityCheckUserid;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 质量办检查人姓名
     */
    @Column(name = "QUALITY_CHECK_USERNAME", nullable = true, length = 50)
    public java.lang.String getQualityCheckUsername() {
        return this.qualityCheckUsername;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 质量办检查人姓名
     */
    public void setQualityCheckUsername(java.lang.String qualityCheckUsername) {
        this.qualityCheckUsername = qualityCheckUsername;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 质量办检查时间
     */
    @Column(name = "QUALITY_CHECK_TIME", nullable = true)
    public java.util.Date getQualityCheckTime() {
        return this.qualityCheckTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 质量办检查时间
     */
    public void setQualityCheckTime(java.util.Date qualityCheckTime) {
        this.qualityCheckTime = qualityCheckTime;
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

    public String getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(String finishFlag) {
        this.finishFlag = finishFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name = "OPERATION_STATUS")
    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    @Column(name = "finish_user_id")
    public String getFinishUserid() {
        return finishUserid;
    }

    public void setFinishUserid(String finishUserid) {
        this.finishUserid = finishUserid;
    }

    @Column(name = "finish_user_name")
    public String getFinishUsername() {
        return finishUsername;
    }

    public void setFinishUsername(String finishUsername) {
        this.finishUsername = finishUsername;
    }

    @Column(name="task_node_name")
    public String getTaskNodeName() {
        return taskNodeName;
    }

    public void setTaskNodeName(String taskNodeName) {
        this.taskNodeName = taskNodeName;
    }

    @Column(name="cgxs")
    public String getCgxs() {
        return cgxs;
    }

    public void setCgxs(String cgxs) {
        this.cgxs = cgxs;
    }

    @Column(name="pjff")
    public String getPjff() {
        return pjff;
    }

    public void setPjff(String pjff) {
        this.pjff = pjff;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
    
}
