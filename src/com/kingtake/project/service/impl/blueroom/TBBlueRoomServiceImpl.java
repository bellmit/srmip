package com.kingtake.project.service.impl.blueroom;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.blueroom.TBBlueRoomEntity;
import com.kingtake.project.service.blueroom.TBBlueRoomServiceI;

@Service("tBBlueRoomService")
@Transactional
public class TBBlueRoomServiceImpl extends CommonServiceImpl implements TBBlueRoomServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBBlueRoomEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBBlueRoomEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBBlueRoomEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBBlueRoomEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBBlueRoomEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBBlueRoomEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBBlueRoomEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{room_name}",String.valueOf(t.getRoomName()));
 		sql  = sql.replace("#{room_level}",String.valueOf(t.getRoomLevel()));
 		sql  = sql.replace("#{hold_unit_id}",String.valueOf(t.getHoldUnitId()));
 		sql  = sql.replace("#{hold_unit_name}",String.valueOf(t.getHoldUnitName()));
 		sql  = sql.replace("#{host_unit_id}",String.valueOf(t.getHostUnitId()));
 		sql  = sql.replace("#{host_unit_name}",String.valueOf(t.getHostUnitName()));
 		sql  = sql.replace("#{hold_address}",String.valueOf(t.getHoldAddress()));
 		sql  = sql.replace("#{activity_scale}",String.valueOf(t.getActivityScale()));
 		sql  = sql.replace("#{begin_report_time}",String.valueOf(t.getBeginReportTime()));
 		sql  = sql.replace("#{end_report_time}",String.valueOf(t.getEndReportTime()));
 		sql  = sql.replace("#{recommend_unit_id}",String.valueOf(t.getRecommendUnitId()));
 		sql  = sql.replace("#{recommend_unit_name}",String.valueOf(t.getRecommendUnitName()));
 		sql  = sql.replace("#{reporter_name}",String.valueOf(t.getReporterName()));
 		sql  = sql.replace("#{post_position}",String.valueOf(t.getPostPosition()));
 		sql  = sql.replace("#{report_content}",String.valueOf(t.getReportContent()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{confirm_flag}",String.valueOf(t.getConfirmFlag()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void deleteAddAttach(TBBlueRoomEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}