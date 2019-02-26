package com.kingtake.project.entity.appraisal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**   
 * @Title: Entity
 * @Description: 鉴定计划未完成情况说明表
 * @author onlineGenerator
 * @date 2015-09-09 09:41:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_appraisa_no_finish", schema = "")
@LogEntity("鉴定计划未完成情况说明表")
@SuppressWarnings("serial")
public class TBAppraisalNoFinishEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键id")
	private java.lang.String id;
	/**鉴定计_主键*/
	@Excel(name="鉴定计_主键")
	@FieldDesc("鉴定计_主键")
	private java.lang.String tbId;
	/**拟更改鉴定时间*/
	@Excel(name="拟更改鉴定时间")
	@FieldDesc("拟更改鉴定时间")
	private java.util.Date changeAppraisaTime;
	/**未完成鉴定原因*/
	@Excel(name="未完成鉴定原因")
	@FieldDesc("未完成鉴定原因")
	private java.lang.String noFinishReason;
	@Excel(name="当前状态")
	@FieldDesc("当前状态")
	private String state;
	/**创建人*/
	@Excel(name="创建人")
	@FieldDesc("创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	@FieldDesc("创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@Excel(name="创建时间")
	@FieldDesc("创建时间")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人")
	@FieldDesc("修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@Excel(name="修改人姓名")
	@FieldDesc("修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@Excel(name="修改时间")
	@FieldDesc("修改时间")
	private java.util.Date updateDate;
	@FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
	
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
	 *@return: java.lang.String  鉴定计_主键
	 */
	@Column(name ="T_B_ID",nullable=true,length=32)
	public java.lang.String getTbId() {
		return tbId;
	}

	public void setTbId(java.lang.String tbId) {
		this.tbId = tbId;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  拟更改鉴定时间
	 */
	@Column(name ="CHANGE_APPRAISA_TIME",nullable=true)
	public java.util.Date getChangeAppraisaTime(){
		return this.changeAppraisaTime;
	}

	

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  拟更改鉴定时间
	 */
	public void setChangeAppraisaTime(java.util.Date changeAppraisaTime){
		this.changeAppraisaTime = changeAppraisaTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  未完成鉴定原因
	 */
	@Column(name ="NO_FINISH_REASON",nullable=true,length=200)
	public java.lang.String getNoFinishReason(){
		return this.noFinishReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  未完成鉴定原因
	 */
	public void setNoFinishReason(java.lang.String noFinishReason){
		this.noFinishReason = noFinishReason;
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

	@Column(name ="STATE",nullable=true,length=1)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bid")
	@OrderBy("createdate")
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }
	
	public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }
}
