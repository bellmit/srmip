package com.kingtake.project.entity.appraisal;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 年度成果鉴定计划表
 * @author onlineGenerator
 * @date 2015-09-07 15:11:33
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_appraisal_project", schema = "")
@LogEntity("成果鉴定计划表")
@SuppressWarnings("serial")
public class TBAppraisalProjectEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键id")
    private java.lang.String id;
    /** 关联项目主键 */
    @FieldDesc("关联项目主键")
    private java.lang.String projectId;
    /**
     * 序号
     */
    private String num;
    /** 项目名称 */
    @Excel(name = "项目名称", width = 20)
    @FieldDesc("项目名称")
    private java.lang.String projectName;
    /** 成果名称 */
    @Excel(name = "成果名称")
    @FieldDesc("成果名称")
    private java.lang.String achievementName;
    /** 研制开始时间 */
    @FieldDesc("研制开始时间")
    @Excel(name = "研制开始时间", width = 20, format = "YYYY-MM-dd")
    private java.util.Date projectBeginDate;
    /** 研制截止时间 */
    @FieldDesc("研制截止时间")
    @Excel(name = "研制截止时间", width = 20, format = "YYYY-MM-dd")
    private java.util.Date projectEndDate;
    /** 承研单位id */
    @FieldDesc("承研单位id")
    private java.lang.String projectDeveloperDepartid;
    /** 承研单位名称 */
    @Excel(name = "承研单位", width = 20)
    @FieldDesc("承研单位")
    private java.lang.String projectDeveloperDepartname;
    /** 项目负责人id */
    @FieldDesc("项目负责人id")
    private java.lang.String projectManagerId;
    /** 项目负责人姓名 */
    @FieldDesc("项目负责人")
    @Excel(name = "项目负责人", width = 20)
    private java.lang.String projectManagerName;
    /** 项目负责人联系电话 */
    @FieldDesc("项目负责人联系电话")
    @Excel(name = "项目负责人联系电话", width = 20)
    private java.lang.String projectManagerPhone;
    /** 任务来源 */
    @FieldDesc("任务来源")
    @Excel(name = "任务来源", width = 20)
    private java.lang.String projectSourceUnit;
    /** 依据文号 */
    @FieldDesc("依据文号")
    @Excel(name = "依据文号", width = 20)
    private java.lang.String projectAccordingNum;
    /** 鉴定主持单位 */
    @FieldDesc("鉴定主持单位")
    @Excel(name = "鉴定主持单位", width = 30)
    private java.lang.String appraisalUnit;
    /** 鉴定主持联系人 */
    @FieldDesc("鉴定主持联系人")
    @Excel(name = "鉴定主持单位联系人", width = 30)
    private java.lang.String appraisalContact;
    /** 鉴定主持联系电话 */
    @FieldDesc("鉴定主持联系电话")
    @Excel(name = "鉴定主持单位联系电话", width = 30)
    private java.lang.String appraisalContactPhone;
    /** 鉴定时间 */
    @Excel(name = "鉴定时间", format = "yyyy-MM", width = 15)
    @FieldDesc("鉴定时间")
    private java.util.Date appraisalTime;
    /** 鉴定地点 */
    @Excel(name = "鉴定地点", width = 20)
    @FieldDesc("鉴定地点")
    private java.lang.String appraisalAddress;
    /** 鉴定形式 */
    @Excel(name = "鉴定形式", isExportConvert = true, width = 15)
    @FieldDesc("鉴定形式")
    private java.lang.String appraisalForm;
    /** 备注 */
    @Excel(name = "备注", width = 20)
    @FieldDesc("备注")
    private java.lang.String memo;
    /** 鉴定计划年度 */
    @FieldDesc("鉴定计划年度")
    private java.lang.String planYear;
    /** 填报单位id */
    @FieldDesc("填报单位id")
    private java.lang.String planUnitid;
    /** 填报单位名称 */
    @FieldDesc("填报单位名称")
    private java.lang.String planUnitname;
    /** 填报时间 */
    @FieldDesc("填报时间")
    private java.util.Date planDate;
    /**
     * 鉴定计划状态,0：初始化状态,鉴定计划待提交，1：鉴定计划已提交,2:鉴定申请审批已提交，3：鉴定申请审批已完成，4，鉴定申请已提交，5鉴定申请已完成，6鉴定会申请已提交，7 鉴定会申请已完成，8 鉴定计划未完成 9
     * 鉴定计划已完成
     */
    @FieldDesc("状态")
    @Excel(name = "状态", width = 20, isExportConvert = true)
    private java.lang.String state;
    /** 创建人 */
    @FieldDesc("创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @FieldDesc("创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @FieldDesc("创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @FieldDesc("修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @FieldDesc("修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @FieldDesc("修改时间")
    private java.util.Date updateDate;
    /** 总经费 */
    @FieldDesc("总经费")
    private Double totalAmount;
    /** 组织鉴定单位 */
    @Excel(name = "组织鉴定单位", isExportConvert = true)
    @FieldDesc("组织鉴定单位")
    private String organizeUnit;

    private String otherSourceProjectIds;

    private String otherSourceProjectNames;

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
     * @return: java.lang.String 关联项目主键
     */
    @Column(name = "PROJECT_ID", nullable = true, length = 32)
    public java.lang.String getProjectId() {
        return this.projectId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 关联项目主键
     */
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
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

    /**
     * 成果名称
     * 
     * @return
     */
    @Column(name = "ACHIEVEMENT_NAME", nullable = true, length = 100)
    public java.lang.String getAchievementName() {
        return achievementName;
    }

    /**
     * 成果名称
     * 
     * @param achievementName
     */
    public void setAchievementName(java.lang.String achievementName) {
        this.achievementName = achievementName;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 研制开始时间
     */
    @Column(name = "PROJECT_BEGIN_DATE", nullable = true)
    public java.util.Date getProjectBeginDate() {
        return this.projectBeginDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 研制开始时间
     */
    public void setProjectBeginDate(java.util.Date projectBeginDate) {
        this.projectBeginDate = projectBeginDate;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 研制截止时间
     */
    @Column(name = "PROJECT_END_DATE", nullable = true)
    public java.util.Date getProjectEndDate() {
        return this.projectEndDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 研制截止时间
     */
    public void setProjectEndDate(java.util.Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 承研单位id
     */
    @Column(name = "PROJECT_DEVELOPER_DEPARTID", nullable = true, length = 32)
    public java.lang.String getProjectDeveloperDepartid() {
        return this.projectDeveloperDepartid;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 承研单位id
     */
    public void setProjectDeveloperDepartid(java.lang.String projectDeveloperDepartid) {
        this.projectDeveloperDepartid = projectDeveloperDepartid;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 承研单位名称
     */
    @Column(name = "PROJECT_DEVELOPER_DEPARTNAME", nullable = true, length = 60)
    public java.lang.String getProjectDeveloperDepartname() {
        return this.projectDeveloperDepartname;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 承研单位名称
     */
    public void setProjectDeveloperDepartname(java.lang.String projectDeveloperDepartname) {
        this.projectDeveloperDepartname = projectDeveloperDepartname;
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
     * @return: java.lang.String 项目负责人联系电话
     */
    @Column(name = "PROJECT_MANAGER_PHONE", nullable = true, length = 30)
    public java.lang.String getProjectManagerPhone() {
        return this.projectManagerPhone;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目负责人联系电话
     */
    public void setProjectManagerPhone(java.lang.String projectManagerPhone) {
        this.projectManagerPhone = projectManagerPhone;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 任务来源
     */
    @Column(name = "PROJECT_SOURCE_UNIT", nullable = true, length = 100)
    public java.lang.String getProjectSourceUnit() {
        return this.projectSourceUnit;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 任务来源
     */
    public void setProjectSourceUnit(java.lang.String projectSourceUnit) {
        this.projectSourceUnit = projectSourceUnit;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 依据文号
     */
    @Column(name = "ACCORDING_NUM", nullable = true, length = 24)
    public java.lang.String getProjectAccordingNum() {
        return projectAccordingNum;
    }

    public void setProjectAccordingNum(java.lang.String projectAccordingNum) {
        this.projectAccordingNum = projectAccordingNum;
    }

    @Column(name = "APPRAISAL_UNIT", nullable = true, length = 60)
    public java.lang.String getAppraisalUnit() {
        return appraisalUnit;
    }

    public void setAppraisalUnit(java.lang.String appraisalUnit) {
        this.appraisalUnit = appraisalUnit;
    }

    @Column(name = "APPRAISAL_CONTACT", nullable = true, length = 50)
    public java.lang.String getAppraisalContact() {
        return appraisalContact;
    }

    public void setAppraisalContact(java.lang.String appraisalContact) {
        this.appraisalContact = appraisalContact;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 鉴定主持联系电话
     */
    @Column(name = "APPRAISAL_CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getAppraisalContactPhone() {
        return this.appraisalContactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定主持联系电话
     */
    public void setAppraisalContactPhone(java.lang.String appraisalContactPhone) {
        this.appraisalContactPhone = appraisalContactPhone;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 鉴定时间
     */
    @Column(name = "APPRAISAL_TIME", nullable = true)
    public java.util.Date getAppraisalTime() {
        return this.appraisalTime;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 鉴定时间
     */
    public void setAppraisalTime(java.util.Date appraisalTime) {
        this.appraisalTime = appraisalTime;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 鉴定形式
     */
    @Column(name = "APPRAISAL_FORM", nullable = true, length = 2)
    public java.lang.String getAppraisalForm() {
        return this.appraisalForm;
    }

    /**
     * 转化为代码值
     * 
     * @return
     */
    public java.lang.String convertGetAppraisalForm() {
        return ConvertDicUtil.getConvertDic("1", "JDXS", this.appraisalForm);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定形式
     */
    public void setAppraisalForm(java.lang.String appraisalForm) {
        this.appraisalForm = appraisalForm;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 鉴定地点
     */
    @Column(name = "APPRAISAL_ADDRESS", nullable = true, length = 100)
    public java.lang.String getAppraisalAddress() {
        return this.appraisalAddress;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定地点
     */
    public void setAppraisalAddress(java.lang.String appraisalAddress) {
        this.appraisalAddress = appraisalAddress;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 备注
     */
    @Column(name = "MEMO", nullable = true, length = 300)
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
     * @return: java.lang.String 鉴定计划年度
     */
    @Column(name = "PLAN_YEAR", nullable = true, length = 4)
    public java.lang.String getPlanYear() {
        return this.planYear;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 鉴定计划年度
     */
    public void setPlanYear(java.lang.String planYear) {
        this.planYear = planYear;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 填报单位id
     */
    @Column(name = "PLAN_UNITID", nullable = true, length = 32)
    public java.lang.String getPlanUnitid() {
        return this.planUnitid;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 填报单位id
     */
    public void setPlanUnitid(java.lang.String planUnitid) {
        this.planUnitid = planUnitid;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 填报单位名称
     */
    @Column(name = "PLAN_UNITNAME", nullable = true, length = 60)
    public java.lang.String getPlanUnitname() {
        return this.planUnitname;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 填报单位名称
     */
    public void setPlanUnitname(java.lang.String planUnitname) {
        this.planUnitname = planUnitname;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 填报时间
     */
    @Column(name = "PLAN_DATE", nullable = true)
    public java.util.Date getPlanDate() {
        return this.planDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 填报时间
     */
    public void setPlanDate(java.util.Date planDate) {
        this.planDate = planDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 计划状态
     */
    @Column(name = "STATE", nullable = true, length = 1)
    public java.lang.String getState() {
        return state;
    }

    public java.lang.String convertGetState() {
        if (StringUtil.isNotEmpty(this.state)) {
            Map<String, Object> map = SrmipConstants.dicResearchMap.get("SPZT");
            if (map != null) {
                Object obj = map.get(this.state);
                if (obj != null)
                    return obj.toString();
            }
        }
        return state;
    }

    public void setState(java.lang.String state) {
        this.state = state;
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

    @Column(name = "TOTAL_AMOUNT", nullable = true, scale = 2, length = 6)
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Column(name = "ORGANIZE_UNIT")
    public String getOrganizeUnit() {
        return organizeUnit;
    }

    public java.lang.String convertGetOrganizeUnit() {
        return ConvertDicUtil.getConvertDic("1", "ZZJDDW", this.organizeUnit);
    }

    public void setOrganizeUnit(String organizeUnit) {
        this.organizeUnit = organizeUnit;
    }

    @Transient
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Column(name = "OTHER_SOURCE_PROJECT_IDS")
    public String getOtherSourceProjectIds() {
        return otherSourceProjectIds;
    }

    public void setOtherSourceProjectIds(String otherSourceProjectIds) {
        this.otherSourceProjectIds = otherSourceProjectIds;
    }

    @Column(name = "OTHER_SOURCE_PROJECT_NAMES")
    public String getOtherSourceProjectNames() {
        return otherSourceProjectNames;
    }

    public void setOtherSourceProjectNames(String otherSourceProjectNames) {
        this.otherSourceProjectNames = otherSourceProjectNames;
    }

    @Transient
    public String getProjectBeginEndDate() {
        StringBuffer sb = new StringBuffer();
        if (this.projectBeginDate != null) {
            String beginDate = DateUtils.formatDate(this.projectBeginDate, "yyyy-MM");
            sb.append(beginDate);
        }
        if (this.projectEndDate != null) {
            String endDate = DateUtils.formatDate(this.projectEndDate, "yyyy-MM");
            sb.append("~").append(endDate);
        }
        return sb.toString();
    }

    @Transient
    public java.lang.String getProjectManager() {
        StringBuffer sb = new StringBuffer();
        if (this.projectManagerName != null) {
            sb.append(this.projectManagerName);
        }
        if (this.projectManagerPhone != null) {
            sb.append(":").append(this.projectManagerPhone);
        }
        return sb.toString();
    }

    @Transient
    public java.lang.String getProjectSourceAccording() {
        StringBuffer sb = new StringBuffer();
        if (this.projectSourceUnit != null) {
            sb.append(this.projectSourceUnit);
        }

        if (this.projectAccordingNum != null) {
            sb.append("（").append(this.projectAccordingNum).append("）");
        }
        return sb.toString();
    }

    @Transient
    public java.lang.String getAppraisalUnitContact() {
        StringBuffer sb = new StringBuffer();
        if (this.appraisalUnit != null) {
            sb.append(this.appraisalUnit);
        }
        if (this.appraisalContact != null) {
            sb.append(",").append(this.appraisalContact);
        }
        if (this.appraisalContactPhone != null) {
            sb.append(":").append(this.appraisalContactPhone);
        }
        return sb.toString();
    }

}
