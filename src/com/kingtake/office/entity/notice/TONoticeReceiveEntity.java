package com.kingtake.office.entity.notice;

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
 * @Description: 通知公告子表
 * @author onlineGenerator
 * @date 2015-07-01 15:53:57
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_notice_receive", schema = "")
@SuppressWarnings("serial")
@LogEntity("通知公告接收人")
public class TONoticeReceiveEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 通知公告信息表主键 */
    @FieldDesc("通知公告信息表主键")
    private java.lang.String noticeId;
    /** 接收人id */
    @Excel(name = "接收人id")
    @FieldDesc("接收人id")
    private java.lang.String receiverId;
    /** 接收人姓名 */
    @Excel(name = "接收人姓名")
    @FieldDesc("接收人姓名")
    private java.lang.String receiverName;
    /** 是否阅读 */
    @Excel(name = "是否阅读")
    @FieldDesc("是否阅读")
    private java.lang.String readFlag;
    /** 阅读时间 */
    @Excel(name = "阅读时间")
    @FieldDesc("阅读时间")
    private java.util.Date readTime;

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
     * @return: java.lang.String 通知公告信息表主键
     */
    @Column(name = "NOTICE_ID", nullable = true, length = 32)
    public java.lang.String getNoticeId() {
        return this.noticeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 通知公告信息表主键
     */
    public void setNoticeId(java.lang.String noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 接收人id
     */
    @Column(name = "RECEIVER_ID", nullable = true, length = 32)
    public java.lang.String getReceiverId() {
        return this.receiverId;
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
     * @return: java.lang.String 是否阅读
     */
    @Column(name = "READ_FLAG", nullable = true, length = 1)
    public java.lang.String getReadFlag() {
        return this.readFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 是否阅读
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
}
