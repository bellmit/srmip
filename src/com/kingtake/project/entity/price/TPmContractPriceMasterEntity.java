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
 * @Description: 合同价款计算书--主表
 * @author onlineGenerator
 * @date 2015-08-10 15:57:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_contract_price_master", schema = "")
@LogEntity("合同价款计价书：主表")
@SuppressWarnings("serial")
public class TPmContractPriceMasterEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**合同价_主键*/
	@Excel(name="合同价_主键")
	@FieldDesc("合同价_主键")
	private java.lang.String tpId;
	/**报价预算类别id*/
	@Excel(name="报价预算类别id")
	@FieldDesc("报价预算类别id")
	private java.lang.String priceBudgetId;
	/**报价预算类别名称*/
	@Excel(name="报价预算类别名称")
	@FieldDesc("报价预算类别名称")
	private java.lang.String priceBudgetName;
	/**报价栏*/
	@Excel(name="报价栏")
	@FieldDesc("报价栏")
	private Double priceColumn;
	/**审价栏*/
	@Excel(name="审价栏")
	@FieldDesc("审价栏")
	private Double auditColumn;
	/**计价栏*/
	@Excel(name="计价栏")
	@FieldDesc("计价栏")
	private Double valuationColumn;
	/**差价栏*/
	@Excel(name="差价栏")
	@FieldDesc("差价栏")
	private Double priceDiffColumn;
	
	@FieldDesc("排序码")
    @Excel(name = "排序码")
    private String sortCode;
	
	@FieldDesc("父节点ID")
    @Excel(name = "父节点ID")
	private String parent;
	
	@FieldDesc("是否为明细表，以及明细表的类型")
    @Excel(name = "是否为明细表，以及明细表的类型")
	private String showFlag;
	
	@FieldDesc("备注")
    @Excel(name = "备注")
	private String memo;
	
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
	 *@return: java.lang.String  报价预算类别id
	 */
	@Column(name ="PRICE_BUDGET_ID",nullable=true,length=32)
	public java.lang.String getPriceBudgetId(){
		return this.priceBudgetId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报价预算类别id
	 */
	public void setPriceBudgetId(java.lang.String priceBudgetId){
		this.priceBudgetId = priceBudgetId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报价预算类别名称
	 */
	@Column(name ="PRICE_BUDGET_NAME",nullable=true,length=30)
	public java.lang.String getPriceBudgetName(){
		return this.priceBudgetName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报价预算类别名称
	 */
	public void setPriceBudgetName(java.lang.String priceBudgetName){
		this.priceBudgetName = priceBudgetName;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  报价栏
	 */
	@Column(name ="PRICE_COLUMN",nullable=true,scale=2,length=10)
	public Double getPriceColumn(){
		return this.priceColumn;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  报价栏
	 */
	public void setPriceColumn(Double priceColumn){
		this.priceColumn = priceColumn;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  审价栏
	 */
	@Column(name ="AUDIT_COLUMN",nullable=true,scale=2,length=10)
	public Double getAuditColumn(){
		return this.auditColumn;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  审价栏
	 */
	public void setAuditColumn(Double auditColumn){
		this.auditColumn = auditColumn;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  计价栏
	 */
	@Column(name ="VALUATION_COLUMN",nullable=true,scale=2,length=10)
	public Double getValuationColumn(){
		return this.valuationColumn;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  计价栏
	 */
	public void setValuationColumn(Double valuationColumn){
		this.valuationColumn = valuationColumn;
	}
	/**
	 *方法: 取得Double
	 *@return: Double  差价栏
	 */
	@Column(name ="PRICE_DIFF_COLUMN",nullable=true,scale=2,length=10)
	public Double getPriceDiffColumn(){
		return this.priceDiffColumn;
	}

	/**
	 *方法: 设置Double
	 *@param: Double  差价栏
	 */
	public void setPriceDiffColumn(Double priceDiffColumn){
		this.priceDiffColumn = priceDiffColumn;
	}

    @Column(name = "SORT_CODE", nullable = true, length = 10)
    public String getSortCode() {
		return sortCode;
	}

    public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	@Column(name ="PARENT",nullable=true,length=32)
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Column(name ="SHOW_FLAG",nullable=true,length=10)
	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	@Column(name ="MEMO",nullable=true,length=200)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
