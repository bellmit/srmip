package com.kingtake.project.entity.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 采购类合同价款计算书
 * @author onlineGenerator
 * @date 2015-09-16 10:45:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_price_purchase", schema = "")
@SuppressWarnings("serial")
public class TPmContractPricePurchaseEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**合同价款计算书封面_主键*/
	@Excel(name="合同价款计算书封面_主键")
	@FieldDesc("合同价款计算书封面_主键")
	private java.lang.String tpId;
	/**供方id*/
	@Excel(name="供方id")
	@FieldDesc("供方id")
	private java.lang.String supplierId;
	/**供方名称*/
	@Excel(name="供方名称")
	@FieldDesc("供方名称")
	private java.lang.String supplierName;
	/**序号*/
	@Excel(name="序号")
	@FieldDesc("序号")
	private String serialNum;
	/**名称*/
	@Excel(name="种类(科研代码集中CGZL采购种类)")
	@FieldDesc("种类(科研代码集中CGZL采购种类)")
	private java.lang.String purchaseCategory;
	/**名称*/
	@Excel(name="名称")
	@FieldDesc("名称")
	private java.lang.String categoryName;
	/**品牌*/
	@Excel(name="品牌、规格型号")
	@FieldDesc("品牌、规格型号")
	private java.lang.String categoryBrandModel;
	/**生产厂家*/
	@Excel(name="生产厂家")
	@FieldDesc("生产厂家")
	private java.lang.String produceUnit;
	/**计量单位*/
	@Excel(name="计量单位")
	@FieldDesc("计量单位")
	private java.lang.String calculateUnit;
	/**数量*/
	@Excel(name="数量")
	@FieldDesc("数量")
	private java.lang.Integer produceNum;
	/**报价栏-单价*/
	@Excel(name="报价栏-单价")
	@FieldDesc("报价栏-单价")
	private Double priceUnitprice;
	/**报价栏-总价*/
	@Excel(name="报价栏-总价")
	@FieldDesc("报价栏-总价")
	private Double priceAmount;
	/**审核栏-单价*/
	@Excel(name="审核栏-单价")
	@FieldDesc("审核栏-单价")
	private Double auditUnitprice;
	/**审核栏-总价*/
	@Excel(name="审核栏-总价")
	@FieldDesc("审核栏-总价")
	private Double auditAmount;
	/**备注*/
	@Excel(name="备注")
	@FieldDesc("备注")
	private java.lang.String memo;
	
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
	 *@return: java.lang.String  供方id
	 */
	@Column(name ="SUPPLIER_ID",nullable=true,length=32)
	public java.lang.String getSupplierId(){
		return this.supplierId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供方id
	 */
	public void setSupplierId(java.lang.String supplierId){
		this.supplierId = supplierId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供方名称
	 */
	@Column(name ="SUPPLIER_NAME",nullable=true,length=60)
	public java.lang.String getSupplierName(){
		return this.supplierName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供方名称
	 */
	public void setSupplierName(java.lang.String supplierName){
		this.supplierName = supplierName;
	}
	
	@Column(name ="SERIAL_NUM",nullable=true,length=10)
	public String getSerialNum(){
		return this.serialNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  序号
	 */
	public void setSerialNum(String serialNum){
		this.serialNum = serialNum;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="CATEGORY_NAME",nullable=true,length=50)
	public java.lang.String getCategoryName(){
		return this.categoryName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setCategoryName(java.lang.String categoryName){
		this.categoryName = categoryName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌、规格型号
	 */
	@Column(name ="CATEGORY_BRAND_MODEL",nullable=true,length=100)
	public java.lang.String getCategoryBrandModel() {
		return categoryBrandModel;
	}

	public void setCategoryBrandModel(java.lang.String categoryBrandModel) {
		this.categoryBrandModel = categoryBrandModel;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  生产厂家
	 */
	@Column(name ="PRODUCE_UNIT",nullable=true,length=60)
	public java.lang.String getProduceUnit(){
		return this.produceUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产厂家
	 */
	public void setProduceUnit(java.lang.String produceUnit){
		this.produceUnit = produceUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计量单位
	 */
	@Column(name ="CALCULATE_UNIT",nullable=true,length=10)
	public java.lang.String getCalculateUnit(){
		return this.calculateUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计量单位
	 */
	public void setCalculateUnit(java.lang.String calculateUnit){
		this.calculateUnit = calculateUnit;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数量
	 */
	@Column(name ="PRODUCE_NUM",nullable=true,length=6)
	public java.lang.Integer getProduceNum(){
		return this.produceNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setProduceNum(java.lang.Integer produceNum){
		this.produceNum = produceNum;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-单价
	 */
	@Column(name ="PRICE_UNITPRICE",nullable=true,scale=2,length=10)
	public Double getPriceUnitprice(){
		return this.priceUnitprice;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-单价
	 */
	public void setPriceUnitprice(Double priceUnitprice){
		this.priceUnitprice = priceUnitprice;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-总价
	 */
	@Column(name ="PRICE_AMOUNT",nullable=true,scale=2,length=10)
	public Double getPriceAmount(){
		return this.priceAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-总价
	 */
	public void setPriceAmount(Double priceAmount){
		this.priceAmount = priceAmount;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审核栏-单价
	 */
	@Column(name ="AUDIT_UNITPRICE",nullable=true,scale=2,length=10)
	public Double getAuditUnitprice(){
		return this.auditUnitprice;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审核栏-单价
	 */
	public void setAuditUnitprice(Double auditUnitprice){
		this.auditUnitprice = auditUnitprice;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审核栏-总价
	 */
	@Column(name ="AUDIT_AMOUNT",nullable=true,scale=2,length=10)
	public Double getAuditAmount(){
		return this.auditAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审核栏-总价
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

	@Column(name ="PURCHASE_CATEGORY",nullable=true,length=2)
	public java.lang.String getPurchaseCategory() {
		return purchaseCategory;
	}

	public void setPurchaseCategory(java.lang.String purchaseCategory) {
		this.purchaseCategory = purchaseCategory;
	}
	
	
}
