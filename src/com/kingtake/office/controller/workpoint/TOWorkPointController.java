package com.kingtake.office.controller.workpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
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
import com.kingtake.office.entity.workpoint.TOWorkPointEntity;
import com.kingtake.office.service.workpoint.TOWorkPointServiceI;

/**
 * @Title: Controller
 * @Description: ????????????
 * @author onlineGenerator
 * @date 2016-04-05 15:42:44
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOWorkPointController")
public class TOWorkPointController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOWorkPointController.class);

    @Autowired
    private TOWorkPointServiceI tOWorkPointService;
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
    @RequestMapping(params = "tOWorkPoint")
    public ModelAndView tOWorkPoint(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        String uid = user.getId();
        request.setAttribute("uid", uid);
        request.setAttribute("uname", user.getUserName());
        request.setAttribute("submitNo", SrmipConstants.SUBMIT_NO);
        request.setAttribute("submitYes", SrmipConstants.SUBMIT_YES);
        request.setAttribute("submitReturn", SrmipConstants.SUBMIT_RETURN);
        request.setAttribute("submitReceive", SrmipConstants.SUBMIT_RECEIVE);
        return new ModelAndView("com/kingtake/office/workpoint/tOWorkPointList");
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
    public void datagrid(TOWorkPointEntity tOWorkPoint, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOWorkPointEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOWorkPoint, request.getParameterMap());
        try{
            //?????????????????????????????????????????????1-????????? 3-?????????
            String[] visibleFlagArr = {SrmipConstants.SUBMIT_YES, SrmipConstants.SUBMIT_RECEIVE};
            //???????????????????????????????????????????????????????????????????????????????????????visibleFlagArr???????????????????????????
            cq.or(Restrictions.eq("createBy", ResourceUtil.getSessionUserName().getUserName()), Restrictions.and(
                    Restrictions.eq("receiverId", ResourceUtil.getSessionUserName().getId()),
                            Restrictions.in("submitFlag", visibleFlagArr)));
            cq.addOrder("createDate", SortDirection.desc);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tOWorkPointService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TOWorkPointEntity tOWorkPoint, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tOWorkPoint.getId())) {
            message = "????????????????????????";
            try {
                this.tOWorkPointService.doSubmit(tOWorkPoint);
            } catch (Exception e) {
                e.printStackTrace();
                message = "????????????????????????";
                j.setSuccess(false);
            }
        }
        j.setObj(tOWorkPoint);
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOWorkPointEntity tOWorkPoint, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOWorkPoint = systemService.getEntity(TOWorkPointEntity.class, tOWorkPoint.getId());
        message = "????????????????????????";
        try {
            tOWorkPointService.delete(tOWorkPoint);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
                TOWorkPointEntity tOWorkPoint = systemService.getEntity(TOWorkPointEntity.class, id);
                tOWorkPointService.delete(tOWorkPoint);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
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
    public AjaxJson doAdd(TOWorkPointEntity tOWorkPoint, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            tOWorkPointService.save(tOWorkPoint);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
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
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOWorkPointEntity tOWorkPoint, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            if (StringUtils.isNotEmpty(tOWorkPoint.getId())) {
                TOWorkPointEntity t = tOWorkPointService.get(TOWorkPointEntity.class, tOWorkPoint.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOWorkPoint, t);
                tOWorkPointService.saveOrUpdate(t);
            } else {
                tOWorkPoint.setSubmitFlag("0");//?????????
                tOWorkPointService.save(tOWorkPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOWorkPointEntity tOWorkPoint, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOWorkPoint.getId())) {
            tOWorkPoint = tOWorkPointService.getEntity(TOWorkPointEntity.class, tOWorkPoint.getId());
            req.setAttribute("tOWorkPointPage", tOWorkPoint);
        }
        return new ModelAndView("com/kingtake/office/workpoint/tOWorkPoint-add");
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOWorkPointEntity tOWorkPoint, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOWorkPoint.getId())) {
            tOWorkPoint = tOWorkPointService.getEntity(TOWorkPointEntity.class, tOWorkPoint.getId());
            req.setAttribute("tOWorkPointPage", tOWorkPoint);
        }
        return new ModelAndView("com/kingtake/office/workpoint/tOWorkPoint-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/workpoint/tOWorkPointUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOWorkPointEntity tOWorkPoint, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOWorkPointEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOWorkPoint, request.getParameterMap());
        //?????????????????????????????????????????????1-????????? 3-?????????
        String[] visibleFlagArr = { SrmipConstants.SUBMIT_YES, SrmipConstants.SUBMIT_RECEIVE };
        TSUser user = ResourceUtil.getSessionUserName();
        //???????????????????????????????????????????????????????????????????????????????????????visibleFlagArr???????????????????????????
        cq.or(Restrictions.eq("createBy", user.getUserName()),
                Restrictions.and(Restrictions.eq("receiverId", ResourceUtil.getSessionUserName().getId()),
                        Restrictions.in("submitFlag", visibleFlagArr)));
        cq.addOrder("time", SortDirection.asc);
        cq.add();
        List<TOWorkPointEntity> workpoints = this.tOWorkPointService.getListByCriteriaQuery(cq, false);
        List<String> yearList = new ArrayList<String>();
        for (TOWorkPointEntity point : workpoints) {
            Date date = point.getTime();
            String year = DateUtil.formatDate(date, "yyyy");
            if (!yearList.contains(year)) {
                yearList.add(year);
            }
        }
        String yearStr = "";
        if (yearList.size() > 1) {
            Collections.sort(yearList);
            yearStr = yearList.get(0) + "~" + yearList.get(yearList.size() - 1);
        } else {
            yearStr = yearList.get(0);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("yearStr", yearStr);
        map.put("workpoints", workpoints);
        modelMap.put(TemplateWordConstants.FILE_NAME, "????????????_" + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "export/template/gzyd.docx");
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
    }

    /**
     * ?????????
     * 
     * @param content
     * @return
     */
    private String getVal(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        return content;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOWorkPointEntity tOWorkPoint, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOWorkPointEntity.class);
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
                List<TOWorkPointEntity> listTOWorkPointEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TOWorkPointEntity.class, params);
                for (TOWorkPointEntity tOWorkPoint : listTOWorkPointEntitys) {
                    tOWorkPointService.save(tOWorkPoint);
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
    @RequestMapping(params = "doRecieve")
    @ResponseBody
    public AjaxJson doRecieve(TOWorkPointEntity tOWorkPoint, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tOWorkPoint.getId())) {
            message = "????????????????????????";
            TOWorkPointEntity t = tOWorkPointService.get(TOWorkPointEntity.class, tOWorkPoint.getId());
            try {
                // ???????????????????????????
                MyBeanUtils.copyBeanNotNull2Bean(tOWorkPoint, t);
                //???????????????????????????????????????3-?????????????????????????????????????????????
                t.setSubmitFlag(SrmipConstants.SUBMIT_RECEIVE);
                t.setReceiveTime(DateUtils.getDate());
                tOWorkPointService.updateEntitie(t);
            } catch (Exception e) {
                e.printStackTrace();
                message = "????????????????????????";
                j.setSuccess(false);
            }
        }
        j.setObj(tOWorkPoint);
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
    public AjaxJson doReturn(TOWorkPointEntity tOWorkPoint, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tOWorkPoint.getId())) {
            message = "????????????????????????";
            TOWorkPointEntity t = tOWorkPointService.get(TOWorkPointEntity.class, tOWorkPoint.getId());
            try {
                // ???????????????????????????
                MyBeanUtils.copyBeanNotNull2Bean(tOWorkPoint, t);
                //?????????????????????????????????????????????2-????????????????????????????????????????????????????????????null
                t.setSubmitFlag(SrmipConstants.SUBMIT_RETURN);
                t.setSubmitTime(DateUtils.getDate());
                t.setReceiveTime(null);
                tOWorkPointService.updateEntitie(t);
            } catch (Exception e) {
                e.printStackTrace();
                message = "????????????????????????";
                j.setSuccess(false);
            }
        }
        j.setObj(tOWorkPoint);
        j.setMsg(message);
        return j;
    }

}
