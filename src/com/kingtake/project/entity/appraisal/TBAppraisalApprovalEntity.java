package com.kingtake.project.entity.appraisal;

import java.math.BigDecimal;
import java.util.List;

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

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;

/**
 * @Title: Entity
 * @Description: 成果鉴定申请审批表
 * @author onlineGenerator
 * @date 2015-09-09 09:43:07
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_appraisa_approval", schema = "")
@LogEntity("成果鉴定申请审批表")
@SuppressWarnings("serial")
public class TBAppraisalApprovalEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键id")
    private java.lang.String id;
    /** 关联鉴定计划信息 */
    private TBAppraisalProjectEntity appraisalProject;
    /** 项目性质 */
    @Excel(name = "项目性质")
    @FieldDesc("项目性质")
    private java.lang.String projectCharacter;
    /** 鉴定条件 */
    @Excel(name = "鉴定条件")
    @FieldDesc("鉴定条件")
    private java.lang.String appraisaCondition;
    /** 开始时间 */
    @Excel(name = "开始时间")
    @FieldDesc("开始时间")
    private java.util.Date beginTime;
    /** 截止时间 */
    @Excel(name = "截止时间")
    @FieldDesc("截止时间")
    private java.util.Date endTime;
    /** 总经费 */
    @Excel(name = "总经费",isAmount = true)
    @FieldDesc("总经费")
    private BigDecimal totalAmount;
    /** 鉴定时间 */
    @Excel(name = "鉴定时间")
    @FieldDesc("鉴定时间")
    private java.util.Date appraisaTime;
    /** 鉴定地点 */
    @Excel(name = "鉴定地点")
    @FieldDesc("鉴定地点")
    private java.lang.String appraisaAddress;
    /** 联系人id */
    @Excel(name = "联系人id")
    @FieldDesc("联系人id")
    private java.lang.String contactUserid;
    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    @FieldDesc("联系人姓名")
    private java.lang.String contactUsername;
    /** 联系电话 */
    @Excel(name = "联系电话")
    @FieldDesc("联系电话")
    private java.lang.String contactPhone;
    /** 创建人 */
    @Excel(name = "创建人")
    @FieldDesc("创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    @FieldDesc("创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间")
    @FieldDesc("创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @Excel(name = "修改人")
    @FieldDesc("修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    @FieldDesc("修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @Excel(name = "修改时间")
    @FieldDesc("修改时间")
    private java.util.Date updateDate;

    /** 审核状态 */
    @Excel(name = "审核状态")
    @FieldDesc("审核状态")
    private String auditStatus;
    /** 完成时间 */
    @Excel(name = "完成时间")
    @FieldDesc("完成时间")
    private java.util.Date finishTime;
    /** 完成人id */
    @Excel(name = "完成人id")
    @FieldDesc("完成人id")
    private java.lang.String finishUserid;
    /** 完成人姓名 */
    @Excel(name = "完成人姓名")
    @FieldDesc("完成人姓名")
    private java.lang.String finishUsername;

    @Excel(name = "驳回记录")
    private TPmApprReceiveLogsEntity backLog;
    @FieldDesc("审批发送记录")
    private List<TPmApprSendLogsEntity> apprSendLogs;
    @FieldDesc("审批记录")
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;

    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

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
     * @return: java.lang.String 项目性质
     */
    @Column(name = "PROJECT_CHARACTER", nullable = true, length = 2)
    public java.lang.String getProjectCharacter() {
        return this.projectCharacter;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目性质
     */
    public void setProjectCharacter(java.lang.String projectCharacter) {
        this.projectCharacter = projectCharacter;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 鉴定条件
     */
    @Column(name = "APPRAISA_CONDITION", nullable = true, length = 2)
    public java.lang.String getAppraisaCondition() {
        return this.appraisaCondition;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 鉴定条件
     */
    public void setAppraisaCondition(java.lang.String appraisaCondition) {
        this.appraisaCondition = appraisaCondition;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 开始时间
     */
    @Column(name = "BEGIN_TIME", nullable = true)
    public java.util.Date getBeginTime() {
        return this.beginTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 开始时间
     */
    public void setBeginTime(java.util.Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 截止时间
     */
    @Column(name = "END_TIME", nullable = true)
    public java.util.Date getEndTime() {
        return this.endTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 截止时间
     */
    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 方法: 取得Double
     *
     * @return: Double 总经费
     */
    @Column(name = "TOTAL_AMOUNT", nullable = true, scale = 2, length = 10)
    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    /**
     * 方法: 设置Double
     *
     * @param: Double 总经费
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 鉴定时间
     */
    @Column(name = "APPRAISA_TIME", nullable = true)
    public java.util.Date getAppraisaTime() {
        return this.appraisaTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 鉴定时间
     */
    public void setAppraisaTime(java.util.Date appraisaTime) {
        this.appraisaTime = appraisaTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 鉴定地点
     */
    @Column(name = "APPRAISA_ADDRESS", nullable = true, length = 100)
    public java.lang.String getAppraisaAddress() {
        return this.appraisaAddress;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 鉴定地点
     */
    public void setAppraisaAddress(java.lang.String appraisaAddress) {
        this.appraisaAddress = appraisaAddress;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人id
     */
    @Column(name = "CONTACT_USERID", nullable = true, length = 32)
    public java.lang.String getContactUserid() {
        return this.contactUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人id
     */
    public void setContactUserid(java.lang.String contactUserid) {
        this.contactUserid = contactUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人姓名
     */
    @Column(name = "CONTACT_USERNAME", nullable = true, length = 30)
    public java.lang.String getContactUsername() {
        return this.contactUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人姓名
     */
    public void setContactUsername(java.lang.String contactUsername) {
        this.contactUsername = contactUsername;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系电话
     */
    @Column(name = "CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系电话
     */
    public void setContactPhone(java.lang.String contactPhone) {
        this.contactPhone = contactPhone;
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

    @ManyToOne
    @JoinColumn(name = "T_B_ID")
    public TBAppraisalProjectEntity getAppraisalProject() {
        return appraisalProject;
    }

    public void setAppraisalProject(TBAppraisalProjectEntity appraisalProject) {
        this.appraisalProject = appraisalProject;
    }

    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "FINISH_TIME", nullable = true)
    public java.util.Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(java.util.Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "FINISH_USERID", nullable = true, length = 32)
    public java.lang.String getFinishUserid() {
        return finishUserid;
    }

    public void setFinishUserid(java.lang.String finishUserid) {
        this.finishUserid = finishUserid;
    }

    @Column(name = "FINISH_USERNAME", nullable = true, length = 100)
    public java.lang.String getFinishUsername() {
        return finishUsername;
    }

    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BACK_ID")
    public TPmApprReceiveLogsEntity getBackLog() {
        return backLog;
    }

    public void setBackLog(TPmApprReceiveLogsEntity backLog) {
        this.backLog = backLog;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "APPR_ID")
    public List<TPmApprSendLogsEntity> getApprSendLogs() {
        return apprSendLogs;
    }

    public void setApprSendLogs(List<TPmApprSendLogsEntity> apprSendLogs) {
        this.apprSendLogs = apprSendLogs;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "APPR_ID")
    public List<TPmApprReceiveLogsEntity> getApprReceiveLogs() {
        return apprReceiveLogs;
    }

    public void setApprReceiveLogs(List<TPmApprReceiveLogsEntity> apprReceiveLogs) {
        this.apprReceiveLogs = apprReceiveLogs;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bid")
    @OrderBy("createdate")
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

}
