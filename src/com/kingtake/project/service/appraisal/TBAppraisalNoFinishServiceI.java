package com.kingtake.project.service.appraisal;
import java.io.Serializable;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.appraisal.TBAppraisalNoFinishEntity;

public interface TBAppraisalNoFinishServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalNoFinishEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalNoFinishEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalNoFinishEntity t);

    /**
     * 保存未完成情况说明
     * 
     * @param tBAppraisalNoFinish
     * @return
     */
    public AjaxJson saveNoFinish(TBAppraisalNoFinishEntity tBAppraisalNoFinish);
}
