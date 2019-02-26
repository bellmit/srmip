package com.kingtake.project.service.price;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.price.TPmContractPriceMasterEntity;

public interface TPmContractPriceMasterServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceMasterEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceMasterEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceMasterEntity t);

	public List<TPmContractPriceMasterEntity> list(TPmContractPriceMasterEntity tPmContractPriceMaster);

	/**
	 * 子项变更导致父节点变更
	 * @param oldData
	 * @param newData
	 * @param parent
	 */
	public void updateParent(TPmContractPriceMasterEntity oldData,TPmContractPriceMasterEntity newData, String parent);
	
	/**
	 * 明细表变更导致主表变更
	 * @param tpId
	 * @param priceBudgetId
	 * @param priceAudit
	 */
	public void updateMaster(String tpId, String priceBudgetId, Double[] priceAudit);
 	
}
