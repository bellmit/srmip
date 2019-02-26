package com.kingtake.project.entity.dbimport;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;


@Entity
@Table(name = "t_pm_dbimport", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmDBImportEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;

    /**
     * 导入用户名称
     */
    private String impUserName;
    /**
     * 导入用户id
     */
    private String impUserId;

    private java.util.Date impTime;

    private String fileName;

    private String filePath;

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

    @Column(name = "imp_user_name")
    public String getImpUserName() {
        return impUserName;
    }

    public void setImpUserName(String impUserName) {
        this.impUserName = impUserName;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    @Column(name = "imp_time")
    public java.util.Date getImpTime() {
        return impTime;
    }

    public void setImpTime(java.util.Date impTime) {
        this.impTime = impTime;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "file_path")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column(name = "IMP_USER_ID")
    public String getImpUserId() {
        return impUserId;
    }

    public void setImpUserId(String impUserId) {
        this.impUserId = impUserId;
    }
}
