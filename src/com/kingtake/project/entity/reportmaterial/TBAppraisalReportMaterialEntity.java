package com.kingtake.project.entity.reportmaterial;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 上报材料信息表
 * @author onlineGenerator
 * @date 2016-01-26 16:35:55
 * @version V1.0
 * 
 */
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "t_b_appraisal_report_material", schema = "")
@SuppressWarnings("serial")
public class TBAppraisalReportMaterialEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 水平评价 */
    @Excel(name = "水平评价")
    private java.lang.String levelEvaluation;
    /** 批准时间 */
    @Excel(name = "批准时间")
    private java.util.Date approveDate;
    /** 鉴定证书年度 */
    @Excel(name = "鉴定证书年度")
    private java.lang.String certificateYear;
    /** 鉴定证书下达机关 */
    @Excel(name = "鉴定证书下达机关")
    private java.lang.String certificateFromUnit;
    /** 鉴定证书号 */
    @Excel(name = "鉴定证书号")
    private java.lang.String certificateNumber;
    /** 证书领取人 */
    @Excel(name = "证书领取人")
    private java.lang.String certificateReceiptor;
    /** 证书领取时间 */
    @Excel(name = "证书领取时间")
    private java.util.Date receiptorReceiveDate;
    /**
     * 审查标志 0：未发送，1 已发送，2 已审核，待完善证书信息，3 已完善证书信息，4 上报材料环节完成
     */
    @Excel(name = "审查标志")
    private java.lang.String checkFlag;
    /** 审查人id */
    @Excel(name = "审查人id")
    private java.lang.String checkUserid;
    /** 审查人部门id */
    @Excel(name = "审查人部门id")
    private java.lang.String checkDepartid;
    /** 审查人姓名 */
    @Excel(name = "审查人姓名")
    private java.lang.String checkUsername;
    /** 审查时间 */
    @Excel(name = "审查时间")
    private java.util.Date checkDate;
    /** 消息内容 */
    @Excel(name = "消息内容")
    private java.lang.String msgText;
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
    @Excel(name = "修改时间")
    private java.util.Date updateDate;
    /** 关联鉴定申请id */
    private java.lang.String applyId;
    /**
     * 会议材料附件
     */
    private List<TSFilesEntity> meetingMaterials;

    /**
     * 鉴定证书扫描照片
     */
    private List<TSFilesEntity> certificateScans;

    /**
     * 
     * 附件关联编码
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
     * @return: java.lang.String 水平评价
     */
    @Column(name = "LEVEL_EVALUATION", nullable = true, length = 1)
    public java.lang.String getLevelEvaluation() {
        return this.levelEvaluation;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 水平评价
     */
    public void setLevelEvaluation(java.lang.String levelEvaluation) {
        this.levelEvaluation = levelEvaluation;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 批准时间
     */
    @Column(name = "APPROVE_DATE", nullable = true)
    public java.util.Date getApproveDate() {
        return this.approveDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 批准时间
     */
    public void setApproveDate(java.util.Date approveDate) {
        this.approveDate = approveDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 鉴定证书年度
     */
    @Column(name = "CERTIFICATE_YEAR", nullable = true, length = 4)
    public java.lang.String getCertificateYear() {
        return this.certificateYear;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定证书年度
     */
    public void setCertificateYear(java.lang.String certificateYear) {
        this.certificateYear = certificateYear;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 鉴定证书下达机关
     */
    @Column(name = "CERTIFICATE_FROM_UNIT", nullable = true, length = 120)
    public java.lang.String getCertificateFromUnit() {
        return this.certificateFromUnit;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定证书下达机关
     */
    public void setCertificateFromUnit(java.lang.String certificateFromUnit) {
        this.certificateFromUnit = certificateFromUnit;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 鉴定证书号
     */
    @Column(name = "CERTIFICATE_NUMBER", nullable = true, length = 30)
    public java.lang.String getCertificateNumber() {
        return this.certificateNumber;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定证书号
     */
    public void setCertificateNumber(java.lang.String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 证书领取人
     */
    @Column(name = "CERTIFICATE_RECEIPTOR", nullable = true, length = 36)
    public java.lang.String getCertificateReceiptor() {
        return this.certificateReceiptor;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 证书领取人
     */
    public void setCertificateReceiptor(java.lang.String certificateReceiptor) {
        this.certificateReceiptor = certificateReceiptor;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 证书领取时间
     */
    @Column(name = "RECEIPTOR_RECEIVE_DATE", nullable = true)
    public java.util.Date getReceiptorReceiveDate() {
        return this.receiptorReceiveDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 证书领取时间
     */
    public void setReceiptorReceiveDate(java.util.Date receiptorReceiveDate) {
        this.receiptorReceiveDate = receiptorReceiveDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 审查标志
     */
    @Column(name = "CHECK_FLAG", nullable = true, length = 1)
    public java.lang.String getCheckFlag() {
        return this.checkFlag;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审查标志
     */
    public void setCheckFlag(java.lang.String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 审查人id
     */
    @Column(name = "CHECK_USERID", nullable = true, length = 32)
    public java.lang.String getCheckUserid() {
        return this.checkUserid;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审查人id
     */
    public void setCheckUserid(java.lang.String checkUserid) {
        this.checkUserid = checkUserid;
    }

    /**
     * 审查人部门id
     * 
     * @return
     */
    @Column(name = "CHECK_DEPARTID", nullable = true, length = 32)
    public java.lang.String getCheckDepartid() {
        return checkDepartid;
    }

    public void setCheckDepartid(java.lang.String checkDepartid) {
        this.checkDepartid = checkDepartid;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 审查人姓名
     */
    @Column(name = "CHECK_USERNAME", nullable = true, length = 36)
    public java.lang.String getCheckUsername() {
        return this.checkUsername;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审查人姓名
     */
    public void setCheckUsername(java.lang.String checkUsername) {
        this.checkUsername = checkUsername;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 审查时间
     */
    @Column(name = "CHECK_DATE", nullable = true)
    public java.util.Date getCheckDate() {
        return this.checkDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 审查时间
     */
    public void setCheckDate(java.util.Date checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 消息内容
     */
    @Column(name = "MSG_TEXT", nullable = true, length = 300)
    public java.lang.String getMsgText() {
        return this.msgText;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 消息内容
     */
    public void setMsgText(java.lang.String msgText) {
        this.msgText = msgText;
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

    @Column(name = "apply_id")
    public java.lang.String getApplyId() {
        return applyId;
    }

    public void setApplyId(java.lang.String applyId) {
        this.applyId = applyId;
    }

    @Transient
    public List<TSFilesEntity> getMeetingMaterials() {
        return meetingMaterials;
    }

    public void setMeetingMaterials(List<TSFilesEntity> meetingMaterials) {
        this.meetingMaterials = meetingMaterials;
    }

    @Transient
    public List<TSFilesEntity> getCertificateScans() {
        return certificateScans;
    }

    public void setCertificateScans(List<TSFilesEntity> certificateScans) {
        this.certificateScans = certificateScans;
    }

    @Column(name = "attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

}
