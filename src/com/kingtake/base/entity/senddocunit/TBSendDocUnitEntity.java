package com.kingtake.base.entity.senddocunit;

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
 * @Description: 来文单位
 * @author onlineGenerator
 * @date 2015-09-20 16:02:05
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_send_doc_unit", schema = "")
@SuppressWarnings("serial")
@LogEntity("来文单位")
public class TBSendDocUnitEntity implements java.io.Serializable {
    /** 主键 */
	@FieldDesc("主键")
    private java.lang.String id;
    /** 单位全称 */
	@FieldDesc("单位全称")
    @Excel(name = "单位全称")
    private java.lang.String unitName;
    /** 单位简称 */
    @Excel(name = "单位简称")
    @FieldDesc("单位简称")
    private java.lang.String unitShortName;
    /** 联系人 */
    @Excel(name = "联系人")
    @FieldDesc("联系人")
    private java.lang.String contact;
    /** 联系电话 */
    @Excel(name = "联系电话")
    @FieldDesc("联系电话")
    private java.lang.String contactPhone;
    /** 创建人 */
    @FieldDesc("创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @FieldDesc("创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @FieldDesc("创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @FieldDesc("修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @FieldDesc("修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @FieldDesc("修改时间")
    private java.util.Date updateDate;
    @FieldDesc("单位拼音")
    private java.lang.String unitPinyin;

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
     * @return: java.lang.String 单位全称
     */
    @Column(name = "UNIT_NAME", nullable = true, length = 200)
    public java.lang.String getUnitName() {
        return this.unitName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 单位全称
     */
    public void setUnitName(java.lang.String unitName) {
        this.unitName = unitName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 单位简称
     */
    @Column(name = "UNIT_SHORT_NAME", nullable = true, length = 100)
    public java.lang.String getUnitShortName() {
        return this.unitShortName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 单位简称
     */
    public void setUnitShortName(java.lang.String unitShortName) {
        this.unitShortName = unitShortName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人
     */
    @Column(name = "CONTACT", nullable = true, length = 30)
    public java.lang.String getContact() {
        return this.contact;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人
     */
    public void setContact(java.lang.String contact) {
        this.contact = contact;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系电话
     */
    @Column(name = "CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系电话
     */
    public void setContactPhone(java.lang.String contactPhone) {
        this.contactPhone = contactPhone;
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

    @Column(name = "UNIT_PINYIN", nullable = true)
    public java.lang.String getUnitPinyin() {
        return unitPinyin;
    }

    public void setUnitPinyin(java.lang.String unitPinyin) {
        this.unitPinyin = unitPinyin;
    }

}
