package com.kingtake.office.entity.approval;

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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Entity
 * @Description: 呈批件信息表
 * @author onlineGenerator
 * @date 2015-08-12 10:45:49
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_approval", schema = "")
@SuppressWarnings("serial")
public class TOApprovalEntity implements java.io.Serializable {
    /** 年度 */
    @Excel(name = "年度")
    private java.lang.String approvalYear;
    /** 文号 */
    @Excel(name = "文号")
    private java.lang.String applicationFileno;
    @FieldDesc("文号前缀")
    @Excel(name = "文号前缀")
    private java.lang.String fileNumPrefix;
    /** 内容 */
    @Excel(name = "内容")
    private java.lang.String applicationContent;
    /** 处理状态 */
    private java.lang.String archiveFlag;
    /** 归档人id */
    private java.lang.String archiveUserid;
    /** 归档人姓名 */
    private java.lang.String archiveUsername;
    /** 归档时间 */
    private java.util.Date archiveDate;
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
    /** 驳回人id */
    private java.lang.String backUserid;
    /** 驳回人姓名 */
    private java.lang.String backUsername;
    /** 驳回人意见 */
    private java.lang.String backSuggestion;
    /** 标题 */
    @Excel(name = "标题")
    private java.lang.String title;
    /** 接收单位id */
    private java.lang.String receiveUnitid;
    /** 接收单位名称 */
    @Excel(name = "接收单位")
    private java.lang.String receiveUnitname;
    /** 落款单位id */
    private java.lang.String signUnitid;
    /** 落款单位名称 */
    @Excel(name = "落款单位")
    private java.lang.String signUnitname;
    /** 主键 */
    private java.lang.String id;
    /** 密级 */
    @Excel(name = "密级", isExportConvert = true)
    private java.lang.String secrityGrade;
    /** 承办单位id */
    private java.lang.String undertakeUnitId;
    /** 承办单位名称 */
    @Excel(name = "承办单位")
    private java.lang.String undertakeUnitName;
    /** 联系人id */
    private java.lang.String contactId;
    /** 联系人姓名 */
    @Excel(name = "联系人")
    private java.lang.String contactName;
    /** 电话 */
    @Excel(name = "联系电话")
    private java.lang.String contactPhone;
    private String businessTablename;

    /**
     * 关联项目id
     */
    private String projectRelaId;

    /**
     * 关联项目名称
     */
    private String projectRelaName;

    /** 收发文登记主键 */
    @FieldDesc("收发文登记主键")
    private String regId;

    /** 收发文标志 */
    @FieldDesc("收发文标志")
    @Excel(name = "收发文标志", isExportConvert = true)
    private java.lang.String registerType;

    /** 校首长阅批 */
    @FieldDesc("校首长阅批")
    @Excel(name = "校首长阅批")
    private java.lang.String leaderReview;
    /** 领导阅批 */
    @FieldDesc("领导阅批")
    @Excel(name = "领导阅批")
    private java.lang.String officeReview;

    /** 正文文件id */
    @FieldDesc("正文文件id")
    private String contentFileId;
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 文号
     */
    @Column(name = "APPLICATION_FILENO", nullable = true, length = 20)
    public java.lang.String getApplicationFileno() {
        return this.applicationFileno;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 文号
     */
    public void setApplicationFileno(java.lang.String applicationFileno) {
        this.applicationFileno = applicationFileno;
    }

    @Column(name = "FILE_NUM_PREFIX", nullable = true, length = 20)
    public java.lang.String getFileNumPrefix() {
        return fileNumPrefix;
    }

    public void setFileNumPrefix(java.lang.String fileNumPrefix) {
        this.fileNumPrefix = fileNumPrefix;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 内容
     */
    @Column(name = "APPLICATION_CONTENT", nullable = true, length = 4000)
    public java.lang.String getApplicationContent() {
        return this.applicationContent;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 内容
     */
    public void setApplicationContent(java.lang.String applicationContent) {
        this.applicationContent = applicationContent;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 处理状态
     */
    @Column(name = "ARCHIVE_FLAG", nullable = true, length = 1)
    public java.lang.String getArchiveFlag() {
        return this.archiveFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 处理状态
     */
    public void setArchiveFlag(java.lang.String archiveFlag) {
        this.archiveFlag = archiveFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 归档人id
     */
    @Column(name = "ARCHIVE_USERID", nullable = true, length = 50)
    public java.lang.String getArchiveUserid() {
        return this.archiveUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 归档人id
     */
    public void setArchiveUserid(java.lang.String archiveUserid) {
        this.archiveUserid = archiveUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 归档人姓名
     */
    @Column(name = "ARCHIVE_USERNAME", nullable = true, length = 50)
    public java.lang.String getArchiveUsername() {
        return this.archiveUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 归档人姓名
     */
    public void setArchiveUsername(java.lang.String archiveUsername) {
        this.archiveUsername = archiveUsername;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 归档时间
     */
    @Column(name = "ARCHIVE_DATE", nullable = true)
    public java.util.Date getArchiveDate() {
        return this.archiveDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 归档时间
     */
    public void setArchiveDate(java.util.Date archiveDate) {
        this.archiveDate = archiveDate;
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
     * @return: java.lang.String 驳回人id
     */
    @Column(name = "BACK_USERID", nullable = true, length = 32)
    public java.lang.String getBackUserid() {
        return this.backUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 驳回人id
     */
    public void setBackUserid(java.lang.String backUserid) {
        this.backUserid = backUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 驳回人姓名
     */
    @Column(name = "BACK_USERNAME", nullable = true, length = 36)
    public java.lang.String getBackUsername() {
        return this.backUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 驳回人姓名
     */
    public void setBackUsername(java.lang.String backUsername) {
        this.backUsername = backUsername;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 驳回人意见
     */
    @Column(name = "BACK_SUGGESTION", nullable = true, length = 100)
    public java.lang.String getBackSuggestion() {
        return this.backSuggestion;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 驳回人意见
     */
    public void setBackSuggestion(java.lang.String backSuggestion) {
        this.backSuggestion = backSuggestion;
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
     * @return: java.lang.String 接收单位id
     */
    @Column(name = "RECEIVE_UNITID", nullable = true, length = 32)
    public java.lang.String getReceiveUnitid() {
        return this.receiveUnitid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收单位id
     */
    public void setReceiveUnitid(java.lang.String receiveUnitid) {
        this.receiveUnitid = receiveUnitid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 接收单位名称
     */
    @Column(name = "RECEIVE_UNITNAME", nullable = true, length = 60)
    public java.lang.String getReceiveUnitname() {
        return this.receiveUnitname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 接收单位名称
     */
    public void setReceiveUnitname(java.lang.String receiveUnitname) {
        this.receiveUnitname = receiveUnitname;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 落款单位id
     */
    @Column(name = "SIGN_UNITID", nullable = true, length = 32)
    public java.lang.String getSignUnitid() {
        return this.signUnitid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 落款单位id
     */
    public void setSignUnitid(java.lang.String signUnitid) {
        this.signUnitid = signUnitid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 落款单位名称
     */
    @Column(name = "SIGN_UNITNAME", nullable = true, length = 60)
    public java.lang.String getSignUnitname() {
        return this.signUnitname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 落款单位名称
     */
    public void setSignUnitname(java.lang.String signUnitname) {
        this.signUnitname = signUnitname;
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
     * @return: java.lang.String 密级
     */
    @Column(name = "SECRITY_GRADE", nullable = true, length = 1)
    public java.lang.String getSecrityGrade() {
        return this.secrityGrade;
    }

    public java.lang.String convertGetSecrityGrade() {
        if (StringUtil.isNotEmpty(this.secrityGrade)) {
            return SrmipConstants.dicStandardMap.get("XMMJ").get(this.secrityGrade).toString();
        }
        return this.secrityGrade;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 密级
     */
    public void setSecrityGrade(java.lang.String secrityGrade) {
        this.secrityGrade = secrityGrade;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 承办单位id
     */
    @Column(name = "UNDERTAKE_UNIT_ID", nullable = true, length = 32)
    public java.lang.String getUndertakeUnitId() {
        return this.undertakeUnitId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 承办单位id
     */
    public void setUndertakeUnitId(java.lang.String undertakeUnitId) {
        this.undertakeUnitId = undertakeUnitId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 承办单位名称
     */
    @Column(name = "UNDERTAKE_UNIT_NAME", nullable = true, length = 60)
    public java.lang.String getUndertakeUnitName() {
        return this.undertakeUnitName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 承办单位名称
     */
    public void setUndertakeUnitName(java.lang.String undertakeUnitName) {
        this.undertakeUnitName = undertakeUnitName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人id
     */
    @Column(name = "CONTACT_ID", nullable = true, length = 32)
    public java.lang.String getContactId() {
        return this.contactId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人id
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人姓名
     */
    @Column(name = "CONTACT_NAME", nullable = true, length = 50)
    public java.lang.String getContactName() {
        return this.contactName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人姓名
     */
    public void setContactName(java.lang.String contactName) {
        this.contactName = contactName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 电话
     */
    @Column(name = "CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 电话
     */
    public void setContactPhone(java.lang.String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 年度
     */
    @Column(name = "APPROVAL_YEAR", nullable = true, length = 4)
    public java.lang.String getApprovalYear() {
        return this.approvalYear;
    }

    @Column(name = "BUSINESS_TABLENAME", nullable = true, length = 500)
    public String getBusinessTablename() {
        return businessTablename;
    }

    public void setBusinessTablename(String businessTablename) {
        this.businessTablename = businessTablename;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 年度
     */
    public void setApprovalYear(java.lang.String approvalYear) {
        this.approvalYear = approvalYear;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 收发文标志
     */
    @Column(name = "REGISTER_TYPE", nullable = true, length = 2)
    public java.lang.String getRegisterType() {
        return this.registerType;
    }

    public java.lang.String convertGetRegisterType() {
        if (StringUtil.isNotEmpty(this.registerType)) {
            return SrmipConstants.dicResearchMap.get("SRFLAG").get(this.registerType).toString();
        }
        return this.registerType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 收发文标志
     */
    public void setRegisterType(java.lang.String registerType) {
        this.registerType = registerType;
    }

    @Column(name = "REG_ID", nullable = true, length = 32)
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 校首长阅批
     */
    @Column(name = "LEADER_REVIEW", nullable = true, length = 100)
    public java.lang.String getLeaderReview() {
        return this.leaderReview;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 校首长阅批
     */
    public void setLeaderReview(java.lang.String leaderReview) {
        this.leaderReview = leaderReview;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 领导阅批
     */
    @Column(name = "OFFICE_REVIEW", nullable = true, length = 100)
    public java.lang.String getOfficeReview() {
        return this.officeReview;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 领导阅批
     */
    public void setOfficeReview(java.lang.String officeReview) {
        this.officeReview = officeReview;
    }

    @Column(name = "CONTENT_FILE_ID", nullable = true, length = 32)
    public String getContentFileId() {
        return contentFileId;
    }

    public void setContentFileId(String contentFileId) {
        this.contentFileId = contentFileId;
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

    @Column(name = "project_rela_id")
    public String getProjectRelaId() {
        return projectRelaId;
    }

    public void setProjectRelaId(String projectRelaId) {
        this.projectRelaId = projectRelaId;
    }

    @Column(name = "project_rela_name")
    public String getProjectRelaName() {
        return projectRelaName;
    }

    public void setProjectRelaName(String projectRelaName) {
        this.projectRelaName = projectRelaName;
    }

}
