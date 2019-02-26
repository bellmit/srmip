package com.kingtake.project.service.budget;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

public interface TpmBudgetServiceI  extends CommonService{
	
	//获取模板左侧
	public List<Map<String, Object>> getLeft(Map mp);
	//获取模板树
	public List<Map<String, Object>> getTemplate(Map mp);
	//预算首页-立项项目查询
	public Map getlxProjectList(Map mp) throws UnsupportedEncodingException;
	//查询模板需要名称
	public List<Map<String,Object>> getPmTemplate(Map<String,Object> mp);
	
	//查询模板名称
	public List<Map<String, Object>> getPmCategory(Map<String,Object> param);
}
