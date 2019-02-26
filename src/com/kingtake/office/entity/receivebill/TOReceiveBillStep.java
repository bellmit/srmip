package com.kingtake.office.entity.receivebill;

import java.util.List;

import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;

/**
 * @Title: Entity
 * @Description:
 * @author onlineGenerator
 * @date 2015-07-17 15:43:38
 * @version V1.0
 *
 */

@SuppressWarnings("serial")
public class TOReceiveBillStep implements java.io.Serializable {

    /** 操作人姓名 */
    private java.lang.String operateUsername;
    /** 操作时间 */
    private java.util.Date operateDate;
    /** 操作人部门名称 */
    private java.lang.String operateDepartname;
    /** 操作人ip地址 */
    private java.lang.String operateIp;
    /** 发送意见 */
    private java.lang.String senderTip;
    /**
     * 接收信息列表
     */
    private List<TOFlowReceiveLogsEntity> rlist;

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

    public List<TOFlowReceiveLogsEntity> getRlist() {
        return rlist;
    }

    public void setRlist(List<TOFlowReceiveLogsEntity> rlist) {
        this.rlist = rlist;
    }

}
