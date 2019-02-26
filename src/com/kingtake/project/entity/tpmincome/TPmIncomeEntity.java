package com.kingtake.project.entity.tpmincome;

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

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 到账信息表
 * @author onlineGenerator
 * @date 2015-12-02 18:36:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_income", schema = "")
@SuppressWarnings("serial")
public class TPmIncomeEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 到账日期 */
    @Excel(name = "到账日期")
    private java.util.Date incomeTime;
    /** 凭证号 */
    @Excel(name = "凭证号")
    private java.lang.String certificate;
    /** 摘要 */
    @Excel(name = "摘要")
    private java.lang.String digest;
    /** 到账金额 */
    @Excel(name = "到账金额",isAmount = true)
    private java.math.BigDecimal incomeAmount;
    /** 到账借贷 */
    @Excel(name = "到账借贷",isAmount = true)
    private java.math.BigDecimal incomeBorrow;
    /** 会计年度 */
    @Excel(name = "会计年度")
    private java.lang.String accountingYear;
    /** 到账年度 */
    @Excel(name = "到账年度")
    private java.lang.String incomeYear;
    /** 到账顺序号 */
    @Excel(name = "到账顺序号")
    private java.lang.String incomeNo;
    /** 到款科目 */
    @Excel(name = "到款科目")
    private java.lang.String incomeSubject;
    /** 备注 */
    @Excel(name = "备注")
    private java.lang.String remark;
    /** 创建人 */
    @Excel(name = "创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @Excel(name = "修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @Excel(name = "修改时间")
    private java.util.Date updateDate;
    /** 可分配金额 */
    @Excel(name = "可分配金额",isAmount = true)
    private java.math.BigDecimal balance;
    /** 项目主键 */
//    private java.lang.String projectId;
    private TPmProjectEntity project;

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
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 到账日期
     */
    @Column(name = "INCOME_TIME", nullable = true)
    public java.util.Date getIncomeTime() {
        return this.incomeTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 到账日期
     */
    public void setIncomeTime(java.util.Date incomeTime) {
        this.incomeTime = incomeTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 凭证号
     */
    @Column(name = "CERTIFICATE", nullable = true, length = 20)
    public java.lang.String getCertificate() {
        return this.certificate;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 凭证号
     */
    public void setCertificate(java.lang.String certificate) {
        this.certificate = certificate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 摘要
     */
    @Column(name = "DIGEST", nullable = true, length = 500)
    public java.lang.String getDigest() {
        return this.digest;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 摘要
     */
    public void setDigest(java.lang.String digest) {
        this.digest = digest;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 到账金额
     */
    @Column(name = "INCOME_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getIncomeAmount() {
        return this.incomeAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 到账金额
     */
    public void setIncomeAmount(java.math.BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }
    
    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 到账借贷
     */
    @Column(name = "INCOME_BORROW", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getIncomeBorrow() {
        return this.incomeBorrow;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 到账借贷
     */
    public void setIncomeBorrow(java.math.BigDecimal incomeBorrow) {
        this.incomeBorrow = incomeBorrow;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 会计年度
     */
    @Column(name = "ACCOUNTING_YEAR")
    public java.lang.String getAccountingYear() {
        return this.accountingYear;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 会计年度
     */
    public void setAccountingYear(java.lang.String accountingYear) {
        this.accountingYear = accountingYear;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 到账年度
     */
    @Column(name = "INCOME_YEAR")
    public java.lang.String getIncomeYear() {
        return this.incomeYear;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 到账年度
     */
    public void setIncomeYear(java.lang.String incomeYear) {
        this.incomeYear = incomeYear;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 到账顺序号
     */
    @Column(name = "INCOME_NO")
    public java.lang.String getIncomeNo() {
        return this.incomeNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 到账顺序号
     */
    public void setIncomeNo(java.lang.String incomeNo) {
        this.incomeNo = incomeNo;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 到账科目
     */
    @Column(name = "INCOME_SUBJECT")
    public java.lang.String getIncomeSubject() {
        return this.incomeSubject;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 到账科目
     */
    public void setIncomeSubject(java.lang.String incomeSubject) {
        this.incomeSubject = incomeSubject;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    @Column(name = "REMARK", nullable = true, length = 1000)
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }
    
//    /**
//     * 方法: 取得java.lang.String
//     *
//     * @return: java.lang.String 项目主键
//     */
//    @Column(name = "PROJECT_ID")
//    public java.lang.String getProjectId() {
//        return this.projectId;
//    }
//
//    /**
//     * 方法: 设置java.lang.String
//     *
//     * @param: java.lang.String 项目主键
//     */
//    public void setProjectId(java.lang.String projectId) {
//        this.projectId = projectId;
//    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创建人
     */
    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 创建人
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创建人姓名
     */
    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 创建人姓名
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 创建时间
     */
    @Column(name = "CREATE_DATE", nullable = true)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 创建时间
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 修改人
     */
    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 修改人
     */
    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 修改人姓名
     */
    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 修改人姓名
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 修改时间
     */
    @Column(name = "UPDATE_DATE", nullable = true)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 修改时间
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 可分配金额
     */
    @Column(name = "BALANCE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 可分配金额
     */
    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
    }
}
