package com.kingtake.office.entity.schedule;

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
 * @Description: T_O_SCHEDULE
 * @author onlineGenerator
 * @date 2015-07-10 11:16:12
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_schedule", schema = "")
@SuppressWarnings("serial")
@LogEntity("日程")
public class TOScheduleEntity implements java.io.Serializable {
    /** 主键 */
    //@Excel(name = "主键")
    @FieldDesc("主键")
    private java.lang.String id;
    /** 用户id */
    //@Excel(name = "用户id")
    @FieldDesc("用户id")
    private java.lang.String userId;
    /** 用户姓名 */
    //@Excel(name = "用户姓名")
    @FieldDesc("用户姓名")
    private java.lang.String userName;
    /** 开始时间 */
    @Excel(name = "开始时间 ", format = "yyyy-MM-dd HH:mm:ss", orderNum = "5")
    @FieldDesc("开始时间 ")
    private java.util.Date beginTime;
    /** 结束时间 */
    @Excel(name = "结束时间 ", format = "yyyy-MM-dd HH:mm:ss", orderNum = "6")
    @FieldDesc("结束时间 ")
    private java.util.Date endTime;
    /** 标题 */
    @Excel(name = "标题 ", orderNum = "1")
    @FieldDesc("标题 ")
    private java.lang.String title;
    /** 地点 */
    //@Excel(name = "地点 ")
    @FieldDesc("地点 ")
    private java.lang.String address;
    /** 内容 */
    @Excel(name = "内容 ", orderNum = "2")
    @FieldDesc("内容 ")
    private java.lang.String content;
    /** 参与人员 */
    //@Excel(name = "参与人员 ")
    @FieldDesc("参与人员 ")
    private java.lang.String relateUserid;
    /** 参与人员姓名 */
    //@Excel(name = "参与人员姓名  ")
    @FieldDesc("参与人员姓名  ")
    private java.lang.String relateUsername;
    /** 关联项目id */
    //@Excel(name = "关联项目id ")
    @FieldDesc("关联项目id")
    private java.lang.String projectId;
    /** 是否完成 */
    //@Excel(name = "是否完成 ")
    @FieldDesc("是否完成")
    private java.lang.String finishedFlag;
    /** 事项类型 */
    //@Excel(name = "事项类型 ")
    @FieldDesc("事项类型 ")
    private java.lang.String scheduleType;

    /**
     * 来源安排
     */
    private String parentId;

    /**
     * 发送接收标志,1 表示发送，2表示未接收 3表示已接收
     */
    private String sendReceiveFlag;
    /** 公开状态 */
    //@Excel(name = "公开状态")
    @FieldDesc("公开状态")
    private java.lang.String openStatus;
    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人", orderNum = "3")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "4")
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;
    @FieldDesc("颜色")
    private String color;

    /**
     * 提醒时间点
     */
    private String warnTimePoint;

    /**
     * 提醒频率
     */
    private String warnFrequency;

    /**
     * 关联文号
     */
    private String fileNum;

    /**
     * 删除标记，0 未删除，1 已删除
     */
    private String deleteFlag;

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
     * @return: java.lang.String 用户id
     */
    @Column(name = "USER_ID", nullable = false, length = 32)
    public java.lang.String getUserId() {
        return this.userId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 用户id
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 用户姓名
     */
    @Column(name = "USER_NAME", nullable = false, length = 50)
    public java.lang.String getUserName() {
        return this.userName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 用户姓名
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 开始时间
     */
    @Column(name = "BEGIN_TIME", nullable = false)
    public java.util.Date getBeginTime() {
        return this.beginTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 开始时间
     */
    public void setBeginTime(java.util.Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 结束时间
     */
    @Column(name = "END_TIME", nullable = true)
    public java.util.Date getEndTime() {
        return this.endTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 结束时间
     */
    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 标题
     */
    @Column(name = "TITLE", nullable = false, length = 200)
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
     * @return: java.lang.String 地点
     */
    @Column(name = "ADDRESS", nullable = true, length = 150)
    public java.lang.String getAddress() {
        return this.address;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 地点
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 内容
     */
    @Column(name = "CONTENT", nullable = true, length = 800)
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
     * @return: java.lang.String 参与人员
     */
    @Column(name = "RELATE_USERID", nullable = true, length = 1000)
    public java.lang.String getRelateUserid() {
        return this.relateUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 参与人员
     */
    public void setRelateUserid(java.lang.String relateUserid) {
        this.relateUserid = relateUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 参与人员姓名
     */
    @Column(name = "RELATE_USERNAME", nullable = true, length = 1000)
    public java.lang.String getRelateUsername() {
        return this.relateUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 参与人员姓名
     */
    public void setRelateUsername(java.lang.String relateUsername) {
        this.relateUsername = relateUsername;
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
     * @return: java.lang.String 是否完成
     */
    @Column(name = "FINISHED_FLAG", nullable = false, length = 1)
    public java.lang.String getFinishedFlag() {
        return this.finishedFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 是否完成
     */
    public void setFinishedFlag(java.lang.String finishedFlag) {
        this.finishedFlag = finishedFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 事项类型
     */
    @Column(name = "SCHEDULE_TYPE", nullable = false, length = 2)
    public java.lang.String getScheduleType() {
        return this.scheduleType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 事项类型
     */
    public void setScheduleType(java.lang.String scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 公开状态
     */
    @Column(name = "OPEN_STATUS", nullable = false, length = 1)
    public java.lang.String getOpenStatus() {
        return this.openStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 公开状态
     */
    public void setOpenStatus(java.lang.String openStatus) {
        this.openStatus = openStatus;
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
    
    @Column(name = "COLOR", nullable = true, length = 20)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Transient
    public String getSendReceiveFlag() {
        return sendReceiveFlag;
    }

    public void setSendReceiveFlag(String sendReceiveFlag) {
        this.sendReceiveFlag = sendReceiveFlag;
    }

    @Column(name = "warn_time_point")
    public String getWarnTimePoint() {
        return warnTimePoint;
    }

    public void setWarnTimePoint(String warnTimePoint) {
        this.warnTimePoint = warnTimePoint;
    }

    @Column(name = "warn_frequency")
    public String getWarnFrequency() {
        return warnFrequency;
    }

    public void setWarnFrequency(String warnFrequency) {
        this.warnFrequency = warnFrequency;
    }

    @Column(name = "fileNum")
    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    @Column(name = "delete_flag")
    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
}
