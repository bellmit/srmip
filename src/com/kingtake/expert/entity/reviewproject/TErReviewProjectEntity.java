package com.kingtake.expert.entity.reviewproject;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;
import com.kingtake.expert.entity.suggestion.TErSuggestionEntity;

/**   
 * @Title: Entity
 * @Description: 评审项目信息表
 * @author onlineGenerator
 * @date 2015-08-18 16:51:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_er_review_project", schema = "")
@SuppressWarnings("serial")
public class TErReviewProjectEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**专家评_主键*/
    private TErReviewMainEntity reviewMain;
	/**所属项目*/
	@Excel(name="所属项目")
	private java.lang.String projectId;
	/**项目名称*/
	@Excel(name="项目名称")
	private java.lang.String projectName;
	/**所属类型*/
	@Excel(name="所属类型")
	private java.lang.String reviewType;
	/**所属阶段*/
	@Excel(name="所属阶段")
	private java.lang.String projectStage;
	/**评审结果*/
	@Excel(name="评审结果")
	private java.lang.String reviewResult;
	/**评审分数*/
	@Excel(name="评审分数")
	private java.math.BigDecimal reviewScore;
	/**评审意见*/
	@Excel(name="评审意见")
	private java.lang.String reviewSuggestion;
	/**结果录入时间*/
	@Excel(name="结果录入时间")
	private java.util.Date resultInputDate;
	/**录入人id*/
	@Excel(name="录入人id")
	private java.lang.String resultInputUserid;
	/**录入人姓名*/
	@Excel(name="录入人姓名")
	private java.lang.String resultInputUsername;
	
    private List<TErSuggestionEntity> suggesstionList;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_E_ID")
    public TErReviewMainEntity getReviewMain() {
        return reviewMain;
    }

    public void setReviewMain(TErReviewMainEntity reviewMain) {
        this.reviewMain = reviewMain;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 所属项目
     */
	@Column(name ="PROJECT_ID",nullable=true,length=32)
	public java.lang.String getProjectId(){
		return this.projectId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属项目
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属类型
	 */
	@Column(name ="REVIEW_TYPE",nullable=true,length=1)
	public java.lang.String getReviewType(){
		return this.reviewType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属类型
	 */
	public void setReviewType(java.lang.String reviewType){
		this.reviewType = reviewType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属阶段
	 */
	@Column(name ="PROJECT_STAGE",nullable=true,length=2)
	public java.lang.String getProjectStage(){
		return this.projectStage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属阶段
	 */
	public void setProjectStage(java.lang.String projectStage){
		this.projectStage = projectStage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评审结果
	 */
	@Column(name ="REVIEW_RESULT",nullable=true,length=1)
	public java.lang.String getReviewResult(){
		return this.reviewResult;
	}

    @Transient
    public java.lang.String getReviewResultStr() {
        return ConvertDicUtil.getConvertDic("0", "PSJL", this.reviewResult);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评审结果
	 */
	public void setReviewResult(java.lang.String reviewResult){
		this.reviewResult = reviewResult;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  评审分数
	 */
	@Column(name ="REVIEW_SCORE",nullable=true,scale=2,length=6)
	public java.math.BigDecimal getReviewScore(){
		return this.reviewScore;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  评审分数
	 */
	public void setReviewScore(java.math.BigDecimal reviewScore){
		this.reviewScore = reviewScore;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评审意见
	 */
	@Column(name ="REVIEW_SUGGESTION",nullable=true,length=500)
	public java.lang.String getReviewSuggestion(){
		return this.reviewSuggestion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评审意见
	 */
	public void setReviewSuggestion(java.lang.String reviewSuggestion){
		this.reviewSuggestion = reviewSuggestion;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结果录入时间
	 */
	@Column(name ="RESULT_INPUT_DATE",nullable=true)
	public java.util.Date getResultInputDate(){
		return this.resultInputDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结果录入时间
	 */
	public void setResultInputDate(java.util.Date resultInputDate){
		this.resultInputDate = resultInputDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  录入人id
	 */
	@Column(name ="RESULT_INPUT_USERID",nullable=true,length=32)
	public java.lang.String getResultInputUserid(){
		return this.resultInputUserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  录入人id
	 */
	public void setResultInputUserid(java.lang.String resultInputUserid){
		this.resultInputUserid = resultInputUserid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  录入人姓名
	 */
	@Column(name ="RESULT_INPUT_USERNAME",nullable=true,length=50)
	public java.lang.String getResultInputUsername(){
		return this.resultInputUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  录入人姓名
	 */
	public void setResultInputUsername(java.lang.String resultInputUsername){
		this.resultInputUsername = resultInputUsername;
	}

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "reviewProject")
    public List<TErSuggestionEntity> getSuggesstionList() {
        return suggesstionList;
    }

    public void setSuggesstionList(List<TErSuggestionEntity> suggesstionList) {
        this.suggesstionList = suggesstionList;
    }

}
