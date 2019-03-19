package com.kingtake.project.service.ys;

import java.util.List;
import java.util.Map;

public interface YsDaoService {
	/**
	 * 获取项目信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getProjectById(String id);
	/**
	 * 获取项目经费
	 * @param param
	 * @return
	 */
	public Map<String,Object> getXmFund(Map<String,Object> param);
	
	/**
	 * 获取垫支总经费
	 * @param param
	 * @return
	 */
	public Map<String,Object> getDzFunds(Map<String,Object> param);
	/**
	 * 获取到账总经费
	 * @param param
	 * @return
	 */
	public Map<String,Object> getRlFunds(Map<String,Object> param);
	/**
	 * 获取分配经费
	 * @param param
	 * @return
	 */
	Map<String, Object> getFpFunds(Map<String, Object> param);
	/**
	 * 获取总预算经费
	 * @param param
	 * @return
	 */
	public Map<String,Object> getZysFunds(Map<String,Object> param);
	
	/**
	 * 获取预算
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getYsList(Map<String,Object> param);
	
	/**
	 * 获取预算明细
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getYsDetailList(Map<String, Object> param);
	/**
	 * 获取预算合计
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getYsTotal(Map<String, Object> param);
	/**
	 * 获取模板
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getPmTemplate(Map<String,Object> param);


	/**
	 * 
	 * delProjectFundsAppr:(删除预算主表)
	 * @author liangzhe
	 *
	 * @param param
	 */
	public int delProjectFundsAppr(Map<String,Object> param);
	/**
	 * 
	 * delContractFunds:(删除预算从表)
	 * @author liangzhe
	 *
	 * @param param
	 */
	public int delContractFunds(Map<String,Object> param);
	/**
	 * 获取分配列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getFpTotalList(Map<String, Object> param);
	/**
	 * 获取垫支列表
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getDzTotalList(Map<String, Object> param);
	

	/**
	 * 添加预算主表
	 * @param param
	 * @return
	 */
	int addFundAppr(Map<String, Object> param);
	/**
	 * 添加预算明细
	 * @param param
	 * @return
	 */
	int addFunds(Map<String, Object> param);
	/**
	 * 编辑预算明细表
	 * @param param
	 * @return
	 */
	int editFunds(Map<String, Object> param);

	/**
	 * 获取预算
	 * @param param
	 * @return
	 */
	Map<String, Object> getYsData(Map<String, Object> param);
	
	/**
	 * 获取项目开支明细
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getSpendingList(Map param);
	/**
	 * 获取预算明细</br>
	 * 补充明细节点除去模板以外的数据
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getDetailFunds(Map<String, Object> param);
	/**
	 * 更新预算状态
	 * @param param
	 * @return
	 */
	int updateYSstatus(Map<String, Object> param);
	/**
	 * 年度预算三级以下节点
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getYsTotalChild(Map<String, Object> param);
	/**
	 * 编辑协作费
	 * @param param
	 * @return
	 */
	int updateXZFunds(Map<String, Object> param);
	/**
	 * 添加协作费
	 * @param param
	 * @return
	 */
	String addXZFunds(Map<String, Object> param);
	/**
	 * 获取协作费
	 * @param param
	 * @return
	 */
	Map<String, Object> getXZFunds(Map<String, Object> param);
	/**
	 * 获取开支
	 * @param param
	 * @return
	 */
	Map<String, Object> getKZFunds(Map<String, Object> param);
	/**
	 * 添加开支
	 * @param param
	 * @return
	 */
	int addBudetAddendum(Map<String, Object> param);
	/**
	 * 关联协作项目
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getXzProjectList(Map<String, Object> param);

	/**
	 * 调整预算申请
	 * @param param
	 */
	Map adjustYsApply(Map param);

	/**
	 * 调整预算申请结果
	 * @param map
	 * @return
	 */
	Object getTzyssqRessult(Map map);
}
