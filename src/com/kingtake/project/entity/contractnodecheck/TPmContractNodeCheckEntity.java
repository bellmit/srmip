package com.kingtake.project.entity.contractnodecheck;

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

import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;

/**
 * @Title: Entity
 * @Description: 合同节点考核
 * @author onlineGenerator
 * @date 2015-08-31 10:00:09
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_contract_node_check", schema = "")
@SuppressWarnings("serial")
@LogEntity("合同节点考核")
public class TPmContractNodeCheckEntity implements java.io.Serializable {
    /** 节点主键 */
    @FieldDesc("合同节点主键")
    private java.lang.String contractNodeId;
    /** 合同编号 */
    @Excel(name = "合同编号")
    @FieldDesc("合同编号")
    private java.lang.String contactNum;
    /** 会计编码 */
    @Excel(name = "会计编码")
    @FieldDesc("会计编码")
    private java.lang.String accountingCode;
    /** 项目名称 */
    @Excel(name = "项目名称")
    @FieldDesc("项目名称")
    private java.lang.String projectName;
    /** 密级 */
    @Excel(name = "密级")
    @FieldDesc("密级")
    private java.lang.String secretDegree;
    /** 合同id */
    @FieldDesc("合同id")
    private java.lang.String contactId;
    /** 合同名称 */
    @Excel(name = "合同名称")
    @FieldDesc("合同名称")
    private java.lang.String contractName;
    /** 责任单位id */
    @FieldDesc("责任单位id")
    private java.lang.String dutyDepartid;
    /** 责任单位名称 */
    @Excel(name = "责任单位名称")
    @FieldDesc("责任单位名称")
    private java.lang.String dutyDepartname;
    /** 合同乙方 */
    @Excel(name = "合同乙方")
    @FieldDesc("合同乙方")
    private java.lang.String contactSecondParty;
    /** 合同价款 */
    @Excel(name = "合同价款",isAmount = true)
    @FieldDesc("合同价款")
    private java.math.BigDecimal contactAmount;
    /** 签订时间 */
    @Excel(name = "签订时间")
    @FieldDesc("签订时间")
    private java.util.Date contractSigningTime;
    /** 节点价款 */
    @Excel(name = "节点价款",isAmount = true)
    @FieldDesc("节点价款")
    private java.math.BigDecimal nodeAmount;
    /** 节点时间 */
    @Excel(name = "节点时间")
    @FieldDesc("节点时间")
    private java.util.Date nodeTime;
    /** 主键 */
    private java.lang.String id;
    /** 验收时间 */
    @Excel(name = "验收时间")
    @FieldDesc("验收时间")
    private java.util.Date checkTime;
    /** 组织单位id */
    @FieldDesc("组织单位id")
    private java.lang.String organizationUnitid;
    /** 组织单位名称 */
    @Excel(name = "组织单位名称")
    @FieldDesc("组织单位名称")
    private java.lang.String organizationUnitname;
    /** 节点要求或内容 */
    @Excel(name = "节点要求或内容")
    @FieldDesc("节点要求或内容")
    private java.lang.String nodeContent;
    /** 节点进度或成果 */
    @Excel(name = "节点进度或成果")
    @FieldDesc("节点进度或成果")
    private java.lang.String nodeResult;
    /** 备注 */
    @Excel(name = "备注")
    @FieldDesc("备注")
    private java.lang.String memo;
    /** 合同类型 */
    @Excel(name = "合同类型")
    @FieldDesc("合同类型")
    private java.lang.String contractType;
	
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

    
    /** 处理状态 */
    @Excel(name = "处理状态")
    private java.lang.String operationStatus;
    /** 完成时间 */
    @Excel(name = "完成时间")
    private java.util.Date finishTime;
    /** 完成操作人id */
    private java.lang.String finishUserid;
    /** 完成操作人姓名 */
    @Excel(name = "完成操作人姓名")
    private java.lang.String finishUsername;
    
    @Excel(name="驳回记录")
    private TPmApprReceiveLogsEntity backLog;
    @FieldDesc("审批发送记录")
    private List<TPmApprSendLogsEntity> apprSendLogs;
    @FieldDesc("审批记录")
    private List<TPmApprReceiveLogsEntity> apprReceiveLogs;
    
    /**
     * 附件关联编码
     */
    private String attachmentCode;

    /**
     * 附件列表
     */
    private List<TSFilesEntity> attachments;

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 完成时间
     */
    @Column(name = "FINISH_TIME", nullable = true)
    public java.util.Date getFinishTime() {
        return this.finishTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 完成时间
     */
    public void setFinishTime(java.util.Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 完成操作人id
     */
    @Column(name = "FINISH_USERID", nullable = true, length = 32)
    public java.lang.String getFinishUserid() {
        return this.finishUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 完成操作人id
     */
    public void setFinishUserid(java.lang.String finishUserid) {
        this.finishUserid = finishUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 完成操作人姓名
     */
    @Column(name = "FINISH_USERNAME", nullable = true, length = 50)
    public java.lang.String getFinishUsername() {
        return this.finishUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 完成操作人姓名
     */
    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
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
     * @return: java.lang.String 节点主键
     */
    @Column(name = "CONTRACT_NODE_ID", nullable = true, length = 32)
    public java.lang.String getContractNodeId() {
        return this.contractNodeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 节点主键
     */
    public void setContractNodeId(java.lang.String contractNodeId) {
        this.contractNodeId = contractNodeId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同编号
     */
    @Column(name = "CONTRACT_NUM", nullable = true, length = 30)
    public java.lang.String getContactNum() {
        return this.contactNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同编号
     */
    public void setContactNum(java.lang.String contactNum) {
        this.contactNum = contactNum;
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
     * @return: java.lang.String 项目名称
     */
    @Column(name = "PROJECT_NAME", nullable = false, length = 100)
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
     * @return: java.lang.String 密级
     */
    @Column(name = "SECRET_DEGREE", nullable = true, length = 1)
    public java.lang.String getSecretDegree() {
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
     * @return: java.lang.String 合同id
     */
    @Column(name = "CONTRACT_ID", nullable = true, length = 32)
    public java.lang.String getContactId() {
        return this.contactId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同id
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
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
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 责任单位id
     */
    @Column(name = "DUTY_DEPARTID", nullable = true, length = 32)
    public java.lang.String getDutyDepartid() {
        return this.dutyDepartid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 责任单位id
     */
    public void setDutyDepartid(java.lang.String dutyDepartid) {
        this.dutyDepartid = dutyDepartid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 责任单位名称
     */
    @Column(name = "DUTY_DEPARTNAME", nullable = true, length = 60)
    public java.lang.String getDutyDepartname() {
        return this.dutyDepartname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 责任单位名称
     */
    public void setDutyDepartname(java.lang.String dutyDepartname) {
        this.dutyDepartname = dutyDepartname;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同乙方
     */
    @Column(name = "CONTRACT_SECOND_PARTY", nullable = true, length = 100)
    public java.lang.String getContactSecondParty() {
        return this.contactSecondParty;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同乙方
     */
    public void setContactSecondParty(java.lang.String contactSecondParty) {
        this.contactSecondParty = contactSecondParty;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 合同价款
     */
    @Column(name = "CONTRACT_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getContactAmount() {
        return this.contactAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 合同价款
     */
    public void setContactAmount(java.math.BigDecimal contactAmount) {
        this.contactAmount = contactAmount;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 签订时间
     */
    @Column(name = "CONTRACT_SIGNING_TIME", nullable = true)
    public java.util.Date getContractSigningTime() {
        return this.contractSigningTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 签订时间
     */
    public void setContractSigningTime(java.util.Date contractSigningTime) {
        this.contractSigningTime = contractSigningTime;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 节点价款
     */
    @Column(name = "NODE_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getNodeAmount() {
        return this.nodeAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 节点价款
     */
    public void setNodeAmount(java.math.BigDecimal nodeAmount) {
        this.nodeAmount = nodeAmount;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 节点时间
     */
    @Column(name = "NODE_TIME", nullable = true)
    public java.util.Date getNodeTime() {
        return this.nodeTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 节点时间
     */
    public void setNodeTime(java.util.Date nodeTime) {
        this.nodeTime = nodeTime;
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
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 验收时间
     */
    @Column(name = "CHECK_TIME", nullable = true)
    public java.util.Date getCheckTime() {
        return this.checkTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 验收时间
     */
    public void setCheckTime(java.util.Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 组织单位id
     */
    @Column(name = "ORGANIZATION_UNITID", nullable = true, length = 32)
    public java.lang.String getOrganizationUnitid() {
        return this.organizationUnitid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 组织单位id
     */
    public void setOrganizationUnitid(java.lang.String organizationUnitid) {
        this.organizationUnitid = organizationUnitid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 组织单位名称
     */
    @Column(name = "ORGANIZATION_UNITNAME", nullable = true, length = 60)
    public java.lang.String getOrganizationUnitname() {
        return this.organizationUnitname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 组织单位名称
     */
    public void setOrganizationUnitname(java.lang.String organizationUnitname) {
        this.organizationUnitname = organizationUnitname;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 节点要求或内容
     */
    @Column(name = "NODE_CONTENT", nullable = true, length = 2000)
    public java.lang.String getNodeContent() {
        return this.nodeContent;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 节点要求或内容
     */
    public void setNodeContent(java.lang.String nodeContent) {
        this.nodeContent = nodeContent;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 节点进度或成果
     */
    @Column(name = "NODE_RESULT", nullable = true, length = 2000)
    public java.lang.String getNodeResult() {
        return this.nodeResult;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 节点进度或成果
     */
    public void setNodeResult(java.lang.String nodeResult) {
        this.nodeResult = nodeResult;
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
     * @return: java.lang.String 合同类型
     */
    @Column(name = "CONTRACT_TYPE", nullable = true, length = 2)
    public java.lang.String getContractType() {
        return this.contractType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同类型
     */
    public void setContractType(java.lang.String contractType) {
        this.contractType = contractType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 处理状态
     */
    @Column(name = "OPERATION_STATUS", nullable = true, length = 1)
    public java.lang.String getOperationStatus() {
        return this.operationStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 处理状态
     */
    public void setOperationStatus(java.lang.String operationStatus) {
        this.operationStatus = operationStatus;
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
