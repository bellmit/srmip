package com.kingtake.base.entity.formtip;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 表单填写说明
 * @author onlineGenerator
 * @date 2016-01-19 11:30:07
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_form_tip", schema = "")
@SuppressWarnings("serial")
public class TBFormTipEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 业务编码 */
    @Excel(name = "业务编码")
    private java.lang.String businessCode;

    /** 业务名称 */
    @Excel(name = "业务名称")
    private java.lang.String businessName;

    /**
     * 说明内容
     */
    @Excel(name = "说明内容")
    private String tipContent;

    private java.lang.String createBy;
    private java.lang.String createName;
    private java.util.Date createDate;
    private java.lang.String updateBy;
    private java.lang.String updateName;
    private java.util.Date updateDate;

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

    @Column(name = "business_code")
    public java.lang.String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(java.lang.String businessCode) {
        this.businessCode = businessCode;
    }

    @Column(name = "business_name")
    public java.lang.String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(java.lang.String businessName) {
        this.businessName = businessName;
    }

    @Column(name = "tip_content")
    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "CREATE_BY")
    public java.lang.String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_NAME")
    public java.lang.String getCreateName() {
        return createName;
    }

    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_DATE")
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_BY")
    public java.lang.String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_NAME")
    public java.lang.String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "UPDATE_DATE")
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }
}
