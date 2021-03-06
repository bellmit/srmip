package com.kingtake.project.controller.change;

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
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
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

import com.kingtake.project.entity.change.TBPmChangeProjectnameEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.change.TBPmChangeProjectnameServiceI;
import com.kingtake.project.service.declare.TPmDeclareServiceI;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2015-08-21 09:56:04
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmChangeProjectnameController")
public class TBPmChangeProjectnameController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBPmChangeProjectnameController.class);

    @Autowired
    private TBPmChangeProjectnameServiceI tBPmChangeProjectnameService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TPmDeclareServiceI tPmDeclareService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
     * ???????????????????????????(???????????????)
     */
    @RequestMapping(params = "goTaskListTab")
    public ModelAndView goTaskListTab(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtil.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/change/taskList-tab");
    }

    /**
     * ???????????????????????????- ????????????????????????
     */
    @RequestMapping(params = "goMyTaskList")
    public ModelAndView goMyTaskList(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtil.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/change/taskList-person");
    }

    /**
     * ??????????????????????????? - ???????????????????????????????????????
     */
    @RequestMapping(params = "goGroupTaskList")
    public ModelAndView goGroupTaskList(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtil.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/change/taskList-group");
    }

    /**
     * ???????????????????????????- ??????????????????????????????
     */
    @RequestMapping(params = "goHistoryTaskList")
    public ModelAndView goHistoryTaskList(HttpServletRequest request) {
        String businessCode = request.getParameter("businessCode");
        if (StringUtil.isNotEmpty(businessCode)) {
            request.setAttribute("businessCode", businessCode);
        }
        return new ModelAndView("com/kingtake/project/change/taskList-history");
    }

    /**
     * ??????????????????-?????????????????????
     */

    @RequestMapping(params = "taskAllList")
    public void taskAllList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List taskList = tPmDeclareService.findPriTodoTasks(user.getUserName(), request);
        dataGrid.setResults(taskList);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * ??????????????????-?????????
     */

    @RequestMapping(params = "taskGroupList")
    public void taskGroupList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        //???????????????????????????
        List<TSRoleUser> roles = systemService.findByProperty(TSRoleUser.class, "TSUser", user);
        List taskList = this.tPmDeclareService.findGroupTodoTasks(roles, request);
        dataGrid.setResults(taskList);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * ??????????????????-????????????
     */

    @RequestMapping(params = "taskHistoryList")
    public void taskHistoryList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        List taskList = tPmDeclareService.findHistoryTasks(user.getUserName(), request);
        Long taskSize = tPmDeclareService.countHistoryTasks(user.getUserName(), request);
        dataGrid.setTotal(taskSize.intValue());
        dataGrid.setResults(taskList);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBPmChangeProjectname")
    public ModelAndView tBPmChangeProjectname(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectnameList");
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
    public void datagrid(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBPmChangeProjectnameEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmChangeProjectname,
                request.getParameterMap());
        try {
            //???????????????????????????
            String projectId = request.getParameter("projectId");
            if (StringUtil.isNotEmpty(projectId)) {
                cq.eq("projectId", projectId);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmChangeProjectnameService.getDataGridReturn(cq, true);
        for (Object entity : dataGrid.getResults()) {
            TBPmChangeProjectnameEntity tmp = (TBPmChangeProjectnameEntity) entity;
            if (!"1".equals(tmp.getBpmStatus())) {
                tmp.setProcessInstId(getProcessInstance(tmp.getId()));
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBPmChangeProjectname = systemService.getEntity(TBPmChangeProjectnameEntity.class,
                tBPmChangeProjectname.getId());
        message = "??????????????????????????????";
        try {
            tBPmChangeProjectnameService.deleteAddAttach(tBPmChangeProjectname);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TBPmChangeProjectnameEntity tBPmChangeProjectname = systemService.getEntity(
                        TBPmChangeProjectnameEntity.class, id);
                tBPmChangeProjectnameService.deleteAddAttach(tBPmChangeProjectname);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            tBPmChangeProjectnameService.save(tBPmChangeProjectname);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tBPmChangeProjectname);
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        TBPmChangeProjectnameEntity t = tBPmChangeProjectnameService.get(TBPmChangeProjectnameEntity.class,
                tBPmChangeProjectname.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBPmChangeProjectname, t);
            tBPmChangeProjectnameService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            req.setAttribute("beforeProjectName", project.getProjectName());
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectname-add");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBPmChangeProjectname.getId())) {
            tBPmChangeProjectname = tBPmChangeProjectnameService.getEntity(TBPmChangeProjectnameEntity.class,
                    tBPmChangeProjectname.getId());
            req.setAttribute("tBPmChangeProjectnamePage", tBPmChangeProjectname);
        }
        String read = req.getParameter("read");
        if (StringUtils.isNotEmpty(read)) {
            req.setAttribute("read", read);
        }
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectname-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectnameUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBPmChangeProjectnameEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmChangeProjectname,
                request.getParameterMap());
        List<TBPmChangeProjectnameEntity> tBPmChangeProjectnames = this.tBPmChangeProjectnameService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TBPmChangeProjectnameEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBPmChangeProjectnames);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBPmChangeProjectnameEntity tBPmChangeProjectname, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBPmChangeProjectnameEntity.class);
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
            MultipartFile file = entity.getValue();// ????????????????????????
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TBPmChangeProjectnameEntity> listTBPmChangeProjectnameEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBPmChangeProjectnameEntity.class, params);
                for (TBPmChangeProjectnameEntity tBPmChangeProjectname : listTBPmChangeProjectnameEntitys) {
                    tBPmChangeProjectnameService.save(tBPmChangeProjectname);
                }
                j.setMsg("?????????????????????");
            } catch (Exception e) {
                j.setMsg("?????????????????????");
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
}
