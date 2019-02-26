package com.kingtake.project.entity.incomeplan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.invoice.TBPmInvoiceEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 计划下达信息表
 * @author onlineGenerator
 * @date 2015-12-02 18:36:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_income_plan", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmIncomePlanEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    
    /** 主表外键 */
    private java.lang.String projectPlanId;
    
    /** 母计划下达ID */
    private java.lang.String parentPlanId;
    /**
     * 关联项目
     */
    private TPmProjectEntity project;
    /**
     * 文件号
     */
    @Excel(name = "文件号")
    @FieldDesc("文件号")
    private String documentNo;
    
    /**
     * 文件名
     */
    @Excel(name = "文件名")
    @FieldDesc("文件名")
    private String documentName;   

    /**
     * 发文时间
     */
    @Excel(name = "发文时间", format = "yyyy-MM-dd HH:mm:ss")
    @FieldDesc("发文时间")
    private Date documentTime;
    
    /**
     * 审核标记
     */
    private String approvalStatus;
    
    /**
     * 是否已做预算标记
     */
    private String ysStatus;
    
    /**
     * 关联预算ID
     */
    private String ysApprId;
    
    /**
     * @return
     */
    @Column(name = "YS_APPR_ID", nullable = false, length = 32)
    public String getYsApprId() {
		return ysApprId;
	}

	public void setYsApprId(String ysApprId) {
		this.ysApprId = ysApprId;
	}

	/**
     * 来源经费科目
     */
    private String fundsSubject;
    
    /**
     * 会计年度
     */
    private String planYear;

    /**
     * 计划下达金额
     */
    @Excel(name = "计划下达金额")
    @FieldDesc("计划下达金额")
    private BigDecimal planAmount;
    
    /**
     * 直接经费
     */
    @FieldDesc("直接经费")
    private BigDecimal directFunds;
    
    /**
     * 间接经费
     */
    @FieldDesc("间接经费")
    private BigDecimal indirectFunds;
    
    /**
     * 归垫经费
     */
    @FieldDesc("归垫经费")
    private BigDecimal payfirstFunds;
    
    /**
     * 经费来源
     */
    @FieldDesc("经费来源")
    private String fundsSources;
    
    /**
     * 大学预留金额
     */
    @FieldDesc("大学预留金额")
    private BigDecimal universityAmount;
    
    /**
     * 归垫经费
     */
    @FieldDesc("院预留金额")
    private BigDecimal academyAmount;
    
    /**
     * 归垫经费
     */
    @FieldDesc("系预留金额")
    private BigDecimal departmentAmount;
    
    /**
     * 归垫经费
     */
    @FieldDesc("绩效奖励金额")
    private BigDecimal performanceAmount;
    
    /**
     * 归垫科目id
     */
    @FieldDesc("归垫科目ID")
    private String payfirstId;
    
    /**
     * 条形码
     */
    private String barcode;
    
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
    
    /**
     * 审核人id
     */
    private String checkUserId;

    /**
     * 审核人部门id
     */
    private String checkUserDeptId;

    /**
     * 审核人中文名
     */
    private String checkUserRealName;
    
    /**
     * 提交时间
     */
    private Date submitTime;
    
    /**
     * 审核时间
     */
    private Date auditTime;
    
    /**
     * 修改意见
     */
    private String msgText;

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
     * @return: java.lang.String 计划下达主表外键
     */
    @Column(name = "PROJECT_PLAN_ID", nullable = false, length = 32)
    public java.lang.String getProjectPlanId() {
        return this.projectPlanId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 计划下达主表外键
     */
    public void setProjectPlanId(java.lang.String projectPlanId) {
        this.projectPlanId = projectPlanId;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 母计划下达ID，校内协作才会有
     */
    @Column(name = "PARENT_PLAN_ID", nullable = false, length = 32)
    public java.lang.String getParentPlanId() {
        return this.parentPlanId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 母计划下达ID，校内协作才会有
     */
    public void setParentPlanId(java.lang.String parentPlanId) {
        this.parentPlanId = parentPlanId;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 发文时间
     */
    @Column(name = "DOCUMENT_TIME", nullable = true)
    public java.util.Date getDocumentTime() {
        return this.documentTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 发文时间
     */
    public void setDocumentTime(java.util.Date documentTime) {
        this.documentTime = documentTime;
    }



    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 计划下达金额
     */
    @Column(name = "PLAN_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getPlanAmount() {
        return this.planAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 计划下达金额
     */
    public void setPlanAmount(java.math.BigDecimal planAmount) {
        this.planAmount = planAmount;
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

    @Column(name = "DOCUMENT_NO")
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Column(name = "DOCUMENT_NAME")
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_p_id")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @Column(name = "approval_status")
    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
    @Column(name = "YS_STATUS")
    public String getYsStatus() {
        return ysStatus;
    }

    public void setYsStatus(String ysStatus) {
        this.ysStatus = ysStatus;
    }

    @Column(name = "funds_subject")
    public String getFundsSubject() {
        return fundsSubject;
    }

    public void setFundsSubject(String fundsSubject) {
        this.fundsSubject = fundsSubject;
    }
    
    @Column(name = "plan_year")
    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear;
    }
    
    @Column(name="payfirst_funds")
    public BigDecimal getPayfirstFunds() {
        return payfirstFunds;
    }

    public void setPayfirstFunds(BigDecimal payfirstFunds) {
        this.payfirstFunds = payfirstFunds;
    }
    
    @Column(name="university_amount")
    public BigDecimal getUniversityAmount() {
        return universityAmount;
    }

    public void setUniversityAmount(BigDecimal universityAmount) {
        this.universityAmount = universityAmount;
    }
    
    @Column(name="academy_amount")
    public BigDecimal getAcademyAmount() {
        return academyAmount;
    }

    public void setAcademyAmount(BigDecimal academyAmount) {
        this.academyAmount = academyAmount;
    }
    
    @Column(name="department_amount")
    public BigDecimal getDepartmentAmount() {
        return departmentAmount;
    }

    public void setDepartmentAmount(BigDecimal departmentAmount) {
        this.departmentAmount = departmentAmount;
    }
    
    @Column(name="performance_amount")
    public BigDecimal getPerformanceAmount() {
        return performanceAmount;
    }

    public void setPerformanceAmount(BigDecimal performanceAmount) {
        this.performanceAmount = performanceAmount;
    }
    
    @Column(name = "payfirst_id")
    public String getPayfirstId() {
        return payfirstId;
    }

    public void setPayfirstId(String payfirstId) {
        this.payfirstId = payfirstId;
    }            
    
    @Column(name="direct_funds")
    public BigDecimal getDirectFunds() {
        return directFunds;
    }

    public void setDirectFunds(BigDecimal directFunds) {
        this.directFunds = directFunds;
    }
    
    @Column(name="indirect_funds")
    public BigDecimal getIndirectFunds() {
        return indirectFunds;
    }

    public void setIndirectFunds(BigDecimal indirectFunds) {
        this.indirectFunds = indirectFunds;
    }
    
    @Column(name = "funds_sources")
    public String getFundsSources() {
        return fundsSources;
    }

    public void setFundsSources(String fundsSources) {
        this.fundsSources = fundsSources;
    }
    
    @Column(name = "CHECK_USER_ID")
    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    @Column(name = "CHECK_USER_DEPTID")
    public String getCheckUserDeptId() {
        return checkUserDeptId;
    }

    public void setCheckUserDeptId(String checkUserDeptId) {
        this.checkUserDeptId = checkUserDeptId;
    }

    @Column(name = "CHECK_USER_REALNAME")
    public String getCheckUserRealName() {
        return checkUserRealName;
    }

    public void setCheckUserRealName(String checkUserRealName) {
        this.checkUserRealName = checkUserRealName;
    }
    
    @Column(name="submit_time")
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    
    @Column(name="audit_time")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
    
    @Column(name = "MSG_TEXT")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
    
    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
