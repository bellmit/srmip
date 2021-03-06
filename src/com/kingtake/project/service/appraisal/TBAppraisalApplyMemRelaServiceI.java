package com.kingtake.project.service.appraisal;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.appraisal.TBAppraisalApplyMemRelaEntity;

public interface TBAppraisalApplyMemRelaServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalApplyMemRelaEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalApplyMemRelaEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalApplyMemRelaEntity t);
}
