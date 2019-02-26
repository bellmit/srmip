package org.jeecgframework.web.system.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 系统用户父类表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_base_user")
@Inheritance(strategy = InheritanceType.JOINED)
@LogEntity("组织机构")
public class TSBaseUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
    @FieldDesc("用户名")
	private String userName;// 用户名
    @FieldDesc("真实姓名")
	private String realName;// 真实姓名
    @FieldDesc("用户使用浏览器类型")
	private String browser;// 用户使用浏览器类型
    @FieldDesc("用户验证唯一标示")
	private String userKey;// 用户验证唯一标示
    @FieldDesc("用户密码")
	private String password;//用户密码
    @FieldDesc("登陆别名")
	private String aliasname;//登陆别名
    @FieldDesc("是否同步工作流引擎")
	private Short activitiSync;//是否同步工作流引擎
    @FieldDesc("状态1：在线,2：离线,0：禁用")
	private Short status;// 状态1：在线,2：离线,0：禁用
    @FieldDesc("签名文件")
	private byte[] signature;// 签名文件
//    update-start--Author:zhangguoming  Date:20140825 for：添加非表字段currentDepart 和 添加userOrgList属性
    //	private TSDepart TSDepart = new TSDepart();// 部门
    private List<TSUserOrg> userOrgList = new ArrayList<TSUserOrg>();
	private TSDepart currentDepart = new TSDepart();// 当前部门
//    update-end--Author:zhangguoming  Date:20140825 for：添加非表字段currentDepart 和 添加userOrgList属性

	@Column(name = "signature",length=3000)
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	@Transient
	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	@Transient
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
	public Short getActivitiSync() {
		return activitiSync;
	}
	@Column(name = "activitisync")
	public void setActivitiSync(Short activitiSync) {
		this.activitiSync = activitiSync;
	}
	
	
	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "aliasname", length = 15)
	public String getAliasname() {
		return aliasname;
	}
	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}
	//	@JsonIgnore    //getList查询转换为列表时处理json转换异常
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "departid")
//	public TSDepart getTSDepart() {
//		return this.TSDepart;
//	}
//
//	public void setTSDepart(TSDepart TSDepart) {
//		this.TSDepart = TSDepart;
//	}
	@Column(name = "username", nullable = false, length = 500)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "realname", length = 36)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

//    update-start--Author:zhangguoming  Date:20140825 for：添加非表字段currentDepart 和 添加userOrgList属性
    @Transient
    public TSDepart getCurrentDepart() {
        return currentDepart;
    }

    public void setCurrentDepart(TSDepart currentDepart) {
        this.currentDepart = currentDepart;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "tsUser")
    public List<TSUserOrg> getUserOrgList() {
        return userOrgList;
    }

    public void setUserOrgList(List<TSUserOrg> userOrgList) {
        this.userOrgList = userOrgList;
    }
//    update-end--Author:zhangguoming  Date:20140825 for：添加非表字段currentDepart 和 添加userOrgList属性
}