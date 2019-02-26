package com.kingtake.project.entity.learning;

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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.util.ConvertDicUtil;

/**   
 * @Title: Entity
 * @Description: 学术团体信息表
 * @author onlineGenerator
 * @date 2015-12-03 11:14:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_learning_team", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
public class TBLearningTeamEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**团体名称*/
	@Excel(name="团体名称")
	private java.lang.String teamName;
	/**团体业务主管单位*/
	@Excel(name="团体业务主管单位")
	private java.lang.String manageDepart;
	/**兼任领导职务名称*/
	@Excel(name="兼任领导职务名称")
	private java.lang.String leaderJob;
	/**批准单位*/
	@Excel(name="批准单位")
	private java.lang.String approveUnit;
	/**社团类别*/
    @Excel(name = "社团类别", isExportConvert = true)
	private java.lang.String collegeType;
	/**是否在民政部门注册*/
    @Excel(name = "是否在民政部门注册", replace = { "是_1", "否_0" })
	private java.lang.String isRegister;
	/**参加团体时间*/
    @Excel(name = "参加团体时间", format = "yyyy-MM-dd HH:mm:ss")
    private String collegeTime;
    /**
     * 流程实例id
     */
    private String processInstId;

    /**
     * 流程审批状态
     */
    private String bpmStatus;
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
	
    /**
     * 上传附件
     */
    private List<TSFilesEntity> uploadFiles;
    
    /**
     * 附件编码
     */
    private String attachmentCode;

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
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 团体名称
     */
	@Column(name ="TEAM_NAME",nullable=true,length=120)
	public java.lang.String getTeamName(){
		return this.teamName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  团体名称
	 */
	public void setTeamName(java.lang.String teamName){
		this.teamName = teamName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  团体业务主管单位
	 */
	@Column(name ="MANAGE_DEPART",nullable=true,length=120)
	public java.lang.String getManageDepart(){
		return this.manageDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  团体业务主管单位
	 */
	public void setManageDepart(java.lang.String manageDepart){
		this.manageDepart = manageDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  兼任领导职务名称
	 */
	@Column(name ="LEADER_JOB",nullable=true,length=30)
	public java.lang.String getLeaderJob(){
		return this.leaderJob;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  兼任领导职务名称
	 */
	public void setLeaderJob(java.lang.String leaderJob){
		this.leaderJob = leaderJob;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批准单位
	 */
	@Column(name ="APPROVE_UNIT",nullable=true,length=60)
	public java.lang.String getApproveUnit(){
		return this.approveUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批准单位
	 */
	public void setApproveUnit(java.lang.String approveUnit){
		this.approveUnit = approveUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  社团类别
	 */
	@Column(name ="COLLEGE_TYPE",nullable=true,length=2)
	public java.lang.String getCollegeType(){
		return this.collegeType;
	}

    public String convertGetCollegeType() {
        return ConvertDicUtil.getConvertDic("1", "STLB", this.collegeType);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  社团类别
	 */
	public void setCollegeType(java.lang.String collegeType){
		this.collegeType = collegeType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否在民政部门注册
	 */
	@Column(name ="IS_REGISTER",nullable=true,length=1)
	public java.lang.String getIsRegister(){
		return this.isRegister;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否在民政部门注册
	 */
	public void setIsRegister(java.lang.String isRegister){
		this.isRegister = isRegister;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  参加团体时间
	 */
    @Column(name = "COLLEGE_TIME")
    public String getCollegeTime() {
		return this.collegeTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  参加团体时间
	 */
    public void setCollegeTime(String collegeTime) {
		this.collegeTime = collegeTime;
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
    public List<TSFilesEntity> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<TSFilesEntity> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    @Column(name = "bpm_status")
    public String getBpmStatus() {
        return bpmStatus;
    }

    public void setBpmStatus(String bpmStatus) {
        this.bpmStatus = bpmStatus;
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

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
}
