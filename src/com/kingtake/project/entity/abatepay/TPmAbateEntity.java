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
 * @Description: 减免信息表
 * @author onlineGenerator
 * @date 2015-07-24 11:33:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_pm_abate", schema = "")
@SuppressWarnings("serial")
@LogEntity("减免信息表")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmAbateEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**项目基_主键*/
	private java.lang.String projectId;
    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;
	/**减免理由*/
    @FieldDesc("减免理由")
	@Excel(name="减免理由", width=40)
	private java.lang.String reason;
	/**减免具体意见*/
    @FieldDesc("减免具体意见")
	@Excel(name="减免具体意见", width=40)
	private java.lang.String suggestion;
    
    /**
     * 
     * 总经费
     */
    @Excel(name="总经费",isAmount=true)
    private BigDecimal allFee;
    
    /**减免经费额度*/
    @FieldDesc("减免金额合计")
    @Excel(name="减免金额合计",isAmount=true)
    private java.math.BigDecimal payFunds;
    /** 指定分承包减免额*/
    @FieldDesc("指定分承包减免额")
    @Excel(name="指定分承包减免额",isAmount=true)
    private BigDecimal zdfcbjme;
    /**指定外协减免额*/
    @FieldDesc("指定外协减免额")
    @Excel(name="指定外协减免额",isAmount=true)
    private BigDecimal zdwxjme;
    /**校内协作减免额*/
    @FieldDesc("校内协作减免额")
    @Excel(name="校内协作减免额",isAmount=true)
    private BigDecimal xnxzjme;
    /**大学预留比例*/
    @FieldDesc("大学预留比例%")
    @Excel(name="大学预留比例%")
    private BigDecimal dxylbl;
    /**大学预留金额*/
    @FieldDesc("大学预留金额")
    @Excel(name="大学预留金额",isAmount=true)
    private BigDecimal dxylje;
    /**学院预留比例*/
    @FieldDesc("学院预留比例%")
    @Excel(name="学院预留比例%")
    private BigDecimal xyylbl;
    /**学院预留金额*/
    @FieldDesc("学院预留金额")
    @Excel(name="学院预留金额",isAmount=true)
    private BigDecimal xyylje;
    /**系预留比例*/
    @FieldDesc("系预留比例%")
    @Excel(name="系预留比例%")
    private BigDecimal xylbl;
    /**系预留金额*/
    @FieldDesc("系预留金额")
    @Excel(name="系预留金额",isAmount=true)
    private BigDecimal xylje;
    /**教研室预留比例*/
    @FieldDesc("教研室预留比例%")
    @Excel(name="教研室预留比例%")
    private BigDecimal jysylbl;
    /**教研室预留金额*/
    @FieldDesc("教研室预留金额")
    @Excel(name="教研室预留金额",isAmount=true)
    private BigDecimal jysylje;
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
	/** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;
    
    /**
     * 附件编码
     */
    private String attachmentCode;
    
    /**
     * 绩效
     */
    private BigDecimal profor;
    
    /**
     * 机动费
     */
    private BigDecimal moto;
    
    /**
     * 绩效比例
     */
    private String profor_ratio;
    
    /**
     * 机动费比例
     */
    private String moto_ratio;
    
    /**
     * 指定分承包减免额
     */
    private String fcb_ratio;
    
    /**
     * 指定外协减免额
     */
    private String wx_ratio;
    
    /**
     * 校内协作减免额
     */
    private String xnxz_ratio;

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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  减免理由
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  减免具体意见
	 */
	@Column(name ="SUGGESTION",nullable=true,length=100)
	public java.lang.String getSuggestion(){
		return this.suggestion;
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
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  减免具体意见
	 */
	public void setSuggestion(java.lang.String suggestion){
		this.suggestion = suggestion;
	}
	@Column(name = "zdfcbjme")
    public BigDecimal getZdfcbjme() {
        return zdfcbjme;
    }

    public void setZdfcbjme(BigDecimal zdfcbjme) {
        this.zdfcbjme = zdfcbjme;
    }

    @Column(name = "zdwxjme")
    public BigDecimal getZdwxjme() {
        return zdwxjme;
    }

    public void setZdwxjme(BigDecimal zdwxjme) {
        this.zdwxjme = zdwxjme;
    }

    @Column(name = "xnxzjme")
    public BigDecimal getXnxzjme() {
        return xnxzjme;
    }

    public void setXnxzjme(BigDecimal xnxzjme) {
        this.xnxzjme = xnxzjme;
    }

    @Column(name = "dxylbl")
    public BigDecimal getDxylbl() {
        return dxylbl;
    }

    public void setDxylbl(BigDecimal dxylbl) {
        this.dxylbl = dxylbl;
    }

    @Column(name = "dxylje")
    public BigDecimal getDxylje() {
        return dxylje;
    }

    public void setDxylje(BigDecimal dxylje) {
        this.dxylje = dxylje;
    }

    @Column(name = "xyylbl")
    public BigDecimal getXyylbl() {
        return xyylbl;
    }

    public void setXyylbl(BigDecimal xyylbl) {
        this.xyylbl = xyylbl;
    }

    @Column(name = "xyylje")
    public BigDecimal getXyylje() {
        return xyylje;
    }

    public void setXyylje(BigDecimal xyylje) {
        this.xyylje = xyylje;
    }

    @Column(name = "xylbl")
    public BigDecimal getXylbl() {
        return xylbl;
    }

    public void setXylbl(BigDecimal xylbl) {
        this.xylbl = xylbl;
    }

    @Column(name = "xylje")
    public BigDecimal getXylje() {
        return xylje;
    }

    public void setXylje(BigDecimal xylje) {
        this.xylje = xylje;
    }

    @Column(name = "jysylbl")
    public BigDecimal getJysylbl() {
        return jysylbl;
    }

    public void setJysylbl(BigDecimal jysylbl) {
        this.jysylbl = jysylbl;
    }

    @Column(name = "jysylje")
    public BigDecimal getJysylje() {
        return jysylje;
    }

    public void setJysylje(BigDecimal jysylje) {
        this.jysylje = jysylje;
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

    @Column(name="all_fee")
    public BigDecimal getAllFee() {
        return allFee;
    }

    public void setAllFee(BigDecimal allFee) {
        this.allFee = allFee;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
    @Column(name = "profor")
    public BigDecimal getProfor() {
        return profor;
    }

    public void setProfor(BigDecimal profor) {
        this.profor = profor;
    }

    @Column(name = "moto")
    public BigDecimal getMoto() {
        return moto;
    }

    public void setMoto(BigDecimal moto) {
        this.moto = moto;
    }
    
    @Column(name = "profor_ratio")
    public String getProfor_ratio() {
        return profor_ratio;
    }

    public void setProfor_ratio(String profor_ratio) {
        this.profor_ratio = profor_ratio;
    }

    @Column(name = "moto_ratio")
    public String getMoto_ratio() {
        return moto_ratio;
    }

    public void setMoto_ratio(String moto_ratio) {
        this.moto_ratio = moto_ratio;
    }
    
    @Column(name = "fcb_ratio")
    public String getFcb_ratio() {
		return fcb_ratio;
	}

	public void setFcb_ratio(String fcb_ratio) {
		this.fcb_ratio = fcb_ratio;
	}

	@Column(name = "wx_ratio")
	public String getWx_ratio() {
		return wx_ratio;
	}

	public void setWx_ratio(String wx_ratio) {
		this.wx_ratio = wx_ratio;
	}

	@Column(name = "xnxz_ratio")
	public String getXnxz_ratio() {
		return xnxz_ratio;
	}

	public void setXnxz_ratio(String xnxz_ratio) {
		this.xnxz_ratio = xnxz_ratio;
	}
}
