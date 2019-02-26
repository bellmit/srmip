package com.kingtake.office.entity.approval;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;

/**
 * @Title: Entity
 * @Description: 呈批件项目汇总
 * @author onlineGenerator
 * @date 2015-08-25 10:37:43
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_approval_project_summary")
@SuppressWarnings("serial")
public class TOApprovalProjectSummaryEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 所属项目id */
    private java.lang.String projectId;
    /** 所属项目名称 */
    @Excel(name = "所属项目名称")
    private java.lang.String projectName;
    /** 项目编号 */
    @Excel(name = "项目编号")
    private java.lang.String projectNo;
    /** 负责人id */
    private java.lang.String projectManagerId;
    /** 负责人名称 */
    @Excel(name = "负责人名称")
    private java.lang.String projectManager;
    /** 起始日期 */
    @Excel(name = "起始日期")
    private java.util.Date beginDate;
    /** 截止日期 */
    @Excel(name = "截止日期")
    private java.util.Date endDate;
    /** 项目类型 */
    @Excel(name = "项目类型")
    private TBProjectTypeEntity projectType;
    /** 经费类型 */
    @Excel(name = "经费类型")
    private TBFundsPropertyEntity feeType;
    /** 承研部门 */
    @Excel(name = "承研部门")
    private TSDepart devDepart;
    /** 分管部门 */
    @Excel(name = "分管部门")
    private java.lang.String manageDepart;
    /** 项目来源 */
    @Excel(name = "项目来源")
    private java.lang.String projectSource;
    /** 依据文号 */
    @Excel(name = "依据文号")
    private java.lang.String accordingNum;
    /** 总经费 */
    @Excel(name = "总经费",isAmount = true)
    private java.math.BigDecimal allFee;
    /** 关联呈批件信息表主键 */
    private java.lang.String approvalId;

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
     * @return: java.lang.String 所属项目id
     */
    @Column(name = "PROJECT_ID", nullable = true, length = 32)
    public java.lang.String getProjectId() {
        return this.projectId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 所属项目id
     */
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 所属项目名称
     */
    @Column(name = "PROJECT_NAME", nullable = true, length = 100)
    public java.lang.String getProjectName() {
        return this.projectName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 所属项目名称
     */
    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目编号
     */
    @Column(name = "PROJECT_NO", nullable = true, length = 20)
    public java.lang.String getProjectNo() {
        return this.projectNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目编号
     */
    public void setProjectNo(java.lang.String projectNo) {
        this.projectNo = projectNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 负责人id
     */
    @Column(name = "PROJECT_MANAGER_ID", nullable = true, length = 32)
    public java.lang.String getProjectManagerId() {
        return this.projectManagerId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 负责人id
     */
    public void setProjectManagerId(java.lang.String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 负责人名称
     */
    @Column(name = "PROJECT_MANAGER", nullable = true, length = 50)
    public java.lang.String getProjectManager() {
        return this.projectManager;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 负责人名称
     */
    public void setProjectManager(java.lang.String projectManager) {
        this.projectManager = projectManager;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 起始日期
     */
    @Column(name = "BEGIN_DATE", nullable = true)
    public java.util.Date getBeginDate() {
        return this.beginDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 起始日期
     */
    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 截止日期
     */
    @Column(name = "END_DATE", nullable = true)
    public java.util.Date getEndDate() {
        return this.endDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 截止日期
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目类型
     */
    @ManyToOne
    @JoinColumn(name = "PROJECT_TYPE")
    public TBProjectTypeEntity getProjectType() {
        return this.projectType;
    }

    public java.lang.String convertGetProjectType() {
        return this.projectType.getProjectTypeName();
    }

    public void setProjectType(TBProjectTypeEntity projectType) {
        this.projectType = projectType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 经费类型
     */
    @ManyToOne
    @JoinColumn(name = "FEE_TYPE")
    public TBFundsPropertyEntity getFeeType() {
        return feeType;
    }

    public void setFeeType(TBFundsPropertyEntity feeType) {
        this.feeType = feeType;
    }

    public java.lang.String convertGetFeeType() {
        return feeType.getFundsName();
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 承研部门
     */
    @ManyToOne
    @JoinColumn(name = "developer_depart")
    public TSDepart getDevDepart() {
        return devDepart;
    }

    public void setDevDepart(TSDepart devDepart) {
        this.devDepart = devDepart;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 分管部门
     */
    @Column(name = "MANAGE_DEPART", nullable = true, length = 60)
    public java.lang.String getManageDepart() {
        return this.manageDepart;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 分管部门
     */
    public void setManageDepart(java.lang.String manageDepart) {
        this.manageDepart = manageDepart;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目来源
     */
    @Column(name = "PROJECT_SOURCE", nullable = true, length = 100)
    public java.lang.String getProjectSource() {
        return this.projectSource;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目来源
     */
    public void setProjectSource(java.lang.String projectSource) {
        this.projectSource = projectSource;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 依据文号
     */
    @Column(name = "ACCORDING_NUM", nullable = true, length = 24)
    public java.lang.String getAccordingNum() {
        return this.accordingNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 依据文号
     */
    public void setAccordingNum(java.lang.String accordingNum) {
        this.accordingNum = accordingNum;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 总经费
     */
    @Column(name = "ALL_FEE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getAllFee() {
        return this.allFee;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 总经费
     */
    public void setAllFee(java.math.BigDecimal allFee) {
        this.allFee = allFee;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联呈批件信息表主键
     */
    @Column(name = "APPROVAL_ID", nullable = true, length = 32)
    public java.lang.String getApprovalId() {
        return this.approvalId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联呈批件信息表主键
     */
    public void setApprovalId(java.lang.String approvalId) {
        this.approvalId = approvalId;
    }
}
