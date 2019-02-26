package com.kingtake.project.entity.systemfile;

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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 体系文件信息表
 * @author onlineGenerator
 * @date 2015-08-20 09:32:29
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_quality_system_file", schema = "")
@SuppressWarnings("serial")
public class TPmQualitySystemFileEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 名称 */
    @Excel(name = "名称")
    private java.lang.String fileName;
    /** 发布时间 */
    @Excel(name = "发布时间")
    private java.util.Date releaseTime;
    /** 实施时间 */
    @Excel(name = "实施时间")
    private java.util.Date executeTime;
    /** 册数 */
    @Excel(name = "册数")
    private java.lang.String volumeNum;
    /** 出版年份 */
    @Excel(name = "出版年份")
    private java.lang.String publishYear;
    /** 编制人id */
    private java.lang.String writingUserid;
    /** 编制人姓名 */
    @Excel(name = "编制人姓名")
    private java.lang.String writingUsername;
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
    @Column(name = "FILE_NAME", nullable = true, length = 80)
    public java.lang.String getFileName() {
        return this.fileName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 名称
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 发布时间
     */
    @Column(name = "RELEASE_TIME", nullable = true)
    public java.util.Date getReleaseTime() {
        return this.releaseTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 发布时间
     */
    public void setReleaseTime(java.util.Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 实施时间
     */
    @Column(name = "EXECUTE_TIME", nullable = true)
    public java.util.Date getExecuteTime() {
        return this.executeTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 实施时间
     */
    public void setExecuteTime(java.util.Date executeTime) {
        this.executeTime = executeTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 册数
     */
    @Column(name = "VOLUME_NUM", nullable = true, length = 10)
    public java.lang.String getVolumeNum() {
        return this.volumeNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 册数
     */
    public void setVolumeNum(java.lang.String volumeNum) {
        this.volumeNum = volumeNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 出版年份
     */
    @Column(name = "PUBLISH_YEAR", nullable = true, length = 4)
    public java.lang.String getPublishYear() {
        return this.publishYear;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 出版年份
     */
    public void setPublishYear(java.lang.String publishYear) {
        this.publishYear = publishYear;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 编制人id
     */
    @Column(name = "WRITING_USERID", nullable = true, length = 32)
    public java.lang.String getWritingUserid() {
        return this.writingUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 编制人id
     */
    public void setWritingUserid(java.lang.String writingUserid) {
        this.writingUserid = writingUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 编制人姓名
     */
    @Column(name = "WRITING_USERNAME", nullable = true, length = 50)
    public java.lang.String getWritingUsername() {
        return this.writingUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 编制人姓名
     */
    public void setWritingUsername(java.lang.String writingUsername) {
        this.writingUsername = writingUsername;
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
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
}
