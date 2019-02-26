package com.kingtake.zscq.entity.sqwj;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 申请文件
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_z_sqwj", schema = "")
@SuppressWarnings("serial")
public class TZSqwjEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;

    /**
     * 专利申请id
     */
    private String zlsqId;

    /**
     * 请求书
     */
    private String qqs;

    /**
     * 说明书摘要
     */
    private String smszy;

    /**
     * 摘要附图
     */
    private String zyft;

    /**
     * 权利要求书
     */
    private String qlyqs;

    /**
     * 说明书
     */
    private String sms;

    /**
     * 说明书附图
     */
    private String smsft;

    /**
     * 实质审查请求书
     */
    private String szscqqs;

    /**
     * 密级证明
     */
    private String mjzm;

    /**
     * 费用减缓请求书
     */
    private String fyjhqqs;

    /**
     * 费用减缓请求证明
     */
    private String fyjhqqzm;

    /**
     * 专利代理委托书
     */
    private String zldlwts;

    /**
     * 状态,0 暂存,1 提交,2 修改 ,3 确认
     */
    private String applyStatus;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 检查用户id
     */
    private String checkUserId;

    /**
     * 检查用户名称
     */
    private String checkUserName;

    /**
     * 修改意见
     */
    private String msgText;

	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@Excel(name="创建时间")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@Excel(name="修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@Excel(name="修改时间")
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

    @Column(name = "zlsq_id")
    public String getZlsqId() {
        return zlsqId;
    }

    public void setZlsqId(String zlsqId) {
        this.zlsqId = zlsqId;
    }

    @Column(name = "qqs")
    public String getQqs() {
        return qqs;
    }

    public void setQqs(String qqs) {
        this.qqs = qqs;
    }

    @Column(name = "smszy")
    public String getSmszy() {
        return smszy;
    }

    public void setSmszy(String smszy) {
        this.smszy = smszy;
    }

    @Column(name = "zyft")
    public String getZyft() {
        return zyft;
    }

    public void setZyft(String zyft) {
        this.zyft = zyft;
    }

    @Column(name = "qlyqs")
    public String getQlyqs() {
        return qlyqs;
    }

    public void setQlyqs(String qlyqs) {
        this.qlyqs = qlyqs;
    }

    @Column(name = "sms")
    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    @Column(name = "smsft")
    public String getSmsft() {
        return smsft;
    }

    public void setSmsft(String smsft) {
        this.smsft = smsft;
    }

    @Column(name = "szscqqs")
    public String getSzscqqs() {
        return szscqqs;
    }

    public void setSzscqqs(String szscqqs) {
        this.szscqqs = szscqqs;
    }

    @Column(name = "mjzm")
    public String getMjzm() {
        return mjzm;
    }

    public void setMjzm(String mjzm) {
        this.mjzm = mjzm;
    }

    @Column(name = "fyjhqqs")
    public String getFyjhqqs() {
        return fyjhqqs;
    }

    public void setFyjhqqs(String fyjhqqs) {
        this.fyjhqqs = fyjhqqs;
    }

    @Column(name = "fyjhqqzm")
    public String getFyjhqqzm() {
        return fyjhqqzm;
    }

    public void setFyjhqqzm(String fyjhqqzm) {
        this.fyjhqqzm = fyjhqqzm;
    }

    @Column(name = "zldlwts")
    public String getZldlwts() {
        return zldlwts;
    }

    public void setZldlwts(String zldlwts) {
        this.zldlwts = zldlwts;
    }

    @Column(name = "apply_status")
    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    @Column(name = "msg_text")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Column(name = "submit_time")
    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Column(name = "check_user_id")
    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    @Column(name = "check_user_name")
    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

}
