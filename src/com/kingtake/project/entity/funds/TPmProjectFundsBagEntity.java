package com.kingtake.project.entity.funds;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.LogEntity;

/**   
 * @Title: Entity
 * @Description: 预算编制表
 * @author onlineGenerator
 * @date 2015-07-26 15:40:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_project_funds_bag", schema = "")
@SuppressWarnings("serial")
@LogEntity("预算编制包")
public class TPmProjectFundsBagEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	
	/**项目主键*/
	private java.lang.String xmdm;
	
	/**
	 * 序号
	 */
	private String xh;
	
	/**
	 * 编制日期
	 */
	private java.util.Date bzrq;
	
	/**
	 * 预算类型
	 */
	private String yslx;
	
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "xmdm")
	public java.lang.String getXmdm() {
		return xmdm;
	}

	public void setXmdm(java.lang.String xmdm) {
		this.xmdm = xmdm;
	}

	@Column(name = "xh")
	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	@Column(name = "bzrq")
	public java.util.Date getBzrq() {
		return bzrq;
	}

	public void setBzrq(java.util.Date bzrq) {
		this.bzrq = bzrq;
	}

	@Column(name = "yslx")
	public String getYslx() {
		return yslx;
	}

	public void setYslx(String yslx) {
		this.yslx = yslx;
	}
    
}
