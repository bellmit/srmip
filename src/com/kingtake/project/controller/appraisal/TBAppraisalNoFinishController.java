package com.kingtake.project.controller.appraisal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.appraisal.TBAppraisalNoFinishEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalProjectEntity;
import com.kingtake.project.service.appraisal.TBAppraisalNoFinishServiceI;



/**   
 * @Title: Controller
 * @Description: 鉴定计划未完成情况说明
 * @author onlineGenerator
 * @date 2015-09-09 09:41:03
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalNoFinishController")
public class TBAppraisalNoFinishController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAppraisalNoFinishController.class);

	@Autowired
	private TBAppraisalNoFinishServiceI tBAppraisalNoFinishService;
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
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAppraisalNoFinishEntity tBAppraisalNoFinish, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAppraisalNoFinish = systemService.getEntity(TBAppraisalNoFinishEntity.class, tBAppraisalNoFinish.getId());
		message = "鉴定计划未完成情况说明删除成功";
		try{
			tBAppraisalNoFinishService.delete(tBAppraisalNoFinish);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "鉴定计划未完成情况说明删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	


	/**
	 * 更新
	 * 
	 * @param tBAppraisalNoFinish
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAppraisalNoFinishEntity tBAppraisalNoFinish, HttpServletRequest request) {
        AjaxJson j = this.tBAppraisalNoFinishService.saveNoFinish(tBAppraisalNoFinish);
        return j;
	}
	

	/**
	 * 编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAppraisalNoFinishEntity tBAppraisalNoFinish, HttpServletRequest req) {
		// 获得鉴定计划id
		String appraisalId = tBAppraisalNoFinish.getTbId();
		// 获得鉴定计划信息
		TBAppraisalProjectEntity appraisalProject = systemService.get(TBAppraisalProjectEntity.class, appraisalId);
		
		// 查询是否已生成未完成情况说明
		List<TBAppraisalNoFinishEntity> list = systemService.findByProperty(
				TBAppraisalNoFinishEntity.class, "tbId", appraisalId);
		if(list != null && list.size() > 0){
			tBAppraisalNoFinish = list.get(0);
		}
		
		// 当前登录人
		TSUser user = ResourceUtil.getSessionUserName();
		
		req.setAttribute("userId", user.getId());
		req.setAttribute("appraisalProject", appraisalProject);
		req.setAttribute("tBAppraisalNoFinish", tBAppraisalNoFinish);
		
		return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalNoFinish");
	}
	
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAppraisalNoFinishEntity tBAppraisalNoFinish,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAppraisalNoFinishEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalNoFinish, request.getParameterMap());
		List<TBAppraisalNoFinishEntity> tBAppraisalNoFinishs = this.tBAppraisalNoFinishService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_B_APPRAISA_NO_FINISH");
		modelMap.put(NormalExcelConstants.CLASS,TBAppraisalNoFinishEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_B_APPRAISA_NO_FINISH列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAppraisalNoFinishs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	/**
	 * 组长审批
	 * 
	 * @param tBAppraisalNoFinish
	 * @return
	 */
	@RequestMapping(params = "managerAppr")
	@ResponseBody
	public AjaxJson managerAppr(TBAppraisalNoFinishEntity tBAppraisalNoFinish, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "操作成功";
		
		try {
			TBAppraisalNoFinishEntity t = tBAppraisalNoFinishService.get(
					TBAppraisalNoFinishEntity.class, tBAppraisalNoFinish.getId());
			t.setState(tBAppraisalNoFinish.getState());
			tBAppraisalNoFinishService.updateEntitie(t);
			j.setObj(JSONHelper.bean2json(t));
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 提交给机关
	 * 
	 * @param tBAppraisalNoFinish
	 * @return
	 */
	@RequestMapping(params = "doSubmit")
	@ResponseBody
	public AjaxJson doSubmit(TBAppraisalNoFinishEntity tBAppraisalNoFinish, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "操作成功";
		
		try {
			tBAppraisalNoFinish = tBAppraisalNoFinishService.get(
					TBAppraisalNoFinishEntity.class, tBAppraisalNoFinish.getId());
			tBAppraisalNoFinish.setState(ApprovalConstant.APPRSTATUS_SEND);
			tBAppraisalNoFinishService.updateEntitie(tBAppraisalNoFinish);
			j.setObj(JSONHelper.bean2json(tBAppraisalNoFinish));
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
}
