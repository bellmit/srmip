package com.kingtake.project.entity.contractnodecheck;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**
 * @Title: Entity
 * @Description: 出账合同节点考核
 *
 */
@Entity
@Table(name = "t_pm_outcontract_node_check", schema = "")
@SuppressWarnings("serial")
@LogEntity("合同合同节点考核")
public class TPmOutContractNodeCheckEntity implements java.io.Serializable {
    
    /** 主键 */
    private java.lang.String id;
    
    /**
     * 项目id
     */
    private String projectId;
    
    /**
     * 合同id
     */
    private String contractId;
    
    /** 节点主键 */
    private java.lang.String contractNodeId;
    
    
    /** 合同编号 */
    private java.lang.String contractCode;
    
    /**
     * 合同类型,从代码集取
     */
    private String htlx;
    
    /**
     * 项目名称
     */
    private String projectName;
    
    /**
     * 合同名称
     */
    private String contractName;
    
    /**
     * 合同总金额
     */
    private BigDecimal contractTotalFunds;
    
    /**
     * 责任单位
     */
    private String zrdw;
    
    /**
     * 项目组长
     */
    private String xmzz;
    
    /**
     * 项目组长电话
     */
    private String xmzzPhone;
    
    /**
     * 联系人
     */
    private String lxr;
    
    /**
     * 联系人电话
     */
    private String lxrPhone;
    
    /**
     * 乙方名称
     */
    private String yfmc;
    
    /**
     * 乙方地址
     */
    private String yfdz;
    
    /**
     * 乙方种类
     */
    private String yfzl;
    
    /**
     * 乙方联系人
     */
    private String yflxr;
    
    /**
     * 乙方联系电话
     */
    private String yflxdh;
    
    /**
     * 节点名称
     */
    private String jdmc;
    
    /**
     * 完成时间
     */
    private Date wcsj;
    
    /**
     * 节点金额
     */
    private BigDecimal jdje;
    
    /**
     * 评审前已完成工作
     */
    private String psqywcgz;
    
    /**
     * 评审方式,1会议评审，2会签评审
     */
    private String psfs;
    
    /**
     * 评审时间
     */
    private Date pssj;
    
    /**
     * 评审地点
     */
    private String psdd;
    
    /**
     * 评审参评人员id
     */
    private String pscpryId;
    
    /**
     * 评审参评人员
     */
    private String pscpry;
    
    /**
     * 评审结果
     */
    private String psjg;
    
    /**
     * 计划处审核人id
     */
    private String jhcshrId;
    
    /**
     * 计划处审核人
     */
    private String jhcshr;
    
    /**
     * 计划处审核发送人
     */
    private String jhcshfsr;
    
    /**
     * 计划处审核发送人id
     */
    private String jhcshfsrId;
    
    /**
     * 计划处审核发送时间
     */
    private Date jhcshfssj;
	
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
     * 评审状态
     */
    private String auditFlag;
    
    /**
     * 0待审核,1通过,2不通过
     */
    private String payFlag;
    
    /**
     * 计划处审核意见
     */
    private String msgText;
    
    /**
     * 重要标志：1:重要，0:一般
     */
    private String importantFlag;
    
    /** 完成时间 */
    @Excel(name = "完成时间")
    private java.util.Date finishTime;
    /** 完成操作人id */
    private java.lang.String finishUserid;
    /** 完成操作人姓名 */
    @Excel(name = "完成操作人姓名")
    private java.lang.String finishUsername;
    
    /**
     * 附件关联编码
     */
    private String attachmentCode;

    /**
     * 附件列表
     */
    private List<TSFilesEntity> attachments;
    
    /**
     * 评审结果附件列表
     */
    private List<TSFilesEntity> psjgFj;

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 完成时间
     */
    @Column(name = "FINISH_TIME", nullable = true)
    public java.util.Date getFinishTime() {
        return this.finishTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 完成时间
     */
    public void setFinishTime(java.util.Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 完成操作人id
     */
    @Column(name = "FINISH_USER_ID", nullable = true, length = 32)
    public java.lang.String getFinishUserid() {
        return this.finishUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 完成操作人id
     */
    public void setFinishUserid(java.lang.String finishUserid) {
        this.finishUserid = finishUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 完成操作人姓名
     */
    @Column(name = "FINISH_USER_NAME", nullable = true, length = 50)
    public java.lang.String getFinishUsername() {
        return this.finishUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 完成操作人姓名
     */
    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
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
     * @return: java.lang.String 节点主键
     */
    @Column(name = "CONTRACT_NODE_ID", nullable = true, length = 32)
    public java.lang.String getContractNodeId() {
        return this.contractNodeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 节点主键
     */
    public void setContractNodeId(java.lang.String contractNodeId) {
        this.contractNodeId = contractNodeId;
    }

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

    @Column(name = "attachment_code")
    public String getAttachmentCode() {
        return attachmentCode;
    }

    public void setAttachmentCode(String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    
    @Column(name="contract_code")
    public java.lang.String getContractCode() {
        return contractCode;
    }

    public void setContractCode(java.lang.String contractCode) {
        this.contractCode = contractCode;
    }

    @Column(name="zrdw")
    public String getZrdw() {
        return zrdw;
    }

    public void setZrdw(String zrdw) {
        this.zrdw = zrdw;
    }

    @Column(name="xmzz")
    public String getXmzz() {
        return xmzz;
    }

    public void setXmzz(String xmzz) {
        this.xmzz = xmzz;
    }

    @Column(name="xmzz_phone")
    public String getXmzzPhone() {
        return xmzzPhone;
    }

    public void setXmzzPhone(String xmzzPhone) {
        this.xmzzPhone = xmzzPhone;
    }

    @Column(name="lxr")
    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    @Column(name="lxr_phone")
    public String getLxrPhone() {
        return lxrPhone;
    }

    public void setLxrPhone(String lxrPhone) {
        this.lxrPhone = lxrPhone;
    }

    @Column(name="yfmc")
    public String getYfmc() {
        return yfmc;
    }

    public void setYfmc(String yfmc) {
        this.yfmc = yfmc;
    }

    @Column(name="yfdz")
    public String getYfdz() {
        return yfdz;
    }

    public void setYfdz(String yfdz) {
        this.yfdz = yfdz;
    }

    @Column(name="yfzl")
    public String getYfzl() {
        return yfzl;
    }

    public void setYfzl(String yfzl) {
        this.yfzl = yfzl;
    }

    @Column(name="yflxr")
    public String getYflxr() {
        return yflxr;
    }

    public void setYflxr(String yflxr) {
        this.yflxr = yflxr;
    }

    @Column(name="yflxdh")
    public String getYflxdh() {
        return yflxdh;
    }

    public void setYflxdh(String yflxdh) {
        this.yflxdh = yflxdh;
    }

    @Column(name="jdmc")
    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    @Column(name="wcsj")
    public Date getWcsj() {
        return wcsj;
    }

    public void setWcsj(Date wcsj) {
        this.wcsj = wcsj;
    }

    @Column(name="jdje")
    public BigDecimal getJdje() {
        return jdje;
    }

    public void setJdje(BigDecimal jdje) {
        this.jdje = jdje;
    }

    @Column(name="psqywcgz")
    public String getPsqywcgz() {
        return psqywcgz;
    }

    public void setPsqywcgz(String psqywcgz) {
        this.psqywcgz = psqywcgz;
    }

    @Column(name="psfs")
    public String getPsfs() {
        return psfs;
    }

    public void setPsfs(String psfs) {
        this.psfs = psfs;
    }

    @Column(name="pssj")
    public Date getPssj() {
        return pssj;
    }

    public void setPssj(Date pssj) {
        this.pssj = pssj;
    }

    @Column(name="psdd")
    public String getPsdd() {
        return psdd;
    }

    public void setPsdd(String psdd) {
        this.psdd = psdd;
    }

    @Column(name="pscpry_id")
    public String getPscpryId() {
        return pscpryId;
    }

    
    public void setPscpryId(String pscpryId) {
        this.pscpryId = pscpryId;
    }

    @Column(name="pscpry")
    public String getPscpry() {
        return pscpry;
    }

    public void setPscpry(String pscpry) {
        this.pscpry = pscpry;
    }

    @Column(name="psjg")
    public String getPsjg() {
        return psjg;
    }

    public void setPsjg(String psjg) {
        this.psjg = psjg;
    }

    @Column(name="audit_flag")
    public String getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(String auditFlag) {
        this.auditFlag = auditFlag;
    }

    @Column(name="contract_total_funds")
    public BigDecimal getContractTotalFunds() {
        return contractTotalFunds;
    }

    public void setContractTotalFunds(BigDecimal contractTotalFunds) {
        this.contractTotalFunds = contractTotalFunds;
    }

    @Column(name="pay_flag")
    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    @Column(name="PROJECT_ID")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Column(name="contract_name")
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    @Column(name="important_flag")
    public String getImportantFlag() {
        return importantFlag;
    }

    public void setImportantFlag(String importantFlag) {
        this.importantFlag = importantFlag;
    }

    @Column(name="project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name="htlx")
    public String getHtlx() {
        return htlx;
    }

    public void setHtlx(String htlx) {
        this.htlx = htlx;
    }

    @Column(name="contract_id")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Column(name="JHCSHR_ID")
    public String getJhcshrId() {
        return jhcshrId;
    }

    public void setJhcshrId(String jhcshrId) {
        this.jhcshrId = jhcshrId;
    }

    @Column(name="JHCSHR")
    public String getJhcshr() {
        return jhcshr;
    }

    public void setJhcshr(String jhcshr) {
        this.jhcshr = jhcshr;
    }

    
    
    @Column(name="msg_text")
    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Column(name="JHCSHFSR")
    public String getJhcshfsr() {
        return jhcshfsr;
    }

    public void setJhcshfsr(String jhcshfsr) {
        this.jhcshfsr = jhcshfsr;
    }

    @Column(name="JHCSHFSR_ID")
    public String getJhcshfsrId() {
        return jhcshfsrId;
    }

    public void setJhcshfsrId(String jhcshfsrId) {
        this.jhcshfsrId = jhcshfsrId;
    }

    @Column(name="jhcshfssj")
    public Date getJhcshfssj() {
        return jhcshfssj;
    }

    public void setJhcshfssj(Date jhcshfssj) {
        this.jhcshfssj = jhcshfssj;
    }

    @Transient
    public List<TSFilesEntity> getPsjgFj() {
        return psjgFj;
    }

    public void setPsjgFj(List<TSFilesEntity> psjgFj) {
        this.psjgFj = psjgFj;
    }
    
    
}
