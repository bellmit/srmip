package com.kingtake.project.entity.approval;

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

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**   
 * @Title: Entity
 * @Description: 论证报告
 * @author onlineGenerator
 * @date 2015-07-12 15:27:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_project_approval")
@SuppressWarnings("serial")
@LogEntity("立项论证")
public class TPmProjectApprovalEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**项目基_主键*/
    private java.lang.String tpId;
    /** 项目名称 */
    @Excel(name = "所属项目")
    @FieldDesc("所属项目")
    private java.lang.String projectName;
    /**
     * 项目实体
     */
    private TPmProjectEntity project;
	/**建议等级*/
	@Excel(name="建议等级")
    @FieldDesc("建议等级")
	private java.lang.String suggestGrade;
	/**建议单位*/
	@Excel(name="建议单位")
    @FieldDesc("建议单位")
	private java.lang.String suggestUnit;
	/**研究时限*/
	@Excel(name="研究时限")
    @FieldDesc("研究时限")
	private java.lang.String studyTime;
	/**项目来源*/
	@Excel(name="项目来源")
    @FieldDesc("项目来源")
	private java.lang.String projectSrc;
	/**研究的必要性*/
	@Excel(name="研究的必要性")
    @FieldDesc("研究的必要性")
	private java.lang.String studyNecessity;
	/**国内外军内外有关情况分析*/
	@Excel(name="国内外军内外有关情况分析")
    @FieldDesc("国内外军内外有关情况分析")
	private java.lang.String situationAnalysis;
	/**主要研究内容*/
	@Excel(name="主要研究内容")
    @FieldDesc("主要研究内容")
	private java.lang.String studyContent;
	/**研究进度与成果形式*/
	@Excel(name="研究进度与成果形式")
    @FieldDesc("研究进度与成果形式")
	private java.lang.String scheduleAndAchieve;
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
    private List<TSFilesEntity> attachments;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=10)
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

	@Transient
    public java.lang.String getTpId() {
        return tpId;
    }

    public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="T_P_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 建议等级
     */
	@Column(name ="SUGGEST_GRADE",nullable=true,length=20)
	public java.lang.String getSuggestGrade(){
		return this.suggestGrade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建议等级
	 */
	public void setSuggestGrade(java.lang.String suggestGrade){
		this.suggestGrade = suggestGrade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建议单位
	 */
	@Column(name ="SUGGEST_UNIT",nullable=true,length=60)
	public java.lang.String getSuggestUnit(){
		return this.suggestUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建议单位
	 */
	public void setSuggestUnit(java.lang.String suggestUnit){
		this.suggestUnit = suggestUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  研究时限
	 */
	@Column(name ="STUDY_TIME",nullable=true,length=50)
	public java.lang.String getStudyTime(){
		return this.studyTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  研究时限
	 */
	public void setStudyTime(java.lang.String studyTime){
		this.studyTime = studyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目来源
	 */
	@Column(name ="PROJECT_SRC",nullable=true,length=100)
	public java.lang.String getProjectSrc(){
		return this.projectSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目来源
	 */
	public void setProjectSrc(java.lang.String projectSrc){
		this.projectSrc = projectSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  研究的必要性
	 */
	@Column(name ="STUDY_NECESSITY",nullable=true,length=1000)
	public java.lang.String getStudyNecessity(){
		return this.studyNecessity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  研究的必要性
	 */
	public void setStudyNecessity(java.lang.String studyNecessity){
		this.studyNecessity = studyNecessity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国内外军内外有关情况分析
	 */
	@Column(name ="SITUATION_ANALYSIS",nullable=true,length=2000)
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
	@Column(name ="STUDY_CONTENT",nullable=true,length=2000)
	public java.lang.String getStudyContent(){
		return this.studyContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主要研究内容
	 */
	public void setStudyContent(java.lang.String studyContent){
		this.studyContent = studyContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  研究进度与成果形式
	 */
	@Column(name ="SCHEDULE_AND_ACHIEVE",nullable=true,length=2000)
	public java.lang.String getScheduleAndAchieve(){
		return this.scheduleAndAchieve;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  研究进度与成果形式
	 */
	public void setScheduleAndAchieve(java.lang.String scheduleAndAchieve){
		this.scheduleAndAchieve = scheduleAndAchieve;
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
    public java.lang.String getProjectName() {
        if (this.getProject() != null) {
            return this.project.getProjectName();
        }
        return "";
    }

    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bid")
    @OrderBy("createdate")
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

}
