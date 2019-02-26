package com.kingtake.project.service.funds;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.funds.TPmFundsBudgetAddendumEntity;

public interface TPmFundsBudgetAddendumServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmFundsBudgetAddendumEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmFundsBudgetAddendumEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmFundsBudgetAddendumEntity t);

	public void update(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum) throws Exception;

	public void del(String id);
	
	public void updateParent(String apprId, Double oldMoney, Double newMoney);
}
