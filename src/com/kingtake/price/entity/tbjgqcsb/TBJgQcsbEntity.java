package com.kingtake.price.entity.tbjgqcsb;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 器材设备价格库
 * @author onlineGenerator
 * @date 2016-07-25 16:44:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jg_qcsb", schema = "")
@SuppressWarnings("serial")
public class TBJgQcsbEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**平台名称*/
	@Excel(name="平台名称")
	private java.lang.String ptmc;
	/**系统设备名称*/
	@Excel(name="系统设备名称")
	private java.lang.String xtsbmc;
	/**器材设备名称*/
	@Excel(name="器材设备名称")
	private java.lang.String qcsbmc;
	/**规格型号*/
	@Excel(name="规格型号")
	private java.lang.String xhgg;
	/**计量单位*/
	@Excel(name="计量单位")
	private java.lang.String jldw;
	/**承制单位*/
	@Excel(name="承制单位")
	private java.lang.String czdw;
	/**单价*/
	@Excel(name="单价",isAmount=true)
	private java.math.BigDecimal dj;
	/**采购时间*/
	@Excel(name="采购时间")
	private java.util.Date cgsj;
	/**审价单位*/
	@Excel(name="审价单位")
	private java.lang.String sjdw;
	/**合同履行监督单位*/
	@Excel(name="合同履行监督单位")
	private java.lang.String htlxjddw;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String beiz;
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
	 *@return: java.lang.String  平台名称
	 */
	@Column(name ="PTMC",nullable=true,length=200)
	public java.lang.String getPtmc(){
		return this.ptmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  平台名称
	 */
	public void setPtmc(java.lang.String ptmc){
		this.ptmc = ptmc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  系统设备名称
	 */
	@Column(name ="XTSBMC",nullable=true,length=200)
	public java.lang.String getXtsbmc(){
		return this.xtsbmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系统设备名称
	 */
	public void setXtsbmc(java.lang.String xtsbmc){
		this.xtsbmc = xtsbmc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  器材设备名称
	 */
	@Column(name ="QCSBMC",nullable=true,length=200)
	public java.lang.String getQcsbmc(){
		return this.qcsbmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  器材设备名称
	 */
	public void setQcsbmc(java.lang.String qcsbmc){
		this.qcsbmc = qcsbmc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格型号
	 */
	@Column(name ="XHGG",nullable=true,length=100)
	public java.lang.String getXhgg(){
		return this.xhgg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格型号
	 */
	public void setXhgg(java.lang.String xhgg){
		this.xhgg = xhgg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计量单位
	 */
	@Column(name ="JLDW",nullable=true,length=30)
	public java.lang.String getJldw(){
		return this.jldw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计量单位
	 */
	public void setJldw(java.lang.String jldw){
		this.jldw = jldw;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  承制单位
	 */
	@Column(name ="CZDW",nullable=true,length=200)
	public java.lang.String getCzdw(){
		return this.czdw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  承制单位
	 */
	public void setCzdw(java.lang.String czdw){
		this.czdw = czdw;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单价
	 */
	@Column(name ="DJ",nullable=true,scale=2,length=12)
	public java.math.BigDecimal getDj(){
		return this.dj;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  单价
	 */
	public void setDj(java.math.BigDecimal dj){
		this.dj = dj;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  采购时间
	 */
	@Column(name ="CGSJ",nullable=true,length=20)
	public java.util.Date getCgsj(){
		return this.cgsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  采购时间
	 */
	public void setCgsj(java.util.Date cgsj){
		this.cgsj = cgsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审价单位
	 */
	@Column(name ="SJDW",nullable=true,length=200)
	public java.lang.String getSjdw(){
		return this.sjdw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审价单位
	 */
	public void setSjdw(java.lang.String sjdw){
		this.sjdw = sjdw;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同履行监督单位
	 */
	@Column(name ="HTLXJDDW",nullable=true,length=200)
	public java.lang.String getHtlxjddw(){
		return this.htlxjddw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同履行监督单位
	 */
	public void setHtlxjddw(java.lang.String htlxjddw){
		this.htlxjddw = htlxjddw;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEIZ",nullable=true,length=1000)
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
