package com.kingtake.project.controller.price;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.util.PriceUtil;
import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPricePurchaseEntity;
import com.kingtake.project.service.price.TPmContractPricePurchaseServiceI;



/**   
 * @Title: Controller
 * @Description: ??????????????????????????????
 * @author onlineGenerator
 * @date 2015-09-16 10:45:41
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractPricePurchaseController")
public class TPmContractPricePurchaseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmContractPricePurchaseController.class);

	@Autowired
	private TPmContractPricePurchaseServiceI tPmContractPricePurchaseService;
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
	 * easyui AJAX????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPmContractPricePurchaseEntity tPmContractPricePurchase,HttpServletRequest request, HttpServletResponse response) {
		List<TPmContractPricePurchaseEntity> list = systemService.getSession()
				.createCriteria(TPmContractPricePurchaseEntity.class)
				.add(Restrictions.eq("tpId", tPmContractPricePurchase.getTpId()))
				.addOrder(Order.asc("serialNum")).list();
		TagUtil.response(response, JSONHelper.collection2json(list));
	}

	/**
	 * ??????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmContractPricePurchaseEntity tPmContractPricePurchase, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmContractPricePurchase = systemService.getEntity(TPmContractPricePurchaseEntity.class, tPmContractPricePurchase.getId());
		message = "????????????";
		try{
			tPmContractPricePurchaseService.delete(tPmContractPricePurchase);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * ??????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmContractPricePurchaseEntity tPmContractPricePurchase, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "????????????";
		try{
			// ??????????????????
			Object max = systemService.getSession().createCriteria(TPmContractPricePurchaseEntity.class)
					.setProjection(Projections.max("serialNum"))
					.add(Restrictions.eq("tpId", tPmContractPricePurchase.getTpId()))
					.uniqueResult();
			tPmContractPricePurchase.setSerialNum(max == null ? "01" : PriceUtil.getNum(max.toString()));
			tPmContractPricePurchaseService.save(tPmContractPricePurchase);
			// ????????????????????????
			j.setObj(JSONHelper.bean2json(tPmContractPricePurchase));
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmContractPricePurchaseEntity tPmContractPricePurchase, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "????????????";
		// ??????????????????????????????
		TPmContractPricePurchaseEntity t = tPmContractPricePurchaseService.get(
				TPmContractPricePurchaseEntity.class, tPmContractPricePurchase.getId());
		j.setObj(JSONHelper.bean2json(t));
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPmContractPricePurchase, t);
			tPmContractPricePurchaseService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmContractPricePurchaseEntity tPmContractPricePurchase, HttpServletRequest req) {
		// ??????????????????
		req.setAttribute("cover", systemService.get(TPmContractPriceCoverEntity.class, tPmContractPricePurchase.getTpId()));
		req.setAttribute("read", req.getParameter("read"));
		return new ModelAndView("com/kingtake/project/price/tPmContractPricePurchase-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/test/tPmContractPricePurchaseUpload");
	}
	
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmContractPricePurchaseEntity tPmContractPricePurchase,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmContractPricePurchaseEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractPricePurchase, request.getParameterMap());
		List<TPmContractPricePurchaseEntity> tPmContractPricePurchases = this.tPmContractPricePurchaseService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_PM_CONTRACT_PRICE_PURCHASE");
		modelMap.put(NormalExcelConstants.CLASS,TPmContractPricePurchaseEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_PM_CONTRACT_PRICE_PURCHASE??????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmContractPricePurchases);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmContractPricePurchaseEntity tPmContractPricePurchase,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_PM_CONTRACT_PRICE_PURCHASE");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmContractPricePurchaseEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// ????????????????????????
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TPmContractPricePurchaseEntity> listTPmContractPricePurchaseEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmContractPricePurchaseEntity.class,params);
				for (TPmContractPricePurchaseEntity tPmContractPricePurchase : listTPmContractPricePurchaseEntitys) {
					tPmContractPricePurchaseService.save(tPmContractPricePurchase);
				}
				j.setMsg("?????????????????????");
			} catch (Exception e) {
				j.setMsg("?????????????????????");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
