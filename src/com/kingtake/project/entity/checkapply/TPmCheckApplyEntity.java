package com.kingtake.project.entity.checkapply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 产品检验申请表
 * @author onlineGenerator
 * @date 2015-07-04 09:42:34
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_check_apply")
@SuppressWarnings("serial")
@DynamicInsert
@DynamicUpdate
@LogEntity("产品检验申请信息")
public class TPmCheckApplyEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 产品检验单编号 */
    @Excel(name = "产品检验单编号")
    private java.lang.String applyNo;
    /** 项目名称 */
    @FieldDesc("项目名称")
    private java.lang.String productName;
    /** 产品代码 */
    @FieldDesc("产品代码")
    private java.lang.String productCode;
    /** 产品编号 */
    @FieldDesc("产品编号")
    private java.lang.String productNo;
    /** 验收时间 */
    @FieldDesc("验收时间")
    private java.util.Date checkTime;
    /** 验收结果 */
    @FieldDesc("验收结果")
    private java.lang.String checkResult;
    /** 合格证书编号 */
    @FieldDesc("合格证书编号")
    private java.lang.String qualificationNo;
    /** 检验记录 */
    @FieldDesc("检验记录")
    private java.lang.String checkRecord;
    /** 附件编码 */
    @FieldDesc("附件编码")
    private java.lang.String attachmentCode;
    /** 状态 */
    @FieldDesc("状态")
    private java.lang.String status;
    

    @FieldDesc("创建人英文名")
    private java.lang.String createBy;
    @FieldDesc("创建人中文名")
    private java.lang.String createName;
    @FieldDesc("创建人日期")
    private java.util.Date createDate;
    @FieldDesc("更新人英文名")
    private java.lang.String updateBy;
    @FieldDesc("更新人中文名")
    private java.lang.String updateName;
    @FieldDesc("更新时间")
    private java.util.Date updateDate;

    /** 项目ID */
    @FieldDesc("项目ID")
    private java.lang.String projectId;

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
     * @return: java.lang.String 产品检验单编号
     */
    @Column(name = "APPLY_NO", nullable = true, length = 32)
    public java.lang.String getApplyNo() {
        return this.applyNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 产品检验单编号
     */
    public void setApplyNo(java.lang.String applyNo) {
        this.applyNo = applyNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 产品名称
     */
    @Column(name = "PRODUCT_NAME", nullable = true, length = 100)
    public java.lang.String getProductName() {
        return this.productName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 产品名称
     */
    public void setProductName(java.lang.String productName) {
        this.productName = productName;
    }

    /**
     * 产品代码
     * 
     * @return
     */
    @Column(name = "PRODUCT_CODE", nullable = true, length = 20)
    public java.lang.String getProductCode() {
        return productCode;
    }

    /**
     * 产品代码
     * 
     * @return
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 产品编号
     */
    @Column(name = "PRODUCT_NO", nullable = true, length = 20)
    public java.lang.String getProductNo() {
        return this.productNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 产品编号
     */
    public void setProductNo(java.lang.String productNo) {
        this.productNo = productNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 验收时间
     */
    @Column(name = "CHECK_TIME", nullable = false)
    public java.util.Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(java.util.Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 验收结果
     */
    @Column(name = "CHECK_RESULT", nullable = true, length = 1)
    public java.lang.String getCheckResult() {
        return this.checkResult;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 验收结果
     */
    public void setCheckResult(java.lang.String checkResult) {
        this.checkResult = checkResult;
    }    

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 合格证书编号
     */
    @Column(name = "QUALIFICATION_NO", nullable = true, length = 20)
    public java.lang.String getQualificationNo() {
        return this.qualificationNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 合格证书编号
     */
    public void setQualificationNo(java.lang.String qualificationNo) {
        this.qualificationNo = qualificationNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 检验记录
     */
    @Column(name = "CHECK_RECORD", nullable = true, length = 50)
    public java.lang.String getCheckRecord() {
        return this.checkRecord;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 检验记录
     */
    public void setCheckRecord(java.lang.String checkRecord) {
        this.checkRecord = checkRecord;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 附件编码
     */
    @Column(name = "ATTACHMENT_CODE", nullable = true, length = 32)
    public java.lang.String getAttachmentCode() {
        return this.attachmentCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 附件编码
     */
    public void setAttachmentCode(java.lang.String attachmentCode) {
        this.attachmentCode = attachmentCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 状态
     */
    @Column(name = "STATUS", nullable = true, length = 1)
    public java.lang.String getStatus() {
        return this.status;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 状态
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
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

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 项目主键
     */
    @Column(name = "T_P_ID", nullable = true, length = 32)
    public java.lang.String getProjectId() {
        return this.projectId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目主键
     */
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }    
	
    
}
