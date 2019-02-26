package com.kingtake.office.entity.disk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "t_o_group", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TOGroupEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private java.lang.String id;

    /**
     * 群名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 上传时间
     */
    private Date create_time;


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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "create_time")
    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

}
