package com.kingtake.project.controller.declare.army;
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
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.entity.ExportParams;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.declare.army.TBPmDeclareArmyResearchEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.declare.army.TBPmDeclareArmyResearchServiceI;



/**   
 * @Title: Controller
 * @Description: 项目申报书信息表（军内科研）
 * @author onlineGenerator
 * @date 2015-08-01 11:39:34
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmDeclareArmyResearchController")
public class TBPmDeclareArmyResearchController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBPmDeclareArmyResearchController.class);

	@Autowired
	private TBPmDeclareArmyResearchServiceI tBPmDeclareArmyResearchService;
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
	 * 项目申报书信息表（军内科研）:主页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBPmDeclareArmyResearch")
	public ModelAndView tBPmDeclareArmyResearch(HttpServletRequest request) {
		// 获得项目id
		String projectId = request.getParameter("projectId");
		request.setAttribute("projectId", projectId);
		
		TBPmDeclareArmyResearchEntity research;
		// 根据项目id查询申报书信息表，看是否已有申报书信息表
		List<TBPmDeclareArmyResearchEntity> researchList = systemService.findByProperty(
				TBPmDeclareArmyResearchEntity.class, "tpId", projectId);
		if(researchList == null || researchList.size() == 0){
			research = tBPmDeclareArmyResearchService.firstSave(projectId);
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
        String opt = request.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            request.setAttribute("opt", opt);
        }
		request.setAttribute("read", read);
        String declareStatus = this.tPmDeclareService.getDeclareStatus(research.getBpmStatus(),
                research.getPlanStatus());
        request.setAttribute("declareStatus", declareStatus);
        return new ModelAndView("com/kingtake/project/declare/army/tBPmDeclareArmyResearch");
	}

    /**
     * 项目申报书信息表（军内科研）:基本信息跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBPmDeclareArmyResearchInfo")
    public ModelAndView tBPmDeclareArmyResearchInfo(HttpServletRequest request) {
        // 获得项目id
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        
        TBPmDeclareArmyResearchEntity research;
        // 根据项目id查询申报书信息表，看是否已有申报书信息表
        List<TBPmDeclareArmyResearchEntity> researchList = systemService.findByProperty(
                TBPmDeclareArmyResearchEntity.class, "tpId", projectId);
        if (researchList == null || researchList.size() == 0) {
            research = tBPmDeclareArmyResearchService.firstSave(projectId);
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
        return new ModelAndView("com/kingtake/project/declare/army/tBPmDeclareArmyResearchInfo");
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
	 * 删除:项目申报书
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBPmDeclareArmyResearchEntity tBPmDeclareArmyResearch, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBPmDeclareArmyResearch = systemService.getEntity(TBPmDeclareArmyResearchEntity.class, tBPmDeclareArmyResearch.getId());
		message = "项目申报书信息表（军内科研）删除成功";
		try{
			tBPmDeclareArmyResearchService.deleteAddAttach(tBPmDeclareArmyResearch);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "项目申报书信息表（军内科研）删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 更新：项目申报书
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
    public AjaxJson doUpdate(TBPmDeclareArmyResearchEntity tBPmDeclareArmyResearch, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目申报书保存成功";
        try {
            if (StringUtils.isEmpty(tBPmDeclareArmyResearch.getId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tBPmDeclareArmyResearch.getTpId());
                if("1".equals(project.getAssignFlag())){//项目指派不需要审批
                    tBPmDeclareArmyResearch.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_6);
                }
                tBPmDeclareArmyResearchService.save(tBPmDeclareArmyResearch);
            } else {
                TBPmDeclareArmyResearchEntity t = tBPmDeclareArmyResearchService.get(
                        TBPmDeclareArmyResearchEntity.class, tBPmDeclareArmyResearch.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBPmDeclareArmyResearch, t);
                tBPmDeclareArmyResearchService.updateEntitie(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目申报书保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        j.setObj(tBPmDeclareArmyResearch);
        return j;
    }
	

	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBPmDeclareArmyResearchEntity tBPmDeclareArmyResearch,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBPmDeclareArmyResearchEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmDeclareArmyResearch, request.getParameterMap());
		List<TBPmDeclareArmyResearchEntity> tBPmDeclareArmyResearchs = this.tBPmDeclareArmyResearchService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_B_PM_DECLARE_ARMY_RESEARCH");
		modelMap.put(NormalExcelConstants.CLASS,TBPmDeclareArmyResearchEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_B_PM_DECLARE_ARMY_RESEARCH列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBPmDeclareArmyResearchs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBPmDeclareArmyResearchEntity tBPmDeclareArmyResearch,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_B_PM_DECLARE_ARMY_RESEARCH");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBPmDeclareArmyResearchEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
}
