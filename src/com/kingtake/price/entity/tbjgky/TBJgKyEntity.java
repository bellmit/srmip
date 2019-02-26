package com.kingtake.price.entity.tbjgky;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 科研价格库
 * @author onlineGenerator
 * @date 2016-07-25 16:43:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jg_ky", schema = "")
@SuppressWarnings("serial")
public class TBJgKyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**种类*/
	@Excel(name="种类")
	private java.lang.String fl;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String mc;
	/**规格型号*/
	@Excel(name="规格型号")
	private java.lang.String ggxh;
	/**计量单位*/
	@Excel(name="计量单位")
	private java.lang.String jldw;
	/**单价*/
	@Excel(name="单价",isAmount=true)
	private java.math.BigDecimal dj;
	/**采购单位*/
	@Excel(name="采购单位")
	private java.lang.String cgdw;
	/**采购时间*/
	@Excel(name="采购时间")
	private java.util.Date cgsj;
	/**生产厂家*/
	@Excel(name="生产厂家")
	private java.lang.String sccj;
	/**来源*/
	@Excel(name="来源")
	private java.lang.String ly;
	/**来源项目*/
	@Excel(name="来源项目")
	private java.lang.String lyxm;
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
	 *@return: java.lang.String  种类
	 */
	@Column(name ="FL",nullable=true,length=10)
	public java.lang.String getFl(){
		return this.fl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  种类
	 */
	public void setFl(java.lang.String fl){
		this.fl = fl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="MC",nullable=true,length=200)
	public java.lang.String getMc(){
		return this.mc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setMc(java.lang.String mc){
		this.mc = mc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格型号
	 */
	@Column(name ="GGXH",nullable=true,length=100)
	public java.lang.String getGgxh(){
		return this.ggxh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格型号
	 */
	public void setGgxh(java.lang.String ggxh){
		this.ggxh = ggxh;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单价
	 */
	@Column(name ="DJ",nullable=true,scale=2,length=10)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  采购单位
	 */
	@Column(name ="CGDW",nullable=true,length=200)
	public java.lang.String getCgdw(){
		return this.cgdw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  采购单位
	 */
	public void setCgdw(java.lang.String cgdw){
		this.cgdw = cgdw;
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
	 *@return: java.lang.String  生产厂家
	 */
	@Column(name ="SCCJ",nullable=true,length=200)
	public java.lang.String getSccj(){
		return this.sccj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  生产厂家
	 */
	public void setSccj(java.lang.String sccj){
		this.sccj = sccj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  来源
	 */
	@Column(name ="LY",nullable=true,length=2)
	public java.lang.String getLy(){
		return this.ly;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  来源
	 */
	public void setLy(java.lang.String ly){
		this.ly = ly;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  来源项目
	 */
	@Column(name ="LYXM",nullable=true,length=320)
	public java.lang.String getLyxm(){
		return this.lyxm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  来源项目
	 */
	public void setLyxm(java.lang.String lyxm){
		this.lyxm = lyxm;
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
