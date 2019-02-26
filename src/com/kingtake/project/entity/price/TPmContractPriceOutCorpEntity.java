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
 * @Description: 合同价款计价书--外协类计算书
 * @author onlineGenerator
 * @date 2015-08-20 11:11:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_price_out_corp", schema = "")
@LogEntity("合同价款计价书--外协类计算书")
@SuppressWarnings("serial")
public class TPmContractPriceOutCorpEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**合同价_主键*/
	@Excel(name="合同价_主键")
	@FieldDesc("合同价_主键")
	private java.lang.String tpId;
	/**外协件名称*/
	@Excel(name="外协件名称")
	@FieldDesc("外协件名称")
	private java.lang.String outsideName;
	/**外协性质*/
	@Excel(name="外协性质")
	@FieldDesc("外协性质")
	private java.lang.String outsideQuality;
	/**外协加工单位*/
	@Excel(name="外协加工单位")
	@FieldDesc("外协加工单位")
	private java.lang.String outsideProcessUnit;
	/**报价栏-计划数量*/
	@Excel(name="报价栏-计划数量")
	@FieldDesc("报价栏-计划数量")
	private Double priceNumber;
	/**报价栏-计划单价*/
	@Excel(name="报价栏-计划单价")
	@FieldDesc("报价栏-计划单价")
	private Double priceUnitPrice;
	/**报价栏-金额*/
	@Excel(name="报价栏-金额")
	@FieldDesc("报价栏-金额")
	private Double priceAmount;
	/**审价栏-计划数量*/
	@Excel(name="审价栏-计划数量")
	@FieldDesc("审价栏-计划数量")
	private Double auditNumber;
	/**审价栏-计划单价*/
	@Excel(name="审价栏-计划单价")
	@FieldDesc("审价栏-计划单价")
	private Double auditUnitPrice;
	/**审价栏-金额*/
	@Excel(name="审价栏-金额")
	@FieldDesc("审价栏-金额")
	private Double auditAmount;
	/**备注*/
	@Excel(name="备注")
	@FieldDesc("备注")
	private java.lang.String memo;
	
	/**类别id（从预算类别表中取）*/
	@Excel(name="类别id（从预算类别表中取）")
	@FieldDesc("类别id（从预算类别表中取）")
	private java.lang.String typeId;
	/**类别名称（对应表格中的‘项目'表）*/
	@Excel(name="类别名称（对应表格中的‘项目'表）")
	@FieldDesc("类别名称（对应表格中的‘项目'表）")
	private java.lang.String typeName;
	/**是否可添加子项*/
	@Excel(name="是否可添加子项")
	@FieldDesc("是否可添加子项")
	private java.lang.String addChildFlag;
	/**所属父类别*/
	@Excel(name="所属父类别")
	@FieldDesc("所属父类别")
	private java.lang.String parentTypeid;
	/**序号*/
	@Excel(name="序号")
	@FieldDesc("序号")
	private String serialNum;
	
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
	 *@return: java.lang.String  合同价_主键
	 */
	@Column(name ="T_P_ID",nullable=true,length=32)
	public java.lang.String getTpId() {
		return tpId;
	}

	public void setTpId(java.lang.String tpId) {
		this.tpId = tpId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外协件名称
	 */
	@Column(name ="OUTSIDE_NAME",nullable=true,length=200)
	public java.lang.String getOutsideName(){
		return this.outsideName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外协件名称
	 */
	public void setOutsideName(java.lang.String outsideName){
		this.outsideName = outsideName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外协性质
	 */
	@Column(name ="OUTSIDE_QUALITY",nullable=true,length=100)
	public java.lang.String getOutsideQuality(){
		return this.outsideQuality;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外协性质
	 */
	public void setOutsideQuality(java.lang.String outsideQuality){
		this.outsideQuality = outsideQuality;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外协加工单位
	 */
	@Column(name ="OUTSIDE_PROCESS_UNIT",nullable=true,length=140)
	public java.lang.String getOutsideProcessUnit(){
		return this.outsideProcessUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外协加工单位
	 */
	public void setOutsideProcessUnit(java.lang.String outsideProcessUnit){
		this.outsideProcessUnit = outsideProcessUnit;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-计划数量
	 */
	@Column(name ="PRICE_NUMBER",nullable=true,scale=2,length=10)
	public Double getPriceNumber(){
		return this.priceNumber;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-计划数量
	 */
	public void setPriceNumber(Double priceNumber){
		this.priceNumber = priceNumber;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-计划单价
	 */
	@Column(name ="PRICE_UNIT_PRICE",nullable=true,scale=2,length=10)
	public Double getPriceUnitPrice(){
		return this.priceUnitPrice;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-计划单价
	 */
	public void setPriceUnitPrice(Double priceUnitPrice){
		this.priceUnitPrice = priceUnitPrice;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-金额
	 */
	@Column(name ="PRICE_AMOUNT",nullable=true,scale=2,length=10)
	public Double getPriceAmount(){
		return this.priceAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-金额
	 */
	public void setPriceAmount(Double priceAmount){
		this.priceAmount = priceAmount;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏-计划数量
	 */
	@Column(name ="AUDIT_NUMBER",nullable=true,scale=2,length=10)
	public Double getAuditNumber(){
		return this.auditNumber;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏-计划数量
	 */
	public void setAuditNumber(Double auditNumber){
		this.auditNumber = auditNumber;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏-计划单价
	 */
	@Column(name ="AUDIT_UNIT_PRICE",nullable=true,scale=2,length=10)
	public Double getAuditUnitPrice(){
		return this.auditUnitPrice;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏-计划单价
	 */
	public void setAuditUnitPrice(Double auditUnitPrice){
		this.auditUnitPrice = auditUnitPrice;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏-金额
	 */
	@Column(name ="AUDIT_AMOUNT",nullable=true,scale=2,length=10)
	public Double getAuditAmount(){
		return this.auditAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏-金额
	 */
	public void setAuditAmount(Double auditAmount){
		this.auditAmount = auditAmount;
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
	 *@return: java.lang.String  是否可添加子项
	 */
	@Column(name ="ADD_CHILD_FLAG",nullable=true,length=1)
	public java.lang.String getAddChildFlag(){
		return this.addChildFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否可添加子项
	 */
	public void setAddChildFlag(java.lang.String addChildFlag){
		this.addChildFlag = addChildFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属父类别
	 */
	@Column(name ="PARENT_TYPEID",nullable=true,length=32)
	public java.lang.String getParentTypeid(){
		return this.parentTypeid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属父类别
	 */
	public void setParentTypeid(java.lang.String parentTypeid){
		this.parentTypeid = parentTypeid;
	}
	
	
	@Column(name ="TYPE_ID",nullable=true,length=32)
	public java.lang.String getTypeId(){
		return this.typeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类别id（从预算类别表中取）
	 */
	public void setTypeId(java.lang.String typeId){
		this.typeId = typeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类别名称（对应表格中的‘项目'表）
	 */
	@Column(name ="TYPE_NAME",nullable=true,length=30)
	public java.lang.String getTypeName(){
		return this.typeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类别名称（对应表格中的‘项目'表）
	 */
	public void setTypeName(java.lang.String typeName){
		this.typeName = typeName;
	}
	
	@Column(name = "SERIAL_NUM", nullable = true, length = 10)
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
}
