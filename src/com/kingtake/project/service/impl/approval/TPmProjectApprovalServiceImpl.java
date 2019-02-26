package com.kingtake.project.service.impl.approval;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.approval.TPmProjectApprovalEntity;
import com.kingtake.project.service.approval.TPmProjectApprovalServiceI;

@Service("tPmProjectApprovalService")
@Transactional
public class TPmProjectApprovalServiceImpl extends CommonServiceImpl implements TPmProjectApprovalServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmProjectApprovalEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmProjectApprovalEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmProjectApprovalEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmProjectApprovalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmProjectApprovalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmProjectApprovalEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmProjectApprovalEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{suggest_grade}",String.valueOf(t.getSuggestGrade()));
 		sql  = sql.replace("#{suggest_unit}",String.valueOf(t.getSuggestUnit()));
 		sql  = sql.replace("#{study_time}",String.valueOf(t.getStudyTime()));
 		sql  = sql.replace("#{project_src}",String.valueOf(t.getProjectSrc()));
 		sql  = sql.replace("#{study_necessity}",String.valueOf(t.getStudyNecessity()));
 		sql  = sql.replace("#{situation_analysis}",String.valueOf(t.getSituationAnalysis()));
 		sql  = sql.replace("#{study_content}",String.valueOf(t.getStudyContent()));
 		sql  = sql.replace("#{schedule_and_achieve}",String.valueOf(t.getScheduleAndAchieve()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void deleteAddAttach(TPmProjectApprovalEntity t) {
        this.delAttachementByBid(t.getId());
        this.commonDao.delete(t);
    }
}