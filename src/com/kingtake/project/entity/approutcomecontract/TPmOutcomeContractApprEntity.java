package com.kingtake.project.entity.approutcomecontract;

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

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.constant.CodeTypeConstant;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 出账合同审批
 * @author onlineGenerator
 * @date 2015-08-10 18:47:48
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_outcome_contract_appr", schema = "")
@SuppressWarnings("serial")
@LogEntity("出账合同审批")
public class TPmOutcomeContractApprEntity implements java.io.Serializable {
	/** 主键 */
	@FieldDesc("主键")
	private java.lang.String id;
	/** 项目基_主键 */
	@FieldDesc("项目基_主键")
	private TPmProjectEntity project;
	/** 项目名称（科目代码） */
	@Excel(name = "项目名称")
	@FieldDesc("项目名称")
	private java.lang.String projectnameSubjectcode;
	/** 合同类别(3：生产订货类；4：研究(制)类；5：采购类) */
	@Excel(name = "合同类别",isExportConvert=true)
	@FieldDesc("合同类别")
	private String contractType;
	/** 申请单位 */
	@Excel(name = "申请单位")
	@FieldDesc("申请单位")
	private java.lang.String applyUnit;
	/** 合同名称 */
	@Excel(name = "合同名称")
	@FieldDesc("合同名称")
	private java.lang.String contractName;
	/**合同编号*/
	@Excel(name="合同编号")
	@FieldDesc("合同编号")
	private java.lang.String contractCode;
	/** 对方单位 */
	@Excel(name = "对方单位")
	@FieldDesc("对方单位")
	private java.lang.String approvalUnit;
	/** 合同第三方 */
	@Excel(name = "合同第三方")
	@FieldDesc("合同第三方")
	private java.lang.String theContractThird;
	/** 开始时间 */
	@Excel(name = "开始时间",format="yyyy-MM-dd")
	@FieldDesc("开始时间")
	private java.util.Date startTime;
	/** 截止时间 */
	@Excel(name = "截止时间",format="yyyy-MM-dd")
	@FieldDesc("截止时间")
	private java.util.Date endTime;
	/** 合同标的 */
	@Excel(name = "合同标的")
	@FieldDesc("合同标的")
	private java.lang.String contractObject;
	/** 业务主管部门 */
	@Excel(name = "业务主管部门")
	@FieldDesc("业务主管部门")
	private java.lang.String busiManageDepart;
	/** 总经费（元） */
	@Excel(name = "总经费（元）",isAmount = true)
	@FieldDesc("总经费（元）")
	private java.math.BigDecimal totalFunds;
	
	
	/** 采购方式（科研代码集中取CGFS） */
	@Excel(name = "采购方式",isExportConvert=true)
	@FieldDesc("采购方式")
	private java.lang.String acquisitionMethod;
	/** 确定该采购方式理由 */
	@Excel(name = "确定该采购方式理由")
	@FieldDesc("确定该采购方式理由")
	private java.lang.String acquisitionReason;
	/** 询价小组成员 */
	@Excel(name = "询价小组成员")
	@FieldDesc("询价小组成员")
	private java.lang.String inquiryMember;
	/** 技术规范或技术规格书（已评审/未评审/本合同不需要） */
    @Excel(name = "技术规范或技术规格书", isExportConvert = true)
	@FieldDesc("技术规范或技术规格书")
	private java.lang.String technicalManual;
    @Excel(name = "质量保证大纲", isExportConvert = true)
	@FieldDesc("质量保证大纲")
	private java.lang.String qualityCetifyFlag;
	/** 标准化大纲（已评审/未评审/本合同不需要） */
    @Excel(name = "标准化大纲", isExportConvert = true)
	@FieldDesc("标准化大纲")
	private java.lang.String standardOutline;
	/**合同签订时间*/
	@Excel(name="合同签订时间",format="yyyy-MM-dd")
	@FieldDesc("合同签订时间")
	private java.util.Date contractSigningTime;
	
    /**
     * 
     * 关联进账合同id
     */
    private String relatedIncomeId;
    /**
     * 关联进账合同名称
     */
    private String relatedIncomeName;

    /**
     * 关联进账类型
     */
    private String relateIncomeType;

    /**
     * 关联进账来源
     */
    private String relateIncomeSource;

    /**
     * 对方单位id
     */
    private String approvalUnitId;
    
    /**
     * 关联采购计划id
     */
    private String glcgjhId;

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
	
	/**合同状态*/
	@FieldDesc("合同状态")
	private java.lang.String finishFlag;
	/**完成时间*/
	@FieldDesc("完成时间")
	private java.util.Date finishTime;
	/**驳回人id*/
	@FieldDesc("完成人id")
	private java.lang.String finishUserid;
	/**驳回人姓名*/
	@FieldDesc("完成人姓名")
	private java.lang.String finishUsername;
	
	@FieldDesc("驳回记录")
	private TPmApprReceiveLogsEntity backLog;
	@FieldDesc("审批发送记录")
    private List<TPmApprSendLogsEntity> apprSendLogs;
	@FieldDesc("审批记录")
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;
	
	@FieldDesc("合同基本信息")
    private List<TPmContractBasicEntity> tpmContractBasics;
	@FieldDesc("合同节点")
    private List<TPmContractNodeEntity> tPmContractNodes;
	@FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
	
	private List<TSFilesEntity> contractBook;//合同正本
	
	/**
	 * 是否上传合同正本，0为未上传，1为已上传，2为已确认
	 */
	private String contractBookFlag;
	
	/**
	 * 关联附件
	 */
	private String attachmentCode;
	
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
	 * @return: java.lang.String 标准化大纲（已评审/未评审/本合同不需要）
	 */
	@Column(name = "STANDARD_OUTLINE", nullable = true, length = 2)
	public java.lang.String getStandardOutline() {
		return this.standardOutline;
	}

    public java.lang.String convertGetStandardOutline() {
        return ConvertDicUtil.getConvertDic("1", "HTPSZT", this.standardOutline);
    }

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 标准化大纲（已评审/未评审/本合同不需要）
	 */
	public void setStandardOutline(java.lang.String standardOutline) {
		this.standardOutline = standardOutline;
	}
	
	@Column(name ="CONTRACT_SIGNING_TIME",nullable=true)
	public java.util.Date getContractSigningTime() {
		return contractSigningTime;
	}

	public void setContractSigningTime(java.util.Date contractSigningTime) {
		this.contractSigningTime = contractSigningTime;
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
	 * @return: java.lang.String 申请单位
	 */
	@Column(name = "APPLY_UNIT", nullable = true, length = 60)
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
	 * @return: java.lang.String 项目名称（科目代码）
	 */
	@Column(name = "PROJECTNAME_SUBJECTCODE", nullable = true, length = 150)
	public java.lang.String getProjectnameSubjectcode() {
		return this.projectnameSubjectcode;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 项目名称（科目代码）
	 */
	public void setProjectnameSubjectcode(
			java.lang.String projectnameSubjectcode) {
		this.projectnameSubjectcode = projectnameSubjectcode;
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
	public void setContractName(java.lang.String contractName) {
		this.contractName = contractName;
	}
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同名称
	 */
	public void setContractCode(java.lang.String contractCode){
		this.contractCode = contractCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同名称
	 */
	@Column(name ="CONTRACT_CODE",nullable=true,length=100)
	public java.lang.String getContractCode(){
		return this.contractCode;
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
	 * @return: java.math.BigDecimal 总经费（元）
	 */
	@Column(name = "TOTAL_FUNDS", nullable = true, scale = 2, length = 10)
	public java.math.BigDecimal getTotalFunds() {
		return this.totalFunds;
	}

	/**
	 * 方法: 设置java.math.BigDecimal
	 *
	 * @param: java.math.BigDecimal 总经费（元）
	 */
	public void setTotalFunds(java.math.BigDecimal totalFunds) {
		this.totalFunds = totalFunds;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 采购方式（科研代码集中取CGFS）
	 */
	@Column(name = "ACQUISITION_METHOD", nullable = true, length = 2)
	public java.lang.String getAcquisitionMethod() {
		return this.acquisitionMethod;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 采购方式（科研代码集中取CGFS）
	 */
	public void setAcquisitionMethod(java.lang.String acquisitionMethod) {
		this.acquisitionMethod = acquisitionMethod;
	}
	public java.lang.String convertGetAcquisitionMethod() {
    return ConvertDicUtil.getConvertDic(CodeTypeConstant.CODE_TYPE_KY, CodeTypeConstant.CGFS, this.acquisitionMethod);
  }
	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 确定该采购方式理由
	 */
	@Column(name = "ACQUISITION_REASON", nullable = true, length = 300)
	public java.lang.String getAcquisitionReason() {
		return this.acquisitionReason;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 确定该采购方式理由
	 */
	public void setAcquisitionReason(java.lang.String acquisitionReason) {
		this.acquisitionReason = acquisitionReason;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 询价小组成员
	 */
	@Column(name = "INQUIRY_MEMBER", nullable = true, length = 200)
	public java.lang.String getInquiryMember() {
		return this.inquiryMember;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 询价小组成员
	 */
	public void setInquiryMember(java.lang.String inquiryMember) {
		this.inquiryMember = inquiryMember;
	}

	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 技术规范或技术规格书（已评审/未评审/本合同不需要）
	 */
	@Column(name = "TECHNICAL_MANUAL", nullable = true, length = 2)
	public java.lang.String getTechnicalManual() {
		return this.technicalManual;
	}

    public java.lang.String convertGetTechnicalManual() {
        return ConvertDicUtil.getConvertDic("1", "HTPSZT", technicalManual);
    }

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 技术规范或技术规格书（已评审/未评审/本合同不需要）
	 */
	public void setTechnicalManual(java.lang.String technicalManual) {
		this.technicalManual = technicalManual;
	}


	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 业务主管部门
	 */
	@Column(name = "BUSI_MANAGE_DEPART", nullable = true, length = 60)
	public java.lang.String getBusiManageDepart() {
		return this.busiManageDepart;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 业务主管部门
	 */
	public void setBusiManageDepart(java.lang.String busiManageDepart) {
		this.busiManageDepart = busiManageDepart;
	}


	/**
	 * 方法: 取得java.lang.String
	 *
	 * @return: java.lang.String 合同标的
	 */
	@Column(name = "CONTRACT_OBJECT", nullable = true, length = 60)
	public java.lang.String getContractObject() {
		return this.contractObject;
	}

	/**
	 * 方法: 设置java.lang.String
	 *
	 * @param: java.lang.String 合同标的
	 */
	public void setContractObject(java.lang.String contractObject) {
		this.contractObject = contractObject;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  质量保证大纲（已评审/未评审/本合同不需要）
	 */
	@Column(name ="QUALITY_CETIFY_FLAG",nullable=true,length=2)
	public java.lang.String getQualityCetifyFlag(){
		return this.qualityCetifyFlag;
	}

    public java.lang.String convertGetQualityCetifyFlag() {
        return ConvertDicUtil.getConvertDic("1", "HTPSZT", qualityCetifyFlag);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  质量保证大纲（已评审/未评审/本合同不需要）
	 */
	public void setQualityCetifyFlag(java.lang.String qualityCetifyFlag){
		this.qualityCetifyFlag = qualityCetifyFlag;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_P_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @Column(name ="FINISH_FLAG",nullable=true)
	public java.lang.String getFinishFlag() {
		return finishFlag;
	}

	public void setFinishFlag(java.lang.String finishFlag) {
		this.finishFlag = finishFlag;
	}

	@Column(name ="FINISH_TIME",nullable=true)
	public java.util.Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(java.util.Date finishTime) {
		this.finishTime = finishTime;
	}
	
	@Column(name ="FINISH_USERID",nullable=true,length=32)
	public java.lang.String getFinishUserid() {
		return finishUserid;
	}

	public void setFinishUserid(java.lang.String finishUserid) {
		this.finishUserid = finishUserid;
	}

	@Column(name ="FINISH_USERNAME",nullable=true,length=100)
	public java.lang.String getFinishUsername() {
		return finishUsername;
	}

	public void setFinishUsername(java.lang.String finishUsername) {
		this.finishUsername = finishUsername;
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

	@Column(name = "CONTRACT_TYPE", nullable = true, length = 2)
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String convertGetContractType() {
    return ConvertDicUtil.getConvertDic(CodeTypeConstant.CODE_TYPE_KY, CodeTypeConstant.HTLB, this.contractType);
  }
    @Column(name = "RELATED_INCOME_ID")
    public String getRelatedIncomeId() {
        return relatedIncomeId;
    }

    public void setRelatedIncomeId(String relatedIncomeId) {
        this.relatedIncomeId = relatedIncomeId;
    }

    @Column(name = "RELATED_INCOME_NAME")
    public String getRelatedIncomeName() {
        return relatedIncomeName;
    }

    public void setRelatedIncomeName(String relatedIncomeName) {
        this.relatedIncomeName = relatedIncomeName;
    }

    @Column(name = "APPROVAL_UNIT_ID")
    public String getApprovalUnitId() {
        return approvalUnitId;
    }

    public void setApprovalUnitId(String approvalUnitId) {
        this.approvalUnitId = approvalUnitId;
    }

    @Column(name = "relate_income_type")
    public String getRelateIncomeType() {
        return relateIncomeType;
    }

    public void setRelateIncomeType(String relateIncomeType) {
        this.relateIncomeType = relateIncomeType;
    }

    @Column(name = "relate_income_source")
    public String getRelateIncomeSource() {
        return relateIncomeSource;
    }

    public void setRelateIncomeSource(String relateIncomeSource) {
        this.relateIncomeSource = relateIncomeSource;
    }

    @Column(name="glcgjhid")
    public String getGlcgjhId() {
        return glcgjhId;
    }

    public void setGlcgjhId(String glcgjhId) {
        this.glcgjhId = glcgjhId;
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

    @Column(name="contract_book_flag")
    public String getContractBookFlag() {
        return contractBookFlag;
    }

    public void setContractBookFlag(String contractBookFlag) {
        this.contractBookFlag = contractBookFlag;
    }

    
}
