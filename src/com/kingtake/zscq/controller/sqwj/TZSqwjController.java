package com.kingtake.zscq.controller.sqwj;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.zscq.entity.sqwj.TZSqwjEntity;
import com.kingtake.zscq.service.sqwj.TZSqwjServiceI;

/**
 * @Title: Controller
 * @Description: 申请文件
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZSqwjController")
public class TZSqwjController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TZSqwjController.class);

    @Autowired
    private TZSqwjServiceI tZSqwjService;
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
     * 跳转到申请文件界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goSqwj")
    public ModelAndView goSqwj(HttpServletRequest request) {
        String zlsqId = request.getParameter("zlsqId");
        String id = request.getParameter("id");
        String opt = request.getParameter("opt");
        String role = request.getParameter("role");
        TZSqwjEntity sqwj = null;
        if (StringUtils.isEmpty(id)) {
            List<TZSqwjEntity> sqwjList = this.systemService.findByProperty(TZSqwjEntity.class, "zlsqId", zlsqId);
            if (sqwjList.size() == 0) {
                sqwj = new TZSqwjEntity();
                sqwj.setZlsqId(zlsqId);
                sqwj.setQqs(UUIDGenerator.generate());
                sqwj.setSmszy(UUIDGenerator.generate());
                sqwj.setZyft(UUIDGenerator.generate());
                sqwj.setQlyqs(UUIDGenerator.generate());
                sqwj.setSms(UUIDGenerator.generate());
                sqwj.setSmsft(UUIDGenerator.generate());
                sqwj.setSzscqqs(UUIDGenerator.generate());
                sqwj.setMjzm(UUIDGenerator.generate());
                sqwj.setFyjhqqs(UUIDGenerator.generate());
                sqwj.setFyjhqqzm(UUIDGenerator.generate());
                sqwj.setZldlwts(UUIDGenerator.generate());
                sqwj.setApplyStatus("0");
                this.systemService.save(sqwj);
            } else {
                sqwj = sqwjList.get(0);
            }
        } else {
            sqwj = this.systemService.get(TZSqwjEntity.class, id);
        }
        request.setAttribute("sqwjPage", sqwj);
        if (StringUtils.isNotEmpty(opt)) {
            request.setAttribute("opt", opt);
        }
        if (StringUtils.isNotEmpty(role)) {
            request.setAttribute("role", role);
        }
        return new ModelAndView("com/kingtake/zscq/sqwj/tZSqwj");
    }


    /**
     * 跳转到申请文件界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goFileUpload")
    public ModelAndView goFileUpload(HttpServletRequest request) {
        String opt = request.getParameter("opt");
        String bid = request.getParameter("bid");
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(bid)) {
            request.setAttribute("bid", bid);
            List<TSFilesEntity> attachments = systemService.getAttachmentByCode(bid, "");
            request.setAttribute("attachments", attachments);
        }
        TZSqwjEntity sqwj = this.systemService.get(TZSqwjEntity.class, id);
        if (StringUtils.isEmpty(opt)) {
            if ("1".equals(sqwj.getApplyStatus()) || "3".equals(sqwj.getApplyStatus())) {
                opt = "view";
            }
        }
        request.setAttribute("opt", opt);
        return new ModelAndView("com/kingtake/zscq/sqwj/fileUpload");
    }

    /**
     * 发送申请文件
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TZSqwjEntity sqwj, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
            message = "申请文件发送成功";
            try {
            tZSqwjService.doSubmit(sqwj);
            } catch (Exception e) {
                e.printStackTrace();
            message = "申请文件发送失败";
                j.setSuccess(false);
            }
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转到机关审核界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goApplyAuditDepartment")
    public ModelAndView goApplyAuditDepartment(HttpServletRequest request) {
        String type = request.getParameter("type");
        if (StringUtils.isNotEmpty(type)) {
            request.setAttribute("type", type);
        }
        return new ModelAndView("com/kingtake/zscq/sqwj/apprList-receive");
    }

    /**
     * 跳转到机关审核界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goApplyAuditTab")
    public ModelAndView goApplyAuditTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/zscq/sqwj/apprList-receiveTab");
    }

    /**
     * 查询审查列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridAudit")
    public void datagridAudit(HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String type = request.getParameter("type");
        String sql = "select * from (select w.id,s.id zlsqId,s.gdh,s.wcdw,s.lx,s.glxm,s.mc,s.fmr,w.apply_status applyStatus , w.submit_time,w.msg_text msgText "
                + "from t_z_sqwj w join t_z_zlsq s on w.zlsq_id=s.id where w.check_user_id=? ";
        if ("0".equals(type)) {//待处理
            sql = sql + " and w.apply_status = '1'";
        } else {//已处理
            sql = sql + " and (w.apply_status = '2' or w.apply_status = '3')";
        }
        sql = sql + ") t order by t.submit_time desc";
        String countSql = "select count(1) from (" + sql + ")";
        List<Map<String, Object>> dataList = this.tZSqwjService
.findForJdbcParam(sql, dataGrid.getPage(),
                dataGrid.getRows(), new Object[] { user.getId() });
        for(Map<String,Object> map :dataList){
            String msgText = (String) map.get("msgText");
            if (StringUtils.isNotEmpty(msgText)) {
                map.put("ismsgText", "1");
            }
        }
        Long count = this.tZSqwjService.getCountForJdbcParam(countSql, new Object[] { user.getId() });
        dataGrid.setResults(dataList);
        dataGrid.setTotal(count.intValue());
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
            TZSqwjEntity apply = this.systemService.get(TZSqwjEntity.class, id);
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
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            if (StringUtil.isNotEmpty(id)) {
                TZSqwjEntity apply = systemService.get(TZSqwjEntity.class, id);
                apply.setMsgText(msgText);
                apply.setApplyStatus("2");
                systemService.updateEntitie(apply);
            }
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
    public AjaxJson doPass(TZSqwjEntity sqwj, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            sqwj = systemService.getEntity(TZSqwjEntity.class, sqwj.getId());
            sqwj.setApplyStatus("3");//auditStatus 为3 通过
            this.tZSqwjService.updateEntitie(sqwj);
            message = "专利申请文件审批通过";
        } catch (Exception e) {
            e.printStackTrace();
            message = "专利申请文件审批失败";
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
    public ModelAndView goConfirm(TZSqwjEntity sqwj, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(sqwj.getId())) {
            sqwj = tZSqwjService.getEntity(TZSqwjEntity.class, sqwj.getId());
            req.setAttribute("msgText", sqwj.getMsgText());
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
    public AjaxJson doConfirm(TZSqwjEntity sqwj, HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        AjaxJson j = new AjaxJson();
        String qrzt = req.getParameter("qrzt");
        String xgyj = req.getParameter("xgyj");
        try {
            if (StringUtil.isNotEmpty(sqwj.getId())) {
                sqwj = tZSqwjService.getEntity(TZSqwjEntity.class, sqwj.getId());
                if ("1".equals(qrzt)) {
                    sqwj.setApplyStatus("3");//确认
                } else {
                    sqwj.setApplyStatus("2");//返回修改
                }
                sqwj.setMsgText(xgyj);
                sqwj.setCheckUserId(user.getId());
                sqwj.setCheckUserName(user.getRealName());
                this.systemService.updateEntitie(sqwj);
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
