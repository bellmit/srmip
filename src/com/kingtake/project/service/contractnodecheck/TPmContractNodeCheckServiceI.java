package com.kingtake.project.service.contractnodecheck;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.contractnodecheck.TPmContractNodeCheckEntity;

public interface TPmContractNodeCheckServiceI extends CommonService{
	
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
 	public boolean doAddSql(TPmContractNodeCheckEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractNodeCheckEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractNodeCheckEntity t);

 	/**
 	 * 查询项目节点审批记录数
 	 */
    public Integer getProjectNodeCheckCount(Map<String, Object> param);

    /**
     * 查询项目节点审批记录
     * 
     * @param start
     * @param end
     * @param param
     * @return
     */
    public List<Map<String, Object>> getProjectNodeApprReceiveList(int start, int end, Map<String, Object> param);

    /**
     * 更新审批状态
     * 
     * @param id
     * @return
     */
    public AjaxJson updateOperateStatus(String id);

    /**
     * 更新审批状态2
     * 
     * @param id
     * @return
     */
    public AjaxJson updateOperateStatus2(String id);

    /**
     * 更新支付申请状态
     * 
     * @param id
     * @return
     */
    public AjaxJson updatePayApplyOperateStatus(String id);

    /**
     * 更新支付申请状态2
     * 
     * @param id
     * @return
     */
    public AjaxJson updatePayApplyOperateStatus2(String id);
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TPmContractNodeCheckEntity t);

}
