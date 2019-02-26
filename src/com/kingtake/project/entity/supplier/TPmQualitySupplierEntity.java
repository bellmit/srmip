package com.kingtake.project.entity.supplier;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Entity
 * @Description: 供方名录信息表
 * @author onlineGenerator
 * @date 2015-08-20 09:36:13
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_pm_quality_supplier", schema = "")
@SuppressWarnings("serial")
public class TPmQualitySupplierEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 供方名称 */
    @Excel(name = "供方名称", orderNum = "2")
    private java.lang.String supplierName;
    /** 等级 */
    //@Excel(name = "等级", isExportConvert = true)
    private java.lang.String supplierLevel;
    /** 类别 */
    @Excel(name = "分类", isExportConvert = true, orderNum = "9")
    private java.lang.String supplierType;
    /** 提供产品 */
    @Excel(name = "提供产品", orderNum = "3")
    private java.lang.String supplierProduct;
    /** 通信地址 */
    @Excel(name = "通信地址", orderNum = "4")
    private java.lang.String supplierAddress;
    /** 邮编 */
    @Excel(name = "邮编", orderNum = "5")
    private java.lang.String supplierPostcode;
    /** 联系电话 */
    @Excel(name = "联系电话", orderNum = "6")
    private java.lang.String supplierPhone;
    /** 传真 */
    @Excel(name = "传真", orderNum = "7")
    private java.lang.String supplierFax;
    /** 联系人 */
    @Excel(name = "联系人", orderNum = "8")
    private java.lang.String supplierContact;
    /** 有效标记 */
    private java.lang.String validFlag;
    /** 开户行名称 */
    //@Excel(name = "开户行名称")
    private java.lang.String bankName;
    /** 开户行行号 */
    //@Excel(name = "开户行行号")
    private java.lang.String bankNum;
    /** 开户行地址 */
    //@Excel(name = "开户行地址")
    private java.lang.String bankAddress;
    /** 企业法人 */
    //@Excel(name = "企业法人")
    private java.lang.String enterpriseLegalPerson;
    /** 组织机构代码 */
    //@Excel(name = "组织机构代码")
    private java.lang.String organizationCode;
    /** 企业登记地址 */
    //@Excel(name = "企业登记地址")
    private java.lang.String registerAddress;

    /**
     * 承制资格，0 否，1 是
     */
    @Excel(name = "承制资格", orderNum = "10", isExportConvert = true)
    private String isQualify;

    /**
     * 是否临时供方，0 否，1 是
     */
    @Excel(name = "临时供方", orderNum = "11", isExportConvert = true)
    private String isTemp;
    
    /**
     * 审核人id
     */
    private String auditUserId;
    
    /**
     * 审核人名称
     */
    @Excel(name = "审核人", orderNum = "12")
    private String auditUserName;
    
    /**
     * 审核时间
     */
    @Excel(name = "审核时间", orderNum = "13", format = "yyyy-MM-dd")
    private Date auditTime;
    
    /**
     * 资质材料审核情况
     */
    private String qualityContent;
    /**
     * 失效时间
     */
    @Excel(name = "失效时间", orderNum = "14", format = "yyyy-MM-dd")
    private Date supplierTime;
    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "1")
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;
    
    @Excel(name="合同名称",orderNum="15")
    private String contractNameStr;
    
    @Excel(name = "负责人", orderNum = "16")
    private String projectManagerStr;

    @Excel(name = "学校单位", orderNum = "17")
    private String applyUnit;

    @Excel(name = "合同经费", orderNum = "18")
    private String totalFunds;

    @Excel(name = "签订时间", orderNum = "19")
    private String signingTime;

    /**
     * 失效原因
     */
    private String reason;

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
     * @return: java.lang.String 供方名称
     */
    @Column(name = "SUPPLIER_NAME", nullable = true, length = 60)
    public java.lang.String getSupplierName() {
        return this.supplierName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 供方名称
     */
    public void setSupplierName(java.lang.String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 等级
     */
    @Column(name = "SUPPLIER_LEVEL", nullable = true, length = 2)
    public java.lang.String getSupplierLevel() {
        return this.supplierLevel;
    }

    public java.lang.String convertGetSupplierLevel() {
        if (StringUtil.isNotEmpty(this.supplierLevel)) {
            return SrmipConstants.dicResearchMap.get("GFDJ").get(this.supplierLevel).toString();
        }
        return this.supplierLevel;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 等级
     */
    public void setSupplierLevel(java.lang.String supplierLevel) {
        this.supplierLevel = supplierLevel;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 类别
     */
    @Column(name = "SUPPLIER_TYPE", nullable = true, length = 2)
    public java.lang.String getSupplierType() {
        return this.supplierType;
    }

    public java.lang.String convertGetSupplierType() {
        if (StringUtil.isNotEmpty(this.supplierType)) {
            return SrmipConstants.dicResearchMap.get("GFLB").get(this.supplierType).toString();
        }
        return this.supplierType;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 类别
     */
    public void setSupplierType(java.lang.String supplierType) {
        this.supplierType = supplierType;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 提供产品
     */
    @Column(name = "SUPPLIER_PRODUCT", nullable = true, length = 80)
    public java.lang.String getSupplierProduct() {
        return this.supplierProduct;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 提供产品
     */
    public void setSupplierProduct(java.lang.String supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 通信地址
     */
    @Column(name = "SUPPLIER_ADDRESS", nullable = true, length = 100)
    public java.lang.String getSupplierAddress() {
        return this.supplierAddress;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 通信地址
     */
    public void setSupplierAddress(java.lang.String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 邮编
     */
    @Column(name = "SUPPLIER_POSTCODE", nullable = true, length = 6)
    public java.lang.String getSupplierPostcode() {
        return this.supplierPostcode;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 邮编
     */
    public void setSupplierPostcode(java.lang.String supplierPostcode) {
        this.supplierPostcode = supplierPostcode;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 联系人
     */
    @Column(name = "SUPPLIER_CONTACT", nullable = true, length = 50)
    public java.lang.String getSupplierContact() {
        return this.supplierContact;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 联系人
     */
    public void setSupplierContact(java.lang.String supplierContact) {
        this.supplierContact = supplierContact;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 联系电话
     */
    @Column(name = "SUPPLIER_PHONE", nullable = true, length = 30)
    public java.lang.String getSupplierPhone() {
        return this.supplierPhone;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 联系电话
     */
    public void setSupplierPhone(java.lang.String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 传真
     */
    @Column(name = "SUPPLIER_FAX", nullable = true, length = 30)
    public java.lang.String getSupplierFax() {
        return this.supplierFax;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 传真
     */
    public void setSupplierFax(java.lang.String supplierFax) {
        this.supplierFax = supplierFax;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 有效标记
     */
    @Column(name = "VALID_FLAG", nullable = true, length = 1)
    public java.lang.String getValidFlag() {
        return this.validFlag;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 有效标记
     */
    public void setValidFlag(java.lang.String validFlag) {
        this.validFlag = validFlag;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 开户行名称
     */
    @Column(name = "BANK_NAME", nullable = true, length = 100)
    public java.lang.String getBankName() {
        return this.bankName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 开户行名称
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 开户行行号
     */
    @Column(name = "BANK_NUM", nullable = true, length = 20)
    public java.lang.String getBankNum() {
        return this.bankNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 开户行行号
     */
    public void setBankNum(java.lang.String bankNum) {
        this.bankNum = bankNum;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 开户行地址
     */
    @Column(name = "BANK_ADDRESS", nullable = true, length = 100)
    public java.lang.String getBankAddress() {
        return this.bankAddress;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 开户行地址
     */
    public void setBankAddress(java.lang.String bankAddress) {
        this.bankAddress = bankAddress;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 企业法人
     */
    @Column(name = "ENTERPRISE_LEGAL_PERSON", nullable = true, length = 30)
    public java.lang.String getEnterpriseLegalPerson() {
        return this.enterpriseLegalPerson;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 企业法人
     */
    public void setEnterpriseLegalPerson(java.lang.String enterpriseLegalPerson) {
        this.enterpriseLegalPerson = enterpriseLegalPerson;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 组织机构代码
     */
    @Column(name = "ORGANIZATION_CODE", nullable = true, length = 20)
    public java.lang.String getOrganizationCode() {
        return this.organizationCode;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 组织机构代码
     */
    public void setOrganizationCode(java.lang.String organizationCode) {
        this.organizationCode = organizationCode;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 企业登记地址
     */
    @Column(name = "REGISTER_ADDRESS", nullable = true, length = 100)
    public java.lang.String getRegisterAddress() {
        return this.registerAddress;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 企业登记地址
     */
    public void setRegisterAddress(java.lang.String registerAddress) {
        this.registerAddress = registerAddress;
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

    @Column(name = "supplier_time")
    public Date getSupplierTime() {
        return supplierTime;
    }

    public void setSupplierTime(Date supplierTime) {
        this.supplierTime = supplierTime;
    }

    @Column(name = "IS_QUALIFY")
    public String getIsQualify() {
        return isQualify;
    }

    public String convertGetIsQualify() {
        String isQualifyStr = "";
        if ("1".equals(this.isQualify)) {
            isQualifyStr = "是";
        } else if ("0".equals(this.isQualify)) {
            isQualifyStr = "否";
        }
        return isQualifyStr;
    }

    public void setIsQualify(String isQualify) {
        this.isQualify = isQualify;
    }

    @Column(name = "IS_TEMP")
    public String getIsTemp() {
        return isTemp;
    }

    public String convertGetIsTemp() {
        String isTempStr = "";
        if ("1".equals(this.isTemp)) {
            isTempStr = "是";
        } else if ("0".equals(this.isTemp)) {
            isTempStr = "否";
        }
        return isTempStr;
    }

    public void setIsTemp(String isTemp) {
        this.isTemp = isTemp;
    }

    @Column(name = "AUDIT_USER_ID")
    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    @Column(name = "AUDIT_USER_NAME")
    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    @Column(name = "AUDIT_TIME")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    @Column(name = "QUALITY_CONTENT")
    public String getQualityContent() {
        return qualityContent;
    }

    public void setQualityContent(String qualityContent) {
        this.qualityContent = qualityContent;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bid")
    @OrderBy("createdate")
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Transient
    public String getContractNameStr() {
        return contractNameStr;
    }

    public void setContractNameStr(String contractNameStr) {
        this.contractNameStr = contractNameStr;
    }

    @Transient
    public String getProjectManagerStr() {
        return projectManagerStr;
    }

    public void setProjectManagerStr(String projectManagerStr) {
        this.projectManagerStr = projectManagerStr;
    }

    @Transient
    public String getApplyUnit() {
        return applyUnit;
    }

    public void setApplyUnit(String applyUnit) {
        this.applyUnit = applyUnit;
    }

    @Transient
    public String getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(String totalFunds) {
        this.totalFunds = totalFunds;
    }

    @Transient
    public String getSigningTime() {
        return signingTime;
    }

    public void setSigningTime(String signingTime) {
        this.signingTime = signingTime;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
