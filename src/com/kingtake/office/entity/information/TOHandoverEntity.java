package com.kingtake.office.entity.information;

import java.util.List;
import java.util.Map;

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

import com.kingtake.common.constant.SrmipConstants;

/**   
 * @Title: Entity
 * @Description: 交班材料信息
 * @author onlineGenerator
 * @date 2015-07-13 17:03:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_handover", schema = "")
@SuppressWarnings("serial")
@LogEntity("交班材料")
public class TOHandoverEntity implements java.io.Serializable {
	/**主键*/
    @FieldDesc("主键")
	private java.lang.String id;
	/**交班人id*/
    @FieldDesc("交班人id")
	private java.lang.String handoverId;
	/**交班人姓名*/
	@Excel(name="交班人")
	@FieldDesc("交班人")
	private java.lang.String handoverName;
    @FieldDesc("交班人单位")
    private java.lang.String handoverDepartId;
    @Excel(name = "交班人单位")
    @FieldDesc("交班人单位")
    private java.lang.String handoverDepartName;
	/**交班时间*/
	@Excel(name="交班时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
	@FieldDesc("交班时间")
	private java.util.Date handoverTime;
    /** 工作内容 */
    @Excel(name = "工作内容")
    @FieldDesc("工作内容")
	private java.lang.String handoverContent;
	/**接收人*/
	@FieldDesc("接收人")
	private java.lang.String receiver;
	/**接收人姓名*/
    @Excel(name="接收人")
    @FieldDesc("接收人")
    private java.lang.String receiverName;
	/**接收时间*/
	@Excel(name="接收时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
	@FieldDesc("接收时间")
	private java.util.Date recieveTime;
	/**备注*/
    @Excel(name = "备注", width = 50)
	@FieldDesc("备注")
	private java.lang.String remark;
	@Excel(name="交班状态", isExportConvert=true)
	@FieldDesc("交班状态：科研代码集--TJZT")
	private java.lang.String submitFlag;
    @Excel(name="状态变更时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
    @FieldDesc("状态变更时间")
    private java.util.Date submitTime;
	@FieldDesc("附件")
    private List<TSFilesEntity> certificates;
	
    /**
     * 下周工作计划
     */
    @FieldDesc("下周工作计划")
    @Excel(name = "下周工作计划")
    private String nextWeekWorkContent;

    /**
     * 承担主要任务情况
     */
    @FieldDesc("承担主要任务情况")
    @Excel(name = "承担主要任务情况")
    private String mainTask;

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
	 *@return: java.lang.String  交班人id
	 */
	@Column(name ="HANDOVER_ID",nullable=true,length=32)
	public java.lang.String getHandoverId(){
		return this.handoverId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  交班人id
	 */
	public void setHandoverId(java.lang.String handoverId){
		this.handoverId = handoverId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  交班人姓名
	 */
	@Column(name ="HANDOVER_NAME",nullable=true,length=36)
	public java.lang.String getHandoverName(){
		return this.handoverName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  交班人姓名
	 */
	public void setHandoverName(java.lang.String handoverName){
		this.handoverName = handoverName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  交班时间
	 */
	@Column(name ="HANDOVER_TIME",nullable=true)
	public java.util.Date getHandoverTime(){
		return this.handoverTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  交班时间
	 */
	public void setHandoverTime(java.util.Date handoverTime){
		this.handoverTime = handoverTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  交班内容摘要
	 */
	@Column(name ="HANDOVER_CONTENT",nullable=true,length=2000)
	public java.lang.String getHandoverContent(){
		return this.handoverContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  交班内容摘要
	 */
	public void setHandoverContent(java.lang.String handoverContent){
		this.handoverContent = handoverContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接收人
	 */
	@Column(name ="RECEIVER_ID",nullable=true,length=1000)
	public java.lang.String getReceiver(){
		return this.receiver;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收人
	 */
	public void setReceiver(java.lang.String receiver){
		this.receiver = receiver;
	}
	
    @Column(name = "RECEIVER_NAME")
	public java.lang.String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(java.lang.String receiverName) {
        this.receiverName = receiverName;
    }

    /**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  接收时间
	 */
	@Column(name ="RECIEVE_TIME",nullable=true)
	public java.util.Date getRecieveTime(){
		return this.recieveTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  接收时间
	 */
	public void setRecieveTime(java.util.Date recieveTime){
		this.recieveTime = recieveTime;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否提交
	 */
	@Column(name ="SUBMIT_FLAG",nullable=true,length=1)
	public java.lang.String getSubmitFlag(){
		return this.submitFlag;
	}

	public String convertGetSubmitFlag(){
    	String result = "";
    	Map<String, Object> detail = SrmipConstants.dicResearchMap.get("TJZT");
    	if(detail != null){
    		result = detail.get(this.submitFlag).toString();
    	}
    	return result;
    }
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否提交
	 */
	public void setSubmitFlag(java.lang.String submitFlag){
		this.submitFlag = submitFlag;
	}
	
    @Column(name ="SUBMIT_TIME",nullable=true)
	public java.util.Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(java.util.Date submitTime) {
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

    @Column(name = "HANDOVER_DEPART_ID")
    public java.lang.String getHandoverDepartId() {
        return handoverDepartId;
    }

    public void setHandoverDepartId(java.lang.String handoverDepartId) {
        this.handoverDepartId = handoverDepartId;
    }

    @Column(name = "HANDOVER_DEPART_NAME")
    public java.lang.String getHandoverDepartName() {
        return handoverDepartName;
    }

    public void setHandoverDepartName(java.lang.String handoverDepartName) {
        this.handoverDepartName = handoverDepartName;
    }

    @Column(name = "NEXTWEEK_WORK_CONTENT")
    public String getNextWeekWorkContent() {
        return nextWeekWorkContent;
    }

    public void setNextWeekWorkContent(String nextWeekWorkContent) {
        this.nextWeekWorkContent = nextWeekWorkContent;
    }

    @Column(name = "MAIN_TASK")
    public String getMainTask() {
        return mainTask;
    }

    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

}
