package com.kingtake.project.entity.resultreward;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;

/**
 * @Title: Entity
 * @Description: 成果奖励基本信息表
 * @author onlineGenerator
 * @date 2015-12-05 10:06:45
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_result_reward", schema = "")
@LogEntity("成果奖励信息表")
@SuppressWarnings("serial")
public class TBResultRewardEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 项目名称 */
    @Excel(name = "项目名称")
    @FieldDesc("项目名称")
    private java.lang.String rewardName;
    /** 主要完成人id */
    private java.lang.String finishUserid;
    /** 主要完成人 */
    @Excel(name = "主要完成人")
    @FieldDesc("主要完成人")
    private java.lang.String finishUsername;
    /** 主要完成单位id */
    private java.lang.String finishDepartid;
    /** 主要完成单位名称 */
    @Excel(name = "主要完成单位")
    @FieldDesc("主要完成单位")
    private java.lang.String finishDepartname;
    /** 简介 */
    private java.lang.String summary;
    /** 创新点 */
    @Excel(name = "创新点")
    @FieldDesc("创新点")
    private java.lang.String innovationPoint;
    /** 拟申报奖励类别 */
    @Excel(name = "拟申报奖励类别", isExportConvert = true)
    @FieldDesc("拟申报奖励类别")
    private java.lang.String reportRewardLevel;
    /** 拟申报等级 */
    @Excel(name = "拟申报等级", isExportConvert = true)
    @FieldDesc("拟申报等级")
    private java.lang.String reportLevel;
    /** 任务来源 */
    @Excel(name = "任务来源")
    @FieldDesc("任务来源")
    private java.lang.String taskSource;
    /** 总投资额 */
    @Excel(name = "总投资额",isAmount = true)
    @FieldDesc("总投资额")
    private java.math.BigDecimal investedAmount;
    /** 起始日期 */
    @Excel(name = "起始日期")
    @FieldDesc("起始日期")
    private String beginDate;
    /** 截止日期 */
    @Excel(name = "截止日期")
    @FieldDesc("截止日期")
    private String endDate;
    /** 联系人 */
    @Excel(name = "联系人")
    @FieldDesc("联系人")
    private java.lang.String contacts;
    /** 联系电话 */
    @Excel(name = "联系电话")
    @FieldDesc("联系电话")
    private java.lang.String contactPhone;
    /** 我校在项目中的贡献 */
    @Excel(name = "我校在项目中的贡献")
    @FieldDesc("我校在项目中的贡献")
    private java.lang.String hgdDevote;
    /** 项目主键 */
    private java.lang.String sourceProjectIds;
    /** 主项目来源 */
    private String mainSurce;
    /** 主项目ID */
    private String mainSurceId;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 鉴定时间
     */
    private Date appraisalTime;

    /**
     * 外协单位及主要贡献
     */
    private String deptMajorContribute;

    /**
     * 附件关联编码
     */
    private String attachmentCode;

    /** 完成状态 */
    @Excel(name = "完成状态", isExportConvert = true)
    @FieldDesc("完成状态")
    private java.lang.String finishFlag;
    /** 完成时间 */
    @Excel(name = "完成时间")
    @FieldDesc("完成时间")
    private java.util.Date finishTime;
    /** 驳回人id */
    @FieldDesc("驳回人id")
    private java.lang.String operateFinishUserid;
    /** 驳回人 */
    @FieldDesc("驳回人")
    private java.lang.String operateFinishUsername;

    private TPmApprReceiveLogsEntity backLog;
    private List<TPmApprSendLogsEntity> apprSendLogs;
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;

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
     * @return: java.lang.String 项目名称
     */
    @Column(name = "REWARD_NAME", nullable = true, length = 120)
    public java.lang.String getRewardName() {
        return this.rewardName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目名称
     */
    public void setRewardName(java.lang.String rewardName) {
        this.rewardName = rewardName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主要完成人id
     */
    @Column(name = "FINISH_USERID", nullable = true, length = 350)
    public java.lang.String getFinishUserid() {
        return this.finishUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主要完成人id
     */
    public void setFinishUserid(java.lang.String finishUserid) {
        this.finishUserid = finishUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主要完成人
     */
    @Column(name = "FINISH_USERNAME", nullable = true, length = 300)
    public java.lang.String getFinishUsername() {
        return this.finishUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主要完成人
     */
    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主要完成单位id
     */
    @Column(name = "FINISH_DEPARTID", nullable = true, length = 350)
    public java.lang.String getFinishDepartid() {
        return this.finishDepartid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主要完成单位id
     */
    public void setFinishDepartid(java.lang.String finishDepartid) {
        this.finishDepartid = finishDepartid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主要完成单位名称
     */
    @Column(name = "FINISH_DEPARTNAME", nullable = true, length = 300)
    public java.lang.String getFinishDepartname() {
        return this.finishDepartname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主要完成单位名称
     */
    public void setFinishDepartname(java.lang.String finishDepartname) {
        this.finishDepartname = finishDepartname;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 简介
     */
    @Column(name = "SUMMARY", nullable = true, length = 800)
    public java.lang.String getSummary() {
        return this.summary;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 简介
     */
    public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创新点
     */
    @Column(name = "INNOVATION_POINT", nullable = true, length = 800)
    public java.lang.String getInnovationPoint() {
        return this.innovationPoint;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 创新点
     */
    public void setInnovationPoint(java.lang.String innovationPoint) {
        this.innovationPoint = innovationPoint;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 拟申报奖励类别
     */
    @Column(name = "REPORT_REWARD_LEVEL", nullable = true, length = 2)
    public java.lang.String getReportRewardLevel() {
        return this.reportRewardLevel;
    }

    public java.lang.String convertGetReportRewardLevel() {
        if (StringUtil.isNotEmpty(this.reportRewardLevel)) {
            return SrmipConstants.dicResearchMap.get("SBJLLB").get(this.reportRewardLevel).toString();
        }
        return this.reportRewardLevel;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 拟申报奖励类别
     */
    public void setReportRewardLevel(java.lang.String reportRewardLevel) {
        this.reportRewardLevel = reportRewardLevel;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 拟申报等级
     */
    @Column(name = "REPORT_LEVEL", nullable = true, length = 2)
    public java.lang.String getReportLevel() {
        return this.reportLevel;
    }

    public java.lang.String convertGetReportLevel() {
        if (StringUtil.isNotEmpty(this.reportLevel)) {
            return SrmipConstants.dicResearchMap.get("SBDJ").get(this.reportLevel).toString();
        }
        return this.reportLevel;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 拟申报等级
     */
    public void setReportLevel(java.lang.String reportLevel) {
        this.reportLevel = reportLevel;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 任务来源
     */
    @Column(name = "TASK_SOURCE", nullable = true, length = 350)
    public java.lang.String getTaskSource() {
        return this.taskSource;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 任务来源
     */
    public void setTaskSource(java.lang.String taskSource) {
        this.taskSource = taskSource;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 总投资额
     */
    @Column(name = "INVESTED_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getInvestedAmount() {
        return this.investedAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 总投资额
     */
    public void setInvestedAmount(java.math.BigDecimal investedAmount) {
        this.investedAmount = investedAmount;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人
     */
    @Column(name = "CONTACTS", nullable = true, length = 36)
    public java.lang.String getContacts() {
        return this.contacts;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人
     */
    public void setContacts(java.lang.String contacts) {
        this.contacts = contacts;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系电话
     */
    @Column(name = "CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系电话
     */
    public void setContactPhone(java.lang.String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 我校在项目中的贡献
     */
    @Column(name = "HGD_DEVOTE", nullable = true, length = 800)
    public java.lang.String getHgdDevote() {
        return this.hgdDevote;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 我校在项目中的贡献
     */
    public void setHgdDevote(java.lang.String hgdDevote) {
        this.hgdDevote = hgdDevote;
    }

    @Column(name = "FINISH_FLAG", nullable = true)
    public java.lang.String getFinishFlag() {
        return finishFlag;
    }

    public java.lang.String convertGetFinishFlag() {
        if (StringUtil.isNotEmpty(this.finishFlag)) {
            return SrmipConstants.dicResearchMap.get("SPZT").get(this.finishFlag).toString();
        }
        return this.finishFlag;
    }

    public void setFinishFlag(java.lang.String finishFlag) {
        this.finishFlag = finishFlag;
    }

    @Column(name = "FINISH_TIME", nullable = true)
    public java.util.Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(java.util.Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "OPERATE_FINISH_USERID", nullable = true)
    public java.lang.String getOperateFinishUserid() {
        return operateFinishUserid;
    }

    public void setOperateFinishUserid(java.lang.String operateFinishUserid) {
        this.operateFinishUserid = operateFinishUserid;
    }

    @Column(name = "OPERATE_FINISH_USERNAME", nullable = true)
    public java.lang.String getOperateFinishUsername() {
        return operateFinishUsername;
    }

    public void setOperateFinishUsername(java.lang.String operateFinishUsername) {
        this.operateFinishUsername = operateFinishUsername;
    }

    @Column(name = "BEGIN_DATE", nullable = true)
    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    @Column(name = "END_DATE", nullable = true)
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Column(name = "SOURCE_PROJECT_IDS", nullable = true)
    public java.lang.String getSourceProjectIds() {
        return sourceProjectIds;
    }

    public void setSourceProjectIds(java.lang.String sourceProjectIds) {
        this.sourceProjectIds = sourceProjectIds;
    }

    @Column(name = "MAIN_SOURCE", nullable = true)
    public String getMainSurce() {
        return mainSurce;
    }

    public void setMainSurce(String mainSurce) {
        this.mainSurce = mainSurce;
    }

    @Column(name = "MAIN_SOURCE_ID", nullable = true)
    public String getMainSurceId() {
        return mainSurceId;
    }

    public void setMainSurceId(String mainSurceId) {
        this.mainSurceId = mainSurceId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BACK_ID")
    public TPmApprReceiveLogsEntity getBackLog() {
        return backLog;
    }

    public void setBackLog(TPmApprReceiveLogsEntity backLog) {
        this.backLog = backLog;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "APPR_ID")
    public List<TPmApprSendLogsEntity> getApprSendLogs() {
        return apprSendLogs;
    }

    public void setApprSendLogs(List<TPmApprSendLogsEntity> apprSendLogs) {
        this.apprSendLogs = apprSendLogs;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "APPR_ID")
    public List<TPmApprReceiveLogsEntity> getApprReceiveLogs() {
        return apprReceiveLogs;
    }

    public void setApprReceiveLogs(List<TPmApprReceiveLogsEntity> apprReceiveLogs) {
        this.apprReceiveLogs = apprReceiveLogs;
    }

    @Column(name = "project_type")
    public String getProjectType() {
        return projectType;
    }
    
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    @Column(name = "appraisal_time")
    public Date getAppraisalTime() {
        return appraisalTime;
    }

    public void setAppraisalTime(Date appraisalTime) {
        this.appraisalTime = appraisalTime;
    }

    @Column(name = "dept_major_contribute")
    public String getDeptMajorContribute() {
        return deptMajorContribute;
    }

    public void setDeptMajorContribute(String deptMajorContribute) {
        this.deptMajorContribute = deptMajorContribute;
    }

    @Column(name = "attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

}
