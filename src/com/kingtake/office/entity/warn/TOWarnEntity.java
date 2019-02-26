package com.kingtake.office.entity.warn;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.constant.SrmipConstants;

/**   
 * @Title: Entity
 * @Description: 公用提醒信息表
 * @author onlineGenerator
 * @date 2015-07-15 15:47:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_warn")
@SuppressWarnings("serial")
@LogEntity("公共提醒")
public class TOWarnEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
    /** 提醒内容 */
    @Excel(name = "提醒内容")
    @FieldDesc("提醒内容")
    private java.lang.String warnContent;
	/**类型*/
    @Excel(name = "类型", isExportConvert=true)
    @FieldDesc("类型")
	private java.lang.String warnType;
	/**关联业务url*/
    //@Excel(name = "关联业务url")
    @FieldDesc("关联业务url")
	private java.lang.String formUrl;
    /** 提醒时限开始时间 */
    @Excel(name = "提醒时限开始时间")
    @FieldDesc("提醒时限开始时间")
	private java.util.Date warnBeginTime;
    /** 提醒时限结束时间 */
    @Excel(name = "提醒时限结束时间")
    @FieldDesc("提醒时限结束时间")
	private java.util.Date warnEndTime;
    /** 提醒状态，0 未完成，1 已完成，2 作废 */
    @Excel(name = "提醒状态", isExportConvert=true)
    @FieldDesc("提醒状态")
	private java.lang.String warnStatus;
    /** 提醒频率 1 一次 2 每天 3 工作日 */
    @Excel(name = "提醒频率", isExportConvert=true)
    @FieldDesc("提醒频率")
	private java.lang.String warnFrequency;

    /**
     * 接收人id列表
     */
    private String receiveUserids;
    /**
     * 
     * 提醒时间点
     */
    @Excel(name = "提醒时间点")
    @FieldDesc("提醒时间点")
    private String warnTimePoint;
    /** 提醒方式 */
    @Excel(name = "提醒方式", isExportConvert = true)
    @FieldDesc("提醒方式")
    private java.lang.String warnWay;
	/**创建人*/
	private java.lang.String createBy;
	/**创建人姓名*/
	private java.lang.String createName;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改人*/
	private java.lang.String updateBy;
	/**修改人姓名*/
	private java.lang.String updateName;
	/**修改时间*/
	private java.util.Date updateDate;

    /**
     * 来源id
     */
    private String sourceId;

	
    /**
     * 接收人列表
     */
    //@ExcelCollection(name = "接收人列表")
    private List<TOWarnReceiveEntity> receiveList;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	
	@Column(name ="WARN_TYPE",nullable=true,length=2)
	public java.lang.String getWarnType(){
		return this.warnType;
	}
	
	public java.lang.String convertGetWarnType(){
		if(StringUtil.isNotEmpty(this.warnType)){
			Map<String, Object> map = SrmipConstants.dicStandardMap.get("WARNTYPE");
			if(map != null){
				Object obj = map.get(this.warnType);
				if(obj != null){
					return obj.toString();
				}
			}
		}
		return this.warnType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setWarnType(java.lang.String warnType){
		this.warnType = warnType;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒内容
	 */
	
	@Column(name ="WARN_CONTENT",nullable=true,length=200)
	public java.lang.String getWarnContent(){
		return this.warnContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒内容
	 */
	public void setWarnContent(java.lang.String warnContent){
		this.warnContent = warnContent;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联业务url
	 */
	
	@Column(name ="FORM_URL",nullable=true,length=300)
	public java.lang.String getFormUrl(){
		return this.formUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联业务url
	 */
	public void setFormUrl(java.lang.String formUrl){
		this.formUrl = formUrl;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始提醒时间
	 */
	
	@Column(name ="WARN_BEGIN_TIME",nullable=true)
	public java.util.Date getWarnBeginTime(){
		return this.warnBeginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始提醒时间
	 */
	public void setWarnBeginTime(java.util.Date warnBeginTime){
		this.warnBeginTime = warnBeginTime;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束提醒时间
	 */
	
	@Column(name ="WARN_END_TIME",nullable=true)
	public java.util.Date getWarnEndTime(){
		return this.warnEndTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束提醒时间
	 */
	public void setWarnEndTime(java.util.Date warnEndTime){
		this.warnEndTime = warnEndTime;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒状态
	 */
	
	@Column(name ="WARN_STATUS",nullable=true,length=1)
	public java.lang.String getWarnStatus(){
		return this.warnStatus;
	}
	
	public java.lang.String convertGetWarnStatus(){
		if(StringUtil.isNotEmpty(this.warnStatus)){
			Map<String, Object> map = SrmipConstants.dicStandardMap.get("WARNSTATUS");
			if(map != null){
				Object obj = map.get(this.warnStatus);
				if(obj != null){
					return obj.toString();
				}
			}
		}
		return this.warnStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒状态
	 */
	public void setWarnStatus(java.lang.String warnStatus){
		this.warnStatus = warnStatus;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提醒频率
	 */
	
	@Column(name ="WARN_FREQUENCY",nullable=true,length=2)
	public java.lang.String getWarnFrequency(){
		return this.warnFrequency;
	}
	
	public java.lang.String convertGetWarnFrequency(){
		if(StringUtil.isNotEmpty(this.warnFrequency)){
			Map<String, Object> map = SrmipConstants.dicStandardMap.get("WARNFREQUENCY");
			if(map != null){
				Object obj = map.get(this.warnFrequency);
				if(obj != null){
					return obj.toString();
				}
			}
		}
		return this.warnFrequency;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提醒频率
	 */
	public void setWarnFrequency(java.lang.String warnFrequency){
		this.warnFrequency = warnFrequency;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人姓名
	 */
	
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人姓名
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人姓名
	 */
	
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人姓名
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "toWarn")
    public List<TOWarnReceiveEntity> getReceiveList() {
        return receiveList;
    }

    public void setReceiveList(List<TOWarnReceiveEntity> receiveList) {
        this.receiveList = receiveList;
    }

    @Column(name = "WARN_TIME_POINT")
    public String getWarnTimePoint() {
        return warnTimePoint;
    }

    public void setWarnTimePoint(String warnTimePoint) {
        this.warnTimePoint = warnTimePoint;
    }

    @Column(name = "WARN_WAY")
    public java.lang.String getWarnWay() {
        return warnWay;
    }
    
    public java.lang.String convertGetWarnWay(){
		if(StringUtil.isNotEmpty(this.warnWay)){
			Map<String, Object> map = SrmipConstants.dicStandardMap.get("WARNWAY");
			if(map != null){
				Object obj = map.get(this.warnWay);
				if(obj != null){
					return obj.toString();
				}
			}
		}
		return this.warnWay;
	}

    public void setWarnWay(java.lang.String warnWay) {
        this.warnWay = warnWay;
    }

    @Transient
    public String getReceiveUserids() {
        return receiveUserids;
    }

    public void setReceiveUserids(String receiveUserids) {
        this.receiveUserids = receiveUserids;
    }

    @Column(name = "source_id")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
	

}
