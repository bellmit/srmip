package com.kingtake.office.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 车辆基本信息
 * @author onlineGenerator
 * @date 2015-07-08 10:00:34
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_vehicle", schema = "")
@SuppressWarnings("serial")
@LogEntity("车辆基本信息")
public class TOVehicleEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 车牌号 */
    @FieldDesc("车牌号")
    @Excel(name = "车牌号", width = 15)
    private java.lang.String licenseNo;
    /** 购置时间 */
    @FieldDesc("购置时间")
    @Excel(name = "购置时间", exportFormat = "yyyy-MM-dd")
    private java.util.Date purchaseTime;
    /** 司机id */
    @FieldDesc("司机id ")
    private java.lang.String driverId;
    /** 司机姓名 */
    @FieldDesc("司机姓名")
    @Excel(name = "司机姓名")
    private java.lang.String driverName;
    /** 所属处室id */
    @FieldDesc("所属处室id")
    private java.lang.String sectionId;
    /** 所属处室名称 */
    @FieldDesc("所属处室名称")
    @Excel(name = "所属处室", width = 15)
    private java.lang.String sectionName;
    /** 车辆状态 */
    @FieldDesc("车辆状态")
    @Excel(name = "车辆状态", isExportConvert = true)
    private java.lang.String vehicleState;
    /** 车辆类型 */
    @FieldDesc("车辆类型")
    @Excel(name = "车辆类型", isExportConvert = true)
    private java.lang.String type;
    /** 军车标志 */
    @FieldDesc("军车标志")
    @Excel(name = "军车标志", isExportConvert = true)
    private java.lang.String militaryFlag;
    /** 加油卡卡号 */
    @FieldDesc("加油卡卡号")
    @Excel(name = "加油卡卡号")
    private java.lang.String refuelNo;
    /** 加油卡余额 */
    @FieldDesc("加油卡余额")
    @Excel(name = "加油卡余额",isAmount = true)
    private java.math.BigDecimal refuelBalance;
    /** 购保标志 */
    @FieldDesc("购保标志")
    @Excel(name = "购保标志", isExportConvert = true)
    private java.lang.String insuranceFlag;
    /** 购保时间 */
    @FieldDesc("购保时间")
    @Excel(name = "购保时间", exportFormat = "yyyy-MM-dd")
    private java.util.Date insuranceTime;
    /** 年检标志 */
    @FieldDesc("年检标志")
    @Excel(name = "年检标志", isExportConvert = true)
    private java.lang.String annualSurveyFlag;
    /** 年检时间 */
    @FieldDesc("年检时间")
    @Excel(name = "年检时间", exportFormat = "yyyy-MM-dd")
    private java.util.Date annualSurveyTime;
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注")
    private java.lang.String remark;

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
     * @return: java.lang.String 车牌号
     */
    @Column(name = "LICENSE_NO", nullable = true, length = 10)
    public java.lang.String getLicenseNo() {
        return this.licenseNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 车牌号
     */
    public void setLicenseNo(java.lang.String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 车辆类型
     */
    @Column(name = "TYPE", nullable = true, length = 2)
    public java.lang.String getType() {
        return this.type;
    }

    public java.lang.String convertGetType() {
        return ConvertDicUtil.getConvertDic("1", "CLLX", this.type);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 车辆类型
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 购置时间
     */
    @Column(name = "PURCHASE_TIME", nullable = true)
    public java.util.Date getPurchaseTime() {
        return this.purchaseTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 购置时间
     */
    public void setPurchaseTime(java.util.Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 司机id
     */
    @Column(name = "DRIVER_ID", nullable = true, length = 32)
    public java.lang.String getDriverId() {
        return this.driverId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 司机id
     */
    public void setDriverId(java.lang.String driverId) {
        this.driverId = driverId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 司机姓名
     */
    @Column(name = "DRIVER_NAME", nullable = true, length = 36)
    public java.lang.String getDriverName() {
        return this.driverName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 司机姓名
     */
    public void setDriverName(java.lang.String driverName) {
        this.driverName = driverName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 车辆状态
     */
    @Column(name = "VEHICLE_STATE", nullable = true, length = 1)
    public java.lang.String getVehicleState() {
        return this.vehicleState;
    }

    public java.lang.String convertGetVehicleState() {
        return ConvertDicUtil.getConvertDic("1", "CLZT", this.vehicleState);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 车辆状态
     */
    public void setVehicleState(java.lang.String vehicleState) {
        this.vehicleState = vehicleState;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 购保标志
     */
    @Column(name = "INSURANCE_FLAG", nullable = true, length = 1)
    public java.lang.String getInsuranceFlag() {
        return this.insuranceFlag;
    }

    public java.lang.String convertGetInsuranceFlag() {
        return ConvertDicUtil.getConvertDic("1", "GBBZ", this.insuranceFlag);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 购保标志
     */
    public void setInsuranceFlag(java.lang.String insuranceFlag) {
        this.insuranceFlag = insuranceFlag;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 购保时间
     */
    @Column(name = "INSURANCE_TIME", nullable = true)
    public java.util.Date getInsuranceTime() {
        return this.insuranceTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 购保时间
     */
    public void setInsuranceTime(java.util.Date insuranceTime) {
        this.insuranceTime = insuranceTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 年检标志
     */
    @Column(name = "ANNUAL_SURVEY_FLAG", nullable = true, length = 1)
    public java.lang.String getAnnualSurveyFlag() {
        return this.annualSurveyFlag;
    }

    public java.lang.String convertGetAnnualSurveyFlag() {
        return ConvertDicUtil.getConvertDic("1", "NJBZ", this.annualSurveyFlag);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 年检标志
     */
    public void setAnnualSurveyFlag(java.lang.String annualSurveyFlag) {
        this.annualSurveyFlag = annualSurveyFlag;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 年检时间
     */
    @Column(name = "ANNUAL_SURVEY_TIME", nullable = true)
    public java.util.Date getAnnualSurveyTime() {
        return this.annualSurveyTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 年检时间
     */
    public void setAnnualSurveyTime(java.util.Date annualSurveyTime) {
        this.annualSurveyTime = annualSurveyTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 加油卡卡号
     */
    @Column(name = "REFUEL_NO", nullable = true, length = 30)
    public java.lang.String getRefuelNo() {
        return this.refuelNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 加油卡卡号
     */
    public void setRefuelNo(java.lang.String refuelNo) {
        this.refuelNo = refuelNo;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 加油卡余额
     */
    @Column(name = "REFUEL_BALANCE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getRefuelBalance() {
        return this.refuelBalance;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 加油卡余额
     */
    public void setRefuelBalance(java.math.BigDecimal refuelBalance) {
        this.refuelBalance = refuelBalance;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 军车标志
     */
    @Column(name = "MILITARY_FLAG", nullable = true, length = 1)
    public java.lang.String getMilitaryFlag() {
        return this.militaryFlag;
    }

    public java.lang.String convertGetMilitaryFlag() {
        return ConvertDicUtil.getConvertDic("0", "SFBZ", this.militaryFlag);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 军车标志
     */
    public void setMilitaryFlag(java.lang.String militaryFlag) {
        this.militaryFlag = militaryFlag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 所属处室id
     */
    @Column(name = "SECTION_ID", nullable = true, length = 32)
    public java.lang.String getSectionId() {
        return this.sectionId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 所属处室id
     */
    public void setSectionId(java.lang.String sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 所属处室名称
     */
    @Column(name = "SECTION_NAME", nullable = true, length = 60)
    public java.lang.String getSectionName() {
        return this.sectionName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 所属处室名称
     */
    public void setSectionName(java.lang.String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    @Column(name = "REMARK", nullable = true, length = 200)
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }
}
