package com.kingtake.project.entity.incomeplan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 计划下达分配金额表
 * @author onlineGenerator
 * @date 2015-12-02 18:36:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_pm_fpje", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TPmFpje implements java.io.Serializable {
    private java.lang.String id;
    
    private java.lang.String xmid;
    
    private java.lang.String jhwjid;
    
    private java.lang.String je;
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
    
    @Column(name = "xmid", nullable = false, length = 32)
    public java.lang.String getXmid() {
        return this.xmid;
    }

    public void setXmid(java.lang.String xmid) {
        this.xmid = xmid;
    }
    
    @Column(name = "jhwjid", nullable = false, length = 32)
    public java.lang.String getJhwjid() {
        return this.jhwjid;
    }

    public void setJhwjid(java.lang.String jhwjid) {
        this.jhwjid = jhwjid;
    }

    @Column(name = "je", nullable = false, length = 32)
    public java.lang.String getJe() {
        return this.je;
    }

    public void setJe(java.lang.String je) {
        this.je = je;
    }

}
