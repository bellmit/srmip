package com.kingtake.officeonline.entity;

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

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: office模板文件
 * @author onlineGenerator
 * @date 2015-12-21 14:09:18
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_s_office_model_files", schema = "")
@SuppressWarnings("serial")
public class TSOfficeModelFilesEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    private String codeDetailId;// 公文种类主键
    private String extend;// 扩展名
    /** 存储路径 */
    @Excel(name = "存储路径")
    private java.lang.String realpath;
    /** 文件主键 */
    private java.lang.String templateFileId;
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
    /** 模板名称 */
    private java.lang.String modelName;

    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

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

    @Column(name = "CODE_DETAIL_ID")
    public String getCodeDetailId() {
        return codeDetailId;
    }

    public void setCodeDetailId(String codeDetailId) {
        this.codeDetailId = codeDetailId;
    }

    @Column(name = "extend", length = 32)
    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 存储路径
     */
    @Column(name = "REALPATH", nullable = true, length = 100)
    public java.lang.String getRealpath() {
        return this.realpath;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 存储路径
     */
    public void setRealpath(java.lang.String realpath) {
        this.realpath = realpath;
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
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 文件主键
     */
    @Column(name = "TEMPLATE_FILE_ID", nullable = true, length = 32)
    public java.lang.String getTemplateFileId() {
        return templateFileId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 文件主键
     */
    public void setTemplateFileId(java.lang.String templateFileId) {
        this.templateFileId = templateFileId;
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

    @Column(name = "MODEL_NAME", nullable = true)
    public java.lang.String getModelName() {
        return modelName;
    }

    public void setModelName(java.lang.String modelName) {
        this.modelName = modelName;
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
