package com.kingtake.base.entity.xmlb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.service.SystemService;

/**
 * @Title: Entity
 * @Description: t_b_jflx
 * @author onlineGenerator
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_jflx", schema = "")
@SuppressWarnings("serial")
@LogEntity("经费类型表")
public class TBJflxEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 经费类型名称 */
    @FieldDesc("经费类型名称")
    @Excel(name = "经费类型名称", width = 50)
    private java.lang.String jflxmc;
    /** 排序码 */
    @FieldDesc("排序码")
    @Excel(name = "排序码")
    private java.lang.Integer code;
    /**
     * 父类型id
     */
    private TBJflxEntity parentType;

    /**
     * 有效标志,0 无效，1 有效
     */
    private String validFlag;

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
     * @return: java.lang.String 经费类型名称
     */
    @Column(name = "JFLXMC", nullable = true, length = 50)
    public java.lang.String getJflxmc() {
        return this.jflxmc;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 经费类型名称
     */
    public void setJflxmc(java.lang.String jflxmc) {
        this.jflxmc = jflxmc;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 排序码
     */
    @Column(name = "CODE", nullable = true, length = 6)
    public java.lang.Integer getCode() {
        return this.code;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 排序码
     */
    public void setCode(java.lang.Integer code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public TBJflxEntity getParentType() {
        return parentType;
    }

    public void setParentType(TBJflxEntity parentType) {
        this.parentType = parentType;
    }

    @Column(name = "valid_flag")
    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

}
