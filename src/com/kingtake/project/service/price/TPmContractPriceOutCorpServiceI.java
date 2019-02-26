package com.kingtake.project.service.price;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.price.TPmContractPriceOutCorpEntity;

public interface TPmContractPriceOutCorpServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceOutCorpEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceOutCorpEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceOutCorpEntity t);
 	
 	public List<TPmContractPriceOutCorpEntity> list(TPmContractPriceOutCorpEntity tPmContractPriceOutCorp);

	public void updateParent(TPmContractPriceOutCorpEntity oldData, TPmContractPriceOutCorpEntity newData, String parentId);
	
	public void update(TPmContractPriceOutCorpEntity newData, String priceBudgetId, AjaxJson j)throws Exception;
	
	public void del(String id, String priceBudgetId, AjaxJson j);
}
