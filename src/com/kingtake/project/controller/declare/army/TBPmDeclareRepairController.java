package com.kingtake.project.controller.declare.army;
import java.io.IOException;
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
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.ActivitiService;
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

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.declare.army.TBPmDeclareRepairEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.declare.army.TBPmDeclareRepairServiceI;



/**   
 * @Title: Controller
 * @Description: ???????????????????????????
 * @author onlineGenerator
 * @date 2015-09-22 11:40:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmDeclareRepairController")
public class TBPmDeclareRepairController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBPmDeclareRepairController.class);

	@Autowired
	private TBPmDeclareRepairServiceI tBPmDeclareRepairService;
	@Autowired
	private SystemService systemService;
	private String message;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TPmDeclareServiceI tPmDeclareService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * ????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBPmDeclareRepair")
	public ModelAndView tBPmDeclareRepair(HttpServletRequest request) {
		// ????????????id
		String projectId = request.getParameter("projectId");

		TBPmDeclareRepairEntity research;
		// ????????????id????????????????????????????????????????????????????????????
		List<TBPmDeclareRepairEntity> researchList = systemService.findByProperty(
				TBPmDeclareRepairEntity.class, "tpId", projectId);
		if(researchList == null || researchList.size() == 0){
			research = tBPmDeclareRepairService.init(projectId);
		}else{
			research = researchList.get(0);
            String processInstId = getProcessInstance(research.getId());
            research.setProcessInstId(processInstId);
		}
		//????????????
        if(StringUtils.isEmpty(research.getAttachmentCode())){
            research.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(research.getAttachmentCode(), "");
            research.setAttachments(attachments);
        }
		request.setAttribute("research", research);
        Map<String, Object> dataMap = activitiService.getProcessInstance(research.getId());
        if (dataMap != null) {
            String processInstId = (String) dataMap.get("processInstId");
            String taskId = (String) dataMap.get("taskId");
            request.setAttribute("processInstId", processInstId);
            request.setAttribute("taskId", taskId);
        }
		String read = request.getParameter("read");
		if(StringUtil.isEmpty(read)){
			// ???????????????1--?????????
            if (SrmipConstants.YES.equals(research.getBpmStatus())
                    || WorkFlowGlobals.BPM_BUS_STATUS_5.equals(research.getBpmStatus())
                    || "2".equals(research.getPlanStatus())) {
				// ?????????
				read = SrmipConstants.NO;
			}else{
				// ????????????
				read = SrmipConstants.YES;
			}
		}
		request.setAttribute("read", read);
        String opt = request.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            request.setAttribute("opt", opt);
        }
        String declareStatus = this.tPmDeclareService.getDeclareStatus(research.getBpmStatus(),
                research.getPlanStatus());
        request.setAttribute("declareStatus", declareStatus);
        return new ModelAndView("com/kingtake/project/declare/army/tBPmDeclareRepair");
	}

    /**
     * ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBPmDeclareRepairInfo")
    public ModelAndView tBPmDeclareRepairInfo(HttpServletRequest request) {
        // ????????????id
        String projectId = request.getParameter("projectId");
        
        TBPmDeclareRepairEntity research;
        // ????????????id????????????????????????????????????????????????????????????
        List<TBPmDeclareRepairEntity> researchList = systemService.findByProperty(TBPmDeclareRepairEntity.class,
                "tpId", projectId);
        if (researchList == null || researchList.size() == 0) {
            research = tBPmDeclareRepairService.init(projectId);
        } else {
            research = researchList.get(0);
            String processInstId = getProcessInstance(research.getId());
            research.setProcessInstId(processInstId);
        }
        //????????????
        if(StringUtils.isEmpty(research.getAttachmentCode())){
            research.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(research.getAttachmentCode(), "");
            research.setAttachments(attachments);
        }
        request.setAttribute("research", research);

        String read = request.getParameter("read");
        if (StringUtil.isEmpty(read)) {
            // ???????????????1--?????????
            if (SrmipConstants.YES.equals(research.getBpmStatus())) {
                // ?????????
                read = SrmipConstants.NO;
            } else {
                // ????????????
                read = SrmipConstants.YES;
            }
        }
        request.setAttribute("read", read);
        String declareStatus = this.tPmDeclareService.getDeclareStatus(research.getBpmStatus(),
                research.getPlanStatus());
        request.setAttribute("declareStatus", declareStatus);
        return new ModelAndView("com/kingtake/project/declare/army/tBPmDeclareRepairInfo");
    }

    /**
     * ??????id??????????????????
     * 
     * @param id
     * @param request
     * @param response
     */
    private String getProcessInstance(String id) {
        String instanceId = null;
        String sql = "select t.proc_inst_id_ from ACT_HI_PROCINST t where t.business_key_=? ";
        List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql, id);
        if (resultList.size() > 0) {
            Map<String, Object> map = resultList.get(0);
            instanceId = (String) map.get("PROC_INST_ID_");
        }
        return instanceId;
    }


	/**
	 * ??????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBPmDeclareRepairEntity tBPmDeclareRepair, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBPmDeclareRepair = systemService.getEntity(
				TBPmDeclareRepairEntity.class, tBPmDeclareRepair.getId());
		message = "???????????????????????????";
		try{
			tBPmDeclareRepairService.deleteAddAttach(tBPmDeclareRepair);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "???????????????????????????";
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
    public AjaxJson doUpdate(TBPmDeclareRepairEntity tBPmDeclareRepair, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            if (StringUtils.isEmpty(tBPmDeclareRepair.getId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tBPmDeclareRepair.getTpId());
                if("1".equals(project.getAssignFlag())){//???????????????????????????
                    tBPmDeclareRepair.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_6);
                }
                tBPmDeclareRepairService.save(tBPmDeclareRepair);
            } else {
                TBPmDeclareRepairEntity t = tBPmDeclareRepairService.get(TBPmDeclareRepairEntity.class,
                        tBPmDeclareRepair.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBPmDeclareRepair, t);
                tBPmDeclareRepairService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            j.setSuccess(false);
        }
        j.setObj(tBPmDeclareRepair);
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
		return new ModelAndView("com/kingtake/test/tBPmDeclareRepairUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBPmDeclareRepairEntity tBPmDeclareRepair,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBPmDeclareRepairEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmDeclareRepair, request.getParameterMap());
		List<TBPmDeclareRepairEntity> tBPmDeclareRepairs = this.tBPmDeclareRepairService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_B_PM_DECLARE_REPAIR");
		modelMap.put(NormalExcelConstants.CLASS,TBPmDeclareRepairEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_B_PM_DECLARE_REPAIR??????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBPmDeclareRepairs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBPmDeclareRepairEntity tBPmDeclareRepair,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_B_PM_DECLARE_REPAIR");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBPmDeclareRepairEntity.class);
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
				List<TBPmDeclareRepairEntity> listTBPmDeclareRepairEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBPmDeclareRepairEntity.class,params);
				for (TBPmDeclareRepairEntity tBPmDeclareRepair : listTBPmDeclareRepairEntitys) {
					tBPmDeclareRepairService.save(tBPmDeclareRepair);
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
