package com.kingtake.project.entity.incomeapply;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 到账信息表
 * @author onlineGenerator
 * @date 2015-12-02 18:36:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "T_PM_INCOME_CONTRACT_NODE_RELA", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmIncomeContractNodeRelaEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /**
     * 来款申请id
     */
    private String incomeApplyId;

    /**
     * 合同id
     */
    private String contractId;
    /**
     * 合同节点id
     */
    private String contractNodeId;
    /**
     * 合同节点金额
     */
    private BigDecimal nodeAmount;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 备注
     */
    private String remark;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 32)
    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "INCOME_APPLY_ID")
    public String getIncomeApplyId() {
        return incomeApplyId;
    }

    public void setIncomeApplyId(String incomeApplyId) {
        this.incomeApplyId = incomeApplyId;
    }

    @Column(name = "CONTRACT_NODE_ID")
    public String getContractNodeId() {
        return contractNodeId;
    }

    public void setContractNodeId(String contractNodeId) {
        this.contractNodeId = contractNodeId;
    }

    @Column(name = "CNODE_AMOUNT")
    public BigDecimal getNodeAmount() {
        return nodeAmount;
    }

    public void setNodeAmount(BigDecimal nodeAmount) {
        this.nodeAmount = nodeAmount;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "NODE_NAME")
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Transient
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

}
