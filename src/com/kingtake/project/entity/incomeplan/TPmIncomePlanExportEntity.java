package com.kingtake.project.entity.incomeplan;

import java.math.BigDecimal;
import javax.persistence.Column;

public class TPmIncomePlanExportEntity implements java.io.Serializable {    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 序号 */
    private java.lang.String no;
    
    /** 会计编码 */
    private java.lang.String accountingCode;
    
    /** 项目名称 */
    private java.lang.String projectName;
    
    /** 研制部门 */
    private java.lang.String developerDepart;
    
    /** 负责人 */
    private java.lang.String projectManager;
        
    /**
     * 计划下达金额
     */
    private BigDecimal planAmount;      
    
    /**
     * 经费来源
     */
    private String fundsSources;
    
    /**
     * 大学预留金额
     */
    private BigDecimal universityAmount;
    
    /**
     * 院预留金额
     */
    private BigDecimal academyAmount;
    
    /**
     * 系预留金额
     */
    private BigDecimal departmentAmount;     
    
    public TPmIncomePlanExportEntity(String no, String accountingCode, String projectName, String developerDepart,
    		String projectManager, BigDecimal planAmount, String fundsSources, BigDecimal universityAmount, BigDecimal academyAmount
    		,BigDecimal departmentAmount) {
        this.no = no;
        this.accountingCode = accountingCode;
        this.projectName = projectName;
        this.developerDepart = developerDepart;
        this.projectManager = projectManager;
        this.planAmount = planAmount;
        this.fundsSources = fundsSources;
        this.universityAmount = universityAmount;
        this.academyAmount = academyAmount;
        this.departmentAmount = departmentAmount;
    }

	/**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 序号
     */
    public java.lang.String getNo() {
        return this.no;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 序号
     */
    public void setNo(java.lang.String no) {
        this.no = no;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目编码
     */
    public java.lang.String getAccountingCode() {
        return this.accountingCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目编码
     */
    public void setPAccountingCode(java.lang.String accountingCode) {
        this.accountingCode = accountingCode;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目名称
     */
    public java.lang.String getProjectName() {
        return this.projectName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目名称
     */
    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 研制部门
     */
    public java.lang.String getDeveloperDepart() {
        return this.developerDepart;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 研制部门
     */
    public void setDeveloperDepart(java.lang.String developerDepart) {
        this.developerDepart = developerDepart;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 负责人
     */
    public java.lang.String getProjectManager() {
        return this.projectManager;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 负责人
     */
    public void setProjectManager(java.lang.String projectManager) {
        this.projectManager = projectManager;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 计划下达金额
     */
    public java.math.BigDecimal getPlanAmount() {
        return this.planAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 计划下达金额
     */
    public void setPlanAmount(java.math.BigDecimal planAmount) {
        this.planAmount = planAmount;
    }    
    
    public BigDecimal getUniversityAmount() {
        return universityAmount;
    }

    public void setUniversityAmount(BigDecimal universityAmount) {
        this.universityAmount = universityAmount;
    }
    
    public BigDecimal getAcademyAmount() {
        return academyAmount;
    }

    public void setAcademyAmount(BigDecimal academyAmount) {
        this.academyAmount = academyAmount;
    }
    
    public BigDecimal getDepartmentAmount() {
        return departmentAmount;
    }

    public void setDepartmentAmount(BigDecimal departmentAmount) {
        this.departmentAmount = departmentAmount;
    }      
    
    public String getFundsSources() {
        return fundsSources;
    }

    public void setFundsSources(String fundsSources) {
        this.fundsSources = fundsSources;
    }        
}
