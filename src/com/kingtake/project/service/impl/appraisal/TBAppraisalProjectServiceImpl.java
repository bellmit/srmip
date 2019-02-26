package com.kingtake.project.service.impl.appraisal;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.appraisal.TBAppraisalProjectEntity;
import com.kingtake.project.service.appraisal.TBAppraisalProjectServiceI;

@Service("tBAppraisalProjectService")
@Transactional
public class TBAppraisalProjectServiceImpl extends CommonServiceImpl implements TBAppraisalProjectServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBAppraisalProjectEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBAppraisalProjectEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBAppraisalProjectEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalProjectEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalProjectEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalProjectEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBAppraisalProjectEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{project_id}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{project_begin_date}",String.valueOf(t.getProjectBeginDate()));
 		sql  = sql.replace("#{project_end_date}",String.valueOf(t.getProjectEndDate()));
 		sql  = sql.replace("#{project_developer_departid}",String.valueOf(t.getProjectDeveloperDepartid()));
 		sql  = sql.replace("#{project_developer_departname}",String.valueOf(t.getProjectDeveloperDepartname()));
 		sql  = sql.replace("#{project_manager_id}",String.valueOf(t.getProjectManagerId()));
 		sql  = sql.replace("#{project_manager_name}",String.valueOf(t.getProjectManagerName()));
 		sql  = sql.replace("#{project_manager_phone}",String.valueOf(t.getProjectManagerPhone()));
 		sql  = sql.replace("#{project_source_unit}",String.valueOf(t.getProjectSourceUnit()));
 		sql  = sql.replace("#{projectAccording_num}",String.valueOf(t.getProjectAccordingNum()));
 		sql  = sql.replace("#{appraisal_unit}",String.valueOf(t.getAppraisalUnit()));
 		sql  = sql.replace("#{appraisal_contact}",String.valueOf(t.getAppraisalContact()));
 		sql  = sql.replace("#{appraisal_contact_phone}",String.valueOf(t.getAppraisalContactPhone()));
 		sql  = sql.replace("#{appraisal_time}",String.valueOf(t.getAppraisalTime()));
 		sql  = sql.replace("#{appraisal_form}",String.valueOf(t.getAppraisalForm()));
 		sql  = sql.replace("#{appraisal_address}",String.valueOf(t.getAppraisalAddress()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{plan_year}",String.valueOf(t.getPlanYear()));
 		sql  = sql.replace("#{plan_unitid}",String.valueOf(t.getPlanUnitid()));
 		sql  = sql.replace("#{plan_unitname}",String.valueOf(t.getPlanUnitname()));
 		sql  = sql.replace("#{plan_date}",String.valueOf(t.getPlanDate()));
 		sql  = sql.replace("#{state}",String.valueOf(t.getState()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}