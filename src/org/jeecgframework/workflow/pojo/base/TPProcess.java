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
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;


/**
 * 流程定义表
 */
@Entity
@Table(name = "t_p_process")
public class TPProcess extends IdEntity implements java.io.Serializable {
	private TSType TSType;//流程类型
	private TSUser TSUser;//创建人
	private String processname;//流程名称
	private Short processstate;//流程状态
	private String processxmlpath;//流程物理路径
	private byte[] processxml;//流程二进制文件
	private String processkey;//流程唯一Key
	private String note;//流程描述
	private Set<TPProcesspro> TPProcesspros = new HashSet<TPProcesspro>(0);	//流程变量集合
	private List<TPProcessnode> TPProcessnodes = new ArrayList<TPProcessnode>(0);//流程环节集合


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeid")
	public TSType getTSType() {
		return this.TSType;
	}

	public void setTSType(TSType TSType) {
		this.TSType = TSType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public TSUser getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(TSUser TSUser) {
		this.TSUser = TSUser;
	}

	@Column(name = "processname", length = 50)
	public String getProcessname() {
		return this.processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	@Column(name = "processstate")
	public Short getProcessstate() {
		return this.processstate;
	}

	public void setProcessstate(Short processstate) {
		this.processstate = processstate;
	}

	@Column(name = "processxmlpath", length = 100)
	public String getProcessxmlpath() {
		return this.processxmlpath;
	}

	public void setProcessxmlpath(String processxmlpath) {
		this.processxmlpath = processxmlpath;
	}

	@Column(name = "processxml")
	public byte[] getProcessxml() {
		return this.processxml;
	}

	public void setProcessxml(byte[] processxml) {
		this.processxml = processxml;
	}

	@Column(name = "processkey", length = 100)
	public String getProcesskey() {
		return this.processkey;
	}

	public void setProcesskey(String processkey) {
		this.processkey = processkey;
	}

	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPProcess")
	public Set<TPProcesspro> getTPProcesspros() {
		return this.TPProcesspros;
	}

	public void setTPProcesspros(Set<TPProcesspro> TPProcesspros) {
		this.TPProcesspros = TPProcesspros;
	}	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPProcess")
	public List<TPProcessnode> getTPProcessnodes() {
		return this.TPProcessnodes;
	}

	public void setTPProcessnodes(List<TPProcessnode> TPProcessnodes) {
		this.TPProcessnodes = TPProcessnodes;
	}

}