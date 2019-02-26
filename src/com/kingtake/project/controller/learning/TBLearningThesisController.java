package com.kingtake.project.controller.learning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import org.jeecgframework.workflow.model.activiti.Variable;
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

import com.alibaba.fastjson.JSONObject;
import com.kingtake.project.entity.learning.TBLearningThesisEntity;
import com.kingtake.project.service.declare.TPmDeclareServiceI;
import com.kingtake.project.service.learning.TBLearningThesisServiceI;

/**
 * @Title: Controller
 * @Description: 学术论文信息表
 * @author onlineGenerator
 * @date 2015-12-03 11:14:19
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBLearningThesisController")
public class TBLearningThesisController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBLearningThesisController.class);

    @Autowired
    private TBLearningThesisServiceI tBLearningThesisService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TPmDeclareServiceI tPmDeclareService;

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
     * 学术论文信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBLearningThesis")
    public ModelAndView tBLearningThesis(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request) {
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        return new ModelAndView("com/kingtake/project/learning/tBLearningThesisList");
    }

    /**
     * 课题组学术论文信息表列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBLearningThesisPm")
    public ModelAndView tBLearningThesisPm(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request) {
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/learning/tBLearningThesisPmList");
    }
    
    /**
     * 学术论文机关页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goCheckTab")
    public ModelAndView goCheckTab(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/learning/learningThesisCheck-tab");
    }

    /**
     * 学术论文信息表审核列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goCheckList")
    public ModelAndView goCheckList(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/learning/learningThesisCheckList");
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
    public void datagrid(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String role = request.getParameter("role");
        CriteriaQuery cq = new CriteriaQuery(TBLearningThesisEntity.class, dataGrid);
        //查询条件组装器
        TSUser user = ResourceUtil.getSessionUserName();
        if ("depart".equals(role)) {
            List<Map<String, Object>> listMap = this.tPmDeclareService.findHistoryTasks(user.getUserName(), request);
            if (listMap.size() == 0) {//如果已审核的学术论文，则返回。
                dataGrid.setResults(new ArrayList<TBLearningThesisEntity>());
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
            cq.or(Restrictions.eq("createBy", user.getUserName()), Restrictions.eq("authorFirst.id", user.getId()));
            
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningThesis,
                request.getParameterMap());
        try {
            //自定义追加查询条件
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBLearningThesisService.getDataGridReturn(cq, true);
        for (Object entity : dataGrid.getResults()) {
            TBLearningThesisEntity tmp = (TBLearningThesisEntity) entity;
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
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "checkList")
    public void checkList(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List<TSBaseBusQuery> taskList = this.tPmDeclareService.findPriTodoTasks(user.getUserName(), request);
        if (taskList.size() == 0) {//如果没有待审核的学术论文，则返回。
            dataGrid.setResults(new ArrayList<TBLearningThesisEntity>());
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
        CriteriaQuery cq = new CriteriaQuery(TBLearningThesisEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningThesis,
                request.getParameterMap());
        try {
            cq.in("id", ids.toArray());
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBLearningThesisService.getDataGridReturn(cq, false);
        for (Object entity : dataGrid.getResults()) {
            TBLearningThesisEntity tmp = (TBLearningThesisEntity) entity;
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
     * 删除学术论文信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBLearningThesis = systemService.getEntity(TBLearningThesisEntity.class, tBLearningThesis.getId());
        message = "学术论文信息表删除成功";
        try {
            tBLearningThesisService.deleteAddAttach(tBLearningThesis);
        } catch (Exception e) {
            e.printStackTrace();
            message = "学术论文信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除学术论文信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "学术论文信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBLearningThesisEntity tBLearningThesis = systemService.getEntity(TBLearningThesisEntity.class, id);
                tBLearningThesisService.deleteAddAttach(tBLearningThesis);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "学术论文信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新学术论文信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "学术论文信息表新增/更新成功";
        try {
            if (StringUtils.isNotEmpty(tBLearningThesis.getId())) {
                TBLearningThesisEntity t = tBLearningThesisService.get(TBLearningThesisEntity.class,
                        tBLearningThesis.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBLearningThesis, t);
                tBLearningThesisService.updateEntitieAttach(t);
            } else {
                tBLearningThesis.setBpmStatus("1");
                tBLearningThesisService.save(tBLearningThesis);
            }
            j.setObj(tBLearningThesis);
        } catch (Exception e) {
            e.printStackTrace();
            message = "学术论文信息表新增/更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 学术论文信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TBLearningThesisEntity tBLearningThesis, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBLearningThesis.getId())) {
            tBLearningThesis = tBLearningThesisService
                    .getEntity(TBLearningThesisEntity.class, tBLearningThesis.getId());
        }
        String role = req.getParameter("role");
        if ("research".equals(role)) {
            req.setAttribute("researchEdit", "1");
        }
        if(StringUtils.isEmpty(tBLearningThesis.getAttachmentCode())){
            tBLearningThesis.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> coverPictures = systemService.getAttachmentByCode(tBLearningThesis.getAttachmentCode(), "coverPic");
            tBLearningThesis.setCoverPictures(coverPictures);
            List<TSFilesEntity> homePage = systemService.getAttachmentByCode(tBLearningThesis.getAttachmentCode(), "homePagePic");
            tBLearningThesis.setHomePagePictures(homePage);
            List<TSFilesEntity> directoryPic = systemService.getAttachmentByCode(tBLearningThesis.getAttachmentCode(), "directoryPic");
            tBLearningThesis.setDirectoryPictures(directoryPic);
            List<TSFilesEntity> eEdition = systemService.getAttachmentByCode(tBLearningThesis.getAttachmentCode(), "eEdition");
            tBLearningThesis.setEditions(eEdition);
        }
        req.setAttribute("tBLearningThesisPage", tBLearningThesis);
        return new ModelAndView("com/kingtake/project/learning/tBLearningThesis-update");
    }

    /**
     * 学术论文信息表审核页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TBLearningThesisEntity tBLearningThesis, HttpServletRequest req, String read) {
        if (StringUtil.isNotEmpty(tBLearningThesis.getId())) {
            tBLearningThesis = tBLearningThesisService
                    .getEntity(TBLearningThesisEntity.class, tBLearningThesis.getId());
        }
        if (req.getParameter("editFlag") != null) {
            req.setAttribute("editFlag", req.getParameter("editFlag"));
        }
        if (req.getParameter("read") != null) {
            req.setAttribute("read", req.getParameter("read"));
        }
        req.setAttribute("tBLearningThesisPage", tBLearningThesis);
        return new ModelAndView("com/kingtake/project/learning/tBLearningThesis-audit");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/learning/tBLearningThesisUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBLearningThesisEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLearningThesis,
                request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        List<TBLearningThesisEntity> tBLearningThesiss = this.tBLearningThesisService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "学术论文信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBLearningThesisEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("学术论文信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBLearningThesiss);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBLearningThesisEntity tBLearningThesis, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "学术论文信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBLearningThesisEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
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
                List<TBLearningThesisEntity> listTBLearningThesisEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBLearningThesisEntity.class, params);
                for (TBLearningThesisEntity tBLearningThesis : listTBLearningThesisEntitys) {
                    tBLearningThesisService.save(tBLearningThesis);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
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
     * 跳转到选人的界面
     * 
     * @return
     */
    @RequestMapping(params = "goSelectOperator")
    public ModelAndView goSelectOperator(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = requestMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            request.setAttribute(entry.getKey(), entry.getValue()[0]);
        }
        return new ModelAndView("com/kingtake/project/learning/selectOperator");
    }

    /**
     * 
     * 提交流程
     * 
     * @param request
     * @param var
     * @return
     */
    @RequestMapping(params = "startProcess")
    @ResponseBody
    public AjaxJson startProcess(HttpServletRequest request, Variable var) {
        AjaxJson j = new AjaxJson();
        try {
            j = this.tBLearningThesisService.startProcess(request, var);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 根据id获取流程实例
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

    /**
     * 校验关联保密输入是否正确
     */
    @RequestMapping(params = "checkSecret")
    @ResponseBody
    public JSONObject checkSecret(TBLearningThesisEntity entity, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject json = new JSONObject();
        String secretNumber = request.getParameter("param");
        String table = request.getParameter("table");
        String field = request.getParameter("field");
        String sql = "select id from " + table + " where " + field + "='" + secretNumber + "'";
        List<Map<String, Object>> list = this.systemService.findForJdbc(sql);
        if (list.size() > 0) {
            json.put("status", "y");
            json.put("info", "关联保密审查编号校验成功！");
        } else {
            json.put("status", "n");
            json.put("info", "关联保密审查编号校验失败！");
        }
        return json;
    }
}
