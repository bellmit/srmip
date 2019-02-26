package com.kingtake.project.entity.apprlog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 审批接受记录
 * @author onlineGenerator
 * @date 2015-08-31 16:58:05
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_appr_receive_logs", schema = "")
@SuppressWarnings("serial")
public class TPmApprReceiveLogsEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 审批表主键 */
    @Excel(name = "审批表主键")
    private java.lang.String apprId;
    /** 发送表主键 */
    @Excel(name = "发送表主键")
    private TPmApprSendLogsEntity apprSendLog;
    /** 意见类型（00：承办单位意见；01：机关部(院)领导阅批；02：校首长阅批……） */
    @Excel(name = "意见类型（00：承办单位意见；01：机关部(院)领导阅批；02：校首长阅批……）")
    private java.lang.String suggestionType;
    @Excel(name = "处理类型")
    private java.lang.String handlerType;
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
    /** 处理状态（0：未处理；1：已处理） */
    @Excel(name = "处理状态（0：未处理；1：已处理）")
    private java.lang.String operateStatus;
    /** 有效标志 */
    @Excel(name = "有效标志")
    private java.lang.String validFlag;
    /** 处理时间 */
    @Excel(name = "处理时间")
    private java.util.Date operateTime;
    /** 意见编码（0：驳回；1：同意） */
    @Excel(name = "意见编码（0：驳回；1：同意）")
    private java.lang.String suggestionCode;
    /** 意见内容 */
    @Excel(name = "意见内容")
    private java.lang.String suggestionContent;
    /** 业务表名 */
    private String tableName;
    private String rebutFlag;
    
    /**
     * 审批节点名称
     */
    private String suggestionTypeName;
    
    /**
     * 接收时间
     */
    private Date receiveTime;

    /**
     * 方法：获取业务表名
     * 
     * @return
     */
    @Column(name = "TABLE_NAME", nullable = true, length = 50)
    public String getTableName() {
        return tableName;
    }

    /**
     * 方法：设置业务表名
     * 
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    
    @Column(name = "REBUT_FLAG", nullable = true, length = 1)
    public String getRebutFlag() {
		return rebutFlag;
	}

	public void setRebutFlag(String rebutFlag) {
		this.rebutFlag = rebutFlag;
	}

	/**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 意见编码（0：驳回；1：同意）
     */
    @Column(name = "SUGGESTION_CODE", nullable = true, length = 1)
    public java.lang.String getSuggestionCode() {
        return this.suggestionCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 意见编码（0：驳回；1：同意）
     */
    public void setSuggestionCode(java.lang.String suggestionCode) {
        this.suggestionCode = suggestionCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审批表主键
     */
    @Column(name = "APPR_ID", nullable = true, length = 32)
    public java.lang.String getApprId() {
        return this.apprId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审批表主键
     */
    public void setApprId(java.lang.String apprId) {
        this.apprId = apprId;
    }

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
    @Column(name = "SUGGESTION_TYPE", nullable = true, length = 4)
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
     * @return: java.lang.String 意见类型（00：承办单位意见；01：机关部(院)领导阅批；02：校首长阅批……）
     */
    @Column(name = "HANDLER_TYPE", nullable = true, length = 2)
    public java.lang.String getHandlerType() {
        return this.handlerType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 意见类型（00：承办单位意见；01：机关部(院)领导阅批；02：校首长阅批……）
     */
    public void setHandlerType(java.lang.String handlerType) {
        this.handlerType = handlerType;
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
     * @return: java.lang.String 有效标志
     */
    @Column(name = "VALID_FLAG", nullable = true, length = 1)
    public java.lang.String getValidFlag() {
        return this.validFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 有效标志
     */
    public void setValidFlag(java.lang.String validFlag) {
        this.validFlag = validFlag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEND_ID")
    public TPmApprSendLogsEntity getApprSendLog() {
        return apprSendLog;
    }

    public void setApprSendLog(TPmApprSendLogsEntity apprSendLog) {
        this.apprSendLog = apprSendLog;
    }

    @Transient
    public String getSuggestionTypeName() {
        return suggestionTypeName;
    }

    public void setSuggestionTypeName(String suggestionTypeName) {
        this.suggestionTypeName = suggestionTypeName;
    }

    @Column(name="receive_time")
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
    
    

}
