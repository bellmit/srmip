package com.kingtake.zscq.entity.zlsq;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**   
 * @Title: Entity
 * @Description: T_Z_ZLSQ
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_z_zlsq", schema = "")
@SuppressWarnings("serial")
public class TZZlsqEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**归档号*/
	@Excel(name="归档号")
	private java.lang.String gdh;
	/**完成单位*/
    private java.lang.String wcdwId;
    /** 完成单位 */
	@Excel(name="完成单位")
	private java.lang.String wcdw;
	/**类型*/
	@Excel(name="类型")
	private java.lang.String lx;
	/**关联项目*/
	@Excel(name="关联项目")
	private java.lang.String glxm;
    /** 关联项目id */
    private java.lang.String glxmId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String mc;
	/**发明人*/
	@Excel(name="发明人")
	private java.lang.String fmr;
	/**第一发明人身份证号*/
	@Excel(name="第一发明人身份证号")
	private java.lang.String dyfmrsfzh;
	/**代理机构*/
	@Excel(name="代理机构")
	private java.lang.String dljgId;
	/**联系人*/
	@Excel(name="联系人")
	private java.lang.String lxr;
	/**联系人电话*/
	@Excel(name="联系人电话")
	private java.lang.String lxrdh;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String bz;
	/**附件*/
	@Excel(name="附件")
	private java.lang.String fjbm;
    /** 状态 */
    private java.lang.String zlzt;
	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createBy;
	/**创建人姓名*/
	@Excel(name="创建人姓名")
	private java.lang.String createName;
	/**创建时间*/
	@Excel(name="创建时间")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人")
	private java.lang.String updateBy;
	/**修改人姓名*/
	@Excel(name="修改人姓名")
	private java.lang.String updateName;
	/**修改时间*/
	@Excel(name="修改时间")
	private java.util.Date updateDate;
	
    /** 附件 */
    private List<TSFilesEntity> attachments;

    /**
     * 审批状态
     */
    private String apprStatus;

    /**
     * 审批完成时间
     */
    private Date apprFinishTime;

    /**
     * 审批完成操作人id
     */
    private String apprFinishUserId;

    /**
     * 审批完成操作人名称
     */
    private String apprFinishUserName;

    /**
     * 文件状态
     */
    private String wjzt;


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
	 *@return: java.lang.String  归档号
	 */
	@Column(name ="GDH",nullable=true,length=50)
	public java.lang.String getGdh(){
		return this.gdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  归档号
	 */
	public void setGdh(java.lang.String gdh){
		this.gdh = gdh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  完成单位
	 */
	@Column(name ="WCDW",nullable=true,length=32)
	public java.lang.String getWcdw(){
		return this.wcdw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  完成单位
	 */
	public void setWcdw(java.lang.String wcdw){
		this.wcdw = wcdw;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */
	@Column(name ="LX",nullable=true,length=2)
	public java.lang.String getLx(){
		return this.lx;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setLx(java.lang.String lx){
		this.lx = lx;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联项目
	 */
	@Column(name ="GLXM",nullable=true,length=32)
	public java.lang.String getGlxm(){
		return this.glxm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联项目
	 */
	public void setGlxm(java.lang.String glxm){
		this.glxm = glxm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="MC",nullable=true,length=200)
	public java.lang.String getMc(){
		return this.mc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setMc(java.lang.String mc){
		this.mc = mc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发明人
	 */
	@Column(name ="FMR",nullable=true,length=1000)
	public java.lang.String getFmr(){
		return this.fmr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发明人
	 */
	public void setFmr(java.lang.String fmr){
		this.fmr = fmr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  第一发明人身份证号
	 */
	@Column(name ="DYFMRSFZH",nullable=true,length=20)
	public java.lang.String getDyfmrsfzh(){
		return this.dyfmrsfzh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  第一发明人身份证号
	 */
	public void setDyfmrsfzh(java.lang.String dyfmrsfzh){
		this.dyfmrsfzh = dyfmrsfzh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理机构
	 */
	@Column(name ="DLJG_ID",nullable=true,length=32)
	public java.lang.String getDljgId(){
		return this.dljgId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理机构
	 */
	public void setDljgId(java.lang.String dljgId){
		this.dljgId = dljgId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="LXR",nullable=true,length=20)
	public java.lang.String getLxr(){
		return this.lxr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setLxr(java.lang.String lxr){
		this.lxr = lxr;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人电话
	 */
	@Column(name ="LXRDH",nullable=true,length=50)
	public java.lang.String getLxrdh(){
		return this.lxrdh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人电话
	 */
	public void setLxrdh(java.lang.String lxrdh){
		this.lxrdh = lxrdh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BZ",nullable=true,length=4000)
	public java.lang.String getBz(){
		return this.bz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBz(java.lang.String bz){
		this.bz = bz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附件
	 */
	@Column(name ="FJBM",nullable=true,length=32)
	public java.lang.String getFjbm(){
		return this.fjbm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附件
	 */
	public void setFjbm(java.lang.String fjbm){
		this.fjbm = fjbm;
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

    @Column(name = "zlzt")
    public java.lang.String getZlzt() {
        return zlzt;
    }

    public void setZlzt(java.lang.String zlzt) {
        this.zlzt = zlzt;
    }

    @Column(name = "glxm_id")
    public java.lang.String getGlxmId() {
        return glxmId;
    }

    public void setGlxmId(java.lang.String glxmId) {
        this.glxmId = glxmId;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name = "appr_status")
    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    @Column(name = "appr_finish_time")
    public Date getApprFinishTime() {
        return apprFinishTime;
    }

    public void setApprFinishTime(Date apprFinishTime) {
        this.apprFinishTime = apprFinishTime;
    }

    @Column(name = "appr_finish_appr_user_id")
    public String getApprFinishUserId() {
        return apprFinishUserId;
    }

    public void setApprFinishUserId(String apprFinishUserId) {
        this.apprFinishUserId = apprFinishUserId;
    }

    @Column(name = "appr_finish_appr_user_name")
    public String getApprFinishUserName() {
        return apprFinishUserName;
    }

    public void setApprFinishUserName(String apprFinishUserName) {
        this.apprFinishUserName = apprFinishUserName;
    }

    @Column(name = "wcdw_id")
    public java.lang.String getWcdwId() {
        return wcdwId;
    }

    public void setWcdwId(java.lang.String wcdwId) {
        this.wcdwId = wcdwId;
    }

    @Transient
    public String getWjzt() {
        return wjzt;
    }

    public void setWjzt(String wjzt) {
        this.wjzt = wjzt;
    }

}
