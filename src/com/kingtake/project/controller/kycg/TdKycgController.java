package com.kingtake.project.controller.kycg;
import java.io.IOException;
import java.util.ArrayList;
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

import com.kingtake.project.entity.kycg.TdKycgEntity;
import com.kingtake.project.service.kycg.TdKycgServiceI;



/**
 * @Title: Controller
 * @Description: 科研成果
 * @author onlineGenerator
 * @date 2016-04-08 11:15:53
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tdKycgController")
public class TdKycgController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TdKycgController.class);

	@Autowired
	private TdKycgServiceI tdKycgService;
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
	 * TD_KYCG列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tdKycg")
	public ModelAndView tdKycg(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/kycg/tdKycgList");
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
	public void datagrid(TdKycgEntity tdKycg,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            String sql = "select r.kycg_id from t_pm_project_kycg_rela r where r.project_id=?";
            List<Map<String, Object>> list = this.systemService.findForJdbc(sql, projectId);
            if (list.size() > 0) {
                List ids = new ArrayList();
                for (Map<String, Object> map : list) {
                    ids.add(map.get("kycg_id"));
                }
                CriteriaQuery cq = new CriteriaQuery(TdKycgEntity.class, dataGrid);
                org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tdKycg,
                        request.getParameterMap());
                cq.in("cgdm", ids.toArray());
                cq.add();
                this.tdKycgService.getDataGridReturn(cq, true);
            }
        }
        TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除TD_KYCG
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TdKycgEntity tdKycg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        tdKycg = systemService.getEntity(TdKycgEntity.class, tdKycg.getCgdm());
        message = "科研成果删除成功";
		try{
			tdKycgService.delete(tdKycg);
		}catch(Exception e){
			e.printStackTrace();
            message = "科研成果删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除TD_KYCG
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "科研成果删除成功";
		try{
			for(String id:ids.split(",")){
				TdKycgEntity tdKycg = systemService.getEntity(TdKycgEntity.class, 
				id
				);
				tdKycgService.delete(tdKycg);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "科研成果删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加TD_KYCG
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TdKycgEntity tdKycg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "科研成果添加成功";
		try{
			tdKycgService.save(tdKycg);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "科研成果添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新TD_KYCG
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TdKycgEntity tdKycg, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "科研成果更新成功";
        TdKycgEntity t = tdKycgService.get(TdKycgEntity.class, tdKycg.getCgdm());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tdKycg, t);
			tdKycgService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
            message = "科研成果更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * TD_KYCG新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TdKycgEntity tdKycg, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tdKycg.getCgdm())) {
            tdKycg = tdKycgService.getEntity(TdKycgEntity.class, tdKycg.getCgdm());
			req.setAttribute("tdKycgPage", tdKycg);
		}
        return new ModelAndView("com/kingtake/project/kycg/tdKycg-add");
	}
	/**
	 * TD_KYCG编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TdKycgEntity tdKycg, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tdKycg.getCgdm())) {
            tdKycg = tdKycgService.getEntity(TdKycgEntity.class, tdKycg.getCgdm());
			req.setAttribute("tdKycgPage", tdKycg);
		}
        return new ModelAndView("com/kingtake/project/kycg/tdKycg-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/kycg/tdKycgUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TdKycgEntity tdKycg,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TdKycgEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tdKycg, request.getParameterMap());
		List<TdKycgEntity> tdKycgs = this.tdKycgService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"TD_KYCG");
		modelMap.put(NormalExcelConstants.CLASS,TdKycgEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("TD_KYCG列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tdKycgs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TdKycgEntity tdKycg,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "TD_KYCG");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TdKycgEntity.class);
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
				List<TdKycgEntity> listTdKycgEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TdKycgEntity.class,params);
				for (TdKycgEntity tdKycg : listTdKycgEntitys) {
					tdKycgService.save(tdKycg);
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
