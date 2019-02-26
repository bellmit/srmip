package org.jeecgframework.web.system.service;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.PortalUserVo;
import org.jeecgframework.web.system.pojo.base.TPortalEntity;
import org.jeecgframework.web.system.pojo.base.TPortalLayoutEntity;
import org.jeecgframework.web.system.pojo.base.TPortalUserEntity;

public interface TPortalServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPortalEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPortalEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPortalEntity t);
 	

	/**
	 * 查询添加待办列表
	 * @param username
	 * @return
	 */
	public List<PortalUserVo> getAddPortalList(String username);

	/**
	 * 添加待办
	 * @param userName
	 * @param id
	 */
	public void addPortal(String userName, String id);

	/**
	 * 根据用户名查找
	 * @param username
	 * @return
	 */
	public TPortalUserEntity getPortalByUserName(String username);

	/**
	 * 设置布局
	 * @param userName
	 * @param layoutId
	 */
	public void setLayout(String userName, String layoutId);

	/**
	 * 获取所有待办布局
	 * @return
	 */
	public List<TPortalLayoutEntity> getAllLayout();

	/**
	 * 保存待办
	 * @param portalUserVo
	 */
	public void savePortalSetting(PortalUserVo portalUserVo);

    /**
     * 设置角色与待办的关联
     * 
     * @param roleId
     * @param portals
     */
    public void setRolePortal(String roleId, String portals);

    /**
     * 获取所有待办信息
     * 
     * @return
     */
    public List<TPortalEntity> getAllPortalList(String userName);
}
