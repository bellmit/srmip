package com.kingtake.project.entity.change;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 项目负责人变更信息表
 * @author onlineGenerator
 * @date 2015-09-02 16:57:09
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_pm_change_projectmanager", schema = "")
@SuppressWarnings("serial")
public class TBPmChangeProjectmanagerEntity implements java.io.Serializable {
    /** id */
    private java.lang.String id;
    /** 项目基_主键 */
    private TPmProjectEntity project;
    /** 变更前项目负责人id */
    @Excel(name = "变更前项目负责人id")
    private TSUser beforeProjectManager;
    /** 变更后项目负责人id */
    @Excel(name = "变更后项目负责人id")
    private TSUser afterProjectManager;
    /** 变更原因 */
    @Excel(name = "变更原因")
    private java.lang.String changeReason;
    /** 变更依据 */
    @Excel(name = "变更依据")
    private java.lang.String changeAccording;
    /** 变更时间 */
    private java.util.Date changeTime;
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
    /** 流程状态 */
    private java.lang.String bpmStatus;

    /**
     * 流程实例id
     */
    private String processInstId;

    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String id
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
     * @param: java.lang.String id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目基_主键
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BEFORE_PROJECT_MANAGER")
    public TSUser getBeforeProjectManager() {
        return beforeProjectManager;
    }

    public void setBeforeProjectManager(TSUser beforeProjectManager) {
        this.beforeProjectManager = beforeProjectManager;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AFTER_PROJECT_MANAGER")
    public TSUser getAfterProjectManager() {
        return afterProjectManager;
    }

    public void setAfterProjectManager(TSUser afterProjectManager) {
        this.afterProjectManager = afterProjectManager;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 变更原因
     */
    @Column(name = "CHANGE_REASON", nullable = true, length = 400)
    public java.lang.String getChangeReason() {
        return this.changeReason;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 变更原因
     */
    public void setChangeReason(java.lang.String changeReason) {
        this.changeReason = changeReason;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 变更依据
     */
    @Column(name = "CHANGE_ACCORDING", nullable = true, length = 100)
    public java.lang.String getChangeAccording() {
        return this.changeAccording;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 变更依据
     */
    public void setChangeAccording(java.lang.String changeAccording) {
        this.changeAccording = changeAccording;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 变更时间
     */
    @Column(name = "CHANGE_TIME", nullable = true)
    public java.util.Date getChangeTime() {
        return this.changeTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 变更时间
     */
    public void setChangeTime(java.util.Date changeTime) {
        this.changeTime = changeTime;
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

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 流程状态
     */
    @Column(name = "BPM_STATUS", nullable = true, length = 1)
    public java.lang.String getBpmStatus() {
        return this.bpmStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 流程状态
     */
    public void setBpmStatus(java.lang.String bpmStatus) {
        this.bpmStatus = bpmStatus;
    }

    @Transient
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bid")
    @OrderBy("createdate")
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }
}
