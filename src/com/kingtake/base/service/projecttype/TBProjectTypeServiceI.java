package com.kingtake.base.service.projecttype;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;

public interface TBProjectTypeServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBProjectTypeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBProjectTypeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBProjectTypeEntity t);

    /**
     * 
     * 删除项目类型
     * 
     * @param tBProjectType
     */
    public void delProjectType(TBProjectTypeEntity tBProjectType);
}
