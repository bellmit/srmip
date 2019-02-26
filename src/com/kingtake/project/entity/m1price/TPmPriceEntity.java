package com.kingtake.project.entity.m1price;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 报价
 * @author onlineGenerator
 * @date 2015-07-23 16:13:47
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_pm_price", schema = "")
@SuppressWarnings("serial")
public class TPmPriceEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 项目实体 */
    private TPmProjectEntity project;
    /** 项目名称 */
    @Excel(name = "项目名称")
    @FieldDesc("项目名称")
    private java.lang.String projectName;
    /** 标题 */
    @Excel(name = "标题", width = 20)
    private java.lang.String title;
    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间", format = "yyyy-MM-dd")
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
    /** 关联合同id */
    private java.lang.String contractId;
    /** 关联合同名称 */
    @Excel(name = "合同名称")
    private java.lang.String contractName;
    /** 关联合同类型 */
    @Excel(name = "合同类型",isExportConvert=true)
    private java.lang.String contractType;

    /**
     * 关联附件编码
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_P_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @Transient
    public java.lang.String getProjectName() {
        if (this.project != null) {
            projectName = project.getProjectName();
        }
        return projectName;
    }

    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 标题
     */
    @Column(name = "TITLE", nullable = true, length = 200)
    public java.lang.String getTitle() {
        return this.title;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 标题
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
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

    @Column(name = "CONTRACT_ID", nullable = true, length = 32)
    public java.lang.String getContractId() {
        return contractId;
    }

    public void setContractId(java.lang.String contractId) {
        this.contractId = contractId;
    }

    @Column(name = "CONTRACT_NAME", nullable = true, length = 100)
    public java.lang.String getContractName() {
        return contractName;
    }

    public void setContractName(java.lang.String contractName) {
        this.contractName = contractName;
    }

    @Column(name = "CONTRACT_TYPE", nullable = true, length = 2)
    public java.lang.String getContractType() {
        return contractType;
    }
    
    public java.lang.String convertGetContractType() {
        String contractTypeStr = "";
        if("i".equals(contractType)){
            contractTypeStr = "进账合同";
        }else{
            contractTypeStr = "出账合同";
        }
        return contractTypeStr;
    }
    
    public void setContractType(java.lang.String contractType) {
        this.contractType = contractType;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

}
