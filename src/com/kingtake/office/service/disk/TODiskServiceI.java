package com.kingtake.office.service.disk;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;

import com.kingtake.office.entity.disk.TOGroupEntity;
import com.kingtake.project.entity.dbimport.TPmDBImportEntity;

public interface TODiskServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TPmDBImportEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TPmDBImportEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TPmDBImportEntity t);

    /**
     * 保存上传文件
     * 
     * @param request
     * @param tsFilesEntity
     */
    public void saveDiskFiles(HttpServletRequest request, TSFilesEntity tsFilesEntity);

    /**
     * 创建群组
     * 
     * @param group
     */
    public void createGroup(TOGroupEntity group);

    /**
     * 添加成员
     * 
     * @param groupId
     * @param userId
     */
    public void saveGroupMember(String groupId, String userId);

    /**
     * 移除成员
     * 
     * @param groupId
     * @param userId
     */
    public void removeGroupMember(String groupId, String userId);

    /**
     * 解散群组
     * 
     * @param groupId
     */
    public void breakGroup(String groupId);
    
    /**
     * 逻辑删除文件
     * 
     * @param ids
     */
    public void doLogicDelete(String ids);
    
    
    /**
     * 彻底删除文件
     * 
     * @param ids
     */
    public void doThoroughDelete(String ids);
    
    
    /**
     * 系统消息
     * 
     * @param ids
     */
    public void doBack(String ids);

    /**
     * 上传文件到FTP
     * 
     * @param request
     * @param tsFilesEntity
     */
    public void saveDiskToFTP(HttpServletRequest request, TSFilesEntity tsFilesEntity);

    /**
     * 查看文件
     * 
     * @param uploadFile
     */
    public void viewDiskFile(UploadFile uploadFile);

}
