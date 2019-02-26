package com.kingtake.office.service.impl.vehicle;
import com.kingtake.office.service.vehicle.TOVehicleMaintenanceServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.office.entity.vehicle.TOVehicleMaintenanceEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tOVehicleMaintenanceService")
@Transactional
public class TOVehicleMaintenanceServiceImpl extends CommonServiceImpl implements TOVehicleMaintenanceServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOVehicleMaintenanceEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOVehicleMaintenanceEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOVehicleMaintenanceEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOVehicleMaintenanceEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOVehicleMaintenanceEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOVehicleMaintenanceEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOVehicleMaintenanceEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{vehicle_id}",String.valueOf(t.getVehicleId()));
 		sql  = sql.replace("#{op_type}",String.valueOf(t.getOpType()));
 		sql  = sql.replace("#{maintenance_time}",String.valueOf(t.getMaintenanceTime()));
 		sql  = sql.replace("#{maintenance_items}",String.valueOf(t.getMaintenanceItems()));
 		sql  = sql.replace("#{maintain_user_id}",String.valueOf(t.getMaintainUserId()));
 		sql  = sql.replace("#{maintain_user_name}",String.valueOf(t.getMaintainUserName()));
 		sql  = sql.replace("#{maintain_user_pay}",String.valueOf(t.getMaintainUserPay()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}