package com.kingtake.project.controller.declare.army;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingtake.project.entity.declare.army.TPmFundsBudgetOutsideEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetOutsideServiceI;



/**   
 * @Title: Controller
 * @Description: 外协费
 * @author onlineGenerator
 * @date 2015-08-01 11:46:27
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmFundsBudgetOutsideController")
public class TPmFundsBudgetOutsideController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmFundsBudgetOutsideController.class);

	@Autowired
	private TPmFundsBudgetOutsideServiceI tPmFundsBudgetOutsideService;
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
	public void datagrid(TPmFundsBudgetOutsideEntity tPmFundsBudgetOutside,HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> result = this.tPmFundsBudgetOutsideService.datagrid(tPmFundsBudgetOutside, request);
		TagUtil.listToJson(response, result);
	}

	/**
	 * 删除：外协费
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmFundsBudgetOutsideEntity tPmFundsBudgetOutside, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmFundsBudgetOutside = systemService.getEntity(TPmFundsBudgetOutsideEntity.class, tPmFundsBudgetOutside.getId());
		message = "外协费删除成功";
		try{
			tPmFundsBudgetOutsideService.delete(tPmFundsBudgetOutside);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "外协费删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 添加：外协费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmFundsBudgetOutsideEntity tPmFundsBudgetOutside, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "外协费添加成功";
		try{
			tPmFundsBudgetOutsideService.save(tPmFundsBudgetOutside);
			// 将id返回给页面
			j.setObj(tPmFundsBudgetOutside.getId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "外协费添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新：外协费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmFundsBudgetOutsideEntity tPmFundsBudgetOutside, HttpServletRequest request) {
		tPmFundsBudgetOutside.setId(request.getParameter("ID"));
		tPmFundsBudgetOutside.setBudgetName(request.getParameter("NAME"));
		tPmFundsBudgetOutside.setFunds(StringUtil.isEmpty(request.getParameter("FUNDS")) ? 
				0 : Double.parseDouble(request.getParameter("FUNDS")));
		tPmFundsBudgetOutside.setMemo(request.getParameter("MEMO"));
		tPmFundsBudgetOutside.setOutsideContent(request.getParameter("CONTENT"));
		tPmFundsBudgetOutside.setOutsideUnit(request.getParameter("UNIT"));
		
		AjaxJson j = new AjaxJson();
		message = "外协费更新成功";
		TPmFundsBudgetOutsideEntity t = tPmFundsBudgetOutsideService.get(TPmFundsBudgetOutsideEntity.class, tPmFundsBudgetOutside.getId());
		try {
			// 更新其祖辈节点
			if(t.getFunds() != tPmFundsBudgetOutside.getFunds()){
				tPmFundsBudgetOutsideService.updateParentsMoney(t.getParent(), t.getFunds(), tPmFundsBudgetOutside.getFunds());
			}
			MyBeanUtils.copyBeanNotNull2Bean(tPmFundsBudgetOutside, t);
			tPmFundsBudgetOutsideService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "外协费更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
}
