package com.kingtake.project.entity.apprcontractcheck;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;


/**   
 * @Title: Entity
 * @Description: 合同验收报告
 * @author onlineGenerator
 * @date 2015-08-31 15:26:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_check", schema = "")
@SuppressWarnings("serial")
public class TPmContractCheckEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**密级*/
	@Excel(name="密级")
	private java.lang.String secretDegree;
	/**合同编号*/
	@Excel(name="合同编号")
	private java.lang.String contractCode;
	/**会计编码*/
	@Excel(name="会计编码")
	private java.lang.String accountingCode;
	/**项目名称*/
	@Excel(name="项目名称")
	private java.lang.String projectName;
	/**合同id*/
	@Excel(name="合同id")
	private java.lang.String contractId;
	/**合同名称*/
	@Excel(name="合同名称")
	private java.lang.String contractName;
	/**责任单位id*/
	@Excel(name="责任单位id")
	private java.lang.String dutyDepartid;
	/**责任单位名称*/
	@Excel(name="责任单位名称")
	private java.lang.String dutyDepartname;
	/**合同乙方*/
	@Excel(name="合同乙方")
	private java.lang.String contractSecondParty;
	/**验收时间*/
	@Excel(name="验收时间")
	private java.util.Date checkTime;
	/**合同价款*/
	@Excel(name="合同价款",isAmount = true)
	private java.math.BigDecimal contractAmount;
	/**签订时间*/
	@Excel(name="签订时间")
	private java.util.Date contractSigningTime;
	/**(执行期限)合同开始时间*/
	@Excel(name="(执行期限)合同开始时间")
	private java.util.Date beginTime;
	/**(执行期限)合同截止时间*/
	@Excel(name="(执行期限)合同截止时间")
	private java.util.Date endTime;
	/**已付价款*/
	@Excel(name="已付价款",isAmount = true)
	private java.math.BigDecimal paidMoney;
	/**待付价款*/
	@Excel(name="待付价款",isAmount = true)
	private java.math.BigDecimal waitMoney;
	/**组织单位id*/
	@Excel(name="组织单位id")
	private java.lang.String organizationUnitid;
	/**组织单位名称*/
	@Excel(name="组织单位名称")
	private java.lang.String organizationUnitname;
	/**合同标的交付情况*/
	@Excel(name="合同标的交付情况")
	private java.lang.String contractTarget;
	/**主要研究成果*/
	@Excel(name="主要研究成果")
	private java.lang.String researchResult;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String memo;
	/**合同类型*/
	@Excel(name="合同类型")
	private java.lang.String contractType;
	
	
	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@Excel(name="创建时间")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@Excel(name="修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@Excel(name="修改时间")
	private java.util.Date updateDate;
	
	/**处理状态*/
	@Excel(name="处理状态")
	private java.lang.String operationStatus;
	/**完成时间*/
	@Excel(name="完成时间")
	private java.util.Date finishTime;
	/**驳回人id*/
	@Excel(name="完成人id")
	@FieldDesc("完成人id")
	private java.lang.String finishUserid;//FINISH_USERID
	/**驳回人姓名*/
	@Excel(name="完成人姓名")
	@FieldDesc("完成人姓名")
	private java.lang.String finishUsername;//FINISH_USERNAME
	
	@Excel(name="驳回记录")
	private TPmApprReceiveLogsEntity backLog;
	@FieldDesc("审批发送记录")
    private List<TPmApprSendLogsEntity> apprSendLogs;
	@FieldDesc("审批记录")
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;
	
	@FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
	
	/**
     * 附件编码
     */
    private String attachmentCode;
	
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
	 *@return: java.lang.String  密级
	 */
	@Column(name ="SECRET_DEGREE",nullable=true,length=1)
	public java.lang.String getSecretDegree(){
		return this.secretDegree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  密级
	 */
	public void setSecretDegree(java.lang.String secretDegree){
		this.secretDegree = secretDegree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同编号
	 */
	@Column(name ="CONTRACT_CODE",nullable=true,length=30)
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
	 *@return: java.lang.String  会计编码
	 */
	@Column(name ="ACCOUNTING_CODE",nullable=true,length=20)
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
	 *@return: java.lang.String  项目名称
	 */
	@Column(name ="PROJECT_NAME",nullable=false,length=100)
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
	 *@return: java.lang.String  合同id
	 */
	@Column(name ="CONTRACT_ID",nullable=true,length=32)
	public java.lang.String getContractId(){
		return this.contractId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同id
	 */
	public void setContractId(java.lang.String contractId){
		this.contractId = contractId;
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
	 *@return: java.lang.String  责任单位id
	 */
	@Column(name ="DUTY_DEPARTID",nullable=true,length=32)
	public java.lang.String getDutyDepartid(){
		return this.dutyDepartid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位id
	 */
	public void setDutyDepartid(java.lang.String dutyDepartid){
		this.dutyDepartid = dutyDepartid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  责任单位名称
	 */
	@Column(name ="DUTY_DEPARTNAME",nullable=true,length=60)
	public java.lang.String getDutyDepartname(){
		return this.dutyDepartname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  责任单位名称
	 */
	public void setDutyDepartname(java.lang.String dutyDepartname){
		this.dutyDepartname = dutyDepartname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同乙方
	 */
	@Column(name ="CONTRACT_SECOND_PARTY",nullable=true,length=100)
	public java.lang.String getContractSecondParty(){
		return this.contractSecondParty;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同乙方
	 */
	public void setContractSecondParty(java.lang.String contractSecondParty){
		this.contractSecondParty = contractSecondParty;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  验收时间
	 */
	@Column(name ="CHECK_TIME",nullable=true)
	public java.util.Date getCheckTime(){
		return this.checkTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  验收时间
	 */
	public void setCheckTime(java.util.Date checkTime){
		this.checkTime = checkTime;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  合同价款
	 */
	@Column(name ="CONTRACT_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getContractAmount(){
		return this.contractAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  合同价款
	 */
	public void setContractAmount(java.math.BigDecimal contractAmount){
		this.contractAmount = contractAmount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  签订时间
	 */
	@Column(name ="CONTRACT_SIGNING_TIME",nullable=true)
	public java.util.Date getContractSigningTime(){
		return this.contractSigningTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  签订时间
	 */
	public void setContractSigningTime(java.util.Date contractSigningTime){
		this.contractSigningTime = contractSigningTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  (执行期限)合同开始时间
	 */
	@Column(name ="BEGIN_TIME",nullable=true)
	public java.util.Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  (执行期限)合同开始时间
	 */
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  (执行期限)合同截止时间
	 */
	@Column(name ="END_TIME",nullable=true)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  (执行期限)合同截止时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  已付价款
	 */
	@Column(name ="PAID_MONEY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getPaidMoney(){
		return this.paidMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  已付价款
	 */
	public void setPaidMoney(java.math.BigDecimal paidMoney){
		this.paidMoney = paidMoney;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  待付价款
	 */
	@Column(name ="WAIT_MONEY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getWaitMoney(){
		return this.waitMoney;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  待付价款
	 */
	public void setWaitMoney(java.math.BigDecimal waitMoney){
		this.waitMoney = waitMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组织单位id
	 */
	@Column(name ="ORGANIZATION_UNITID",nullable=true,length=32)
	public java.lang.String getOrganizationUnitid(){
		return this.organizationUnitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织单位id
	 */
	public void setOrganizationUnitid(java.lang.String organizationUnitid){
		this.organizationUnitid = organizationUnitid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  组织单位名称
	 */
	@Column(name ="ORGANIZATION_UNITNAME",nullable=true,length=60)
	public java.lang.String getOrganizationUnitname(){
		return this.organizationUnitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  组织单位名称
	 */
	public void setOrganizationUnitname(java.lang.String organizationUnitname){
		this.organizationUnitname = organizationUnitname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同标的交付情况
	 */
	@Column(name ="CONTRACT_TARGET",nullable=true,length=3000)
	public java.lang.String getContractTarget(){
		return this.contractTarget;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同标的交付情况
	 */
	public void setContractTarget(java.lang.String contractTarget){
		this.contractTarget = contractTarget;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主要研究成果
	 */
	@Column(name ="RESEARCH_RESULT",nullable=true,length=3000)
	public java.lang.String getResearchResult(){
		return this.researchResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要研究成果
	 */
	public void setResearchResult(java.lang.String researchResult){
		this.researchResult = researchResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="MEMO",nullable=true,length=300)
	public java.lang.String getMemo(){
		return this.memo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setMemo(java.lang.String memo){
		this.memo = memo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同类型
	 */
	@Column(name ="CONTRACT_TYPE",nullable=true,length=2)
	public java.lang.String getContractType(){
		return this.contractType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同类型
	 */
	public void setContractType(java.lang.String contractType){
		this.contractType = contractType;
	}
	
	
	@Column(name ="OPERATION_STATUS",nullable=true,length=1)
	public java.lang.String getOperationStatus(){
		return this.operationStatus;
	}
	public void setOperationStatus(java.lang.String operationStatus){
		this.operationStatus = operationStatus;
	}
	
	@Column(name ="FINISH_TIME",nullable=true)
	public java.util.Date getFinishTime(){
		return this.finishTime;
	}
	public void setFinishTime(java.util.Date finishTime){
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
