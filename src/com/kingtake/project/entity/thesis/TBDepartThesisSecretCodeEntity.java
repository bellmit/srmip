package com.kingtake.project.entity.thesis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 论文保密申请信息表
 * @author onlineGenerator
 * @date 2016-01-08 15:56:13
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_b_depart_thesis_secret_code", schema = "")
@SuppressWarnings("serial")
public class TBDepartThesisSecretCodeEntity implements java.io.Serializable {

    private java.lang.String id;

    /**
     * 部门id
     */
    private String departId;

    /**
     * 审查编号
     */
    private String secretCode;

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

    @Column(name = "DEPART_ID")
    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    @Column(name = "SECRET_CODE")
    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

}
