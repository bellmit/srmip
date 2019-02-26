package com.kingtake.officeonline.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
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

import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;
import com.kingtake.officeonline.service.TOOfficeOnlineFilesServiceI;

/**
 * @Title: Controller
 * @Description: 在线office文件信息表
 * @author onlineGenerator
 * @date 2015-12-23 19:46:41
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOOfficeOnlineFilesController")
public class TOOfficeOnlineFilesController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOOfficeOnlineFilesController.class);

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
     * 在线office文件信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOOfficeOnlineFiles")
    public ModelAndView tOOfficeOnlineFiles(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/officeonlinefiles/tOOfficeOnlineFilesList");
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
    public void datagrid(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOOfficeOnlineFilesEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOOfficeOnlineFiles,
                request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tOOfficeOnlineFilesService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除在线office文件信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOOfficeOnlineFiles = systemService.getEntity(TOOfficeOnlineFilesEntity.class, tOOfficeOnlineFiles.getId());
        message = "在线office文件信息表删除成功";
        try {
            tOOfficeOnlineFilesService.delete(tOOfficeOnlineFiles);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "在线office文件信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除在线office文件信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "在线office文件信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TOOfficeOnlineFilesEntity tOOfficeOnlineFiles = systemService.getEntity(
                        TOOfficeOnlineFilesEntity.class, id);
                tOOfficeOnlineFilesService.delete(tOOfficeOnlineFiles);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "在线office文件信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加在线office文件信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "在线office文件信息表添加成功";
        try {
            tOOfficeOnlineFilesService.save(tOOfficeOnlineFiles);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "在线office文件信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新在线office文件信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "在线office文件信息表更新成功";
        TOOfficeOnlineFilesEntity t = tOOfficeOnlineFilesService.get(TOOfficeOnlineFilesEntity.class,
                tOOfficeOnlineFiles.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOOfficeOnlineFiles, t);
            tOOfficeOnlineFilesService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "在线office文件信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 在线office文件信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOOfficeOnlineFiles.getId())) {
            tOOfficeOnlineFiles = tOOfficeOnlineFilesService.getEntity(TOOfficeOnlineFilesEntity.class,
                    tOOfficeOnlineFiles.getId());
            req.setAttribute("tOOfficeOnlineFilesPage", tOOfficeOnlineFiles);
        }
        return new ModelAndView("com/kingtake/officeonlinefiles/tOOfficeOnlineFiles-add");
    }

    /**
     * 在线office文件信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOOfficeOnlineFiles.getId())) {
            tOOfficeOnlineFiles = tOOfficeOnlineFilesService.getEntity(TOOfficeOnlineFilesEntity.class,
                    tOOfficeOnlineFiles.getId());
            req.setAttribute("tOOfficeOnlineFilesPage", tOOfficeOnlineFiles);
        }
        return new ModelAndView("com/kingtake/officeonlinefiles/tOOfficeOnlineFiles-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/officeonlinefiles/tOOfficeOnlineFilesUpload");
    }

    @RequestMapping(params = "uploadOfficeonlineFiles", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson uploadOfficeonlineFiles(HttpServletRequest request, HttpServletResponse response,
            TOOfficeOnlineFilesEntity onlineFiles) {
        AjaxJson j = new AjaxJson();
        String businesskey = request.getParameter("businesskey");
        String cusPath = request.getParameter("cusPath");
        Map<String, Object> attributes = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(onlineFiles.getId())) {
            onlineFiles = systemService.get(TOOfficeOnlineFilesEntity.class, onlineFiles.getId());
            if (onlineFiles != null) {
                tOOfficeOnlineFilesService.deleteFileAndEntity(onlineFiles);
            }
        }
        TOOfficeOnlineFilesEntity newFiles = new TOOfficeOnlineFilesEntity();
        newFiles.setBusinesskey(businesskey);
        UploadFile uploadFile = new UploadFile(request, newFiles);
        uploadFile.setCusPath(cusPath);//文件路径子目录名称
        uploadFile.setByteField(null);// 不存二进制内容
        newFiles = systemService.uploadFile(uploadFile);
        j.setMsg("文件添加成功");
        j.setAttributes(attributes);
        j.setObj(newFiles);

        // 保存索引
        //        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        //        Map<String, Object> map = new HashMap<String, Object>();
        //        map.put("ID", tsFilesEntity.getId());
        //        map.put("TITLE", tsFilesEntity.getAttachmenttitle());
        //        map.put("PATH", tsFilesEntity.getRealpath());
        //        map.put("EXTEND", tsFilesEntity.getExtend());
        //        list.add(map);
        //
        //        String realPath = new File(request.getRealPath("/")).getParent();
        //        SolrOperate.addFilesIndex(list, realPath);

        return j;
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOOfficeOnlineFilesEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOOfficeOnlineFiles,
                request.getParameterMap());
        List<TOOfficeOnlineFilesEntity> tOOfficeOnlineFiless = this.tOOfficeOnlineFilesService.getListByCriteriaQuery(
                cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "在线office文件信息表");
        modelMap.put(NormalExcelConstants.CLASS, TOOfficeOnlineFilesEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("在线office文件信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tOOfficeOnlineFiless);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOOfficeOnlineFilesEntity tOOfficeOnlineFiles, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "在线office文件信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOOfficeOnlineFilesEntity.class);
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
                List<TOOfficeOnlineFilesEntity> listTOOfficeOnlineFilesEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TOOfficeOnlineFilesEntity.class, params);
                for (TOOfficeOnlineFilesEntity tOOfficeOnlineFiles : listTOOfficeOnlineFilesEntitys) {
                    tOOfficeOnlineFilesService.save(tOOfficeOnlineFiles);
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
