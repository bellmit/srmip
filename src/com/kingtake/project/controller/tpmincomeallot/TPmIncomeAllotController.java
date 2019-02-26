package com.kingtake.project.controller.tpmincomeallot;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.tpmincomeallot.TPmIncomeAllotServiceI;

/**
 * @Title: Controller
 * @Description: 到账分配信息表
 * @author onlineGenerator
 * @date 2015-12-03 11:11:47
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmIncomeAllotController")
public class TPmIncomeAllotController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmIncomeAllotController.class);

    @Autowired
    private TPmIncomeAllotServiceI tPmIncomeAllotService;
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
     * 到账分配信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeAllot")
    public ModelAndView tPmIncomeAllot(HttpServletRequest request) {
        String incomeId = request.getParameter("incomeId");
        request.setAttribute("incomeId", incomeId);
        return new ModelAndView("com/kingtake/project/tpmincomeallot/tPmIncomeAllotList");
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
    public void datagrid(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeAllotEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeAllot,
                request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmIncomeAllotService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除到账分配信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomeAllot = systemService.getEntity(TPmIncomeAllotEntity.class, tPmIncomeAllot.getId());
        message = "到账分配信息表删除成功";
        try {
            tPmIncomeAllotService.delete(tPmIncomeAllot);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "到账分配信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除到账分配信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "到账分配信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmIncomeAllotEntity tPmIncomeAllot = systemService.getEntity(TPmIncomeAllotEntity.class, id);
                tPmIncomeAllotService.delete(tPmIncomeAllot);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "到账分配信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加到账分配信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "到账分配信息表添加成功";
        try {
            tPmIncomeAllot.setBalance(tPmIncomeAllot.getAmount());
            tPmIncomeAllotService.saveUpdateIncomeAllot(tPmIncomeAllot);
        } catch (Exception e) {
            e.printStackTrace();
            message = "到账分配信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
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
     * 更新到账分配信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "到账分配信息表更新成功";
        TPmIncomeAllotEntity t = tPmIncomeAllotService.get(TPmIncomeAllotEntity.class, tPmIncomeAllot.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmIncomeAllot, t);
            tPmIncomeAllotService.saveUpdateIncomeAllot(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "到账分配信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 到账分配信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest req) {
        String incomeId = req.getParameter("incomeId");
        TPmIncomeEntity income = systemService.get(TPmIncomeEntity.class, incomeId);
        req.setAttribute("income", income);
        if (StringUtil.isNotEmpty(tPmIncomeAllot.getId())) {
            tPmIncomeAllot = tPmIncomeAllotService.getEntity(TPmIncomeAllotEntity.class, tPmIncomeAllot.getId());
            req.setAttribute("tPmIncomeAllotPage", tPmIncomeAllot);
        }
        return new ModelAndView("com/kingtake/project/tpmincomeallot/tPmIncomeAllot-add");
    }

    /**
     * 到账分配信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncomeAllot.getId())) {
            tPmIncomeAllot = tPmIncomeAllotService.getEntity(TPmIncomeAllotEntity.class, tPmIncomeAllot.getId());
            req.setAttribute("tPmIncomeAllotPage", tPmIncomeAllot);
            TPmIncomeEntity income = tPmIncomeAllot.getIncome();
            income.setBalance(tPmIncomeAllot.getAmount().add(income.getBalance()));//修改时可分配金额为主表余额+本记录已分配金额
            req.setAttribute("income", income);
        }
        return new ModelAndView("com/kingtake/project/tpmincomeallot/tPmIncomeAllot-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/tpmincomeallot/tPmIncomeAllotUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeAllotEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeAllot,
                request.getParameterMap());
        List<TPmIncomeAllotEntity> tPmIncomeAllots = this.tPmIncomeAllotService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "到账分配信息表");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeAllotEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("到账分配信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomeAllots);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "到账分配信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmIncomeAllotEntity.class);
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
                List<TPmIncomeAllotEntity> listTPmIncomeAllotEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmIncomeAllotEntity.class, params);
                for (TPmIncomeAllotEntity tPmIncomeAllot : listTPmIncomeAllotEntitys) {
                    tPmIncomeAllotService.save(tPmIncomeAllot);
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
