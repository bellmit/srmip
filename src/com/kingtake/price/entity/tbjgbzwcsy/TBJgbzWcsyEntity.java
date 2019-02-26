package com.kingtake.price.entity.tbjgbzwcsy;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 价格库系统_外场试验费标准
 * @author onlineGenerator
 * @date 2016-07-25 09:39:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_jgbz_wcsy", schema = "")
@SuppressWarnings("serial")
public class TBJgbzWcsyEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**差旅费*/
	@Excel(name="差旅费")
	private java.lang.String clf;
	/**外场试验人员补助费*/
	@Excel(name="外场试验人员补助费")
	private java.lang.String wcsyrybzf;
	/**动态飞行架次费用*/
	@Excel(name="动态飞行架次费用")
	private java.lang.String dtfxjcf;
	/**风洞试验*/
	@Excel(name="风洞试验")
	private java.lang.String fdsy;
	/**靶场时间标准*/
	@Excel(name="靶场时间标准")
	private java.lang.String bcsjbz;
	/**地方船只使用标准*/
	@Excel(name="地方船只使用标准")
	private java.lang.String dfczsybz;
	/**运输费*/
	@Excel(name="运输费")
	private java.lang.String ysf;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	
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
	 *@return: java.lang.String  差旅费
	 */
	@Column(name ="CLF",nullable=true,length=100)
	public java.lang.String getClf(){
		return this.clf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  差旅费
	 */
	public void setClf(java.lang.String clf){
		this.clf = clf;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外场试验人员补助费
	 */
	@Column(name ="WCSYRYBZF",nullable=true,length=2000)
	public java.lang.String getWcsyrybzf(){
		return this.wcsyrybzf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外场试验人员补助费
	 */
	public void setWcsyrybzf(java.lang.String wcsyrybzf){
		this.wcsyrybzf = wcsyrybzf;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  动态飞行架次费用
	 */
	@Column(name ="DTFXJCF",nullable=true,length=500)
	public java.lang.String getDtfxjcf(){
		return this.dtfxjcf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  动态飞行架次费用
	 */
	public void setDtfxjcf(java.lang.String dtfxjcf){
		this.dtfxjcf = dtfxjcf;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  风洞试验
	 */
	@Column(name ="FDSY",nullable=true,length=500)
	public java.lang.String getFdsy(){
		return this.fdsy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  风洞试验
	 */
	public void setFdsy(java.lang.String fdsy){
		this.fdsy = fdsy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  靶场时间标准
	 */
	@Column(name ="BCSJBZ",nullable=true,length=500)
	public java.lang.String getBcsjbz(){
		return this.bcsjbz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  靶场时间标准
	 */
	public void setBcsjbz(java.lang.String bcsjbz){
		this.bcsjbz = bcsjbz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地方船只使用标准
	 */
	@Column(name ="DFCZSYBZ",nullable=true,length=500)
	public java.lang.String getDfczsybz(){
		return this.dfczsybz;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地方船只使用标准
	 */
	public void setDfczsybz(java.lang.String dfczsybz){
		this.dfczsybz = dfczsybz;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  运输费
	 */
	@Column(name ="YSF",nullable=true,length=500)
	public java.lang.String getYsf(){
		return this.ysf;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  运输费
	 */
	public void setYsf(java.lang.String ysf){
		this.ysf = ysf;
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
}
