package org.jeecgframework.workflow.pojo.base;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 流程环节表
 */
@Entity
@Table(name = "t_p_processnode")
public class TPProcessnode extends IdEntity implements java.io.Serializable {

	private TPForm TPForm=new TPForm();//外部表单
	private TPProcess TPProcess=new TPProcess();//所属流程
	private String processnodename;//节点名称
	private String processnodecode;//节点ID
	private String modelandview;//外部表单跳转方法
	private List<TPProcesspro> TPProcesspros = new ArrayList();//流程变量集合
	private List<TPProcessListener> TPProcessListeners = new ArrayList<TPProcessListener>();//环节监听器集合

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formid")
	public TPForm getTPForm() {
		return this.TPForm;
	}

	public void setTPForm(TPForm TPForm) {
		this.TPForm = TPForm;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processid")
	public TPProcess getTPProcess() {
		return this.TPProcess;
	}

	public void setTPProcess(TPProcess TPProcess) {
		this.TPProcess = TPProcess;
	}

	@Column(name = "processnodename", length = 50)
	public String getProcessnodename() {
		return this.processnodename;
	}

	public void setProcessnodename(String processnodename) {
		this.processnodename = processnodename;
	}

	@Column(name = "processnodecode", length = 50)
	public String getProcessnodecode() {
		return this.processnodecode;
	}

	public void setProcessnodecode(String processnodecode) {
		this.processnodecode = processnodecode;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPProcessnode")
	public List<TPProcesspro> getTPProcesspros() {
		return this.TPProcesspros;
	}

	public void setTPProcesspros(List<TPProcesspro> TPProcesspros) {
		this.TPProcesspros = TPProcesspros;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPProcessnode")
	public List<TPProcessListener> getTPProcessListeners() {
		return TPProcessListeners;
	}

	public void setTPProcessListeners(List<TPProcessListener> tPProcessListeners) {
		TPProcessListeners = tPProcessListeners;
	}
	@Column(name = "modelandview", length = 50)
	public String getModelandview() {
		return modelandview;
	}

	public void setModelandview(String modelandview) {
		this.modelandview = modelandview;
	}


}