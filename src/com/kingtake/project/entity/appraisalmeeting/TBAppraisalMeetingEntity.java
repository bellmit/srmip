package com.kingtake.project.entity.appraisalmeeting;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;

/**
 * @Title: Entity
 * @Description: 鉴定会信息表
 * @author onlineGenerator
 * @date 2016-01-22 14:26:13
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_appraisal_meeting", schema = "")
@SuppressWarnings("serial")
public class TBAppraisalMeetingEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 鉴定计划主键 */
    private java.lang.String applyId;
    /** 鉴定会时间 */
    @Excel(name = "鉴定会时间")
    private java.util.Date meetingDate;
    /** 鉴定会地点 */
    @Excel(name = "鉴定会地点")
    private java.lang.String meetingAddr;
    /** 联系人 */
    @Excel(name = "联系人")
    private java.lang.String contactName;
    /** 联系电话 */
    @Excel(name = "联系电话")
    private java.lang.String contactPhone;
    /** 报到时间 */
    @Excel(name = "报到时间")
    private java.util.Date registerTime;
    /** 报到地点 */
    @Excel(name = "报到地点")
    private java.lang.String registerAddr;
    /** 主持单位id */
    private java.lang.String hostDepartid;
    /** 主持单位 */
    @Excel(name = "主持单位")
    private java.lang.String hostDepartname;
    /** 通知编号 */
    @Excel(name = "通知编号")
    private java.lang.String noticeNum;
    /** 鉴定会状态 */
    private String meetingStatus;

    @FieldDesc("审核人id")
    private String checkUserid;
    @FieldDesc("审核人姓名")
    private String checkUsername;
    @FieldDesc("审核时间")
    private Date checkDate;
    @FieldDesc("消息内容")
    private String msgText;
    @FieldDesc("审核人部门id")
    private String checkDepartid;
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
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

    /**
     * 关联附件编码
     */
    private String attachmentCode;

    /**
     * 机关填写审批结果
     */
    private TBAppraisalApplyAttachedEntity applyAttached;

    /**
     * 鉴定证书扫描照片
     */
    private List<TSFilesEntity> departAccessorys;

    /**
     * 鉴定会证书扫描照片关联附件编码
     */
    private String departAccessoryCode;

    /**
     * 鉴定会成员关联编码
     */
    private String meetingDeptCode;

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

    @Column(name = "apply_id")
    public java.lang.String getApplyId() {
        return applyId;
    }

    public void setApplyId(java.lang.String applyId) {
        this.applyId = applyId;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 鉴定会时间
     */
    @Column(name = "MEETING_DATE", nullable = true)
    public java.util.Date getMeetingDate() {
        return this.meetingDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 鉴定会时间
     */
    public void setMeetingDate(java.util.Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 鉴定会地点
     */
    @Column(name = "MEETING_ADDR", nullable = true, length = 200)
    public java.lang.String getMeetingAddr() {
        return this.meetingAddr;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定会地点
     */
    public void setMeetingAddr(java.lang.String meetingAddr) {
        this.meetingAddr = meetingAddr;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 联系人
     */
    @Column(name = "CONTACT_NAME", nullable = true, length = 50)
    public java.lang.String getContactName() {
        return this.contactName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 联系人
     */
    public void setContactName(java.lang.String contactName) {
        this.contactName = contactName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 联系电话
     */
    @Column(name = "CONTACT_PHONE", nullable = true, length = 20)
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
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 报到时间
     */
    @Column(name = "REGISTER_TIME", nullable = true)
    public java.util.Date getRegisterTime() {
        return this.registerTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 报到时间
     */
    public void setRegisterTime(java.util.Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 报到地点
     */
    @Column(name = "REGISTER_ADDR", nullable = true, length = 200)
    public java.lang.String getRegisterAddr() {
        return this.registerAddr;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 报到地点
     */
    public void setRegisterAddr(java.lang.String registerAddr) {
        this.registerAddr = registerAddr;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主持单位id
     */
    @Column(name = "HOST_DEPARTID", nullable = true, length = 32)
    public java.lang.String getHostDepartid() {
        return this.hostDepartid;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 主持单位id
     */
    public void setHostDepartid(java.lang.String hostDepartid) {
        this.hostDepartid = hostDepartid;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主持单位
     */
    @Column(name = "HOST_DEPARTNAME", nullable = true, length = 50)
    public java.lang.String getHostDepartname() {
        return this.hostDepartname;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 主持单位
     */
    public void setHostDepartname(java.lang.String hostDepartname) {
        this.hostDepartname = hostDepartname;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 通知编号
     */
    @Column(name = "NOTICE_NUM", nullable = true, length = 20)
    public java.lang.String getNoticeNum() {
        return this.noticeNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 通知编号
     */
    public void setNoticeNum(java.lang.String noticeNum) {
        this.noticeNum = noticeNum;
    }

    @Column(name = "MEETING_STATUS")
    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    @Column(name = "CHECK_USERID")
    public String getCheckUserid() {
        return checkUserid;
    }

    public void setCheckUserid(String checkUserid) {
        this.checkUserid = checkUserid;
    }

    @Column(name = "CHECK_USERNAME")
    public String getCheckUsername() {
        return checkUsername;
    }

    public void setCheckUsername(String checkUsername) {
        this.checkUsername = checkUsername;
    }

    @Column(name = "CHECK_DATE")
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @Column(name = "MSG_TEXT")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Column(name = "CHECK_DEPARTID")
    public String getCheckDepartid() {
        return checkDepartid;
    }

    public void setCheckDepartid(String checkDepartid) {
        this.checkDepartid = checkDepartid;
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
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Column(name = "attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Transient
    public TBAppraisalApplyAttachedEntity getApplyAttached() {
        return applyAttached;
    }

    public void setApplyAttached(TBAppraisalApplyAttachedEntity applyAttached) {
        this.applyAttached = applyAttached;
    }

    @Transient
    public List<TSFilesEntity> getDepartAccessorys() {
        return departAccessorys;
    }

    public void setDepartAccessorys(List<TSFilesEntity> departAccessorys) {
        this.departAccessorys = departAccessorys;
    }

    @Column(name = "depart_accessory_code ")
    public String getDepartAccessoryCode() {
        return departAccessoryCode;
    }

    public void setDepartAccessoryCode(String departAccessoryCode) {
        this.departAccessoryCode = departAccessoryCode;
    }

    @Column(name="meeting_depart_code")
    public String getMeetingDeptCode() {
        return meetingDeptCode;
    }

    public void setMeetingDeptCode(String meetingDeptCode) {
        this.meetingDeptCode = meetingDeptCode;
    }

}
