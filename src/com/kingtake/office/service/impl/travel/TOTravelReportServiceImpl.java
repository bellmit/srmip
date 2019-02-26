package com.kingtake.office.service.impl.travel;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.travel.TOTravelReportEntity;
import com.kingtake.office.service.travel.TOTravelReportServiceI;

@Service("tOTravelReportService")
@Transactional
public class TOTravelReportServiceImpl extends CommonServiceImpl implements TOTravelReportServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOTravelReportEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOTravelReportEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOTravelReportEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TOTravelReportEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TOTravelReportEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TOTravelReportEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOTravelReportEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_o_id}",String.valueOf(t.getToId()));
 		sql  = sql.replace("#{travel_reason}",String.valueOf(t.getTravelReason()));
 		sql  = sql.replace("#{relate_username}",String.valueOf(t.getRelateUsername()));
 		sql  = sql.replace("#{start_time}",String.valueOf(t.getStartTime()));
 		sql  = sql.replace("#{end_time}",String.valueOf(t.getEndTime()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{chief_approval}",String.valueOf(t.getChiefApproval()));
 		sql  = sql.replace("#{depart_approval}",String.valueOf(t.getDepartApproval()));
 		sql  = sql.replace("#{section_approval}",String.valueOf(t.getSectionApproval()));
 		sql  = sql.replace("#{circel_read}",String.valueOf(t.getCircelRead()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{submit_time}",String.valueOf(t.getSubmitTime()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}