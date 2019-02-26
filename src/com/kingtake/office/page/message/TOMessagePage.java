
package com.kingtake.office.page.message;
import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;


/**   
 * @Title: Entity
 * @Description: 系统消息
 * @author onlineGenerator
 * @date 2015-07-07 17:03:31
 * @version V1.0   
 *
 */
public class TOMessagePage implements java.io.Serializable {
	/**保存-系统消息接收人*/
	private List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
	public List<TOMessageReadEntity> getTOMessageReadList() {
		return tOMessageReadList;
	}
	public void setTOMessageReadList(List<TOMessageReadEntity> tOMessageReadList) {
		this.tOMessageReadList = tOMessageReadList;
	}

	/**主键*/
	private java.lang.String id;
	/**发送人id*/
	private java.lang.String senderId;
	/**发送人姓名*/
	private java.lang.String senderName;
	/**发送时间*/
	private java.util.Date sendTime;
	/**标题*/
	private java.lang.String title;
	/**内容*/
	private java.lang.String content;
	/**发送人删除标志*/
	private java.lang.String delFlag;
	/**删除时间*/
	private java.util.Date delTime;
	
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
	 *@return: java.lang.String  发送人id
	 */
	public java.lang.String getSenderId(){
		return this.senderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送人id
	 */
	public void setSenderId(java.lang.String senderId){
		this.senderId = senderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送人姓名
	 */
	public java.lang.String getSenderName(){
		return this.senderName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送人姓名
	 */
	public void setSenderName(java.lang.String senderName){
		this.senderName = senderName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发送时间
	 */
	public java.util.Date getSendTime(){
		return this.sendTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发送时间
	 */
	public void setSendTime(java.util.Date sendTime){
		this.sendTime = sendTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送人删除标志
	 */
	public java.lang.String getDelFlag(){
		return this.delFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送人删除标志
	 */
	public void setDelFlag(java.lang.String delFlag){
		this.delFlag = delFlag;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  删除时间
	 */
	public java.util.Date getDelTime(){
		return this.delTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  删除时间
	 */
	public void setDelTime(java.util.Date delTime){
		this.delTime = delTime;
	}
}
