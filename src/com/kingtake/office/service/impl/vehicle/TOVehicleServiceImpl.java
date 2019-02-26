package com.kingtake.office.service.impl.vehicle;
import com.kingtake.office.service.vehicle.TOVehicleServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.office.entity.vehicle.TOVehicleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tOVehicleService")
@Transactional
public class TOVehicleServiceImpl extends CommonServiceImpl implements TOVehicleServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOVehicleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOVehicleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOVehicleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOVehicleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOVehicleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOVehicleEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOVehicleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{license_no}",String.valueOf(t.getLicenseNo()));
 		sql  = sql.replace("#{type}",String.valueOf(t.getType()));
 		sql  = sql.replace("#{purchase_time}",String.valueOf(t.getPurchaseTime()));
 		sql  = sql.replace("#{driver_id}",String.valueOf(t.getDriverId()));
 		sql  = sql.replace("#{driver_name}",String.valueOf(t.getDriverName()));
 		sql  = sql.replace("#{vehicle_state}",String.valueOf(t.getVehicleState()));
 		sql  = sql.replace("#{insurance_flag}",String.valueOf(t.getInsuranceFlag()));
 		sql  = sql.replace("#{insurance_time}",String.valueOf(t.getInsuranceTime()));
 		sql  = sql.replace("#{annual_survey_flag}",String.valueOf(t.getAnnualSurveyFlag()));
 		sql  = sql.replace("#{annual_survey_time}",String.valueOf(t.getAnnualSurveyTime()));
 		sql  = sql.replace("#{refuel_no}",String.valueOf(t.getRefuelNo()));
 		sql  = sql.replace("#{refuel_balance}",String.valueOf(t.getRefuelBalance()));
 		sql  = sql.replace("#{military_flag}",String.valueOf(t.getMilitaryFlag()));
 		sql  = sql.replace("#{section_id}",String.valueOf(t.getSectionId()));
 		sql  = sql.replace("#{section_name}",String.valueOf(t.getSectionName()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}