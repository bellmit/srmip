package com.kingtake.project.entity.member;

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
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**   
 * @Title: Entity
 * @Description: 项目组成员
 * @author onlineGenerator
 * @date 2015-07-09 17:34:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_pm_project_member")
@LogEntity("项目组成员")
@SuppressWarnings("serial")
public class TPmProjectMemberEntity implements java.io.Serializable {
	/**createBy*/
	private java.lang.String createBy;
	/**createName*/
	private java.lang.String createName;
	/**createDate*/
	private java.util.Date createDate;
	/**updateBy*/
	private java.lang.String updateBy;
	/**updateName*/
	private java.lang.String updateName;
	/**updateDate*/
	private java.util.Date updateDate;
	/**主键*/
	private java.lang.String id;
	/**项目基_主键*/
    private java.lang.String tpId;

    /**
     * 项目实体
     */
    private TPmProjectEntity project;

    /**
     * 项目名称
     */
    private String projectName;
    
    /**
     * 项目立项
     */
    private String projectLxName;
    /**
     * 用户
     */
    private TSUser user;
    /**
     * 用户id
     */
    private String userId;
	/**姓名*/
    @FieldDesc("姓名")
	@Excel(name="姓名")
	private java.lang.String name;
	/**性别*/
    @FieldDesc("性别")
    @Excel(name = "性别", isExportConvert = true)
	private java.lang.String sex;
	/**出生年月*/
    @FieldDesc("出生年月")
    @Excel(name = "出生年月", format = "yyyy-MM-dd")
	private java.util.Date birthday;
	/**学位*/
    @FieldDesc("学位")
    @Excel(name = "学位", isExportConvert = true)
	private java.lang.String degree;
	/**职称职务*/
    @FieldDesc("职称职务")
    @Excel(name = "职称职务", isExportConvert = true)
	private java.lang.String position;
	/**任务分工*/
    @FieldDesc("任务分工")
	@Excel(name="任务分工")
	private java.lang.String taskDivide;
	/**所属单位*/
    @FieldDesc("所属单位")
    private java.lang.String superUnitId;
    @Excel(name = "所属单位")
    private java.lang.String superUnitName;
    /**
     * 所属单位
     */
    private TSDepart superUnit;
	/**是否负责人*/
    @Excel(name = "是否负责人", isExportConvert = true)
	private java.lang.String projectManager;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createBy
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  createName
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  createName
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createDate
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateBy
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateBy
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  updateName
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  updateName
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  updateDate
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  updateDate
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
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
	
    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 项目基_主键
     */
    @Transient
    public java.lang.String getTpId() {
        return tpId;
    }

    public void setTpId(java.lang.String tpId) {
        this.tpId = tpId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 姓名
     */
	@Column(name ="NAME",nullable=true,length=36)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,length=1)
	public java.lang.String getSex(){
		return this.sex;
	}

    public String convertGetSex() {
        return ConvertDicUtil.getConvertDic("0", "SEX", this.sex);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  出生年月
	 */
	@Column(name ="BIRTHDAY",nullable=true)
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  出生年月
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  学位
	 */
	@Column(name ="DEGREE",nullable=true,length=2)
	public java.lang.String getDegree(){
		return this.degree;
	}

    public String convertGetDegree() {
        return ConvertDicUtil.getConvertDic("0", "XWLB", this.degree);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  学位
	 */
	public void setDegree(java.lang.String degree){
		this.degree = degree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职称职务
	 */
	@Column(name ="POSITION",nullable=true,length=2)
	public java.lang.String getPosition(){
		return this.position;
	}

    public String convertGetPosition() {
        return ConvertDicUtil.getConvertDic("0", "PROFESSIONAL", this.position);
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职称职务
	 */
	public void setPosition(java.lang.String position){
		this.position = position;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务分工
	 */
	@Column(name ="TASK_DIVIDE",nullable=true,length=2000)
	public java.lang.String getTaskDivide(){
		return this.taskDivide;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务分工
	 */
	public void setTaskDivide(java.lang.String taskDivide){
		this.taskDivide = taskDivide;
	}

    @Transient
    public java.lang.String getSuperUnitId() {
        return superUnitId;
    }

    public void setSuperUnitId(java.lang.String superUnitId) {
        this.superUnitId = superUnitId;
    }

    @Transient
    public java.lang.String getSuperUnitName() {
        if (this.superUnit != null) {
            return superUnit.getDepartname();
        }
        return "";
    }

    public void setSuperUnitName(java.lang.String superUnitName) {
        this.superUnitName = superUnitName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 是否负责人
     */
	@Column(name ="PROJECT_MANAGER",nullable=true,length=1)
	public java.lang.String getProjectManager(){
		return this.projectManager;
	}

    public String convertGetProjectManager() {
        if (SrmipConstants.YES.equals(this.projectManager)) {
            return "是";
        } else {
            return "否";
        }
    }

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否负责人
	 */
	public void setProjectManager(java.lang.String projectManager){
		this.projectManager = projectManager;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPER_UNIT")
    public TSDepart getSuperUnit() {
        return superUnit;
    }

    public void setSuperUnit(TSDepart superUnit) {
        this.superUnit = superUnit;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public TSUser getUser() {
        return user;
    }

    public void setUser(TSUser user) {
        this.user = user;
    }

    @Transient
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_P_ID")
    public TPmProjectEntity getProject() {
        return project;
    }

    public void setProject(TPmProjectEntity project) {
        this.project = project;
    }

    @Transient
    public String getProjectName() {
        if (this.project != null) {
            return project.getProjectName();
        }
        return "";
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
