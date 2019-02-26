package com.kingtake.project.page.contractnode;

/**
 * @Title: Entity
 * @Description: T_PM_CONTRACT_NODE
 * @author onlineGenerator
 * @date 2015-08-01 16:16:43
 * @version V1.0
 *
 */

public class TPmContractNodePage implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 节点名称 */
    private java.lang.String contractNodeName;
    /** 进出帐标志 */
    private java.lang.String inOutFlag;
    /** 关联进出帐合同审批表主键 */
    private String inOutContractid;

    private String contractName;
    /** 关联支付节点id(关联到项目支付节点信息表id) */
    private java.lang.String projPayNodeId;
    /** 计划/合同标志 */
    private java.lang.String planContractFlag;
    /** 类型(0:标准项目；1：外协项目；2：校内协作) */
    private java.lang.String prjType;
    /** 完成时间 */
    private java.util.Date completeDate;
    /** 成果形式 */
    private java.lang.String resultForm;
    /** 评价方法 */
    private java.lang.String evaluationMethod;
    /** 备注 */
    private java.lang.String remarks;

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
    /** 考核标志 */
    private String checkFlag;

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 创建时间
     */
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
     * @return: java.lang.String 关联支付节点id(关联到项目支付节点信息表id)
     */
    public java.lang.String getProjPayNodeId() {
        return this.projPayNodeId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联支付节点id(关联到项目支付节点信息表id)
     */
    public void setProjPayNodeId(java.lang.String projPayNodeId) {
        this.projPayNodeId = projPayNodeId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主键
     */
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
     * @return: java.lang.String 类型(0:标准项目；1：外协项目；2：校内协作)
     */
    public java.lang.String getPrjType() {
        return this.prjType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 类型(0:标准项目；1：外协项目；2：校内协作)
     */
    public void setPrjType(java.lang.String prjType) {
        this.prjType = prjType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 节点名称
     */
    public java.lang.String getContractNodeName() {
        return this.contractNodeName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 节点名称
     */
    public void setContractNodeName(java.lang.String contractNodeName) {
        this.contractNodeName = contractNodeName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果形式
     */
    public java.lang.String getResultForm() {
        return this.resultForm;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果形式
     */
    public void setResultForm(java.lang.String resultForm) {
        this.resultForm = resultForm;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 评价方法
     */
    public java.lang.String getEvaluationMethod() {
        return this.evaluationMethod;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 评价方法
     */
    public void setEvaluationMethod(java.lang.String evaluationMethod) {
        this.evaluationMethod = evaluationMethod;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 完成时间
     */
    public java.util.Date getCompleteDate() {
        return this.completeDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 完成时间
     */
    public void setCompleteDate(java.util.Date completeDate) {
        this.completeDate = completeDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    public java.lang.String getRemarks() {
        return this.remarks;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setRemarks(java.lang.String remarks) {
        this.remarks = remarks;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 计划/合同标志
     */
    public java.lang.String getPlanContractFlag() {
        return this.planContractFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 计划/合同标志
     */
    public void setPlanContractFlag(java.lang.String planContractFlag) {
        this.planContractFlag = planContractFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 进出帐标志
     */
    public java.lang.String getInOutFlag() {
        return this.inOutFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 进出帐标志
     */
    public void setInOutFlag(java.lang.String inOutFlag) {
        this.inOutFlag = inOutFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创建人
     */
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

    public String getInOutContractid() {
        return inOutContractid;
    }

    public void setInOutContractid(String inOutContractid) {
        this.inOutContractid = inOutContractid;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

}
