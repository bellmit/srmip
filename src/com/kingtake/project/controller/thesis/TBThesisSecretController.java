package com.kingtake.project.controller.thesis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
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

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.project.entity.thesis.TBThesisSecretEntity;
import com.kingtake.project.service.thesis.TBThesisSecretServiceI;

/**
 * @Title: Controller
 * @Description: 论文保密申请信息表
 * @author onlineGenerator
 * @date 2016-01-08 15:56:13
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBThesisSecretController")
public class TBThesisSecretController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBThesisSecretController.class);

    @Autowired
    private TBThesisSecretServiceI tBThesisSecretService;
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
     * 论文保密申请信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBThesisSecret")
    public ModelAndView tBThesisSecret(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/thesis/tBThesisSecretList");
    }

    /**
     * 论文保密申请信息表列表(机关)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tBThesisSecretForDepart")
    public ModelAndView tBThesisSecretForDepart(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/thesis/tBThesisSecretListForDepart");
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
    public void datagrid(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBThesisSecretEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBThesisSecret,
                request.getParameterMap());
        try {
            //自定义追加查询条件
            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("createBy", user.getUserName());
            cq.addOrder("checkFlag", SortDirection.asc);
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBThesisSecretService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridForDepart")
    public void datagridForDepart(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBThesisSecretEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBThesisSecret,
                request.getParameterMap());
        try {
            //自定义追加查询条件
            cq.addOrder("checkFlag", SortDirection.asc);
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBThesisSecretService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除论文保密申请信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBThesisSecret = systemService.getEntity(TBThesisSecretEntity.class, tBThesisSecret.getId());
        message = "论文保密申请信息表删除成功";
        try {
            tBThesisSecretService.deleteAddAttach(tBThesisSecret);
        } catch (Exception e) {
            e.printStackTrace();
            message = "论文保密申请信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除论文保密申请信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "论文保密申请信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBThesisSecretEntity tBThesisSecret = systemService.getEntity(TBThesisSecretEntity.class, id);
                tBThesisSecretService.deleteAddAttach(tBThesisSecret);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "论文保密申请信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加论文保密申请信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "论文保密申请信息表添加成功";
        try {
            tBThesisSecretService.save(tBThesisSecret);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "论文保密申请信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tBThesisSecret);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新论文保密申请信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isEmpty(tBThesisSecret.getId())) {
            //新增
            message = "论文保密申请信息表添加成功";
            try {
                tBThesisSecret.setCheckFlag(SrmipConstants.NO);
                tBThesisSecretService.save(tBThesisSecret);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "论文保密申请信息表添加失败";
                throw new BusinessException(e.getMessage());
            }
            j.setObj(tBThesisSecret);
        } else {
            //修改
            message = "论文保密申请信息表更新成功";
            TBThesisSecretEntity t = tBThesisSecretService.get(TBThesisSecretEntity.class, tBThesisSecret.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tBThesisSecret, t);
                tBThesisSecretService.saveOrUpdate(t);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "论文保密申请信息表更新失败";
                throw new BusinessException(e.getMessage());
            }
            j.setObj(t);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 论文保密审查
     * 
     * @param tBThesisSecret
     * @param request
     * @return
     */
    @RequestMapping(params = "doCheck")
    @ResponseBody
    public AjaxJson doCheck(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        //修改
        message = "论文保密审查成功";
        TBThesisSecretEntity t = tBThesisSecretService.get(TBThesisSecretEntity.class, tBThesisSecret.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBThesisSecret, t);
            t.setCheckFlag(SrmipConstants.YES);
            tBThesisSecretService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "论文保密审查失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * 论文保密申请信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBThesisSecretEntity tBThesisSecret, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBThesisSecret.getId())) {
            tBThesisSecret = tBThesisSecretService.getEntity(TBThesisSecretEntity.class, tBThesisSecret.getId());
            req.setAttribute("tBThesisSecretPage", tBThesisSecret);
        }
        return new ModelAndView("com/kingtake/project/thesis/tBThesisSecret-update");
    }

    /**
     * 论文保密申请信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBThesisSecretEntity tBThesisSecret, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBThesisSecret.getId())) {
            tBThesisSecret = tBThesisSecretService.getEntity(TBThesisSecretEntity.class, tBThesisSecret.getId());
        }
        //附件
        if(StringUtils.isEmpty(tBThesisSecret.getAttachmentCode())){
            tBThesisSecret.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBThesisSecret.getAttachmentCode(), "");
            tBThesisSecret.setCertificates(certificates);
        }
        req.setAttribute("tBThesisSecretPage", tBThesisSecret);
        String opt = req.getParameter("opt");
        if(StringUtils.isNotEmpty(opt)){
            req.setAttribute("opt", opt);
        }
        return new ModelAndView("com/kingtake/project/thesis/tBThesisSecret-update");
    }

    /**
     * 审查页面跳转
     * 
     * @param tBThesisSecret
     * @param req
     * @return
     */
    @RequestMapping(params = "goCheck")
    public ModelAndView goCheck(TBThesisSecretEntity tBThesisSecret, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBThesisSecret.getId())) {
            tBThesisSecret = tBThesisSecretService.getEntity(TBThesisSecretEntity.class, tBThesisSecret.getId());
        }
        //附件
        if(StringUtils.isEmpty(tBThesisSecret.getAttachmentCode())){
            tBThesisSecret.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBThesisSecret.getAttachmentCode(), "");
            tBThesisSecret.setCertificates(certificates);
        }
        req.setAttribute("tBThesisSecretPage", tBThesisSecret);
        return new ModelAndView("com/kingtake/project/thesis/tBThesisSecret-check");
    }

    @RequestMapping(params = "goOfficePage")
    public ModelAndView goOfficePage(HttpServletRequest req, TBThesisSecretEntity tBThesisSecret) {
        if (StringUtil.isNotEmpty(tBThesisSecret.getId())) {
            tBThesisSecret = tBThesisSecretService.getEntity(TBThesisSecretEntity.class, tBThesisSecret.getId());
            req.setAttribute("tBThesisSecretPage", tBThesisSecret);
        }
        return new ModelAndView("com/kingtake/project/thesis/onlyOfficePage");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/thesis/tBThesisSecretUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBThesisSecretEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBThesisSecret,
                request.getParameterMap());
        List<TBThesisSecretEntity> tBThesisSecrets = this.tBThesisSecretService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "论文保密申请信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBThesisSecretEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("论文保密申请信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBThesisSecrets);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public void exportXlsByT(TBThesisSecretEntity tBThesisSecret, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    	tBThesisSecretService.downloadTemplate(request, response);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String regesterType = request.getParameter("regesterType");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            try {
            	String msg = "";
                HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());

                msg = tBThesisSecretService.importExcel(wb);
                
                j.setMsg("文件导入成功！<br>" + msg);
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                e.printStackTrace();
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
     * 获取审查编号
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getSerectCode")
    @ResponseBody
    public AjaxJson getSerectCode(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String concreteDeptId = request.getParameter("concreteDeptId");
        String subordinateDeptId = request.getParameter("subordinateDeptId");
        String secretCode = PrimaryGenerater.getInstance().generaterNextThesisSecretCode(concreteDeptId,
                subordinateDeptId);
        j.setObj(secretCode);
        return j;
    }
}
