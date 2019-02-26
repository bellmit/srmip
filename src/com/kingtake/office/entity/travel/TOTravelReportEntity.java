package com.kingtake.office.entity.travel;

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
 * @Description: 差旅-出差报告信息表
 * @author onlineGenerator
 * @date 2015-07-07 11:24:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_travel_report", schema = "")
@SuppressWarnings("serial")
@LogEntity("出差报告")
public class TOTravelReportEntity implements java.io.Serializable {
	/**主键*/
    @FieldDesc("主键")
	private java.lang.String id;
	/**差旅主键*/
    @FieldDesc("差旅主键")
	private java.lang.String toId;
	/**出差事由*/
	@Excel(name="出差事由")
	@FieldDesc("出差事由")
	private java.lang.String travelReason;
	/**参加人员*/
	@Excel(name="参加人员")
	@FieldDesc("参加人员")
	private java.lang.String relateUsername;
	/**起始时间*/
	@Excel(name="起始时间")
	@FieldDesc("起始时间")
	private java.util.Date startTime;
	/**截止时间*/
	@Excel(name="截止时间")
	@FieldDesc("截止时间")
	private java.util.Date endTime;
	/**地点*/
	@Excel(name="地点")
	@FieldDesc("地点")
	private java.lang.String address;
	/**校首长阅批*/
	@Excel(name="校首长阅批")
	@FieldDesc("校首长阅批")
	private java.lang.String chiefApproval;
	/**部领导阅批*/
	@Excel(name="部领导阅批")
	@FieldDesc("部领导阅批")
	private java.lang.String departApproval;
	/**处领导阅批*/
	@Excel(name="处领导阅批")
	@FieldDesc("处领导阅批")
	private java.lang.String sectionApproval;
	/**传阅*/
	@Excel(name="传阅")
	@FieldDesc("传阅")
	private java.lang.String circelRead;
	/**备注*/
	@Excel(name="备注")
	@FieldDesc("备注")
	private java.lang.String remark;
	/**报告提交日期*/
	@Excel(name="报告提交日期")
	@FieldDesc("报告提交日期")
	private java.util.Date submitTime;
	/**附件*/
	private List<TSFilesEntity> certificates;

	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出差事由
	 */
	@Column(name ="TRAVEL_REASON",nullable=true,length=800)
	public java.lang.String getTravelReason(){
		return this.travelReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出差事由
	 */
	public void setTravelReason(java.lang.String travelReason){
		this.travelReason = travelReason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参加人员
	 */
	@Column(name ="RELATE_USERNAME",nullable=true,length=1000)
	public java.lang.String getRelateUsername(){
		return this.relateUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参加人员
	 */
	public void setRelateUsername(java.lang.String relateUsername){
		this.relateUsername = relateUsername;
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
	 *@return: java.lang.String  地点
	 */
	@Column(name ="ADDRESS",nullable=true,length=150)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地点
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  校首长阅批
	 */
	@Column(name ="CHIEF_APPROVAL",nullable=true,length=500)
	public java.lang.String getChiefApproval(){
		return this.chiefApproval;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  校首长阅批
	 */
	public void setChiefApproval(java.lang.String chiefApproval){
		this.chiefApproval = chiefApproval;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部领导阅批
	 */
	@Column(name ="DEPART_APPROVAL",nullable=true,length=500)
	public java.lang.String getDepartApproval(){
		return this.departApproval;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部领导阅批
	 */
	public void setDepartApproval(java.lang.String departApproval){
		this.departApproval = departApproval;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处领导阅批
	 */
	@Column(name ="SECTION_APPROVAL",nullable=true,length=500)
	public java.lang.String getSectionApproval(){
		return this.sectionApproval;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处领导阅批
	 */
	public void setSectionApproval(java.lang.String sectionApproval){
		this.sectionApproval = sectionApproval;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传阅
	 */
	@Column(name ="CIRCEL_READ",nullable=true,length=500)
	public java.lang.String getCircelRead(){
		return this.circelRead;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传阅
	 */
	public void setCircelRead(java.lang.String circelRead){
		this.circelRead = circelRead;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=200)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  报告提交日期
	 */
	@Column(name ="SUBMIT_TIME",nullable=true)
	public java.util.Date getSubmitTime(){
		return this.submitTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  报告提交日期
	 */
	public void setSubmitTime(java.util.Date submitTime){
		this.submitTime = submitTime;
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
