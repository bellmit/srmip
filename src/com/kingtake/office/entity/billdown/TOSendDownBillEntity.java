package com.kingtake.office.entity.billdown;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_o_send_down_bill", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TOSendDownBillEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private java.lang.String id;

    /**
     * 发送人id
     */
    private String senderId;

    /**
     * 发送人名称
     */
    private String senderName;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 接收人id
     */
    private String receiverId;

    /**
     * 接收人名称
     */
    private String receiverName;

    /**
     * 接收时间
     */
    private Date receiveTime;

    /**
     * 下达状态，0 未下达，1 已下达
     */
    private String sendStatus;

    /**
     * 阅批单是否显示，1表示显示
     */
    private String flowShow;

    /**
     * 正文是否显示,1表示显示
     */
    private String contentShow;

    /**
     * 附件是否显示，1表示显示
     */
    private String attachementShow;

    /**
     * 发送接收状态,0 待接收，1 已接收
     */
    private String status;

    /**
     * 发文id
     */
    private String billId;

    /**
     * 发文编号
     */
    private String billCode;

    /**
     * 公文标题
     */
    private String title;

    /**
     * 正文id
     */
    private String contentFileId;

    /**
     * 附件编码
     */
    private String attachementCode;

    /**
     * 是否是第一个
     */
    private String isFirst;

    /**
     * 来源id
     */
    private String sourceId;


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

    @Column(name = "sender_id")
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Column(name = "sender_name")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Column(name = "send_time")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "bill_id")
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    @Column(name = "bill_code")
    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content_file_id")
    public String getContentFileId() {
        return contentFileId;
    }

    public void setContentFileId(String contentFileId) {
        this.contentFileId = contentFileId;
    }

    @Column(name = "attachement_code")
    public String getAttachementCode() {
        return attachementCode;
    }

    public void setAttachementCode(String attachementCode) {
        this.attachementCode = attachementCode;
    }

    @Column(name = "isFirst")
    public String getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }

    @Column(name = "source_id")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "RECEIVER_ID")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Column(name = "RECEIVER_NAME")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name = "RECEIVE_TIME")
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Column(name = "FLOW_SHOW")
    public String getFlowShow() {
        return flowShow;
    }

    public void setFlowShow(String flowShow) {
        this.flowShow = flowShow;
    }

    @Column(name = "CONTENT_SHOW")
    public String getContentShow() {
        return contentShow;
    }

    public void setContentShow(String contentShow) {
        this.contentShow = contentShow;
    }

    @Column(name = "ATTACHEMENT_SHOW")
    public String getAttachementShow() {
        return attachementShow;
    }

    public void setAttachementShow(String attachementShow) {
        this.attachementShow = attachementShow;
    }

    @Column(name = "send_status")
    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

}
