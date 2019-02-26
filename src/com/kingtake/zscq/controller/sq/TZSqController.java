package com.kingtake.zscq.controller.sq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.zscq.entity.sq.TZJfjlEntity;
import com.kingtake.zscq.entity.sq.TZSqEntity;
import com.kingtake.zscq.service.sq.TZSqServiceI;



/**
 * @Title: Controller
 * @Description: 缴费记录
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZSqController")
public class TZSqController extends BaseController {
	/**
	 * Logger for this class
	 */
    private static final Logger logger = Logger.getLogger(TZSqController.class);

	@Autowired
    private TZSqServiceI tZSqService;
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
     * 保存授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate", method = RequestMethod.POST)
	@ResponseBody
    public AjaxJson doUpdate(TZSqEntity sq, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "授权保存成功！";
        try {
            this.tZSqService.saveSq(sq, request);
        } catch (Exception e) {
            e.printStackTrace();
            message = "授权保存失败！";
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
    public ModelAndView goUpdate(TZSqEntity sq, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(sq.getZlsqId())) {
            List<TZSqEntity> list = tZSqService.findByProperty(TZSqEntity.class, "zlsqId", sq.getZlsqId());
            if (list.size() > 0) {
                sq = list.get(0);
            }
        } else if (StringUtil.isNotEmpty(sq.getId())) {
            sq = tZSqService.getEntity(TZSqEntity.class, sq.getId());
        }
        if (StringUtils.isEmpty(sq.getFjbm())) {
            sq.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(sq.getFjbm(), "");
            sq.setAttachments(fileList);
        }
        req.setAttribute("sqPage", sq);
        String role = req.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            req.setAttribute("role", role);
        }
        return new ModelAndView("com/kingtake/zscq/sq/tZSq-update");
    }
	
    /**
     * 年费列表
     * 
     * @param jfjl
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "nfList")
    @ResponseBody
    public JSONObject nfList(TZJfjlEntity jfjl, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        JSONObject json = new JSONObject();
        CriteriaQuery cq = new CriteriaQuery(TZJfjlEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, jfjl, request.getParameterMap());
        cq.eq("csbj", "1");//初始录入的
        cq.addOrder("fyspsj", SortDirection.desc);
        cq.add();
        this.tZSqService.getDataGridReturn(cq, true);
        List<TZJfjlEntity> results = dataGrid.getResults();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        JSONArray array = new JSONArray();
        for (TZJfjlEntity tmp : results) {
            JSONObject tmpJson = new JSONObject();
            String year = sdf.format(tmp.getFyspsj());
            tmpJson.put("fyspsj", year);
            tmpJson.put("jnje", tmp.getJnje());
            array.add(tmpJson);
        }
        json.put("rows", array);
        json.put("total", array.size());
        return json;
    }

    /**
     * 缴费记录列表跳转
     * 
     * @param jfjl
     * @param req
     * @return
     */
    @RequestMapping(params = "goJfjlList")
    public ModelAndView goJfjlList(TZJfjlEntity jfjl, HttpServletRequest req) {
        String role = req.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            req.setAttribute("role", role);
        }
        String sqId = req.getParameter("sqId");
        if (StringUtils.isNotEmpty(sqId)) {
            req.setAttribute("sqId", sqId);
        }
        return new ModelAndView("com/kingtake/zscq/sq/tZJfjlList");
    }
    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "jfjlDatagrid")
    public void jfjlDatagrid(TZJfjlEntity jfjl, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String role = request.getParameter("role");
        CriteriaQuery cq = new CriteriaQuery(TZJfjlEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, jfjl, request.getParameterMap());
        cq.isNull("csbj");
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tZSqService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goJfjlUpdate")
    public ModelAndView goJfjlUpdate(TZJfjlEntity jfjl, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(jfjl.getId())) {
            jfjl = tZSqService.getEntity(TZJfjlEntity.class, jfjl.getId());
        }
        if (jfjl.getFyspsj() == null) {
            jfjl.setFyspsj(new Date());
        }
        req.setAttribute("jfjlPage", jfjl);
        String role = req.getParameter("role");
        if (StringUtils.isNotEmpty(role)) {
            req.setAttribute("role", role);
        }
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", opt);
        }
        return new ModelAndView("com/kingtake/zscq/sq/tZJfjl-update");
    }

    /**
     * 保存授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doJfjlUpdate", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doJfjlUpdate(TZJfjlEntity jfjl, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "缴费记录保存成功";
        try {
            if (StringUtils.isEmpty(jfjl.getId())) {
                this.tZSqService.save(jfjl);
            } else {
                TZJfjlEntity t = tZSqService.get(TZJfjlEntity.class, jfjl.getId());
                MyBeanUtils.copyBeanNotNull2Bean(jfjl, t);
                tZSqService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "缴费记录失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 删除T_Z_ZLSQ
     * 
     * @return
     */
    @RequestMapping(params = "doDelJfjl")
    @ResponseBody
    public AjaxJson doDel(TZJfjlEntity jfjl, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "缴费记录删除成功";
        try {
            jfjl = this.systemService.get(TZJfjlEntity.class, jfjl.getId());
            if (jfjl.getFyspsj() != null) {
                j.setMsg("缴费记录已审批通过，不能删除！");
                j.setSuccess(true);
                return j;
            }
            tZSqService.delJfjl(jfjl);
        } catch (Exception e) {
            e.printStackTrace();
            message = "缴费记录删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
