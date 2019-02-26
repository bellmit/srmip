package com.kingtake.office.entity.holiday;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;

/**   
 * @Title: Entity
 * @Description: 假前工作计划
 * @author onlineGenerator
 * @date 2016-05-18 17:28:12
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_holiday_plan", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
public class TOHolidayPlanEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**标题*/
	@Excel(name="标题")
	private java.lang.String title;
	/**假期编码*/
    @Excel(name = "假期", isExportConvert = true)
	private java.lang.String holidayCode;
	/**单位id*/
	private java.lang.String deptId;
	/**单位名称*/
	@Excel(name="单位名称")
	private java.lang.String deptName;
	/**计划内容*/
	@Excel(name="计划内容")
	private java.lang.String planContent;
	/**createBy*/
	private java.lang.String createBy;
	/**createName*/
	private java.lang.String createName;
	/**createDate*/
	private java.util.Date createDate;
	/**updateBy*/
	private java.lang.String updateBy;
	/**updateName*/
	private java.lang.String updateName;
	/**updateDate*/
	private java.util.Date updateDate;
	
    private String receiverId;

    private String receiverName;

    /**
     * 提交状态，0 未提交 ，1 已提交，2 已驳回，3 已接收
     */
    private String submitFlag;

    /**
     * 提交时间
     */
    private Date submitTime;
    /**
     * 接收时间
     */
    private Date receiveTime;

    /**
     * 修改意见
     */
    private String msgText;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=200)
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
	 *@return: java.lang.String  假期编码
	 */
	@Column(name ="HOLIDAY_CODE",nullable=true,length=2)
	public java.lang.String getHolidayCode(){
		return this.holidayCode;
	}

    public java.lang.String convertGetHolidayCode() {
        return ConvertDicUtil.getConvertDic("1", "JQ", this.holidayCode);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  假期编码
	 */
	public void setHolidayCode(java.lang.String holidayCode){
		this.holidayCode = holidayCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位id
	 */
	@Column(name ="DEPT_ID",nullable=true,length=32)
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位id
	 */
	public void setDeptId(java.lang.String deptId){
		this.deptId = deptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位名称
	 */
	@Column(name ="DEPT_NAME",nullable=true,length=50)
	public java.lang.String getDeptName(){
		return this.deptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位名称
	 */
	public void setDeptName(java.lang.String deptName){
		this.deptName = deptName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计划内容
	 */
	@Column(name ="PLAN_CONTENT",nullable=true,length=4000)
	public java.lang.String getPlanContent(){
		return this.planContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划内容
	 */
	public void setPlanContent(java.lang.String planContent){
		this.planContent = planContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createBy
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createName
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createName
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createDate
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateBy
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateBy
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateName
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateName
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  updateDate
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  updateDate
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

    @Column(name = "receiver_id")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Column(name = "receiver_name")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name = "submit_flag")
    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    @Column(name = "submit_time")
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Column(name = "receive_time")
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Column(name = "msg_text")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

}
