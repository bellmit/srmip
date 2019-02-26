
package com.kingtake.office.page.warn;

import java.util.ArrayList;
import java.util.List;

import com.kingtake.office.entity.warn.TOWarnReceiveEntity;


/**   
 * @Title: Entity
 * @Description: 公用提醒信息表
 * @author onlineGenerator
 * @date 2015-07-15 15:47:17
 * @version V1.0   
 *
 */
public class TOWarnPage implements java.io.Serializable {
	/**保存-公用信息接收人表*/
	private List<TOWarnReceiveEntity> tOWarnReceiveList = new ArrayList<TOWarnReceiveEntity>();
	public List<TOWarnReceiveEntity> getTOWarnReceiveList() {
		return tOWarnReceiveList;
	}
	public void setTOWarnReceiveList(List<TOWarnReceiveEntity> tOWarnReceiveList) {
		this.tOWarnReceiveList = tOWarnReceiveList;
	}

	/**主键*/
	private java.lang.String id;
	/**类型*/
	private java.lang.String warnType;
	/**提醒内容*/
	private java.lang.String warnContent;
	/**关联业务url*/
	private java.lang.String formUrl;
	/**开始提醒时间*/
	private java.util.Date warnBeginTime;
	/**结束提醒时间*/
	private java.util.Date warnEndTime;
	/**提醒状态*/
	private java.lang.String warnStatus;
	/**提醒频率*/
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
	 *@return: java.lang.String  类型
	 */
	public java.lang.String getWarnType(){
		return this.warnType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setWarnType(java.lang.String warnType){
		this.warnType = warnType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒内容
	 */
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
	 *@return: java.lang.String  关联业务url
	 */
	public java.lang.String getFormUrl(){
		return this.formUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联业务url
	 */
	public void setFormUrl(java.lang.String formUrl){
		this.formUrl = formUrl;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始提醒时间
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒状态
	 */
	public java.lang.String getWarnStatus(){
		return this.warnStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒状态
	 */
	public void setWarnStatus(java.lang.String warnStatus){
		this.warnStatus = warnStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒频率
	 */
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
