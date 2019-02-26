package com.kingtake.project.service.impl.contractbase;
import com.kingtake.project.service.contractbase.TPmContractBasicServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tPmContractBasicService")
@Transactional
public class TPmContractBasicServiceImpl extends CommonServiceImpl implements TPmContractBasicServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractBasicEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractBasicEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractBasicEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractBasicEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractBasicEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractBasicEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractBasicEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getRid()));
 		sql  = sql.replace("#{in_out_contractid}",String.valueOf(t.getInOutContractid()));
 		sql  = sql.replace("#{unit_name_a}",String.valueOf(t.getUnitNameA()));
 		sql  = sql.replace("#{unit_name_b}",String.valueOf(t.getUnitNameB()));
 		sql  = sql.replace("#{unit_position_a}",String.valueOf(t.getUnitPositionA()));
 		sql  = sql.replace("#{unit_position_b}",String.valueOf(t.getUnitPositionB()));
 		sql  = sql.replace("#{name_a}",String.valueOf(t.getNameA()));
 		sql  = sql.replace("#{name_b}",String.valueOf(t.getNameB()));
 		sql  = sql.replace("#{agency_uint_name_a}",String.valueOf(t.getAgencyUintNameA()));
 		sql  = sql.replace("#{agency_unit_name_b}",String.valueOf(t.getAgencyUnitNameB()));
 		sql  = sql.replace("#{agency_unit_position_a}",String.valueOf(t.getAgencyUnitPositionA()));
 		sql  = sql.replace("#{agency_unit_position_b}",String.valueOf(t.getAgencyUnitPositionB()));
 		sql  = sql.replace("#{agency_name_a}",String.valueOf(t.getAgencyNameA()));
 		sql  = sql.replace("#{agency_name_b}",String.valueOf(t.getAgencyNameB()));
 		sql  = sql.replace("#{address_a}",String.valueOf(t.getAddressA()));
 		sql  = sql.replace("#{address_b}",String.valueOf(t.getAddressB()));
 		sql  = sql.replace("#{postalcode_a}",String.valueOf(t.getPostalcodeA()));
 		sql  = sql.replace("#{postalcode_b}",String.valueOf(t.getPostalcodeB()));
 		sql  = sql.replace("#{tel_a}",String.valueOf(t.getTelA()));
 		sql  = sql.replace("#{tel_b}",String.valueOf(t.getTelB()));
 		sql  = sql.replace("#{account_name_a}",String.valueOf(t.getAccountNameA()));
 		sql  = sql.replace("#{account_name_b}",String.valueOf(t.getAccountNameB()));
 		sql  = sql.replace("#{bank_a}",String.valueOf(t.getBankA()));
 		sql  = sql.replace("#{bank_b}",String.valueOf(t.getBankB()));
 		sql  = sql.replace("#{account_id_a}",String.valueOf(t.getAccountIdA()));
 		sql  = sql.replace("#{account_id_b}",String.valueOf(t.getAccountIdB()));
 		sql  = sql.replace("#{sign_address_a}",String.valueOf(t.getSignAddressA()));
 		sql  = sql.replace("#{sign_address_b}",String.valueOf(t.getSignAddressB()));
 		sql  = sql.replace("#{the_third}",String.valueOf(t.getTheThird()));
 		sql  = sql.replace("#{monitor_unit}",String.valueOf(t.getMonitorUnit()));
 		sql  = sql.replace("#{military_deputy}",String.valueOf(t.getMilitaryDeputy()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}