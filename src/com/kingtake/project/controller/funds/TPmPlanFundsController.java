package com.kingtake.project.controller.funds;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
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

import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.util.PriceUtil;
import com.kingtake.project.entity.funds.TPmPlanFundsEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.funds.TPmContractFundsServiceI;
import com.kingtake.project.service.funds.TPmPlanFundsServiceI;



/**   
 * @Title: Controller
 * @Description: ?????????????????????????????????
 * @author onlineGenerator
 * @date 2015-08-04 16:29:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmPlanFundsController")
public class TPmPlanFundsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmPlanFundsController.class);

	@Autowired
	private TPmPlanFundsServiceI tPmPlanFundsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TPmContractFundsServiceI tPmContractFundsService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * ????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmPlanFunds")
    public ModelAndView tPmPlanFunds(HttpServletRequest request) {
        String apprId = request.getParameter("tpId");
        //??????????????????ID
        request.setAttribute("apprId", apprId);
        //???????????????
        boolean edit = "false".equals(request.getParameter("edit")) ? false : true;
        request.setAttribute("edit", edit);
        // ??????????????????
        request.setAttribute("changeFlag", request.getParameter("changeFlag"));
        TPmProjectFundsApprEntity fundApprEntity = this.systemService.get(TPmProjectFundsApprEntity.class, apprId);
        if (fundApprEntity != null && "3".equals(fundApprEntity.getFundsType())) {//?????????????????????
            if (StringUtils.isNotEmpty(fundApprEntity.getTpId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, fundApprEntity.getTpId());
                BigDecimal allFee = project.getAllFee() == null ? BigDecimal.ZERO : project.getAllFee();
                BigDecimal history = BigDecimal.ZERO;
                CriteriaQuery cq = new CriteriaQuery(TPmPlanFundsEntity.class);
                cq.eq("apprId", apprId);
                cq.isNull("num");
                cq.add();
                List<TPmPlanFundsEntity> planFundsList = this.systemService.getListByCriteriaQuery(cq, false);
                if (planFundsList.size() > 0) {
                    TPmPlanFundsEntity fundsEntity = planFundsList.get(0);
                    Double historyMoney = fundsEntity.getHistoryMoney();
                    if (historyMoney != null) {
                        history = new BigDecimal(historyMoney);
                    }
                }
                String tip = "?????????????????????????????????" + allFee + "???????????????-" + history + "??????????????????=" + (allFee.subtract(history));
                request.setAttribute("tip", tip);
            }
        }
        return new ModelAndView("com/kingtake/project/funds/tPmPlanFundsList");
    }

	/**
	 * ????????????????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TPmPlanFundsEntity tPmPlanFunds,HttpServletRequest request, HttpServletResponse response) {
        String apprId = request.getParameter("apprId");
        
        // ??????????????????????????????
        /*String sql = ProjectConstant.FUNDS_SQL.get("planTree");
        List<Map<String, Object>> result = tPmContractFundsService.getTreeBySql(sql, apprId);
        TagUtil.listToJson(response, result);*/
        
        List<Map<String, Object>> result = tPmContractFundsService.queryPlanTree( apprId);
        TagUtil.listToJson(response, result);
	}
	
	/**
	 * ??????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmPlanFundsEntity tPmPlanFunds, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "????????????";
		try{
			tPmPlanFundsService.del(tPmPlanFunds.getId());
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
	 * @param tPmPlanFunds
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmPlanFundsEntity tPmPlanFunds, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "????????????";
		try{
            TPmPlanFundsEntity parentEntity = this.systemService.get(TPmPlanFundsEntity.class,
            		tPmPlanFunds.getParent());
            // ??????????????????????????????
			Object max = systemService.getSession().createCriteria(TPmPlanFundsEntity.class)
				.add(Restrictions.eq("parent", tPmPlanFunds.getParent()))
				.setProjection(Projections.max("num")).uniqueResult();
			tPmPlanFunds.setNum(max == null ? parentEntity.getNum() + "01" : PriceUtil.getNum(max.toString()));
			// ??????
            tPmPlanFundsService.save(tPmPlanFunds);
			// ????????????????????????????????????
			j.setObj(JSONHelper.bean2json(tPmPlanFunds));
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
	public AjaxJson doUpdate(TPmPlanFundsEntity tPmPlanFunds, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "????????????";
		try {
//			String result = tPmPlanFundsService.updateAndReturn(tPmPlanFunds);
//			if(result == "????????????"){
			tPmPlanFundsService.update(tPmPlanFunds);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
//				message = "????????????";
//			}else{
//				message = result;
//			}			
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
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/funds/tPmFunds-upload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmPlanFundsEntity tPmPlanFunds,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmPlanFundsEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPlanFunds, request.getParameterMap());
		List<TPmPlanFundsEntity> tPmPlanFundss = this.tPmPlanFundsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"????????????????????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TPmPlanFundsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("??????????????????????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmPlanFunds);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmPlanFundsEntity tPmPlanFunds,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????????????????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmPlanFundsEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
    /**
     * ????????????
     * 
     * @param request
     * @param response
     * @return
     */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
        String showType = request.getParameter("flag");
        String tpId = request.getParameter("tpId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// ????????????????????????
			try {
                HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
                tPmPlanFundsService.importExcel(wb, tpId, showType);
				j.setMsg("?????????????????????");
			} catch (Exception e) {
                j.setMsg("??????????????????????????????" + e.getMessage());
                logger.error("?????????????????????", e);
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
