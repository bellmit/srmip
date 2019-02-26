package com.kingtake.office.entity.purchaseplanmain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import com.kingtake.office.entity.purchaseplandetail.TBPurchasePlanDetailEntity;

/**
 * @Title: Entity
 * @Description: 科研采购计划
 * @author onlineGenerator
 * @date 2016-05-31 18:50:15
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_purchase_plan", schema = "")
@SuppressWarnings("serial")
public class TBPurchasePlanMainEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 责任单位id */
    private java.lang.String dutyDepartId;
    /** 责任单位名称 */
    @Excel(name = "责任单位")
    private java.lang.String dutyDepartName;
    /** 负责人id */
    private java.lang.String managerId;
    /** 负责人 */
    @Excel(name = "负责人")
    private java.lang.String managerName;
    /** 项目编码 */
    private java.lang.String projectCode;
    /** 项目名称 */
    @Excel(name = "项目名称")
    private java.lang.String projectName;
    
    /**
     * 项目id
     */
    private String projectId;
    /** 总经费 */
    @Excel(name = "总经费",isAmount = true)
    private java.math.BigDecimal totalFunds;
    /** 计划时间 */
    private java.util.Date planDate;
    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    private java.lang.String createName;
    /** 创建时间 */
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;
    
    /**
     * 采购计划明细
     */
    private String planDetailStr;
    
    /**
     * 采购计划编号年度
     */
    private String codeYear;
    
    /**
     * 采购计划编号批次
     */
    private String codeBatch;
    
    
    /**
     * 录入入口标志，1表示课题组，2表示机关
     */
    private String inputRole;

    /**
     * 审批人id
     */
    private String finishUserId;

    /**
     * 审批人姓名
     */
    @Excel(name = "审核人")
    private String finishUserName;

    /**
     * 审批时间
     */
    @Excel(name = "审核时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 提交状态,0 未提交，1 已提交，2 通过 ，3 不通过
     */
    @Excel(name = "审核状态", replace = { "未发送_0", "已发送_1", "已完成_2", "被驳回_3" })
    private String finishFlag;

    /**
     * 修改意见
     */
    private String msgText;

    @ExcelCollection(name = "采购计划明细")
    private List<TBPurchasePlanDetailEntity> planDetailList;

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 32)
    public java.lang.String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 主键
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 责任单位id
     */

    @Column(name = "DUTY_DEPART_ID", nullable = true, length = 32)
    public java.lang.String getDutyDepartId() {
        return this.dutyDepartId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 责任单位id
     */
    public void setDutyDepartId(java.lang.String dutyDepartId) {
        this.dutyDepartId = dutyDepartId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 责任单位名称
     */

    @Column(name = "DUTY_DEPART_NAME", nullable = true, length = 200)
    public java.lang.String getDutyDepartName() {
        return this.dutyDepartName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 责任单位名称
     */
    public void setDutyDepartName(java.lang.String dutyDepartName) {
        this.dutyDepartName = dutyDepartName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 负责人id
     */

    @Column(name = "MANAGER_ID", nullable = true, length = 32)
    public java.lang.String getManagerId() {
        return this.managerId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 负责人id
     */
    public void setManagerId(java.lang.String managerId) {
        this.managerId = managerId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 负责人
     */

    @Column(name = "MANAGER_NAME", nullable = true, length = 36)
    public java.lang.String getManagerName() {
        return this.managerName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 负责人
     */
    public void setManagerName(java.lang.String managerName) {
        this.managerName = managerName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目编码
     */

    @Column(name = "PROJECT_CODE", nullable = true, length = 30)
    public java.lang.String getProjectCode() {
        return this.projectCode;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目编码
     */
    public void setProjectCode(java.lang.String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目名称
     */

    @Column(name = "PROJECT_NAME", nullable = true, length = 200)
    public java.lang.String getProjectName() {
        return this.projectName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 项目名称
     */
    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     * 
     * @return: java.math.BigDecimal 总经费
     */

    @Column(name = "TOTAL_FUNDS", nullable = true, scale = 2, length = 12)
    public java.math.BigDecimal getTotalFunds() {
        return this.totalFunds;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     * 
     * @param: java.math.BigDecimal 总经费
     */
    public void setTotalFunds(java.math.BigDecimal totalFunds) {
        this.totalFunds = totalFunds;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 创建人
     */

    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 创建人
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 创建人姓名
     */

    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 创建人姓名
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 创建时间
     */

    @Column(name = "CREATE_DATE", nullable = true)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 创建时间
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 修改人
     */

    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 修改人
     */
    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 修改人姓名
     */

    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 修改人姓名
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 修改时间
     */

    @Column(name = "UPDATE_DATE", nullable = true)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 修改时间
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 计划时间
     */

    @Column(name = "PLAN_DATE", nullable = true)
    public java.util.Date getPlanDate() {
        return this.planDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 计划时间
     */
    public void setPlanDate(java.util.Date planDate) {
        this.planDate = planDate;
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PURCHASE_PLAN_ID")
    public List<TBPurchasePlanDetailEntity> getPlanDetailList() {
        return planDetailList;
    }

    public void setPlanDetailList(List<TBPurchasePlanDetailEntity> planDetailList) {
        this.planDetailList = planDetailList;
    }

    @Column(name="FINISH_USER_ID")
    public String getFinishUserId() {
        return finishUserId;
    }

    public void setFinishUserId(String finishUserId) {
        this.finishUserId = finishUserId;
    }

    @Column(name="FINISH_USER_NAME")
    public String getFinishUserName() {
        return finishUserName;
    }

    public void setFinishUserName(String finishUserName) {
        this.finishUserName = finishUserName;
    }

    @Column(name="FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name="FINISH_FLAG")
    public String getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(String finishFlag) {
        this.finishFlag = finishFlag;
    }

    @Column(name = "msg_text")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Column(name="code_year")
    public String getCodeYear() {
        return codeYear;
    }

    public void setCodeYear(String codeYear) {
        this.codeYear = codeYear;
    }

    @Column(name="code_batch")
    public String getCodeBatch() {
        return codeBatch;
    }

    public void setCodeBatch(String codeBatch) {
        this.codeBatch = codeBatch;
    }

    @Column(name="input_role")
    public String getInputRole() {
        return inputRole;
    }

    public void setInputRole(String inputRole) {
        this.inputRole = inputRole;
    }

    @Column(name="project_id")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Transient
    public String getPlanDetailStr() {
        return planDetailStr;
    }

    public void setPlanDetailStr(String planDetailStr) {
        this.planDetailStr = planDetailStr;
    }

    
}
