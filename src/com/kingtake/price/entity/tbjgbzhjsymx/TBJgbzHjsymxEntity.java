package com.kingtake.price.entity.tbjgbzhjsymx;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 环境试验费标准明细表
 * @author onlineGenerator
 * @date 2016-07-29 11:02:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_hjsymx", schema = "")
@SuppressWarnings("serial")
public class TBJgbzHjsymxEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**试验项目id*/
	@Excel(name="试验项目id")
	private java.lang.String syxmid;
	/**收费标准*/
	@Excel(name="收费标准",isAmount=true)
	private java.math.BigDecimal sfbz;
	/**收费单位*/
	@Excel(name="收费单位")
	private java.lang.String sfdw;
	/**试验设备*/
	@Excel(name="试验设备")
	private java.lang.String sysb;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String beiz;
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
	 *@return: java.lang.String  试验项目id
	 */
	@Column(name ="SYXMID",nullable=true,length=32)
	public java.lang.String getSyxmid(){
		return this.syxmid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  试验项目id
	 */
	public void setSyxmid(java.lang.String syxmid){
		this.syxmid = syxmid;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  收费标准
	 */
	@Column(name ="SFBZ",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getSfbz(){
		return this.sfbz;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  收费标准
	 */
	public void setSfbz(java.math.BigDecimal sfbz){
		this.sfbz = sfbz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收费单位
	 */
	@Column(name ="SFDW",nullable=true,length=50)
	public java.lang.String getSfdw(){
		return this.sfdw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收费单位
	 */
	public void setSfdw(java.lang.String sfdw){
		this.sfdw = sfdw;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  试验设备
	 */
	@Column(name ="SYSB",nullable=true,length=200)
	public java.lang.String getSysb(){
		return this.sysb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  试验设备
	 */
	public void setSysb(java.lang.String sysb){
		this.sysb = sysb;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEIZ",nullable=true,length=1000)
	public java.lang.String getBeiz(){
		return this.beiz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeiz(java.lang.String beiz){
		this.beiz = beiz;
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
