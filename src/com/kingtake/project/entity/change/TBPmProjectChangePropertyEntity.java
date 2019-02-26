package com.kingtake.project.entity.change;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 项目变更子表
 * @author onlineGenerator
 * @date 2015-09-02 16:57:09
 * @version V1.0
 * 
 */
@Entity
@Table(name = "T_B_PM_PROJECT_CHANGE_PROPERTY")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TBPmProjectChangePropertyEntity implements java.io.Serializable {
    /** id */
    private java.lang.String id;
    /**
     * 字段名
     */
    private String propertyName;
    /**
     * 旧值
     */
    private String oldValue;
    /**
     * 新值
     */
    private String newValue;

    /**
     * 主表id
     */
    private String changeId;

    /**
     * 字段类型
     */
    private String propertyType;

    /**
     * 拓展旧值
     */
    private String extOldValue;
    /**
     * 拓展新值
     */
    private String extNewValue;

    /**
     * 字段描述
     */
    private String propertyDesc;

    /**
     * 是否显示，1 是，0 否
     */
    private String showFlag = "1";


    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String id
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
     * @param: java.lang.String id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "PROPERTY_NAME")
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Column(name = "OLD_VALUE")
    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    @Column(name = "NEW_VALUE")
    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @Column(name = "CHANGE_ID")
    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    @Column(name = "property_type")
    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    @Column(name = "EXT_OLD_VALUE")
    public String getExtOldValue() {
        return extOldValue;
    }

    public void setExtOldValue(String extOldValue) {
        this.extOldValue = extOldValue;
    }

    @Column(name = "EXT_NEW_VALUE")
    public String getExtNewValue() {
        return extNewValue;
    }

    public void setExtNewValue(String extNewValue) {
        this.extNewValue = extNewValue;
    }

    @Column(name = "PROPERTY_DESC")
    public String getPropertyDesc() {
        return propertyDesc;
    }

    public void setPropertyDesc(String propertyDesc) {
        this.propertyDesc = propertyDesc;
    }

    @Column(name = "show_flag")
    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

}
