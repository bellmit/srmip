package com.kingtake.base.entity.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 经费类型表
 * @author onlineGenerator
 * @date 2015-07-17 11:30:53
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_funds_property", schema = "")
@SuppressWarnings("serial")
@LogEntity("经费类型表")
public class TBFundsPropertyEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 性质编码 */
    @FieldDesc("性质编码")
    @Excel(name = "性质编码")
    private java.lang.String fundsCode;
    /** 性质名称 */
    @FieldDesc("性质名称")
    @Excel(name = "性质名称")
    private java.lang.String fundsName;
    /** 是否有绩效费 */
    @FieldDesc("是否有绩效费")
    @Excel(name = "是否有绩效费")
    private java.lang.String proforFlag;
    /** 绩效费默认比例 */
    @FieldDesc("绩效费默认比例")
    @Excel(name = "绩效费默认比例")
    private java.lang.String proforRatio;
    /** 是否有机动费 */
    @FieldDesc("是否有机动费")
    @Excel(name = "是否有机动费")
    private java.lang.String motoFlag;
    /** 机动费默认比例 */
    @FieldDesc("机动费默认比例")
    @Excel(name = "机动费默认比例")
    private java.lang.String motoRatio;
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注")
    private java.lang.String memo;

    /**
     * 有效标志，0 无效，1 有效
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
     * @return: java.lang.String 性质编码
     */
    @Column(name = "FUNDS_CODE", nullable = true, length = 4)
    public java.lang.String getFundsCode() {
        return this.fundsCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 性质编码
     */
    public void setFundsCode(java.lang.String fundsCode) {
        this.fundsCode = fundsCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 性质名称
     */
    @Column(name = "FUNDS_NAME", nullable = true, length = 20)
    public java.lang.String getFundsName() {
        return this.fundsName;
    }
    
    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 性质名称
     */
    public void setFundsName(java.lang.String fundsName) {
        this.fundsName = fundsName;
    }


    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 是否有绩效
     */
    public void setProforFlag(java.lang.String proforFlag) {
        this.proforFlag = proforFlag;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 是否有绩效
     */
    @Column(name = "PROFOR_FLAG", nullable = true, length = 1)
    public java.lang.String getProforFlag() {
        return this.proforFlag;
    }
    
    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 默认绩效比例
     */
    public void setProforRatio(java.lang.String proforRatio) {
        this.proforRatio = proforRatio;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 默认绩效比例
     */
    @Column(name = "PROFOR_RATIO", nullable = true, length = 20)
    public java.lang.String getProforRatio() {
        return this.proforRatio;
    }

    
    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 是否机动费
     */
    public void setMotoFlag(java.lang.String motoFlag) {
        this.motoFlag = motoFlag;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 是否机动费
     */
    @Column(name = "MOTO_FLAG", nullable = true, length = 1)
    public java.lang.String getMotoFlag() {
        return this.motoFlag;
    }
    
    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 默认机动费比例
     */
    public void setMotoRatio(java.lang.String motoRatio) {
        this.motoRatio = motoRatio;
    }
    
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 默认机动费比例
     */
    @Column(name = "MOTO_RATIO", nullable = true, length = 20)
    public java.lang.String getMotoRatio() {
        return this.motoRatio;
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

    @Column(name = "valid_flag")
    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

}
