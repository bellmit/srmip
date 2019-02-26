package com.kingtake.base.controller.formtip;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.base.entity.formtip.TBFormTipEntity;
import com.kingtake.base.service.formtip.TBFormTipServiceI;

/**
 * @Title: Controller
 * @Description: 项目模块配置表
 * @author onlineGenerator
 * @date 2016-01-19 11:30:07
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBFormTipController")
public class TBFormTipController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBFormTipController.class);

    @Autowired
    private TBFormTipServiceI tBFormTipService;
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
     * 项目模块配置表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBFormTip")
    public ModelAndView tPmSidecatalog(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/formtip/tBFormTipList");
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
    public void datagrid(TBFormTipEntity tBFormTip, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBFormTipEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBFormTip,
                request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBFormTipService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除项目模块配置表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBFormTipEntity tBFormTip, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBFormTip = systemService.getEntity(TBFormTipEntity.class, tBFormTip.getId());
        message = "表单填写说明删除成功";
        try {
            tBFormTipService.delete(tBFormTip);
        } catch (Exception e) {
            e.printStackTrace();
            message = "表单填写说明删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除项目模块配置表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "表单填写说明删除成功";
        try {
            for (String id : ids.split(",")) {
                TBFormTipEntity tBFormTip = systemService.getEntity(TBFormTipEntity.class, id);
                tBFormTipService.delete(tBFormTip);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "表单填写说明删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 更新项目模块配置表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBFormTipEntity tBFormTip, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "表单填写说明保存成功";
        try {
            if (StringUtils.isEmpty(tBFormTip.getId())) {
                tBFormTipService.save(tBFormTip);
            } else {
                TBFormTipEntity t = tBFormTipService.get(TBFormTipEntity.class, tBFormTip.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBFormTip, t);
                tBFormTipService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "表单填写说明保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 项目模块配置表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TBFormTipEntity tBFormTip, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBFormTip.getId())) {
            tBFormTip = tBFormTipService.getEntity(TBFormTipEntity.class, tBFormTip.getId());
            req.setAttribute("tBFormTipPage", tBFormTip);
        }
        return new ModelAndView("com/kingtake/base/formtip/tBFormTip-update");
    }

    /**
     * 根据id获取说明
     * 
     * @param tPmSidecatalog
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getTipContent")
    public void getTipContent(TBFormTipEntity tBFormTip, HttpServletRequest request, HttpServletResponse response) {
        String tipContent = "";
        tBFormTip = this.systemService.findUniqueByProperty(TBFormTipEntity.class, "businessCode",
                tBFormTip.getBusinessCode());
        if (tBFormTip != null && StringUtils.isNotEmpty(tBFormTip.getTipContent())) {
            tipContent = tBFormTip.getTipContent();
        }
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(tipContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
