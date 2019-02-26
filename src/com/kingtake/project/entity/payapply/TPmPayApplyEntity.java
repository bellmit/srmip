package com.kingtake.project.entity.payapply;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 支付申请
 * @author onlineGenerator
 * @date 2016-03-03 15:44:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_pay_apply", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
public class TPmPayApplyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**承研单位名称*/
	@Excel(name="承研单位名称")
	private java.lang.String devdepartName;
	/**项目负责人名称*/
	@Excel(name="项目负责人名称")
	private java.lang.String projectManagerName;
	/**项目名称*/
	@Excel(name="项目名称")
	private java.lang.String projectName;
	/**会计编码*/
	@Excel(name="会计编码")
	private java.lang.String accountingCode;
	/**项目编码*/
	@Excel(name="项目编码")
	private java.lang.String projectCode;
	/**合同名称*/
	@Excel(name="合同名称")
	private java.lang.String contractName;
	/**合同编号*/
	@Excel(name="合同编号")
	private java.lang.String contractCode;
	/**合同乙方*/
	@Excel(name="合同乙方")
	private java.lang.String contractUnitnameb;
	/**合同开始时间*/
	@Excel(name="合同开始时间")
	private java.util.Date contractStartTime;
	/**合同结束时间*/
	@Excel(name="合同结束时间")
	private java.util.Date contractEndTime;
	/**合同总价款*/
	@Excel(name="合同总价款",isAmount = true)
	private java.math.BigDecimal contractTotalAmount;
	/**已付合同款*/
	@Excel(name="已付合同款",isAmount = true)
    private java.math.BigDecimal contractPaidAmount;
	/**本期付款金额*/
	@Excel(name="本期付款金额",isAmount = true)
	private java.math.BigDecimal currentPayAmount;
	/**本期付款要求*/
	@Excel(name="本期付款要求")
	private java.lang.String payDemand;
	/**合同执行情况*/
	@Excel(name="合同执行情况")
	private java.lang.String executeState;
	/**项目组意见*/
	@Excel(name="项目组意见")
	private java.lang.String projectTeamSuggestion;
	/**关联合同节点id*/
	private java.lang.String contractNodeId;

    /**
     * 合同节点名称
     */
    private String contractNodeName;
    /**
     * 操作状态
     */
    private java.lang.String operateStatus;

    /**
     * 流程结束人姓名
     */
    private String finishUsername;

    /**
     * 流程结束时间
     */
    private Date finishTime;
    /**
     * 流程结束人id
     */
    private String finishUserid;

    /** createBy */
    private java.lang.String createBy;
    /** createName */
    private java.lang.String createName;
    /** createDate */
    private java.util.Date createDate;
    /** updateBy */
    private java.lang.String updateBy;
    /** updateName */
    private java.lang.String updateName;
    /** updateDate */
    private java.util.Date updateDate;
	
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承研单位名称
	 */
	@Column(name ="DEVDEPART_NAME",nullable=true,length=50)
	public java.lang.String getDevdepartName(){
		return this.devdepartName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承研单位名称
	 */
	public void setDevdepartName(java.lang.String devdepartName){
		this.devdepartName = devdepartName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目负责人名称
	 */
	@Column(name ="PROJECT_MANAGER_NAME",nullable=true,length=50)
	public java.lang.String getProjectManagerName(){
		return this.projectManagerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目负责人名称
	 */
	public void setProjectManagerName(java.lang.String projectManagerName){
		this.projectManagerName = projectManagerName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目名称
	 */
	@Column(name ="PROJECT_NAME",nullable=true,length=32)
	public java.lang.String getProjectName(){
		return this.projectName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目名称
	 */
	public void setProjectName(java.lang.String projectName){
		this.projectName = projectName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  会计编码
	 */
	@Column(name ="ACCOUNTING_CODE",nullable=true,length=32)
	public java.lang.String getAccountingCode(){
		return this.accountingCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会计编码
	 */
	public void setAccountingCode(java.lang.String accountingCode){
		this.accountingCode = accountingCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目编码
	 */
	@Column(name ="PROJECT_CODE",nullable=true,length=32)
	public java.lang.String getProjectCode(){
		return this.projectCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目编码
	 */
	public void setProjectCode(java.lang.String projectCode){
		this.projectCode = projectCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同名称
	 */
	@Column(name ="CONTRACT_NAME",nullable=true,length=100)
	public java.lang.String getContractName(){
		return this.contractName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同名称
	 */
	public void setContractName(java.lang.String contractName){
		this.contractName = contractName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同编号
	 */
	@Column(name ="CONTRACT_CODE",nullable=true,length=50)
	public java.lang.String getContractCode(){
		return this.contractCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同编号
	 */
	public void setContractCode(java.lang.String contractCode){
		this.contractCode = contractCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同乙方
	 */
	@Column(name ="CONTRACT_UNITNAMEB",nullable=true,length=100)
	public java.lang.String getContractUnitnameb(){
		return this.contractUnitnameb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同乙方
	 */
	public void setContractUnitnameb(java.lang.String contractUnitnameb){
		this.contractUnitnameb = contractUnitnameb;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  合同开始时间
	 */
	@Column(name ="CONTRACT_START_TIME",nullable=true)
	public java.util.Date getContractStartTime(){
		return this.contractStartTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  合同开始时间
	 */
	public void setContractStartTime(java.util.Date contractStartTime){
		this.contractStartTime = contractStartTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  合同结束时间
	 */
	@Column(name ="CONTRACT_END_TIME",nullable=true)
	public java.util.Date getContractEndTime(){
		return this.contractEndTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  合同结束时间
	 */
	public void setContractEndTime(java.util.Date contractEndTime){
		this.contractEndTime = contractEndTime;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  合同总价款
	 */
	@Column(name ="CONTRACT_TOTAL_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getContractTotalAmount(){
		return this.contractTotalAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  合同总价款
	 */
	public void setContractTotalAmount(java.math.BigDecimal contractTotalAmount){
		this.contractTotalAmount = contractTotalAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  已付合同款
	 */
    @Column(name = "CONTRACT_PAID_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getContractPaidAmount() {
        return this.contractPaidAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  已付合同款
	 */
    public void setContractPaidAmount(java.math.BigDecimal contractPaidAmount) {
        this.contractPaidAmount = contractPaidAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期付款金额
	 */
	@Column(name ="CURRENT_PAY_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getCurrentPayAmount(){
		return this.currentPayAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期付款金额
	 */
	public void setCurrentPayAmount(java.math.BigDecimal currentPayAmount){
		this.currentPayAmount = currentPayAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  本期付款要求
	 */
	@Column(name ="PAY_DEMAND",nullable=true,length=4000)
	public java.lang.String getPayDemand(){
		return this.payDemand;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  本期付款要求
	 */
	public void setPayDemand(java.lang.String payDemand){
		this.payDemand = payDemand;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同执行情况
	 */
	@Column(name ="EXECUTE_STATE",nullable=true,length=4000)
	public java.lang.String getExecuteState(){
		return this.executeState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同执行情况
	 */
	public void setExecuteState(java.lang.String executeState){
		this.executeState = executeState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目组意见
	 */
	@Column(name ="PROJECT_TEAM_SUGGESTION",nullable=true,length=4000)
	public java.lang.String getProjectTeamSuggestion(){
		return this.projectTeamSuggestion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目组意见
	 */
	public void setProjectTeamSuggestion(java.lang.String projectTeamSuggestion){
		this.projectTeamSuggestion = projectTeamSuggestion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联合同节点id
	 */
	@Column(name ="CONTRACT_NODE_ID",nullable=true,length=32)
	public java.lang.String getContractNodeId(){
		return this.contractNodeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联合同节点id
	 */
	public void setContractNodeId(java.lang.String contractNodeId){
		this.contractNodeId = contractNodeId;
	}

    @Column(name = "OPERATION_STATUS")
    public java.lang.String getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(java.lang.String operateStatus) {
        this.operateStatus = operateStatus;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 完成时间
     */
    @Column(name = "FINISH_TIME", nullable = true)
    public java.util.Date getFinishTime() {
        return this.finishTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 完成时间
     */
    public void setFinishTime(java.util.Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 完成操作人id
     */
    @Column(name = "FINISH_USERID", nullable = true, length = 32)
    public java.lang.String getFinishUserid() {
        return this.finishUserid;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 完成操作人id
     */
    public void setFinishUserid(java.lang.String finishUserid) {
        this.finishUserid = finishUserid;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 完成操作人姓名
     */
    @Column(name = "FINISH_USERNAME", nullable = true, length = 50)
    public java.lang.String getFinishUsername() {
        return this.finishUsername;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 完成操作人姓名
     */
    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
    }

    @Column(name = "CREATE_BY")
    public java.lang.String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_NAME")
    public java.lang.String getCreateName() {
        return createName;
    }

    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_DATE")
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_BY")
    public java.lang.String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_NAME")
    public java.lang.String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "UPDATE_DATE")
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "CONTRACT_NODE_NAME")
    public String getContractNodeName() {
        return contractNodeName;
    }

    public void setContractNodeName(String contractNodeName) {
        this.contractNodeName = contractNodeName;
    }

}
