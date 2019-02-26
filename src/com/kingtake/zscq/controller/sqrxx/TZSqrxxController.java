package com.kingtake.zscq.controller.sqrxx;

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

import com.kingtake.zscq.entity.sqrxx.TZSqrxxEntity;
import com.kingtake.zscq.service.sqrxx.TZSqrxxServiceI;

/**
 * @Title: Controller
 * @Description: 申请人信息
 * @author onlineGenerator
 * @date 2016-07-04 16:48:25
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZSqrxxController")
public class TZSqrxxController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TZSqrxxController.class);

    @Autowired
    private TZSqrxxServiceI tZSqrxxService;
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
     * 申请人信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tZSqrxx")
    public ModelAndView tZSqrxx(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/zscq/sqrxx/tZSqrxxList");
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
    public void datagrid(TZSqrxxEntity tZSqrxx, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TZSqrxxEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZSqrxx, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tZSqrxxService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除申请人信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TZSqrxxEntity tZSqrxx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tZSqrxx = systemService.getEntity(TZSqrxxEntity.class, tZSqrxx.getId());
        message = "申请人信息删除成功";
        try {
            tZSqrxxService.delete(tZSqrxx);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "申请人信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除申请人信息
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "申请人信息删除成功";
        try {
            for (String id : ids.split(",")) {
                TZSqrxxEntity tZSqrxx = systemService.getEntity(TZSqrxxEntity.class, id);
                tZSqrxxService.delete(tZSqrxx);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "申请人信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加申请人信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TZSqrxxEntity tZSqrxx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "申请人信息添加成功";
        try {
            tZSqrxxService.save(tZSqrxx);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "申请人信息添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新申请人信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TZSqrxxEntity tZSqrxx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "申请人信息保存成功";
        try {
            if (StringUtils.isEmpty(tZSqrxx.getId())) {
                this.tZSqrxxService.save(tZSqrxx);
            } else {
                TZSqrxxEntity t = tZSqrxxService.get(TZSqrxxEntity.class, tZSqrxx.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tZSqrxx, t);
                tZSqrxxService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "申请人信息保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 申请人信息新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TZSqrxxEntity tZSqrxx, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZSqrxx.getId())) {
            tZSqrxx = tZSqrxxService.getEntity(TZSqrxxEntity.class, tZSqrxx.getId());
            req.setAttribute("tZSqrxxPage", tZSqrxx);
        }
        return new ModelAndView("com/kingtake/zscq/sqrxx/tZSqrxx-add");
    }

    /**
     * 申请人信息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TZSqrxxEntity tZSqrxx, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZSqrxx.getId())) {
            tZSqrxx = tZSqrxxService.getEntity(TZSqrxxEntity.class, tZSqrxx.getId());
        }
        req.setAttribute("tZSqrxxPage", tZSqrxx);
        return new ModelAndView("com/kingtake/zscq/sqrxx/tZSqrxx-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/zscq/sqrxx/tZSqrxxUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TZSqrxxEntity tZSqrxx, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TZSqrxxEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZSqrxx, request.getParameterMap());
        List<TZSqrxxEntity> tZSqrxxs = this.tZSqrxxService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "申请人信息");
        modelMap.put(NormalExcelConstants.CLASS, TZSqrxxEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("申请人信息列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tZSqrxxs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TZSqrxxEntity tZSqrxx, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "申请人信息");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TZSqrxxEntity.class);
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
                List<TZSqrxxEntity> listTZSqrxxEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TZSqrxxEntity.class, params);
                for (TZSqrxxEntity tZSqrxx : listTZSqrxxEntitys) {
                    tZSqrxxService.save(tZSqrxx);
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
