package com.kingtake.office.page.notice;

import java.util.ArrayList;
import java.util.List;

import com.kingtake.office.entity.notice.TONoticeReceiveEntity;

/**
 * @Title: Entity
 * @Description: 通知公告
 * @author onlineGenerator
 * @date 2015-07-01 15:53:57
 * @version V1.0
 *
 */
public class TONoticePage implements java.io.Serializable {
    /** 保存-通知公告子表 */
    private List<TONoticeReceiveEntity> tONoticeReceiveList = new ArrayList<TONoticeReceiveEntity>();

    public List<TONoticeReceiveEntity> getTONoticeReceiveList() {
        return tONoticeReceiveList;
    }

    public void setTONoticeReceiveList(List<TONoticeReceiveEntity> tONoticeReceiveList) {
        this.tONoticeReceiveList = tONoticeReceiveList;
    }

    /** 主键 */
    private java.lang.String id;
    /** 发送人id */
    private java.lang.String senderId;
    /** 发送人 */
    private java.lang.String senderName;
    /** 发送时间 */
    private java.util.Date sendTime;
    /** 标题 */
    private java.lang.String title;
    /** 内容 */
    private java.lang.String content;
    //    /** 关联项目id */
    //    private java.lang.String projId;
    //    /** 项目名称 */
    //    private java.lang.String projName;
    /** 关联文号 */
    private java.lang.String fileNum;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主键
     */
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
    //    public java.lang.String getProjId() {
    //        return this.projId;
    //    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联项目id
     */
    //    public void setProjId(java.lang.String projId) {
    //        this.projId = projId;
    //    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目名称
     */
    //    public java.lang.String getProjName() {
    //        return this.projName;
    //    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目名称
     */
    //    public void setProjName(java.lang.String projName) {
    //        this.projName = projName;
    //    }

    public List<TONoticeReceiveEntity> gettONoticeReceiveList() {
        return tONoticeReceiveList;
    }

    public void settONoticeReceiveList(List<TONoticeReceiveEntity> tONoticeReceiveList) {
        this.tONoticeReceiveList = tONoticeReceiveList;
    }

    public java.lang.String getFileNum() {
        return fileNum;
    }

    public void setFileNum(java.lang.String fileNum) {
        this.fileNum = fileNum;
    }

}
