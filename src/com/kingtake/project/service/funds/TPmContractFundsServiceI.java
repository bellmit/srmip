package com.kingtake.project.service.funds;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.funds.TPmContractFundsEntity;

public interface TPmContractFundsServiceI extends CommonService{
	
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
 	public boolean doAddSql(TPmContractFundsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractFundsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractFundsEntity t);
 	
 	public void init(String apprId);
 	
 	public void initParent(String apprId);
 	
 	/**
     * 获取年度预算明细初始化页面
     * @param iProjectId 项目ID
     * @return 年度预算明细列表
     */
    public List<Map<String, Object>> initYearFundsDetailInfo(String iProjectId);

	public void updateParentsMoney(String parent, double oldMoney, double newMoney);
	
	public List<Map<String, Object>> getTreeBySql(String sql, String projectId, String apprId);

	/*public void batSaveChange(TPmContractFundsEntity[] contracts, TPmPlanFundsEntity[] plans);*/
    
    public void update(TPmContractFundsEntity tPmContractFunds) throws Exception;

	public void del(String id);

	List<Map<String, Object>> queryPlanTree(String apprId);
	
	List<Map<String, Object>> querycontractTree(String apprId);

	List<Map<String, Object>> querycontractTree2(String apprId,String tpId);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
