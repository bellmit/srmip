package com.kingtake.expert.entity.info;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 专家信息
 * @author onlineGenerator
 * @date 2015-07-13 19:47:51
 * @version V1.0
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "t_er_expert_user", schema = "")
@LogEntity("专家信息用户表")
public class TErExpertUserEntity extends TErExpertEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户名 */
    @FieldDesc("用户名")
    @Excel(name = "用户名")
    private java.lang.String userName;

    /** 密码 */
    @FieldDesc("密码")
    @Excel(name = "密码")
    private java.lang.String userPwd;

    @Column(name = "USER_NAME")
    public java.lang.String getUserName() {
        return userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    @Column(name = "USER_PWD")
    public java.lang.String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(java.lang.String userPwd) {
        this.userPwd = userPwd;
    }

}
