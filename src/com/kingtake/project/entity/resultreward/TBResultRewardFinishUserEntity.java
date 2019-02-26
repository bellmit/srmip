package com.kingtake.project.entity.resultreward;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 成果奖励完成人信息表
 * @author onlineGenerator
 * @date 2016-02-02 15:33:33
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_result_reward_finish_user")
@SuppressWarnings("serial")
public class TBResultRewardFinishUserEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 奖励表主键 */
    @Excel(name = "奖励表主键")
    private java.lang.String rewardId;
    /** 成果完成人姓名 */
    @Excel(name = "成果完成人姓名")
    private java.lang.String finishUsername;
    /** 主要完成单位名称 */
    @Excel(name = "主要完成单位名称")
    private java.lang.String finishDepartname;

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
     * @return: java.lang.String 奖励表主键
     */
    @Column(name = "REWARD_ID", nullable = true, length = 32)
    public java.lang.String getRewardId() {
        return this.rewardId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 奖励表主键
     */
    public void setRewardId(java.lang.String rewardId) {
        this.rewardId = rewardId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 成果完成人姓名
     */
    @Column(name = "FINISH_USERNAME", nullable = true, length = 500)
    public java.lang.String getFinishUsername() {
        return this.finishUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 成果完成人姓名
     */
    public void setFinishUsername(java.lang.String finishUsername) {
        this.finishUsername = finishUsername;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 主要完成单位名称
     */
    @Column(name = "FINISH_DEPARTNAME", nullable = true, length = 500)
    public java.lang.String getFinishDepartname() {
        return this.finishDepartname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 主要完成单位名称
     */
    public void setFinishDepartname(java.lang.String finishDepartname) {
        this.finishDepartname = finishDepartname;
    }
}
