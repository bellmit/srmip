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
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Entity
 * @Description: 车辆使用登记信息表
 * @author onlineGenerator
 * @date 2015-07-08 17:33:46
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_vehicle_use", schema = "")
@SuppressWarnings("serial")
@LogEntity("车辆使用登记信息表")
public class TOVehicleUseEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键 ")
    private java.lang.String id;
    /** 车辆主键 */
    @FieldDesc("车辆主键")
    @Excel(name = "车牌号", isExportConvert = true, width = 15)
    private java.lang.String vehicleId;
    /** 司机id */
    @FieldDesc("司机id")
    private java.lang.String driverId;
    /** 司机姓名 */
    @FieldDesc("司机姓名")
    @Excel(name = "司机")
    private java.lang.String driverName;
    /** 用车人id */
    @FieldDesc("用车人id")
    private java.lang.String useId;
    /** 用车人姓名 */
    @FieldDesc("用车人姓名")
    @Excel(name = "用车人")
    private java.lang.String useName;
    /** 申请时间 */
    @FieldDesc("申请时间")
    @Excel(name = "申请时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date applyTime;
    /** 批准人id */
    @FieldDesc("批准人id")
    private java.lang.String approverId;
    /** 批准人姓名 */
    @FieldDesc("批准人姓名")
    @Excel(name = "批准人")
    private java.lang.String approverName;
    /** 批准时间 */
    @FieldDesc("批准时间")
    @Excel(name = "批准时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date approverTime;
    /** 关联项目id */
    @FieldDesc("关联项目id")
    @Excel(name = "关联项目", isExportConvert = true)
    private java.lang.String projectId;
    /** 到达地点 */
    @FieldDesc("到达地点")
    @Excel(name = "到达地点")
    private java.lang.String acheivePlace;
    /** 出车事由 */
    @FieldDesc("出车事由")
    @Excel(name = "出车事由")
    private java.lang.String outReason;
    /** 行驶里程 */
    @FieldDesc("行驶里程")
    @Excel(name = "行驶里程")
    private java.math.BigDecimal driverDistance;
    /** 油耗 */
    @FieldDesc("油耗")
    @Excel(name = "油耗")
    private java.math.BigDecimal fuelUse;
    /** 出场时间 */
    @FieldDesc("出场时间")
    @Excel(name = "出场时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date outTime;
    /** 回场时间 */
    @FieldDesc("回场时间")
    @Excel(name = "回场时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date backTime;
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注")
    private java.lang.String remark;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 用车人姓名
     */
    @Column(name = "USE_NAME", nullable = true, length = 36)
    public java.lang.String getUseName() {
        return this.useName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 用车人姓名
     */
    public void setUseName(java.lang.String useName) {
        this.useName = useName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 申请时间
     */
    @Column(name = "APPLY_TIME", nullable = true)
    public java.util.Date getApplyTime() {
        return this.applyTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 申请时间
     */
    public void setApplyTime(java.util.Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 批准人id
     */
    @Column(name = "APPROVER_ID", nullable = true, length = 32)
    public java.lang.String getApproverId() {
        return this.approverId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 批准人id
     */
    public void setApproverId(java.lang.String approverId) {
        this.approverId = approverId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 批准人姓名
     */
    @Column(name = "APPROVER_NAME", nullable = true, length = 36)
    public java.lang.String getApproverName() {
        return this.approverName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 批准人姓名
     */
    public void setApproverName(java.lang.String approverName) {
        this.approverName = approverName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 批准时间
     */
    @Column(name = "APPROVER_TIME", nullable = true)
    public java.util.Date getApproverTime() {
        return this.approverTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 批准时间
     */
    public void setApproverTime(java.util.Date approverTime) {
        this.approverTime = approverTime;
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
     * @return: java.lang.String 到达地点
     */
    @Column(name = "ACHEIVE_PLACE", nullable = true, length = 150)
    public java.lang.String getAcheivePlace() {
        return this.acheivePlace;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 到达地点
     */
    public void setAcheivePlace(java.lang.String acheivePlace) {
        this.acheivePlace = acheivePlace;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 行驶里程
     */
    @Column(name = "DRIVER_DISTANCE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getDriverDistance() {
        return this.driverDistance;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 行驶里程
     */
    public void setDriverDistance(java.math.BigDecimal driverDistance) {
        this.driverDistance = driverDistance;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 油耗
     */
    @Column(name = "FUEL_USE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getFuelUse() {
        return this.fuelUse;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 油耗
     */
    public void setFuelUse(java.math.BigDecimal fuelUse) {
        this.fuelUse = fuelUse;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 出场时间
     */
    @Column(name = "OUT_TIME", nullable = true)
    public java.util.Date getOutTime() {
        return this.outTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 出场时间
     */
    public void setOutTime(java.util.Date outTime) {
        this.outTime = outTime;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 回场时间
     */
    @Column(name = "BACK_TIME", nullable = true)
    public java.util.Date getBackTime() {
        return this.backTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 回场时间
     */
    public void setBackTime(java.util.Date backTime) {
        this.backTime = backTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 出车事由
     */
    @Column(name = "OUT_REASON", nullable = true, length = 800)
    public java.lang.String getOutReason() {
        return this.outReason;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 出车事由
     */
    public void setOutReason(java.lang.String outReason) {
        this.outReason = outReason;
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
     * @return: java.lang.String 车辆主键
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
     * @param: java.lang.String 车辆主键
     */
    public void setVehicleId(java.lang.String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联项目id
     */
    @Column(name = "PROJECT_ID", nullable = true, length = 32)
    public java.lang.String getProjectId() {
        return this.projectId;
    }

    public java.lang.String convertGetProjectId() {
        if (!StringUtil.isNotEmpty(this.projectId)) {
            return "";
        }
        systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        TPmProjectEntity tPmProject = systemService.get(TPmProjectEntity.class, this.projectId);
        if (tPmProject != null) {
            return tPmProject.getProjectName();
        } else {
            return "";
        }
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联项目id
     */
    public void setProjectId(java.lang.String projectId) {
        this.projectId = projectId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 用车人id
     */
    @Column(name = "USE_ID", nullable = true, length = 32)
    public java.lang.String getUseId() {
        return this.useId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 用车人id
     */
    public void setUseId(java.lang.String useId) {
        this.useId = useId;
    }
}
