package com.kingtake.project.entity.reportreq;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 申报需求信息表
 * @author onlineGenerator
 * @date 2016-01-24 14:58:37
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_pm_report_req", schema = "")
@SuppressWarnings("serial")
public class TBPmReportReqEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 关联项目id */
    @Excel(name = "关联项目id")
    private java.lang.String projectId;
    /** 序号 */
    @Excel(name = "序号")
    private java.lang.String reqNum;
    /** 项目名称 */
    @Excel(name = "项目名称")
    private java.lang.String projectName;
    /** 立项需求及研究总体要求 */
    @Excel(name = "立项需求及研究总体要求")
    private java.lang.String researchReq;
    /** 起始时间 */
    @Excel(name = "起始时间")
    private java.util.Date beginDate;
    /** 结束时间 */
    @Excel(name = "结束时间")
    private java.util.Date endDate;
    /** 申报单位 */
    // private java.lang.String reportUnit;
    @FieldDesc("申报单位实体")
    private TSDepart reportUnit;
    /** 责任单位 */
    // @Excel(name = "责任单位")
    // private java.lang.String manageUnit;
    @FieldDesc("责任单位实体")
    private TSDepart manageUnit;
    /** 经费需求 */
    @Excel(name = "经费需求",isAmount = true)
    private java.math.BigDecimal feeReq;
    /** 申请人 */
    @Excel(name = "申请人")
    private java.lang.String applicantor;
    /** 流程处理状态 */
    @Excel(name = "流程处理状态")
    private java.lang.String bpmStatus;
    /** 创建人 */
    @Excel(name = "创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @Excel(name = "修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @Excel(name = "修改时间")
    private java.util.Date updateDate;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;

    /**
     * 流程实例id
     */
    private String processInstId;

    /**
     * 计划状态，1 已通过 ，2 已驳回 ，3 重新提交
     */
    private String planStatus;
    
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

    @ManyToOne
    @JoinColumn(name = "report_unit")
    public TSDepart getReportUnit() {
        return reportUnit;
    }

    public void setReportUnit(TSDepart reportUnit) {
        this.reportUnit = reportUnit;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 关联项目id
     */
    @Column(name = "PROJECT_ID", nullable = true, length = 32)
    public java.lang.String getProjectId() {
        return this.projectId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 关联项目id
     */
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 序号
     */
    @Column(name = "REQ_NUM", nullable = true, length = 20)
    public java.lang.String getReqNum() {
        return this.reqNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 序号
     */
    public void setReqNum(java.lang.String reqNum) {
        this.reqNum = reqNum;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目名称
     */
    @Column(name = "PROJECT_NAME", nullable = true, length = 100)
    public java.lang.String getProjectName() {
        return this.projectName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目名称
     */
    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 立项需求及研究总体要求
     */
    @Column(name = "RESEARCH_REQ", nullable = true, length = 800)
    public java.lang.String getResearchReq() {
        return this.researchReq;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 立项需求及研究总体要求
     */
    public void setResearchReq(java.lang.String researchReq) {
        this.researchReq = researchReq;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 起始时间
     */
    @Column(name = "BEGIN_DATE", nullable = true)
    public java.util.Date getBeginDate() {
        return this.beginDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 起始时间
     */
    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 结束时间
     */
    @Column(name = "END_DATE", nullable = true)
    public java.util.Date getEndDate() {
        return this.endDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 结束时间
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 申报单位
     */
    /*
     * @Column(name = "REPORT_UNIT", nullable = true, length = 32) public java.lang.String getReportUnit() { return
     * this.reportUnit; }
     */

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 申报单位
     */
    /*
     * public void setReportUnit(java.lang.String reportUnit) { this.reportUnit = reportUnit; }
     */

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 责任单位
     */
    /*
     * @Column(name = "MANAGE_UNIT", nullable = true, length = 32) public java.lang.String getManageUnit() { return
     * this.manageUnit; }
     */

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 责任单位
     */
    /*
     * public void setManageUnit(java.lang.String manageUnit) { this.manageUnit = manageUnit; }
     */

    @ManyToOne
    @JoinColumn(name = "MANAGE_UNIT")
    public TSDepart getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(TSDepart manageUnit) {
        this.manageUnit = manageUnit;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     * 
     * @return: java.math.BigDecimal 经费需求
     */
    @Column(name = "FEE_REQ", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getFeeReq() {
        return this.feeReq;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     * 
     * @param: java.math.BigDecimal 经费需求
     */
    public void setFeeReq(java.math.BigDecimal feeReq) {
        this.feeReq = feeReq;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 申请人
     */
    @Column(name = "APPLICANTOR", nullable = true, length = 32)
    public java.lang.String getApplicantor() {
        return this.applicantor;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 申请人
     */
    public void setApplicantor(java.lang.String applicantor) {
        this.applicantor = applicantor;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 流程处理状态
     */
    @Column(name = "BPM_STATUS", nullable = true, length = 1)
    public java.lang.String getBpmStatus() {
        return this.bpmStatus;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 流程处理状态
     */
    public void setBpmStatus(java.lang.String bpmStatus) {
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
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Transient
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @Column(name = "PLAN_STATUS")
    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
    
}
