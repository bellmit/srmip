package com.kingtake.price.entity.tbjgbzfy;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.constant.SrmipConstants;

/**   
 * @Title: Entity
 * @Description: 翻译费标准
 * @author onlineGenerator
 * @date 2016-07-22 17:23:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_fy", schema = "")
@SuppressWarnings("serial")
public class TBJgbzFyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**源语种*/
	@Excel(name="语种")
	private java.lang.String yz;
	/**普通类*/
	@Excel(name="普通类",isAmount=true)
	private java.math.BigDecimal ptl;
	/**技术类*/
	@Excel(name="技术类",isAmount=true)
	private java.math.BigDecimal jsl;
	/**合同条款类*/
	@Excel(name="合同条款类",isAmount=true)
	private java.math.BigDecimal httkl;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	
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
	 *@return: java.lang.String  源语种
	 */
	@Column(name ="YZ",nullable=true,length=4)
	public java.lang.String getYz(){
		return this.yz;
	}

	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源语种
	 */
	public void setYz(java.lang.String yz){
		this.yz = yz;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  普通类
	 */
	@Column(name ="PTL",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getPtl(){
		return this.ptl;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  普通类
	 */
	public void setPtl(java.math.BigDecimal ptl){
		this.ptl = ptl;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  技术类
	 */
	@Column(name ="JSL",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getJsl(){
		return this.jsl;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  技术类
	 */
	public void setJsl(java.math.BigDecimal jsl){
		this.jsl = jsl;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  合同条款类
	 */
	@Column(name ="HTTKL",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getHttkl(){
		return this.httkl;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  合同条款类
	 */
	public void setHttkl(java.math.BigDecimal httkl){
		this.httkl = httkl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
