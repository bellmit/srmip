package com.kingtake.project.service.declare.army;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.declare.army.TBPmDeclareTechnologyEntity;

public interface TBPmDeclareTechnologyServiceI extends CommonService{
	
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
 	public boolean doAddSql(TBPmDeclareTechnologyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBPmDeclareTechnologyEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBPmDeclareTechnologyEntity t);

	public TBPmDeclareTechnologyEntity init(String projectId);
	/**
     * 删除业务数据同时删除附件
     */
	public void deleteAddAttach(TBPmDeclareTechnologyEntity t);
}
