package com.kingtake.expert.entity.reviewmain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 专家评审内容信息表
 * @author onlineGenerator
 * @date 2015-08-18 16:51:37
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_er_content")
@SuppressWarnings("serial")
public class TErMainTypeRelaEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
    /**
     * 专家评审主表id
     */
    private java.lang.String teMainId;

    /**
     * 评审内容类型表id
     */
    private java.lang.String teTypeId;
	

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


    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column(name = "T_E_ID")
    public java.lang.String getTeMainId() {
        return teMainId;
    }

    public void setTeMainId(java.lang.String teMainId) {
        this.teMainId = teMainId;
    }


    @Column(name = "T_E_ID2")
    public java.lang.String getTeTypeId() {
        return teTypeId;
    }


    public void setTeTypeId(java.lang.String teTypeId) {
        this.teTypeId = teTypeId;
    }


}
