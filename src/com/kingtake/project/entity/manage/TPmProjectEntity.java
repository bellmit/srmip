package com.kingtake.project.entity.manage;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.base.entity.xmlb.TBJflxEntity;
import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 项目基本信息表
 * @author onlineGenerator
 * @date 2015-07-04 09:42:34
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_project")
@SuppressWarnings("serial")
@DynamicInsert
@DynamicUpdate
@LogEntity("项目基本信息")
public class TPmProjectEntity implements java.io.Serializable , Cloneable{
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 项目编号 */
    @Excel(name = "项目编号")
    @FieldDesc("项目编号")
    private java.lang.String projectNo;
    /** 项目状态 */
    @FieldDesc("项目状态")
    private java.lang.String projectStatus;
    /** 立项状态,0为未确认，1为已确认，2为已驳回 */
    @FieldDesc("立项状态")
    private java.lang.String approvalStatus;
    /** 项目名称 */
    @FieldDesc("项目名称")
    @Excel(name = "项目名称")
    private java.lang.String projectName;
    /** 负责人 */
    @FieldDesc("负责人")
    @Excel(name = "负责人")
    private java.lang.String projectManager;
    @FieldDesc("负责人id")
    private java.lang.String projectManagerId;
    /** 项目简介 */
    @FieldDesc("项目简介")
    @Excel(name = "项目简介")
    private java.lang.String projectAbstract;
    /** 起始日期 */
    @FieldDesc("起始日期")
    @Excel(name = "起始日期", exportFormat = "yyyy-MM-dd")  
    private java.util.Date beginDate;
    /** 截止日期 */
    @FieldDesc("截止日期")
    @Excel(name = "截止日期", exportFormat = "yyyy-MM-dd")  
    private java.util.Date endDate;
    /** 分管部门 */
    @FieldDesc("分管部门")
    @Excel(name = "分管部门")
    private java.lang.String manageDepart;
    /**
     * 分管部门编码
     */
    @FieldDesc("分管部门编码")
    private String manageDepartCode;
    /** 负责人电话 */
    @FieldDesc("负责人电话")
    @Excel(name = "负责人电话")
    private java.lang.String managerPhone;
    /** 联系人 */
    @FieldDesc("联系人")
    @Excel(name = "联系人")
    private java.lang.String contact;
    /** 联系人电话 */
    @FieldDesc("联系人电话")
    @Excel(name = "联系人电话")
    private java.lang.String contactPhone;
    /** 计划合同标志 */
    @FieldDesc("计划合同标志")
    @Excel(name = "计划合同标志",isExportConvert=true)
    private java.lang.String planContractFlag;
    /** 项目类型 */
    @FieldDesc("项目类型")
    //@Excel(name = "项目类型")
    //@ExcelEntity(id="projectTypeName")
    private TBProjectTypeEntity projectType;
    @Excel(name="项目类型")
    private String projectTypeStr;
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
    
    /** 项目类别   修改项目类别改为项目类型   by yixiaoping 20180316*/
    @FieldDesc("项目类别")
    private TBXmlbEntity xmlb;
    @FieldDesc("项目类别名称")
    @Excel(name="项目类型")
    private String xmlbStr;
    
    /** 经费类型 */
    @FieldDesc("经费类型")
    private TBJflxEntity jflx;
    @FieldDesc("经费类型名称")
    @Excel(name="经费类型")
    private String jflxStr;
    
    /** 主管单位 */
    @FieldDesc("主管单位")
    @Excel(name = "主管单位")
    private java.lang.String zgdw;
    /** 项目类型  修改  项目类型改为项目属性 by yixiaoping 20180316*/
    @FieldDesc("项目属性")
    @Excel(name = "项目属性")
    private java.lang.String xmlx;
    /** 项目性质 */
    @FieldDesc("项目性质")
    @Excel(name = "项目性质")
    private java.lang.String xmxz;
    /** 项目来源 */
    @FieldDesc("项目来源")
    @Excel(name = "项目来源")
    private java.lang.String xmly;
    /** 联合申报 */
    @FieldDesc("联合申报")
    @Excel(name = "联合申报")
    private java.lang.String lhsb;
    /** 项目母类别 */
    @FieldDesc("项目母类别")
    @Excel(name = "项目母类别")
    private java.lang.String xmml;
    
    /** 外来编号 */
    @FieldDesc("外来编号")
    @Excel(name = "外来编号")
    private java.lang.String outsideNo;
    /** 经费类型 */
    @FieldDesc("经费类型")
    private TBFundsPropertyEntity feeType;
    @Excel(name="经费类型")
    private String feeTypeStr;
    /** 子类型 */
    @FieldDesc("子类型")
    @Excel(name = "子类型")
    private java.lang.String subType;
    /** 会计编码 */
    @FieldDesc("会计编码")
    @Excel(name = "会计编码")
    private java.lang.String accountingCode;
    /** 原会计编码 */
    @FieldDesc("原会计编码")
    private java.lang.String oldAccountingCode;
    /** 合同计划文号 */
    @FieldDesc("合同计划文号")
    @Excel(name = "合同计划文号")
    private java.lang.String planContractRefNo;
    /** 合同日期 */
    @FieldDesc("合同日期")
    @Excel(name = "合同日期", exportFormat = "yyyy-MM-dd")  
    private java.util.Date contractDate;
    /** 合同计划名称 */
    @FieldDesc("合同计划名称")
    @Excel(name = "合同计划名称")
    private java.lang.String planContractName;
    /** 来源单位 */
    @FieldDesc("来源单位")
    @Excel(name = "来源单位")
    private java.lang.String sourceUnit;
    /** 项目来源 */
    @FieldDesc("项目来源")
    @Excel(name = "项目来源")
    private java.lang.String projectSource;

    @FieldDesc("承研部门实体")
    private TSDepart devDepart;
    @Excel(name="承研部门")
    private String devDepartStr;
    
    @FieldDesc("部门类型")
    @Excel(name="部门类型")
    private String bmlx;
    
    /** 责任部门 */
    @FieldDesc("责任部门")
//    @Excel(name = "责任部门")
    private TSDepart dutyDepart;
    
    @Excel(name="责任部门")
    private String dutyDepartStr;

	/** 经费单列 */
    @FieldDesc("经费单列")
    @Excel(name = "经费单列")
    private java.lang.String feeSingleColumn;
    /** 项目密级 */
    @FieldDesc("项目密级")
    @Excel(name = "项目密级",isExportConvert=true)
    private java.lang.String secretDegree;
    /** 是否需要鉴定 */
    @FieldDesc("是否需要鉴定")
    @Excel(name = "是否需要鉴定",isExportConvert=true)
    private java.lang.String appraisalFlag;
    /** 是否已经出校检验 */
    @FieldDesc("是否已经出校检验")
    @Excel(name = "是否已经出校检验",isExportConvert=true)
    private java.lang.String checkapprovalFlag;
    /** 总经费 */
    @FieldDesc("总经费")
    @Excel(name = "总经费",isAmount = true)
    private java.math.BigDecimal allFee;
    /** 预算金额 */
    @FieldDesc("预算金额")
    @Excel(name = "预算金额",isAmount = true)
    private java.math.BigDecimal budgetAmount;

    /** 责任机关参谋 */
    @FieldDesc("责任机关参谋")
    @Excel(name = "责任机关参谋",isExportConvert=true)
    private java.lang.String officeStaff;    
    
    /** 责任院系参谋 */
    @FieldDesc("责任院系参谋")
    @Excel(name = "责任院系参谋",isExportConvert=true)
    private java.lang.String departStaff;
    /** 原项目编号 */
    @Excel(name = "原项目编号")
    @FieldDesc("原项目编号")
    private java.lang.String yxmbh;
    /** 已安排 */
    @Excel(name = "已安排")
    @FieldDesc("已安排")
    private java.lang.String yap;
    /** 当年经费 */
    @Excel(name = "当年经费")
    @FieldDesc("当年经费")
    private java.lang.String dnjf;
    /** 账面余额 */
    @Excel(name = "账面余额")
    @FieldDesc("账面余额")
    private java.lang.String zmye;
    /** 备注 */
    @Excel(name = "备注")
    @FieldDesc("备注")
    private java.lang.String bz;
    
    /** 删除标识 0未删 1已删 */
    @FieldDesc("删除")
    private java.lang.String scbz;
    

    /** 计划年度 */
    //    @FieldDesc("计划年度")
    //    @Excel(name = "计划年度")
    //    private java.lang.String planYear;
    /** 依据文号 */
    @FieldDesc("依据文号")
    @Excel(name = "依据文号")
    private java.lang.String accordingNum;
    /** 是否重大专项 */
    @FieldDesc("是否重大专项")
    @Excel(name = "是否重大专项",isExportConvert=true)
    private java.lang.String greatSpecialFlag;
    /** 是否校内协作 */
    @FieldDesc("是否校内协作")
    @Excel(name = "是否校内协作",isExportConvert=true)
    private java.lang.String schoolCooperationFlag;
    
    /** 项目编号的流水号 */
    @FieldDesc("流水号")
    private java.lang.String lsh;
    
    /** 查询码 */
    @FieldDesc("查询码")
    private java.lang.String cxm;
    
    /**
     * 母项目
     */
    @FieldDesc("所属母项目")
    private TPmProjectEntity parentPmProject;
    
    /**
     * 所属母项目名称
     */
    private String parentProjectName;
    

    /** 任务指定标志位 */
    @FieldDesc("任务指定标志位")
    @Excel(name = "任务指定标志位")
    private String rwzd;
    

    /** 立项依据标志位 */
    @FieldDesc("立项依据标志位")
    @Excel(name = "立项依据标志位")
    private String lxyj;
    

    /** 审核状态,0 未提交 1 已提交 2 通过 3 不通过 */
    @FieldDesc("审核状态")
    @Excel(name = "审核状态")
    private String auditStatus;
    
    /**
     * 立项状态,0 未立项 1 已立项 
     */
    private String lxStatus;
    
    /** 关联项目 */
    @FieldDesc("关联项目")
    private TPmProjectEntity glxm;
    @Excel(name="关联项目名称")
    private String glxmStr;

    /**
     * 项目指派,1表示是
     */
    private String assignFlag;
    
    /**
     * 间接费用，1表示母项目承担，2表示母子项目分担
     */
    private String indirect;
    
    /**
     * 科目代码
     */
    private String subject_code;
    
    /**
     * 零基预算状态，0未申请，1正在申请，2已提交给财务，3可做调整预算，4余额不足无法做调整
     */
    private String tzys_status;
    
	/**
     * 型号背景
     */
    @FieldDesc("型号背景")
    @Excel(name = "型号背景")
    private String modelBackground;
    
    /**
     * 舰艇舷号
     */
    @FieldDesc("舰艇舷号")
    @Excel(name = "舰艇舷号")
    private String shipNumber;
    
    /**
     * 是否归档。0-未归档、1-已归档
     */
    @FieldDesc("是否归档")
    @Excel(name = "是否归档")
    private String gdStatus;
    
    /**
     * 配套情况
     */
    @FieldDesc("配套情况")
    @Excel(name = "配套情况")
    private String matchSituation;
    
    /**
     * 审批权限
     */
    @FieldDesc("审批权限")
    @Excel(name = "审批权限")
    private String approvalAuthority;

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

    private String mergeFlag;
    private TPmProjectEntity mergeProject;
    
    /** 计划下达主键,记录最后一次的计划下达，完整计划下达列表查t_pm_fpje */
    private java.lang.String jhid;
    
    /**
     * 
     * 是否需要认款，1表示是，0表示否
     */
    @FieldDesc("是否需要认款")
    private String incomeApplyFlag = "1";
    /**
     * added by zhangls
     * date 2016-03-03
     */
    @FieldDesc("课题组确认人id")
    private java.lang.String confirmBy;
    @FieldDesc("课题组确认人姓名")
    private java.lang.String confirmName;
    @FieldDesc("课题组确认时间")
    private java.util.Date confirmDate;
    @FieldDesc("机关确定立项操作人id")
    private java.lang.String approvalUserid;
    @FieldDesc("机关确定立项操作时间")
    private java.util.Date approvalDate;
    /**
     * 机关确定操作人部门id
     */
    private String approvalDeptId;
    /**
     * 是否生成计划草案，0 未生成 ，1 已生成
     */
    private String planFlag;

    /**
     * 修改意见
     */
    private String msgText;
    
    /**
     * 上报状态，0-未生效(上级未批)，1-已上报（上报给上级）
     */
    private String reportState;
    
    /**
     * 预算类型
     */
    private String yslx;
    
    /**
     * 间接费计算方式
     */
    private String jjfjsfs;
    
    /**
     * 大学比例
     */
    private String dxbl;
    
    /**
     * 责任单位i比例
     */
    private String zrdwbl;
    
    /**
     * 承研单位比例
     */
    private String cydwbl;
    
    /**
     * 项目组比例
     */
    private String xmzbl;
    
    

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
     * @return: java.lang.String 项目编号
     */
    @Column(name = "PROJECT_NO", nullable = true, length = 20)
    public java.lang.String getProjectNo() {
        return this.projectNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目编号
     */
    public void setProjectNo(java.lang.String projectNo) {
        this.projectNo = projectNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目状态
     */
    @Column(name = "PROJECT_STATUS", nullable = true, length = 1)
    public java.lang.String getProjectStatus() {
        return this.projectStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目状态
     */
    public void setProjectStatus(java.lang.String projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * 立项状态
     * 
     * @return
     */
    @Column(name = "APPROVAL_STATUS", nullable = true, length = 1)
    public java.lang.String getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * 立项状态
     * 
     * @return
     */
    public void setApprovalStatus(java.lang.String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
    /**
     * 上报状态
     * 
     * @return
     */
    @Column(name = "REPORT_STATE", nullable = true, length = 1)
    public java.lang.String getReportState() {
        return reportState;
    }

    /**
     * 上报状态
     * 
     * @return
     */
    public void setReportState(java.lang.String reportState) {
        this.reportState = reportState;
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
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 负责人
     */
    @Column(name = "PROJECT_MANAGER", nullable = false, length = 50)
    public java.lang.String getProjectManager() {
        return this.projectManager;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 负责人
     */
    public void setProjectManager(java.lang.String projectManager) {
        this.projectManager = projectManager;
    }

    @Column(name = "PROJECT_MANAGER_ID", nullable = false, length = 50)
    public java.lang.String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(java.lang.String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目简介
     */
    @Column(name = "PROJECT_ABSTRACT", nullable = true, length = 800)
    public java.lang.String getProjectAbstract() {
        return this.projectAbstract;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目简介
     */
    public void setProjectAbstract(java.lang.String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 起始日期
     */
    @Column(name = "BEGIN_DATE", nullable = true)
    public java.util.Date getBeginDate() {
        return this.beginDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 起始日期
     */
    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 截止日期
     */
    @Column(name = "END_DATE", nullable = true)
    public java.util.Date getEndDate() {
        return this.endDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 截止日期
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 分管部门
     */
    @Column(name = "MANAGE_DEPART", nullable = true, length = 60)
    public java.lang.String getManageDepart() {
        return this.manageDepart;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 分管部门
     */
    public void setManageDepart(java.lang.String manageDepart) {
        this.manageDepart = manageDepart;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 负责人电话
     */
    @Column(name = "MANAGER_PHONE", nullable = true, length = 30)
    public java.lang.String getManagerPhone() {
        return this.managerPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 负责人电话
     */
    public void setManagerPhone(java.lang.String managerPhone) {
        this.managerPhone = managerPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人
     */
    @Column(name = "CONTACT", nullable = true, length = 50)
    public java.lang.String getContact() {
        return this.contact;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人
     */
    public void setContact(java.lang.String contact) {
        this.contact = contact;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人电话
     */
    @Column(name = "CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人电话
     */
    public void setContactPhone(java.lang.String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 计划合同标志
     */
    @Column(name = "PLAN_CONTRACT_FLAG", nullable = true, length = 1)
    public java.lang.String getPlanContractFlag() {
        return this.planContractFlag;
    }

    public java.lang.String convertGetPlanContractFlag() {
        if (ProjectConstant.PROJECT_PLAN.equals(this.planContractFlag)) {
            return "计划类";
        } else {
            return "合同类";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 计划合同标志
     */
    public void setPlanContractFlag(java.lang.String planContractFlag) {
        this.planContractFlag = planContractFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目类型
     */
    @ManyToOne
    @JoinColumn(name = "PROJECT_TYPE")
    @NotFound(action = NotFoundAction.IGNORE)
    //忽略关联不上
    public TBProjectTypeEntity getProjectType() {
        return this.projectType;
    }

    public java.lang.String convertGetProjectType() {
        return this.projectType.getProjectTypeName();
    }

    public void setProjectType(TBProjectTypeEntity projectType) {
        this.projectType = projectType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 外来编号
     */
    @Column(name = "OUTSIDE_NO", nullable = true, length = 20)
    public java.lang.String getOutsideNo() {
        return this.outsideNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 外来编号
     */
    public void setOutsideNo(java.lang.String outsideNo) {
        this.outsideNo = outsideNo;
    }

    @ManyToOne
    @JoinColumn(name = "FEE_TYPE")
    public TBFundsPropertyEntity getFeeType() {
        return feeType;
    }

    public void setFeeType(TBFundsPropertyEntity feeType) {
        this.feeType = feeType;
    }

    public java.lang.String convertGetFeeType() {
        return feeType.getFundsName();
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 子类型
     */
    @Column(name = "SUB_TYPE", nullable = true, length = 40)
    public java.lang.String getSubType() {
        return this.subType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 子类型
     */
    public void setSubType(java.lang.String subType) {
        this.subType = subType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 会计编码
     */
    @Column(name = "ACCOUNTING_CODE", nullable = true, length = 20)
    public java.lang.String getAccountingCode() {
        return this.accountingCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 会计编码
     */
    public void setAccountingCode(java.lang.String accountingCode) {
        this.accountingCode = accountingCode;
    }
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 原会计编码
     */
    @Column(name = "OLD_ACCOUNTING_CODE", nullable = true, length = 20)
    public java.lang.String getOldAccountingCode() {
        return this.oldAccountingCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 原会计编码
     */
    public void setOldAccountingCode(java.lang.String oldAccountingCode) {
        this.oldAccountingCode = oldAccountingCode;
    }
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同计划文号
     */
    @Column(name = "PLAN_CONTRACT_REF_NO", nullable = true, length = 24)
    public java.lang.String getPlanContractRefNo() {
        return this.planContractRefNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同计划文号
     */
    public void setPlanContractRefNo(java.lang.String planContractRefNo) {
        this.planContractRefNo = planContractRefNo;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 合同日期
     */
    @Column(name = "CONTRACT_DATE", nullable = true)
    public java.util.Date getContractDate() {
        return this.contractDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 合同日期
     */
    public void setContractDate(java.util.Date contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同计划名称
     */
    @Column(name = "PLAN_CONTRACT_NAME", nullable = true, length = 100)
    public java.lang.String getPlanContractName() {
        return this.planContractName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同计划名称
     */
    public void setPlanContractName(java.lang.String planContractName) {
        this.planContractName = planContractName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 来源单位
     */
    @Column(name = "SOURCE_UNIT", nullable = true, length = 60)
    public java.lang.String getSourceUnit() {
        return this.sourceUnit;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 来源单位
     */
    public void setSourceUnit(java.lang.String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目来源
     */
    @Column(name = "PROJECT_SOURCE", nullable = true, length = 100)
    public java.lang.String getProjectSource() {
        return this.projectSource;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目来源
     */
    public void setProjectSource(java.lang.String projectSource) {
        this.projectSource = projectSource;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 责任部门
     */
    @ManyToOne
    @JoinColumn(name = "DUTY_DEPART")
    public TSDepart getDutyDepart() {
        return this.dutyDepart;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 责任部门
     */
    public void setDutyDepart(TSDepart dutyDepart) {
        this.dutyDepart = dutyDepart;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 经费单列
     */
    @Column(name = "FEE_SINGLE_COLUMN", nullable = true, length = 20)
    public java.lang.String getFeeSingleColumn() {
        return this.feeSingleColumn;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 经费单列
     */
    public void setFeeSingleColumn(java.lang.String feeSingleColumn) {
        this.feeSingleColumn = feeSingleColumn;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目密级
     */
    @Column(name = "SECRET_DEGREE", nullable = false, length = 1)
    public java.lang.String getSecretDegree() {
        return this.secretDegree;
    }
   
	public java.lang.String convertGetSecretDegree() {
        return ConvertDicUtil.getConvertDic("0", "XMMJ", this.secretDegree);
        
    }
    
    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目密级
     */
    public void setSecretDegree(java.lang.String secretDegree) {
        this.secretDegree = secretDegree;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 是否需要鉴定
     */
    @Column(name = "APPRAISAL_FLAG", nullable = true, length = 1)
    public java.lang.String getAppraisalFlag() {
        return this.appraisalFlag;
    }
    
    public java.lang.String convertGetAppraisalFlag() {
        if (SrmipConstants.YES.equals(this.appraisalFlag)) {
            return "是";
        } else {
            return "否";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 是否需要鉴定
     */
    public void setAppraisalFlag(java.lang.String appraisalFlag) {
        this.appraisalFlag = appraisalFlag;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 是否已经出校检验
     */
    @Column(name = "CHECKAPPROVAL_FLAG", nullable = true, length = 1)
    public java.lang.String getCheckapprovalFlag() {
        return this.checkapprovalFlag;
    }
    
    public java.lang.String convertGetCheckapprovalFlag() {
        if (SrmipConstants.YES.equals(this.checkapprovalFlag)) {
            return "是";
        } else {
            return "否";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 是否已经出校检验
     */
    public void setCheckapprovalFlag(java.lang.String checkapprovalFlag) {
        this.checkapprovalFlag = checkapprovalFlag;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 总经费
     */
    @Column(name = "ALL_FEE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getAllFee() {
        return this.allFee;
    }

//    public java.lang.String convertGetAllFee() {
//    	String allFee = "";
//    	if (this.allFee != null) {
//    		allFee= DecimalFormatUtil.format(this.allFee);
//    	}
//    	return allFee;
//    }
    
    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 总经费
     */
    public void setAllFee(java.math.BigDecimal allFee) {
        this.allFee = allFee;
    }
    
    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 预算金额
     */
    @Column(name = "BUDGET_AMOUNT")
    public java.math.BigDecimal getBudgetAmount() {
        return this.budgetAmount;
    }
    
    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 预算金额
     */
    public void setBudgetAmount(java.math.BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    @ManyToOne
    @JoinColumn(name = "parent_project")
    public TPmProjectEntity getParentPmProject() {
        return parentPmProject;
    }

    public void setParentPmProject(TPmProjectEntity parentPmProject) {
        this.parentPmProject = parentPmProject;
    }

    @ManyToOne
    @JoinColumn(name = "developer_depart")
    public TSDepart getDevDepart() {
        return devDepart;
    }

    public void setDevDepart(TSDepart devDepart) {
        this.devDepart = devDepart;
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

    //    @Column(name = "PLAN_YEAR")
    //    public java.lang.String getPlanYear() {
    //        return planYear;
    //    }
    //
    //    public void setPlanYear(java.lang.String planYear) {
    //        this.planYear = planYear;
    //    }

    @Column(name = "ACCORDING_NUM")
    public java.lang.String getAccordingNum() {
        return accordingNum;
    }

    public void setAccordingNum(java.lang.String accordingNum) {
        this.accordingNum = accordingNum;
    }

    @Column(name = "GREAT_SPECIAL_FLAG")
    public java.lang.String getGreatSpecialFlag() {
        return greatSpecialFlag;
    }

    public java.lang.String convertGetGreatSpecialFlag() {
        if (SrmipConstants.YES.equals(this.greatSpecialFlag)) {
            return "是";
        } else {
            return "否";
        }
    }
    
    public void setGreatSpecialFlag(java.lang.String greatSpecialFlag) {
        this.greatSpecialFlag = greatSpecialFlag;
    }
    
    /**
     * 是否校内协作
     * 
     * @return
     */
    @Column(name = "SCHOOL_COOPERATION_FLAG")
    public java.lang.String getSchoolCooperationFlag() {
        return schoolCooperationFlag;
    }

    public java.lang.String convertGetSchoolCooperationFlag() {
        if (SrmipConstants.YES.equals(this.schoolCooperationFlag)) {
            return "是";
        } else {
            return "否";
        }
    }
    
    public void setSchoolCooperationFlag(java.lang.String schoolCooperationFlag) {
        this.schoolCooperationFlag = schoolCooperationFlag;
    }

    /**
     * 项目合并标识
     * 
     * @return
     */
    @Column(name = "MERGE_FLAG")
    public String getMergeFlag() {
        return mergeFlag;
    }

    /**
     * 项目合并标识0-正常，1-合并后新项目，2-被合并项目
     * 
     * @return
     */
    public void setMergeFlag(String mergeFlag) {
        this.mergeFlag = mergeFlag;
    }

    @ManyToOne
    @JoinColumn(name = "MERGE_PROJECT_ID")
    public TPmProjectEntity getMergeProject() {
        return mergeProject;
    }

    public void setMergeProject(TPmProjectEntity mergeProject) {
        this.mergeProject = mergeProject;
    }

    @Column(name = "OFFICE_STAFF", length = 32)
    public java.lang.String getOfficeStaff() {
        return officeStaff;
    }
    
    public java.lang.String convertGetOfficeStaff() { 
    	String officeStaffName = "";
        if (StringUtil.isNotEmpty(this.officeStaff)) {
        	SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            TSUser user = systemService.get(TSUser.class, this.officeStaff);                        
            if (user != null) {
            	officeStaffName= user.getRealName();
            }
        }                        
        return officeStaffName;
    }

    public void setOfficeStaff(java.lang.String officeStaff) {
        this.officeStaff = officeStaff;        
    }
       

	@Column(name = "DEPART_STAFF", length = 32)
    public java.lang.String getDepartStaff() {
        return departStaff;
    }

    public java.lang.String convertGetDepartStaff() { 
    	String departStaffName = "";
        if (StringUtil.isNotEmpty(this.departStaff)) {
        	SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            TSUser user = systemService.get(TSUser.class, this.departStaff);                        
            if (user != null) {
            	departStaffName= user.getRealName();
            }
        }                        
        return departStaffName;
    }
	
    public void setDepartStaff(java.lang.String departStaff) {
        this.departStaff = departStaff;
    }

    @Column(name = "MANAGE_DEPART_CODE")
    public String getManageDepartCode() {
        return manageDepartCode;
    }

    public void setManageDepartCode(String manageDepartCode) {
        this.manageDepartCode = manageDepartCode;
    }
    
    @Column(name = "CONFIRM_BY")
    public java.lang.String getConfirmBy() {
      return confirmBy;
    }

    public void setConfirmBy(java.lang.String confirmBy) {
      this.confirmBy = confirmBy;
    }

    @Column(name = "CONFIRM_NAME")
    public java.lang.String getConfirmName() {
      return confirmName;
    }

    public void setConfirmName(java.lang.String confirmName) {
      this.confirmName = confirmName;
    }

    @Column(name = "CONFIRM_DATE")
    public java.util.Date getConfirmDate() {
      return confirmDate;
    }

    public void setConfirmDate(java.util.Date confirmDate) {
      this.confirmDate = confirmDate;
    }

    @Column(name = "APPROVAL_USERID")
    public java.lang.String getApprovalUserid() {
      return approvalUserid;
    }

    public void setApprovalUserid(java.lang.String approvalUserid) {
      this.approvalUserid = approvalUserid;
    }

    @Column(name = "APPROVAL_DATE")
    public java.util.Date getApprovalDate() {
      return approvalDate;
    }

    public void setApprovalDate(java.util.Date approvalDate) {
      this.approvalDate = approvalDate;
    }

    @Column(name = "APPROVAL_DEPT_ID")
    public String getApprovalDeptId() {
        return approvalDeptId;
    }

    public void setApprovalDeptId(String approvalDeptId) {
        this.approvalDeptId = approvalDeptId;
    }

    @Column(name = "plan_flag")
    public String getPlanFlag() {
        return planFlag;
    }

    public void setPlanFlag(String planFlag) {
        this.planFlag = planFlag;
    }

    @Column(name = "msg_text")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

	@Transient
	public String getFeeTypeStr() {
		String feeTypeStr = "";
		if(this.feeType!=null){
			feeTypeStr = feeType.getFundsName();
		}
		return feeTypeStr;
	}

	public void setFeeTypeStr(String feeTypeStr) {
		this.feeTypeStr = feeTypeStr;
	}

	@Transient
	public String getProjectTypeStr() {
		String projectTypeStr = "";
		if (this.projectType != null) {
			projectTypeStr = projectType.getProjectTypeName();
		}
		return projectTypeStr;
	}

	public void setProjectTypeStr(String projectTypeStr) {
		this.projectTypeStr = projectTypeStr;
	}

	@Transient
	public String getDutyDepartStr() {
		
		String dutyDepartStr = "";
		if (this.dutyDepart != null) {
			dutyDepartStr = dutyDepart.getDepartname();
		}
		return dutyDepartStr;
	}

	public void setDutyDepartStr(String dutyDepartStr) {
		this.dutyDepartStr = dutyDepartStr;
	}

	@Transient
	public String getDevDepartStr() {		
		String devDepartStr = "";
		if (this.devDepart != null) {
			devDepartStr = devDepart.getDepartname();
		}
		return devDepartStr;
	}

	public void setDevDepartStr(String devDepartStr) {
		this.devDepartStr = devDepartStr;
	}
	
	@Transient
	public String getBmlx() {		
		String bmlx = "";
		if (this.devDepart != null) {
			bmlx = devDepart.getBmlx();
		}
		return bmlx;
	}

	public void setBmlx(String bmlx) {
		this.bmlx = bmlx;
	}

    @Column(name = "assign_flag")
    public String getAssignFlag() {
        return assignFlag;
    }

    public void setAssignFlag(String assignFlag) {
        this.assignFlag = assignFlag;
    }

    @Transient
    public String getParentProjectName() {
        String parentProjectName = "";
        if(this.parentPmProject!=null){
            parentProjectName = this.parentPmProject.getProjectName()+"("+this.parentPmProject.getProjectTypeStr()+")";
        }
        return parentProjectName;
    }

    public void setParentProjectName(String parentProjectName) {
        this.parentProjectName = parentProjectName;
    }
    
    @Column(name = "rwzd")
    public String getRwzd() {
        return rwzd;
    }

    public void setRwzd(String rwzd) {
        this.rwzd = rwzd;
    }
    
    @Column(name = "lxyj")
    public String getLxyj() {
        return lxyj;
    }

    public void setLxyj(String lxyj) {
        this.lxyj = lxyj;
    }

    @Column(name="income_apply_flag")
    public String getIncomeApplyFlag() {
        return incomeApplyFlag;
    }

    public void setIncomeApplyFlag(String incomeApplyFlag) {
        this.incomeApplyFlag = incomeApplyFlag;
    }
    
    @Column(name="MODEL_BACKGROUND")
    public String getModelBackground() {
        return modelBackground;
    }

    public void setModelBackground(String modelBackground) {
        this.modelBackground = modelBackground;
    }
   
    @Column(name = "SHIP_NUMBER")
    public String getShipNumber() {
        return this.shipNumber;
    }

    public void setShipNumber(String shipNumber) {
        this.shipNumber = shipNumber;
    }
    
    @Column(name = "GD_STATUS")
    public String getGdStatus() {
        return this.gdStatus;
    }

    public void setGdStatus(String gdStatus) {
        this.gdStatus = gdStatus;
    }
    
    @Column(name="MATCH_SITUATION")
    public String getMatchSituation() {
        return matchSituation;
    }

    public void setMatchSituation(String matchSituation) {
        this.matchSituation = matchSituation;
    }
        
    @Column(name="APPROVAL_AUTHORITY")
    public String getApprovalAuthority() {
        return approvalAuthority;
    }

    public void setApprovalAuthority(String approvalAuthority) {
        this.approvalAuthority = approvalAuthority;
    }
    
    @Column(name="indirect")
    public String getIndirect() {
		return indirect;
	}

	public void setIndirect(String indirect) {
		this.indirect = indirect;
	}
	
	@Column(name="subject_code")
    public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}
	
	@Column(name="tzys_status")
    public String getTzys_status() {
		return tzys_status;
	}

	public void setTzys_status(String tzys_status) {
		this.tzys_status = tzys_status;
	}
	
	/**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目类别
     */
    @ManyToOne
    @JoinColumn(name = "XMLB")
    @NotFound(action = NotFoundAction.IGNORE)
    //忽略关联不上
    public TBXmlbEntity getXmlb() {
        return this.xmlb;
    }

    public java.lang.String convertGetXmlb() {
        return this.xmlb.getXmlb();
    }

    public void setXmlb(TBXmlbEntity xmlb) {
        this.xmlb = xmlb;
    }
    
    @Transient
	public String getXmlbStr() {
		String xmlbStr = "";
		if (this.xmlb != null) {
			xmlbStr = xmlb.getXmlb();
		}
		return xmlbStr;
	}

	public void setXmlbStr(String xmlbStr) {
		this.xmlbStr = xmlbStr;
	}
	
	/**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 经费类型
     */
    @ManyToOne
    @JoinColumn(name = "JFLX")
    @NotFound(action = NotFoundAction.IGNORE)
    //忽略关联不上
    public TBJflxEntity getJflx() {
        return this.jflx;
    }

    public java.lang.String convertGetJflx() {
        return this.jflx.getJflxmc();
    }

    public void setJflx(TBJflxEntity jflx) {
        this.jflx = jflx;
    }
    
    @Transient
	public String getJflxStr() {
		String jflxStr = "";
		if (this.jflx != null) {
			jflxStr = jflx.getJflxmc();
		}
		return jflxStr;
	}

	public void setJflxStr(String jflxStr) {
		this.jflxStr = jflxStr;
	}
	
	/**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目类型
     */
    @Column(name = "XMLX", nullable = true, length = 4)
    public java.lang.String getXmlx() {
        return this.xmlx;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目类型
     */
    public void setXmlx(java.lang.String xmlx) {
        this.xmlx = xmlx;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目性质
     */
    @Column(name = "XMXZ", nullable = true, length = 4)
    public java.lang.String getXmxz() {
        return this.xmxz;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目性质
     */
    public void setXmxz(java.lang.String xmxz) {
        this.xmxz = xmxz;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目来源
     */
    @Column(name = "XMLY", nullable = true, length = 4)
    public java.lang.String getXmly() {
        return this.xmly;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目来源
     */
    public void setXmly(java.lang.String xmly) {
        this.xmly = xmly;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主管单位
     */
    @Column(name = "ZGDW", nullable = true, length = 100)
    public java.lang.String getZgdw() {
        return this.zgdw;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主管单位
     */
    public void setZgdw(java.lang.String zgdw) {
        this.zgdw = zgdw;
    }
	
	/**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 流水号
     */
    @Column(name = "LSH", nullable = true, length = 20)
    public java.lang.String getLsh() {
        return this.lsh;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 流水号
     */
    public void setLsh(java.lang.String lsh) {
        this.lsh = lsh;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 查询码
     */
    @Column(name = "CXM", nullable = true, length = 50)
    public java.lang.String getCxm() {
        return this.cxm;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 查询码
     */
    public void setCxm(java.lang.String cxm) {
        this.cxm = cxm;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联合申报
     */
    @Column(name = "LHSB", nullable = true, length = 4)
    public java.lang.String getLhsb() {
        return this.lhsb;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联合申报
     */
    public void setLhsb(java.lang.String lhsb) {
        this.lhsb = lhsb;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目母类别
     */
    @Column(name = "XMML", nullable = true, length = 50)
    public java.lang.String getXmml() {
        return this.xmml;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目母类别
     */
    public void setXmml(java.lang.String xmml) {
        this.xmml = xmml;
    }
    
    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
    
    @Column(name = "lx_status")
    public String getLxStatus() {
        return lxStatus;
    }

    public void setLxStatus(String lxStatus) {
        this.lxStatus = lxStatus;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联项目
     */
    @ManyToOne
    @JoinColumn(name = "GLXM")
    @NotFound(action = NotFoundAction.IGNORE)
    public TPmProjectEntity getGlxm() {
        return this.glxm;
    }

    public void setGlxm(TPmProjectEntity glxm) {
        this.glxm = glxm;
    }
    
    @Transient
	public String getGlxmStr() {
		String glxmStr = "";
		if (this.glxm != null) {
			glxmStr = glxm.getProjectName();
		}
		return glxmStr;
	}

	public void setGlxmStr(String glxmStr) {
		this.glxmStr = glxmStr;
	}
	
	@Column(name = "JHID", nullable = true, length = 32)
    public java.lang.String getJhid() {
        return this.jhid;
    }

    public void setJhid(java.lang.String jhid) {
        this.jhid = jhid;
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
    
    @Column(name = "YXMBH", nullable = true, length = 32)
    public java.lang.String getYxmbh() {
        return this.yxmbh;
    }

    public void setYxmbh(java.lang.String yxmbh) {
        this.yxmbh = yxmbh;
    }

    @Column(name = "YAP", nullable = true, length = 32)
	public java.lang.String getYap() {
		return yap;
	}

	public void setYap(java.lang.String yap) {
		this.yap = yap;
	}

	@Column(name = "DNJF", nullable = true, length = 32)
	public java.lang.String getDnjf() {
		return dnjf;
	}

	public void setDnjf(java.lang.String dnjf) {
		this.dnjf = dnjf;
	}

	@Column(name = "ZMYE", nullable = true, length = 32)
	public java.lang.String getZmye() {
		return zmye;
	}

	public void setZmye(java.lang.String zmye) {
		this.zmye = zmye;
	}

	@Column(name = "BZ", nullable = true, length = 500)
	public java.lang.String getBz() {
		return bz;
	}

	public void setBz(java.lang.String bz) {
		this.bz = bz;
	}
	
	@Column(name = "SCBZ", nullable = true, length = 500)
	public java.lang.String getScbz() {
		return scbz;
	}

	public void setScbz(java.lang.String scbz) {
		this.scbz = scbz;
	}
	
	@Column(name = "YSLX", nullable = true, length = 32)
	public String getYslx() {
		return yslx;
	}

	public void setYslx(String yslx) {
		this.yslx = yslx;
	}

	@Column(name = "JJFJSFS", nullable = true, length = 32)
	public String getJjfjsfs() {
		return jjfjsfs;
	}

	public void setJjfjsfs(String jjfjsfs) {
		this.jjfjsfs = jjfjsfs;
	}

	@Column(name = "DXBL", nullable = true, length = 32)
	public String getDxbl() {
		return dxbl;
	}

	public void setDxbl(String dxbl) {
		this.dxbl = dxbl;
	}

	@Column(name = "ZRDWBL", nullable = true, length = 32)
	public String getZrdwbl() {
		return zrdwbl;
	}

	public void setZrdwbl(String zrdwbl) {
		this.zrdwbl = zrdwbl;
	}

	@Column(name = "CYDWBL", nullable = true, length = 32)
	public String getCydwbl() {
		return cydwbl;
	}

	public void setCydwbl(String cydwbl) {
		this.cydwbl = cydwbl;
	}

	@Column(name = "XMZBL", nullable = true, length = 32)
	public String getXmzbl() {
		return xmzbl;
	}

	public void setXmzbl(String xmzbl) {
		this.xmzbl = xmzbl;
	}

	@Override  
    public Object clone() {  
		TPmProjectEntity project = null;  
        try{  
        	project = (TPmProjectEntity)super.clone();  
        	if(this.devDepart != null) {
        		project.devDepart = (TSDepart)this.devDepart.clone();
        	}
        	if(this.dutyDepart != null) {
        		project.dutyDepart = (TSDepart)this.dutyDepart.clone();
        	}
        	if(this.glxm != null) {
        		project.glxm = (TPmProjectEntity)this.glxm.clone();
        	}
        	if(this.mergeProject != null) {
        		project.mergeProject = (TPmProjectEntity)this.mergeProject.clone();
        	}
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return project;  
    }  
}
