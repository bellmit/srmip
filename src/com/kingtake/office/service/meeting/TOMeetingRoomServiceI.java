package com.kingtake.office.service.meeting;
import com.kingtake.office.entity.meeting.TOMeetingRoomEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TOMeetingRoomServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOMeetingRoomEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOMeetingRoomEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOMeetingRoomEntity t);
}
