package com.kingtake.project.controller.funds;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.funds.TPmMaterialEntity;
import com.kingtake.project.service.funds.TPmMaterialServiceI;



/**   
 * @Title: Controller
 * @Description: 原材料
 * @author onlineGenerator
 * @date 2015-09-17 19:52:15
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmMaterialController")
public class TPmMaterialController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmMaterialController.class);

	@Autowired
	private TPmMaterialServiceI tPmMaterialService;
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
	 * 列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmMaterial")
	public ModelAndView tPmMaterial(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/base/material/tPmMaterialList");
	}
	
	/**
	 * 只读列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmMaterialRead")
	public ModelAndView tPmMaterialRead(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/base/material/tPmMaterialList-read");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
  @RequestMapping(params = "datagrid")
    public void datagrid(TPmMaterialEntity tPmMaterial, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmMaterialEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmMaterial, request.getParameterMap());
        cq.addOrder("supplyDate", SortDirection.desc);
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 新增、编辑 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmMaterialEntity tPmMaterial, HttpServletRequest request) {
		if(StringUtil.isNotEmpty(tPmMaterial.getId())){
			tPmMaterial = systemService.get(TPmMaterialEntity.class, tPmMaterial.getId());
			request.setAttribute("tPmMaterial", tPmMaterial);
		}
		return new ModelAndView("com/kingtake/base/material/tPmMaterial");
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmMaterialEntity tPmMaterial, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmMaterial = systemService.getEntity(TPmMaterialEntity.class, tPmMaterial.getId());
		message = "删除成功";
		try{
			tPmMaterialService.delete(tPmMaterial);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 添加、更新
	 * 
	 * @param tPmMaterial
	 * @return
	 */
	@RequestMapping(params = "doSaveOrUpdate")
	@ResponseBody
	public AjaxJson doSaveOrUpdate(TPmMaterialEntity tPmMaterial, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "操作成功";
		
		try {
			if(StringUtil.isNotEmpty(tPmMaterial.getId())){
				TPmMaterialEntity t = tPmMaterialService.get(TPmMaterialEntity.class, tPmMaterial.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tPmMaterial, t);
				tPmMaterialService.updateEntitie(t);
			}else{
				tPmMaterial.setMaterialResource(SrmipConstants.LRLY_HAND);
				tPmMaterial.setSupplyDate(new Date());
				tPmMaterialService.save(tPmMaterial);
			}
			
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
