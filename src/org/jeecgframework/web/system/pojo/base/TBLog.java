package org.jeecgframework.web.system.pojo.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * TLog entity.
 *
 * @author 张代浩
 */
@Entity
@Table(name = "t_b_log")
public class TBLog extends IdEntity implements java.io.Serializable {
    /**
     * 操作人
     */
    private TSUser TSUser;
    /**
     * 操作时间
     */
    private Timestamp operatetime;
    /**
     * 操作人姓名
     */
    private String username;
    /**
     * 操作人电脑ip
     */
    private String ip;
    /**
     * 级别（常量：1：info；2：warn；3：error）
     */
    private Short loglevel;
    /**
     * 类型（常量：1登录/2退出/3插入/4删除/5更新/6上传/7其它…，自行扩展）
     */
    private Short operatetype;
    /**
     * 描述（填写操作的补充信息，如：被修改的对象主键及关键字段说明-新增人员信息的主键及人员姓名等；删除了主表信息后相关联的子表信息也跟着删除了，也可以在这里补充说明一下）
     */
    private String logcontent;
    /**
     * 原值json串
     */
    private String oldJson;
    /**
     * 新值json串
     */
    private String newJson;
    /**
     * 表单url
     */
    private String formUrl;
    /**
     * 用户浏览器类型
     */
    private String browser;

    /**
     * 操作对象名称.
     */
    private String opObjectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    public TSUser getTSUser() {
        return this.TSUser;
    }

    public void setTSUser(TSUser TSUser) {
        this.TSUser = TSUser;
    }

    @Column(name = "loglevel")
    public Short getLoglevel() {
        return this.loglevel;
    }

    public void setLoglevel(Short loglevel) {
        this.loglevel = loglevel;
    }

    @Column(name = "operatetime", nullable = false, length = 35)
    public Timestamp getOperatetime() {
        return this.operatetime;
    }

    public void setOperatetime(Timestamp operatetime) {
        this.operatetime = operatetime;
    }

    @Column(name = "operatetype")
    public Short getOperatetype() {
        return this.operatetype;
    }

    public void setOperatetype(Short operatetype) {
        this.operatetype = operatetype;
    }

    @Column(name = "logcontent", nullable = false, length = 2000)
    public String getLogcontent() {
        return this.logcontent;
    }

    public void setLogcontent(String logcontent) {
        this.logcontent = logcontent;
    }

    @Column(name = "browser", length = 100)
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "old_json")
    public String getOldJson() {
        return oldJson;
    }

    public void setOldJson(String oldJson) {
        this.oldJson = oldJson;
    }

    @Column(name = "new_json")
    public String getNewJson() {
        return newJson;
    }

    public void setNewJson(String newJson) {
        this.newJson = newJson;
    }

    @Column(name = "form_url")
    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    @Column(name = "op_object_name")
    public String getOpObjectName() {
        return opObjectName;
    }

    public void setOpObjectName(String opObjectName) {
        this.opObjectName = opObjectName;
    }

}