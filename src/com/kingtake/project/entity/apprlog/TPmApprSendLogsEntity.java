package com.kingtake.project.entity.apprlog;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 审批发送记录
 * @author onlineGenerator
 * @date 2015-08-31 16:57:40
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_appr_send_logs", schema = "")
@SuppressWarnings("serial")
public class TPmApprSendLogsEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 审批表主键 */
    @Excel(name = "审批表主键")
    private java.lang.String apprId;
    /** 操作人id */
    @Excel(name = "操作人id")
    private java.lang.String operateUserid;
    /** 操作人姓名 */
    @Excel(name = "操作人姓名")
    private java.lang.String operateUsername;
    /** 操作时间 */
    @Excel(name = "操作时间")
    private java.util.Date operateDate;
    /** 操作人部门id */
    @Excel(name = "操作人部门id")
    private java.lang.String operateDepartid;
    /** 操作人部门名称 */
    @Excel(name = "操作人部门名称")
    private java.lang.String operateDepartname;
    /** 操作人ip地址 */
    @Excel(name = "操作人ip地址")
    private java.lang.String operateIp;
    /** 发送意见 */
    @Excel(name = "发送意见")
    private java.lang.String senderTip;
    /** 意见类型 */
    @Excel(name = "意见类型（00：承办单位意见；01：机关部(院)领导阅批；02：校首长阅批……）")
    private java.lang.String suggestionType;

    @FieldDesc("审批接收信息")
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;

    /** 业务表名 */
    private String tableName;
    
    /**
     * 关联的接收表记录，标识出该发送记录是从哪个接收记录而来，从而能形成节点链.
     */
    private String sourceId;

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
     * @return: java.lang.String 操作人id
     */
    @Column(name = "OPERATE_USERID", nullable = true, length = 32)
    public java.lang.String getOperateUserid() {
        return this.operateUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 操作人id
     */
    public void setOperateUserid(java.lang.String operateUserid) {
        this.operateUserid = operateUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 操作人姓名
     */
    @Column(name = "OPERATE_USERNAME", nullable = true, length = 50)
    public java.lang.String getOperateUsername() {
        return this.operateUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 操作人姓名
     */
    public void setOperateUsername(java.lang.String operateUsername) {
        this.operateUsername = operateUsername;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 操作时间
     */
    @Column(name = "OPERATE_DATE", nullable = true)
    public java.util.Date getOperateDate() {
        return this.operateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 操作时间
     */
    public void setOperateDate(java.util.Date operateDate) {
        this.operateDate = operateDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 操作人部门id
     */
    @Column(name = "OPERATE_DEPARTID", nullable = true, length = 50)
    public java.lang.String getOperateDepartid() {
        return this.operateDepartid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 操作人部门id
     */
    public void setOperateDepartid(java.lang.String operateDepartid) {
        this.operateDepartid = operateDepartid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 操作人部门名称
     */
    @Column(name = "OPERATE_DEPARTNAME", nullable = true, length = 60)
    public java.lang.String getOperateDepartname() {
        return this.operateDepartname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 操作人部门名称
     */
    public void setOperateDepartname(java.lang.String operateDepartname) {
        this.operateDepartname = operateDepartname;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 操作人ip地址
     */
    @Column(name = "OPERATE_IP", nullable = true, length = 20)
    public java.lang.String getOperateIp() {
        return this.operateIp;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 操作人ip地址
     */
    public void setOperateIp(java.lang.String operateIp) {
        this.operateIp = operateIp;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 发送意见
     */
    @Column(name = "SENDER_TIP", nullable = true, length = 100)
    public java.lang.String getSenderTip() {
        return this.senderTip;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 发送意见
     */
    public void setSenderTip(java.lang.String senderTip) {
        this.senderTip = senderTip;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "SEND_ID")
    public List<TPmApprReceiveLogsEntity> getApprReceiveLogs() {
        return apprReceiveLogs;
    }

    public void setApprReceiveLogs(List<TPmApprReceiveLogsEntity> apprReceiveLogs) {
        this.apprReceiveLogs = apprReceiveLogs;
    }

    @Column(name="source_id")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
    
    
}
