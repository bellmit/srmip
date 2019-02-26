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
 * @Description: 项目申报书人员组织与分工信息表
 * @author onlineGenerator
 * @date 2015-08-01 11:43:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_declare_member", schema = "")
@LogEntity("项目申报书人员组织与分工信息表")
@SuppressWarnings("serial")
public class TPmDeclareMemberEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**项目申报书表主键*/
	@FieldDesc("项目申报书表主键")
	@Excel(name="项目申报书表主键")
	private java.lang.String projDeclareId;
	/**用户id*/
	@FieldDesc("用户id")
	@Excel(name="用户id")
	private java.lang.String userid;
	/**姓名*/
	@FieldDesc("姓名")
	@Excel(name="姓名")
	private java.lang.String name;
	/**性别*/
	@FieldDesc("性别")
	@Excel(name="性别")
	private java.lang.String sex;
	/**出生年月*/
	@FieldDesc("出生年月")
	@Excel(name="出生年月")
	private java.util.Date birthday;
	/**学位*/
	@FieldDesc("学位")
	@Excel(name="学位")
	private java.lang.String degree;
	/**职称职务*/
	@FieldDesc("职称职务")
	@Excel(name="职称职务")
	private java.lang.String position;
	/**任务分工*/
	@FieldDesc("任务分工")
	@Excel(name="任务分工")
	private java.lang.String taskDivide;
	/**所属单位*/
	@FieldDesc("所属单位")
	@Excel(name="所属单位")
	private java.lang.String superUnit;
	/**负责人标志*/
	@FieldDesc("负责人标志")
	@Excel(name="负责人标志")
	private java.lang.String projectManager;
	/**军线电话/手机*/
	@FieldDesc("军线电话/手机")
	@Excel(name="军线电话/手机")
	private java.lang.String contactPhone;
	/**通信地址*/
	@FieldDesc("通信地址")
	@Excel(name="通信地址")
	private java.lang.String postalAddress;
	/**邮编*/
	@FieldDesc("邮编")
	@Excel(name="邮编")
	private java.lang.String postCode;
	
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
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USERID",nullable=true,length=32)
	public java.lang.String getUserid(){
		return this.userid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserid(java.lang.String userid){
		this.userid = userid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="NAME",nullable=true,length=36)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,length=1)
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  出生年月
	 */
	@Column(name ="BIRTHDAY",nullable=true)
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  出生年月
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  学位
	 */
	@Column(name ="DEGREE",nullable=true,length=2)
	public java.lang.String getDegree(){
		return this.degree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  学位
	 */
	public void setDegree(java.lang.String degree){
		this.degree = degree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职称职务
	 */
	@Column(name ="POSITION",nullable=true,length=2)
	public java.lang.String getPosition(){
		return this.position;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职称职务
	 */
	public void setPosition(java.lang.String position){
		this.position = position;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务分工
	 */
	@Column(name ="TASK_DIVIDE",nullable=true,length=2000)
	public java.lang.String getTaskDivide(){
		return this.taskDivide;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务分工
	 */
	public void setTaskDivide(java.lang.String taskDivide){
		this.taskDivide = taskDivide;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属单位
	 */
	@Column(name ="SUPER_UNIT",nullable=true,length=32)
	public java.lang.String getSuperUnit(){
		return this.superUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属单位
	 */
	public void setSuperUnit(java.lang.String superUnit){
		this.superUnit = superUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人标志
	 */
	@Column(name ="PROJECT_MANAGER",nullable=true,length=1)
	public java.lang.String getProjectManager(){
		return this.projectManager;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人标志
	 */
	public void setProjectManager(java.lang.String projectManager){
		this.projectManager = projectManager;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  军线电话/手机
	 */
	@Column(name ="CONTACT_PHONE",nullable=true,length=20)
	public java.lang.String getContactPhone(){
		return this.contactPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  军线电话/手机
	 */
	public void setContactPhone(java.lang.String contactPhone){
		this.contactPhone = contactPhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通信地址
	 */
	@Column(name ="POSTAL_ADDRESS",nullable=true,length=80)
	public java.lang.String getPostalAddress(){
		return this.postalAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通信地址
	 */
	public void setPostalAddress(java.lang.String postalAddress){
		this.postalAddress = postalAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮编
	 */
	@Column(name ="POST_CODE",nullable=true,length=6)
	public java.lang.String getPostCode(){
		return this.postCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮编
	 */
	public void setPostCode(java.lang.String postCode){
		this.postCode = postCode;
	}
}
