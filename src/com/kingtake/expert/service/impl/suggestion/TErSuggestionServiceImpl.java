package com.kingtake.expert.service.impl.suggestion;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.expert.entity.suggestion.TErSuggestionEntity;
import com.kingtake.expert.service.suggestion.TErSuggestionServiceI;

@Service("tErSuggestionService")
@Transactional
public class TErSuggestionServiceImpl extends CommonServiceImpl implements TErSuggestionServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TErSuggestionEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TErSuggestionEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TErSuggestionEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TErSuggestionEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TErSuggestionEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TErSuggestionEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TErSuggestionEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{review_project_id}", String.valueOf(t.getReviewProject().getId()));
 		sql  = sql.replace("#{expert_id}",String.valueOf(t.getExpertId()));
 		sql  = sql.replace("#{expert_name}",String.valueOf(t.getExpertName()));
 		sql  = sql.replace("#{expert_time}",String.valueOf(t.getExpertTime()));
 		sql  = sql.replace("#{expert_score}",String.valueOf(t.getExpertScore()));
 		sql  = sql.replace("#{expert_result}",String.valueOf(t.getExpertResult()));
 		sql  = sql.replace("#{expert_content}",String.valueOf(t.getExpertContent()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}