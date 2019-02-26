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
 * @Description: 车辆使用费用信息表
 * @author onlineGenerator
 * @date 2015-07-09 20:14:05
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_vehicle_expense", schema = "")
@SuppressWarnings("serial")
@LogEntity("车辆使用费用信息表")
public class TOVehicleExpenseEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 车辆主键 */
    @FieldDesc("车辆主键")
    @Excel(name = "车牌号", isExportConvert = true, width = 15)
    private java.lang.String vehicleId;
    /** 车辆使用登记表id */
    @FieldDesc("车辆使用登记表id")
    @Excel(name = "关联使用信息", isExportConvert = true, width = 20)
    private java.lang.String useId;
    /** 缴费人员id */
    @FieldDesc("缴费人员id")
    private java.lang.String payerId;
    /** 缴费人员姓名 */
    @FieldDesc("缴费人员姓名")
    @Excel(name = "缴费人员")
    private java.lang.String payerName;
    /** 缴费时间 */
    @FieldDesc("缴费时间")
    @Excel(name = "缴费时间", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    private java.util.Date payTime;
    /** 费用类型 */
    @FieldDesc("费用类型")
    @Excel(name = "费用类型", isExportConvert = true)
    private java.lang.String expenseType;
    /** 加油量 */
    @FieldDesc("加油量")
    @Excel(name = "加油量",isAmount = true)
    private java.math.BigDecimal fuelCharge;
    /** 缴费类型 */
    @FieldDesc("缴费类型")
    @Excel(name = "缴费类型", isExportConvert = true)
    private java.lang.String payType;
    /** 金额 */
    @FieldDesc("金额")
    @Excel(name = "金额",isAmount = true)
    private java.math.BigDecimal money;
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
     * @return: java.lang.String 费用类型
     */
    @Column(name = "EXPENSE_TYPE", nullable = true, length = 2)
    public java.lang.String getExpenseType() {
        return this.expenseType;
    }

    public java.lang.String convertGetExpenseType() {
        return ConvertDicUtil.getConvertDic("1", "FYLX", this.expenseType);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 费用类型
     */
    public void setExpenseType(java.lang.String expenseType) {
        this.expenseType = expenseType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 缴费类型
     */
    @Column(name = "PAY_TYPE", nullable = true, length = 1)
    public java.lang.String getPayType() {
        return this.payType;
    }

    public java.lang.String convertGetPayType() {
        return ConvertDicUtil.getConvertDic("1", "JYJFLX", this.payType);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 缴费类型
     */
    public void setPayType(java.lang.String payType) {
        this.payType = payType;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 缴费时间
     */
    @Column(name = "PAY_TIME", nullable = true)
    public java.util.Date getPayTime() {
        return this.payTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 缴费时间
     */
    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 加油量
     */
    @Column(name = "FUEL_CHARGE", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getFuelCharge() {
        return this.fuelCharge;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 加油量
     */
    public void setFuelCharge(java.math.BigDecimal fuelCharge) {
        this.fuelCharge = fuelCharge;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal 金额
     */
    @Column(name = "MONEY", nullable = true, scale = 2, length = 10)
    public java.math.BigDecimal getMoney() {
        return this.money;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal 金额
     */
    public void setMoney(java.math.BigDecimal money) {
        this.money = money;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 缴费人员id
     */
    @Column(name = "PAYER_ID", nullable = true, length = 32)
    public java.lang.String getPayerId() {
        return this.payerId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 缴费人员id
     */
    public void setPayerId(java.lang.String payerId) {
        this.payerId = payerId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 缴费人员姓名
     */
    @Column(name = "PAYER_NAME", nullable = true, length = 36)
    public java.lang.String getPayerName() {
        return this.payerName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 缴费人员姓名
     */
    public void setPayerName(java.lang.String payerName) {
        this.payerName = payerName;
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
