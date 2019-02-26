package com.kingtake.project.entity.thesis;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Entity
 * @Description: 论文保密申请信息表
 * @author onlineGenerator
 * @date 2016-01-08 15:56:13
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_thesis_secret", schema = "")
@SuppressWarnings("serial")
public class TBThesisSecretEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 论文题目 */
    @Excel(name = "论文题目")
    private java.lang.String thesisTitle;
    /** 字数 */
    @Excel(name = "字数")
    private java.lang.Integer wordCount;
    /** 所属院 */
    private java.lang.String subordinateDeptId;
    /** 所属院名称 */
    @Excel(name = "所属院名称")
    private java.lang.String subordinateDeptName;
    /** 具体单位 */
    private java.lang.String concreteDeptId;
    /** 具体单位名称 */
    @Excel(name = "具体单位名称")
    private java.lang.String concreteDeptName;
    /** 承办单位名称 */
    @Excel(name = "承办单位及地点")
    private java.lang.String undertakeUnitName;
    /** 密级 */
    @Excel(name = "密级", isExportConvert = true)
    private java.lang.String secretDegree;
    /** 拟发表刊物名称 */
    @Excel(name = "拟发表刊物名称")
    private java.lang.String publicationName;
    /** 拟发表刊物分区级别 */
    @Excel(name = "拟发表刊物分区级别")
    private java.lang.String publicationLevel;
    /** 会议名称 */
    @Excel(name = "会议名称")
    private java.lang.String meetingName;
    /** 征文名称 */
    @Excel(name = "征文名称")
    private java.lang.String articleName;
    /** 征文单位 */
    @Excel(name = "征文单位")
    private java.lang.String articleDepart;
    /** 固定联系电话 */
    private java.lang.String fixTelephone;
    /** 移动联系电话 */
    @Excel(name = "移动电话")
    private java.lang.String mobileTelephone;
    /** 第一作者姓名 */
    @Excel(name = "第一作者")
    private java.lang.String firstAuthor;
    /** 其他作者姓名 */
    private java.lang.String otherAuthor;
    /** 备注 */
    private java.lang.String memo;
    /** 论文内容要点 */
    @Excel(name = "论文内容要点")
    private java.lang.String thesisContentKey;
    /** 审查号 */
    @Excel(name = "审查号")
    private java.lang.String reviewNumber;
    /** 流程处理状态 */
    private java.lang.String bpmStatus;
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
    /** 审查状态（0:未审查,1:已审查） */
    private java.lang.String checkFlag;
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
    
    /**
     * 审查专家
     */
    private String checkExpert;

    /**
     * 附件关联编码
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
     * @return: java.lang.String 论文题目
     */
    @Column(name = "THESIS_TITLE", nullable = true, length = 200)
    public java.lang.String getThesisTitle() {
        return this.thesisTitle;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 论文题目
     */
    public void setThesisTitle(java.lang.String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    /**
     * 方法: 取得java.lang.Integer
     * 
     * @return: java.lang.Integer 字数
     */
    @Column(name = "WORD_COUNT", nullable = true, length = 8)
    public java.lang.Integer getWordCount() {
        return this.wordCount;
    }

    /**
     * 方法: 设置java.lang.Integer
     * 
     * @param: java.lang.Integer 字数
     */
    public void setWordCount(java.lang.Integer wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 所属院
     */
    @Column(name = "SUBORDINATE_DEPT_ID", nullable = true, length = 32)
    public java.lang.String getSubordinateDeptId() {
        return this.subordinateDeptId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 所属院
     */
    public void setSubordinateDeptId(java.lang.String subordinateDeptId) {
        this.subordinateDeptId = subordinateDeptId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 所属院名称
     */
    @Column(name = "SUBORDINATE_DEPT_NAME", nullable = true, length = 32)
    public java.lang.String getSubordinateDeptName() {
        return this.subordinateDeptName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 所属院名称
     */
    public void setSubordinateDeptName(java.lang.String subordinateDeptName) {
        this.subordinateDeptName = subordinateDeptName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 具体单位
     */
    @Column(name = "CONCRETE_DEPT_ID", nullable = true, length = 32)
    public java.lang.String getConcreteDeptId() {
        return this.concreteDeptId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 具体单位
     */
    public void setConcreteDeptId(java.lang.String concreteDeptId) {
        this.concreteDeptId = concreteDeptId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 具体单位名称
     */
    @Column(name = "CONCRETE_DEPT_NAME", nullable = true, length = 32)
    public java.lang.String getConcreteDeptName() {
        return this.concreteDeptName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 具体单位名称
     */
    public void setConcreteDeptName(java.lang.String concreteDeptName) {
        this.concreteDeptName = concreteDeptName;
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
     * @return: java.lang.String 密级
     */
    @Column(name = "SECRET_DEGREE", nullable = true, length = 1)
    public java.lang.String getSecretDegree() {
        return this.secretDegree;
    }

    public java.lang.String convertGetSecretDegree() {
        if (StringUtil.isNotEmpty(this.secretDegree)) {
            return SrmipConstants.dicStandardMap.get("XMMJ").get(this.secretDegree).toString();
        }
        return this.secretDegree;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 密级
     */
    public void setSecretDegree(java.lang.String secretDegree) {
        this.secretDegree = secretDegree;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 拟发表刊物名称
     */
    @Column(name = "PUBLICATION_NAME", nullable = true, length = 200)
    public java.lang.String getPublicationName() {
        return this.publicationName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 拟发表刊物名称
     */
    public void setPublicationName(java.lang.String publicationName) {
        this.publicationName = publicationName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 拟发表刊物分区级别
     */
    @Column(name = "PUBLICATION_LEVEL", nullable = true, length = 2)
    public java.lang.String getPublicationLevel() {
        return this.publicationLevel;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 拟发表刊物分区级别
     */
    public void setPublicationLevel(java.lang.String publicationLevel) {
        this.publicationLevel = publicationLevel;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 会议名称
     */
    @Column(name = "MEETING_NAME", nullable = true, length = 200)
    public java.lang.String getMeetingName() {
        return this.meetingName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 会议名称
     */
    public void setMeetingName(java.lang.String meetingName) {
        this.meetingName = meetingName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 征文名称
     */
    @Column(name = "ARTICLE_NAME", nullable = true, length = 200)
    public java.lang.String getArticleName() {
        return this.articleName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 征文名称
     */
    public void setArticleName(java.lang.String articleName) {
        this.articleName = articleName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 征文单位
     */
    @Column(name = "ARTICLE_DEPART", nullable = true, length = 50)
    public java.lang.String getArticleDepart() {
        return this.articleDepart;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 征文单位
     */
    public void setArticleDepart(java.lang.String articleDepart) {
        this.articleDepart = articleDepart;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 固定联系电话
     */
    @Column(name = "FIX_TELEPHONE", nullable = true, length = 20)
    public java.lang.String getFixTelephone() {
        return this.fixTelephone;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 固定联系电话
     */
    public void setFixTelephone(java.lang.String fixTelephone) {
        this.fixTelephone = fixTelephone;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 移动联系电话
     */
    @Column(name = "MOBILE_TELEPHONE", nullable = true, length = 20)
    public java.lang.String getMobileTelephone() {
        return this.mobileTelephone;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 移动联系电话
     */
    public void setMobileTelephone(java.lang.String mobileTelephone) {
        this.mobileTelephone = mobileTelephone;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 第一作者姓名
     */
    @Column(name = "FIRST_AUTHOR", nullable = true, length = 36)
    public java.lang.String getFirstAuthor() {
        return this.firstAuthor;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 第一作者姓名
     */
    public void setFirstAuthor(java.lang.String firstAuthor) {
        this.firstAuthor = firstAuthor;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 其他作者姓名
     */
    @Column(name = "OTHER_AUTHOR", nullable = true, length = 50)
    public java.lang.String getOtherAuthor() {
        return this.otherAuthor;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 其他作者姓名
     */
    public void setOtherAuthor(java.lang.String otherAuthor) {
        this.otherAuthor = otherAuthor;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 备注
     */
    @Column(name = "MEMO", nullable = true, length = 500)
    public java.lang.String getMemo() {
        return this.memo;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 备注
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 论文内容要点
     */
    @Column(name = "THESIS_CONTENT_KEY", nullable = true, length = 500)
    public java.lang.String getThesisContentKey() {
        return this.thesisContentKey;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 论文内容要点
     */
    public void setThesisContentKey(java.lang.String thesisContentKey) {
        this.thesisContentKey = thesisContentKey;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 审查号
     */
    @Column(name = "REVIEW_NUMBER", nullable = true, length = 50)
    public java.lang.String getReviewNumber() {
        return this.reviewNumber;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 审查号
     */
    public void setReviewNumber(java.lang.String reviewNumber) {
        this.reviewNumber = reviewNumber;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 流程处理状态
     */
    @Column(name = "BPM_STATUS", nullable = true, length = 1)
    public java.lang.String getBpmStatus() {
        return this.bpmStatus;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 流程处理状态
     */
    public void setBpmStatus(java.lang.String bpmStatus) {
        this.bpmStatus = bpmStatus;
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
     * @return: java.lang.String 审查状态
     */
    @Column(name = "CHECK_FLAG", nullable = true, length = 1)
    public java.lang.String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(java.lang.String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Column(name="check_expert")
    public String getCheckExpert() {
        return checkExpert;
    }

    public void setCheckExpert(String checkExpert) {
        this.checkExpert = checkExpert;
    }
    
}
