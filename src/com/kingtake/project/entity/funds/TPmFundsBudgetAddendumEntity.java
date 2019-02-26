package com.kingtake.project.entity.funds;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 项目经费预算附表
 * @author onlineGenerator
 * @date 2015-08-04 16:16:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_funds_budget_addendum", schema = "")
@SuppressWarnings("serial")
@LogEntity("项目经费预算附表")
public class TPmFundsBudgetAddendumEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**关联项目预算审批表*/
	@FieldDesc("项目_主键")
	@Excel(name="关联项目预算审批表")
	private java.lang.String pid;
	/**序号*/
	@FieldDesc("序号")
	@Excel(name="序号")
    private java.lang.String num;
	/**开支内容*/
	@FieldDesc("开支内容")
	@Excel(name="开支内容")
	private java.lang.String content;
	/**型号*/
	@FieldDesc("型号")
	@Excel(name="型号")
	private java.lang.String model;
	/**数量*/
	@FieldDesc("数量")
	@Excel(name="数量")
	private java.lang.String account;
	/**单价*/
	@FieldDesc("单价")
	@Excel(name="单价")
	private Double price;
	/**金额*/
	@FieldDesc("金额")
	@Excel(name="金额")
	private Double money;
	/**备注*/
	@FieldDesc("备注")
	@Excel(name="备注")
	private java.lang.String remark;
	/**预算项目类型*/
    @FieldDesc("预算项目类型")
    @Excel(name="预算项目类型")
    private String addChildFlag;
    
    
    @FieldDesc("会计年度")
	@Excel(name="会计年度")
    private java.lang.String ztnd;
    
    @FieldDesc("凭证号")
	@Excel(name="凭证号")
    private java.lang.String zxh;
    
    @FieldDesc("顺序号")
	@Excel(name="顺序号")
    private java.lang.String sxh;
    
    @FieldDesc("记账日期")
	@Excel(name="记账日期")
    private Date jzrq;
    
    @FieldDesc("会计凭证号")
	@Excel(name="会计凭证号")
    private java.lang.String kjpzh;
    
    @FieldDesc("项目编码")
	@Excel(name="项目编码")
    private java.lang.String xmdm;
    
    @FieldDesc("明细代码")
	@Excel(name="明细代码")
    private java.lang.String mxdm;

	@FieldDesc("科目代码")
	@Excel(name="科目代码")
	private java.lang.String kmdm;
    
    @FieldDesc("摘要")
	@Excel(name="摘要")
    private java.lang.String zhy;
    
    @FieldDesc("借贷")
	@Excel(name="借贷")
    private java.lang.String jd;
    
    @FieldDesc("金额")
	@Excel(name="金额")
    private java.lang.String je;

	@FieldDesc("开支类型：1开支，2借贷，3还款")
	@Excel(name="开支类型：1开支，2借贷，3还款")
	private java.lang.String kzlx;
    
    
    
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
	 *@return: java.lang.String  关联项目预算审批表
	 */
	@Column(name ="PID",nullable=true,length=32)
	public java.lang.String getPid(){
		return this.pid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联项目预算审批表
	 */
	public void setPid(java.lang.String pid){
		this.pid = pid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  序号
	 */
	@Column(name ="NUM",nullable=true,length=10)
    public java.lang.String getNum() {
		return this.num;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  序号
	 */
    public void setNum(java.lang.String num) {
		this.num = num;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开支内容
	 */
	@Column(name ="CONTENT",nullable=true,length=2000)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开支内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  型号
	 */
	@Column(name ="MODEL",nullable=true,length=10)
	public java.lang.String getModel(){
		return this.model;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  型号
	 */
	public void setModel(java.lang.String model){
		this.model = model;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量
	 */
	@Column(name ="ACCOUNT",nullable=true,length=10)
	public java.lang.String getAccount(){
		return this.account;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数量
	 */
	public void setAccount(java.lang.String account){
		this.account = account;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单价
	 */
	@Column(name ="PRICE",nullable=true,scale=2,length=10)
	public Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  单价
	 */
	public void setPrice(Double price){
		this.price = price;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="MONEY",nullable=true,scale=2,length=10)
	public Double getMoney(){
		return this.money;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setMoney(Double money){
		this.money = money;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}

	@Column(name ="ADD_CHILD_FLAG",nullable=true,length=10)
    public String getAddChildFlag() {
        return addChildFlag;
    }

    public void setAddChildFlag(String addChildFlag) {
        this.addChildFlag = addChildFlag;
    }

    @Column(name ="ZTND",nullable=true,length=32)
	public java.lang.String getZtnd() {
		return ztnd;
	}

	public void setZtnd(java.lang.String ztnd) {
		this.ztnd = ztnd;
	}

	@Column(name ="zxh",nullable=true,length=32)
	public java.lang.String getZxh() {
		return zxh;
	}

	public void setZxh(java.lang.String zxh) {
		this.zxh = zxh;
	}

	@Column(name ="sxh",nullable=true,length=32)
	public java.lang.String getSxh() {
		return sxh;
	}

	public void setSxh(java.lang.String sxh) {
		this.sxh = sxh;
	}

	@Column(name ="jzrq",nullable=true)
	public Date getJzrq() {
		return jzrq;
	}

	public void setJzrq(Date jzrq) {
		this.jzrq = jzrq;
	}

	@Column(name ="kjpzh",nullable=true,length=32)
	public java.lang.String getKjpzh() {
		return kjpzh;
	}

	public void setKjpzh(java.lang.String kjpzh) {
		this.kjpzh = kjpzh;
	}

	@Column(name ="xmdm",nullable=true,length=32)
	public java.lang.String getXmdm() {
		return xmdm;
	}

	public void setXmdm(java.lang.String xmdm) {
		this.xmdm = xmdm;
	}

	@Column(name ="mxdm",nullable=true,length=32)
	public java.lang.String getMxdm() {
		return mxdm;
	}

	public void setMxdm(java.lang.String mxdm) {
		this.mxdm = mxdm;
	}

	@Column(name ="zhy",nullable=true,length=32)
	public java.lang.String getZhy() {
		return zhy;
	}

	public void setZhy(java.lang.String zhy) {
		this.zhy = zhy;
	}

	@Column(name ="jd",nullable=true,length=32)
	public java.lang.String getJd() {
		return jd;
	}

	public void setJd(java.lang.String jd) {
		this.jd = jd;
	}

	@Column(name ="je",nullable=true,length=32)
	public java.lang.String getJe() {
		return je;
	}

	public void setJe(java.lang.String je) {
		this.je = je;
	}

	@Column(name ="kmdm",nullable=true,length=32)
	public String getKmdm() {
		return kmdm;
	}

	public void setKmdm(String kmdm) {
		this.kmdm = kmdm;
	}

	public String getKzlx() {
		return kzlx;
	}

	public void setKzlx(String kzlx) {
		this.kzlx = kzlx;
	}
}
