package com.kingtake.price.entity.tbjgfg;


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
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

/**   
 * @Title: Entity
 * @Description: 价格法规库
 * @author onlineGenerator
 * @date 2016-07-25 16:44:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jg_fg", schema = "")
@SuppressWarnings("serial")
public class TBJgFgEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**法规名称*/
	@Excel(name="法规名称")
	private java.lang.String fgmc;
	/**文号*/
	@Excel(name="文号")
	private java.lang.String wh;
	/**发布单位*/
	@Excel(name="发布单位")
	private java.lang.String fbdw;
	/**发布时间*/
	@Excel(name="发布时间")
	private java.util.Date fbsj;
	/**摘要*/
	@Excel(name="摘要")
	private java.lang.String zy;
	/**施行时间*/
	@Excel(name="施行时间")
	private java.util.Date sxsj;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String beiz;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**创建人名称*/
	private java.lang.String createName;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	
	/** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  法规名称
	 */
	@Column(name ="FGMC",nullable=true,length=200)
	public java.lang.String getFgmc(){
		return this.fgmc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法规名称
	 */
	public void setFgmc(java.lang.String fgmc){
		this.fgmc = fgmc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文号
	 */
	@Column(name ="WH",nullable=true,length=80)
	public java.lang.String getWh(){
		return this.wh;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文号
	 */
	public void setWh(java.lang.String wh){
		this.wh = wh;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发布单位
	 */
	@Column(name ="FBDW",nullable=true,length=200)
	public java.lang.String getFbdw(){
		return this.fbdw;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发布单位
	 */
	public void setFbdw(java.lang.String fbdw){
		this.fbdw = fbdw;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发布时间
	 */
	@Column(name ="FBSJ",nullable=true,length=20)
	public java.util.Date getFbsj(){
		return this.fbsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发布时间
	 */
	public void setFbsj(java.util.Date fbsj){
		this.fbsj = fbsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */
	@Column(name ="ZY",nullable=true,length=4000)
	public java.lang.String getZy(){
		return this.zy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setZy(java.lang.String zy){
		this.zy = zy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  施行时间
	 */
	@Column(name ="SXSJ",nullable=true,length=20)
	public java.util.Date getSxsj(){
		return this.sxsj;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  施行时间
	 */
	public void setSxsj(java.util.Date sxsj){
		this.sxsj = sxsj;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="BEIZ",nullable=true,length=1000)
	public java.lang.String getBeiz(){
		return this.beiz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setBeiz(java.lang.String beiz){
		this.beiz = beiz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
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
