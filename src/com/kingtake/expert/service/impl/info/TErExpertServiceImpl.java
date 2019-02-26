package com.kingtake.expert.service.impl.info;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.expert.entity.info.TErExpertEntity;
import com.kingtake.expert.service.info.TErExpertServiceI;

@Service("tErExpertService")
@Transactional
public class TErExpertServiceImpl extends CommonServiceImpl implements TErExpertServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TErExpertEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TErExpertEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TErExpertEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TErExpertEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TErExpertEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TErExpertEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TErExpertEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{expert_num}",String.valueOf(t.getExpertNum()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{sex}",String.valueOf(t.getSex()));
 		sql  = sql.replace("#{id_type}",String.valueOf(t.getIdType()));
 		sql  = sql.replace("#{id_no}",String.valueOf(t.getIdNo()));
 		sql  = sql.replace("#{expert_birth_date}",String.valueOf(t.getExpertBirthDate()));
 		sql  = sql.replace("#{expert_position}",String.valueOf(t.getExpertPosition()));
 		sql  = sql.replace("#{expert_pracitic_req}",String.valueOf(t.getExpertPraciticReq()));
 		sql  = sql.replace("#{expert_profession}",String.valueOf(t.getExpertProfession()));
 		sql  = sql.replace("#{expert_phone}",String.valueOf(t.getExpertPhone()));
 		sql  = sql.replace("#{expert_depart_id}",String.valueOf(t.getExpertDepartId()));
 		sql  = sql.replace("#{expert_depart_name}",String.valueOf(t.getExpertDepartName()));
 		sql  = sql.replace("#{expert_district}",String.valueOf(t.getExpertDistrict()));
 		sql  = sql.replace("#{expert_summary}",String.valueOf(t.getExpertSummary()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}