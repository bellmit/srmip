package com.kingtake.office.entity.borrowbill;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_o_borrow_bill", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TOBorrowBillEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private java.lang.String id;

    /**
     * 公文标题
     */
    private String title;

    /**
     * 公文编号
     */
    private String fileNum;

    /**
     * 发文单位
     */
    private String sendUnit;

    /**
     * 发文开始时间
     */
    private Date sendBeginTime;

    /**
     * 发文结束时间
     */
    private Date sendEndTime;

    /**
     * 申请人名称
     */
    private String applyUserName;

    /**
     * 申请人id
     */
    private String applyUserId;

    /**
     * 承研单位id
     */
    private String undertakeUnitId;

    /**
     * 承研单位名称
     */
    private String undertakeUnitName;

    /**
     * 责任部门id
     */
    private String dutyUnitId;

    /**
     * 责任部门名称
     */
    private String dutyUnitName;

    /**
     * 借阅理由
     */
    private String borrowReason;

    /**
     * 借阅开始时间
     */
    private Date borrowBeginTime;

    /**
     * 借阅结束时间
     */
    private Date borrowEndTime;

    /**
     * 借阅单位id
     */
    private String borrowUnitId;

    /**
     * 借阅单位名称
     */
    private String borrowUnitName;

    /**
     * 审批状态,0 未提交，1 已提交，2 通过，3 驳回修改
     */
    private String auditStatus;

    /**
     * 修改意见
     */
    private String msgText;

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

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "filenum")
    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    @Column(name = "send_unit")
    public String getSendUnit() {
        return sendUnit;
    }

    public void setSendUnit(String sendUnit) {
        this.sendUnit = sendUnit;
    }

    @Column(name = "send_begin_time")
    public Date getSendBeginTime() {
        return sendBeginTime;
    }

    public void setSendBeginTime(Date sendBeginTime) {
        this.sendBeginTime = sendBeginTime;
    }

    @Column(name = "send_end_time")
    public Date getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(Date sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    @Column(name = "apply_user_name")
    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    @Column(name = "apply_user_id")
    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    @Column(name = "undertake_unit_id")
    public String getUndertakeUnitId() {
        return undertakeUnitId;
    }

    public void setUndertakeUnitId(String undertakeUnitId) {
        this.undertakeUnitId = undertakeUnitId;
    }

    @Column(name = "undertake_unit_name")
    public String getUndertakeUnitName() {
        return undertakeUnitName;
    }

    public void setUndertakeUnitName(String undertakeUnitName) {
        this.undertakeUnitName = undertakeUnitName;
    }

    @Column(name = "duty_unit_id")
    public String getDutyUnitId() {
        return dutyUnitId;
    }

    public void setDutyUnitId(String dutyUnitId) {
        this.dutyUnitId = dutyUnitId;
    }

    @Column(name = "duty_unit_name")
    public String getDutyUnitName() {
        return dutyUnitName;
    }

    public void setDutyUnitName(String dutyUnitName) {
        this.dutyUnitName = dutyUnitName;
    }

    @Column(name = "borrow_reason")
    public String getBorrowReason() {
        return borrowReason;
    }

    public void setBorrowReason(String borrowReason) {
        this.borrowReason = borrowReason;
    }

    @Column(name = "borrow_begin_time")
    public Date getBorrowBeginTime() {
        return borrowBeginTime;
    }

    public void setBorrowBeginTime(Date borrowBeginTime) {
        this.borrowBeginTime = borrowBeginTime;
    }

    @Column(name = "borrow_end_time")
    public Date getBorrowEndTime() {
        return borrowEndTime;
    }

    public void setBorrowEndTime(Date borrowEndTime) {
        this.borrowEndTime = borrowEndTime;
    }

    @Column(name = "borrow_unit_id")
    public String getBorrowUnitId() {
        return borrowUnitId;
    }

    public void setBorrowUnitId(String borrowUnitId) {
        this.borrowUnitId = borrowUnitId;
    }

    @Column(name = "borrow_unit_name")
    public String getBorrowUnitName() {
        return borrowUnitName;
    }

    public void setBorrowUnitName(String borrowUnitName) {
        this.borrowUnitName = borrowUnitName;
    }

    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "msg_text")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public void setId(java.lang.String id) {
        this.id = id;
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


}
