package com.kingtake.base.entity.deptchange;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

@Entity
@Table(name = "t_b_dept_change_confirm", schema = "")
public class TBDeptChangeConfirmEntity extends IdEntity {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 现部门id
     */
    private String deptNId;
    
    /**
     * 现部门名称
     */
    private String deptNName;
    
    /**
     * 原部门id
     */
    private String deptOId;
    
    /**
     * 原部门名称
     */
    private String deptOName;
    
    /**
     * 确认状态，0待确认，1同意，2驳回
     */
    private String confirmStatus;
    
    /**
     * 确认时间
     */
    private Date confirmTime;
    
    /**
     * 确认用户id
     */
    private String confirmUserId;

    /**
     * 确认用户名
     */
    private String confirmUserName;

    /**
     * 意见
     */
    private String msgText;

    /**
     * 创建时间
     */
    private Date createDate;

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "dept_nid")
    public String getDeptNId() {
        return deptNId;
    }

    public void setDeptNId(String deptNId) {
        this.deptNId = deptNId;
    }

    @Column(name = "dept_nname")
    public String getDeptNName() {
        return deptNName;
    }

    public void setDeptNName(String deptNName) {
        this.deptNName = deptNName;
    }

    @Column(name = "dept_oid")
    public String getDeptOId() {
        return deptOId;
    }

    public void setDeptOId(String deptOId) {
        this.deptOId = deptOId;
    }

    @Column(name = "dept_oname")
    public String getDeptOName() {
        return deptOName;
    }

    public void setDeptOName(String deptOName) {
        this.deptOName = deptOName;
    }

    @Column(name = "confirm_status")
    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    @Column(name = "confirm_time")
    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    @Column(name = "confirm_user_id")
    public String getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(String confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    @Column(name = "confirm_user_name")
    public String getConfirmUserName() {
        return confirmUserName;
    }

    public void setConfirmUserName(String confirmUserName) {
        this.confirmUserName = confirmUserName;
    }

    @Column(name = "msg_text")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
