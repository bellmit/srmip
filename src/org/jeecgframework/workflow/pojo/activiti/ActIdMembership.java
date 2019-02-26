package org.jeecgframework.workflow.pojo.activiti;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 人员角色映射表
 */
@Entity
@Table(name = "act_id_membership")
public class ActIdMembership implements java.io.Serializable {

	private ActIdGroup actIdGroup;
	private ActIdUser actIdUser;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id_", nullable = false, insertable = false, updatable = false)
	public ActIdGroup getActIdGroup() {
		return this.actIdGroup;
	}

	public void setActIdGroup(ActIdGroup actIdGroup) {
		this.actIdGroup = actIdGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_", nullable = false, insertable = false, updatable = false)
	public ActIdUser getActIdUser() {
		return this.actIdUser;
	}

	public void setActIdUser(ActIdUser actIdUser) {
		this.actIdUser = actIdUser;
	}

}