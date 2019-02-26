package com.kingtake.office.service.impl.vehicle;
import com.kingtake.office.service.vehicle.TOVehicleAccidentServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.office.entity.vehicle.TOVehicleAccidentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tOVehicleAccidentService")
@Transactional
public class TOVehicleAccidentServiceImpl extends CommonServiceImpl implements TOVehicleAccidentServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOVehicleAccidentEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOVehicleAccidentEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOVehicleAccidentEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOVehicleAccidentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOVehicleAccidentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOVehicleAccidentEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOVehicleAccidentEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{vehicle_id}",String.valueOf(t.getVehicleId()));
 		sql  = sql.replace("#{use_id}",String.valueOf(t.getUseId()));
 		sql  = sql.replace("#{person_id}",String.valueOf(t.getPersonId()));
 		sql  = sql.replace("#{person_name}",String.valueOf(t.getPersonName()));
 		sql  = sql.replace("#{occur_time}",String.valueOf(t.getOccurTime()));
 		sql  = sql.replace("#{occur_address}",String.valueOf(t.getOccurAddress()));
 		sql  = sql.replace("#{result}",String.valueOf(t.getResult()));
 		sql  = sql.replace("#{handle_mode}",String.valueOf(t.getHandleMode()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}