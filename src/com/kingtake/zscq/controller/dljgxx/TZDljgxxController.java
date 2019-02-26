package com.kingtake.zscq.controller.dljgxx;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.kingtake.zscq.entity.dljgxx.TZDljgxxEntity;
import com.kingtake.zscq.service.dljgxx.TZDljgxxServiceI;

/**
 * @Title: Controller
 * @Description: 代理机构信息
 * @author onlineGenerator
 * @date 2016-07-04 16:48:12
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZDljgxxController")
public class TZDljgxxController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TZDljgxxController.class);

    @Autowired
    private TZDljgxxServiceI tZDljgxxService;
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
     * 代理机构信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tZDljgxx")
    public ModelAndView tZDljgxx(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/zscq/dljgxx/tZDljgxxList");
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
    public void datagrid(TZDljgxxEntity tZDljgxx, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TZDljgxxEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZDljgxx, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tZDljgxxService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除代理机构信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TZDljgxxEntity tZDljgxx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tZDljgxx = systemService.getEntity(TZDljgxxEntity.class, tZDljgxx.getId());
        message = "代理机构信息删除成功";
        try {
            tZDljgxxService.delete(tZDljgxx);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "代理机构信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除代理机构信息
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "代理机构信息删除成功";
        try {
            for (String id : ids.split(",")) {
                TZDljgxxEntity tZDljgxx = systemService.getEntity(TZDljgxxEntity.class, id);
                tZDljgxxService.delete(tZDljgxx);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "代理机构信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加代理机构信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TZDljgxxEntity tZDljgxx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "代理机构信息添加成功";
        try {
            tZDljgxxService.save(tZDljgxx);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "代理机构信息添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新代理机构信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TZDljgxxEntity tZDljgxx, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "代理机构信息更新成功";
        try {
            if (StringUtils.isEmpty(tZDljgxx.getId())) {
                this.tZDljgxxService.save(tZDljgxx);
            } else {
                TZDljgxxEntity t = tZDljgxxService.get(TZDljgxxEntity.class, tZDljgxx.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tZDljgxx, t);
                tZDljgxxService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "代理机构信息更新失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 代理机构信息新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TZDljgxxEntity tZDljgxx, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZDljgxx.getId())) {
            tZDljgxx = tZDljgxxService.getEntity(TZDljgxxEntity.class, tZDljgxx.getId());
            req.setAttribute("tZDljgxxPage", tZDljgxx);
        }
        return new ModelAndView("com/kingtake/zscq/dljgxx/tZDljgxx-add");
    }

    /**
     * 代理机构信息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TZDljgxxEntity tZDljgxx, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZDljgxx.getId())) {
            tZDljgxx = tZDljgxxService.getEntity(TZDljgxxEntity.class, tZDljgxx.getId());
            req.setAttribute("tZDljgxxPage", tZDljgxx);
        }
        return new ModelAndView("com/kingtake/zscq/dljgxx/tZDljgxx-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/zscq/dljgxx/tZDljgxxUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TZDljgxxEntity tZDljgxx, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TZDljgxxEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZDljgxx, request.getParameterMap());
        List<TZDljgxxEntity> tZDljgxxs = this.tZDljgxxService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "代理机构信息");
        modelMap.put(NormalExcelConstants.CLASS, TZDljgxxEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("代理机构信息列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tZDljgxxs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TZDljgxxEntity tZDljgxx, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "代理机构信息");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TZDljgxxEntity.class);
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
                List<TZDljgxxEntity> listTZDljgxxEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TZDljgxxEntity.class, params);
                for (TZDljgxxEntity tZDljgxx : listTZDljgxxEntitys) {
                    tZDljgxxService.save(tZDljgxx);
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

    @RequestMapping(params = "getDljgList")
    @ResponseBody
    public JSONArray getDljgList(HttpServletRequest request, HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        List<TZDljgxxEntity> dljgList = this.systemService.loadAll(TZDljgxxEntity.class);
        for (TZDljgxxEntity dljg : dljgList) {
            JSONObject json = new JSONObject();
            json.put("id", dljg.getId());
            json.put("text", dljg.getJgmc());
            jsonArray.add(json);
        }
        return jsonArray;
    }
}
