package com.kingtake.project.entity.contractnodecheck;

import java.util.List;

/**
 * @Title: Entity
 * @Description: 合同节点考核流转记录表
 * @author onlineGenerator
 * @date 2015-08-28 15:01:06
 * @version V1.0
 *
 */
@SuppressWarnings("serial")
public class NodeCheckStep implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 关联合同节点id */
    private java.lang.String contractNodeId;
    /** 操作人id */
    private java.lang.String operateUserid;
    /** 操作人姓名 */
    private java.lang.String operateUsername;
    /** 操作时间 */
    private java.util.Date operateDate;
    /** 操作人部门id */
    private java.lang.String operateDepartid;
    /** 操作人部门名称 */
    private java.lang.String operateDepartname;
    /** 操作人ip地址 */
    private java.lang.String operateIp;
    /** 发送意见 */
    private java.lang.String senderTip;

    /**
     * 接收信息列表
     */
    private List<TPmContractNchkReciLogsEntity> rlist;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getContractNodeId() {
        return contractNodeId;
    }

    public void setContractNodeId(java.lang.String contractNodeId) {
        this.contractNodeId = contractNodeId;
    }

    public java.lang.String getOperateUserid() {
        return operateUserid;
    }

    public void setOperateUserid(java.lang.String operateUserid) {
        this.operateUserid = operateUserid;
    }

    public java.lang.String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(java.lang.String operateUsername) {
        this.operateUsername = operateUsername;
    }

    public java.util.Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(java.util.Date operateDate) {
        this.operateDate = operateDate;
    }

    public java.lang.String getOperateDepartid() {
        return operateDepartid;
    }

    public void setOperateDepartid(java.lang.String operateDepartid) {
        this.operateDepartid = operateDepartid;
    }

    public java.lang.String getOperateDepartname() {
        return operateDepartname;
    }

    public void setOperateDepartname(java.lang.String operateDepartname) {
        this.operateDepartname = operateDepartname;
    }

    public java.lang.String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(java.lang.String operateIp) {
        this.operateIp = operateIp;
    }

    public java.lang.String getSenderTip() {
        return senderTip;
    }

    public void setSenderTip(java.lang.String senderTip) {
        this.senderTip = senderTip;
    }

    public List<TPmContractNchkReciLogsEntity> getRlist() {
        return rlist;
    }

    public void setRlist(List<TPmContractNchkReciLogsEntity> rlist) {
        this.rlist = rlist;
    }

}
