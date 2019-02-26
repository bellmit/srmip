package com.kingtake.project.entity.abatepay;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**   
 * @Title: Entity
 * @Description: 垫支信息表
 * @author onlineGenerator
 * @date 2015-07-24 11:33:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_pm_payfirst", schema = "")
@SuppressWarnings("serial")
@LogEntity("垫支信息表")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmPayfirstEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**项目基_主键*/

	private java.lang.String projectId;
    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;
	/**垫支经费额度*/
    @FieldDesc("垫支经费额度")
	@Excel(name="垫支经费额度",isAmount = true)
	private java.math.BigDecimal payFunds;
	/**减免垫支理由*/
    @FieldDesc("垫支理由")
	@Excel(name="垫支理由", width=40)
	private java.lang.String reason;
    /**垫资来源 */
    private String paySource;
    /**垫资年度 */
    private String payYear;
    /**垫支科目代码 */
    private String paySubjectcode;
    /**
     * 余额
     */
    private BigDecimal balance;
    
    /**
     * 条形码
     */
    private String barcode;
    
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
	/**财务状态*/
	private String cwStatus;
	/**归垫状态*/
	private String gdStatus;
	/** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;
    
    /**
     * 关联附件
     */
    private String attachmentCode;
    /**
     * 流程实例id
     */
    private String processInstId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 分配人
     */
    private String assigneeName;

    /**
     * 流程审批状态
     */
    private String bpmStatus;
	
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

	@Column(name ="PROJECT_ID",nullable=true,length=32)
    public java.lang.String getProjectId(){
        return this.projectId;
    }
	
	public void setProjectId(java.lang.String projectId){
        this.projectId = projectId;
    }
    
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  垫支经费额度
	 */
	@Column(name ="PAY_FUNDS",nullable=true,scale=2,length=13)
	public java.math.BigDecimal getPayFunds(){
		return this.payFunds;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  减免或垫支经费额度
	 */
	public void setPayFunds(java.math.BigDecimal payFunds){
		this.payFunds = payFunds;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  减免垫支理由
	 */
	@Column(name ="REASON",nullable=true,length=800)
	public java.lang.String getReason(){
		return this.reason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  减免垫支理由
	 */
	public void setReason(java.lang.String reason){
		this.reason = reason;
	}
	
	@Column(name = "pay_source")
    public String getPaySource() {
        return paySource;
    }

    public void setPaySource(String paySource) {
        this.paySource = paySource;
    }
    
    @Column(name = "pay_year")
    public String getPayYear() {
        return payYear;
    }

    public void setPayYear(String payYear) {
        this.payYear = payYear;
    }
    
    @Column(name = "pay_subjectcode")
    public String getPaySubjectcode() {
        return paySubjectcode;
    }

    public void setPaySubjectcode(String paySubjectcode) {
        this.paySubjectcode = paySubjectcode;
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

	@Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }
    
    @Transient
    public String getProcessInstId() {
        return processInstId;
    }
    
    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }
    
    @Transient
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Transient
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    @Transient
    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 流程状态
     */
    @Column(name = "BPM_STATUS", nullable = true, length = 1)
    public String getBpmStatus() {
        return bpmStatus;
    }

    public void setBpmStatus(String bpmStatus) {
        this.bpmStatus = bpmStatus;
    }

    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Column(name="balance")
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    @Column(name = "CW_STATUS")
	public String getCwStatus() {
		return cwStatus;
	}

	public void setCwStatus(String cwStatus) {
		this.cwStatus = cwStatus;
	}
	@Column(name = "GD_STATUS")
	public String getGdStatus() {
		return gdStatus;
	}

	public void setGdStatus(String gdStatus) {
		this.gdStatus = gdStatus;
	}
    
}
