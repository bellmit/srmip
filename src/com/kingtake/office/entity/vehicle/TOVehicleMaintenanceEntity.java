package com.kingtake.office.entity.vehicle;

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

/**
 * @Title: Entity
 * @Description: 车辆维护保养信息表
 * @author onlineGenerator
 * @date 2015-07-09 17:40:49
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_vehicle_maintenance", schema = "")
@SuppressWarnings("serial")
@LogEntity("车辆维护保养信息表")
public class TOVehicleMaintenanceEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 车辆主键 */
    @FieldDesc("车辆主键")
    @Excel(name = "车辆主键", isExportConvert = true, width = 15)
    private java.lang.String vehicleId;
    /** 类型 */
    @FieldDesc("类型")
    @Excel(name = "类型", isExportConvert = true)
    private java.lang.String opType;
    /** 维护保养时间 */
    @FieldDesc("维护保养时间")
    @Excel(name = "维护保养时间", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    private java.util.Date maintenanceTime;
    /** 维护保养事项 */
    @FieldDesc("维护保养事项")
    @Excel(name = "维护保养事项", width = 25)
    private java.lang.String maintenanceItems;
    /** 维护保养人id */
    @FieldDesc("维护保养人id")
    private java.lang.String maintainUserId;
    /** 维护保养人姓名 */
    @FieldDesc("维护保养人姓名")
    @Excel(name = "维护保养人")
    private java.lang.String maintainUserName;
    /** 维护保养费用 */
    @FieldDesc("维护保养费用")
    @Excel(name = "维护保养费用", width = 15,isAmount = true)
    private java.math.BigDecimal maintainUserPay;
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
     * @return: java.lang.String 类型
     */
    @Column(name = "OP_TYPE", nullable = true, length = 1)
    public java.lang.String getOpType() {
        return this.opType;
    }

    public java.lang.String convertGetOpType() {
        return ConvertDicUtil.getConvertDic("1", "WXBYLX", this.opType);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 类型
     */
    public void setOpType(java.lang.String opType) {
        this.opType = opType;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 维护保养时间
     */
    @Column(name = "MAINTENANCE_TIME", nullable = true)
    public java.util.Date getMaintenanceTime() {
        return this.maintenanceTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 维护保养时间
     */
    public void setMaintenanceTime(java.util.Date maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 维护保养事项
     */
    @Column(name = "MAINTENANCE_ITEMS", nullable = true, length = 2000)
    public java.lang.String getMaintenanceItems() {
        return this.maintenanceItems;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 维护保养事项
     */
    public void setMaintenanceItems(java.lang.String maintenanceItems) {
        this.maintenanceItems = maintenanceItems;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 维护保养人id
     */
    @Column(name = "MAINTAIN_USER_ID", nullable = true, length = 32)
    public java.lang.String getMaintainUserId() {
        return this.maintainUserId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 维护保养人id
     */
    public void setMaintainUserId(java.lang.String maintainUserId) {
        this.maintainUserId = maintainUserId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 维护保养人姓名
     */
    @Column(name = "MAINTAIN_USER_NAME", nullable = true, length = 36)
    public java.lang.String getMaintainUserName() {
        return this.maintainUserName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 维护保养人姓名
     */
    public void setMaintainUserName(java.lang.String maintainUserName) {
        this.maintainUserName = maintainUserName;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 维护保养费用
     */
    @Column(name = "MAINTAIN_USER_PAY", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getMaintainUserPay() {
        return this.maintainUserPay;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 维护保养费用
     */
    public void setMaintainUserPay(java.math.BigDecimal maintainUserPay) {
        this.maintainUserPay = maintainUserPay;
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
