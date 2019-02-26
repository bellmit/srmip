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
 * @Description: 合同价款计价书--材料类计算表
 * @author onlineGenerator
 * @date 2015-08-10 15:57:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_price_material", schema = "")
@LogEntity("合同价款计价书--材料类别计算表")
@SuppressWarnings("serial")
public class TPmContractPriceMaterialEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**合同价_主键*/
	@Excel(name="合同价_主键")
	@FieldDesc("合同价_主键")
	private java.lang.String tpId;
	/**序号*/
	@Excel(name="序号")
	@FieldDesc("序号")
	private String serialNum;
	/**类别id*/
	@Excel(name="类别id")
	@FieldDesc("类别id")
	private java.lang.String typeId;
	/**类别名称*/
	@Excel(name="类别名称")
	@FieldDesc("类别名称")
	private java.lang.String typeName;
	/**名称*/
	@Excel(name="名称")
	@FieldDesc("名称")
	private java.lang.String priceName;
	/**规格型号*/
	@Excel(name="规格型号")
	@FieldDesc("规格型号")
	private java.lang.String prodModel;
	/**计量单位*/
	@Excel(name="计量单位")
	@FieldDesc("计量单位")
	private java.lang.String calculateUnit;
	/**供货单位*/
	@Excel(name="供货单位")
	@FieldDesc("供货单位")
	private java.lang.String supplyUnit;
	/**报价栏(计划数量)*/
	@Excel(name="报价栏(计划数量)")
	@FieldDesc("报价栏(计划数量)")
	private java.lang.Integer pricePlanNum;
	/**报价栏(计划单价)*/
	@Excel(name="报价栏(计划单价)")
	@FieldDesc("报价栏(计划单价)")
	private Double pricePlanUnitprice;
	/**报价栏(金额)*/
	@Excel(name="报价栏(金额)")
	@FieldDesc("报价栏(金额)")
	private Double pricePlanAmount;
	/**审价栏(计划数量)*/
	@Excel(name="审价栏(计划数量)")
	@FieldDesc("审价栏(计划数量)")
	private java.lang.Integer priceAuditNum;
	/**审价栏(计划单价)*/
	@Excel(name="审价栏(计划单价)")
	@FieldDesc("审价栏(计划单价)")
	private Double priceAuditUnitprice;
	/**审价栏(金额)*/
	@Excel(name="审价栏(金额)")
	@FieldDesc("审价栏(金额)")
	private Double priceAuditAmount;
	/**备注*/
	@Excel(name="备注")
	@FieldDesc("备注")
	private java.lang.String memo;
	/**是否可添加子项*/
	@Excel(name="是否可添加子项")
	@FieldDesc("是否可添加子项")
	private java.lang.String addChildFlag;
	/**所属父类别*/
	@Excel(name="所属父类别")
	@FieldDesc("所属父类别")
	private java.lang.String parentTypeid;
	
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
	 *@return: java.lang.String  序号
	 */
	@Column(name = "SERIAL_NUM", nullable = true, length = 10)
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类别id
	 */
	@Column(name ="TYPE_ID",nullable=true,length=32)
	public java.lang.String getTypeId(){
		return this.typeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类别id
	 */
	public void setTypeId(java.lang.String typeId){
		this.typeId = typeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类别名称
	 */
	@Column(name ="TYPE_NAME",nullable=true,length=30)
	public java.lang.String getTypeName(){
		return this.typeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类别名称
	 */
	public void setTypeName(java.lang.String typeName){
		this.typeName = typeName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="PRICE_NAME",nullable=true,length=40)
	public java.lang.String getPriceName(){
		return this.priceName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setPriceName(java.lang.String priceName){
		this.priceName = priceName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格型号
	 */
	@Column(name ="PROD_MODEL",nullable=true,length=30)
	public java.lang.String getProdModel(){
		return this.prodModel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格型号
	 */
	public void setProdModel(java.lang.String prodModel){
		this.prodModel = prodModel;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供货单位
	 */
	@Column(name ="SUPPLY_UNIT",nullable=true,length=60)
	public java.lang.String getSupplyUnit(){
		return this.supplyUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供货单位
	 */
	public void setSupplyUnit(java.lang.String supplyUnit){
		this.supplyUnit = supplyUnit;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  报价栏(计划数量)
	 */
	@Column(name ="PRICE_PLAN_NUM",nullable=true,length=6)
	public java.lang.Integer getPricePlanNum(){
		return this.pricePlanNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  报价栏(计划数量)
	 */
	public void setPricePlanNum(java.lang.Integer pricePlanNum){
		this.pricePlanNum = pricePlanNum;
	}
	/**
	 *方法: 取得java.math.DoubleDecimal
	 *@return: Double  报价栏(计划单价)
	 */
	@Column(name ="PRICE_PLAN_UNITPRICE",nullable=true,scale=2,length=10)
	public Double getPricePlanUnitprice(){
		return this.pricePlanUnitprice;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏(计划单价)
	 */
	public void setPricePlanUnitprice(Double pricePlanUnitprice){
		this.pricePlanUnitprice = pricePlanUnitprice;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏(金额)
	 */
	@Column(name ="PRICE_PLAN_AMOUNT",nullable=true,scale=2,length=10)
	public Double getPricePlanAmount(){
		return this.pricePlanAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏(金额)
	 */
	public void setPricePlanAmount(Double pricePlanAmount){
		this.pricePlanAmount = pricePlanAmount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审价栏(计划数量)
	 */
	@Column(name ="PRICE_AUDIT_NUM",nullable=true,length=6)
	public java.lang.Integer getPriceAuditNum(){
		return this.priceAuditNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审价栏(计划数量)
	 */
	public void setPriceAuditNum(java.lang.Integer priceAuditNum){
		this.priceAuditNum = priceAuditNum;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏(计划单价)
	 */
	@Column(name ="PRICE_AUDIT_UNITPRICE",nullable=true,scale=2,length=10)
	public Double getPriceAuditUnitprice(){
		return this.priceAuditUnitprice;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏(计划单价)
	 */
	public void setPriceAuditUnitprice(Double priceAuditUnitprice){
		this.priceAuditUnitprice = priceAuditUnitprice;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏(金额)
	 */
	@Column(name ="PRICE_AUDIT_AMOUNT",nullable=true,scale=2,length=10)
	public Double getPriceAuditAmount(){
		return this.priceAuditAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏(金额)
	 */
	public void setPriceAuditAmount(Double priceAuditAmount){
		this.priceAuditAmount = priceAuditAmount;
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
}
