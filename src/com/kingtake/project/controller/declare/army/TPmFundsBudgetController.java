package com.kingtake.project.controller.declare.army;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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

import com.kingtake.project.entity.declare.army.TPmFundsBudgetEntity;
import com.kingtake.project.service.declare.army.TPmFundsBudgetServiceI;



/**   
 * @Title: Controller
 * @Description: 项目经费年度预算
 * @author onlineGenerator
 * @date 2015-08-01 11:44:58
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmFundsBudgetController")
public class TPmFundsBudgetController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmFundsBudgetController.class);

	@Autowired
	private TPmFundsBudgetServiceI tPmFundsBudgetService;
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
	public void datagrid(TPmFundsBudgetEntity tPmFundsBudget,HttpServletRequest request, HttpServletResponse response) {
		Session session = this.tPmFundsBudgetService.getSession();
		List<TPmFundsBudgetEntity> list = session.createCriteria(TPmFundsBudgetEntity.class).add(
				Restrictions.eq("projDeclareId", tPmFundsBudget.getProjDeclareId())).list();
		TagUtil.response(response, TagUtil.listtojson(new String[]{
				"id", "budgetYear", "equipFunds", "materialFunds", "outsideFunds", "businessFunds", 
				"totalFunds", "memo"}, list));
	}

	/**
	 * 删除：年度预算
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmFundsBudgetEntity tPmFundsBudget, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmFundsBudget = systemService.getEntity(TPmFundsBudgetEntity.class, tPmFundsBudget.getId());
		message = "年度预算删除成功";
		try{
			tPmFundsBudgetService.delete(tPmFundsBudget);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "年度预算删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 添加:年度预算
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmFundsBudgetEntity tPmFundsBudget, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "年度预算添加成功";
		try{
			// 计算年度预算的合计数
			tPmFundsBudget.setTotalFunds(
					(tPmFundsBudget.getEquipFunds() == null ? new BigDecimal(0) : tPmFundsBudget.getEquipFunds())
					.add(tPmFundsBudget.getMaterialFunds() == null ? new BigDecimal(0) : tPmFundsBudget.getMaterialFunds())
					.add(tPmFundsBudget.getOutsideFunds() == null ? new BigDecimal(0) : tPmFundsBudget.getOutsideFunds())
					.add(tPmFundsBudget.getBusinessFunds() == null ? new BigDecimal(0) : tPmFundsBudget.getBusinessFunds()));
			tPmFundsBudgetService.save(tPmFundsBudget);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "年度预算添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 同一申报书，同一年只能有一条记录
	 * @param tPmFundsBudget
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "isExistYear")
	@ResponseBody
	public void isExistYear(TPmFundsBudgetEntity tPmFundsBudget, HttpServletRequest request, HttpServletResponse response){
		List<TPmFundsBudgetEntity> list = systemService.getSession().createCriteria(TPmFundsBudgetEntity.class)
			.add(Restrictions.eq("projDeclareId", tPmFundsBudget.getProjDeclareId()))
			.add(Restrictions.eq("budgetYear", tPmFundsBudget.getBudgetYear())).list();
		
		if(list != null && list.size() > 0){
			TagUtil.response(response, "true");
		}else{
			TagUtil.response(response, "false");
		}
	}
	
	/**
	 * 更新T_PM_FUNDS_BUDGET
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmFundsBudgetEntity tPmFundsBudget, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "年度预算更新成功";
		TPmFundsBudgetEntity t = tPmFundsBudgetService.get(TPmFundsBudgetEntity.class, tPmFundsBudget.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPmFundsBudget, t);
			t.setTotalFunds(
					(t.getEquipFunds() == null ? new BigDecimal(0) : t.getEquipFunds())
					.add(t.getMaterialFunds() == null ? new BigDecimal(0) : t.getMaterialFunds())
					.add(t.getOutsideFunds() == null ? new BigDecimal(0) : t.getOutsideFunds())
					.add(t.getBusinessFunds() == null ? new BigDecimal(0) : t.getBusinessFunds()));
			tPmFundsBudgetService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "年度预算更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 经费年度预算：添加、编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmFundsBudgetEntity tPmFundsBudget, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmFundsBudget.getId())) {
			tPmFundsBudget = tPmFundsBudgetService.getEntity(TPmFundsBudgetEntity.class, tPmFundsBudget.getId());
		}
		req.setAttribute("tPmFundsBudget", tPmFundsBudget);
		return new ModelAndView("com/kingtake/project/declare/army/tPmFundsBudget-update");
	}
}
