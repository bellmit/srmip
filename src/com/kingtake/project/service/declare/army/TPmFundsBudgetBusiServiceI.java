package com.kingtake.project.service.declare.army;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.declare.army.TPmFundsBudgetBusiEntity;

public interface TPmFundsBudgetBusiServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmFundsBudgetBusiEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmFundsBudgetBusiEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmFundsBudgetBusiEntity t);

	public void init(String projDeclareId, Class clazz)throws Exception;

	public void initUpdate(String projDeclareId);

	public void updateParentsMoney(String parent, double oldMoney, double newMoney);

    public List<Map<String, Object>> datagrid(TPmFundsBudgetBusiEntity tPmFundsBudgetBusi, HttpServletRequest request);
}
