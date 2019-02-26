package com.kingtake.office.entity.travel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**   
 * @Title: Entity
 * @Description: 差旅-人员请假信息表
 * @author onlineGenerator
 * @date 2015-07-07 11:24:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_travel_leave", schema = "")
@SuppressWarnings("serial")
@LogEntity("差旅请假单")
public class TOTravelLeaveEntity implements java.io.Serializable {
    private static SystemService systemService;
	/**主键*/
    @FieldDesc("主键")
	private java.lang.String id;
    
	/**标题*/
    @Excel(name="标题")
    @FieldDesc("标题")
	private java.lang.String title;
    
	/**关联项目*/
	private TPmProjectEntity project;
	/**关联项目名称*/
    @FieldDesc("关联项目名称")
    private java.lang.String projectName;
	/**请假人员id*/
    @FieldDesc("请假人员id")
	private java.lang.String leaveId;
	/**请假人员姓名*/
    @Excel(name="请假人员姓名")
    @FieldDesc("请假人员姓名")
	private java.lang.String leaveName;
	/**请假人员单位id*/
    @FieldDesc("请假人员单位id")
	private java.lang.String departId;
	/**请假人员单位名称*/
    @Excel(name="请假人员单位名称")
    @FieldDesc("请假人员单位名称")
	private java.lang.String departName;
	/**请假时间*/
    @Excel(name="请假时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
    @FieldDesc("请假时间")
	private java.util.Date leaveTime;
	/**职务技术等级*/
    @Excel(name="职务技术等级", isExportConvert=true, width=18)
    @FieldDesc("职务技术等级")
	private java.lang.String duty;
	/**呈报单位意见*/
    @Excel(name="呈报单位意见")
    @FieldDesc("呈报单位意见")
	private java.lang.String unitOpinion;
	/**意见人id*/
    @FieldDesc("意见人id")
	private java.lang.String opinionUserid;
	/**意见人姓名*/
    @Excel(name="意见人姓名")
    @FieldDesc("意见人姓名")
	private java.lang.String opinionUsername;
	/**意见时间*/
    @Excel(name="意见时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
    @FieldDesc("意见时间")
	private java.util.Date opinionDate;
	/**部领导批示*/
    @Excel(name="部领导批示")
    @FieldDesc("部领导批示")
	private java.lang.String departInstruc;
	/**批示人id*/
    @FieldDesc("批示人id")
	private java.lang.String instrucUserid;
	/**批示人姓名*/
    @Excel(name="批示人姓名")
    @FieldDesc("批示人姓名")
	private java.lang.String instrucUsername;
	/**批示时间*/
    @Excel(name="批示时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
    @FieldDesc("批示时间")
	private java.util.Date instrucDate;
	/**销假情况*/
    @Excel(name="销假情况")
    @FieldDesc("销假情况")
	private java.lang.String backLeaveState;
	/**销假时间*/
    @Excel(name="销假时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
    @FieldDesc("销假时间")
	private java.util.Date backLeaveDate;
	/**备注*/
    @Excel(name="备注")
    @FieldDesc("备注")
	private java.lang.String remark;
	/**是否补办*/
    @Excel(name="是否补办", isExportConvert=true)
    @FieldDesc("是否补办")
	private java.lang.String reissuedFlag;
	/**补办时间*/
    @Excel(name="补办时间", exportFormat = "yyyy-MM-dd hh:mm:ss", width=20)
    @FieldDesc("补办时间")
	private java.util.Date reissuedTime;
	/**补办理由*/
    @Excel(name="补办理由")
    @FieldDesc("补办理由")
	private java.lang.String reissuedReason;

    
    /**允许编辑状态-虚拟列界面判断使用-只允许编辑、删除、发送审批请假人或者创建人是自己的请假申请单*/    
    private java.lang.String editStatus;
    	    

	 /** 附件 */
	@FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

    /**
     * 审批状态
     */
    private String apprStatus;

    /**
     * 审批完成时间
     */
    private Date apprFinishTime;

    /**
     * 审批完成操作人id
     */
    private String apprFinishUserId;

    /**
     * 审批完成操作人名称
     */
    private String apprFinishUserName;

    /** createBy */
    private java.lang.String createBy;
    /** createName */
    private java.lang.String createName;
    /** createDate */
    private java.util.Date createDate;
    /** updateBy */
    private java.lang.String updateBy;
    /** updateName */
    private java.lang.String updateName;
    /** updateDate */
    private java.util.Date updateDate;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联项目id
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }
	
	@Transient
    public java.lang.String getProjectName() {
        return projectName;
    }

    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  请假人员id
	 */
	
	@Column(name ="LEAVE_ID",nullable=true,length=32)
	public java.lang.String getLeaveId(){
		return this.leaveId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  请假人员id
	 */
	public void setLeaveId(java.lang.String leaveId){
		this.leaveId = leaveId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  请假人员姓名
	 */
	
	@Column(name ="LEAVE_NAME",nullable=true,length=50)
	public java.lang.String getLeaveName(){
		return this.leaveName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  请假人员姓名
	 */
	public void setLeaveName(java.lang.String leaveName){
		this.leaveName = leaveName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  请假人员单位id
	 */
	
	@Column(name ="DEPART_ID",nullable=true,length=32)
	public java.lang.String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  请假人员单位id
	 */
	public void setDepartId(java.lang.String departId){
		this.departId = departId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  请假人员单位名称
	 */
	
	@Column(name ="DEPART_NAME",nullable=true,length=60)
	public java.lang.String getDepartName(){
		return this.departName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  请假人员单位名称
	 */
	public void setDepartName(java.lang.String departName){
		this.departName = departName;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  请假时间
	 */
	
	@Column(name ="LEAVE_TIME",nullable=true)
	public java.util.Date getLeaveTime(){
		return this.leaveTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  请假时间
	 */
	public void setLeaveTime(java.util.Date leaveTime){
		this.leaveTime = leaveTime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职务技术等级
	 */
	
	@Column(name ="DUTY",nullable=true,length=6)
	public java.lang.String getDuty(){
		return this.duty;
	}
	
	public java.lang.String convertGetDuty() {
		if(StringUtil.isNotEmpty(this.duty)){
			Map<String, Object> map = SrmipConstants.dicStandardMap.get("ZYJSZW");
			if(map != null){
				Object obj = map.get(this.duty);
				if(obj != null){
					return obj.toString();
				}
			}
		}
        return this.duty;
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职务技术等级
	 */
	public void setDuty(java.lang.String duty){
		this.duty = duty;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  呈报单位意见
	 */
	
	@Column(name ="UNIT_OPINION",nullable=true,length=100)
	public java.lang.String getUnitOpinion(){
		return this.unitOpinion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  呈报单位意见
	 */
	public void setUnitOpinion(java.lang.String unitOpinion){
		this.unitOpinion = unitOpinion;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  意见人id
	 */
	
	@Column(name ="OPINION_USERID",nullable=true,length=32)
	public java.lang.String getOpinionUserid(){
		return this.opinionUserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  意见人id
	 */
	public void setOpinionUserid(java.lang.String opinionUserid){
		this.opinionUserid = opinionUserid;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  意见人姓名
	 */
	
	@Column(name ="OPINION_USERNAME",nullable=true,length=50)
	public java.lang.String getOpinionUsername(){
		return this.opinionUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  意见人姓名
	 */
	public void setOpinionUsername(java.lang.String opinionUsername){
		this.opinionUsername = opinionUsername;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  意见时间
	 */
	
	@Column(name ="OPINION_DATE",nullable=true)
	public java.util.Date getOpinionDate(){
		return this.opinionDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  意见时间
	 */
	public void setOpinionDate(java.util.Date opinionDate){
		this.opinionDate = opinionDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部领导批示
	 */
	
	@Column(name ="DEPART_INSTRUC",nullable=true,length=500)
	public java.lang.String getDepartInstruc(){
		return this.departInstruc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部领导批示
	 */
	public void setDepartInstruc(java.lang.String departInstruc){
		this.departInstruc = departInstruc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批示人id
	 */
	
	@Column(name ="INSTRUC_USERID",nullable=true,length=32)
	public java.lang.String getInstrucUserid(){
		return this.instrucUserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批示人id
	 */
	public void setInstrucUserid(java.lang.String instrucUserid){
		this.instrucUserid = instrucUserid;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批示人姓名
	 */
	
	@Column(name ="INSTRUC_USERNAME",nullable=true,length=50)
	public java.lang.String getInstrucUsername(){
		return this.instrucUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批示人姓名
	 */
	public void setInstrucUsername(java.lang.String instrucUsername){
		this.instrucUsername = instrucUsername;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  批示时间
	 */
	
	@Column(name ="INSTRUC_DATE",nullable=true)
	public java.util.Date getInstrucDate(){
		return this.instrucDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  批示时间
	 */
	public void setInstrucDate(java.util.Date instrucDate){
		this.instrucDate = instrucDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  销假情况
	 */
	
	@Column(name ="BACK_LEAVE_STATE",nullable=true,length=100)
	public java.lang.String getBackLeaveState(){
		return this.backLeaveState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  销假情况
	 */
	public void setBackLeaveState(java.lang.String backLeaveState){
		this.backLeaveState = backLeaveState;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  销假时间
	 */
	
	@Column(name ="BACK_LEAVE_DATE",nullable=true)
	public java.util.Date getBackLeaveDate(){
		return this.backLeaveDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  销假时间
	 */
	public void setBackLeaveDate(java.util.Date backLeaveDate){
		this.backLeaveDate = backLeaveDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	
	@Column(name ="REMARK",nullable=true,length=200)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	
	@Column(name ="TITLE",nullable=true,length=500)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否补办
	 */
	
	@Column(name ="REISSUED_FLAG",nullable=true,length=1)
	public java.lang.String getReissuedFlag(){
		return this.reissuedFlag;
	}

	public java.lang.String convertGetReissuedFlag() {
		if(StringUtil.isNotEmpty(this.reissuedFlag)){
			Map<String, Object> map = SrmipConstants.dicStandardMap.get("SFBZ");
			if(map != null){
				Object obj = map.get(this.reissuedFlag);
				if(obj != null){
					return obj.toString();
				}
			}
		}
        return this.reissuedFlag;
    }
	
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否补办
	 */
	public void setReissuedFlag(java.lang.String reissuedFlag){
		this.reissuedFlag = reissuedFlag;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  补办时间
	 */
	
	@Column(name ="REISSUED_TIME",nullable=true)
	public java.util.Date getReissuedTime(){
		return this.reissuedTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  补办时间
	 */
	public void setReissuedTime(java.util.Date reissuedTime){
		this.reissuedTime = reissuedTime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  补办理由
	 */
	
	@Column(name ="REISSUED_REASON",nullable=true,length=800)
	public java.lang.String getReissuedReason(){
		return this.reissuedReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  补办理由
	 */
	public void setReissuedReason(java.lang.String reissuedReason){
		this.reissuedReason = reissuedReason;
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

    @Column(name = "APPR_STATUS")
    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }
    
    @Transient
    public java.lang.String getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(java.lang.String editStatus) {
        this.editStatus = editStatus;
    }
       

    @Column(name = "APPR_FINISH_TIME")
    public Date getApprFinishTime() {
        return apprFinishTime;
    }

    public void setApprFinishTime(Date apprFinishTime) {
        this.apprFinishTime = apprFinishTime;
    }

    @Column(name = "APPR_FINISH_USER_ID")
    public String getApprFinishUserId() {
        return apprFinishUserId;
    }

    public void setApprFinishUserId(String apprFinishUserId) {
        this.apprFinishUserId = apprFinishUserId;
    }

    @Column(name = "APPR_FINISH_USER_NAME")
    public String getApprFinishUserName() {
        return apprFinishUserName;
    }

    public void setApprFinishUserName(String apprFinishUserName) {
        this.apprFinishUserName = apprFinishUserName;
    }

    @Column(name = "CREATE_BY")
    public java.lang.String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_NAME")
    public java.lang.String getCreateName() {
        return createName;
    }

    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_DATE")
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_BY")
    public java.lang.String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_NAME")
    public java.lang.String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "UPDATE_DATE")
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

}
