package com.kingtake.base.entity.warnmessage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 消息提醒
 * @author onlineGenerator
 * @date 2016-01-08 15:32:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_warn", schema = "")
@SuppressWarnings("serial")
public class TBWarnEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**提醒配置表id*/
	@Excel(name="提醒配置表id")
    private TBWarnPropertiesEntity warnProperty;
	/**开始提醒时间*/
	@Excel(name="开始提醒时间")
	private java.util.Date warnBeginTime;
	/**结束提醒时间*/
	@Excel(name="结束提醒时间")
	private java.util.Date warnEndTime;
	/**提醒时间点*/
	@Excel(name="提醒时间点")
    private String warnTimePoint;
	/**提醒内容*/
	@Excel(name="提醒内容")
	private java.lang.String warnContent;
	/**提醒频率*/
	@Excel(name="提醒频率")
	private java.lang.String warnFrequency;
	/**创建人*/
	private java.lang.String createBy;
	/**创建人姓名*/
	private java.lang.String createName;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改人*/
	private java.lang.String updateBy;
	/**修改人姓名*/
	private java.lang.String updateName;
	/**修改时间*/
	private java.util.Date updateDate;
	
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    public TBWarnPropertiesEntity getWarnProperty() {
        return warnProperty;
    }

    public void setWarnProperty(TBWarnPropertiesEntity warnProperty) {
        this.warnProperty = warnProperty;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 开始提醒时间
     */
	@Column(name ="WARN_BEGIN_TIME",nullable=true)
	public java.util.Date getWarnBeginTime(){
		return this.warnBeginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始提醒时间
	 */
	public void setWarnBeginTime(java.util.Date warnBeginTime){
		this.warnBeginTime = warnBeginTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束提醒时间
	 */
	@Column(name ="WARN_END_TIME",nullable=true)
	public java.util.Date getWarnEndTime(){
		return this.warnEndTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束提醒时间
	 */
	public void setWarnEndTime(java.util.Date warnEndTime){
		this.warnEndTime = warnEndTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  提醒时间点
	 */
	@Column(name ="WARN_TIME_POINT",nullable=true)
    public String getWarnTimePoint() {
		return this.warnTimePoint;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  提醒时间点
	 */
    public void setWarnTimePoint(String warnTimePoint) {
		this.warnTimePoint = warnTimePoint;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒内容
	 */
	@Column(name ="WARN_CONTENT",nullable=true,length=200)
	public java.lang.String getWarnContent(){
		return this.warnContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒内容
	 */
	public void setWarnContent(java.lang.String warnContent){
		this.warnContent = warnContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒频率
	 */
	@Column(name ="WARN_FREQUENCY",nullable=true,length=2)
	public java.lang.String getWarnFrequency(){
		return this.warnFrequency;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒频率
	 */
	public void setWarnFrequency(java.lang.String warnFrequency){
		this.warnFrequency = warnFrequency;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人姓名
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人姓名
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
