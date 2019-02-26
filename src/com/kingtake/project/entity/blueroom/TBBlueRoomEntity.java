package com.kingtake.project.entity.blueroom;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 蓝色讲坛信息表
 * @author onlineGenerator
 * @date 2016-01-14 23:11:51
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_blue_room", schema = "")
@SuppressWarnings("serial")
public class TBBlueRoomEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 名称 */
    @Excel(name = "名称")
    private java.lang.String roomName;
    /** 级别 */
    @Excel(name = "级别")
    private java.lang.String roomLevel;
    /** 举办单位id */
    private java.lang.String holdUnitId;
    /** 举办单位名称 */
    @Excel(name = "举办单位名称")
    private java.lang.String holdUnitName;
    /** 主办单位id */
    private java.lang.String hostUnitId;
    /** 主办单位名称 */
    @Excel(name = "主办单位名称")
    private java.lang.String hostUnitName;
    /** 举办地点 */
    @Excel(name = "举办地点")
    private java.lang.String holdAddress;
    /** 活动规模 */
    @Excel(name = "活动规模")
    private java.lang.Integer activityScale;
    /** 开始报告时间 */
    @Excel(name = "开始报告时间")
    private java.util.Date beginReportTime;
    /** 结束报告时间 */
    @Excel(name = "结束报告时间")
    private java.util.Date endReportTime;
    /** 推荐单位id */
    private java.lang.String recommendUnitId;
    /** 推荐单位名称 */
    @Excel(name = "推荐单位名称")
    private java.lang.String recommendUnitName;
    /** 报告人姓名 */
    @Excel(name = "报告人姓名")
    private java.lang.String reporterName;
    /** 担任职务 */
    @Excel(name = "担任职务")
    private java.lang.String postPosition;
    /** 报告内容 */
    @Excel(name = "报告内容")
    private java.lang.String reportContent;
    /** 备注 */
    @Excel(name = "备注")
    private java.lang.String memo;
    /** 状态 */
    @Excel(name = "状态")
    private java.lang.String confirmFlag;
    /** 创建人 */
    @Excel(name = "创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @Excel(name = "修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @Excel(name = "修改时间")
    private java.util.Date updateDate;
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
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

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 名称
     */
    @Column(name = "ROOM_NAME", nullable = true, length = 80)
    public java.lang.String getRoomName() {
        return this.roomName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 名称
     */
    public void setRoomName(java.lang.String roomName) {
        this.roomName = roomName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 级别
     */
    @Column(name = "ROOM_LEVEL", nullable = true, length = 1)
    public java.lang.String getRoomLevel() {
        return this.roomLevel;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 级别
     */
    public void setRoomLevel(java.lang.String roomLevel) {
        this.roomLevel = roomLevel;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 举办单位id
     */
    @Column(name = "HOLD_UNIT_ID", nullable = true, length = 32)
    public java.lang.String getHoldUnitId() {
        return this.holdUnitId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 举办单位id
     */
    public void setHoldUnitId(java.lang.String holdUnitId) {
        this.holdUnitId = holdUnitId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 举办单位名称
     */
    @Column(name = "HOLD_UNIT_NAME", nullable = true, length = 60)
    public java.lang.String getHoldUnitName() {
        return this.holdUnitName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 举办单位名称
     */
    public void setHoldUnitName(java.lang.String holdUnitName) {
        this.holdUnitName = holdUnitName;
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
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 举办地点
     */
    @Column(name = "HOLD_ADDRESS", nullable = true, length = 150)
    public java.lang.String getHoldAddress() {
        return this.holdAddress;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 举办地点
     */
    public void setHoldAddress(java.lang.String holdAddress) {
        this.holdAddress = holdAddress;
    }

    /**
     * 方法: 取得java.lang.Integer
     * 
     * @return: java.lang.Integer 活动规模
     */
    @Column(name = "ACTIVITY_SCALE", nullable = true, length = 6)
    public java.lang.Integer getActivityScale() {
        return this.activityScale;
    }

    /**
     * 方法: 设置java.lang.Integer
     * 
     * @param: java.lang.Integer 活动规模
     */
    public void setActivityScale(java.lang.Integer activityScale) {
        this.activityScale = activityScale;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 开始报告时间
     */
    @Column(name = "BEGIN_REPORT_TIME", nullable = true)
    public java.util.Date getBeginReportTime() {
        return this.beginReportTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 开始报告时间
     */
    public void setBeginReportTime(java.util.Date beginReportTime) {
        this.beginReportTime = beginReportTime;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 结束报告时间
     */
    @Column(name = "END_REPORT_TIME", nullable = true)
    public java.util.Date getEndReportTime() {
        return this.endReportTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 结束报告时间
     */
    public void setEndReportTime(java.util.Date endReportTime) {
        this.endReportTime = endReportTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 推荐单位id
     */
    @Column(name = "RECOMMEND_UNIT_ID", nullable = true, length = 32)
    public java.lang.String getRecommendUnitId() {
        return this.recommendUnitId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 推荐单位id
     */
    public void setRecommendUnitId(java.lang.String recommendUnitId) {
        this.recommendUnitId = recommendUnitId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 推荐单位名称
     */
    @Column(name = "RECOMMEND_UNIT_NAME", nullable = true, length = 60)
    public java.lang.String getRecommendUnitName() {
        return this.recommendUnitName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 推荐单位名称
     */
    public void setRecommendUnitName(java.lang.String recommendUnitName) {
        this.recommendUnitName = recommendUnitName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 报告人姓名
     */
    @Column(name = "REPORTER_NAME", nullable = true, length = 36)
    public java.lang.String getReporterName() {
        return this.reporterName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 报告人姓名
     */
    public void setReporterName(java.lang.String reporterName) {
        this.reporterName = reporterName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 担任职务
     */
    @Column(name = "POST_POSITION", nullable = true, length = 20)
    public java.lang.String getPostPosition() {
        return this.postPosition;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 担任职务
     */
    public void setPostPosition(java.lang.String postPosition) {
        this.postPosition = postPosition;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 报告内容
     */
    @Column(name = "REPORT_CONTENT", nullable = true, length = 500)
    public java.lang.String getReportContent() {
        return this.reportContent;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 报告内容
     */
    public void setReportContent(java.lang.String reportContent) {
        this.reportContent = reportContent;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 备注
     */
    @Column(name = "MEMO", nullable = true, length = 500)
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
     * @return: java.lang.String 状态
     */
    @Column(name = "CONFIRM_FLAG", nullable = true, length = 1)
    public java.lang.String getConfirmFlag() {
        return this.confirmFlag;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 状态
     */
    public void setConfirmFlag(java.lang.String confirmFlag) {
        this.confirmFlag = confirmFlag;
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

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    @Column(name = "attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

}
