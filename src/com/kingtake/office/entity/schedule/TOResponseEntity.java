package com.kingtake.office.entity.schedule;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;

/**
 * 回复
 * 
 * @author admin
 * 
 */
@Entity
@Table(name = "t_o_response", schema = "")
@SuppressWarnings("serial")
@LogEntity("回复")
public class TOResponseEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;

    /**
     * 回复时间
     */
    private Date resTime;
    /** 用户人id */
    private java.lang.String resUserId;
    /** 回复人姓名 */
    private java.lang.String resUserName;
    /**
     * 回复关联id
     */
    private java.lang.String resSourceId;
    /**
     * 回复类型
     */
    private String resType;

    /**
     * 回复内容
     */
    private String resContent;

    /**
     * 回复的标题
     */
    private String resTitle;

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

    @Column(name = "res_time")
    public Date getResTime() {
        return resTime;
    }

    public void setResTime(Date resTime) {
        this.resTime = resTime;
    }

    @Column(name = "res_user_id")
    public java.lang.String getResUserId() {
        return resUserId;
    }

    public void setResUserId(java.lang.String resUserId) {
        this.resUserId = resUserId;
    }

    @Column(name = "res_user_name")
    public java.lang.String getResUserName() {
        return resUserName;
    }

    public void setResUserName(java.lang.String resUserName) {
        this.resUserName = resUserName;
    }

    @Column(name = "res_source_id")
    public java.lang.String getResSourceId() {
        return resSourceId;
    }

    public void setResSourceId(java.lang.String resSourceId) {
        this.resSourceId = resSourceId;
    }

    @Column(name = "res_type")
    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    @Column(name = "res_content")
    public String getResContent() {
        return resContent;
    }

    public void setResContent(String resContent) {
        this.resContent = resContent;
    }

    @Transient
    public String getResTitle() {
        return resTitle;
    }

    public void setResTitle(String resTitle) {
        this.resTitle = resTitle;
    }

}
