package com.kingtake.project.controller.checkreport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
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

import com.kingtake.project.entity.checkreport.TPmCheckReportEntity;
import com.kingtake.project.service.checkreport.TPmCheckReportServiceI;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2016-04-13 16:20:02
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmCheckReportController")
public class TPmCheckReportController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmCheckReportController.class);

    @Autowired
    private TPmCheckReportServiceI tPmCheckReportService;
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
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmCheckReport")
    public ModelAndView tPmCheckReport(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/checkreport/tPmCheckReportList");
    }

    /**
     * ???????????????????????? ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmCheckReportInfo")
    public ModelAndView tPmCheckReportInfo(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/checkreport/tPmCheckReportListInfo");
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String projectId = request.getParameter("projectId");
        CriteriaQuery cq = new CriteriaQuery(TPmCheckReportEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmCheckReport,
                request.getParameterMap());
        if (StringUtils.isNotEmpty(projectId)) {
            cq.eq("tpId", projectId);
        }
        cq.add();
        this.tPmCheckReportService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmCheckReport = systemService.getEntity(TPmCheckReportEntity.class, tPmCheckReport.getId());
        message = "??????????????????????????????";
        try {
            tPmCheckReportService.deleteAddAttach(tPmCheckReport);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmCheckReportEntity tPmCheckReport = systemService.getEntity(TPmCheckReportEntity.class, id);
                tPmCheckReportService.deleteAddAttach(tPmCheckReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {

        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            if (StringUtils.isEmpty(tPmCheckReport.getId())) {
                tPmCheckReport.setCheckStatus("0");
                tPmCheckReportService.save(tPmCheckReport);
            } else {
                TPmCheckReportEntity t = tPmCheckReportService.get(TPmCheckReportEntity.class, tPmCheckReport.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmCheckReport, t);
                tPmCheckReportService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        j.setObj(tPmCheckReport);
        return j;
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmCheckReportEntity tPmCheckReport, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmCheckReport.getId())) {
            tPmCheckReport = tPmCheckReportService.getEntity(TPmCheckReportEntity.class, tPmCheckReport.getId());
            req.setAttribute("tPmCheckReportPage", tPmCheckReport);
        }
        return new ModelAndView("com/kingtake/project/checkreport/tPmCheckReport-add");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmCheckReportEntity tPmCheckReport, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmCheckReport.getId())) {
            tPmCheckReport = tPmCheckReportService.getEntity(TPmCheckReportEntity.class, tPmCheckReport.getId());
        } else {
            String projectId = req.getParameter("projectId");
            tPmCheckReport.setTpId(projectId);
        }
        if(StringUtils.isEmpty(tPmCheckReport.getAttachmentCode())){
            tPmCheckReport.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmCheckReport.getAttachmentCode(), "");
            tPmCheckReport.setCertificates(certificates);
        }
        req.setAttribute("tPmCheckReportPage", tPmCheckReport);
        return new ModelAndView("com/kingtake/project/checkreport/tPmCheckReport-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/checkreport/tPmCheckReportUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmCheckReportEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmCheckReport,
                request.getParameterMap());
        List<TPmCheckReportEntity> tPmCheckReports = this.tPmCheckReportService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmCheckReportEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmCheckReports);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmCheckReportEntity.class);
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
            MultipartFile file = entity.getValue();// ????????????????????????
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TPmCheckReportEntity> listTPmCheckReportEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmCheckReportEntity.class, params);
                for (TPmCheckReportEntity tPmCheckReport : listTPmCheckReportEntitys) {
                    tPmCheckReportService.save(tPmCheckReport);
                }
                j.setMsg("?????????????????????");
            } catch (Exception e) {
                j.setMsg("?????????????????????");
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

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String opt = request.getParameter("opt");
        String userId = request.getParameter("userId");
        String realname = request.getParameter("realname");
        String deptId = request.getParameter("deptId");
        tPmCheckReport = systemService.getEntity(TPmCheckReportEntity.class, tPmCheckReport.getId());
        try {
            if ("submit".equals(opt)) {
                if ("0".equals(tPmCheckReport.getCheckStatus())) {
                    tPmCheckReport.setCheckStatus("1");//auditStatus ???1 ?????????
                } else if ("3".equals(tPmCheckReport.getCheckStatus())) {
                    tPmCheckReport.setCheckStatus("4");//auditStatus ???4 ????????????
                }
                tPmCheckReport.setCheckUserid(userId);
                tPmCheckReport.setCheckUsername(realname);
                tPmCheckReport.setCheckUserDeptId(deptId);
                message = "??????????????????????????????";
                this.tPmCheckReportService.saveOrUpdate(tPmCheckReport);
            } else if ("pass".equals(opt)) {
                tPmCheckReport.setCheckStatus("2");//auditStatus ???2 ??????
                this.tPmCheckReportService.updateEntitie(tPmCheckReport);
                message = "??????????????????????????????";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????? ?????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "checkReportTab")
    public ModelAndView checkReportTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/checkreport/checkReport-tab");
    }

    /**
     * ???????????? ?????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "checkReportListForDepartment")
    public ModelAndView checkReportListForDepartment(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        String type = request.getParameter("type");
        if ("1".equals(type)) {
            return new ModelAndView("com/kingtake/project/checkreport/checkReportListAuditForDepartment");
        } else {
            return new ModelAndView("com/kingtake/project/checkreport/checkReportListAuditedForDepartment");
        }
    }

    /**
     * ???????????? ?????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "checkReportListDatagrid")
    public void checkReportListDatagrid(TPmCheckReportEntity tPmCheckReport, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String type = request.getParameter("type");
        CriteriaQuery cq = new CriteriaQuery(TPmCheckReportEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmCheckReport,
                request.getParameterMap());
        cq.eq("checkUserid", user.getId());
        cq.eq("checkUserDeptId", user.getCurrentDepart().getId());
        if ("1".equals(type)) {
            cq.or(Restrictions.eq("checkStatus", "1"), Restrictions.eq("checkStatus", "4"));
        } else {
            cq.or(Restrictions.eq("checkStatus", "2"), Restrictions.eq("checkStatus", "3"));
        }
        cq.add();
        this.tPmCheckReportService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TPmCheckReportEntity apply = this.systemService.get(TPmCheckReportEntity.class, id);
            req.setAttribute("apply", apply);
        }
        return new ModelAndView("com/kingtake/project/checkreport/proposePage");
    }

    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "???????????????";
        try {
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            if (StringUtil.isNotEmpty(id)) {
                TPmCheckReportEntity apply = systemService.get(TPmCheckReportEntity.class, id);
                apply.setCheckSuggest(msgText);
                apply.setCheckStatus("3");
                systemService.updateEntitie(apply);
            }
        } catch (Exception e) {
            message = "???????????????";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

}
