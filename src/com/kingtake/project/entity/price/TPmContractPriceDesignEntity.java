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
 * @Description: 合同价款计价书：设计类计算表
 * @author onlineGenerator
 * @date 2015-08-19 15:43:51
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_price_design", schema = "")
@LogEntity("合同价款计价书：设计类计算表")
@SuppressWarnings("serial")
public class TPmContractPriceDesignEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("报价人姓名")
	private java.lang.String id;
	/**合同价_主键*/
	@Excel(name="合同价_主键")
	@FieldDesc("合同价_主键")
	private java.lang.String tpId;
	/**序号*/
	@Excel(name="序号")
	@FieldDesc("序号")
	private String serialNum;
	/**类别id（从预算类别表中取）*/
	@Excel(name="类别id（从预算类别表中取）")
	@FieldDesc("类别id（从预算类别表中取）")
	private java.lang.String typeId;
	/**类别名称（对应表格中的‘项目'表）*/
	@Excel(name="类别名称（对应表格中的‘项目'表）")
	@FieldDesc("类别名称（对应表格中的‘项目'表）")
	private java.lang.String typeName;
	/**报价说明*/
	@Excel(name="报价说明")
	@FieldDesc("报价说明")
	private java.lang.String priceExplain;
	/**报价栏-计量单位*/
	@Excel(name="报价栏-计量单位")
	@FieldDesc("报价栏-计量单位")
	private java.lang.String priceUnit;
	/**报价栏-计量数量*/
	@Excel(name="报价栏-计量数量")
	@FieldDesc("报价栏-计量数量")
	private Double priceNumber;
	/**报价栏-计划成本*/
	@Excel(name="报价栏-计划成本")
	@FieldDesc("报价栏-计划成本")
	private Double priceCost;
	/**报价栏-金额*/
	@Excel(name="报价栏-金额")
	@FieldDesc("报价栏-金额")
	private Double priceAmount;
	/**审核栏-计量单位*/
	@Excel(name="审核栏-计量单位")
	@FieldDesc("审核栏-计量单位")
	private String auditUnit;
	/**审核栏-计量数量*/
	@Excel(name="审核栏-计量数量")
	@FieldDesc("审核栏-计量数量")
	private Double auditNumber;
	/**审核栏-计划成本*/
	@Excel(name="审核栏-计划成本")
	@FieldDesc("审核栏-计划成本")
	private Double auditCost;
	/**审核栏-金额*/
	@Excel(name="审核栏-金额")
	@FieldDesc("审核栏-金额")
	private Double auditAmount;
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
	
	
	@Column(name ="SERIAL_NUM", length=10)
	public String getSerialNum(){
		return this.serialNum;
	}

	public void setSerialNum(String serialNum){
		this.serialNum = serialNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类别id（从预算类别表中取）
	 */
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报价说明
	 */
	@Column(name ="PRICE_EXPLAIN",nullable=true,length=200)
	public java.lang.String getPriceExplain(){
		return this.priceExplain;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报价说明
	 */
	public void setPriceExplain(java.lang.String priceExplain){
		this.priceExplain = priceExplain;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报价栏-计量单位
	 */
	@Column(name ="PRICE_UNIT",nullable=true,length=20)
	public java.lang.String getPriceUnit(){
		return this.priceUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报价栏-计量单位
	 */
	public void setPriceUnit(java.lang.String priceUnit){
		this.priceUnit = priceUnit;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-计量数量
	 */
	@Column(name ="PRICE_NUMBER",nullable=true,scale=2,length=6)
	public Double getPriceNumber(){
		return this.priceNumber;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-计量数量
	 */
	public void setPriceNumber(Double priceNumber){
		this.priceNumber = priceNumber;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-计划成本
	 */
	@Column(name ="PRICE_COST",nullable=true,scale=2,length=10)
	public Double getPriceCost(){
		return this.priceCost;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-计划成本
	 */
	public void setPriceCost(Double priceCost){
		this.priceCost = priceCost;
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
	
	@Column(name ="AUDIT_UNIT",nullable=true,length=20)
	public String getAuditUnit(){
		return this.auditUnit;
	}

	public void setAuditUnit(String auditUnit){
		this.auditUnit = auditUnit;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审核栏-计量数量
	 */
	@Column(name ="AUDIT_NUMBER",nullable=true,scale=2,length=10)
	public Double getAuditNumber(){
		return this.auditNumber;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审核栏-计量数量
	 */
	public void setAuditNumber(Double auditNumber){
		this.auditNumber = auditNumber;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审核栏-计划成本
	 */
	@Column(name ="AUDIT_COST",nullable=true,scale=2,length=10)
	public Double getAuditCost(){
		return this.auditCost;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审核栏-计划成本
	 */
	public void setAuditCost(Double auditCost){
		this.auditCost = auditCost;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审核栏-金额
	 */
	@Column(name ="AUDIT_AMOUNT",nullable=true,scale=2,length=10)
	public Double getAuditAmount(){
		return this.auditAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审核栏-金额
	 */
	public void setAuditAmount(Double auditAmount){
		this.auditAmount = auditAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="MEMO",nullable=true,length=3000)
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
