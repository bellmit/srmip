package com.kingtake.expert.controller.suggestion;

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

import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.entity.suggestion.TErSuggestionEntity;
import com.kingtake.expert.service.suggestion.TErSuggestionServiceI;

/**
 * @Title: Controller
 * @Description: 评审意见表
 * @author onlineGenerator
 * @date 2015-08-18 16:49:31
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tErSuggestionController")
public class TErSuggestionController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TErSuggestionController.class);

    @Autowired
    private TErSuggestionServiceI tErSuggestionService;
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
     * 评审意见表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tErSuggestion")
    public ModelAndView tErSuggestion(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/expert/suggestion/tErSuggestionList");
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
    public void datagrid(TErSuggestionEntity tErSuggestion, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TErReviewProjectEntity reviewProjectEntity = this.tErSuggestionService.get(TErReviewProjectEntity.class,
                tErSuggestion.getReviewProject().getId());
        CriteriaQuery cq = new CriteriaQuery(TErSuggestionEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErSuggestion,
                request.getParameterMap());
        cq.eq("reviewProject.id", reviewProjectEntity.getId());
        cq.add();
        this.tErSuggestionService.getDataGridReturn(cq, false);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除评审意见表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TErSuggestionEntity tErSuggestion, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tErSuggestion = systemService.getEntity(TErSuggestionEntity.class, tErSuggestion.getId());
        message = "评审意见表删除成功";
        try {
            tErSuggestionService.delete(tErSuggestion);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "评审意见表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除评审意见表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "评审意见表删除成功";
        try {
            for (String id : ids.split(",")) {
                TErSuggestionEntity tErSuggestion = systemService.getEntity(TErSuggestionEntity.class, id);
                tErSuggestionService.delete(tErSuggestion);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "评审意见表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加评审意见表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TErSuggestionEntity tErSuggestion, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "评审意见表添加成功";
        try {
            tErSuggestionService.save(tErSuggestion);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "评审意见表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新评审意见表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TErSuggestionEntity tErSuggestion, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "评审意见表更新成功";
        TErSuggestionEntity t = tErSuggestionService.get(TErSuggestionEntity.class, tErSuggestion.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tErSuggestion, t);
            tErSuggestionService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "评审意见表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 评审意见表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TErSuggestionEntity tErSuggestion, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tErSuggestion.getId())) {
            tErSuggestion = tErSuggestionService.getEntity(TErSuggestionEntity.class, tErSuggestion.getId());
            req.setAttribute("tErSuggestionPage", tErSuggestion);
        }
        return new ModelAndView("com/kingtake/expert/suggestion/tErSuggestion-add");
    }

    /**
     * 评审意见表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TErSuggestionEntity tErSuggestion, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tErSuggestion.getId())) {
            tErSuggestion = tErSuggestionService.getEntity(TErSuggestionEntity.class, tErSuggestion.getId());
            req.setAttribute("tErSuggestionPage", tErSuggestion);
        }
        return new ModelAndView("com/kingtake/expert/suggestion/tErSuggestion-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/expert/suggestion/tErSuggestionUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TErSuggestionEntity tErSuggestion, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TErSuggestionEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErSuggestion,
                request.getParameterMap());
        List<TErSuggestionEntity> tErSuggestions = this.tErSuggestionService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "评审意见表");
        modelMap.put(NormalExcelConstants.CLASS, TErSuggestionEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("评审意见表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tErSuggestions);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TErSuggestionEntity tErSuggestion, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "评审意见表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TErSuggestionEntity.class);
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
                List<TErSuggestionEntity> listTErSuggestionEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TErSuggestionEntity.class, params);
                for (TErSuggestionEntity tErSuggestion : listTErSuggestionEntitys) {
                    tErSuggestionService.save(tErSuggestion);
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
