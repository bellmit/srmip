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
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingtake.project.entity.declare.army.TPmFundsBudgetBusiEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetBusiServiceI;



/**   
 * @Title: Controller
 * @Description: 业务费
 * @author onlineGenerator
 * @date 2015-08-01 11:46:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmFundsBudgetBusiController")
public class TPmFundsBudgetBusiController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmFundsBudgetBusiController.class);

	@Autowired
	private TPmFundsBudgetBusiServiceI tPmFundsBudgetBusiService;
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
	public void datagrid(TPmFundsBudgetBusiEntity tPmFundsBudgetBusi,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        List<Map<String, Object>> result = this.tPmFundsBudgetBusiService.datagrid(tPmFundsBudgetBusi, request);
		TagUtil.listToJson(response, result);
	}

	/**
	 * 删除：业务费
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmFundsBudgetBusiEntity tPmFundsBudgetBusi, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmFundsBudgetBusi = systemService.getEntity(TPmFundsBudgetBusiEntity.class, tPmFundsBudgetBusi.getId());
		message = "业务费删除成功";
		try{
			tPmFundsBudgetBusiService.delete(tPmFundsBudgetBusi);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "业务费删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 添加:业务费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmFundsBudgetBusiEntity tPmFundsBudgetBusi, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "业务费添加成功";
		try{
			tPmFundsBudgetBusiService.save(tPmFundsBudgetBusi);
			// 将id返回给页面
			j.setObj(tPmFundsBudgetBusi.getId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "业务费添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新：业务费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmFundsBudgetBusiEntity tPmFundsBudgetBusi, HttpServletRequest request) {
		tPmFundsBudgetBusi.setId(request.getParameter("ID"));
		tPmFundsBudgetBusi.setBudgetName(request.getParameter("NAME"));
		tPmFundsBudgetBusi.setFunds(StringUtil.isEmpty(request.getParameter("FUNDS")) ? 
				0 : Double.parseDouble(request.getParameter("FUNDS")));
		tPmFundsBudgetBusi.setMemo(request.getParameter("MEMO"));
		tPmFundsBudgetBusi.setBriefContent(request.getParameter("CONTENT"));
		
		AjaxJson j = new AjaxJson();
		message = "业务费更新成功";
		TPmFundsBudgetBusiEntity t = tPmFundsBudgetBusiService.get(TPmFundsBudgetBusiEntity.class, tPmFundsBudgetBusi.getId());
		try {
			// 更新其祖辈节点
			if(t.getFunds() != tPmFundsBudgetBusi.getFunds()){
				tPmFundsBudgetBusiService.updateParentsMoney(t.getParent(), t.getFunds(), tPmFundsBudgetBusi.getFunds());
			}
			MyBeanUtils.copyBeanNotNull2Bean(tPmFundsBudgetBusi, t);
			tPmFundsBudgetBusiService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "业务费更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

}
