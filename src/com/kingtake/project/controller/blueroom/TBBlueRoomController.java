package com.kingtake.project.controller.blueroom;

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
import com.kingtake.project.entity.blueroom.TBBlueRoomEntity;
import com.kingtake.project.service.blueroom.TBBlueRoomServiceI;

/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2016-01-14 23:11:51
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBBlueRoomController")
public class TBBlueRoomController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TBBlueRoomController.class);

  @Autowired
  private TBBlueRoomServiceI  tBBlueRoomService;
  @Autowired
  private SystemService       systemService;
  private String              message;

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
  @RequestMapping(params = "tBBlueRoom")
  public ModelAndView tBBlueRoom(HttpServletRequest request) {
    return new ModelAndView("com/kingtake/project/blueroom/tBBlueRoomList");
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
  public void datagrid(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TBBlueRoomEntity.class, dataGrid);
    // ?????????????????????
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBBlueRoom, request.getParameterMap());
    try {
      // ???????????????????????????
      cq.addOrder("confirmFlag", SortDirection.asc);
      cq.addOrder("createDate", SortDirection.desc);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tBBlueRoomService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * ???????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBBlueRoom = systemService.getEntity(TBBlueRoomEntity.class, tBBlueRoom.getId());
    message = "?????????????????????????????????";
    try {
      tBBlueRoomService.deleteAddAttach(tBBlueRoom);
    } catch (Exception e) {
      e.printStackTrace();
      message = "?????????????????????????????????";
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
        TBBlueRoomEntity tBBlueRoom = systemService.getEntity(TBBlueRoomEntity.class, id);
        tBBlueRoomService.deleteAddAttach(tBBlueRoom);
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
   * ???????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    try {
      tBBlueRoomService.save(tBBlueRoom);
      systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "?????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ???????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    TBBlueRoomEntity t = tBBlueRoomService.get(TBBlueRoomEntity.class, tBBlueRoom.getId());
    try {
      MyBeanUtils.copyBeanNotNull2Bean(tBBlueRoom, t);
      tBBlueRoomService.saveOrUpdate(t);
      systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "?????????????????????????????????";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * ???????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TBBlueRoomEntity tBBlueRoom, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBBlueRoom.getId())) {
      tBBlueRoom = tBBlueRoomService.getEntity(TBBlueRoomEntity.class, tBBlueRoom.getId());
      req.setAttribute("tBBlueRoomPage", tBBlueRoom);
    }
    return new ModelAndView("com/kingtake/project/blueroom/tBBlueRoom-add");
  }

  /**
   * ???????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TBBlueRoomEntity tBBlueRoom, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBBlueRoom.getId())) {
      tBBlueRoom = tBBlueRoomService.getEntity(TBBlueRoomEntity.class, tBBlueRoom.getId());
      req.setAttribute("tBBlueRoomPage", tBBlueRoom);
    }
    return new ModelAndView("com/kingtake/project/blueroom/tBBlueRoom-update");
  }

  /**
   * ????????????????????????????????????????????????
   * 
   * @return
   */
  @RequestMapping(params = "goEdit")
  public ModelAndView goEdit(TBBlueRoomEntity tBBlueRoom, HttpServletRequest req) {

    if (StringUtil.isNotEmpty(tBBlueRoom.getId())) {
      tBBlueRoom = tBBlueRoomService.getEntity(TBBlueRoomEntity.class, tBBlueRoom.getId());
    }
    if(StringUtils.isEmpty(tBBlueRoom.getAttachmentCode())){
        tBBlueRoom.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBBlueRoom.getAttachmentCode(), "");
        tBBlueRoom.setCertificates(certificates);
    }
    req.setAttribute("tBBlueRoomPage", tBBlueRoom);
    return new ModelAndView("com/kingtake/project/blueroom/tBBlueRoomEdit");
  }

  /**
   * ?????????????????????????????????????????????????????????
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doSave")
  @ResponseBody
  public AjaxJson doSave(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "?????????????????????????????????";
    if (tBBlueRoom != null && !tBBlueRoom.getId().isEmpty()) {// ??????
      TBBlueRoomEntity t = tBBlueRoomService.get(TBBlueRoomEntity.class, tBBlueRoom.getId());
      try {
        MyBeanUtils.copyBeanNotNull2Bean(tBBlueRoom, t);
        tBBlueRoomService.saveOrUpdate(t);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "?????????????????????????????????";
        throw new BusinessException(e.getMessage());
      }
    } else {// ??????
      try {
        tBBlueRoom.setConfirmFlag(SrmipConstants.NO);// ??????????????????0????????????
        tBBlueRoomService.save(tBBlueRoom);
        systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "?????????????????????????????????";
        throw new BusinessException(e.getMessage());
      }
    }
    j.setObj(tBBlueRoom);
    j.setMsg(message);
    return j;

  }

  @RequestMapping(params = "changeFlag")
  @ResponseBody
  public AjaxJson changeFlag(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBBlueRoom = tBBlueRoomService.get(TBBlueRoomEntity.class, tBBlueRoom.getId());
    message = "????????????";
    try {
      tBBlueRoom.setConfirmFlag(request.getParameter("confirmFlag"));
      tBBlueRoomService.updateEntitie(tBBlueRoom);
    } catch (Exception e) {
      e.printStackTrace();
      message = "????????????";
      throw new BusinessException(e.getMessage());
    }
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
    return new ModelAndView("com/kingtake/project/blueroom/tBBlueRoomUpload");
  }

  /**
   * ??????excel
   * 
   * @param request
   * @param response
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(params = "exportXls")
  public String exportXls(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TBBlueRoomEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBBlueRoom, request.getParameterMap());
    List<TBBlueRoomEntity> tBBlueRooms = this.tBBlueRoomService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
    modelMap.put(NormalExcelConstants.CLASS, TBBlueRoomEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????", "?????????:" + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tBBlueRooms);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * ??????excel ?????????
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TBBlueRoomEntity tBBlueRoom, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
    modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
    modelMap.put(TemplateExcelConstants.MAP_DATA, null);
    modelMap.put(TemplateExcelConstants.CLASS, TBBlueRoomEntity.class);
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
        List<TBBlueRoomEntity> listTBBlueRoomEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(), TBBlueRoomEntity.class, params);
        for (TBBlueRoomEntity tBBlueRoom : listTBBlueRoomEntitys) {
          tBBlueRoomService.save(tBBlueRoom);
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
