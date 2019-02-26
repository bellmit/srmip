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
 * @Description: 技术基础项目申报书年度预算
 * @author onlineGenerator
 * @date 2015-09-22 11:40:24
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_declare_funds_technology", schema = "")
@LogEntity("技术基础项目申报书年度预算")
@SuppressWarnings("serial")
public class TPmDeclareFundsTechnologyEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**申报书id*/
	@Excel(name="申报书id")
	@FieldDesc("申报书id")
	private java.lang.String projDeclareId;
	/**预算年度*/
	@Excel(name="预算年度")
	@FieldDesc("预算年度")
	private java.lang.String budgetYear;
	/**预算名称*/
	@Excel(name="预算名称")
	@FieldDesc("预算名称")
	private java.lang.String budgetName;
	/**预算金额*/
	@Excel(name="预算金额")
	@FieldDesc("预算金额")
	private Double budgetFunds;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申报书id
	 */
	@Column(name ="PROJ_DECLARE_ID",nullable=true,length=32)
	public java.lang.String getProjDeclareId(){
		return this.projDeclareId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申报书id
	 */
	public void setProjDeclareId(java.lang.String projDeclareId){
		this.projDeclareId = projDeclareId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预算年度
	 */
	@Column(name ="BUDGET_YEAR",nullable=true,length=4)
	public java.lang.String getBudgetYear(){
		return this.budgetYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预算年度
	 */
	public void setBudgetYear(java.lang.String budgetYear){
		this.budgetYear = budgetYear;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预算名称
	 */
	@Column(name ="BUDGET_NAME",nullable=true,length=50)
	public java.lang.String getBudgetName(){
		return this.budgetName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预算名称
	 */
	public void setBudgetName(java.lang.String budgetName){
		this.budgetName = budgetName;
	}
	
	@Column(name ="BUDGET_FUNDS",nullable=true,scale=2,length=10)
	public Double getBudgetFunds(){
		return this.budgetFunds;
	}

	public void setBudgetFunds(Double budgetFunds){
		this.budgetFunds = budgetFunds;
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
}
