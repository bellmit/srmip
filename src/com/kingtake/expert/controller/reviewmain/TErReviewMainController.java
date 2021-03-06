package com.kingtake.expert.controller.reviewmain;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.service.reviewmain.TErReviewMainServiceI;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2015-08-18 16:50:55
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tErReviewMainController")
public class TErReviewMainController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TErReviewMainController.class);

    @Autowired
    private TErReviewMainServiceI tErReviewMainService;
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
    @RequestMapping(params = "tErReviewMain")
    public ModelAndView tErReviewMain(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/expert/reviewmain/tErReviewMainList");
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
    @ResponseBody
    public JSONObject datagrid(TErReviewMainEntity tErReviewMain, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TErReviewMainEntity.class, dataGrid);
        installParam(request, cq);
        cq.add();
        int count = this.tErReviewMainService.getCountByCriteriaQuery(cq);
        CriteriaQuery mainCq = new CriteriaQuery(TErReviewMainEntity.class, dataGrid);
        installParam(request, mainCq);
        mainCq.setCurPage(Integer.valueOf(request.getParameter("page")));
        mainCq.setPageSize(Integer.valueOf(request.getParameter("rows")));
        mainCq.addOrder("createDate", SortDirection.desc);
        mainCq.add();
        List<TErReviewMainEntity> reviewMainList = this.tErReviewMainService.getListByCriteriaQuery(mainCq, true);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (TErReviewMainEntity mainEntity : reviewMainList) {
            JSONObject mainJson = new JSONObject();
            String[] fields = dataGrid.getField().split(",");
            ReflectHelper helper = new ReflectHelper(mainEntity);
            for (String field : fields) {
                Object obj = helper.getMethodValue(field);
                mainJson.put(field, obj);
            }
            array.add(mainJson);
        }
        json.put("rows", array);
        json.put("total", count);
        return json;
    }

    /**
     * ??????????????????
     * 
     * @param request
     * @param cq
     */
    private void installParam(HttpServletRequest request, CriteriaQuery cq) {
        String accordingNum = request.getParameter("accordingNum");
        String planReviewDateBegin_begin = request.getParameter("planReviewDateBegin_begin");
        String planReviewDateBegin_end = request.getParameter("planReviewDateBegin_end");

        if (StringUtils.isNotEmpty(accordingNum)) {
            cq.like("accordingNum", "%" + accordingNum + "%");
        }
        if (StringUtils.isNotEmpty(planReviewDateBegin_begin)) {
            try {
                cq.ge("planReviewDateBegin", DateUtils.parseDate(planReviewDateBegin_begin, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(planReviewDateBegin_end)) {
            try {
                cq.le("planReviewDateBegin", DateUtils.parseDate(planReviewDateBegin_end, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ?????????????????????
     * 
     * @param tErReviewMain
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getDetailList")
    @ResponseBody
    public JSONObject getDetailList(TErReviewMainEntity tErReviewMain, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject json = new JSONObject();
        tErReviewMain = this.tErReviewMainService.get(TErReviewMainEntity.class, tErReviewMain.getId());
        JSONArray subProjectArr = new JSONArray();
        for (TErReviewProjectEntity entity : tErReviewMain.getReviewProjectList()) {
            JSONObject subJson = new JSONObject();
            subJson.put("id", entity.getId());
            subJson.put("projectId", entity.getProjectId());
            subJson.put("projectName", entity.getProjectName());
            subJson.put("reviewResultStr", entity.getReviewResultStr());
            subJson.put("reviewScore", entity.getReviewScore());
            subJson.put("reviewSuggestion", entity.getReviewSuggestion());
            subProjectArr.add(subJson);
        }
        json.put("rows", subProjectArr);
        json.put("total", subProjectArr.size());
        return json;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TErReviewMainEntity tErReviewMain, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tErReviewMain = systemService.getEntity(TErReviewMainEntity.class, tErReviewMain.getId());
        message = "??????????????????????????????";
        try {
            tErReviewMainService.deleteReviewMain(tErReviewMain);
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
                TErReviewMainEntity tErReviewMain = systemService.getEntity(TErReviewMainEntity.class, id);
                tErReviewMainService.delete(tErReviewMain);
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
    public AjaxJson doAdd(TErReviewMainEntity tErReviewMain, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            tErReviewMainService.save(tErReviewMain);
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
    public AjaxJson doUpdate(TErReviewMainEntity tErReviewMain, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        TErReviewMainEntity t = tErReviewMainService.get(TErReviewMainEntity.class, tErReviewMain.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tErReviewMain, t);
            tErReviewMainService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
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
    public ModelAndView goAdd(TErReviewMainEntity tErReviewMain, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tErReviewMain.getId())) {
            tErReviewMain = tErReviewMainService.getEntity(TErReviewMainEntity.class, tErReviewMain.getId());
            req.setAttribute("tErReviewMainPage", tErReviewMain);
        }
        return new ModelAndView("com/kingtake/expert/reviewmain/tErReviewMain-add");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TErReviewMainEntity tErReviewMain, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tErReviewMain.getId())) {
            tErReviewMain = tErReviewMainService.getEntity(TErReviewMainEntity.class, tErReviewMain.getId());
            req.setAttribute("tErReviewMainPage", tErReviewMain);
        }
        return new ModelAndView("com/kingtake/expert/reviewmain/tErReviewMain-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/expert/reviewmain/tErReviewMainUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public void exportXls(TPmOutcomeContractApprEntity tPmOutcomeContractAppr, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Workbook wb = this.tErReviewMainService.export();
            String fileName = "?????????????????????.xls";
            fileName = POIPublicUtil
                    .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            logger.error("??????????????????", e);
            throw new BusinessException("??????????????????", e);
        }
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TErReviewMainEntity tErReviewMain, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TErReviewMainEntity.class);
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
                List<TErReviewMainEntity> listTErReviewMainEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TErReviewMainEntity.class, params);
                for (TErReviewMainEntity tErReviewMain : listTErReviewMainEntitys) {
                    tErReviewMainService.save(tErReviewMain);
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
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goProjectListForReviewApproval")
    public ModelAndView goProjectListForReviewApproval(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/manage/tPmProjectListForReviewApproval");
    }

    @RequestMapping(params = "goApprovalProjectList")
    public ModelAndView goApprovalProjectList(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/manage/goApprovalProjectList");
    }

    /**
     * ????????????????????????.
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveReviewMainInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveReviewMainInfo(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson ajax = new AjaxJson();
        TErReviewMainEntity reviewMain = this.tErReviewMainService.saveReviewMainInfo(request);
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("id", reviewMain.getId());
        ajax.setObj(dataMap);
        ajax.setMsg("?????????????????????????????????");
        ajax.setSuccess(true);
        return ajax;
    }

    /**
     * ???????????????????????????
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goExpertReviewInfo")
    public ModelAndView goExpertReviewInfo(TErReviewProjectEntity entity, HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isNotEmpty(entity.getId())) {
            entity = this.tErReviewMainService.get(TErReviewProjectEntity.class, entity.getId());
            request.setAttribute("reviewProjectEntity", entity);
        }
        return new ModelAndView("com/kingtake/expert/review/expertReviewInfo");
    }

    /**
     * ????????????
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "submitReview")
    @ResponseBody
    public AjaxJson submitReview(TErReviewMainEntity tErReviewMain, HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isNotEmpty(tErReviewMain.getId())) {
            tErReviewMain = this.systemService.get(TErReviewMainEntity.class, tErReviewMain.getId());
            tErReviewMain.setReviewStatus("1");//?????????
            this.systemService.updateEntitie(tErReviewMain);
            j.setMsg("??????????????????!");
        }
        return j;
    }

}
