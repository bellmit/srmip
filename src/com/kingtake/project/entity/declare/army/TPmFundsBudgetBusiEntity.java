package com.kingtake.project.entity.declare.army;

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
 * @Description: 经费预算明细表-业务费（军内科研）
 * @author onlineGenerator
 * @date 2015-08-01 11:46:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_funds_budget_busi", schema = "")
@LogEntity("经费预算明细表-业务费")
@SuppressWarnings("serial")
public class TPmFundsBudgetBusiEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**项目申报书主键*/
	@FieldDesc("项目申报书主键")
	@Excel(name="项目申报书主键")
	private java.lang.String projDeclareId;
	/**预算类别id（二级，对应预算类别表id）*/
	@FieldDesc("预算类别id（二级，对应预算类别表id）")
	@Excel(name="预算类别id（二级，对应预算类别表id）")
	private java.lang.String budgetId;
	/**项目/预算类别名称（可手工输入，或对应预算类别表二级类别名称）*/
	@FieldDesc("项目/预算类别名称（可手工输入，或对应预算类别表二级类别名称）")
	@Excel(name="项目/预算类别名称（可手工输入，或对应预算类别表二级类别名称）")
	private java.lang.String budgetName;
	/**简要内容*/
	@FieldDesc("简要内容")
	@Excel(name="简要内容")
	private java.lang.String briefContent;
	/**经费*/
	@FieldDesc("经费")
	@Excel(name="经费")
	private double funds;
	/**备注*/
	@FieldDesc("备注")
	@Excel(name="备注")
	private java.lang.String memo;
	/**是否可添加子节点*/
	@FieldDesc("是否可添加子节点")
	private String addChildFlag;
	/**
	 * 父节点ID
	 */
	@FieldDesc("父节点")
	private String parent;
	
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
	 *@return: java.lang.String  项目申报书主键
	 */
	@Column(name ="PROJ_DECLARE_ID",nullable=true,length=32)
	public java.lang.String getProjDeclareId(){
		return this.projDeclareId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目申报书主键
	 */
	public void setProjDeclareId(java.lang.String projDeclareId){
		this.projDeclareId = projDeclareId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预算类别id（二级，对应预算类别表id）
	 */
	@Column(name ="BUDGET_ID",nullable=true,length=32)
	public java.lang.String getBudgetId(){
		return this.budgetId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预算类别id（二级，对应预算类别表id）
	 */
	public void setBudgetId(java.lang.String budgetId){
		this.budgetId = budgetId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目/预算类别名称（可手工输入，或对应预算类别表二级类别名称）
	 */
	@Column(name ="BUDGET_NAME",nullable=true,length=50)
	public java.lang.String getBudgetName(){
		return this.budgetName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目/预算类别名称（可手工输入，或对应预算类别表二级类别名称）
	 */
	public void setBudgetName(java.lang.String budgetName){
		this.budgetName = budgetName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简要内容
	 */
	@Column(name ="BRIEF_CONTENT",nullable=true,length=60)
	public java.lang.String getBriefContent(){
		return this.briefContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简要内容
	 */
	public void setBriefContent(java.lang.String briefContent){
		this.briefContent = briefContent;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  经费
	 */
	@Column(name ="FUNDS",nullable=true,scale=2,length=10)
	public double getFunds(){
		return this.funds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  经费
	 */
	public void setFunds(double funds){
		this.funds = funds;
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

	@Column(name ="ADD_CHILD_FLAG",nullable=true,length=10)
	public String getAddChildFlag() {
		return addChildFlag;
	}

	public void setAddChildFlag(String addChildFlag) {
		this.addChildFlag = addChildFlag;
	}
	
	@Column(name ="PARENT",nullable=true,length=32)
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	
}
