package com.kingtake.price.entity.tbjgbzzjzx;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.constant.SrmipConstants;

/**   
 * @Title: Entity
 * @Description: 专家咨询费标准
 * @author onlineGenerator
 * @date 2016-07-25 09:35:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_zjzx", schema = "")
@SuppressWarnings("serial")
public class TBJgbzZjzxEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**咨询专家*/
	@Excel(name="咨询专家",isExportConvert=true)
	private java.lang.String zxzj;
	/**咨询方式*/
	@Excel(name="咨询方式",isExportConvert=true)
	private java.lang.String zxfs;
	/**标准*/
	@Excel(name="标准")
	private java.lang.String bz;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String beiz;
	/**执行时间*/
	@Excel(name="执行时间")
	private java.util.Date zxsj;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  咨询专家
	 */
	@Column(name ="ZXZJ",nullable=true,length=2)
	public java.lang.String getZxzj(){
		return this.zxzj;
	}

    public java.lang.String convertGetZxzj() {
        if (StringUtil.isNotEmpty(this.zxzj)) {
            return SrmipConstants.dicResearchMap.get("ZXZJ").get(this.zxzj).toString();
        }
        return this.zxzj;
    }
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  咨询专家
	 */
	public void setZxzj(java.lang.String zxzj){
		this.zxzj = zxzj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  咨询方式
	 */
	@Column(name ="ZXFS",nullable=true,length=2)
	public java.lang.String getZxfs(){
		return this.zxfs;
	}

    public java.lang.String convertGetZxfs() {
        if (StringUtil.isNotEmpty(this.zxfs)) {
            return SrmipConstants.dicResearchMap.get("ZXFS").get(this.zxfs).toString();
        }
        return this.zxfs;
    }
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  咨询方式
	 */
	public void setZxfs(java.lang.String zxfs){
		this.zxfs = zxfs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标准
	 */
	@Column(name ="BZ",nullable=true,length=400)
	public java.lang.String getBz(){
		return this.bz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标准
	 */
	public void setBz(java.lang.String bz){
		this.bz = bz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEIZ",nullable=true,length=500)
	public java.lang.String getBeiz(){
		return this.beiz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeiz(java.lang.String beiz){
		this.beiz = beiz;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  执行时间
	 */
	@Column(name ="ZXSJ",nullable=true,length=20)
	public java.util.Date getZxsj(){
		return this.zxsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  执行时间
	 */
	public void setZxsj(java.util.Date zxsj){
		this.zxsj = zxsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
