package org.jeecgframework.workflow.pojo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.model.activiti.Process;


/**
 * 业务父类表,如需走流程引擎的业务须继承该表
 */
@Entity
@Table(name = "t_s_basebus")
public class TSBaseBusQuery extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private TSUser TSUser;// 创建人
	private TSType TSType;// 业务类型
	private Date createtime;// 创建时间
	private TSPrjstatus TSPrjstatus;// 业务自定义状态表
	private Process Process = new Process();//工作流引擎实例模型
	private TSBusConfig TSBusConfig = new TSBusConfig();// 业务配置表
	private String userid;
	private String userRealName;//任务发起人
	private String assigneeName;//任务办理人
    private String businessName;//业务名称
    private String businessCode;//业务编码

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public TSUser getTSUser() {
		return TSUser;	
	}

	public void setTSUser(TSUser tSUser) {
		TSUser = tSUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeid")
	public TSType getTSType() {
		return TSType;
	}

	public void setTSType(TSType tSType) {
		TSType = tSType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prjstateid")
	public TSPrjstatus getTSPrjstatus() {
		return TSPrjstatus;
	}

	public void setTSPrjstatus(TSPrjstatus tSPrjstatus) {
		this.TSPrjstatus = tSPrjstatus;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busconfigid")
	public TSBusConfig getTSBusConfig() {
		return TSBusConfig;
	}

	public void setTSBusConfig(TSBusConfig TSBusConfig) {
		this.TSBusConfig = TSBusConfig;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createtime", length = 13)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Transient
	public Process getProcess() {
		return Process;
	}
	public void setProcess(Process Process) {
		this.Process = Process;
	}
	
	@Column(name = "userid",insertable=false, updatable=false)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Transient
	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	@Transient
	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

    @Column(name = "business_name")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Column(name = "business_code")
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

}