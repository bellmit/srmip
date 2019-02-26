package com.kingtake.expert.entity.reviewmain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;

/**   
 * @Title: Entity
 * @Description: 专家评审主表
 * @author onlineGenerator
 * @date 2015-08-18 16:50:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_er_review_main", schema = "")
@SuppressWarnings("serial")
@LogEntity("专家评审主表")
public class TErReviewMainEntity implements java.io.Serializable {
	/**主键*/
    @FieldDesc("主键")
	private java.lang.String id;
    @FieldDesc("评审过程")
    private java.lang.String reviewProcess;
	/**依据文号*/
    @FieldDesc("依据文号")
	@Excel(name="依据文号")
	private java.lang.String accordingNum;
	/**呈批文件号*/
    @FieldDesc("呈批文件号")
	@Excel(name="呈批文件号")
	private java.lang.String docNum;
	/**评审标题*/
    @FieldDesc("评审标题")
	@Excel(name="评审标题")
	private java.lang.String reviewTitle;
	/**评审内容*/
    @FieldDesc("评审内容")
	@Excel(name="评审内容")
	private java.lang.String reviewContent;
	/**计划评审时间*/
    @FieldDesc("计划评审开始时间")
	@Excel(name="计划评审开始时间")
	private java.util.Date planReviewDateBegin;
    @FieldDesc("计划评审结束时间")
	@Excel(name="计划评审结束时间")
	private java.util.Date planReviewDateEnd;
    /**专家评审时间*/
    @FieldDesc("专家评审开始时间")
	@Excel(name="专家评审开始时间")
	private java.util.Date expertReviewDateBegin;
    @FieldDesc("专家评审结束时间")
	@Excel(name="专家评审结束时间")
	private java.util.Date expertReviewDateEnd;
    /**计划会审地点*/
    @FieldDesc("计划会审地点")
	@Excel(name="计划会审地点")
	private java.lang.String planReviewAddress;
	/**评审方式*/
    @FieldDesc("评审方式")
	@Excel(name="评审方式")
	private java.lang.String reviewMode;

    /**
     * 评审状态,0 待提交，1 已提交，2 已完成
     */
    private String reviewStatus;
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
	
    private List<TErReviewProjectEntity> reviewProjectList;
    
    /**
     * 附件编码
     */
    private String attachmentCode;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;

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
	 *@return: java.lang.String  依据文号
	 */
	@Column(name ="ACCORDING_NUM",nullable=true,length=24)
	public java.lang.String getAccordingNum(){
		return this.accordingNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  依据文号
	 */
	public void setAccordingNum(java.lang.String accordingNum){
		this.accordingNum = accordingNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  呈批文件号
	 */
	@Column(name ="DOC_NUM",nullable=true,length=24)
	public java.lang.String getDocNum(){
		return this.docNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  呈批文件号
	 */
	public void setDocNum(java.lang.String docNum){
		this.docNum = docNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评审标题
	 */
	@Column(name ="REVIEW_TITLE",nullable=true,length=200)
	public java.lang.String getReviewTitle(){
		return this.reviewTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评审标题
	 */
	public void setReviewTitle(java.lang.String reviewTitle){
		this.reviewTitle = reviewTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评审内容
	 */
	@Column(name ="REVIEW_CONTENT",nullable=true,length=800)
	public java.lang.String getReviewContent(){
		return this.reviewContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评审内容
	 */
	public void setReviewContent(java.lang.String reviewContent){
		this.reviewContent = reviewContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  计划评审开始时间
	 */
	@Column(name ="PLAN_REVIEW_DATE_BEGIN",nullable=true)
	public java.util.Date getPlanReviewDateBegin(){
		return this.planReviewDateBegin;
	}

    @Transient
    public String getPlanReviewDateBeginStr() {
        String planReviewDateBeginStr = "";
        if (this.planReviewDateBegin != null) {
        	planReviewDateBeginStr = DateUtils.formatDate(this.planReviewDateBegin, "yyyy-MM-dd");
        }
        return planReviewDateBeginStr;
    }

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  计划评审开始时间
	 */
	public void setPlanReviewDateBegin(java.util.Date planReviewDateBegin){
		this.planReviewDateBegin = planReviewDateBegin;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  计划评审结束时间
	 */
	@Column(name ="PLAN_REVIEW_DATE_END",nullable=true)
	public java.util.Date getPlanReviewDateEnd(){
		return this.planReviewDateEnd;
	}

    @Transient
    public String getPlanReviewDateEndStr() {
        String planReviewDateEndStr = "";
        if (this.planReviewDateEnd != null) {
        	planReviewDateEndStr = DateUtils.formatDate(this.planReviewDateEnd, "yyyy-MM-dd");
        }
        return planReviewDateEndStr;
    }

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  计划评审结束时间
	 */
	public void setPlanReviewDateEnd(java.util.Date planReviewDateEnd){
		this.planReviewDateEnd = planReviewDateEnd;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  专家评审开始时间
	 */
	@Column(name ="EXPERT_REVIEW_DATE_BEGIN",nullable=true)
	public java.util.Date getExpertReviewDateBegin(){
		return this.expertReviewDateBegin;
	}

    @Transient
    public String getExpertReviewDateBeginStr() {
        String expertReviewDateBeginStr = "";
        if (this.expertReviewDateBegin != null) {
        	expertReviewDateBeginStr = DateUtils.formatDate(this.expertReviewDateBegin, "yyyy-MM-dd");
        }
        return expertReviewDateBeginStr;
    }

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  专家评审开始时间
	 */
	public void setExpertReviewDateBegin(java.util.Date expertReviewDateBegin){
		this.expertReviewDateBegin = expertReviewDateBegin;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  专家评审结束时间
	 */
	@Column(name ="EXPERT_REVIEW_DATE_END",nullable=true)
	public java.util.Date getExpertReviewDateEnd(){
		return this.expertReviewDateEnd;
	}

    @Transient
    public String getExpertReviewDateEndStr() {
        String expertReviewDateEndStr = "";
        if (this.expertReviewDateEnd != null) {
        	expertReviewDateEndStr = DateUtils.formatDate(this.expertReviewDateEnd, "yyyy-MM-dd");
        }
        return expertReviewDateEndStr;
    }

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  专家评审结束时间
	 */
	public void setExpertReviewDateEnd(java.util.Date expertReviewDateEnd){
		this.expertReviewDateEnd = expertReviewDateEnd;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计划会审地点
	 */
	@Column(name ="PLAN_REVIEW_ADDRESS",nullable=true)
	public java.lang.String getPlanReviewAddress(){
		return this.planReviewAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划会审地点
	 */
	public void setPlanReviewAddress(java.lang.String planReviewAddress){
		this.planReviewAddress = planReviewAddress;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评审方式
	 */
	@Column(name ="REVIEW_MODE",nullable=true,length=1)
	public java.lang.String getReviewMode(){
		return this.reviewMode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评审方式
	 */
	public void setReviewMode(java.lang.String reviewMode){
		this.reviewMode = reviewMode;
	}

    public java.lang.String convertGetReviewMode() {
        return ConvertDicUtil.getConvertDic("1", "PSFS", this.reviewMode);
    }

    @Transient
    public String getReviewModeStr() {
        return this.convertGetReviewMode();
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

    @Transient
    public String getCreateDateStr() {
        String createDateStr = "";
        if (this.createDate != null) {
            createDateStr = DateUtils.formatDate(this.createDate, "yyyy-MM-dd HH:mm:ss");
        }
        return createDateStr;
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

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "T_E_ID")
    public List<TErReviewProjectEntity> getReviewProjectList() {
        return reviewProjectList;
    }

    public void setReviewProjectList(List<TErReviewProjectEntity> reviewProjectList) {
        this.reviewProjectList = reviewProjectList;
    }

    @Column(name = "REVIEW_PROCESS")
    public java.lang.String getReviewProcess() {
        return reviewProcess;
    }

    public void setReviewProcess(java.lang.String reviewProcess) {
        this.reviewProcess = reviewProcess;
    }

    @Column(name = "review_status")
    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

}
