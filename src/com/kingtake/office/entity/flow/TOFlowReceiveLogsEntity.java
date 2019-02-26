package com.kingtake.office.entity.flow;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 阅批单接收人
 * @author onlineGenerator
 * @date 2015-07-20 18:57:25
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_flow_receive_logs", schema = "")
@SuppressWarnings("serial")
public class TOFlowReceiveLogsEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;

    /** 关联收发文业务主键 */
    @Excel(name = "关联收发文业务主键")
    private java.lang.String sendReceiveId;
    /** 发送主键 */
    @Excel(name = "发送记录主键")
    private java.lang.String toFlowSendId;
    /** 接收人id */
    @Excel(name = "接收人id")
    private java.lang.String receiveUserid;
    /** 接收人姓名 */
    @Excel(name = "接收人姓名")
    private java.lang.String receiveUsername;
    /** 接收人部门id */
    @Excel(name = "接收人部门id")
    private java.lang.String receiveDepartid;
    /** 接收人部门名称 */
    @Excel(name = "接收人部门名称")
    private java.lang.String receiveDepartname;
    /** 处理时间 */
    @Excel(name = "处理时间")
    private java.util.Date operateTime;
    /** 处理状态（0：未处理；1：已处理） */
    @Excel(name = "处理状态（0：未处理；1：已处理）")
    private java.lang.String operateStatus;
    /** 意见类型 */
    @Excel(name = "意见类型")
    private java.lang.String suggestionType;
    /** 意见内容 */
    @Excel(name = "意见内容")
    private java.lang.String suggestionContent;
    /** 有效标志 */
    @Excel(name = "有效标志")
    private String validFlag;
    /** 意见编码（0：驳回；1：同意） */
    @Excel(name = "意见编码（0：驳回；1：同意）")
    private String suggestionCode;
    /** 是否为传阅接收人 */
    @Excel(name = "是否为传阅接收人")
    private java.lang.String ifcirculate;
    /** 签收状态（0：未签收；1：已签收） */
    @Excel(name = "签收状态（0：未签收；1：已签收）")
    private String signInFlag;
    /** 签收时间 */
    @Excel(name = "签收时间")
    private Date signInTime;
    

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
     * @return: java.lang.String 关联收发文业务主键
     */
    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "SEND_RECEIVE_ID")
    @Column(name = "SEND_RECEIVE_ID", nullable = true, length = 32)
    public java.lang.String getSendReceiveId() {
        return this.sendReceiveId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联收发文业务主键
     */
    public void setSendReceiveId(java.lang.String sendReceiveId) {
        this.sendReceiveId = sendReceiveId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 发送主键
     */
    @Column(name = "T_O_FLOW_SEND_ID", nullable = true, length = 32)
    public java.lang.String getToFlowSendId() {
        return this.toFlowSendId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 发送主键
     */
    public void setToFlowSendId(java.lang.String toFlowSendId) {
        this.toFlowSendId = toFlowSendId;
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
     * @return: java.lang.String 处理状态（0：未处理；1：已处理）
     */
    @Column(name = "OPERATE_STATUS", nullable = true, length = 1)
    public java.lang.String getOperateStatus() {
        return this.operateStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 处理状态（0：未处理；1：已处理）
     */
    public void setOperateStatus(java.lang.String operateStatus) {
        this.operateStatus = operateStatus;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 意见类型（00：承办单位意见；01：机关部(院)领导阅批；02：校首长阅批……）
     */
    @Column(name = "SUGGESTION_TYPE", nullable = true, length = 2)
    public java.lang.String getSuggestionType() {
        return this.suggestionType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 意见类型（00：承办单位意见；01：机关部(院)领导阅批；02：校首长阅批……）
     */
    public void setSuggestionType(java.lang.String suggestionType) {
        this.suggestionType = suggestionType;
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

    @Column(name = "VALID_FLAG", nullable = true, length = 1)
    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    @Column(name = "SUGGESTION_CODE", nullable = true, length = 1)
    public String getSuggestionCode() {
        return suggestionCode;
    }

    public void setSuggestionCode(String suggestionCode) {
        this.suggestionCode = suggestionCode;
    }

    @Column(name = "IFCIRCULATE", nullable = true, length = 1)
    public java.lang.String getIfcirculate() {
        return ifcirculate;
    }

    public void setIfcirculate(java.lang.String ifcirculate) {
        this.ifcirculate = ifcirculate;
    }

    @Column(name = "SIGN_IN_FLAG", nullable = true, length = 1)
    public String getSignInFlag() {
        return signInFlag;
    }

    public void setSignInFlag(String signInFlag) {
        this.signInFlag = signInFlag;
    }

    @Column(name = "SIGN_IN_TIME", nullable = true, length = 1)
    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }
    
}
