package com.kingtake.office.controller.holiday;

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

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.holiday.TOHolidayPlanEntity;
import com.kingtake.office.service.holiday.TOHolidayPlanServiceI;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2016-05-18 17:28:12
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOHolidayPlanController")
public class TOHolidayPlanController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOHolidayPlanController.class);

    @Autowired
    private TOHolidayPlanServiceI tOHolidayPlanService;
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
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tOHolidayPlan")
    public ModelAndView tOHolidayPlan(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        String uid = user.getId();
        request.setAttribute("uid", uid);
        request.setAttribute("uname", user.getUserName());
        request.setAttribute("submitNo", SrmipConstants.SUBMIT_NO);
        request.setAttribute("submitYes", SrmipConstants.SUBMIT_YES);
        request.setAttribute("submitReturn", SrmipConstants.SUBMIT_RETURN);
        request.setAttribute("submitReceive", SrmipConstants.SUBMIT_RECEIVE);
        return new ModelAndView("com/kingtake/office/holiday/tOHolidayPlanList");
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
    public void datagrid(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOHolidayPlanEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOHolidayPlan,
                request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tOHolidayPlanService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOHolidayPlan = systemService.getEntity(TOHolidayPlanEntity.class, tOHolidayPlan.getId());
        message = "??????????????????????????????";
        try {
            tOHolidayPlanService.delete(tOHolidayPlan);
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
                TOHolidayPlanEntity tOHolidayPlan = systemService.getEntity(TOHolidayPlanEntity.class, id);
                tOHolidayPlanService.delete(tOHolidayPlan);
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
    public AjaxJson doAdd(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            tOHolidayPlanService.save(tOHolidayPlan);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
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
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            if (StringUtils.isEmpty(tOHolidayPlan.getId())) {
                this.systemService.save(tOHolidayPlan);
            } else {
                TOHolidayPlanEntity t = tOHolidayPlanService.get(TOHolidayPlanEntity.class, tOHolidayPlan.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOHolidayPlan, t);
                tOHolidayPlanService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOHolidayPlan.getId())) {
            tOHolidayPlan = tOHolidayPlanService.getEntity(TOHolidayPlanEntity.class, tOHolidayPlan.getId());
            req.setAttribute("tOHolidayPlanPage", tOHolidayPlan);
        }
        return new ModelAndView("com/kingtake/office/holiday/tOHolidayPlan-add");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOHolidayPlan.getId())) {
            tOHolidayPlan = tOHolidayPlanService.getEntity(TOHolidayPlanEntity.class, tOHolidayPlan.getId());
            req.setAttribute("tOHolidayPlanPage", tOHolidayPlan);
        }
        return new ModelAndView("com/kingtake/office/holiday/tOHolidayPlan-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/holiday/tOHolidayPlanUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOHolidayPlanEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOHolidayPlan,
                request.getParameterMap());
        List<TOHolidayPlanEntity> tOHolidayPlans = this.tOHolidayPlanService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TOHolidayPlanEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tOHolidayPlans);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOHolidayPlanEntity tOHolidayPlan, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOHolidayPlanEntity.class);
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
                List<TOHolidayPlanEntity> listTOHolidayPlanEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TOHolidayPlanEntity.class, params);
                for (TOHolidayPlanEntity tOHolidayPlan : listTOHolidayPlanEntitys) {
                    tOHolidayPlanService.save(tOHolidayPlan);
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
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TOHolidayPlanEntity holidayPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(holidayPlan.getId())) {
            message = "??????????????????????????????";
            try {
                this.tOHolidayPlanService.doSubmit(holidayPlan);
            } catch (Exception e) {
                e.printStackTrace();
                message = "??????????????????????????????";
                j.setSuccess(false);
            }
        }
        j.setObj(holidayPlan);
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doRecieve")
    @ResponseBody
    public AjaxJson doRecieve(TOHolidayPlanEntity holidayPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(holidayPlan.getId())) {
            message = "?????????????????????????????????";
            TOHolidayPlanEntity t = tOHolidayPlanService.get(TOHolidayPlanEntity.class, holidayPlan.getId());
            try {
                // ???????????????????????????
                MyBeanUtils.copyBeanNotNull2Bean(holidayPlan, t);
                //???????????????????????????????????????3-?????????????????????????????????????????????
                t.setSubmitFlag(SrmipConstants.SUBMIT_RECEIVE);
                t.setReceiveTime(DateUtils.getDate());
                tOHolidayPlanService.updateEntitie(t);
            } catch (Exception e) {
                e.printStackTrace();
                message = "?????????????????????????????????";
                j.setSuccess(false);
            }
        }
        j.setObj(holidayPlan);
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doReturn")
    @ResponseBody
    public AjaxJson doReturn(TOHolidayPlanEntity holidayPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(holidayPlan.getId())) {
            message = "??????????????????????????????";
            try {
                TOHolidayPlanEntity t = tOHolidayPlanService.get(TOHolidayPlanEntity.class, holidayPlan.getId());
                t.setSubmitFlag(SrmipConstants.SUBMIT_RETURN);
                t.setSubmitTime(DateUtils.getDate());
                t.setReceiveTime(null);
                t.setMsgText(holidayPlan.getMsgText());
                tOHolidayPlanService.updateEntitie(t);
            } catch (Exception e) {
                e.printStackTrace();
                message = "??????????????????????????????";
                j.setSuccess(false);
            }
        }
        //j.setObj(holidayPlan);
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goSelectOperator")
    public ModelAndView goSelectOperator(HttpServletRequest request) {
        String mode = request.getParameter("mode");
        if (StringUtils.isNotEmpty(mode)) {
            request.setAttribute("mode", mode);
        } else {
            request.setAttribute("mode", "single");//??????????????????
        }
        return new ModelAndView("com/kingtake/office/holiday/selectOperator");
    }


    /**
     * ??????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TOHolidayPlanEntity apply = this.systemService.get(TOHolidayPlanEntity.class, id);
            req.setAttribute("msgText", apply.getMsgText());
        }
        return new ModelAndView("com/kingtake/project/plandraft/proposePage");
    }
}
