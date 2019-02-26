package com.kingtake.office.service.impl.vehicle;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.vehicle.TOVehicleUseEntity;
import com.kingtake.office.service.vehicle.TOVehicleUseServiceI;

@Service("tOVehicleUseService")
@Transactional
public class TOVehicleUseServiceImpl extends CommonServiceImpl implements TOVehicleUseServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
        //执行删除操作配置的sql增强
		this.doDelSql((TOVehicleUseEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
 		this.doAddSql((TOVehicleUseEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
 		this.doUpdateSql((TOVehicleUseEntity)entity);
 	}
 	
 	         /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
 	public boolean doAddSql(TOVehicleUseEntity t){
	 	return true;
 	}
 	
    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
 	public boolean doUpdateSql(TOVehicleUseEntity t){
	 	return true;
 	}
 	
    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
 	public boolean doDelSql(TOVehicleUseEntity t){
	 	return true;
 	}
 	
 	         /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
 	public String replaceVal(String sql,TOVehicleUseEntity t){
 		sql  = sql.replace("#{use_name}",String.valueOf(t.getUseName()));
 		sql  = sql.replace("#{apply_time}",String.valueOf(t.getApplyTime()));
 		sql  = sql.replace("#{approver_id}",String.valueOf(t.getApproverId()));
 		sql  = sql.replace("#{approver_name}",String.valueOf(t.getApproverName()));
 		sql  = sql.replace("#{approver_time}",String.valueOf(t.getApproverTime()));
 		sql  = sql.replace("#{driver_id}",String.valueOf(t.getDriverId()));
 		sql  = sql.replace("#{driver_name}",String.valueOf(t.getDriverName()));
 		sql  = sql.replace("#{acheive_place}",String.valueOf(t.getAcheivePlace()));
 		sql  = sql.replace("#{driver_distance}",String.valueOf(t.getDriverDistance()));
 		sql  = sql.replace("#{fuel_use}",String.valueOf(t.getFuelUse()));
 		sql  = sql.replace("#{out_time}",String.valueOf(t.getOutTime()));
 		sql  = sql.replace("#{back_time}",String.valueOf(t.getBackTime()));
 		sql  = sql.replace("#{out_reason}",String.valueOf(t.getOutReason()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{vehicle_id}", String.valueOf(t.getVehicleId()));
 		sql  = sql.replace("#{project_id}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{use_id}",String.valueOf(t.getUseId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}