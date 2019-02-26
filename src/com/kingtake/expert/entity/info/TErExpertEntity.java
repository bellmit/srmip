package com.kingtake.expert.entity.info;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.kingtake.common.util.ConvertDicUtil;

/**
 * @Title: Entity
 * @Description: 专家信息
 * @author onlineGenerator
 * @date 2015-07-13 19:47:51
 * @version V1.0
 * 
 */
@Entity
@Table(name = "t_er_expert", schema = "")
@Inheritance(strategy = InheritanceType.JOINED)
@LogEntity("专家信息")
public class TErExpertEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 编号 */
    @FieldDesc("编号")
    @Excel(name = "编号")
    private java.lang.String expertNum;
    /** 姓名 */
    @FieldDesc("姓名")
    @Excel(name = "姓名")
    private java.lang.String name;
    /** 性别(标准代码集) */
    @FieldDesc("性别")
    @Excel(name = "性别", isExportConvert = true)
    private java.lang.String sex;

    /** 出生年月 */
    @FieldDesc("出生年月")
    //@Excel(name = "出生年月", format = "yyyy-MM-dd")
    private java.util.Date expertBirthDate;
    /** 身份证件类型(标准代码集) */
    @FieldDesc("身份证件类型")
    //@Excel(name = "身份证件类型", isExportConvert = true, width = 15)
    private java.lang.String idType;
    /** 证件号码 */
    @FieldDesc("证件号码")
    //@Excel(name = "证件号码", width = 20)
    private java.lang.String idNo;
    /** 单位id */
    @FieldDesc("单位id")
    private java.lang.String expertDepartId;
    /** 单位名称（可输可选） */
    @FieldDesc("单位名称")
    @Excel(name = "单位名称", width = 15)
    private java.lang.String expertDepartName;
    /** 专业(标准代码集) */
    @FieldDesc("专业")
    @Excel(name = "专业", isExportConvert = true)
    private java.lang.String expertProfession;

    /** 职称(标准代码集) */
    @FieldDesc("职称")
    @Excel(name = "职称", isExportConvert = true)
    private java.lang.String expertPosition;

    /** 执业资格(标准代码集) */
    @FieldDesc("执业资格")
    //@Excel(name = "执业资格", isExportConvert = true)
    private java.lang.String expertPraciticReq;

    /** 地区（标准代码集） */
    @FieldDesc("地区")
    //@Excel(name = "地区", isExportConvert = true)
    private java.lang.String expertDistrict;
    /** 电话/手机 */
    @FieldDesc("电话/手机")
    @Excel(name = "电话/手机", width = 20)
    private java.lang.String expertPhone;
    /** 简介 */
    @FieldDesc("简介")
    //@Excel(name = "简介", width = 20)
    private java.lang.String expertSummary;
    /** 有效标记 */
    @FieldDesc("有效标记")
    private java.lang.String expertValidFlag;

    /**
     * 是否正式专家
     */
    @Excel(name = "是否正式专家",isExportConvert=true)
    private String isOfficial;

    /**
     * 座机
     */
    private String workPhone;

    /**
     * 专业组
     */
    private String officialGroup;

    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    private java.lang.String createName;
    /** 创建时间 */
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    private java.lang.String updateName;
    /** 修改时间 */
    private java.util.Date updateDate;

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
     * @return: java.lang.String 编号
     */
    @Column(name = "EXPERT_NUM", nullable = true, length = 10)
    public java.lang.String getExpertNum() {
        return this.expertNum;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 编号
     */
    public void setExpertNum(java.lang.String expertNum) {
        this.expertNum = expertNum;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 姓名
     */
    @Column(name = "NAME", nullable = true, length = 50)
    public java.lang.String getName() {
        return this.name;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 姓名
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 性别(标准代码集)
     */
    @Column(name = "SEX", nullable = true, length = 1)
    public java.lang.String getSex() {
        return this.sex;
    }

    public java.lang.String convertGetSex() {
        return ConvertDicUtil.getConvertDic("0", "SEX", this.sex);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 性别(标准代码集)
     */
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 身份证件类型(标准代码集)
     */
    @Column(name = "ID_TYPE", nullable = true, length = 1)
    public java.lang.String getIdType() {
        return this.idType;
    }

    public java.lang.String convertGetIdType() {
        return ConvertDicUtil.getConvertDic("0", "SFZJLX", this.idType);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 身份证件类型(标准代码集)
     */
    public void setIdType(java.lang.String idType) {
        this.idType = idType;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 证件号码
     */
    @Column(name = "ID_NO", nullable = true, length = 20)
    public java.lang.String getIdNo() {
        return this.idNo;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 证件号码
     */
    public void setIdNo(java.lang.String idNo) {
        this.idNo = idNo;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 出生年月
     */
    @Column(name = "EXPERT_BIRTH_DATE", nullable = true)
    public java.util.Date getExpertBirthDate() {
        return this.expertBirthDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 出生年月
     */
    public void setExpertBirthDate(java.util.Date expertBirthDate) {
        this.expertBirthDate = expertBirthDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 职称(标准代码集)
     */
    @Column(name = "EXPERT_POSITION", nullable = true, length = 2)
    public java.lang.String getExpertPosition() {
        return this.expertPosition;
    }

    public java.lang.String convertGetExpertPosition() {
        return ConvertDicUtil.getConvertDic("1", "ZHCH", this.expertPosition);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 职称(标准代码集)
     */
    public void setExpertPosition(java.lang.String expertPosition) {
        this.expertPosition = expertPosition;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 执业资格(标准代码集)
     */
    @Column(name = "EXPERT_PRACITIC_REQ", nullable = true, length = 2)
    public java.lang.String getExpertPraciticReq() {
        return this.expertPraciticReq;
    }

    public java.lang.String convertGetExpertPraciticReq() {
        return ConvertDicUtil.getConvertDic("1", "ZYZG", this.expertPraciticReq);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 执业资格(标准代码集)
     */
    public void setExpertPraciticReq(java.lang.String expertPraciticReq) {
        this.expertPraciticReq = expertPraciticReq;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 专业(标准代码集)
     */
    @Column(name = "EXPERT_PROFESSION", nullable = true, length = 10)
    public java.lang.String getExpertProfession() {
        return this.expertProfession;
    }

    public java.lang.String convertGetExpertProfession() {
        return ConvertDicUtil.getConvertDic("1", "MAJOR", this.expertProfession);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 专业(标准代码集)
     */
    public void setExpertProfession(java.lang.String expertProfession) {
        this.expertProfession = expertProfession;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 电话/手机
     */
    @Column(name = "EXPERT_PHONE", nullable = true, length = 20)
    public java.lang.String getExpertPhone() {
        return this.expertPhone;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 电话/手机
     */
    public void setExpertPhone(java.lang.String expertPhone) {
        this.expertPhone = expertPhone;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 单位id
     */
    @Column(name = "EXPERT_DEPART_ID", nullable = true, length = 32)
    public java.lang.String getExpertDepartId() {
        return this.expertDepartId;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 单位id
     */
    public void setExpertDepartId(java.lang.String expertDepartId) {
        this.expertDepartId = expertDepartId;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 单位名称（可输可选）
     */
    @Column(name = "EXPERT_DEPART_NAME", nullable = true, length = 60)
    public java.lang.String getExpertDepartName() {
        return this.expertDepartName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 单位名称（可输可选）
     */
    public void setExpertDepartName(java.lang.String expertDepartName) {
        this.expertDepartName = expertDepartName;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 地区（标准代码集）
     */
    @Column(name = "EXPERT_DISTRICT", nullable = true, length = 3)
    public java.lang.String getExpertDistrict() {
        return this.expertDistrict;
    }

    public java.lang.String convertGetExpertDistrict() {
        return ConvertDicUtil.getConvertDic("0", "NATIVEPLACE", this.expertDistrict);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 地区（标准代码集）
     */
    public void setExpertDistrict(java.lang.String expertDistrict) {
        this.expertDistrict = expertDistrict;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 简介
     */
    @Column(name = "EXPERT_SUMMARY", nullable = true, length = 1000)
    public java.lang.String getExpertSummary() {
        return this.expertSummary;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 简介
     */
    public void setExpertSummary(java.lang.String expertSummary) {
        this.expertSummary = expertSummary;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 有效标记
     */
    @Column(name = "EXPERT_VALID_FLAG", nullable = true, length = 1)
    public java.lang.String getExpertValidFlag() {
        return this.expertValidFlag;
    }

    public java.lang.String convertGetExpertValidFlag() {
        return ConvertDicUtil.getConvertDic("0", "SFBZ", this.expertValidFlag);
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 有效标记
     */
    public void setExpertValidFlag(java.lang.String expertValidFlag) {
        this.expertValidFlag = expertValidFlag;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 创建人
     */
    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public java.lang.String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 创建人
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 创建人姓名
     */
    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 创建人姓名
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 创建时间
     */
    @Column(name = "CREATE_DATE", nullable = true)
    public java.util.Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 创建时间
     */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 修改人
     */
    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public java.lang.String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 修改人
     */
    public void setUpdateBy(java.lang.String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.lang.String
     * 
     * @return: java.lang.String 修改人姓名
     */
    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     * 
     * @param: java.lang.String 修改人姓名
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.util.Date
     * 
     * @return: java.util.Date 修改时间
     */
    @Column(name = "UPDATE_DATE", nullable = true)
    public java.util.Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     * 
     * @param: java.util.Date 修改时间
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "IS_OFFICIAL")
    public String getIsOfficial() {
        return isOfficial;
    }
    
    public String convertGetIsOfficial(){
        if("1".equals(this.isOfficial)){
            return "是";
        }else{
            return "否";
        }
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    @Column(name = "WORK_PHONE")
    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    @Column(name = "official_group")
    public String getOfficialGroup() {
        return officialGroup;
    }

    public void setOfficialGroup(String officialGroup) {
        this.officialGroup = officialGroup;
    }

}
