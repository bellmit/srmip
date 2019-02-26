package com.kingtake.project.service.impl.learning;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.learning.TBLearningActivityEntity;
import com.kingtake.project.service.learning.TBLearningActivityServiceI;

@Service("tBLearningActivityService")
@Transactional
public class TBLearningActivityServiceImpl extends CommonServiceImpl implements TBLearningActivityServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBLearningActivityEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBLearningActivityEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBLearningActivityEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBLearningActivityEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBLearningActivityEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBLearningActivityEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBLearningActivityEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{activity_name}",String.valueOf(t.getActivityName()));
 		sql  = sql.replace("#{activity_level}",String.valueOf(t.getActivityLevel()));
 		sql  = sql.replace("#{holding_unit}",String.valueOf(t.getHoldingUnit()));
 		sql  = sql.replace("#{host_unit}",String.valueOf(t.getHostUnit()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{activity_scope}",String.valueOf(t.getActivityScope()));
 		sql  = sql.replace("#{attendee_name}",String.valueOf(t.getAttendeeName()));
        sql = sql.replace("#{activity_time}", String.valueOf(t.getActivityTime()));
 		sql  = sql.replace("#{held_job}",String.valueOf(t.getHeldJob()));
 		sql  = sql.replace("#{activity_content}",String.valueOf(t.getActivityContent()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
        sql = sql.replace("#{audit_status}", String.valueOf(t.getBpmStatus()));
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
    public void deleteAddAttach(TBLearningActivityEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}