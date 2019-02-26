package com.kingtake.project.controller.apprincomecontract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
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
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractbase.TPmContractBasicEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.apprincomecontract.TPmIncomeContractApprServiceI;

/**
 * @Title: Controller
 * @Description: 进账合同审批
 * @author onlineGenerator
 * @date 2015-07-26 15:07:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmIncomeContractApprController")
public class TPmIncomeContractApprController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmIncomeContractApprController.class);

    @Autowired
    private TPmIncomeContractApprServiceI tPmIncomeContractApprService;
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
     * 进账合同审批处理tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-receiveTab");
    }

    /**
     * 进账合同审批列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractAppr")
    public ModelAndView tPmIncomeContractAppr(HttpServletRequest request) {
        //发起审批流程
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList");
        }

        //处理审批
        //0：未处理；1：已处理
        String type = request.getParameter("type");
        if (StringUtil.isNotEmpty(type)) {
            if ("0".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.NO);
            } else if ("1".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.YES);
            }
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_RECEIVE);
            request.setAttribute("YES", SrmipConstants.YES);
            request.setAttribute("NO", SrmipConstants.NO);
            return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-receive");
        }
        return new ModelAndView("common/404.jsp");

    }

    /**
     * 基本信息中进账合同审批列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractApprInfo")
    public ModelAndView tPmIncomeContractApprInfo(HttpServletRequest request) {
        //发起审批流程
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
            request.setAttribute("projectId", projectId);
            request.setAttribute("lxyj", project.getLxyj());
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
        }
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprListInfo");
    }

    /**
     * 项目--》进账合同列表--》合同验收报告关联页面
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractApprCheck")
    public ModelAndView tPmIncomeContractApprCheck(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-check");
    }

    /**
     * 待审批进账合同列表给待办信息
     * 
     * @return
     */
    @RequestMapping(params = "toPortal")
    public ModelAndView tPmIncomeContractApprForPortal(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-portal");
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
    public void datagrid(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//发起审批

            CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractApprEntity.class, dataGrid);
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeContractAppr,
                    request.getParameterMap());
            cq.addOrder("createDate", SortDirection.desc);
            cq.add();
            this.tPmIncomeContractApprService.getDataGridReturn(cq, true);

        } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批

            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();//获取参数

            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            StringBuffer resultSql = new StringBuffer();
            resultSql
                    .append("SELECT APPR.ID AS APPR_ID, APPR.T_P_ID AS PROJECTID, APPR.APPLY_UNIT, APPR.PROJECT_NAME, APPR.CONTRACT_NAME, \n");
            resultSql.append("APPR.APPROVAL_UNIT, APPR.TOTAL_FUNDS, APPR.FUNDS_REQUIRE, APPR.FINISH_FLAG, \n");
            resultSql.append("APPR.CONTRACT_TYPE, APPR.CONTRACT_TYPE_CONTENT, \n");
            resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
            resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
            resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

            String temp = "FROM T_PM_INCOME_CONTRACT_APPR APPR, T_PM_APPR_SEND_LOGS SEND, "
                    + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                    + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                    + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                    + "AND RECEIVE.RECEIVE_USERID = ?  ";

            if (SrmipConstants.YES.equals(operateStatus)) {
                //已处理
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                //未处理：需要是有效的
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                        + SrmipConstants.YES;
            }

            String projectname = request.getParameter("project.name");
            if (StringUtil.isNotEmpty(projectname)) {
                temp += " AND APPR.PROJECT_NAME LIKE '%" + projectname + "%'";
            }

            String contractName = request.getParameter("contract.name");
            if (StringUtil.isNotEmpty(contractName)) {
                temp += " AND APPR.CONTRACT_NAME LIKE '%" + contractName + "%'";
            }

            temp += "ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
            String[] params = new String[] { user.getId() };

            List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                    dataGrid.getPage(), dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除进账合同审批
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomeContractAppr = systemService.getEntity(TPmIncomeContractApprEntity.class,
                tPmIncomeContractAppr.getId());
        message = "进账合同审批删除成功";
        try {
            tPmIncomeContractApprService.deleteAddAttach(tPmIncomeContractAppr);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "进账合同审批删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除进账合同审批
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "进账合同审批删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmIncomeContractApprEntity tPmIncomeContractAppr = systemService.getEntity(
                        TPmIncomeContractApprEntity.class, id);
                tPmIncomeContractApprService.deleteAddAttach(tPmIncomeContractAppr);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "进账合同审批删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加进账合同审批
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "进账合同审批添加成功";
        try {
            tPmIncomeContractApprService.save(tPmIncomeContractAppr);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "进账合同审批添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(tPmIncomeContractAppr.getId());
        return j;
    }

    /**
     * 更新进账合同审批
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmIncomeContractApprEntity tPmIncomeContractAppr,
            TPmContractBasicEntity tPmContractBasic, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "进账合同审批更新成功";
        TPmIncomeContractApprEntity t = tPmIncomeContractApprService.get(TPmIncomeContractApprEntity.class,
                tPmIncomeContractAppr.getId());
        TPmContractBasicEntity b = systemService.get(TPmContractBasicEntity.class, tPmContractBasic.getRid());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmIncomeContractAppr, t);
            tPmIncomeContractApprService.saveOrUpdate(t);

            MyBeanUtils.copyBeanNotNull2Bean(tPmContractBasic, b);
            systemService.saveOrUpdate(b);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "进账合同审批更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(t.getId());
        return j;
    }

    /**
     * 更新进账合同审批
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateFiles")
    @ResponseBody
    public AjaxJson doUpdateFiles(String id, String fileKeys, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "附件上传成功";
        try {
            if (StringUtil.isNotEmpty(id)) {

            }

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "附件上传失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 保存进账合同审批
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TPmIncomeContractApprEntity tPmIncomeContractAppr, TPmContractBasicEntity tPmContractBasic,
            HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "进账合同审批保存成功";
        try {
            Map<String, Object> ids = new HashMap<String, Object>();//保存审批主表id和合同基本信息id

            String contractId = null;
            if (StringUtil.isEmpty(tPmIncomeContractAppr.getId())) {
                tPmIncomeContractAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
                tPmIncomeContractAppr.setContractBookFlag("0");//初始化为未确认
                tPmIncomeContractApprService.save(tPmIncomeContractAppr);
                contractId = tPmIncomeContractAppr.getId();
            } else {
                contractId = tPmIncomeContractAppr.getId();
                TPmIncomeContractApprEntity t = tPmIncomeContractApprService.get(TPmIncomeContractApprEntity.class,
                        contractId);
                MyBeanUtils.copyBeanNotNull2Bean(tPmIncomeContractAppr, t);
                tPmIncomeContractApprService.saveOrUpdate(t);
            }

            //合同基本信息
            String basicId = null;
            if (!StringUtil.isNotEmpty(tPmContractBasic.getRid())) {
                tPmContractBasic.setInOutContractid(contractId);
                systemService.save(tPmContractBasic);
                basicId = tPmContractBasic.getRid();
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } else {
                basicId = tPmContractBasic.getRid();
                TPmContractBasicEntity basic = systemService.get(TPmContractBasicEntity.class, basicId);
                MyBeanUtils.copyBeanNotNull2Bean(tPmContractBasic, basic);
                systemService.saveOrUpdate(basic);
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }

            ids.put("contractId", contractId);
            ids.put("basicId", basicId);
            j.setAttributes(ids);

            //关联附件
//            String fileKeys = request.getParameter("fileKeys");
//            if (StringUtil.isNotEmpty(fileKeys)) {
//                CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
//                cq.in("id", fileKeys.substring(0, fileKeys.length() - 1).split(","));
//                cq.isNull("bid");
//                cq.add();
//                List<TSFilesEntity> files = systemService.getListByCriteriaQuery(cq, false);
//                if (files != null && files.size() > 0) {
//                    for (TSFilesEntity file : files) {
//                        file.setBid(contractId);
//                        systemService.updateEntitie(file);
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "进账合同审批保存失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 进账合同审批新增tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddTab")
    public ModelAndView goAddTab(String projectId, HttpServletRequest req) {
        req.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractAppr-addTab");
    }

    /**
     * 进账合同审批新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncomeContractAppr.getId())) {
            tPmIncomeContractAppr = tPmIncomeContractApprService.getEntity(TPmIncomeContractApprEntity.class,
                    tPmIncomeContractAppr.getId());
        }

        TPmProjectEntity project = tPmIncomeContractAppr.getProject();
        if (project != null) {
            String projectId = project.getId();
            if (StringUtil.isNotEmpty(projectId)) {
                project = systemService.get(TPmProjectEntity.class, projectId);
                req.setAttribute("project", project);
                tPmIncomeContractAppr.setProjectName(project.getProjectName());
                tPmIncomeContractAppr.setTotalFunds(project.getAllFee());
                tPmIncomeContractAppr.setStartTime(project.getBeginDate());
                tPmIncomeContractAppr.setEndTime(project.getEndDate());
            }
        }

        tPmIncomeContractAppr.setContractSigningTime(new Date());
        //附件
        if(StringUtils.isEmpty(tPmIncomeContractAppr.getAttachmentCode())){
            tPmIncomeContractAppr.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmIncomeContractAppr.getAttachmentCode(), "tPmIncomeContractAppr");
            tPmIncomeContractAppr.setCertificates(certificates);
            List<TSFilesEntity> contractBook = systemService.getAttachmentByCode(tPmIncomeContractAppr.getAttachmentCode(), "incomeContractBook");
            tPmIncomeContractAppr.setContractBook(contractBook);
        }
        req.setAttribute("tPmIncomeContractApprPage", tPmIncomeContractAppr);
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprAndBasicContract-add");
    }

    @RequestMapping(params = "updateFlag")
    @ResponseBody
    public AjaxJson updateFlag(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmIncomeContractApprEntity contract = tPmIncomeContractApprService.get(TPmIncomeContractApprEntity.class,
                    id);
            if (contract != null) {
                if (ApprovalConstant.APPRSTATUS_SEND.equals(contract.getFinishFlag())) {
                    j.setSuccess(false);
                    j.setMsg("进账合同审批已发送，不能再进行编辑！");
                } else if (ApprovalConstant.APPRSTATUS_FINISH.equals(contract.getFinishFlag())) {
                    j.setSuccess(false);
                    j.setMsg("进账合同审批流程已完成，不能再进行编辑！");
                }
            }
        }
        return j;
    }



    /**
     * 进账合同审批新增tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateTab")
    public ModelAndView goUpdateTab(HttpServletRequest req) {
        String contractId = req.getParameter("id");
        req.setAttribute("contractId", contractId);

        String node = req.getParameter("node");
        req.setAttribute("node", node);

        String receive = req.getParameter("receive");
        req.setAttribute("receive", receive);

        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractAppr-updateTab");
    }

    /**
     * 进账合同审批编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncomeContractAppr.getId())) {
            tPmIncomeContractAppr = tPmIncomeContractApprService.getEntity(TPmIncomeContractApprEntity.class,
                    tPmIncomeContractAppr.getId());
          //附件
            if(StringUtils.isEmpty(tPmIncomeContractAppr.getAttachmentCode())){
                tPmIncomeContractAppr.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmIncomeContractAppr.getAttachmentCode(), "tPmIncomeContractAppr");
                tPmIncomeContractAppr.setCertificates(certificates);
                List<TSFilesEntity> contractBook = systemService.getAttachmentByCode(tPmIncomeContractAppr.getAttachmentCode(), "incomeContractBook");
                tPmIncomeContractAppr.setContractBook(contractBook);
            }
            req.setAttribute("tPmIncomeContractApprPage", tPmIncomeContractAppr);
        }
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprAndBasicContract-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractApprEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeContractAppr,
                request.getParameterMap());
        List<TPmIncomeContractApprEntity> tPmIncomeContractApprs = this.tPmIncomeContractApprService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "进账合同审批");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeContractApprEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("进账合同审批列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomeContractApprs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "进账合同审批");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmIncomeContractApprEntity.class);
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
                List<TPmIncomeContractApprEntity> listTPmIncomeContractApprEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmIncomeContractApprEntity.class, params);
                for (TPmIncomeContractApprEntity tPmIncomeContractAppr : listTPmIncomeContractApprEntitys) {
                    tPmIncomeContractApprService.save(tPmIncomeContractAppr);
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
     * 验证合同编号
     * 
     * @param tPmIncomeContractAppr
     * @param req
     * @return
     */
    @RequestMapping(params = "validformContractCode")
    @ResponseBody
    public ValidForm validformContractCode(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest req) {

        String inputVal = req.getParameter("param");

        ValidForm v = tPmIncomeContractApprService.validformCode("t_pm_income_contract_appr", "contract_code",
                tPmIncomeContractAppr.getId(), inputVal);

        return v;
    }

    /**
     * 确认提交确认
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "doSend")
    @ResponseBody
    public AjaxJson doSend(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String opt = request.getParameter("opt");
        String id = request.getParameter("id");
        TPmIncomeContractApprEntity contractAppr = systemService.getEntity(TPmIncomeContractApprEntity.class, id);
        try {
            if ("submit".equals(opt)) {
                String userId = request.getParameter("userId");
                String realname = request.getParameter("realname");
                String deptId = request.getParameter("deptId");
                contractAppr.setConfirmStatus("1");//confirm为1 已提交
                contractAppr.setConfirmUserId(userId);
                contractAppr.setConfirmUserRealName(realname);
                contractAppr.setConfirmDeptId(deptId);
                j.setMsg("进账合同提交确认成功");
                this.tPmIncomeContractApprService.saveOrUpdate(contractAppr);
            } else if ("pass".equals(opt)) {
                contractAppr.setConfirmStatus("2");//confirm为2 已确认
                j.setMsg("进账合同确认成功");
                this.tPmIncomeContractApprService.saveOrUpdate(contractAppr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg("操作失败");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 进账合同审批处理tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goConfirmTab")
    public ModelAndView goConfirmTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-confirmTab");
    }

    /**
     * 进账合同确认列表
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractListConfirm")
    public ModelAndView tPmIncomeContractListConfirm(HttpServletRequest request) {
        //处理审批
        //0：未处理；1：已处理
        String type = request.getParameter("type");
        if (StringUtil.isNotEmpty(type)) {
            if ("0".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.NO);
            } else if ("1".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.YES);
            }
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_RECEIVE);
            request.setAttribute("YES", SrmipConstants.YES);
            request.setAttribute("NO", SrmipConstants.NO);
        }
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-confirm");
    }

    /**
     * 进账合同确认列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForConfirm")
    public void datagridForConfirm(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批
            TSUser user = ResourceUtil.getSessionUserName();
            List<TPmProjectEntity> projectList = this.systemService.findByProperty(TPmProjectEntity.class,
                    "approvalUserid", user.getId());
            if (projectList.size() > 0) {
                List<String> projectIdList = new ArrayList<String>();
                for (TPmProjectEntity proj : projectList) {
                    projectIdList.add(proj.getId());
                }
                String operateStatus = request.getParameter("operateStatus");
                CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractApprEntity.class, dataGrid);
                org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeContractAppr,
                        request.getParameterMap());
                cq.in("project.id", projectIdList.toArray());
                if ("0".equals(operateStatus)) {
                    cq.eq("contractBookFlag", "1");//查询已上传未确认的数据
                } else {
                    cq.eq("contractBookFlag", "2");//查询已确认的数据
                }
                cq.addOrder("createDate", SortDirection.desc);
                cq.add();
                this.tPmIncomeContractApprService.getDataGridReturn(cq, true);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 获取合同编号
     * 
     * @return
     */
    @RequestMapping(params = "getContractCode")
    @ResponseBody
    public JSONObject getContractCode(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String inOutFlag = request.getParameter("inOutFlag");
        String greatSpecialFlag = request.getParameter("greatSpecialFlag");
        String businessCode = null;
        if ("i".equals(inOutFlag)) {
            businessCode = "incomeContractCode";
        } else if ("o".equals(inOutFlag)) {
            businessCode = "outcomeContractCode";
        }
        String code = PrimaryGenerater.getInstance().generaterNextContractCode(businessCode);
        //如果是重大专项项目，要在编码中加入字母Z
        if(greatSpecialFlag.equals(SrmipConstants.YES)){
          code = code.substring(0, 4)+"Z"+code.substring(4);
        }
        json.put("code", code);
        return json;
    }

    @RequestMapping(params = "checkIsAppr")
    @ResponseBody
    public JSONObject checkIsAppr(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("isAppr", "0");
        String apprId = request.getParameter("apprId");
        List<Map<String, Object>> apprList = this.getAppr(apprId);//查询课题组长
        if (apprList.size() > 0) {
            Map<String, Object> map = apprList.get(0);
            if (map.get("APPR_ID") != null) {
                json.put("isAppr", "1");
                json.put("receiveId", map.get("ID"));
            }
        }
        return json;
    }

    /**
     * 查询课题组长审批
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//获取参数
        StringBuffer resultSql = new StringBuffer();
        resultSql
                .append("SELECT APPR.ID AS APPR_ID, APPR.T_P_ID AS PROJECTID, APPR.APPLY_UNIT, APPR.PROJECT_NAME, APPR.CONTRACT_NAME, \n");
        resultSql.append("APPR.APPROVAL_UNIT, APPR.TOTAL_FUNDS, APPR.FUNDS_REQUIRE, APPR.FINISH_FLAG, \n");
        resultSql.append("APPR.CONTRACT_TYPE, APPR.CONTRACT_TYPE_CONTENT, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_PM_INCOME_CONTRACT_APPR APPR, T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n" + "AND EXT.ID='0301' \n"
                + "AND RECEIVE.RECEIVE_USERID = ?  ";

        //未处理：需要是有效的
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };

        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
    
    /**
     * 上传合同正本页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmContractUpload")
    public ModelAndView tPmContractUpload(HttpServletRequest request) {
        String contractId = request.getParameter("id");
        String inoutFlag = request.getParameter("inoutFlag");
        String businessCode = null;
        String bid = null;
        String projectId = null;
        List<TSFilesEntity> contractBook = null;
        String contractFlag = null;
        if("i".equals(inoutFlag)){
            businessCode = "incomeContractBook";
            TPmIncomeContractApprEntity incomeContract = this.systemService.get(TPmIncomeContractApprEntity.class, contractId);
            //附件
            if(StringUtils.isEmpty(incomeContract.getAttachmentCode())){
                bid = UUIDGenerator.generate();
            }else{
                contractBook = systemService.getAttachmentByCode(incomeContract.getAttachmentCode(), "incomeContractBook");
                bid = incomeContract.getAttachmentCode();
            }
            projectId = incomeContract.getProject().getId();
            contractFlag = incomeContract.getContractBookFlag();
        }else{
            businessCode = "outcomeContractBook";
            TPmOutcomeContractApprEntity outcomeContract = this.systemService.get(TPmOutcomeContractApprEntity.class, contractId);
            if(StringUtils.isEmpty(outcomeContract.getAttachmentCode())){
                bid = UUIDGenerator.generate();
            }else{
                contractBook = systemService.getAttachmentByCode(outcomeContract.getAttachmentCode(), "outcomeContractBook");
                bid = outcomeContract.getAttachmentCode();
            }
            projectId = outcomeContract.getProject().getId();
            contractFlag = outcomeContract.getContractBookFlag();
        }
        request.setAttribute("bid", bid);
        request.setAttribute("businessCode", businessCode);
        request.setAttribute("projectId", projectId);
        request.setAttribute("contractId", contractId);
        request.setAttribute("inoutFlag", inoutFlag);
        request.setAttribute("contractBook", contractBook);
        return new ModelAndView("com/kingtake/project/apprincomecontract/contract-upload");
    }
    
    /**
     * 确认合同正本
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "confirmContractBook")
    @ResponseBody
    public AjaxJson confirmContractBook(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try{
        if (StringUtil.isNotEmpty(id)) {
            TPmIncomeContractApprEntity contract = tPmIncomeContractApprService.get(TPmIncomeContractApprEntity.class,
                    id);
            if (contract != null) {
                contract.setContractBookFlag("2");//将状态改为已确认
                this.systemService.updateEntitie(contract);
            }
            j.setMsg("合同正本上传标志更新成功！");
        }
        }catch(Exception e){
            e.printStackTrace();
            j.setMsg("合同正本上传标志更新失败！");
        }
        return j;
    }
    
    /**
     * 上传合同正本
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateContractBook")
    @ResponseBody
    public AjaxJson updateContractBook(HttpServletRequest request, HttpServletResponse response) {
        String contractId = request.getParameter("contractId");
        String inoutFlag = request.getParameter("inoutFlag");
        AjaxJson j = new AjaxJson();
        if ("i".equals(inoutFlag)) {
            TPmIncomeContractApprEntity contract = tPmIncomeContractApprService.get(TPmIncomeContractApprEntity.class,
                    contractId);
            if (contract != null) {
                contract.setContractBookFlag("1");//将状态改为已上传
                this.systemService.updateEntitie(contract);
            }
        }else{
            TPmOutcomeContractApprEntity contract = tPmIncomeContractApprService.get(TPmOutcomeContractApprEntity.class,
                    contractId);
            if (contract != null) {
                contract.setContractBookFlag("1");//将状态改为已上传
                this.systemService.updateEntitie(contract);
            }
        }
        j.setMsg("上传合同正本成功！");
        return j;
    }
}
