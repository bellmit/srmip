package com.kingtake.project.entity.m2pay;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 支付节点管理
 * @author onlineGenerator
 * @date 2015-07-22 19:02:48
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_pay_node", schema = "")
@SuppressWarnings("serial")
@LogEntity("支付节点管理")
public class TPmPayNodeEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 项目基_主键 */
    //	@Excel(name="项目名称", isExportConvert=true, width=20)
    @FieldDesc("项目基_主键")
    private java.lang.String tpId;
    /** 支付时间 */
    @Excel(name = "支付时间", format = "yyyy-MM-dd")
    @FieldDesc("支付时间")
    private java.util.Date payTime;
    /** 支付金额 */
    @FieldDesc("支付金额")
    @Excel(name = "支付金额",isAmount = true)
    private java.math.BigDecimal payAmount;
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注", width = 20)
    private java.lang.String memo;
    /** 支付条件 */
    @FieldDesc("支付条件")
    @Excel(name = "支付条件")
    private java.lang.String payCondition;
    /** 创建人 */
    @FieldDesc("创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @FieldDesc("创建人姓名")
    //  @Excel(name="创建人")
    private java.lang.String createName;
    /** 创建时间 */
    @FieldDesc("创建时间")
    //    @Excel(name="创建时间", format="yyyy-MM-dd HH:mm:ss", width=20)
    private java.util.Date createDate;
    /** 审核标志 */
    @FieldDesc("审核标志")
    @Excel(name = "审核标志", isExportConvert = true)
    private java.lang.String auditFlag;
    /** 审核人id */
    @FieldDesc("审核人id")
    private java.lang.String auditUserid;
    /** 审核人姓名 */
    @FieldDesc("审核人姓名")
    //	@Excel(name="审核人")
    private java.lang.String auditUsername;
    /** 审核时间 */
    @FieldDesc("审核时间")
    //	@Excel(name="审核时间", format="yyyy-MM-dd HH:mm:ss", width=20)
    private java.util.Date auditTime;
    /** 修改人 */
    @FieldDesc("修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @FieldDesc("修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @FieldDesc("修改时间")
    private java.util.Date updateDate;
    /** 可指定金额 */
    @FieldDesc("可指定金额")
    private java.math.BigDecimal balance;

    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

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
     * @return: java.lang.String 项目基_主键
     */
    @Column(name = "T_P_ID", nullable = true, length = 32)
    public java.lang.String getTpId() {
        return this.tpId;
    }

    public java.lang.String convertGetTpId() {
        if (!StringUtil.isNotEmpty(this.tpId)) {
            return "";
        }
        systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        TPmProjectEntity project = systemService.get(TPmProjectEntity.class, this.tpId);
        if (project != null) {
            return project.getProjectName();
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目基_主键
     */
    public void settpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 支付时间
     */
    @Column(name = "PAY_TIME", nullable = true)
    public java.util.Date getPayTime() {
        return this.payTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 支付时间
     */
    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 支付金额
     */
    @Column(name = "PAY_AMOUNT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getPayAmount() {
        return this.payAmount;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 支付金额
     */
    public void setPayAmount(java.math.BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    @Column(name = "MEMO", nullable = true, length = 400)
    public java.lang.String getMemo() {
        return this.memo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }

    @Column(name = "PAY_CONDITION", nullable = true, length = 200)
    public java.lang.String getPayCondition() {
        return payCondition;
    }

    public void setPayCondition(java.lang.String payCondition) {
        this.payCondition = payCondition;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审核人id
     */
    @Column(name = "AUDIT_USERID", nullable = true, length = 50)
    public java.lang.String getAuditUserid() {
        return this.auditUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审核人id
     */
    public void setAuditUserid(java.lang.String auditUserid) {
        this.auditUserid = auditUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审核人姓名
     */
    @Column(name = "AUDIT_USERNAME", nullable = true, length = 50)
    public java.lang.String getAuditUsername() {
        return this.auditUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审核人姓名
     */
    public void setAuditUsername(java.lang.String auditUsername) {
        this.auditUsername = auditUsername;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 审核时间
     */
    @Column(name = "AUDIT_TIME", nullable = true)
    public java.util.Date getAuditTime() {
        return this.auditTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 审核时间
     */
    public void setAuditTime(java.util.Date auditTime) {
        this.auditTime = auditTime;
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
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 审核标志
     */
    @Column(name = "AUDIT_FLAG", nullable = true, length = 1)
    public java.lang.String getAuditFlag() {
        return this.auditFlag;
    }

    public java.lang.String convertGetAuditFlag() {
        return ConvertDicUtil.getConvertDic("0", "SFBZ", this.auditFlag);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 审核标志
     */
    public void setAuditFlag(java.lang.String auditFlag) {
        this.auditFlag = auditFlag;
    }

    @Column(name = "BALANCE", nullable = true)
    public java.math.BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(java.math.BigDecimal balance) {
        this.balance = balance;
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
}
