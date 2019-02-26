package com.kingtake.officeonline.controller;

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

import com.alibaba.fastjson.JSONArray;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;
import com.kingtake.officeonline.entity.TSOfficeModelFilesEntity;
import com.kingtake.officeonline.service.TOOfficeOnlineFilesServiceI;
import com.kingtake.officeonline.service.TSOfficeModelFilesServiceI;

/**
 * @Title: Controller
 * @Description: office模板文件
 * @author onlineGenerator
 * @date 2015-12-21 14:09:18
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSOfficeModelFilesController")
public class TSOfficeModelFilesController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TSOfficeModelFilesController.class);

    @Autowired
    private TSOfficeModelFilesServiceI tSOfficeModelFilesService;
    @Autowired
    private TOOfficeOnlineFilesServiceI tOOfficeOnlineFilesService;
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
     * office模板文件列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tSOfficeModelFiles")
    public ModelAndView tSOfficeModelFiles(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/officeonline/tSOfficeModelFilesList");
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
    public void datagrid(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        try {
            String sql = "SELECT T.NAME,T.CODE,T.ID AS ID FROM T_B_CODE_DETAIL T  WHERE T.CODE_TYPE_ID='40288af64e56bf48014e57136e1f001a' ORDER BY T.SORT";
            String cql = "SELECT COUNT(0) FROM T_B_CODE_DETAIL T WHERE T.CODE_TYPE_ID='40288af64e56bf48014e57136e1f001a'";

            List<Map<String, Object>> result = systemService.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

            Long count = systemService.getCountForJdbc(cql);
            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除office模板文件
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tSOfficeModelFiles = systemService.getEntity(TSOfficeModelFilesEntity.class, tSOfficeModelFiles.getId());
        message = "office模板文件删除成功";
        try {
            tSOfficeModelFilesService.delete(tSOfficeModelFiles);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "office模板文件删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除office模板文件
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "office模板文件删除成功";
        try {
            for (String id : ids.split(",")) {
                TSOfficeModelFilesEntity tSOfficeModelFiles = systemService.getEntity(TSOfficeModelFilesEntity.class,
                        id);
                tSOfficeModelFilesService.delete(tSOfficeModelFiles);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "office模板文件删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加office模板文件
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "office模板文件添加成功";
        try {
            tSOfficeModelFilesService.save(tSOfficeModelFiles);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "office模板文件添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(tSOfficeModelFiles);
        return j;
    }

    /**
     * 更新office模板文件
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "office模板文件更新成功";
        TSOfficeModelFilesEntity t = tSOfficeModelFilesService.get(TSOfficeModelFilesEntity.class,
                tSOfficeModelFiles.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tSOfficeModelFiles, t);
            tSOfficeModelFilesService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "office模板文件更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(t);
        return j;
    }

    /**
     * office模板文件新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tSOfficeModelFiles.getId())) {
            tSOfficeModelFiles = tSOfficeModelFilesService.getEntity(TSOfficeModelFilesEntity.class,
                    tSOfficeModelFiles.getId());
        }
        String code = req.getParameter("code");
        req.setAttribute("code", code);
        req.setAttribute("tSOfficeModelFilesPage", tSOfficeModelFiles);
        return new ModelAndView("com/kingtake/officeonline/tSOfficeModelFiles-add");
    }

    /**
     * office模板文件编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest req) {
        String code = req.getParameter("code");
        req.setAttribute("code", code);
        req.setAttribute("tSOfficeModelFilesPage", tSOfficeModelFiles);

        String sql = "select t.model_name as modelName,t.id as id,t.code_detail_id as codeDetailId,f.realpath,f.id as templateFileId from t_s_office_model_files t left join t_o_office_online_files f on t.template_file_id = f.id  where t.code_detail_id='"
                + tSOfficeModelFiles.getCodeDetailId() + "'";
        List<Map<String, Object>> result = systemService.findForJdbc(sql, null);
        if (result.size() > 0) {
            JSONArray array = new JSONArray();
            array = (JSONArray) JSONArray.toJSON(result);
            req.setAttribute("array", array);
            return new ModelAndView("com/kingtake/officeonline/tSOfficeModelFiles-update");
        }
        return new ModelAndView("com/kingtake/officeonline/tSOfficeModelFiles-add");
    }

    @RequestMapping(params = "getFileByModelId")
    @ResponseBody
    public AjaxJson getFileByModelId(HttpServletRequest req, String id) {
        AjaxJson j = new AjaxJson();
        TSOfficeModelFilesEntity model = systemService.get(TSOfficeModelFilesEntity.class, id);
        j.setObj(model);
        return j;
    }

    @RequestMapping(params = "deleteTemplate")
    @ResponseBody
    public AjaxJson deleteTemplate(HttpServletRequest req, String id) {
        AjaxJson j = new AjaxJson();
        TSOfficeModelFilesEntity model = systemService.get(TSOfficeModelFilesEntity.class, id);
        TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class, model.getTemplateFileId());
        tOOfficeOnlineFilesService.deleteFileAndEntity(file);
        systemService.delete(model);
        j.setObj(model);
        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/officeonline/tSOfficeModelFilesUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TSOfficeModelFilesEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSOfficeModelFiles,
                request.getParameterMap());
        List<TSOfficeModelFilesEntity> tSOfficeModelFiless = this.tSOfficeModelFilesService.getListByCriteriaQuery(cq,
                false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "office模板文件");
        modelMap.put(NormalExcelConstants.CLASS, TSOfficeModelFilesEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("office模板文件列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tSOfficeModelFiless);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TSOfficeModelFilesEntity tSOfficeModelFiles, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "office模板文件");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TSOfficeModelFilesEntity.class);
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
                List<TSOfficeModelFilesEntity> listTSOfficeModelFilesEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TSOfficeModelFilesEntity.class, params);
                for (TSOfficeModelFilesEntity tSOfficeModelFiles : listTSOfficeModelFilesEntitys) {
                    tSOfficeModelFilesService.save(tSOfficeModelFiles);
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
