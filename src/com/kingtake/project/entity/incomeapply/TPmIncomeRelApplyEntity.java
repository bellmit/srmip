package com.kingtake.project.entity.incomeapply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 到账分配到账信息关联表
 * @author onlineGenerator
 * @date 2018-03-22 23:11:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_income_rel_apply", schema = "")
@SuppressWarnings("serial")
public class TPmIncomeRelApplyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**到账id*/
	@Excel(name="到账id")
	private java.lang.String incomeId;
	/**到账分配id*/
	@Excel(name="到账分配id")
	private java.lang.String incomeApplyId;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  到账id
	 */
	@Column(name ="INCOME_ID",nullable=true,length=32)
	public java.lang.String getIncomeId(){
		return this.incomeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到账id
	 */
	public void setIncomeId(java.lang.String incomeId){
		this.incomeId = incomeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  到账分配id
	 */
	@Column(name ="INCOME_APPLY_ID",nullable=true,length=32)
	public java.lang.String getIncomeApplyId(){
		return this.incomeApplyId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  到账分配id
	 */
	public void setIncomeApplyId(java.lang.String incomeApplyId){
		this.incomeApplyId = incomeApplyId;
	}
}
