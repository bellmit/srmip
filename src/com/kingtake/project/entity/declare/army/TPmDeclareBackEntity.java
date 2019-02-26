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
 * @Description: 后勤科研项目申报书
 * @author onlineGenerator
 * @date 2015-09-22 11:41:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_pm_declare_back", schema = "")
@LogEntity("后勤科研项目申报书")
@SuppressWarnings("serial")
public class TPmDeclareBackEntity implements java.io.Serializable {
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
	@Excel(name="项目类型id")
	@FieldDesc("项目类型id")
	private TBProjectTypeEntity projectType;
	/**分管部门*/
	@Excel(name="分管部门")
	@FieldDesc("分管部门")
	private java.lang.String manageDepart;
	/**建议单位ID*/
	@Excel(name="建议单位ID")
	@FieldDesc("建议单位ID")
	private java.lang.String developerDeptId;
	/**是否重大专项（0：否；1：是）*/
	@Excel(name="是否重大专项（0：否；1：是）")
	@FieldDesc("是否重大专项（0：否；1：是）")
	private java.lang.String greatSpecialFlag;
	/**建议单位*/
	@Excel(name="建议单位")
	@FieldDesc("建议单位")
	private java.lang.String developerDeptName;
	/**项目开始时间*/
	@Excel(name="项目开始时间")
	@FieldDesc("项目开始时间")
	private java.util.Date projectBeginDate;
	/**项目结束时间*/
	@Excel(name="项目结束时间")
	@FieldDesc("项目结束时间")
	private java.util.Date projectEndDate;
	/**项目来源*/
	@Excel(name="项目来源")
	@FieldDesc("项目来源")
	private java.lang.String projectSource;
	/**研究的必要性*/
	@Excel(name="研究的必要性")
	@FieldDesc("研究的必要性")
	private java.lang.String researchNecessity;
	/**国内外军内外有关情况分析*/
	@Excel(name="国内外军内外有关情况分析")
	@FieldDesc("国内外军内外有关情况分析")
	private java.lang.String situationAnalysis;
	/**主要研究内容*/
	@Excel(name="主要研究内容")
	@FieldDesc("主要研究内容")
	private java.lang.String mainResearchContent;
	/**研究进度*/
	@Excel(name="研究进度")
	@FieldDesc("研究进度")
	private java.lang.String researchSchedule;
	/**成果形式*/
	@Excel(name="成果形式")
	@FieldDesc("成果形式")
	private java.lang.String resultForm;
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
	 *@return: java.lang.String  分管部门
	 */
	@Column(name ="MANAGE_DEPART",nullable=true,length=60)
	public java.lang.String getManageDepart(){
		return this.manageDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分管部门
	 */
	public void setManageDepart(java.lang.String manageDepart){
		this.manageDepart = manageDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建议单位ID
	 */
	@Column(name ="DEVELOPER_DEPT_ID",nullable=true,length=32)
	public java.lang.String getDeveloperDeptId(){
		return this.developerDeptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建议单位ID
	 */
	public void setDeveloperDeptId(java.lang.String developerDeptId){
		this.developerDeptId = developerDeptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否重大专项（0：否；1：是）
	 */
	@Column(name ="GREAT_SPECIAL_FLAG",nullable=true,length=1)
	public java.lang.String getGreatSpecialFlag(){
		return this.greatSpecialFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否重大专项（0：否；1：是）
	 */
	public void setGreatSpecialFlag(java.lang.String greatSpecialFlag){
		this.greatSpecialFlag = greatSpecialFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建议单位
	 */
	@Column(name ="DEVELOPER_DEPT_NAME",nullable=true,length=100)
	public java.lang.String getDeveloperDeptName(){
		return this.developerDeptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建议单位
	 */
	public void setDeveloperDeptName(java.lang.String developerDeptName){
		this.developerDeptName = developerDeptName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  项目开始时间
	 */
	@Column(name ="PROJECT_BEGIN_DATE",nullable=true)
	public java.util.Date getProjectBeginDate(){
		return this.projectBeginDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  项目开始时间
	 */
	public void setProjectBeginDate(java.util.Date projectBeginDate){
		this.projectBeginDate = projectBeginDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  项目结束时间
	 */
	@Column(name ="PROJECT_END_DATE",nullable=true)
	public java.util.Date getProjectEndDate(){
		return this.projectEndDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  项目结束时间
	 */
	public void setProjectEndDate(java.util.Date projectEndDate){
		this.projectEndDate = projectEndDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目来源
	 */
	@Column(name ="PROJECT_SOURCE",nullable=true,length=100)
	public java.lang.String getProjectSource(){
		return this.projectSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目来源
	 */
	public void setProjectSource(java.lang.String projectSource){
		this.projectSource = projectSource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  研究的必要性
	 */
	@Column(name ="RESEARCH_NECESSITY",nullable=true,length=400)
	public java.lang.String getResearchNecessity(){
		return this.researchNecessity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  研究的必要性
	 */
	public void setResearchNecessity(java.lang.String researchNecessity){
		this.researchNecessity = researchNecessity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国内外军内外有关情况分析
	 */
	@Column(name ="SITUATION_ANALYSIS",nullable=true,length=400)
	public java.lang.String getSituationAnalysis(){
		return this.situationAnalysis;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国内外军内外有关情况分析
	 */
	public void setSituationAnalysis(java.lang.String situationAnalysis){
		this.situationAnalysis = situationAnalysis;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主要研究内容
	 */
	@Column(name ="MAIN_RESEARCH_CONTENT",nullable=true,length=400)
	public java.lang.String getMainResearchContent(){
		return this.mainResearchContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要研究内容
	 */
	public void setMainResearchContent(java.lang.String mainResearchContent){
		this.mainResearchContent = mainResearchContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  研究进度
	 */
	@Column(name ="RESEARCH_SCHEDULE",nullable=true,length=400)
	public java.lang.String getResearchSchedule(){
		return this.researchSchedule;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  研究进度
	 */
	public void setResearchSchedule(java.lang.String researchSchedule){
		this.researchSchedule = researchSchedule;
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
	
	@Column(name ="BPM_STATUS",nullable=true,length=1)
	public java.lang.String getBpmStatus(){
		return this.bpmStatus;
	}

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
