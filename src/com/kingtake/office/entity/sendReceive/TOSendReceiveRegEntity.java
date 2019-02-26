package com.kingtake.office.entity.sendReceive;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.annotation.log.FieldDesc;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;

import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Entity
 * @Description: 收发文登记
 * @author onlineGenerator
 * @date 2015-07-15 19:36:41
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_send_receive_reg", schema = "")
@SuppressWarnings("serial")
public class TOSendReceiveRegEntity implements java.io.Serializable {
    /** 主键 */
    @FieldDesc("主键")
    private java.lang.String id;
    /** 收发文日期 */
    @FieldDesc("收发文日期")
    @Excel(name = "收发文日期", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date registerDate;
    /** 序号 */
    @FieldDesc("序号")
    private java.lang.Integer num;
    /** 密级 */
    @FieldDesc("密级")
    @Excel(name = "密级", isExportConvert = true)
    private java.lang.String securityGrade;
    /** 期限，秘密几年 */
    @FieldDesc("期限")
    @Excel(name = "期限")
    private java.lang.String referenceNum;
    /** 机关 */
    @FieldDesc("来文单位/发往单位")
    @Excel(name = "来文单位/发往单位", isExportConvert = true)
    private java.lang.String office;
    /** 联系人 */
    @FieldDesc("联系人")
    private java.lang.String contact;
    /** 联系电话 */
    @FieldDesc("联系电话")
    private java.lang.String contactPhone;
    /** 标题 */
    @FieldDesc("标题")
    @Excel(name = "标题",width=60)
    private java.lang.String title;
    /** 关键字 */
    @FieldDesc("关键字")
    @Excel(name = "关键字")
    private java.lang.String keyname;
    /** 份数 */
    @FieldDesc("份数")
    @Excel(name = "份数")
    private java.lang.Integer count;
   
    /** 办理情况 */
    @FieldDesc("办理情况")
    private java.lang.String condition;
    /** 收者签名 */
    @FieldDesc("收者签名")
    private java.lang.String receiveSign;
    /** 签名日期 */
    @FieldDesc("签名日期")
    private java.util.Date signTime;
    /** 案卷号 */
    @FieldDesc("收文编号")
    @Excel(name = "收文编号")
    private java.lang.String filesId;
    /** 处理结果 */
    @FieldDesc("处理结果")
    private java.lang.String result;
    /** 备注 */
    @FieldDesc("备注")
    @Excel(name = "备注")
    private java.lang.String remark;
    /** 收发文标志 */
    @FieldDesc("收发文标志")
//    @Excel(name = "收发文标志", isExportConvert = true)
    private java.lang.String registerType;
    /** 前缀+'20'+年度+文号 */
    @FieldDesc("文号")
    private java.lang.String fileNum;
    @FieldDesc("文号前缀")
    private java.lang.String fileNumPrefix;
    @FieldDesc("公文年度")
    private java.lang.String fileNumYear;
    /** 关联项目类型 */
    @FieldDesc("关联项目类型")
    @Excel(name = "关联项目类型", isExportConvert = true)
    private java.lang.String projectType;
    
    /**
     * 是否联合发文,0为否，1为是
     */
    private String isUnion;

    /**
     * 关联项目id
     */
    private String projectRelaId;

    /**
     * 关联项目名称
     */
    private String projectRelaName;
    /** 公文种类 */
    @FieldDesc("公文种类")
    @Excel(name = "公文种类", isExportConvert = true)
    private java.lang.String regType;

    /** 业务类型 */
    @FieldDesc("案卷类型")
    @Excel(name = "案卷类型", isExportConvert = true)
    private java.lang.String type;

    /** 生成表单状态 */
    @FieldDesc("生成表单状态")
    @Excel(name = "生成表单状态", isExportConvert = true)
    private java.lang.String generationFlag;

    /** 合并公文编号 */
    @FieldDesc("公文编号")
    @Excel(name = "公文编号",width=15)
    private java.lang.String mergeFileNum;

    /**
     * 承办单位id
     */
    private String undertakeDeptId;

    /**
     * 承办单位名称
     */
    private String undertakeDeptName;

    /**
     * 承办单位联系人id
     */
    private String undertakeContactId;

    /**
     * 承办单位联系人名称
     */
    private String undertakeContactName;

    /**
     * 承办单位联系人电话
     */
    private String undertakeTelephone;
    /**
     * 发文下达状态，1 已下达
     */
    private String downFlag;

    /**
     * 
     * 正文id
     */
    private String contentFileId;

    /** 归档人id */
    private java.lang.String archiveUserid;
    /** 归档人姓名 */
    @FieldDesc("归档人")
    @Excel(name = "归档人")
    private java.lang.String archiveUsername;
    /** 归档时间 */
    @FieldDesc("归档时间")
    @Excel(name = "归档时间")
    private java.util.Date archiveDate;
    /** 创建人 */
    private java.lang.String createBy;
    /** 创建人姓名 */
    @FieldDesc("创建人姓名")
    private java.lang.String createName;
    /** 创建时间 */
    @FieldDesc("创建时间")
    private java.util.Date createDate;
    /** 修改人 */
    private java.lang.String updateBy;
    /** 修改人姓名 */
    @FieldDesc("修改人姓名")
    private java.lang.String updateName;
    /** 修改时间 */
    @FieldDesc("修改时间")
    private java.util.Date updateDate;

    /** 附件 */
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件

    /**
     * 
     * 是否电子审批，默认1 是，0 否
     */
    private String eauditFlag;
    
    /**
     * 重要级别，0为不重要，1为重要，默认不重要
     */
    private String zyjb;

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
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 收发文日期
     */
    @Column(name = "REGISTER_DATE", nullable = true)
    public java.util.Date getRegisterDate() {
        return this.registerDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 收发文日期
     */
    public void setRegisterDate(java.util.Date registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 序号
     */
    @Column(name = "NUM", nullable = true, length = 3)
    public java.lang.Integer getNum() {
        return this.num;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 序号
     */
    public void setNum(java.lang.Integer num) {
        this.num = num;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 密级
     */
    @Column(name = "SECURITY_GRADE", nullable = true, length = 1)
    public java.lang.String getSecurityGrade() {
        return this.securityGrade;
    }

    public java.lang.String convertGetSecurityGrade() {
        if (StringUtil.isNotEmpty(this.securityGrade)) {
            return SrmipConstants.dicStandardMap.get("XMMJ").get(this.securityGrade).toString();
        }
        return this.securityGrade;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 密级
     */
    public void setSecurityGrade(java.lang.String securityGrade) {
        this.securityGrade = securityGrade;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 机关
     */
    @Column(name = "OFFICE", nullable = true, length = 60)
    public java.lang.String getOffice() {
        return this.office;
    }

    public java.lang.String convertGetOffice() {
        if (StringUtil.isNotEmpty(this.office)) {
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            TSDepart depart = systemService.get(TSDepart.class, this.office);
            if (depart != null) {
                return depart.getDepartname();
            }
        }
        return this.office;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 机关
     */
    public void setOffice(java.lang.String office) {
        this.office = office;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人
     */
    @Column(name = "CONTACT", nullable = true, length = 30)
    public java.lang.String getContact() {
        return this.contact;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人
     */
    public void setContact(java.lang.String contact) {
        this.contact = contact;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系电话
     */
    @Column(name = "CONTACT_PHONE", nullable = true, length = 30)
    public java.lang.String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系电话
     */
    public void setContactPhone(java.lang.String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 标题
     */
    @Column(name = "TITLE", nullable = true, length = 200)
    public java.lang.String getTitle() {
        return this.title;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 标题
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关键字
     */
    @Column(name = "KEYNAME", nullable = true, length = 50)
    public java.lang.String getKeyname() {
        return this.keyname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关键字
     */
    public void setKeyname(java.lang.String keyname) {
        this.keyname = keyname;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 份数
     */
    @Column(name = "COUNT", nullable = true, length = 3)
    public java.lang.Integer getCount() {
        return this.count;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 份数
     */
    public void setCount(java.lang.Integer count) {
        this.count = count;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 号数
     */
    @Column(name = "REFERENCE_NUM", nullable = true, length = 10)
    public java.lang.String getReferenceNum() {
        return this.referenceNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 号数
     */
    public void setReferenceNum(java.lang.String referenceNum) {
        this.referenceNum = referenceNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 办理情况
     */
    @Column(name = "CONDITION", nullable = true, length = 2000)
    public java.lang.String getCondition() {
        return this.condition;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 办理情况
     */
    public void setCondition(java.lang.String condition) {
        this.condition = condition;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 收者签名
     */
    @Column(name = "RECEIVE_SIGN", nullable = true, length = 36)
    public java.lang.String getReceiveSign() {
        return this.receiveSign;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 收者签名
     */
    public void setReceiveSign(java.lang.String receiveSign) {
        this.receiveSign = receiveSign;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 签名日期
     */
    @Column(name = "SIGN_TIME", nullable = true)
    public java.util.Date getSignTime() {
        return this.signTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 签名日期
     */
    public void setSignTime(java.util.Date signTime) {
        this.signTime = signTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 案卷号
     */
    @Column(name = "FILES_ID", nullable = true, length = 10)
    public java.lang.String getFilesId() {
        return this.filesId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 案卷号
     */
    public void setFilesId(java.lang.String filesId) {
        this.filesId = filesId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 处理结果
     */
    @Column(name = "RESULT", nullable = true, length = 100)
    public java.lang.String getResult() {
        return this.result;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 处理结果
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 备注
     */
    @Column(name = "REMARK", nullable = true, length = 500)
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 备注
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 收发文标志
     */
    @Column(name = "REGISTER_TYPE", nullable = true, length = 2)
    public java.lang.String getRegisterType() {
        return this.registerType;
    }

    public java.lang.String convertGetRegisterType() {
        if (StringUtil.isNotEmpty(this.registerType)) {
            return SrmipConstants.dicResearchMap.get("SRFLAG").get(this.registerType).toString();
        }
        return this.registerType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 收发文标志
     */
    public void setRegisterType(java.lang.String registerType) {
        this.registerType = registerType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 文号
     */
    @Column(name = "FILE_NUM", nullable = true, length = 20)
    public java.lang.String getFileNum() {
        return this.fileNum;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 文号
     */
    public void setFileNum(java.lang.String fileNum) {
        this.fileNum = fileNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 关联项目类型
     */
    @Column(name = "PROJECT_TYPE", nullable = true, length = 10)
    public java.lang.String getProjectType() {
        return this.projectType;
    }

    public java.lang.String convertGetProjectType() {
        if (StringUtil.isNotEmpty(this.projectType)) {
            SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
            TBProjectTypeEntity type = systemService.get(TBProjectTypeEntity.class, this.projectType);
            if (type != null) {
                return type.getProjectTypeName();
            }
        }
        return this.projectType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 关联项目类型
     */
    public void setProjectType(java.lang.String projectType) {
        this.projectType = projectType;
    }

    @Column(name = "REG_TYPE", nullable = true, length = 2)
    public java.lang.String getRegType() {
        return regType;
    }

    public java.lang.String convertGetRegType() {
        if (StringUtil.isNotEmpty(this.regType)) {
            Map<String, Object> map = SrmipConstants.dicResearchMap.get("GWZL");
            if (map != null) {
                Object obj = map.get(this.regType);
                if (obj != null) {
                    return obj.toString();
                }
            }
        }
        return this.regType;
    }

    public void setRegType(java.lang.String regType) {
        this.regType = regType;
    }

    @Column(name = "TYPE", nullable = true, length = 2)
    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public java.lang.String convertGetType() {
        if (StringUtil.isNotEmpty(this.type)) {
            Map<String, Object> map = SrmipConstants.dicResearchMap.get("SFWLX");
            if (map != null) {
                Object obj = map.get(this.type);
                if (obj != null) {
                    return obj.toString();
                }
            }
        }
        return this.type;
    }

    @Column(name = "GENERATION_FLAG", nullable = true, length = 2)
    public java.lang.String getGenerationFlag() {
        return generationFlag;
    }

    public java.lang.String convertGetGenerationFlag() {
        if (StringUtil.isNotEmpty(this.generationFlag)) {
            return SrmipConstants.dicResearchMap.get("YPDZT").get(this.generationFlag).toString();
        }
        return this.generationFlag;
    }

    public void setGenerationFlag(java.lang.String generationFlag) {
        this.generationFlag = generationFlag;
    }

    @Column(name = "FILE_NUM_PREFIX", nullable = true, length = 20)
    public java.lang.String getFileNumPrefix() {
        return fileNumPrefix;
    }

    public void setFileNumPrefix(java.lang.String fileNumPrefix) {
        this.fileNumPrefix = fileNumPrefix;
    }

    @Column(name = "FILE_NUM_YEAR", nullable = true, length = 4)
    public java.lang.String getFileNumYear() {
        return fileNumYear;
    }

    public void setFileNumYear(java.lang.String fileNumYear) {
        this.fileNumYear = fileNumYear;
    }

    @Column(name = "MERGE_FILE_NUM", nullable = true, length = 30)
    public java.lang.String getMergeFileNum() {
        return mergeFileNum;
    }

    public void setMergeFileNum(java.lang.String mergeFileNum) {
        this.mergeFileNum = mergeFileNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 归档人id
     */
    @Column(name = "ARCHIVE_USERID", nullable = true, length = 50)
    public java.lang.String getArchiveUserid() {
        return this.archiveUserid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 归档人id
     */
    public void setArchiveUserid(java.lang.String archiveUserid) {
        this.archiveUserid = archiveUserid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 归档人姓名
     */
    @Column(name = "ARCHIVE_USERNAME", nullable = true, length = 50)
    public java.lang.String getArchiveUsername() {
        return this.archiveUsername;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 归档人姓名
     */
    public void setArchiveUsername(java.lang.String archiveUsername) {
        this.archiveUsername = archiveUsername;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 归档时间
     */
    @Column(name = "ARCHIVE_DATE", nullable = true)
    public java.util.Date getArchiveDate() {
        return this.archiveDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 归档时间
     */
    public void setArchiveDate(java.util.Date archiveDate) {
        this.archiveDate = archiveDate;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bid")
    @OrderBy("createdate")
    public List<TSFilesEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<TSFilesEntity> certificates) {
        this.certificates = certificates;
    }

    @Column(name = "undertake_dept_id")
    public String getUndertakeDeptId() {
        return undertakeDeptId;
    }

    public void setUndertakeDeptId(String undertakeDeptId) {
        this.undertakeDeptId = undertakeDeptId;
    }

    @Column(name = "undertake_dept_name")
    public String getUndertakeDeptName() {
        return undertakeDeptName;
    }

    public void setUndertakeDeptName(String undertakeDeptName) {
        this.undertakeDeptName = undertakeDeptName;
    }

    @Column(name = "undertake_contact_id")
    public String getUndertakeContactId() {
        return undertakeContactId;
    }

    public void setUndertakeContactId(String undertakeContactId) {
        this.undertakeContactId = undertakeContactId;
    }

    @Column(name = "undertake_contact_name")
    public String getUndertakeContactName() {
        return undertakeContactName;
    }

    public void setUndertakeContactName(String undertakeContactName) {
        this.undertakeContactName = undertakeContactName;
    }

    @Column(name = "undertake_telephone")
    public String getUndertakeTelephone() {
        return undertakeTelephone;
    }

    public void setUndertakeTelephone(String undertakeTelephone) {
        this.undertakeTelephone = undertakeTelephone;
    }

    @Column(name = "PROJECT_RELA_ID")
    public String getProjectRelaId() {
        return projectRelaId;
    }

    public void setProjectRelaId(String projectRelaId) {
        this.projectRelaId = projectRelaId;
    }

    @Column(name = "PROJECT_RELA_NAME")
    public String getProjectRelaName() {
        return projectRelaName;
    }

    public void setProjectRelaName(String projectRelaName) {
        this.projectRelaName = projectRelaName;
    }

    @Column(name = "content_file_id")
    public String getContentFileId() {
        return contentFileId;
    }

    public void setContentFileId(String contentFileId) {
        this.contentFileId = contentFileId;
    }

    @Column(name = "down_flag")
    public String getDownFlag() {
        return downFlag;
    }

    public void setDownFlag(String downFlag) {
        this.downFlag = downFlag;
    }

    @Column(name = "eaudit_flag")
    public String getEauditFlag() {
        return eauditFlag;
    }

    public void setEauditFlag(String eauditFlag) {
        this.eauditFlag = eauditFlag;
    }

    @Column(name="isUnion")
    public String getIsUnion() {
        return isUnion;
    }

    public void setIsUnion(String isUnion) {
        this.isUnion = isUnion;
    }

    @Column(name="zyjb")
    public String getZyjb() {
        return zyjb;
    }

    public void setZyjb(String zyjb) {
        this.zyjb = zyjb;
    }

    
}
