package org.jeecgframework.web.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpClientUtil;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.dao.JeecgDictDao;
import org.jeecgframework.web.system.dao.TBLogDao;
import org.jeecgframework.web.system.pojo.base.DictEntity;
import org.jeecgframework.web.system.pojo.base.TBLog;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSIcon;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kingtake.common.util.CodeGenerateUtils;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
    @Autowired
    private JeecgDictDao jeecgDictDao;

    @Autowired
    private TBLogDao tbLogDao;

    @Override
    public TSUser checkUserExits(TSUser user) throws Exception {
        return commonDao.getUserByUserIdAndUserNameExits(user);
    }

    @Override
    public List<DictEntity> queryDict(String dicTable, String dicCode, String dicText) {
        List<DictEntity> dictList = null;
        // step.1 如果没有字典表则使用系统字典表
        if (StringUtil.isEmpty(dicTable)) {
            dictList = jeecgDictDao.querySystemDict(dicCode);
        } else {
            dicText = StringUtil.isEmpty(dicText, dicCode);
            dictList = jeecgDictDao.queryCustomDict(dicTable, dicCode, dicText);
        }
        return dictList;
    }

    /**
     * 添加日志
     */
    @Override
    public void addLog(String logcontent, Short operatetype, Short loglevel) {
        HttpServletRequest request = ContextHolderUtils.getRequest();
        String broswer = BrowserUtils.checkBrowse(request);
        TSLog log = new TSLog();
        log.setLogcontent(logcontent);
        log.setLoglevel(loglevel);
        log.setOperatetype(operatetype);
        log.setNote(oConvertUtils.getIp());
        log.setBroswer(broswer);
        log.setOperatetime(DateUtils.gettimestamp());
        log.setTSUser(ResourceUtil.getSessionUserName());
        commonDao.save(log);
    }

    /**
     * 添加业务日志
     */
    @Override
    public void addBusinessLog(String opObjName, Short operatetype, Short loglevel, String logcontent, String oldJson,
            String newJson) {
        HttpServletRequest request = ContextHolderUtils.getRequest();
        String broswer = null;
        if (request != null) {
            broswer = BrowserUtils.checkBrowse(request);
        }
        TBLog log = new TBLog();
        log.setId(UUID.randomUUID().toString().replace("-", ""));
        log.setLogcontent(logcontent);
        log.setLoglevel(loglevel);
        log.setOperatetype(operatetype);
        log.setIp(oConvertUtils.getIp());
        log.setBrowser(broswer);
        log.setOperatetime(DateUtils.gettimestamp());
        TSUser user = ResourceUtil.getSessionUserName();
        if (user != null) {
            log.setTSUser(user);
            log.setUsername(user.getRealName());
        } else {
            log.setUsername("系统");
        }
        log.setOpObjectName(opObjName);
        log.setOldJson(oldJson);
        log.setNewJson(newJson);
        tbLogDao.insert(log);
    }

    /**
     * 根据类型编码和类型名称获取Type,如果为空则创建一个
     * 
     * @param typecode
     * @param typename
     * @return
     */
    @Override
    public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup) {
        TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode);
        if (actType == null) {
            actType = new TSType();
            actType.setTypecode(typecode);
            actType.setTypename(typename);
            actType.setTSTypegroup(tsTypegroup);
            commonDao.save(actType);
        }
        return actType;

    }

    /**
     * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
     * 
     * @param typecode
     * @param typename
     * @return
     */
    @Override
    public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
        TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupcode);
        if (tsTypegroup == null) {
            tsTypegroup = new TSTypegroup();
            tsTypegroup.setTypegroupcode(typegroupcode);
            tsTypegroup.setTypegroupname(typgroupename);
            commonDao.save(tsTypegroup);
        }
        return tsTypegroup;
    }

    @Override
    public TSTypegroup getTypeGroupByCode(String typegroupCode) {
        TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
        return tsTypegroup;
    }

    @Override
    public void initAllTypeGroups() {
        List<TSTypegroup> typeGroups = commonDao.loadAll(TSTypegroup.class);
        for (TSTypegroup tsTypegroup : typeGroups) {
            TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
            List<TSType> types = commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
            TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
        }
    }

    @Override
    public void refleshTypesCach(TSType type) {
        TSTypegroup tsTypegroup = type.getTSTypegroup();
        TSTypegroup typeGroupEntity = commonDao.get(TSTypegroup.class, tsTypegroup.getId());
        List<TSType> types = commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
        TSTypegroup.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
    }

    @Override
    public void refleshTypeGroupCach() {
        TSTypegroup.allTypeGroups.clear();
        List<TSTypegroup> typeGroups = commonDao.loadAll(TSTypegroup.class);
        for (TSTypegroup tsTypegroup : typeGroups) {
            TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
        }
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    /**
     * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
     * 
     * @param roleId
     * @param functionId
     * @return
     */
    @Override
    public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
        Set<String> operationCodes = new HashSet<String>();
        TSRole role = commonDao.get(TSRole.class, roleId);
        CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
        cq1.eq("TSRole.id", role.getId());
        cq1.eq("TSFunction.id", functionId);
        cq1.add();
        List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
        if (null != rFunctions && rFunctions.size() > 0) {
            TSRoleFunction tsRoleFunction = rFunctions.get(0);
            if (null != tsRoleFunction.getOperation()) {
                String[] operationArry = tsRoleFunction.getOperation().split(",");
                for (int i = 0; i < operationArry.length; i++) {
                    operationCodes.add(operationArry[i]);
                }
            }
        }
        return operationCodes;
    }

    /**
     * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
     * 
     * @param roleId
     * @param functionId
     * @return
     */
    @Override
    public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId) {
        Set<String> operationCodes = new HashSet<String>();
        List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
        for (TSRoleUser ru : rUsers) {
            TSRole role = ru.getTSRole();
            CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
            cq1.eq("TSRole.id", role.getId());
            cq1.eq("TSFunction.id", functionId);
            cq1.add();
            List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
            if (null != rFunctions && rFunctions.size() > 0) {
                TSRoleFunction tsRoleFunction = rFunctions.get(0);
                if (null != tsRoleFunction.getOperation()) {
                    String[] operationArry = tsRoleFunction.getOperation().split(",");
                    for (int i = 0; i < operationArry.length; i++) {
                        operationCodes.add(operationArry[i]);
                    }
                }
            }
        }
        return operationCodes;
    }

    // ----------------------------------------------------------------
    // ----------------------------------------------------------------

    @Override
    public void flushRoleFunciton(String id, TSFunction newFunction) {
        TSFunction functionEntity = this.getEntity(TSFunction.class, id);
        if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
            return;
        }
        TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getTSIcon().getId());
        if (!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
            // 刷新缓存
            HttpSession session = ContextHolderUtils.getSession();
            TSUser user = ResourceUtil.getSessionUserName();
            List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
            for (TSRoleUser ru : rUsers) {
                TSRole role = ru.getTSRole();
                session.removeAttribute(role.getId());
            }
        }
    }

    @Override
    public String generateOrgCode(String id, String pid) {
        String newOrgCode = "";
        if (!StringUtils.hasText(pid)) { // 第一级编码
            String sql = "select t.org_code orgCode from t_s_depart t where t.parentdepartid is null";
            List<Object> pOrgCodeList = commonDao.findListbySql(sql);
            newOrgCode = generateNext("10", pOrgCodeList);//默认开始为10
        } else { // 下级编码
            String sql = "select t.org_code orgCode from t_s_depart t where t.id = ?";
            String subSql = "select t.org_code orgCode from t_s_depart t where t.parentdepartid = '" + pid + "'";
            Map<String, Object> orgCodeMap = commonDao.findOneForJdbc(sql, pid);
            List<Object> orgCodeList = commonDao.findListbySql(subSql);
            String pCode = (String) orgCodeMap.get("orgCode");
            newOrgCode = generateNext(pCode + "00", orgCodeList);
        }
        return newOrgCode;
    }

    /**
     * 生成下一个编码
     * 
     * @param baseCode
     * @param orgCodeList
     * @return
     */
    private String generateNext(String baseCode, List<Object> orgCodeList) {
        boolean flag = true;
        String code = "";
        while(flag){
            baseCode = CodeGenerateUtils.getLayNo(baseCode, baseCode.length());
            if (!orgCodeList.contains(baseCode)) {
                code = baseCode;
                flag = false;
            }
        }
        return code;
    }

    @Override
    public Set<String> getOperationCodesByRoleIdAndruleDataId(String roleId, String functionId) {
        Set<String> operationCodes = new HashSet<String>();
        TSRole role = commonDao.get(TSRole.class, roleId);
        CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
        cq1.eq("TSRole.id", role.getId());
        cq1.eq("TSFunction.id", functionId);
        cq1.add();
        List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
        if (null != rFunctions && rFunctions.size() > 0) {
            TSRoleFunction tsRoleFunction = rFunctions.get(0);
            if (null != tsRoleFunction.getDataRule()) {
                String[] operationArry = tsRoleFunction.getDataRule().split(",");
                for (int i = 0; i < operationArry.length; i++) {
                    operationCodes.add(operationArry[i]);
                }
            }
        }
        return operationCodes;
    }

    @Override
    public Set<String> getOperationCodesByUserIdAndDataId(String userId, String functionId) {
        // TODO Auto-generated method stub
        Set<String> dataRulecodes = new HashSet<String>();
        List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
        for (TSRoleUser ru : rUsers) {
            TSRole role = ru.getTSRole();
            CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
            cq1.eq("TSRole.id", role.getId());
            cq1.eq("TSFunction.id", functionId);
            cq1.add();
            List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
            if (null != rFunctions && rFunctions.size() > 0) {
                TSRoleFunction tsRoleFunction = rFunctions.get(0);
                if (null != tsRoleFunction.getDataRule()) {
                    String[] operationArry = tsRoleFunction.getDataRule().split(",");
                    for (int i = 0; i < operationArry.length; i++) {
                        dataRulecodes.add(operationArry[i]);
                    }
                }
            }
        }
        return dataRulecodes;
    }

    /**
     * 附件删除
     */
    @Override
    public void deleteFile(TSFilesEntity file) {
        // [1].删除附件
        String sql = "select * from t_s_attachment where id = ?";
        Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, file.getId());
        // 附件相对路径
        String realpath = (String) attachmentMap.get("realpath");
        String fileName = FileUtils.getFilePrefix2(realpath);

        // 获取部署项目绝对地址
        String realPath = new File(ContextHolderUtils.getSession().getServletContext().getRealPath("/")).getParent()
                + "/";
        FileUtils.delete(realPath + realpath);
        FileUtils.delete(realPath + fileName + ".html");
        FileUtils.delete(realPath + fileName + ".pdf");
        FileUtils.delete(realPath + fileName + ".swf");
        // [2].删除数据
        commonDao.delete(file);
    }

    /**
     * 根据编码获取附件列表
     * 
     * @param attachmentCode
     * @return
     */
    @Override
    public List<TSFilesEntity> getAttachmentByCode(String attachmentCode, String businessType) {
        CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
        cq.eq("bid", attachmentCode);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(businessType)) {
            cq.eq("businessType", businessType);
        }
        cq.addOrder("createdate", SortDirection.asc);
        cq.add();
        List<TSFilesEntity> fileList = this.commonDao.getListByCriteriaQuery(cq, false);
        return fileList;
    }
    
    @Override
    public void saveFilesToFTP(HttpServletRequest request, TSFilesEntity tsFilesEntity) {
        tsFilesEntity.setCreatedate(new Timestamp(new Date().getTime()));
        UploadFile uploadFile = new UploadFile(request, tsFilesEntity);
        uploadFile.setCusPath("files");
        uploadFile.setSwfpath("swfpath");
        uploadFile.setByteField(null);// 不存二进制内容
        Object object = uploadFile.getObject();
        try {
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            ReflectHelper reflectHelper = new ReflectHelper(uploadFile.getObject());
            String uploadbasepath = "srmip";// 文件上传根目录
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            // 文件数据库保存路径
            String path = uploadbasepath + "/";// 文件保存在硬盘的相对路径
            String fileName = "";
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();// 获取上传文件对象
                fileName = mf.getOriginalFilename();// 获取文件名
                String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
                String myfilename = "";
                String noextfilename = "";//不带扩展名
                tsFilesEntity.setFileSize(mf.getSize());//保存文件大小
                tsFilesEntity.setType("FTP");//标识出ftp标志
                noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);//自定义文件名称
                myfilename = noextfilename + "." + extend;//自定义文件名称

                String savePath = path + myfilename;// 文件保存全路径
                String fileprefixName = FileUtils.getFilePrefix(fileName);
                if (uploadFile.getTitleField() != null) {
                    reflectHelper.setMethodValue(uploadFile.getTitleField(), fileprefixName);// 动态调用set方法给文件对象标题赋值
                }
                if (uploadFile.getExtend() != null) {
                    // 动态调用 set方法给文件对象内容赋值
                    reflectHelper.setMethodValue(uploadFile.getExtend(), extend);
                }
                if (uploadFile.getRealPath() != null) {
                    // 设置文件数据库的物理路径
                    reflectHelper.setMethodValue(uploadFile.getRealPath(), path + myfilename);
                }
                FtpClientUtil.sendFile(savePath, mf.getInputStream());
                saveOrUpdate(object);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }
    
    @Override
    public void viewFTPFile(UploadFile uploadFile) {
        HttpServletResponse response = uploadFile.getResponse();
        response.setContentType("UTF-8");
        response.setCharacterEncoding("UTF-8");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            if (!uploadFile.isView() && uploadFile.getExtend() != null) {
                if (uploadFile.getExtend().equals("text")) {
                    response.setContentType("text/plain;");
                } else if (uploadFile.getExtend().equals("doc")) {
                    response.setContentType("application/msword;");
                } else if (uploadFile.getExtend().equals("xls")) {
                    response.setContentType("application/ms-excel;");
                } else if (uploadFile.getExtend().equals("pdf")) {
                    response.setContentType("application/pdf;");
                } else if (uploadFile.getExtend().equals("jpg") || uploadFile.getExtend().equals("jpeg")) {
                    response.setContentType("image/jpeg;");
                } else {
                    response.setContentType("application/octet-stream;");
                }
                response.setHeader(
                        "Content-disposition",
                        "attachment; filename="
                                + new String((uploadFile.getTitleField() + "." + uploadFile.getExtend())
                                        .getBytes("GBK"), "ISO8859-1"));
                response.setHeader("Content-Length", String.valueOf(uploadFile.getSize()));
            }
            FtpClientUtil.retrieveFile(uploadFile.getRealPath(), os);
            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 附件删除
     */
    @Override
    public void deleteFTPFile(TSFilesEntity file) {
        try{
        // [1].删除附件
        String sql = "select * from t_s_attachment where id = ?";
        Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, file.getId());
        // 附件相对路径
        String realpath = (String) attachmentMap.get("realpath");
        FtpClientUtil.deleteFile(realpath);
        // [2].删除数据
        commonDao.delete(file);
        }catch(Exception e){
            throw new BusinessException(e);
        }
    }
}
