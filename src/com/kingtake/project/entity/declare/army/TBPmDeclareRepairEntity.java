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
 * @Description: 维修科研项目申报书
 * @author onlineGenerator
 * @date 2015-09-22 11:40:37
 * @version V1.0   
 *
 */
@Entity
@LogEntity("维修科研项目申报书")
@Table(name = "t_b_pm_declare_repair", schema = "")
@SuppressWarnings("serial")
public class TBPmDeclareRepairEntity implements java.io.Serializable {
	/**军事需求*/
	@Excel(name="军事需求")
	@FieldDesc("军事需求")
	private java.lang.String militaryDemand;
	/**应用前景*/
	@Excel(name="应用前景")
	@FieldDesc("应用前景")
	private java.lang.String applicationProspect;
	/**技术途径/研究方案*/
	@Excel(name="技术途径/研究方案")
	@FieldDesc("技术途径/研究方案")
	private java.lang.String technicalResearch;
	/**先进程度*/
	@Excel(name="先进程度")
	@FieldDesc("先进程度")
	private java.lang.String advancedDegree;
	/**技术指标*/
	@Excel(name="技术指标")
	@FieldDesc("技术指标")
	private java.lang.String technicalIndex;
	/**进度安排*/
	@Excel(name="进度安排")
	@FieldDesc("进度安排")
	private java.lang.String scheduling;
	/**风险评估*/
	@Excel(name="风险评估")
	@FieldDesc("风险评估")
	private java.lang.String riskAssessment;
	/**成果形式*/
	@Excel(name="成果形式")
	@FieldDesc("成果形式")
	private java.lang.String resultForm;
	/**承研能力/设施条件*/
	@Excel(name="承研能力/设施条件")
	@FieldDesc("承研能力/设施条件")
	private java.lang.String bearingFacility;
	/**以往承担装备维修科学研究与改革项目的推广应用情况*/
	@Excel(name="以往承担装备维修科学研究与改革项目的推广应用情况")
	@FieldDesc("以往承担装备维修科学研究与改革项目的推广应用情况")
	private java.lang.String pastSituation;
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
	/**项目类别(科研代码集WXSBSXMLB：理论研究/技术研究/手段改进/固定科目)*/
	@Excel(name="项目类别(科研代码集WXSBSXMLB：理论研究/技术研究/手段改进/固定科目)")
	@FieldDesc("项目类别(科研代码集WXSBSXMLB：理论研究/技术研究/手段改进/固定科目)")
	private java.lang.String projectCategory;
	/**填报单位id*/
	@Excel(name="填报单位id")
	@FieldDesc("填报单位id")
	private java.lang.String applyDeptId;
	/**填报单位*/
	@Excel(name="填报单位")
	@FieldDesc("填报单位")
	private java.lang.String applyDeptName;
	/**填报日期*/
	@Excel(name="填报日期")
	@FieldDesc("填报日期")
	private java.util.Date applyTime;
	/**项目组负责人id*/
	@Excel(name="项目组负责人id")
	@FieldDesc("项目组负责人id")
	private java.lang.String projectManagerId;
	/**项目组负责人*/
	@Excel(name="项目组负责人")
	@FieldDesc("项目组负责人")
	private java.lang.String projectManagerName;
	/**项目简介(150字以内)*/
	@Excel(name="项目简介(150字以内)")
	@FieldDesc("项目简介(150字以内)")
	private java.lang.String projectIntroduce;
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
     * 计划状态，1 已通过 ，2 已驳回 ，3 重新提交
     */
    private String planStatus;

    /**
     * 流程实例id
     */
    private String processInstId;
    
    /**
     * 附件管理id
     */
    private String attachmentCode;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  军事需求
	 */
	@Column(name ="MILITARY_DEMAND",nullable=true,length=400)
	public java.lang.String getMilitaryDemand(){
		return this.militaryDemand;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  军事需求
	 */
	public void setMilitaryDemand(java.lang.String militaryDemand){
		this.militaryDemand = militaryDemand;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  应用前景
	 */
	@Column(name ="APPLICATION_PROSPECT",nullable=true,length=400)
	public java.lang.String getApplicationProspect(){
		return this.applicationProspect;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  应用前景
	 */
	public void setApplicationProspect(java.lang.String applicationProspect){
		this.applicationProspect = applicationProspect;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  技术途径/研究方案
	 */
	@Column(name ="TECHNICAL_RESEARCH",nullable=true,length=400)
	public java.lang.String getTechnicalResearch(){
		return this.technicalResearch;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  技术途径/研究方案
	 */
	public void setTechnicalResearch(java.lang.String technicalResearch){
		this.technicalResearch = technicalResearch;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  先进程度
	 */
	@Column(name ="ADVANCED_DEGREE",nullable=true,length=400)
	public java.lang.String getAdvancedDegree(){
		return this.advancedDegree;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  先进程度
	 */
	public void setAdvancedDegree(java.lang.String advancedDegree){
		this.advancedDegree = advancedDegree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  技术指标
	 */
	@Column(name ="TECHNICAL_INDEX",nullable=true,length=400)
	public java.lang.String getTechnicalIndex(){
		return this.technicalIndex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  技术指标
	 */
	public void setTechnicalIndex(java.lang.String technicalIndex){
		this.technicalIndex = technicalIndex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  进度安排
	 */
	@Column(name ="SCHEDULING",nullable=true,length=400)
	public java.lang.String getScheduling(){
		return this.scheduling;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  进度安排
	 */
	public void setScheduling(java.lang.String scheduling){
		this.scheduling = scheduling;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风险评估
	 */
	@Column(name ="RISK_ASSESSMENT",nullable=true,length=400)
	public java.lang.String getRiskAssessment(){
		return this.riskAssessment;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风险评估
	 */
	public void setRiskAssessment(java.lang.String riskAssessment){
		this.riskAssessment = riskAssessment;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成果形式
	 */
	@Column(name ="RESULT_FORM",nullable=true,length=400)
	public java.lang.String getResultForm(){
		return this.resultForm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  成果形式
	 */
	public void setResultForm(java.lang.String resultForm){
		this.resultForm = resultForm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承研能力/设施条件
	 */
	@Column(name ="BEARING_FACILITY",nullable=true,length=400)
	public java.lang.String getBearingFacility(){
		return this.bearingFacility;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承研能力/设施条件
	 */
	public void setBearingFacility(java.lang.String bearingFacility){
		this.bearingFacility = bearingFacility;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  以往承担装备维修科学研究与改革项目的推广应用情况
	 */
	@Column(name ="PAST_SITUATION",nullable=true,length=400)
	public java.lang.String getPastSituation(){
		return this.pastSituation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  以往承担装备维修科学研究与改革项目的推广应用情况
	 */
	public void setPastSituation(java.lang.String pastSituation){
		this.pastSituation = pastSituation;
	}
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
	 *@return: java.lang.String  项目类别(科研代码集WXSBSXMLB：理论研究/技术研究/手段改进/固定科目)
	 */
	@Column(name ="PROJECT_CATEGORY",nullable=true,length=5)
	public java.lang.String getProjectCategory(){
		return this.projectCategory;
	}

	

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目类别(科研代码集WXSBSXMLB：理论研究/技术研究/手段改进/固定科目)
	 */
	public void setProjectCategory(java.lang.String projectCategory){
		this.projectCategory = projectCategory;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  填报单位id
	 */
	@Column(name ="APPLY_DEPT_ID",nullable=true,length=32)
	public java.lang.String getApplyDeptId(){
		return this.applyDeptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  填报单位id
	 */
	public void setApplyDeptId(java.lang.String applyDeptId){
		this.applyDeptId = applyDeptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  填报单位
	 */
	@Column(name ="APPLY_DEPT_NAME",nullable=true,length=100)
	public java.lang.String getApplyDeptName(){
		return this.applyDeptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  填报单位
	 */
	public void setApplyDeptName(java.lang.String applyDeptName){
		this.applyDeptName = applyDeptName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  填报日期
	 */
	@Column(name ="APPLY_TIME",nullable=true)
	public java.util.Date getApplyTime(){
		return this.applyTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  填报日期
	 */
	public void setApplyTime(java.util.Date applyTime){
		this.applyTime = applyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目组负责人id
	 */
	@Column(name ="PROJECT_MANAGER_ID",nullable=true,length=32)
	public java.lang.String getProjectManagerId(){
		return this.projectManagerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目组负责人id
	 */
	public void setProjectManagerId(java.lang.String projectManagerId){
		this.projectManagerId = projectManagerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目组负责人
	 */
	@Column(name ="PROJECT_MANAGER_NAME",nullable=true,length=50)
	public java.lang.String getProjectManagerName(){
		return this.projectManagerName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目组负责人
	 */
	public void setProjectManagerName(java.lang.String projectManagerName){
		this.projectManagerName = projectManagerName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目简介(150字以内)
	 */
	@Column(name ="PROJECT_INTRODUCE",nullable=true,length=300)
	public java.lang.String getProjectIntroduce(){
		return this.projectIntroduce;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目简介(150字以内)
	 */
	public void setProjectIntroduce(java.lang.String projectIntroduce){
		this.projectIntroduce = projectIntroduce;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流程流转状态
	 */
	@Column(name ="BPM_STATUS",nullable=true,length=1)
	public java.lang.String getBpmStatus(){
		return this.bpmStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程流转状态
	 */
	public void setBpmStatus(java.lang.String bpmStatus){
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
