package com.kingtake.office.controller.billdown;

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

import com.kingtake.office.entity.approval.TOApprovalEntity;
import com.kingtake.office.entity.billdown.TOSendDownBillEntity;
import com.kingtake.office.entity.borrowbill.TOBorrowBillEntity;
import com.kingtake.office.entity.borrowbill.TOBorrowRecBillEntity;
import com.kingtake.office.entity.receivebill.TOReceiveBillEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.entity.sendbill.TOSendBillEntity;
import com.kingtake.office.service.billdown.TOSendDownBillServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

/**
 * 公文下达
 * 
 * @author admin
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOSendDownBillController")
public class TOSendDownBillController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOSendDownBillController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private TOSendDownBillServiceI tOSendDownBillService;

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
    @RequestMapping(params = "tOSendDownBill")
    public ModelAndView tOSendDownBill(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/billdown/tOBorrowBillTab");
    }

    /**
     * 借阅申请信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOMyReceiveList")
    public ModelAndView tOMyReceiveList(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("userId", user.getId());
        return new ModelAndView("com/kingtake/office/billdown/tOMyReceiveBillList");
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
    public void datagrid(TOSendDownBillEntity tOSendDownBill, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String title = request.getParameter("title");
        String regType = request.getParameter("regType");
        String query_registerDate_begin = request.getParameter("registerDate_begin");
        String query_registerDate_end = request.getParameter("registerDate_end");
       
        String sql = "select * from (select t.id downId, t.bill_id id, t.sender_id senderId, "
                + "t.sender_name senderName, t.send_time sendTime, t.bill_code billCode, r.title,"
                + " t.create_date createDate, t.status,t.send_status sendStatus, t.receiver_id, t.receiver_name,"
                + " r.merge_file_num mergeFileNum, r.security_grade securityGrade, r.reg_type regType,"
                + " r.register_date registerDate, r.generation_flag generationFlag, t.send_status from t_o_send_down_bill t"
                + " join  t_o_send_receive_reg r on t.bill_id = r.id where t.receiver_id = ? ";
        sql = sql+" and 1=1";
        if (StringUtil.isNotEmpty(query_registerDate_begin)) {
           sql = sql+" and r.register_date>=to_date('"+query_registerDate_begin+"','yyyy-MM-dd')";
        }
        if (StringUtil.isNotEmpty(query_registerDate_end)) {
            sql = sql+" and r.register_date<=to_date('"+query_registerDate_end+"','yyyy-MM-dd')";
        }
        if (StringUtils.isNotEmpty(title)) {
            sql = sql + "and r.title like '%" + title + "%'";
        }
        if (StringUtils.isNotEmpty(regType)) {
            sql = sql + "and r.reg_type = '" + regType + "'";
        }
        sql = sql + ") order by createDate desc";
        String countSql = "select count(1) from (" + sql + ")";
        List<Map<String, Object>> dataList = this.systemService.findForJdbcParam(sql, dataGrid.getPage(),
                dataGrid.getRows(), new Object[] { user.getId() });
        Long count = this.systemService.getCountForJdbcParam(countSql, new Object[] { user.getId() });
        dataGrid.setTotal(count.intValue());
        dataGrid.setResults(dataList);
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
            tOBorrowBill = tOSendDownBillService.getEntity(TOBorrowBillEntity.class, tOBorrowBill.getId());
        } else {
            TSUser user = ResourceUtil.getSessionUserName();
            tOBorrowBill.setApplyUserId(user.getId());
            tOBorrowBill.setApplyUserName(user.getRealName());
        }
        req.setAttribute("tOBorrowBillPage", tOBorrowBill);

        return new ModelAndView("com/kingtake/office/borrowbill/tOBorrowBill-update");
    }

    /**
     * 删除下达发文
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOSendDownBillEntity tOSendDownBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "发文下达删除成功";
        try {
            this.tOSendDownBillService.doDelete(tOSendDownBill);
        } catch (Exception e) {
            e.printStackTrace();
            message = "发文下达删除失败";
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
    @RequestMapping(params = "doAddSendDown")
    @ResponseBody
    public AjaxJson doAddSendDown(TOBorrowBillEntity tOBorrowBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "新增发文下达成功";
        try {
            String id = request.getParameter("ids");
            this.tOSendDownBillService.addSendDown(id);
        } catch (Exception e) {
            e.printStackTrace();
            message = "新增发文下达失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 发送
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSend")
    @ResponseBody
    public AjaxJson doSend(TOSendDownBillEntity tOSendDownBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String regId = request.getParameter("regId");
        String receiverId = request.getParameter("receiverId");
        String receiverName = request.getParameter("receiverName");
        String showFlag = request.getParameter("showFlag");
        if (StringUtils.isNotEmpty(regId)) {
            tOSendDownBill.setBillId(regId);
        }
            message = "发文下达成功";
            try {
                this.tOSendDownBillService.doSubmit(tOSendDownBill, receiverId, receiverName, showFlag);
            } catch (Exception e) {
                e.printStackTrace();
                message = "发文下达失败";
                j.setSuccess(false);
        }
        j.setObj(tOSendDownBill);
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
        cq.eq("generationFlag", "3");//已归档的发文
        cq.eq("registerType", "1");//发文
        cq.addOrder("generationFlag", SortDirection.asc);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tOSendDownBillService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 选择收文弹出框
     * 
     * @return
     */
    @RequestMapping(params = "tOMyBillList")
    public ModelAndView tOMyBillList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/billdown/tOMyBillList");
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
        CriteriaQuery cq = new CriteriaQuery(TOBorrowRecBillEntity.class);
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

    /**
     * 弹出框查看下达发文
     * 
     * @return
     */
    @RequestMapping(params = "goViewDownBill")
    public ModelAndView goViewDownBill(HttpServletRequest request) {
        String regId = request.getParameter("id");
        String downId = request.getParameter("downId");
        if (StringUtils.isNotEmpty(downId)) {
            TOSendDownBillEntity sendDown = this.tOSendDownBillService.get(TOSendDownBillEntity.class, downId);
            sendDown.setReceiveTime(new Date());
            sendDown.setStatus("1");
            this.tOSendDownBillService.updateEntitie(sendDown);
            request.setAttribute("flowShow", sendDown.getFlowShow());
            request.setAttribute("contentShow", sendDown.getContentShow());
            request.setAttribute("attachementShow", sendDown.getAttachementShow());
        } else {
            request.setAttribute("flowShow", "1");
            request.setAttribute("contentShow", "1");
            request.setAttribute("attachementShow", "1");
        }
        TOSendReceiveRegEntity reg = this.tOSendDownBillService.get(TOSendReceiveRegEntity.class, regId);
        if ("13".equals(reg.getRegType())) {//呈批件
            List<TOApprovalEntity> tOApprovals = systemService.findByProperty(TOApprovalEntity.class, "regId", regId);
            TOApprovalEntity tOApproval = null;
            if (tOApprovals.size() > 0) {
                tOApproval = tOApprovals.get(0);
                request.setAttribute("tOApprovalPage", tOApproval);
                TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                        tOApproval.getContentFileId());
                request.setAttribute("file", file);
            }
        } else {//发文
            List<TOSendBillEntity> tOSendBills = systemService.findByProperty(TOSendBillEntity.class, "regId", regId);
            TOSendBillEntity sendBill = null;
            if (tOSendBills.size() > 0) {
                sendBill = tOSendBills.get(0);
                request.setAttribute("tOSendBillPage", sendBill);
                TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                        sendBill.getContentFileId());
                request.setAttribute("file", file);
            }
        }
        if ("13".equals(reg.getRegType())) {
            return new ModelAndView("com/kingtake/office/billdown/viewApproval");
        } else {
            return new ModelAndView("com/kingtake/office/billdown/viewSendBill");
        }
    }

    /**
     * 接收交班材料信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doRecieve")
    @ResponseBody
    public AjaxJson doRecieve(TOSendDownBillEntity sendDownBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(sendDownBill.getId())) {
            message = "发文下达接收成功";
            try {
                this.tOSendDownBillService.doReceive(sendDownBill);
            } catch (Exception e) {
                e.printStackTrace();
                message = "发文下达接收失败";
                j.setSuccess(false);
            }
        }
        j.setObj(sendDownBill);
        j.setMsg(message);
        return j;
    }

    /**
     * 弹出框查看接收情况
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveLogList")
    public ModelAndView goReceiveLogList(HttpServletRequest request) {
        String billId = request.getParameter("billId");
        if (StringUtils.isNotEmpty(billId)) {
            request.setAttribute("billId", billId);
        }
        return new ModelAndView("com/kingtake/office/billdown/receiveLogList");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "logDatagrid")
    public void logDatagrid(TOSendDownBillEntity sendDown, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        try {
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery cq = new CriteriaQuery(TOSendDownBillEntity.class, dataGrid);
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
                    .installHql(cq, sendDown,
                    request.getParameterMap());
            cq.eq("senderId", user.getId());
            cq.addOrder("receiveTime", SortDirection.asc);
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到选人的界面
     * 
     * @return
     */
    @RequestMapping(params = "goSelectOperator")
    public ModelAndView goSelectOperator(HttpServletRequest request) {
        String mode = request.getParameter("mode");
        String regId = request.getParameter("regId");
        String role = request.getParameter("role");
        if (!"send".equals(role)) {
            TSUser user = ResourceUtil.getSessionUserName();
            TOSendDownBillEntity sendDown = this.tOSendDownBillService.getSendDownByReg(regId, "", user.getId());
            if (sendDown != null) {
                request.setAttribute("sendDown", sendDown);
            }
        }
        if (StringUtils.isNotEmpty(mode)) {
            request.setAttribute("mode", mode);
        } else {
            request.setAttribute("mode", "single");//默认选单个人
        }
        return new ModelAndView("com/kingtake/office/billdown/selectOperator");
    }

    /**
     * 跳转到修改查看权限的界面
     * 
     * @return
     */
    @RequestMapping(params = "goOperation")
    public ModelAndView goOperation(HttpServletRequest request) {
        String id = request.getParameter("id");
        TOSendDownBillEntity sendDown = this.tOSendDownBillService.get(TOSendDownBillEntity.class, id);
        if (sendDown != null && StringUtils.isNotEmpty(sendDown.getSourceId())) {
            TOSendDownBillEntity parentDown = this.tOSendDownBillService.get(TOSendDownBillEntity.class,
                    sendDown.getSourceId());
            if (parentDown != null) {
                request.setAttribute("parentDown", parentDown);
            }
        }
        request.setAttribute("billDown", sendDown);
        return new ModelAndView("com/kingtake/office/billdown/operation");
    }

    /**
     * 修改查看权限
     * 
     * @return
     */
    @RequestMapping(params = "updateOperation")
    @ResponseBody
    public AjaxJson updateOperation(TOSendDownBillEntity sendDown, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
        TOSendDownBillEntity query = this.tOSendDownBillService.get(TOSendDownBillEntity.class, sendDown.getId());
        query.setFlowShow(sendDown.getFlowShow());
        query.setContentShow(sendDown.getContentShow());
        query.setAttachementShow(sendDown.getAttachementShow());
        this.systemService.updateEntitie(query);
            j.setMsg("更新公文权限成功！");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("更新公文权限失败！");
        }
        return j;
    }


}
