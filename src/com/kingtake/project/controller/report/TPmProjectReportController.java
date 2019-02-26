package com.kingtake.project.controller.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ExceptionUtil;
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

import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.office.entity.notice.TONoticeEntity;
import com.kingtake.office.entity.notice.TONoticeProjectRelaEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.entity.report.TPmProjectreportEntity;
import com.kingtake.project.service.member.TPmProjectMemberServiceI;

/**
 * @Title: Controller
 * @Description: 项目组申报依据
 * @author onlineGenerator
 * @date 2015-07-09 17:34:25
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmProjectReportController")
public class TPmProjectReportController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmProjectReportController.class);

    @Autowired
    private TPmProjectMemberServiceI tPmProjectMemberService;
    @Autowired
    private SystemService systemService;
    /**
     * 代码集service
     */
    @Autowired
    private TBCodeTypeServiceI codeTypeService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 项目组成员列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectReport")
    public ModelAndView tPmProjectMember(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String planContractFlag = request.getParameter("planContractFlag");
        if (ProjectConstant.PROJECT_CONTRACT.equals(planContractFlag)) {
            return new ModelAndView("redirect:tPmProjectController.do?goUpdateForResearchGroup&id="+ projectId);
        }
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/report/tPmProjectReportList");
    }

    /**
     * 项目组成员列表 过程管理页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectReportProcess")
    public ModelAndView tPmProjectReportProcess(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
            if (project != null) {
                request.setAttribute("projectName", project.getProjectName());
            }
        }
        return new ModelAndView("com/kingtake/project/report/tPmProjectReportListProcess");
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
    public void datagrid(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String projectId = request.getParameter("projectId");
        List<TPmProjectreportEntity> reportList = new ArrayList<TPmProjectreportEntity>();
        if (StringUtils.isNotEmpty(projectId)) {
            tPmProject = systemService.get(TPmProjectEntity.class, projectId);
            if (StringUtil.isNotEmpty(tPmProject.getAccordingNum())) {
                CriteriaQuery cq = new CriteriaQuery(TOSendReceiveRegEntity.class);
                cq.eq("mergeFileNum", tPmProject.getAccordingNum());
                cq.add();
                List<TOSendReceiveRegEntity> regList = systemService.getListByCriteriaQuery(cq, false);
                if (regList.size() > 0) {
                    TOSendReceiveRegEntity reg = regList.get(0);
                    TPmProjectreportEntity report = new TPmProjectreportEntity();
                    report.setId(reg.getId());
                    report.setFileNum((StringUtils.isEmpty(reg.getFileNumPrefix()) ? "" : reg.getFileNumPrefix())
                            + (StringUtils.isEmpty(reg.getFileNumYear()) ? "" : ("20" + reg.getFileNumYear()))
                            + reg.getFileNum());
                    report.setViewUrl("tOSendReceiveRegController.do?goUpdate");
                    report.setProject(tPmProject);
                    report.setRelatedUserName(reg.getReceiveSign());
                    report.setRelatedTime(reg.getRegisterDate());
                    report.setTitle(reg.getTitle());
                    report.setRelatedType("收发文");
                    reportList.add(report);
                }
            }
            CriteriaQuery pcq = new CriteriaQuery(TONoticeProjectRelaEntity.class);
            pcq.eq("projectId", projectId);
            pcq.add();
            List<TONoticeProjectRelaEntity> prList = systemService.getListByCriteriaQuery(pcq, false);
            for (TONoticeProjectRelaEntity pr : prList) {
                TONoticeEntity notice = systemService.get(TONoticeEntity.class, pr.getNoticeId());
                TPmProjectreportEntity report = new TPmProjectreportEntity();
                report.setTitle(notice.getTitle());
                report.setProject(tPmProject);
                report.setRelatedUserName(notice.getSenderName());
                report.setFileNum(notice.getFileNum());
                report.setRelatedTime(notice.getSendTime());
                report.setId(notice.getId());
                report.setRelatedType("通知公告");
                report.setViewUrl("tONoticeController.do?goUpdate");
                reportList.add(report);
            }
            dataGrid.setResults(reportList);
            dataGrid.setTotal(reportList.size());
        }

        //        dataGrid.setResults(results);

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/member/tPmProjectMemberUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectMemberEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectMember,
                request.getParameterMap());
        List<TPmProjectMemberEntity> tPmProjectMembers = this.tPmProjectMemberService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目组成员");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectMemberEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目组成员列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjectMembers);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "项目组成员");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmProjectMemberEntity.class);
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
                List<TPmProjectMemberEntity> listTPmProjectMemberEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmProjectMemberEntity.class, params);
                for (TPmProjectMemberEntity tPmProjectMember : listTPmProjectMemberEntitys) {
                    tPmProjectMemberService.save(tPmProjectMember);
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

    /* *//**
     * 替换代码集的值
     * 
     * @param member
     */
    /*
     * private void replaceCodeType(TPmProjectMemberEntity member) { String sql =
     * "select d.code_type_id,d.name from t_b_code_detail d join t_b_code_type t " +
     * "on d.code_type_id=t.id where t.code=? and t.code_type=? and d.code=?"; String[] param = new String[] { "SEX",
     * "0", member.getSex() }; List<Map<String, Object>> codeList = this.systemService.findForJdbc(sql, param);
     * if(codeList.size()>0){ String value = (String) codeList.get(0).get("NAME"); member.setSex(value); } param = new
     * String[] { "XWLB", "0", member.getDegree() }; codeList = this.systemService.findForJdbc(sql, param); if
     * (codeList.size() > 0) { String value = (String) codeList.get(0).get("NAME"); member.setDegree(value); } param =
     * new String[] { "PROFESSIONAL", "0", member.getPosition() }; codeList = this.systemService.findForJdbc(sql,
     * param); if (codeList.size() > 0) { String value = (String) codeList.get(0).get("NAME");
     * member.setPosition(value); } param = new String[] { "SFBZ", "0", member.getProjectManager() }; codeList =
     * this.systemService.findForJdbc(sql, param); if (codeList.size() > 0) { String value = (String)
     * codeList.get(0).get("NAME"); member.setProjectManager(value); } }
     */
}
