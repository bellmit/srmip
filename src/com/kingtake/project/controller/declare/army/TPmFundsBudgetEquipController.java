package com.kingtake.project.controller.declare.army;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.project.entity.declare.army.TPmFundsBudgetEquipEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetEquipServiceI;



/**   
 * @Title: Controller
 * @Description: 设备费 	
 * @author onlineGenerator
 * @date 2015-08-01 11:45:26
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmFundsBudgetEquipController")
public class TPmFundsBudgetEquipController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmFundsBudgetEquipController.class);

	@Autowired
	private TPmFundsBudgetEquipServiceI tPmFundsBudgetEquipService;
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
	public void datagrid(TPmFundsBudgetEquipEntity tPmFundsBudgetEquip,HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> result = this.tPmFundsBudgetEquipService.datagrid(tPmFundsBudgetEquip, request);
		TagUtil.listToJson(response, result);
	}

	/**
	 * 删除:设备费
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmFundsBudgetEquipEntity tPmFundsBudgetEquip, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmFundsBudgetEquip = systemService.getEntity(TPmFundsBudgetEquipEntity.class, tPmFundsBudgetEquip.getId());
		message = "设备费删除成功";
		try{
			tPmFundsBudgetEquipService.delete(tPmFundsBudgetEquip);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "设备费删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加：设备预算
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmFundsBudgetEquipEntity tPmFundsBudgetEquip, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "设备费添加成功";
		try{
			tPmFundsBudgetEquipService.save(tPmFundsBudgetEquip);
			// 将id返回给页面
			j.setObj(tPmFundsBudgetEquip.getId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "设备费添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新：设备费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmFundsBudgetEquipEntity tPmFundsBudgetEquip, HttpServletRequest request) {
		tPmFundsBudgetEquip.setId(request.getParameter("ID"));
		tPmFundsBudgetEquip.setBudgetName(request.getParameter("NAME"));
		tPmFundsBudgetEquip.setFunds(StringUtil.isEmpty(request.getParameter("FUNDS")) ? 
				0 : Double.parseDouble(request.getParameter("FUNDS")));
		tPmFundsBudgetEquip.setMemo(request.getParameter("MEMO"));
		tPmFundsBudgetEquip.setModelConfig(request.getParameter("CONFIG"));
		tPmFundsBudgetEquip.setQuantity(StringUtil.isEmpty(request.getParameter("QUANTITY")) ? 
				0 : Integer.parseInt(request.getParameter("QUANTITY")));
		tPmFundsBudgetEquip.setUnitPrice(StringUtil.isEmpty(request.getParameter("PRICE")) ? 
				0 : Double.parseDouble(request.getParameter("PRICE")));
		
		AjaxJson j = new AjaxJson();
		message = "设备费更新成功";
		TPmFundsBudgetEquipEntity t = tPmFundsBudgetEquipService.get(
				TPmFundsBudgetEquipEntity.class, tPmFundsBudgetEquip.getId());
		try {
			// 更新其祖辈节点
			if(t.getFunds() != tPmFundsBudgetEquip.getFunds()){
				tPmFundsBudgetEquipService.updateParentsMoney(t.getParent(), t.getFunds(), tPmFundsBudgetEquip.getFunds());
			}
			MyBeanUtils.copyBeanNotNull2Bean(tPmFundsBudgetEquip, t);
			tPmFundsBudgetEquipService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "设备费更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 设备预算：编辑、新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmFundsBudgetEquipEntity tPmFundsBudgetEquip, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmFundsBudgetEquip.getId())) {
			tPmFundsBudgetEquip = tPmFundsBudgetEquipService.getEntity(TPmFundsBudgetEquipEntity.class, tPmFundsBudgetEquip.getId());
		}
		
		req.setAttribute("tPmFundsBudgetEquipPage", tPmFundsBudgetEquip);
		return new ModelAndView("com/kingtake/project/funds/tPmFundsBudgetEquip-update");
	}
	
}
