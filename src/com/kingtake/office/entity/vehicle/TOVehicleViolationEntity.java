package com.kingtake.office.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 车辆违章信息表
 * @author onlineGenerator
 * @date 2015-07-10 15:29:34
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_vehicle_violation", schema = "")
@SuppressWarnings("serial")
@LogEntity("车辆违章信息表")
public class TOVehicleViolationEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 车辆信息表id */
    @FieldDesc("车辆信息表id")
    @Excel(name = "车牌号", isExportConvert = true, width = 15)
    private java.lang.String vehicleId;
    /** 车辆使用登记表id */
    @FieldDesc("车辆使用登记表id")
    @Excel(name = "关联使用信息", isExportConvert = true, width = 20)
    private java.lang.String useId;
    /** 违章人id */
    @FieldDesc("违章人id")
    private java.lang.String violationId;
    /** 违章人姓名 */
    @FieldDesc("违章人姓名")
    @Excel(name = "违章人")
    private java.lang.String violationName;
    /** 违章时间 */
    @FieldDesc("违章时间")
    @Excel(name = "违章时间", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    private java.util.Date violationTime;
    /** 违章地点 */
    @FieldDesc("违章地点")
    @Excel(name = "违章地点", width = 20)
    private java.lang.String violationAddr;
    /** 违章代码 */
    @FieldDesc("违章代码")
    @Excel(name = "违章代码")
    private java.lang.String violationCode;
    /** 违章描述 */
    @FieldDesc("违章描述")
    @Excel(name = "违章描述", width = 20)
    private java.lang.String violationDesc;
    /** 违章分数 */
    @FieldDesc("违章分数")
    @Excel(name = "违章分数")
    private java.lang.Integer violationScore;
    /** 处罚金额 */
    @FieldDesc("处罚金额")
    @Excel(name = "处罚金额",isAmount = true)
    private java.math.BigDecimal fines;
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
     * @return: java.lang.String 车辆使用登记表id
     */
    @Column(name = "USE_ID", nullable = true, length = 32)
    public java.lang.String getUseId() {
        return this.useId;
    }

    public java.lang.String convertGetUseId() {
        if (!StringUtil.isNotEmpty(this.getUseId())) {
            return "";
        }
        systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        TOVehicleUseEntity vehicleUse = systemService.get(TOVehicleUseEntity.class, this.useId);
        if (vehicleUse != null) {
            if (vehicleUse.getOutTime() != null) {
                String outTime = DateUtils.date2Str(vehicleUse.getOutTime(), DateUtils.date_sdf);
                return vehicleUse.getUseName() + outTime + "用车";
            } else {
                return vehicleUse.getUseName() + "用车";
            }
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 车辆使用登记表id
     */
    public void setUseId(java.lang.String useId) {
        this.useId = useId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 车辆信息表id
     */
    @Column(name = "VEHICLE_ID", nullable = true, length = 32)
    public java.lang.String getVehicleId() {
        return this.vehicleId;
    }

    public java.lang.String convertGetVehicleId() {
        if (!StringUtil.isNotEmpty(this.vehicleId)) {
            return "";
        }
        systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        TOVehicleEntity vehicle = systemService.get(TOVehicleEntity.class, this.vehicleId);
        if (vehicle != null) {
            return vehicle.getLicenseNo();
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 车辆信息表id
     */
    public void setVehicleId(java.lang.String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 违章人id
     */
    @Column(name = "VIOLATION_ID", nullable = true, length = 32)
    public java.lang.String getViolationId() {
        return this.violationId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 违章人id
     */
    public void setViolationId(java.lang.String violationId) {
        this.violationId = violationId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 违章人姓名
     */
    @Column(name = "VIOLATION_NAME", nullable = true, length = 36)
    public java.lang.String getViolationName() {
        return this.violationName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 违章人姓名
     */
    public void setViolationName(java.lang.String violationName) {
        this.violationName = violationName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 违章时间
     */
    @Column(name = "VIOLATION_TIME", nullable = true)
    public java.util.Date getViolationTime() {
        return this.violationTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 违章时间
     */
    public void setViolationTime(java.util.Date violationTime) {
        this.violationTime = violationTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 违章地点
     */
    @Column(name = "VIOLATION_ADDR", nullable = true, length = 150)
    public java.lang.String getViolationAddr() {
        return this.violationAddr;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 违章地点
     */
    public void setViolationAddr(java.lang.String violationAddr) {
        this.violationAddr = violationAddr;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 违章代码
     */
    @Column(name = "VIOLATION_CODE", nullable = true, length = 32)
    public java.lang.String getViolationCode() {
        return this.violationCode;
    }

    public java.lang.String convertGetViolationCode() {
        return ConvertDicUtil.getConvertDic("", "", this.violationCode);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 违章代码
     */
    public void setViolationCode(java.lang.String violationCode) {
        this.violationCode = violationCode;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 违章描述
     */
    @Column(name = "VIOLATION_DESC", nullable = true, length = 500)
    public java.lang.String getViolationDesc() {
        return this.violationDesc;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 违章描述
     */
    public void setViolationDesc(java.lang.String violationDesc) {
        this.violationDesc = violationDesc;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 违章分数
     */
    @Column(name = "VIOLATION_SCORE", nullable = true, length = 5)
    public java.lang.Integer getViolationScore() {
        return this.violationScore;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 违章分数
     */
    public void setViolationScore(java.lang.Integer violationScore) {
        this.violationScore = violationScore;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 处罚金额
     */
    @Column(name = "FINES", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getFines() {
        return this.fines;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 处罚金额
     */
    public void setFines(java.math.BigDecimal fines) {
        this.fines = fines;
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
