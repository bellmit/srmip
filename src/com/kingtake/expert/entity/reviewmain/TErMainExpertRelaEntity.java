package com.kingtake.expert.entity.reviewmain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 专家评审主表
 * @author onlineGenerator
 * @date 2015-08-18 16:50:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_er_review_expert_rela")
@SuppressWarnings("serial")
public class TErMainExpertRelaEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	
    private String teMainId;

    private String teExpertId;
	
	
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

    @Column(name = "T_E_ID")
    public String getTeMainId() {
        return teMainId;
    }

    public void setTeMainId(String teMainId) {
        this.teMainId = teMainId;
    }

    @Column(name = "T_E_ID2")
    public String getTeExpertId() {
        return teExpertId;
    }

    public void setTeExpertId(String teExpertId) {
        this.teExpertId = teExpertId;
    }


}
