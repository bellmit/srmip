package com.kingtake.project.controller.invoice;

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

import com.kingtake.project.entity.invoice.TBPmInvoiceEntity;
import com.kingtake.project.service.invoice.TBPmInvoiceServiceI;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2016-01-21 20:20:22
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmInvoiceController")
public class TBPmInvoiceController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TBPmInvoiceController.class);

  @Autowired
  private TBPmInvoiceServiceI tBPmInvoiceService;
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
  @RequestMapping(params = "tBPmInvoiceList")
    public ModelAndView tBPmInvoiceList(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
        request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/invoice/tBPmInvoiceList");
    }

  /**
   * easyui AJAX????????????
   * 
   * @param request
   * @param response
   * @param dataGrid
   * @param user
   */

  @SuppressWarnings("unchecked")
  @RequestMapping(params = "datagrid")
  public void datagrid(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TBPmInvoiceEntity.class, dataGrid);
    // ?????????????????????
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmInvoice, request.getParameterMap());
    try {
      // ???????????????????????????
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tBPmInvoiceService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * ?????????????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(tBPmInvoice.getId())) {
            tBPmInvoice = tBPmInvoiceService.getEntity(TBPmInvoiceEntity.class, tBPmInvoice.getId());
        }
        if (StringUtils.isNotEmpty(projectId)) {
            tBPmInvoice.setProjectId(projectId);
        }
      //??????
        if(StringUtils.isEmpty(tBPmInvoice.getAttachmentCode())){
            tBPmInvoice.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBPmInvoice.getAttachmentCode(), "");
            tBPmInvoice.setCertificates(certificates);
        }
        req.setAttribute("tBPmInvoicePage", tBPmInvoice);
        return new ModelAndView("com/kingtake/project/invoice/tBPmInvoiceEdit");
    }

  /**
   * ?????????????????????????????????????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doSave")
  @ResponseBody
  public AjaxJson doSave(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    if (tBPmInvoice != null && !tBPmInvoice.getId().isEmpty()) {// ??????
      TBPmInvoiceEntity t = tBPmInvoiceService.get(TBPmInvoiceEntity.class, tBPmInvoice.getId());
      try {
        MyBeanUtils.copyBeanNotNull2Bean(tBPmInvoice, t);
        tBPmInvoiceService.saveOrUpdate(t);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "?????????????????????????????????";
        throw new BusinessException(e.getMessage());
      }
    } else {// ??????
      try {
        tBPmInvoiceService.save(tBPmInvoice);
        systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "?????????????????????????????????";
        throw new BusinessException(e.getMessage());
      }
    }
    j.setObj(tBPmInvoice);
    j.setMsg(message);
    return j;
  }

  /**
   * ????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBPmInvoice = systemService.getEntity(TBPmInvoiceEntity.class, tBPmInvoice.getId());
    message = "??????????????????????????????";
    try {
      tBPmInvoiceService.deleteAddAttach(tBPmInvoice);
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
        TBPmInvoiceEntity tBPmInvoice = systemService.getEntity(TBPmInvoiceEntity.class, id);
        tBPmInvoiceService.deleteAddAttach(tBPmInvoice);
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
  public AjaxJson doAdd(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "??????????????????????????????";
    try {
      tBPmInvoiceService.save(tBPmInvoice);
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
  public AjaxJson doUpdate(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "??????????????????????????????";
    TBPmInvoiceEntity t = tBPmInvoiceService.get(TBPmInvoiceEntity.class, tBPmInvoice.getId());
    try {
      MyBeanUtils.copyBeanNotNull2Bean(tBPmInvoice, t);
      tBPmInvoiceService.saveOrUpdate(t);
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
  public ModelAndView goAdd(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPmInvoice.getId())) {
      tBPmInvoice = tBPmInvoiceService.getEntity(TBPmInvoiceEntity.class, tBPmInvoice.getId());
      req.setAttribute("tBPmInvoicePage", tBPmInvoice);
    }
    return new ModelAndView("com/kingtake/project/invoice/tBPmInvoice-add");
  }

  /**
   * ????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPmInvoice.getId())) {
      tBPmInvoice = tBPmInvoiceService.getEntity(TBPmInvoiceEntity.class, tBPmInvoice.getId());
      req.setAttribute("tBPmInvoicePage", tBPmInvoice);
    }
    return new ModelAndView("com/kingtake/project/invoice/tBPmInvoice-update");
  }

  /**
   * ??????????????????
   * 
   * @return
   */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/project/invoice/tBPmInvoiceUpload");
  }

  /**
   * ??????excel
   * 
   * @param request
   * @param response
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(params = "exportXls")
  public String exportXls(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TBPmInvoiceEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmInvoice, request.getParameterMap());
    List<TBPmInvoiceEntity> tBPmInvoices = this.tBPmInvoiceService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
    modelMap.put(NormalExcelConstants.CLASS, TBPmInvoiceEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:" + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tBPmInvoices);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * ??????excel ?????????
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
    modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
    modelMap.put(TemplateExcelConstants.MAP_DATA, null);
    modelMap.put(TemplateExcelConstants.CLASS, TBPmInvoiceEntity.class);
    modelMap.put(TemplateExcelConstants.LIST_DATA, null);
    return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
  }

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
        List<TBPmInvoiceEntity> listTBPmInvoiceEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(), TBPmInvoiceEntity.class, params);
        for (TBPmInvoiceEntity tBPmInvoice : listTBPmInvoiceEntitys) {
          tBPmInvoiceService.save(tBPmInvoice);
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
}
