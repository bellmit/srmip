package com.kingtake.project.controller.productdelivery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.project.entity.productdelivery.TBPmProductDeliveryEntity;
import com.kingtake.project.entity.productdetail.TBPmProductDetailEntity;
import com.kingtake.project.page.productdelivery.TBPmProductDeliveryPage;
import com.kingtake.project.service.productdelivery.TBPmProductDeliveryServiceI;

/**
 * @Title: Controller
 * @Description: 产品交接清单信息
 * @author onlineGenerator
 * @date 2016-03-01 16:24:20
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBPmProductDeliveryController")
public class TBPmProductDeliveryController extends BaseController {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(TBPmProductDeliveryController.class);

  @Autowired
  private TBPmProductDeliveryServiceI tBPmProductDeliveryService;
  @Autowired
  private SystemService systemService;

  /**
   * 产品交接清单信息列表 页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "tBPmProductDelivery")
  public ModelAndView tBPmProductDelivery(HttpServletRequest request) {
    String projectId = request.getParameter("projectId");
    request.setAttribute("projectId", projectId);
    return new ModelAndView("com/kingtake/project/productdelivery/tBPmProductDeliveryList");
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
  public void datagrid(TBPmProductDeliveryEntity tBPmProductDelivery, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(TBPmProductDeliveryEntity.class, dataGrid);
    // 查询条件组装器
    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPmProductDelivery);
    try {
      // 自定义追加查询条件
    } catch (Exception e) {
      throw new BusinessException(e.getMessage());
    }
    cq.add();
    this.tBPmProductDeliveryService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * 删除产品交接清单信息
   * 
   * @return
   */
  @RequestMapping(params = "doDel")
  @ResponseBody
  public AjaxJson doDel(TBPmProductDeliveryEntity tBPmProductDelivery, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    tBPmProductDelivery = systemService.getEntity(TBPmProductDeliveryEntity.class, tBPmProductDelivery.getId());
    String message = "产品交接清单信息删除成功";
    try {
      tBPmProductDeliveryService.delMain(tBPmProductDelivery);
      systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "产品交接清单信息删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 批量删除产品交接清单信息
   * 
   * @return
   */
  @RequestMapping(params = "doBatchDel")
  @ResponseBody
  public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
    AjaxJson j = new AjaxJson();
    String message = "产品交接清单信息删除成功";
    try {
      for (String id : ids.split(",")) {
        TBPmProductDeliveryEntity tBPmProductDelivery = systemService.getEntity(TBPmProductDeliveryEntity.class, id);
        tBPmProductDeliveryService.delMain(tBPmProductDelivery);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
      }
    } catch (Exception e) {
      e.printStackTrace();
      message = "产品交接清单信息删除失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 添加产品交接清单信息
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doAdd")
  @ResponseBody
  public AjaxJson doAdd(TBPmProductDeliveryEntity tBPmProductDelivery, TBPmProductDeliveryPage tBPmProductDeliveryPage, HttpServletRequest request) {
    List<TBPmProductDetailEntity> tBPmProductDetailList = tBPmProductDeliveryPage.getTBPmProductDetailList();
    AjaxJson j = new AjaxJson();
    String message = "添加成功";
    try {
      tBPmProductDeliveryService.addMain(tBPmProductDelivery, tBPmProductDetailList);
      systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "产品交接清单信息添加失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 更新产品交接清单信息
   * 
   * @param ids
   * @return
   */
  @RequestMapping(params = "doUpdate")
  @ResponseBody
  public AjaxJson doUpdate(TBPmProductDeliveryEntity tBPmProductDelivery, TBPmProductDeliveryPage tBPmProductDeliveryPage, HttpServletRequest request) {
    List<TBPmProductDetailEntity> tBPmProductDetailList = tBPmProductDeliveryPage.getTBPmProductDetailList();
    AjaxJson j = new AjaxJson();
    String message = "更新成功";
    try {
      tBPmProductDeliveryService.updateMain(tBPmProductDelivery, tBPmProductDetailList);
      systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
    } catch (Exception e) {
      e.printStackTrace();
      message = "更新产品交接清单信息失败";
      throw new BusinessException(e.getMessage());
    }
    j.setMsg(message);
    return j;
  }

  /**
   * 产品交接清单信息新增页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "goAdd")
  public ModelAndView goAdd(TBPmProductDeliveryEntity tBPmProductDelivery, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPmProductDelivery.getId())) {
      tBPmProductDelivery = tBPmProductDeliveryService.getEntity(TBPmProductDeliveryEntity.class, tBPmProductDelivery.getId());
      req.setAttribute("tBPmProductDeliveryPage", tBPmProductDelivery);
    }
    String projectId = req.getParameter("projectId");
    req.setAttribute("projectId", projectId);
    
    return new ModelAndView("com/kingtake/project/productdelivery/tBPmProductDelivery-add");
  }

  /**
   * 产品交接清单信息编辑页面跳转
   * 
   * @return
   */
  @RequestMapping(params = "goUpdate")
  public ModelAndView goUpdate(TBPmProductDeliveryEntity tBPmProductDelivery, HttpServletRequest req) {
    if (StringUtil.isNotEmpty(tBPmProductDelivery.getId())) {
      tBPmProductDelivery = tBPmProductDeliveryService.getEntity(TBPmProductDeliveryEntity.class, tBPmProductDelivery.getId());
      req.setAttribute("tBPmProductDeliveryPage", tBPmProductDelivery);
    }
    return new ModelAndView("com/kingtake/project/productdelivery/tBPmProductDelivery-update");
  }

  /**
   * 加载明细列表[产品交接清单明细]
   * 
   * @return
   */
  @RequestMapping(params = "tBPmProductDetailList")
  public ModelAndView tBPmProductDetailList(TBPmProductDeliveryEntity tBPmProductDelivery, HttpServletRequest req) {

    // ===================================================================================
    // 获取参数
    Object id0 = tBPmProductDelivery.getId();
    // ===================================================================================
    // 查询-产品交接清单明细
    String hql0 = "from TBPmProductDetailEntity where 1 = 1 AND pROEUCT_DELIVERY_ID = ? ";
    try {
      List<TBPmProductDetailEntity> tBPmProductDetailEntityList = systemService.findHql(hql0, id0);
      req.setAttribute("tBPmProductDetailList", tBPmProductDetailEntityList);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return new ModelAndView("com/kingtake/project/productdetail/tBPmProductDetailList");
  }

}
