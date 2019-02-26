package com.kingtake.office.service.meeting;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.meeting.TOMeetingEntity;

public interface TOMeetingServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOMeetingEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOMeetingEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOMeetingEntity t);

    /**
     * 获取待办数目
     * 
     * @param param
     * @return
     */
    public Integer getPortalCount(Map<String, String> param);

    /**
     * 获取待办列表
     * 
     * @param param
     * @return
     */
    List<Map<String, String>> getPortalList(Map<String, String> param, int start, int end);
}
