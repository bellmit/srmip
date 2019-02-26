package com.kingtake.office.entity.message;

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
 * @Description: 系统消息接收人
 * @author onlineGenerator
 * @date 2015-07-08 10:13:05
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_message_read", schema = "")
@SuppressWarnings("serial")
@LogEntity("系统消息接收人")
public class TOMessageReadEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 系统消息主键 */
    @FieldDesc("系统消息主键")
    private java.lang.String toid;
    /** 接收人id */
    @FieldDesc("接收人id")
    private java.lang.String receiverId;
    /** 接收人姓名 */
    @FieldDesc("接收人姓名")
    @Excel(name = "接收人姓名")
    private java.lang.String receiverName;
    /** 阅读标志 */
    @FieldDesc("阅读标志")
    @Excel(name = "阅读标志")
    private java.lang.String readFlag;
    /** 阅读时间 */
    @FieldDesc("阅读时间")
    @Excel(name = "阅读时间")
    private java.util.Date readTime;
    /** 删除标志 */
    @FieldDesc("删除标志")
    private java.lang.String delFlag;
    /** 删除时间 */
    @FieldDesc("删除时间")
    private java.util.Date delTime;

    private String show;

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
     * @return: java.lang.String 系统消_主键
     */
    /*
     * @Column(name ="T_O_ID",nullable=true,length=32) public java.lang.String getTOId(){ return this.tOId; }
     *//**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 系统消_主键
     */
    /*
     * public void setTOId(java.lang.String tOId){ this.tOId = tOId; }
     */
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 接收人id
     */
    @Column(name = "RECEIVER_ID", nullable = true, length = 32)
    public java.lang.String getReceiverId() {
        return this.receiverId;
    }

    @Column(name = "T_O_ID", nullable = true, length = 32)
    public java.lang.String getToid() {
        return toid;
    }

    public void setToid(java.lang.String toid) {
        this.toid = toid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收人id
     */
    public void setReceiverId(java.lang.String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 接收人姓名
     */
    @Column(name = "RECEIVER_NAME", nullable = true, length = 50)
    public java.lang.String getReceiverName() {
        return this.receiverName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收人姓名
     */
    public void setReceiverName(java.lang.String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 阅读标志
     */
    @Column(name = "READ_FLAG", nullable = true, length = 1)
    public java.lang.String getReadFlag() {
        return this.readFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 阅读标志
     */
    public void setReadFlag(java.lang.String readFlag) {
        this.readFlag = readFlag;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 阅读时间
     */
    @Column(name = "READ_TIME", nullable = true)
    public java.util.Date getReadTime() {
        return this.readTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 阅读时间
     */
    public void setReadTime(java.util.Date readTime) {
        this.readTime = readTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 删除标志
     */
    @Column(name = "DEL_FLAG", nullable = true, length = 1)
    public java.lang.String getDelFlag() {
        return this.delFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 删除标志
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

    @Column(name = "SHOW", nullable = true)
    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

}
