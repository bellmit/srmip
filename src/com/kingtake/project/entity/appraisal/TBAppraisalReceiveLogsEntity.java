package com.kingtake.project.entity.appraisal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 成果鉴定接收记录表
 * @author onlineGenerator
 * @date 2016-01-25 19:24:20
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_appraisal_receive_logs")
@SuppressWarnings("serial")
public class TBAppraisalReceiveLogsEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 接收人id */
    private java.lang.String receiveUserid;
    /** 接收人姓名 */
    @Excel(name = "接收人姓名")
    private java.lang.String receiveUsername;
    /** 接收人部门id */
    private java.lang.String receiveDepartid;
    /** 接收人部门名称 */
    @Excel(name = "接收人部门名称")
    private java.lang.String receiveDepartname;
    /** 处理时间 */
    private java.util.Date operateTime;
    /** 处理状态 */
    private java.lang.String operateStatus;
    /** 意见内容 */
    @Excel(name = "意见内容")
    private java.lang.String suggestionContent;
    /** 关联业务主键 */
    private java.lang.String sendReceiveId;
    /** 意见编码 */
    @Excel(name = "意见编码")
    private java.lang.String suggestionCode;

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
     * @return: java.lang.String 接收人id
     */
    @Column(name = "RECEIVE_USERID", nullable = true, length = 32)
    public java.lang.String getReceiveUserid() {
        return this.receiveUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收人id
     */
    public void setReceiveUserid(java.lang.String receiveUserid) {
        this.receiveUserid = receiveUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 接收人姓名
     */
    @Column(name = "RECEIVE_USERNAME", nullable = true, length = 50)
    public java.lang.String getReceiveUsername() {
        return this.receiveUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收人姓名
     */
    public void setReceiveUsername(java.lang.String receiveUsername) {
        this.receiveUsername = receiveUsername;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 接收人部门id
     */
    @Column(name = "RECEIVE_DEPARTID", nullable = true, length = 32)
    public java.lang.String getReceiveDepartid() {
        return this.receiveDepartid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收人部门id
     */
    public void setReceiveDepartid(java.lang.String receiveDepartid) {
        this.receiveDepartid = receiveDepartid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 接收人部门名称
     */
    @Column(name = "RECEIVE_DEPARTNAME", nullable = true, length = 60)
    public java.lang.String getReceiveDepartname() {
        return this.receiveDepartname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收人部门名称
     */
    public void setReceiveDepartname(java.lang.String receiveDepartname) {
        this.receiveDepartname = receiveDepartname;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 处理时间
     */
    @Column(name = "OPERATE_TIME", nullable = true)
    public java.util.Date getOperateTime() {
        return this.operateTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 处理时间
     */
    public void setOperateTime(java.util.Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 处理状态
     */
    @Column(name = "OPERATE_STATUS", nullable = true, length = 1)
    public java.lang.String getOperateStatus() {
        return this.operateStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 处理状态
     */
    public void setOperateStatus(java.lang.String operateStatus) {
        this.operateStatus = operateStatus;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 意见内容
     */
    @Column(name = "SUGGESTION_CONTENT", nullable = true, length = 50)
    public java.lang.String getSuggestionContent() {
        return this.suggestionContent;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 意见内容
     */
    public void setSuggestionContent(java.lang.String suggestionContent) {
        this.suggestionContent = suggestionContent;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联业务主键
     */
    @Column(name = "SEND_RECEIVE_ID", nullable = true, length = 32)
    public java.lang.String getSendReceiveId() {
        return this.sendReceiveId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联业务主键
     */
    public void setSendReceiveId(java.lang.String sendReceiveId) {
        this.sendReceiveId = sendReceiveId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 意见编码
     */
    @Column(name = "SUGGESTION_CODE", nullable = true, length = 1)
    public java.lang.String getSuggestionCode() {
        return this.suggestionCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 意见编码
     */
    public void setSuggestionCode(java.lang.String suggestionCode) {
        this.suggestionCode = suggestionCode;
    }
}
