package org.jeecgframework.web.system.pojo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.DateUtils;

/**
 * 系统用户表
 * 
 * @author 张代浩
 */
@Entity
@Table(name = "t_s_user")
@PrimaryKeyJoinColumn(name = "id")
@LogEntity("系统用户")
public class TSUser extends TSBaseUser implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @FieldDesc("签名文件")
    private String signatureFile;// 签名文件
    @FieldDesc("手机号")
    private String mobilePhone;// 手机
    @FieldDesc("办公电话")
    private String officePhone;// 办公电话
    @FieldDesc("邮箱")
    private String email;// 邮箱

    /**
     * 姓名拼音
     */
    private String namePinyin;

    /**
     * 曾用名
     */
    private String nameUsed;

    /**
     * 性别（取标准代码集中的性别代码）
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 证件类型（取标准代码集中的身份证件类型代码）
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNo;

    /**
     * 政治面貌（取标准代码集中的政治面貌代码）
     */
    private String politicalStatus;

    /**
     * 职务（取标准代码集中的专业职务代码）
     */
    private String duty;

    /**
     * 籍贯（取标准代码集中的籍贯代码）
     */
    private String nativePlace;

    /**
     * 民族（取标准代码集中的民族代码）
     */
    private String nation;

    /**
     * 是否军人（取标准代码集中的是否标志代码）
     */
    private String soldierFlag;

    /**
     * 军衔（取标准代码集中的军衔代码）
     */
    private String rank;

    /**
     * 排序码
     */
    private Integer sortCode;

    /**
     * 编制标志
     */
    private String authorizedFlag;

    /**
     * 干部标志
     */
    private String leaderFlag;

    /**
     * 军官证号
     */
    private String officerNum;

    /**
     * 身份证号
     */
    private String idNum;

    @Column(name = "signatureFile", length = 100)
    public String getSignatureFile() {
        return this.signatureFile;
    }

    public void setSignatureFile(String signatureFile) {
        this.signatureFile = signatureFile;
    }

    @Column(name = "mobilePhone", length = 30)
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "officePhone", length = 20)
    public String getOfficePhone() {
        return this.officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    @Column(name = "email", length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "NAME_PINYIN")
    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    @Column(name = "NAME_USED")
    public String getNameUsed() {
        return nameUsed;
    }

    public void setNameUsed(String nameUsed) {
        this.nameUsed = nameUsed;
    }

    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "BIRTHDAY")
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 格式化
     * 
     * @return
     */
    @Transient
    public String getBirthdayStr() {
        String birthdayStr = "";
        if (birthday != null) {
            birthdayStr = DateUtils.formatDate(birthday);
        }
        return birthdayStr;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = "ID_TYPE")
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Column(name = "ID_NO")
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Column(name = "POLITICAL_STATUS")
    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    @Column(name = "DUTY")
    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Column(name = "NATIVE_PLACE")
    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    @Column(name = "NATION")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Column(name = "SOLDIER_FLAG")
    public String getSoldierFlag() {
        return soldierFlag;
    }

    public void setSoldierFlag(String soldierFlag) {
        this.soldierFlag = soldierFlag;
    }

    @Column(name = "RANK")
    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Column(name = "SORT")
    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }
    
    @Column(name = "AUTHORIZED_FLAG")
    public String getAuthorizedFlag() {
        return authorizedFlag;
    }

    public void setAuthorizedFlag(String authorizedFlag) {
        this.authorizedFlag = authorizedFlag;
    }

    @Column(name = "LEADER_FLAG")
    public String getLeaderFlag() {
        return leaderFlag;
    }

    public void setLeaderFlag(String leaderFlag) {
        this.leaderFlag = leaderFlag;
    }

    @Column(name = "OFFICER_NUM")
    public String getOfficerNum() {
        return officerNum;
    }

    public void setOfficerNum(String officerNum) {
        this.officerNum = officerNum;
    }

    @Column(name = "ID_NUM")
    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
}