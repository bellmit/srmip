package com.kingtake.office.entity.warn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 公用信息接收人表
 * @author onlineGenerator
 * @date 2015-07-15 15:47:16
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_o_warn_receive", schema = "")
@SuppressWarnings("serial")
@LogEntity("公共信息接收人")
public class TOWarnReceiveEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
    /**
     * 关联的主表实体
     */
    private TOWarnEntity toWarn;
	/**接收人id*/
	private java.lang.String receiveUserid;
	/**接收人姓名*/
	@Excel(name="接收人姓名")
    @FieldDesc("接收人姓名")
	private java.lang.String receiveUsername;
	/**是否完成*/
	@Excel(name="是否完成")
    @FieldDesc("是否完成")
	private java.lang.String finishFlag;
	/**完成时间*/
	@Excel(name="完成时间")
    @FieldDesc("完成时间")
	private java.util.Date finishDate;
	/**备注*/
	@Excel(name="备注")
    @FieldDesc("备注")
	private java.lang.String memo;
	
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

    @ManyToOne
    @JoinColumn(name = "T_O_WARN_ID")
    public TOWarnEntity getToWarn() {
        return toWarn;
    }

    public void setToWarn(TOWarnEntity toWarn) {
        this.toWarn = toWarn;
    }


    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 接收人id
     */
	@Column(name ="RECEIVE_USERID",nullable=true,length=50)
	public java.lang.String getReceiveUserid(){
		return this.receiveUserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收人id
	 */
	public void setReceiveUserid(java.lang.String receiveUserid){
		this.receiveUserid = receiveUserid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  接收人姓名
	 */
	@Column(name ="RECEIVE_USERNAME",nullable=true,length=50)
	public java.lang.String getReceiveUsername(){
		return this.receiveUsername;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收人姓名
	 */
	public void setReceiveUsername(java.lang.String receiveUsername){
		this.receiveUsername = receiveUsername;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否完成
	 */
	@Column(name ="FINISH_FLAG",nullable=true,length=1)
	public java.lang.String getFinishFlag(){
		return this.finishFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否完成
	 */
	public void setFinishFlag(java.lang.String finishFlag){
		this.finishFlag = finishFlag;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  完成时间
	 */
	@Column(name ="FINISH_DATE",nullable=true)
	public java.util.Date getFinishDate(){
		return this.finishDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  完成时间
	 */
	public void setFinishDate(java.util.Date finishDate){
		this.finishDate = finishDate;
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
}
