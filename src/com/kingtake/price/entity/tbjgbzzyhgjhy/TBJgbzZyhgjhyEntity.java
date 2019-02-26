package com.kingtake.price.entity.tbjgbzzyhgjhy;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.constant.SrmipConstants;

/**   
 * @Title: Entity
 * @Description: 中央和国家机关会议费
 * @author onlineGenerator
 * @date 2016-07-22 15:47:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_zyhgjhy", schema = "")
@SuppressWarnings("serial")
public class TBJgbzZyhgjhyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**会议类型*/
	@Excel(name="会议类型", isExportConvert=true)
	private java.lang.String hylx;
	/**合计*/
	@Excel(name="合计",isAmount=true)
	private java.math.BigDecimal hj;
	/**住宿费*/
	@Excel(name="住宿费",isAmount=true)
	private java.math.BigDecimal zsf;
	/**伙食费*/
	@Excel(name="伙食费",isAmount=true)
	private java.math.BigDecimal hsf;
	/**其他费用*/
	@Excel(name="其他费用",isAmount=true)
	private java.math.BigDecimal qt;
	/**执行时间*/
	@Excel(name="执行时间",exportFormat="yyyy-MM-dd",width=20)
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
	 *@return: java.lang.String  会议类型
	 */
	@Column(name ="HYLX",nullable=true,length=2)
	public java.lang.String getHylx(){
		return this.hylx;
	}

	
    public java.lang.String convertGetHylx() {
        if (StringUtil.isNotEmpty(this.hylx)) {
            return SrmipConstants.dicResearchMap.get("HYLX").get(this.hylx).toString();
        }
        return this.hylx;
    }
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会议类型
	 */
	public void setHylx(java.lang.String hylx){
		this.hylx = hylx;
	}
	
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  合计
	 */
	@Column(name ="HJ",nullable=true,scale=2,length=10)
	@Transient
	public java.math.BigDecimal getHj() {
		return hj;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  合计
	 */
	public void setHj(java.math.BigDecimal hj) {
		this.hj = hj;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  住宿费
	 */
	@Column(name ="ZSF",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getZsf(){
		return this.zsf;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  住宿费
	 */
	public void setZsf(java.math.BigDecimal zsf){
		this.zsf = zsf;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  伙食费
	 */
	@Column(name ="HSF",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getHsf(){
		return this.hsf;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  伙食费
	 */
	public void setHsf(java.math.BigDecimal hsf){
		this.hsf = hsf;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  其他费用
	 */
	@Column(name ="QT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getQt(){
		return this.qt;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  其他费用
	 */
	public void setQt(java.math.BigDecimal qt){
		this.qt = qt;
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
