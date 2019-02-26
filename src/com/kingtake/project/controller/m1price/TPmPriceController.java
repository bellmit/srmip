package com.kingtake.project.controller.m1price;

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

import com.alibaba.fastjson.JSONArray;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.m1price.TPmPriceEntity;
import com.kingtake.project.service.m1price.TPmPriceServiceI;

/**
 * @Title: Controller
 * @Description: 报价
 * @author onlineGenerator
 * @date 2015-07-23 16:13:47
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmPriceController")
public class TPmPriceController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TPmPriceController.class);

  @Autowired
  private TPmPriceServiceI tPmPriceService;
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
   * 报价列表 页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "tPmPrice")
  public ModelAndView tPmPrice(HttpServletRequest request) {
    String projectId = request.getParameter("projectId");
    request.setAttribute("projectId", projectId);
    String planContractFlag = request.getParameter("planContractFlag");
    if (ProjectConstant.PROJECT_PLAN.equals(planContractFlag)) {
      return new ModelAndView("redirect:tPmProjectController.do?goUpdateForResearchGroup&id=" + projectId);
    }
    return new ModelAndView("com/kingtake/project/m1price/tPmPriceList");
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
  public void datagrid(TPmPriceEntity tPmPrice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TPmPriceEntity.class, dataGrid);
    // 查询条件组装器
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPrice, request.getParameterMap());
    try {
      // 自定义追加查询条件
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tPmPriceService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * 删除报价
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TPmPriceEntity tPmPrice, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tPmPrice = systemService.getEntity(TPmPriceEntity.class, tPmPrice.getId());
    message = "报价删除成功";
    try {
      tPmPriceService.deleteAddAttach(tPmPrice);
    } catch (Exception e) {
      e.printStackTrace();
      message = "报价删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 批量删除报价
   * 
   * @return
   */
  @RequestMapping(params = "doBatchDel")
  @ResponseBody
  public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "报价删除成功";
    try {
      for (String id : ids.split(",")) {
        TPmPriceEntity tPmPrice = systemService.getEntity(TPmPriceEntity.class, id);
        tPmPriceService.deleteAddAttach(tPmPrice);
      }
    } catch (Exception e) {
      e.printStackTrace();
      message = "报价删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 添加报价
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TPmPriceEntity tPmPrice, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "报价添加成功";
    try {
      tPmPriceService.save(tPmPrice);
      systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "报价添加失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    j.setObj(tPmPrice);
    return j;
  }

  /**
   * 更新报价
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TPmPriceEntity tPmPrice, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    message = "报价更新成功";
    TPmPriceEntity t = tPmPriceService.get(TPmPriceEntity.class, tPmPrice.getId());
    try {
      MyBeanUtils.copyBeanNotNull2Bean(tPmPrice, t);
      tPmPriceService.saveOrUpdate(t);
      systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "报价更新失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    j.setObj(t);
    return j;
  }

  /**
   * 报价新增页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TPmPriceEntity tPmPrice, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tPmPrice.getId())) {
      tPmPrice = tPmPriceService.getEntity(TPmPriceEntity.class, tPmPrice.getId());
    }
    if(StringUtils.isEmpty(tPmPrice.getAttachmentCode())){
        tPmPrice.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmPrice.getAttachmentCode(), "");
        tPmPrice.setCertificates(certificates);
    }
    req.setAttribute("tPmPricePage", tPmPrice);
    return new ModelAndView("com/kingtake/project/m1price/tPmPrice-add");
  }

  /**
   * 报价编辑页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TPmPriceEntity tPmPrice, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tPmPrice.getId())) {
      tPmPrice = tPmPriceService.getEntity(TPmPriceEntity.class, tPmPrice.getId());
    }
    if(StringUtils.isEmpty(tPmPrice.getAttachmentCode())){
        tPmPrice.setAttachmentCode(UUIDGenerator.generate());
    }else{
        List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmPrice.getAttachmentCode(), "");
        tPmPrice.setCertificates(certificates);
    }
    req.setAttribute("tPmPricePage", tPmPrice);
    return new ModelAndView("com/kingtake/project/m1price/tPmPrice-update");
  }

  /**
   * 导入功能跳转
   * 
   * @return
   */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
    return new ModelAndView("com/kingtake/project/m1price/tPmPriceUpload");
  }

  /**
   * 导出excel
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXls")
  public String exportXls(TPmPriceEntity tPmPrice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    CriteriaQuery cq = new CriteriaQuery(TPmPriceEntity.class, dataGrid);
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPrice, request.getParameterMap());
    List<TPmPriceEntity> tPmPrices = this.tPmPriceService.getListByCriteriaQuery(cq, false);
    modelMap.put(NormalExcelConstants.FILE_NAME, "报价");
    modelMap.put(NormalExcelConstants.CLASS, TPmPriceEntity.class);
    modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("报价列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
    modelMap.put(NormalExcelConstants.DATA_LIST, tPmPrices);
    return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }

  /**
   * 导出excel 使模板
   * 
   * @param request
   * @param response
   */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TPmPriceEntity tPmPrice, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
    modelMap.put(TemplateExcelConstants.FILE_NAME, "报价");
    modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
    modelMap.put(TemplateExcelConstants.MAP_DATA, null);
    modelMap.put(TemplateExcelConstants.CLASS, TPmPriceEntity.class);
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
      MultipartFile file = entity.getValue();// 获取上传文件对象
      ImportParams params = new ImportParams();
      params.setTitleRows(2);
      params.setHeadRows(1);
      params.setNeedSave(true);
      try {
        List<TPmPriceEntity> listTPmPriceEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(), TPmPriceEntity.class, params);
        for (TPmPriceEntity tPmPrice : listTPmPriceEntitys) {
          tPmPriceService.save(tPmPrice);
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

  /**
   * 查询项目关联的进出账合同列表，并将合同类型（进/出）一起返回
   * 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(params = "getContractList")
  @ResponseBody
  public JSONArray getContractList(HttpServletRequest request, HttpServletResponse response) {
    String projectId = request.getParameter("projectId");
    //查询进账合同列表
    CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractApprEntity.class);
    cq.eq("project.id", projectId);
    cq.add();
    List<TPmIncomeContractApprEntity> incomeList = this.systemService.getListByCriteriaQuery(cq, false);
    JSONArray array = new JSONArray();
    if (incomeList != null && incomeList.size() > 0) {
      for (TPmIncomeContractApprEntity in : incomeList) {
        TPmIncomeContractApprEntity tmp  = new TPmIncomeContractApprEntity();
        tmp.setId(in.getId());
        tmp.setContractName(in.getContractName());
        tmp.setContractType(ProjectConstant.INCOME_CONTRACT);//这里临时用contractType属性来存放类型标志
        array.add(tmp);
      }
    }
    //查询出账合同列表
    CriteriaQuery cq2 = new CriteriaQuery(TPmOutcomeContractApprEntity.class);
    cq2.eq("project.id", projectId);
    cq2.add();
    List<TPmOutcomeContractApprEntity> outcomeList = this.systemService.getListByCriteriaQuery(cq2, false);
    if (outcomeList != null && outcomeList.size() > 0) {
      for (TPmOutcomeContractApprEntity out : outcomeList) {
        TPmIncomeContractApprEntity tmp  = new TPmIncomeContractApprEntity();
        tmp.setId(out.getId());
        tmp.setContractName(out.getContractName());
        tmp.setContractType(ProjectConstant.OUTCOME_CONTRACT);//这里临时用contractType属性来存放类型标志
        array.add(tmp);
      }
    }
    return array;
  }
}
