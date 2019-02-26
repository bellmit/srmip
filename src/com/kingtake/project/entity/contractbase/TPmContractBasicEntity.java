package com.kingtake.project.entity.contractbase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 合同基本信息
 * @author onlineGenerator
 * @date 2015-08-14 17:18:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_basic", schema = "")
@SuppressWarnings("serial")
public class TPmContractBasicEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String rid;
	/**进出帐合同审批表主键*/
	@Excel(name="进出帐合同审批表主键")
	private java.lang.String inOutContractid;
	/**法人单位名称(甲方)*/
	@Excel(name="法人单位名称(甲方)")
	private java.lang.String unitNameA;
	/**法人单位名称(乙方)*/
	@Excel(name="法人单位名称(乙方)")
	private java.lang.String unitNameB;
	/**法定代表人职务(甲方)*/
	@Excel(name="法定代表人职务(甲方)")
	private java.lang.String unitPositionA;
	/**法定代表人职务(乙方)*/
	@Excel(name="法定代表人职务(乙方)")
	private java.lang.String unitPositionB;
	/**法定代表人姓名(甲方)*/
	@Excel(name="法定代表人姓名(甲方)")
	private java.lang.String nameA;
	/**法定代表人姓名(乙方)*/
	@Excel(name="法定代表人姓名(乙方)")
	private java.lang.String nameB;
	/**代理单位名称(甲方)*/
	@Excel(name="代理单位名称(甲方)")
	private java.lang.String agencyUintNameA;
	/**代表单位名称(乙方)*/
	@Excel(name="代表单位名称(乙方)")
	private java.lang.String agencyUnitNameB;
	/**代理人职务(甲方)*/
	@Excel(name="代理人职务(甲方)")
	private java.lang.String agencyUnitPositionA;
	/**代理人职务(乙方)*/
	@Excel(name="代理人职务(乙方)")
	private java.lang.String agencyUnitPositionB;
	/**代理人姓名(甲方)*/
	@Excel(name="代理人姓名(甲方)")
	private java.lang.String agencyNameA;
	/**代理人姓名(乙方)*/
	@Excel(name="代理人姓名(乙方)")
	private java.lang.String agencyNameB;
	/**单位地址(甲方)*/
	@Excel(name="单位地址(甲方)")
	private java.lang.String addressA;
	/**单位地址(乙方)*/
	@Excel(name="单位地址(乙方)")
	private java.lang.String addressB;
	/**邮政编码(甲方)*/
	@Excel(name="邮政编码(甲方)")
	private java.lang.String postalcodeA;
	/**邮政编码(乙方)*/
	@Excel(name="邮政编码(乙方)")
	private java.lang.String postalcodeB;
	/**联系电话(甲方)*/
	@Excel(name="联系电话(甲方)")
	private java.lang.String telA;
	/**联系电话(乙方)*/
	@Excel(name="联系电话(乙方)")
	private java.lang.String telB;
	/**账户名称(甲方)*/
	@Excel(name="账户名称(甲方)")
	private java.lang.String accountNameA;
	/**帐户名称(乙方)*/
	@Excel(name="帐户名称(乙方)")
	private java.lang.String accountNameB;
	/**开户银行(甲方)*/
	@Excel(name="开户银行(甲方)")
	private java.lang.String bankA;
	/**开户银行(乙方)*/
	@Excel(name="开户银行(乙方)")
	private java.lang.String bankB;
	/**帐号(甲方)*/
	@Excel(name="帐号(甲方)")
	private java.lang.String accountIdA;
	/**帐号(乙方)*/
	@Excel(name="帐号(乙方)")
	private java.lang.String accountIdB;
	/**签字地点(甲方)*/
	@Excel(name="签字地点(甲方)")
	private java.lang.String signAddressA;
	/**签字地点(乙方)*/
	@Excel(name="签字地点(乙方)")
	private java.lang.String signAddressB;
	/**第三方*/
	@Excel(name="第三方")
	private java.lang.String theThird;
	/**甲方合同履行监督单位*/
	@Excel(name="甲方合同履行监督单位")
	private java.lang.String monitorUnit;
	/**总军事代表*/
	@Excel(name="总军事代表")
	private java.lang.String militaryDeputy;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getRid(){
		return this.rid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setRid(java.lang.String rid){
		this.rid = rid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  进出帐合同审批表主键
	 */
	@Column(name ="IN_OUT_CONTRACTID",nullable=true,length=32)
	public java.lang.String getInOutContractid(){
		return this.inOutContractid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  进出帐合同审批表主键
	 */
	public void setInOutContractid(java.lang.String inOutContractid){
		this.inOutContractid = inOutContractid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人单位名称(甲方)
	 */
	@Column(name ="UNIT_NAME_A",nullable=true,length=60)
	public java.lang.String getUnitNameA(){
		return this.unitNameA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人单位名称(甲方)
	 */
	public void setUnitNameA(java.lang.String unitNameA){
		this.unitNameA = unitNameA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人单位名称(乙方)
	 */
	@Column(name ="UNIT_NAME_B",nullable=true,length=60)
	public java.lang.String getUnitNameB(){
		return this.unitNameB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人单位名称(乙方)
	 */
	public void setUnitNameB(java.lang.String unitNameB){
		this.unitNameB = unitNameB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法定代表人职务(甲方)
	 */
	@Column(name ="UNIT_POSITION_A",nullable=true,length=60)
	public java.lang.String getUnitPositionA(){
		return this.unitPositionA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法定代表人职务(甲方)
	 */
	public void setUnitPositionA(java.lang.String unitPositionA){
		this.unitPositionA = unitPositionA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法定代表人职务(乙方)
	 */
	@Column(name ="UNIT_POSITION_B",nullable=true,length=60)
	public java.lang.String getUnitPositionB(){
		return this.unitPositionB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法定代表人职务(乙方)
	 */
	public void setUnitPositionB(java.lang.String unitPositionB){
		this.unitPositionB = unitPositionB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法定代表人姓名(甲方)
	 */
	@Column(name ="NAME_A",nullable=true,length=36)
	public java.lang.String getNameA(){
		return this.nameA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法定代表人姓名(甲方)
	 */
	public void setNameA(java.lang.String nameA){
		this.nameA = nameA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法定代表人姓名(乙方)
	 */
	@Column(name ="NAME_B",nullable=true,length=36)
	public java.lang.String getNameB(){
		return this.nameB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法定代表人姓名(乙方)
	 */
	public void setNameB(java.lang.String nameB){
		this.nameB = nameB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理单位名称(甲方)
	 */
	@Column(name ="AGENCY_UINT_NAME_A",nullable=true,length=60)
	public java.lang.String getAgencyUintNameA(){
		return this.agencyUintNameA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理单位名称(甲方)
	 */
	public void setAgencyUintNameA(java.lang.String agencyUintNameA){
		this.agencyUintNameA = agencyUintNameA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代表单位名称(乙方)
	 */
	@Column(name ="AGENCY_UNIT_NAME_B",nullable=true,length=60)
	public java.lang.String getAgencyUnitNameB(){
		return this.agencyUnitNameB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代表单位名称(乙方)
	 */
	public void setAgencyUnitNameB(java.lang.String agencyUnitNameB){
		this.agencyUnitNameB = agencyUnitNameB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理人职务(甲方)
	 */
	@Column(name ="AGENCY_UNIT_POSITION_A",nullable=true,length=60)
	public java.lang.String getAgencyUnitPositionA(){
		return this.agencyUnitPositionA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理人职务(甲方)
	 */
	public void setAgencyUnitPositionA(java.lang.String agencyUnitPositionA){
		this.agencyUnitPositionA = agencyUnitPositionA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理人职务(乙方)
	 */
	@Column(name ="AGENCY_UNIT_POSITION_B",nullable=true,length=60)
	public java.lang.String getAgencyUnitPositionB(){
		return this.agencyUnitPositionB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理人职务(乙方)
	 */
	public void setAgencyUnitPositionB(java.lang.String agencyUnitPositionB){
		this.agencyUnitPositionB = agencyUnitPositionB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理人姓名(甲方)
	 */
	@Column(name ="AGENCY_NAME_A",nullable=true,length=36)
	public java.lang.String getAgencyNameA(){
		return this.agencyNameA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理人姓名(甲方)
	 */
	public void setAgencyNameA(java.lang.String agencyNameA){
		this.agencyNameA = agencyNameA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理人姓名(乙方)
	 */
	@Column(name ="AGENCY_NAME_B",nullable=true,length=36)
	public java.lang.String getAgencyNameB(){
		return this.agencyNameB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理人姓名(乙方)
	 */
	public void setAgencyNameB(java.lang.String agencyNameB){
		this.agencyNameB = agencyNameB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位地址(甲方)
	 */
	@Column(name ="ADDRESS_A",nullable=true,length=200)
	public java.lang.String getAddressA(){
		return this.addressA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位地址(甲方)
	 */
	public void setAddressA(java.lang.String addressA){
		this.addressA = addressA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位地址(乙方)
	 */
	@Column(name ="ADDRESS_B",nullable=true,length=200)
	public java.lang.String getAddressB(){
		return this.addressB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位地址(乙方)
	 */
	public void setAddressB(java.lang.String addressB){
		this.addressB = addressB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮政编码(甲方)
	 */
	@Column(name ="POSTALCODE_A",nullable=true,length=6)
	public java.lang.String getPostalcodeA(){
		return this.postalcodeA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮政编码(甲方)
	 */
	public void setPostalcodeA(java.lang.String postalcodeA){
		this.postalcodeA = postalcodeA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮政编码(乙方)
	 */
	@Column(name ="POSTALCODE_B",nullable=true,length=6)
	public java.lang.String getPostalcodeB(){
		return this.postalcodeB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮政编码(乙方)
	 */
	public void setPostalcodeB(java.lang.String postalcodeB){
		this.postalcodeB = postalcodeB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话(甲方)
	 */
	@Column(name ="TEL_A",nullable=true,length=30)
	public java.lang.String getTelA(){
		return this.telA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话(甲方)
	 */
	public void setTelA(java.lang.String telA){
		this.telA = telA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话(乙方)
	 */
	@Column(name ="TEL_B",nullable=true,length=30)
	public java.lang.String getTelB(){
		return this.telB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话(乙方)
	 */
	public void setTelB(java.lang.String telB){
		this.telB = telB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账户名称(甲方)
	 */
	@Column(name ="ACCOUNT_NAME_A",nullable=true,length=100)
	public java.lang.String getAccountNameA(){
		return this.accountNameA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账户名称(甲方)
	 */
	public void setAccountNameA(java.lang.String accountNameA){
		this.accountNameA = accountNameA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  帐户名称(乙方)
	 */
	@Column(name ="ACCOUNT_NAME_B",nullable=true,length=100)
	public java.lang.String getAccountNameB(){
		return this.accountNameB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帐户名称(乙方)
	 */
	public void setAccountNameB(java.lang.String accountNameB){
		this.accountNameB = accountNameB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开户银行(甲方)
	 */
	@Column(name ="BANK_A",nullable=true,length=100)
	public java.lang.String getBankA(){
		return this.bankA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开户银行(甲方)
	 */
	public void setBankA(java.lang.String bankA){
		this.bankA = bankA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开户银行(乙方)
	 */
	@Column(name ="BANK_B",nullable=true,length=100)
	public java.lang.String getBankB(){
		return this.bankB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开户银行(乙方)
	 */
	public void setBankB(java.lang.String bankB){
		this.bankB = bankB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  帐号(甲方)
	 */
	@Column(name ="ACCOUNT_ID_A",nullable=true,length=30)
	public java.lang.String getAccountIdA(){
		return this.accountIdA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帐号(甲方)
	 */
	public void setAccountIdA(java.lang.String accountIdA){
		this.accountIdA = accountIdA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  帐号(乙方)
	 */
	@Column(name ="ACCOUNT_ID_B",nullable=true,length=30)
	public java.lang.String getAccountIdB(){
		return this.accountIdB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  帐号(乙方)
	 */
	public void setAccountIdB(java.lang.String accountIdB){
		this.accountIdB = accountIdB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签字地点(甲方)
	 */
	@Column(name ="SIGN_ADDRESS_A",nullable=true,length=200)
	public java.lang.String getSignAddressA(){
		return this.signAddressA;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签字地点(甲方)
	 */
	public void setSignAddressA(java.lang.String signAddressA){
		this.signAddressA = signAddressA;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签字地点(乙方)
	 */
	@Column(name ="SIGN_ADDRESS_B",nullable=true,length=200)
	public java.lang.String getSignAddressB(){
		return this.signAddressB;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签字地点(乙方)
	 */
	public void setSignAddressB(java.lang.String signAddressB){
		this.signAddressB = signAddressB;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  第三方
	 */
	@Column(name ="THE_THIRD",nullable=true,length=60)
	public java.lang.String getTheThird(){
		return this.theThird;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  第三方
	 */
	public void setTheThird(java.lang.String theThird){
		this.theThird = theThird;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  甲方合同履行监督单位
	 */
	@Column(name ="MONITOR_UNIT",nullable=true,length=60)
	public java.lang.String getMonitorUnit(){
		return this.monitorUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  甲方合同履行监督单位
	 */
	public void setMonitorUnit(java.lang.String monitorUnit){
		this.monitorUnit = monitorUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  总军事代表
	 */
	@Column(name ="MILITARY_DEPUTY",nullable=true,length=36)
	public java.lang.String getMilitaryDeputy(){
		return this.militaryDeputy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  总军事代表
	 */
	public void setMilitaryDeputy(java.lang.String militaryDeputy){
		this.militaryDeputy = militaryDeputy;
	}
}
