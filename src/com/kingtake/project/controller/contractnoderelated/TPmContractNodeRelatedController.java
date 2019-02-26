package com.kingtake.project.controller.contractnoderelated;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.contractnoderelated.TPmContractNodeRelatedEntity;
import com.kingtake.project.entity.m2income.TPmIncomeNodeEntity;
import com.kingtake.project.entity.m2pay.TPmPayNodeEntity;
import com.kingtake.project.service.contractnoderelated.TPmContractNodeRelatedServiceI;

/**
 * @Title: Controller
 * @Description: T_PM_CONTRACT_NODE_RELATED
 * @author onlineGenerator
 * @date 2015-12-01 14:30:53
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmContractNodeRelatedController")
public class TPmContractNodeRelatedController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmContractNodeRelatedController.class);

    @Autowired
    private TPmContractNodeRelatedServiceI tPmContractNodeRelatedService;
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
     * T_PM_CONTRACT_NODE_RELATED列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmContractNodeRelated")
    public ModelAndView tPmContractNodeRelated(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/contractnoderelated/tPmContractNodeRelatedList");
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
    public void datagrid(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        try {
            TPmContractNodeEntity contractNode = systemService.get(TPmContractNodeEntity.class,
                    tPmContractNodeRelated.getContractNodeId());
            StringBuffer sb = new StringBuffer();
            StringBuffer cb = new StringBuffer();
            if ("i".equals(contractNode.getInOutFlag())) {
                sb.append("SELECT R.ID AS RID,R.AMOUNT,R.AUDIT_FLAG AS AUDITFLAG,I.ID AS ID,I.INCOME_TIME AS TIME,I.INCOME_AMOUNT AS BALANCE FROM T_PM_CONTRACT_NODE_RELATED R,T_PM_INCOME_NODE I WHERE R.CONTRACT_NODE_ID='"
                        + contractNode.getId() + "'AND R.INCOME_PAY_NODE_ID =I.ID");
                cb.append("SELECT COUNT(0) FROM T_PM_CONTRACT_NODE_RELATED R,T_PM_INCOME_NODE I WHERE R.CONTRACT_NODE_ID='"
                        + contractNode.getId() + "'AND R.INCOME_PAY_NODE_ID =I.ID");
            } else {
                sb.append("SELECT R.ID AS RID,R.AMOUNT,R.AUDIT_FLAG AS AUDITFLAG,I.ID AS ID,I.PAY_TIME AS TIME,I.PAY_AMOUNT AS BALANCE FROM T_PM_CONTRACT_NODE_RELATED R,T_PM_PAY_NODE I WHERE R.CONTRACT_NODE_ID='"
                        + contractNode.getId() + "'AND R.INCOME_PAY_NODE_ID =I.ID");
                cb.append("SELECT COUNT(0) FROM T_PM_CONTRACT_NODE_RELATED R,T_PM_PAY_NODE I WHERE R.CONTRACT_NODE_ID='"
                        + contractNode.getId() + "'AND R.INCOME_PAY_NODE_ID =I.ID");
            }
            List<Map<String, Object>> result = systemService.findForJdbc(sb.toString(), dataGrid.getPage(),
                    dataGrid.getRows());
            Long count = systemService.getCountForJdbc(cb.toString());
            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 更新来款节点管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateAuditFlag")
    @ResponseBody
    public AjaxJson doUpdateAuditFlag(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "节点管理更新成功";
        TPmContractNodeRelatedEntity t = systemService.get(TPmContractNodeRelatedEntity.class,
                tPmContractNodeRelated.getId());
        if (SrmipConstants.YES.equals(t.getAuditFlag())) {
            //操作：取消审核
            t.setAuditFlag(SrmipConstants.NO);
            t.setAuditUserid(null);
            t.setAuditUsername(null);
            t.setAuditTime(null);
        } else {
            //操作：审核
            t.setAuditFlag(SrmipConstants.YES);
            t.setAuditUserid(ResourceUtil.getSessionUserName().getId());
            t.setAuditUsername(ResourceUtil.getSessionUserName().getRealName());
            t.setAuditTime(new Date());
        }
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmContractNodeRelated, t);
            systemService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "节点管理更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 删除T_PM_CONTRACT_NODE_RELATED
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmContractNodeRelated = systemService.getEntity(TPmContractNodeRelatedEntity.class,
                tPmContractNodeRelated.getId());
        message = "节点指定取消成功";
        try {
            tPmContractNodeRelatedService.delete(tPmContractNodeRelated);
            TPmContractNodeEntity contractNode = systemService.get(TPmContractNodeEntity.class,
                    tPmContractNodeRelated.getContractNodeId());
            if ("i".equals(contractNode.getInOutFlag())) {
                updateIncomeNodeBalance(tPmContractNodeRelated.getIncomePayNodeId());
            } else {
                updatePayNodeBalance(tPmContractNodeRelated.getIncomePayNodeId());
            }
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "节点指定取消失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除T_PM_CONTRACT_NODE_RELATED
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "节点指定取消成功";
        try {
            for (String id : ids.split(",")) {
                TPmContractNodeRelatedEntity tPmContractNodeRelated = systemService.getEntity(
                        TPmContractNodeRelatedEntity.class, id);
                tPmContractNodeRelatedService.delete(tPmContractNodeRelated);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "节点指定取消失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加T_PM_CONTRACT_NODE_RELATED
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "节点指定成功！";
        try {
            tPmContractNodeRelated.setAuditFlag(SrmipConstants.NO);
            tPmContractNodeRelatedService.save(tPmContractNodeRelated);
            TPmContractNodeEntity contractNode = systemService.get(TPmContractNodeEntity.class,
                    tPmContractNodeRelated.getContractNodeId());
            if ("i".equals(contractNode.getInOutFlag())) {
                updateIncomeNodeBalance(tPmContractNodeRelated.getIncomePayNodeId());
            } else {
                updatePayNodeBalance(tPmContractNodeRelated.getIncomePayNodeId());
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "节点指定失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    public void updateIncomeNodeBalance(String inComeNodeId) {
        TPmIncomeNodeEntity incomeNode = systemService.get(TPmIncomeNodeEntity.class, inComeNodeId);
        CriteriaQuery cq = new CriteriaQuery(TPmContractNodeRelatedEntity.class);
        cq.eq("incomePayNodeId", inComeNodeId);
        cq.add();
        List<TPmContractNodeRelatedEntity> list = systemService.getListByCriteriaQuery(cq, false);
        BigDecimal balance = new BigDecimal(0);
        for (TPmContractNodeRelatedEntity r : list) {
            balance = balance.add(r.getAmount());
        }
        incomeNode.setBalance(incomeNode.getIncomeAmount().subtract(balance));
        systemService.updateEntitie(incomeNode);//保存成功时更新来款节点中可指定金额
    }

    public void updatePayNodeBalance(String payNodeId) {
        TPmPayNodeEntity payNode = systemService.get(TPmPayNodeEntity.class, payNodeId);
        CriteriaQuery cq = new CriteriaQuery(TPmContractNodeRelatedEntity.class);
        cq.eq("incomePayNodeId", payNodeId);
        cq.add();
        List<TPmContractNodeRelatedEntity> list = systemService.getListByCriteriaQuery(cq, false);
        BigDecimal balance = new BigDecimal(0);
        for (TPmContractNodeRelatedEntity r : list) {
            balance = balance.add(r.getAmount());
        }
        payNode.setBalance(payNode.getPayAmount().subtract(balance));
        systemService.updateEntitie(payNode);//保存成功时更新支付节点中可指定金额
    }

    /**
     * 更新T_PM_CONTRACT_NODE_RELATED
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "节点指定更新成功";
        TPmContractNodeRelatedEntity t = tPmContractNodeRelatedService.get(TPmContractNodeRelatedEntity.class,
                tPmContractNodeRelated.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmContractNodeRelated, t);
            tPmContractNodeRelatedService.saveOrUpdate(t);
            TPmContractNodeEntity contractNode = systemService.get(TPmContractNodeEntity.class, t.getContractNodeId());
            if ("i".equals(contractNode.getInOutFlag())) {
                updateIncomeNodeBalance(t.getIncomePayNodeId());
            } else {
                updatePayNodeBalance(t.getIncomePayNodeId());
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "节点指定更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * T_PM_CONTRACT_NODE_RELATED新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest req) {
        String tpid = req.getParameter("tpId");
        String contractNodeId = req.getParameter("contractNodeId");
        if (StringUtil.isNotEmpty(tPmContractNodeRelated.getId())) {
            tPmContractNodeRelated = tPmContractNodeRelatedService.getEntity(TPmContractNodeRelatedEntity.class,
                    tPmContractNodeRelated.getId());
            req.setAttribute("tPmContractNodeRelatedPage", tPmContractNodeRelated);
        }
        req.setAttribute("tpid", tpid);
        req.setAttribute("contractNodeId", contractNodeId);
        return new ModelAndView("com/kingtake/project/contractnoderelated/tPmContractNodeRelated-add");
    }

    @RequestMapping(params = "assign")
    public ModelAndView assign(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest req) {
        String tpid = req.getParameter("tpId");
        String contractNodeId = req.getParameter("contractNodeId");
        if (StringUtil.isNotEmpty(tPmContractNodeRelated.getId())) {
            tPmContractNodeRelated = tPmContractNodeRelatedService.getEntity(TPmContractNodeRelatedEntity.class,
                    tPmContractNodeRelated.getId());
            req.setAttribute("tPmContractNodeRelatedPage", tPmContractNodeRelated);
        }
        req.setAttribute("tpid", tpid);
        req.setAttribute("contractNodeId", contractNodeId);
        return new ModelAndView("com/kingtake/project/contractnoderelated/tPmContractNodeRelated-assign");
    }

    /**
     * 节点指定列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAssign")
    public ModelAndView goAssign(HttpServletRequest req) {
        String tpid = req.getParameter("tpid");
        String contractNodeId = req.getParameter("contractNodeId");
        TPmContractNodeEntity contractNode = systemService.get(TPmContractNodeEntity.class, contractNodeId);
        String inOutFlag = contractNode.getInOutFlag();
        req.setAttribute("tpid", tpid);
        req.setAttribute("contractNodeId", contractNodeId);
        req.setAttribute("inOutFlag", inOutFlag);
        return new ModelAndView("com/kingtake/project/contractnoderelated/tPmContractNodeRelated-assign");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "assignDatagrid")
    public void assignDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String tpid = request.getParameter("tpid");
        String contractNodeId = request.getParameter("contractNodeId");
        String inOutFlag = request.getParameter("inOutFlag");
        StringBuffer sb = new StringBuffer();
        StringBuffer cb = new StringBuffer();
        if ("i".equals(inOutFlag)) {
            sb.append("SELECT '' as assign,T.ID,T.INCOME_TIME AS TIME,T.INCOME_AMOUNT AS AMOUNT,T.CREATE_NAME,T.BALANCE FROM T_PM_INCOME_NODE T WHERE T.T_P_ID = '"
                    + tpid + "' ");
            cb.append("SELECT COUNT(0) FROM T_PM_INCOME_NODE T WHERE T.T_P_ID = '" + tpid + "' ");
        } else {
            sb.append("SELECT '' as assign,T.ID,T.PAY_TIME AS TIME,T.PAY_AMOUNT AS AMOUNT,T.CREATE_NAME,T.BALANCE FROM T_PM_PAY_NODE T WHERE T.T_P_ID = '"
                    + tpid + "' ");
            cb.append("SELECT COUNT(0) FROM T_PM_PAY_NODE T WHERE T.T_P_ID = '" + tpid + "' ");
        }
        sb.append(" AND T.AUDIT_FLAG ='1' AND T.ID NOT IN ( SELECT R.INCOME_PAY_NODE_ID  FROM T_PM_CONTRACT_NODE_RELATED R WHERE R.CONTRACT_NODE_ID = '"
                + contractNodeId + "')");
        cb.append(" AND T.AUDIT_FLAG ='1' AND T.ID NOT IN ( SELECT R.INCOME_PAY_NODE_ID  FROM T_PM_CONTRACT_NODE_RELATED R WHERE R.CONTRACT_NODE_ID = '"
                + contractNodeId + "')");
        List<Map<String, Object>> result = systemService.findForJdbc(sb.toString(), dataGrid.getPage(),
                dataGrid.getRows());
        Long count = systemService.getCountForJdbc(cb.toString());
        dataGrid.setResults(result);
        dataGrid.setTotal(count.intValue());
        TagUtil.listToJson(response, result);
    }

    /**
     * T_PM_CONTRACT_NODE_RELATED编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest req) {
        String tpid = req.getParameter("tpid");
        req.setAttribute("tpid", tpid);
        if (StringUtil.isNotEmpty(tPmContractNodeRelated.getId())) {
            tPmContractNodeRelated = tPmContractNodeRelatedService.getEntity(TPmContractNodeRelatedEntity.class,
                    tPmContractNodeRelated.getId());
            TPmContractNodeEntity contractNode = systemService.get(TPmContractNodeEntity.class,
                    tPmContractNodeRelated.getContractNodeId());
            String inOutFlag = contractNode.getInOutFlag();
            if ("i".equals(inOutFlag)) {
                TPmIncomeNodeEntity inconeNode = systemService.get(TPmIncomeNodeEntity.class,
                        tPmContractNodeRelated.getIncomePayNodeId());
                req.setAttribute("balance", inconeNode.getBalance());
            } else {
                TPmPayNodeEntity payNode = systemService.get(TPmPayNodeEntity.class,
                        tPmContractNodeRelated.getIncomePayNodeId());
                req.setAttribute("balance", payNode.getBalance());
            }
            req.setAttribute("tPmContractNodeRelatedPage", tPmContractNodeRelated);
        }
        return new ModelAndView("com/kingtake/project/contractnoderelated/tPmContractNodeRelated-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/contractnoderelated/tPmContractNodeRelatedUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmContractNodeRelatedEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmContractNodeRelated,
                request.getParameterMap());
        List<TPmContractNodeRelatedEntity> tPmContractNodeRelateds = this.tPmContractNodeRelatedService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "T_PM_CONTRACT_NODE_RELATED");
        modelMap.put(NormalExcelConstants.CLASS, TPmContractNodeRelatedEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("T_PM_CONTRACT_NODE_RELATED列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmContractNodeRelateds);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmContractNodeRelatedEntity tPmContractNodeRelated, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "T_PM_CONTRACT_NODE_RELATED");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmContractNodeRelatedEntity.class);
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
                List<TPmContractNodeRelatedEntity> listTPmContractNodeRelatedEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmContractNodeRelatedEntity.class, params);
                for (TPmContractNodeRelatedEntity tPmContractNodeRelated : listTPmContractNodeRelatedEntitys) {
                    tPmContractNodeRelatedService.save(tPmContractNodeRelated);
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
}
