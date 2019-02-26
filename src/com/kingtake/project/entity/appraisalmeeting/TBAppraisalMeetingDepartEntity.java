package com.kingtake.project.entity.appraisalmeeting;

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
 * @Description: 鉴定会单位信息表
 * @author onlineGenerator
 * @date 2016-01-22 14:27:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_appraisal_meeting_depart", schema = "")
@SuppressWarnings("serial")
public class TBAppraisalMeetingDepartEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**机构名称*/
	@Excel(name="机构名称")
	private java.lang.String departname;
	/**名额*/
	@Excel(name="名额")
	private java.lang.String quota;
	/**鉴定会主键*/
	@Excel(name="鉴定会主键")
	private java.lang.String meetingId;
	/**人员类型*/
	@Excel(name="人员类型")
	private java.lang.String memberType;
	
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
	 *@return: java.lang.String  机构名称
	 */
	@Column(name ="DEPARTNAME",nullable=true,length=60)
	public java.lang.String getDepartname(){
		return this.departname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构名称
	 */
	public void setDepartname(java.lang.String departname){
		this.departname = departname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名额
	 */
	@Column(name ="QUOTA",nullable=true,length=20)
	public java.lang.String getQuota(){
		return this.quota;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名额
	 */
	public void setQuota(java.lang.String quota){
		this.quota = quota;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  鉴定会主键
	 */
	@Column(name ="MEETING_ID",nullable=true,length=32)
	public java.lang.String getMeetingId(){
		return this.meetingId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  鉴定会主键
	 */
	public void setMeetingId(java.lang.String meetingId){
		this.meetingId = meetingId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  人员类型
	 */
	@Column(name ="MEMBER_TYPE",nullable=true,length=2)
	public java.lang.String getMemberType(){
		return this.memberType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  人员类型
	 */
	public void setMemberType(java.lang.String memberType){
		this.memberType = memberType;
	}
}
