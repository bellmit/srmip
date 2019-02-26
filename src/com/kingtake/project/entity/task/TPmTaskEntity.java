package com.kingtake.project.entity.task;

import java.util.Date;
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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**   
 * @Title: Entity
 * @Description: 任务管理
 * @author onlineGenerator
 * @date 2015-07-21 14:04:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_task", schema = "")
@SuppressWarnings("serial")
@LogEntity("任务管理")
public class TPmTaskEntity implements java.io.Serializable {
	/**主键*/
    @FieldDesc("主键")
	private java.lang.String id;
	/**项目基_主键*/
    @FieldDesc("项目基_主键")
	private java.lang.String tpId;
	/**项目实体*/
    @FieldDesc("项目实体")
    private TPmProjectEntity project;
	/**标题*/
    @FieldDesc("标题")
	private java.lang.String taskTitle;
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
    private List<TSFilesEntity> attachments;
	
	/**
	 * 关联附件
	 */
	private String attachmentCode;
	
    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 完成人id
     */
    private String finishUserId;

    /**
     * 完成用户名
     */
    private String finishUserName;

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
	 *@return: java.lang.String  项目基_主键
	 */
	
	@Transient
	public java.lang.String getTpId(){
		return this.tpId;
	}
	
	public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    @ManyToOne
	@JsonIgnore(true)
	@JoinColumn(name="T_P_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	
	@Column(name ="TASK_TITLE",nullable=true,length=200)
	public java.lang.String getTaskTitle(){
		return this.taskTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTaskTitle(java.lang.String taskTitle){
		this.taskTitle = taskTitle;
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
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }
	
    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Column(name = "finish_time")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "finish_user_id")
    public String getFinishUserId() {
        return finishUserId;
    }

    public void setFinishUserId(String finishUserId) {
        this.finishUserId = finishUserId;
    }

    @Column(name = "finish_user_name")
    public String getFinishUserName() {
        return finishUserName;
    }

    public void setFinishUserName(String finishUserName) {
        this.finishUserName = finishUserName;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    
}
