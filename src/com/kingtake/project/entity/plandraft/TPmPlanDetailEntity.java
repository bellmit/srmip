package com.kingtake.project.entity.plandraft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 计划草案明细
 * @author onlineGenerator
 * @date 2016-03-30 10:46:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_plan_detail", schema = "")
@SuppressWarnings("serial")
public class TPmPlanDetailEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**计划草案主键*/
	private java.lang.String planId;
	/**申报信息主键*/
	private java.lang.String declareId;
    /**
     * 项目主键
     */
    private String projectId;
	/**申报类型*/
	private java.lang.String declareType;
	/**审批状态*/
	private java.lang.String auditStatus;
	
	    /**
     * 修改意见
     */
    private String msgText;

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
	 *@return: java.lang.String  计划草案主键
	 */
	@Column(name ="PLAN_ID",nullable=true,length=32)
	public java.lang.String getPlanId(){
		return this.planId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划草案主键
	 */
	public void setPlanId(java.lang.String planId){
		this.planId = planId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申报信息主键
	 */
	@Column(name ="DECLARE_ID",nullable=true,length=32)
	public java.lang.String getDeclareId(){
		return this.declareId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申报信息主键
	 */
	public void setDeclareId(java.lang.String declareId){
		this.declareId = declareId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申报类型
	 */
	@Column(name ="DECLARE_TYPE",nullable=true,length=1)
	public java.lang.String getDeclareType(){
		return this.declareType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申报类型
	 */
	public void setDeclareType(java.lang.String declareType){
		this.declareType = declareType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审批状态
	 */
	@Column(name ="AUDIT_STATUS",nullable=true,length=1)
	public java.lang.String getAuditStatus(){
		return this.auditStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审批状态
	 */
	public void setAuditStatus(java.lang.String auditStatus){
		this.auditStatus = auditStatus;
	}

    @Column(name = "project_id")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Column(name = "MSG_TEXT")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

}
