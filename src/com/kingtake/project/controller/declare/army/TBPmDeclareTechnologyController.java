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
import com.kingtake.project.entity.declare.army.TBPmDeclareTechnologyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.declare.army.TBPmDeclareTechnologyServiceI;



/**   
 * @Title: Controller
 * @Description: 技术基础项目--申报书
 * @author onlineGenerator
 * @date 2015-09-22 11:40:31
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmDeclareTechnologyController")
public class TBPmDeclareTechnologyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBPmDeclareTechnologyController.class);

	@Autowired
	private TBPmDeclareTechnologyServiceI tBPmDeclareTechnologyService;
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
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBPmDeclareTechnology")
	public ModelAndView tBPmDeclareTechnology(HttpServletRequest request) {
		// 获得项目id
		String projectId = request.getParameter("projectId");

		TBPmDeclareTechnologyEntity research;
		// 根据项目id查询申报书信息表，看是否已有申报书信息表
		List<TBPmDeclareTechnologyEntity> researchList = systemService.findByProperty(
				TBPmDeclareTechnologyEntity.class, "tpId", projectId);
		if(researchList == null || researchList.size() == 0){
			research = tBPmDeclareTechnologyService.init(projectId);
		}else{
			research = researchList.get(0);
            String processInstId = getProcessInstance(research.getId());
            research.setProcessInstId(processInstId);
		}
		//带出附件
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
			// 流程状态：1--未提交
            if (SrmipConstants.YES.equals(research.getBpmStatus())
                    || WorkFlowGlobals.BPM_BUS_STATUS_5.equals(research.getBpmStatus())
                    || "2".equals(research.getPlanStatus())) {
				// 可编辑
				read = SrmipConstants.NO;
			}else{
				// 不可编辑
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
		return new ModelAndView("com/kingtake/project/declare/army/tBPmDeclareTechnology");
	}

    /**
     * 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBPmDeclareTechnologyInfo")
    public ModelAndView tBPmDeclareTechnologyInfo(HttpServletRequest request) {
        // 获得项目id
        String projectId = request.getParameter("projectId");
        
        TBPmDeclareTechnologyEntity research;
        // 根据项目id查询申报书信息表，看是否已有申报书信息表
        List<TBPmDeclareTechnologyEntity> researchList = systemService.findByProperty(
                TBPmDeclareTechnologyEntity.class, "tpId", projectId);
        if (researchList == null || researchList.size() == 0) {
            research = tBPmDeclareTechnologyService.init(projectId);
        } else {
            research = researchList.get(0);
            String processInstId = getProcessInstance(research.getId());
            research.setProcessInstId(processInstId);
        }
      //带出附件
        if(StringUtils.isEmpty(research.getAttachmentCode())){
            research.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(research.getAttachmentCode(), "");
            research.setAttachments(attachments);
        }
        request.setAttribute("research", research);

        String read = request.getParameter("read");
        if (StringUtil.isEmpty(read)) {
            // 流程状态：1--未提交
            if (SrmipConstants.YES.equals(research.getBpmStatus())) {
                // 可编辑
                read = SrmipConstants.NO;
            } else {
                // 不可编辑
                read = SrmipConstants.YES;
            }
        }
        request.setAttribute("read", read);
        String declareStatus = this.tPmDeclareService.getDeclareStatus(research.getBpmStatus(),
                research.getPlanStatus());
        request.setAttribute("declareStatus", declareStatus);
        return new ModelAndView("com/kingtake/project/declare/army/tBPmDeclareTechnologyInfo");
    }

    /**
     * 根据id获取流程实例
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
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBPmDeclareTechnologyEntity tBPmDeclareTechnology, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBPmDeclareTechnology = systemService.getEntity(TBPmDeclareTechnologyEntity.class, tBPmDeclareTechnology.getId());
		message = "删除成功";
		try{
			tBPmDeclareTechnologyService.deleteAddAttach(tBPmDeclareTechnology);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 更新
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
    public AjaxJson doUpdate(TBPmDeclareTechnologyEntity tBPmDeclareTechnology, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目申报书保存成功";
        try {
            if (StringUtils.isEmpty(tBPmDeclareTechnology.getId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tBPmDeclareTechnology.getTpId());
                if("1".equals(project.getAssignFlag())){//项目指派不需要审批
                    tBPmDeclareTechnology.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_6);
                }
                this.tBPmDeclareTechnologyService.save(tBPmDeclareTechnology);
            } else {
                TBPmDeclareTechnologyEntity t = tBPmDeclareTechnologyService.get(TBPmDeclareTechnologyEntity.class,
                        tBPmDeclareTechnology.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBPmDeclareTechnology, t);
                tBPmDeclareTechnologyService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目申报书保存失败";
            j.setSuccess(false);
        }
        j.setObj(tBPmDeclareTechnology);
        j.setMsg(message);
        return j;
    }
	

	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/test/tBPmDeclareTechnologyUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBPmDeclareTechnologyEntity tBPmDeclareTechnology,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBPmDeclareTechnologyEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmDeclareTechnology, request.getParameterMap());
		List<TBPmDeclareTechnologyEntity> tBPmDeclareTechnologys = this.tBPmDeclareTechnologyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_B_PM_DECLARE_TECHNOLOGY");
		modelMap.put(NormalExcelConstants.CLASS,TBPmDeclareTechnologyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_B_PM_DECLARE_TECHNOLOGY列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBPmDeclareTechnologys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBPmDeclareTechnologyEntity tBPmDeclareTechnology,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_B_PM_DECLARE_TECHNOLOGY");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBPmDeclareTechnologyEntity.class);
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
				List<TBPmDeclareTechnologyEntity> listTBPmDeclareTechnologyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBPmDeclareTechnologyEntity.class,params);
				for (TBPmDeclareTechnologyEntity tBPmDeclareTechnology : listTBPmDeclareTechnologyEntitys) {
					tBPmDeclareTechnologyService.save(tBPmDeclareTechnology);
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
