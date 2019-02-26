package com.kingtake.project.service.ys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public interface YsService {
	
	/**
	 * 获取总预算类型</br>
	 * 计算项目预算方式：总预算、调整总预算、调增总预算
	 * @param param
	 * @return
	 */
	@Autowired
	public Map<String,Object> getZysType(Map<String,Object> param);
	
	/**
	 * 获取年度预算类型
	 * @param param
	 * @return
	 */
	@Autowired
	public Map<String,Object> getNdysType(Map<String,Object> param);
	
	/**
	 * 获取垫支费用
	 * @param param
	 * @return
	 */
	@Autowired
	public List getDzFundsList(Map<String,Object> param);
	
	/**
	 * 获取分配费用
	 * @param param
	 * @return
	 */
	@Autowired
	public List getRlFundsList(Map<String,Object> param);
	
	/**
	 * 获取总预算模板
	 * @param param
	 * @return
	 
	@Autowired
	public Map<String, Object> getZysTemplate(Map<String,Object> param);*/
	
	/**
	 * 获取总预算
	 * @param param
	 * @return
	 */
	@Autowired
	public List getZysFundsList(Map<String,Object> param);
	
	/**
	 * 获取总预算明细
	 * @param param
	 * @return
	 */
	@Autowired
	public List getZysDetailList(Map<String,Object> param);
	
	/**
	 * 获取总预算合计
	 * @param param
	 * @return
	 */
	@Autowired
	public List getZysTotalList(Map<String,Object> param);
	
	/**
	 * 获取预算树
	 * @param param
	 * @return
	 */
	@Autowired
	public Map<String, Object> getTreeTemplate(Map<String,Object> param);
	/**
	 * 获取年度预算
	 * @param param
	 * @return
	 */
	@Autowired
	public List getNdysFundsList(Map<String,Object> param);
	
	/**
	 * 获取年度预算明细
	 * @param param
	 * @return
	 */
	@Autowired
	public List getNdysDetailList(Map<String,Object> param);
	
	/**
	 * 获取年度预算合计
	 * @param param
	 * @return
	 */
	@Autowired
	public List getNdysTotalList(Map<String,Object> param);
	/**
	 * 
	 * delZysContractFunds:(删除预算)
	 * @author liangzhe
	 *
	 * @param param
	 */
	@Autowired
	public int delZysContractFunds(Map<String,Object> param);

	/**
	 * 获取分配列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getFpTotalList(Map<String, Object> param);
	/**
	 * 垫支列表
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getDzTotalList(Map<String, Object> param);
	
	/**
	 * 项目执行情况
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> getProjectExecInfo(Map map);

	/**
	 * 新增、编辑预算
	 * @param param
	 * @return
	 */
	int saveOrUpdateFund(Map<String, Object> param);
	/**
	 * 新增、修改协作费
	 * @param param
	 * @return
	 */
	Map<String,Object> saveOrUpdateXZFunds(Map param);
	/**
	 * 协作费
	 * @param param
	 * @return
	 */
	public Map<String, Object> getXZFunds(Map<String, Object> param);
	/**
	 * 关联协作项目
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getXzProjectList(Map<String, Object> param);
	/**
	 * 新增、编辑开支
	 * @param param
	 * @return
	 */
	Map<String, Object> saveOrUpdateKz(Map param);

	/**
	 * 调整预算申请
	 * @param param
	 */
	Map adjustYsApply(Map param);

}
