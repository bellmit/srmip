package com.kingtake.project.controller.reportmaterial;

import java.io.IOException;
import java.util.Date;
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

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.reportmaterial.TBAppraisalReportMaterialEntity;
import com.kingtake.project.service.reportmaterial.TBAppraisalReportMaterialServiceI;

/**
 * @Title: Controller
 * @Description: 上报材料信息表
 * @author onlineGenerator
 * @date 2016-01-26 16:35:55
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalReportMaterialController")
public class TBAppraisalReportMaterialController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBAppraisalReportMaterialController.class);

    @Autowired
    private TBAppraisalReportMaterialServiceI tBAppraisalReportMaterialService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI messageService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 上报材料信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisalReportMaterial")
    public ModelAndView tBAppraisalReportMaterial(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterialList");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalReportMaterialEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalReportMaterial,
                request.getParameterMap());
            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("checkUserid", user.getId());
            //cq.eq("checkDepartid", user.getCurrentDepart().getId());
            cq.notEq("checkFlag", ApprovalConstant.APPRSTATUS_UNSEND);
        cq.add();
        this.tBAppraisalReportMaterialService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            if (StringUtil.isNotEmpty(id)) {
                TBAppraisalReportMaterialEntity reportMaterial = systemService.get(
                        TBAppraisalReportMaterialEntity.class, id);
                if (ApprovalConstant.APPRSTATUS_SEND.equals(reportMaterial.getCheckFlag())) {
                    reportMaterial.setCheckFlag(ApprovalConstant.APPRSTATUS_REBUT);
                } 
                reportMaterial.setMsgText(msgText);
                systemService.updateEntitie(reportMaterial);
            }
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(HttpServletRequest req) {
        String id = req.getParameter("id");
        AjaxJson j = this.tBAppraisalReportMaterialService.doAudit(id);
        return j;
    }

    /**
     * 删除上报材料信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBAppraisalReportMaterial = systemService.getEntity(TBAppraisalReportMaterialEntity.class,
                tBAppraisalReportMaterial.getId());
        message = "上报材料信息表删除成功";
        try {
            tBAppraisalReportMaterialService.delAttachementByBid(tBAppraisalReportMaterial.getAttachmentCode());
            tBAppraisalReportMaterialService.delete(tBAppraisalReportMaterial);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "上报材料信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除上报材料信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "上报材料信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBAppraisalReportMaterialEntity tBAppraisalReportMaterial = systemService.getEntity(
                        TBAppraisalReportMaterialEntity.class, id);
                tBAppraisalReportMaterialService.delete(tBAppraisalReportMaterial);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "上报材料信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加上报材料信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "上报材料信息表添加成功";
        try {
            tBAppraisalReportMaterialService.save(tBAppraisalReportMaterial);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "上报材料信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新上报材料信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "上报材料信息保存成功";
        try {
            if (StringUtil.isEmpty(tBAppraisalReportMaterial.getId())) {
                tBAppraisalReportMaterial.setCheckFlag(ApprovalConstant.APPLYSTATUS_UNSEND);//保存即为提交
                systemService.save(tBAppraisalReportMaterial);
                j.setObj(tBAppraisalReportMaterial);
            } else {
                TBAppraisalReportMaterialEntity t = tBAppraisalReportMaterialService.get(
                        TBAppraisalReportMaterialEntity.class, tBAppraisalReportMaterial.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalReportMaterial, t);
                tBAppraisalReportMaterialService.saveOrUpdate(t);
                j.setObj(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "上报材料信息保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 上报材料信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalReportMaterial.getId())) {
            tBAppraisalReportMaterial = tBAppraisalReportMaterialService.getEntity(
                    TBAppraisalReportMaterialEntity.class, tBAppraisalReportMaterial.getId());
            req.setAttribute("tBAppraisalReportMaterialPage", tBAppraisalReportMaterial);
        }
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterial-add");
    }

    /**
     * 上报材料信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalReportMaterial.getId())) {
            tBAppraisalReportMaterial = tBAppraisalReportMaterialService.getEntity(
                    TBAppraisalReportMaterialEntity.class, tBAppraisalReportMaterial.getId());
        } else if(StringUtils.isNotEmpty(tBAppraisalReportMaterial.getApplyId())){
            TBAppraisalApplyEntity apply = this.systemService.get(TBAppraisalApplyEntity.class, tBAppraisalReportMaterial.getApplyId());
            req.setAttribute("projectId", apply.getProjectId());
            TBAppraisalReportMaterialEntity queryMaterial =  this.systemService.findUniqueByProperty(TBAppraisalReportMaterialEntity.class, "applyId", tBAppraisalReportMaterial.getApplyId());
            if(queryMaterial!=null){
                tBAppraisalReportMaterial = queryMaterial;
            }
        }
        if (StringUtils.isEmpty(tBAppraisalReportMaterial.getAttachmentCode())) {
            tBAppraisalReportMaterial.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList1 = this.systemService.getAttachmentByCode(
                    tBAppraisalReportMaterial.getAttachmentCode(), "meetingMaterial");
            tBAppraisalReportMaterial.setMeetingMaterials(fileList1);
            List<TSFilesEntity> fileList2 = this.systemService.getAttachmentByCode(
                    tBAppraisalReportMaterial.getAttachmentCode(), "certificateScan");
            tBAppraisalReportMaterial.setCertificateScans(fileList2);
        }
        if(StringUtils.isNotEmpty(tBAppraisalReportMaterial.getApplyId())){
        TBAppraisalApplyEntity apply = this.systemService.get(TBAppraisalApplyEntity.class, tBAppraisalReportMaterial.getApplyId());
        req.setAttribute("projectId", apply.getProjectId());
        }
        req.setAttribute("tBAppraisalReportMaterialPage", tBAppraisalReportMaterial);
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterialEdit");
    }
    
    /**
     * 上报材料信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateForDepart")
    public ModelAndView goUpdateForDepart(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial,
            HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalReportMaterial.getId())) {
            tBAppraisalReportMaterial = tBAppraisalReportMaterialService.getEntity(
                    TBAppraisalReportMaterialEntity.class, tBAppraisalReportMaterial.getId());
            if (StringUtils.isEmpty(tBAppraisalReportMaterial.getAttachmentCode())) {
                tBAppraisalReportMaterial.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
            } else {
                List<TSFilesEntity> fileList1 = this.systemService.getAttachmentByCode(
                        tBAppraisalReportMaterial.getAttachmentCode(), "meetingMaterial");
                tBAppraisalReportMaterial.setMeetingMaterials(fileList1);
                List<TSFilesEntity> fileList2 = this.systemService.getAttachmentByCode(
                        tBAppraisalReportMaterial.getAttachmentCode(), "certificateScan");
                tBAppraisalReportMaterial.setCertificateScans(fileList2);
            }
            req.setAttribute("tBAppraisalReportMaterialPage", tBAppraisalReportMaterial);
        }
        if(StringUtils.isNotEmpty(tBAppraisalReportMaterial.getApplyId())){
            TBAppraisalApplyEntity apply = this.systemService.get(TBAppraisalApplyEntity.class, tBAppraisalReportMaterial.getApplyId());
            req.setAttribute("projectId", apply.getProjectId());
            }
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterialEditForDepart");
    }

    /**
     * 上报材料信息表新增或修改页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalReportMaterial.getId())) {
            tBAppraisalReportMaterial = systemService.get(TBAppraisalReportMaterialEntity.class,
                    tBAppraisalReportMaterial.getId());
        } else if(StringUtils.isNotEmpty(tBAppraisalReportMaterial.getApplyId())){
            TBAppraisalApplyEntity apply = this.systemService.get(TBAppraisalApplyEntity.class, tBAppraisalReportMaterial.getApplyId());
            req.setAttribute("projectId", apply.getProjectId());
            TBAppraisalReportMaterialEntity queryMaterial =  this.systemService.findUniqueByProperty(TBAppraisalReportMaterialEntity.class, "applyId", tBAppraisalReportMaterial.getApplyId());
            if(queryMaterial!=null){
                tBAppraisalReportMaterial = queryMaterial;
            }
            tBAppraisalReportMaterial.setCheckFlag("0");
        }
        if (StringUtils.isEmpty(tBAppraisalReportMaterial.getAttachmentCode())) {
            tBAppraisalReportMaterial.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList1 = this.systemService.getAttachmentByCode(
                    tBAppraisalReportMaterial.getAttachmentCode(), "meetingMaterial");
            tBAppraisalReportMaterial.setMeetingMaterials(fileList1);
            List<TSFilesEntity> fileList2 = this.systemService.getAttachmentByCode(
                    tBAppraisalReportMaterial.getAttachmentCode(), "certificateScan");
            tBAppraisalReportMaterial.setCertificateScans(fileList2);
        }
        req.setAttribute("tBAppraisalReportMaterialPage", tBAppraisalReportMaterial);
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterialEdit");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterialUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "exportXls")
    public String exportXls(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalReportMaterialEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalReportMaterial,
                request.getParameterMap());
        List<TBAppraisalReportMaterialEntity> tBAppraisalReportMaterials = this.tBAppraisalReportMaterialService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "上报材料信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBAppraisalReportMaterialEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("上报材料信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBAppraisalReportMaterials);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBAppraisalReportMaterialEntity tBAppraisalReportMaterial, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "上报材料信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBAppraisalReportMaterialEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

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
                List<TBAppraisalReportMaterialEntity> listTBAppraisalReportMaterialEntitys = ExcelImportUtil
                        .importExcelByIs(file.getInputStream(), TBAppraisalReportMaterialEntity.class, params);
                for (TBAppraisalReportMaterialEntity tBAppraisalReportMaterial : listTBAppraisalReportMaterialEntitys) {
                    tBAppraisalReportMaterialService.save(tBAppraisalReportMaterial);
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

    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String id = req.getParameter("id");
        String operator = req.getParameter("operator");
        String userId = req.getParameter("userId");
        String departId = req.getParameter("departId");
        if (StringUtil.isNotEmpty(id)) {
            TBAppraisalReportMaterialEntity materia = systemService.get(TBAppraisalReportMaterialEntity.class, id);
            materia.setCheckFlag(ApprovalConstant.APPRSTATUS_SEND);//提交审核
            materia.setCheckUserid(userId);
            materia.setCheckUsername(operator);
            materia.setCheckDepartid(departId);
            materia.setCheckDate(new Date());
            systemService.updateEntitie(materia);
            TSUser user = ResourceUtil.getSessionUserName();
                messageService.sendMessage(materia.getCheckUserid(), "上报材料审查提醒！", "上报材料",
                        "您有新的上报材料审查信息，请到项目管理->成果鉴定->上报材料审查中处理！",
                    user.getId());
            } 
        return j;
    }
    
    /**
     * 成果鉴定会申请审批tab页
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisaMaterialReceiveTab")
    public ModelAndView tBAppraisaMaterialReceiveTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterialList-receiveTab");
    }
    
    /**
     * 机关审核鉴定材料审查
     * 
     * @return
     */
    @RequestMapping(params = "goAppraisaMaterialReceive")
    public ModelAndView goAppraisaMaterialReceive(HttpServletRequest request) {
        //处理审批
        //0：未处理；1：已处理
        String operateStatus = request.getParameter("operateStatus");
        if(StringUtils.isNotEmpty(operateStatus)){
            request.setAttribute("operateStatus", operateStatus);
        }
        return new ModelAndView("com/kingtake/project/reportmaterial/tBAppraisalReportMaterialList");
    }
    
    /**
     * 鉴定会申请列表列表请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForApply")
    public void datagridForApply(TBAppraisalReportMaterialEntity tBAppraisalMaterial, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String operateStatus = request.getParameter("operateStatus");
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalReportMaterialEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMaterial,
                request.getParameterMap());
        if("0".equals(operateStatus)){
           cq.eq("checkFlag", ApprovalConstant.APPRSTATUS_SEND);//已发送
        }else{
           cq.notEq("checkFlag", ApprovalConstant.APPRSTATUS_SEND);//已处理
        }
        cq.eq("checkUserid", user.getId());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBAppraisalReportMaterialService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
}
