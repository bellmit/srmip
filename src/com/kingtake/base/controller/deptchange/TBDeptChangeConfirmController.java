package com.kingtake.base.controller.deptchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.base.entity.deptchange.TBDeptChangeConfirmEntity;
import com.kingtake.base.service.deptchange.TBDeptChangeConfirmServiceI;

/**
 * 部门变动确认表
 * 
 * @author admin
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBDeptChangeConfirmController")
public class TBDeptChangeConfirmController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBDeptChangeConfirmController.class);

    @Autowired
    private TBDeptChangeConfirmServiceI tBDeptChangeConfirmService;
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
     * 部门变动确认页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBDeptChangeConfirm")
    public ModelAndView tBDeptChangeConfirm(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/deptchange/tBDeptChangeConfirmList");
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
    public void datagrid(TBDeptChangeConfirmEntity deptChangeConfirm, HttpServletRequest request,
            HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDeptChangeConfirmEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, deptChangeConfirm,
                request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBDeptChangeConfirmService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 弹出修改意见框
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TBDeptChangeConfirmEntity apply = this.systemService.get(TBDeptChangeConfirmEntity.class, id);
            req.setAttribute("msgText", apply.getMsgText());
        }
        return new ModelAndView("com/kingtake/project/plandraft/proposePage");
    }

    /**
     * 保存修改意见
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(TBDeptChangeConfirmEntity deptChangeConfirm, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            this.tBDeptChangeConfirmService.doReject(deptChangeConfirm);
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 申请文件审核
     * 
     * @return
     */
    @RequestMapping(params = "doPass")
    @ResponseBody
    public AjaxJson doPass(TBDeptChangeConfirmEntity deptChangeConfirm, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            this.tBDeptChangeConfirmService.doPass(deptChangeConfirm);
            message = "部门变动确认通过";
        } catch (Exception e) {
            e.printStackTrace();
            message = "部门变动确认失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

}
