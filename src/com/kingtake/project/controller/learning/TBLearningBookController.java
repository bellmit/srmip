package com.kingtake.project.controller.learning;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.pojo.base.TSBaseBusQuery;
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

import com.kingtake.project.entity.learning.TBLearningBookEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.learning.TBLearningBookServiceI;



/**   
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2015-12-03 11:15:00
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBLearningBookController")
public class TBLearningBookController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBLearningBookController.class);

	@Autowired
	private TBLearningBookServiceI tBLearningBookService;
	@Autowired
	private SystemService systemService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TPmDeclareServiceI tPmDeclareService;

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
	@RequestMapping(params = "tBLearningBook")
    public ModelAndView tBLearningBook(TBLearningBookEntity tBLearningBook, HttpServletRequest request) {
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        return new ModelAndView("com/kingtake/project/learning/tBLearningBookList");
	}

	/**
     * ???????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBLearningBookPm")
    public ModelAndView tBLearningBookPm(TBLearningBookEntity tBLearningBook, HttpServletRequest request) {
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)){
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/learning/tBLearningBookPmList");
    }
    
    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goCheckTab")
    public ModelAndView goCheckTab(TBLearningBookEntity tBLearningBook, HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/learning/learningBookCheck-tab");
    }

    /**
     * ?????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goCheckList")
    public ModelAndView goCheckList(TBLearningBookEntity tBLearningBook, HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/learning/learningBookCheckList");
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
	public void datagrid(TBLearningBookEntity tBLearningBook,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String role = request.getParameter("role");
		CriteriaQuery cq = new CriteriaQuery(TBLearningBookEntity.class, dataGrid);
		//?????????????????????
        TSUser user = ResourceUtil.getSessionUserName();
        if ("depart".equals(role)) {
            List<Map<String, Object>> listMap = this.tPmDeclareService.findHistoryTasks(user.getUserName(), request);
            if (listMap.size() == 0) {//?????????????????????????????????????????????
                dataGrid.setResults(new ArrayList<TBLearningBookEntity>());
                dataGrid.setTotal(0);
                TagUtil.datagrid(response, dataGrid);
                return;
            }
            Set<String> set = new HashSet<String>();
            for (Map<String, Object> map : listMap) {
                String id = (String) map.get("business_key");
                set.add(id);
            }
            cq.in("id", set.toArray());
        } else {
            cq.eq("createBy", user.getUserName());
        }
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningBook, request.getParameterMap());
		try{
		//???????????????????????????
            cq.addOrder("createDate", SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
        this.tBLearningBookService.getDataGridReturn(cq, true);
        for (Object entity : dataGrid.getResults()) {
            TBLearningBookEntity tmp = (TBLearningBookEntity) entity;
            if (!"1".equals(tmp.getBpmStatus())) {
                Map<String, Object> dataMap = activitiService.getProcessInstance(tmp.getId());
                if (dataMap != null) {
                    String taskName = (String) dataMap.get("taskName");
                    if (StringUtils.isNotEmpty(taskName)) {
                        tmp.setTaskName(taskName);
                    }
                    String processInstId = (String) dataMap.get("processInstId");
                    if (StringUtils.isNotEmpty(processInstId)) {
                        tmp.setProcessInstId(processInstId);
                    }
                    String taskId = (String) dataMap.get("taskId");
                    if (StringUtils.isNotEmpty(taskId)) {
                        tmp.setTaskId(taskId);
                    }
                    String assignee = (String) dataMap.get("assignee");
                    if (StringUtils.isNotEmpty(assignee)) {
                        tmp.setAssigneeName(assignee);
                    }
                }
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "checkList")
    public void checkList(TBLearningBookEntity tBLearningBook, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List<TSBaseBusQuery> taskList = this.tPmDeclareService.findPriTodoTasks(user.getUserName(), request);
        if (taskList.size() == 0) {//???????????????????????????????????????????????????
            dataGrid.setResults(new ArrayList<TBLearningBookEntity>());
            dataGrid.setTotal(0);
            TagUtil.datagrid(response, dataGrid);
            return;
        }
        Map<String, TSBaseBusQuery> taskMap = new HashMap<String, TSBaseBusQuery>();
        for (TSBaseBusQuery query : taskList) {
            taskMap.put(query.getId(), query);
        }
        List<String> ids = new ArrayList<String>();
        for (TSBaseBusQuery bus : taskList) {
            ids.add(bus.getId());
        }
        CriteriaQuery cq = new CriteriaQuery(TBLearningBookEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningBook,
                request.getParameterMap());
        try {
            cq.in("id", ids.toArray());
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBLearningBookService.getDataGridReturn(cq, false);
        for (Object entity : dataGrid.getResults()) {
            TBLearningBookEntity tmp = (TBLearningBookEntity) entity;
            TSBaseBusQuery query = taskMap.get(tmp.getId());
            tmp.setAssigneeName(query.getAssigneeName());
            if (query != null) {
                org.jeecgframework.workflow.model.activiti.Process pro = query.getProcess();
                if (pro != null) {
                    tmp.setTaskId(pro.getTask().getId());
                    tmp.setTaskName(pro.getTask().getName());
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

	/**
	 * ???????????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBLearningBookEntity tBLearningBook, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBLearningBook = systemService.getEntity(TBLearningBookEntity.class, tBLearningBook.getId());
		message = "?????????????????????????????????";
		try{
			tBLearningBookService.deleteAddAttach(tBLearningBook);
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
				TBLearningBookEntity tBLearningBook = systemService.getEntity(TBLearningBookEntity.class, 
				id
				);
				tBLearningBookService.deleteAddAttach(tBLearningBook);
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
    @RequestMapping(params = "doAddUpdate")
	@ResponseBody
    public AjaxJson doAddUpdate(TBLearningBookEntity tBLearningBook, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "???????????????????????????/????????????";
		try {
            if (StringUtils.isNotEmpty(tBLearningBook.getId())) {
                TBLearningBookEntity t = tBLearningBookService.get(TBLearningBookEntity.class, tBLearningBook.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBLearningBook, t);
                tBLearningBookService.updateEntitieAttach(t);
            } else {
                tBLearningBookService.save(tBLearningBook);
            }
            j.setObj(tBLearningBook);
		} catch (Exception e) {
			e.printStackTrace();
            message = "???????????????????????????/????????????";
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
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TBLearningBookEntity tBLearningBook, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBLearningBook.getId())) {
			tBLearningBook = tBLearningBookService.getEntity(TBLearningBookEntity.class, tBLearningBook.getId());
		}
		if(StringUtils.isEmpty(tBLearningBook.getAttachmentCode())){
		    tBLearningBook.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBLearningBook.getAttachmentCode(), "");
            tBLearningBook.setUploadFiles(certificates);
        }
        req.setAttribute("tBLearningBookPage", tBLearningBook);
		return new ModelAndView("com/kingtake/project/learning/tBLearningBook-update");
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("com/kingtake/project/learning/tBLearningBookUpload");
	}
	
	/**
	 * ??????excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBLearningBookEntity tBLearningBook,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBLearningBookEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningBook, request.getParameterMap());
		List<TBLearningBookEntity> tBLearningBooks = this.tBLearningBookService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"?????????????????????");
		modelMap.put(NormalExcelConstants.CLASS,TBLearningBookEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("???????????????????????????", "?????????:"+ResourceUtil.getSessionUserName().getRealName(),
			"????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBLearningBooks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * ??????excel ?????????
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBLearningBookEntity tBLearningBook,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel????????????"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TBLearningBookEntity.class);
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
				List<TBLearningBookEntity> listTBLearningBookEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TBLearningBookEntity.class,params);
				for (TBLearningBookEntity tBLearningBook : listTBLearningBookEntitys) {
					tBLearningBookService.save(tBLearningBook);
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

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TBLearningBookEntity tBLearningBook, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBLearningBook.getId())) {
            tBLearningBook = tBLearningBookService.getEntity(TBLearningBookEntity.class, tBLearningBook.getId());
        }
        if (req.getParameter("editFlag") != null) {
            req.setAttribute("editFlag", req.getParameter("editFlag"));
        }
        if (req.getParameter("read") != null) {
            req.setAttribute("read", req.getParameter("read"));
        }
        req.setAttribute("tBLearningBookPage", tBLearningBook);
        return new ModelAndView("com/kingtake/project/learning/tBLearningBook-audit");
    }


    /**
     * ??????id??????????????????
     * 
     * @param id
     * @param request
     * @param response
     */
    private Map<String, Object> getProcessInstance(String id) {
        Map<String, Object> map = null;
        String sql = "select t.proc_inst_id_,r.name_ ,r.id_,r.assignee_ from ACT_HI_PROCINST t "
                + "left join act_ru_task r on t.proc_inst_id_= r.proc_inst_id_ " + " where t.business_key_=?";
        List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql, id);
        if (resultList.size() > 0) {
            map = resultList.get(0);
        }
        return map;
    }
}
