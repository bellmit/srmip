package com.kingtake.office.service.impl.disk;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpClientUtil;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kingtake.office.entity.disk.TODiskEntity;
import com.kingtake.office.entity.disk.TOGroupEntity;
import com.kingtake.office.entity.disk.TOGroupMemberEntity;
import com.kingtake.office.service.disk.TODiskServiceI;
import com.kingtake.project.entity.dbimport.TPmDBImportEntity;
import com.kingtake.solr.SolrOperate;

@Service("tODiskService")
@Transactional
public class TODiskServiceImpl extends CommonServiceImpl implements TODiskServiceI {

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TPmDBImportEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TPmDBImportEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPmDBImportEntity t) {
        return true;
    }

    @Override
    public void saveDiskFiles(HttpServletRequest request, TSFilesEntity tsFilesEntity) {
        TSUser user = ResourceUtil.getSessionUserName();
        String uploadType = request.getParameter("uploadType");
        TODiskEntity disEntity = new TODiskEntity();
        if ("group".equals(uploadType)) {
            String groupId = request.getParameter("groupId");
            disEntity.setGroupId(groupId);
        }
        disEntity.setUserId(user.getId());
        disEntity.setUserName(user.getRealName());
        disEntity.setUploadTime(new Date());
        disEntity.setUploadType(uploadType);
        disEntity.setAttachmentCode(UUIDGenerator.generate());
        this.commonDao.save(disEntity);
        tsFilesEntity.setBid(disEntity.getAttachmentCode());
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
            String uploadbasepath = "disk";// 文件上传根目录
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            // 文件数据库保存路径
            String path = uploadbasepath + "/";// 文件保存在硬盘的相对路径
            String realPath = new File(uploadFile.getMultipartRequest().getSession().getServletContext()
                    .getRealPath("/")).getParent()
                    + "/" + path;// 文件的硬盘真实路径
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();// 创建根目录
            }
            if (uploadFile.getCusPath() != null) {
                realPath += uploadFile.getCusPath() + "/";
                path += uploadFile.getCusPath() + "/";
                file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();// 创建文件自定义子目录
                }
            } else {
                realPath += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
                path += DateUtils.getDataString(DateUtils.yyyyMMdd) + "/";
                file = new File(realPath);
                if (!file.exists()) {
                    file.mkdir();// 创建文件时间子目录
                }
            }
            String fileName = "";
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();// 获取上传文件对象
                fileName = mf.getOriginalFilename();// 获取文件名
                String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
                String myfilename = "";
                String noextfilename = "";//不带扩展名
                if (uploadFile.isRename()) {
                    noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);//自定义文件名称
                    myfilename = noextfilename + "." + extend;//自定义文件名称
                } else {
                    myfilename = fileName;
                }

                String savePath = realPath + myfilename;// 文件保存全路径
                String fileprefixName = FileUtils.getFilePrefix(fileName);
                if (uploadFile.getTitleField() != null) {
                    reflectHelper.setMethodValue(uploadFile.getTitleField(), fileprefixName);// 动态调用set方法给文件对象标题赋值
                }
                if (uploadFile.getExtend() != null) {
                    // 动态调用 set方法给文件对象内容赋值
                    reflectHelper.setMethodValue(uploadFile.getExtend(), extend);
                }
                File savefile = new File(savePath);
                if (uploadFile.getRealPath() != null) {
                    // 设置文件数据库的物理路径
                    reflectHelper.setMethodValue(uploadFile.getRealPath(), path + myfilename);
                }
                saveOrUpdate(object);
                FileCopyUtils.copy(mf.getBytes(), savefile);

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if ("public".equals(uploadType)) {//公共网盘的附件才会保存索引
            // 保存索引
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ID", tsFilesEntity.getId());
            map.put("TITLE", tsFilesEntity.getAttachmenttitle());
            map.put("PATH", tsFilesEntity.getRealpath());
            map.put("EXTEND", tsFilesEntity.getExtend());
            list.add(map);
            String realPath = new File(request.getRealPath("/")).getParent();
            SolrOperate.addFilesIndexNoContent(list, realPath);
        }
    }

    @Override
    public void saveDiskToFTP(HttpServletRequest request, TSFilesEntity tsFilesEntity) {
        TSUser user = ResourceUtil.getSessionUserName();
        String uploadType = request.getParameter("uploadType");
        TODiskEntity disEntity = new TODiskEntity();
        if ("group".equals(uploadType)) {
            String groupId = request.getParameter("groupId");
            disEntity.setGroupId(groupId);
        }
        disEntity.setUserId(user.getId());
        disEntity.setUserName(user.getRealName());
        disEntity.setUploadTime(new Date());
        disEntity.setUploadType(uploadType);
        disEntity.setAttachmentCode(UUIDGenerator.generate());
        this.commonDao.save(disEntity);
        tsFilesEntity.setBid(disEntity.getAttachmentCode());
        tsFilesEntity.setCreatedate(new Timestamp(new Date().getTime()));
        tsFilesEntity.setType("FTP");//标识出ftp标志
        UploadFile uploadFile = new UploadFile(request, tsFilesEntity);
        uploadFile.setCusPath("files");
        uploadFile.setSwfpath("swfpath");
        uploadFile.setByteField(null);// 不存二进制内容
        Object object = uploadFile.getObject();
        try {
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            ReflectHelper reflectHelper = new ReflectHelper(uploadFile.getObject());
            String uploadbasepath = "disk";// 文件上传根目录
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

        if ("public".equals(uploadType)) {//公共网盘的附件才会保存索引
            // 保存索引
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ID", tsFilesEntity.getId());
            map.put("TITLE", tsFilesEntity.getAttachmenttitle());
            map.put("PATH", tsFilesEntity.getRealpath());
            map.put("EXTEND", tsFilesEntity.getExtend());
            map.put("TYPE", "FTP");
            list.add(map);
            String realPath = new File(request.getRealPath("/")).getParent();
            SolrOperate.addFilesIndexNoContent(list, realPath);
        }
    }

    private String toHexString(int index) {
        String hexString = Integer.toHexString(index);
        // 1个byte变成16进制的，只需要2位就可以表示了，取后面两位，去掉前面的符号填充   
        hexString = hexString.substring(hexString.length() - 2);
        return hexString;
    }

    /**
     * 创建群
     */
    @Override
    public void createGroup(TOGroupEntity group) {
        TSUser user = ResourceUtil.getSessionUserName();
        group.setCreate_time(new Date());
        this.commonDao.save(group);
        TOGroupMemberEntity member = new TOGroupMemberEntity();
        member.setGroupId(group.getId());
        member.setIsMgr("1");
        member.setJoinTime(new Date());
        member.setUserId(user.getId());
        member.setUserName(user.getRealName());
        member.setSort(1);
        this.commonDao.save(member);
    }

    @Override
    public void saveGroupMember(String groupId, String userId) {
        CriteriaQuery cq = new CriteriaQuery(TOGroupMemberEntity.class);
        cq.eq("groupId", groupId);
        cq.add();
        List<TOGroupMemberEntity> memberList = this.commonDao.getListByCriteriaQuery(cq, false);
        List<String> memList = new ArrayList<String>();
        for (TOGroupMemberEntity member : memberList) {
            memList.add(member.getUserId());
        }
        String sql = "select max(sort) max from t_o_group_member t where t.group_id=? ";
        Map<String, Object> dataMap = this.commonDao.findOneForJdbc(sql, groupId);
        BigDecimal max = (BigDecimal) dataMap.get("max");
        int index = max == null ? 0 : max.intValue();
        String[] userIdArr = userId.split(",");
        for (String uId : userIdArr) {
            if (memList.contains(uId)) {
                continue;
            }
            index++;
            TSUser user = this.commonDao.get(TSUser.class, uId);
            TOGroupMemberEntity member = new TOGroupMemberEntity();
            member.setGroupId(groupId);
            member.setIsMgr("0");
            member.setJoinTime(new Date());
            member.setSort(index);
            member.setUserId(user.getId());
            member.setUserName(user.getRealName());
            this.commonDao.save(member);
        }

    }

    @Override
    public void removeGroupMember(String groupId, String userId) {
        CriteriaQuery cq = new CriteriaQuery(TOGroupMemberEntity.class);
        cq.eq("groupId", groupId);
        cq.eq("userId", userId);
        cq.add();
        List<TOGroupMemberEntity> memberList = this.commonDao.getListByCriteriaQuery(cq, false);
        for (TOGroupMemberEntity member : memberList) {
            this.commonDao.delete(member);
        }
    }

    @Override
    public void breakGroup(String groupId) {
        String delMemberSql = "delete from t_o_group_member t where t.group_id=?";
        String delGroupSql = "delete from t_o_group t where t.id=?";
        List<TODiskEntity> diskList = this.commonDao.findByProperty(TODiskEntity.class, "groupId", groupId);
        for (TODiskEntity disk : diskList) {
            CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
            cq.eq("bid", disk.getAttachmentCode());
            cq.addOrder("createdate", SortDirection.asc);
            cq.add();
            List<TSFilesEntity> fileList = this.commonDao.getListByCriteriaQuery(cq, false);
            for (TSFilesEntity file : fileList) {
                deleteFile(file);
            }
        }
        this.commonDao.executeSql(delMemberSql, groupId);//删除成员
        this.commonDao.executeSql(delGroupSql, groupId);//删除组
    }

    //删除附件
    private void deleteFile(TSFilesEntity file) {
        String sql = "select * from t_s_attachment where id = ?";
        Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, file.getId());
        // 附件相对路径
        String realpath = (String) attachmentMap.get("realpath");
        try {
            FtpClientUtil.deleteFile(realpath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // [2].删除数据
        commonDao.delete(file);
    }
    
    /**
     * 逻辑删除
     */
    @Override
    public void doLogicDelete(String ids) {
        for (String id : ids.split(",")) {
        	TSUser user = ResourceUtil.getSessionUserName();
        	TSFilesEntity tsFilesEntity = commonDao.getEntity(TSFilesEntity.class, id);                        
        	tsFilesEntity.setDelFlag("1");
        	tsFilesEntity.setDelTime(new Date());
        	tsFilesEntity.setDelUser(user.getId());
        	tsFilesEntity.setDelUserName(user.getUserName());
            this.commonDao.updateEntitie(tsFilesEntity);
        }
    }
    
    /**
     * 恢复
     */
    @Override
    public void doBack(String ids) {
        for (String id : ids.split(",")) {
        	TSUser user = ResourceUtil.getSessionUserName();
        	TSFilesEntity tsFilesEntity = commonDao.getEntity(TSFilesEntity.class, id);                        
        	tsFilesEntity.setDelFlag("0");
        	tsFilesEntity.setDelTime(new Date());
        	tsFilesEntity.setDelUser(user.getId());
        	tsFilesEntity.setDelUserName(user.getUserName());
            this.commonDao.updateEntitie(tsFilesEntity);
        }
    }
    
    /**
     * 彻底删除
     */
    @Override
    public void doThoroughDelete(String ids) {
        for (String id : ids.split(",")) {
        	
        	TSFilesEntity tsFilesEntity = commonDao.getEntity(TSFilesEntity.class, id);  
        	
            String sql = "select * from t_s_attachment where id = ?";
            Map<String, Object> attachmentMap = commonDao.findOneForJdbc(sql, tsFilesEntity.getId());
            // 附件相对路径
            String realpath = (String) attachmentMap.get("realpath");
            try {
                FtpClientUtil.deleteFile(realpath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // [2].删除数据
            commonDao.delete(tsFilesEntity);
        }
    }

    @Override
    public void viewDiskFile(UploadFile uploadFile) {
        HttpServletResponse response = uploadFile.getResponse();
        response.setContentType("UTF-8");
        response.setCharacterEncoding("UTF-8");
        OutputStream os = null;
        try {
            String ip = FtpClientUtil.getFtpProps("FTP.SERVER.IP");
            int port = Integer.valueOf(FtpClientUtil.getFtpProps("FTP.SERVER.PORT"));
            String username = FtpClientUtil.getFtpProps("FTP.SERVER.USERNAME");
            String password = FtpClientUtil.getFtpProps("FTP.SERVER.PASSWORD");
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

}