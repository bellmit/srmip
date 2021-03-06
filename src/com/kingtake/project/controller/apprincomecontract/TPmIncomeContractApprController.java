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
 * @Description: ??????????????????
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
     * ????????????????????????tab????????????
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-receiveTab");
    }

    /**
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractAppr")
    public ModelAndView tPmIncomeContractAppr(HttpServletRequest request) {
        //??????????????????
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList");
        }

        //????????????
        //0???????????????1????????????
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
     * ??????????????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractApprInfo")
    public ModelAndView tPmIncomeContractApprInfo(HttpServletRequest request) {
        //??????????????????
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
     * ??????--?????????????????????--?????????????????????????????????
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
     * ??????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "toPortal")
    public ModelAndView tPmIncomeContractApprForPortal(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-portal");
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
    public void datagrid(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//????????????

            CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractApprEntity.class, dataGrid);
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeContractAppr,
                    request.getParameterMap());
            cq.addOrder("createDate", SortDirection.desc);
            cq.add();
            this.tPmIncomeContractApprService.getDataGridReturn(cq, true);

        } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//????????????

            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();//????????????

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
                //?????????
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                //??????????????????????????????
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
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomeContractAppr = systemService.getEntity(TPmIncomeContractApprEntity.class,
                tPmIncomeContractAppr.getId());
        message = "??????????????????????????????";
        try {
            tPmIncomeContractApprService.deleteAddAttach(tPmIncomeContractAppr);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
                TPmIncomeContractApprEntity tPmIncomeContractAppr = systemService.getEntity(
                        TPmIncomeContractApprEntity.class, id);
                tPmIncomeContractApprService.deleteAddAttach(tPmIncomeContractAppr);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
    public AjaxJson doAdd(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            tPmIncomeContractApprService.save(tPmIncomeContractAppr);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(tPmIncomeContractAppr.getId());
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
    public AjaxJson doUpdate(TPmIncomeContractApprEntity tPmIncomeContractAppr,
            TPmContractBasicEntity tPmContractBasic, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
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
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(t.getId());
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateFiles")
    @ResponseBody
    public AjaxJson doUpdateFiles(String id, String fileKeys, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????";
        try {
            if (StringUtil.isNotEmpty(id)) {

            }

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????";
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
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TPmIncomeContractApprEntity tPmIncomeContractAppr, TPmContractBasicEntity tPmContractBasic,
            HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            Map<String, Object> ids = new HashMap<String, Object>();//??????????????????id?????????????????????id

            String contractId = null;
            if (StringUtil.isEmpty(tPmIncomeContractAppr.getId())) {
                tPmIncomeContractAppr.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
                tPmIncomeContractAppr.setContractBookFlag("0");//?????????????????????
                tPmIncomeContractApprService.save(tPmIncomeContractAppr);
                contractId = tPmIncomeContractAppr.getId();
            } else {
                contractId = tPmIncomeContractAppr.getId();
                TPmIncomeContractApprEntity t = tPmIncomeContractApprService.get(TPmIncomeContractApprEntity.class,
                        contractId);
                MyBeanUtils.copyBeanNotNull2Bean(tPmIncomeContractAppr, t);
                tPmIncomeContractApprService.saveOrUpdate(t);
            }

            //??????????????????
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

            //????????????
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
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????tab????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAddTab")
    public ModelAndView goAddTab(String projectId, HttpServletRequest req) {
        req.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractAppr-addTab");
    }

    /**
     * ????????????????????????????????????
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
        //??????
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
                    j.setMsg("??????????????????????????????????????????????????????");
                } else if (ApprovalConstant.APPRSTATUS_FINISH.equals(contract.getFinishFlag())) {
                    j.setSuccess(false);
                    j.setMsg("????????????????????????????????????????????????????????????");
                }
            }
        }
        return j;
    }



    /**
     * ????????????????????????tab????????????
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
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncomeContractAppr.getId())) {
            tPmIncomeContractAppr = tPmIncomeContractApprService.getEntity(TPmIncomeContractApprEntity.class,
                    tPmIncomeContractAppr.getId());
          //??????
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
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprUpload");
    }

    /**
     * ??????excel
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
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeContractApprEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomeContractApprs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmIncomeContractApprEntity tPmIncomeContractAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
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
            MultipartFile file = entity.getValue();// ????????????????????????
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
     * ??????????????????
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
                contractAppr.setConfirmStatus("1");//confirm???1 ?????????
                contractAppr.setConfirmUserId(userId);
                contractAppr.setConfirmUserRealName(realname);
                contractAppr.setConfirmDeptId(deptId);
                j.setMsg("??????????????????????????????");
                this.tPmIncomeContractApprService.saveOrUpdate(contractAppr);
            } else if ("pass".equals(opt)) {
                contractAppr.setConfirmStatus("2");//confirm???2 ?????????
                j.setMsg("????????????????????????");
                this.tPmIncomeContractApprService.saveOrUpdate(contractAppr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg("????????????");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * ????????????????????????tab????????????
     * 
     * @return
     */
    @RequestMapping(params = "goConfirmTab")
    public ModelAndView goConfirmTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractApprList-confirmTab");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeContractListConfirm")
    public ModelAndView tPmIncomeContractListConfirm(HttpServletRequest request) {
        //????????????
        //0???????????????1????????????
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
     * ????????????????????????
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
        if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//????????????
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
                    cq.eq("contractBookFlag", "1");//?????????????????????????????????
                } else {
                    cq.eq("contractBookFlag", "2");//????????????????????????
                }
                cq.addOrder("createDate", SortDirection.desc);
                cq.add();
                this.tPmIncomeContractApprService.getDataGridReturn(cq, true);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ??????????????????
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
        //?????????????????????????????????????????????????????????Z
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
        List<Map<String, Object>> apprList = this.getAppr(apprId);//??????????????????
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
     * ????????????????????????
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//????????????
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

        //??????????????????????????????
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };

        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
    
    /**
     * ??????????????????????????????
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
            //??????
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
     * ??????????????????
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
                contract.setContractBookFlag("2");//????????????????????????
                this.systemService.updateEntitie(contract);
            }
            j.setMsg("???????????????????????????????????????");
        }
        }catch(Exception e){
            e.printStackTrace();
            j.setMsg("???????????????????????????????????????");
        }
        return j;
    }
    
    /**
     * ??????????????????
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
                contract.setContractBookFlag("1");//????????????????????????
                this.systemService.updateEntitie(contract);
            }
        }else{
            TPmOutcomeContractApprEntity contract = tPmIncomeContractApprService.get(TPmOutcomeContractApprEntity.class,
                    contractId);
            if (contract != null) {
                contract.setContractBookFlag("1");//????????????????????????
                this.systemService.updateEntitie(contract);
            }
        }
        j.setMsg("???????????????????????????");
        return j;
    }
}
