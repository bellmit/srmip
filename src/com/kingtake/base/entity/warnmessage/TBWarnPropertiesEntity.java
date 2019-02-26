package com.kingtake.base.entity.warnmessage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 提醒配置
 * @author onlineGenerator
 * @date 2016-01-08 15:32:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_warn_properties", schema = "")
@SuppressWarnings("serial")
public class TBWarnPropertiesEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**提醒业务名称*/
	@Excel(name="提醒业务名称")
	private java.lang.String businessname;
	/**配置sql*/
	@Excel(name="配置sql")
	private java.lang.String sqlstr;
	/**提醒跳转url*/
	@Excel(name="提醒跳转url")
	private java.lang.String url;
	
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
	 *@return: java.lang.String  提醒业务名称
	 */
	@Column(name ="BUSINESSNAME",nullable=true,length=50)
	public java.lang.String getBusinessname(){
		return this.businessname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒业务名称
	 */
	public void setBusinessname(java.lang.String businessname){
		this.businessname = businessname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配置sql
	 */
	@Column(name ="SQLSTR",nullable=true,length=2000)
	public java.lang.String getSqlstr(){
		return this.sqlstr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配置sql
	 */
	public void setSqlstr(java.lang.String sqlstr){
		this.sqlstr = sqlstr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒跳转url
	 */
	@Column(name ="URL",nullable=true,length=2000)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒跳转url
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
}
