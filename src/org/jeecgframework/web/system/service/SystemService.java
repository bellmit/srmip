package org.jeecgframework.web.system.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.DictEntity;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**
 *
 * @author 张代浩
 *
 */
public interface SystemService extends CommonService {
    /**
     * 方法描述: 查询数据字典 作 者： yiming.zhang 日 期： 2014年5月11日-下午4:22:42
     *
     * @param dicTable
     * @param dicCode
     * @param dicText
     * @return 返回类型： List<DictEntity>
     */
    public List<DictEntity> queryDict(String dicTable, String dicCode, String dicText);

    /**
     * 登陆用户检查
     *
     * @param user
     * @return
     * @throws Exception
     */
    public TSUser checkUserExits(TSUser user) throws Exception;

    /**
     * 日志添加
     *
     * @param LogContent
     *            内容
     * @param loglevel
     *            级别
     * @param operatetype
     *            类型
     * @param TUser
     *            操作人
     */
    public void addLog(String LogContent, Short operatetype, Short loglevel);

    /**
     * 根据类型编码和类型名称获取Type,如果为空则创建一个
     *
     * @param typecode
     * @param typename
     * @return
     */
    public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup);

    /**
     * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
     *
     * @param typecode
     * @param typename
     * @return
     */
    public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename);

    /**
     * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
     *
     * @param roleId
     * @param functionId
     * @return
     */
    public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId);

    /**
     * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
     *
     * @param roleId
     * @param functionId
     * @return
     */
    public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId);

    /**
     * 根据编码获取字典组
     *
     * @param typegroupCode
     * @return
     */
    public TSTypegroup getTypeGroupByCode(String typegroupCode);

    /**
     * 对数据字典进行缓存
     */
    public void initAllTypeGroups();

    /**
     * 刷新字典缓存
     *
     * @param type
     */
    public void refleshTypesCach(TSType type);

    /**
     * 刷新字典分组缓存
     */
    public void refleshTypeGroupCach();

    /**
     * 刷新菜单
     *
     * @param id
     */
    public void flushRoleFunciton(String id, TSFunction newFunciton);

    /**
     * 生成组织机构编码
     *
     * @param id
     *            组织机构主键
     * @param pid
     *            组织机构的父级主键
     * @return 组织机构编码
     */
    String generateOrgCode(String id, String pid);

    /**
     *
     * getOperationCodesByRoleIdAndruleDataId 根据角色id 和 菜单Id 获取 具有操作权限的数据规则
     *
     * @Title: getOperationCodesByRoleIdAndruleDataId
     * @Description: TODO
     * @param @param roleId
     * @param @param functionId
     * @param @return 设定文件
     * @return Set<String> 返回类型
     * @throws
     */

    public Set<String> getOperationCodesByRoleIdAndruleDataId(String roleId, String functionId);

    public Set<String> getOperationCodesByUserIdAndDataId(String userId, String functionId);

    /**
     * 删除附件
     *
     * @param file
     */
    public void deleteFile(TSFilesEntity file);

    /**
     * 添加业务日志.
     * @param opObjName
     * @param operatetype
     * @param loglevel
     * @param logcontent
     * @param oldJson
     * @param newJson
     */
    public void addBusinessLog(String opObjName, Short operatetype, Short loglevel, String logcontent,
            String oldJson, String newJson);

    /**
     * 获取附件
     * 
     * @param attachmentCode
     * @return
     */
    public List<TSFilesEntity> getAttachmentByCode(String attachmentCode, String businessType);

    /**
     * 
     * 保存文件到FTP
     * @param request
     * @param tsFilesEntity
     */
    public void saveFilesToFTP(HttpServletRequest request, TSFilesEntity tsFilesEntity);

    /**
     * 查看FTP文件
     * @param uploadFile
     */
    public void viewFTPFile(UploadFile uploadFile);

    /**
     * 
     * 删除FTP文件
     * @param file
     */
    public void deleteFTPFile(TSFilesEntity file);
}
