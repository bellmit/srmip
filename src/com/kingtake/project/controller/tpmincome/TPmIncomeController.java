package com.kingtake.project.controller.tpmincome;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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

import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.tpmincome.TPmIncomeServiceI;
import com.thoughtworks.xstream.mapper.Mapper.Null;

/**
 * @Title: Controller
 * @Description: ???????????????
 * @author onlineGenerator
 * @date 2015-12-02 18:36:38
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmIncomeController")
public class TPmIncomeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmIncomeController.class);

    @Autowired
    private TPmIncomeServiceI tPmIncomeService;
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
     * ????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncome")
    public ModelAndView tPmIncome(HttpServletRequest request) {
    	String loadType = request.getParameter("loadType");
    	if(StringUtil.isNotEmpty(loadType)){
    		request.setAttribute("loadType", loadType);
    	}    	
        return new ModelAndView("com/kingtake/project/tpmincome/tPmIncomeList");
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
    public void datagrid(TPmIncomeEntity tPmIncome, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
    	//????????????????????????????????????????????????????????????
    	String loadType = request.getParameter("loadType");
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncome, request.getParameterMap());
        try {
            //???????????????????????????
            cq.addOrder("createDate", SortDirection.desc);
            if(loadType.equals("HT")){
            	cq.isNull("project.id");
            }else if (loadType.equals("JH")){
            	cq.isNotNull("project.id");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmIncomeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
    /**
     * easyui AJAX????????????  ??????????????????=====lijun??????????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid2")
    public void datagrid2(TPmIncomeEntity tPmIncome, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
    	String voucherNo = request.getParameter("voucherNo");
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeEntity.class, dataGrid);
        try {
        	cq.in("certificate", voucherNo.split(","));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmIncomeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ?????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmIncomeEntity tPmIncome, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        List<TPmIncomeAllotEntity> incomeAllotList = this.systemService.findByProperty(TPmIncomeAllotEntity.class,
                "income.id", tPmIncome.getId());
        if (incomeAllotList.size() > 0) {
            j.setMsg("???????????????????????????????????????");
            return j;
        }
        tPmIncome = systemService.getEntity(TPmIncomeEntity.class, tPmIncome.getId());
        message = "???????????????????????????";
        try {
            tPmIncomeService.delete(tPmIncome);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmIncomeEntity tPmIncome = systemService.getEntity(TPmIncomeEntity.class, id);
                tPmIncomeService.delete(tPmIncome);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmIncomeEntity tPmIncome, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            tPmIncome.setBalance(tPmIncome.getIncomeAmount());
            tPmIncomeService.save(tPmIncome);
            TPmIncomeApplyEntity incomeApplyEntity = new TPmIncomeApplyEntity();
            if(StringUtil.isNotEmpty(tPmIncome.getProject().getId())){
            	//incomeApplyEntity.setProjectId(tPmIncome.getProject().getId());
            }
            if(StringUtil.isNotEmpty(tPmIncome.getCertificate())){
            	incomeApplyEntity.setVoucherNo(tPmIncome.getCertificate());
            }
            if(StringUtil.isNotEmpty(tPmIncome.getIncomeAmount())){
            	incomeApplyEntity.setApplyAmount(tPmIncome.getIncomeAmount());
            }
            systemService.save(incomeApplyEntity);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmIncomeEntity tPmIncome, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        TPmIncomeEntity t = tPmIncomeService.get(TPmIncomeEntity.class, tPmIncome.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmIncome, t);
            tPmIncomeService.saveOrUpdate(t);
            updateIncomeBalance(t.getId());
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
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
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmIncomeEntity tPmIncome, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncome.getId())) {
            tPmIncome = tPmIncomeService.getEntity(TPmIncomeEntity.class, tPmIncome.getId());
            req.setAttribute("tPmIncomePage", tPmIncome);
        }
        //????????????????????????????????????????????????????????????
    	String loadType = req.getParameter("loadType");
    	if (StringUtil.isNotEmpty(loadType)) {            
            req.setAttribute("loadType", loadType);
        }
        return new ModelAndView("com/kingtake/project/tpmincome/tPmIncome-add");
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmIncomeEntity tPmIncome, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncome.getId())) {
            tPmIncome = tPmIncomeService.getEntity(TPmIncomeEntity.class, tPmIncome.getId());
            BigDecimal minAmount = getMinAmount(tPmIncome.getId());
            req.setAttribute("minAmount", minAmount);
            req.setAttribute("tPmIncomePage", tPmIncome);
        }
        //????????????????????????????????????????????????????????????
    	String loadType = req.getParameter("loadType");
    	if (StringUtil.isNotEmpty(loadType)) {            
            req.setAttribute("loadType", loadType);
        }
        return new ModelAndView("com/kingtake/project/tpmincome/tPmIncome-update");
    }

    public BigDecimal getMinAmount(String incomeId) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeAllotEntity.class);
        cq.eq("income.id", incomeId);
        cq.add();
        List<TPmIncomeAllotEntity> list = systemService.getListByCriteriaQuery(cq, false);
        BigDecimal balance = new BigDecimal(0);
        for (TPmIncomeAllotEntity allot : list) {
            balance = balance.add(allot.getAmount());
        }
        return balance;
    }

    public void updateIncomeBalance(String incomeId) {
        TPmIncomeEntity income = systemService.get(TPmIncomeEntity.class, incomeId);
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeAllotEntity.class);
        cq.eq("income.id", income.getId());
        cq.add();
        List<TPmIncomeAllotEntity> list = systemService.getListByCriteriaQuery(cq, false);
        BigDecimal balance = new BigDecimal(0);
        for (TPmIncomeAllotEntity allot : list) {
            balance = balance.add(allot.getAmount());
        }
        income.setBalance(income.getIncomeAmount().subtract(balance));
        systemService.updateEntitie(income);

    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/tpmincome/tPmIncomeUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmIncomeEntity tPmIncome, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncome, request.getParameterMap());
        List<TPmIncomeEntity> tPmIncomes = this.tPmIncomeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "???????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomes);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public void exportXlsByT(HttpServletRequest request, HttpServletResponse response) {
    	tPmIncomeService.downloadTemplate(request,response);
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
            try {
            	String msg = "";
                HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
                    msg = tPmIncomeService.importExcel(wb);
                j.setMsg("?????????????????????<br>" + msg);
            } catch (Exception e) {
                j.setMsg("?????????????????????");
                e.printStackTrace();
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
