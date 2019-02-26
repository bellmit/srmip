package com.kingtake.office.entity.meeting;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 会议登记管理
 * @author onlineGenerator
 * @date 2015-07-03 19:01:03
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_meeting", schema = "")
@SuppressWarnings("serial")
@LogEntity("会议登记管理")
public class TOMeetingEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 会议室id */
    @FieldDesc("会议室id")
    private java.lang.String meetingRoomId;
    /** 会议室名称 */
    @FieldDesc("会议室名称")
    @Excel(name = "会议室名称", width = 15)
    private java.lang.String meetingRoomName;
    /** 关联项目id */
    @FieldDesc("关联项目id")
    private java.lang.String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /** 会议议题 */
    @FieldDesc("会议议题")
    @Excel(name = "会议议题", width = 15)
    private java.lang.String meetingTitle;
    /** 会议内容简介 */
    @FieldDesc("会议内容简介")
    @Excel(name = "会议内容简介", width = 30)
    private java.lang.String meetingContent;
    /** 主办单位id */
    @FieldDesc("主办单位id")
    private java.lang.String hostUnitId;
    /** 主办单位名称 */
    @FieldDesc("主办单位名称")
    @Excel(name = "主办单位名称", width = 15)
    private java.lang.String hostUnitName;
    /** 开始时间 */
    @FieldDesc("开始时间")
    @Excel(name = "开始时间", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date beginDate;
    /** 结束时间 */
    @FieldDesc("结束时间")
    @Excel(name = "结束时间", width = 20, exportFormat = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date endDate;
    /** 参会人员id */
    @FieldDesc("参会人员id")
    private java.lang.String attendeesId;
    /** 参会人员姓名 */
    @FieldDesc("参会人员姓名")
    @Excel(name = "参会人员姓名", width = 30)
    private java.lang.String attendeesName;
    /** 与会单位数 */
    @FieldDesc("与会单位数")
    @Excel(name = "与会单位数")
    private java.lang.Integer attendUnitNum;
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注")
    private java.lang.String memo;

    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    private java.lang.String createName;
    /** 创建时间 */
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;

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
     * @return: java.lang.String 会议室id
     */
    @Column(name = "MEETING_ROOM_ID", nullable = true, length = 32)
    public java.lang.String getMeetingRoomId() {
        return this.meetingRoomId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 会议室id
     */
    public void setMeetingRoomId(java.lang.String meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 会议室名称
     */
    @Column(name = "MEETING_ROOM_NAME", nullable = true, length = 30)
    public java.lang.String getMeetingRoomName() {
        return this.meetingRoomName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 会议室名称
     */
    public void setMeetingRoomName(java.lang.String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联项目id
     */
    @Column(name = "PROJECT_ID", nullable = true, length = 32)
    public java.lang.String getProjectId() {
        return this.projectId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联项目id
     */
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 会议议题
     */
    @Column(name = "MEETING_TITLE", nullable = true, length = 200)
    public java.lang.String getMeetingTitle() {
        return this.meetingTitle;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 会议议题
     */
    public void setMeetingTitle(java.lang.String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 会议内容简介
     */
    @Column(name = "MEETING_CONTENT", nullable = true, length = 2000)
    public java.lang.String getMeetingContent() {
        return this.meetingContent;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 会议内容简介
     */
    public void setMeetingContent(java.lang.String meetingContent) {
        this.meetingContent = meetingContent;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主办单位id
     */
    @Column(name = "HOST_UNIT_ID", nullable = true, length = 32)
    public java.lang.String getHostUnitId() {
        return this.hostUnitId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主办单位id
     */
    public void setHostUnitId(java.lang.String hostUnitId) {
        this.hostUnitId = hostUnitId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主办单位名称
     */
    @Column(name = "HOST_UNIT_NAME", nullable = true, length = 60)
    public java.lang.String getHostUnitName() {
        return this.hostUnitName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主办单位名称
     */
    public void setHostUnitName(java.lang.String hostUnitName) {
        this.hostUnitName = hostUnitName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 开始时间
     */
    @Column(name = "BEGIN_DATE", nullable = true)
    public java.util.Date getBeginDate() {
        return this.beginDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 开始时间
     */
    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 结束时间
     */
    @Column(name = "END_DATE", nullable = true)
    public java.util.Date getEndDate() {
        return this.endDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 结束时间
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 参会人员id
     */
    @Column(name = "ATTENDEES_ID", nullable = true, length = 1000)
    public java.lang.String getAttendeesId() {
        return this.attendeesId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 参会人员id
     */
    public void setAttendeesId(java.lang.String attendeesId) {
        this.attendeesId = attendeesId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 参会人员姓名
     */
    @Column(name = "ATTENDEES_NAME", nullable = true, length = 1000)
    public java.lang.String getAttendeesName() {
        return this.attendeesName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 参会人员姓名
     */
    public void setAttendeesName(java.lang.String attendeesName) {
        this.attendeesName = attendeesName;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 与会单位数
     */
    @Column(name = "ATTEND_UNIT_NUM", nullable = true, length = 2)
    public java.lang.Integer getAttendUnitNum() {
        return this.attendUnitNum;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 与会单位数
     */
    public void setAttendUnitNum(java.lang.Integer attendUnitNum) {
        this.attendUnitNum = attendUnitNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    @Column(name = "MEMO", nullable = true, length = 200)
    public java.lang.String getMemo() {
        return this.memo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 创建人
     */

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 创建人
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 创建人姓名
     */

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 创建人姓名
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 创建时间
     */

    @Column(name = "CREATE_DATE", nullable = true)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 创建时间
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 修改人
     */

    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 修改人
     */
    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 修改人姓名
     */

    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 修改人姓名
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 修改时间
     */

    @Column(name = "UPDATE_DATE", nullable = true)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 修改时间
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
