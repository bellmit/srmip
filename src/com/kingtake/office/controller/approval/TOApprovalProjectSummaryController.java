package com.kingtake.office.controller.approval;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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

import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.office.entity.approval.TOApprovalEntity;
import com.kingtake.office.entity.approval.TOApprovalProjectSummaryEntity;
import com.kingtake.office.service.approval.TOApprovalProjectSummaryServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2015-08-25 10:37:43
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOApprovalProjectSummaryController")
public class TOApprovalProjectSummaryController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOApprovalProjectSummaryController.class);

    @Autowired
    private TOApprovalProjectSummaryServiceI tOApprovalProjectSummaryService;
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
     * ??????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tOApprovalProjectSummary")
    public ModelAndView tOApprovalProjectSummary(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/approval/tOApprovalProjectSummaryList");
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
    public void datagrid(TOApprovalProjectSummaryEntity tOApprovalProjectSummary, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOApprovalProjectSummaryEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOApprovalProjectSummary,
                request.getParameterMap());
        try {
            //???????????????????????????
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tOApprovalProjectSummaryService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ???????????????????????????????????????
     * 
     * @param project
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForApproval")
    public void datagridForApproval(TPmProjectEntity project, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, project, request.getParameterMap());
        try {
            //???????????????????????????
            String[] pids;
            String projectIds = request.getParameter("projectIds");
            if (StringUtil.isNotEmpty(projectIds)) {
                pids = projectIds.split(",");
            } else {
                pids = new String[0];
            }
            cq.in("id", pids);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOApprovalProjectSummaryEntity tOApprovalProjectSummary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOApprovalProjectSummary = systemService.getEntity(TOApprovalProjectSummaryEntity.class,
                tOApprovalProjectSummary.getId());
        message = "?????????????????????????????????";
        try {
            tOApprovalProjectSummaryService.delete(tOApprovalProjectSummary);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TOApprovalProjectSummaryEntity tOApprovalProjectSummary = systemService.getEntity(
                        TOApprovalProjectSummaryEntity.class, id);
                tOApprovalProjectSummaryService.delete(tOApprovalProjectSummary);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOApprovalEntity tOApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            systemService.save(tOApproval);
            String projectIds = request.getParameter("projectIds");
            if (StringUtil.isNotEmpty(projectIds)) {
                String[] pids = projectIds.split(",");
                for (String pid : pids) {
                    TOApprovalProjectSummaryEntity tOApprovalProjectSummary = new TOApprovalProjectSummaryEntity();
                    TPmProjectEntity project = systemService.get(TPmProjectEntity.class, pid);
                    tOApprovalProjectSummary.setApprovalId(tOApproval.getId());
                    tOApprovalProjectSummary.setAccordingNum(project.getAccordingNum());
                    tOApprovalProjectSummary.setAllFee(project.getAllFee());
                    tOApprovalProjectSummary.setBeginDate(project.getBeginDate());
                    tOApprovalProjectSummary.setDevDepart(project.getDevDepart());
                    tOApprovalProjectSummary.setEndDate(project.getEndDate());
                    tOApprovalProjectSummary.setFeeType(project.getFeeType());
                    tOApprovalProjectSummary.setManageDepart(project.getManageDepart());
                    tOApprovalProjectSummary.setProjectId(project.getId());
                    tOApprovalProjectSummary.setProjectManager(project.getProjectManager());
                    tOApprovalProjectSummary.setProjectManagerId(project.getProjectManagerId());
                    tOApprovalProjectSummary.setProjectName(project.getProjectName());
                    tOApprovalProjectSummary.setProjectNo(project.getProjectNo());
                    tOApprovalProjectSummary.setProjectSource(project.getProjectSource());
                    tOApprovalProjectSummary.setProjectType(project.getProjectType());
                    systemService.save(tOApprovalProjectSummary);
                }
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOApproval);
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOApprovalProjectSummaryEntity tOApprovalProjectSummary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        TOApprovalProjectSummaryEntity t = tOApprovalProjectSummaryService.get(TOApprovalProjectSummaryEntity.class,
                tOApprovalProjectSummary.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOApprovalProjectSummary, t);
            tOApprovalProjectSummaryService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????tab???????????????????????????????????????
     * 
     * @param req
     * @param projectIds
     * @return
     */
    @RequestMapping(params = "goAddTab")
    public ModelAndView goAddTab(HttpServletRequest req, String projectIds) {
        if (StringUtil.isNotEmpty(projectIds)) {
            req.setAttribute("projectIds", projectIds);
        }
        return new ModelAndView("com/kingtake/office/approval/tOApprovalProjectSummary-addTab");
    }

    @RequestMapping(params = "gotPmProjectListForApproval")
    public ModelAndView gotPmProjectListForApproval(HttpServletRequest req, String projectIds) {
        if (StringUtil.isNotEmpty(projectIds)) {
            req.setAttribute("projectIds", projectIds);
        }
        return new ModelAndView("com/kingtake/project/manage/tPmProjectListForApproval");
    }

    /**
     * ?????????????????????????????????????????????
     * 
     * @param req
     * @param approvalId
     * @return
     */
    @RequestMapping(params = "goBusinessForApproval")
    public ModelAndView goBusinessForApproval(HttpServletRequest req, String approvalId) {
        if (StringUtil.isNotEmpty(approvalId)) {
            CriteriaQuery cq = new CriteriaQuery(TOApprovalProjectSummaryEntity.class);
            cq.eq("approvalId", approvalId);
            cq.add();
            List<TOApprovalProjectSummaryEntity> list = systemService.getListByCriteriaQuery(cq, false);
            StringBuffer sb = new StringBuffer();
            for (TOApprovalProjectSummaryEntity pse : list) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(pse.getProjectId());
            }
            req.setAttribute("projectIds", sb.toString());
        }
        return new ModelAndView("com/kingtake/project/manage/tPmProjectListForApproval");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOApprovalEntity tOApproval, HttpServletRequest req, String projectIds) {
        TSUser user = ResourceUtil.getSessionUserName();
        Calendar time = Calendar.getInstance();
        tOApproval.setApprovalYear(String.valueOf(time.get(Calendar.YEAR) - 2000));
        tOApproval.setArchiveFlag(ReceiveBillConstant.BILL_FLOWING);
        tOApproval.setContactId(user.getId());
        tOApproval.setContactName(user.getRealName());
        tOApproval.setContactPhone(user.getMobilePhone());
        tOApproval.setSignUnitid(user.getCurrentDepart().getId());
        tOApproval.setSignUnitname(user.getCurrentDepart().getDepartname());
        tOApproval.setUndertakeUnitId(user.getCurrentDepart().getId());
        tOApproval.setUndertakeUnitName(user.getCurrentDepart().getDepartname());
        tOApproval.setBusinessTablename("t_o_approval_project_summary");
        tOApproval.setCreateDate(new Date());
        if (StringUtil.isNotEmpty(tOApproval.getId())) {
            tOApproval = systemService.getEntity(TOApprovalEntity.class, tOApproval.getId());
        }
        req.setAttribute("tOApprovalPage", tOApproval);
        if (StringUtil.isNotEmpty(projectIds)) {
            req.setAttribute("projectIds", projectIds);
        }
        return new ModelAndView("com/kingtake/office/approval/tOApproval-addForProjectSummary");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOApprovalProjectSummaryEntity tOApprovalProjectSummary, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOApprovalProjectSummary.getId())) {
            tOApprovalProjectSummary = tOApprovalProjectSummaryService.getEntity(TOApprovalProjectSummaryEntity.class,
                    tOApprovalProjectSummary.getId());
            req.setAttribute("tOApprovalProjectSummaryPage", tOApprovalProjectSummary);
        }
        return new ModelAndView("com/kingtake/office/approval/tOApprovalProjectSummary-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/approval/tOApprovalProjectSummaryUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOApprovalProjectSummaryEntity tOApprovalProjectSummary, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOApprovalProjectSummaryEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOApprovalProjectSummary,
                request.getParameterMap());
        List<TOApprovalProjectSummaryEntity> tOApprovalProjectSummarys = this.tOApprovalProjectSummaryService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TOApprovalProjectSummaryEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tOApprovalProjectSummarys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOApprovalProjectSummaryEntity tOApprovalProjectSummary, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOApprovalProjectSummaryEntity.class);
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
                List<TOApprovalProjectSummaryEntity> listTOApprovalProjectSummaryEntitys = ExcelImportUtil
                        .importExcelByIs(file.getInputStream(), TOApprovalProjectSummaryEntity.class, params);
                for (TOApprovalProjectSummaryEntity tOApprovalProjectSummary : listTOApprovalProjectSummaryEntitys) {
                    tOApprovalProjectSummaryService.save(tOApprovalProjectSummary);
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
}
