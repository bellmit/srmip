package com.kingtake.project.controller.declare.army;
import java.util.List;
import java.util.Map;

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

import com.kingtake.project.entity.declare.army.TPmDeclareMemberEntity;
import com.kingtake.project.service.declare.army.TPmDeclareMemberServiceI;



/**   
 * @Title: Controller
 * @Description: 申报书人员信息
 * @author onlineGenerator
 * @date 2015-08-01 11:43:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmDeclareMemberController")
public class TPmDeclareMemberController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmDeclareMemberController.class);

	@Autowired
	private TPmDeclareMemberServiceI tPmDeclareMemberService;
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
	public void datagrid(TPmDeclareMemberEntity tPmDeclareMember,HttpServletRequest request, HttpServletResponse response) {
		Session session = this.tPmDeclareMemberService.getSession();
		List list = session.createCriteria(TPmDeclareMemberEntity.class).add(
				Restrictions.eq("projDeclareId", tPmDeclareMember.getProjDeclareId())).list();
		TagUtil.response(response, TagUtil.listtojson(new String[]{
				"id", "name", "sex", "birthday", "degree", "contactPhone", 
				"superUnit", "postCode", "postalAddress", "position", "taskDivide", "projectManager"}, list));
	}
	
	@RequestMapping(params = "comboList")
	public void comboList(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = tPmDeclareMemberService
				.findForJdbc("SELECT ID, NAME AS TEXT FROM T_PM_DECLARE_MEMBER WHERE PROJ_DECLARE_ID = ?", 
						request.getParameter("projDeclareId"));
		TagUtil.listToJson(response, list);
	}

	/**
	 * 删除T_PM_DECLARE_MEMBER
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmDeclareMemberEntity tPmDeclareMember, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmDeclareMember = systemService.getEntity(TPmDeclareMemberEntity.class, tPmDeclareMember.getId());
		message = "成员删除成功";
		try{
			tPmDeclareMemberService.delete(tPmDeclareMember);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "成员删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除T_PM_DECLARE_MEMBER
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "成员删除成功";
		try{
			for(String id:ids.split(",")){
				TPmDeclareMemberEntity tPmDeclareMember = systemService.getEntity(TPmDeclareMemberEntity.class, id);
				tPmDeclareMemberService.delete(tPmDeclareMember);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "成员删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加成员
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmDeclareMemberEntity tPmDeclareMember, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "成员添加成功";
		try{
			tPmDeclareMemberService.save(tPmDeclareMember);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "成员添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新T_PM_DECLARE_MEMBER
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmDeclareMemberEntity tPmDeclareMember, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "成员更新成功";
		TPmDeclareMemberEntity t = tPmDeclareMemberService.get(TPmDeclareMemberEntity.class, tPmDeclareMember.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPmDeclareMember, t);
			tPmDeclareMemberService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "成员更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 新增、更新页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmDeclareMemberEntity tPmDeclareMember, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmDeclareMember.getId())) {
			tPmDeclareMember = tPmDeclareMemberService.getEntity(TPmDeclareMemberEntity.class, tPmDeclareMember.getId());
		}
		req.setAttribute("tPmDeclareMember", tPmDeclareMember);
		return new ModelAndView("com/kingtake/project/declare/army/tPmDeclareMember-update");
	}
}
