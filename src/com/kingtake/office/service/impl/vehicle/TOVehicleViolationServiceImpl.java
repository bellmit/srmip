package com.kingtake.office.service.impl.vehicle;
import com.kingtake.office.service.vehicle.TOVehicleViolationServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.office.entity.vehicle.TOVehicleViolationEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tOVehicleViolationService")
@Transactional
public class TOVehicleViolationServiceImpl extends CommonServiceImpl implements TOVehicleViolationServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOVehicleViolationEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOVehicleViolationEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOVehicleViolationEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOVehicleViolationEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOVehicleViolationEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOVehicleViolationEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOVehicleViolationEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{use_id}",String.valueOf(t.getUseId()));
 		sql  = sql.replace("#{vehicle_id}",String.valueOf(t.getVehicleId()));
 		sql  = sql.replace("#{violation_id}",String.valueOf(t.getViolationId()));
 		sql  = sql.replace("#{violation_name}",String.valueOf(t.getViolationName()));
 		sql  = sql.replace("#{violation_time}",String.valueOf(t.getViolationTime()));
 		sql  = sql.replace("#{violation_addr}",String.valueOf(t.getViolationAddr()));
 		sql  = sql.replace("#{violation_code}",String.valueOf(t.getViolationCode()));
 		sql  = sql.replace("#{violation_desc}",String.valueOf(t.getViolationDesc()));
 		sql  = sql.replace("#{violation_score}",String.valueOf(t.getViolationScore()));
 		sql  = sql.replace("#{fines}",String.valueOf(t.getFines()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}