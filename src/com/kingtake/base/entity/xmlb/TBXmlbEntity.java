package com.kingtake.base.entity.xmlb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;

/**
 * @Title: Entity
 * @Description: t_b_xmlx
 * @author onlineGenerator
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_xmlb", schema = "")
@SuppressWarnings("serial")
@LogEntity("项目类型表")
public class TBXmlbEntity implements java.io.Serializable {
    private static SystemService systemService;

    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 项目类别 */
    @FieldDesc("项目类别")
    @Excel(name = "项目类别", width = 50)
    private java.lang.String xmlb;
    
    /** 主管单位 */
    @FieldDesc("主管单位")
    @Excel(name = "主管单位", width = 50)
    private java.lang.String zgdw;
    
    /** 经费类型 */
    @FieldDesc("经费类型")
    @Excel(name = "经费类型", width = 32)
    private java.lang.String jflxStr;
    
    @FieldDesc("经费类型")
    private TBJflxEntity jflx;
    
    /** 项目类型*/
    @FieldDesc("项目类型")
    @Excel(name = "项目类型", width = 10)
    private java.lang.String xmlx;
    
    /** 项目性质 */
    @FieldDesc("项目性质")
    @Excel(name = "项目性质", width = 50)
    private java.lang.String xmxz;
    
    /** 项目来源 */
    @FieldDesc("项目来源")
    @Excel(name = "项目来源", width = 20)
    private java.lang.String xmly;
    
    /** 会计编码规则 */
    @FieldDesc("会计编码规则")
    @Excel(name = "会计编码规则", width = 50)
    private java.lang.String kjbmgz;
    
    /** 备注*/
    @FieldDesc("备注")
    @Excel(name = "备注", width = 200)
    private java.lang.String bz;
    
    /** 排序码 */
    @FieldDesc("排序码")
    @Excel(name = "排序码")
    private java.lang.Integer sortCode;
    
    /** 唯一编号 */
    @FieldDesc("唯一编号")
    @Excel(name = "唯一编号")
    private java.lang.Integer wybh;
    /**
     * 父类型id
     */
    private TBXmlbEntity parentType;

    /**
     * 有效标志,0 无效，1 有效
     */
    private String validFlag;

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 32)
    public java.lang.String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主键
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "XMLB", nullable = true, length = 50)
    public java.lang.String getXmlb() {
        return this.xmlb;
    }

    public void setXmlb(java.lang.String xmlb) {
        this.xmlb = xmlb;
    }
    
    @Column(name = "ZGDW", nullable = true, length = 50)
    public java.lang.String getZgdw() {
        return this.zgdw;
    }

    public void setZgdw(java.lang.String zgdw) {
        this.zgdw = zgdw;
    }
    
    @Transient
	public String getJflxStr() {
		String jflxStr = "";
		if (this.jflx != null) {
			jflxStr = jflx.getJflxmc();
		}
		return jflxStr;
	}

	public void setJflxStr(String jflxStr) {
		this.jflxStr = jflxStr;
	}
    
    @ManyToOne
    @JoinColumn(name = "JFLX")
    @NotFound(action = NotFoundAction.IGNORE)
    //忽略关联不上
    public TBJflxEntity getJf() {
        return this.jflx;
    }

    public java.lang.String convertGetJf() {
        return this.jflx.getJflxmc();
    }

    public void setJf(TBJflxEntity jflx) {
        this.jflx = jflx;
    }
    
    @Column(name = "XMLX", nullable = true, length = 10)
    public java.lang.String getXmlx() {
        return this.xmlx;
    }

    public void setXmlx(java.lang.String xmlx) {
        this.xmlx = xmlx;
    }
    
    @Column(name = "XMXZ", nullable = true, length = 50)
    public java.lang.String getXmxz() {
        return this.xmxz;
    }

    public void setXmxz(java.lang.String xmxz) {
        this.xmxz = xmxz;
    }
    
    @Column(name = "XMLY", nullable = true, length = 20)
    public java.lang.String getXmly() {
        return this.xmly;
    }

    public void setXmly(java.lang.String xmly) {
        this.xmly = xmly;
    }
    
    @Column(name = "KJBMGZ", nullable = true, length = 50)
    public java.lang.String getKjbmgz() {
        return this.kjbmgz;
    }

    public void setKjbmgz(java.lang.String kjbmgz) {
        this.kjbmgz = kjbmgz;
    }
    
    @Column(name = "BZ", nullable = true, length = 200)
    public java.lang.String getBz() {
        return this.bz;
    }

    public void setBz(java.lang.String bz) {
        this.bz = bz;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 排序码
     */
    @Column(name = "SORT_CODE", nullable = true, length = 6)
    public java.lang.Integer getSortCode() {
        return this.sortCode;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 排序码
     */
    public void setSortCode(java.lang.Integer sortCode) {
        this.sortCode = sortCode;
    }
    
    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 唯一编号
     */
    @Column(name = "WYBH", nullable = true, length = 6)
    public java.lang.Integer getWybh() {
        return this.wybh;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 唯一编号
     */
    public void setWybh(java.lang.Integer wybh) {
        this.wybh = wybh;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public TBXmlbEntity getParentType() {
        return parentType;
    }

    public void setParentType(TBXmlbEntity parentType) {
        this.parentType = parentType;
    }

    @Column(name = "valid_flag")
    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag;
    }

}
