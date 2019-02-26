package com.kingtake.zscq.controller.zhyy;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.zscq.entity.zhyy.TZZhyyEntity;
import com.kingtake.zscq.service.zhyy.TZZhyyServiceI;



/**
 * @Title: Controller
 * @Description: 转化应用
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZZhyyController")
public class TZZhyyController extends BaseController {
	/**
	 * Logger for this class
	 */
    private static final Logger logger = Logger.getLogger(TZZhyyController.class);

	@Autowired
    private TZZhyyServiceI tZZhyyService;
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
     * 删除申请人信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TZZhyyEntity zhyy, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "转化应用删除成功";
        try {
            zhyy = systemService.getEntity(TZZhyyEntity.class, zhyy.getId());
            tZZhyyService.delZhyy(zhyy);
        } catch (Exception e) {
            e.printStackTrace();
            message = "转化应用删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 保存受理通知书
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate", method = RequestMethod.POST)
	@ResponseBody
    public AjaxJson doUpdate(TZZhyyEntity zhyy, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "转化应用保存成功！";
        try {
            this.tZZhyyService.saveZhyy(zhyy);
        } catch (Exception e) {
            e.printStackTrace();
            message = "转化应用保存失败！";
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
    public ModelAndView goUpdate(TZZhyyEntity zhyy, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(zhyy.getId())) {
            zhyy = tZZhyyService.getEntity(TZZhyyEntity.class, zhyy.getId());
        }
        if (StringUtils.isEmpty(zhyy.getFjbm())) {
            zhyy.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(zhyy.getFjbm(), "");
            zhyy.setAttachments(fileList);
        }
        req.setAttribute("zhyyPage", zhyy);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/zhyy/zhyy-update");
    }

    /**
     * 跳转到确认界面
     * 
     * @return
     */
    @RequestMapping(params = "goConfirm")
    public ModelAndView goConfirm(TZZhyyEntity zhyy, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(zhyy.getId())) {
            zhyy = tZZhyyService.getEntity(TZZhyyEntity.class, zhyy.getId());
            req.setAttribute("msgText", zhyy.getMsgText());
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
    public AjaxJson doConfirm(TZZhyyEntity zhyy, HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        AjaxJson j = new AjaxJson();
        String qrzt = req.getParameter("qrzt");
        String xgyj = req.getParameter("xgyj");
        try {
            if (StringUtil.isNotEmpty(zhyy.getId())) {
                zhyy = tZZhyyService.getEntity(TZZhyyEntity.class, zhyy.getId());
                if ("1".equals(qrzt)) {
                    zhyy.setQrzt("3");//确认
                } else {
                    zhyy.setQrzt("2");//返回修改
                }
                zhyy.setMsgText(xgyj);
                zhyy.setCheckUserId(user.getId());
                zhyy.setCheckUserName(user.getRealName());
                this.systemService.updateEntitie(zhyy);
                j.setMsg("确认成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("确认失败！");
        }
        return j;
    }

    /**
     * 转化应用列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goZhyyList")
    public ModelAndView goZhyyList(HttpServletRequest request) {
        String zlsqId = request.getParameter("zlsqId");
        if (StringUtils.isNotEmpty(zlsqId)) {
            request.setAttribute("zlsqId", zlsqId);
        }
        String role = request.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        return new ModelAndView("com/kingtake/zscq/zhyy/zhyyList");
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
    public void datagrid(TZZhyyEntity zhyy, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TZZhyyEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, zhyy, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tZZhyyService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 提交
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TZZhyyEntity zhyy, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "转化应用提交成功";
        try {
            zhyy = this.systemService.get(TZZhyyEntity.class, zhyy.getId());
            zhyy.setQrzt("1");//已提交
            this.systemService.updateEntitie(zhyy);
        } catch (Exception e) {
            e.printStackTrace();
            message = "转化应用提交失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 填写修改意见
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TZZhyyEntity apply = this.systemService.get(TZZhyyEntity.class, id);
            req.setAttribute("msgText", apply.getMsgText());
        }
        return new ModelAndView("com/kingtake/project/plandraft/proposePage");
    }
}
