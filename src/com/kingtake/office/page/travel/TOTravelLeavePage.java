
package com.kingtake.office.page.travel;
import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.office.entity.travel.TOTravelLeavedetailEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;


/**   
 * @Title: Entity
 * @Description: 差旅-人员请假信息表
 * @author onlineGenerator
 * @date 2015-07-07 11:24:20
 * @version V1.0   
 *
 */
public class TOTravelLeavePage implements java.io.Serializable {
	/**保存-差旅-人员请假详细信息表*/
	private List<TOTravelLeavedetailEntity> tOTravelLeavedetailList = new ArrayList<TOTravelLeavedetailEntity>();
	public List<TOTravelLeavedetailEntity> getTOTravelLeavedetailList() {
		return tOTravelLeavedetailList;
	}
	public void setTOTravelLeavedetailList(List<TOTravelLeavedetailEntity> tOTravelLeavedetailList) {
		this.tOTravelLeavedetailList = tOTravelLeavedetailList;
	}

	/**主键*/
	private java.lang.String id;
	/**关联项目id*/
	private java.lang.String projectId;
	/**请假人员id*/
	private java.lang.String leaveId;
	/**请假人员姓名*/
	private java.lang.String leaveName;
	/**请假人员单位id*/
	private java.lang.String departId;
	/**请假人员单位名称*/
	private java.lang.String departName;
	/**请假时间*/
	private java.util.Date leaveTime;
	/**职务技术等级*/
	private java.lang.String duty;
	/**呈报单位意见*/
	private java.lang.String unitOpinion;
	/**意见人id*/
	private java.lang.String opinionUserid;
	/**意见人姓名*/
	private java.lang.String opinionUsername;
	/**意见时间*/
	private java.util.Date opinionDate;
	/**部领导批示*/
	private java.lang.String departInstruc;
	/**批示人id*/
	private java.lang.String instrucUserid;
	/**批示人姓名*/
	private java.lang.String instrucUsername;
	/**批示时间*/
	private java.util.Date instrucDate;
	/**销假情况*/
	private java.lang.String backLeaveState;
	/**销假时间*/
	private java.util.Date backLeaveDate;
	/**备注*/
	private java.lang.String remark;
	/**是否补办*/
	private java.lang.String reissuedFlag;
	/**补办时间*/
	private java.util.Date reissuedTime;
	/**补办理由*/
	private java.lang.String reissuedReason;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	public java.lang.String getProjectId(){
		return this.projectId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联项目id
	 */
	public void setProjectId(java.lang.String projectId){
		this.projectId = projectId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  请假人员id
	 */
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
	public java.lang.String getDuty(){
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
	 *@return: java.lang.String  是否补办
	 */
	public java.lang.String getReissuedFlag(){
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
}
