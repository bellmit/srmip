package org.jeecgframework.web.system.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 部门机构表
 * 
 * @author 张代浩
 */
@Entity
@Table(name = "t_s_depart")
@LogEntity("组织机构")
public class TSDepart extends IdEntity implements java.io.Serializable,Cloneable {
    @FieldDesc("上级部门")
    private TSDepart TSPDepart;// 上级部门
    @FieldDesc("部门名称")
    private String departname;// 部门名称
    @FieldDesc("部门描述")
    private String description;// 部门描述
    @FieldDesc("机构编码")
    private String orgCode;// 机构编码
    @FieldDesc("机构类型")
    private String orgType;// 机构类型
    @FieldDesc("下属部门")
    private List<TSDepart> TSDeparts = new ArrayList<TSDepart>();// 下属部门

    /**
     * @Description: 实体新增字段
     * @author lxp
     * @date 2015-6-15 12:45:59
     */
    @FieldDesc("机构简称")
    private String shortname;// 机构简称
    @FieldDesc("学科方向编码")
    private String subjectDirectionCode;// 学科方向编码（从科研代码集中选取，可以有多个，以逗号隔开）
    @FieldDesc("学科方向名称")
    private String subjectDirectionName;// 学科方向名称
    @FieldDesc("排序码")
    private Integer sortCode;// 排序码
    @FieldDesc("有效标记")
    private String validFlag;// 有效标记（固定值，0：否；1：是，默认0）
    @FieldDesc("科研机构标记")
    private String scientificInstitutionFlag;// 科研机构标记(固定值，0：否；1：是)
    @FieldDesc("科研机构组成单位")
    private String sci_insti_group;// 科研机构组成单位
    @FieldDesc("科研机构组成单位名称")
    private String sci_insti_groupname;// 科研机构组成单位名称
    @FieldDesc("主管部门")
    private String manager_depart; // 主管部门
    @FieldDesc("负责参谋")
    private String leader_official; // 负责参谋
    /**
     * 负责参谋id
     */
    private String leaderOfficialId; // 负责参谋id
    @FieldDesc("批复文件")
    private String reply_files; // 批复文件
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
    @FieldDesc("部门类型")
    private String bmlx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentdepartid")
    public TSDepart getTSPDepart() {
        return this.TSPDepart;
    }

    public void setTSPDepart(TSDepart TSPDepart) {
        this.TSPDepart = TSPDepart;
    }

    @Column(name = "departname", nullable = false, length = 100)
    public String getDepartname() {
        return this.departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSPDepart")
    public List<TSDepart> getTSDeparts() {
        return TSDeparts;
    }

    public void setTSDeparts(List<TSDepart> tSDeparts) {
        TSDeparts = tSDeparts;
    }

    @Column(name = "org_code", length = 64)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "org_type", length = 1)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Column(name = "shortname", length = 50)
    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @Column(name = "subject_direction_code", length = 30)
    public String getSubjectDirectionCode() {
        return subjectDirectionCode;
    }

    public void setSubjectDirectionCode(String subjectDirectionCode) {
        this.subjectDirectionCode = subjectDirectionCode;
    }

    @Column(name = "subject_direction_name", length = 200)
    public String getSubjectDirectionName() {
        return subjectDirectionName;
    }

    public void setSubjectDirectionName(String subjectDirectionName) {
        this.subjectDirectionName = subjectDirectionName;
    }

    @Column(name = "sort")
    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    @Column(name = "valid_flag")
    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

    @Column(name = "scientific_institution_flag")
    public String getScientificInstitutionFlag() {
        return scientificInstitutionFlag;
    }

    public void setScientificInstitutionFlag(String scientificInstitutionFlag) {
        this.scientificInstitutionFlag = scientificInstitutionFlag;
    }

    @Column(name = "SCI_INSTI_GROUP", length = 200)
    public String getSci_insti_group() {
        return sci_insti_group;
    }

    public void setSci_insti_group(String sci_insti_group) {
        this.sci_insti_group = sci_insti_group;
    }

    @Column(name = "SCI_INSTI_GROUPNAME", length = 300)
    public String getSci_insti_groupname() {
        return sci_insti_groupname;
    }

    public void setSci_insti_groupname(String sci_insti_groupname) {
        this.sci_insti_groupname = sci_insti_groupname;
    }

    @Column(name = "MANAGER_DEPART", length = 60)
    public String getManager_depart() {
        return manager_depart;
    }

    public void setManager_depart(String manager_depart) {
        this.manager_depart = manager_depart;
    }

    @Column(name = "LEADER_OFFICIAL", length = 50)
    public String getLeader_official() {
        return leader_official;
    }

    public void setLeader_official(String leader_official) {
        this.leader_official = leader_official;
    }

    @Column(name = "REPLY_FILES", length = 500)
    public String getReply_files() {
        return reply_files;
    }

    public void setReply_files(String reply_files) {
        this.reply_files = reply_files;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bid")
    @OrderBy("createdate")
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Column(name = "LEADER_OFFICIAL_ID")
    public String getLeaderOfficialId() {
        return leaderOfficialId;
    }

    public void setLeaderOfficialId(String leaderOfficialId) {
        this.leaderOfficialId = leaderOfficialId;
    }
    
    @Column(name = "BMLX")
    public String getBmlx() {
        return bmlx;
    }

    public void setBmlx(String bmlx) {
        this.bmlx = bmlx;
    }

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}