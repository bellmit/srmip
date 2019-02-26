package com.kingtake.project.entity.checkreport;

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
 * @Description: 中期检查报告
 * @author onlineGenerator
 * @date 2016-04-13 16:20:03
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_pm_check_report", schema = "")
@SuppressWarnings("serial")
public class TPmCheckReportEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 项目基_主键 */
    @Excel(name = "项目基_主键")
    @FieldDesc("项目主键")
    private java.lang.String tpId;
    /** 标题 */
    @Excel(name = "标题")
    @FieldDesc("标题")
    private java.lang.String reportTitle;
    /** 报告起始时间 */
    @Excel(name = "报告起始时间")
    @FieldDesc("报告起始时间")
    private java.util.Date yearStart;
    /** 报告结束时间 */
    @Excel(name = "报告结束时间")
    @FieldDesc("报告结束时间")
    private java.util.Date yearEnd;
    /** 备注 */
    @Excel(name = "备注")
    @FieldDesc("备注")
    private java.lang.String remark;
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
    /**
     * 审核状态,0 未提交 1 已提交 2 通过 3 不通过 4 重新提交
     */
    @Excel(name = "审查状态")
    @FieldDesc("审查状态")
    private java.lang.String checkStatus;
    /** 审查意见 */
    @Excel(name = "审查意见")
    @FieldDesc("审查意见")
    private java.lang.String checkSuggest;
    /** 审查人id */
    @Excel(name = "审查人id")
    @FieldDesc("审查人id")
    private java.lang.String checkUserid;
    /** 审核人姓名 */
    @Excel(name = "审核人姓名")
    @FieldDesc("审核人姓名")
    private java.lang.String checkUsername;
    /**
     * 审核人部门id
     */
    @FieldDesc("审核人部门id")
    private String checkUserDeptId;
    /** 审核时间 */
    @Excel(name = "审核时间")
    @FieldDesc("审核时间")
    private java.util.Date checkDate;

    private List<TSFilesEntity> certificates;// 附件
    
    /**
     * 附件
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

    @Column(name = "T_P_ID", nullable = true, length = 32)
    public java.lang.String getTpId() {
        return tpId;
    }

    public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 标题
     */
    @Column(name = "REPORT_TITLE", nullable = true, length = 200)
    public java.lang.String getReportTitle() {
        return this.reportTitle;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 标题
     */
    public void setReportTitle(java.lang.String reportTitle) {
        this.reportTitle = reportTitle;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 报告起始时间
     */
    @Column(name = "YEAR_START", nullable = true)
    public java.util.Date getYearStart() {
        return this.yearStart;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 报告起始时间
     */
    public void setYearStart(java.util.Date yearStart) {
        this.yearStart = yearStart;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 报告结束时间
     */
    @Column(name = "YEAR_END", nullable = true)
    public java.util.Date getYearEnd() {
        return this.yearEnd;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 报告结束时间
     */
    public void setYearEnd(java.util.Date yearEnd) {
        this.yearEnd = yearEnd;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 备注
     */
    @Column(name = "REMARK", nullable = true, length = 500)
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 备注
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
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

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 审查状态
     */
    @Column(name = "CHECK_STATUS", nullable = true, length = 1)
    public java.lang.String getCheckStatus() {
        return this.checkStatus;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审查状态
     */
    public void setCheckStatus(java.lang.String checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 审查意见
     */
    @Column(name = "CHECK_SUGGEST", nullable = true, length = 300)
    public java.lang.String getCheckSuggest() {
        return this.checkSuggest;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审查意见
     */
    public void setCheckSuggest(java.lang.String checkSuggest) {
        this.checkSuggest = checkSuggest;
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
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 审核人姓名
     */
    @Column(name = "CHECK_USERNAME", nullable = true, length = 50)
    public java.lang.String getCheckUsername() {
        return this.checkUsername;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审核人姓名
     */
    public void setCheckUsername(java.lang.String checkUsername) {
        this.checkUsername = checkUsername;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 审核时间
     */
    @Column(name = "CHECK_DATE", nullable = true)
    public java.util.Date getCheckDate() {
        return this.checkDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 审核时间
     */
    public void setCheckDate(java.util.Date checkDate) {
        this.checkDate = checkDate;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    @Column(name = "CHECK_USER_DEPTID")
    public String getCheckUserDeptId() {
        return checkUserDeptId;
    }

    public void setCheckUserDeptId(String checkUserDeptId) {
        this.checkUserDeptId = checkUserDeptId;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    
}
