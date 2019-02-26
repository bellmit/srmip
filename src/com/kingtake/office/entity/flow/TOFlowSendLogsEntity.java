package com.kingtake.office.entity.flow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 阅批单发送人
 * @author onlineGenerator
 * @date 2015-07-20 18:59:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_flow_send_logs", schema = "")
@SuppressWarnings("serial")
public class TOFlowSendLogsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**关联收发文业务主键*/
	@Excel(name="关联收发文业务主键")
	private java.lang.String sendReceiveId;
	/**流转步骤（详细说明流转的步骤）*/
	@Excel(name="流转步骤（详细说明流转的步骤）")
	private java.lang.String flowStep;
	/**操作人id*/
	@Excel(name="操作人id")
	private java.lang.String operateUserid;
	/**操作人姓名*/
	@Excel(name="操作人姓名")
	private java.lang.String operateUsername;
	/**操作时间*/
	@Excel(name="操作时间")
	private java.util.Date operateDate;
	/**操作人部门id*/
	@Excel(name="操作人部门id")
	private java.lang.String operateDepartid;
	/**操作人部门名称*/
	@Excel(name="操作人部门名称")
	private java.lang.String operateDepartname;
	/**操作人ip地址*/
	@Excel(name="操作人ip地址")
	private java.lang.String operateIp;
	/**发送意见*/
	@Excel(name="发送意见")
	private java.lang.String senderTip;
	
    /**
     * 接收记录主键
     */
    private String flowReceiveId;

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主键
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
	 *@return: java.lang.String  关联收发文业务主键
	 */
	@Column(name ="SEND_RECEIVE_ID",nullable=true,length=32)
	public java.lang.String getSendReceiveId(){
		return this.sendReceiveId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联收发文业务主键
	 */
	public void setSendReceiveId(java.lang.String sendReceiveId){
		this.sendReceiveId = sendReceiveId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流转步骤（详细说明流转的步骤）
	 */
	@Column(name ="FLOW_STEP",nullable=true,length=100)
	public java.lang.String getFlowStep(){
		return this.flowStep;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流转步骤（详细说明流转的步骤）
	 */
	public void setFlowStep(java.lang.String flowStep){
		this.flowStep = flowStep;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人id
	 */
	@Column(name ="OPERATE_USERID",nullable=true,length=32)
	public java.lang.String getOperateUserid(){
		return this.operateUserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人id
	 */
	public void setOperateUserid(java.lang.String operateUserid){
		this.operateUserid = operateUserid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人姓名
	 */
	@Column(name ="OPERATE_USERNAME",nullable=true,length=50)
	public java.lang.String getOperateUsername(){
		return this.operateUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人姓名
	 */
	public void setOperateUsername(java.lang.String operateUsername){
		this.operateUsername = operateUsername;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  操作时间
	 */
	@Column(name ="OPERATE_DATE",nullable=true)
	public java.util.Date getOperateDate(){
		return this.operateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  操作时间
	 */
	public void setOperateDate(java.util.Date operateDate){
		this.operateDate = operateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人部门id
	 */
	@Column(name ="OPERATE_DEPARTID",nullable=true,length=50)
	public java.lang.String getOperateDepartid(){
		return this.operateDepartid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人部门id
	 */
	public void setOperateDepartid(java.lang.String operateDepartid){
		this.operateDepartid = operateDepartid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人部门名称
	 */
	@Column(name ="OPERATE_DEPARTNAME",nullable=true,length=60)
	public java.lang.String getOperateDepartname(){
		return this.operateDepartname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人部门名称
	 */
	public void setOperateDepartname(java.lang.String operateDepartname){
		this.operateDepartname = operateDepartname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人ip地址
	 */
	@Column(name ="OPERATE_IP",nullable=true,length=20)
	public java.lang.String getOperateIp(){
		return this.operateIp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人ip地址
	 */
	public void setOperateIp(java.lang.String operateIp){
		this.operateIp = operateIp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送意见
	 */
	@Column(name ="SENDER_TIP",nullable=true)
	public java.lang.String getSenderTip(){
		return this.senderTip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送意见
	 */
	public void setSenderTip(java.lang.String senderTip){
		this.senderTip = senderTip;
	}

    @Column(name = "FLOW_RECEIVE_ID")
    public String getFlowReceiveId() {
        return flowReceiveId;
    }

    public void setFlowReceiveId(String flowReceiveId) {
        this.flowReceiveId = flowReceiveId;
    }

}
