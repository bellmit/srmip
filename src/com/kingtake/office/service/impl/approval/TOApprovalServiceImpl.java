package com.kingtake.office.service.impl.approval;
import com.kingtake.office.service.approval.TOApprovalServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.office.entity.approval.TOApprovalEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tOApprovalService")
@Transactional
public class TOApprovalServiceImpl extends CommonServiceImpl implements TOApprovalServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOApprovalEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOApprovalEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOApprovalEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOApprovalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOApprovalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOApprovalEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOApprovalEntity t){
 		sql  = sql.replace("#{application_fileno}",String.valueOf(t.getApplicationFileno()));
 		sql  = sql.replace("#{application_content}",String.valueOf(t.getApplicationContent()));
 		sql  = sql.replace("#{archive_flag}",String.valueOf(t.getArchiveFlag()));
 		sql  = sql.replace("#{archive_userid}",String.valueOf(t.getArchiveUserid()));
 		sql  = sql.replace("#{archive_username}",String.valueOf(t.getArchiveUsername()));
 		sql  = sql.replace("#{archive_date}",String.valueOf(t.getArchiveDate()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{back_userid}",String.valueOf(t.getBackUserid()));
 		sql  = sql.replace("#{back_username}",String.valueOf(t.getBackUsername()));
 		sql  = sql.replace("#{back_suggestion}",String.valueOf(t.getBackSuggestion()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{receive_unitid}",String.valueOf(t.getReceiveUnitid()));
 		sql  = sql.replace("#{receive_unitname}",String.valueOf(t.getReceiveUnitname()));
 		sql  = sql.replace("#{sign_unitid}",String.valueOf(t.getSignUnitid()));
 		sql  = sql.replace("#{sign_unitname}",String.valueOf(t.getSignUnitname()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{secrity_grade}",String.valueOf(t.getSecrityGrade()));
 		sql  = sql.replace("#{undertake_unit_id}",String.valueOf(t.getUndertakeUnitId()));
 		sql  = sql.replace("#{undertake_unit_name}",String.valueOf(t.getUndertakeUnitName()));
 		sql  = sql.replace("#{contact_id}",String.valueOf(t.getContactId()));
 		sql  = sql.replace("#{contact_name}",String.valueOf(t.getContactName()));
 		sql  = sql.replace("#{contact_phone}",String.valueOf(t.getContactPhone()));
 		sql  = sql.replace("#{approval_year}",String.valueOf(t.getApprovalYear()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}