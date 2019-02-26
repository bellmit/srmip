package com.kingtake.price.controller.tbjgbzzyhgjhy;

import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.price.entity.tbjgbzbeiz.TBJgbzBeizEntity;
import com.kingtake.price.entity.tbjgbzzyhgjhy.TBJgbzZyhgjhyEntity;
import com.kingtake.price.service.tbjgbzzyhgjhy.TBJgbzZyhgjhyServiceI;
import com.sun.star.bridge.oleautomation.Decimal;

import java.util.ArrayList;
import java.util.Date;
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
import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 中央和国家机关会议费
 * @author onlineGenerator
 * @date 2016-07-22 15:47:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBJgbzZyhgjhyController")
public class TBJgbzZyhgjhyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBJgbzZyhgjhyController.class);

	@Autowired
	private TBJgbzZyhgjhyServiceI tBJgbzZyhgjhyService;
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
	 * 中央和国家机关会议费列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBJgbzZyhgjhy")
	public ModelAndView tBJgbzZyhgjhy(HttpServletRequest request) {
		
		CriteriaQuery cq = new CriteriaQuery(TBJgbzBeizEntity.class);
		cq.eq("jgkdm", "zyhgjhy");
		cq.add();
		List<TBJgbzBeizEntity> pList = this.systemService.getListByCriteriaQuery(cq, false);
		if (pList.size()>0){
			TBJgbzBeizEntity tbJgbzBeizEntity = pList.get(0);
			request.setAttribute("jgkId", tbJgbzBeizEntity.getId());
			request.setAttribute("jgkBeiz", tbJgbzBeizEntity.getBeiz());
		}
		
		return new ModelAndView("com/kingtake/price/tbjgbzzyhgjhy/tBJgbzZyhgjhyList");
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
	public void datagrid(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		StringBuffer countSql = new StringBuffer("SELECT COUNT(0) ");
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT ID,HYLX,ZSF,HSF,QT,ZXSJ,CREATE_NAME,CREATE_BY,CREATE_DATE,UPDATE_NAME,UPDATE_BY,UPDATE_DATE,ZSF+HSF+QT as HJ  \n");
        
        String temp = " FROM T_B_JGBZ_ZYHGJHY WHERE 1 = 1 ";
        
		String sHylx = request.getParameter("hylx");
		if (StringUtil.isNotEmpty(sHylx)) {
			temp += " AND hylx like '%" + sHylx + "%'";
		}
		
        String leaveTime_begin = request.getParameter("zxsj_begin");
        if (StringUtil.isNotEmpty(leaveTime_begin)) {
            temp += " AND ZXSJ >= to_date('" + leaveTime_begin + "','yyyy-mm-dd')";
        }     
        
        String leaveTime_end = request.getParameter("zxsj_end");
        if (StringUtil.isNotEmpty(leaveTime_end)) {
            temp += " AND ZXSJ <= to_date('" + leaveTime_end + "','yyyy-mm-dd')";                       
        }
        
        temp += " ORDER BY ZXSJ";
        
        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(),
                dataGrid.getPage(), dataGrid.getRows());       
        
        Long count = systemService.getCountForJdbc(countSql.append(temp).toString());

        dataGrid.setResults(result);
        dataGrid.setTotal(count.intValue());
				
        TagUtil.datagrid(response, dataGrid);	
	}

	/**
	 * 删除中央和国家机关会议费
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBJgbzZyhgjhy = systemService.getEntity(TBJgbzZyhgjhyEntity.class, tBJgbzZyhgjhy.getId());
		message = "中央和国家机关会议费标准删除成功";
		try{
			tBJgbzZyhgjhyService.delete(tBJgbzZyhgjhy);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "中央和国家机关会议费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除中央和国家机关会议费
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "中央和国家机关会议费标准删除成功";
		try{
			for(String id:ids.split(",")){
				TBJgbzZyhgjhyEntity tBJgbzZyhgjhy = systemService.getEntity(TBJgbzZyhgjhyEntity.class, 
				id
				);
				tBJgbzZyhgjhyService.delete(tBJgbzZyhgjhy);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "中央和国家机关会议费标准删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加中央和国家机关会议费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "中央和国家机关会议费标准添加成功";
		try{
			tBJgbzZyhgjhyService.save(tBJgbzZyhgjhy);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "中央和国家机关会议费标准添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新中央和国家机关会议费
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "中央和国家机关会议费标准更新成功";
		
		try 
		{
			if (StringUtils.isEmpty(tBJgbzZyhgjhy.getId())) {
				tBJgbzZyhgjhyService.save(tBJgbzZyhgjhy);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
			else
			{
				TBJgbzZyhgjhyEntity t = tBJgbzZyhgjhyService.get(TBJgbzZyhgjhyEntity.class, tBJgbzZyhgjhy.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBJgbzZyhgjhy, t);
				tBJgbzZyhgjhyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			message = "中央和国家机关会议费标准更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 中央和国家机关会议费新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzZyhgjhy.getId())) {
			tBJgbzZyhgjhy = tBJgbzZyhgjhyService.getEntity(TBJgbzZyhgjhyEntity.class, tBJgbzZyhgjhy.getId());
			req.setAttribute("tBJgbzZyhgjhyPage", tBJgbzZyhgjhy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzzyhgjhy/tBJgbzZyhgjhy-add");
	}
	/**
	 * 中央和国家机关会议费编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBJgbzZyhgjhy.getId())) {
			tBJgbzZyhgjhy = tBJgbzZyhgjhyService.getEntity(TBJgbzZyhgjhyEntity.class, tBJgbzZyhgjhy.getId());
			req.setAttribute("tBJgbzZyhgjhyPage", tBJgbzZyhgjhy);
		}
		return new ModelAndView("com/kingtake/price/tbjgbzzyhgjhy/tBJgbzZyhgjhy-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/price/tbjgbzzyhgjhy/tBJgbzZyhgjhyUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
/*		CriteriaQuery cq = new CriteriaQuery(TBJgbzZyhgjhyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBJgbzZyhgjhy, request.getParameterMap());
		List<TBJgbzZyhgjhyEntity> tBJgbzZyhgjhys = this.tBJgbzZyhgjhyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"中央和国家机关会议费标准");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzZyhgjhyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("中央和国家机关会议费标准列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzZyhgjhys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;*/
		
		
		
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT ID,HYLX,ZSF,HSF,QT,ZXSJ,CREATE_NAME,CREATE_BY,CREATE_DATE,UPDATE_NAME,UPDATE_BY,UPDATE_DATE,ZSF+HSF+QT as HJ  \n");
        
        String temp = " FROM T_B_JGBZ_ZYHGJHY WHERE 1 = 1 ";
        
		String sHylx = request.getParameter("hylx");
		if (StringUtil.isNotEmpty(sHylx)) {
			temp += " AND hylx ='" + sHylx + "'";
		}
        
        String leaveTime_begin = request.getParameter("zxsj_begin");
        if (StringUtil.isNotEmpty(leaveTime_begin)) {
            temp += " AND ZXSJ >= to_date('" + leaveTime_begin + "','yyyy-mm-dd')";
        }     
        
        String leaveTime_end = request.getParameter("zxsj_end");
        if (StringUtil.isNotEmpty(leaveTime_end)) {
            temp += " AND ZXSJ <= to_date('" + leaveTime_end + "','yyyy-mm-dd')";                       
        }
        
        temp += " ORDER BY ZXSJ";
        
        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(),
                dataGrid.getPage(), dataGrid.getRows());       
        
        List<TBJgbzZyhgjhyEntity> tBJgbzZyhgjhys = new ArrayList<TBJgbzZyhgjhyEntity>();   
        if (result.size() > 0) {
			for (Map<String, Object> map : result) {
				TBJgbzZyhgjhyEntity zyhgjhyEntity = new TBJgbzZyhgjhyEntity();
				zyhgjhyEntity.setHylx((String)map.get("HYLX"));
				zyhgjhyEntity.setHj((BigDecimal)map.get("HJ"));
				zyhgjhyEntity.setZsf((BigDecimal)map.get("ZSF"));
				zyhgjhyEntity.setHsf((BigDecimal)map.get("HSF"));
				zyhgjhyEntity.setQt((BigDecimal)map.get("QT"));
				zyhgjhyEntity.setZxsj((Date)map.get("ZXSJ"));
								
				tBJgbzZyhgjhys.add(zyhgjhyEntity);
			}
		}
		
		modelMap.put(NormalExcelConstants.FILE_NAME,"中央和国家机关会议费标准");
		modelMap.put(NormalExcelConstants.CLASS,TBJgbzZyhgjhyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("中央和国家机关会议费标准列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBJgbzZyhgjhys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBJgbzZyhgjhyEntity tBJgbzZyhgjhy,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "中央和国家机关会议费标准");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBJgbzZyhgjhyEntity.class);
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
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TBJgbzZyhgjhyEntity> listTBJgbzZyhgjhyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBJgbzZyhgjhyEntity.class,params);
				for (TBJgbzZyhgjhyEntity tBJgbzZyhgjhy : listTBJgbzZyhgjhyEntitys) {
					tBJgbzZyhgjhyService.save(tBJgbzZyhgjhy);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
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
