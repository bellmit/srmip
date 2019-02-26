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
@Table(name = "t_o_send_down_receive", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TOSendDownReceiveEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private java.lang.String id;

    /**
     * 发送表id
     */
    private String sendId;

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
     * 发送接收状态,0 未接收，1 已接收
     */
    private String status;


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

    @Column(name = "receiver_id")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Column(name = "receiver_name")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Column(name = "receive_time")
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    @Column(name = "send_id")
    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

}
