package com.kingtake.office.service.impl.sendReceive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codecLib.mpa.Constants;

import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.office.entity.approval.TOApprovalEntity;
import com.kingtake.office.entity.receivebill.TOReceiveBillEntity;
import com.kingtake.office.entity.sendReceive.TORegHistoryEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.entity.sendbill.TOSendBillEntity;
import com.kingtake.office.service.sendReceive.TOSendReceiveRegServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

@Service("tOSendReceiveRegService")
@Transactional
public class TOSendReceiveRegServiceImpl extends CommonServiceImpl implements TOSendReceiveRegServiceI {

    public static final String PATH_CLASS_ROOT = Constants.class.getResource("/").getPath();
    public static final String FILE_PATH = PATH_CLASS_ROOT.substring(1, PATH_CLASS_ROOT.length() - 22);

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        // 执行删除操作配置的sql增强
        this.doDelSql((TOSendReceiveRegEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        // 执行新增操作配置的sql增强
        this.doAddSql((TOSendReceiveRegEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        // 执行更新操作配置的sql增强
        this.doUpdateSql((TOSendReceiveRegEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TOSendReceiveRegEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TOSendReceiveRegEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TOSendReceiveRegEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TOSendReceiveRegEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{register_date}", String.valueOf(t.getRegisterDate()));
        sql = sql.replace("#{num}", String.valueOf(t.getNum()));
        sql = sql.replace("#{security_grade}", String.valueOf(t.getSecurityGrade()));
        sql = sql.replace("#{office}", String.valueOf(t.getOffice()));
        sql = sql.replace("#{title}", String.valueOf(t.getTitle()));
        sql = sql.replace("#{keyname}", String.valueOf(t.getKeyname()));
        sql = sql.replace("#{count}", String.valueOf(t.getCount()));
        sql = sql.replace("#{reference_num}", String.valueOf(t.getReferenceNum()));
        sql = sql.replace("#{condition}", String.valueOf(t.getCondition()));
        sql = sql.replace("#{receive_sign}", String.valueOf(t.getReceiveSign()));
        sql = sql.replace("#{sign_time}", String.valueOf(t.getSignTime()));
        sql = sql.replace("#{files_id}", String.valueOf(t.getFilesId()));
        sql = sql.replace("#{result}", String.valueOf(t.getResult()));
        sql = sql.replace("#{remark}", String.valueOf(t.getRemark()));
        sql = sql.replace("#{register_type}", String.valueOf(t.getRegisterType()));
        sql = sql.replace("#{file_num}", String.valueOf(t.getFileNum()));
        sql = sql.replace("#{project_type}", String.valueOf(t.getProjectType()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 根据公文种类查找模板文件
     */
    @Override
    public List<Map<String, Object>> findModelByRegType(String regType) {

        String sql = "SELECT F.*,O.REALPATH AS FILEPATH FROM T_B_CODE_DETAIL T JOIN T_S_OFFICE_MODEL_FILES F ON T.ID = F.CODE_DETAIL_ID JOIN T_O_OFFICE_ONLINE_FILES O ON F.TEMPLATE_FILE_ID = O.ID WHERE  T.CODE_TYPE_ID='40288af64e56bf48014e57136e1f001a' AND T.CODE = '"
                + regType + "' ";
        List<Map<String, Object>> list = this.findForJdbc(sql, null);
        return list;
    }

    /**
     * 将收发文登记中的附件copy到公文
     */
    @Override
    public boolean copyFile(String regId, String billId, String businessType) {
        CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
        cq.eq("bid", regId);
        cq.add();
        List<TSFilesEntity> files = this.getListByCriteriaQuery(cq, false);
        String newFileName = "";
        String inputFile = "";
        String outputFile = "";
        String folder = "";
        for (TSFilesEntity file : files) {
            inputFile = FILE_PATH + file.getRealpath();
            newFileName = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);//自定义文件名称
            folder = file.getRealpath().substring(0, file.getRealpath().lastIndexOf("/") + 1);
            outputFile = FILE_PATH + folder + newFileName + "." + file.getExtend();
            try {
                FileUtils.copyFile(inputFile, outputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            TSFilesEntity newFile = new TSFilesEntity();
            newFile.setBid(billId);
            newFile.setBusinessType(businessType);
            newFile.setIsconvert("0");
            newFile.setSwfpath(null);
            newFile.setRealpath(folder + newFileName + "." + file.getExtend());
            newFile.setAttachmentcontent(file.getAttachmentcontent());
            newFile.setAttachmenttitle(file.getAttachmenttitle());
            newFile.setBusinessKey(file.getBusinessKey());
            newFile.setCreatedate(file.getCreatedate());
            newFile.setExtend(file.getExtend());
            newFile.setNote(file.getNote());
            newFile.setSubclassname(file.getSubclassname());
            newFile.setTSUser(file.getTSUser());
            super.save(newFile);
        }
        return false;
    }

    /**
     * 拷贝正文
     */
    @Override
    public String copyContentFile(String regId) {
        String contentFileId = "";
        TOSendReceiveRegEntity sendReceive = this.commonDao.get(TOSendReceiveRegEntity.class, regId);
        if(sendReceive!=null&&StringUtils.isNotEmpty(sendReceive.getContentFileId())){
            TOOfficeOnlineFilesEntity contentFile = this.commonDao.get(TOOfficeOnlineFilesEntity.class,
                    sendReceive.getContentFileId());
            TOOfficeOnlineFilesEntity newFile = new TOOfficeOnlineFilesEntity();
            try {
                MyBeanUtils.copyBeanNotNull2Bean(contentFile, newFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String baseDir = new File(ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/"))
                    .getParent() + "/";
            String realPath = contentFile.getRealpath();
            String oldPath = baseDir + realPath;
            String newFileName = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);//自定义文件名称
            String oldFileName = realPath.substring(realPath.lastIndexOf("/") + 1, realPath.lastIndexOf("."));
            String newRealPath = realPath.replace(oldFileName, newFileName);
            String newPath = baseDir + newRealPath;
            try {
                FileUtils.copyFile(oldPath, newPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            newFile.setRealpath(newRealPath);
            newFile.setCreateDate(new Date());
            this.commonDao.save(newFile);
            contentFileId = newFile.getId();
        }
        return contentFileId;
    }

    @Override
    public void updateBill(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request) {
        try {
            TOSendReceiveRegEntity t = this.commonDao.get(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
            String filesId = tOSendReceiveReg.getFilesId();
            Boolean typeFlag = true;
            if (StringUtils.isNotEmpty(filesId)) {
                typeFlag = filesId.equals(t.getFilesId());//判断案卷号是否发生改变
            }
            MyBeanUtils.copyBeanNotNull2Bean(tOSendReceiveReg, t);
            String merge = (StringUtils.isEmpty(t.getFileNumPrefix()) ? "" : t.getFileNumPrefix())
                    + (StringUtils.isEmpty(t.getFileNumYear()) ? "" : ("〔20" + t.getFileNumYear()) + "〕")
                    + t.getFileNum();
            t.setMergeFileNum(merge);
            this.commonDao.saveOrUpdate(t);
            if (!typeFlag) {//案卷号发生变化则变更流水号记录表
                //变更流水号
                String businessCode = request.getParameter("businessCode");
                String currentNum = request.getParameter("currentNum");
                PrimaryGenerater gen = PrimaryGenerater.getInstance();
                gen.updateNext(businessCode, currentNum);
            }
            if ("0".equals(t.getRegisterType())) {//收文
                TOReceiveBillEntity tOReceiveBill = this.commonDao.findUniqueByProperty(TOReceiveBillEntity.class,
                        "regId", t.getId());
                if (tOReceiveBill != null) {
                    tOReceiveBill.setSecrityGrade(t.getSecurityGrade());
                    tOReceiveBill.setTitle(t.getTitle());
                    tOReceiveBill.setBillNum(t.getFileNum());//文号
                    tOReceiveBill.setFileNumPrefix(t.getFileNumPrefix());
                    tOReceiveBill.setFileNumYear(t.getFileNumYear());
                    tOReceiveBill.setReceiveUnitName(t.getOffice());
                    tOReceiveBill.setDutyId(t.getUndertakeDeptId());
                    tOReceiveBill.setDutyName(t.getUndertakeDeptName());
                    tOReceiveBill.setContactId(t.getUndertakeContactId());
                    tOReceiveBill.setContactName(t.getUndertakeContactName());
                    tOReceiveBill.setContactTel(t.getUndertakeTelephone());
                    tOReceiveBill.setContentFileId(t.getContentFileId());
                    this.commonDao.updateEntitie(tOReceiveBill);
                    this.delAttachementByBid(tOReceiveBill.getId());//先删除原有附件，再添加
                    this.copyFile(t.getId(), tOReceiveBill.getId(), "tOReceiveBill");
                }
            } else if ("1".equals(t.getRegisterType())) {//发文
                if ("13".equals(t.getRegType())) {//呈批件
                    TOApprovalEntity tOApproval = this.commonDao.findUniqueByProperty(TOApprovalEntity.class, "regId",
                            t.getId());
                    if (tOApproval != null) {
                        tOApproval.setApplicationFileno(t.getFileNum());
                        tOApproval.setSecrityGrade(t.getSecurityGrade());
                        tOApproval.setTitle(t.getTitle());
                        tOApproval.setApprovalYear(t.getFileNumYear());
                        tOApproval.setFileNumPrefix(t.getFileNumPrefix());
                        tOApproval.setProjectRelaId(t.getProjectRelaId());
                        tOApproval.setProjectRelaName(t.getProjectRelaName());
                        tOApproval.setContactId(t.getUndertakeContactId());
                        tOApproval.setContactName(t.getUndertakeContactName());
                        tOApproval.setContactPhone(t.getUndertakeTelephone());
                        tOApproval.setUndertakeUnitId(t.getUndertakeDeptId());
                        tOApproval.setUndertakeUnitName(t.getUndertakeDeptName());
                        tOApproval.setContentFileId(t.getContentFileId());
                        this.commonDao.updateEntitie(tOApproval);
                        this.delAttachementByBid(tOApproval.getId());//先删除原有附件，再添加
                        this.copyFile(t.getId(), tOApproval.getId(), "tOApproval");
                    }
                } else {
                    TOSendBillEntity tOSendBill = this.commonDao.findUniqueByProperty(TOSendBillEntity.class, "regId",
                            t.getId());
                    if (tOSendBill != null) {
                        tOSendBill.setSecrityGrade(t.getSecurityGrade());
                        tOSendBill.setSendTitle(t.getTitle());
                        tOSendBill.setSendNum(t.getFileNum());
                        tOSendBill.setFileNumPrefix(t.getFileNumPrefix());
                        tOSendBill.setSendYear(t.getFileNumYear());
                        tOSendBill.setSendTypeCode(t.getRegType());
                        tOSendBill.setPrintNum(t.getCount());//印数
                        tOSendBill.setSendUnit(t.getOffice());
                        tOSendBill.setUndertakeUnitId(t.getUndertakeDeptId());
                        tOSendBill.setUndertakeUnitName(t.getUndertakeDeptName());
                        tOSendBill.setContactId(t.getUndertakeContactId());
                        tOSendBill.setContactName(t.getUndertakeContactName());
                        tOSendBill.setContactPhone(t.getUndertakeTelephone());
                        tOSendBill.setContentFileId(t.getContentFileId());
                        this.commonDao.updateEntitie(tOSendBill);
                        this.delAttachementByBid(tOSendBill.getId());//先删除原有附件，再添加
                        this.copyFile(t.getId(), tOSendBill.getId(), "tOSendBill");
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException("更新公文失败", e);
        }
    }

    @Override
    public void doCopy(TOSendReceiveRegEntity tOSendReceiveReg) {
        tOSendReceiveReg = this.commonDao.get(TOSendReceiveRegEntity.class, tOSendReceiveReg.getId());
        TOSendReceiveRegEntity reg = new TOSendReceiveRegEntity();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOSendReceiveReg, reg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reg.setId(null);
        reg.setCertificates(null);
        reg.setCreateDate(new Date());
        reg.setGenerationFlag("0");
        this.commonDao.save(reg);
        this.copyFile(tOSendReceiveReg.getId(), reg.getId(), "tOSendReceiveReg");
        String contentFileId = copyContentFile(tOSendReceiveReg.getId());
        tOSendReceiveReg.setContentFileId(contentFileId);
        this.commonDao.updateEntitie(tOSendReceiveReg);
    }

    @Override
    public void doHistory(TOSendReceiveRegEntity tOSendReceiveReg) {
        TSUser user = ResourceUtil.getSessionUserName();
        TORegHistoryEntity history = new TORegHistoryEntity();
        history.setCreateUserId(user.getId());
        history.setCreateUserName(user.getRealName());
        history.setCreateTime(new Date());
        history.setTitle(tOSendReceiveReg.getTitle());
        history.setAttachmentCode(UUIDGenerator.generate());
        history.setRegId(tOSendReceiveReg.getId());
        this.commonDao.save(history);
        this.copyFile(tOSendReceiveReg.getId(), history.getAttachmentCode(), "tOSendReceiveReg");
        String contentFileId = this.copyContentFile(tOSendReceiveReg.getId());
        history.setContentFileId(contentFileId);
        this.commonDao.updateEntitie(history);
    }

    @Override
    public String importSendBillExcel(HSSFWorkbook wb) {
    	String msg = "";
        HSSFSheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
        	msg += "EXCEL文档为空，成功导入0条数据！<br>";
            return msg;
        }
        List<TOSendReceiveRegEntity> regList = new ArrayList<TOSendReceiveRegEntity>();
        int rownum = sheet.getLastRowNum();
        int rownumSuccess = 0;
        TSUser user = ResourceUtil.getSessionUserName();
        for (int i = 1; i <= rownum; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
            	msg += "第" + (i + 1) + "行数据为空，导入失败！<br>";
                continue;
            }
            String time = getCellValue(row, 0);
            if(StringUtils.isEmpty(time)){
            	msg += "第" + (i + 1) + "行数据发文时间为空，导入失败！<br>";
                continue;
            }
            HSSFCell cellTime = row.getCell(0);           
            if(cellTime.getCellType() != 1){
            	msg += "第" + (i + 1) + "行数据发文时间格式错误，导入失败！<br>";
                continue;
            }
            TOSendReceiveRegEntity reg = new TOSendReceiveRegEntity();
            String fileNum = getCellValue(row, 1);
            fileNum = fileNum.replaceAll("号", "");
            if(StringUtils.isNotEmpty(fileNum)){
                String prefix = "";
                String year = "";
                String suffix = "";
                int st = fileNum.indexOf("〔");
                int en = fileNum.indexOf("〕");
                if(st!=-1){
                    prefix = fileNum.substring(0, st);
                }
                if(en!=-1){
                    suffix = fileNum.substring(en+1);
                }
                if(st!=-1&&en!=-1){
                    year = fileNum.substring(st+1,en);
                    if(year.length()==4){
                        year = year.substring(2, 4);
                    }
                }
                reg.setFileNumPrefix(prefix);
                reg.setFileNumYear(year);
                reg.setFileNum(suffix);
            }
            String regType = getCellValue(row, 2);
            String secretGrade = getCellValue(row, 3);
            String referenceNum = getCellValue(row, 4);
            String title = getCellValue(row, 5);
            String contactName = getCellValue(row, 6);
            String undertakeUnitName = getCellValue(row, 7);
            String sendUnitName = getCellValue(row, 8);
            String count = getCellValue(row, 9);
            String remark = getCellValue(row, 10);
            
            reg.setCreateBy(user.getUserName());
            reg.setArchiveUserid(user.getUserName());
            reg.setArchiveUsername(user.getRealName());
            if (StringUtils.isNotEmpty(time)) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date createDate = sdf.parse(time);
                    reg.setCreateDate(createDate);
                    reg.setRegisterDate(createDate);
                    reg.setArchiveDate(createDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reg.setMergeFileNum(fileNum);
            reg.setTitle(title);
            if(StringUtils.isNotEmpty(count)){
            	HSSFCell countCell = row.getCell(9);
                if(countCell.getCellType() != 1){
                	msg += "第" + (i + 1) + "行数据份数格式错误，导入失败！<br>";
                    continue;
                }
                String regValidate="^\\d+$";
                if(count.matches(regValidate) == true){
                	reg.setCount(Integer.parseInt(count));
                }
                else{
                	msg += "第" + (i + 1) + "行数据份数格式错误，导入失败！<br>";
                	continue;
                }
            }
            reg.setRemark(remark);
            String regTypeVal = getCodeValue("GWZL", "1", regType);
            reg.setRegType(regTypeVal);
            reg.setRegisterType("1");//发文
            String secretGradeVal = getCodeValue("XMMJ", "0", secretGrade);
            reg.setSecurityGrade(secretGradeVal);
            reg.setReferenceNum(referenceNum);
            TSUser contact = getUserByName(contactName);
            if (contact != null) {
            String phone = "";
            if (StringUtils.isNotEmpty(contact.getOfficePhone())) {
                phone = contact.getOfficePhone();
            } else if (StringUtils.isNotEmpty(contact.getMobilePhone())) {
                phone = contact.getMobilePhone();
            }
            reg.setUndertakeContactId(contact.getId());
                reg.setUndertakeContactName(contact.getRealName());
                reg.setUndertakeTelephone(phone);
                reg.setCreateBy(contact.getUserName());
                reg.setCreateName(contact.getRealName());
            }
            TSDepart undertakeUnit = getDeptByName(undertakeUnitName);
            if (undertakeUnit != null) {
                reg.setUndertakeDeptId(undertakeUnit.getId());
                reg.setUndertakeDeptName(undertakeUnit.getDepartname());
            }
            reg.setOffice(sendUnitName);
            reg.setEauditFlag("0");
            reg.setGenerationFlag(ReceiveBillConstant.BILL_ARCHIVED);
            regList.add(reg);
            rownumSuccess++;
        }
        this.commonDao.batchSave(regList);
        msg += "EXCEL文档共" + rownum + " 条数据，成功导入" + rownumSuccess + "条！";
        return msg;
    }
    
    public String importReceiveBillExcel(HSSFWorkbook wb) {
    	String msg = "";
        HSSFSheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
        	msg += "EXCEL文档为空，成功导入0条数据！<br>";
            return msg;
        }
        List<TOSendReceiveRegEntity> regList = new ArrayList<TOSendReceiveRegEntity>();
        int rownum = sheet.getLastRowNum();
        int rownumSuccess = 0;
        TSUser user = ResourceUtil.getSessionUserName();
        for (int i = 1; i <= rownum; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
            	msg += "第" + (i + 1) + "行数据为空，导入失败！<br>";
                continue;                
            }            
            String time = getCellValue(row, 0);
            if(StringUtils.isEmpty(time)){
            	msg += "第" + (i + 1) + "行数据收文时间为空，导入失败！<br>";
                continue;
            }
            HSSFCell cellTime = row.getCell(0);           
            if(cellTime.getCellType() != 1){
            	msg += "第" + (i + 1) + "行数据收文时间格式错误，导入失败！<br>";
                continue;
            }
            String regFilesId = getCellValue(row, 2);
            String type = "";
            if(StringUtils.isNotEmpty(regFilesId)){
            	type = regFilesId.substring(0, 2);
            }
//            if(StringUtils.isEmpty(regFilesId)){
//            	msg += "第" + i + "条数据收文编号为空，导入失败！<br>";
//                continue;
//            }
            TOSendReceiveRegEntity reg = new TOSendReceiveRegEntity();
            String fileNum = getCellValue(row, 1);
            fileNum = fileNum.replaceAll("号", "");
            if(StringUtils.isNotEmpty(fileNum)){
                String prefix = "";
                String year = "";
                String suffix = "";
                int st = fileNum.indexOf("〔");
                int en = fileNum.indexOf("〕");
                if(st!=-1){
                    prefix = fileNum.substring(0, st);
                }
                if(en!=-1){
                    suffix = fileNum.substring(en+1);
                }
                if(st!=-1&&en!=-1){
                    year = fileNum.substring(st+1,en);
                    if(year.length()==4){
                        year = year.substring(2, 4);
                    }
                }
                reg.setFileNumPrefix(prefix);
                reg.setFileNumYear(year);
                reg.setFileNum(suffix);
            }            
            String regType = getCellValue(row, 3);
            String secretGrade = getCellValue(row, 4);
            String referenceNum = getCellValue(row, 5);
            String title = getCellValue(row, 6);
            String contactName = getCellValue(row, 7);
            String undertakeUnitName = getCellValue(row, 8);
            String sendUnitName = getCellValue(row, 9);            
            String count = getCellValue(row, 10);
            if(StringUtils.isNotEmpty(count)){
            	HSSFCell countCell = row.getCell(10);
                if(countCell.getCellType() != 1){
                	msg += "第" + (i + 1) + "行数据份数格式错误，导入失败！<br>";
                    continue;
                }
                String regValidate="^\\d+$";
                if(count.matches(regValidate) == true){
                	reg.setCount(Integer.parseInt(count));
                }
                else{
                	msg += "第" + (i + 1) + "行数据份数格式错误，导入失败！<br>";
                	continue;
                }
            }            
            String remark = getCellValue(row, 11);
            
            reg.setCreateBy(user.getUserName());
            reg.setArchiveUserid(user.getUserName());
            reg.setArchiveUsername(user.getRealName());
            if (StringUtils.isNotEmpty(time)) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date createDate = sdf.parse(time);
                    reg.setCreateDate(createDate);
                    reg.setRegisterDate(createDate);
                    reg.setArchiveDate(createDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
                       
            reg.setMergeFileNum(fileNum);
            reg.setTitle(title);            
            reg.setRemark(remark);
            reg.setFilesId(regFilesId);
            reg.setType(type);
            String regTypeVal = getCodeValue("GWZL", "1", regType);
            reg.setRegType(regTypeVal);
            reg.setRegisterType("0");//收文
            String secretGradeVal = getCodeValue("XMMJ", "0", secretGrade);
            reg.setSecurityGrade(secretGradeVal);
            reg.setReferenceNum(referenceNum);
            TSUser contact = getUserByName(contactName);
            if (contact != null) {
            String phone = "";
            if (StringUtils.isNotEmpty(contact.getOfficePhone())) {
                phone = contact.getOfficePhone();
            } else if (StringUtils.isNotEmpty(contact.getMobilePhone())) {
                phone = contact.getMobilePhone();
            }
            reg.setUndertakeContactId(contact.getId());
                reg.setUndertakeContactName(contact.getRealName());
                reg.setUndertakeTelephone(phone);                
            }
            
            TSDepart undertakeUnit = getDeptByName(undertakeUnitName);
            if (undertakeUnit != null) {
                reg.setUndertakeDeptId(undertakeUnit.getId());
                reg.setUndertakeDeptName(undertakeUnit.getDepartname());
            }
            reg.setOffice(sendUnitName);
            reg.setEauditFlag("0");
            reg.setGenerationFlag(ReceiveBillConstant.BILL_ARCHIVED);
            regList.add(reg);
            rownumSuccess++;
        }
        this.commonDao.batchSave(regList);
        msg += "EXCEL文档共" + rownum + " 条数据，成功导入" + rownumSuccess + "条！";
        return msg;
    }

    /**
     * 获取单元格值
     * 
     * @param row
     * @param index
     * @return
     */
    private String getCellValue(HSSFRow row, int index) {
        HSSFCell cell = row.getCell(index);
        if(cell==null){
            return "";
        }
        int cellType = cell.getCellType();
        String value = "";
        if (HSSFCell.CELL_TYPE_NUMERIC == cellType) {
            value = String.valueOf(cell.getNumericCellValue());
        } else {
            value = cell.getStringCellValue();
        }
        return value;
    }

    //根据代码集的名称得到值
    private String getCodeValue(String code, String codeType, String text) {
        String sql = "select d.code from t_b_code_type t join t_b_code_detail d "
                + "on t.id=d.code_type_id where t.code=? and t.code_type=? and d.name=?";
        List<Map<String, Object>> listMap = this.commonDao.findForJdbc(sql, new Object[] { code, codeType, text });
        if(listMap.size()>0){
            Map<String, Object> map = listMap.get(0);
            String codeValue = (String) map.get("code");
            return codeValue;
        }
        return "";
    }

    //通过姓名获取用户
    private TSUser getUserByName(String name) {
        List<TSUser> userList = this.commonDao.findByProperty(TSUser.class, "realName", name);
        if (userList.size() > 0) {
            TSUser user = userList.get(0);
            return user;
        }
        return null;
    }

    //通过单位名获取单位
    private TSDepart getDeptByName(String name) {
        List<TSDepart> deptList = this.commonDao.findByProperty(TSDepart.class, "departname", name);
        if (deptList.size() > 0) {
            TSDepart dept = deptList.get(0);
            return dept;
        }
        return null;
    }

    @Override
    public void downloadTemplate(String regesterType, HttpServletRequest request,HttpServletResponse response) {
        OutputStream out = null;
        InputStream templateIs = null;
        try {
            String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
            String fileName = "";
            if ("1".equals(regesterType)) {
                templateIs = new FileInputStream(classPath + "export" + File.separator + "template" + File.separator
                        + "sendReceiveTemplate.xls");
                fileName = "发文登记导入模板.xls";
            }
            else if ("0".equals(regesterType)){
            	templateIs = new FileInputStream(classPath + "export" + File.separator + "template" + File.separator
                        + "toSendReceiveTemplate.xls");
                fileName = "收文登记导入模板.xls";
            }
            fileName = POIPublicUtil
                    .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            out = response.getOutputStream();
            IOUtils.copy(templateIs, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("导出模板失败", e);
        } finally {
            IOUtils.closeQuietly(templateIs);
            IOUtils.closeQuietly(out);
        }
    }

}