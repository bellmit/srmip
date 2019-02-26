package com.kingtake.zscq.entity.sqrxx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 申请人信息
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_z_sqrxx", schema = "")
@SuppressWarnings("serial")
public class TZSqrxxEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
    /**
     * 专利申请id
     */
    private String zlsqId;
    /**
     * 关联系统用户id
     */
    private String userId;
    /** 姓名 */
    @Excel(name = "姓名")
    private java.lang.String xm;
    /** 电话 */
    @Excel(name = "电话")
    private java.lang.String dh;
    /** 居民身份证件号码 */
    @Excel(name = "居民身份证件号码")
    private java.lang.String idno;
    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private java.lang.String dzyx;
    /** 国籍 */
    @Excel(name = "国籍")
    private java.lang.String gj;
    /** 居所地 */
    @Excel(name = "居所地")
    private java.lang.String jsd;
    /** 邮政编码 */
    @Excel(name = "邮政编码")
    private java.lang.String yzbm;
    /** 详细地址 */
    @Excel(name = "详细地址")
    private java.lang.String xxdz;
    /** 所属部门 */
    @Excel(name = "所属部门")
    private java.lang.String ssbm;

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

    @Column(name = "xm")
    public java.lang.String getXm() {
        return xm;
    }

    public void setXm(java.lang.String xm) {
        this.xm = xm;
    }

    @Column(name = "dh")
    public java.lang.String getDh() {
        return dh;
    }

    public void setDh(java.lang.String dh) {
        this.dh = dh;
    }

    @Column(name = "idno")
    public java.lang.String getIdno() {
        return idno;
    }

    public void setIdno(java.lang.String idno) {
        this.idno = idno;
    }

    @Column(name = "dzyx")
    public java.lang.String getDzyx() {
        return dzyx;
    }

    public void setDzyx(java.lang.String dzyx) {
        this.dzyx = dzyx;
    }

    @Column(name = "gj")
    public java.lang.String getGj() {
        return gj;
    }

    public void setGj(java.lang.String gj) {
        this.gj = gj;
    }

    @Column(name = "jsd")
    public java.lang.String getJsd() {
        return jsd;
    }

    public void setJsd(java.lang.String jsd) {
        this.jsd = jsd;
    }

    @Column(name = "yzbm")
    public java.lang.String getYzbm() {
        return yzbm;
    }

    public void setYzbm(java.lang.String yzbm) {
        this.yzbm = yzbm;
    }

    @Column(name = "xxdz")
    public java.lang.String getXxdz() {
        return xxdz;
    }

    public void setXxdz(java.lang.String xxdz) {
        this.xxdz = xxdz;
    }

    @Column(name = "ssbm")
    public java.lang.String getSsbm() {
        return ssbm;
    }

    public void setSsbm(java.lang.String ssbm) {
        this.ssbm = ssbm;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "zlsq_id")
    public String getZlsqId() {
        return zlsqId;
    }

    public void setZlsqId(String zlsqId) {
        this.zlsqId = zlsqId;
    }

    @Column(name = "create_by")
    public java.lang.String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "create_name")
    public java.lang.String getCreateName() {
        return createName;
    }

    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    @Column(name = "create_date")
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "update_by")
    public java.lang.String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "update_name")
    public java.lang.String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    @Column(name = "update_date")
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

}
