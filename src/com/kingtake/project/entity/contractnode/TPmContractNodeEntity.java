package com.kingtake.project.entity.contractnode;

import java.math.BigDecimal;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: T_PM_CONTRACT_NODE
 * @author onlineGenerator
 * @date 2015-08-01 16:16:43
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_contract_node", schema = "")
@SuppressWarnings("serial")
@LogEntity("合同节点信息")
public class TPmContractNodeEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 节点名称 */
    @Excel(name = "节点名称")
    @FieldDesc("节点名称")
    private java.lang.String contractNodeName;
    /** 进出帐标志 */
    @Excel(name = "进出帐标志")
    @FieldDesc("进出帐标志")
    private java.lang.String inOutFlag;
    /** 关联进出帐合同审批表主键 */
    @Excel(name = "关联进出帐合同审批表主键")
    @FieldDesc("关联进出帐合同审批表主键")
    private String inOutContractid;
    /** 关联支付节点id(关联到项目支付节点信息表id) */
    @Excel(name = "关联支付节点或进账节点")
    @FieldDesc("关联支付节点或进账节点")
    private java.lang.String projPayNodeId;
    /** 计划/合同标志 */
    @Excel(name = "计划/合同标志")
    @FieldDesc("计划/合同标志")
    private java.lang.String planContractFlag;
    /** 类型(0:标准项目；1：外协项目；2：校内协作) */
    @Excel(name = "类型")
    @FieldDesc("类型")
    private java.lang.String prjType;
    /** 完成时间 */
    @Excel(name = "完成时间")
    @FieldDesc("完成时间")
    private java.util.Date completeDate;
    /** 成果形式 */
    @Excel(name = "成果形式")
    @FieldDesc("成果形式")
    private java.lang.String resultForm;
    /** 评价方法 */
    @Excel(name = "评价方法")
    @FieldDesc("评价方法")
    private java.lang.String evaluationMethod;
    /** 备注 */
    @Excel(name = "备注")
    @FieldDesc("备注")
    private java.lang.String remarks;

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
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
    
    /**
     * 关联附件
     */
    private String attachmentCode;

    /**
     * 支付百分比
     */
    private Integer payPercent;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付条件
     */
    private String payCondition;
    
    /**
     * 累计比例
     */
    private Integer cumulativeProportion;

    @Column(name = "cumulative_proportion")
    public Integer getCumulativeProportion() {
		return cumulativeProportion;
	}

	public void setCumulativeProportion(Integer cumulativeProportion) {
		this.cumulativeProportion = cumulativeProportion;
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

    @Transient
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联支付节点id(关联到项目支付节点信息表id)
     */
    @Column(name = "PROJ_PAY_NODE_ID", nullable = true, length = 32)
    public java.lang.String getProjPayNodeId() {
        return this.projPayNodeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联支付节点id(关联到项目支付节点信息表id)
     */
    public void setProjPayNodeId(java.lang.String projPayNodeId) {
        this.projPayNodeId = projPayNodeId;
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
     * @return: java.lang.String 类型(0:标准项目；1：外协项目；2：校内协作)
     */
    @Column(name = "PRJ_TYPE", nullable = true, length = 2)
    public java.lang.String getPrjType() {
        return this.prjType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 类型(0:标准项目；1：外协项目；2：校内协作)
     */
    public void setPrjType(java.lang.String prjType) {
        this.prjType = prjType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 节点名称
     */
    @Column(name = "NODE_NAME", nullable = true, length = 50)
    public java.lang.String getContractNodeName() {
        return this.contractNodeName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 节点名称
     */
    public void setContractNodeName(java.lang.String contractNodeName) {
        this.contractNodeName = contractNodeName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果形式
     */
    @Column(name = "RESULT_FORM", nullable = true, length = 1000)
    public java.lang.String getResultForm() {
        return this.resultForm;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果形式
     */
    public void setResultForm(java.lang.String resultForm) {
        this.resultForm = resultForm;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 评价方法
     */
    @Column(name = "EVALUATION_METHOD", nullable = true, length = 1000)
    public java.lang.String getEvaluationMethod() {
        return this.evaluationMethod;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 评价方法
     */
    public void setEvaluationMethod(java.lang.String evaluationMethod) {
        this.evaluationMethod = evaluationMethod;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 完成时间
     */
    @Column(name = "COMPLETE_DATE", nullable = true)
    public java.util.Date getCompleteDate() {
        return this.completeDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 完成时间
     */
    public void setCompleteDate(java.util.Date completeDate) {
        this.completeDate = completeDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    @Column(name = "REMARKS", nullable = true, length = 500)
    public java.lang.String getRemarks() {
        return this.remarks;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setRemarks(java.lang.String remarks) {
        this.remarks = remarks;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 计划/合同标志
     */
    @Column(name = "PLAN_CONTRACT_FLAG", nullable = true, length = 1)
    public java.lang.String getPlanContractFlag() {
        return this.planContractFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 计划/合同标志
     */
    public void setPlanContractFlag(java.lang.String planContractFlag) {
        this.planContractFlag = planContractFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 进出帐标志
     */
    @Column(name = "IN_OUT_FLAG", nullable = true, length = 1)
    public java.lang.String getInOutFlag() {
        return this.inOutFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 进出帐标志
     */
    public void setInOutFlag(java.lang.String inOutFlag) {
        this.inOutFlag = inOutFlag;
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

    @Column(name = "IN_OUT_CONTRACTID", nullable = true, length = 32)
    public String getInOutContractid() {
        return inOutContractid;
    }

    public void setInOutContractid(String inOutContractid) {
        this.inOutContractid = inOutContractid;
    }

    @Column(name = "pay_percent")
    public Integer getPayPercent() {
        return payPercent;
    }

    public void setPayPercent(Integer payPercent) {
        this.payPercent = payPercent;
    }

    @Column(name = "pay_amount")
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    @Column(name = "pay_condition")
    public String getPayCondition() {
        return payCondition;
    }

    public void setPayCondition(String payCondition) {
        this.payCondition = payCondition;
    }

    @Column(name="attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }


    
}
