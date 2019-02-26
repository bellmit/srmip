package com.kingtake.project.entity.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 合同价款计算书--封面
 * @author onlineGenerator
 * @date 2015-08-10 15:57:10
 * @version V1.0
 */
@Entity
@Table(name = "t_pm_contract_price_cover", schema = "")
@LogEntity("合同价款计算书--封面")
@SuppressWarnings("serial")
public class TPmContractPriceCoverEntity implements java.io.Serializable {
	/**报价人姓名*/
	@Excel(name="报价人姓名")
	@FieldDesc("报价人姓名")
	private java.lang.String priceUsername;
	/**报价时间*/
	@Excel(name="报价时间")
	@FieldDesc("报价时间")
	private java.util.Date priceDate;
	/**审核人id*/
	@Excel(name="审核人id")
	@FieldDesc("审核人id")
	private java.lang.String auditUserid;
	/**审核人姓名*/
	@Excel(name="审核人姓名")
	@FieldDesc("审核人姓名")
	private java.lang.String auditUsername;
	/**审核时间*/
	@Excel(name="审核时间")
	@FieldDesc("审核时间")
	private java.util.Date auditDate;
	/**创建人*/
	@Excel(name="创建人")
	@FieldDesc("创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	@FieldDesc("创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@Excel(name="创建时间")
	@FieldDesc("创建时间")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人")
	@FieldDesc("修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@Excel(name="修改人姓名")
	@FieldDesc("修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@Excel(name="修改时间")
	@FieldDesc("修改时间")
	private java.util.Date updateDate;
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**关联合同*/
	@Excel(name="关联合同")
	@FieldDesc("关联合同")
	private java.lang.String contractId;
	/**合同名称*/
	@Excel(name="合同名称")
	@FieldDesc("合同名称")
	private java.lang.String contract;
	/**合同甲方*/
	@Excel(name="合同甲方")
	@FieldDesc("合同甲方")
	private java.lang.String contractPartyA;
	/**合同乙方*/
	@Excel(name="合同乙方")
	@FieldDesc("合同乙方")
	private java.lang.String contractPartyB;
	/**提交时间*/
	@Excel(name="提交时间")
	@FieldDesc("提交时间")
	private java.util.Date submitDate;
	/**所属类别(常量，3：生产订货类；4：研究(制)类；5：采购类)*/
	@Excel(name="所属类别(常量，3：生产订货类；4：研究(制)类；5：采购类)")
	@FieldDesc("所属类别(常量，3：生产订货类；4：研究(制)类；5：采购类)")
	private java.lang.String contractType;
	/**报价人*/
	@Excel(name="报价人")
	@FieldDesc("报价人")
	private java.lang.String priceUserid;
	/**密级*/
	@Excel(name="密级")
	@FieldDesc("密级")
	private String secrityGrade;
	/**编号*/
	@Excel(name="编号")
	@FieldDesc("编号")
	private String serialNumber;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报价人姓名
	 */
	@Column(name ="PRICE_USERNAME",nullable=true,length=50)
	public java.lang.String getPriceUsername(){
		return this.priceUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报价人姓名
	 */
	public void setPriceUsername(java.lang.String priceUsername){
		this.priceUsername = priceUsername;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  报价时间
	 */
	@Column(name ="PRICE_DATE",nullable=true)
	public java.util.Date getPriceDate(){
		return this.priceDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  报价时间
	 */
	public void setPriceDate(java.util.Date priceDate){
		this.priceDate = priceDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人id
	 */
	@Column(name ="AUDIT_USERID",nullable=true,length=50)
	public java.lang.String getAuditUserid(){
		return this.auditUserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人id
	 */
	public void setAuditUserid(java.lang.String auditUserid){
		this.auditUserid = auditUserid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人姓名
	 */
	@Column(name ="AUDIT_USERNAME",nullable=true,length=50)
	public java.lang.String getAuditUsername(){
		return this.auditUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人姓名
	 */
	public void setAuditUsername(java.lang.String auditUsername){
		this.auditUsername = auditUsername;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核时间
	 */
	@Column(name ="AUDIT_DATE",nullable=true)
	public java.util.Date getAuditDate(){
		return this.auditDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核时间
	 */
	public void setAuditDate(java.util.Date auditDate){
		this.auditDate = auditDate;
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
	 *@return: java.lang.String  关联合同
	 */
	@Column(name ="CONTRACT_ID",nullable=true,length=32)
	public java.lang.String getContractId() {
		return contractId;
	}

	public void setContractId(java.lang.String contractId) {
		this.contractId = contractId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同名称
	 */
	@Column(name ="CONTRACT",nullable=true,length=100)
	public java.lang.String getContract(){
		return this.contract;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同名称
	 */
	public void setContract(java.lang.String contract){
		this.contract = contract;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同甲方
	 */
	@Column(name ="CONTRACT_PARTY_A",nullable=true,length=100)
	public java.lang.String getContractPartyA(){
		return this.contractPartyA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同甲方
	 */
	public void setContractPartyA(java.lang.String contractPartyA){
		this.contractPartyA = contractPartyA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同乙方
	 */
	@Column(name ="CONTRACT_PARTY_B",nullable=true,length=100)
	public java.lang.String getContractPartyB(){
		return this.contractPartyB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同乙方
	 */
	public void setContractPartyB(java.lang.String contractPartyB){
		this.contractPartyB = contractPartyB;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  提交时间
	 */
	@Column(name ="SUBMIT_DATE",nullable=true)
	public java.util.Date getSubmitDate(){
		return this.submitDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  提交时间
	 */
	public void setSubmitDate(java.util.Date submitDate){
		this.submitDate = submitDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属类别(常量，3：生产订货类；4：研究(制)类；5：采购类)
	 */
	@Column(name ="PRICE_TYPE",nullable=true,length=1)
	public java.lang.String getContractType() {
		return contractType;
	}

	public void setContractType(java.lang.String contractType) {
		this.contractType = contractType;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报价人
	 */
	@Column(name ="PRICE_USERID",nullable=true,length=32)
	public java.lang.String getPriceUserid(){
		return this.priceUserid;
	}
	

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报价人
	 */
	public void setPriceUserid(java.lang.String priceUserid){
		this.priceUserid = priceUserid;
	}

	@Column(name ="SECRITY_GRADE",nullable=true,length=1)
	public String getSecrityGrade() {
		return secrityGrade;
	}

	public void setSecrityGrade(String secrityGrade) {
		this.secrityGrade = secrityGrade;
	}

	@Column(name ="SERIAL_NUMBER",nullable=true,length=20)
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
