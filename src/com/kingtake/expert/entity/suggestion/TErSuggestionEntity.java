package com.kingtake.expert.entity.suggestion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;

/**   
 * @Title: Entity
 * @Description: 评审意见表
 * @author onlineGenerator
 * @date 2015-08-18 16:49:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_er_suggestion", schema = "")
@SuppressWarnings("serial")
public class TErSuggestionEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**评审项目信息表主键*/
    private TErReviewProjectEntity reviewProject;
	/**评审专家id*/
	@Excel(name="评审专家id")
	private java.lang.String expertId;
	/**评审专家姓名*/
	@Excel(name="评审专家姓名")
	private java.lang.String expertName;
	/**评审时间*/
	@Excel(name="评审时间")
	private java.util.Date expertTime;
	/**专家打分*/
	@Excel(name="专家打分")
	private java.math.BigDecimal expertScore;
	/**专家结论*/
	@Excel(name="专家结论")
	private java.lang.String expertResult;
	/**理由或意见建议*/
	@Excel(name="理由或意见建议")
	private java.lang.String expertContent;
	
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

    @ManyToOne
    @JoinColumn(name = "REVIEW_PROJECT_ID")
    public TErReviewProjectEntity getReviewProject() {
        return reviewProject;
    }

    public void setReviewProject(TErReviewProjectEntity reviewProject) {
        this.reviewProject = reviewProject;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 评审专家id
     */
	@Column(name ="EXPERT_ID",nullable=true,length=32)
	public java.lang.String getExpertId(){
		return this.expertId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评审专家id
	 */
	public void setExpertId(java.lang.String expertId){
		this.expertId = expertId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  评审专家姓名
	 */
	@Column(name ="EXPERT_NAME",nullable=true,length=50)
	public java.lang.String getExpertName(){
		return this.expertName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  评审专家姓名
	 */
	public void setExpertName(java.lang.String expertName){
		this.expertName = expertName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  评审时间
	 */
	@Column(name ="EXPERT_TIME",nullable=true)
	public java.util.Date getExpertTime(){
		return this.expertTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  评审时间
	 */
	public void setExpertTime(java.util.Date expertTime){
		this.expertTime = expertTime;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  专家打分
	 */
	@Column(name ="EXPERT_SCORE",nullable=true,scale=2,length=5)
	public java.math.BigDecimal getExpertScore(){
		return this.expertScore;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  专家打分
	 */
	public void setExpertScore(java.math.BigDecimal expertScore){
		this.expertScore = expertScore;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专家结论
	 */
	@Column(name ="EXPERT_RESULT",nullable=true,length=1)
	public java.lang.String getExpertResult(){
		return this.expertResult;
	}

    /**
     * 获取转化后的专家结果
     * 
     * @return
     */
    @Transient
    public String getExpertResultStr() {
        return ConvertDicUtil.getConvertDic("0", "PSJL", this.expertResult);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专家结论
	 */
	public void setExpertResult(java.lang.String expertResult){
		this.expertResult = expertResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  理由或意见建议
	 */
	@Column(name ="EXPERT_CONTENT",nullable=true,length=300)
	public java.lang.String getExpertContent(){
		return this.expertContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  理由或意见建议
	 */
	public void setExpertContent(java.lang.String expertContent){
		this.expertContent = expertContent;
	}


}
