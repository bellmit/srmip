package com.kingtake.project.entity.resultspread;

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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Entity
 * @Description: 成果推广基本信息表
 * @author onlineGenerator
 * @date 2015-12-07 00:03:12
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_result_spread", schema = "")
@SuppressWarnings("serial")
public class TBResultSpreadEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 技术开发单位 */
    @Excel(name = "技术开发单位")
    private java.lang.String techDevUnit;
    /** 成果完成人id */
    private java.lang.String finishUserid;
    /** 成果完成人姓名 */
    @Excel(name = "成果完成人姓名")
    private java.lang.String finishUsername;
    /** 技术简介 */
    @Excel(name = "技术简介")
    private java.lang.String techSummary;
    /** 专利状态 */
    @Excel(name = "专利状态")
    private java.lang.String patentStatus;
    /** 获奖情况 */
    @Excel(name = "获奖情况")
    private java.lang.String rewardInfo;
    /** 技术状态 */
    @Excel(name = "技术状态")
    private java.lang.String techStatus;
    /** 可应用领域 */
    @Excel(name = "可应用领域")
    private java.lang.String applyScope;
    /** 转化预期 */
    @Excel(name = "转化预期")
    private java.lang.String changeExpect;
    /** 投入需求 */
    @Excel(name = "投入需求",isAmount = true)
    private java.math.BigDecimal devotionRequirement;
    /** 预期效益 */
    @Excel(name = "预期效益")
    private java.lang.String expectBenefit;
    /** 联系人 */
    @Excel(name = "联系人")
    private java.lang.String resultContact;
    /** 电话 */
    @Excel(name = "电话")
    private java.lang.String resultPhone;
    /** 转化进行情况 */
    @Excel(name = "转化进行情况", isExportConvert = true)
    private java.lang.String changeInfo;
    /** 合作单位 */
    @Excel(name = "合作单位")
    private java.lang.String cooperativeUnit;
    /** 转让形式 */
    @Excel(name = "转让形式", isExportConvert = true)
    private java.lang.String transferForm;
    /** 合同情况 */
    @Excel(name = "合同情况")
    private java.lang.String contractInfo;
    /** 合同期限 */
    @Excel(name = "合同期限")
    private java.lang.String contractDeadline;
    /** 合同金额 */
    @Excel(name = "合同金额")
    private java.lang.String contractAmount;
    /** 年度收益 */
    @Excel(name = "年度收益")
    private java.lang.String contractIncome;
    /** 需解决困难 */
    @Excel(name = "需解决困难")
    private java.lang.String resolveDifficult;
    /** 联系人 */
    @Excel(name = "联系人")
    private java.lang.String changeContact;
    /** 电话 */
    @Excel(name = "电话")
    private java.lang.String changePhone;

    private String projectId;

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
     * @return: java.lang.String 技术开发单位
     */
    @Column(name = "TECH_DEV_UNIT", nullable = true, length = 120)
    public java.lang.String getTechDevUnit() {
        return this.techDevUnit;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 技术开发单位
     */
    public void setTechDevUnit(java.lang.String techDevUnit) {
        this.techDevUnit = techDevUnit;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果完成人id
     */
    @Column(name = "FINISH_USERID", nullable = true, length = 32)
    public java.lang.String getFinishUserid() {
        return this.finishUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果完成人id
     */
    public void setFinishUserid(java.lang.String finishUserid) {
        this.finishUserid = finishUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果完成人姓名
     */
    @Column(name = "FINISH_USERNAME", nullable = true, length = 120)
    public java.lang.String getFinishUsername() {
        return this.finishUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果完成人姓名
     */
    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 技术简介
     */
    @Column(name = "TECH_SUMMARY", nullable = true, length = 800)
    public java.lang.String getTechSummary() {
        return this.techSummary;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 技术简介
     */
    public void setTechSummary(java.lang.String techSummary) {
        this.techSummary = techSummary;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 专利状态
     */
    @Column(name = "PATENT_STATUS", nullable = true, length = 200)
    public java.lang.String getPatentStatus() {
        return this.patentStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 专利状态
     */
    public void setPatentStatus(java.lang.String patentStatus) {
        this.patentStatus = patentStatus;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 获奖情况
     */
    @Column(name = "REWARD_INFO", nullable = true, length = 120)
    public java.lang.String getRewardInfo() {
        return this.rewardInfo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 获奖情况
     */
    public void setRewardInfo(java.lang.String rewardInfo) {
        this.rewardInfo = rewardInfo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 技术状态
     */
    @Column(name = "TECH_STATUS", nullable = true, length = 800)
    public java.lang.String getTechStatus() {
        return this.techStatus;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 技术状态
     */
    public void setTechStatus(java.lang.String techStatus) {
        this.techStatus = techStatus;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 可应用领域
     */
    @Column(name = "APPLY_SCOPE", nullable = true, length = 800)
    public java.lang.String getApplyScope() {
        return this.applyScope;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 可应用领域
     */
    public void setApplyScope(java.lang.String applyScope) {
        this.applyScope = applyScope;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 转化预期
     */
    @Column(name = "CHANGE_EXPECT", nullable = true, length = 800)
    public java.lang.String getChangeExpect() {
        return this.changeExpect;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 转化预期
     */
    public void setChangeExpect(java.lang.String changeExpect) {
        this.changeExpect = changeExpect;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 投入需求
     */
    @Column(name = "DEVOTION_REQUIREMENT", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getDevotionRequirement() {
        return this.devotionRequirement;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 投入需求
     */
    public void setDevotionRequirement(java.math.BigDecimal devotionRequirement) {
        this.devotionRequirement = devotionRequirement;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 预期效益
     */
    @Column(name = "EXPECT_BENEFIT", nullable = true, length = 800)
    public java.lang.String getExpectBenefit() {
        return this.expectBenefit;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 预期效益
     */
    public void setExpectBenefit(java.lang.String expectBenefit) {
        this.expectBenefit = expectBenefit;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人
     */
    @Column(name = "RESULT_CONTACT", nullable = true, length = 36)
    public java.lang.String getResultContact() {
        return this.resultContact;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人
     */
    public void setResultContact(java.lang.String resultContact) {
        this.resultContact = resultContact;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 电话
     */
    @Column(name = "RESULT_PHONE", nullable = true, length = 30)
    public java.lang.String getResultPhone() {
        return this.resultPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 电话
     */
    public void setResultPhone(java.lang.String resultPhone) {
        this.resultPhone = resultPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 转化进行情况
     */
    @Column(name = "CHANGE_INFO", nullable = true, length = 1)
    public java.lang.String getChangeInfo() {
        return this.changeInfo;
    }

    public java.lang.String convertGetChangeInfo() {
        if (StringUtil.isNotEmpty(this.changeInfo)) {
            return SrmipConstants.dicResearchMap.get("CGZHQK").get(this.changeInfo).toString();
        }
        return this.changeInfo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 转化进行情况
     */
    public void setChangeInfo(java.lang.String changeInfo) {
        this.changeInfo = changeInfo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合作单位
     */
    @Column(name = "COOPERATIVE_UNIT", nullable = true, length = 350)
    public java.lang.String getCooperativeUnit() {
        return this.cooperativeUnit;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合作单位
     */
    public void setCooperativeUnit(java.lang.String cooperativeUnit) {
        this.cooperativeUnit = cooperativeUnit;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 转让形式
     */
    @Column(name = "TRANSFER_FORM", nullable = true, length = 1)
    public java.lang.String getTransferForm() {
        return this.transferForm;
    }

    public java.lang.String convertGetTransferForm() {
        if (StringUtil.isNotEmpty(this.transferForm)) {
            return SrmipConstants.dicResearchMap.get("CGZRXS").get(this.transferForm).toString();
        }
        return this.transferForm;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 转让形式
     */
    public void setTransferForm(java.lang.String transferForm) {
        this.transferForm = transferForm;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同情况
     */
    @Column(name = "CONTRACT_INFO", nullable = true, length = 32)
    public java.lang.String getContractInfo() {
        return this.contractInfo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同情况
     */
    public void setContractInfo(java.lang.String contractInfo) {
        this.contractInfo = contractInfo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同期限
     */
    @Column(name = "CONTRACT_DEADLINE", nullable = true, length = 50)
    public java.lang.String getContractDeadline() {
        return this.contractDeadline;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同期限
     */
    public void setContractDeadline(java.lang.String contractDeadline) {
        this.contractDeadline = contractDeadline;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合同金额
     */
    @Column(name = "CONTRACT_AMOUNT", nullable = true, length = 800)
    public java.lang.String getContractAmount() {
        return this.contractAmount;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合同金额
     */
    public void setContractAmount(java.lang.String contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 年度收益
     */
    @Column(name = "CONTRACT_INCOME", nullable = true, length = 800)
    public java.lang.String getContractIncome() {
        return this.contractIncome;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 年度收益
     */
    public void setContractIncome(java.lang.String contractIncome) {
        this.contractIncome = contractIncome;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 需解决困难
     */
    @Column(name = "RESOLVE_DIFFICULT", nullable = true, length = 800)
    public java.lang.String getResolveDifficult() {
        return this.resolveDifficult;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 需解决困难
     */
    public void setResolveDifficult(java.lang.String resolveDifficult) {
        this.resolveDifficult = resolveDifficult;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人
     */
    @Column(name = "CHANGE_CONTACT", nullable = true, length = 36)
    public java.lang.String getChangeContact() {
        return this.changeContact;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人
     */
    public void setChangeContact(java.lang.String changeContact) {
        this.changeContact = changeContact;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 电话
     */
    @Column(name = "CHANGE_PHONE", nullable = true, length = 30)
    public java.lang.String getChangePhone() {
        return this.changePhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 电话
     */
    public void setChangePhone(java.lang.String changePhone) {
        this.changePhone = changePhone;
    }

    @Column(name = "PROJECT_ID", nullable = true)
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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
