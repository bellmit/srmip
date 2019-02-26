package com.kingtake.project.controller.resultspread;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.resultspread.TBResultSpreadEntity;
import com.kingtake.project.service.resultspread.TBResultSpreadServiceI;

/**
 * @Title: Controller
 * @Description: 成果推广基本信息表
 * @author onlineGenerator
 * @date 2015-12-07 00:03:12
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBResultSpreadController")
public class TBResultSpreadController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBResultSpreadController.class);

    @Autowired
    private TBResultSpreadServiceI tBResultSpreadService;
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
     * 成果推广基本信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBResultSpread")
    public ModelAndView tBResultSpread(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
        }
        return new ModelAndView("com/kingtake/project/resultspread/tBResultSpreadList");
    }

    /**
     * 成果推广基本信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBResultSpreadProcess")
    public ModelAndView tBResultSpreadProcess(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
        }
        return new ModelAndView("com/kingtake/project/resultspread/tBResultSpreadListProcess");
    }

    /**
     * 成果推广基本信息表列表 机关页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBResultSpreadListForDepartment")
    public ModelAndView tBResultSpreadListForDepartment(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/resultspread/tBResultSpreadListForDepartment");
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
    public void datagrid(TBResultSpreadEntity tBResultSpread, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBResultSpreadEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBResultSpread,
                request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBResultSpreadService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除成果推广基本信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBResultSpreadEntity tBResultSpread, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBResultSpread = systemService.getEntity(TBResultSpreadEntity.class, tBResultSpread.getId());
        message = "成果推广基本信息表删除成功";
        try {
            tBResultSpreadService.delete(tBResultSpread);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果推广基本信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除成果推广基本信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成果推广基本信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBResultSpreadEntity tBResultSpread = systemService.getEntity(TBResultSpreadEntity.class, id);
                tBResultSpreadService.delete(tBResultSpread);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果推广基本信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加成果推广基本信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBResultSpreadEntity tBResultSpread, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成果推广基本信息表添加成功";
        try {
            tBResultSpreadService.save(tBResultSpread);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果推广基本信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tBResultSpread);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新成果推广基本信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBResultSpreadEntity tBResultSpread, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成果推广基本信息表更新成功";
        TBResultSpreadEntity t = tBResultSpreadService.get(TBResultSpreadEntity.class, tBResultSpread.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBResultSpread, t);
            tBResultSpreadService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果推广基本信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * 成果推广基本信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBResultSpreadEntity tBResultSpread, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        tBResultSpread.setProjectId(projectId);
        if (StringUtil.isNotEmpty(tBResultSpread.getId())) {
            tBResultSpread = tBResultSpreadService.getEntity(TBResultSpreadEntity.class, tBResultSpread.getId());
        }
        req.setAttribute("tBResultSpreadPage", tBResultSpread);
        return new ModelAndView("com/kingtake/project/resultspread/tBResultSpread-add");
    }

    /**
     * 成果推广基本信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBResultSpreadEntity tBResultSpread, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBResultSpread.getId())) {
            tBResultSpread = tBResultSpreadService.getEntity(TBResultSpreadEntity.class, tBResultSpread.getId());
            req.setAttribute("tBResultSpreadPage", tBResultSpread);
        }
        return new ModelAndView("com/kingtake/project/resultspread/tBResultSpread-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/resultspread/tBResultSpreadUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBResultSpreadEntity tBResultSpread, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBResultSpreadEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBResultSpread,
                request.getParameterMap());
        List<TBResultSpreadEntity> tBResultSpreads = this.tBResultSpreadService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "成果推广基本信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBResultSpreadEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("成果推广基本信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBResultSpreads);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBResultSpreadEntity tBResultSpread, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "成果推广基本信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBResultSpreadEntity.class);
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
                List<TBResultSpreadEntity> listTBResultSpreadEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBResultSpreadEntity.class, params);
                for (TBResultSpreadEntity tBResultSpread : listTBResultSpreadEntitys) {
                    tBResultSpreadService.save(tBResultSpread);
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
