package com.kingtake.project.controller.budget;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.kingtake.project.service.budget.TpmBudgetServiceI;
import com.kingtake.project.service.ys.YsDaoService;

@Scope("prototype")
@Controller
@RequestMapping("/TpmBudgetController")
public class TpmBudgetController {
	
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TpmBudgetController.class);

	@Autowired
	private TpmBudgetServiceI tpmBudgetService;
	@Autowired
	private YsDaoService ysDaoService;
	
    /**
     * 预算界面模板跳转
     * @return
     */
    @RequestMapping(params = "goBudgetTemplete")
    public ModelAndView goBudgetTemplete(HttpServletRequest request){
        return new ModelAndView("budget/templates/budgetTemplate/budgetTemplate");
    }
    
    //获取左侧模板值
	@RequestMapping(params = "getLeft")
	public void getLeft(HttpServletRequest request, HttpServletResponse response) {
        Map mp = TagUtil.getMapByRequest(request);             
        List<Map<String, Object>> result = tpmBudgetService.getLeft(mp);
        String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}
	
	//获取模板树
	@RequestMapping(params = "getTemplate")
	public void getTemplate(HttpServletRequest request, HttpServletResponse response) {
        Map mp = TagUtil.getMapByRequest(request); 
        logger.info(mp);
        List<Map<String, Object>> result = tpmBudgetService.getTemplate(mp);
        String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}
	
	//获取模板树名称
	@RequestMapping(params = "getPmTemplate")
	public void getPmTemplate(HttpServletRequest request, HttpServletResponse response) {
        Map mp = TagUtil.getMapByRequest(request); 
        String projectId = MapUtils.getString(mp, "projectId");
        List<Map<String, Object>> result = tpmBudgetService.getPmTemplate(mp);
        Map<String, Object> data = TagUtil.getDataJson(result);
        mp.put("T_P_ID", projectId);
        Map<String, Object> xmFund = ysDaoService.getXmFund(mp);
        data.putAll(xmFund);
        String jsonStr = JSON.toJSONString(data);
        TagUtil.responseOut(response, jsonStr);
	}
	@RequestMapping(params = "getPmCategory")
	public void getPmCategory(HttpServletRequest request, HttpServletResponse response) {
		Map mp = TagUtil.getMapByRequest(request); 
		logger.info(mp);
		List<Map<String, Object>> result = tpmBudgetService.getPmCategory(mp);
        String jsonStr = JSON.toJSONString(TagUtil.getDataJson(result));
        TagUtil.responseOut(response, jsonStr);
	}
	  
	//预算首页-立项项目查询
	@RequestMapping(params = "getlxProjectList")
	public void getlxProjectList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Map mp = TagUtil.getMapByRequest(request); 
        logger.info(mp);
        //取得个人角色 code
        String usrRoleCodes = (String)request.getSession().getAttribute("currentRoleCodes"); 
        usrRoleCodes = usrRoleCodes == null ? "":usrRoleCodes;
        mp.put("usrRoleCodes", usrRoleCodes);
        Map result = tpmBudgetService.getlxProjectList(mp);
        String jsonStr = JSON.toJSONString(result);
        TagUtil.responseOut(response, jsonStr);
	}
    
}
