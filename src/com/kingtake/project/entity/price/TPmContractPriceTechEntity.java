package com.kingtake.project.entity.price;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 技术服务类合同价款计算书
 * @author onlineGenerator
 * @date 2015-09-16 10:45:41
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_pm_contract_price_tech", schema = "")
@SuppressWarnings("serial")
public class TPmContractPriceTechEntity implements java.io.Serializable {
	/**主键*/
	@FieldDesc("主键")
	private java.lang.String id;
	/**合同价款计算书封面_主键*/
	@Excel(name="合同价款计算书封面_主键")
	@FieldDesc("合同价款计算书封面_主键")
	private java.lang.String tpId;

    /**
     * 序号
     */
    private String serialNum;

    /**
     * 技术服务内容
     */
    private String content;

    /**
     * 报价构成说明
     */
    private String explain;

    /**
     * 报价栏
     */
    private BigDecimal priceAmount;

    /**
     * 审价栏
     */
    private BigDecimal auditAmount;


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
	
	@Column(name ="T_P_ID",nullable=true,length=32)
	public java.lang.String getTpId() {
		return tpId;
	}

	public void setTpId(java.lang.String tpId) {
		this.tpId = tpId;
	}

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "explain")
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Column(name = "price_amount")
    public BigDecimal getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    @Column(name = "audit_amount")
    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    @Column(name = "memo")
    public java.lang.String getMemo() {
        return memo;
    }

    public void setMemo(java.lang.String memo) {
        this.memo = memo;
    }

    @Column(name = "serial_num")
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

}
