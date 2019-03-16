package com.kingtake.project.entity.incomeplan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.invoice.TBPmInvoiceEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 计划下达主表
 * @author onlineGenerator
 * @date 2015-12-02 18:36:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_project_plan", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmProjectPlanEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /**
     * 文件号
     */
    @Excel(name = "文件号")
    @FieldDesc("文件号")
    private String documentNo;
    
    /**
     * 文件名
     */
    @Excel(name = "文件名")
    @FieldDesc("文件名")
    private String documentName;   

    /**
     * 发文时间
     */
    @Excel(name = "发文时间", format = "yyyy-MM-dd HH:mm:ss")
    @FieldDesc("发文时间")
    private Date documentTime;       
    
    /**
     * 来源经费科目
     */
    private String fundsSubject;
    
    /**
     * 会计年度
     */
    private String planYear;
    
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 计划下达总金额
     */
    @Excel(name = "计划下达总金额")
    @FieldDesc("计划下达总金额")
    private BigDecimal amount;         
    
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
    
    /** 修改时间 */
    @Excel(name = "审核状态")
    private String aduitStatus;
    
    /**
     * 预算状态
     */
    private String ysStatus;
    
    /**
     * 关联预算ID
     */
    private String ysApprId;

    /**
     * 打印编号
     */
    private String dybh;

    /**
     * excel文件名称
     */
    private String uploadFileName;

    
    @Column(name = "YS_STATUS")
    public String getYsStatus() {
		return ysStatus;
	}

	public void setYsStatus(String ysStatus) {
		this.ysStatus = ysStatus;
	}

	@Column(name = "YS_APPR_ID")
	public String getYsApprId() {
		return ysApprId;
	}

	public void setYsApprId(String ysApprId) {
		this.ysApprId = ysApprId;
	}

	/**
     * 方法: 取得String
     * @return: String 审核状态
     */
    @Column(name = "ADUIT_STATUS", nullable = true)
    public String getAduitStatus() {
		return aduitStatus;
	}

	public void setAduitStatus(String aduitStatus) {
		this.aduitStatus = aduitStatus;
	}

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
     * @return: java.util.Date 发文时间
     */
    @Column(name = "DOCUMENT_TIME", nullable = true)
    public java.util.Date getDocumentTime() {
        return this.documentTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 发文时间
     */
    public void setDocumentTime(java.util.Date documentTime) {
        this.documentTime = documentTime;
    }



    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 计划下达总金额
     */
    @Column(name = "AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 计划下达总金额
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }



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

    @Column(name = "DOCUMENT_NO")
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Column(name = "DOCUMENT_NAME")
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }       

    @Column(name = "funds_subject")
    public String getFundsSubject() {
        return fundsSubject;
    }

    public void setFundsSubject(String fundsSubject) {
        this.fundsSubject = fundsSubject;
    }  
    
    @Column(name = "plan_year")
    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear;
    }
    
    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Column(name = "dybh")
    public String getDybh() {
        return dybh;
    }

    public void setDybh(String dybh) {
        this.dybh = dybh;
    }

    @Column(name = "upload_file_name")
    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
}
