package com.kingtake.project.service.supplier;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.supplier.TPmQualitySupplierEntity;

public interface TPmQualitySupplierServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmQualitySupplierEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmQualitySupplierEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmQualitySupplierEntity t);

    /**
     * 刷新有效标志
     */
    public void refreshValidFlag();

    /**
     * 保存
     * 
     * @param tPmQualitySupplier
     */
    public void saveSupplier(TPmQualitySupplierEntity tPmQualitySupplier);

    /**
     * 关联出账合同
     * 
     * @param supplierId
     * @param ids
     */
    public void doRelateContract(String supplierId, String ids);
}
