package com.kingtake.base.entity.quota;

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
 * @Description: 经费限额设置
 * @author onlineGenerator
 * @date 2015-07-17 11:29:53
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_funds_budget_rela", schema = "")
@SuppressWarnings("serial")
@LogEntity("经费限额设置")
public class TBFundsBudgetRelaEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键 ")
	private java.lang.String id;
    /** 经费性质设置信息表主键 */
    @FieldDesc("经费性质")
    @Excel(name = "经费性质")
	private java.lang.String fundsPropertyCode;
    /** 立项形式与预算类型关联表主键 */
    @FieldDesc("预算类型")
    @Excel(name = "预算类型")
	private java.lang.String approvalBudgetRelaId;
    /** 100万以上比例值 */
    @FieldDesc("100万以上比例值")
    @Excel(name = "100万以上比例值",isAmount = true)
	private java.math.BigDecimal millionUp;
    /** 100万及以下比例值 */
    @FieldDesc("100万及以下比例值")
    @Excel(name = "100万及以下比例值",isAmount = true)
	private java.math.BigDecimal millionDown;
    /** 备注 */
    @FieldDesc("备注 ")
    @Excel(name = "备注")
	private java.lang.String memo;
	
	        /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主键
     */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	        /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主键
     */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 经费性质设置信息表主键
     */
	@Column(name ="FUNDS_PROPERTY_CODE",nullable=true,length=32)
	public java.lang.String getFundsPropertyCode(){
		return this.fundsPropertyCode;
	}

	        /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 经费性质设置信息表主键
     */
	public void setFundsPropertyCode(java.lang.String fundsPropertyCode){
		this.fundsPropertyCode = fundsPropertyCode;
	}
	
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 立项形式与预算类型关联表主键
     */
	@Column(name ="APPROVAL_BUDGET_RELA_ID",nullable=true,length=32)
	public java.lang.String getApprovalBudgetRelaId(){
		return this.approvalBudgetRelaId;
	}

	        /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 立项形式与预算类型关联表主键
     */
	public void setApprovalBudgetRelaId(java.lang.String approvalBudgetRelaId){
		this.approvalBudgetRelaId = approvalBudgetRelaId;
	}
	
    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 100万以上比例值
     */
	@Column(name ="MILLION_UP",nullable=true,scale=2,length=6)
	public java.math.BigDecimal getMillionUp(){
		return this.millionUp;
	}

	        /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 100万以上比例值
     */
	public void setMillionUp(java.math.BigDecimal millionUp){
		this.millionUp = millionUp;
	}
	
    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 100万及以下比例值
     */
	@Column(name ="MILLION_DOWN",nullable=true,scale=2,length=6)
	public java.math.BigDecimal getMillionDown(){
		return this.millionDown;
	}

	        /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 100万及以下比例值
     */
	public void setMillionDown(java.math.BigDecimal millionDown){
		this.millionDown = millionDown;
	}
	
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
	@Column(name ="MEMO",nullable=true,length=200)
	public java.lang.String getMemo(){
		return this.memo;
	}

	        /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
	public void setMemo(java.lang.String memo){
		this.memo = memo;
	}
}
