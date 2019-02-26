package com.kingtake.project.entity.funds;


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
 * @Description: 项目余额明细表
 * @author onlineGenerator
 * @date 2015-07-26 15:40:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_project_balance", schema = "")
@SuppressWarnings("serial")
@LogEntity("项目余额明细表")
public class TPmProjectBalanceEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**项目基_主键*/
	private java.lang.String tpId;
	/** 项目代码 */
    @FieldDesc("项目代码")
    @Excel(name = "项目代码")
    private java.lang.String projectNo;
	/**明细代码*/
	@Excel(name="明细代码")
	private java.lang.String balanceNo;
	
	/**
	 * 明细名称
	 */
	private java.lang.String balanceName;
	
	/**
	 * 关联预算ID
	 */
	private String ysApprId;
	
	@Column(name ="YS_APPR_ID",nullable=true,length=32)
	public String getYsApprId() {
		return ysApprId;
	}

	public void setYsApprId(String ysApprId) {
		this.ysApprId = ysApprId;
	}

	/** 历次金额*/
    @FieldDesc("历次金额")
    @Excel(name = "历次金额",isAmount = true)
    private java.math.BigDecimal lcAmount;
	/**预算金额*/
	@Excel(name="预算金额",isAmount = true)
	private java.math.BigDecimal ysAmount;
	/**执行金额*/
	@Excel(name="执行金额",isAmount = true)
	private java.math.BigDecimal zxAmount;
	/**借款金额*/
	@Excel(name="借款金额",isAmount = true)
	private java.math.BigDecimal jkAmount;
	/**预算余额*/
	@Excel(name="预算余额",isAmount = true)
	private java.math.BigDecimal balanceAmount;	
	/**创建人*/
	@FieldDesc("创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@FieldDesc("创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@FieldDesc("编制时间")
	@Excel(name="编制时间")
	private java.util.Date createDate;
	/**修改人*/
	@FieldDesc("修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@FieldDesc("修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@FieldDesc("修改时间")
	private java.util.Date updateDate;
	
	/**
     * 是否已做预算标记
     */
    private String ysStatus;
    
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

	@Column(name ="T_P_ID",nullable=true,length=32)
	public java.lang.String getTpId() {
		return tpId;
	}

	public void setTpId(java.lang.String tpId) {
		this.tpId = tpId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目代码
	 */
	@Column(name ="PROJECT_NO")
	public java.lang.String getProjectNo(){
		return this.projectNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目代码
	 */
	public void setProjectNo(java.lang.String projectNo){
		this.projectNo = projectNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  明细代码
	 */
	@Column(name ="BALANCE_NO")
	public java.lang.String getBalanceNo(){
		return this.balanceNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  明细代码
	 */
	public void setBalanceNo(java.lang.String balanceNo){
		this.balanceNo = balanceNo;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  明细名称
	 */
	@Column(name ="BALANCE_NAME")
	public java.lang.String getBalanceName(){
		return this.balanceName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  明细名称
	 */
	public void setBalanceName(java.lang.String balanceName){
		this.balanceName = balanceName;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  历次金额
	 */
	@Column(name ="LC_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getLcAmount(){
		return this.lcAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  历次金额
	 */
	public void setLcAmount(java.math.BigDecimal lcAmount){
		this.lcAmount = lcAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  预算金额
	 */
	@Column(name ="YS_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getYsAmount(){
		return this.ysAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  预算金额
	 */
	public void setYsAmount(java.math.BigDecimal ysAmount){
		this.ysAmount = ysAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  执行金额
	 */
	@Column(name ="ZX_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getZxAmount(){
		return this.zxAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  执行金额
	 */
	public void setZxAmount(java.math.BigDecimal zxAmount){
		this.zxAmount = zxAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  借款金额
	 */
	@Column(name ="JK_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getJkAmount(){
		return this.jkAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  借款金额
	 */
	public void setJkAmount(java.math.BigDecimal jkAmount){
		this.jkAmount = jkAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  预算余额
	 */
	@Column(name ="BALANCE_AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getBalanceAmount(){
		return this.balanceAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  预算余额
	 */
	public void setBalanceAmount(java.math.BigDecimal balanceAmount){
		this.balanceAmount = balanceAmount;
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
	
	@Column(name = "YS_STATUS")
    public String getYsStatus() {
        return ysStatus;
    }

    public void setYsStatus(String ysStatus) {
        this.ysStatus = ysStatus;
    }
}
