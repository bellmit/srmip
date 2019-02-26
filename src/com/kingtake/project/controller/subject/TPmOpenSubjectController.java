package com.kingtake.project.controller.subject;
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
import org.jeecgframework.tag.core.easyui.TagUtil;
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

import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.subject.TPmOpenSubjectEntity;
import com.kingtake.project.service.subject.TPmOpenSubjectServiceI;



/**   
 * @Title: Controller
 * @Description: 开题信息
 * @author onlineGenerator
 * @date 2015-07-16 15:00:51
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmOpenSubjectController")
public class TPmOpenSubjectController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPmOpenSubjectController.class);

	@Autowired
	private TPmOpenSubjectServiceI tPmOpenSubjectService;
	@Autowired
	private SystemService systemService;
    @Autowired
    private ActivitiService activitiService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 开题信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tPmOpenSubject")
	public ModelAndView tPmOpenSubject(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/project/subject/tPmOpenSubjectList");
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
	public void datagrid(TPmOpenSubjectEntity tPmOpenSubject,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPmOpenSubjectEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOpenSubject, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPmOpenSubjectService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除开题信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPmOpenSubjectEntity tPmOpenSubject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPmOpenSubject = systemService.getEntity(TPmOpenSubjectEntity.class, tPmOpenSubject.getId());
		message = "开题信息删除成功";
		try{
			tPmOpenSubjectService.deleteAddAttach(tPmOpenSubject);
		}catch(Exception e){
			e.printStackTrace();
			message = "开题信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除开题信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "开题信息删除成功";
		try{
			for(String id:ids.split(",")){
				TPmOpenSubjectEntity tPmOpenSubject = systemService.getEntity(TPmOpenSubjectEntity.class, 
				id
				);
				tPmOpenSubjectService.deleteAddAttach(tPmOpenSubject);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "开题信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加开题信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPmOpenSubjectEntity tPmOpenSubject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "开题信息添加成功";
		try{
			tPmOpenSubjectService.save(tPmOpenSubject);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "开题信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新开题信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPmOpenSubjectEntity tPmOpenSubject, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
	    try {
	        if(StringUtil.isNotEmpty(tPmOpenSubject.getId())){
	            message = "开题信息更新成功";
	            TPmOpenSubjectEntity t = tPmOpenSubjectService.get(TPmOpenSubjectEntity.class, tPmOpenSubject.getId());
	            MyBeanUtils.copyBeanNotNull2Bean(tPmOpenSubject, t);
	            if (StringUtils.isNotEmpty(tPmOpenSubject.getTpId())) {
	                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmOpenSubject.getTpId());
	                t.setProject(project);
	            }
	            tPmOpenSubjectService.updateEntitie(t);
	        }else{
	            message = "开题信息新增成功";
	            if (StringUtils.isNotEmpty(tPmOpenSubject.getTpId())) {
                    TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmOpenSubject.getTpId());
                    boolean checkFlag = checkDeclare(project);
                    if(!checkFlag){
                        j.setMsg("未录入申报书，不能进行开题！");
                        j.setSuccess(false);
                        return j;
                    }
                    tPmOpenSubject.setProject(project);
                }
                tPmOpenSubject.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
	            tPmOpenSubjectService.save(tPmOpenSubject);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        message = "开题信息保存失败";
	        throw new BusinessException(e.getMessage());
	    }
		j.setObj(tPmOpenSubject);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 检查是否存在申报书
	 * @param projectId
	 * @return
	 */
	private boolean checkDeclare(TPmProjectEntity project){
	    boolean flag = false;
	    String projectId = project.getId();
	    String sql = "select d.id from t_b_pm_declare d where d.t_p_id=? "
	            + "union all select a.id from t_b_pm_declare_army_research a where a.t_p_id=? "
	            + "union all select b.id from t_b_pm_declare_back  b where b.t_p_id=? "
	            + "union all select r.id from t_b_pm_declare_repair  r where r.t_p_id=? "
	            + "union all select t.id from t_b_pm_declare_technology  t where t.t_p_id=?";
	    List<Map<String,Object>> dataMapList = this.systemService.findForJdbc(sql, new Object[]{projectId,projectId,projectId,projectId,projectId});
	    if(dataMapList.size()>0){
	        flag = true;
	        return flag;
	    }
	    if("1".equals(project.getMergeFlag())){//是合并后的项目
	        List<TPmProjectEntity> mergeProjectList = this.systemService.findByProperty(TPmProjectEntity.class, "mergeProject.id", project.getId());
	        for(TPmProjectEntity tmp:mergeProjectList){
	            projectId = tmp.getId();
	            dataMapList = this.systemService.findForJdbc(sql, new Object[]{projectId,projectId,projectId,projectId,projectId});
	            if(dataMapList.size()>0){
	                flag = true;
	                return flag;
	            }
	        }
	    }
	    return flag;
	}

	/**
	 * 开题信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPmOpenSubjectEntity tPmOpenSubject, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmOpenSubject.getId())) {
			tPmOpenSubject = tPmOpenSubjectService.getEntity(TPmOpenSubjectEntity.class, tPmOpenSubject.getId());
			req.setAttribute("tPmOpenSubjectPage", tPmOpenSubject);
		}
		return new ModelAndView("com/kingtake/project/subject/tPmOpenSubject-add");
	}
	/**
	 * 开题信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPmOpenSubjectEntity tPmOpenSubject, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPmOpenSubject.getId())) {
			tPmOpenSubject = tPmOpenSubjectService.getEntity(TPmOpenSubjectEntity.class, tPmOpenSubject.getId());
		}
		if (StringUtils.isEmpty(tPmOpenSubject.getAttachmentCode())) {
		    tPmOpenSubject.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                    tPmOpenSubject.getAttachmentCode(), "");
            tPmOpenSubject.setAttachments(fileList);
        }
		req.setAttribute("tPmOpenSubjectPage", tPmOpenSubject);
		return new ModelAndView("com/kingtake/project/subject/tPmOpenSubject-update");
	}
	
	/**
     * 开题信息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateForResearchGroup")
    public ModelAndView goUpdateForResearchGroup(TPmOpenSubjectEntity tPmOpenSubject, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmOpenSubject.getId())) {
            tPmOpenSubject = tPmOpenSubjectService.getEntity(TPmOpenSubjectEntity.class, tPmOpenSubject.getId());
        }
        if (StringUtils.isEmpty(tPmOpenSubject.getAttachmentCode())) {
            tPmOpenSubject.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                    tPmOpenSubject.getAttachmentCode(), "");
            tPmOpenSubject.setAttachments(fileList);
        }
        req.setAttribute("tPmOpenSubjectPage", tPmOpenSubject);
        return new ModelAndView("com/kingtake/project/subject/tPmOpenSubject-updateForResearchGroup");
    }
    
    /**
     * 跳转到课题组的开题信息编辑界面.
     * 
     * @return
     */
    @RequestMapping(params = "openSubjectUpdateForResearchGroup")
    public ModelAndView openSubjectUpdateForResearchGroup(TPmOpenSubjectEntity tPmOpenSubject,
            HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmOpenSubject.getId())) {
            tPmOpenSubject = tPmOpenSubjectService.getEntity(TPmOpenSubjectEntity.class, tPmOpenSubject.getId());
            if (StringUtils.isEmpty(tPmOpenSubject.getAttachmentCode())) {
                tPmOpenSubject.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
            } else {
                List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                        tPmOpenSubject.getAttachmentCode(), "");
                tPmOpenSubject.setAttachments(fileList);
            }
            req.setAttribute("tPmOpenSubjectPage", tPmOpenSubject);
        }
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            CriteriaQuery cq = new CriteriaQuery(TPmOpenSubjectEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmOpenSubjectEntity> subjectList = tPmOpenSubjectService.getListByCriteriaQuery(cq, false);
            if (subjectList.size() > 0) {
                TPmOpenSubjectEntity openSubject = subjectList.get(0);
                Map<String, Object> dataMap = activitiService.getProcessInstance(openSubject.getId());
                if (dataMap != null) {
                    String processInstId = (String) dataMap.get("processInstId");
                    String taskId = (String) dataMap.get("taskId");
                    req.setAttribute("processInstId", processInstId);
                    req.setAttribute("taskId", taskId);
                    openSubject.setProcessInstId(processInstId);
                }
                if (StringUtils.isEmpty(openSubject.getAttachmentCode())) {
                    openSubject.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
                } else {
                    List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                            openSubject.getAttachmentCode(), "");
                    openSubject.setAttachments(fileList);
                }
                req.setAttribute("tPmOpenSubjectPage", openSubject);
            }else{
                TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
                TPmOpenSubjectEntity openSubjectEntity = new TPmOpenSubjectEntity();
                openSubjectEntity.setProject(proj);
//                openSubjectEntity.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
                if (StringUtils.isEmpty(openSubjectEntity.getAttachmentCode())) {
                    openSubjectEntity.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
                } else {
                    List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                            openSubjectEntity.getAttachmentCode(), "");
                    openSubjectEntity.setAttachments(fileList);
                }
                req.setAttribute("tPmOpenSubjectPage", openSubjectEntity);
            }
        }
        return new ModelAndView("com/kingtake/project/subject/tPmOpenSubject-updateForResearchGroup");
    }

    /**
     * 跳转到课题组的开题信息编辑界面.
     * 
     * @return
     */
    @RequestMapping(params = "openSubjectUpdateForResearchGroupInfo")
    public ModelAndView openSubjectUpdateForResearchGroupInfo(TPmOpenSubjectEntity tPmOpenSubject,
            HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmOpenSubject.getId())) {
            tPmOpenSubject = tPmOpenSubjectService.getEntity(TPmOpenSubjectEntity.class, tPmOpenSubject.getId());
            if (StringUtils.isEmpty(tPmOpenSubject.getAttachmentCode())) {
                tPmOpenSubject.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
            } else {
                List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                        tPmOpenSubject.getAttachmentCode(), "");
                tPmOpenSubject.setAttachments(fileList);
            }
            req.setAttribute("tPmOpenSubjectPage", tPmOpenSubject);
        }
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            CriteriaQuery cq = new CriteriaQuery(TPmOpenSubjectEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmOpenSubjectEntity> subjectList = tPmOpenSubjectService.getListByCriteriaQuery(cq, false);
            if (subjectList.size() > 0) {
                TPmOpenSubjectEntity openSubjectEntity = subjectList.get(0);
                if (StringUtils.isEmpty(openSubjectEntity.getAttachmentCode())) {
                    openSubjectEntity.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
                } else {
                    List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                            openSubjectEntity.getAttachmentCode(), "");
                    openSubjectEntity.setAttachments(fileList);
                }
                req.setAttribute("tPmOpenSubjectPage", openSubjectEntity);
            } else {
                TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
                TPmOpenSubjectEntity openSubjectEntity = new TPmOpenSubjectEntity();
                openSubjectEntity.setProject(proj);
                openSubjectEntity.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
                if (StringUtils.isEmpty(tPmOpenSubject.getAttachmentCode())) {
                    openSubjectEntity.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
                } else {
                    List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                            openSubjectEntity.getAttachmentCode(), "");
                    openSubjectEntity.setAttachments(fileList);
                }
                req.setAttribute("tPmOpenSubjectPage", openSubjectEntity);
            }
        }
        return new ModelAndView("com/kingtake/project/subject/tPmOpenSubject-updateForResearchGroupInfo");
    }

    
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/subject/tPmOpenSubjectUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPmOpenSubjectEntity tPmOpenSubject,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPmOpenSubjectEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmOpenSubject, request.getParameterMap());
		List<TPmOpenSubjectEntity> tPmOpenSubjects = this.tPmOpenSubjectService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"开题信息");
		modelMap.put(NormalExcelConstants.CLASS,TPmOpenSubjectEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("开题信息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPmOpenSubjects);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPmOpenSubjectEntity tPmOpenSubject,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "开题信息");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TPmOpenSubjectEntity.class);
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
				List<TPmOpenSubjectEntity> listTPmOpenSubjectEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TPmOpenSubjectEntity.class,params);
				for (TPmOpenSubjectEntity tPmOpenSubject : listTPmOpenSubjectEntitys) {
					tPmOpenSubjectService.save(tPmOpenSubject);
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

    /**
     * 跳转到课题组的开题报告审核界面.
     * 
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TPmOpenSubjectEntity tPmOpenSubject, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            CriteriaQuery cq = new CriteriaQuery(TPmOpenSubjectEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmOpenSubjectEntity> subjectList = tPmOpenSubjectService.getListByCriteriaQuery(cq, false);
            if (subjectList.size() > 0) {
                TPmOpenSubjectEntity openSubject = subjectList.get(0);
                Map<String, Object> dataMap = activitiService.getProcessInstance(openSubject.getId());
                if (dataMap != null) {
                    String processInstId = (String) dataMap.get("processInstId");
                    String taskId = (String) dataMap.get("taskId");
                    req.setAttribute("processInstId", processInstId);
                    req.setAttribute("taskId", taskId);
                    openSubject.setProcessInstId(processInstId);
                }
                if (StringUtils.isEmpty(openSubject.getAttachmentCode())) {
                    openSubject.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
                } else {
                    List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                            openSubject.getAttachmentCode(), "");
                    openSubject.setAttachments(fileList);
                }
                req.setAttribute("tPmOpenSubjectPage", openSubject);
            }
        }
        req.setAttribute("read", "1");
        req.setAttribute("opt", "audit");
        return new ModelAndView("com/kingtake/project/subject/tPmOpenSubject-updateForResearchGroup");
    }
}
