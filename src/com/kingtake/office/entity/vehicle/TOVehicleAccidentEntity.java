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
 * @Description: 车辆事故信息表
 * @author onlineGenerator
 * @date 2015-07-10 17:12:49
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_vehicle_accident", schema = "")
@SuppressWarnings("serial")
@LogEntity("车辆事故信息表")
public class TOVehicleAccidentEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    private java.lang.String id;
    /** 车辆主键 */
    @Excel(name = "车牌号", isExportConvert = true, width = 15)
    @FieldDesc("车辆主键")
    private java.lang.String vehicleId;
    /** 车辆使用登记表id */
    @Excel(name = "关联使用信息", isExportConvert = true, width = 20)
    @FieldDesc("车辆使用登记表id")
    private java.lang.String useId;
    /** 事故人id */
    @FieldDesc("事故人id")
    private java.lang.String personId;
    /** 事故人姓名 */
    @Excel(name = "事故人姓名")
    @FieldDesc("事故人")
    private java.lang.String personName;
    /** 事故发生时间 */
    @FieldDesc("事故发生时间")
    @Excel(name = "事故发生时间", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    private java.util.Date occurTime;
    /** 事故发生地点 */
    @FieldDesc("事故发生地点")
    @Excel(name = "事故发生地点", width = 20)
    private java.lang.String occurAddress;
    /** 交警处理结果 */
    @FieldDesc("交警处理结果")
    @Excel(name = "交警处理结果", width = 20)
    private java.lang.String result;
    /** 处理方式 */
    @FieldDesc("处理方式")
    @Excel(name = "处理方式", isExportConvert = true)
    private java.lang.String handleMode;
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
     * @return: java.lang.String 事故人id
     */
    @Column(name = "PERSON_ID", nullable = true, length = 32)
    public java.lang.String getPersonId() {
        return this.personId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 事故人id
     */
    public void setPersonId(java.lang.String personId) {
        this.personId = personId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 事故人姓名
     */
    @Column(name = "PERSON_NAME", nullable = true, length = 36)
    public java.lang.String getPersonName() {
        return this.personName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 事故人姓名
     */
    public void setPersonName(java.lang.String personName) {
        this.personName = personName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 事故发生时间
     */
    @Column(name = "OCCUR_TIME", nullable = true)
    public java.util.Date getOccurTime() {
        return this.occurTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 事故发生时间
     */
    public void setOccurTime(java.util.Date occurTime) {
        this.occurTime = occurTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 事故发生地点
     */
    @Column(name = "OCCUR_ADDRESS", nullable = true, length = 60)
    public java.lang.String getOccurAddress() {
        return this.occurAddress;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 事故发生地点
     */
    public void setOccurAddress(java.lang.String occurAddress) {
        this.occurAddress = occurAddress;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 交警处理结果
     */
    @Column(name = "RESULT", nullable = true, length = 150)
    public java.lang.String getResult() {
        return this.result;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 交警处理结果
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 处理方式
     */
    @Column(name = "HANDLE_MODE", nullable = true, length = 1)
    public java.lang.String getHandleMode() {
        return this.handleMode;
    }

    public java.lang.String convertGetHandleMode() {
        return ConvertDicUtil.getConvertDic("1", "CLFS", this.handleMode);
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 处理方式
     */
    public void setHandleMode(java.lang.String handleMode) {
        this.handleMode = handleMode;
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
