package com.kingtake.project.controller.supplier;

import java.io.IOException;
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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.DecimalFormatUtil;
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
import org.jeecgframework.tag.vo.datatable.SortDirection;
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

import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.supplier.TPmQualitySupplierEntity;
import com.kingtake.project.service.supplier.TPmQualitySupplierServiceI;

/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2015-08-20 09:36:13
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmQualitySupplierController")
public class TPmQualitySupplierController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmQualitySupplierController.class);

    @Autowired
    private TPmQualitySupplierServiceI tPmQualitySupplierService;
    @Autowired
    private SystemService systemService;
    private String message;

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;

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
    @RequestMapping(params = "tPmQualitySupplier")
    public ModelAndView tPmQualitySupplier(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/supplier/tPmQualitySupplierList");
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
    public void datagrid(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmQualitySupplierEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmQualitySupplier,
                request.getParameterMap());
        //cq.eq("validFlag", SrmipConstants.YES);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tPmQualitySupplierService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmQualitySupplier = systemService.getEntity(TPmQualitySupplierEntity.class, tPmQualitySupplier.getId());
        message = "?????????????????????????????????";
        try {
            tPmQualitySupplier.setValidFlag(SrmipConstants.NO);//??????????????????
            tPmQualitySupplierService.updateEntitie(tPmQualitySupplier);
            //            tPmQualitySupplierService.delete(tPmQualitySupplier);
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
                TPmQualitySupplierEntity tPmQualitySupplier = systemService.getEntity(TPmQualitySupplierEntity.class,
                        id);
                tPmQualitySupplier.setValidFlag(SrmipConstants.NO);//??????????????????
                tPmQualitySupplierService.updateEntitie(tPmQualitySupplier);
                //                tPmQualitySupplierService.delete(tPmQualitySupplier);
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
    public AjaxJson doAdd(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            tPmQualitySupplierService.save(tPmQualitySupplier);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
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
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            this.tPmQualitySupplierService.saveSupplier(tPmQualitySupplier);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
            j.setSuccess(false);
        }
        j.setObj(tPmQualitySupplier);
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmQualitySupplier.getId())) {
            tPmQualitySupplier = tPmQualitySupplierService.getEntity(TPmQualitySupplierEntity.class,
                    tPmQualitySupplier.getId());
            req.setAttribute("tPmQualitySupplierPage", tPmQualitySupplier);
        }
        req.setAttribute("validFlag", SrmipConstants.YES);//????????????
        return new ModelAndView("com/kingtake/project/supplier/tPmQualitySupplier-add");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmQualitySupplier.getId())) {
            tPmQualitySupplier = tPmQualitySupplierService.getEntity(TPmQualitySupplierEntity.class,
                    tPmQualitySupplier.getId());
        }
        req.setAttribute("tPmQualitySupplierPage", tPmQualitySupplier);
        return new ModelAndView("com/kingtake/project/supplier/tPmQualitySupplier-update");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAudit")
    public ModelAndView goAudit(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmQualitySupplier.getId())) {
            TSUser user = ResourceUtil.getSessionUserName();
            tPmQualitySupplier = tPmQualitySupplierService.getEntity(TPmQualitySupplierEntity.class,
                    tPmQualitySupplier.getId());
            if (StringUtils.isEmpty(tPmQualitySupplier.getAuditUserId())) {
                tPmQualitySupplier.setAuditUserId(user.getId());
                tPmQualitySupplier.setAuditUserName(user.getRealName());
            }
        }
        req.setAttribute("tPmQualitySupplierPage", tPmQualitySupplier);
        return new ModelAndView("com/kingtake/project/supplier/tPmQualitySupplier-audit");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/supplier/tPmQualitySupplierUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmQualitySupplierEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmQualitySupplier,
                request.getParameterMap());
        cq.eq("validFlag", SrmipConstants.YES);
        cq.add();
        List<TPmQualitySupplierEntity> tPmQualitySuppliers = this.tPmQualitySupplierService.getListByCriteriaQuery(cq,
                false);
        setContractInfo(tPmQualitySuppliers);//??????????????????
        modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmQualitySupplierEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmQualitySuppliers);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????????????????
     * 
     * @param tPmQualitySuppliers
     */
    private void setContractInfo(List<TPmQualitySupplierEntity> tPmQualitySuppliers) {
        for (TPmQualitySupplierEntity supplier : tPmQualitySuppliers) {
            CriteriaQuery contractCq = new CriteriaQuery(TPmOutcomeContractApprEntity.class);
            contractCq.eq("approvalUnitId", supplier.getId());
            contractCq.addOrder("contractSigningTime", SortDirection.desc);
            contractCq.add();
            List<TPmOutcomeContractApprEntity> contractApprList = this.tPmQualitySupplierService
                    .getListByCriteriaQuery(contractCq, false);
            StringBuffer contractNameSb = new StringBuffer();
            StringBuffer projectManagerSb = new StringBuffer();
            StringBuffer applyUnitSb = new StringBuffer();
            StringBuffer totalFundsSb = new StringBuffer();
            StringBuffer signingTimeSb = new StringBuffer();
            if(contractApprList.size()>0){
            for (int i = 0; i < contractApprList.size(); i++) {
                    int index = i + 1;
                    String contractName = contractApprList.get(i).getContractName() == null ? "" : contractApprList
                            .get(i).getContractName();
                    contractNameSb.append(index + "???" + contractName).append("\n");
                    if (contractApprList.get(i).getProject() != null
                            && contractApprList.get(i).getProject().getProjectManager() != null) {
                        projectManagerSb.append(index + "???" + contractApprList.get(i).getProject().getProjectManager())
                                .append(
                            "\n");
                    }
                    String applyUnit = contractApprList.get(i).getApplyUnit()==null?"":contractApprList.get(i).getApplyUnit();
                    applyUnitSb.append(index + "???" + applyUnit).append("\n");
                    String totalFunds = contractApprList.get(i).getTotalFunds() == null ? "" : DecimalFormatUtil
                            .format(contractApprList.get(i).getTotalFunds());
                    totalFundsSb.append(index + "???" + totalFunds).append("\n");
                    if (contractApprList.get(i).getContractSigningTime() != null) {
                    signingTimeSb.append(
                            index
                                    + "???"
                                    + DateUtils.formatDate(contractApprList.get(i).getContractSigningTime(),
                                            "yyyy-MM-dd")).append("\n");
                    }
            }
                supplier.setContractNameStr(contractNameSb.toString());
                supplier.setProjectManagerStr(projectManagerSb.toString());
                supplier.setApplyUnit(applyUnitSb.toString());
                supplier.setTotalFunds(totalFundsSb.toString());
                supplier.setSigningTime(signingTimeSb.toString());
            }
        }
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmQualitySupplierEntity.class);
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
                List<TPmQualitySupplierEntity> listTPmQualitySupplierEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmQualitySupplierEntity.class, params);
                for (TPmQualitySupplierEntity tPmQualitySupplier : listTPmQualitySupplierEntitys) {
                    tPmQualitySupplierService.save(tPmQualitySupplier);
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
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "supplierList")
    public ModelAndView supplierList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/supplier/supplierList");
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "cooperationList")
    public ModelAndView cooperationList(HttpServletRequest request) {
        String supplierId = request.getParameter("supplierId");
        if (StringUtils.isNotEmpty(supplierId)) {
            request.setAttribute("supplierId", supplierId);
        }
        return new ModelAndView("com/kingtake/project/supplier/supplierCooperationList");
    }

    /**
     * ??????????????????
     * 
     * @param tPmQualitySupplier
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForCooperationList")
    public void datagridForCooperationList(TPmQualitySupplierEntity tPmQualitySupplier, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String supplierId = request.getParameter("supplierId");
        String projectName = request.getParameter("projectName");
        String contractCode = request.getParameter("contractCode");
        String contractName = request.getParameter("contractName");
        String accountingCode = request.getParameter("accountingCode");
        String sql = "select * from (select p.id projectId, p.project_name projectName, p.project_manager projectManager,p.accounting_code accountingCode,a.apply_unit applyUnit,"
                + "a.id contractId, a.contract_name contractName, a.contract_code contractCode, a.total_funds totalFunds, "
                + "a.contract_signing_time contractSigningTime from t_pm_outcome_contract_appr a join t_pm_project p"
                + " on a.t_p_id = p.id where 1=1 ";
        if (StringUtils.isNotEmpty(supplierId)) {
            sql += "and  a.approval_unit_id =  '" + supplierId + "'";
        }
        if (StringUtils.isNotEmpty(projectName)) {
            sql += "and  p.project_name like '%" + projectName + "%'";
        }
        if (StringUtils.isNotEmpty(contractCode)) {
            sql += "and  a.contract_code like '%" + contractCode + "%'";
        }
        if (StringUtils.isNotEmpty(contractName)) {
            sql += "and  a.contract_name like '%" + contractName + "%'";
        }
        if (StringUtils.isNotEmpty(accountingCode)) {
            sql += "and  p.accounting_code like '%" + accountingCode + "%'";
        }
        String s = " )t  order by contractSigningTime desc";
        sql += s;
        List<Map<String, Object>> cooperationList = this.tPmQualitySupplierService.findForJdbcParam(sql,
                dataGrid.getPage(), dataGrid.getRows());
        String countSql = "select count(1) from (" + sql + ")";
        Long count = tPmQualitySupplierService.getCountForJdbcParam(countSql, new String[] {});
        dataGrid.setResults(cooperationList);
        dataGrid.setTotal(count.intValue());
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ??????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goEndTimeSet")
    public ModelAndView goEndTimeSet(HttpServletRequest req) {
        TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
        codeTypeEntity.setCodeType("1");
        codeTypeEntity.setCode("GFSXNX");
        List<TBCodeDetailEntity> codeDetailList = tBCodeTypeService.getCodeDetailByCodeType(codeTypeEntity);
        if (codeDetailList.size() > 0) {
            TBCodeDetailEntity detail = codeDetailList.get(0);
            String sxnx = detail.getName();
            req.setAttribute("sxnx", sxnx);
        } else {
            throw new BusinessException("?????????????????????GFSXNX...");
        }
        return new ModelAndView("com/kingtake/project/supplier/supplierEndTime");
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doEndTimeSet")
    @ResponseBody
    public AjaxJson doEndTimeSet(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
            String sxnx = req.getParameter("sxnx");
            if (!sxnx.matches("\\d+")) {
                j.setSuccess(false);
                j.setMsg("????????????????????????");
                return j;
            }
        TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
        codeTypeEntity.setCodeType("1");
        codeTypeEntity.setCode("GFSXNX");
        List<TBCodeDetailEntity> codeDetailList = tBCodeTypeService.getCodeDetailByCodeType(codeTypeEntity);
        if (codeDetailList.size() > 0) {
            TBCodeDetailEntity detail = codeDetailList.get(0);
                detail.setName(sxnx);
                this.systemService.updateEntitie(detail);
            }
            j.setMsg("???????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("???????????????????????????");
        }
        return j;
    }

    /**
     * ??????????????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "doRelateContract")
    @ResponseBody
    public AjaxJson doRelateContract(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String supplierId = request.getParameter("supplierId");
        try {
            this.tPmQualitySupplierService.doRelateContract(supplierId, ids);
            j.setMsg("???????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("???????????????????????????");
        }
        return j;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goRelateContractList")
    public ModelAndView goRelateContractList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/supplier/contractList");
    }
}
