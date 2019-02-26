package com.kingtake.price.entity.tbjgbzjdhy;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 军队会议费
 * @author onlineGenerator
 * @date 2016-07-22 15:55:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_jdhy", schema = "")
@SuppressWarnings("serial")
public class TBJgbzJdhyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**招待所类别*/
	@Excel(name="招待所类别")
	private java.lang.String zdslb;
	/**一类会议*/
	@Excel(name="一类会议",isAmount=true)
	private java.math.BigDecimal ylhy;
	/**二类会议*/
	@Excel(name="二类会议",isAmount=true)
	private java.math.BigDecimal elhy;
	/**四类会议*/
	@Excel(name="四类会议",isAmount=true)
	private java.math.BigDecimal silhy;
	/**三类会议*/
	@Excel(name="三类会议",isAmount=true)
	private java.math.BigDecimal sanlhy;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String beiz;
	/**执行时间*/
	@Excel(name="执行时间",exportFormat="yyyy-MM-dd",width=20)
	private java.util.Date zxsj;
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
	 *@return: java.lang.String  招待所类别
	 */
	@Column(name ="ZDSLB",nullable=true,length=100)
	public java.lang.String getZdslb(){
		return this.zdslb;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  招待所类别
	 */
	public void setZdslb(java.lang.String zdslb){
		this.zdslb = zdslb;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  一类会议
	 */
	@Column(name ="YLHY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getYlhy(){
		return this.ylhy;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  一类会议
	 */
	public void setYlhy(java.math.BigDecimal ylhy){
		this.ylhy = ylhy;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  二类会议
	 */
	@Column(name ="ELHY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getElhy(){
		return this.elhy;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  二类会议
	 */
	public void setElhy(java.math.BigDecimal elhy){
		this.elhy = elhy;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  四类会议
	 */
	@Column(name ="SILHY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getSilhy(){
		return this.silhy;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  四类会议
	 */
	public void setSilhy(java.math.BigDecimal silhy){
		this.silhy = silhy;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  三类会议
	 */
	@Column(name ="SANLHY",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getSanlhy(){
		return this.sanlhy;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  三类会议
	 */
	public void setSanlhy(java.math.BigDecimal sanlhy){
		this.sanlhy = sanlhy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEIZ",nullable=true,length=500)
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
