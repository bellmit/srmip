package com.kingtake.office.service.impl.signature;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.kingtake.office.entity.approval.TOApprovalProjectSummaryEntity;
import com.kingtake.office.service.signature.SignatureServiceI;

@Service("signatureService")
@Transactional
public class SignatureServiceImpl extends CommonServiceImpl implements SignatureServiceI {

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
        String mUserName = request.getParameter("UserName");
        String mPassword = request.getParameter("PassWord");
        String mMarkName = request.getParameter("MarkName");
        String signatureID = request.getParameter("SignatureID");
        if (StringUtils.isEmpty(signatureID)) {
            String querysql = "select MarkName from Signature where MarkName='" + mMarkName + "'";
            List<Map<String, Object>> list = commonDao.findForJdbc(querysql);
            if (list.size() > 0) {
                throw new BusinessException("已存在相同名称的签章！");
            }
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
            while (iter.hasNext()) {
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if (myFileName.trim() != "") {
                        mMarkSize = file.getSize();
                        String[] names = myFileName.split("\\.");
                        if (names.length > 1) {
                            mMarkType = "." + names[names.length - 1];
                            if (!".jpg".equals(mMarkType)) {
                                throw new BusinessException("只支持jpg格式图片");
                            }
                        }
                        try {
                            String mSql = null;
                            String id = null;
                            if (StringUtils.isEmpty(signatureID)) {//新增
                                mSql = "Insert Into Signature (id,UserName,Password,MarkName,MarkSize,MarkType,MarkBody,markDate) values (?,?,?,?,?,?,?,sysdate)";
                                id = UUIDGenerator.generate();
                                this.commonDao.executeSql(mSql, new Object[] { id, mUserName, mPassword, mMarkName,
                                        mMarkSize, mMarkType, file.getBytes() });
                            } else {//更新
                                id = signatureID;
                                mSql = "update Signature set UserName=?,Password=?,MarkName=?,MarkSize=?,MarkType=?,MarkBody=?,markDate=sysdate where id=?";
                                this.commonDao.executeSql(mSql, new Object[] { mUserName, mPassword, mMarkName,
                                        mMarkSize, mMarkType, file.getBytes(), id });
                            }
                        } catch (Exception e) {
                            throw new BusinessException("上传文件失败!");
                        }
                    }
                }
            }
        }
    }
}