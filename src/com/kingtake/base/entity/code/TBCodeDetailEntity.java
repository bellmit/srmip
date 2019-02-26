package com.kingtake.base.entity.code;

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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 基础标准代码参数值
 * @author onlineGenerator
 * @date 2015-06-11 11:12:05
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_code_detail", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
@LogEntity("代码集参数值")
public class TBCodeDetailEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**基础标准代码类别表*/
	private java.lang.String codeTypeId;
	/**代码*/
	@Excel(name="代码")
    @FieldDesc("代码")
	private java.lang.String code;
	/**名称*/
	@Excel(name="名称")
    @FieldDesc("名称")
	private java.lang.String name;
	/**备注*/
	@Excel(name="备注")
    @FieldDesc("备注")
	private java.lang.String memo;
	/**所属上级代码*/
	@Excel(name="所属上级代码")
    @FieldDesc("所属上级代码")
	private java.lang.String parentCode;

	private TBCodeDetailEntity parentCodeDetail;
	/**排序码*/
	@Excel(name="排序码")
    private java.lang.Integer sortFlag;
	/**有效标记*/

	private java.lang.String validFlag;

	/**
	 * 下属代码参数值
	 */
	private List<TBCodeDetailEntity> codeDetails;

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
	 *@return: java.lang.String  基础标准代码类别表
	 */
	@Column(name ="CODE_TYPE_ID",nullable=true,length=32)
	public java.lang.String getCodeTypeId(){
		return this.codeTypeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基础标准代码类别表
	 */
	public void setCodeTypeId(java.lang.String codeTypeId){
		this.codeTypeId = codeTypeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代码
	 */
	@Column(name ="CODE",nullable=false,length=20)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=false,length=40)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="MEMO",nullable=true,length=200)
	public java.lang.String getMemo(){
		return this.memo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setMemo(java.lang.String memo){
		this.memo = memo;
	}

	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinColumn(name="parent_code")
	public TBCodeDetailEntity getParentCodeDetail() {
		return parentCodeDetail;
	}

	public void setParentCodeDetail(TBCodeDetailEntity parentCodeDetail) {
		this.parentCodeDetail = parentCodeDetail;
	}

    @Column(name = "SORT")
    public java.lang.Integer getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(java.lang.Integer sortFlag) {
        this.sortFlag = sortFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 有效标记
     */
	@Column(name ="VALID_FLAG",nullable=false,length=1)
	public java.lang.String getValidFlag(){
		return this.validFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  有效标记
	 */
	public void setValidFlag(java.lang.String validFlag){
		this.validFlag = validFlag;
	}

	@Transient
	public java.lang.String getParentCode() {

		if(StringUtils.isNotEmpty(parentCode)){
			return parentCode;
		}
		if(this.parentCodeDetail!=null){
			return parentCodeDetail.getCode();
		}
		return null;
	}

	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentCodeDetail")
	public List<TBCodeDetailEntity> getCodeDetails() {
		return codeDetails;
	}

	public void setCodeDetails(List<TBCodeDetailEntity> codeDetails) {
		this.codeDetails = codeDetails;
	}

}
