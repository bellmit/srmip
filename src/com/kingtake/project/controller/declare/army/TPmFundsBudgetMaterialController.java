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

import com.kingtake.project.entity.declare.army.TPmFundsBudgetMaterialEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetMaterialServiceI;



/**   
 * @Title: Controller
 * @Description: 材料费
 * @author onlineGenerator
 * @date 2015-08-01 11:46:19
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmFundsBudgetMaterialController")
public class TPmFundsBudgetMaterialController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmFundsBudgetMaterialController.class);

	@Autowired
	private TPmFundsBudgetMaterialServiceI tPmFundsBudgetMaterialService;
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
	public void datagrid(TPmFundsBudgetMaterialEntity tPmFundsBudgetMaterial,HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> result = this.tPmFundsBudgetMaterialService.datagrid(tPmFundsBudgetMaterial, request);
		TagUtil.listToJson(response, result);
	}

	/**
	 * 删除:材料费
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmFundsBudgetMaterialEntity tPmFundsBudgetMaterial, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmFundsBudgetMaterial = systemService.getEntity(TPmFundsBudgetMaterialEntity.class, tPmFundsBudgetMaterial.getId());
		message = "材料费删除成功";
		try{
			tPmFundsBudgetMaterialService.delete(tPmFundsBudgetMaterial);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "材料费删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加：材料费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmFundsBudgetMaterialEntity tPmFundsBudgetMaterial, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "材料费添加成功";
		try{
			tPmFundsBudgetMaterialService.save(tPmFundsBudgetMaterial);
			// 将id返回给页面
			j.setObj(tPmFundsBudgetMaterial.getId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "材料费添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新：材料费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmFundsBudgetMaterialEntity tPmFundsBudgetMaterial, HttpServletRequest request) {
		tPmFundsBudgetMaterial.setId(request.getParameter("ID"));
		tPmFundsBudgetMaterial.setBudgetName(request.getParameter("NAME"));
		tPmFundsBudgetMaterial.setFunds(StringUtil.isEmpty(request.getParameter("FUNDS")) ? 
				0 : Double.parseDouble(request.getParameter("FUNDS")));
		tPmFundsBudgetMaterial.setMemo(request.getParameter("MEMO"));
		tPmFundsBudgetMaterial.setModelStandard(request.getParameter("MODEL"));
		tPmFundsBudgetMaterial.setQuantity(StringUtil.isEmpty(request.getParameter("QUANTITY"))? 
				0 : Integer.parseInt(request.getParameter("QUANTITY")));
		tPmFundsBudgetMaterial.setUnitPrice(StringUtil.isEmpty(request.getParameter("PRICE")) ? 
				0 : Double.parseDouble(request.getParameter("PRICE")));
		
		AjaxJson j = new AjaxJson();
		message = "材料费更新成功";
		TPmFundsBudgetMaterialEntity t = tPmFundsBudgetMaterialService.get(
				TPmFundsBudgetMaterialEntity.class, tPmFundsBudgetMaterial.getId());
		try {
			// 更新其祖辈节点
			if(t.getFunds() != tPmFundsBudgetMaterial.getFunds()){
				tPmFundsBudgetMaterialService.updateParentsMoney(t.getParent(), t.getFunds(), tPmFundsBudgetMaterial.getFunds());
			}
			MyBeanUtils.copyBeanNotNull2Bean(tPmFundsBudgetMaterial, t);
			tPmFundsBudgetMaterialService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "材料费更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
}
