package com.kingtake.base.service.budget;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;

public interface TBApprovalBudgetRelaServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBApprovalBudgetRelaEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBApprovalBudgetRelaEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBApprovalBudgetRelaEntity t);

    /**
     * 保存项目类型与模块关联
     * 
     * @param list
     */
    public void saveProjectTypeInfoRela(String projectTypeId, String catalogListStr);
    /**
     * 保存从其它对应项目类型的关联模块复制到当前项目类型的数据，先删除当前项目类型数据再新增
     * 
     * @param void
     */
    public String saveCatalogRelaFromCopy(String projectTypeId, String fromProjectId);

    /**
     * 保存从其它对应项目类型的预算类别复制到当前项目类型的数据，先删除当前项目类型数据再新增
     * 
     * @param void
     */
    public void saveApprovalBudgetFromCopy(String id, String fromProjectId);
    
    /**
     * 保存从其它对应经费类型的预算类别复制到当前经费类型的数据，先删除当前经费类型数据再新增
     * 
     * @param void
     */
    public void saveApprovalBudgetFromCopyFund(String id, String fromFundPropertyId);
}
