package com.kingtake.office.service.impl.sign;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.kingtake.office.entity.approval.TOApprovalProjectSummaryEntity;
import com.kingtake.office.service.sign.SignServiceI;

@Service("signService")
@Transactional
public class SignServiceImpl extends CommonServiceImpl implements SignServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TOApprovalProjectSummaryEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TOApprovalProjectSummaryEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TOApprovalProjectSummaryEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOApprovalProjectSummaryEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOApprovalProjectSummaryEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOApprovalProjectSummaryEntity t) {
        return true;
    }

    @Override
    public void saveSignature(HttpServletRequest request) {
        String filename = request.getParameter("filename");
        String name = request.getParameter("SignName");
        String username = request.getParameter("SignUser");
        String password = request.getParameter("Password1");
        String querysql = "select name from t_o_sign where name='" + name + "'";
        List<Map<String, Object>> list = commonDao.findForJdbc(querysql);
        if (list.size() > 0) {
            throw new BusinessException("已存在相同名称的印章！");
        }
        long mMarkSize = 0;
        String mMarkType = null;
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        //判断 request 是否有文件上传,即多部分请求  
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();
            String realPath = new File(multiRequest.getSession().getServletContext().getRealPath("/")).getParent()
                    + "/" + "sign";// 文件的硬盘真实路径
            File espDir = new File(realPath);
            if (!espDir.exists()) {
                espDir.mkdirs();
            }
            String mSql = null;
            String id = null;
            String espPath = null;
            String picPath = null;
            while (iter.hasNext()) {
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    mMarkSize = file.getSize();
                    String[] names = myFileName.split("\\.");
                    if (names.length > 1) {
                        mMarkType = "." + names[names.length - 1];
                    }
                    String filePath = realPath + "/" + myFileName;
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        if (myFileName.trim() != "" && myFileName.contains(".esp")) {
                            espPath = "sign" + "/" + myFileName;
                        } else {
                            String ext = myFileName.substring(myFileName.indexOf(".") + 1, myFileName.length());
                            picPath = "sign" + "/" + name + "." + ext;
                            filePath = realPath + "/" + name + "." + ext;
                        }
                        is = file.getInputStream();
                        os = new FileOutputStream(filePath);
                        IOUtils.copy(is, os);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BusinessException("保存印章出错", e);
                    } finally {
                        IOUtils.closeQuietly(is);
                        IOUtils.closeQuietly(os);
                    }
                }
            }
            mSql = "Insert Into t_o_sign (id,name,username,password,filename,esppath,picpath,createdate) values (?,?,?,?,?,?,?,sysdate)";
            id = UUIDGenerator.generate();
            this.commonDao.executeSql(mSql, new Object[] { id, name, username, password, filename, espPath, picPath });
        }
    }

    @Override
    public void deleteSign(String id) {
        String sql = "select esppath,picpath from t_o_sign where id=?";
        List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, id);
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            String espPath = (String) map.get("esppath");
            String picpath = (String) map.get("picpath");
            // 获取部署项目绝对地址
            String realPath = new File(ContextHolderUtils.getSession().getServletContext().getRealPath("/"))
                    .getParent() + "/";
            FileUtils.delete(realPath + espPath);
            FileUtils.delete(realPath + picpath);
        }
        String delSql = "delete from t_o_sign t where t.id=?";
        this.commonDao.executeSql(delSql, id);
    }

    //    @Override
    //    public String viewSign(HttpServletRequest request, HttpServletResponse response) {
    //        AjaxJson j = new AjaxJson();
    //        String url = "";
    //        String id = request.getParameter("id");
    //        String sql = "select esppath,picpath from t_o_sign where id=?";
    //        List<Map<String, Object>> list = this.commonDao.findForJdbc(sql, id);
    //        if (list.size() > 0) {
    //            Map<String, Object> map = list.get(0);
    //            String picpath = (String) map.get("picpath");
    //            // 获取部署项目绝对地址
    //            url = picpath;
    //        }
    //        return url;
    //    }
}