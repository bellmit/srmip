package com.kingtake.project.entity.learning;

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**   
 * @Title: Entity
 * @Description: 学术著作信息表
 * @author onlineGenerator
 * @date 2015-12-03 11:15:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_learning_book", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
public class TBLearningBookEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**所属项目*/
    private TPmProjectEntity project;
    /**
     * 所属项目名称
     */
    @Excel(name = "所属项目名称")
    private String projectName;
	/**著作名*/
	@Excel(name="著作名")
	private java.lang.String bookName;
    /** 第一作者id */
    private TSUser authorFirst;
    /** 第一作者名称 */
    @Excel(name = "第一作者")
    private java.lang.String authorFirstName;
	/**第二作者*/
	@Excel(name="第二作者")
	private java.lang.String authorSecond;
	/**第三作者*/
	@Excel(name="第三作者")
	private java.lang.String authorThird;
	/**其他作者*/
	@Excel(name="其他作者")
	private java.lang.String authorOther;
	/**第一作者所属院*/
	@Excel(name="第一作者所属院")
    private TSDepart authorFirstDepart;
	/**具体单位*/
	@Excel(name="具体单位")
	private java.lang.String concreteDepart;
	/**关键词*/
	@Excel(name="关键词")
	private java.lang.String keyword;
	/**中图分类号*/
	@Excel(name="中图分类号")
	private java.lang.String classNum;
	/**密级*/
	@Excel(name="密级")
	private java.lang.String secretCode;
	/**类型*/
	@Excel(name="类型")
	private java.lang.String bookType;
	/**出版社*/
	@Excel(name="出版社")
	private java.lang.String publisher;
	/**ISBN号*/
	@Excel(name="ISBN号")
	private java.lang.String isbnNum;
	/**出版年份*/
	@Excel(name="出版年份")
	private java.lang.String publishYear;
    /**
     * 关联保密
     */
    @Excel(name = "关联保密证明编号")
    private String secretNumber;
	/**内容摘要*/
	@Excel(name="内容摘要")
	private java.lang.String summary;
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

    @JoinColumn(name = "PROJECT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @Transient
    public String getProjectName() {
        if (this.project != null) {
            return this.project.getProjectName();
        }
        return "";
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "SECRET_NUMBER")
    public String getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(String secretNumber) {
        this.secretNumber = secretNumber;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 著作名
     */
	@Column(name ="BOOK_NAME",nullable=true,length=300)
	public java.lang.String getBookName(){
		return this.bookName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  著作名
	 */
	public void setBookName(java.lang.String bookName){
		this.bookName = bookName;
	}

    @JoinColumn(name = "AUTHOR_FIRST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public TSUser getAuthorFirst() {
        return authorFirst;
    }

    public void setAuthorFirst(TSUser authorFirst) {
        this.authorFirst = authorFirst;
    }

    @Column(name = "AUTHOR_FIRST_NAME")
    public java.lang.String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(java.lang.String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 第二作者
     */
	@Column(name ="AUTHOR_SECOND",nullable=true,length=36)
	public java.lang.String getAuthorSecond(){
		return this.authorSecond;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  第二作者
	 */
	public void setAuthorSecond(java.lang.String authorSecond){
		this.authorSecond = authorSecond;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  第三作者
	 */
	@Column(name ="AUTHOR_THIRD",nullable=true,length=36)
	public java.lang.String getAuthorThird(){
		return this.authorThird;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  第三作者
	 */
	public void setAuthorThird(java.lang.String authorThird){
		this.authorThird = authorThird;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  其他作者
	 */
	@Column(name ="AUTHOR_OTHER",nullable=true,length=120)
	public java.lang.String getAuthorOther(){
		return this.authorOther;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  其他作者
	 */
	public void setAuthorOther(java.lang.String authorOther){
		this.authorOther = authorOther;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  第一作者所属院
	 */
    @JoinColumn(name = "AUTHOR_FIRST_DEPART")
    @ManyToOne(fetch = FetchType.LAZY)
    public TSDepart getAuthorFirstDepart() {
		return this.authorFirstDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  第一作者所属院
	 */
    public void setAuthorFirstDepart(TSDepart authorFirstDepart) {
		this.authorFirstDepart = authorFirstDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  具体单位
	 */
	@Column(name ="CONCRETE_DEPART",nullable=true,length=60)
	public java.lang.String getConcreteDepart(){
		return this.concreteDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  具体单位
	 */
	public void setConcreteDepart(java.lang.String concreteDepart){
		this.concreteDepart = concreteDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关键词
	 */
	@Column(name ="KEYWORD",nullable=true,length=200)
	public java.lang.String getKeyword(){
		return this.keyword;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关键词
	 */
	public void setKeyword(java.lang.String keyword){
		this.keyword = keyword;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  中图分类号
	 */
	@Column(name ="CLASS_NUM",nullable=true,length=30)
	public java.lang.String getClassNum(){
		return this.classNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  中图分类号
	 */
	public void setClassNum(java.lang.String classNum){
		this.classNum = classNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  密级
	 */
	@Column(name ="SECRET_CODE",nullable=true,length=1)
	public java.lang.String getSecretCode(){
		return this.secretCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  密级
	 */
	public void setSecretCode(java.lang.String secretCode){
		this.secretCode = secretCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	@Column(name ="BOOK_TYPE",nullable=true,length=2)
	public java.lang.String getBookType(){
		return this.bookType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setBookType(java.lang.String bookType){
		this.bookType = bookType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出版社
	 */
	@Column(name ="PUBLISHER",nullable=true,length=60)
	public java.lang.String getPublisher(){
		return this.publisher;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出版社
	 */
	public void setPublisher(java.lang.String publisher){
		this.publisher = publisher;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ISBN号
	 */
	@Column(name ="ISBN_NUM",nullable=true,length=20)
	public java.lang.String getIsbnNum(){
		return this.isbnNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ISBN号
	 */
	public void setIsbnNum(java.lang.String isbnNum){
		this.isbnNum = isbnNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出版年份
	 */
	@Column(name ="PUBLISH_YEAR",nullable=true,length=4)
	public java.lang.String getPublishYear(){
		return this.publishYear;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出版年份
	 */
	public void setPublishYear(java.lang.String publishYear){
		this.publishYear = publishYear;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容摘要
	 */
	@Column(name ="SUMMARY",nullable=true,length=800)
	public java.lang.String getSummary(){
		return this.summary;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容摘要
	 */
	public void setSummary(java.lang.String summary){
		this.summary = summary;
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
