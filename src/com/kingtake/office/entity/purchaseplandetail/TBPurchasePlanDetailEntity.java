package com.kingtake.office.entity.purchaseplandetail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 科研采购计划明细
 * @author onlineGenerator
 * @date 2016-05-31 18:50:15
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_purchase_plan_detail", schema = "")
@SuppressWarnings("serial")
public class TBPurchasePlanDetailEntity implements java.io.Serializable {
    /** 采购计划名称 */
    @Excel(name = "采购计划名称")
    private java.lang.String planName;
    /** 采购概算 */
    @Excel(name = "采购概算",isAmount = true)
    private java.math.BigDecimal purchaseEstimates;
    /** 采购方式 */
    @Excel(name = "采购方式", isExportConvert = true)
    private java.lang.String purchaseMode;
    /** 采购来源 */
    @Excel(name = "采购来源")
    private java.lang.String purchaseSource;
    
    /**
     * 采购理由
     */
    private String purchaseReason;
    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    private java.lang.String createName;
    /** 创建时间 */
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 主键 */
    private java.lang.String id;
    /** 科研采购计划信息表_主键 */
    private java.lang.String purchasePlanId;
    
    /**
     * 采购计划编号流水号，三位，001,002……
     */
    private String codeSerial;
    
    /**
     * 采购计划编号，年度+批次+流水号
     */
    private String mergeCode;

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 采购方式
     */
    @Column(name = "PURCHASE_MODE", nullable = true, length = 2)
    public java.lang.String getPurchaseMode() {
        return this.purchaseMode;
    }

    public java.lang.String convertGetPurchaseMode() {
        return ConvertDicUtil.getConvertDic("1", "CGFS", purchaseMode);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 采购方式
     */
    public void setPurchaseMode(java.lang.String purchaseMode) {
        this.purchaseMode = purchaseMode;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 采购来源
     */
    @Column(name = "PURCHASE_SOURCE", nullable = true, length = 200)
    public java.lang.String getPurchaseSource() {
        return this.purchaseSource;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 采购来源
     */
    public void setPurchaseSource(java.lang.String purchaseSource) {
        this.purchaseSource = purchaseSource;
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
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 科研采购计划信息表_主键
     */
    @Column(name = "PURCHASE_PLAN_ID", nullable = true, length = 32)
    public java.lang.String getPurchasePlanId() {
        return this.purchasePlanId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 科研采购计划信息表_主键
     */
    public void setPurchasePlanId(java.lang.String purchasePlanId) {
        this.purchasePlanId = purchasePlanId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 采购计划名称
     */
    @Column(name = "PLAN_NAME", nullable = true, length = 200)
    public java.lang.String getPlanName() {
        return this.planName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 采购计划名称
     */
    public void setPlanName(java.lang.String planName) {
        this.planName = planName;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     * 
     * @return: java.math.BigDecimal 采购概算
     */
    @Column(name = "PURCHASE_ESTIMATES", nullable = true, scale = 2, length = 12)
    public java.math.BigDecimal getPurchaseEstimates() {
        return this.purchaseEstimates;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     * 
     * @param: java.math.BigDecimal 采购概算
     */
    public void setPurchaseEstimates(java.math.BigDecimal purchaseEstimates) {
        this.purchaseEstimates = purchaseEstimates;
    }

    @Column(name="purchase_reason")
    public String getPurchaseReason() {
        return purchaseReason;
    }

    public void setPurchaseReason(String purchaseReason) {
        this.purchaseReason = purchaseReason;
    }
    
    @Column(name="code_serial")
    public String getCodeSerial() {
        return codeSerial;
    }

    public void setCodeSerial(String codeSerial) {
        this.codeSerial = codeSerial;
    }

    @Column(name="merge_code")
    public String getMergeCode() {
        return mergeCode;
    }

    public void setMergeCode(String mergeCode) {
        this.mergeCode = mergeCode;
    }
    
}
