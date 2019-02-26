package com.kingtake.project.entity.apprincomecontract;

import java.math.BigDecimal;
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

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.project.entity.apprcontractcheck.TPmContractCheckEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 进账合同审批
 * @author onlineGenerator
 * @date 2015-07-26 15:07:05
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_income_contract_appr", schema = "")
@SuppressWarnings("serial")
@LogEntity("进账合同审批")
public class TPmIncomeContractApprEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 申请单位 */
    @Excel(name = "申请单位")
    @FieldDesc("申请单位")
    private java.lang.String applyUnit;
    /** 项目实体 */
    @FieldDesc("关联项目")
    private TPmProjectEntity project;
    /** 项目名称 */
    @Excel(name = "项目名称")
    @FieldDesc("项目名称")
    private java.lang.String projectName;
    private String lxyj;
    /** 合同编号 */
    @Excel(name = "合同编号")
    @FieldDesc("合同编号")
    private java.lang.String contractCode;
    /** 合同名称 */
    @Excel(name = "合同名称")
    @FieldDesc("合同名称")
    private java.lang.String contractName;
    /** 对方单位 */
    @Excel(name = "对方单位")
    @FieldDesc("对方单位")
    private java.lang.String approvalUnit;
    /** 合同第三方 */
    @Excel(name = "合同第三方")
    @FieldDesc("合同第三方")
    private java.lang.String theContractThird;
    /** 开始时间 */
    @Excel(name = "开始时间")
    @FieldDesc("开始时间")
    private java.util.Date startTime;
    /** 截止时间 */
    @Excel(name = "截止时间")
    @FieldDesc("截止时间")
    private java.util.Date endTime;
    /** 总经费 */
    @Excel(name = "总经费",isAmount = true)
    @FieldDesc("总经费")
    private java.math.BigDecimal totalFunds;
    /** 合同类别（从科研代码集中取） */
    @Excel(name = "合同类别（从科研代码集中取）")
    @FieldDesc("合同类别")
    private java.lang.String contractType;
    /** 合同类别为其他时：填写具体的合同内容 */
    @Excel(name = "其他合同类别")
    @FieldDesc("其他合同类别")
    private java.lang.String contractTypeContent;
    /** 协作单位及经费情况 */
    @Excel(name = "协作单位及经费情况")
    @FieldDesc("协作单位及经费情况")
    private java.lang.String coorUnitFundsInfo;
    /** 技术规范或技术规格书（已评审/未评审/本合同不需要） */
    @Excel(name = "技术规范或技术规格书")
    @FieldDesc("技术规范或技术规格书")
    private java.lang.String technologyFlag;
    /** 质量保证大纲（已评审/未评审/本合同不需要） */
    @Excel(name = "质量保证大纲")
    @FieldDesc("质量保证大纲")
    private java.lang.String qualityCetifyFlag;
    /** 标准化大纲 */
    @Excel(name = "标准化大纲")
    @FieldDesc("标准化大纲")
    private java.lang.String standardOutline;
    /** 合同要求-技术要求 */
    @Excel(name = "合同要求-技术要求")
    @FieldDesc("合同要求-技术要求")
    private java.lang.String technologyRequire;
    
    /**
     * 合同要求-技术要求-负责评审的单位或人员
     */
    private String personTechRequire;
    
    /** 合同要求-进度要求 */
    @Excel(name = "合同要求-进度要求")
    @FieldDesc("合同要求-进度要求")
    private java.lang.String scheduleRequire;
    /**
     * 合同要求-进度要求-负责评审的单位或人员
     */
    private String personScheduleRequire;
    
    /** 合同要求-质量保证要求 */
    @Excel(name = "合同要求-质量保证要求")
    @FieldDesc("合同要求-质量保证要求")
    private java.lang.String qualityRequire;
    
    /**
     * 合同要求-质量保证要求-负责评审的单位或人员
     */
    private String personQualityRequire;
    
    /** 合同要求-经费 */
    @Excel(name = "合同要求-经费",isAmount = true)
    @FieldDesc("合同要求-经费")
    private java.math.BigDecimal fundsRequire;
    
    /**
     * 合同要求-经费要求-负责评审的单位或人员
     */
    private String personFundsRequire;
    
    /** 合同要求-特殊要求 */
    @Excel(name = "合同要求-特殊要求")
    @FieldDesc("合同要求-特殊要求")
    private java.lang.String specialRequire;
    
    /**
     * 合同要求-特殊要求-负责评审的单位或人员
     */
    private String personSpecialRequire;
    
    /** 合同要求-其他要求 */
    @Excel(name = "合同要求-其他要求")
    @FieldDesc("合同要求-其他要求")
    private java.lang.String otherRequire;
    
    /**
     * 合同要求-其他要求-负责评审的单位或人员
     */
    private String personOtherRequire;
    
    /** 法律法规要求 */
    @Excel(name = "法律法规要求")
    @FieldDesc("法律法规要求")
    private java.lang.String regulationRequire;
    
    /**
     * 合同要求-法律法规-负责评审的单位或人员
     */
    private String personLawRequire;
    
    /** 研制责任单位确定的附加要求 */
    @Excel(name = "研制责任单位确定的附加要求")
    @FieldDesc("研制责任单位确定的附加要求")
    private java.lang.String additionRequire;
    
    /**
     * 合同要求-研制责任单位附加要求-负责评审的单位或人员
     */
    private String personAdditionRequire;
    
    /** 规定用途或已知的预期用途所必须的要求 */
    @Excel(name = "规定用途或已知的预期用途所必须的要求")
    @FieldDesc("规定用途或已知的预期用途所必须的要求")
    private java.lang.String useRequire;
    
    /**
     * 合同要求-规定用途或已知预期用途-负责评审的单位或人员
     */
    private String personUseRequire;
    
    /** 合同签订时间 */
    @Excel(name = "合同签订时间")
    @FieldDesc("合同签订时间")
    private java.util.Date contractSigningTime;

    /** 创建人 */
    @Excel(name = "创建人")
    @FieldDesc("创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    @FieldDesc("创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间")
    @FieldDesc("创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @Excel(name = "修改人")
    @FieldDesc("修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    @FieldDesc("修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @Excel(name = "修改时间")
    @FieldDesc("修改时间")
    private java.util.Date updateDate;

    /** 合同状态 */
    @Excel(name = "合同状态")
    @FieldDesc("合同状态")
    private java.lang.String finishFlag;
    /** 完成时间 */
    @Excel(name = "完成时间")
    @FieldDesc("完成时间")
    private java.util.Date finishTime;
    /** 驳回人id */
    @Excel(name = "完成人id")
    @FieldDesc("完成人id")
    private java.lang.String finishUserid;
    /** 驳回人姓名 */
    @Excel(name = "完成人姓名")
    @FieldDesc("完成人姓名")
    private java.lang.String finishUsername;
    /** 研制内容 */
    @Excel(name = "研制内容")
    @FieldDesc("研制内容")
    private java.lang.String developContent;
    /** 变更说明 */
    @Excel(name = "变更说明")
    @FieldDesc("变更说明")
    private java.lang.String changeExplain;

    @Excel(name = "驳回记录")
    private TPmApprReceiveLogsEntity backLog;
    @FieldDesc("审批发送记录")
    private List<TPmApprSendLogsEntity> apprSendLogs;
    @FieldDesc("审批记录")
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;

    @FieldDesc("合同基本信息")
    private List<TPmContractBasicEntity> tpmContractBasics;
    @FieldDesc("合同节点")
    private List<TPmContractNodeEntity> tPmContractNodes;
    @FieldDesc("合同验收报告")
    //实际上只有一个
    private List<TPmContractCheckEntity> contractCheck;

    /**
     * 其它附件关联id
     */
    private String attachmentCode; 
    
    /**
     * 是否上传合同正本，0为否,1为是,2为已确认
     */
    private String contractBookFlag;
    
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
    
    private List<TSFilesEntity> contractBook;//合同正本
    

    /**
     * 确认状态 ,0 未发送 ，1 已发送，2 已确认
     */
    private String confirmStatus;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 确认人id
     */
    private String confirmUserId;

    /**
     * 确认人部门id
     */
    private String confirmDeptId;

    /**
     * 确认人姓名
     */
    private String confirmUserRealName;
    
	/**
	 * 合同依据
	 */
	private String contractBasis;
	
	@Column(name = "CONTRACT_BASIS")
	public String getContractBasis() {
		return contractBasis;
	}

	public void setContractBasis(String contractBasis) {
		this.contractBasis = contractBasis;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_P_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 申请单位
     */
    @Column(name = "APPLY_UNIT", nullable = false, length = 60)
    public java.lang.String getApplyUnit() {
        return this.applyUnit;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 申请单位
     */
    public void setApplyUnit(java.lang.String applyUnit) {
        this.applyUnit = applyUnit;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目名称
     */
    @Column(name = "PROJECT_NAME", nullable = true, length = 60)
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
     * @return: java.lang.String 合同名称
     */
    @Column(name = "CONTRACT_NAME", nullable = true, length = 100)
    public java.lang.String getContractName() {
        return this.contractName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同名称
     */
    public void setContractCode(java.lang.String contractCode) {
        this.contractCode = contractCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同名称
     */
    @Column(name = "CONTRACT_CODE", nullable = true, length = 100)
    public java.lang.String getContractCode() {
        return this.contractCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同名称
     */
    public void setContractName(java.lang.String contractName) {
        this.contractName = contractName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 对方单位
     */
    @Column(name = "APPROVAL_UNIT", nullable = true, length = 60)
    public java.lang.String getApprovalUnit() {
        return this.approvalUnit;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 对方单位
     */
    public void setApprovalUnit(java.lang.String approvalUnit) {
        this.approvalUnit = approvalUnit;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同第三方
     */
    @Column(name = "THE_THIRD", nullable = true, length = 60)
    public java.lang.String getTheContractThird() {
        return this.theContractThird;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同第三方
     */
    public void setTheContractThird(java.lang.String theContractThird) {
        this.theContractThird = theContractThird;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 开始时间
     */
    @Column(name = "START_TIME", nullable = true)
    public java.util.Date getStartTime() {
        return this.startTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 开始时间
     */
    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 截止时间
     */
    @Column(name = "END_TIME", nullable = true)
    public java.util.Date getEndTime() {
        return this.endTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 截止时间
     */
    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 总经费
     */
    @Column(name = "TOTAL_FUNDS", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getTotalFunds() {
        return this.totalFunds;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 总经费
     */
    public void setTotalFunds(java.math.BigDecimal totalFunds) {
        this.totalFunds = totalFunds;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同类别（从科研代码集中取）
     */
    @Column(name = "CONTRACT_TYPE", nullable = true, length = 2)
    public java.lang.String getContractType() {
        return this.contractType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同类别（从科研代码集中取）
     */
    public void setContractTypeContent(java.lang.String contractTypeContent) {
        this.contractTypeContent = contractTypeContent;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同类别（从科研代码集中取）
     */
    @Column(name = "CONTRACT_TYPE_CONTENT", nullable = true, length = 2)
    public java.lang.String getContractTypeContent() {
        return this.contractTypeContent;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同类别（从科研代码集中取）
     */
    public void setContractType(java.lang.String contractType) {
        this.contractType = contractType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 协作单位及经费情况
     */
    @Column(name = "COOR_UNIT_FUNDS_INFO", nullable = true, length = 2000)
    public java.lang.String getCoorUnitFundsInfo() {
        return this.coorUnitFundsInfo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 协作单位及经费情况
     */
    public void setCoorUnitFundsInfo(java.lang.String coorUnitFundsInfo) {
        this.coorUnitFundsInfo = coorUnitFundsInfo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 技术规范或技术规格书（已评审/未评审/本合同不需要）
     */
    @Column(name = "TECHNOLOGY_FLAG", nullable = true, length = 2)
    public java.lang.String getTechnologyFlag() {
        return this.technologyFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 技术规范或技术规格书（已评审/未评审/本合同不需要）
     */
    public void setTechnologyFlag(java.lang.String technologyFlag) {
        this.technologyFlag = technologyFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 质量保证大纲（已评审/未评审/本合同不需要）
     */
    @Column(name = "QUALITY_CETIFY_FLAG", nullable = true, length = 2)
    public java.lang.String getQualityCetifyFlag() {
        return this.qualityCetifyFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 质量保证大纲（已评审/未评审/本合同不需要）
     */
    public void setQualityCetifyFlag(java.lang.String qualityCetifyFlag) {
        this.qualityCetifyFlag = qualityCetifyFlag;
    }

    @Column(name = "STANDARD_OUTLINE", nullable = true, length = 2)
    public java.lang.String getStandardOutline() {
        return standardOutline;
    }

    public void setStandardOutline(java.lang.String standardOutline) {
        this.standardOutline = standardOutline;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同要求-技术要求
     */
    @Column(name = "TECHNOLOGY_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getTechnologyRequire() {
        return this.technologyRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同要求-技术要求
     */
    public void setTechnologyRequire(java.lang.String technologyRequire) {
        this.technologyRequire = technologyRequire;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同要求-进度要求
     */
    @Column(name = "SCHEDULE_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getScheduleRequire() {
        return this.scheduleRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同要求-进度要求
     */
    public void setScheduleRequire(java.lang.String scheduleRequire) {
        this.scheduleRequire = scheduleRequire;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同要求-质量保证要求
     */
    @Column(name = "QUALITY_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getQualityRequire() {
        return this.qualityRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同要求-质量保证要求
     */
    public void setQualityRequire(java.lang.String qualityRequire) {
        this.qualityRequire = qualityRequire;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 合同要求-经费
     */
    @Column(name = "FUNDS_REQUIRE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getFundsRequire() {
        return this.fundsRequire;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 合同要求-经费
     */
    public void setFundsRequire(java.math.BigDecimal fundsRequire) {
        this.fundsRequire = fundsRequire;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同要求-特殊要求
     */
    @Column(name = "SPECIAL_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getSpecialRequire() {
        return this.specialRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同要求-特殊要求
     */
    public void setSpecialRequire(java.lang.String specialRequire) {
        this.specialRequire = specialRequire;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同要求-其他要求
     */
    @Column(name = "OTHER_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getOtherRequire() {
        return this.otherRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同要求-其他要求
     */
    public void setOtherRequire(java.lang.String otherRequire) {
        this.otherRequire = otherRequire;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 法律法规要求
     */
    @Column(name = "REGULATION_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getRegulationRequire() {
        return this.regulationRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 法律法规要求
     */
    public void setRegulationRequire(java.lang.String regulationRequire) {
        this.regulationRequire = regulationRequire;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 研制责任单位确定的附加要求
     */
    @Column(name = "ADDITION_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getAdditionRequire() {
        return this.additionRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 研制责任单位确定的附加要求
     */
    public void setAdditionRequire(java.lang.String additionRequire) {
        this.additionRequire = additionRequire;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 规定用途或已知的预期用途所必须的要求
     */
    @Column(name = "USE_REQUIRE", nullable = true, length = 2000)
    public java.lang.String getUseRequire() {
        return this.useRequire;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 规定用途或已知的预期用途所必须的要求
     */
    public void setUseRequire(java.lang.String useRequire) {
        this.useRequire = useRequire;
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

    @Column(name = "CONTRACT_SIGNING_TIME", nullable = true)
    public java.util.Date getContractSigningTime() {
        return contractSigningTime;
    }

    public void setContractSigningTime(java.util.Date contractSigningTime) {
        this.contractSigningTime = contractSigningTime;
    }

    @Column(name = "FINISH_FLAG", nullable = true)
    public java.lang.String getFinishFlag() {
        return finishFlag;
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

    @Column(name = "FINISH_USERID", nullable = true, length = 32)
    public java.lang.String getFinishUserid() {
        return finishUserid;
    }

    public void setFinishUserid(java.lang.String finishUserid) {
        this.finishUserid = finishUserid;
    }

    @Column(name = "FINISH_USERNAME", nullable = true, length = 100)
    public java.lang.String getFinishUsername() {
        return finishUsername;
    }

    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
    }

    @Column(name = "DEVELOP_CONTENT", nullable = true, length = 100)
    public java.lang.String getDevelopContent() {
        return developContent;
    }

    public void setDevelopContent(java.lang.String developContent) {
        this.developContent = developContent;
    }

    @Column(name = "CHANGE_EXPLAIN", nullable = true, length = 500)
    public java.lang.String getChangeExplain() {
        return changeExplain;
    }

    public void setChangeExplain(java.lang.String changeExplain) {
        this.changeExplain = changeExplain;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "IN_OUT_CONTRACTID")
    public List<TPmContractNodeEntity> getTPmContractNodes() {
        return tPmContractNodes;
    }

    public void setTPmContractNodes(List<TPmContractNodeEntity> tPmContractNodes) {
        this.tPmContractNodes = tPmContractNodes;
    }

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "IN_OUT_CONTRACTID")
    public List<TPmContractBasicEntity> getTpmContractBasics() {
        return tpmContractBasics;
    }

    public void setTpmContractBasics(List<TPmContractBasicEntity> tpmContractBasics) {
        this.tpmContractBasics = tpmContractBasics;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "CONTRACT_ID")
    public List<TPmContractCheckEntity> getContractCheck() {
        return contractCheck;
    }

    public void setContractCheck(List<TPmContractCheckEntity> contractCheck) {
        this.contractCheck = contractCheck;
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

    @Column(name = "confirm_status")
    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    @Column(name = "CONFIRM_TIME")
    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    @Column(name = "CONFIRM_USER_ID")
    public String getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(String confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    @Column(name = "CONFIRM_DEPT_ID")
    public String getConfirmDeptId() {
        return confirmDeptId;
    }

    public void setConfirmDeptId(String confirmDeptId) {
        this.confirmDeptId = confirmDeptId;
    }

    @Column(name = "CONFIRM_USER_REALNAME")
    public String getConfirmUserRealName() {
        return confirmUserRealName;
    }

    public void setConfirmUserRealName(String confirmUserRealName) {
        this.confirmUserRealName = confirmUserRealName;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Transient
    public List<TSFilesEntity> getContractBook() {
        return contractBook;
    }

    public void setContractBook(List<TSFilesEntity> contractBook) {
        this.contractBook = contractBook;
    }

    @Column(name="person_tech_require")
    public String getPersonTechRequire() {
        return personTechRequire;
    }

    public void setPersonTechRequire(String personTechRequire) {
        this.personTechRequire = personTechRequire;
    }

    @Column(name="person_schedule_require")
    public String getPersonScheduleRequire() {
        return personScheduleRequire;
    }

    public void setPersonScheduleRequire(String personScheduleRequire) {
        this.personScheduleRequire = personScheduleRequire;
    }

    @Column(name="person_quality_require")
    public String getPersonQualityRequire() {
        return personQualityRequire;
    }

    public void setPersonQualityRequire(String personQualityRequire) {
        this.personQualityRequire = personQualityRequire;
    }

    @Column(name="person_funds_require")
    public String getPersonFundsRequire() {
        return personFundsRequire;
    }

    public void setPersonFundsRequire(String personFundsRequire) {
        this.personFundsRequire = personFundsRequire;
    }

    @Column(name="person_special_require")
    public String getPersonSpecialRequire() {
        return personSpecialRequire;
    }

    public void setPersonSpecialRequire(String personSpecialRequire) {
        this.personSpecialRequire = personSpecialRequire;
    }

    @Column(name="person_other_require")
    public String getPersonOtherRequire() {
        return personOtherRequire;
    }

    public void setPersonOtherRequire(String personOtherRequire) {
        this.personOtherRequire = personOtherRequire;
    }

    @Column(name="person_law_require")
    public String getPersonLawRequire() {
        return personLawRequire;
    }

    public void setPersonLawRequire(String personLawRequire) {
        this.personLawRequire = personLawRequire;
    }

    @Column(name="person_addition_require")
    public String getPersonAdditionRequire() {
        return personAdditionRequire;
    }

    public void setPersonAdditionRequire(String personAdditionRequire) {
        this.personAdditionRequire = personAdditionRequire;
    }

    @Column(name="person_use_require")
    public String getPersonUseRequire() {
        return personUseRequire;
    }

    public void setPersonUseRequire(String personUseRequire) {
        this.personUseRequire = personUseRequire;
    }

    @Column(name="contract_book_flag")
    public String getContractBookFlag() {
        return contractBookFlag;
    }

    public void setContractBookFlag(String contractBookFlag) {
        this.contractBookFlag = contractBookFlag;
    }
    
    @Transient
	public String getLxyj() {
		String lxyj = "";
		if (this.project != null) {
			lxyj = project.getLxyj();
		}
		return lxyj;
	}

	public void setLxyj(String lxyj) {
		this.lxyj = lxyj;
	}
}
