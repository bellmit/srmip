package com.kingtake.project.entity.appraisalmeeting;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 鉴定会人员单位关联表
 * @author onlineGenerator
 * @date 2016-01-22 14:27:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_appraisal_meeting_depart_m", schema = "")
@SuppressWarnings("serial")
public class TBAppraisalMeetingDepartMEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**申请人单位id*/
	@Excel(name="申请人单位id")
	private java.lang.String departId;
	/**成员id*/
	@Excel(name="成员id")
	private java.lang.String memberId;
	/**是否新增*/
	@Excel(name="是否新增")
	private java.lang.String flag;
	
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
	 *@return: java.lang.String  申请人单位id
	 */
	@Column(name ="DEPART_ID",nullable=true,length=32)
	public java.lang.String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请人单位id
	 */
	public void setDepartId(java.lang.String departId){
		this.departId = departId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成员id
	 */
	@Column(name ="MEMBER_ID",nullable=true,length=32)
	public java.lang.String getMemberId(){
		return this.memberId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  成员id
	 */
	public void setMemberId(java.lang.String memberId){
		this.memberId = memberId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否新增
	 */
	@Column(name ="FLAG",nullable=true,length=2)
	public java.lang.String getFlag(){
		return this.flag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否新增
	 */
	public void setFlag(java.lang.String flag){
		this.flag = flag;
	}
}
