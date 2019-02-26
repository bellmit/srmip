package com.kingtake.project.entity.kycg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: TD_KYCG
 * @author onlineGenerator
 * @date 2016-04-08 11:15:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "td_kycg", schema = "")
@SuppressWarnings("serial")
public class TdKycgEntity implements java.io.Serializable {
	/**cgdm*/
	@Excel(name="cgdm")
	private java.lang.String cgdm;
	/**xmdm*/
	@Excel(name="xmdm")
	private java.lang.String xmdm;
	/**jcbh*/
	@Excel(name="jcbh")
	private java.lang.String jcbh;
	/**cgmc*/
	@Excel(name="cgmc")
	private java.lang.String cgmc;
	/**完成单位*/
	@Excel(name="完成单位")
	private java.lang.String wcdw;
	/**xmzlxr*/
	@Excel(name="xmzlxr")
	private java.lang.String xmzlxr;
	/**xmzlxrs*/
	@Excel(name="xmzlxrs")
	private java.lang.String xmzlxrs;
	/**jglxr*/
	@Excel(name="jglxr")
	private java.lang.String jglxr;
	/**jglxfs*/
	@Excel(name="jglxfs")
	private java.lang.String jglxfs;
	/**jdsj*/
	@Excel(name="jdsj")
	private java.util.Date jdsj;
	/**jddd*/
	@Excel(name="jddd")
	private java.lang.String jddd;
	/**jdxs*/
	@Excel(name="jdxs")
	private java.lang.String jdxs;
	/**cgzt*/
	@Excel(name="cgzt")
	private java.lang.String cgzt;
	/**sqrq*/
	@Excel(name="sqrq")
	private java.util.Date sqrq;
	/**gdh*/
	@Excel(name="gdh")
	private java.lang.String gdh;
	/**scrq*/
	@Excel(name="scrq")
	private java.util.Date scrq;
	/**sbrq*/
	@Excel(name="sbrq")
	private java.util.Date sbrq;
	/**jdsph*/
	@Excel(name="jdsph")
	private java.lang.String jdsph;
	/**tzbh*/
	@Excel(name="tzbh")
	private java.lang.String tzbh;
	/**clsbrq*/
	@Excel(name="clsbrq")
	private java.util.Date clsbrq;
	/**sppj*/
	@Excel(name="sppj")
	private java.lang.String sppj;
	/**zsbh*/
	@Excel(name="zsbh")
	private java.lang.String zsbh;
	/**wcr*/
	@Excel(name="wcr")
	private java.lang.String wcr;
	/**zslqr*/
	@Excel(name="zslqr")
	private java.lang.String zslqr;
	/**zslqrq*/
	@Excel(name="zslqrq")
	private java.util.Date zslqrq;
	/**bz*/
	@Excel(name="bz")
	private java.lang.String bz;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  cgdm
	 */
    @Id
	@Column(name ="CGDM",nullable=false,length=20)
	public java.lang.String getCgdm(){
		return this.cgdm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cgdm
	 */
	public void setCgdm(java.lang.String cgdm){
		this.cgdm = cgdm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  xmdm
	 */
	@Column(name ="XMDM",nullable=true,length=200)
	public java.lang.String getXmdm(){
		return this.xmdm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  xmdm
	 */
	public void setXmdm(java.lang.String xmdm){
		this.xmdm = xmdm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  jcbh
	 */
	@Column(name ="JCBH",nullable=true,length=20)
	public java.lang.String getJcbh(){
		return this.jcbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  jcbh
	 */
	public void setJcbh(java.lang.String jcbh){
		this.jcbh = jcbh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  cgmc
	 */
	@Column(name ="CGMC",nullable=true,length=500)
	public java.lang.String getCgmc(){
		return this.cgmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cgmc
	 */
	public void setCgmc(java.lang.String cgmc){
		this.cgmc = cgmc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  完成单位
	 */
	@Column(name ="WCDW",nullable=true,length=32)
	public java.lang.String getWcdw(){
		return this.wcdw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  完成单位
	 */
	public void setWcdw(java.lang.String wcdw){
		this.wcdw = wcdw;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  xmzlxr
	 */
	@Column(name ="XMZLXR",nullable=true,length=50)
	public java.lang.String getXmzlxr(){
		return this.xmzlxr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  xmzlxr
	 */
	public void setXmzlxr(java.lang.String xmzlxr){
		this.xmzlxr = xmzlxr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  xmzlxrs
	 */
	@Column(name ="XMZLXRS",nullable=true,length=300)
	public java.lang.String getXmzlxrs(){
		return this.xmzlxrs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  xmzlxrs
	 */
	public void setXmzlxrs(java.lang.String xmzlxrs){
		this.xmzlxrs = xmzlxrs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  jglxr
	 */
	@Column(name ="JGLXR",nullable=true,length=50)
	public java.lang.String getJglxr(){
		return this.jglxr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  jglxr
	 */
	public void setJglxr(java.lang.String jglxr){
		this.jglxr = jglxr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  jglxfs
	 */
	@Column(name ="JGLXFS",nullable=true,length=300)
	public java.lang.String getJglxfs(){
		return this.jglxfs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  jglxfs
	 */
	public void setJglxfs(java.lang.String jglxfs){
		this.jglxfs = jglxfs;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  jdsj
	 */
	@Column(name ="JDSJ",nullable=true)
	public java.util.Date getJdsj(){
		return this.jdsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  jdsj
	 */
	public void setJdsj(java.util.Date jdsj){
		this.jdsj = jdsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  jddd
	 */
	@Column(name ="JDDD",nullable=true,length=50)
	public java.lang.String getJddd(){
		return this.jddd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  jddd
	 */
	public void setJddd(java.lang.String jddd){
		this.jddd = jddd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  jdxs
	 */
	@Column(name ="JDXS",nullable=true,length=50)
	public java.lang.String getJdxs(){
		return this.jdxs;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  jdxs
	 */
	public void setJdxs(java.lang.String jdxs){
		this.jdxs = jdxs;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  cgzt
	 */
	@Column(name ="CGZT",nullable=true,length=20)
	public java.lang.String getCgzt(){
		return this.cgzt;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  cgzt
	 */
	public void setCgzt(java.lang.String cgzt){
		this.cgzt = cgzt;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  sqrq
	 */
	@Column(name ="SQRQ",nullable=true)
	public java.util.Date getSqrq(){
		return this.sqrq;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  sqrq
	 */
	public void setSqrq(java.util.Date sqrq){
		this.sqrq = sqrq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  gdh
	 */
	@Column(name ="GDH",nullable=true,length=50)
	public java.lang.String getGdh(){
		return this.gdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  gdh
	 */
	public void setGdh(java.lang.String gdh){
		this.gdh = gdh;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  scrq
	 */
	@Column(name ="SCRQ",nullable=true)
	public java.util.Date getScrq(){
		return this.scrq;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  scrq
	 */
	public void setScrq(java.util.Date scrq){
		this.scrq = scrq;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  sbrq
	 */
	@Column(name ="SBRQ",nullable=true)
	public java.util.Date getSbrq(){
		return this.sbrq;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  sbrq
	 */
	public void setSbrq(java.util.Date sbrq){
		this.sbrq = sbrq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  jdsph
	 */
	@Column(name ="JDSPH",nullable=true,length=50)
	public java.lang.String getJdsph(){
		return this.jdsph;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  jdsph
	 */
	public void setJdsph(java.lang.String jdsph){
		this.jdsph = jdsph;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  tzbh
	 */
	@Column(name ="TZBH",nullable=true,length=50)
	public java.lang.String getTzbh(){
		return this.tzbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  tzbh
	 */
	public void setTzbh(java.lang.String tzbh){
		this.tzbh = tzbh;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  clsbrq
	 */
	@Column(name ="CLSBRQ",nullable=true)
	public java.util.Date getClsbrq(){
		return this.clsbrq;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  clsbrq
	 */
	public void setClsbrq(java.util.Date clsbrq){
		this.clsbrq = clsbrq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  sppj
	 */
	@Column(name ="SPPJ",nullable=true,length=30)
	public java.lang.String getSppj(){
		return this.sppj;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  sppj
	 */
	public void setSppj(java.lang.String sppj){
		this.sppj = sppj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  zsbh
	 */
	@Column(name ="ZSBH",nullable=true,length=50)
	public java.lang.String getZsbh(){
		return this.zsbh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  zsbh
	 */
	public void setZsbh(java.lang.String zsbh){
		this.zsbh = zsbh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  wcr
	 */
	@Column(name ="WCR",nullable=true,length=200)
	public java.lang.String getWcr(){
		return this.wcr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  wcr
	 */
	public void setWcr(java.lang.String wcr){
		this.wcr = wcr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  zslqr
	 */
	@Column(name ="ZSLQR",nullable=true,length=20)
	public java.lang.String getZslqr(){
		return this.zslqr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  zslqr
	 */
	public void setZslqr(java.lang.String zslqr){
		this.zslqr = zslqr;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  zslqrq
	 */
	@Column(name ="ZSLQRQ",nullable=true)
	public java.util.Date getZslqrq(){
		return this.zslqrq;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  zslqrq
	 */
	public void setZslqrq(java.util.Date zslqrq){
		this.zslqrq = zslqrq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  bz
	 */
	@Column(name ="BZ",nullable=true,length=4000)
	public java.lang.String getBz(){
		return this.bz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  bz
	 */
	public void setBz(java.lang.String bz){
		this.bz = bz;
	}
}
