package com.kingtake.base.entity.code;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

/**
 * @Title: Entity
 * @Description: 基础标准代码类别表
 * @author onlineGenerator
 * @date 2015-06-11 11:12:05
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_code_type")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
@LogEntity("基础数据代码集管理")
public class TBCodeTypeEntity implements java.io.Serializable {
	/** 主键 */
	private java.lang.String id;
	/** 类别代码 */
	@Excel(name = "类别代码", needMerge = true)
    @FieldDesc("类别代码")
	private java.lang.String code;
	/** 类别名称 */
    @FieldDesc("类别名称")
	@Excel(name = "类别名称", needMerge = true)
	private java.lang.String name;
	/** 所属代码集 */
    @FieldDesc("所属代码集")
	private java.lang.String codeType;
	/** 有效标记 */
    @FieldDesc("有效标记")
	private java.lang.String validFlag;
	/** createBy */
	private java.lang.String createBy;
	/** createName */
	private java.lang.String createName;
	/** createDate */
	private java.util.Date createDate;
	/** updateBy */
	private java.lang.String updateBy;
	/** updateName */
	private java.lang.String updateName;
	/** updateDate */
	private java.util.Date updateDate;

	@ExcelCollection(name="标准代码参数值")
	private List<TBCodeDetailEntity> tbCodeDetails;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 主键
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 类别代码
	 */

	@Column(name = "CODE", nullable = false, length = 24)
	public java.lang.String getCode() {
		return this.code;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 类别代码
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 类别名称
	 */

	@Column(name = "NAME", nullable = false, length = 40)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 类别名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 所属代码集
	 */

	@Column(name = "CODE_TYPE", nullable = false, length = 1)
	public java.lang.String getCodeType() {
		return this.codeType;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 所属代码集
	 */
	public void setCodeType(java.lang.String codeType) {
		this.codeType = codeType;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 有效标记
	 */

	@Column(name = "VALID_FLAG", nullable = false, length = 1)
	public java.lang.String getValidFlag() {
		return this.validFlag;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 有效标记
	 */
	public void setValidFlag(java.lang.String validFlag) {
		this.validFlag = validFlag;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String createBy
	 */

	@Column(name = "CREATE_BY", nullable = true, length = 50)
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String createBy
	 */
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String createName
	 */

	@Column(name = "CREATE_NAME", nullable = true, length = 50)
	public java.lang.String getCreateName() {
		return this.createName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String createName
	 */
	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date createDate
	 */

	@Column(name = "CREATE_DATE", nullable = true)
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date createDate
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String updateBy
	 */

	@Column(name = "UPDATE_BY", nullable = true, length = 50)
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String updateBy
	 */
	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String updateName
	 */

	@Column(name = "UPDATE_NAME", nullable = true, length = 50)
	public java.lang.String getUpdateName() {
		return this.updateName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String updateName
	 */
	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date updateDate
	 */

	@Column(name = "UPDATE_DATE", nullable = true)
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date updateDate
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="CODE_TYPE_ID")
	public List<TBCodeDetailEntity> getTbCodeDetails() {
		return tbCodeDetails;
	}

	public void setTbCodeDetails(List<TBCodeDetailEntity> tbCodeDetails) {
		this.tbCodeDetails = tbCodeDetails;
	}

}
