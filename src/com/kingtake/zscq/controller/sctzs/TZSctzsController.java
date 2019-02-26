package com.kingtake.zscq.controller.sctzs;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.zscq.entity.sctzs.TZSctzsEntity;
import com.kingtake.zscq.service.sctzs.TZSctzsServiceI;



/**
 * @Title: Controller
 * @Description: 审查通知书
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZSctzsController")
public class TZSctzsController extends BaseController {
	/**
	 * Logger for this class
	 */
    private static final Logger logger = Logger.getLogger(TZSctzsController.class);

	@Autowired
    private TZSctzsServiceI tZSctzsService;
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
     * 申请人信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tZSctzs")
    public ModelAndView tZSctzs(HttpServletRequest request) {
        String zlsqId = request.getParameter("zlsqId");
        if (StringUtils.isNotEmpty(zlsqId)) {
            request.setAttribute("zlsqId", zlsqId);
        }
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        return new ModelAndView("com/kingtake/zscq/sctzs/tZSctzsList");
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
    public void datagrid(TZSctzsEntity tZSctzs, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TZSctzsEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZSctzs, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tZSctzsService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 保存受理通知书
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate", method = RequestMethod.POST)
	@ResponseBody
    public AjaxJson doUpdate(TZSctzsEntity tZSctzs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "审查通知书保存成功！";
        try {
            String opt = request.getParameter("opt");
            this.tZSctzsService.saveSctzs(opt, tZSctzs);
        } catch (Exception e) {
            e.printStackTrace();
            message = "审查通知书保存失败！";
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
    public ModelAndView goUpdate(TZSctzsEntity tZSctzs, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZSctzs.getId())) {
            tZSctzs = tZSctzsService.getEntity(TZSctzsEntity.class, tZSctzs.getId());
        }
        if (StringUtils.isEmpty(tZSctzs.getFjbm())) {
            tZSctzs.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tZSctzs.getFjbm(), "");
            tZSctzs.setAttachments(fileList);
        }
        if (StringUtils.isEmpty(tZSctzs.getScyj())) {
            tZSctzs.setScyj(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tZSctzs.getScyj(), "");
            tZSctzs.setYjattachments(fileList);
        }
        req.setAttribute("tZSctzsPage", tZSctzs);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", opt);
        }
        return new ModelAndView("com/kingtake/zscq/sctzs/tZSctzs-update");
    }
	
	
    /**
     * 删除申请人信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TZSctzsEntity tZSctzs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tZSctzs = systemService.getEntity(TZSctzsEntity.class, tZSctzs.getId());
        message = "审查通知书删除成功";
        try {
            tZSctzsService.delSctzs(tZSctzs);
        } catch (Exception e) {
            e.printStackTrace();
            message = "审查通知书删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转到确认界面
     * 
     * @return
     */
    @RequestMapping(params = "goConfirm")
    public ModelAndView goConfirm(TZSctzsEntity tZSctzs, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZSctzs.getId())) {
            tZSctzs = tZSctzsService.getEntity(TZSctzsEntity.class, tZSctzs.getId());
            req.setAttribute("msgText", tZSctzs.getXgyj());
        }
        return new ModelAndView("com/kingtake/zscq/sctzs/confirmPage");
    }

    /**
     * 确认
     * 
     * @return
     */
    @RequestMapping(params = "doConfirm")
    @ResponseBody
    public AjaxJson doConfirm(TZSctzsEntity tZSctzs, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String qrzt = req.getParameter("qrzt");
        String xgyj = req.getParameter("xgyj");
        try {
            if (StringUtil.isNotEmpty(tZSctzs.getId())) {
                tZSctzs = tZSctzsService.getEntity(TZSctzsEntity.class, tZSctzs.getId());
                if ("1".equals(qrzt)) {
                    tZSctzs.setQrzt("4");//确认
                } else {
                    tZSctzs.setQrzt("3");//返回修改
                }
                tZSctzs.setXgyj(xgyj);
                this.systemService.updateEntitie(tZSctzs);
                j.setMsg("确认成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("确认失败！");
        }
        return j;
    }

}
