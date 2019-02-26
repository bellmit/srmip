package com.kingtake.office.entity.sendReceive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 收发文登记历史版本
 * @author onlineGenerator
 * @date 2015-07-15 19:36:41
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_o_reg_history", schema = "")
@SuppressWarnings("serial")
public class TORegHistoryEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /**
     * 
     * 登记id
     */
    private String regId;
    /** 标题 */
    private String title;
    /** 创建用户id */
    private String createUserId;
    /** 创建用户名称 */
    private String createUserName;
    /** 创建时间 */
    private Date createTime;
    /** 正文id */
    private String contentFileId;
    /**
     * 附件编码
     */
    private String attachmentCode;

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

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "create_user_name")
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "content_file_id")
    public String getContentFileId() {
        return contentFileId;
    }

    public void setContentFileId(String contentFileId) {
        this.contentFileId = contentFileId;
    }

    @Column(name = "attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Column(name = "reg_id")
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

}
