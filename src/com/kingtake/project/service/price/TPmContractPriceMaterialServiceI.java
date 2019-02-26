package com.kingtake.project.service.price;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.price.TPmContractPriceDesignEntity;
import com.kingtake.project.entity.price.TPmContractPriceMaterialEntity;

public interface TPmContractPriceMaterialServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceMaterialEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceMaterialEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceMaterialEntity t);
 	
 	/**
 	 * 根据根节点加载table内容
 	 * @param tPmContractPriceMaterial
 	 * @return
 	 */
	public List<TPmContractPriceMaterialEntity> list(TPmContractPriceMaterialEntity tPmContractPriceMaterial);

	/**
	 * 更新父辈节点，以及主表数据
	 * @param oldData
	 * @param newData
	 * @param parentId
	 */
	public void updateParent(TPmContractPriceMaterialEntity oldData, TPmContractPriceMaterialEntity newData, String parentId);
	
	public void update(TPmContractPriceMaterialEntity newData, String priceBudgetId, AjaxJson j)throws Exception;
	
	public void del(String id, String priceBudgetId, AjaxJson j);
}
