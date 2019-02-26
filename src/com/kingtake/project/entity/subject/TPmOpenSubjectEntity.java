package com.kingtake.project.entity.subject;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 开题信息
 * @author onlineGenerator
 * @date 2015-07-16 15:00:51
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_pm_open_subject", schema = "")
@SuppressWarnings("serial")
@LogEntity("开题信息")
public class TPmOpenSubjectEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 项目基_主键 */
    private java.lang.String tpId;
    /** 项目实体 */
    private TPmProjectEntity project;
    /** 关键技术指标 */
    @Excel(name = "关键技术指标")
    @FieldDesc("关键技术指标")
    private java.lang.String technicalIndicator;
    /** 主要研究内容 */
    @Excel(name = "主要研究内容")
    @FieldDesc("主要研究内容")
    private java.lang.String researchContents;
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
    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;

    /**
     * 流程状态
     */
    private String bpmStatus;

    /**
     * 流程实例
     */
    private String processInstId;
    
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

    @Transient
    public java.lang.String getTpId() {
        return tpId;
    }

    public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    @ManyToOne
    @JsonIgnore(true)
    @JoinColumn(name = "T_P_PROJECT_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 关键技术指标
     */
    @Column(name = "TECHNICAL_INDICATOR", nullable = true, length = 800)
    public java.lang.String getTechnicalIndicator() {
        return this.technicalIndicator;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 关键技术指标
     */
    public void setTechnicalIndicator(java.lang.String technicalIndicator) {
        this.technicalIndicator = technicalIndicator;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主要研究内容
     */
    @Column(name = "RESEARCH_CONTENTS", nullable = true, length = 800)
    public java.lang.String getResearchContents() {
        return this.researchContents;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 主要研究内容
     */
    public void setResearchContents(java.lang.String researchContents) {
        this.researchContents = researchContents;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
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

    @Column(name = "bpm_status")
    public String getBpmStatus() {
        return bpmStatus;
    }

    public void setBpmStatus(String bpmStatus) {
        this.bpmStatus = bpmStatus;
    }

    @Transient
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
}
