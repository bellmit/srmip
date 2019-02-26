package com.kingtake.project.controller.systemfile;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
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

import com.kingtake.project.entity.systemfile.TPmQualitySystemFileEntity;
import com.kingtake.project.service.systemfile.TPmQualitySystemFileServiceI;

/**
 * @Title: Controller
 * @Description: 体系文件信息表
 * @author onlineGenerator
 * @date 2015-08-20 09:32:29
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmQualitySystemFileController")
public class TPmQualitySystemFileController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmQualitySystemFileController.class);

    @Autowired
    private TPmQualitySystemFileServiceI tPmQualitySystemFileService;
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
     * 体系文件信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmQualitySystemFile")
    public ModelAndView tPmQualitySystemFile(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/systemfile/tPmQualitySystemFileList");
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
    public void datagrid(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmQualitySystemFileEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmQualitySystemFile,
                request.getParameterMap());
        try {
            //自定义追加查询条件
            String query_releaseTime_begin = request.getParameter("releaseTime_begin");
            String query_releaseTime_end = request.getParameter("releaseTime_end");
            if (StringUtil.isNotEmpty(query_releaseTime_begin)) {
                cq.ge("releaseTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_releaseTime_begin));
            }
            if (StringUtil.isNotEmpty(query_releaseTime_end)) {
                cq.le("releaseTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_releaseTime_end));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmQualitySystemFileService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除体系文件信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmQualitySystemFile = systemService.getEntity(TPmQualitySystemFileEntity.class, tPmQualitySystemFile.getId());
        message = "体系文件信息表删除成功";
        try {
            tPmQualitySystemFileService.deleteAddAttach(tPmQualitySystemFile);
        } catch (Exception e) {
            e.printStackTrace();
            message = "体系文件信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除体系文件信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "体系文件信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmQualitySystemFileEntity tPmQualitySystemFile = systemService.getEntity(
                        TPmQualitySystemFileEntity.class, id);
                tPmQualitySystemFileService.deleteAddAttach(tPmQualitySystemFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "体系文件信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加体系文件信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "体系文件信息表添加成功";
        try {
            tPmQualitySystemFileService.save(tPmQualitySystemFile);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "体系文件信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tPmQualitySystemFile);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新体系文件信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "体系文件信息表更新成功";
        TPmQualitySystemFileEntity t = tPmQualitySystemFileService.get(TPmQualitySystemFileEntity.class,
                tPmQualitySystemFile.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmQualitySystemFile, t);
            tPmQualitySystemFileService.updateEntitie(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "体系文件信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * 体系文件信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmQualitySystemFile.getId())) {
            tPmQualitySystemFile = tPmQualitySystemFileService.getEntity(TPmQualitySystemFileEntity.class,
                    tPmQualitySystemFile.getId());
        }
        //附件
        if(StringUtils.isEmpty(tPmQualitySystemFile.getAttachmentCode())){
            tPmQualitySystemFile.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmQualitySystemFile.getAttachmentCode(), "");
            tPmQualitySystemFile.setCertificates(certificates);
        }
        req.setAttribute("tPmQualitySystemFilePage", tPmQualitySystemFile);
        return new ModelAndView("com/kingtake/project/systemfile/tPmQualitySystemFile-add");
    }

    /**
     * 体系文件信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmQualitySystemFile.getId())) {
            tPmQualitySystemFile = tPmQualitySystemFileService.getEntity(TPmQualitySystemFileEntity.class,
                    tPmQualitySystemFile.getId());
        }
        //附件
        if(StringUtils.isEmpty(tPmQualitySystemFile.getAttachmentCode())){
            tPmQualitySystemFile.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmQualitySystemFile.getAttachmentCode(), "");
            tPmQualitySystemFile.setCertificates(certificates);
        }
        req.setAttribute("tPmQualitySystemFilePage", tPmQualitySystemFile);
        return new ModelAndView("com/kingtake/project/systemfile/tPmQualitySystemFile-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/systemfile/tPmQualitySystemFileUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmQualitySystemFileEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmQualitySystemFile,
                request.getParameterMap());
        List<TPmQualitySystemFileEntity> tPmQualitySystemFiles = this.tPmQualitySystemFileService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "体系文件信息表");
        modelMap.put(NormalExcelConstants.CLASS, TPmQualitySystemFileEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("体系文件信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmQualitySystemFiles);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmQualitySystemFileEntity tPmQualitySystemFile, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "体系文件信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmQualitySystemFileEntity.class);
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
                List<TPmQualitySystemFileEntity> listTPmQualitySystemFileEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmQualitySystemFileEntity.class, params);
                for (TPmQualitySystemFileEntity tPmQualitySystemFile : listTPmQualitySystemFileEntitys) {
                    tPmQualitySystemFileService.save(tPmQualitySystemFile);
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
