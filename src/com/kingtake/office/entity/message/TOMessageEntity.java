package com.kingtake.office.entity.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;

/**
 * @Title: Entity
 * @Description: 系统消息
 * @author onlineGenerator
 * @date 2015-07-07 17:03:31
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_message", schema = "")
@SuppressWarnings("serial")
@LogEntity("系统消息")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TOMessageEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 发送人id */
    @FieldDesc("发送人id")
    private java.lang.String senderId;
    /** 发送人姓名 */
    @FieldDesc("发送人姓名")
    private java.lang.String senderName;
    /** 发送时间 */
    @FieldDesc("发送时间")
    private java.util.Date sendTime;
    /** 标题 */
    @FieldDesc("标题")
    private java.lang.String title;
    /** 内容 */
    @FieldDesc("内容")
    private java.lang.String content;
    /** 发送人删除标志 */
    @FieldDesc("发送人删除标志")
    private java.lang.String delFlag;
    /** 删除时间 */
    @FieldDesc("删除时间")
    private java.util.Date delTime;

    /***
     * 消息类型
     */
    private String type;
    
    /**
     * 执行sql
     */
    private String executeSql;

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
     * @return: java.lang.String 发送人id
     */

    @Column(name = "SENDER_ID", nullable = true, length = 32)
    public java.lang.String getSenderId() {
        return this.senderId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 发送人id
     */
    public void setSenderId(java.lang.String senderId) {
        this.senderId = senderId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 发送人姓名
     */

    @Column(name = "SENDER_NAME", nullable = true, length = 50)
    public java.lang.String getSenderName() {
        return this.senderName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 发送人姓名
     */
    public void setSenderName(java.lang.String senderName) {
        this.senderName = senderName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 发送时间
     */

    @Column(name = "SEND_TIME", nullable = true)
    public java.util.Date getSendTime() {
        return this.sendTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 发送时间
     */
    public void setSendTime(java.util.Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 标题
     */

    @Column(name = "TITLE", nullable = true, length = 200)
    public java.lang.String getTitle() {
        return this.title;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 标题
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 内容
     */

    @Column(name = "CONTENT", nullable = true, length = 2000)
    public java.lang.String getContent() {
        return this.content;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 内容
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 发送人删除标志
     */

    @Column(name = "DEL_FLAG", nullable = true, length = 1)
    public java.lang.String getDelFlag() {
        return this.delFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 发送人删除标志
     */
    public void setDelFlag(java.lang.String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 删除时间
     */

    @Column(name = "DEL_TIME", nullable = true)
    public java.util.Date getDelTime() {
        return this.delTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 删除时间
     */
    public void setDelTime(java.util.Date delTime) {
        this.delTime = delTime;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name="execute_sql")
    public String getExecuteSql() {
        return executeSql;
    }

    public void setExecuteSql(String executeSql) {
        this.executeSql = executeSql;
    }

    
}
