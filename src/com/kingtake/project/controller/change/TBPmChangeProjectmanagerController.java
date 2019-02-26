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

import com.kingtake.project.entity.change.TBPmChangeProjectmanagerEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.change.TBPmChangeProjectmanagerServiceI;

/**
 * @Title: Controller
 * @Description: 项目负责人变更信息表
 * @author onlineGenerator
 * @date 2015-09-02 16:57:08
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmChangeProjectmanagerController")
public class TBPmChangeProjectmanagerController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBPmChangeProjectmanagerController.class);

    @Autowired
    private TBPmChangeProjectmanagerServiceI tBPmChangeProjectmanagerService;
    @Autowired
    private SystemService systemService;
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
     * 项目负责人变更信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBPmChangeProjectmanager")
    public ModelAndView tBPmChangeProjectmanager(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectmanagerList");
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
    public void datagrid(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBPmChangeProjectmanagerEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmChangeProjectmanager,
                request.getParameterMap());
        cq.add();
        systemService.getDataGridReturn(cq, true);
        for (Object entity : dataGrid.getResults()) {
            TBPmChangeProjectmanagerEntity tmp = (TBPmChangeProjectmanagerEntity) entity;
            if (!"1".equals(tmp.getBpmStatus())) {
                tmp.setProcessInstId(getProcessInstance(tmp.getId()));
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除项目负责人变更信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBPmChangeProjectmanager = systemService.getEntity(TBPmChangeProjectmanagerEntity.class,
                tBPmChangeProjectmanager.getId());
        message = "项目负责人变更信息表删除成功";
        try {
            tBPmChangeProjectmanagerService.deleteAddAttach(tBPmChangeProjectmanager);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目负责人变更信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除项目负责人变更信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目负责人变更信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager = systemService.getEntity(
                        TBPmChangeProjectmanagerEntity.class, id);
                tBPmChangeProjectmanagerService.deleteAddAttach(tBPmChangeProjectmanager);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目负责人变更信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加项目负责人变更信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, String before, String after,
            String projectId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目负责人变更信息表添加成功";
        try {
            if (StringUtil.isNotEmpty(before)) {
                TSUser beforeManager = systemService.get(TSUser.class, before);
                tBPmChangeProjectmanager.setBeforeProjectManager(beforeManager);
            }
            if (StringUtil.isNotEmpty(after)) {
                TSUser afterManager = systemService.get(TSUser.class, after);
                tBPmChangeProjectmanager.setAfterProjectManager(afterManager);
            }
            if (StringUtil.isNotEmpty(projectId)) {
                TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
                tBPmChangeProjectmanager.setProject(project);
            }
            tBPmChangeProjectmanagerService.save(tBPmChangeProjectmanager);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目负责人变更信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tBPmChangeProjectmanager.getId());
        j.setMsg(message);
        return j;
    }

    /**
     * 更新项目负责人变更信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, String before, String after,
            String projectId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目负责人变更信息表更新成功";
        TBPmChangeProjectmanagerEntity t = tBPmChangeProjectmanagerService.get(TBPmChangeProjectmanagerEntity.class,
                tBPmChangeProjectmanager.getId());
        try {
            if (StringUtil.isNotEmpty(before)) {
                TSUser beforeManager = systemService.get(TSUser.class, before);
                tBPmChangeProjectmanager.setBeforeProjectManager(beforeManager);
            }
            if (StringUtil.isNotEmpty(after)) {
                TSUser afterManager = systemService.get(TSUser.class, after);
                tBPmChangeProjectmanager.setAfterProjectManager(afterManager);
            }
            if (StringUtil.isNotEmpty(projectId)) {
                TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
                tBPmChangeProjectmanager.setProject(project);
            }
            MyBeanUtils.copyBeanNotNull2Bean(tBPmChangeProjectmanager, t);
            tBPmChangeProjectmanagerService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目负责人变更信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t.getId());
        j.setMsg(message);
        return j;
    }

    /**
     * 项目负责人变更信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            req.setAttribute("project", project);
        }

        if (StringUtil.isNotEmpty(tBPmChangeProjectmanager.getId())) {
            tBPmChangeProjectmanager = tBPmChangeProjectmanagerService.getEntity(TBPmChangeProjectmanagerEntity.class,
                    tBPmChangeProjectmanager.getId());
            req.setAttribute("tBPmChangeProjectmanagerPage", tBPmChangeProjectmanager);
        }
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectmanager-add");
    }

    /**
     * 项目负责人变更信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBPmChangeProjectmanager.getId())) {
            tBPmChangeProjectmanager = tBPmChangeProjectmanagerService.getEntity(TBPmChangeProjectmanagerEntity.class,
                    tBPmChangeProjectmanager.getId());
            req.setAttribute("tBPmChangeProjectmanagerPage", tBPmChangeProjectmanager);
        }
        String read = req.getParameter("read");
        if (StringUtils.isNotEmpty(read)) {
            req.setAttribute("read", read);
        }
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectmanager-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/change/tBPmChangeProjectmanagerUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBPmChangeProjectmanagerEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmChangeProjectmanager,
                request.getParameterMap());
        List<TBPmChangeProjectmanagerEntity> tBPmChangeProjectmanagers = this.tBPmChangeProjectmanagerService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目负责人变更信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBPmChangeProjectmanagerEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目负责人变更信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBPmChangeProjectmanagers);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "项目负责人变更信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBPmChangeProjectmanagerEntity.class);
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
                List<TBPmChangeProjectmanagerEntity> listTBPmChangeProjectmanagerEntitys = ExcelImportUtil
                        .importExcelByIs(file.getInputStream(), TBPmChangeProjectmanagerEntity.class, params);
                for (TBPmChangeProjectmanagerEntity tBPmChangeProjectmanager : listTBPmChangeProjectmanagerEntitys) {
                    tBPmChangeProjectmanagerService.save(tBPmChangeProjectmanager);
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
}
