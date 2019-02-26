package com.kingtake.project.entity.reporthall;

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
import org.jeecgframework.core.constant.CodeTypeConstant;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 学术报告厅信息登记表
 * @author onlineGenerator
 * @date 2016-01-15 16:56:10
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_report_hall_use", schema = "")
@SuppressWarnings("serial")
public class TBReportHallUseEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 学术报告厅 */
    @Excel(name = "学术报告厅", isExportConvert = true)
    private java.lang.String reportHallId;
    /** 编号 */
    @Excel(name = "编号")
    private java.lang.String reviewNumber;
    /** 使用单位id */
    private java.lang.String useDepartId;
    /** 使用单位名称 */
    @Excel(name = "使用单位名称")
    private java.lang.String useDepartName;
    /** 使用目的 */
    @Excel(name = "使用目的")
    private java.lang.String usePurpose;
    /** 开始使用时间 */
    @Excel(name = "使用时间", format = "yyyy-MM-dd")
    private java.util.Date beginUseTime;
    /** 结束使用时间 */
    private java.util.Date endUseTime;
    /** 联系人id */
    private java.lang.String contactId;
    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    private java.lang.String contactName;
    /** 联系电话 */
    @Excel(name = "联系电话")
    private java.lang.String contactPhone;
    /** 拟参加人员 */
    @Excel(name = "拟参加人员")
    private java.lang.String attendeeName;
    /** 需学术报告厅准备事宜 */
    @Excel(name = "需学术报告厅准备事宜")
    private java.lang.String prepareThings;
    /** 备注 */
    @Excel(name = "备注")
    private java.lang.String memo;
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
    /** 审查状态 */
    @Excel(name = "审查状态", isExportConvert = true)
    private java.lang.String checkFlag;
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates; // 附件

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
     * @return: java.lang.String 学术报告厅
     */
    @Column(name = "REPORT_HALL_ID", nullable = true, length = 2)
    public java.lang.String getReportHallId() {
        return this.reportHallId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 学术报告厅
     */
    public void setReportHallId(java.lang.String reportHallId) {
        this.reportHallId = reportHallId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 编号
     */
    @Column(name = "REVIEW_NUMBER", nullable = true, length = 50)
    public java.lang.String getReviewNumber() {
        return this.reviewNumber;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 编号
     */
    public void setReviewNumber(java.lang.String reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 使用单位id
     */
    @Column(name = "USE_DEPART_ID", nullable = true, length = 32)
    public java.lang.String getUseDepartId() {
        return this.useDepartId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 使用单位id
     */
    public void setUseDepartId(java.lang.String useDepartId) {
        this.useDepartId = useDepartId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 使用单位名称
     */
    @Column(name = "USE_DEPART_NAME", nullable = true, length = 60)
    public java.lang.String getUseDepartName() {
        return this.useDepartName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 使用单位名称
     */
    public void setUseDepartName(java.lang.String useDepartName) {
        this.useDepartName = useDepartName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 使用目的
     */
    @Column(name = "USE_PURPOSE", nullable = true, length = 100)
    public java.lang.String getUsePurpose() {
        return this.usePurpose;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 使用目的
     */
    public void setUsePurpose(java.lang.String usePurpose) {
        this.usePurpose = usePurpose;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 开始使用时间
     */
    @Column(name = "BEGIN_USE_TIME", nullable = true)
    public java.util.Date getBeginUseTime() {
        return this.beginUseTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 开始使用时间
     */
    public void setBeginUseTime(java.util.Date beginUseTime) {
        this.beginUseTime = beginUseTime;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 结束使用时间
     */
    @Column(name = "END_USE_TIME", nullable = true)
    public java.util.Date getEndUseTime() {
        return this.endUseTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 结束使用时间
     */
    public void setEndUseTime(java.util.Date endUseTime) {
        this.endUseTime = endUseTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 联系人id
     */
    @Column(name = "CONTACT_ID", nullable = true, length = 32)
    public java.lang.String getContactId() {
        return this.contactId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 联系人id
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 联系人姓名
     */
    @Column(name = "CONTACT_NAME", nullable = true, length = 36)
    public java.lang.String getContactName() {
        return this.contactName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 联系人姓名
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
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 拟参加人员
     */
    @Column(name = "ATTENDEE_NAME", nullable = true, length = 200)
    public java.lang.String getAttendeeName() {
        return this.attendeeName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 拟参加人员
     */
    public void setAttendeeName(java.lang.String attendeeName) {
        this.attendeeName = attendeeName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 需学术报告厅准备事宜
     */
    @Column(name = "PREPARE_THINGS", nullable = true, length = 500)
    public java.lang.String getPrepareThings() {
        return this.prepareThings;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 需学术报告厅准备事宜
     */
    public void setPrepareThings(java.lang.String prepareThings) {
        this.prepareThings = prepareThings;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 需学术报告厅准备事宜
     */
    @Column(name = "MEMO", nullable = true, length = 500)
    public java.lang.String getMemo() {
        return this.memo;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 需学术报告厅准备事宜
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
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
    @Column(name = "CHECK_FLAG", nullable = true, length = 1)
    public java.lang.String getCheckFlag() {
        return this.checkFlag;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审查状态
     */
    public void setCheckFlag(java.lang.String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    /**
     * 导出审核状态代码转换
     * 
     * @return
     */
    public String convertGetCheckFlag() {
        return ConvertDicUtil.getConvertDic(CodeTypeConstant.CODE_TYPE_KY, CodeTypeConstant.SCZT, this.checkFlag);
    }

    /**
     * 导出学术报告厅代码转换
     * 
     * @return
     */
    public String convertGetReportHallId() {
        return ConvertDicUtil.getConvertDic(CodeTypeConstant.CODE_TYPE_KY, CodeTypeConstant.XSBGT, this.reportHallId);
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

}
