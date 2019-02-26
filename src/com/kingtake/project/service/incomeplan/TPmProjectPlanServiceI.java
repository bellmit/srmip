package com.kingtake.project.service.incomeplan;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;

public interface TPmProjectPlanServiceI extends CommonService {
	
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
    public boolean doAddSql(TPmProjectPlanEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TPmProjectPlanEntity t);
 	
    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TPmProjectPlanEntity t);  
}
