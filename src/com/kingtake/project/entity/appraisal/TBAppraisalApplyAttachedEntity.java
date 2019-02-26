package com.kingtake.project.entity.appraisal;

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

/**
 * @Title: Entity
 * @Description: 鉴定申请附表
 * @author onlineGenerator
 * @date 2016-01-21 16:42:02
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_appraisal_apply_attached", schema = "")
@SuppressWarnings("serial")
public class TBAppraisalApplyAttachedEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 主表id */
    private java.lang.String applyId;
    /** 海军机关批复意见 */
    @Excel(name = "海军机关批复意见")
    private java.lang.String navalAuthorityView;
    /** 登记编号 */
    @Excel(name = "登记编号")
    private java.lang.String registerNum;
    /** 审批号前缀 */
    @Excel(name = "审批号前缀")
    private java.lang.String applyPrefix;
    /** 审批年度 */
    @Excel(name = "审批年度")
    private java.lang.String applyYear;
    /** 审批字号 */
    @Excel(name = "审批字号")
    private java.lang.String applyNum;
    /** 合并审批号 */
    private java.lang.String mergeApplyNum;
    /** 申请鉴定单位批准时间 */
    @Excel(name = "申请鉴定单位批准时间")
    private java.util.Date applyApproveDate;
    /** 主持鉴定单位批准时间 */
    @Excel(name = "主持鉴定单位批准时间")
    private java.util.Date hostApproveDate;
    /** 组织鉴定单位批准时间 */
    @Excel(name = "组织鉴定单位批准时间")
    private java.util.Date organizeApproveDate;
    /** 审批状态 */
    private java.lang.String auditStatus;
    
    private String attachmentCode;
    
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
     * @return: java.lang.String 主表id
     */
    @Column(name = "APPLY_ID", nullable = true, length = 32)
    public java.lang.String getApplyId() {
        return this.applyId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主表id
     */
    public void setApplyId(java.lang.String applyId) {
        this.applyId = applyId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 海军机关批复意见
     */
    @Column(name = "NAVAL_AUTHORITY_VIEW", nullable = true, length = 500)
    public java.lang.String getNavalAuthorityView() {
        return this.navalAuthorityView;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 海军机关批复意见
     */
    public void setNavalAuthorityView(java.lang.String navalAuthorityView) {
        this.navalAuthorityView = navalAuthorityView;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 登记编号
     */
    @Column(name = "REGISTER_NUM", nullable = true, length = 50)
    public java.lang.String getRegisterNum() {
        return this.registerNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 登记编号
     */
    public void setRegisterNum(java.lang.String registerNum) {
        this.registerNum = registerNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审批号前缀
     */
    @Column(name = "APPLY_PREFIX", nullable = true, length = 10)
    public java.lang.String getApplyPrefix() {
        return this.applyPrefix;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审批号前缀
     */
    public void setApplyPrefix(java.lang.String applyPrefix) {
        this.applyPrefix = applyPrefix;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审批年度
     */
    @Column(name = "APPLY_YEAR", nullable = true, length = 4)
    public java.lang.String getApplyYear() {
        return this.applyYear;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审批年度
     */
    public void setApplyYear(java.lang.String applyYear) {
        this.applyYear = applyYear;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审批字号
     */
    @Column(name = "APPLY_NUM", nullable = true, length = 10)
    public java.lang.String getApplyNum() {
        return this.applyNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审批字号
     */
    public void setApplyNum(java.lang.String applyNum) {
        this.applyNum = applyNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合并审批号
     */
    @Column(name = "MERGE_APPLY_NUM", nullable = true, length = 30)
    public java.lang.String getMergeApplyNum() {
        return this.mergeApplyNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合并审批号
     */
    public void setMergeApplyNum(java.lang.String mergeApplyNum) {
        this.mergeApplyNum = mergeApplyNum;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 申请鉴定单位批准时间
     */
    @Column(name = "APPLY_APPROVE_DATE", nullable = true)
    public java.util.Date getApplyApproveDate() {
        return this.applyApproveDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 申请鉴定单位批准时间
     */
    public void setApplyApproveDate(java.util.Date applyApproveDate) {
        this.applyApproveDate = applyApproveDate;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 主持鉴定单位批准时间
     */
    @Column(name = "HOST_APPROVE_DATE", nullable = true)
    public java.util.Date getHostApproveDate() {
        return this.hostApproveDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 主持鉴定单位批准时间
     */
    public void setHostApproveDate(java.util.Date hostApproveDate) {
        this.hostApproveDate = hostApproveDate;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 组织鉴定单位批准时间
     */
    @Column(name = "ORGANIZE_APPROVE_DATE", nullable = true)
    public java.util.Date getOrganizeApproveDate() {
        return this.organizeApproveDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 组织鉴定单位批准时间
     */
    public void setOrganizeApproveDate(java.util.Date organizeApproveDate) {
        this.organizeApproveDate = organizeApproveDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审批状态
     */
    @Column(name = "AUDIT_STATUS", nullable = true, length = 1)
    public java.lang.String getAuditStatus() {
        return this.auditStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审批状态
     */
    public void setAuditStatus(java.lang.String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
}
