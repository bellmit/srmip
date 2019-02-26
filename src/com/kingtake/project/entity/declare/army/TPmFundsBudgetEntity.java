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
 * @Description: 项目经费年度预算表（军内科研/维修科研）
 * @author onlineGenerator
 * @date 2015-08-01 11:44:58
 * @version V1.0   
 */
@Entity
@LogEntity("项目经费年度预算表（军内科研/维修科研）")
@Table(name = "t_pm_funds_budget", schema = "")
@SuppressWarnings("serial")
public class TPmFundsBudgetEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	@FieldDesc("项目申报书表主键")
	@Excel(name="项目申报书表主键")
	/**项目申报书表主键*/
	private java.lang.String projDeclareId;
	/**年度*/
	@FieldDesc("年度")
	@Excel(name="年度")
	private java.lang.String budgetYear;
	/**设备费*/
	@FieldDesc("设备费")
	@Excel(name="设备费",isAmount = true)
	private java.math.BigDecimal equipFunds;
	/**材料费*/
	@FieldDesc("材料费")
	@Excel(name="材料费",isAmount = true)
	private java.math.BigDecimal materialFunds;
	/**外协费*/
	@FieldDesc("外协费")
	@Excel(name="外协费",isAmount = true)
	private java.math.BigDecimal outsideFunds;
	/**业务费*/
	@FieldDesc("业务费")
	@Excel(name="业务费",isAmount = true)
	private java.math.BigDecimal businessFunds;
	/**合计*/
	@FieldDesc("合计")
	@Excel(name="合计",isAmount = true)
	private java.math.BigDecimal totalFunds;
	/**备注*/
	@FieldDesc("备注")
	@Excel(name="备注")
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
	 *@return: java.lang.String  项目申报书表主键
	 */
	@Column(name ="PROJ_DECLARE_ID",nullable=true,length=32)
	public java.lang.String getProjDeclareId(){
		return this.projDeclareId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目申报书表主键
	 */
	public void setProjDeclareId(java.lang.String projDeclareId){
		this.projDeclareId = projDeclareId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  年度
	 */
	@Column(name ="BUDGET_YEAR",nullable=true,length=4)
	public java.lang.String getBudgetYear(){
		return this.budgetYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年度
	 */
	public void setBudgetYear(java.lang.String budgetYear){
		this.budgetYear = budgetYear;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  设备费
	 */
	@Column(name ="EQUIP_FUNDS",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getEquipFunds(){
		return this.equipFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  设备费
	 */
	public void setEquipFunds(java.math.BigDecimal equipFunds){
		this.equipFunds = equipFunds;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  材料费
	 */
	@Column(name ="MATERIAL_FUNDS",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getMaterialFunds(){
		return this.materialFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  材料费
	 */
	public void setMaterialFunds(java.math.BigDecimal materialFunds){
		this.materialFunds = materialFunds;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  外协费
	 */
	@Column(name ="OUTSIDE_FUNDS",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getOutsideFunds(){
		return this.outsideFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  外协费
	 */
	public void setOutsideFunds(java.math.BigDecimal outsideFunds){
		this.outsideFunds = outsideFunds;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  业务费
	 */
	@Column(name ="BUSINESS_FUNDS",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getBusinessFunds(){
		return this.businessFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  业务费
	 */
	public void setBusinessFunds(java.math.BigDecimal businessFunds){
		this.businessFunds = businessFunds;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  合计
	 */
	@Column(name ="TOTAL_FUNDS",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getTotalFunds(){
		return this.totalFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  合计
	 */
	public void setTotalFunds(java.math.BigDecimal totalFunds){
		this.totalFunds = totalFunds;
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
