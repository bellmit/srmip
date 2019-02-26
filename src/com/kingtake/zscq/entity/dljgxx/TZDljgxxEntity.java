package com.kingtake.zscq.entity.dljgxx;

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
@Table(name = "t_z_dljgxx", schema = "")
@SuppressWarnings("serial")
public class TZDljgxxEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
    /** 机构名称 */
    @Excel(name = "机构名称")
    private java.lang.String jgmc;
    /** 代号 */
    @Excel(name = "代号")
    private java.lang.String dh;
    /** 联系人 */
    @Excel(name = "联系人")
    private java.lang.String lxr;
    /** 联系电话 */
    @Excel(name = "联系电话")
    private java.lang.String lxdh;
	
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

    @Column(name = "jgmc")
    public java.lang.String getJgmc() {
        return jgmc;
    }

    public void setJgmc(java.lang.String jgmc) {
        this.jgmc = jgmc;
    }

    @Column(name = "dh")
    public java.lang.String getDh() {
        return dh;
    }

    public void setDh(java.lang.String dh) {
        this.dh = dh;
    }

    public java.lang.String getLxr() {
        return lxr;
    }

    public void setLxr(java.lang.String lxr) {
        this.lxr = lxr;
    }

    public java.lang.String getLxdh() {
        return lxdh;
    }

    public void setLxdh(java.lang.String lxdh) {
        this.lxdh = lxdh;
    }

}
