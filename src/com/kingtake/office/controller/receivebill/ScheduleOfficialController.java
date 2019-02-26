package com.kingtake.office.controller.receivebill;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.dao.OfficialDao;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.office.service.receivebill.TOReceiveBillServiceI;

/**
 * @Title: Controller
 * @Description: 待办公文
 * @author onlineGenerator
 * @date 2015-07-17 15:43:38
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/scheduleOfficialController")
public class ScheduleOfficialController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ScheduleOfficialController.class);

    @Autowired
    private TOReceiveBillServiceI tOReceiveBillService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    @Autowired
    private OfficialDao officialDao;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 收文
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tOReceiveBillListToMe")
    public ModelAndView tOReceiveBillListToMe(HttpServletRequest request) {
        request.setAttribute("treated", ReceiveBillConstant.OPERATE_TREATED);
        request.setAttribute("untreated", ReceiveBillConstant.OPERATE_UNTREATED);
        request.setAttribute("registerType", request.getParameter("registerType"));
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBillListTab");
    }

    /**
     * 发文
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tOSendBillListToMe")
    public ModelAndView tOSendBillListToMe(HttpServletRequest request) {
        request.setAttribute("treated", ReceiveBillConstant.OPERATE_TREATED);
        request.setAttribute("untreated", ReceiveBillConstant.OPERATE_UNTREATED);
        request.setAttribute("registerType", request.getParameter("registerType"));
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBillListTab");
    }

    /**
     * 呈批件
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tOApprovalListToMe")
    public ModelAndView tOSendBillList_toMe(HttpServletRequest request) {
        request.setAttribute("treated", ReceiveBillConstant.OPERATE_TREATED);
        request.setAttribute("untreated", ReceiveBillConstant.OPERATE_UNTREATED);
        request.setAttribute("registerType", request.getParameter("registerType"));
        return new ModelAndView("com/kingtake/office/approval/tOApprovalListTab");
    }

    @RequestMapping(params = "goScheduleOfficial")
    public ModelAndView goBootStrap(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        String registerType = request.getParameter("registerType");//0表示收文，1表示发文
        Map<String, String> map = new HashMap<String, String>();
        map.put("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
        map.put("receiveUserid", user.getId());
        map.put("receiveDepartId", user.getCurrentDepart().getId());
        map.put("userName", user.getUserName());
        map.put("validFlag", SrmipConstants.YES);
        map.put("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
        map.put("registerType", registerType);
        Map<String, Object> countMap = new HashMap<String, Object>();
        map.put("tableName", "t_o_receive_bill");
        Integer untreatedReceiveBill = officialDao.getUntreated(map);
        map.put("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
        Integer treatedReceiveBill = officialDao.getUntreated(map);
        map.put("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
        map.put("tableName", "t_o_approval");
        Integer untreatedApproval = officialDao.getUntreated(map);
        map.put("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
        Integer treatedapproval = officialDao.getUntreated(map);
        map.put("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
        map.put("tableName", "t_o_send_bill");
        Integer untreatedSendBill = officialDao.getUntreated(map);
        map.put("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
        Integer treatedSendBill = officialDao.getUntreated(map);
        countMap.put("untreatedReceiveBill", untreatedReceiveBill);
        countMap.put("treatedReceiveBill", treatedReceiveBill);
        countMap.put("untreatedSendBill", untreatedApproval + untreatedSendBill);
        countMap.put("treatedSendBill", treatedapproval + treatedSendBill);
        request.setAttribute("countMap", countMap);
        request.setAttribute("registerType", registerType);
        return new ModelAndView("com/kingtake/office/receivebill/scheduleOfficial");
    }

}
