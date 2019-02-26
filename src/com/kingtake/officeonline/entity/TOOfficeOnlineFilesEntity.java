package com.kingtake.officeonline.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 在线office文件信息表
 * @author onlineGenerator
 * @date 2015-12-23 19:46:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_office_online_files", schema = "")
@SuppressWarnings("serial")
public class TOOfficeOnlineFilesEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**文件名*/
	@Excel(name="文件名")
	private java.lang.String attachmenttitle;
	/**文件类型*/
	@Excel(name="文件类型")
	private java.lang.String extend;
	/**存储路径*/
	@Excel(name="存储路径")
	private java.lang.String realpath;
	/**业务编码*/
	@Excel(name="业务编码")
	private java.lang.String businesskey;
	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@Excel(name="创建时间")
	private java.util.Date createDate;
	
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
	 *@return: java.lang.String  文件名
	 */
	@Column(name ="ATTACHMENTTITLE",nullable=true,length=100)
	public java.lang.String getAttachmenttitle(){
		return this.attachmenttitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件名
	 */
	public void setAttachmenttitle(java.lang.String attachmenttitle){
		this.attachmenttitle = attachmenttitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件类型
	 */
	@Column(name ="EXTEND",nullable=true,length=32)
	public java.lang.String getExtend(){
		return this.extend;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件类型
	 */
	public void setExtend(java.lang.String extend){
		this.extend = extend;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  存储路径
	 */
	@Column(name ="REALPATH",nullable=true,length=100)
	public java.lang.String getRealpath(){
		return this.realpath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  存储路径
	 */
	public void setRealpath(java.lang.String realpath){
		this.realpath = realpath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务编码
	 */
	@Column(name ="BUSINESSKEY",nullable=true,length=32)
	public java.lang.String getBusinesskey(){
		return this.businesskey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务编码
	 */
	public void setBusinesskey(java.lang.String businesskey){
		this.businesskey = businesskey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人姓名
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人姓名
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
}
