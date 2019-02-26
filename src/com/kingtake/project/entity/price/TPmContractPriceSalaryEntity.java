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
 * @Description: 合同价款计价书--工资类计算书
 * @author onlineGenerator
 * @date 2015-08-10 15:57:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_price_salary", schema = "")
@LogEntity("合同价款计价书--工资类计算书")
@SuppressWarnings("serial")
public class TPmContractPriceSalaryEntity implements java.io.Serializable {
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
	/**单位/部门*/
	@Excel(name="单位/部门")
	@FieldDesc("单位/部门")
	private java.lang.String unitDepart;
	/**工作内容*/
	@Excel(name="工作内容")
	@FieldDesc("工作内容")
	private java.lang.String workContent;
	/**报价栏-预计人数*/
	@Excel(name="报价栏-预计人数")
	@FieldDesc("报价栏-预计人数")
	private java.lang.Integer pricePlanPeople;
	/**报价栏-计划天数*/
	@Excel(name="报价栏-计划天数")
	@FieldDesc("报价栏-计划天数")
	private Double pricePlanDays;
	/**报价栏-元/人天*/
	@Excel(name="报价栏-元/人天")
	@FieldDesc("报价栏-元/人天")
	private Double pricePlanCost;
	/**报价栏-金额*/
	@Excel(name="报价栏-金额")
	@FieldDesc("报价栏-金额")
	private Double pricePlanAmount;
	/**审价栏-预计人数*/
	@Excel(name="审价栏-预计人数")
	@FieldDesc("审价栏-预计人数")
	private java.lang.Integer auditPlanPeople;
	/**审价栏-计划天数*/
	@Excel(name="审价栏-计划天数")
	@FieldDesc("审价栏-计划天数")
	private Double auditPlanDays;
	/**审价栏-元/人天*/
	@Excel(name="审价栏-元/人天")
	@FieldDesc("审价栏-元/人天")
	private Double auditPlanCost;
	/**审价栏-金额*/
	@Excel(name="审价栏-金额")
	@FieldDesc("审价栏-金额")
	private Double auditPlanAmount;
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
	
	
	@Column(name = "SERIAL_NUM", nullable = true, length = 10)
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位/部门
	 */
	@Column(name ="UNIT_DEPART",nullable=true,length=60)
	public java.lang.String getUnitDepart(){
		return this.unitDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位/部门
	 */
	public void setUnitDepart(java.lang.String unitDepart){
		this.unitDepart = unitDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作内容
	 */
	@Column(name ="WORK_CONTENT",nullable=true,length=300)
	public java.lang.String getWorkContent(){
		return this.workContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作内容
	 */
	public void setWorkContent(java.lang.String workContent){
		this.workContent = workContent;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  报价栏-预计人数
	 */
	@Column(name ="PRICE_PLAN_PEOPLE",nullable=true,length=5)
	public java.lang.Integer getPricePlanPeople(){
		return this.pricePlanPeople;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  报价栏-预计人数
	 */
	public void setPricePlanPeople(java.lang.Integer pricePlanPeople){
		this.pricePlanPeople = pricePlanPeople;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-计划天数
	 */
	@Column(name ="PRICE_PLAN_DAYS",nullable=true,scale=1,length=6)
	public Double getPricePlanDays(){
		return this.pricePlanDays;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-计划天数
	 */
	public void setPricePlanDays(Double pricePlanDays){
		this.pricePlanDays = pricePlanDays;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-元/人天
	 */
	@Column(name ="PRICE_PLAN_COST",nullable=true,scale=2,length=10)
	public Double getPricePlanCost(){
		return this.pricePlanCost;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-元/人天
	 */
	public void setPricePlanCost(Double pricePlanCost){
		this.pricePlanCost = pricePlanCost;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏-金额
	 */
	@Column(name ="PRICE_PLAN_AMOUNT",nullable=true,scale=2,length=10)
	public Double getPricePlanAmount(){
		return this.pricePlanAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏-金额
	 */
	public void setPricePlanAmount(Double pricePlanAmount){
		this.pricePlanAmount = pricePlanAmount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审价栏-预计人数
	 */
	@Column(name ="AUDIT_PLAN_PEOPLE",nullable=true,length=5)
	public java.lang.Integer getAuditPlanPeople(){
		return this.auditPlanPeople;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审价栏-预计人数
	 */
	public void setAuditPlanPeople(java.lang.Integer auditPlanPeople){
		this.auditPlanPeople = auditPlanPeople;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏-计划天数
	 */
	@Column(name ="AUDIT_PLAN_DAYS",nullable=true,scale=1,length=6)
	public Double getAuditPlanDays(){
		return this.auditPlanDays;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏-计划天数
	 */
	public void setAuditPlanDays(Double auditPlanDays){
		this.auditPlanDays = auditPlanDays;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏-元/人天
	 */
	@Column(name ="AUDIT_PLAN_COST",nullable=true,scale=2,length=10)
	public Double getAuditPlanCost(){
		return this.auditPlanCost;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏-元/人天
	 */
	public void setAuditPlanCost(Double auditPlanCost){
		this.auditPlanCost = auditPlanCost;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏-金额
	 */
	@Column(name ="AUDIT_PLAN_AMOUNT",nullable=true,scale=2,length=10)
	public Double getAuditPlanAmount(){
		return this.auditPlanAmount;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏-金额
	 */
	public void setAuditPlanAmount(Double auditPlanAmount){
		this.auditPlanAmount = auditPlanAmount;
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
