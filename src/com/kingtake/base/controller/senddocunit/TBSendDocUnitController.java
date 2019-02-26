package com.kingtake.base.controller.senddocunit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PinyinUtil;
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

import com.kingtake.base.entity.senddocunit.TBSendDocUnitEntity;
import com.kingtake.base.service.senddocunit.TBSendDocUnitServiceI;

/**
 * @Title: Controller
 * @Description: 来文单位
 * @author onlineGenerator
 * @date 2015-09-20 16:02:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBSendDocUnitController")
public class TBSendDocUnitController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBSendDocUnitController.class);

    @Autowired
    private TBSendDocUnitServiceI tBSendDocUnitService;
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
     * 来文单位列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBSendDocUnit")
    public ModelAndView tBSendDocUnit(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/senddocunit/tBSendDocUnitList");
    }

    @RequestMapping(params = "getUnitPinyin")
    @ResponseBody
    public String getUsernamePinyin(String userName) {
        String pinyin = "";
        if (StringUtils.isNotEmpty(userName)) {
            String[] pinyins = PinyinUtil.stringToPinyin(userName);
            StringBuffer sb = new StringBuffer();
            for (String str : pinyins) {
                sb.append(str);
            }
            pinyin = sb.toString();
        }
        return pinyin;
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
    public void datagrid(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBSendDocUnitEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBSendDocUnit,
                request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBSendDocUnitService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除来文单位
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBSendDocUnit = systemService.getEntity(TBSendDocUnitEntity.class, tBSendDocUnit.getId());
        message = "来文单位删除成功";
        try {
            tBSendDocUnitService.delete(tBSendDocUnit);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "来文单位删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除来文单位
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "来文单位删除成功";
        try {
            for (String id : ids.split(",")) {
                TBSendDocUnitEntity tBSendDocUnit = systemService.getEntity(TBSendDocUnitEntity.class, id);
                tBSendDocUnitService.delete(tBSendDocUnit);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "来文单位删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加来文单位
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "来文单位添加成功";
        try {
            String pinyin = getUsernamePinyin(tBSendDocUnit.getUnitName());
            tBSendDocUnit.setUnitPinyin(pinyin);
            tBSendDocUnitService.save(tBSendDocUnit);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "来文单位添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新来文单位
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "来文单位更新成功";
        TBSendDocUnitEntity t = tBSendDocUnitService.get(TBSendDocUnitEntity.class, tBSendDocUnit.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBSendDocUnit, t);
            String pinyin = getUsernamePinyin(t.getUnitName());
            t.setUnitPinyin(pinyin);
            tBSendDocUnitService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "来文单位更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 来文单位新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBSendDocUnit.getId())) {
            tBSendDocUnit = tBSendDocUnitService.getEntity(TBSendDocUnitEntity.class, tBSendDocUnit.getId());
            req.setAttribute("tBSendDocUnitPage", tBSendDocUnit);
        }
        return new ModelAndView("com/kingtake/base/senddocunit/tBSendDocUnit-add");
    }

    /**
     * 来文单位编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBSendDocUnit.getId())) {
            tBSendDocUnit = tBSendDocUnitService.getEntity(TBSendDocUnitEntity.class, tBSendDocUnit.getId());
            req.setAttribute("tBSendDocUnitPage", tBSendDocUnit);
        }
        return new ModelAndView("com/kingtake/base/senddocunit/tBSendDocUnit-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/base/senddocunit/tBSendDocUnitUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBSendDocUnitEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBSendDocUnit,
                request.getParameterMap());
        List<TBSendDocUnitEntity> tBSendDocUnits = this.tBSendDocUnitService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "来文单位");
        modelMap.put(NormalExcelConstants.CLASS, TBSendDocUnitEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("来文单位列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBSendDocUnits);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBSendDocUnitEntity tBSendDocUnit, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "来文单位");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBSendDocUnitEntity.class);
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
                List<TBSendDocUnitEntity> listTBSendDocUnitEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBSendDocUnitEntity.class, params);
                for (TBSendDocUnitEntity tBSendDocUnit : listTBSendDocUnitEntitys) {
                    tBSendDocUnitService.save(tBSendDocUnit);
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
