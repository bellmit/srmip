package com.kingtake.base.controller.quota;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
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

import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.quota.TBFundsBudgetRelaEntity;
import com.kingtake.base.service.quota.TBFundsBudgetRelaServiceI;
import com.kingtake.common.constant.SrmipConstants;



/**
 * @Title: Controller
 * @Description: 经费限额设置
 * @author onlineGenerator
 * @date 2015-07-17 11:29:53
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBFundsBudgetRelaController")
public class TBFundsBudgetRelaController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBFundsBudgetRelaController.class);

	@Autowired
	private TBFundsBudgetRelaServiceI tBFundsBudgetRelaService;
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
     * 经费限额设置列表 页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "tBFundsBudgetRela")
	public ModelAndView tBFundsBudgetRela(HttpServletRequest request) {
        String projectType = request.getParameter("projectType");
        String fundId = request.getParameter("fundId");
        request.setAttribute("projectType", projectType);
        request.setAttribute("fundId", fundId);
		return new ModelAndView("com/kingtake/base/quota/tBFundsBudgetRelaList");
	}

    /**
     * 经费限额设置列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBFundsBudgetRelaTab")
    public ModelAndView tBFundsBudgetRelaTab(HttpServletRequest request) {
        String fundId = request.getParameter("fundId");
        request.setAttribute("fundId", fundId);
        return new ModelAndView("com/kingtake/base/quota/tBFundsBudgetRelaTab");
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
	public void datagrid(TBFundsBudgetRelaEntity tBFundsBudgetRela,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBFundsBudgetRelaEntity.class, dataGrid);
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBFundsBudgetRela, request.getParameterMap());
		try{
            //自定义追加查询条件
		    String projectType = request.getParameter("projectType");
		    if(StringUtil.isNotEmpty(projectType)){
                String sql = "SELECT ID FROM T_B_APPROVAL_BUDGET_RELA WHERE PROJ_APPROVAL = '" + projectType
		                +"' AND SCALE_FLAG = '"+SrmipConstants.YES+"'";
                List<String> budIds = systemService.findListbySql(sql);
                if (budIds != null && budIds.size() > 0) {
                    cq.in("approvalBudgetRelaId", budIds.toArray());
                } else {
                    return;
                }
		    }
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBFundsBudgetRelaService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	                                                                                                                                /**
     * 删除经费限额设置
     * 
     * @return
     */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBFundsBudgetRelaEntity tBFundsBudgetRela, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBFundsBudgetRela = systemService.getEntity(TBFundsBudgetRelaEntity.class, tBFundsBudgetRela.getId());
        message = "经费限额设置删除成功";
		try{
			tBFundsBudgetRelaService.delete(tBFundsBudgetRela);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
            message = "经费限额设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	                                                                                                                                /**
     * 批量删除经费限额设置
     * 
     * @return
     */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
        message = "经费限额设置删除成功";
		try{
			for(String id:ids.split(",")){
				TBFundsBudgetRelaEntity tBFundsBudgetRela = systemService.getEntity(TBFundsBudgetRelaEntity.class, 
				id
				);
				tBFundsBudgetRelaService.delete(tBFundsBudgetRela);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
            message = "经费限额设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	                                                                                                                                /**
     * 更新经费限额设置
     * 
     * @param ids
     * @return
     */
	@RequestMapping(params = "doAddOrUpdate")
	@ResponseBody
	public AjaxJson doAddOrUpdate(TBFundsBudgetRelaEntity tBFundsBudgetRela, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "操作成功";
		
		try {
			if(StringUtil.isEmpty(tBFundsBudgetRela.getId())){
				tBFundsBudgetRelaService.save(tBFundsBudgetRela);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}else{
				TBFundsBudgetRelaEntity t = tBFundsBudgetRelaService.get(
						TBFundsBudgetRelaEntity.class, tBFundsBudgetRela.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tBFundsBudgetRela, t);
				tBFundsBudgetRelaService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
            message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
     * 新增、编辑页面跳转
     * 
     * @return
     */
	@RequestMapping(params = "goAddOrUpdate")
	public ModelAndView goAddOrUpdate(TBFundsBudgetRelaEntity tBFundsBudgetRela, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBFundsBudgetRela.getId())) {
			tBFundsBudgetRela = tBFundsBudgetRelaService.getEntity(
					TBFundsBudgetRelaEntity.class, tBFundsBudgetRela.getId());
        }
		String projectType = req.getParameter("projectType");
        req.setAttribute("projectType", projectType);
		if(StringUtil.isNotEmpty(tBFundsBudgetRela.getFundsPropertyCode())){
            TBFundsPropertyEntity  tBFundsProperty = systemService.get(
            		TBFundsPropertyEntity.class, tBFundsBudgetRela.getFundsPropertyCode());
            if (tBFundsProperty != null) {
                req.setAttribute("fundName", tBFundsProperty.getFundsName());
            }
        }
        req.setAttribute("tBFundsBudgetRelaPage", tBFundsBudgetRela);
		return new ModelAndView("com/kingtake/base/quota/tBFundsBudgetRela");
	}
	
	/**                                                                                                                                /**
     * 导入功能跳转
     * 
     * @return
     */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/base/quota/tBFundsBudgetRelaUpload");
	}
	
	 /**                                                                                                                               /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBFundsBudgetRelaEntity tBFundsBudgetRela,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBFundsBudgetRelaEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBFundsBudgetRela, request.getParameterMap());
		List<TBFundsBudgetRelaEntity> tBFundsBudgetRelas = this.tBFundsBudgetRelaService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "经费限额设置");
		modelMap.put(NormalExcelConstants.CLASS,TBFundsBudgetRelaEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("经费限额设置列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBFundsBudgetRelas);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBFundsBudgetRelaEntity tBFundsBudgetRela,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "经费限额设置");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBFundsBudgetRelaEntity.class);
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
				List<TBFundsBudgetRelaEntity> listTBFundsBudgetRelaEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBFundsBudgetRelaEntity.class,params);
				for (TBFundsBudgetRelaEntity tBFundsBudgetRela : listTBFundsBudgetRelaEntitys) {
					tBFundsBudgetRelaService.save(tBFundsBudgetRela);
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
	
	@RequestMapping(params = "valiBud", method = RequestMethod.POST)
    @ResponseBody
    public ValidForm valiBud(TBFundsBudgetRelaEntity tBFundsBudgetRela, HttpServletRequest request,
            HttpServletResponse response) {
        //	    AjaxJson j = new AjaxJson();
        //        
        //        CriteriaQuery cq = new CriteriaQuery(TBFundsBudgetRelaEntity.class);
        //        if (StringUtil.isNotEmpty(tBFundsBudgetRela.getId())) {
        //            cq.notEq("id", tBFundsBudgetRela.getId());
        //        }
        //        if (StringUtil.isNotEmpty(tBFundsBudgetRela.getFundsPropertyCode())) {
        //            cq.eq("fundsPropertyCode", tBFundsBudgetRela.getFundsPropertyCode());
        //        }
        //        if (StringUtil.isNotEmpty(tBFundsBudgetRela.getApprovalBudgetRelaId())) {
        //            cq.eq("approvalBudgetRelaId", tBFundsBudgetRela.getApprovalBudgetRelaId());
        //        }
        //        cq.add();
        //        List<TBFundsBudgetRelaEntity> tBFundsBudgetRelas = systemService.getListByCriteriaQuery(cq, false);
        //
        //        if (tBFundsBudgetRelas != null && tBFundsBudgetRelas.size() > 0) {
        //            j.setSuccess(false);
        //            j.setMsg("该预算类型经费限额设置已存在，清重新选择！");
        //        }
        //
        //        return j;
        ValidForm v = new ValidForm();
        
        CriteriaQuery cq = new CriteriaQuery(TBFundsBudgetRelaEntity.class);
        if (StringUtil.isNotEmpty(tBFundsBudgetRela.getId())) {
            cq.notEq("id", tBFundsBudgetRela.getId());
        }
        if (StringUtil.isNotEmpty(tBFundsBudgetRela.getFundsPropertyCode())) {
            cq.eq("fundsPropertyCode", tBFundsBudgetRela.getFundsPropertyCode());
        }
        if (StringUtil.isNotEmpty(tBFundsBudgetRela.getApprovalBudgetRelaId())) {
            cq.eq("approvalBudgetRelaId", tBFundsBudgetRela.getApprovalBudgetRelaId());
        }
        cq.add();
        List<TBFundsBudgetRelaEntity> tBFundsBudgetRelas = systemService.getListByCriteriaQuery(cq, false);

        if (tBFundsBudgetRelas != null && tBFundsBudgetRelas.size() > 0) {
            v.setStatus("n");
            v.setInfo("该预算类型经费限额设置已存在，清重新选择！");
        }

        return v;
    }
}
