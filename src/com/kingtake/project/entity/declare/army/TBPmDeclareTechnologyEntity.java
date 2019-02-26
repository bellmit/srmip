package com.kingtake.project.entity.declare.army;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;

/**   
 * @Title: Entity
 * @Description: 技术基础项目申报书
 * @author onlineGenerator
 * @date 2015-09-22 11:40:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_pm_declare_technology", schema = "")
@LogEntity("技术基础项目申报书")
@SuppressWarnings("serial")
public class TBPmDeclareTechnologyEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**项目id*/
	@Excel(name="项目id")
	@FieldDesc("项目id")
	private java.lang.String tpId;
	/**项目名称*/
	@Excel(name="项目名称")
	@FieldDesc("项目名称")
	private java.lang.String projectName;
	/**项目类型id*/
	@Excel(name="项目类型")
	@FieldDesc("项目类型")
	private TBProjectTypeEntity projectType;
	/**主办单位id*/
	@Excel(name="主办单位id")
	@FieldDesc("主办单位id")
	private java.lang.String hostDeptId;
	/**主办单位名称*/
	@Excel(name="主办单位名称")
	@FieldDesc("主办单位名称")
	private java.lang.String hostDeptName;
	/**论证单位id*/
	@Excel(name="论证单位id")
	@FieldDesc("论证单位id")
	private java.lang.String argumentDeptId;
	/**论证单位名称*/
	@Excel(name="论证单位名称")
	@FieldDesc("论证单位名称")
	private java.lang.String argumentDeptName;
	/**填报时间*/
	@Excel(name="填报时间")
	@FieldDesc("填报时间")
	private java.util.Date applyTime;
	/**内容与范围*/
	@Excel(name="内容与范围")
	@FieldDesc("内容与范围")
	private java.lang.String contentRange;
	/**需求分析*/
	@Excel(name="需求分析")
	@FieldDesc("需求分析")
	private java.lang.String demandAnalysis;
	/**国内标准现状分析*/
	@Excel(name="国内标准现状分析")
	@FieldDesc("国内标准现状分析")
	private java.lang.String domesticAnalysis;
	/**国外标准情况分析*/
	@Excel(name="国外标准情况分析")
	@FieldDesc("国外标准情况分析")
	private java.lang.String internationalAnalysis;
	/**可行性分析*/
	@Excel(name="可行性分析")
	@FieldDesc("可行性分析")
	private java.lang.String feasibilityAnalysis;
	/**有关单位及意见*/
	@Excel(name="有关单位及意见")
	@FieldDesc("有关单位及意见")
	private java.lang.String relatedUnitOpinion;
	/**项目进度*/
	@Excel(name="项目进度")
	@FieldDesc("项目进度")
	private java.lang.String projectSchedule;
	/**流程流转状态*/
	@FieldDesc("流程流转状态")
	@Excel(name="流程流转状态")
	private java.lang.String bpmStatus;
	
    @FieldDesc("创建人英文名")
    private java.lang.String createBy;
    @FieldDesc("创建人中文名")
    private java.lang.String createName;
    @FieldDesc("创建人日期")
    private java.util.Date createDate;
    @FieldDesc("更新人英文名")
    private java.lang.String updateBy;
    @FieldDesc("更新人中文名")
    private java.lang.String updateName;
    @FieldDesc("更新人中文名")
    private java.util.Date updateDate;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;

    /**
     * 流程实例id
     */
    private String processInstId;

    /**
     * 计划状态，1 已通过 ，2 已驳回 ，3 重新提交
     */
    private String planStatus;
    
    /**
     * 附件管理id
     */
    private String attachmentCode;

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
	 *@return: java.lang.String  项目id
	 */
	@Column(name ="T_P_ID",nullable=true,length=32)
	public java.lang.String getTpId() {
		return tpId;
	}

	public void setTpId(java.lang.String tpId) {
		this.tpId = tpId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目名称
	 */
	@Column(name ="PROJECT_NAME",nullable=true,length=100)
	public java.lang.String getProjectName(){
		return this.projectName;
	}

	

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目名称
	 */
	public void setProjectName(java.lang.String projectName){
		this.projectName = projectName;
	}
	
	@ManyToOne
    @JoinColumn(name = "PROJECT_TYPE_ID")
	public TBProjectTypeEntity getProjectType() {
		return projectType;
	}

	public void setProjectType(TBProjectTypeEntity projectType) {
		this.projectType = projectType;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主办单位id
	 */
	@Column(name ="HOST_DEPT_ID",nullable=true,length=32)
	public java.lang.String getHostDeptId(){
		return this.hostDeptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主办单位id
	 */
	public void setHostDeptId(java.lang.String hostDeptId){
		this.hostDeptId = hostDeptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主办单位名称
	 */
	@Column(name ="HOST_DEPT_NAME",nullable=true,length=100)
	public java.lang.String getHostDeptName(){
		return this.hostDeptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主办单位名称
	 */
	public void setHostDeptName(java.lang.String hostDeptName){
		this.hostDeptName = hostDeptName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  论证单位id
	 */
	@Column(name ="ARGUMENT_DEPT_ID",nullable=true,length=32)
	public java.lang.String getArgumentDeptId(){
		return this.argumentDeptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  论证单位id
	 */
	public void setArgumentDeptId(java.lang.String argumentDeptId){
		this.argumentDeptId = argumentDeptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  论证单位名称
	 */
	@Column(name ="ARGUMENT_DEPT_NAME",nullable=true,length=100)
	public java.lang.String getArgumentDeptName(){
		return this.argumentDeptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  论证单位名称
	 */
	public void setArgumentDeptName(java.lang.String argumentDeptName){
		this.argumentDeptName = argumentDeptName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  填报时间
	 */
	@Column(name ="APPLY_TIME",nullable=true)
	public java.util.Date getApplyTime(){
		return this.applyTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  填报时间
	 */
	public void setApplyTime(java.util.Date applyTime){
		this.applyTime = applyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容与范围
	 */
	@Column(name ="CONTENT_RANGE",nullable=true,length=500)
	public java.lang.String getContentRange(){
		return this.contentRange;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容与范围
	 */
	public void setContentRange(java.lang.String contentRange){
		this.contentRange = contentRange;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  需求分析
	 */
	@Column(name ="DEMAND_ANALYSIS",nullable=true,length=500)
	public java.lang.String getDemandAnalysis(){
		return this.demandAnalysis;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  需求分析
	 */
	public void setDemandAnalysis(java.lang.String demandAnalysis){
		this.demandAnalysis = demandAnalysis;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国内标准现状分析
	 */
	@Column(name ="DOMESTIC_ANALYSIS",nullable=true,length=500)
	public java.lang.String getDomesticAnalysis(){
		return this.domesticAnalysis;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国内标准现状分析
	 */
	public void setDomesticAnalysis(java.lang.String domesticAnalysis){
		this.domesticAnalysis = domesticAnalysis;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国外标准情况分析
	 */
	@Column(name ="INTERNATIONAL_ANALYSIS",nullable=true,length=500)
	public java.lang.String getInternationalAnalysis(){
		return this.internationalAnalysis;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国外标准情况分析
	 */
	public void setInternationalAnalysis(java.lang.String internationalAnalysis){
		this.internationalAnalysis = internationalAnalysis;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  可行性分析
	 */
	@Column(name ="FEASIBILITY_ANALYSIS",nullable=true,length=500)
	public java.lang.String getFeasibilityAnalysis(){
		return this.feasibilityAnalysis;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  可行性分析
	 */
	public void setFeasibilityAnalysis(java.lang.String feasibilityAnalysis){
		this.feasibilityAnalysis = feasibilityAnalysis;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  有关单位及意见
	 */
	@Column(name ="RELATED_UNIT_OPINION",nullable=true,length=500)
	public java.lang.String getRelatedUnitOpinion(){
		return this.relatedUnitOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  有关单位及意见
	 */
	public void setRelatedUnitOpinion(java.lang.String relatedUnitOpinion){
		this.relatedUnitOpinion = relatedUnitOpinion;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目进度
	 */
	@Column(name ="PROJECT_SCHEDULE",nullable=true,length=400)
	public java.lang.String getProjectSchedule(){
		return this.projectSchedule;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目进度
	 */
	public void setProjectSchedule(java.lang.String projectSchedule){
		this.projectSchedule = projectSchedule;
	}

	@Column(name ="BPM_STATUS",nullable=true,length=1)
	public java.lang.String getBpmStatus() {
		return bpmStatus;
	}

	public void setBpmStatus(java.lang.String bpmStatus) {
		this.bpmStatus = bpmStatus;
	}

    @Column(name = "CREATE_BY")
    public java.lang.String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_NAME")
    public java.lang.String getCreateName() {
        return createName;
    }

    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_DATE")
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_BY")
    public java.lang.String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_NAME")
    public java.lang.String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "UPDATE_DATE")
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name = "PLAN_STATUS")
    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
    
}
