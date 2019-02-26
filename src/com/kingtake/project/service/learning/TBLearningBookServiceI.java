package com.kingtake.project.service.learning;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.learning.TBLearningBookEntity;

public interface TBLearningBookServiceI extends CommonService{
	
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
 	public boolean doAddSql(TBLearningBookEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBLearningBookEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBLearningBookEntity t);

    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TBLearningBookEntity t);
    /**
     * 更新论文及附近关联项目信息字段
     */
    public void updateEntitieAttach(TBLearningBookEntity t);
}
