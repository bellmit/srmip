package jeecg.template.onetomany;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the T_B_PM_DECLARE database table.
 * 
 */
@Entity
@Table(name="T_B_PM_DECLARE")
@NamedQuery(name="TBPmDeclare.findAll", query="SELECT t FROM TBPmDeclare t")
public class TBPmDeclare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	@Temporal(TemporalType.DATE)
	@Column(name="APPLY_DATE")
	private Date applyDate;

	@Temporal(TemporalType.DATE)
	@Column(name="BEGIN_DATE")
	private Date beginDate;

	@Column(name="BPM_STATUS")
	private String bpmStatus;

	@Column(name="BRANCH_UNIT_ID")
	private String branchUnitId;

	@Column(name="BRANCH_UNIT_NAME")
	private String branchUnitName;

	@Column(name="BUILD_UNIT_ID")
	private String buildUnitId;

	@Column(name="BUILD_UNIT_NAME")
	private String buildUnitName;

	@Column(name="CONSTRUCTION_ADDR")
	private String constructionAddr;

	@Column(name="CONTACT_PHONE")
	private String contactPhone;

	@Column(name="COORPERATION_UNIT")
	private String coorperationUnit;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_NAME")
	private String createName;

	@Column(name="DEPART_AUDITED")
	private String departAudited;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="GUID_ID")
	private String guidId;

	@Column(name="GUID_NAME")
	private String guidName;

	@Column(name="MAILING_ADDRESS")
	private String mailingAddress;

	@Column(name="POSTAL_CODE")
	private String postalCode;

	@Column(name="PROJECT_MANAGER_ID")
	private String projectManagerId;

	@Column(name="PROJECT_MANAGER_NAME")
	private String projectManagerName;

	@Column(name="PROJECT_NAME")
	private String projectName;

	@Column(name="PROJECT_NO")
	private String projectNo;

	@Column(name="PROJECT_SRC")
	private String projectSrc;

	@Column(name="PROJECT_TYPE_ID")
	private String projectTypeId;

	@Column(name="RESEARCH_CONTENT")
	private String researchContent;

	@Column(name="SCHEDULE_AND_ACHIEVE")
	private String scheduleAndAchieve;

	@Column(name="SECRET_CODE")
	private String secretCode;

	@Column(name="SUGGEST_LEVEL")
	private String suggestLevel;

	@Column(name="T_P_ID")
	private String tPId;

	@Column(name="UPDATE_BY")
	private String updateBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate;

	@Column(name="UPDATE_NAME")
	private String updateName;

	public TBPmDeclare() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getBpmStatus() {
		return this.bpmStatus;
	}

	public void setBpmStatus(String bpmStatus) {
		this.bpmStatus = bpmStatus;
	}

	public String getBranchUnitId() {
		return this.branchUnitId;
	}

	public void setBranchUnitId(String branchUnitId) {
		this.branchUnitId = branchUnitId;
	}

	public String getBranchUnitName() {
		return this.branchUnitName;
	}

	public void setBranchUnitName(String branchUnitName) {
		this.branchUnitName = branchUnitName;
	}

	public String getBuildUnitId() {
		return this.buildUnitId;
	}

	public void setBuildUnitId(String buildUnitId) {
		this.buildUnitId = buildUnitId;
	}

	public String getBuildUnitName() {
		return this.buildUnitName;
	}

	public void setBuildUnitName(String buildUnitName) {
		this.buildUnitName = buildUnitName;
	}

	public String getConstructionAddr() {
		return this.constructionAddr;
	}

	public void setConstructionAddr(String constructionAddr) {
		this.constructionAddr = constructionAddr;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCoorperationUnit() {
		return this.coorperationUnit;
	}

	public void setCoorperationUnit(String coorperationUnit) {
		this.coorperationUnit = coorperationUnit;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getDepartAudited() {
		return this.departAudited;
	}

	public void setDepartAudited(String departAudited) {
		this.departAudited = departAudited;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGuidId() {
		return this.guidId;
	}

	public void setGuidId(String guidId) {
		this.guidId = guidId;
	}

	public String getGuidName() {
		return this.guidName;
	}

	public void setGuidName(String guidName) {
		this.guidName = guidName;
	}

	public String getMailingAddress() {
		return this.mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProjectManagerId() {
		return this.projectManagerId;
	}

	public void setProjectManagerId(String projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	public String getProjectManagerName() {
		return this.projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNo() {
		return this.projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getProjectSrc() {
		return this.projectSrc;
	}

	public void setProjectSrc(String projectSrc) {
		this.projectSrc = projectSrc;
	}

	public String getProjectTypeId() {
		return this.projectTypeId;
	}

	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public String getResearchContent() {
		return this.researchContent;
	}

	public void setResearchContent(String researchContent) {
		this.researchContent = researchContent;
	}

	public String getScheduleAndAchieve() {
		return this.scheduleAndAchieve;
	}

	public void setScheduleAndAchieve(String scheduleAndAchieve) {
		this.scheduleAndAchieve = scheduleAndAchieve;
	}

	public String getSecretCode() {
		return this.secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getSuggestLevel() {
		return this.suggestLevel;
	}

	public void setSuggestLevel(String suggestLevel) {
		this.suggestLevel = suggestLevel;
	}

	public String getTPId() {
		return this.tPId;
	}

	public void setTPId(String tPId) {
		this.tPId = tPId;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateName() {
		return this.updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

}