package com.kingtake.project.service.impl.reporthall;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.reporthall.TBReportHallUseEntity;
import com.kingtake.project.service.reporthall.TBReportHallUseServiceI;

@Service("tBReportHallUseService")
@Transactional
public class TBReportHallUseServiceImpl extends CommonServiceImpl implements TBReportHallUseServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBReportHallUseEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBReportHallUseEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBReportHallUseEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBReportHallUseEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBReportHallUseEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBReportHallUseEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBReportHallUseEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{report_hall_id}",String.valueOf(t.getReportHallId()));
 		sql  = sql.replace("#{review_number}",String.valueOf(t.getReviewNumber()));
 		sql  = sql.replace("#{use_depart_id}",String.valueOf(t.getUseDepartId()));
 		sql  = sql.replace("#{use_depart_name}",String.valueOf(t.getUseDepartName()));
 		sql  = sql.replace("#{use_purpose}",String.valueOf(t.getUsePurpose()));
 		sql  = sql.replace("#{begin_use_time}",String.valueOf(t.getBeginUseTime()));
 		sql  = sql.replace("#{end_use_time}",String.valueOf(t.getEndUseTime()));
 		sql  = sql.replace("#{contact_id}",String.valueOf(t.getContactId()));
 		sql  = sql.replace("#{contact_name}",String.valueOf(t.getContactName()));
 		sql  = sql.replace("#{contact_phone}",String.valueOf(t.getContactPhone()));
 		sql  = sql.replace("#{attendee_name}",String.valueOf(t.getAttendeeName()));
 		sql  = sql.replace("#{prepare_things}",String.valueOf(t.getPrepareThings()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{check_flag}",String.valueOf(t.getCheckFlag()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void deleteAddAttach(TBReportHallUseEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}