package com.kingtake.zscq.entity.sctzs;

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
 * @Description: 审查通知书
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_z_sctzs", schema = "")
@SuppressWarnings("serial")
public class TZSctzsEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;

    /**
     * 专利申请id
     */
    private String zlsqId;

    /**
     * 通知类型
     */
    private String tzlx;

    /**
     * 发文日
     */
    private Date fwr;

    /**
     * 要求回复时间
     */
    private Integer yqhfsj;

    /**
     * 附件编码
     */
    private String fjbm;

    /**
     * 是否放弃，0 否，1 是
     */
    private String sffq;

    /**
     * 放弃原因
     */
    private String fqyy;

    /**
     * 上传意见
     */
    private String scyj;

    /**
     * 修改意见
     */
    private String xgyj;

    /**
     * 确认状态,0 带生成，1 待回复，2 已回复，3 返回修改，4 已确认
     */
    private String qrzt;

    /**
     * 附件列表
     */
    private List<TSFilesEntity> attachments;

    /**
     * 意见附件列表
     */
    private List<TSFilesEntity> yjattachments;

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

    @Column(name = "zlsq_id")
    public String getZlsqId() {
        return zlsqId;
    }

    public void setZlsqId(String zlsqId) {
        this.zlsqId = zlsqId;
    }


    @Column(name = "fwr")
    public Date getFwr() {
        return fwr;
    }

    public void setFwr(Date fwr) {
        this.fwr = fwr;
    }

    @Column(name = "fjbm")
    public String getFjbm() {
        return fjbm;
    }

    public void setFjbm(String fjbm) {
        this.fjbm = fjbm;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }

    @Column(name = "TZLX")
    public String getTzlx() {
        return tzlx;
    }

    public void setTzlx(String tzlx) {
        this.tzlx = tzlx;
    }

    @Column(name = "YQHFSJ")
    public Integer getYqhfsj() {
        return yqhfsj;
    }

    public void setYqhfsj(Integer yqhfsj) {
        this.yqhfsj = yqhfsj;
    }

    @Column(name = "SFFQ")
    public String getSffq() {
        return sffq;
    }

    public void setSffq(String sffq) {
        this.sffq = sffq;
    }

    @Column(name = "FQYY")
    public String getFqyy() {
        return fqyy;
    }

    public void setFqyy(String fqyy) {
        this.fqyy = fqyy;
    }

    @Column(name = "SCYJ")
    public String getScyj() {
        return scyj;
    }

    public void setScyj(String scyj) {
        this.scyj = scyj;
    }

    @Column(name = "XGYJ")
    public String getXgyj() {
        return xgyj;
    }

    public void setXgyj(String xgyj) {
        this.xgyj = xgyj;
    }

    @Column(name = "QRZT")
    public String getQrzt() {
        return qrzt;
    }

    public void setQrzt(String qrzt) {
        this.qrzt = qrzt;
    }

    @Transient
    public List<TSFilesEntity> getYjattachments() {
        return yjattachments;
    }

    public void setYjattachments(List<TSFilesEntity> yjattachments) {
        this.yjattachments = yjattachments;
    }

}
