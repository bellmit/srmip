package com.kingtake.project.service.impl.price;
import java.io.Serializable;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.price.TPmContractPriceTechEntity;
import com.kingtake.project.service.price.TPmContractPriceTechServiceI;

@Service("tPmContractPriceTechService")
@Transactional
public class TPmContractPriceTechServiceImpl extends CommonServiceImpl implements TPmContractPriceTechServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
        this.doDelSql((TPmContractPriceTechEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
        this.doAddSql((TPmContractPriceTechEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
        this.doUpdateSql((TPmContractPriceTechEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TPmContractPriceTechEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TPmContractPriceTechEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TPmContractPriceTechEntity t) {
	 	return true;
 	}
 	
}