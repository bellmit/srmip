package com.kingtake.project.service.task;
import java.util.List;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.task.TPmTaskEntity;
import com.kingtake.project.entity.task.TPmTaskNodeEntity;

public interface TPmTaskServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TPmTaskEntity tPmTask,
	        List<TPmTaskNodeEntity> tPmTaskNodeList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TPmTaskEntity tPmTask,
	        List<TPmTaskNodeEntity> tPmTaskNodeList);
	public void delMain (TPmTaskEntity tPmTask);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmTaskEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmTaskEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmTaskEntity t);

    
}
