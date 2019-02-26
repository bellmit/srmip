package com.kingtake.office.entity.meeting;

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
 * @Description: 会议室
 * @author onlineGenerator
 * @date 2015-07-02 19:17:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_meeting_room", schema = "")
@SuppressWarnings("serial")
@LogEntity("会议室管理")
public class TOMeetingRoomEntity implements java.io.Serializable {
	/**主键*/
    @FieldDesc("主键")
	private java.lang.String id;
	/**会议室名称*/
	@Excel(name="会议室名称", width = 15)
	@FieldDesc("会议室名称" )
	private java.lang.String roomName;
	/**会议室编号*/
	@FieldDesc("会议室编号")
	private java.lang.String roomNum;
	/**所属单位id*/
	@FieldDesc("所属单位id")
	private java.lang.String departId;
	/**所属单位名称*/
	@Excel(name="所属单位名称", width = 15)
	@FieldDesc("所属单位名称")
	private java.lang.String departName;
	/**最大容纳人数*/
	@Excel(name="最大容纳人数", width = 15)
	@FieldDesc("最大容纳人数")
	private java.lang.Integer maxnum;
	/**联系人*/
	@Excel(name="联系人", width = 15)
	@FieldDesc("联系人")
	private java.lang.String contact;
	/**联系电话*/
	@Excel(name="联系电话", width = 20)
	@FieldDesc("联系电话")
	private java.lang.String phone;
	/**地点*/
	@Excel(name="地点", width = 30)
	@FieldDesc("地点")
	private java.lang.String address;
	/**备注*/
	@FieldDesc("备注")
	private java.lang.String memo;
	/** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    private java.lang.String createName;
    /** 创建时间 */
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;
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
	 *@return: java.lang.String  会议室名称
	 */
	@Column(name ="ROOM_NAME",nullable=false,length=30)
	public java.lang.String getRoomName(){
		return this.roomName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会议室名称
	 */
	public void setRoomName(java.lang.String roomName){
		this.roomName = roomName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  会议室编号
	 */
	@Column(name ="ROOM_NUM",nullable=true,length=20)
	public java.lang.String getRoomNum(){
		return this.roomNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会议室编号
	 */
	public void setRoomNum(java.lang.String roomNum){
		this.roomNum = roomNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属单位id
	 */
	@Column(name ="DEPART_ID",nullable=true,length=32)
	public java.lang.String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属单位id
	 */
	public void setDepartId(java.lang.String departId){
		this.departId = departId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属单位名称
	 */
	@Column(name ="DEPART_NAME",nullable=true,length=60)
	public java.lang.String getDepartName(){
		return this.departName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属单位名称
	 */
	public void setDepartName(java.lang.String departName){
		this.departName = departName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  最大容纳人数
	 */
	@Column(name ="MAXNUM",nullable=true,length=6)
	public java.lang.Integer getMaxnum(){
		return this.maxnum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  最大容纳人数
	 */
	public void setMaxnum(java.lang.Integer maxnum){
		this.maxnum = maxnum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACT",nullable=true,length=50)
	public java.lang.String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContact(java.lang.String contact){
		this.contact = contact;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="PHONE",nullable=true,length=50)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地点
	 */
	@Column(name ="ADDRESS",nullable=true,length=60)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地点
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="MEMO",nullable=true,length=200)
	public java.lang.String getMemo(){
		return this.memo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setMemo(java.lang.String memo){
		this.memo = memo;
	}

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创建人
     */
    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 创建人
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 创建人姓名
     */
    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 创建人姓名
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 创建时间
     */
    @Column(name = "CREATE_DATE", nullable = true)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 创建时间
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 修改人
     */
    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 修改人
     */
    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 修改人姓名
     */
    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 修改人姓名
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 修改时间
     */
    @Column(name = "UPDATE_DATE", nullable = true)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 修改时间
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }
}
