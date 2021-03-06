package com.kingtake.project.controller.booksecret;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.project.entity.booksecret.TBBookSecretEntity;
import com.kingtake.project.service.booksecret.TBBookSecretServiceI;

/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2016-01-12 10:37:56
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBBookSecretController")
public class TBBookSecretController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger  logger = Logger.getLogger(TBBookSecretController.class);

  @Autowired
  private TBBookSecretServiceI tBBookSecretService;
  @Autowired
  private SystemService        systemService;
  private String               message;

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
  @RequestMapping(params = "tBBookSecret")
  public ModelAndView tBBookSecret(HttpServletRequest request) {
    return new ModelAndView("com/kingtake/project/booksecret/tBBookSecretList");
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
  public void datagrid(TBBookSecretEntity tBBookSecret, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TBBookSecretEntity.class, dataGrid);
    // ?????????????????????
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBBookSecret, request.getParameterMap());
    try {
      // ???????????????????????????
      String type = request.getParameter("type");
      if(StringUtil.isEmpty(type) || !"1".equals(type)){//???????????????????????????????????????????????????????????????????????????????????????
        cq.eq("createBy",ResourceUtil.getSessionUserName().getUserName());
      }
      
      cq.addOrder("checkFlag", SortDirection.asc);
      cq.addOrder("createDate", SortDirection.desc);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tBBookSecretService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * ???????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TBBookSecretEntity tBBookSecret, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBBookSecret = systemService.getEntity(TBBookSecretEntity.class, tBBookSecret.getId());
    message = "?????????????????????????????????";
    try {
      tBBookSecretService.deleteAddAttach(tBBookSecret);
    } catch (Exception e) {
      e.printStackTrace();
      message = "??????????????????????????????";
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
        TBBookSecretEntity tBBookSecret = systemService.getEntity(TBBookSecretEntity.class, id);
        tBBookSecretService.deleteAddAttach(tBBookSecret);
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
   * ????????????????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goEdit")
  public ModelAndView goEdit(TBBookSecretEntity tBBookSecret, HttpServletRequest req) {
    // ?????????????????????????????????????????????
    String checkFlag = req.getParameter("checkFlag");
    req.setAttribute("checkFlag", StringUtil.isNotEmpty(checkFlag) ? checkFlag : "");

    if (StringUtil.isNotEmpty(tBBookSecret.getId())) {
      tBBookSecret = tBBookSecretService.getEntity(TBBookSecretEntity.class, tBBookSecret.getId());
    }
  //??????
    if(StringUtils.isEmpty(tBBookSecret.getAttachmentCode())){
        tBBookSecret.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBBookSecret.getAttachmentCode(), "");
        tBBookSecret.setCertificates(certificates);
    }
    req.setAttribute("tBBookSecretPage", tBBookSecret);
    return new ModelAndView("com/kingtake/project/booksecret/tBBookSecretEdit");
  }

  /**
   * ?????????????????????????????????????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doSave")
  @ResponseBody
  public AjaxJson doSave(TBBookSecretEntity tBBookSecret, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    if (tBBookSecret != null && !tBBookSecret.getId().isEmpty()) {// ??????
      TBBookSecretEntity t = tBBookSecretService.get(TBBookSecretEntity.class, tBBookSecret.getId());
      try {
        MyBeanUtils.copyBeanNotNull2Bean(tBBookSecret, t);
        tBBookSecretService.saveOrUpdate(t);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "?????????????????????????????????";
        throw new BusinessException(e.getMessage());
      }
    } else {// ??????
      try {
        tBBookSecret.setCheckFlag(SrmipConstants.NO);// ??????????????????0????????????
        tBBookSecretService.save(tBBookSecret);
        systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "?????????????????????????????????";
        throw new BusinessException(e.getMessage());
      }
    }
    j.setObj(tBBookSecret);
    j.setMsg(message);
    return j;

  }

  /**
   * ??????????????????
   * 
   * @return
   */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/project/booksecret/tBBookSecretUpload");
  }

  /**
   * ??????excel
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXls")
  public String exportXls(TBBookSecretEntity tBBookSecret, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TBBookSecretEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBBookSecret, request.getParameterMap());
    List<TBBookSecretEntity> tBBookSecrets = this.tBBookSecretService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
    modelMap.put(NormalExcelConstants.CLASS, TBBookSecretEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????", "?????????:" + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tBBookSecrets);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * ??????excel ?????????
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public void exportXlsByT(TBBookSecretEntity tBBookSecret, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
	  tBBookSecretService.downloadTemplate(request, response);
  }

  /**
   * ???????????????
   * 
   * @param req
   * @param tBBookSecret
   * @return
   */
  @RequestMapping(params = "goAudit")
  public ModelAndView goAudit(HttpServletRequest req, TBBookSecretEntity tBBookSecret) {
    if (StringUtil.isNotEmpty(tBBookSecret.getId())) {
      tBBookSecret = tBBookSecretService.getEntity(TBBookSecretEntity.class, tBBookSecret.getId());
      req.setAttribute("tBBookSecretPage", tBBookSecret);
    }
    return new ModelAndView("com/kingtake/project/booksecret/tBBookSecretAudit");
  }

  /**
   * ?????????????????????????????????(??????)
   * 
   * @param request
   * @return
   */
  @RequestMapping(params = "tBBookSecret4Depart")
  public ModelAndView tBBookSecret4Depart(HttpServletRequest req) {
    String type = req.getParameter("type");
    req.setAttribute("type", StringUtil.isNotEmpty(type) ? type : "");
    return new ModelAndView("com/kingtake/project/booksecret/tBBookSecretList");
  }

    /**
     * ??????????????????
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getSerectCode")
    @ResponseBody
    public AjaxJson getSerectCode(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String secretCode = PrimaryGenerater.getInstance().generaterNextBookSecretCode();
        j.setObj(secretCode);
        return j;
    }
    
    /**
     * ??????EXCEL
     * 
     * @param request
     * @param response
     * @return
     */
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

                msg = tBBookSecretService.importExcel(wb);
                
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
