package com.kingtake.base.controller.warnmessage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
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

import com.kingtake.base.entity.warnmessage.TBWarnEntity;
import com.kingtake.base.entity.warnmessage.TBWarnPropertiesEntity;
import com.kingtake.base.service.warnmessage.TBWarnServiceI;



/**   
 * @Title: Controller
 * @Description: ????????????
 * @author onlineGenerator
 * @date 2016-01-08 15:32:56
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBWarnController")
public class TBWarnController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBWarnController.class);

	@Autowired
	private TBWarnServiceI tBWarnService;
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
	 * ?????????????????? ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBWarn")
	public ModelAndView tBWarn(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/warnmessage/tBWarnList");
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
	public void datagrid(TBWarnEntity tBWarn,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBWarnEntity.class, dataGrid);
		//?????????????????????
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBWarn, request.getParameterMap());
		try{
		//???????????????????????????
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBWarnService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBWarnEntity tBWarn, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBWarn = systemService.getEntity(TBWarnEntity.class, tBWarn.getId());
		message = "????????????????????????";
		try{
			tBWarnService.delete(tBWarn);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "????????????????????????";
		try{
			for(String id:ids.split(",")){
				TBWarnEntity tBWarn = systemService.getEntity(TBWarnEntity.class, 
				id
				);
				tBWarnService.delete(tBWarn);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "????????????????????????";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????????????????
	 * 
	 * @param ids
	 * @return
	 */
    @RequestMapping(params = "doAddUpdate")
	@ResponseBody
    public AjaxJson doAddUpdate(TBWarnEntity tBWarn, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????/????????????";
        try {
            if (StringUtils.isEmpty(tBWarn.getId())) {
                tBWarnService.save(tBWarn);
            } else {
                TBWarnEntity t = tBWarnService.get(TBWarnEntity.class, tBWarn.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBWarn, t);
                tBWarnService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????/????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
	

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TBWarnEntity tBWarn, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBWarn.getId())) {
			tBWarn = tBWarnService.getEntity(TBWarnEntity.class, tBWarn.getId());
        } else {
            Calendar cal = Calendar.getInstance();
            Date begin = cal.getTime();
            cal.add(Calendar.DAY_OF_YEAR, 7);
            Date end = cal.getTime();
            tBWarn.setWarnBeginTime(begin);
            tBWarn.setWarnEndTime(end);
		}
        req.setAttribute("tBWarnPage", tBWarn);
        List<TBWarnPropertiesEntity> properties = this.systemService.loadAll(TBWarnPropertiesEntity.class);
        req.setAttribute("properties", properties);
        return new ModelAndView("com/kingtake/base/warnmessage/tBWarn-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/base/warnmessage/tBWarnUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBWarnEntity tBWarn,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBWarnEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBWarn, request.getParameterMap());
		List<TBWarnEntity> tBWarns = this.tBWarnService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"????????????");
		modelMap.put(NormalExcelConstants.CLASS,TBWarnEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("??????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBWarns);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBWarnEntity tBWarn,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBWarnEntity.class);
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
				List<TBWarnEntity> listTBWarnEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBWarnEntity.class,params);
				for (TBWarnEntity tBWarn : listTBWarnEntitys) {
					tBWarnService.save(tBWarn);
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
