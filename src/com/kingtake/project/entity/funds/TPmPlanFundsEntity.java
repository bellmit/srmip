package com.kingtake.project.entity.funds;

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
 * @Description: 计划类项目经费预算主表
 * @author onlineGenerator
 * @date 2015-08-04 16:29:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_plan_funds", schema = "")
@SuppressWarnings("serial")
@LogEntity("计划类项目经费预算主表")
public class TPmPlanFundsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**项目_主键*/
	@Excel(name="预算审批表主键")
	private java.lang.String apprId;
	/**序号*/
	@Excel(name="序号")
    private java.lang.String num;
	/**设备名称ID*/
    @Excel(name="设备名称ID")
    @FieldDesc("设备名称ID")
    private java.lang.String contentId;
	/**设备名称*/
	@Excel(name="设备名称")
	@FieldDesc("设备名称")
	private java.lang.String content;
	/**型号*/
	@Excel(name="型号")
	@FieldDesc("型号")
	private java.lang.String model;
	/**单位*/
	@Excel(name="单位")
	@FieldDesc("单位")
	private java.lang.String unit;
	/**数量*/
	@Excel(name="数量")
	@FieldDesc("数量")
	private java.lang.Integer amount;
	/**单价*/
	@Excel(name="单价")
	@FieldDesc("单价")
	private Double price;
	/**金额*/
	@Excel(name="金额")
	@FieldDesc("金额")
	private Double money;
	/**可变金额*/
	@Excel(name="可变金额")
	@FieldDesc("可变金额")
	private Double variableMoney;
	/**金额*/
	@Excel(name="历史金额")
	@FieldDesc("历史金额")
	private Double historyMoney;
	/**父节点id*/
    @FieldDesc("父节点id")
    @Excel(name="父节点id")
    private String parent;
	/**预算项目类型*/
    @FieldDesc("预算项目类型")
    @Excel(name="预算项目类型")
    private String addChildFlag;
	/**备注*/
    @FieldDesc("备注")
    @Excel(name="备注")
    private String remark;
    
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
	 *@return: java.lang.String  项目_主键
	 */
	@Column(name ="T_P_ID",nullable=true,length=32)
	public java.lang.String getApprId() {
		return apprId;
	}

	public void setApprId(java.lang.String apprId) {
		this.apprId = apprId;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  序号
	 */
    @Column(name = "NUM", nullable = true, length = 10)
    public java.lang.String getNum() {
		return this.num;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  序号
	 */
    public void setNum(java.lang.String num) {
		this.num = num;
	}
	
	@Column(name ="EQUIPMENT_ID",nullable=true,length=32)
	public java.lang.String getContentId() {
		return contentId;
	}

	public void setContentId(java.lang.String contentId) {
		this.contentId = contentId;
	}

	@Column(name ="EQUIPMENT_NAME",nullable=true,length=50)
	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  型号
	 */
	@Column(name ="MODEL",nullable=true,length=100)
	public java.lang.String getModel(){
		return this.model;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  型号
	 */
	public void setModel(java.lang.String model){
		this.model = model;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNIT",nullable=true,length=32)
	public java.lang.String getUnit(){
		return this.unit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnit(java.lang.String unit){
		this.unit = unit;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数量
	 */
	@Column(name ="AMOUNT",nullable=true,length=3)
	public java.lang.Integer getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setAmount(java.lang.Integer amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单价
	 */
	@Column(name ="PRICE",nullable=true,scale=2,length=10)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name ="MONEY",nullable=true,scale=2,length=13)
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name ="VARIABLE_MONEY",nullable=true,scale=2,length=13)
	public Double getVariableMoney() {
		return variableMoney;
	}

	public void setVariableMoney(Double variableMoney) {
		this.variableMoney = variableMoney;
	}
	
	@Column(name ="PARENT",nullable=true,length=32)
    public String getParent() {
        return parent;
    }

	public void setParent(String parent) {
        this.parent = parent;
    }
    
	@Column(name ="ADD_CHILD_FLAG",nullable=true,length=10)
    public String getAddChildFlag() {
        return addChildFlag;
    }

    public void setAddChildFlag(String addChildFlag) {
        this.addChildFlag = addChildFlag;
    }

    @Column(name ="REMARK",nullable=true,length=500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name ="HISTORY_MONEY",nullable=true,scale=2,length=13)
	public Double getHistoryMoney() {
		return historyMoney;
	}

	public void setHistoryMoney(Double historyMoney) {
		this.historyMoney = historyMoney;
	}

}
