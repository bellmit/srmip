package com.kingtake.project.entity.tpmincomeallot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;

/**   
 * @Title: Entity
 * @Description: 到账分配信息表
 * @author onlineGenerator
 * @date 2015-12-03 11:11:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_income_allot", schema = "")
@SuppressWarnings("serial")
public class TPmIncomeAllotEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**项目主键*/
	private java.lang.String projectId;
	/**项目名称*/
	@Excel(name="项目名称")
	private java.lang.String projectName;
	/**负责人ID*/
	private java.lang.String projectManagerId;
	/**负责人*/
	@Excel(name="负责人")
	private java.lang.String projectManager;
    /** 负责人 */
    @Excel(name = "负责人单位")
    private java.lang.String projectMgrDept;
	/**分配金额*/
	@Excel(name="分配金额",isAmount = true)
	private java.math.BigDecimal amount;
	/**创建人*/
	private java.lang.String createBy;
	/**创建人姓名*/
	private java.lang.String createName;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改人*/
	private java.lang.String updateBy;
	/**修改人姓名*/
	private java.lang.String updateName;
	/**修改时间*/
	private java.util.Date updateDate;
	/**可分配金额*/
	@Excel(name="可分配金额",isAmount = true)
	private java.math.BigDecimal balance;
	/**到账信息表主键*/
    //private java.lang.String incomeId;
    private TPmIncomeEntity income;

    /**
     * 分配单位
     */
    @Excel(name = "分配单位")
    private String incomeDept;
	

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
	 *@return: java.lang.String  项目主键
	 */
	@Column(name ="PROJECT_ID",nullable=false,length=32)
	public java.lang.String getProjectId(){
		return this.projectId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目主键
	 */
	public void setProjectId(java.lang.String projectId){
		this.projectId = projectId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  项目名称
	 */
	@Column(name ="PROJECT_NAME",nullable=true,length=100)
	public java.lang.String getProjectName(){
		return this.projectName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目名称
	 */
	public void setProjectName(java.lang.String projectName){
		this.projectName = projectName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人ID
	 */
	@Column(name ="PROJECT_MANAGER_ID",nullable=true,length=32)
	public java.lang.String getProjectManagerId(){
		return this.projectManagerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人ID
	 */
	public void setProjectManagerId(java.lang.String projectManagerId){
		this.projectManagerId = projectManagerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人
	 */
	@Column(name ="PROJECT_MANAGER",nullable=false,length=50)
	public java.lang.String getProjectManager(){
		return this.projectManager;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人
	 */
	public void setProjectManager(java.lang.String projectManager){
		this.projectManager = projectManager;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  分配金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  分配金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
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
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  可分配金额
	 */
	@Column(name ="BALANCE",nullable=true,scale=2,length=10)
	public java.math.BigDecimal getBalance(){
		return this.balance;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  可分配金额
	 */
	public void setBalance(java.math.BigDecimal balance){
		this.balance = balance;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INCOME_ID")
    public TPmIncomeEntity getIncome() {
        return income;
    }

    public void setIncome(TPmIncomeEntity income) {
        this.income = income;
    }

    @Column(name = "PROJECT_MGR_DEPT")
    public java.lang.String getProjectMgrDept() {
        return projectMgrDept;
    }

    public void setProjectMgrDept(java.lang.String projectMgrDept) {
        this.projectMgrDept = projectMgrDept;
    }

    @Column(name = "INCOME_DEPT")
    public String getIncomeDept() {
        return incomeDept;
    }

    public void setIncomeDept(String incomeDept) {
        this.incomeDept = incomeDept;
    }

}
