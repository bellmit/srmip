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
 * @Description: 论证报告
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
     * 论证报告列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectApproval")
    public ModelAndView tPmProjectApproval(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApprovalList");
    }

    /**
     * 链接为空时 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "errorJsp")
    public ModelAndView errorJsp(HttpServletRequest request) {
        return new ModelAndView("common/404.jsp");
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
    public void datagrid(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectApprovalEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectApproval,
                request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmProjectApprovalService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除论证报告
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProjectApproval = systemService.getEntity(TPmProjectApprovalEntity.class, tPmProjectApproval.getId());
        message = "论证报告删除成功";
        try {
            tPmProjectApprovalService.deleteAddAttach(tPmProjectApproval);
        } catch (Exception e) {
            e.printStackTrace();
            message = "论证报告删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除论证报告
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "论证报告删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmProjectApprovalEntity tPmProjectApproval = systemService.getEntity(TPmProjectApprovalEntity.class,
                        id);
                tPmProjectApprovalService.deleteAddAttach(tPmProjectApproval);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "论证报告删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加论证报告
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "论证报告添加成功";
        try {
            if (StringUtils.isNotEmpty(tPmProjectApproval.getTpId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmProjectApproval.getTpId());
                tPmProjectApproval.setProject(project);
            }
            tPmProjectApprovalService.save(tPmProjectApproval);
        } catch (Exception e) {
            e.printStackTrace();
            message = "论证报告添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        TPmProjectApprovalEntity tmp = new TPmProjectApprovalEntity();
        tmp.setId(tPmProjectApproval.getId());
        j.setObj(tmp);
        return j;
    }

    /**
     * 更新论证报告
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
                message = "立项论证更新成功";
            } else {
                if (StringUtils.isNotEmpty(tPmProjectApproval.getTpId())) {
                    TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class,
                            tPmProjectApproval.getTpId());
                    tPmProjectApproval.setProject(project);
                }
                tPmProjectApprovalService.save(tPmProjectApproval);
                message = "立项论证新增成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "立项论证新增/更新失败";
            throw new BusinessException(e.getMessage());
        }
        TPmProjectApprovalEntity tmp = new TPmProjectApprovalEntity();
        tmp.setId(tPmProjectApproval.getId());
        j.setObj(tmp);
        j.setMsg(message);
        return j;
    }

    /**
     * 论证报告新增页面跳转
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
     * 论证报告编辑页面跳转
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
     * 论证报告编辑页面跳转-课题组界面
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
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/approval/tPmProjectApprovalUpload");
    }

    /**
     * 导出excel
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
        modelMap.put(NormalExcelConstants.FILE_NAME, "论证报告");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectApprovalEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("论证报告列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjectApprovals);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmProjectApprovalEntity tPmProjectApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "论证报告");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
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
            MultipartFile file = entity.getValue();// 获取上传文件对象
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

    /**
     * 跳转到立项管理的界面
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
     * 跳转到课题组的立项论证编辑界面.
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
     * 获取权限
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
             * @author zhangls 2015-08-10 将质量管理模块从课题组项目管理中移除，挪到机关项目管理中
             */
            /*
             * case "8": menuStr = initQualityList(projectId); break;
             */
            }
        }
        return menuStr;
    }

    /**
     * 初始化立项管理菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。 原名为：initFunctionList
     * 
     * @return
     */
    private String initApprovalList(String projectId, String planContractFlag, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup&id=" + projectId);//基本信息
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//立项论证-1
        urlList.add("tPmProjectReportController.do?tPmProjectReport");//申报依据-2
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//项目组人员-3
        urlList.add("tPmDeclareController.do?tPmDeclareForResearchGroup");//申报书管理-4

        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//审查报批-5
        urlList.add("tPmAbatePayfirstController.do?tPmAbatePayfirst");//减免垫支-6

        urlList.add("tPmPriceController.do?tPmPrice");//报价-7
        urlList.add("tPmOpenSubjectController.do?openSubjectUpdateForResearchGroup");//开题信息-8

        urlList.add("tPmIncomeContractApprController.do?tPmIncomeContractAppr");//进账合同审批-9

        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'1'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>立项论证</span></a></div>");
        }
        if (planContractFlag.equals(ProjectConstant.PROJECT_PLAN)) {
            if (checkAuth(urlList.get(2), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(2) + "' target='contentFrame'><span>申报依据</span></a></div>");
            }
            if (checkAuth(urlList.get(3), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(3)
                        + "' target='contentFrame'><span>项目组成员管理</span></a></div>");
            }
            if (checkAuth(urlList.get(4), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>申报书管理</span></a></div>");
            }
            if (checkAuth(urlList.get(6), departFlag)) {
                sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(6)
                        + "' target='contentFrame'><span>减免垫支</span></a></div>");
            }
            if (checkAuth(urlList.get(9), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(9)
                        + "' target='contentFrame'><span>进账合同审批</span></a></div>");
            }
        } else {
            if (checkAuth(urlList.get(3), departFlag)) {
                sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(3)
                        + "' target='contentFrame'><span>项目组成员管理</span></a></div>");
            }
            if (checkAuth(urlList.get(7), departFlag)) {
                sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(7)
                        + "' target='contentFrame'><span>报价</span></a></div>");
            }
            if (checkAuth(urlList.get(8), departFlag)) {
                sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(8)
                        + "' target='contentFrame'><span>开题信息</span></a></div>");
            }
            if (checkAuth(urlList.get(6), departFlag)) {
                sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(6)
                        + "' target='contentFrame'><span>减免垫支</span></a></div>");
            }
            if (checkAuth(urlList.get(9), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(9)
                        + "' target='contentFrame'><span>进账合同审批</span></a></div>");
            }
        }
        return sb.toString();
    }

    /**
     * 初始化计划管理菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。
     * 
     * @return
     */
    private String initPlanist(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmTaskController.do?taskUpdateForResearchGroup");//任务书/任务节点管理-0
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//任务节点管理-1
        urlList.add("tPmIncomeNodeController.do?tPmIncomeNode");//来款节点管理-2
        urlList.add("tPmPayNodeController.do?tPmPayNode");//支付节点管理-3
        urlList.add("tPmProjectFundsApprController.do?tPmProjectFundsAppr");//预算管理-4
        //        urlList.add("tPmIncomeContractApprController.do?tPmIncomeContractAppr");//进账合同审批-5
        //        urlList.add("tPmOutcomeContractApprController.do?tPmOutcomeContractAppr");//出账合同审批-6
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//外协管理-7
        urlList.add("tPmOpenSubjectController.do?openSubjectUpdateForResearchGroup");//各节点提醒设置/管理-8
        urlList.add("tPmTaskController.do?taskUpdateForResearchGroup");//任务书/任务节点管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//任务节点管理
        urlList.add("tPmIncomeNodeController.do?tPmIncomeNode");//来款节点管理
        urlList.add("tPmPayNodeController.do?tPmPayNode");//支付节点管理
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//预算管理
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//合同节点管理
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//外协管理
        urlList.add("tPmOpenSubjectController.do?openSubjectUpdateForResearchGroup");//各节点提醒设置/管理
        urlList.add("tPmContractNodeController.do?goIncomeContractNodeList");//15-合同节点指定来款节点
        urlList.add("tPmContractNodeController.do?goPayContractNodeList");//16-合同节点指定支付节点
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div data-options=\"id:'1'\"><a subhref='" + urlList.get(0)
                    + "' target='contentFrame'><span>任务书/任务节点管理</span></a></div>");
        }
        /*
         * if (checkAuth(urlList.get(1))) { sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1) +
         * "' target='contentFrame'><span>任务节点管理</span></a></div>"); }
         */
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>来款节点管理</span></a></div>");
        }
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(3)
                    + "' target='contentFrame'><span>支付节点管理</span></a></div>");
        }
        if (checkAuth(urlList.get(4), departFlag)) {
            sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(4)
                    + "' target='contentFrame'><span>项目预算管理</span></a></div>");
        }
        if (checkAuth(urlList.get(15), departFlag)) {
            sb.append("<div data-options=\"id:'15'\"><a subhref='" + urlList.get(15)
                    + "' target='contentFrame'><span>合同节点指定来款节点</span></a></div>");
        }
        if (checkAuth(urlList.get(16), departFlag)) {
            sb.append("<div data-options=\"id:'16'\"><a subhref='" + urlList.get(16)
                    + "' target='contentFrame'><span>合同节点指定支付节点</span></a></div>");
        }
        /*
         * sb.append("<div><span>合同审批管理</span><div>"); if (checkAuth(urlList.get(5))) {
         * sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(5) +
         * "' target='contentFrame'><span>进账合同审批</span></a></div>"); } if (checkAuth(urlList.get(5))) {
         * sb.append("<div data-options=\"id:'6'\"><a subhref='" + urlList.get(6) +
         * "' target='contentFrame'><span>出账合同审批</span></a></div>"); } sb.append("</div></div>");
         */
        /*
         * if (checkAuth(urlList.get(6))) { sb.append("<div data-options=\"id:'7'\"><a subhref='" + urlList.get(7) +
         * "' target='contentFrame'><span>外协管理</span></a></div>"); }
         */
        return sb.toString();
    }

    /**
     * 初始化执行管理菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。
     * 
     * @return
     */
    private String initExecList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();

        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//采购合同签订审批-0
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//合同支付节点审核-1
        urlList.add("tPmContractPriceCoverController.do?tPmContractPriceCover");//合同价款计算书2
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//计划类项目任务管理-3
        urlList.add("tPmOutcomeContractApprController.do?tPmOutcomeContractAppr");//出账合同审批-4

        //        if (checkAuth(urlList.get(0))) {
        //            sb.append("<div data-options=\"id:'1'\" style='height:20px'><a subhref='" + urlList.get(0)
        //                    + "' target='contentFrame'><span>采购合同签订审批</span></a></div>");
        //        }
        /**
         * @author zhangls 挪到机关项目管理中
         */
        /*
         * if (checkAuth(urlList.get(1))) { sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1) +
         * "' target='contentFrame'><span>合同支付节点审核</span></a></div>"); }
         */
        /*
         * sb.append("<div><span>合同价款计算书</span><div>"); if (checkAuth(urlList.get(4))) {
         * sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(4) + "&type=" +
         * ProjectConstant.CONTRACT_PRODUCE + "' target='contentFrame'><span>生产订货类合同价款</span></a></div>"); } if
         * (checkAuth(urlList.get(5))) { sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(5) +
         * "&type=" + ProjectConstant.CONTRACT_DEVELOP + "' target='contentFrame'><span>研制类合同价款</span></a></div>"); } if
         * (checkAuth(urlList.get(6))) { sb.append("<div data-options=\"id:'6'\"><a subhref='" + urlList.get(6) +
         * "&type=" + ProjectConstant.CONTRACT_BUY + "' target='contentFrame'><span>采购类合同价款</span></a></div>"); }
         */
        /* sb.append("</div></div>"); */
        if (checkAuth(urlList.get(4), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>出账合同审批</span></a></div>");
        }

        /**
         * @author zhangls 与计划管理中任务书与节点重复
         */
        /*
         * if (checkAuth(urlList.get(3))) { sb.append("<div data-options=\"id:'4'\"><a subhref='" + urlList.get(3) +
         * "' target='contentFrame'><span>计划类项目任务管理</span></a></div>"); }
         */
        return sb.toString();
    }

    /**
     * 初始化控制管理菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。
     * 
     * @return
     */
    private String initContralList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//项目变更管理
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//项目任务节点检查
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//合同管理
        urlList.add("tBPmChangeProjectnameController.do?tBPmChangeProjectname");//项目名称变更申请
        urlList.add("tBPmChangeProjectmanagerController.do?tBPmChangeProjectmanager");//项目负责人变更申请
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div><span>项目变更管理</span><div>");
            if (checkAuth(urlList.get(3), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(3)
                        + "' target='contentFrame'><span>项目名称变更申请</span></a></div>");
            }
            if (checkAuth(urlList.get(4), departFlag)) {
                sb.append("<div><a subhref='" + urlList.get(4)
                        + "' target='contentFrame'><span>项目负责人变更申请</span></a></div>");
            }
            sb.append("</div></div>");
        }
        /**
         * @author zhangls 挪到机关项目管理中
         */
        /*
         * if (checkAuth(urlList.get(1))) { sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1) +
         * "' target='contentFrame'><span>项目任务节点检查</span></a></div>"); }
         */
        /**
         * @author zhangls 与进出账合同管理重复
         */
        /*
         * if (checkAuth(urlList.get(2))) { sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(2) +
         * "' target='contentFrame'><span>合同管理</span></a></div>"); }
         */
        return sb.toString();
    }

    /**
     * 初始化鉴定验收菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。
     * 
     * @return
     */
    private String initIdentifyList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//鉴定计划管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//会议管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//合同验收管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//财务结题管理
        /*
         * if (checkAuth(urlList.get(0), departFlag)) {
         * sb.append("<div data-options=\"id:'1', disabled:'true'\"><a subhref='" + urlList.get(0) +
         * "' target='contentFrame' ><span>鉴定计划管理</span></a></div>"); }
         */
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'2', disabled:'true'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>会议管理</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'3', disabled:'true'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>合同验收管理</span></a></div>");
        }
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div data-options=\"id:'4', disabled:'true'\"><a subhref='" + urlList.get(3)
                    + "' target='contentFrame'><span>财务结题管理</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * 初始化成果管理菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。
     * 
     * @return
     */
    private String initResultList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        //urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//学术实力管理
        urlList.add("tBLearningThesisController.do?tBLearningThesis");//学术论文管理
        urlList.add("tBLearningBookController.do?tBLearningBook");//学术著作管理
        urlList.add("tBLearningTeamController.do?tBLearningTeam");//学术团体管理
        urlList.add("tBLearningActivityController.do?tBLearningActivity");//学术活动管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//学术报告厅管理
        //urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup&projectId=" + projectId);//论著网上保密审查
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//论文保密审查管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//专著保密审查管理
        urlList.add("tBAppraisalProjectController.do?tBAppraisalProject");//鉴定计划
        urlList.add("tBResultRewardController.do?tBResultReward");//成果奖励
        urlList.add("tBResultSpreadController.do?tBResultSpread");//成果推广
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//成果验收

        // 学术实力管理
        sb.append("<div><span>学术实力管理</span><div>");
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(0) + "' target='contentFrame'><span>学术论文管理</span></a></div>");
        }
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(1) + "' target='contentFrame'><span>学术著作管理</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(2) + "' target='contentFrame'><span>学术团体管理</span></a></div>");
        }
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(3) + "' target='contentFrame'><span>学术活动管理</span></a></div>");
        }
        //        if (checkAuth(urlList.get(4), departFlag)) {
        //            sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>学术报告厅管理</span></a></div>");
        //        }
        sb.append("</div></div>");

        // 论著网上保密审查
        sb.append("<div data-options=\"disabled:'true'\"><span>论著网上保密审查</span><div>");
        if (checkAuth(urlList.get(5), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(5) + "' target='contentFrame'><span>论文保密审查管理</span></a></div>");
        }
        if (checkAuth(urlList.get(6), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(6) + "' target='contentFrame'><span>专著保密审查管理</span></a></div>");
        }
        sb.append("</div></div>");

        // 成果鉴定管理
        if (checkAuth(urlList.get(7), departFlag)) {
            sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(7)
                    + "' target='contentFrame'><span>鉴定计划</span></a></div>");
        }

        // 成果奖励管理
        if (checkAuth(urlList.get(8), departFlag)) {
            sb.append("<div data-options=\"id:'4',\"><a subhref='" + urlList.get(8)
                    + "' target='contentFrame'><span>成果奖励</span></a></div>");
        }

        // 成果推广管理
        if (checkAuth(urlList.get(9), departFlag)) {
            sb.append("<div data-options=\"id:'5', \"><a subhref='" + urlList.get(9)
                    + "' target='contentFrame'><span>成果推广</span></a></div>");
        }

        // 成果验收管理
        if (checkAuth(urlList.get(10), departFlag)) {
            sb.append("<div data-options=\"id:'6', disabled:'true'\"><a subhref='" + urlList.get(10)
                    + "' target='contentFrame'><span>成果验收</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * 初始化知识产权菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。
     * 
     * @return
     */
    private String initPatentList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//专利管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//发明报告
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//知识产权保护
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//知识产权服务管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//知识产权代理管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//知识产权经费管理
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//知识产权网站
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div data-options=\"id:'1', disabled:'true'\"><a subhref='" + urlList.get(0)
                    + "' target='contentFrame'><span>专利管理</span></a></div>");
        }
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'2', disabled:'true'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>发明报告</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'3', disabled:'true'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>知识产权保护</span></a></div>");
        }
        sb.append("<div data-options=\"disabled:'true'\"><span>知识产权服务管理</span><div>");
        if (checkAuth(urlList.get(3), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(3) + "' target='contentFrame'><span>知识产权代理管理</span></a></div>");
        }
        if (checkAuth(urlList.get(4), departFlag)) {
            sb.append("<div><a subhref='" + urlList.get(4) + "' target='contentFrame'><span>知识产权经费管理</span></a></div>");
        }
        if (checkAuth(urlList.get(5), departFlag)) {
            sb.append("<div data-options=\"id:'5'\"><a subhref='" + urlList.get(5)
                    + "' target='contentFrame'><span>知识产权网站</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * 初始化质量管理菜单,菜单权限手动控制，需要将制定菜单地址配置到系统中。
     * 
     * @return
     */
    private String initQualityList(String projectId, String departFlag) {
        StringBuffer sb = new StringBuffer();
        List<String> urlList = new ArrayList<String>();
        urlList.add("tPmProjectController.do?goUpdateForResearchGroup");//体系文件管理
        urlList.add("tPmProjectApprovalController.do?approvalUpdateForResearchGroup");//供方名录管理
        urlList.add("tPmProjectMemberController.do?tPmProjectMember");//其他说明
        if (checkAuth(urlList.get(0), departFlag)) {
            sb.append("<div data-options=\"id:'1'\"><a subhref='" + urlList.get(0)
                    + "' target='contentFrame'><span>体系文件管理</span></a></div>");
        }
        if (checkAuth(urlList.get(1), departFlag)) {
            sb.append("<div data-options=\"id:'2'\"><a subhref='" + urlList.get(1)
                    + "' target='contentFrame'><span>供方名录管理</span></a></div>");
        }
        if (checkAuth(urlList.get(2), departFlag)) {
            sb.append("<div data-options=\"id:'3'\"><a subhref='" + urlList.get(2)
                    + "' target='contentFrame'><span>其他说明</span></a></div>");
        }
        return sb.toString();
    }

    /**
     * 检查是否有权限.
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
