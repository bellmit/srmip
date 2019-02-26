package com.kingtake.office.entity.disk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_o_group_member", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TOGroupMemberEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private java.lang.String id;

    /**
     * 群id
     */
    private String groupId;

    /**
     * 成员id
     */
    private String userId;

    /**
     * 成员名称
     */
    private String userName;

    /**
     * 加入时间
     */
    private Date joinTime;

    /**
     * 是否是管理员，0 否，1 是
     */
    private String isMgr;

    /**
     * 排序
     */
    private Integer sort;



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

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

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

    @Column(name = "join_time")
    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    @Column(name = "is_mgr")
    public String getIsMgr() {
        return isMgr;
    }

    public void setIsMgr(String isMgr) {
        this.isMgr = isMgr;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
