package com.kingtake.price.controller.tbjgbzzjzx;
import com.kingtake.price.entity.tbjgbzbeiz.TBJgbzBeizEntity;
import com.kingtake.price.entity.tbjgbzzjzx.TBJgbzZjzxEntity;
import com.kingtake.price.service.tbjgbzzjzx.TBJgbzZjzxServiceI;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2016-07-25 09:35:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzZjzxController")
public class TBJgbzZjzxController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzZjzxController.class);

	@Autowired
	private TBJgbzZjzxServiceI tBJgbzZjzxService;
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
	 * ??????????????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzZjzx")
	public ModelAndView tBJgbzZjzx(HttpServletRequest request) {
		
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class);
		cq.eq("jgkdm", "zjzx");
		cq.add();
		List<TBJgbzBeizEntity> pList = this.systemService.getListByCriteriaQuery(cq, false);
		if (pList.size()>0){
			TBJgbzBeizEntity tbJgbzBeizEntity = pList.get(0);
			request.setAttribute("jgkId", tbJgbzBeizEntity.getId());
			request.setAttribute("jgkBeiz", tbJgbzBeizEntity.getBeiz());
		}
		
		return new ModelAndView("com/kingtake/price/tbjgbzzjzx/tBJgbzZjzxList");
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
	public void datagrid(TBJgbzZjzxEntity tBJgbzZjzx,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzZjzxEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzZjzx, request.getParameterMap());
		try{
		//???????????????????????????
		String query_zxsj_begin = request.getParameter("zxsj_begin");
		String query_zxsj_end = request.getParameter("zxsj_end");
		if(StringUtil.isNotEmpty(query_zxsj_begin)){
			cq.ge("zxsj", new SimpleDateFormat("yyyy-MM-dd").parse(query_zxsj_begin));
		}
		if(StringUtil.isNotEmpty(query_zxsj_end)){
			cq.le("zxsj", new SimpleDateFormat("yyyy-MM-dd").parse(query_zxsj_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzZjzxService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzZjzxEntity tBJgbzZjzx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzZjzx = systemService.getEntity(TBJgbzZjzxEntity.class, tBJgbzZjzx.getId());
		message = "?????????????????????????????????";
		try{
			tBJgbzZjzxService.delete(tBJgbzZjzx);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ?????????????????????????????????
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "?????????????????????????????????";
		try{
			for(String id:ids.split(",")){
				TBJgbzZjzxEntity tBJgbzZjzx = systemService.getEntity(TBJgbzZjzxEntity.class, 
				id
				);
				tBJgbzZjzxService.delete(tBJgbzZjzx);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * ???????????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzZjzxEntity tBJgbzZjzx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "?????????????????????????????????";
		try{
			tBJgbzZjzxService.save(tBJgbzZjzx);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ???????????????????????????
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzZjzxEntity tBJgbzZjzx, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "?????????????????????????????????";
		
		try {
			if (StringUtils.isEmpty(tBJgbzZjzx.getId())) {
				tBJgbzZjzxService.save(tBJgbzZjzx);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzZjzxEntity t = tBJgbzZjzxService.get(TBJgbzZjzxEntity.class, tBJgbzZjzx.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzZjzx, t);
				tBJgbzZjzxService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			message = "?????????????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * ???????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzZjzxEntity tBJgbzZjzx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzZjzx.getId())) {
			tBJgbzZjzx = tBJgbzZjzxService.getEntity(TBJgbzZjzxEntity.class, tBJgbzZjzx.getId());
			req.setAttribute("tBJgbzZjzxPage", tBJgbzZjzx);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzzjzx/tBJgbzZjzx-add");
	}
	/**
	 * ???????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzZjzxEntity tBJgbzZjzx, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzZjzx.getId())) {
			tBJgbzZjzx = tBJgbzZjzxService.getEntity(TBJgbzZjzxEntity.class, tBJgbzZjzx.getId());
			req.setAttribute("tBJgbzZjzxPage", tBJgbzZjzx);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzzjzx/tBJgbzZjzx-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzzjzx/tBJgbzZjzxUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzZjzxEntity tBJgbzZjzx,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzZjzxEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzZjzx, request.getParameterMap());
		List<TBJgbzZjzxEntity> tBJgbzZjzxs = this.tBJgbzZjzxService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"?????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzZjzxEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("???????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzZjzxs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzZjzxEntity tBJgbzZjzx,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzZjzxEntity.class);
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
				List<TBJgbzZjzxEntity> listTBJgbzZjzxEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzZjzxEntity.class,params);
				for (TBJgbzZjzxEntity tBJgbzZjzx : listTBJgbzZjzxEntitys) {
					tBJgbzZjzxService.save(tBJgbzZjzx);
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
