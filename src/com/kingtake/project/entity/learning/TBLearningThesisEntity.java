package com.kingtake.project.entity.learning;

import java.util.Date;
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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 学术论文信息表
 * @author onlineGenerator
 * @date 2015-12-03 11:14:19
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_learning_thesis", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
@SuppressWarnings("serial")
public class TBLearningThesisEntity implements java.io.Serializable {
    /** 中文题名 */
    @Excel(name = "中文题名")
    private java.lang.String titleCn;
    /** 英文题名 */
    @Excel(name = "英文题名")
    private java.lang.String titleEn;
    /** 第一作者id */
    private TSUser authorFirst;
    /** 第一作者名称 */
    @Excel(name = "第一作者")
    private java.lang.String authorFirstName;
    /** 第二作者 */
    @Excel(name = "第二作者")
    private java.lang.String authorSecond;
    /** 第三作者 */
    @Excel(name = "第三作者")
    private java.lang.String authorThird;
    /** 其他作者 */
    @Excel(name = "其他作者")
    private java.lang.String authorOther;
    /** 第一作者所属院系 */
    private TSDepart authorFirstDepart;

    @Excel(name = "第一作者所属院系")
    private String authorFirstDepartName;
    /** 具体单位 */
    @Excel(name = "具体单位")
    private java.lang.String concreteDepart;
    /** 关键词 */
    @Excel(name = "关键词")
    private java.lang.String keyword;
    /** 密级 */
    @Excel(name = "密级")
    private java.lang.String secretCode;
    /** 期刊名称 */
    @Excel(name = "期刊名称")
    private java.lang.String magazineName;
    /** 发表年度 */
    @Excel(name = "发表时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    /**
     * 关联保密
     */
    @Excel(name = "关联保密编号")
    private String secretNumber;

    /**
     * 关联项目名称
     */
    @Excel(name = "关联项目名称")
    private String projectName;
    /**
     * 期刊分类
     */
    @Excel(name = "期刊分类", isExportConvert = true)
    private String periodicalType;

    /**
     * 会议论文分类
     */
    @Excel(name = "会议论文分类", isExportConvert = true)
    private String articleType;

    /** 卷数 */
    @Excel(name = "卷数")
    private java.lang.String volumeNum;
    /** 期数 */
    @Excel(name = "期数")
    private java.lang.String phaseNum;
    /** 文章页码 */
    @Excel(name = "文章页码")
    private java.lang.String pageNum;
    /** 资助基金 */
    @Excel(name = "资助基金")
    private java.lang.String sustentationFund;
    /** 摘要 */
    @Excel(name = "摘要")
    private java.lang.String summary;
    /** 收录情况索引名称 */
    @Excel(name = "收录情况索引名称")
    private java.lang.String indexName;
    /** 收录情况收录号 */
    @Excel(name = "收录情况收录号")
    private java.lang.String collectionNum;
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
    /** 主键 */
    private java.lang.String id;
    /** 关联项目 */
    private TPmProjectEntity project;

    /**
     * 流程实例id
     */
    private String processInstId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 分配人
     */
    private String assigneeName;

    /**
     * 流程审批状态
     */
    private String bpmStatus;

    /**
     * 全文电子版
     */
    private List<TSFilesEntity> editions;

    /**
     * 封面扫描照片
     */
    private List<TSFilesEntity> coverPictures;

    /**
     * 目录扫描照片
     */
    private List<TSFilesEntity> directoryPictures;

    /**
     * 论文首页扫描照片
     */
    private List<TSFilesEntity> homePagePictures;
    
    /**
     * 附件编码
     */
    private String attachmentCode;

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 卷数
     */
    @Column(name = "VOLUME_NUM", nullable = true, length = 10)
    public java.lang.String getVolumeNum() {
        return this.volumeNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 卷数
     */
    public void setVolumeNum(java.lang.String volumeNum) {
        this.volumeNum = volumeNum;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 期数
     */
    @Column(name = "PHASE_NUM", nullable = true, length = 5)
    public java.lang.String getPhaseNum() {
        return this.phaseNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 期数
     */
    public void setPhaseNum(java.lang.String phaseNum) {
        this.phaseNum = phaseNum;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 文章页码
     */
    @Column(name = "PAGE_NUM", nullable = true, length = 30)
    public java.lang.String getPageNum() {
        return this.pageNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 文章页码
     */
    public void setPageNum(java.lang.String pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 资助基金
     */
    @Column(name = "SUSTENTATION_FUND", nullable = true, length = 120)
    public java.lang.String getSustentationFund() {
        return this.sustentationFund;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 资助基金
     */
    public void setSustentationFund(java.lang.String sustentationFund) {
        this.sustentationFund = sustentationFund;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 摘要
     */
    @Column(name = "SUMMARY", nullable = true, length = 800)
    public java.lang.String getSummary() {
        return this.summary;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 摘要
     */
    public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 收录情况索引名称
     */
    @Column(name = "INDEX_NAME", nullable = true, length = 2)
    public java.lang.String getIndexName() {
        return this.indexName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 收录情况索引名称
     */
    public void setIndexName(java.lang.String indexName) {
        this.indexName = indexName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 收录情况收录号
     */
    @Column(name = "COLLECTION_NUM", nullable = true, length = 50)
    public java.lang.String getCollectionNum() {
        return this.collectionNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 收录情况收录号
     */
    public void setCollectionNum(java.lang.String collectionNum) {
        this.collectionNum = collectionNum;
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
     * @return: java.lang.String 所属项目id
     */
    @JoinColumn(name = "PROJECT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public TPmProjectEntity getProject() {
        return this.project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 中文题名
     */
    @Column(name = "TITLE_CN", nullable = true, length = 300)
    public java.lang.String getTitleCn() {
        return this.titleCn;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 中文题名
     */
    public void setTitleCn(java.lang.String titleCn) {
        this.titleCn = titleCn;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 英文题名
     */
    @Column(name = "TITLE_EN", nullable = true, length = 300)
    public java.lang.String getTitleEn() {
        return this.titleEn;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 英文题名
     */
    public void setTitleEn(java.lang.String titleEn) {
        this.titleEn = titleEn;
    }

    @JoinColumn(name = "AUTHOR_FIRST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public TSUser getAuthorFirst() {
        return authorFirst;
    }

    public void setAuthorFirst(TSUser authorFirst) {
        this.authorFirst = authorFirst;
    }

    @Column(name = "AUTHOR_FIRST_NAME")
    public java.lang.String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(java.lang.String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 第二作者
     */
    @Column(name = "AUTHOR_SECOND", nullable = true, length = 36)
    public java.lang.String getAuthorSecond() {
        return this.authorSecond;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 第二作者
     */
    public void setAuthorSecond(java.lang.String authorSecond) {
        this.authorSecond = authorSecond;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 第三作者
     */
    @Column(name = "AUTHOR_THIRD", nullable = true, length = 36)
    public java.lang.String getAuthorThird() {
        return this.authorThird;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 第三作者
     */
    public void setAuthorThird(java.lang.String authorThird) {
        this.authorThird = authorThird;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 其他作者
     */
    @Column(name = "AUTHOR_OTHER", nullable = true, length = 120)
    public java.lang.String getAuthorOther() {
        return this.authorOther;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 其他作者
     */
    public void setAuthorOther(java.lang.String authorOther) {
        this.authorOther = authorOther;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 第一作者所属院系
     */
    @JoinColumn(name = "AUTHOR_FIRST_DEPART")
    @ManyToOne(fetch = FetchType.LAZY)
    public TSDepart getAuthorFirstDepart() {
        return this.authorFirstDepart;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 第一作者所属院系
     */
    public void setAuthorFirstDepart(TSDepart authorFirstDepart) {
        this.authorFirstDepart = authorFirstDepart;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 具体单位
     */
    @Column(name = "CONCRETE_DEPART", nullable = true, length = 60)
    public java.lang.String getConcreteDepart() {
        return this.concreteDepart;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 具体单位
     */
    public void setConcreteDepart(java.lang.String concreteDepart) {
        this.concreteDepart = concreteDepart;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 关键词
     */
    @Column(name = "KEYWORD", nullable = true, length = 200)
    public java.lang.String getKeyword() {
        return this.keyword;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 关键词
     */
    public void setKeyword(java.lang.String keyword) {
        this.keyword = keyword;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 密级
     */
    @Column(name = "SECRET_CODE", nullable = true, length = 1)
    public java.lang.String getSecretCode() {
        return this.secretCode;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 密级
     */
    public void setSecretCode(java.lang.String secretCode) {
        this.secretCode = secretCode;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 期刊名称
     */
    @Column(name = "MAGAZINE_NAME", nullable = true, length = 60)
    public java.lang.String getMagazineName() {
        return this.magazineName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 期刊名称
     */
    public void setMagazineName(java.lang.String magazineName) {
        this.magazineName = magazineName;
    }

    @Transient
    public List<TSFilesEntity> getEditions() {
        return this.editions;
    }

    public void setEditions(List<TSFilesEntity> editions) {
        this.editions = editions;
    }

    @Transient
    public List<TSFilesEntity> getCoverPictures() {
        return this.coverPictures;
    }

    public void setCoverPictures(List<TSFilesEntity> coverPictures) {
        this.coverPictures = coverPictures;
    }

    @Transient
    public List<TSFilesEntity> getDirectoryPictures() {
        return this.directoryPictures;
    }

    public void setDirectoryPictures(List<TSFilesEntity> directoryPictures) {
        this.directoryPictures = directoryPictures;
    }

    @Transient
    public List<TSFilesEntity> getHomePagePictures() {
        return this.homePagePictures;
    }

    public void setHomePagePictures(List<TSFilesEntity> homePagePictures) {
        this.homePagePictures = homePagePictures;
    }

    @Column(name = "BPM_STATUS")
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

    @Column(name = "PERIODICAL_TYPE")
    public String getPeriodicalType() {
        return periodicalType;
    }

    public void setPeriodicalType(String periodicalType) {
        this.periodicalType = periodicalType;
    }

    @Column(name = "ARTICLE_TYPE")
    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    @Column(name = "SECRET_NUMBER")
    public String getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(String secretNumber) {
        this.secretNumber = secretNumber;
    }

    @Transient
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Transient
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Transient
    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    @Transient
    public String getAuthorFirstDepartName() {
        if (this.authorFirstDepart != null) {
            return this.authorFirstDepart.getDepartname();
        } else {
            return "";
        }
    }

    public void setAuthorFirstDepartName(String authorFirstDepartName) {
        this.authorFirstDepartName = authorFirstDepartName;
    }

    public String convertGetPeriodicalType() {
        return ConvertDicUtil.getConvertDic("1", "QKFL", this.periodicalType);
    }

    public String convertGetArticleType() {
        return ConvertDicUtil.getConvertDic("1", "HYLWFL", this.articleType);
    }

    @Transient
    public String getProjectName() {
        if (this.project != null) {
            return this.project.getProjectName();
        }
        return "";
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "publish_time")
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }
    
    

}
