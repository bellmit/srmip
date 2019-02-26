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
 * @Description: 合同类项目经费预算主表/计划类项目经费预算主表二
 * @author onlineGenerator
 * @date 2015-07-26 15:49:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_funds", schema = "")
@SuppressWarnings("serial")
@LogEntity("合同类项目经费预算主表")
public class TPmContractFundsEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**项目_主键*/
	@FieldDesc("项目ID")
	@Excel(name="项目ID")
	private java.lang.String tpId;
	/**预算主表主键*/
	@FieldDesc("预算主表主键")
	@Excel(name="预算主表主键")
	private java.lang.String tApprId;
	/**序号*/
	@FieldDesc("序号")
	@Excel(name="序号")
    private java.lang.String num;
	/**项目、内容id*/
	@FieldDesc("项目、内容id")
	@Excel(name="项目、内容id")
	private java.lang.String contentId;
	/**项目、内容*/
	@FieldDesc("项目、内容")
	@Excel(name="项目、内容")
	private java.lang.String content;
	/**金额(元)*/
	@FieldDesc("金额(元)")
	@Excel(name="金额(元)")
	private Double money;
	@FieldDesc("可调整金额(元)")
	@Excel(name="可调整金额(元)")
	private Double variableMoney;
	@FieldDesc("历次金额(元)")
	@Excel(name="历次金额(元)")
	private Double historyMoney;
	/**备注*/
	@FieldDesc("备注")
	@Excel(name="备注")
	private java.lang.String remark;
	/**父节点id*/
	@FieldDesc("父节点id")
	@Excel(name="父节点id")
	private String parent;
	/**预算项目类型*/
	@FieldDesc("预算项目类型")
	@Excel(name="预算项目类型")
	private String addChildFlag;
	
	/**计量单位*/
	@FieldDesc("计量单位")
	@Excel(name="计量单位")
	private String unit;
	
	/**单价*/
	@FieldDesc("单价")
	@Excel(name="单价")
	private Double price;
	
	/**数量*/
	@Excel(name="数量")
	@FieldDesc("数量")
	private java.lang.Integer amount;
	
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
	 * 项目ID
	 * @return
	 */
	@Column(name ="T_P_ID",nullable=true,length=32)
	public java.lang.String getTpId() {
		return tpId;
	}

	public void setTpId(java.lang.String tpId) {
		this.tpId = tpId;
	}
	/**
	 * 预算主表主键
	 * @return
	 */
	@Column(name ="T_APPR_ID",nullable=true,length=32)
	public java.lang.String gettApprId() {
		return tApprId;
	}

	public void settApprId(java.lang.String tApprId) {
		this.tApprId = tApprId;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目、内容id
	 */
	@Column(name ="CONTENT_ID",nullable=true,length=32)
	public java.lang.String getContentId(){
		return this.contentId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目、内容id
	 */
	public void setContentId(java.lang.String contentId){
		this.contentId = contentId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目、内容
	 */
	@Column(name ="CONTENT",nullable=true,length=2000)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目、内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额(元)
	 */
	@Column(name ="MONEY",nullable=true,scale=2,length=13)
	public Double getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额(元)
	 */
	public void setMoney(Double money){
		this.money = money;
	}
	
	@Column(name ="VARIABLE_MONEY",nullable=true,scale=2,length=13)
	public Double getVariableMoney() {
		return variableMoney;
	}

	public void setVariableMoney(Double variableMoney) {
		this.variableMoney = variableMoney;
	}
	
	@Column(name ="HISTORY_MONEY",nullable=true,scale=2,length=13)
	public Double getHistoryMoney() {
		return historyMoney;
	}

	public void setHistoryMoney(Double historyMoney) {
		this.historyMoney = historyMoney;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
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
	
	@Column(name ="unit",nullable=true,length=32)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Column(name ="price",nullable=true,scale=2,length=13)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
	
}
