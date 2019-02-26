package com.kingtake.office.entity.receivebill;

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
import org.jeecgframework.core.annotation.log.LogEntity;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;

/**
 * @Title: Entity
 * @Description: 收文阅批单信息表
 * @author onlineGenerator
 * @date 2015-07-17 15:43:38
 * @version V1.0
 *
 */
@Entity
@Table(name = "t_o_receive_bill", schema = "")
@SuppressWarnings("serial")
@LogEntity("收发文阅批单")
public class TOReceiveBillEntity implements java.io.Serializable {
    /** 主键 */
    private java.lang.String id;
    /** 来文单位id */
    private java.lang.String receiveUnitId;
    /** 标题 */
    @FieldDesc("标题")
    @Excel(name = "标题")
    private java.lang.String title;
    /** 来文单位名 */
    @FieldDesc("来文单位")
    @Excel(name = "来文单位")
    private java.lang.String receiveUnitName;
    /** 序号 */
    @FieldDesc("公文编号")
    @Excel(name = "公文编号")
    private java.lang.String billNum;
    @FieldDesc("文号前缀")
    @Excel(name = "文号前缀")
    private java.lang.String fileNumPrefix;
    @FieldDesc("公文年度")
    @Excel(name = "公文年度")
    private java.lang.String fileNumYear;
    /** 密级 */
    @FieldDesc("密级")
    @Excel(name = "密级", isExportConvert = true)
    private java.lang.String secrityGrade;
    /** 校首长阅批 */
    @FieldDesc("校首长阅批")
    @Excel(name = "校首长阅批")
    private java.lang.String leaderReview;
    /** 领导阅批 */
    @FieldDesc("领导阅批")
    @Excel(name = "领导阅批")
    private java.lang.String officeReview;
    /** 承办单位id */
    private String dutyId;
    /** 承办单位 */
    @FieldDesc("承办单位")
    @Excel(name = "承办单位")
    private java.lang.String dutyName;
    /** 承办单位意见 */
    @FieldDesc("承办单位意见")
    @Excel(name = "承办单位意见")
    private java.lang.String dutyOpinion;
    /** 联系人id */
    private java.lang.String contactId;
    /** 联系人姓名 */
    @FieldDesc("联系人")
    @Excel(name = "联系人")
    private java.lang.String contactName;
    /** 电话 */
    @FieldDesc("联系电话")
    @Excel(name = "联系电话")
    private java.lang.String contactTel;
    /** 登记人id */
    private java.lang.String registerId;
    /** 登记人姓名 */
    @FieldDesc("登记人姓名")
    @Excel(name = "登记人姓名")
    private java.lang.String registerName;
    /** 登记人部门id */
    private java.lang.String registerDepartId;
    /** 登记人部门名称 */
    @FieldDesc("登记人部门")
    @Excel(name = "登记人部门")
    private java.lang.String registerDepartName;
    /** 登记日期 */
    @FieldDesc("登记日期")
    @Excel(name = "登记日期")
    private java.util.Date registerTime;
    /** 公文状态 */
    @FieldDesc("公文状态")
    @Excel(name = "公文状态", isExportConvert = true)
    private java.lang.String archiveFlag;
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
    /** 驳回人 id */
    @FieldDesc("驳回人id")
    private String backUserId;
    /** 驳回人 */
    @FieldDesc("驳回人")
    @Excel(name = "驳回人")
    private String backUserName;
    /** 驳回人意见 */
    @FieldDesc("驳回人意见")
    @Excel(name = "驳回人意见")
    private String Suggestion;
    /** 公文种类 */
    @FieldDesc("公文种类")
    @Excel(name = "公文种类", isExportConvert = true)
    private String regType;
    /** 收发文登记主键 */
    @FieldDesc("收发文登记主键")
    private String regId;
    /** 收发文标识 */
    @FieldDesc("收发文标识")
    private String registerType;
    /** 正文文件id */
    @FieldDesc("正文文件id")
    private String contentFileId;
    @FieldDesc("附件")
    private List<TSFilesEntity> certificates;// 附件
    //@ExcelCollection(name = "接收人", orderNum = "1")
    private List<TOFlowReceiveLogsEntity> receive;

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
     * @return: java.lang.String 来文单位id
     */
    @Column(name = "RECEIVE_UNIT_ID", nullable = true, length = 32)
    public java.lang.String getReceiveUnitId() {
        return this.receiveUnitId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 来文单位id
     */
    public void setReceiveUnitId(java.lang.String receiveUnitId) {
        this.receiveUnitId = receiveUnitId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 来文单位名
     */
    @Column(name = "RECEIVE_UNIT_NAME", nullable = true, length = 60)
    public java.lang.String getReceiveUnitName() {
        return this.receiveUnitName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 来文单位名
     */
    public void setReceiveUnitName(java.lang.String receiveUnitName) {
        this.receiveUnitName = receiveUnitName;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer 序号
     */
    @Column(name = "BILL_NUM", nullable = true, length = 20)
    public java.lang.String getBillNum() {
        return this.billNum;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer 序号
     */
    public void setBillNum(java.lang.String billNum) {
        this.billNum = billNum;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 密级
     */
    @Column(name = "SECRITY_GRADE", nullable = true, length = 1)
    public java.lang.String getSecrityGrade() {
        return this.secrityGrade;
    }

    public java.lang.String convertGetSecrityGrade() {
        if (StringUtil.isNotEmpty(this.secrityGrade)) {
            return SrmipConstants.dicStandardMap.get("XMMJ").get(this.secrityGrade).toString();
        }
        return this.secrityGrade;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 密级
     */
    public void setSecrityGrade(java.lang.String secrityGrade) {
        this.secrityGrade = secrityGrade;
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
     * @return: java.lang.String 校首长阅批
     */
    @Column(name = "LEADER_REVIEW", nullable = true, length = 100)
    public java.lang.String getLeaderReview() {
        return this.leaderReview;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 校首长阅批
     */
    public void setLeaderReview(java.lang.String leaderReview) {
        this.leaderReview = leaderReview;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 领导阅批
     */
    @Column(name = "OFFICE_REVIEW", nullable = true, length = 100)
    public java.lang.String getOfficeReview() {
        return this.officeReview;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 领导阅批
     */
    public void setOfficeReview(java.lang.String officeReview) {
        this.officeReview = officeReview;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 承办单位意见
     */
    @Column(name = "DUTY_OPINION", nullable = true, length = 50)
    public java.lang.String getDutyOpinion() {
        return this.dutyOpinion;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 承办单位意见
     */
    public void setDutyOpinion(java.lang.String dutyOpinion) {
        this.dutyOpinion = dutyOpinion;
    }

    @Column(name = "DUTY_ID", nullable = true, length = 32)
    public String getDutyId() {
        return dutyId;
    }

    public void setDutyId(String dutyId) {
        this.dutyId = dutyId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 承办单位
     */
    @Column(name = "DUTY_NAME", nullable = true, length = 60)
    public java.lang.String getDutyName() {
        return this.dutyName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 承办单位
     */
    public void setDutyName(java.lang.String dutyName) {
        this.dutyName = dutyName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人id
     */
    @Column(name = "CONTACT_ID", nullable = true, length = 32)
    public java.lang.String getContactId() {
        return this.contactId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人id
     */
    public void setContactId(java.lang.String contactId) {
        this.contactId = contactId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 联系人姓名
     */
    @Column(name = "CONTACT_NAME", nullable = true, length = 36)
    public java.lang.String getContactName() {
        return this.contactName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 联系人姓名
     */
    public void setContactName(java.lang.String contactName) {
        this.contactName = contactName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 电话
     */
    @Column(name = "CONTACT_TEL", nullable = true, length = 30)
    public java.lang.String getContactTel() {
        return this.contactTel;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 电话
     */
    public void setContactTel(java.lang.String contactTel) {
        this.contactTel = contactTel;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date 登记日期
     */
    @Column(name = "REGISTER_TIME", nullable = true)
    public java.util.Date getRegisterTime() {
        return this.registerTime;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date 登记日期
     */
    public void setRegisterTime(java.util.Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 登记人id
     */
    @Column(name = "REGISTER_ID", nullable = true, length = 32)
    public java.lang.String getRegisterId() {
        return this.registerId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 登记人id
     */
    public void setRegisterId(java.lang.String registerId) {
        this.registerId = registerId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 登记人姓名
     */
    @Column(name = "REGISTER_NAME", nullable = true, length = 36)
    public java.lang.String getRegisterName() {
        return this.registerName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 登记人姓名
     */
    public void setRegisterName(java.lang.String registerName) {
        this.registerName = registerName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 登记人部门id
     */
    @Column(name = "REGISTER_DEPART_ID", nullable = true, length = 32)
    public java.lang.String getRegisterDepartId() {
        return this.registerDepartId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 登记人部门id
     */
    public void setRegisterDepartId(java.lang.String registerDepartId) {
        this.registerDepartId = registerDepartId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 登记人部门名称
     */
    @Column(name = "REGISTER_DEPART_NAME", nullable = true, length = 60)
    public java.lang.String getRegisterDepartName() {
        return this.registerDepartName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 登记人部门名称
     */
    public void setRegisterDepartName(java.lang.String registerDepartName) {
        this.registerDepartName = registerDepartName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String 归档标志
     */
    @Column(name = "ARCHIVE_FLAG", nullable = true, length = 1)
    public java.lang.String getArchiveFlag() {
        return this.archiveFlag;
    }

    public java.lang.String convertGetArchiveFlag() {
        if (StringUtil.isNotEmpty(this.archiveFlag)) {
            return SrmipConstants.dicResearchMap.get("YPDZT").get(this.archiveFlag).toString();
        }
        return this.archiveFlag;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 归档标志
     */
    public void setArchiveFlag(java.lang.String archiveFlag) {
        this.archiveFlag = archiveFlag;
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

    @Column(name = "BACK_USERID", nullable = true, length = 32)
    public String getBackUserId() {
        return backUserId;
    }

    public void setBackUserId(String backUserId) {
        this.backUserId = backUserId;
    }

    @Column(name = "BACK_USERNAME", nullable = true, length = 36)
    public String getBackUserName() {
        return backUserName;
    }

    public void setBackUserName(String backUserName) {
        this.backUserName = backUserName;
    }

    @Column(name = "BACK_SUGGESTION", nullable = true, length = 100)
    public String getSuggestion() {
        return Suggestion;
    }

    public void setSuggestion(String suggestion) {
        Suggestion = suggestion;
    }

    @Column(name = "REG_TYPE", nullable = true, length = 2)
    public String getRegType() {
        return regType;
    }

    public String convertGetRegType() {
        if (StringUtil.isNotEmpty(this.regType)) {
            Map<String, Object> map = SrmipConstants.dicResearchMap.get("GWZL");
            if (map != null) {
                Object obj = map.get(this.regType);
                if (obj != null) {
                    return obj.toString();
                }
            }
        }
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    @Column(name = "REG_ID", nullable = true, length = 32)
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
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

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String 收发文标志
     */
    public void setRegisterType(java.lang.String registerType) {
        this.registerType = registerType;
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

    @Column(name = "CONTENT_FILE_ID", nullable = true, length = 32)
    public String getContentFileId() {
        return contentFileId;
    }

    public void setContentFileId(String contentFileId) {
        this.contentFileId = contentFileId;
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

    @OneToMany(mappedBy = "sendReceiveId", cascade = CascadeType.REMOVE)
    public List<TOFlowReceiveLogsEntity> getReceive() {
        return receive;
    }

    public void setReceive(List<TOFlowReceiveLogsEntity> receive) {
        this.receive = receive;
    }

}
