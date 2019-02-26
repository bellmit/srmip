package com.kingtake.project.entity.appraisal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;

/**   
 * @Title: Entity
 * @Description: 鉴定委员会成员表
 * @author onlineGenerator
 * @date 2015-09-09 09:44:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_appraisal_member", schema = "")
@LogEntity("鉴定委员会成员表")
@SuppressWarnings("serial")
public class TBAppraisalMemberEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键id")
	private java.lang.String id;
	/**姓名*/
	@Excel(name="姓名")
	@FieldDesc("姓名")
	private java.lang.String memberName;
	/**职称*/
	@Excel(name="职称")
	@FieldDesc("职称")
	private java.lang.String memberPosition;
	/**专业*/
	@Excel(name="专业")
	@FieldDesc("专业")
	private java.lang.String memberProfession;
	/**工作单位*/
	@Excel(name="工作单位")
	@FieldDesc("工作单位")
	private java.lang.String workUnit;
	/**备注*/
	@Excel(name="备注")
	@FieldDesc("备注")
	private java.lang.String memo;
	/**创建人*/
	@Excel(name="创建人")
	@FieldDesc("创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	@FieldDesc("创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@Excel(name="创建时间")
	@FieldDesc("创建时间")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人")
	@FieldDesc("修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@Excel(name="修改人姓名")
	@FieldDesc("修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@Excel(name="修改时间")
	@FieldDesc("修改时间")

	private java.util.Date updateDate;
	
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
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="MEMBER_NAME",nullable=true,length=50)
	public java.lang.String getMemberName(){
		return this.memberName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setMemberName(java.lang.String memberName){
		this.memberName = memberName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职称
	 */
	@Column(name ="MEMBER_POSITION",nullable=true,length=2)
	public java.lang.String getMemberPosition(){
		return this.memberPosition;
	}

    @Transient
    public String getMemberPositionStr(){
        return ConvertDicUtil.getConvertDic("1", "ZHCH", this.memberPosition);
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职称
	 */
	public void setMemberPosition(java.lang.String memberPosition){
		this.memberPosition = memberPosition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专业
	 */
	@Column(name ="MEMBER_PROFESSION",nullable=true,length=2)
	public java.lang.String getMemberProfession(){
		return this.memberProfession;
	}

    @Transient
    public String getMemberProfessionStr() {
        return ConvertDicUtil.getConvertDic("1", "MAJOR", this.memberProfession);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专业
	 */
	public void setMemberProfession(java.lang.String memberProfession){
		this.memberProfession = memberProfession;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作单位
	 */
	@Column(name ="WORK_UNIT",nullable=true,length=300)
	public java.lang.String getWorkUnit(){
		return this.workUnit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作单位
	 */
	public void setWorkUnit(java.lang.String workUnit){
		this.workUnit = workUnit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="MEMO",nullable=true,length=300)
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
}
