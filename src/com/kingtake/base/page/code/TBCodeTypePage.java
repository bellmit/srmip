package com.kingtake.base.page.code;
import java.util.ArrayList;
import java.util.List;

import com.kingtake.base.entity.code.TBCodeDetailEntity;


/**   
 * @Title: Entity
 * @Description: 基础标准代码类别表
 * @author onlineGenerator
 * @date 2015-06-11 11:12:06
 * @version V1.0   
 *
 */
public class TBCodeTypePage implements java.io.Serializable {
	/**-基础标准代码参数值*/
	private List<TBCodeDetailEntity> tBCodeDetailList = new ArrayList<TBCodeDetailEntity>();
	public List<TBCodeDetailEntity> getTBCodeDetailList() {
		return tBCodeDetailList;
	}
	public void setTBCodeDetailList(List<TBCodeDetailEntity> tBCodeDetailList) {
		this.tBCodeDetailList = tBCodeDetailList;
	}

	/**主键*/
	private java.lang.String id;
	/**类别代码*/
	private java.lang.String code;
	/**类别名称*/
	private java.lang.String name;
	/**所属代码集*/
	private java.lang.String codeType;
	/**有效标记*/
	private java.lang.String validFlag;
	/**createBy*/
	private java.lang.String createBy;
	/**createName*/
	private java.lang.String createName;
	/**createDate*/
	private java.util.Date createDate;
	/**updateBy*/
	private java.lang.String updateBy;
	/**updateName*/
	private java.lang.String updateName;
	/**updateDate*/
	private java.util.Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *@return: java.lang.String  类别代码
	 */
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类别代码
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类别名称
	 */
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类别名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属代码集
	 */
	public java.lang.String getCodeType(){
		return this.codeType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属代码集
	 */
	public void setCodeType(java.lang.String codeType){
		this.codeType = codeType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  有效标记
	 */
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createBy
	 */
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createName
	 */
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createName
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createDate
	 */
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateBy
	 */
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateBy
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateName
	 */
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateName
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  updateDate
	 */
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  updateDate
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
