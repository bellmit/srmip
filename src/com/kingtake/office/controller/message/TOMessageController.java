package com.kingtake.office.controller.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.page.message.TOMessagePage;
import com.kingtake.office.service.message.TOMessageServiceI;

/**
 * @Title: Controller
 * @Description: 系统消息
 * @author onlineGenerator
 * @date 2015-07-07 17:03:31
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOMessageController")
public class TOMessageController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOMessageController.class);

    @Autowired
    private TOMessageServiceI tOMessageService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "tOMessageTab")
    public ModelAndView tOMessageTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/message/tOMessageListTab");
    }

    /**
     * 我发出的消息
     * 
     * @return
     */
    @RequestMapping(params = "tOMessage")
    public ModelAndView tOMessage(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/message/tOMessageList");
    }

    /**
     * 我接收的消息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tOMessageToMe")
    public ModelAndView tOMessageToMe(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/message/tOMessageList-toMe");
    }

    /**
     * 我发出的消息列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TOMessageEntity tOMessage, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOMessageEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOMessage);
        TSUser user = ResourceUtil.getSessionUserName();
        try {
            //自定义追加查询条件
            cq.eq("senderId", user.getId());
            String sendTimeBegin = request.getParameter("sendTime_begin");
            String sendTimeEnd = request.getParameter("sendTime_end");
            if (StringUtil.isNotEmpty(sendTimeBegin)) {
                cq.ge("sendTime", DateUtils.str2Date(sendTimeBegin, DateUtils.date_sdf));
            }
            if (StringUtil.isNotEmpty(sendTimeEnd)) {
                Date end = DateUtils.str2Date(sendTimeEnd, DateUtils.date_sdf);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(end);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                end = new Date(calendar.getTimeInMillis());
                cq.lt("sendTime", end);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tOMessageService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridToMe")
    public void datagridToMe(TOMessageEntity tOMessage, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String delFlag = request.getParameter("df");
        TSUser user = ResourceUtil.getSessionUserName();
        String sendName = request.getParameter("sendName");
        String title = request.getParameter("title");

        String begin = request.getParameter("sendTime_begin");
        String end = request.getParameter("sendTime_end");
        StringBuffer cb = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT T.*,T.SENDER_NAME AS sendName,T.SEND_TIME AS sendTime,R.READ_FLAG AS READFLAG FROM T_O_MESSAGE T  JOIN T_O_MESSAGE_READ R ON R.RECEIVER_ID = ? AND R.T_O_ID = T.ID WHERE 1=1 ");
        cb.append("SELECT COUNT(0) FROM T_O_MESSAGE T  JOIN T_O_MESSAGE_READ R ON R.RECEIVER_ID = ? AND R.T_O_ID = T.ID WHERE 1=1 ");
        if (StringUtil.isNotEmpty(sendName)) {
            sb.append(" AND T.SENDER_NAME LIKE '%" + sendName + "%'");
            cb.append(" AND T.SENDER_NAME LIKE '%" + sendName + "%'");
        }
        if (StringUtil.isNotEmpty(title)) {
            sb.append(" AND T.TITLE LIKE '%" + title + "%'");
            cb.append(" AND T.TITLE LIKE '%" + title + "%'");
        }

        if (StringUtil.isNotEmpty(begin)) {
            sb.append(" and t.SEND_TIME >= to_date('" + begin + "','yyyy-mm-dd')");
            cb.append(" and t.SEND_TIME >= to_date('" + begin + "','yyyy-mm-dd')");
        }

        if (StringUtil.isNotEmpty(end)) {
            sb.append(" and t.SEND_TIME < (to_date('" + end + "','yyyy-mm-dd')+1)");
            cb.append("and t.SEND_TIME < (to_date('" + end + "','yyyy-mm-dd')+1)");
        }

        if ("0".equals(delFlag)) {
            sb.append(" and t.del_flag is null or t.del_flag = '0'");
            cb.append("and t.del_flag is null or t.del_flag = '0'");
        } else {
            sb.append(" and t.del_flag = '1'");
            cb.append("and t.del_flag = '1'");
        }

        String sort = dataGrid.getSort();
        String order = dataGrid.getOrder().toString();
        sb.append(" ORDER BY ");
        cb.append(" ORDER BY ");
        if (StringUtil.isNotEmpty(order) && StringUtil.isNotEmpty(sort)) {

            sb.append(sort + " " + order + ",");
            //            cb.append(" ORDER BY " + sort + " " + order);
        }
        sb.append(" READ_FLAG,SEND_TIME DESC");
        cb.append(" READ_FLAG,SEND_TIME DESC");
        String countSql = cb.toString();
        String sql = sb.toString();
        String[] params = new String[] { user.getId() };
        List<Map<String, Object>> list = systemService.findForJdbcParam(sql, dataGrid.getPage(), dataGrid.getRows(),
                user.getId());
        Long count = systemService.getCountForJdbcParam(countSql, params);
        dataGrid.setResults(list);
        dataGrid.setTotal(count.intValue());
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除系统消息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOMessageEntity tOMessage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOMessage = systemService.getEntity(TOMessageEntity.class, tOMessage.getId());
        String message = "系统消息删除成功";
        try {
            tOMessageService.delMain(tOMessage);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "系统消息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 逻辑删除系统消息
     * 
     * @return
     */
    @RequestMapping(params = "doHide")
    @ResponseBody
    public AjaxJson doHide(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "系统消息隐藏成功";
        try {
            this.tOMessageService.doLogicDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "系统消息隐藏失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除系统消息
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "系统消息删除成功";
        try {
            for (String id : ids.split(",")) {
                TOMessageEntity tOMessage = systemService.getEntity(TOMessageEntity.class, id);
                tOMessageService.delMain(tOMessage);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "系统消息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加系统消息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOMessageEntity tOMessage, TOMessagePage tOMessagePage, String receiverid,
            HttpServletRequest request) {
        List<TOMessageReadEntity> tOMessageReadList = tOMessagePage.getTOMessageReadList();
        List<TSUser> ulist = new ArrayList<TSUser>();
        String[] rids = receiverid.split(",");
        if (rids.length > 0) {
            CriteriaQuery rcq = new CriteriaQuery(TSUser.class);
            rcq.in("id", rids);
            rcq.add();
            ulist = systemService.getListByCriteriaQuery(rcq, false);
            for (TSUser user : ulist) {
                TOMessageReadEntity reader = new TOMessageReadEntity();
                reader.setReadFlag("0");
                reader.setDelFlag("0");
                reader.setShow("0");
                reader.setReceiverId(user.getId());
                reader.setReceiverName(user.getRealName());
                tOMessageReadList.add(reader);
            }
        }
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try {
            tOMessageService.addMain(tOMessage, tOMessageReadList);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "系统消息添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新系统消息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOMessageEntity tOMessage, TOMessagePage tOMessagePage, HttpServletRequest request,
            String receverid) {
        List<TOMessageReadEntity> tOMessageReadList = tOMessagePage.getTOMessageReadList();
        if (StringUtil.isNotEmpty(receverid)) {
            String[] receiverids = receverid.split(",");
            for (String rid : receiverids) {
                TOMessageReadEntity re = new TOMessageReadEntity();
                TSUser user = systemService.get(TSUser.class, rid);
                re.setReceiverId(rid);
                re.setReceiverName(user.getRealName());
                re.setReadFlag("0");
                tOMessageReadList.add(re);
            }
        }
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try {
            CriteriaQuery cq = new CriteriaQuery(TOMessageReadEntity.class);
            cq.eq("toid", tOMessage.getId());
            cq.add();
            List<TOMessageReadEntity> tOMessageOldReceiveList = tOMessageService.getListByCriteriaQuery(cq, false);
            for (TOMessageReadEntity ore : tOMessageOldReceiveList) {
                systemService.deleteEntityById(TOMessageReadEntity.class, ore.getId());
            }
            tOMessageService.updateMain(tOMessage, tOMessageReadList);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新系统消息失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 系统消息新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOMessageEntity tOMessage, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOMessage.getId())) {
            tOMessage = tOMessageService.getEntity(TOMessageEntity.class, tOMessage.getId());
            req.setAttribute("tOMessagePage", tOMessage);
        }
        TSUser user = ResourceUtil.getSessionUserName();
        req.setAttribute("now", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        req.setAttribute("user", user);
        return new ModelAndView("com/kingtake/office/message/tOMessage-add");
    }

    /**
     * 系统消息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOMessageEntity tOMessage, HttpServletRequest req, String flag, String toid) {
        if (StringUtil.isNotEmpty(tOMessage.getId())) {
            tOMessage = tOMessageService.getEntity(TOMessageEntity.class, tOMessage.getId());
            CriteriaQuery rcq = new CriteriaQuery(TOMessageReadEntity.class);
            //            System.out.println("+++++++++++++++++++++++++++++++" + tOMessage.getId());
            rcq.eq("toid", tOMessage.getId());
            rcq.add();
            List<TOMessageReadEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            if (rlist.size() > 0) {
                String receverid = "";
                String recevername = "";
                for (TOMessageReadEntity re : rlist) {
                    receverid += re.getReceiverId() + ",";
                    recevername += re.getReceiverName() + ",";
                }
                receverid = receverid.substring(0, receverid.lastIndexOf(","));
                recevername = recevername.substring(0, recevername.lastIndexOf(","));
                req.setAttribute("receverid", receverid);
                req.setAttribute("recevername", recevername);
            }
            if (StringUtil.isNotEmpty(flag)) {
                TSUser user = ResourceUtil.getSessionUserName();
                CriteriaQuery rcq2 = new CriteriaQuery(TOMessageReadEntity.class);
                rcq.eq("toid", tOMessage.getId());
                rcq.eq("receiverId", user.getId());
                rcq.add();
                TOMessageReadEntity receiver = (TOMessageReadEntity) systemService.getListByCriteriaQuery(rcq, false)
                        .get(0);
                if (receiver.getReadFlag().equals("0")) {
                    receiver.setReadFlag("1");
                    receiver.setReadTime(new Date());
                    systemService.updateEntitie(receiver);
                }
            }
            req.setAttribute("tOMessagePage", tOMessage);
        }
        return new ModelAndView("com/kingtake/office/message/tOMessage-update");
    }

    /**
     * 系统消息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goView")
    public ModelAndView goView(TOMessageEntity tOMessage, HttpServletRequest req, String flag, String toid) {
        if (StringUtil.isNotEmpty(tOMessage.getId())) {
            tOMessage = tOMessageService.getEntity(TOMessageEntity.class, tOMessage.getId());
            CriteriaQuery rcq = new CriteriaQuery(TOMessageReadEntity.class);
            //            System.out.println("+++++++++++++++++++++++++++++++" + tOMessage.getId());
            rcq.eq("toid", tOMessage.getId());
            rcq.add();
            List<TOMessageReadEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            if (rlist.size() > 0) {
                String receverid = "";
                String recevername = "";
                for (TOMessageReadEntity re : rlist) {
                    receverid += re.getReceiverId() + ",";
                    recevername += re.getReceiverName() + ",";
                }
                receverid = receverid.substring(0, receverid.lastIndexOf(","));
                recevername = recevername.substring(0, recevername.lastIndexOf(","));
                req.setAttribute("receverid", receverid);
                req.setAttribute("recevername", recevername);
            }
            if (StringUtil.isNotEmpty(flag)) {
                TSUser user = ResourceUtil.getSessionUserName();
                CriteriaQuery rcq2 = new CriteriaQuery(TOMessageReadEntity.class);
                rcq2.eq("toid", tOMessage.getId());
                rcq2.eq("receiverId", user.getId());
                rcq2.add();
                TOMessageReadEntity receiver = (TOMessageReadEntity) systemService.getListByCriteriaQuery(rcq2, false)
                        .get(0);
                if (receiver.getReadFlag().equals("0")) {
                    receiver.setReadFlag("1");
                    receiver.setReadTime(new Date());
                    systemService.updateEntitie(receiver);
                    //消息设置为已读之后，执行回调sql
                    TOMessageEntity toMessage = this.systemService.get(TOMessageEntity.class, receiver.getToid());
                    this.tOMessageService.executeCallbackSql(toMessage);
                }
            }
            req.setAttribute("tOMessagePage", tOMessage);
        }
        return new ModelAndView("com/kingtake/office/message/tOMessage-view");
    }

    /**
     * 加载明细列表[系统消息接收人]
     * 
     * @return
     */
    @RequestMapping(params = "tOMessageReadList")
    public ModelAndView tOMessageReadList(TOMessageEntity tOMessage, HttpServletRequest req) {

        /*
         * //=================================================================================== //获取参数 Object id0 =
         * tOMessage.getId(); //===================================================================================
         * //查询-系统消息接收人 String hql0 = "from TOMessageReadEntity where 1 = 1 AND t_O_ID = ? "; try {
         * List<TOMessageReadEntity> tOMessageReadEntityList = systemService.findHql(hql0, id0);
         * req.setAttribute("tOMessageReadList", tOMessageReadEntityList); } catch (Exception e) {
         * logger.info(e.getMessage()); }
         */
        return new ModelAndView("com/kingtake/office/message/tOMessageReadList");
    }

    @RequestMapping(params = "editUserList")
    public ModelAndView editUserList(HttpServletRequest request, String messageid) {
        request.setAttribute("messageid", messageid);
        return new ModelAndView("com/kingtake/office/message/editReceiverList");
    }

    @RequestMapping(params = "userDatagrid")
    public void userDatagrid(TOMessageReadEntity receiver, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOMessageReadEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, receiver);
        String messageid = oConvertUtils.getString(request.getParameter("messageid"));
        cq.eq("toid", messageid);
        /*
         * String[] rids; if (!StringUtil.isEmpty(noticeid)) { CriteriaQuery rcq = new
         * CriteriaQuery(TONoticeReceiveEntity.class); rcq.eq("noticeId", noticeid); rcq.add();
         * List<TONoticeReceiveEntity> rlist = systemService.getListByCriteriaQuery(rcq, false); rids = new
         * String[rlist.size()]; for (int i = 0; i < rlist.size(); i++) { rids[i] = rlist.get(i).getReceiverId(); } }
         * else { rids = new String[0]; }
         */
        //        Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
        /*
         * if (rids.length > 0) { // cq.add(Restrictions.not(Restrictions.in("id", rids))); cq.in("id", rids);
         * 
         * }
         */
        //        cq.in("status", userstate);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "delReceiver")
    @ResponseBody
    public AjaxJson delReceiver(HttpServletRequest request, HttpServletResponse response, TOMessageReadEntity receiver,
            String userid, String messageid) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(userid) && StringUtil.isNotEmpty(messageid)) {
            try {
                CriteriaQuery cq = new CriteriaQuery(TOMessageReadEntity.class);
                cq.eq("toid", messageid);
                cq.eq("receiverId", userid);
                cq.add();
                receiver = (TOMessageReadEntity) systemService.getListByCriteriaQuery(cq, false).get(0);
                systemService.delete(receiver);
                j.setMsg("删除成功！");
            } catch (Exception e) {
                j.setMsg("删除失败！");
                e.printStackTrace();
            }
        }
        return j;
    }

    /**
     * 获取未读消息总数
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getCountForUnread")
    @ResponseBody
    public AjaxJson getCountForUnread(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUserName();
        String sql = "SELECT COUNT(0) FROM T_O_MESSAGE_READ R WHERE R.RECEIVER_ID =? AND READ_FLAG = ?";
        String[] params = new String[] { user.getId(), SrmipConstants.NO };
        Long count = systemService.getCountForJdbcParam(sql, params);
        j.setObj(count);
        return j;
    }

    /**
     * 获取未提示消息内容及未提示消息
     * 
     * @return
     */
    @RequestMapping(params = "getSystemMessage")
    @ResponseBody
    public AjaxJson getSystemMessage() {
        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUserName();
        String csql = "SELECT COUNT(0) FROM T_O_MESSAGE_READ R JOIN T_O_MESSAGE M ON R.T_O_ID=M.ID WHERE R.RECEIVER_ID =? AND R.READ_FLAG = ? AND (M.DEL_FLAG='0' OR M.DEL_FLAG IS NULL) ";
        String[] cparams = new String[] { user.getId(), SrmipConstants.NO };
        Long count = systemService.getCountForJdbcParam(csql, cparams);
        j.setObj(count);//未读消息总数
        String sql = "SELECT M.*,R.ID AS RID FROM T_O_MESSAGE M JOIN T_O_MESSAGE_READ R ON  R.RECEIVER_ID =? AND R.T_O_ID = M.ID AND R.DEL_FLAG =? AND  R.SHOW=? ORDER BY M.SEND_TIME ASC";
        String[] params = new String[] { user.getId(), SrmipConstants.NO, SrmipConstants.NO };
        List<Map<String, Object>> list = systemService.findForJdbc(sql, params);
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            j.setAttributes(map);//未提示消息（按发送时间升序排列，每次取一条）
        }
        return j;
    }

    @RequestMapping(params = "changeMessageShow")
    public void changeMessageShow(HttpServletRequest request, HttpServletResponse response) {
        String rid = request.getParameter("rid");
        TOMessageReadEntity read = systemService.get(TOMessageReadEntity.class, rid);
        if (read != null) {
            read.setShow(SrmipConstants.YES);
            systemService.updateEntitie(read);
        }
    }
    

    /**
     * 批量标注系统消息已读
     * 
     * @return
     */
    @RequestMapping(params = "doReaded")
    @ResponseBody
    public AjaxJson doReaded(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "系统消息已读设置成功";
        try {
            for (String id : ids.split(",")) {
                TSUser user = ResourceUtil.getSessionUserName();
                
                CriteriaQuery rcq = new CriteriaQuery(TOMessageReadEntity.class);
                rcq.eq("toid", id);
                rcq.eq("receiverId", user.getId());
                rcq.add();
                TOMessageReadEntity receiver = (TOMessageReadEntity) systemService.getListByCriteriaQuery(rcq, false).get(0);
                if (receiver.getReadFlag().equals("0")) {
                    receiver.setReadFlag("1");
                    receiver.setReadTime(new Date());
                    systemService.updateEntitie(receiver);
                    //消息设置为已读之后，执行回调sql
                    TOMessageEntity toMessage = this.systemService.get(TOMessageEntity.class, receiver.getToid());
                    this.tOMessageService.executeCallbackSql(toMessage);
                }
                
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "系统消息已读设置失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转到回收站界面
     * 
     * @return
     */
    @RequestMapping(params = "goRecycle")
    public ModelAndView goRecycle(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/message/tOMessageRecycleList");
    }

    /**
     * 恢复隐藏的日程安排
     * 
     * @return
     */
    @RequestMapping(params = "doBack")
    @ResponseBody
    public AjaxJson doBack(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "系统消息恢复成功!";
        try {
            this.tOMessageService.doBack(ids);
        } catch (Exception e) {
            e.printStackTrace();
            message = "系统消息恢复失败!";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
