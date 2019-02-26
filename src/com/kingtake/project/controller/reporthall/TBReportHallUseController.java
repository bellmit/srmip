package com.kingtake.project.controller.reporthall;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.reporthall.TBReportHallUseEntity;
import com.kingtake.project.service.reporthall.TBReportHallUseServiceI;

/**
 * @Title: Controller
 * @Description: 学术报告厅信息登记表
 * @author onlineGenerator
 * @date 2016-01-15 16:56:10
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBReportHallUseController")
public class TBReportHallUseController extends BaseController {
  /**
   * Logger for this class
   */
  @SuppressWarnings("unused")
  private static final Logger     logger = Logger.getLogger(TBReportHallUseController.class);

  @Autowired
  private TBReportHallUseServiceI tBReportHallUseService;
  @Autowired
  private SystemService           systemService;
  private String                  message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * 学术报告厅信息登记表列表 页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "tBReportHallUse")
  public ModelAndView tBReportHallUse(HttpServletRequest request) {
    return new ModelAndView("com/kingtake/project/reporthall/tBReportHallUseList");
  }

  /**
   * easyui AJAX请求数据
   * 
   * @param request
   * @param response
   * @param dataGrid
   * @param user
   */

  @SuppressWarnings("unchecked")
  @RequestMapping(params = "datagrid")
  public void datagrid(TBReportHallUseEntity tBReportHallUse, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TBReportHallUseEntity.class, dataGrid);
    // 查询条件组装器
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBReportHallUse, request.getParameterMap());
    try {
      // 自定义追加查询条件
      String type = request.getParameter("type");
      if(StringUtil.isEmpty(type) || !"1".equals(type)){//课题组录入时只能看到本人创建的；机关审查，不需要区分创建人
        cq.eq("createBy",ResourceUtil.getSessionUserName().getUserName());
      }
      cq.addOrder("checkFlag", SortDirection.asc);
      cq.addOrder("createDate", SortDirection.desc);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tBReportHallUseService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * 删除学术报告厅信息登记表
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TBReportHallUseEntity tBReportHallUse, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBReportHallUse = systemService.getEntity(TBReportHallUseEntity.class, tBReportHallUse.getId());
    message = "学术报告厅信息登记表删除成功";
    try {
      tBReportHallUseService.deleteAddAttach(tBReportHallUse);
      systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "学术报告厅信息登记表删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 批量删除学术报告厅信息登记表
   * 
   * @return
   */
  @RequestMapping(params = "doBatchDel")
  @ResponseBody
  public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "学术报告厅信息登记表删除成功";
    try {
      for (String id : ids.split(",")) {
        TBReportHallUseEntity tBReportHallUse = systemService.getEntity(TBReportHallUseEntity.class, id);
        tBReportHallUseService.deleteAddAttach(tBReportHallUse);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
      }
    } catch (Exception e) {
      e.printStackTrace();
      message = "学术报告厅信息登记表删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 添加学术报告厅信息登记表
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TBReportHallUseEntity tBReportHallUse, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "学术报告厅信息登记表添加成功";
    try {
      tBReportHallUseService.save(tBReportHallUse);
      systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "学术报告厅信息登记表添加失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 更新学术报告厅信息登记表
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TBReportHallUseEntity tBReportHallUse, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "学术报告厅信息登记表更新成功";
    TBReportHallUseEntity t = tBReportHallUseService.get(TBReportHallUseEntity.class, tBReportHallUse.getId());
    try {
      MyBeanUtils.copyBeanNotNull2Bean(tBReportHallUse, t);
      tBReportHallUseService.saveOrUpdate(t);
      systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "学术报告厅信息登记表更新失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 学术报告厅信息登记表新增页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TBReportHallUseEntity tBReportHallUse, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBReportHallUse.getId())) {
      tBReportHallUse = tBReportHallUseService.getEntity(TBReportHallUseEntity.class, tBReportHallUse.getId());
      req.setAttribute("tBReportHallUsePage", tBReportHallUse);
    }
    return new ModelAndView("com/kingtake/project/reporthall/tBReportHallUse-add");
  }

  /**
   * 学术报告厅信息登记表编辑页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TBReportHallUseEntity tBReportHallUse, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBReportHallUse.getId())) {
      tBReportHallUse = tBReportHallUseService.getEntity(TBReportHallUseEntity.class, tBReportHallUse.getId());
      req.setAttribute("tBReportHallUsePage", tBReportHallUse);
    }
    return new ModelAndView("com/kingtake/project/reporthall/tBReportHallUse-update");
  }

  /**
   * 学术报告厅信息登记表新增或修改页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "goEdit")
  public ModelAndView goEdit(TBReportHallUseEntity tBReportHallUse, HttpServletRequest req) {
    // 机关审查时要控制“编号”的显示
    String checkFlag = req.getParameter("checkFlag");
    req.setAttribute("checkFlag", StringUtil.isNotEmpty(checkFlag) ? checkFlag : "");

    if (StringUtil.isNotEmpty(tBReportHallUse.getId())) {
      tBReportHallUse = tBReportHallUseService.getEntity(TBReportHallUseEntity.class, tBReportHallUse.getId());
    }
    if(StringUtils.isEmpty(tBReportHallUse.getAttachmentCode())){
        tBReportHallUse.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBReportHallUse.getAttachmentCode(), "");
        tBReportHallUse.setCertificates(certificates);
    }
    req.setAttribute("tBReportHallUsePage", tBReportHallUse);
    return new ModelAndView("com/kingtake/project/reporthall/tBReportHallUseEdit");
  }

  /**
   * 保存学术报告厅信息表，在新增或修改时调用
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doSave")
  @ResponseBody
  public AjaxJson doSave(TBReportHallUseEntity tBReportHallUse, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "学术报告厅信息表更新成功";
    if (tBReportHallUse != null && !tBReportHallUse.getId().isEmpty()) {// 修改
      TBReportHallUseEntity t = tBReportHallUseService.get(TBReportHallUseEntity.class, tBReportHallUse.getId());
      try {
        MyBeanUtils.copyBeanNotNull2Bean(tBReportHallUse, t);
        tBReportHallUseService.saveOrUpdate(t);
        systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "学术报告厅信息表更新失败";
        throw new BusinessException(e.getMessage());
      }
    } else {// 新增
      try {
        tBReportHallUse.setCheckFlag(SrmipConstants.NO);// 新增时默认为0：未审查
        tBReportHallUseService.save(tBReportHallUse);
        systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
      } catch (Exception e) {
        e.printStackTrace();
        message = "学术报告厅信息表添加失败";
        throw new BusinessException(e.getMessage());
      }
    }
    j.setObj(tBReportHallUse);
    j.setMsg(message);
    return j;
  }

  /**
   * 导出审批表
   * 
   * @param req
   * @param tBBookSecret
   * @return
   */
  @RequestMapping(params = "goExport")
  public ModelAndView goAudit(HttpServletRequest req, TBReportHallUseEntity tBReportHallUse) {
    if (StringUtil.isNotEmpty(tBReportHallUse.getId())) {
      tBReportHallUse = tBReportHallUseService.getEntity(TBReportHallUseEntity.class, tBReportHallUse.getId());
      req.setAttribute("tBReportHallUsePage", tBReportHallUse);
      JSONArray jsonArray = JSONHelper.toJSONArray(tBReportHallUse.getCertificates());
      req.setAttribute("attachJSON", jsonArray.toString());
    }
    return new ModelAndView("com/kingtake/project/reporthall/tBReportHallUseExport");
  }

  /**
   * 导入功能跳转
   * 
   * @return
   */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/project/reporthall/tBReportHallUseUpload");
  }

  /**
   * 导出excel
   * 
   * @param request
   * @param response
   */
  @SuppressWarnings("unchecked")
  @RequestMapping(params = "exportXls")
  public String exportXls(TBReportHallUseEntity tBReportHallUse, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TBReportHallUseEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBReportHallUse, request.getParameterMap());
    List<TBReportHallUseEntity> tBReportHallUses = this.tBReportHallUseService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "学术报告厅信息登记表");
    modelMap.put(NormalExcelConstants.CLASS, TBReportHallUseEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("学术报告厅信息登记表列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tBReportHallUses);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * 导出excel 使模板
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TBReportHallUseEntity tBReportHallUse, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    modelMap.put(TemplateExcelConstants.FILE_NAME, "学术报告厅信息登记表");
    modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
    modelMap.put(TemplateExcelConstants.MAP_DATA, null);
    modelMap.put(TemplateExcelConstants.CLASS, TBReportHallUseEntity.class);
    modelMap.put(TemplateExcelConstants.LIST_DATA, null);
    return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
  }
  /**
   * 学术报告厅使用信息表列表(机关)
   * 
   * @param request
   * @return
   */
  @RequestMapping(params = "tBReportHallUse4Depart")
  public ModelAndView tBReportHallUse4Depart(HttpServletRequest req) {
    String type = req.getParameter("type");
    req.setAttribute("type", StringUtil.isNotEmpty(type) ? type : "");
    return new ModelAndView("com/kingtake/project/reporthall/tBReportHallUseList");
  }
}
