package com.kingtake.office.service.impl.meeting;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.dao.MeetingPortalDao;
import com.kingtake.office.entity.meeting.TOMeetingEntity;
import com.kingtake.office.service.meeting.TOMeetingServiceI;

@Service("tOMeetingService")
@Transactional
public class TOMeetingServiceImpl extends CommonServiceImpl implements TOMeetingServiceI {
    @Autowired
    public MeetingPortalDao meetingPortalDao;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOMeetingEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOMeetingEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOMeetingEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOMeetingEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOMeetingEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOMeetingEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOMeetingEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{meeting_room_id}",String.valueOf(t.getMeetingRoomId()));
 		sql  = sql.replace("#{meeting_room_name}",String.valueOf(t.getMeetingRoomName()));
 		sql  = sql.replace("#{project_id}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{meeting_title}",String.valueOf(t.getMeetingTitle()));
 		sql  = sql.replace("#{meeting_content}",String.valueOf(t.getMeetingContent()));
 		sql  = sql.replace("#{host_unit_id}",String.valueOf(t.getHostUnitId()));
 		sql  = sql.replace("#{host_unit_name}",String.valueOf(t.getHostUnitName()));
 		sql  = sql.replace("#{begin_date}",String.valueOf(t.getBeginDate()));
 		sql  = sql.replace("#{end_date}",String.valueOf(t.getEndDate()));
 		sql  = sql.replace("#{attendees_id}",String.valueOf(t.getAttendeesId()));
 		sql  = sql.replace("#{attendees_name}",String.valueOf(t.getAttendeesName()));
 		sql  = sql.replace("#{attend_unit_num}",String.valueOf(t.getAttendUnitNum()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    /**
     * 获取待办列表
     * 
     * @param param
     * @return
     */
    @Override
    public List<Map<String, String>> getPortalList(Map<String, String> param, int start, int end) {
        return this.meetingPortalDao.getPortalList(param, start, end);
    }

    /**
     * 获取待办总数
     * 
     * @param param
     * @return
     */
    @Override
    public Integer getPortalCount(Map<String, String> param) {
        return this.meetingPortalDao.getPortalCount(param);
    }
}