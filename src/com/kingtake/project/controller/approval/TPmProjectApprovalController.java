package com.kingtake.project.controller.approval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.approval.TPmProjectApprovalEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.service.approval.TPmProjectApprovalServiceI;

/**
 * @Title: Controller
 * @Description: ????????????
 * @author onlineGenerator
 * @date 2015-07-12 15:27:28
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmProjectApprovalController")
public class TPmProjectApprovalController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmProjectApprovalController.class);

    @Autowired
    private TPmProjectApprovalServiceI tPmProjectApprovalService;
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
     * ?????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectApproval")
    public ModelAndView tPmProjectApproval(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApprovalList");
    }

    /**
     * ??????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "errorJsp")
    public ModelAndView errorJsp(HttpServletRequest request) {
        return new ModelAndView("common/404.jsp");
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
    public void datagrid(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectApprovalEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectApproval,
                request.getParameterMap());
        try {
            //???????????????????????????
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmProjectApprovalService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProjectApproval = systemService.getEntity(TPmProjectApprovalEntity.class, tPmProjectApproval.getId());
        message = "????????????????????????";
        try {
            tPmProjectApprovalService.deleteAddAttach(tPmProjectApproval);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmProjectApprovalEntity tPmProjectApproval = systemService.getEntity(TPmProjectApprovalEntity.class,
                        id);
                tPmProjectApprovalService.deleteAddAttach(tPmProjectApproval);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            if (StringUtils.isNotEmpty(tPmProjectApproval.getTpId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmProjectApproval.getTpId());
                tPmProjectApproval.setProject(project);
            }
            tPmProjectApprovalService.save(tPmProjectApproval);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        TPmProjectApprovalEntity tmp = new TPmProjectApprovalEntity();
        tmp.setId(tPmProjectApproval.getId());
        j.setObj(tmp);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(tPmProjectApproval.getId())) {
                TPmProjectApprovalEntity t = tPmProjectApprovalService.get(TPmProjectApprovalEntity.class,
                        tPmProjectApproval.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmProjectApproval, t);
                if (StringUtils.isNotEmpty(tPmProjectApproval.getTpId())) {
                    TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class,
                            tPmProjectApproval.getTpId());
                    t.setProject(project);
                }
                tPmProjectApprovalService.saveOrUpdate(t);
                message = "????????????????????????";
            } else {
                if (StringUtils.isNotEmpty(tPmProjectApproval.getTpId())) {
                    TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class,
                            tPmProjectApproval.getTpId());
                    tPmProjectApproval.setProject(project);
                }
                tPmProjectApprovalService.save(tPmProjectApproval);
                message = "????????????????????????";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????/????????????";
            throw new BusinessException(e.getMessage());
        }
        TPmProjectApprovalEntity tmp = new TPmProjectApprovalEntity();
        tmp.setId(tPmProjectApproval.getId());
        j.setObj(tmp);
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmProjectApproval.getId())) {
            tPmProjectApproval = tPmProjectApprovalService.getEntity(TPmProjectApprovalEntity.class,
                    tPmProjectApproval.getId());
            req.setAttribute("tPmProjectApprovalPage", tPmProjectApproval);
        }
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApproval-add");
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmProjectApproval.getId())) {
            tPmProjectApproval = tPmProjectApprovalService.getEntity(TPmProjectApprovalEntity.class,
                    tPmProjectApproval.getId());
            req.setAttribute("tPmProjectApprovalPage", tPmProjectApproval);
        }
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApproval-update");
    }

    /**
     * ??????????????????????????????-???????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateForResearchGroup")
    public ModelAndView goUpdateForResearchGroup(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmProjectApproval.getId())) {
            tPmProjectApproval = tPmProjectApprovalService.getEntity(TPmProjectApprovalEntity.class,
                    tPmProjectApproval.getId());
            req.setAttribute("tPmProjectApprovalPage", tPmProjectApproval);
        }
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApproval-updateForResearchGroup");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApprovalUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectApprovalEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectApproval,
                request.getParameterMap());
        List<TPmProjectApprovalEntity> tPmProjectApprovals = this.tPmProjectApprovalService.getListByCriteriaQuery(cq,
                false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectApprovalEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("??????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjectApprovals);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmProjectApprovalEntity.class);
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
                List<TPmProjectApprovalEntity> listTPmProjectApprovalEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmProjectApprovalEntity.class, params);
                for (TPmProjectApprovalEntity tPmProjectApproval : listTPmProjectApprovalEntitys) {
                    tPmProjectApprovalService.save(tPmProjectApproval);
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
     * ??????????????????????????????
     */
    @RequestMapping(params = "goApprovalMgr")
    public String goApprovalMgr(HttpServletRequest request, HttpServletResponse response) {
        String menu = request.getParameter("menu");
        if (StringUtils.isNotEmpty(menu)) {
            request.setAttribute("menu", menu);
        }
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return "com/kingtake/project/approval/ApprovalMgr";
    }

    /**
     * ?????????????????????????????????????????????.
     * 
     * @return
     */
    @RequestMapping(params = "approvalUpdateForResearchGroup")
    public ModelAndView goApprovalUpdateForResearchGroup(TPmProjectApprovalEntity tPmProjectApproval,
            HttpServletRequest request) {
        if (StringUtil.isNotEmpty(tPmProjectApproval.getId())) {
            tPmProjectApproval = tPmProjectApprovalService.getEntity(TPmProjectApprovalEntity.class,
                    tPmProjectApproval.getId());
            request.setAttribute("tPmProjectApprovalPage", tPmProjectApproval);
        }
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            CriteriaQuery cq = new CriteriaQuery(TPmProjectApprovalEntity.class);
            cq.eq("project.id", projectId);
            cq.add();
            List<TPmProjectApprovalEntity> approvalList = tPmProjectApprovalService.getListByCriteriaQuery(cq, false);
            if (approvalList.size() > 0) {
                request.setAttribute("tPmProjectApprovalPage", approvalList.get(0));
            } else {
                TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, projectId);
                TPmProjectApprovalEntity approvalEntity = new TPmProjectApprovalEntity();
                approvalEntity.setProject(proj);
                if (StringUtil.isNotEmpty(proj.getProjectSource())) {
                    approvalEntity.setProjectSrc(proj.getProjectSource());
                }
                request.setAttribute("tPmProjectApprovalPage", approvalEntity);

            }
            TPmProjectEntity tPmProject = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("tPmProjectPage", tPmProject);
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectMemberEntity.class);
            mcq.eq("project.id", projectId);
            mcq.add();
            List<TPmProjectMemberEntity> memberList = systemService.getListByCriteriaQuery(mcq, false);
            request.setAttribute("memberList", memberList);
        }
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApproval-updateForResearchGroup");
    }

    /**
     * ????????????
     * 
     * @return
     */
    @RequestMapping(params = "getApprovalMenu")
    @ResponseBody
    public String getApprovalMenu(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String menu = request.getParameter("menu");
        String planContractFlag = request.getParameter("planContractFlag");
        String menuStr = "";
        String departFlag = request.getParameter("departFlag");
        if (StringUtils.isNotEmpty(menu)) {
            switch (menu) {
            case "1":
                menuStr = initApprovalList(projectId, planContractFlag, departFlag);
                break;
            case "2":
                menuStr = initPlanist(projectId, departFlag);
                break;
            case "3":
                menuStr = initExecList(projectId, departFlag);
                break;
            case "4":
                menuStr = initContralList(projectId, departFlag);
                break;
            case "5":
                menuStr = initIdentifyList(projectId, departFlag);
                break;
            case "6":
                menuStr = initResultList(projectId, departFlag);
                break;
            case "7":
                menuStr = initPatentList(projectId, departFlag);
                break;
            /**
             * @author zhangls 2015-08-10 ????????????????????????????????????????????????????????????????????????????????????
             */
            /*
             * case "8": menuStr = initQualityList(projectId); break;
             */
            }
        }
        return menuStr;
    }

    /**
     * ???????????????????????????,??????????????????????????????????????????????????????????????????????????? ????????????initFunctionList
     * 
     * @return
     */
    private String initApprovalList(String projectId, String planContractFlag, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup&id=" + projectId);//????????????
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//????????????-1
        urlList.add("tPmProjectReportController.do?tPmProjectReport");//????????????-2
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//???????????????-3
        urlList.add("tPmDeclareController.do?tPmDeclareForResearchGroup");//???????????????-4

        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//????????????-5
        urlList.add("tPmAbatePayfirstController.do?tPmAbatePayfirst");//????????????-6

        urlList.add("tPmPriceController.do?tPmPrice");//??????-7
        urlList.add("tPmOpenSubjectController.do?openSubjectUpdateForResearchGroup");//????????????-8

        urlList.add("tPmIncomeContractApprController.do?tPmIncomeContractAppr");//??????????????????-9

        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'1'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }
        if (planContractFlag.equals(ProjectConstant.PROJECT_PLAN)) {
            if (checkAuth(urlList.get(2), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(2) + "' target='contentFrame'><span>????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(3), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(3)
                        + "' target='contentFrame'><span>?????????????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(4), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>???????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(6), departFlag)) {
                sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(6)
                        + "' target='contentFrame'><span>????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(9), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(9)
                        + "' target='contentFrame'><span>??????????????????</span></a></div>");
            }
        } else {
            if (checkAuth(urlList.get(3), departFlag)) {
                sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(3)
                        + "' target='contentFrame'><span>?????????????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(7), departFlag)) {
                sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(7)
                        + "' target='contentFrame'><span>??????</span></a></div>");
            }
            if (checkAuth(urlList.get(8), departFlag)) {
                sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(8)
                        + "' target='contentFrame'><span>????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(6), departFlag)) {
                sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(6)
                        + "' target='contentFrame'><span>????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(9), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(9)
                        + "' target='contentFrame'><span>??????????????????</span></a></div>");
            }
        }
        return sb.toString();
    }

    /**
     * ???????????????????????????,???????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    private String initPlanist(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmTaskController.do?taskUpdateForResearchGroup");//?????????/??????????????????-0
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????-1
        urlList.add("tPmIncomeNodeController.do?tPmIncomeNode");//??????????????????-2
        urlList.add("tPmPayNodeController.do?tPmPayNode");//??????????????????-3
        urlList.add("tPmProjectFundsApprController.do?tPmProjectFundsAppr");//????????????-4
        //        urlList.add("tPmIncomeContractApprController.do?tPmIncomeContractAppr");//??????????????????-5
        //        urlList.add("tPmOutcomeContractApprController.do?tPmOutcomeContractAppr");//??????????????????-6
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//????????????-7
        urlList.add("tPmOpenSubjectController.do?openSubjectUpdateForResearchGroup");//?????????????????????/??????-8
        urlList.add("tPmTaskController.do?taskUpdateForResearchGroup");//?????????/??????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        urlList.add("tPmIncomeNodeController.do?tPmIncomeNode");//??????????????????
        urlList.add("tPmPayNodeController.do?tPmPayNode");//??????????????????
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//????????????
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//??????????????????
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//????????????
        urlList.add("tPmOpenSubjectController.do?openSubjectUpdateForResearchGroup");//?????????????????????/??????
        urlList.add("tPmContractNodeController.do?goIncomeContractNodeList");//15-??????????????????????????????
        urlList.add("tPmContractNodeController.do?goPayContractNodeList");//16-??????????????????????????????
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div data-options=\"id:'1'\"><a subhref='" + urlList.get(0)
                    + "' target='contentFrame'><span>?????????/??????????????????</span></a></div>");
        }
        /*
         * if (checkAuth(urlList.get(1))) { sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1) +
         * "' target='contentFrame'><span>??????????????????</span></a></div>"); }
         */
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(3)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(4), departFlag)) {
            sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(4)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(15), departFlag)) {
            sb.append("<div data-options=\"id:'15'\"><a subhref='" + urlList.get(15)
                    + "' target='contentFrame'><span>??????????????????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(16), departFlag)) {
            sb.append("<div data-options=\"id:'16'\"><a subhref='" + urlList.get(16)
                    + "' target='contentFrame'><span>??????????????????????????????</span></a></div>");
        }
        /*
         * sb.append("<div><span>??????????????????</span><div>"); if (checkAuth(urlList.get(5))) {
         * sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(5) +
         * "' target='contentFrame'><span>??????????????????</span></a></div>"); } if (checkAuth(urlList.get(5))) {
         * sb.append("<div data-options=\"id:'6'\"><a subhref='" + urlList.get(6) +
         * "' target='contentFrame'><span>??????????????????</span></a></div>"); } sb.append("</div></div>");
         */
        /*
         * if (checkAuth(urlList.get(6))) { sb.append("<div data-options=\"id:'7'\"><a subhref='" + urlList.get(7) +
         * "' target='contentFrame'><span>????????????</span></a></div>"); }
         */
        return sb.toString();
    }

    /**
     * ???????????????????????????,???????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    private String initExecList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();

        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????????????????-0
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//????????????????????????-1
        urlList.add("tPmContractPriceCoverController.do?tPmContractPriceCover");//?????????????????????2
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//???????????????????????????-3
        urlList.add("tPmOutcomeContractApprController.do?tPmOutcomeContractAppr");//??????????????????-4

        //        if (checkAuth(urlList.get(0))) {
        //            sb.append("<div data-options=\"id:'1'\" style='height:20px'><a subhref='" + urlList.get(0)
        //                    + "' target='contentFrame'><span>????????????????????????</span></a></div>");
        //        }
        /**
         * @author zhangls ???????????????????????????
         */
        /*
         * if (checkAuth(urlList.get(1))) { sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1) +
         * "' target='contentFrame'><span>????????????????????????</span></a></div>"); }
         */
        /*
         * sb.append("<div><span>?????????????????????</span><div>"); if (checkAuth(urlList.get(4))) {
         * sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(4) + "&type=" +
         * ProjectConstant.CONTRACT_PRODUCE + "' target='contentFrame'><span>???????????????????????????</span></a></div>"); } if
         * (checkAuth(urlList.get(5))) { sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(5) +
         * "&type=" + ProjectConstant.CONTRACT_DEVELOP + "' target='contentFrame'><span>?????????????????????</span></a></div>"); } if
         * (checkAuth(urlList.get(6))) { sb.append("<div data-options=\"id:'6'\"><a subhref='" + urlList.get(6) +
         * "&type=" + ProjectConstant.CONTRACT_BUY + "' target='contentFrame'><span>?????????????????????</span></a></div>"); }
         */
        /* sb.append("</div></div>"); */
        if (checkAuth(urlList.get(4), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }

        /**
         * @author zhangls ??????????????????????????????????????????
         */
        /*
         * if (checkAuth(urlList.get(3))) { sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(3) +
         * "' target='contentFrame'><span>???????????????????????????</span></a></div>"); }
         */
        return sb.toString();
    }

    /**
     * ???????????????????????????,???????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    private String initContralList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//????????????????????????
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//????????????
        urlList.add("tBPmChangeProjectnameController.do?tBPmChangeProjectname");//????????????????????????
        urlList.add("tBPmChangeProjectmanagerController.do?tBPmChangeProjectmanager");//???????????????????????????
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div><span>??????????????????</span><div>");
            if (checkAuth(urlList.get(3), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(3)
                        + "' target='contentFrame'><span>????????????????????????</span></a></div>");
            }
            if (checkAuth(urlList.get(4), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(4)
                        + "' target='contentFrame'><span>???????????????????????????</span></a></div>");
            }
            sb.append("</div></div>");
        }
        /**
         * @author zhangls ???????????????????????????
         */
        /*
         * if (checkAuth(urlList.get(1))) { sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1) +
         * "' target='contentFrame'><span>????????????????????????</span></a></div>"); }
         */
        /**
         * @author zhangls ??????????????????????????????
         */
        /*
         * if (checkAuth(urlList.get(2))) { sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(2) +
         * "' target='contentFrame'><span>????????????</span></a></div>"); }
         */
        return sb.toString();
    }

    /**
     * ???????????????????????????,???????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    private String initIdentifyList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        /*
         * if (checkAuth(urlList.get(0), departFlag)) {
         * sb.append("<div data-options=\"id:'1', disabled:'true'\"><a subhref='" + urlList.get(0) +
         * "' target='contentFrame' ><span>??????????????????</span></a></div>"); }
         */
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'2', disabled:'true'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'3', disabled:'true'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div data-options=\"id:'4', disabled:'true'\"><a subhref='" + urlList.get(3)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * ???????????????????????????,???????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    private String initResultList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        //urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        urlList.add("tBLearningThesisController.do?tBLearningThesis");//??????????????????
        urlList.add("tBLearningBookController.do?tBLearningBook");//??????????????????
        urlList.add("tBLearningTeamController.do?tBLearningTeam");//??????????????????
        urlList.add("tBLearningActivityController.do?tBLearningActivity");//??????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//?????????????????????
        //urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup&projectId=" + projectId);//????????????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????????????????
        urlList.add("tBAppraisalProjectController.do?tBAppraisalProject");//????????????
        urlList.add("tBResultRewardController.do?tBResultReward");//????????????
        urlList.add("tBResultSpreadController.do?tBResultSpread");//????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????

        // ??????????????????
        sb.append("<div><span>??????????????????</span><div>");
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(0) + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(1) + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(2) + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(3) + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        //        if (checkAuth(urlList.get(4), departFlag)) {
        //            sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>?????????????????????</span></a></div>");
        //        }
        sb.append("</div></div>");

        // ????????????????????????
        sb.append("<div data-options=\"disabled:'true'\"><span>????????????????????????</span><div>");
        if (checkAuth(urlList.get(5), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(5) + "' target='contentFrame'><span>????????????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(6), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(6) + "' target='contentFrame'><span>????????????????????????</span></a></div>");
        }
        sb.append("</div></div>");

        // ??????????????????
        if (checkAuth(urlList.get(7), departFlag)) {
            sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(7)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }

        // ??????????????????
        if (checkAuth(urlList.get(8), departFlag)) {
            sb.append("<div data-options=\"id:'4',\"><a subhref='" + urlList.get(8)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }

        // ??????????????????
        if (checkAuth(urlList.get(9), departFlag)) {
            sb.append("<div data-options=\"id:'5', \"><a subhref='" + urlList.get(9)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }

        // ??????????????????
        if (checkAuth(urlList.get(10), departFlag)) {
            sb.append("<div data-options=\"id:'6', disabled:'true'\"><a subhref='" + urlList.get(10)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * ???????????????????????????,???????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    private String initPatentList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//????????????????????????
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div data-options=\"id:'1', disabled:'true'\"><a subhref='" + urlList.get(0)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'2', disabled:'true'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'3', disabled:'true'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        sb.append("<div data-options=\"disabled:'true'\"><span>????????????????????????</span><div>");
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(3) + "' target='contentFrame'><span>????????????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(4), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>????????????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(5), departFlag)) {
            sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(5)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * ???????????????????????????,???????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    private String initQualityList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//??????????????????
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//??????????????????
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//????????????
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div data-options=\"id:'1'\"><a subhref='" + urlList.get(0)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>??????????????????</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>????????????</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * ?????????????????????.
     * 
     * @param url
     * @return
     */
    private boolean checkAuth(String url, String departFlag) {
        boolean hasAuth = false;
        if (departFlag.equals(SrmipConstants.YES)) {
            hasAuth = true;
        } else {
            int idx = url.indexOf("&");
            if (idx != -1) {
                url = url.substring(0, idx);
            }
            TSUser user = ResourceUtil.getSessionUserName();
            String sql = "select count(1) as count from t_s_base_user u join t_s_role_user ru on u.id = ru.userid join t_s_role r on ru.roleid = r.id join t_s_role_function rf on rf.roleid = r.id join t_s_function f on rf.functionid = f.id where u.username = ? and f.functionurl = ?";
            List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql,
                    new Object[] { user.getUserName(), url });
            if (resultList != null && resultList.size() > 0) {
                Map<String, Object> map = resultList.get(0);
                BigDecimal big = (BigDecimal) map.get("count");
                if (big.intValue() > 0) {
                    hasAuth = true;
                }
            }
        }
        return hasAuth;
    }
}
