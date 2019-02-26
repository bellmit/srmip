package com.kingtake.base.entity.sideccatalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 项目模块配置表
 * @author onlineGenerator
 * @date 2016-01-19 11:30:07
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_pm_sidecatalog", schema = "")
@SuppressWarnings("serial")
public class TPmSidecatalogEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 标题 */
    @Excel(name = "标题")
    private java.lang.String title;
    /** 等级 */
    @Excel(name = "等级")
    private java.lang.String level;
    /** 业务代码 */
    @Excel(name = "业务代码")
    private java.lang.String businessCode;
    /** 页面url */
    @Excel(name = "页面url")
    private java.lang.String url;
    /** 序号 */
    @Excel(name = "序号")
    private Integer index;

    /**
     * 模块类型，1 表示项目基本信息 2表示过程管理
     */
    private String moduleType;

    /**
     * 备注
     */
    private String memo;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;
    /**
     * 是否使用
     */
    private Boolean isUsed;

    @Column(name = "USED")
    public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

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

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 标题
     */
    @Column(name = "TITLE", nullable = true, length = 32)
    public java.lang.String getTitle() {
        return this.title;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 标题
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    @Column(name = "NODELEVEL", nullable = true, length = 1)
    public java.lang.String getLevel() {
        return level;
    }

    public void setLevel(java.lang.String level) {
        this.level = level;
    }


    @Column(name = "SERIA_NUMBER", nullable = true, length = 5)
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 业务代码
     */
    @Column(name = "BUSINESS_CODE", nullable = true, length = 32)
    public java.lang.String getBusinessCode() {
        return this.businessCode;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 业务代码
     */
    public void setBusinessCode(java.lang.String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 页面url
     */
    @Column(name = "URL", nullable = true, length = 200)
    public java.lang.String getUrl() {
        return this.url;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 页面url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    @Column(name = "module_type")
    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "width")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}
