package com.kingtake.project.entity.contractnoderelated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: T_PM_CONTRACT_NODE_RELATED
 * @author onlineGenerator
 * @date 2015-12-01 14:30:53
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_contract_node_related", schema = "")
@SuppressWarnings("serial")
public class TPmContractNodeRelatedEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 合同节点表主键 */
    @Excel(name = "合同节点表主键")
    private java.lang.String contractNodeId;
    /** 指定金额 */
    @Excel(name = "指定金额",isAmount = true)
    private java.math.BigDecimal amount;
    /** 关联进出账节点id */
    @Excel(name = "关联进出账节点id")
    private java.lang.String incomePayNodeId;

    /** 审核标志 */
    @FieldDesc("审核标志")
    @Excel(name = "审核标志", isExportConvert = true)
    private java.lang.String auditFlag;

    /** 审核人id */
    @FieldDesc("审核人id")
    private java.lang.String auditUserid;
    /** 审核人姓名 */
    @FieldDesc("审核人姓名")
    @Excel(name = "审核人")
    private java.lang.String auditUsername;
    /** 审核时间 */
    @FieldDesc("审核时间")
    @Excel(name = "审核时间", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    private java.util.Date auditTime;

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
     * @return: java.lang.String 合同节点表主键
     */
    @Column(name = "CONTRACT_NODE_ID", nullable = true, length = 32)
    public java.lang.String getContractNodeId() {
        return this.contractNodeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同节点表主键
     */
    public void setContractNodeId(java.lang.String contractNodeId) {
        this.contractNodeId = contractNodeId;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 指定金额
     */
    @Column(name = "AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 指定金额
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联进出账节点id
     */
    @Column(name = "INCOME_PAY_NODE_ID", nullable = true, length = 32)
    public java.lang.String getIncomePayNodeId() {
        return this.incomePayNodeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联进出账节点id
     */
    public void setIncomePayNodeId(java.lang.String incomePayNodeId) {
        this.incomePayNodeId = incomePayNodeId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审核标志
     */
    @Column(name = "AUDIT_FLAG", nullable = true, length = 1)
    public java.lang.String getAuditFlag() {
        return this.auditFlag;
    }

    public java.lang.String convertGetAuditFlag() {
        return ConvertDicUtil.getConvertDic("0", "SFBZ", this.auditFlag);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审核标志
     */
    public void setAuditFlag(java.lang.String auditFlag) {
        this.auditFlag = auditFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审核人id
     */
    @Column(name = "AUDIT_USERID", nullable = true, length = 32)
    public java.lang.String getAuditUserid() {
        return this.auditUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审核人id
     */
    public void setAuditUserid(java.lang.String auditUserid) {
        this.auditUserid = auditUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审核人姓名
     */
    @Column(name = "AUDIT_USERNAME", nullable = true, length = 50)
    public java.lang.String getAuditUsername() {
        return this.auditUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审核人姓名
     */
    public void setAuditUsername(java.lang.String auditUsername) {
        this.auditUsername = auditUsername;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审核时间
     */
    @Column(name = "AUDIT_TIME", nullable = true, length = 50)
    public java.util.Date getAuditTime() {
        return this.auditTime;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审核时间
     */
    public void setAuditTime(java.util.Date auditTime) {
        this.auditTime = auditTime;
    }

}
