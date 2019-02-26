package com.kingtake.zscq.controller.zlzz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.zscq.entity.zlzz.TZZlzzEntity;
import com.kingtake.zscq.service.zlzz.TZZlzzServiceI;

/**
 * @Title: Controller
 * @Description: 专利奖励
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZZlzzController")
public class TZZlzzController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TZZlzzController.class);

    @Autowired
    private TZZlzzServiceI tZZlzzService;
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
     * 专利终止信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goList")
    public ModelAndView goList(HttpServletRequest request) {
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        String zlsqId = request.getParameter("zlsqId");
        if (StringUtils.isNotEmpty(zlsqId)) {
            request.setAttribute("zlsqId", zlsqId);
        }
        return new ModelAndView("com/kingtake/zscq/zlzz/zlzzList");
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
    public void datagrid(TZZlzzEntity zlzz, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TZZlzzEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, zlzz, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tZZlzzService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 删除代理机构信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TZZlzzEntity zlzz, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        zlzz = systemService.getEntity(TZZlzzEntity.class, zlzz.getId());
        message = "专利终止删除成功";
        try {
            tZZlzzService.delete(zlzz);
        } catch (Exception e) {
            e.printStackTrace();
            message = "专利终止删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TZZlzzEntity zlzz, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "专利终止保存成功";
        try {
            this.tZZlzzService.saveZlzz(zlzz);
        } catch (Exception e) {
            e.printStackTrace();
            message = "专利终止保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TZZlzzEntity zlzz, HttpServletRequest req) {
        String zzzt = req.getParameter("zzzt");
        if (StringUtil.isNotEmpty(zlzz.getId())) {
            zlzz = tZZlzzService.getEntity(TZZlzzEntity.class, zlzz.getId());
        }
        if (StringUtils.isEmpty(zlzz.getFjbm())) {
            zlzz.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(zlzz.getFjbm(), "");
            zlzz.setAttachments(fileList);
        }
        if (StringUtils.isNotEmpty(zzzt)) {
            zlzz.setZzzt(zzzt);
        }
        req.setAttribute("zlzzPage", zlzz);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/zlzz/zlzz-update");
    }
}
