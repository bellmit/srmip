package com.kingtake.project.controller.ys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.kingtake.project.service.ys.YsService;

/**
 * 预算管理
 * @author XiaoZhongliang
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ysController")
public class YsController extends BaseController{
	@Autowired
	private YsService ysService;

	/**
	 * 获取总预算类型 调整、调增
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getZysType")
	public void getZysType(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		Map<String,Object> result = ysService.getZysType(param);
		String jsonStr = JSON.toJSONString(result);
        TagUtil.responseOut(response, jsonStr);
	}

	/**
	 * 获取年度预算类型  调整、调增
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getNdysType")
	public void getNdysType(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		Map<String,Object> result = ysService.getNdysType(param);
		String jsonStr = JSON.toJSONString(result);
        TagUtil.responseOut(response, jsonStr);
	}
	
	
	
	/**
	 * 获取总预算模板
	 * @param request
	 * @param response
	 
	@RequestMapping(params = "getZysTemplate")
	public void getZysTemplate(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		Map<String,Object> result = ysService.getZysTemplate(param);
		String jsonStr = JSON.toJSONString(result);
        TagUtil.responseOut(response, jsonStr);
	}*/
	/**
	 * 获取总预算
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getZysFundsList")
	public void getZysFundsList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		List<Map<String,Object>> result = ysService.getZysFundsList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 获取总预算明细
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getZysDetailList")
	public void getZysDetailList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		List<Map<String,Object>> result = ysService.getZysDetailList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}
	
	/**
	 * 获取预算模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getTreeTemplate")
	public void getTreeTemplate(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		Map<String,Object> result = ysService.getTreeTemplate(param);
		String jsonStr = JSON.toJSONString(result);
        TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 获取年度预算
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getNdysFundsList")
	public void getNdysFundsList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		List<Map<String,Object>> result = ysService.getNdysFundsList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 获取年度预算明细
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getNdysDetailList")
	public void getNdysDetailList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		List<Map<String,Object>> result = ysService.getNdysDetailList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}
	
	/**
	 * 获取年度预算合计
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getNdysTotalList")
	public void getNdysTotalList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);   
		List<Map<String,Object>> result = ysService.getNdysTotalList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}

	/**
	 * 获取总预算合计
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getZysTotalList")
	public void getZysTotalList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		List<Map<String,Object>> result = ysService.getZysTotalList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
		TagUtil.responseOut(response, jsonStr);
	}

	/**
	 * 新增总预算
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "saveOrUpdateFund")
	public void saveOrUpdateFund(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		Map json = new HashedMap();
		try {
			int result = ysService.saveOrUpdateFund(param);
			json.put("code", 0);
			json.put("msg", "新增成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 1);
			json.put("msg", "新增失败");
		}finally {
			String jsonStr = JSON.toJSONString(json);
			TagUtil.responseOut(response, jsonStr);
		}

	}

	/**
	 * 
	 * delZysContractFunds:(删除总预算与年度预算)
	 * @author liangzhe FUNDS_CATEGORY 1：总预算；2：年度预算
	 * ID:预算主表ID
	 *
	 */
	@RequestMapping(params = "delZysContractFunds")
	public void delZysContractFunds(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			int num = ysService.delZysContractFunds(param);
			json.put("code", 0);
		    json.put("msg", "删除预算信息成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("code", 1);
		    json.put("msg", "删除预算信息失败");
		}finally {
			String jsonStr = JSON.toJSONString(json);
			TagUtil.responseOut(response, jsonStr);
		}
	}
	/**
	 * 获取分配列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getFpTotalList")
	public void getFpTotalList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		List<Map<String,Object>> result = ysService.getFpTotalList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
		TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 获取垫支列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getDzTotalList")
	public void getDzTotalList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		List<Map<String,Object>> result = ysService.getDzTotalList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
		TagUtil.responseOut(response, jsonStr);
	}


	/**
	 * 获取项目执行情况
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getProjectExecList")
	public void getProjectExecList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
        List<Map<String,Object>> result = ysService.getProjectExecInfo(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
		TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 新增、修改协作费
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "saveOrUpdateXZFunds")
	public void saveOrUpdateXZFunds(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
        Map<String,Object> result = ysService.saveOrUpdateXZFunds(param);
		String jsonStr = JSON.toJSONString(result);
		TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 获取协作费
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getXZFunds")
	public void getXZFunds(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
        Map<String,Object> result = ysService.getXZFunds(param);
		String jsonStr = JSON.toJSONString(result);
		TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 获取协作费
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getXzProjectList")
	public void getXzProjectList(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		List<Map<String,Object>> result = ysService.getXzProjectList(param);
		String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
		TagUtil.responseOut(response, jsonStr);
	}
	/**
	 * 新增、编辑开支
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "saveOrUpdateKz")
	public void saveOrUpdateKz(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		Map<String,Object> result = ysService.saveOrUpdateKz(param);
		String jsonStr = JSON.toJSONString(result);
		TagUtil.responseOut(response, jsonStr);
	}

	/**
	 * 调整预算申请
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "adjustYsApply")
	public void adjustYsApply(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = TagUtil.getMapByRequest(request);
		Map result = ysService.adjustYsApply(param);
		String jsonStr = JSON.toJSONString(result);
		TagUtil.responseOut(response, jsonStr);
	}
	
}
