package com.kingtake.project.entity.funds;

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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;

/**   
 * @Title: Entity
 * @Description: 项目预算管理
 * @author onlineGenerator
 * @date 2015-07-26 15:40:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_project_funds_appr", schema = "")
@SuppressWarnings("serial")
@LogEntity("项目经费信息表")
public class TPmProjectFundsApprEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**项目基_主键*/
	private java.lang.String tpId;
	/** 会计编码 */
    @FieldDesc("会计编码")
    @Excel(name = "会计编码")
    private java.lang.String accountingCode;
	/**到账凭证号*/
	@Excel(name="到账凭证号")
	private java.lang.String voucherNum;
	
	/**
	 * 关联来款申请id
	 */
	private String incomeApplyId;
	
	/**
	 * 关联计划下达id
	 */
	private String incomePlanId;
	
	/**
	 * 关联归垫科目id
	 */
	private String payfirstId;
	
	/**
	 * 关联项目余额id
	 */
	private String balanceId;
	
	/**
	private String incomeApplyId;
	/**发票号*/
	@Excel(name="发票号")
	private java.lang.String invoiceNum;
	/** 承研部门id*/
    @FieldDesc("承研单位id")
    private java.lang.String departsId;
    @FieldDesc("承研单位")
    @Excel(name = "承研单位")
    private java.lang.String departsName;
    /** 负责人id */
    @FieldDesc("负责人id")
    private java.lang.String projectManager;
    /** 负责人 */
    @FieldDesc("负责人")
    @Excel(name = "负责人")
    private java.lang.String projectManagerName;
    /** 项目名称 */
    @FieldDesc("项目名称")
    @Excel(name = "项目名称")
    private java.lang.String projectName;
    /** 经费类型 */
    @FieldDesc("经费类型")
    @Excel(name = "经费类型", isExportConvert = true)
    private java.lang.String feeType;
    /** 项目来源 */
    @FieldDesc("项目来源")
    @Excel(name = "项目来源")
    private java.lang.String projectSource;
	/**开始年度*/
	@Excel(name="开始年度")
	private java.lang.String startYear;
	/**截止年度*/
	@Excel(name="截止年度")
	private java.lang.String endYear;
	/** 总经费 */
    @FieldDesc("总经费")
    @Excel(name = "总经费",isAmount = true)
    private java.math.BigDecimal allFee;
	/**年度经费*/
	@Excel(name="年度经费",isAmount = true)
	private java.math.BigDecimal yearFundsPlan;
	/**总预算经费*/
	@Excel(name="总预算经费",isAmount = true)
	private java.math.BigDecimal totalFunds;
	/**归垫经费(计划类)*/
	@Excel(name="归垫经费",isAmount = true)
	private java.math.BigDecimal reinFundsPlan;
	/**到账经费*/
	@Excel(name="到账经费",isAmount = true)
	private java.math.BigDecimal accountFunds;
	/**计划下达经费*/
	@Excel(name="计划下达经费",isAmount = true)
	private java.math.BigDecimal incomeplanAmount;
	/**公文编号*/
    @Excel(name="公文编号")
    private java.lang.String billNum;
    /**计划下达文件号*/
    @Excel(name="计划下达文件号")
    private java.lang.String documentNo;
    /** 项目组成员 */
    @FieldDesc("项目组成员id")
    private java.lang.String membersId;
    @FieldDesc("项目组成员名称")
    @Excel(name = "项目组成员")
    private java.lang.String membersName;
    /**是否为预算变更的标志*/
	@Excel(name="是否为预算变更", isExportConvert = true)
	@FieldDesc("是否为预算变更的标志")
	private String changeFlag;
	/**项目的主要内容及技术指标*/
	private java.lang.String content;
	/**承研单位审核意见*/
	private java.lang.String developerAuditOpinion;
	/**责任单位审核意见*/
	private java.lang.String dutyAuditOpinion;
	/**科研部计划处审核意见*/
	private java.lang.String researchAuditOpinion;
	/**校务部财务处审核意见*/
	private java.lang.String financeAuditOpinion;
	/**科研部审批意见*/
	private java.lang.String developerApprovalOpinion;
	/**创建人*/
	@FieldDesc("创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@FieldDesc("创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@FieldDesc("编制时间")
	@Excel(name="编制时间")
	private java.util.Date createDate;
	/**修改人*/
	@FieldDesc("修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@FieldDesc("修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@FieldDesc("修改时间")
	private java.util.Date updateDate;
	/**合同状态*/
	@Excel(name="合同状态", isExportConvert = true)
	@FieldDesc("合同状态")
	private java.lang.String finishFlag;
	/**完成时间*/
	@FieldDesc("完成时间")
	private java.util.Date finishTime;
	/**完成人id*/
	@FieldDesc("完成人id")
	private java.lang.String finishUserid;
	/**完成人姓名*/
	@FieldDesc("完成人姓名")
	private java.lang.String finishUsername;
	private TPmApprReceiveLogsEntity backLog;
	@FieldDesc("审批发送记录")
    private List<TPmApprSendLogsEntity> apprSendLogs;
	@FieldDesc("审批记录")
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;
	
	@FieldDesc("财务状态")
    private String cwStatus;
	
	
	/**
	 * 预算类别 1：总预算 2：年度预算
	 */
	private String fundsCategory;

    /**
     * 预算类型, 1零基预算  2
     */
    private String fundsType;
    
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}

	@Column(name ="T_P_ID",nullable=true,length=32)
	public java.lang.String getTpId() {
		return tpId;
	}

	public void setTpId(java.lang.String tpId) {
		this.tpId = tpId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到账凭证号
	 */
	@Column(name ="VOUCHER_NUM",nullable=true,length=20)
	public java.lang.String getVoucherNum(){
		return this.voucherNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到账凭证号
	 */
	public void setVoucherNum(java.lang.String voucherNum){
		this.voucherNum = voucherNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发票号
	 */
	@Column(name ="INVOICE_NUM",nullable=true,length=20)
	public java.lang.String getInvoiceNum(){
		return this.invoiceNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发票号
	 */
	public void setInvoiceNum(java.lang.String invoiceNum){
		this.invoiceNum = invoiceNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开始年度
	 */
	@Column(name ="START_YEAR",nullable=true,length=4)
	public java.lang.String getStartYear(){
		return this.startYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开始年度
	 */
	public void setStartYear(java.lang.String startYear){
		this.startYear = startYear;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  截止年度
	 */
	@Column(name ="END_YEAR",nullable=true,length=4)
	public java.lang.String getEndYear(){
		return this.endYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  截止年度
	 */
	public void setEndYear(java.lang.String endYear){
		this.endYear = endYear;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  到账经费
	 */
	@Column(name ="ACCOUNT_FUNDS",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getAccountFunds(){
		return this.accountFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  到账经费
	 */
	public void setAccountFunds(java.math.BigDecimal accountFunds){
		this.accountFunds = accountFunds;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  计划下达经费
	 */
	@Column(name ="INCOMEPLAN_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getIncomeplanAmount(){
		return this.incomeplanAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  计划下达经费
	 */
	public void setIncomeplanAmount(java.math.BigDecimal incomeplanAmount){
		this.incomeplanAmount = incomeplanAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目的主要内容及技术指标
	 */
	@Column(name ="CONTENT",nullable=true,length=2000)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目的主要内容及技术指标
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承研单位审核意见
	 */
	@Column(name ="DEVELOPER_AUDIT_OPINION",nullable=true,length=100)
	public java.lang.String getDeveloperAuditOpinion(){
		return this.developerAuditOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承研单位审核意见
	 */
	public void setDeveloperAuditOpinion(java.lang.String developerAuditOpinion){
		this.developerAuditOpinion = developerAuditOpinion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位审核意见
	 */
	@Column(name ="DUTY_AUDIT_OPINION",nullable=true,length=100)
	public java.lang.String getDutyAuditOpinion(){
		return this.dutyAuditOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位审核意见
	 */
	public void setDutyAuditOpinion(java.lang.String dutyAuditOpinion){
		this.dutyAuditOpinion = dutyAuditOpinion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  科研部计划处审核意见
	 */
	@Column(name ="RESEARCH_AUDIT_OPINION",nullable=true,length=100)
	public java.lang.String getResearchAuditOpinion(){
		return this.researchAuditOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  科研部计划处审核意见
	 */
	public void setResearchAuditOpinion(java.lang.String researchAuditOpinion){
		this.researchAuditOpinion = researchAuditOpinion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  校务部财务处审核意见
	 */
	@Column(name ="FINANCE_AUDIT_OPINION",nullable=true,length=100)
	public java.lang.String getFinanceAuditOpinion(){
		return this.financeAuditOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  校务部财务处审核意见
	 */
	public void setFinanceAuditOpinion(java.lang.String financeAuditOpinion){
		this.financeAuditOpinion = financeAuditOpinion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  科研部审批意见
	 */
	@Column(name ="DEVELOPER_APPROVAL_OPINION",nullable=true,length=100)
	public java.lang.String getDeveloperApprovalOpinion(){
		return this.developerApprovalOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  科研部审批意见
	 */
	public void setDeveloperApprovalOpinion(java.lang.String developerApprovalOpinion){
		this.developerApprovalOpinion = developerApprovalOpinion;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  年度经费
	 */
	@Column(name ="YEAR_FUNDS_PLAN",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getYearFundsPlan(){
		return this.yearFundsPlan;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  年度经费
	 */
	public void setYearFundsPlan(java.math.BigDecimal yearFundsPlan){
		this.yearFundsPlan = yearFundsPlan;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  总预算金额
	 */
	@Column(name ="TOTAL_FUNDS",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getTotalFunds(){
		return this.totalFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  总预算金额
	 */
	public void setTotalFunds(java.math.BigDecimal totalFunds){
		this.totalFunds = totalFunds;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  归垫经费
	 */
	@Column(name ="REIN_FUNDS_PLAN",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getReinFundsPlan(){
		return this.reinFundsPlan;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  归垫经费
	 */
	public void setReinFundsPlan(java.math.BigDecimal reinFundsPlan){
		this.reinFundsPlan = reinFundsPlan;
	}

	@Column(name = "BILL_NUM", nullable = true, length = 20)
	public java.lang.String getBillNum() {
        return billNum;
    }

    public void setBillNum(java.lang.String billNum) {
        this.billNum = billNum;
    }
    
    @Column(name = "DOCUMENT_NO", nullable = true, length = 20)
	public java.lang.String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(java.lang.String documentNo) {
        this.documentNo = documentNo;
    }

    @Column(name = "PROJECT_NAME", nullable = true, length = 100)
    public java.lang.String getProjectName() {
        return projectName;
    }

    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "ACCOUNTING_CODE", nullable = true, length = 20)
    public java.lang.String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(java.lang.String accountingCode) {
        this.accountingCode = accountingCode;
    }

    @Column(name = "DEPARTS_ID", nullable = true, length = 50)
    public java.lang.String getDepartsId() {
        return departsId;
    }

    public void setDepartsId(java.lang.String departsId) {
        this.departsId = departsId;
    }
    
    
    
    @Column(name = "CW_STATUS", nullable = true, length = 100)
    public java.lang.String getCwStatus() {
        return cwStatus;
    }

    public void setCwStatus(java.lang.String cwStatus) {
        this.cwStatus = cwStatus;
    }

    
    
    @Column(name = "DEPARTS_NAME", nullable = true, length = 100)
    public java.lang.String getDepartsName() {
        return departsName;
    }

    public void setDepartsName(java.lang.String departsName) {
        this.departsName = departsName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 负责人
     */
    @Column(name = "PROJECT_MANAGER", nullable = false, length = 50)
    public java.lang.String getProjectManager() {
        return this.projectManager;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 负责人
     */
    public void setProjectManager(java.lang.String projectManager) {
        this.projectManager = projectManager;
    }

    @Column(name = "PROJECT_MANAGER_NAME", nullable = false, length = 100)
    public java.lang.String getProjectManagerName() {
        return projectManagerName;
    }

    public void setProjectManagerName(java.lang.String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 经费类型
     */
    @Column(name = "FEE_TYPE", nullable = true, length = 32)
    public java.lang.String getFeeType() {
        return this.feeType;
    }

    public java.lang.String convertGetFeeType() {
    	if(StringUtil.isNotEmpty(this.tpId)){
    		SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
    		TBFundsPropertyEntity fee = systemService.get(TBFundsPropertyEntity.class, this.feeType);
    		if(fee != null){
    			return fee.getFundsName();
    		}
    	}
    	return "";
    }
    
    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 经费类型
     */
    public void setFeeType(java.lang.String feeType) {
        this.feeType = feeType;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目来源
     */
    @Column(name = "PROJECT_SOURCE", nullable = true, length = 100)
    public java.lang.String getProjectSource() {
        return this.projectSource;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目来源
     */
    public void setProjectSource(java.lang.String projectSource) {
        this.projectSource = projectSource;
    }
    
    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 总经费
     */
    @Column(name = "ALL_FEE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getAllFee() {
        return this.allFee;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 总经费
     */
    public void setAllFee(java.math.BigDecimal allFee) {
        this.allFee = allFee;
    }

    @Column(name = "MEMBERS_ID", nullable = true, length = 2000)
    public java.lang.String getMembersId() {
        return membersId;
    }

    public void setMembersId(java.lang.String membersId) {
        this.membersId = membersId;
    }

    @Column(name = "MEMBERS_NAME", nullable = true, length = 4000)
    public java.lang.String getMembersName() {
        return membersName;
    }

    public void setMembersName(java.lang.String membersName) {
        this.membersName = membersName;
    }
	
	@Column(name ="CHANGE_FLAG",nullable=true)
	public String getChangeFlag() {
		return changeFlag;
	}

	public void setChangeFlag(String changeFlag) {
		this.changeFlag = changeFlag;
	}
	
	public String convertGetChangeFlag() {
		if(StringUtil.isNotEmpty(this.changeFlag)){
			return SrmipConstants.dicStandardMap.get("SFBZ").get(this.changeFlag).toString();
		}
		return "";
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人姓名
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人姓名
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人姓名
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人姓名
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	
	@Column(name ="FINISH_FLAG",nullable=true)
	public java.lang.String getFinishFlag() {
		return finishFlag;
	}

	public void setFinishFlag(java.lang.String finishFlag) {
		this.finishFlag = finishFlag;
	}
	
	public java.lang.String convertGetFinishFlag() {
		if(StringUtil.isNotEmpty(this.finishFlag)){
			return SrmipConstants.dicResearchMap.get("SPZT").get(this.finishFlag).toString();
		}
		return "";
	}

	@Column(name ="FINISH_TIME",nullable=true)
	public java.util.Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(java.util.Date finishTime) {
		this.finishTime = finishTime;
	}
	
	@Column(name ="FINISH_USERID",nullable=true,length=32)
	public java.lang.String getFinishUserid() {
		return finishUserid;
	}

	public void setFinishUserid(java.lang.String finishUserid) {
		this.finishUserid = finishUserid;
	}

	@Column(name ="FINISH_USERNAME",nullable=true,length=100)
	public java.lang.String getFinishUsername() {
		return finishUsername;
	}

	public void setFinishUsername(java.lang.String finishUsername) {
		this.finishUsername = finishUsername;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BACK_ID")
	public TPmApprReceiveLogsEntity getBackLog() {
		return backLog;
	}

	public void setBackLog(TPmApprReceiveLogsEntity backLog) {
		this.backLog = backLog;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "APPR_ID")
	public List<TPmApprSendLogsEntity> getApprSendLogs() {
		return apprSendLogs;
	}

	public void setApprSendLogs(List<TPmApprSendLogsEntity> apprSendLogs) {
		this.apprSendLogs = apprSendLogs;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "APPR_ID")
	public List<TPmApprReceiveLogsEntity> getApprReceiveLogs() {
		return apprReceiveLogs;
	}

	public void setApprReceiveLogs(List<TPmApprReceiveLogsEntity> apprReceiveLogs) {
		this.apprReceiveLogs = apprReceiveLogs;
	}

    @Column(name = "FUNDS_TYPE")
    public String getFundsType() {
        return fundsType;
    }

    public void setFundsType(String fundsType) {
        this.fundsType = fundsType;
    }
    
    @Column(name = "FUNDS_CATEGORY")
    public String getFundsCategory() {
		return fundsCategory;
	}

	public void setFundsCategory(String fundsCategory) {
		this.fundsCategory = fundsCategory;
	}

	@Column(name="income_apply_id")
    public String getIncomeApplyId() {
        return incomeApplyId;
    }

    public void setIncomeApplyId(String incomeApplyId) {
        this.incomeApplyId = incomeApplyId;
    }

    @Column(name="incomeplan_id")
    public String getIncomePlanId() {
        return incomePlanId;
    }

    public void setIncomePlanId(String incomePlanId) {
        this.incomePlanId = incomePlanId;
    }
    
    @Column(name="payfirst_id")
    public String getPayfirstId() {
        return payfirstId;
    }

    public void setPayfirstId(String payfirstId) {
        this.payfirstId = payfirstId;
    }
    
    @Column(name="balance_id")
    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }
}
