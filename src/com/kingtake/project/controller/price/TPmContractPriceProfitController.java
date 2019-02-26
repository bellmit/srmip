package com.kingtake.project.controller.price;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPriceMasterEntity;
import com.kingtake.project.entity.price.TPmContractPriceProfitEntity;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;
import com.kingtake.project.service.price.TPmContractPriceProfitServiceI;



/**   
 * @Title: Controller
 * @Description: 价款计算书：收益类
 * @author onlineGenerator
 * @date 2015-08-10 15:57:47
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractPriceProfitController")
public class TPmContractPriceProfitController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmContractPriceProfitController.class);

	@Autowired
	private TPmContractPriceProfitServiceI tPmContractPriceProfitService;
	@Autowired
	private TPmContractPriceMasterServiceI tPmContractPriceMasterService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPmContractPriceProfitEntity tPmContractPriceProfit,HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		// 查询主表信息
		List<TPmContractPriceMasterEntity> masters = systemService.getSession().createCriteria(TPmContractPriceMasterEntity.class)
			.add(Restrictions.eq("tpId", tPmContractPriceProfit.getTpId()))
			.add(Restrictions.isNull("parent"))
			.addOrder(Order.asc("sortCode")).list();
		Map<String, Object> master = new HashMap<String, Object>();
		master.put("id", masters.get(0).getId());
		master.put("typeName", masters.get(0).getPriceBudgetName());
		master.put("priceAmount", masters.get(0).getPriceColumn());
		master.put("auditAmount", masters.get(0).getAuditColumn());
		master.put("memo", masters.get(0).getMemo());
		master.put("serialNum", masters.get(0).getSortCode());
		master.put("addChildFlag", "0");
		result.add(master);
		
		// 查询利益表
		List<TPmContractPriceProfitEntity> profits = systemService.getSession().createCriteria(TPmContractPriceProfitEntity.class)
				.add(Restrictions.eq("tpId", tPmContractPriceProfit.getTpId()))
				.addOrder(Order.asc("serialNum")).list();
		for(int i =0; i < profits.size(); i++){
			TPmContractPriceProfitEntity profit = profits.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("id", profit.getId());
			map.put("typeName", profit.getTypeName());
			map.put("priceExplain", profit.getPriceExplain());
			map.put("pricePercent", profit.getPricePercent());
			map.put("priceAmount", profit.getPriceAmount());
			map.put("auditPercent", profit.getAuditPercent());
			map.put("auditAmount", profit.getAuditAmount());
			map.put("memo", profit.getMemo());
			map.put("serialNum", profit.getSerialNum());
			map.put("addChildFlag", "2");
			
			result.add(map);
		}
		TagUtil.listToJson(response, result);
	}

	
	/**
	 * 更新
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmContractPriceProfitEntity tPmContractPriceProfit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		
		try {
			tPmContractPriceProfitService.update(tPmContractPriceProfit, j);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmContractPriceProfitEntity tPmContractPriceProfit, HttpServletRequest req) {
		// 查询合同信息
		req.setAttribute("cover", systemService.get(TPmContractPriceCoverEntity.class, tPmContractPriceProfit.getTpId()));
		// 标题
		try {
            String title = URLDecoder.decode(req.getParameter("title"), "UTF-8");
			req.setAttribute("title", title);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		req.setAttribute("read", req.getParameter("read"));
		return new ModelAndView("com/kingtake/project/price/tPmContractPriceProfit-update");
	}
}
