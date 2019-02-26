package com.kingtake.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.project.controller.manage.ProjectMgrController;

/**
 * 选择人员
 * 
 * @author admin
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/commonMessageController")
public class CommonMessageController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ProjectMgrController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private CommonMessageServiceI commonMessageService;

    /**
     * 跳转到界面
     * 
     * @return
     */
    @RequestMapping(params = "commonMsg")
    public ModelAndView commonUser(HttpServletRequest request) {
        String msgType = request.getParameter("msgType");
        if (StringUtils.isNotEmpty(msgType)) {
            request.setAttribute("msgType", msgType);
        }
        return new ModelAndView("com/kingtake/common/sendMsg");
    }

    /**
     * 跳转到在线聊天界面
     * 
     * @return
     */
    @RequestMapping(params = "onlineMsg")
    public ModelAndView onlineMsg(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/common/sendOnlineMsg");
    }

    /**
     * 保存常用联系人
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "sendMsg")
    @ResponseBody
    public AjaxJson sendMsg(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String userIds = request.getParameter("userId");
            String msgType = request.getParameter("msgType");
            String msgTitle = request.getParameter("msgTitle");
            String msgContent = request.getParameter("msgContent");
            commonMessageService.sendMessage(userIds, msgType, msgTitle, msgContent);
            j.setMsg("发送消息成功！");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("发送消息失败！");
        }
        return j;
    }

}
