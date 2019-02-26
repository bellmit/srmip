package com.kingtake.base.entity.sideccatalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 项目模块关联表
 * @author onlineGenerator
 * @date 2016-01-19 11:30:07
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_pm_project_catalog_rela", schema = "")
@SuppressWarnings("serial")
public class TPmProjectCatalogRelaEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 项目id */
    private java.lang.String projectId;
    /** 模块id */
    private java.lang.String catalogId;
    /** 是否完成 */
    private java.lang.String finishFlag;

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

    @Column(name = "project_id")
    public java.lang.String getProjectId() {
        return projectId;
    }

    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }

    @Column(name = "catalog_id")
    public java.lang.String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(java.lang.String catalogId) {
        this.catalogId = catalogId;
    }

    @Column(name = "finishFlag")
    public java.lang.String getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(java.lang.String finishFlag) {
        this.finishFlag = finishFlag;
    }

}
