package com.kingtake.project.service.appraisal;
import java.io.Serializable;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;

public interface TBAppraisalApplyServiceI extends CommonService{
	
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
 	public boolean doAddSql(TBAppraisalApplyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalApplyEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalApplyEntity t);

    /**
     * 保存申请与成员
     * 
     * @param tBAppraisalApply
     * @param memberStr
     */
    public void saveApplyAndMember(TBAppraisalApplyEntity tBAppraisalApply, 
    		String memberStr, String sendIds);

    /**
     * 提交申请
     * 
     * @param id
     * @return
     */
    public AjaxJson submitApply(TBAppraisalApplyEntity entity, String sendIds);

    /**
     * 保存批复意见
     * 
     * @param req
     * @return
     */
    public AjaxJson saveAttached(TBAppraisalApplyAttachedEntity attached);

    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TBAppraisalApplyEntity t);
}
