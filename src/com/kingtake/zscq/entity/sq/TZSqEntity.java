package com.kingtake.zscq.entity.sq;

import java.math.BigDecimal;
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
@Table(name = "t_z_sq", schema = "")
@SuppressWarnings("serial")
public class TZSqEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
    /** 专利申请id */
    private java.lang.String zlsqId;
    /** 发文日 */
    private Date fwr;
    /** 登记费 */
    private BigDecimal djf;
    /**
     * 证书印花税
     */
    private BigDecimal zsyhs;
    /** 总金额 */
    private BigDecimal zje;
    /** 要求缴纳时间 */
    private Date yqjnsj;
    /** 附件编码 */
    private java.lang.String fjbm;

    /** 创建人 */
    @Excel(name = "创建人")
    private java.lang.String createBy;
    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @Excel(name = "创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    @Excel(name = "修改人")
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @Excel(name = "修改时间")
    private java.util.Date updateDate;

    /** 附件 */
    private List<TSFilesEntity> attachments;
	
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

    @Column(name = "ZLSQ_ID")
    public java.lang.String getZlsqId() {
        return zlsqId;
    }

    public void setZlsqId(java.lang.String zlsqId) {
        this.zlsqId = zlsqId;
    }

    @Column(name = "FWR")
    public Date getFwr() {
        return fwr;
    }

    public void setFwr(Date fwr) {
        this.fwr = fwr;
    }

    @Column(name = "DJF")
    public BigDecimal getDjf() {
        return djf;
    }

    public void setDjf(BigDecimal djf) {
        this.djf = djf;
    }

    @Column(name = "ZSYHS")
    public BigDecimal getZsyhs() {
        return zsyhs;
    }

    public void setZsyhs(BigDecimal zsyhs) {
        this.zsyhs = zsyhs;
    }

    @Column(name = "ZJE")
    public BigDecimal getZje() {
        return zje;
    }

    public void setZje(BigDecimal zje) {
        this.zje = zje;
    }

    @Column(name = "YQJNSJ")
    public Date getYqjnsj() {
        return yqjnsj;
    }

    public void setYqjnsj(Date yqjnsj) {
        this.yqjnsj = yqjnsj;
    }

    @Column(name = "FJBM")
    public java.lang.String getFjbm() {
        return fjbm;
    }

    public void setFjbm(java.lang.String fjbm) {
        this.fjbm = fjbm;
    }

    @Column(name = "CREATE_BY")
    public java.lang.String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_NAME")
    public java.lang.String getCreateName() {
        return createName;
    }

    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    @Column(name = "CREATE_DATE")
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_BY")
    public java.lang.String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "UPDATE_NAME")
    public java.lang.String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "UPDATE_DATE")
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public List<TSFilesEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TSFilesEntity> attachments) {
        this.attachments = attachments;
    }
}
