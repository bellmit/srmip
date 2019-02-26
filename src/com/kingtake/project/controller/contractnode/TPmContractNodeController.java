package com.kingtake.project.controller.contractnode;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.m2income.TPmIncomeNodeEntity;
import com.kingtake.project.entity.m2pay.TPmPayNodeEntity;
import com.kingtake.project.service.contractnode.TPmContractNodeServiceI;

/**
 * @Title: Controller
 * @Description: 合同节点信息
 * @author onlineGenerator
 * @date 2015-08-01 16:16:43
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractNodeController")
public class TPmContractNodeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractNodeController.class);

    @Autowired
    private TPmContractNodeServiceI tPmContractNodeService;
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
     * 合同节点信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmContractNode")
    public ModelAndView tPmContractNode(HttpServletRequest request) {
        String contractId = request.getParameter("contractId");
        request.setAttribute("contractId", contractId);

        String node = request.getParameter("node");
        if ("false".equals(node)) {
            request.setAttribute("node", false);
        } else {
            request.setAttribute("node", true);
        }

        return new ModelAndView("com/kingtake/project/contractnode/tPmContractNodeList");
    }

    /**
     * 合同节点指定来款节点 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "contractNodeRefToInOrPayNode")
    public ModelAndView contractNodeRefToInOrPayNode(HttpServletRequest request) {
        String inOutFlag = request.getParameter("inOutFlag");
        if ("i".equals(inOutFlag)) {
            request.setAttribute("inOutFlag", ProjectConstant.INCOME_CONTRACT);
        } else if ("o".equals(inOutFlag)) {
            request.setAttribute("inOutFlag", ProjectConstant.OUTCOME_CONTRACT);
        }
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractNodeRefToInOrPayNode");
    }

    /**
     * 合同节点指定来款节点 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "contractAndContractNode")
    public ModelAndView contractAndContractNode(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);

        String inOutFlag = request.getParameter("inOutFlag");
        request.setAttribute("inOutFlag", inOutFlag);

        if (ProjectConstant.INCOME_CONTRACT.equals(inOutFlag)) {
            request.setAttribute("income", true);
            request.setAttribute("title", "进账合同节点");
        } else {
            request.setAttribute("income", false);
            request.setAttribute("title", "出账合同节点");
        }

        return new ModelAndView("com/kingtake/project/contractnode/contractNodeListForRelatedCheck");
    }

    /**
     * 进账合同节点列表页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goIncomeContractNodeList")
    public ModelAndView goIncomeNodeList(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        request.setAttribute("inOutFlag", ProjectConstant.INCOME_CONTRACT);
        request.setAttribute("income", true);
        request.setAttribute("title", "进账合同节点");
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractAndContractNodeList");
    }
    
    /**
     * 选择进账合同节点页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tPmIncomeApplyNodeList")
    public ModelAndView tPmIncomeApplyNodeList(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String contractId = request.getParameter("contractId");
        request.setAttribute("projectId", projectId);
        request.setAttribute("contractId", contractId);
        request.setAttribute("inOutFlag", ProjectConstant.INCOME_CONTRACT);
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyNodeList");
    }
    
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridNode")
    public void datagridNode(TPmContractNodeEntity tPmContractNode, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
    	String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            String sql = "select b.* from T_PM_INCOME_CONTRACT_APPR a left join t_pm_contract_node b on a.id=b.in_out_contractid "
            		+ " where t_p_id='"+projectId+"'";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            dataGrid.setResults(list);
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 出账合同节点列表页面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goPayContractNodeList")
    public ModelAndView goPayContractNodeList(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        request.setAttribute("inOutFlag", ProjectConstant.OUTCOME_CONTRACT);
        request.setAttribute("income", false);
        request.setAttribute("title", "出账合同节点");
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractAndContractNodeList");
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
    public void datagrid(TPmContractNodeEntity tPmContractNode, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractNodeEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractNode,
                request.getParameterMap());
        try {
            //自定义追加查询条件
            String projectId = request.getParameter("projectId");
            if (StringUtil.isNotEmpty(projectId)) {
                String temp = null;
                if (ProjectConstant.INCOME_CONTRACT.equals(tPmContractNode.getInOutFlag())) {
                    temp = "T_PM_INCOME_CONTRACT_APPR";
                } else {
                    temp = "T_PM_OUTCOME_CONTRACT_APPR";
                }
                String sql = "SELECT ID FROM " + temp + " WHERE T_P_ID = '" + projectId + "'";
                List<String> contractIds = systemService.findListbySql(sql);
                if (contractIds != null && contractIds.size() > 0) {
                    cq.in("inOutContractid", contractIds.toArray());
                } else {
                    cq.eq("id", "index");
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmContractNodeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除合同节点信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmContractNodeEntity tPmContractNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmContractNode = systemService.getEntity(TPmContractNodeEntity.class, tPmContractNode.getId());
        message = "合同节点信息删除成功";
        try {
            tPmContractNodeService.deleteAddAttach(tPmContractNode);
        } catch (Exception e) {
            e.printStackTrace();
            message = "合同节点信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除合同节点信息
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "合同节点信息删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmContractNodeEntity tPmContractNode = systemService.getEntity(TPmContractNodeEntity.class, id);
                tPmContractNodeService.deleteAddAttach(tPmContractNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "合同节点信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加合同节点信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmContractNodeEntity tPmContractNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "合同节点信息添加成功";
        try {            
            //计算累计比例
            BigDecimal cumulativeProportion = new BigDecimal(0);
            if(tPmContractNode.getPayPercent() != null){
            	cumulativeProportion = cumulativeProportion.add(new BigDecimal(tPmContractNode.getPayPercent()));
                String sqlString = "select PAY_PERCENT from T_PM_CONTRACT_NODE where IN_OUT_CONTRACTID = '" + tPmContractNode.getInOutContractid() + "'";
                List<Map<String, Object>> tList = tPmContractNodeService.findForJdbc(sqlString);
                for (Map<String, Object> map : tList) {
                	String payPercentStr = map.get("PAY_PERCENT").toString();
                	BigDecimal payPercent = new BigDecimal(payPercentStr);
                	cumulativeProportion = cumulativeProportion.add(payPercent);
    			}
                tPmContractNode.setCumulativeProportion(cumulativeProportion.intValue());
            }            
            tPmContractNodeService.save(tPmContractNode);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "合同节点信息添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tPmContractNode);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新合同节点信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmContractNodeEntity tPmContractNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "合同节点信息更新成功";
        TPmContractNodeEntity t = tPmContractNodeService.get(TPmContractNodeEntity.class, tPmContractNode.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmContractNode, t);
            //计算累计比例
            BigDecimal cumulativeProportion = new BigDecimal(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            String createDateStr = sdf.format(t.getCreateDate());
            String sqlString = "select PAY_PERCENT from T_PM_CONTRACT_NODE where IN_OUT_CONTRACTID = '" + t.getInOutContractid() + "' and CREATE_DATE <= to_date('" + createDateStr + "','yyyy-mm-dd hh24:mi:ss')";
            List<Map<String, Object>> tList = tPmContractNodeService.findForJdbc(sqlString);
            for (Map<String, Object> map : tList) {
            	if(map.get("PAY_PERCENT") != null){
            		String payPercentStr = map.get("PAY_PERCENT").toString();
            		BigDecimal payPercent = new BigDecimal(payPercentStr);
            		cumulativeProportion = cumulativeProportion.add(payPercent);
            	}
			}
            t.setCumulativeProportion(cumulativeProportion.intValue());
            tPmContractNodeService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "合同节点信息更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新合同节点的关联来款或支付节点
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "updateInOutNodes")
    @ResponseBody
    public AjaxJson updateInOutNodes(TPmContractNodeEntity tPmContractNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "合同节点信息更新成功";
        TPmContractNodeEntity t = tPmContractNodeService.get(TPmContractNodeEntity.class, tPmContractNode.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmContractNode, t);
            tPmContractNodeService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "合同节点信息更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 合同节点信息新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmContractNodeEntity tPmContractNode, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmContractNode.getId())) {
            tPmContractNode = tPmContractNodeService.getEntity(TPmContractNodeEntity.class, tPmContractNode.getId());
        }
        String inOutContractid = tPmContractNode.getInOutContractid();
        if (StringUtil.isNotEmpty(inOutContractid)) {
            if (ProjectConstant.INCOME_CONTRACT.equals(tPmContractNode.getInOutFlag())) {
                TPmIncomeContractApprEntity contract = systemService.get(TPmIncomeContractApprEntity.class,
                        inOutContractid);
                if (contract != null) {
                    req.setAttribute("contractName", contract.getContractName());
                    req.setAttribute("projectId", contract.getProject().getId());
                    req.setAttribute("allFee", contract.getTotalFunds());
                }
            } else if (ProjectConstant.OUTCOME_CONTRACT.equals(tPmContractNode.getInOutFlag())) {
                TPmOutcomeContractApprEntity contract = systemService.get(TPmOutcomeContractApprEntity.class,
                        inOutContractid);
                if (contract != null) {
                    req.setAttribute("contractName", contract.getContractName());
                    req.setAttribute("projectId", contract.getProject().getId());
                    req.setAttribute("allFee", contract.getTotalFunds());
                }
            }
        }
        //附件
        if(StringUtils.isEmpty(tPmContractNode.getAttachmentCode())){
            tPmContractNode.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmContractNode.getAttachmentCode(), "");
            tPmContractNode.setCertificates(certificates);
        }
        req.setAttribute("tPmContractNodePage", tPmContractNode);
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractNode-add");
    }

    /**
     * 合同节点信息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmContractNodeEntity tPmContractNode, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmContractNode.getId())) {
            tPmContractNode = tPmContractNodeService.getEntity(TPmContractNodeEntity.class, tPmContractNode.getId());
            //附件
            if(StringUtils.isEmpty(tPmContractNode.getAttachmentCode())){
                tPmContractNode.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmContractNode.getAttachmentCode(), "");
                tPmContractNode.setCertificates(certificates);
            }
            req.setAttribute("tPmContractNodePage", tPmContractNode);

            if (ProjectConstant.INCOME_CONTRACT.equals(tPmContractNode.getInOutFlag())) {
                if (StringUtil.isNotEmpty(tPmContractNode.getInOutContractid())) {
                    TPmIncomeContractApprEntity contract = systemService.get(TPmIncomeContractApprEntity.class,
                            tPmContractNode.getInOutContractid());
                    if (contract != null) {
                        req.setAttribute("contractName", contract.getContractName());
                        req.setAttribute("projectId", contract.getProject().getId());
                    }
                }
                if (StringUtil.isNotEmpty(tPmContractNode.getProjPayNodeId())) {
                    TPmIncomeNodeEntity incomeNode = systemService.get(TPmIncomeNodeEntity.class,
                            tPmContractNode.getProjPayNodeId());
                    if (incomeNode != null) {
                        req.setAttribute(
                                "projPayNodeInfo",
                                DateUtils.date2Str(incomeNode.getIncomeTime(), DateUtils.date_sdf) + "来款"
                                        + incomeNode.getIncomeAmount() + "元");
                    }
                }
            } else if (ProjectConstant.OUTCOME_CONTRACT.equals(tPmContractNode.getInOutFlag())) {
                if (StringUtil.isNotEmpty(tPmContractNode.getInOutContractid())) {
                    TPmOutcomeContractApprEntity contract = systemService.get(TPmOutcomeContractApprEntity.class,
                            tPmContractNode.getInOutContractid());
                    if (contract != null) {
                        req.setAttribute("contractName", contract.getContractName());
                        req.setAttribute("projectId", contract.getProject().getId());
                    }
                }
                if (StringUtil.isNotEmpty(tPmContractNode.getProjPayNodeId())) {
                    TPmPayNodeEntity payNode = systemService.get(TPmPayNodeEntity.class,
                            tPmContractNode.getProjPayNodeId());
                    if (payNode != null) {
                        req.setAttribute(
                                "projPayNodeInfo",
                                DateUtils.date2Str(payNode.getPayTime(), DateUtils.date_sdf) + "支付"
                                        + payNode.getPayAmount() + "元");
                    }
                }

            }

            if (ProjectConstant.PROJECT_PLAN.equals(tPmContractNode.getPlanContractFlag())) {
                req.setAttribute("PROJECT_PLAN", true);
            } else {
                req.setAttribute("PROJECT_PLAN", false);
            }
        }
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractNode-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/contractnode/tPmContractNodeUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmContractNodeEntity tPmContractNode, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractNodeEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractNode,
                request.getParameterMap());
        List<TPmContractNodeEntity> tPmContractNodes = this.tPmContractNodeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "合同节点信息");
        modelMap.put(NormalExcelConstants.CLASS, TPmContractNodeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("合同节点信息列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmContractNodes);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmContractNodeEntity tPmContractNode, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "合同节点信息");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmContractNodeEntity.class);
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
                List<TPmContractNodeEntity> listTPmContractNodeEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmContractNodeEntity.class, params);
                for (TPmContractNodeEntity tPmContractNode : listTPmContractNodeEntitys) {
                    tPmContractNodeService.save(tPmContractNode);
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
     * 根据当前节点百分比计算支付金额
     * 
     * @return
     */
    @RequestMapping(params = "calPayAmount")
    @ResponseBody
    public AjaxJson calPayAmount(TPmContractNodeEntity tPmContractNode, HttpServletRequest req) {
    	AjaxJson j = new AjaxJson();
        String inOutContractid = tPmContractNode.getInOutContractid();
        if (StringUtil.isNotEmpty(inOutContractid)) {
        	TPmOutcomeContractApprEntity contract = systemService.get(TPmOutcomeContractApprEntity.class,
                    inOutContractid);
            if (contract != null) {
            	BigDecimal payPercent = new BigDecimal(tPmContractNode.getPayPercent());            	
            	BigDecimal payAmount = payPercent.multiply(contract.getTotalFunds());
            	BigDecimal percent = new BigDecimal(100);            	
            	payAmount = payAmount.divide(percent);
            	Map<String,Object> map = new HashMap<String,Object>();
            	map.put("payAmount", payAmount);
                j.setAttributes(map);
            }            
        }        
        return j;
    }
}
