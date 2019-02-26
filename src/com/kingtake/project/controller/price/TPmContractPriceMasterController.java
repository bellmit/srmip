package com.kingtake.project.controller.price;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
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
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;



/**   
 * @Title: Controller
 * @Description: 价款计算书：主表
 * @author onlineGenerator
 * @date 2015-08-10 15:57:22
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractPriceMasterController")
public class TPmContractPriceMasterController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmContractPriceMasterController.class);

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
	 * @param tPmContractPriceMaster
	 * @param request
	 * @param response
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPmContractPriceMasterEntity tPmContractPriceMaster,HttpServletRequest request, HttpServletResponse response) {
		List<TPmContractPriceMasterEntity> masters = tPmContractPriceMasterService.list(tPmContractPriceMaster);
		TagUtil.response(response, JSONHelper.collection2json(masters));
	}


	/**
	 * 更新
	 * 
	 * @param tPmContractPriceMaster
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmContractPriceMasterEntity tPmContractPriceMaster, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "更新成功";
		try {
			// 获得未更新之前的数据
			TPmContractPriceMasterEntity t = tPmContractPriceMasterService.get(
					TPmContractPriceMasterEntity.class, tPmContractPriceMaster.getId());
			j.setObj(JSONHelper.bean2json(t));
			// 更新父辈数据
			tPmContractPriceMasterService.updateParent(t, tPmContractPriceMaster, t.getParent());
			MyBeanUtils.copyBeanNotNull2Bean(tPmContractPriceMaster, t);
			t.setPriceDiffColumn((t.getPriceColumn() == null ? 0 : t.getPriceColumn())
					- (t.getValuationColumn() == null ? 0 : t.getValuationColumn()));
			tPmContractPriceMasterService.saveOrUpdate(t);
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
	 * 主表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmContractPriceMasterEntity tPmContractPriceMaster, HttpServletRequest req) {
		// 查询合同信息
		req.setAttribute("cover", systemService.get(TPmContractPriceCoverEntity.class, tPmContractPriceMaster.getTpId()));
		req.setAttribute("read", req.getParameter("read"));
		return new ModelAndView("com/kingtake/project/price/tPmContractPriceMaster-update");
	}
	
}
