package com.kingtake.project.entity.incomeapply;

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
 * @Description: 到账信息表
 * @author onlineGenerator
 * @date 2015-12-02 18:36:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_income_apply", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmIncomeApplyEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /**
     * 关联项目
     */
//    private String projectId;
    /**
     * 关联项目
     */
    private TPmProjectEntity project;
    private String projectNameStr;
    /**
     * 关联母来款申请，校内协作才有
     */
    private String parentApplyId;
    /**
     * 到账凭证号
     */
    @Excel(name = "到账凭证号")
    @FieldDesc("到账凭证号")
    private String voucherNo;
    
    /** 会计年度 */
    @Excel(name = "会计年度")
    private java.lang.String applyYear;
    
    /**
     * 到账表id
     */
    private String incomeId;
    
    /**
     * 发票
     */
    private TBPmInvoiceEntity invoice;

    @Excel(name = "发票号",isExportConvert=true)
    @FieldDesc("发票号")
    @Transient
    private String invoiceNo;

    /**
     * 来款金额
     */
    @Excel(name = "来款金额")
    @FieldDesc("来款金额")
    private BigDecimal incomeAmount;
    
    /**
     * 认领金额
     */
    @FieldDesc("认领金额")
    private BigDecimal applyAmount;
    
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
     * 经费来源
     */
    @FieldDesc("经费来源")
    private String fundsSources;
    
    /**
     * 归垫科目id
     */
    @FieldDesc("归垫科目ID")
    private String payfirstId;

    /**
     * 认领时间
     */
    @Excel(name = "认领时间", format = "yyyy-MM-dd HH:mm:ss")
    @FieldDesc("认领时间")
    private Date incomeTime;
    
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 来款说明
     */
    @Excel(name = "来款说明")
    @FieldDesc("来款说明")
    private String incomeRemark;
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

    private List<TSFilesEntity> certificates;// 附件
    
    /**
     * 关联附件
     */
    private String attachmentCode;

    //节点列表字符串
    private String nodeListStr;

    /**
     * 审核状态,0 未提交 1 已提交 2 通过 3 不通过
     */
    private String auditStatus;
    
    /**
     * 是否已做预算状态,0 否 1 是
     */
    private String ysStatus;
    
    /**
     * 财务状态
     */
    private String cwStatus;
    
    /**
     * 关联预算ID
     */
    private String ysApprId;

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
     * 申请人
     */
    private String applyUser;

    /**
     * 申请单位
     */
    private String applyDept;

    /**
     * 修改意见
     */
    private String msgText;
    
    /**
     * 提交时间
     */
    private Date submitTime;
    
    /**
     * 审核时间
     */
    private Date auditTime;
    
    /**
     * 是否做预算，1为是
     */
    private String fundsFlag;

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
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 到账日期
     */
    @Column(name = "INCOME_TIME", nullable = true)
    public java.util.Date getIncomeTime() {
        return this.incomeTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 到账日期
     */
    public void setIncomeTime(java.util.Date incomeTime) {
        this.incomeTime = incomeTime;
    }



    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 到账金额
     */
    @Column(name = "INCOME_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getIncomeAmount() {
        return this.incomeAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 到账金额
     */
    public void setIncomeAmount(java.math.BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
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

    @Column(name = "VOUCHER_NO")
    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    public TBPmInvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(TBPmInvoiceEntity invoice) {
        this.invoice = invoice;
    }
    
    @ManyToOne
    @JoinColumn(name = "T_P_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @Column(name = "INCOME_REMARK")
    public String getIncomeRemark() {
        return incomeRemark;
    }

    public void setIncomeRemark(String incomeRemark) {
        this.incomeRemark = incomeRemark;
    }

//    @Column(name = "t_p_id")
//    public String getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(String projectId) {
//        this.projectId = projectId;
//    }
    
    @Column(name = "parent_apply_id")
    public String getParentApplyId() {
        return parentApplyId;
    }

    public void setParentApplyId(String parentApplyId) {
        this.parentApplyId = parentApplyId;
    }

    public String convertGetInvoiceNo() {
        if (this.invoice != null) {
            return invoice.getInvoiceNum1();
        }
        return "";
    }


    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    @Transient
    public String getNodeListStr() {
        return nodeListStr;
    }

    public void setNodeListStr(String nodeListStr) {
        this.nodeListStr = nodeListStr;
    }

    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
    
    @Column(name = "ys_status")
    public String getYsStatus() {
        return ysStatus;
    }

    public void setYsStatus(String ysStatus) {
        this.ysStatus = ysStatus;
    }
    
    @Column(name = "YS_APPR_ID")
    public String getYsApprId() {
		return ysApprId;
	}

	public void setYsApprId(String ysApprId) {
		this.ysApprId = ysApprId;
	}

	@Column(name = "apply_user")
    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    @Column(name = "apply_dept")
    public String getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(String applyDept) {
        this.applyDept = applyDept;
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

    @Column(name = "MSG_TEXT")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Column(name="apply_amount")
    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
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
    
    @Column(name = "funds_sources")
    public String getFundsSources() {
        return fundsSources;
    }

    public void setFundsSources(String fundsSources) {
        this.fundsSources = fundsSources;
    }
    
    @Column(name = "payfirst_id")
    public String getPayfirstId() {
        return payfirstId;
    }

    public void setPayfirstId(String payfirstId) {
        this.payfirstId = payfirstId;
    }

    @Column(name="income_id")
    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
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

    @Column(name="funds_flag")
    public String getFundsFlag() {
        return fundsFlag;
    }

    public void setFundsFlag(String fundsFlag) {
        this.fundsFlag = fundsFlag;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 会计年度
     */
    @Column(name = "APPLY_YEAR")
    public java.lang.String getApplyYear() {
        return this.applyYear;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 会计年度
     */
    public void setApplyYear(java.lang.String applyYear) {
        this.applyYear = applyYear;
    }
    
    @Transient
	public String getProjectNameStr() {		
		String projectNameStr = "";
		if (this.project != null) {
			projectNameStr = project.getProjectName();
		}
		return projectNameStr;
	}

	public void setProjectNameStr(String projectNameStr) {
		this.projectNameStr = projectNameStr;
	}
	
	@Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "CW_STATUS")
	public String getCwStatus() {
		return cwStatus;
	}

	public void setCwStatus(String cwStatus) {
		this.cwStatus = cwStatus;
	}
    
    
}
