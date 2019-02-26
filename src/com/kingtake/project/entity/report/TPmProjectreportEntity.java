package com.kingtake.project.entity.report;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 项目申报依据
 * @author onlineGenerator
 * @date 2015-07-09 17:34:25
 * @version V1.0
 *
 */

public class TPmProjectreportEntity implements java.io.Serializable {
    /** 相关主键 */
    private String id;
    /** 相关人员 */
    private java.lang.String relatedUserName;
    /** 标题 */
    private java.lang.String title;
    /** 文号 */
    private java.lang.String fileNum;
    /** 时间 */
    private java.util.Date relatedTime;
    /** 查看详情路径 */
    private String viewUrl;
    /** 关联项 */
    private String relatedType;
    /**
     * 项目实体
     */
    private TPmProjectEntity project;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public java.lang.String getRelatedUserName() {
        return relatedUserName;
    }

    public void setRelatedUserName(java.lang.String relatedUserName) {
        this.relatedUserName = relatedUserName;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.util.Date getRelatedTime() {
        return relatedTime;
    }

    public void setRelatedTime(java.util.Date relatedTime) {
        this.relatedTime = relatedTime;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    public java.lang.String getFileNum() {
        return fileNum;
    }

    public void setFileNum(java.lang.String fileNum) {
        this.fileNum = fileNum;
    }

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }

}
