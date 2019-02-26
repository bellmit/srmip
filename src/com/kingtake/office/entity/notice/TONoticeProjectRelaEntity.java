package com.kingtake.office.entity.notice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 通知公告关联项目子表
 * @author onlineGenerator
 * @date 2015-08-16 10:48:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_notice_project_rela", schema = "")
@SuppressWarnings("serial")
public class TONoticeProjectRelaEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**通知公告_主键*/
	@Excel(name="通知公告_主键")
	private java.lang.String noticeId;
	/**项目主键*/
	@Excel(name="项目主键")
	private java.lang.String projectId;
	/**项目名称*/
	@Excel(name="项目名称")
	private java.lang.String projectName;
	
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
	 *@return: java.lang.String  通知公告_主键
	 */
	@Column(name ="NOTICE_ID",nullable=true,length=32)
	public java.lang.String getNoticeId(){
		return this.noticeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知公告_主键
	 */
	public void setNoticeId(java.lang.String noticeId){
		this.noticeId = noticeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目主键
	 */
	@Column(name ="PROJECT_ID",nullable=true,length=32)
	public java.lang.String getProjectId(){
		return this.projectId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目主键
	 */
	public void setProjectId(java.lang.String projectId){
		this.projectId = projectId;
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
}
