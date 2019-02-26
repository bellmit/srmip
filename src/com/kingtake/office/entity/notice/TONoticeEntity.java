package com.kingtake.office.entity.notice;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 通知公告
 * @author onlineGenerator
 * @date 2015-07-01 15:53:57
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_notice", schema = "")
@SuppressWarnings("serial")
@LogEntity("通知公告")
public class TONoticeEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 发送人id */
    @FieldDesc("发送人id")
    private java.lang.String senderId;
    /** 发送人 */
    @FieldDesc("发送人")
    private java.lang.String senderName;
    /** 发送时间 */
    @FieldDesc("发送时间")
    private java.util.Date sendTime;
    /** 标题 */
    @Excel(name = "标题")
    @FieldDesc("标题")
    private java.lang.String title;
    /** 内容 */
    @FieldDesc("内容")
    private java.lang.String content;
    /** 关联项目id */
    @FieldDesc("关联项目id")
    private java.lang.String projId;
    /** 项目名称 */
    @Excel(name = "项目名称")
    @FieldDesc("项目名称")
    private java.lang.String projName;
    @FieldDesc("收发文号")
    private java.lang.String fileNum;

    @ExcelCollection(name = "接收人信息")
    private List<TONoticeReceiveEntity> tONoticeReceiveEntitys;
    
    /**
     * 附件关联id
     */
    private String attachmentCode;

    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

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
     * @return: java.lang.String 发送人
     */

    @Column(name = "SENDER_NAME", nullable = true, length = 50)
    public java.lang.String getSenderName() {
        return this.senderName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 发送人
     */
    public void setSenderName(java.lang.String senderName) {
        this.senderName = senderName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 发送时间
     */

    @Column(name = "SEND_TIME", nullable = true, length = 20)
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

    @Column(name = "TITLE", nullable = true, length = 100)
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

    @Column(name = "CONTENT", nullable = true, length = 4000)
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
     * @return: java.lang.String 关联项目id
     */

    @Column(name = "PROJ_ID", nullable = true, length = 32)
    public java.lang.String getProjId() {
        return this.projId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联项目id
     */
    public void setProjId(java.lang.String projId) {
        this.projId = projId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目名称
     */

    @Column(name = "PROJ_NAME", nullable = true, length = 4000)
    public java.lang.String getProjName() {
        return this.projName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目名称
     */
    public void setProjName(java.lang.String projName) {
        this.projName = projName;
    }

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "NOTICE_ID")
    public List<TONoticeReceiveEntity> gettONoticeReceiveEntitys() {
        return tONoticeReceiveEntitys;
    }

    public void settONoticeReceiveEntitys(List<TONoticeReceiveEntity> tONoticeReceiveEntitys) {
        this.tONoticeReceiveEntitys = tONoticeReceiveEntitys;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Column(name = "FILE_NUM", nullable = true, length = 20)
    public java.lang.String getFileNum() {
        return fileNum;
    }

    public void setFileNum(java.lang.String fileNum) {
        this.fileNum = fileNum;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    
}
