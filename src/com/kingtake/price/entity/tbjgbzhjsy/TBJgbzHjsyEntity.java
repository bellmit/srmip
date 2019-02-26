package com.kingtake.price.entity.tbjgbzhjsy;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import com.kingtake.office.entity.purchaseplandetail.TBPurchasePlanDetailEntity;
import com.kingtake.price.entity.tbjgbzhjsymx.TBJgbzHjsymxEntity;

/**   
 * @Title: Entity
 * @Description: 价格库系统_环境试验费标准
 * @author onlineGenerator
 * @date 2016-07-29 10:02:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_hjsy", schema = "")
@SuppressWarnings("serial")
public class TBJgbzHjsyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**试验项目*/
	@Excel(name="试验项目")
	private java.lang.String syxm;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建日期*/
	private java.util.Date createDate;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新日期*/
	private java.util.Date updateDate;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	
    @ExcelCollection(name = "环境试验费标准明细")
    private List<TBJgbzHjsymxEntity> tBJgbzhjsymxEntity;
	
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
	 *@return: java.lang.String  试验项目
	 */
	@Column(name ="SYXM",nullable=true,length=100)
	public java.lang.String getSyxm(){
		return this.syxm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  试验项目
	 */
	public void setSyxm(java.lang.String syxm){
		this.syxm = syxm;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "syxmid")
	public List<TBJgbzHjsymxEntity> gettBJgbzhjsymxEntity() {
		return tBJgbzhjsymxEntity;
	}

	public void settBJgbzhjsymxEntity(List<TBJgbzHjsymxEntity> tBJgbzhjsymxEntity) {
		this.tBJgbzhjsymxEntity = tBJgbzhjsymxEntity;
	}
	
	
}
