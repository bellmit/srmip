package com.kingtake.expert.service.impl.reviewproject;

import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.service.reviewproject.TErReviewProjectServiceI;

@Service("tErReviewProjectService")
@Transactional
public class TErReviewProjectServiceImpl extends CommonServiceImpl implements TErReviewProjectServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TErReviewProjectEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TErReviewProjectEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TErReviewProjectEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TErReviewProjectEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TErReviewProjectEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TErReviewProjectEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TErReviewProjectEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{t_e_id}", String.valueOf(t.getReviewMain().getId()));
 		sql  = sql.replace("#{project_id}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{review_type}",String.valueOf(t.getReviewType()));
 		sql  = sql.replace("#{project_stage}",String.valueOf(t.getProjectStage()));
 		sql  = sql.replace("#{review_result}",String.valueOf(t.getReviewResult()));
 		sql  = sql.replace("#{review_score}",String.valueOf(t.getReviewScore()));
 		sql  = sql.replace("#{review_suggestion}",String.valueOf(t.getReviewSuggestion()));
 		sql  = sql.replace("#{result_input_date}",String.valueOf(t.getResultInputDate()));
 		sql  = sql.replace("#{result_input_userid}",String.valueOf(t.getResultInputUserid()));
 		sql  = sql.replace("#{result_input_username}",String.valueOf(t.getResultInputUsername()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}