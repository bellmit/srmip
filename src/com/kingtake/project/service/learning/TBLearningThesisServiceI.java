package com.kingtake.project.service.learning;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.workflow.model.activiti.Variable;

import com.kingtake.project.entity.learning.TBLearningThesisEntity;

public interface TBLearningThesisServiceI extends CommonService{
	
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
 	public boolean doAddSql(TBLearningThesisEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBLearningThesisEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBLearningThesisEntity t);

    /**
     * 提交流程
     * 
     * @param request
     * @param var
     * @return
     */
    public AjaxJson startProcess(HttpServletRequest request, Variable var);
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TBLearningThesisEntity t);
    /**
     * 更新论文及附近关联项目信息字段
     */
    public void updateEntitieAttach(TBLearningThesisEntity t);
}
