package com.kingtake.office.controller.summary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import com.kingtake.office.entity.researchactivity.TOReceiveLogEntity;
import com.kingtake.office.entity.summary.TOSummaryEntity;
import com.kingtake.office.service.summary.TOSummaryServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

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
@RequestMapping("/tOSummaryController")
public class TOSummaryController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOSummaryController.class);

    @Autowired
    private TOSummaryServiceI tOSummaryService;
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
    @RequestMapping(params = "tOSummary")
    public ModelAndView tOSummary(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        String uid = user.getId();
        request.setAttribute("uid", uid);
        request.setAttribute("uname", user.getUserName());
        request.setAttribute("submitNo", SrmipConstants.SUBMIT_NO);
        request.setAttribute("submitYes", SrmipConstants.SUBMIT_YES);
        request.setAttribute("submitReturn", SrmipConstants.SUBMIT_RETURN);
        request.setAttribute("submitReceive", SrmipConstants.SUBMIT_RECEIVE);
        return new ModelAndView("com/kingtake/office/summary/tOSummaryList");
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
    public void datagrid(TOSummaryEntity tOSummary, HttpServletRequest request,
            HttpServletResponse response,
            DataGrid dataGrid) {
        String delFlag = request.getParameter("df");
        CriteriaQuery cq = new CriteriaQuery(TOSummaryEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSummary,
                request.getParameterMap());
        try{
            TSUser user = ResourceUtil.getSessionUserName();
            List<TOReceiveLogEntity> logList = this.systemService.findByProperty(TOReceiveLogEntity.class,
                    "receiverId", user.getId());
            Set<String> logIdSet = new HashSet<String>();
            for (TOReceiveLogEntity log : logList) {
                logIdSet.add(log.getResearchId());
            }
            if (logIdSet.size() == 0) {
                logIdSet.add("id");
            }
            //?????????????????????????????????????????????????????????
            cq.or(Restrictions.eq("createBy", user.getUserName()), Restrictions.in("id", logIdSet.toArray()));
            if ("0".equals(delFlag)) {//?????????
                cq.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", "0"));
            } else {
                cq.eq("delFlag", "1");//?????????
            }
            cq.addOrder("createDate", SortDirection.desc);
            cq.add();
            this.tOSummaryService.getDataGridReturn(cq, true);
            List<TOSummaryEntity> results = dataGrid.getResults();
            for (TOSummaryEntity entity : results) {
                CriteriaQuery logCq = new CriteriaQuery(TOReceiveLogEntity.class, dataGrid);
                logCq.eq("receiverId", user.getId());
                logCq.eq("researchId", entity.getId());
                logCq.eq("sendType", SrmipConstants.SEND_TYPE_SUMMARY);
                logCq.add();
                List<TOReceiveLogEntity> receiveLogs = this.systemService.getListByCriteriaQuery(logCq, false);
                boolean isRec = false;
                if (receiveLogs.size() > 0) {
                    for (TOReceiveLogEntity recLog : receiveLogs) {
                        if ("1".equals(recLog.getReceiveFlag())) {
                            isRec = true;
                            break;
                        }
                    }
                    if (isRec) {
                        entity.setSendReceiveFlag("3");//?????????
                    } else {
                        entity.setSendReceiveFlag("2");//?????????
                    }
                } else {
                    entity.setSendReceiveFlag("1");//?????????
                }
            }
            TagUtil.datagrid(response, dataGrid);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TOSummaryEntity tOSummary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String receiverIds = request.getParameter("receiverId");
        String receiverNames = request.getParameter("receiverName");
        if (StringUtil.isNotEmpty(tOSummary.getId())) {
            message = "????????????????????????";
            try {
                tOSummaryService.doSubmit(tOSummary, receiverIds, receiverNames);
            } catch (Exception e) {
                e.printStackTrace();
                message = "????????????????????????";
                j.setSuccess(false);
            }
        }
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
    public AjaxJson doDel(TOSummaryEntity tOSummary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOSummary = systemService.getEntity(TOSummaryEntity.class, tOSummary.getId());
        message = "????????????????????????";
        try {
            tOSummaryService.delete(tOSummary);
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
                TOSummaryEntity tOSummary = systemService.getEntity(TOSummaryEntity.class,
                        id);
                tOSummaryService.delete(tOSummary);
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
    public AjaxJson doAdd(TOSummaryEntity tOSummary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            tOSummaryService.save(tOSummary);
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
    public AjaxJson doUpdate(TOSummaryEntity tOSummary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
            if (StringUtils.isNotEmpty(tOSummary.getId())) {
                TOSummaryEntity t = tOSummaryService.get(TOSummaryEntity.class, tOSummary.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOSummary, t);
                tOSummaryService.saveOrUpdate(t);
            } else {
                tOSummary.setSubmitFlag("0");//?????????
                tOSummaryService.save(tOSummary);
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
    public ModelAndView goAdd(TOSummaryEntity tOSummary, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSummary.getId())) {
            tOSummary = tOSummaryService.getEntity(TOSummaryEntity.class, tOSummary.getId());
            req.setAttribute("tOWorkPointPage", tOSummary);
        }
        return new ModelAndView("com/kingtake/office/workpoint/tOSummary-add");
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOSummaryEntity tOSummary, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSummary.getId())) {
            tOSummary = tOSummaryService.getEntity(TOSummaryEntity.class, tOSummary.getId());
            if (StringUtils.isNotEmpty(tOSummary.getContentFileId())) {
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                        tOSummary.getContentFileId());
            req.setAttribute("file", file);
            }
            req.setAttribute("tOSummaryPage", tOSummary);
        }
        return new ModelAndView("com/kingtake/office/summary/tOSummary-update");
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
    public void exportXls(TOSummaryEntity tOSummary, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (StringUtils.isNotEmpty(tOSummary.getId())) {
                tOSummary = this.systemService.get(TOSummaryEntity.class, tOSummary.getId());
                TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                        tOSummary.getContentFileId());
                String dateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
                String codedFileName = "????????????_" + dateStr + "." + file.getExtend();
                response.setHeader("content-disposition", "attachment;filename="
                        + new String(codedFileName.getBytes(), "iso8859-1"));
                String realPath = request.getRealPath("/");
                File realDir = new File(realPath);
                String parentPath = realDir.getParent();
                String filePath = parentPath + File.separator + file.getRealpath();
                FileInputStream fis = new FileInputStream(filePath);
                ServletOutputStream out = response.getOutputStream();
                IOUtils.copy(fis, out);
                fis.close();
                out.close();
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOSummaryEntity tOSummary, HttpServletRequest request,
            HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOSummaryEntity.class);
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
                List<TOSummaryEntity> listTOWorkPointEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TOSummaryEntity.class, params);
                for (TOSummaryEntity tOSummary : listTOWorkPointEntitys) {
                    tOSummaryService.save(tOSummary);
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
    public AjaxJson doRecieve(TOSummaryEntity tOSummary, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????????????????";
        try {
            tOSummaryService.doReceive(tOSummary);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveLogList")
    public ModelAndView goReceiveLogList(HttpServletRequest request) {
        String researchId = request.getParameter("researchId");
        if (StringUtils.isNotEmpty(researchId)) {
            request.setAttribute("researchId", researchId);
        }
        String sendType = request.getParameter("sendType");
        if (StringUtils.isNotEmpty(sendType)) {
            request.setAttribute("sendType", sendType);
        }
        return new ModelAndView("com/kingtake/office/summary/receiveLogList");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doHide")
    @ResponseBody
    public AjaxJson doHide(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "????????????????????????";
        try {
            this.tOSummaryService.doLogicDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goRecycle")
    public ModelAndView goRecycle(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/summary/tOSummaryRecycleList");
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBack")
    @ResponseBody
    public AjaxJson doBack(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "????????????????????????!";
        try {
            this.tOSummaryService.doBack(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????!";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
