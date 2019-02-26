package com.kingtake.project.service.impl.appraisal;
import java.io.Serializable;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.appraisal.TBAppraisalApplyMemRelaEntity;
import com.kingtake.project.service.appraisal.TBAppraisalApplyMemRelaServiceI;

@Service("tBAppraisaApplyMemRelaService")
@Transactional
public class TBAppraisalApplyMemRelaServiceImpl extends CommonServiceImpl implements TBAppraisalApplyMemRelaServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBAppraisalApplyMemRelaEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBAppraisalApplyMemRelaEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBAppraisalApplyMemRelaEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalApplyMemRelaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalApplyMemRelaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalApplyMemRelaEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBAppraisalApplyMemRelaEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{t_b_id}", String.valueOf(t.getTbId()));
 		sql  = sql.replace("#{member_id}",String.valueOf(t.getMemberId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}