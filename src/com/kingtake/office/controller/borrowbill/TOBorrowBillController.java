package com.kingtake.office.controller.borrowbill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.office.entity.borrowbill.TOBorrowBillEntity;
import com.kingtake.office.entity.borrowbill.TOBorrowRecBillEntity;
import com.kingtake.office.entity.receivebill.TOReceiveBillEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.service.borrowbill.TOBorrowBillServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

/**
 * 公文借阅
 * 
 * @author admin
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOBorrowBillController")
public class TOBorrowBillController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOBorrowBillController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private TOBorrowBillServiceI tOBorrowBillService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    /**
     * 借阅申请信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOBorrowBill")
    public ModelAndView tOBorrowBill(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("userId", user.getId());
        request.setAttribute("userName", user.getUserName());
        return new ModelAndView("com/kingtake/office/borrowbill/tOBorrowBillList");
    }

    /**
     * 借阅申请信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOBorrowBillAudit")
    public ModelAndView tOBorrowBillAudit(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("userId", user.getId());
        request.setAttribute("userName", user.getUserName());
        String auditType = request.getParameter("auditType");
        request.setAttribute("auditType", auditType);
        return new ModelAndView("com/kingtake/office/borrowbill/tOBorrowBillList-audit");
    }

    /**
     * 借阅申请信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOBorrowBillAuditTab")
    public ModelAndView tOBorrowBillAuditTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/borrowbill/tOBorrowBillTab");
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
    public void datagrid(TOBorrowBillEntity tOBorrowBill, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOBorrowBillEntity.class, dataGrid);
        //查询条件组装器
        TSUser user = ResourceUtil.getSessionUserName();
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
                .installHql(cq, tOBorrowBill, request.getParameterMap());
        cq.or(Restrictions.eq("applyUserId", user.getId()), Restrictions.eq("createBy", user.getUserName()));
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tOBorrowBillService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridAudit")
    public void datagridAudit(TOBorrowBillEntity tOBorrowBill, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String auditType = request.getParameter("auditType");
        CriteriaQuery cq = new CriteriaQuery(TOBorrowBillEntity.class, dataGrid);
        //查询条件组装器
        TSUser user = ResourceUtil.getSessionUserName();
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
                .installHql(cq, tOBorrowBill, request.getParameterMap());
        if ("1".equals(auditType)) {
            cq.eq("auditStatus", "1");
        } else {
            cq.or(Restrictions.eq("auditStatus", "2"), Restrictions.eq("auditStatus", "3"));
        }
        cq.eq("borrowUnitId", user.getCurrentDepart().getId());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tOBorrowBillService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 公文借阅编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOBorrowBillEntity tOBorrowBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOBorrowBill.getId())) {
            tOBorrowBill = tOBorrowBillService.getEntity(TOBorrowBillEntity.class, tOBorrowBill.getId());
        } else {
            TSUser user = ResourceUtil.getSessionUserName();
            tOBorrowBill.setApplyUserId(user.getId());
            tOBorrowBill.setApplyUserName(user.getRealName());
        }
        req.setAttribute("tOBorrowBillPage", tOBorrowBill);

        return new ModelAndView("com/kingtake/office/borrowbill/tOBorrowBill-update");
    }

    /**
     * 删除借阅申请
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOBorrowBillEntity tOBorrowBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOBorrowBill = systemService.getEntity(TOBorrowBillEntity.class, tOBorrowBill.getId());
        message = "借阅申请删除成功";
        try {
            this.tOBorrowBillService.delete(tOBorrowBill);
        } catch (Exception e) {
            e.printStackTrace();
            message = "借阅申请删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 保存收文借阅
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOBorrowBillEntity tOBorrowBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "收文借阅保存成功";
        try {
            if (StringUtils.isNotEmpty(tOBorrowBill.getId())) {
                TOBorrowBillEntity t = tOBorrowBillService.get(TOBorrowBillEntity.class, tOBorrowBill.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOBorrowBill, t);
                tOBorrowBillService.saveOrUpdate(t);
            } else {
                tOBorrowBill.setAuditStatus("0");//未提交
                tOBorrowBillService.save(tOBorrowBill);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文借阅保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 提交借阅申请
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TOBorrowBillEntity tOBorrowBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tOBorrowBill.getId())) {
            message = "借阅申请提交成功";
            try {
                this.tOBorrowBillService.doSubmit(tOBorrowBill);
            } catch (Exception e) {
                e.printStackTrace();
                message = "借阅申请提交失败";
                j.setSuccess(false);
            }
        }
        j.setObj(tOBorrowBill);
        j.setMsg(message);
        return j;
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
            TOBorrowBillEntity apply = this.systemService.get(TOBorrowBillEntity.class, id);
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
    public AjaxJson doPropose(TOBorrowBillEntity tOBorrowBill, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "退回成功！";
        try {
            TOBorrowBillEntity t = this.systemService.get(TOBorrowBillEntity.class, tOBorrowBill.getId());
            t.setMsgText(tOBorrowBill.getMsgText());
            t.setAuditStatus("3");
            this.systemService.updateEntitie(t);
        } catch (Exception e) {
            message = "退回失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * easyui AJAX请求数据 收发文登记列表用
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForList")
    public void datagridForList(TOSendReceiveRegEntity tOSendReceiveReg, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOSendReceiveRegEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOSendReceiveReg,
                request.getParameterMap());
        try {
            // 自定义追加查询条件
            String query_registerDate_begin = request.getParameter("registerDate_begin");
            String query_registerDate_end = request.getParameter("registerDate_end");
            if (StringUtil.isNotEmpty(query_registerDate_begin)) {
                cq.ge("registerDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_registerDate_begin));
            }
            if (StringUtil.isNotEmpty(query_registerDate_end)) {
                cq.le("registerDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_registerDate_end));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询收发文登记出错", e);
        }
        TSUser user = ResourceUtil.getSessionUserName();
        cq.or(Restrictions.eq("createBy", user.getUserName()), Restrictions.eq("archiveUserid", user.getId()));
        cq.eq("generationFlag", "3");//已归档的收文
        cq.eq("registerType", "0");//收文
        cq.addOrder("generationFlag", SortDirection.asc);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tOBorrowBillService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 选择收文弹出框
     * 
     * @return
     */
    @RequestMapping(params = "goRegSelect")
    public ModelAndView goRegSelect(HttpServletRequest request) {
        String borrowId = request.getParameter("id");
        if (StringUtils.isNotEmpty(borrowId)){
            request.setAttribute("borrowId", borrowId);
        }
        return new ModelAndView("com/kingtake/office/borrowbill/tORegSelectList");
    }

    /**
     * 保存修改意见
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "doPass")
    @ResponseBody
    public AjaxJson doPass(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "审批通过成功！";
        try {
            String borrowId = req.getParameter("id");
            String regIds = req.getParameter("ids");
            this.tOBorrowBillService.doPass(borrowId, regIds);
        } catch (Exception e) {
            message = "审批通过失败！";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 保存修改意见
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "checkValid")
    @ResponseBody
    public AjaxJson checkValid(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "时限有效！";
        try {
            String borrowId = req.getParameter("id");
            TOBorrowBillEntity borrow = this.systemService.get(TOBorrowBillEntity.class, borrowId);
            if (!betweenDate(borrow.getBorrowBeginTime(), borrow.getBorrowEndTime())) {
                j.setMsg("已经超过借阅时限，不能查看公文！");
                j.setSuccess(false);
                return j;
            }
        } catch (Exception e) {
            message = "检查时限失败！";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 弹出框查看收文
     * 
     * @return
     */
    @RequestMapping(params = "goViewBorrowRecBill")
    public ModelAndView goViewBorrowRecBill(HttpServletRequest request) {
        String borrowId = request.getParameter("id");
        CriteriaQuery cq = new  CriteriaQuery(TOBorrowRecBillEntity.class);
        cq.eq("borrowId", borrowId);
        cq.add();
        List<TOBorrowRecBillEntity> billList = this.systemService.getListByCriteriaQuery(cq, false);
        List<Map<String, String>> recList = new ArrayList<Map<String, String>>();
        for (TOBorrowRecBillEntity bill : billList) {
            Map<String, String> recMap = new HashMap<String, String>();
            TOReceiveBillEntity recBill = this.systemService.get(TOReceiveBillEntity.class, bill.getRecId());
            recMap.put("title", recBill.getTitle());
            TOOfficeOnlineFilesEntity onlineFile = this.systemService.get(TOOfficeOnlineFilesEntity.class,
                    recBill.getContentFileId());
            recMap.put("path", onlineFile.getRealpath());
            recList.add(recMap);
        }
        request.setAttribute("recList", recList);
        return new ModelAndView("com/kingtake/office/borrowbill/viewRecBill");
    }

    /**
     * 判断是否在时间起止范围之内
     * 
     * @param begin
     * @param end
     * @return
     */
    private boolean betweenDate(Date begin, Date end) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(end);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        end = cal.getTime();
        if (now.after(begin) && now.before(end)) {
            return true;
        }
        return false;
    }
}
