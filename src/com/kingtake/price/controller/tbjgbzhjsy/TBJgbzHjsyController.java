package com.kingtake.price.controller.tbjgbzhjsy;
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.price.entity.tbjgbzbeiz.TBJgbzBeizEntity;
import com.kingtake.price.entity.tbjgbzhjsy.TBJgbzHjsyEntity;
import com.kingtake.price.entity.tbjgbzhjsymx.TBJgbzHjsymxEntity;
import com.kingtake.price.service.tbjgbzhjsy.TBJgbzHjsyServiceI;
import java.util.List;
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
 * @date 2016-07-29 10:02:53
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzHjsyController")
public class TBJgbzHjsyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzHjsyController.class);

	@Autowired
	private TBJgbzHjsyServiceI tBJgbzHjsyService;
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
	@RequestMapping(params = "tBJgbzHjsy")
	public ModelAndView tBJgbzHjsy(HttpServletRequest request) {
		
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class);
		cq.eq("jgkdm", "hjsy");
		cq.add();
		List<TBJgbzBeizEntity> pList = this.systemService.getListByCriteriaQuery(cq, false);
		if (pList.size()>0){
			TBJgbzBeizEntity tbJgbzBeizEntity = pList.get(0);
			request.setAttribute("jgkId", tbJgbzBeizEntity.getId());
			request.setAttribute("jgkBeiz", tbJgbzBeizEntity.getBeiz());
		}
		
		return new ModelAndView("com/kingtake/price/tbjgbzhjsy/tBJgbzHjsyList");
	}

	
    /**
     * ??????????????????[???????????????????????????]
     * 
     * @return
     */
    @RequestMapping(params = "tBJgbzHjsymxList")
    public ModelAndView tBJgbzHjsymxList(TBJgbzHjsyEntity tBJgbzHjsy, HttpServletRequest req) {

        // ===================================================================================
        // ????????????
        Object id0 = tBJgbzHjsy.getId();
        // ===================================================================================
        // ??????-???????????????????????????
        String hql0 = "from TBJgbzHjsymxEntity where 1 = 1 AND syxmid = ?";
        try {
            List<TBJgbzHjsymxEntity> tBCodeDetailEntityList = systemService.findHql(hql0, id0);
            req.setAttribute("tBJgbzHjsymxList", tBCodeDetailEntityList);
            req.setAttribute("syxmid", id0);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/kingtake/price/tbjgbzhjsymx/tBJgbzHjsymxList");
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
	public void datagrid(TBJgbzHjsyEntity tBJgbzHjsy,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzHjsyEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzHjsy, request.getParameterMap());
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBJgbzHjsyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzHjsyEntity tBJgbzHjsy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzHjsy = systemService.getEntity(TBJgbzHjsyEntity.class, tBJgbzHjsy.getId());
		message = "?????????????????????????????????";
		try{
			tBJgbzHjsyService.delete(tBJgbzHjsy);
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
				TBJgbzHjsyEntity tBJgbzHjsy = systemService.getEntity(TBJgbzHjsyEntity.class, 
				id
				);
				tBJgbzHjsyService.delete(tBJgbzHjsy);
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
	public AjaxJson doAdd(TBJgbzHjsyEntity tBJgbzHjsy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "?????????????????????????????????";
		try{
			tBJgbzHjsyService.save(tBJgbzHjsy);
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
	public AjaxJson doUpdate(TBJgbzHjsyEntity tBJgbzHjsy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "?????????????????????????????????";
		
		try {			
			if (StringUtils.isEmpty(tBJgbzHjsy.getId())) {
				tBJgbzHjsyService.save(tBJgbzHjsy);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzHjsyEntity t = tBJgbzHjsyService.get(TBJgbzHjsyEntity.class, tBJgbzHjsy.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzHjsy, t);
				tBJgbzHjsyService.saveOrUpdate(t);
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
	public ModelAndView goAdd(TBJgbzHjsyEntity tBJgbzHjsy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzHjsy.getId())) {
			tBJgbzHjsy = tBJgbzHjsyService.getEntity(TBJgbzHjsyEntity.class, tBJgbzHjsy.getId());
			req.setAttribute("tBJgbzHjsyPage", tBJgbzHjsy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzhjsy/tBJgbzHjsy-add");
	}
	/**
	 * ???????????????????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzHjsyEntity tBJgbzHjsy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzHjsy.getId())) {
			tBJgbzHjsy = tBJgbzHjsyService.getEntity(TBJgbzHjsyEntity.class, tBJgbzHjsy.getId());
			req.setAttribute("tBJgbzHjsyPage", tBJgbzHjsy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzhjsy/tBJgbzHjsy-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzhjsy/tBJgbzHjsyUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzHjsyEntity tBJgbzHjsy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBJgbzHjsyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzHjsy, request.getParameterMap());
		List<TBJgbzHjsyEntity> tBJgbzHjsys = this.tBJgbzHjsyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"?????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzHjsyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("???????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzHjsys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzHjsyEntity tBJgbzHjsy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzHjsyEntity.class);
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
				List<TBJgbzHjsyEntity> listTBJgbzHjsyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzHjsyEntity.class,params);
				for (TBJgbzHjsyEntity tBJgbzHjsy : listTBJgbzHjsyEntitys) {
					tBJgbzHjsyService.save(tBJgbzHjsy);
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
