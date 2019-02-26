package com.kingtake.base.controller.sideccatalog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.jeecgframework.tag.vo.datatable.SortDirection;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.sideccatalog.TPmSidecatalogEntity;
import com.kingtake.base.entity.sideccatalog.TPmTypeCatalogRelaEntity;
import com.kingtake.base.service.sideccatalog.TPmSidecatalogServiceI;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: 项目模块配置表
 * @author onlineGenerator
 * @date 2016-01-19 11:30:07
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmSidecatalogController")
public class TPmSidecatalogController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmSidecatalogController.class);

    @Autowired
    private TPmSidecatalogServiceI tPmSidecatalogService;
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
     * 项目模块配置表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmSidecatalog")
    public ModelAndView tPmSidecatalog(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/sideccatalog/tPmSidecatalogList");
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
    public void datagrid(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmSidecatalogEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmSidecatalog,
                request.getParameterMap());
        try {
            cq.addOrder("index", SortDirection.asc);
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmSidecatalogService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除项目模块配置表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmSidecatalog = systemService.getEntity(TPmSidecatalogEntity.class, tPmSidecatalog.getId());
        message = "项目模块配置表删除成功";
        try {
            tPmSidecatalogService.delete(tPmSidecatalog);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目模块配置表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除项目模块配置表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目模块配置表删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmSidecatalogEntity tPmSidecatalog = systemService.getEntity(TPmSidecatalogEntity.class, id);
                tPmSidecatalogService.delete(tPmSidecatalog);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目模块配置表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加项目模块配置表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目模块配置表添加成功";
        try {
            tPmSidecatalogService.save(tPmSidecatalog);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目模块配置表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新项目模块配置表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目模块配置表新增/更新成功";
        try {
            if (StringUtils.isEmpty(tPmSidecatalog.getId())) {
                tPmSidecatalogService.save(tPmSidecatalog);
            } else {
                TPmSidecatalogEntity t = tPmSidecatalogService.get(TPmSidecatalogEntity.class, tPmSidecatalog.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmSidecatalog, t);
                tPmSidecatalogService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目模块配置表新增/更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 项目模块配置表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmSidecatalog.getId())) {
            tPmSidecatalog = tPmSidecatalogService.getEntity(TPmSidecatalogEntity.class, tPmSidecatalog.getId());
            req.setAttribute("tPmSidecatalogPage", tPmSidecatalog);
        }
        return new ModelAndView("com/kingtake/base/sideccatalog/tPmSidecatalog-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/base/sideccatalog/tPmSidecatalogUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmSidecatalogEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmSidecatalog,
                request.getParameterMap());
        List<TPmSidecatalogEntity> tPmSidecatalogs = this.tPmSidecatalogService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目模块配置表");
        modelMap.put(NormalExcelConstants.CLASS, TPmSidecatalogEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目模块配置表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmSidecatalogs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "项目模块配置表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmSidecatalogEntity.class);
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
                List<TPmSidecatalogEntity> listTPmSidecatalogEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmSidecatalogEntity.class, params);
                for (TPmSidecatalogEntity tPmSidecatalog : listTPmSidecatalogEntitys) {
                    tPmSidecatalogService.save(tPmSidecatalog);
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
     * 查询项目配置
     * 
     * @param tPmSidecatalog
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getSideCatalogList")
    @ResponseBody
    public JSONArray getSideCatalogList(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request,
            HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        List<String> catalogIds = new ArrayList<String>();
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        if (project != null && project.getProjectType() != null) {//查询项目类型与模块关联
            List<TPmTypeCatalogRelaEntity> typeCatalogRelaList = this.systemService.findByProperty(
                    TPmTypeCatalogRelaEntity.class, "typeId", project.getProjectType().getId());
            for (TPmTypeCatalogRelaEntity rela : typeCatalogRelaList) {
                catalogIds.add(rela.getCatalogId());
            }
        }
        CriteriaQuery cq = new CriteriaQuery(TPmSidecatalogEntity.class);
        cq.eq("moduleType", ProjectConstant.PROJECT_MODULE_PROJECTINFO);
        cq.or(Restrictions.isNull("isUsed"),Restrictions.eq("isUsed", true));
        if (catalogIds.size() > 0) {
            cq.in("id", catalogIds.toArray());
        }
        cq.addOrder("index", SortDirection.asc);
        cq.add();
        List<TPmSidecatalogEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
        for (TPmSidecatalogEntity entity : list) {
            if (StringUtils.isNotEmpty(entity.getUrl()) && entity.getUrl().contains("${projectId}")) {
                entity.setUrl(entity.getUrl().replace("${projectId}", projectId));
            }
        }
        JSONArray array = (JSONArray) JSONArray.toJSON(list);
        return array;
    }

    /**
     * 查询项目过程管理配置
     * 
     * @param tPmSidecatalog
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getProcessModuleList")
    @ResponseBody
    public JSONObject getProcessModuleList(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request,
            HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        List<String> catalogIds = new ArrayList<String>();
        if (project != null && project.getProjectType() != null) {
            List<TPmTypeCatalogRelaEntity> catalogRelaList = this.systemService.findByProperty(
                    TPmTypeCatalogRelaEntity.class, "typeId", project.getProjectType().getId());
            for (TPmTypeCatalogRelaEntity rela : catalogRelaList) {
                catalogIds.add(rela.getCatalogId());
            }
        }
        CriteriaQuery cq = new CriteriaQuery(TPmSidecatalogEntity.class);
        cq.eq("moduleType", ProjectConstant.PROJECT_MODULE_PROCESSMGR);
        if (catalogIds.size() > 0) {//若没有配置，则显示所有
            cq.in("id", catalogIds.toArray());
        }
        //cq.notEq("isUsed", '');
        cq.addOrder("index", SortDirection.asc);
        cq.add();
        List<TPmSidecatalogEntity> list = this.systemService.getListByCriteriaQuery(cq, false);
        Map<String, List<TPmSidecatalogEntity>> moduleMap = new HashMap<String, List<TPmSidecatalogEntity>>();
        for (TPmSidecatalogEntity catalog : list) {
            if (StringUtils.isNotEmpty(catalog.getUrl()) && catalog.getUrl().contains("${projectId}")) {
                catalog.setUrl(catalog.getUrl().replace("${projectId}", projectId));
            }
            String businessCode = catalog.getBusinessCode();
            String[] codes = businessCode.split("_");
            if (moduleMap.containsKey(codes[0])) {
                List<TPmSidecatalogEntity> tmpList = moduleMap.get(codes[0]);
                tmpList.add(catalog);
            } else {
                List<TPmSidecatalogEntity> tmpList = new ArrayList<TPmSidecatalogEntity>();
                tmpList.add(catalog);
                moduleMap.put(codes[0], tmpList);
            }
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(moduleMap);
        return json;
    }

    /**
     * 根据id获取说明
     * 
     * @param tPmSidecatalog
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getMemo")
    public void getMemo(TPmSidecatalogEntity tPmSidecatalog, HttpServletRequest request, HttpServletResponse response) {
        String memo = "";
        tPmSidecatalog = this.systemService.get(TPmSidecatalogEntity.class, tPmSidecatalog.getId());
        if (StringUtils.isNotEmpty(tPmSidecatalog.getMemo())) {
            memo = tPmSidecatalog.getMemo();
        }
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(memo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
