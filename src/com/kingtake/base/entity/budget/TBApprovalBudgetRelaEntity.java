package com.kingtake.base.entity.budget;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 预算类别表
 * @author onlineGenerator
 * @date 2015-07-15 15:24:33
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_b_approval_budget_rela", schema = "")
@SuppressWarnings("serial")
@LogEntity("预算类别表")
public class TBApprovalBudgetRelaEntity implements java.io.Serializable {
  /** 主键 */
  @FieldDesc("主键")
  private java.lang.String id;
  /** 立项形式 */
  /**
   * zhangls 2016-02-26 将字段改为项目类型
   */
  // @FieldDesc("立项形式")
  // @Excel(name = "立项形式", isExportConvert = true)
  @FieldDesc("项目类型")
  @Excel(name = "项目类型", isExportConvert = true)
  private java.lang.String projApproval;
  /** 预算类型名称 */
  @FieldDesc("预算类型名称")
  @Excel(name = "预算类型名称", width = 20)
  private java.lang.String budgetNae;
  /** 是否比例项 */
  @FieldDesc("是否比例项")
  @Excel(name = "是否比例项", isExportConvert = true)
  private java.lang.String scaleFlag;
  /** 备注 */
  @FieldDesc("备注")
  @Excel(name = "备注", width = 20)
  private java.lang.String memo;
  /** 排序码 */
  @FieldDesc("排序码")
  @Excel(name = "排序码")
  private java.lang.String sortCode;
  /** 所属上级 */
  @FieldDesc("所属上级")
  private TBApprovalBudgetRelaEntity TBApprovalBudgetRelaEntity;
  /** 所属上级 */
  private List<TBApprovalBudgetRelaEntity> TBApprovalBudgetRelaEntitys = new ArrayList<TBApprovalBudgetRelaEntity>();
  /*
   * 是否可自行添加子项: 不可操作_0, 可添加_1, 可添加、可编辑_2, 可添加、可编辑、可删除_3
   */
  @FieldDesc("是否可自行添加子项")
  @Excel(name = "是否可自行添加子项", width = 1)
  private String addChildFlag;
  /**
   * 预算显示标志：1_显示，空_不显示； 计算书类型标志：1_管理类，2_材料类，3_工资类，4_利润类，5_设计类，6_外协类，7_实验类
   */
  @FieldDesc("显示表类型标志")
  @Excel(name = "显示表类型标志", width = 1)
  private String showFlag;

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
   * @param: java.lang.String
   *           主键
   */
  public void setId(java.lang.String id) {
    this.id = id;
  }

  /**
   * 方法: 取得java.lang.String
   *
   * @return: java.lang.String 立项形式
   */
  @Column(name = "PROJ_APPROVAL", nullable = true, length = 32)
  public java.lang.String getProjApproval() {
    return this.projApproval;
  }

  public java.lang.String convertGetProjApproval() {
    if (ProjectConstant.PROJECT_CONTRACT.equals(this.projApproval)) {
      return ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_CONTRACT);
    } else if (ProjectConstant.PROJECT_PLAN.equals(this.projApproval)) {
      return ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_PLAN);
    } else {
      return "";
    }
  }

  /**
   * 方法: 设置java.lang.String
   *
   * @param: java.lang.String
   *           立项形式
   */
  public void setProjApproval(java.lang.String projApproval) {
    this.projApproval = projApproval;
  }

  /**
   * 方法: 取得java.lang.String
   *
   * @return: java.lang.String 预算类型名称
   */
  @Column(name = "BUDGET_NAE", nullable = true, length = 100)
  public java.lang.String getBudgetNae() {
    return this.budgetNae;
  }

  /**
   * 方法: 设置java.lang.String
   *
   * @param: java.lang.String
   *           预算类型名称
   */
  public void setBudgetNae(java.lang.String budgetNae) {
    this.budgetNae = budgetNae;
  }

  /**
   * 方法: 取得java.lang.String
   *
   * @return: java.lang.String 是否比例项
   */
  @Column(name = "SCALE_FLAG", nullable = true, length = 1)
  public java.lang.String getScaleFlag() {
    return this.scaleFlag;
  }

  public java.lang.String convertGetScaleFlag() {
    return ConvertDicUtil.getConvertDic("0", "SFBZ", this.scaleFlag);
  }

  /**
   * 方法: 设置java.lang.String
   *
   * @param: java.lang.String
   *           是否比例项
   */
  public void setScaleFlag(java.lang.String scaleFlag) {
    this.scaleFlag = scaleFlag;
  }

  /**
   * 方法: 取得java.lang.String
   *
   * @return: java.lang.String 备注
   */
  @Column(name = "MEMO", nullable = true)
  public java.lang.String getMemo() {
    return this.memo;
  }

  /**
   * 方法: 设置java.lang.String
   *
   * @param: java.lang.String
   *           备注
   */
  public void setMemo(java.lang.String memo) {
    this.memo = memo;
  }

  /**
   * 方法: 取得java.lang.Integer
   *
   * @return: java.lang.Integer 排序码
   */
  @Column(name = "SORT_CODE", nullable = true, length = 10)
  public java.lang.String getSortCode() {
    return this.sortCode;
  }

  /**
   * 方法: 设置java.lang.Integer
   *
   * @param: java.lang.Integer
   *           排序码
   */
  public void setSortCode(java.lang.String sortCode) {
    this.sortCode = sortCode;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID")
  public TBApprovalBudgetRelaEntity getTBApprovalBudgetRelaEntity() {
    return TBApprovalBudgetRelaEntity;
  }

  public void setTBApprovalBudgetRelaEntity(TBApprovalBudgetRelaEntity tBApprovalBudgetRelaEntity) {
    TBApprovalBudgetRelaEntity = tBApprovalBudgetRelaEntity;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TBApprovalBudgetRelaEntity")
  public List<TBApprovalBudgetRelaEntity> getTBApprovalBudgetRelaEntitys() {
    return TBApprovalBudgetRelaEntitys;
  }

  public void setTBApprovalBudgetRelaEntitys(List<TBApprovalBudgetRelaEntity> tBApprovalBudgetRelaEntitys) {
    TBApprovalBudgetRelaEntitys = tBApprovalBudgetRelaEntitys;
  }

  @Column(name = "ADD_CHILD_FLAG", nullable = true, length = 1)
  public String getAddChildFlag() {
    return addChildFlag;
  }

  public void setAddChildFlag(String addChildFlag) {
    this.addChildFlag = addChildFlag;
  }

  @Column(name = "SHOW_FLAG", nullable = true, length = 1)
  public String getShowFlag() {
    return showFlag;
  }

  public void setShowFlag(String showFlag) {
    this.showFlag = showFlag;
  }
}
