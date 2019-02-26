package com.kingtake.office.entity.travel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;

/**   
 * @Title: Entity
 * @Description: 差旅-人员请假详细信息表
 * @author onlineGenerator
 * @date 2015-07-07 11:24:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_travel_leavedetail", schema = "")
@SuppressWarnings("serial")
@LogEntity("差旅-人员请假详细信息表")
public class TOTravelLeavedetailEntity implements java.io.Serializable {
	/**主键*/
    @FieldDesc("主键")
	private java.lang.String id;
	/**差旅主键*/
	@FieldDesc("差旅主键")
	private java.lang.String toId;
	/**起始时间*/
	@FieldDesc("起始时间")
	private java.util.Date startTime;
	/**截止时间*/
	@FieldDesc("截止时间")
	private java.util.Date endTime;
	/**外出地点*/
	@FieldDesc("外出地点")
	private java.lang.String address;
	/**请假事由*/
	@FieldDesc("请假事由")
	private java.lang.String leaveReason;

	@FieldDesc("随行人员ID")
	private java.lang.String withPeopleID;

	@FieldDesc("随行人员名称")
	private java.lang.String withPeopleName;
	
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
	 *@return: java.lang.String  差旅主键
	 */
	@Column(name ="T_O_ID",nullable=true,length=32)
	public java.lang.String getToId(){
		return this.toId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  差旅主键
	 */
	public void setToId(java.lang.String toId){
		this.toId = toId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  起始时间
	 */
	@Column(name ="START_TIME",nullable=true)
	public java.util.Date getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  起始时间
	 */
	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  截止时间
	 */
	@Column(name ="END_TIME",nullable=true)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  截止时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外出地点
	 */
	@Column(name ="ADDRESS",nullable=true,length=150)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外出地点
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  请假事由
	 */
	@Column(name ="LEAVE_REASON",nullable=true,length=800)
	public java.lang.String getLeaveReason(){
		return this.leaveReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  请假事由
	 */
	public void setLeaveReason(java.lang.String leaveReason){
		this.leaveReason = leaveReason;
	}

	/**随行人员ID*/
	@Column(name ="WITH_PEOPLE_ID",nullable=true,length=32)
	public String getWithPeopleID() {
		return withPeopleID;
	}

	public void setWithPeopleID(String withPeopleID) {
		this.withPeopleID = withPeopleID;
	}

	/**随行人员名称*/
	@Column(name ="WITH_PEOPLE_NAME",nullable=true,length=100)
	public String getWithPeopleName() {
		return withPeopleName;
	}

	public void setWithPeopleName(String withPeopleName) {
		this.withPeopleName = withPeopleName;
	}
}
