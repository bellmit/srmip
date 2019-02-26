package com.kingtake.office.service.approval;
import com.kingtake.office.entity.approval.TOApprovalProjectSummaryEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TOApprovalProjectSummaryServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOApprovalProjectSummaryEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOApprovalProjectSummaryEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOApprovalProjectSummaryEntity t);
}
