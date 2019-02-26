package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 业务配置表
 */
@Entity
@Table(name = "t_s_busconfig")
public class TSBusConfig extends IdEntity implements java.io.Serializable {
	private String busname;//业务名称
	/**
	 * 表单类型：自定义实体，onlinecoding配置表
	 */
	private String formType;
	/**
	 * onlinecoding配置表ID
	 */
	private String onlineId;
	private TSTable TSTable=new TSTable();//业务表
	private TPProcess TPProcess=new TPProcess();
	@Column(name = "busname", length = 30)
	public String getBusname() {
		return busname;
	}

	public void setBusname(String busname) {
		this.busname = busname;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tableid")
	public TSTable getTSTable() {
		return TSTable;
	}

	public void setTSTable(TSTable tSTable) {
		TSTable = tSTable;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processid")
	public TPProcess getTPProcess() {
		return TPProcess;
	}

	public void setTPProcess(TPProcess tPProcess) {
		TPProcess = tPProcess;
	}
	
	@Column(name = "form_type", length = 10)
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	@Column(name = "online_id", length = 50)
	public String getOnlineId() {
		return onlineId;
	}

	public void setOnlineId(String onlineId) {
		this.onlineId = onlineId;
	}



}