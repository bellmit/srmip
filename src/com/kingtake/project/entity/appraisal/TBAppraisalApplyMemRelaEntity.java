package com.kingtake.project.entity.appraisal;

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
 * @Description: 鉴定委员会成员与鉴定申请表关系表
 * @author onlineGenerator
 * @date 2015-09-09 09:44:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_appraisa_apply_mem_rela", schema = "")
@LogEntity("鉴定委员会成员与鉴定申请表关系表")
@SuppressWarnings("serial")
public class TBAppraisalApplyMemRelaEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键id")
	private java.lang.String id;
	/**鉴定申_主键*/
	@Excel(name="鉴定申_主键")
	@FieldDesc("鉴定申_主键")
    private java.lang.String tbId;
	/**成员id*/
	@Excel(name="成员id")
	@FieldDesc("成员id")
	private java.lang.String memberId;
	
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

	@Column(name ="T_B_ID",nullable=true,length=32)
    public java.lang.String getTbId() {
        return tbId;
    }

    public void setTbId(java.lang.String tbId) {
        this.tbId = tbId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 成员id
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
}
