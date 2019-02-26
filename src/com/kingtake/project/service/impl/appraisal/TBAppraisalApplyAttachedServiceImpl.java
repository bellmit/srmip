package com.kingtake.project.service.impl.appraisal;
import com.kingtake.project.service.appraisal.TBAppraisalApplyAttachedServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBAppraisalApplyAttachedService")
@Transactional
public class TBAppraisalApplyAttachedServiceImpl extends CommonServiceImpl implements TBAppraisalApplyAttachedServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBAppraisalApplyAttachedEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBAppraisalApplyAttachedEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBAppraisalApplyAttachedEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalApplyAttachedEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalApplyAttachedEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalApplyAttachedEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBAppraisalApplyAttachedEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{apply_id}",String.valueOf(t.getApplyId()));
 		sql  = sql.replace("#{naval_authority_view}",String.valueOf(t.getNavalAuthorityView()));
 		sql  = sql.replace("#{register_num}",String.valueOf(t.getRegisterNum()));
 		sql  = sql.replace("#{apply_prefix}",String.valueOf(t.getApplyPrefix()));
 		sql  = sql.replace("#{apply_year}",String.valueOf(t.getApplyYear()));
 		sql  = sql.replace("#{apply_num}",String.valueOf(t.getApplyNum()));
 		sql  = sql.replace("#{merge_apply_num}",String.valueOf(t.getMergeApplyNum()));
 		sql  = sql.replace("#{apply_approve_date}",String.valueOf(t.getApplyApproveDate()));
 		sql  = sql.replace("#{host_approve_date}",String.valueOf(t.getHostApproveDate()));
 		sql  = sql.replace("#{organize_approve_date}",String.valueOf(t.getOrganizeApproveDate()));
 		sql  = sql.replace("#{audit_status}",String.valueOf(t.getAuditStatus()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}