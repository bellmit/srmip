package com.kingtake.base.entity.serial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 流水号信息表
 * @author onlineGenerator
 * @date 2015-10-07 14:57:20
 * @version V1.0
 *
 */
@Entity
@Table(name = "T_S_SERIAL_NUMBER")
@SuppressWarnings("serial")
public class TSSerialNumberEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 创建人名称 */
    private java.lang.String createName;
    /** 创建人登录名称 */
    private java.lang.String createBy;
    /** 创建日期 */
    private java.util.Date createDate;
    /** 更新人名称 */
    private java.lang.String updateName;
    /** 更新人登录名称 */
    private java.lang.String updateBy;
    /** 更新日期 */
    private java.util.Date updateDate;
    /** 关联业务编码 */
    @Excel(name = "关联业务编码")
    private java.lang.String businessCode;
    /** 下一位流水号 */
    @Excel(name = "下一位流水号")
    private java.lang.String nextNumber;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
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
     * @return: java.lang.String 创建人名称
     */
    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 创建人名称
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创建人登录名称
     */
    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 创建人登录名称
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 创建日期
     */
    @Column(name = "CREATE_DATE", nullable = true, length = 20)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 创建日期
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 更新人名称
     */
    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 更新人名称
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 更新人登录名称
     */
    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 更新人登录名称
     */
    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 更新日期
     */
    @Column(name = "UPDATE_DATE", nullable = true, length = 20)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 更新日期
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联业务编码
     */
    @Column(name = "BUSINESS_CODE", nullable = false, length = 32)
    public java.lang.String getBusinessCode() {
        return this.businessCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联业务编码
     */
    public void setBusinessCode(java.lang.String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 下一位流水号
     */
    @Column(name = "NEXT_NUMBER", nullable = true, length = 20)
    public java.lang.String getNextNumber() {
        return this.nextNumber;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 下一位流水号
     */
    public void setNextNumber(java.lang.String nextNumber) {
        this.nextNumber = nextNumber;
    }
}
