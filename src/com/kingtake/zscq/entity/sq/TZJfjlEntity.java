package com.kingtake.zscq.entity.sq;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: T_Z_ZLSQ
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_z_jfjl", schema = "")
@SuppressWarnings("serial")
public class TZJfjlEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
    /** 授权id */
    private java.lang.String sqId;
    /** 缴纳方式,1 发明人缴纳 2 办公室缴纳 */
    private java.lang.String jnfs;
    /** 缴纳金额 */
    private BigDecimal jnje;
    /** 费用审批时间 */
    private Date fyspsj;
    /** 汇款人账号 */
    private java.lang.String hkrzh;
    /** 汇款人id */
    private java.lang.String hkrId;
    /** 汇款人名称 */
    private java.lang.String hkrmc;
    /** 联系方式 */
    private java.lang.String lxfs;

    /**
     * 报销凭证上交时间
     */
    private Date bxpzsjsj;

    /**
     * 初始录入标记
     */
    private String csbj;

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

    @Column(name = "SQ_ID")
    public java.lang.String getSqId() {
        return sqId;
    }

    public void setSqId(java.lang.String sqId) {
        this.sqId = sqId;
    }

    @Column(name = "JNFS")
    public java.lang.String getJnfs() {
        return jnfs;
    }

    public void setJnfs(java.lang.String jnfs) {
        this.jnfs = jnfs;
    }

    @Column(name = "JNJE")
    public BigDecimal getJnje() {
        return jnje;
    }

    public void setJnje(BigDecimal jnje) {
        this.jnje = jnje;
    }

    @Column(name = "FYSPSJ")
    public Date getFyspsj() {
        return fyspsj;
    }

    public void setFyspsj(Date fyspsj) {
        this.fyspsj = fyspsj;
    }

    @Column(name = "HKRZH")
    public java.lang.String getHkrzh() {
        return hkrzh;
    }

    public void setHkrzh(java.lang.String hkrzh) {
        this.hkrzh = hkrzh;
    }

    @Column(name = "HKR_ID")
    public java.lang.String getHkrId() {
        return hkrId;
    }

    public void setHkrId(java.lang.String hkrId) {
        this.hkrId = hkrId;
    }

    @Column(name = "HKRMC")
    public java.lang.String getHkrmc() {
        return hkrmc;
    }

    public void setHkrmc(java.lang.String hkrmc) {
        this.hkrmc = hkrmc;
    }

    @Column(name = "LXFS")
    public java.lang.String getLxfs() {
        return lxfs;
    }

    public void setLxfs(java.lang.String lxfs) {
        this.lxfs = lxfs;
    }

    @Column(name = "csbj")
    public String getCsbj() {
        return csbj;
    }

    public void setCsbj(String csbj) {
        this.csbj = csbj;
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

    @Column(name = "bxpzsjsj")
    public Date getBxpzsjsj() {
        return bxpzsjsj;
    }

    public void setBxpzsjsj(Date bxpzsjsj) {
        this.bxpzsjsj = bxpzsjsj;
    }

}
