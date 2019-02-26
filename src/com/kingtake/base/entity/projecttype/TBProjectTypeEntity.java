package com.kingtake.base.entity.projecttype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.common.constant.ProjectConstant;

/**
 * @Title: Entity
 * @Description: T_B_PROJECT_TYPE
 * @author onlineGenerator
 * @date 2015-07-16 17:50:27
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_project_type", schema = "")
@SuppressWarnings("serial")
@LogEntity("项目类型表")
public class TBProjectTypeEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 项目类型名称 */
    @FieldDesc("项目类型名称")
    @Excel(name = "项目类型", width = 20)
    private java.lang.String projectTypeName;
    /** 所属立项形式（计划类/合同类，常量文件中取值） */
    @FieldDesc("立项形式")
    @Excel(name = "立项形式", isExportConvert = true)
    private java.lang.String approvalCode;
    /** 所属经费性质 */
    @FieldDesc("经费性质")
    @Excel(name = "经费性质", isExportConvert = true)
    private java.lang.String fundsPropertyId;
    /** 排序码 */
    @FieldDesc("排序码")
    @Excel(name = "排序码")
    private java.lang.Integer sortCode;
    /** 负责该项目类型的机关参谋 */
    /*
     * @FieldDesc("负责该项目类型的机关参谋")
     * 
     * @Excel(name = "负责该项目类型的机关参谋", isExportConvert = true) private java.lang.String officer;
     */
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注", width = 20)
    private java.lang.String memo;

    /**
     * 申报书类型
     */
    private String declareType;

    /**
     * 父类型id
     */
    private TBProjectTypeEntity parentType;

    /**
     * 有效标志,0 无效，1 有效
     */
    private String validFlag;

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
     * @return: java.lang.String 项目类型名称
     */
    @Column(name = "PROJECT_TYPE_NAME", nullable = true, length = 50)
    public java.lang.String getProjectTypeName() {
        return this.projectTypeName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 项目类型名称
     */
    public void setProjectTypeName(java.lang.String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 所属立项形式（计划类/合同类，常量文件中取值）
     */
    @Column(name = "APPROVAL_CODE", nullable = true, length = 1)
    public java.lang.String getApprovalCode() {
        return this.approvalCode;
    }

    public java.lang.String convertGetApprovalCode() {
        if (ProjectConstant.PROJECT_CONTRACT.equals(this.approvalCode)) {
            return ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_CONTRACT);
        } else if (ProjectConstant.PROJECT_PLAN.equals(this.approvalCode)) {
            return ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_PLAN);
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 所属立项形式（计划类/合同类，常量文件中取值）
     */
    public void setApprovalCode(java.lang.String approvalCode) {
        this.approvalCode = approvalCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 所属经费性质
     */
    @Column(name = "FUNDS_PROPERTY_ID", nullable = true, length = 32)
    public java.lang.String getFundsPropertyId() {
        return this.fundsPropertyId;
    }

    public java.lang.String convertGetFundsPropertyId() {
        if (!StringUtil.isNotEmpty(this.fundsPropertyId)) {
            return "";
        }
        systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        TBFundsPropertyEntity fund = systemService.get(TBFundsPropertyEntity.class, this.fundsPropertyId);
        if (fund != null) {
            return fund.getFundsName();
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 所属经费性质
     */
    public void setFundsPropertyId(java.lang.String fundsPropertyId) {
        this.fundsPropertyId = fundsPropertyId;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 排序码
     */
    @Column(name = "SORT_CODE", nullable = true, length = 6)
    public java.lang.Integer getSortCode() {
        return this.sortCode;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 排序码
     */
    public void setSortCode(java.lang.Integer sortCode) {
        this.sortCode = sortCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    @Column(name = "MEMO", nullable = true, length = 200)
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

    //    @Column(name = "OFFICER", nullable = true, length = 32)
    //	public java.lang.String getOfficer() {
    //		return officer;
    //	}
    //    
    //    public java.lang.String convertGetOfficer() {
    //        if (StringUtil.isNotEmpty(this.fundsPropertyId)) {
    //        	systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
    //        	TSUser user = systemService.get(TSUser.class, this.officer);
    //            if (user != null) {
    //                return user.getRealName();
    //            }
    //        }
    //        return this.officer;
    //    }
    //
    //	public void setOfficer(java.lang.String officer) {
    //		this.officer = officer;
    //	}

    @Column(name = "declare_type")
    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public TBProjectTypeEntity getParentType() {
        return parentType;
    }

    public void setParentType(TBProjectTypeEntity parentType) {
        this.parentType = parentType;
    }

    @Column(name = "valid_flag")
    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

}
