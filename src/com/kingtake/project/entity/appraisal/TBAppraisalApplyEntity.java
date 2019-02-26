package com.kingtake.project.entity.appraisal;

import java.math.BigDecimal;
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
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 鉴定申请表
 * @author onlineGenerator
 * @date 2015-09-09 09:43:44
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_appraisal_apply", schema = "")
@LogEntity("鉴定申请表")
@SuppressWarnings("serial")
public class TBAppraisalApplyEntity implements java.io.Serializable {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    
    /**
     * 成果名称
     */
    private String achievementName;
    
    /** 登记编号 */
    @Excel(name = "登记编号")
    @FieldDesc("登记编号")
    private java.lang.String registerCode;
    /** 成果类别 */
    @Excel(name = "成果类别")
    @FieldDesc("成果类别")
    private java.lang.String resultType;
    /** 鉴定形式 */
    @Excel(name = "鉴定形式")
    @FieldDesc("鉴定形式")
    private java.lang.String appraisalForm;
    /** 鉴定时间 */
    @Excel(name = "鉴定时间")
    @FieldDesc("鉴定时间")
    private java.util.Date appraisalTime;
    /** 鉴定地点 */
    @Excel(name = "鉴定地点")
    @FieldDesc("鉴定地点")
    private java.lang.String appraisalAddress;
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
    /** 主键 */
    @FieldDesc("主键id")
    private java.lang.String id;
   
    /** 成果完成单位(默认海工，可输入其它单位 名称) */
    @Excel(name = "成果完成单位(默认海工，可输入其它单位 名称)")
    @FieldDesc("成果完成单位(默认海工，可输入其它单位 名称)")
    private java.lang.String finishUnit;
    /** 工作开始时间 */
    @Excel(name = "工作开始时间")
    @FieldDesc("工作开始时间")
    private java.util.Date beginTime;
    /** 工作截止时间 */
    @Excel(name = "工作截止时间")
    @FieldDesc("工作截止时间")
    private java.util.Date endTime;
    /** 归档号(HJ-502-2015-001) */
    @Excel(name = "归档号(HJ-502-2015-001)")
    @FieldDesc("归档号(HJ-502-2015-001)")
    private java.lang.String archiveNum;
    /** 基层编号 */
    @Excel(name = "基层编号")
    @FieldDesc("基层编号")
    private String basicNum;
    /** 成果完成单位联系人id */
    @Excel(name = "成果完成单位联系人id")
    @FieldDesc("成果完成单位联系人id")
    private java.lang.String finishContactId;
    /** 成果完成单位联系人姓名 */
    @Excel(name = "成果完成单位联系人姓名")
    @FieldDesc("成果完成单位联系人姓名")
    private java.lang.String finishContactName;
    /** 成果完成单位联系人电话 */
    @Excel(name = "成果完成单位联系人电话")
    @FieldDesc("成果完成单位联系人电话")
    private java.lang.String finishContactPhone;
    /** 主持鉴定单位联系人 */
    @Excel(name = "主持鉴定单位联系人")
    @FieldDesc("主持鉴定单位联系人")
    private java.lang.String appraisalContactName;
    /**
     * 主持鉴定单位
     */
    @Excel(name = "主持鉴定单位")
    @FieldDesc("主持鉴定单位")
    private java.lang.String hostUnit;
    /** 主持鉴定单位联系人电话 */
    @Excel(name = "主持鉴定单位联系人电话")
    @FieldDesc("主持鉴定单位联系人电话")
    private java.lang.String appraisalContactPhone;

    /**
     * 组织鉴定完成单位
     */
    @Excel(name = "组织鉴定完成单位")
    @FieldDesc("组织鉴定完成单位")
    private String organizeUnit;

    /**
     * 组织鉴定完成单位
     */
    @Excel(name = "组织鉴定完成单位联系人")
    @FieldDesc("组织鉴定完成单位联系人")
    private String organizeContactName;

    /**
     * 组织鉴定完成单位
     */
    @Excel(name = "组织鉴定完成单位联系人电话")
    @FieldDesc("组织鉴定完成单位联系人电话")
    private String organizeContactPhone;
    
    /**
     * 
     * 总经费
     */
    private BigDecimal totalAmount;
    
    /**
     * 
     * 项目类型
     */
    private String projectType;
    
    /**
     * 申请单位
     */
    private String approvalUnitName;
    
    /**
     * 联系人
     */
    private String contactName;
    
    /**
     * 联系人电话
     */
    private String contactPhone ;
    
    /**
     * 其他关联项目
     */
    private String qtglxm;
    
    /**
     * 其他关联项目id
     */
    private String qtglxmId;

    /** 审核状态 */
    @Excel(name = "审核状态")
    @FieldDesc("审核状态")
    private String auditStatus;
    /**
     * 流程审批状态
     */
    private String bpmStatus;
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
    
    /**
     * 关联鉴定会状态
     */
    private String meetingStatus;
    
    /**
     * 关联材料状态
     */
    private String materialStatus;

    /**
     * 关联附件编码
     */
    private String attachmentCode;
    
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果完成单位联系人电话
     */
    @Column(name = "FINISH_CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getFinishContactPhone() {
        return this.finishContactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果完成单位联系人电话
     */
    public void setFinishContactPhone(java.lang.String finishContactPhone) {
        this.finishContactPhone = finishContactPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主持鉴定单位联系人
     */
    @Column(name = "APPRAISAL_CONTACT_NAME", nullable = true, length = 50)
    public java.lang.String getAppraisalContactName() {
        return this.appraisalContactName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主持鉴定单位联系人
     */
    public void setAppraisalContactName(java.lang.String appraisalContactName) {
        this.appraisalContactName = appraisalContactName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主持鉴定单位联系人电话
     */
    @Column(name = "APPRAISAL_CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getAppraisalContactPhone() {
        return this.appraisalContactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主持鉴定单位联系人电话
     */
    public void setAppraisalContactPhone(java.lang.String appraisalContactPhone) {
        this.appraisalContactPhone = appraisalContactPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 登记编号
     */
    @Column(name = "REGISTER_CODE", nullable = true, length = 30)
    public java.lang.String getRegisterCode() {
        return this.registerCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 登记编号
     */
    public void setRegisterCode(java.lang.String registerCode) {
        this.registerCode = registerCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果类别
     */
    @Column(name = "RESULT_TYPE", nullable = true, length = 2)
    public java.lang.String getResultType() {
        return this.resultType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果类别
     */
    public void setResultType(java.lang.String resultType) {
        this.resultType = resultType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 鉴定形式
     */
    @Column(name = "APPRAISAL_FORM", nullable = true, length = 2)
    public java.lang.String getAppraisalForm() {
        return this.appraisalForm;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 鉴定形式
     */
    public void setAppraisalForm(java.lang.String appraisalForm) {
        this.appraisalForm = appraisalForm;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 鉴定时间
     */
    @Column(name = "APPRAISAL_TIME", nullable = true)
    public java.util.Date getAppraisalTime() {
        return this.appraisalTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 鉴定时间
     */
    public void setAppraisalTime(java.util.Date appraisalTime) {
        this.appraisalTime = appraisalTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 鉴定地点
     */
    @Column(name = "APPRAISAL_ADDRESS", nullable = true, length = 300)
    public java.lang.String getAppraisalAddress() {
        return this.appraisalAddress;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 鉴定地点
     */
    public void setAppraisalAddress(java.lang.String appraisalAddress) {
        this.appraisalAddress = appraisalAddress;
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
     * @return: java.lang.String 成果完成单位(默认海工，可输入其它单位 名称)
     */
    @Column(name = "FINISH_UNIT", nullable = true, length = 1000)
    public java.lang.String getFinishUnit() {
        return this.finishUnit;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果完成单位(默认海工，可输入其它单位 名称)
     */
    public void setFinishUnit(java.lang.String finishUnit) {
        this.finishUnit = finishUnit;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 工作开始时间
     */
    @Column(name = "BEGIN_TIME", nullable = true)
    public java.util.Date getBeginTime() {
        return this.beginTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 工作开始时间
     */
    public void setBeginTime(java.util.Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 工作截止时间
     */
    @Column(name = "END_TIME", nullable = true)
    public java.util.Date getEndTime() {
        return this.endTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 工作截止时间
     */
    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 归档号(HJ-502-2015-001)
     */
    @Column(name = "ARCHIVE_NUM", nullable = true, length = 20)
    public java.lang.String getArchiveNum() {
        return this.archiveNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 归档号(HJ-502-2015-001)
     */
    public void setArchiveNum(java.lang.String archiveNum) {
        this.archiveNum = archiveNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果完成单位联系人id
     */
    @Column(name = "FINISH_CONTACT_ID", nullable = true, length = 32)
    public java.lang.String getFinishContactId() {
        return this.finishContactId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果完成单位联系人id
     */
    public void setFinishContactId(java.lang.String finishContactId) {
        this.finishContactId = finishContactId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果完成单位联系人姓名
     */
    @Column(name = "FINISH_CONTACT_NAME", nullable = true, length = 50)
    public java.lang.String getFinishContactName() {
        return this.finishContactName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果完成单位联系人姓名
     */
    public void setFinishContactName(java.lang.String finishContactName) {
        this.finishContactName = finishContactName;
    }

    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "BPM_STATUS")
    public String getBpmStatus() {
        return bpmStatus;
    }

    public void setBpmStatus(String bpmStatus) {
        this.bpmStatus = bpmStatus;
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

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Column(name = "BASIC_NUM", nullable = true, length = 20)
    public String getBasicNum() {
        return basicNum;
    }

    public void setBasicNum(String basicNum) {
        this.basicNum = basicNum;
    }

    @Column(name = "host_unit")
    public java.lang.String getHostUnit() {
        return hostUnit;
    }

    public void setHostUnit(java.lang.String hostUnit) {
        this.hostUnit = hostUnit;
    }

    @Column(name = "organize_unit")
    public String getOrganizeUnit() {
        return organizeUnit;
    }

    public void setOrganizeUnit(String organizeUnit) {
        this.organizeUnit = organizeUnit;
    }

    @Column(name = "organize_contact_name")
    public String getOrganizeContactName() {
        return organizeContactName;
    }

    public void setOrganizeContactName(String organizeContactName) {
        this.organizeContactName = organizeContactName;
    }

    @Column(name = "organize_contact_phone")
    public String getOrganizeContactPhone() {
        return organizeContactPhone;
    }

    public void setOrganizeContactPhone(String organizeContactPhone) {
        this.organizeContactPhone = organizeContactPhone;
    }

    @Column(name="project_id")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Column(name="project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name="achievement_Name")
    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    @Column(name="total_amount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Column(name="project_type")
    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    @Column(name="approval_Unit_Name")
    public String getApprovalUnitName() {
        return approvalUnitName;
    }

    public void setApprovalUnitName(String approvalUnitName) {
        this.approvalUnitName = approvalUnitName;
    }

    @Column(name="contact_name ")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Column(name="contact_phone")
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Column(name="qtglxm")
    public String getQtglxm() {
        return qtglxm;
    }

    public void setQtglxm(String qtglxm) {
        this.qtglxm = qtglxm;
    }

    @Column(name="qtglxmid")
    public String getQtglxmId() {
        return qtglxmId;
    }

    public void setQtglxmId(String qtglxmId) {
        this.qtglxmId = qtglxmId;
    }

    @Transient
    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    @Transient
    public String getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(String materialStatus) {
        this.materialStatus = materialStatus;
    }
    
    
}
