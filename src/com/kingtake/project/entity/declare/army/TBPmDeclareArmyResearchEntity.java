package com.kingtake.project.entity.declare.army;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;

/**
 * @Title: Entity
 * @Description: 项目申报书信息表（军内科研）
 * @author onlineGenerator
 * @date 2015-08-01 11:39:34
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_pm_declare_army_research", schema = "")
@LogEntity("项目申报书信息表（军内科研）")
@SuppressWarnings("serial")
public class TBPmDeclareArmyResearchEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 项目基_主键 */
    @FieldDesc("项目基_主键")
    @Excel(name = "项目基_主键")
    private java.lang.String tpId;
    /** 项目编号 */
    @FieldDesc("项目编号")
    @Excel(name = "项目编号")
    private String projectNo;
    /** 项目名称 */
    @FieldDesc("项目名称")
    @Excel(name = "项目名称")
    private java.lang.String projectName;
    /**
     * 项目批次
     */
    @FieldDesc("项目批次")
    @Excel(name = "项目批次")
    private String projectGroup;
    /**
     * 申报单位id
     */
    @FieldDesc("申报单位id")
    private String reportDepartId;
    /**
     * 申报单位名称
     */
    @FieldDesc("申报单位名称")
    @Excel(name = "申报单位")
    private String reportDepartName;
    /** 项目类别 */
    @FieldDesc("项目类型")
    @Excel(name = "项目类型")
    private TBProjectTypeEntity projectType;
    /**
     * 项目性质（下拉：新上、续研）
     */
    @FieldDesc("项目性质")
    @Excel(name = "项目性质")
    private String projectProperties;

    /**
     * 所属部门,海军、空军、二炮、总参测绘局、总参气水局、总参二部、总参三部、总参四部，手输
     */
    @FieldDesc("所属部门")
    @Excel(name = "所属部门")
    private String superiorDepartname;

    /**
     * 分管部门id
     */
    @FieldDesc("分管部门id")
    private String branchDepartId;
    /**
     * 分管部门名称
     */
    @FieldDesc("分管部门名称")
    @Excel(name = "分管部门")
    private String branchDepartName;

    /**
     * 完成单位，默认为：海军工程大学
     */
    @FieldDesc("完成单位")
    @Excel(name = "完成单位")
    private String completeDepartName;

    /**
     * 合作单位（手输：多个）
     */
    @FieldDesc("合作单位")
    @Excel(name = "合作单位")
    private String cooperationDepartname;

    /**
     * 身份证号：（负责人身份证号）
     */
    @FieldDesc("身份证号")
    @Excel(name = "身份证号")
    private String contactIdNo;

    /**
     * 固定电话（负责人固定电话）
     */
    @FieldDesc("固定电话")
    @Excel(name = "固定电话")
    private String contactFixPhone;

    /**
     * 关键词
     */
    @FieldDesc("关键词")
    @Excel(name = "关键词")
    private String declareKey;

    /**
     * 项目类别（下拉选择：论证项目、技术研究项目、改进、研制项目、使用研究项目、实验验证项目、其他项目）
     */
    @FieldDesc("项目类别")
    @Excel(name = "项目类别")
    private String projectCategory;

    /**
     * 项目级别
     */
    @FieldDesc("项目级别")
    @Excel(name = "项目级别")
    private String projectGrade;

    /** 项目负责人id */
    @FieldDesc("项目负责人id")
    @Excel(name = "项目负责人id")
    private java.lang.String projectManagerId;
    /** 项目负责人姓名 */
    @FieldDesc("项目负责人姓名")
    @Excel(name = "项目负责人姓名")
    private java.lang.String projectManagerName;
    /** 联系电话 */
    @FieldDesc("联系电话")
    @Excel(name = "联系电话")
    private java.lang.String contactPhone;
    /** 通信地址 */
    @FieldDesc("通信地址")
    @Excel(name = "通信地址")
    private java.lang.String address;
    /** 邮政编码 */
    @FieldDesc("邮政编码")
    @Excel(name = "邮政编码")
    private java.lang.String postCode;
    /** 申报日期 */
    @FieldDesc("申报日期")
    @Excel(name = "申报日期")
    private java.util.Date applyTime;
    /** 项目简介（150字以内） */
    @FieldDesc("项目简介（150字以内）")
    @Excel(name = "项目简介（150字以内）")
    private java.lang.String projectSummary;
    /** 一、研究目的 */
    @FieldDesc("一、研究目的")
    @Excel(name = "一、研究目的")
    private java.lang.String researchAim;
    /** 二、主要研究内容 */
    @FieldDesc("二、主要研究内容")
    @Excel(name = "二、主要研究内容")
    private java.lang.String researchContent;
    /** 三、初步方案和可行性分析 */
    @FieldDesc("三、初步方案和可行性分析")
    @Excel(name = "三、初步方案和可行性分析")
    private java.lang.String researchAnalyse;
    /** 四、周期进度、成果形式和主要考核指标-(一)项目周期（年） */
    @FieldDesc("四、周期进度、成果形式和主要考核指标-(一)项目周期（年）")
    @Excel(name = "四、周期进度、成果形式和主要考核指标-(一)项目周期（年）")
    private java.lang.String projectCycle;
    /** 四、周期进度、成果形式和主要考核指标-(二)进度安排 */
    @FieldDesc("四、周期进度、成果形式和主要考核指标-(二)进度安排")
    @Excel(name = "四、周期进度、成果形式和主要考核指标-(二)进度安排")
    private java.lang.String projectSchedule;
    /** 四、周期进度、成果形式和主要考核指标-(三)成果形式 */
    @FieldDesc("四、周期进度、成果形式和主要考核指标-(三)成果形式")
    @Excel(name = "四、周期进度、成果形式和主要考核指标-(三)成果形式")
    private java.lang.String projectResult;
    /** 四、周期进度、成果形式和主要考核指标-(四)主要考核指标 */
    @FieldDesc("四、周期进度、成果形式和主要考核指标-(四)主要考核指标")
    @Excel(name = "四、周期进度、成果形式和主要考核指标-(四)主要考核指标")
    private java.lang.String projectCheck;
    /** 五、推广应用前景及效益分析 */
    @Excel(name = "五、推广应用前景及效益分析")
    @FieldDesc("五、推广应用前景及效益分析")
    private java.lang.String projectExtendBenefit;
    /** 六、已有研究基础和保障条件 */
    @FieldDesc("六、已有研究基础和保障条件")
    @Excel(name = "六、已有研究基础和保障条件")
    private java.lang.String projectBaseCondition;
    /** 流程流转状态 */
    @FieldDesc("流程流转状态")
    @Excel(name = "流程流转状态")
    private java.lang.String bpmStatus;

    @FieldDesc("创建人英文名")
    private java.lang.String createBy;
    @FieldDesc("创建人中文名")
    private java.lang.String createName;
    @FieldDesc("创建人日期")
    private java.util.Date createDate;
    @FieldDesc("更新人英文名")
    private java.lang.String updateBy;
    @FieldDesc("更新人中文名")
    private java.lang.String updateName;
    @FieldDesc("更新人中文名")
    private java.util.Date updateDate;

    /**
     * 流程实例id
     */
    private String processInstId;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> attachments;
    
    /**
     * 附件关联编码
     */
    private String attachmentCode;

    /**
     * 计划状态，1 已通过 ，2 已驳回 ，3 重新提交
     */
    private String planStatus;

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
     * @return: java.lang.String 项目基_主键
     */
    @Column(name = "T_P_ID", nullable = true, length = 32)
    public java.lang.String getTpId() {
        return this.tpId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目基_主键
     */
    public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目名称
     */
    @Column(name = "PROJECT_NAME", nullable = true, length = 100)
    public java.lang.String getProjectName() {
        return this.projectName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目名称
     */
    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    @ManyToOne
    @JoinColumn(name = "PROJECT_TYPE")
    public TBProjectTypeEntity getProjectType() {
        return projectType;
    }

    public void setProjectType(TBProjectTypeEntity projectType) {
        this.projectType = projectType;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目负责人id
     */
    @Column(name = "PROJECT_MANAGER_ID", nullable = true, length = 32)
    public java.lang.String getProjectManagerId() {
        return this.projectManagerId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目负责人id
     */
    public void setProjectManagerId(java.lang.String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目负责人姓名
     */
    @Column(name = "PROJECT_MANAGER_NAME", nullable = true, length = 50)
    public java.lang.String getProjectManagerName() {
        return this.projectManagerName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目负责人姓名
     */
    public void setProjectManagerName(java.lang.String projectManagerName) {
        this.projectManagerName = projectManagerName;
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
     * @return: java.lang.String 通信地址
     */
    @Column(name = "ADDRESS", nullable = true, length = 150)
    public java.lang.String getAddress() {
        return this.address;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 通信地址
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 邮政编码
     */
    @Column(name = "POST_CODE", nullable = true, length = 6)
    public java.lang.String getPostCode() {
        return this.postCode;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 邮政编码
     */
    public void setPostCode(java.lang.String postCode) {
        this.postCode = postCode;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 申报日期
     */
    @Column(name = "APPLY_TIME", nullable = true)
    public java.util.Date getApplyTime() {
        return this.applyTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 申报日期
     */
    public void setApplyTime(java.util.Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目简介（150字以内）
     */
    @Column(name = "PROJECT_SUMMARY", nullable = true, length = 400)
    public java.lang.String getProjectSummary() {
        return this.projectSummary;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目简介（150字以内）
     */
    public void setProjectSummary(java.lang.String projectSummary) {
        this.projectSummary = projectSummary;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 一、研究目的
     */
    @Column(name = "RESEARCH_AIM", nullable = true, length = 400)
    public java.lang.String getResearchAim() {
        return this.researchAim;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 一、研究目的
     */
    public void setResearchAim(java.lang.String researchAim) {
        this.researchAim = researchAim;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 二、主要研究内容
     */
    @Column(name = "RESEARCH_CONTENT", nullable = true, length = 400)
    public java.lang.String getResearchContent() {
        return this.researchContent;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 二、主要研究内容
     */
    public void setResearchContent(java.lang.String researchContent) {
        this.researchContent = researchContent;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 三、初步方案和可行性分析
     */
    @Column(name = "RESEARCH_ANALYSE", nullable = true, length = 400)
    public java.lang.String getResearchAnalyse() {
        return this.researchAnalyse;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 三、初步方案和可行性分析
     */
    public void setResearchAnalyse(java.lang.String researchAnalyse) {
        this.researchAnalyse = researchAnalyse;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 四、周期进度、成果形式和主要考核指标-(一)项目周期（年）
     */
    @Column(name = "PROJECT_CYCLE", nullable = true, length = 10)
    public java.lang.String getProjectCycle() {
        return this.projectCycle;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 四、周期进度、成果形式和主要考核指标-(一)项目周期（年）
     */
    public void setProjectCycle(java.lang.String projectCycle) {
        this.projectCycle = projectCycle;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 四、周期进度、成果形式和主要考核指标-(二)进度安排
     */
    @Column(name = "PROJECT_SCHEDULE", nullable = true, length = 400)
    public java.lang.String getProjectSchedule() {
        return this.projectSchedule;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 四、周期进度、成果形式和主要考核指标-(二)进度安排
     */
    public void setProjectSchedule(java.lang.String projectSchedule) {
        this.projectSchedule = projectSchedule;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 四、周期进度、成果形式和主要考核指标-(三)成果形式
     */
    @Column(name = "PROJECT_RESULT", nullable = true, length = 400)
    public java.lang.String getProjectResult() {
        return this.projectResult;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 四、周期进度、成果形式和主要考核指标-(三)成果形式
     */
    public void setProjectResult(java.lang.String projectResult) {
        this.projectResult = projectResult;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 四、周期进度、成果形式和主要考核指标-(四)主要考核指标
     */
    @Column(name = "PROJECT_CHECK", nullable = true, length = 400)
    public java.lang.String getProjectCheck() {
        return this.projectCheck;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 四、周期进度、成果形式和主要考核指标-(四)主要考核指标
     */
    public void setProjectCheck(java.lang.String projectCheck) {
        this.projectCheck = projectCheck;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 五、推广应用前景及效益分析
     */
    @Column(name = "PROJECT_EXTEND_BENEFIT", nullable = true, length = 400)
    public java.lang.String getProjectExtendBenefit() {
        return this.projectExtendBenefit;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 五、推广应用前景及效益分析
     */
    public void setProjectExtendBenefit(java.lang.String projectExtendBenefit) {
        this.projectExtendBenefit = projectExtendBenefit;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 六、已有研究基础和保障条件
     */
    @Column(name = "PROJECT_BASE_CONDITION", nullable = true, length = 400)
    public java.lang.String getProjectBaseCondition() {
        return this.projectBaseCondition;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 六、已有研究基础和保障条件
     */
    public void setProjectBaseCondition(java.lang.String projectBaseCondition) {
        this.projectBaseCondition = projectBaseCondition;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 流程流转状态
     */
    @Column(name = "BPM_STATUS", nullable = true, length = 1)
    public java.lang.String getBpmStatus() {
        return this.bpmStatus;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 流程流转状态
     */
    public void setBpmStatus(java.lang.String bpmStatus) {
        this.bpmStatus = bpmStatus;
    }

    @Column(name = "CREATE_BY")
    public java.lang.String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_NAME")
    public java.lang.String getCreateName() {
        return createName;
    }

    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_DATE")
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_BY")
    public java.lang.String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_NAME")
    public java.lang.String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "UPDATE_DATE")
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    @Column(name = "project_no")
    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    @Column(name = "project_group")
    public String getProjectGroup() {
        return projectGroup;
    }

    public void setProjectGroup(String projectGroup) {
        this.projectGroup = projectGroup;
    }

    @Column(name = "REPORT_DEPARTID")
    public String getReportDepartId() {
        return reportDepartId;
    }

    public void setReportDepartId(String reportDepartId) {
        this.reportDepartId = reportDepartId;
    }

    @Column(name = "REPORT_DEPARTNAME")
    public String getReportDepartName() {
        return reportDepartName;
    }

    public void setReportDepartName(String reportDepartName) {
        this.reportDepartName = reportDepartName;
    }

    @Column(name = "PROJECT_PROPERTIES")
    public String getProjectProperties() {
        return projectProperties;
    }

    public void setProjectProperties(String projectProperties) {
        this.projectProperties = projectProperties;
    }

    @Column(name = "SUPERIOR_DEPARTNAME")
    public String getSuperiorDepartname() {
        return superiorDepartname;
    }

    public void setSuperiorDepartname(String superiorDepartname) {
        this.superiorDepartname = superiorDepartname;
    }

    @Column(name = "BRANCH_DEPARTID")
    public String getBranchDepartId() {
        return branchDepartId;
    }

    public void setBranchDepartId(String branchDepartId) {
        this.branchDepartId = branchDepartId;
    }

    @Column(name = "BRANCH_DEPARTNAME")
    public String getBranchDepartName() {
        return branchDepartName;
    }

    public void setBranchDepartName(String branchDepartName) {
        this.branchDepartName = branchDepartName;
    }

    @Column(name = "COMPLETE_DEPARTNAME")
    public String getCompleteDepartName() {
        return completeDepartName;
    }

    public void setCompleteDepartName(String completeDepartName) {
        this.completeDepartName = completeDepartName;
    }

    @Column(name = "COOPERATION_DEPARTNAME")
    public String getCooperationDepartname() {
        return cooperationDepartname;
    }

    public void setCooperationDepartname(String cooperationDepartname) {
        this.cooperationDepartname = cooperationDepartname;
    }

    @Column(name = "CONTACT_IDNO")
    public String getContactIdNo() {
        return contactIdNo;
    }

    public void setContactIdNo(String contactIdNo) {
        this.contactIdNo = contactIdNo;
    }

    @Column(name = "CONTACT_FIXPHONE")
    public String getContactFixPhone() {
        return contactFixPhone;
    }

    public void setContactFixPhone(String contactFixPhone) {
        this.contactFixPhone = contactFixPhone;
    }

    @Column(name = "DECLARE_KEY")
    public String getDeclareKey() {
        return declareKey;
    }

    public void setDeclareKey(String declareKey) {
        this.declareKey = declareKey;
    }

    @Column(name = "PROJECT_CATEGORY")
    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    @Column(name = "PROJECT_GRADE")
    public String getProjectGrade() {
        return projectGrade;
    }

    public void setProjectGrade(String projectGrade) {
        this.projectGrade = projectGrade;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name = "PLAN_STATUS")
    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

}
