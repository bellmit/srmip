package org.jeecgframework.web.system.pojo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;

/**
 * 自定义附件类
 * 
 * @author admin
 * @date 2015年6月25日下午3:20:51
 * 
 */
@Entity
@Table(name = "t_s_files")
@PrimaryKeyJoinColumn(name = "id")
@SuppressWarnings("serial")
@LogEntity("附件上传")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TSFilesEntity extends TSAttachment implements java.io.Serializable {

    /** 业务表id */
    @FieldDesc("业务表id")
    private String bid;

    /** 业务类型，规则如下：业务实体名_业务分类*/
    @FieldDesc("业务规则")
    private String businessType;
    
    
    /**
     * 删除标志
     */
    @FieldDesc("删除状态")
    private String delFlag;
    
    /**
     * 删除时间
     */
    @FieldDesc("删除时间")
	private Date delTime;
    
    @FieldDesc("删除用户")
    private String delUser;
    
    @FieldDesc("删除用户名称")
    private String delUserName;
    
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;
    
    /**
     * 添加索引标志，1 为成功，0为失败
     */
    private String addIndexFlag;
    
    /**
     * 
     * 添加索引失败原因
     */
    private String addIndexMsg;


	@Column(name = "bid")
    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Column(name = "businessType")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "del_Flag")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "del_Time")
	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@Column(name = "del_User")
	public String getDelUser() {
		return delUser;
	}

	public void setDelUser(String delUser) {
		this.delUser = delUser;
	}
    
	@Column(name = "del_UserName")
    public String getDelUserName() {
		return delUserName;
	}

	public void setDelUserName(String delUserName) {
		this.delUserName = delUserName;
	}

    @Column(name = "project_id")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name="add_index_flag")
    public String getAddIndexFlag() {
        return addIndexFlag;
    }

    public void setAddIndexFlag(String addIndexFlag) {
        this.addIndexFlag = addIndexFlag;
    }

    @Column(name="add_index_msg")
    public String getAddIndexMsg() {
        return addIndexMsg;
    }

    public void setAddIndexMsg(String addIndexMsg) {
        this.addIndexMsg = addIndexMsg;
    }

}
