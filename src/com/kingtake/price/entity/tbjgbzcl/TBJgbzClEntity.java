package com.kingtake.price.entity.tbjgbzcl;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 价格库系统_差旅费
 * @author onlineGenerator
 * @date 2016-07-22 14:08:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_cl", schema = "")
@SuppressWarnings("serial")
public class TBJgbzClEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**地区*/
	@Excel(name="地区",width=20)
	private java.lang.String dq;
	/**大军区职以上以及相当职务人员*/
	@Excel(name="大军区职以上以及相当职务人员",width=12)
	private java.lang.String djqz;
	/**军职以及相当职务人员*/
	@Excel(name="军职以及相当职务人员",width=12)
	private java.lang.String jz;
	/**师职以及相当职务人员*/
	@Excel(name="师职以及相当职务人员",width=12)
	private java.lang.String sz;
	/**团职以下以及相当职务人员*/
	@Excel(name="团职以下以及相当职务人员",width=12)
	private java.lang.String tz;
	/**伙食补助费标准*/
	@Excel(name="伙食补助费标准",width=12)
	private java.lang.String hsbz;
	/**市内交通费标准*/
	@Excel(name="市内交通费标准",width=12)
	private java.lang.String snjt;
	/**执行时间*/
	@Excel(name="执行时间",exportFormat="yyyy-MM-dd",width=20)
	private java.util.Date zxsj;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新人名称*/
	private java.lang.String updateName;
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
	 *@return: java.lang.String  地区
	 */
	@Column(name ="DQ",nullable=true,length=10)
	public java.lang.String getDq(){
		return this.dq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地区
	 */
	public void setDq(java.lang.String dq){
		this.dq = dq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  大军区职以上以及相当职务人员
	 */
	@Column(name ="DJQZ",nullable=true,length=80)
	public java.lang.String getDjqz(){
		return this.djqz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  大军区职以上以及相当职务人员
	 */
	public void setDjqz(java.lang.String djqz){
		this.djqz = djqz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  军职以及相当职务人员
	 */
	@Column(name ="JZ",nullable=true,length=80)
	public java.lang.String getJz(){
		return this.jz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  军职以及相当职务人员
	 */
	public void setJz(java.lang.String jz){
		this.jz = jz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  师职以及相当职务人员
	 */
	@Column(name ="SZ",nullable=true,length=80)
	public java.lang.String getSz(){
		return this.sz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  师职以及相当职务人员
	 */
	public void setSz(java.lang.String sz){
		this.sz = sz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  团职以下以及相当职务人员
	 */
	@Column(name ="TZ",nullable=true,length=80)
	public java.lang.String getTz(){
		return this.tz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  团职以下以及相当职务人员
	 */
	public void setTz(java.lang.String tz){
		this.tz = tz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  伙食补助费标准
	 */
	@Column(name ="HSBZ",nullable=true,length=80)
	public java.lang.String getHsbz(){
		return this.hsbz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  伙食补助费标准
	 */
	public void setHsbz(java.lang.String hsbz){
		this.hsbz = hsbz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  市内交通费标准
	 */
	@Column(name ="SNJT",nullable=true,length=80)
	public java.lang.String getSnjt(){
		return this.snjt;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  市内交通费标准
	 */
	public void setSnjt(java.lang.String snjt){
		this.snjt = snjt;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  执行时间
	 */
	@Column(name ="ZXSJ",nullable=true,length=20)
	public java.util.Date getZxsj(){
		return this.zxsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  执行时间
	 */
	public void setZxsj(java.util.Date zxsj){
		this.zxsj = zxsj;
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
}
