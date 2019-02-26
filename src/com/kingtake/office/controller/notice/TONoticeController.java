package com.kingtake.office.controller.notice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.notice.TONoticeEntity;
import com.kingtake.office.entity.notice.TONoticeProjectRelaEntity;
import com.kingtake.office.entity.notice.TONoticeReceiveEntity;
import com.kingtake.office.entity.schedule.TOResponseEntity;
import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.page.notice.TONoticePage;
import com.kingtake.office.service.notice.TONoticeServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: 通知公告
 * @author onlineGenerator
 * @date 2015-07-01 15:53:57
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tONoticeController")
public class TONoticeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TONoticeController.class);

    @Autowired
    private TONoticeServiceI tONoticeService;
    @Autowired
    private SystemService systemService;

    /**
     * 通知公告列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tONotice")
    public ModelAndView tONotice(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/notice/tONoticeList");
    }

    /**
     * 跳转到我接收的通知公告页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tONoticeToMe")
    public ModelAndView tONoticeToMe(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/notice/tONoticeList-toMe");
    }

    /**
     * 通知公告tab页
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "toNoticeTab")
    public ModelAndView toNoticceTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/notice/tONoticeListTab");
    }

    /**
     * 通知公告列表给待办信息
     * 
     * @return
     */
    @RequestMapping(params = "toPortal")
    public ModelAndView tONoticeForPortal(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/notice/tONoticeListForPortal");
    }

    /**
     * easyui AJAX请求数据，我发送的
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TONoticeEntity tONotice, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TONoticeEntity.class, dataGrid);
        TSUser user = ResourceUtil.getSessionUserName();
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tONotice, request.getParameterMap());
        try {
            //自定义追加查询条件
            String query_title_begin = request.getParameter("title_begin");
            String query_title_end = request.getParameter("title_end");
            if (StringUtil.isNotEmpty(query_title_begin)) {
                cq.ge("title", Integer.parseInt(query_title_begin));
            }
            if (StringUtil.isNotEmpty(query_title_end)) {
                cq.le("title", Integer.parseInt(query_title_end));
            }
            String query_projName_begin = request.getParameter("projName_begin");
            String query_projName_end = request.getParameter("projName_end");
            if (StringUtil.isNotEmpty(query_projName_begin)) {
                cq.ge("projName", Integer.parseInt(query_projName_begin));
            }
            if (StringUtil.isNotEmpty(query_projName_end)) {
                cq.le("projName", Integer.parseInt(query_projName_end));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.eq("senderId", user.getId());
        cq.add();
        this.tONoticeService.getDataGridReturn(cq, true);
        List<TONoticeEntity> noticeList = dataGrid.getResults();
        for (TONoticeEntity notice : noticeList) {
            CriteriaQuery pcq = new CriteriaQuery(TONoticeProjectRelaEntity.class);
            pcq.eq("noticeId", notice.getId());
            pcq.add();
            List<TONoticeProjectRelaEntity> plist = systemService.getListByCriteriaQuery(pcq, false);
            StringBuffer namesb = new StringBuffer();
            for (TONoticeProjectRelaEntity p : plist) {
                if (namesb.length() > 0) {
                    namesb.append(",");
                }
                namesb.append(p.getProjectName());
            }
            notice.setProjName(namesb.toString());
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 通知公告，AJAX请求数据，我接收的
     * 
     * @param tONotice
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridToMe")
    public void datagridToMe(TONoticeEntity tONotice, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String sendName = request.getParameter("SENDER.NAME");
        String title = request.getParameter("title");
        String begin = request.getParameter("SEND_TIME_begin");
        String end = request.getParameter("SEND_TIME_end");
        StringBuffer cb = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT T.*,T.SENDER_NAME AS sendName,RE.PROJECT_NAME AS projName,T.SEND_TIME AS sendTime,T.FILE_NUM AS fileNum,R.READ_FLAG AS READFLAG FROM T_O_NOTICE T  JOIN T_O_NOTICE_RECEIVE R ON R.RECEIVER_ID = ? AND R.NOTICE_ID = T.ID LEFT JOIN T_O_NOTICE_PROJECT_RELA RE ON RE.NOTICE_ID = T.ID  WHERE 1=1 ");
        cb.append("SELECT COUNT(0) FROM T_O_NOTICE T  JOIN T_O_NOTICE_RECEIVE R ON R.RECEIVER_ID = ? AND R.NOTICE_ID = T.ID WHERE 1=1 ");
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
        String sort = dataGrid.getSort();
        String order = dataGrid.getOrder().toString();
        sb.append(" ORDER BY ");
        cb.append(" ORDER BY ");
        if (StringUtil.isNotEmpty(order) && StringUtil.isNotEmpty(sort)) {

            sb.append(sort + " " + order + ",");
            //            cb.append(" ORDER BY " + sort + " " + order);
        }
        sb.append(" SEND_TIME DESC");
        cb.append(" SEND_TIME DESC");
        String countSql = cb.toString();
        String sql = sb.toString();
        String[] params = new String[] { user.getId() };
        List<Map<String, Object>> list = systemService.findForJdbcParam(sql, dataGrid.getPage(), dataGrid.getRows(),
                user.getId());
        for (Map<String, Object> map : list) {
            CriteriaQuery pcq = new CriteriaQuery(TONoticeProjectRelaEntity.class);
            pcq.eq("noticeId", map.get("id"));
            pcq.add();
            List<TONoticeProjectRelaEntity> plist = systemService.getListByCriteriaQuery(pcq, false);
            StringBuffer namesb = new StringBuffer();
            for (TONoticeProjectRelaEntity p : plist) {
                if (namesb.length() > 0) {
                    namesb.append(",");
                }
                namesb.append(p.getProjectName());
            }
            map.put("PROJ_NAME", namesb.toString());
        }
        Long count = systemService.getCountForJdbcParam(countSql, params);
        dataGrid.setResults(list);
        dataGrid.setTotal(count.intValue());
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除通知公告
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TONoticeEntity tONotice, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        try {
            String message = this.tONoticeService.deleteNotice(tONotice);
            json.setMsg(message);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        }
        return json;
    }

    @RequestMapping(params = "relevance")
    @ResponseBody
    public AjaxJson relevance(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String id = req.getParameter("id");
        TONoticeEntity tONotice = systemService.get(TONoticeEntity.class, id);
        CriteriaQuery pcq = new CriteriaQuery(TONoticeProjectRelaEntity.class);
        pcq.eq("noticeId", tONotice.getId());
        pcq.add();
        List<TONoticeProjectRelaEntity> plist = systemService.getListByCriteriaQuery(pcq, false);
        if (plist.size() > 0) {
            j.setObj(SrmipConstants.YES);
            j.setMsg("该通知公告与项目[" + plist.get(0).getProjectName() + "]关联，是否确定删除？");
        } else {
            j.setObj(SrmipConstants.NO);
        }
        return j;
    }

    /**
     * 批量删除通知公告
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "通知公告删除成功";
        try {
            for (String id : ids.split(",")) {
                TONoticeEntity tONotice = systemService.getEntity(TONoticeEntity.class, id);
                tONoticeService.delMain(tONotice);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "通知公告删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加通知公告
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TONoticeEntity tONotice, TONoticePage tONoticePage, HttpServletRequest request,
            String receiverid, String projId1) {
        List<TONoticeReceiveEntity> tONoticeReceiveList = tONoticePage.getTONoticeReceiveList();
        if (StringUtil.isNotEmpty(receiverid)) {
            String[] receiverids = receiverid.split(",");
            for (String rid : receiverids) {
                TONoticeReceiveEntity re = new TONoticeReceiveEntity();
                TSUser user = systemService.get(TSUser.class, rid);
                re.setReceiverId(rid);
                re.setReceiverName(user.getRealName());
                re.setReadFlag("0");
                tONoticeReceiveList.add(re);
            }
        }
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        if (StringUtil.isNotEmpty(tONotice.getId())) {
            try {
                TONoticeEntity tn = systemService.get(TONoticeEntity.class, tONotice.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tONotice, tn);
                tONoticeService.updateMain(tn, tONoticeReceiveList);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                tONoticeService.addMain(tONotice, tONoticeReceiveList);
                if (StringUtil.isNotEmpty(projId1)) {
                    String[] projIds = projId1.split(",");
                    for (String pid : projIds) {
                        TPmProjectEntity project = systemService.get(TPmProjectEntity.class, pid);
                        TONoticeProjectRelaEntity npr = new TONoticeProjectRelaEntity();
                        npr.setNoticeId(tONotice.getId());
                        npr.setProjectId(pid);
                        npr.setProjectName(project.getProjectName());
                        systemService.save(npr);
                    }
                }
                //            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "通知公告添加失败";
                throw new BusinessException(e.getMessage());
            }
        }
        j.setObj(tONotice);
        j.setMsg(message);
        return j;
    }

    /**
     * 更新通知公告
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TONoticeEntity tONotice, TONoticePage tONoticePage, HttpServletRequest request,
            String receiverid) {
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try {
            this.tONoticeService.updateNotice(tONotice, receiverid);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新通知公告失败";
            j.setSuccess(false);
        }
        j.setObj(tONotice);
        j.setMsg(message);
        return j;
    }

    /**
     * 通知公告新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TONoticeEntity tONotice, HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        req.setAttribute("user", user);
        req.setAttribute("now", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (StringUtil.isNotEmpty(tONotice.getId())) {
            tONotice = tONoticeService.getEntity(TONoticeEntity.class, tONotice.getId());
        }
        if(StringUtils.isEmpty(tONotice.getAttachmentCode())){
            tONotice.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tONotice.getAttachmentCode(), "");
            tONotice.setCertificates(certificates);
        }
        req.setAttribute("tONoticePage", tONotice);
        return new ModelAndView("com/kingtake/office/notice/tONotice-add");
    }

    /**
     * 通知公告编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TONoticeEntity tONotice, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tONotice.getId())) {
            tONotice = tONoticeService.getEntity(TONoticeEntity.class, tONotice.getId());
            if(StringUtils.isEmpty(tONotice.getAttachmentCode())){
                tONotice.setAttachmentCode(UUIDGenerator.generate());
            }else{
                List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tONotice.getAttachmentCode(), "");
                tONotice.setCertificates(certificates);
            }
            req.setAttribute("tONoticePage", tONotice);
            CriteriaQuery pcq = new CriteriaQuery(TONoticeProjectRelaEntity.class);
            pcq.eq("noticeId", tONotice.getId());
            pcq.add();
            List<TONoticeProjectRelaEntity> plist = systemService.getListByCriteriaQuery(pcq, false);
            StringBuffer idsb = new StringBuffer();
            StringBuffer namesb = new StringBuffer();
            for (TONoticeProjectRelaEntity p : plist) {
                if (idsb.length() > 0) {
                    idsb.append(",");
                }
                idsb.append(p.getProjectId());
                if (namesb.length() > 0) {
                    namesb.append(",");
                }
                namesb.append(p.getProjectName());
            }
            req.setAttribute("projId", idsb.toString());
            req.setAttribute("projName", namesb.toString());
            CriteriaQuery cq = new CriteriaQuery(TONoticeReceiveEntity.class);
            //            List<TONoticeReceiveEntity> tONoticeReceiveList = new ArrayList<TONoticeReceiveEntity>();
            cq.eq("noticeId", tONotice.getId());
            cq.add();
            List<TONoticeReceiveEntity> tONoticeReceiveList = tONoticeService.getListByCriteriaQuery(cq, false);
            if (tONoticeReceiveList.size() > 0) {
                String receiverid = "";
                String realName = "";
                for (TONoticeReceiveEntity re : tONoticeReceiveList) {
                    receiverid += re.getReceiverId() + ",";
                    realName += re.getReceiverName() + ",";
                }
                receiverid = receiverid.substring(0, receiverid.lastIndexOf(","));
                realName = realName.substring(0, realName.lastIndexOf(","));
                req.setAttribute("realName", realName);
                req.setAttribute("receiverid", receiverid);
            }
            String flag = req.getParameter("flag");
            if (StringUtil.isNotEmpty(flag)) {
                TSUser user = ResourceUtil.getSessionUserName();
                CriteriaQuery rcq = new CriteriaQuery(TONoticeReceiveEntity.class);
                rcq.eq("noticeId", tONotice.getId());
                rcq.eq("receiverId", user.getId());
                rcq.add();
                TONoticeReceiveEntity receiver = (TONoticeReceiveEntity) tONoticeService.getListByCriteriaQuery(rcq,
                        false).get(0);
                if (receiver.getReadFlag().equals("0")) {
                    receiver.setReadFlag("1");
                    receiver.setReadTime(new Date());
                    tONoticeService.updateEntitie(receiver);
                }
            }
        }
        return new ModelAndView("com/kingtake/office/notice/tONotice-update");
    }

    /**
     * 加载明细列表[通知公告子表]
     * 
     * @return
     */
    @RequestMapping(params = "tONoticeReceiveList")
    public ModelAndView tONoticeReceiveList(TONoticeEntity tONotice, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tONotice.getId();
        //===================================================================================
        //查询-通知公告子表
        String hql0 = "from TONoticeReceiveEntity where 1 = 1 AND nOTICE_ID = ? ";
        try {
            List<TONoticeReceiveEntity> tONoticeReceiveEntityList = systemService.findHql(hql0, id0);
            req.setAttribute("tONoticeReceiveList", tONoticeReceiveEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/kingtake/office/notice/tONoticeReceiveList");
    }

    /**
     * 新增时通知公告接收人页面跳转
     * 
     * @param request
     * @param noticeid
     * @return
     */
    @RequestMapping(params = "userList")
    public ModelAndView userList(HttpServletRequest request, String noticeid) {
        request.setAttribute("noticeid", noticeid);
        return new ModelAndView("com/kingtake/office/notice/receiverList");
    }

    /**
     * 通知公告接收人编辑页面跳转
     * 
     * @param request
     * @param noticeid
     * @return
     */
    @RequestMapping(params = "editUserList")
    public ModelAndView editUserList(HttpServletRequest request, String noticeid) {
        request.setAttribute("noticeid", noticeid);
        return new ModelAndView("com/kingtake/office/notice/editReceiverList");
    }

    /**
     * 新增时通知公告接收人数据请求
     * 
     * @param receiver
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "userDatagrid")
    public void userDatagrid(TONoticeReceiveEntity receiver, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TONoticeReceiveEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, receiver);
        String noticeid = oConvertUtils.getString(request.getParameter("noticeid"));
        cq.eq("noticeId", noticeid);
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

    /**
     * 通知公告接收人数据筛选
     * 
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "addUserDatagrid")
    public void addUserDatagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
        String noticeid = oConvertUtils.getString(request.getParameter("noticeid"));
        String[] rids;
        if (!StringUtil.isEmpty(noticeid)) {
            CriteriaQuery rcq = new CriteriaQuery(TONoticeReceiveEntity.class);
            rcq.eq("noticeId", noticeid);
            rcq.add();
            List<TONoticeReceiveEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            rids = new String[rlist.size()];
            for (int i = 0; i < rlist.size(); i++) {
                rids[i] = rlist.get(i).getReceiverId();
            }
        } else {
            rids = new String[0];
        }
        Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
        if (rids.length > 0) {
            cq.add(Restrictions.not(Restrictions.in("id", rids)));
        }
        cq.in("status", userstate);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 添加接收人
     * 
     * @param request
     * @param response
     * @param userIds
     * @param noticeid
     * @return
     */
    @RequestMapping(params = "doAddReceiver")
    @ResponseBody
    public AjaxJson doAddReceiver(HttpServletRequest request, HttpServletResponse response, String userIds,
            String noticeid) {
        if (StringUtil.isNotEmpty(noticeid)) {
            if (StringUtil.isNotEmpty(userIds)) {
                String[] uids = userIds.split(",");
                CriteriaQuery cq = new CriteriaQuery(TSUser.class);
                cq.in("id", uids);
                cq.add();
                List<TSUser> ulist = systemService.getListByCriteriaQuery(cq, false);
                for (int i = 0; i < ulist.size(); i++) {
                    TONoticeReceiveEntity receiver = new TONoticeReceiveEntity();
                    receiver.setNoticeId(noticeid);
                    receiver.setReadFlag("0");
                    receiver.setReceiverId(ulist.get(i).getId());
                    receiver.setReceiverName(ulist.get(i).getRealName());
                    tONoticeService.save(receiver);
                }

            }
        }
        AjaxJson j = new AjaxJson();
        j.setMsg("添加成功");
        return j;
    }

    /**
     * 删除接收人
     * 
     * @param request
     * @param response
     * @param receiver
     * @param userid
     * @param noticeid
     * @return
     */
    @RequestMapping(params = "delReceiver")
    @ResponseBody
    public AjaxJson delReceiver(HttpServletRequest request, HttpServletResponse response,
            TONoticeReceiveEntity receiver, String userid, String noticeid) {
        AjaxJson j = new AjaxJson();
        j.setMsg("删除成功！");
        if (StringUtil.isNotEmpty(userid) && StringUtil.isNotEmpty(noticeid)) {
            try {
                CriteriaQuery cq = new CriteriaQuery(TONoticeReceiveEntity.class);
                cq.eq("noticeId", noticeid);
                cq.add();
                List<TONoticeReceiveEntity> rlist = systemService.getListByCriteriaQuery(cq, false);
                if (rlist.size() > 1) {
                    for (TONoticeReceiveEntity r : rlist) {
                        if (userid.equals(r.getReceiverId())) {
                            systemService.delete(r);
                        }
                    }
                } else {
                    j.setMsg("通知公告必须存在至少一名接收人！");
                }
            } catch (Exception e) {
                j.setMsg("删除失败！");
                e.printStackTrace();
            }
        }
        return j;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TONoticeEntity tONotice, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TONoticeEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tONotice, request.getParameterMap());
        List<TONoticeEntity> tONoticeEntitys = tONoticeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "标准代码类型列表");
        modelMap.put(NormalExcelConstants.CLASS, TONoticeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("标准代码类型列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tONoticeEntitys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 跳转到回复界面
     * 
     * @return
     */
    @RequestMapping(params = "goResponse")
    public ModelAndView goResponse(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/schedule/response");
    }

    /**
     * 回复日程安排
     * 
     * @return
     */
    @RequestMapping(params = "doResponse")
    @ResponseBody
    public AjaxJson doResponse(TONoticeEntity tONotice, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String resContent = request.getParameter("resContent");
        String type = request.getParameter("type");
        String message = "通知公告回复成功！";
        try {
            tONoticeService.doResponse(tONotice, resContent, type);
        } catch (Exception e) {
            e.printStackTrace();
            message = "通知公告回复失败！";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 跳转到我的回复界面
     * 
     * @return
     */
    @RequestMapping(params = "goMyResponseList")
    public ModelAndView goMyResponseList(HttpServletRequest request) {
        String noticeId = request.getParameter("noticeId");
        if (StringUtils.isNotEmpty(noticeId)) {
            request.setAttribute("noticeId", noticeId);
        }
        return new ModelAndView("com/kingtake/office/notice/myResponseList");
    }

    /**
     * 我的回复列表
     * 
     * @param tOSchedule
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "myResponseList")
    public void myResponseList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        try {
            String noticeId = request.getParameter("noticeId");
            TONoticeEntity notice = this.systemService.get(TONoticeEntity.class, noticeId);
            CriteriaQuery cq = new CriteriaQuery(TOResponseEntity.class, dataGrid);
            cq.eq("resSourceId", noticeId);
            cq.addOrder("resTime", SortDirection.desc);
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到我的回复界面
     * 
     * @return
     */
    @RequestMapping(params = "goResponseList")
    public ModelAndView goResponseList(HttpServletRequest request) {
        String noticeId = request.getParameter("noticeId");
        if (StringUtils.isNotEmpty(noticeId)) {
            request.setAttribute("noticeId", noticeId);
        }
        return new ModelAndView("com/kingtake/office/notice/responseList");
    }

    /**
     * 跳转到提醒界面
     * 
     * @return
     */
    @RequestMapping(params = "goWarn")
    public ModelAndView goWarn(HttpServletRequest request) {
        TOWarnEntity warn = null;
        String noticeId = request.getParameter("noticeId");
        if (StringUtils.isNotEmpty(noticeId)) {
            List<TOWarnEntity> warnList = this.systemService.findByProperty(TOWarnEntity.class, "sourceId", noticeId);
            if (warnList.size() > 0) {
                warn = warnList.get(0);
            } else {
                warn = new TOWarnEntity();
                warn.setWarnBeginTime(new Date());
                warn.setWarnEndTime(org.apache.commons.lang.time.DateUtils.addDays(new Date(), 7));
                warn.setWarnTimePoint(DateUtils.formatDate(new Date(), "HH:mm"));
                warn.setWarnStatus("2");//默认为失效
                warn.setSourceId(noticeId);
            }
        }
        request.setAttribute("tOWarnPage", warn);
        return new ModelAndView("com/kingtake/office/notice/noticeWarn");
    }

    /**
     * 我的回复列表
     * 
     * @param tOSchedule
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "responseList")
    public void responseList(TONoticeEntity tONotice, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        try {
            CriteriaQuery cq = new CriteriaQuery(TOResponseEntity.class, dataGrid);
            cq.eq("resSourceId", tONotice.getId());
            cq.addOrder("resTime", SortDirection.desc);
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            TagUtil.datagrid(response, dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回复日程安排
     * 
     * @return
     */
    @RequestMapping(params = "doWarn")
    @ResponseBody
    public AjaxJson doWarn(TOWarnEntity tOWarn, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String sourceId = request.getParameter("sourceId");
        TONoticeEntity noticeEntity = this.systemService.get(TONoticeEntity.class, sourceId);
        String message = "通知公告添加提醒成功！";
        try {
            tONoticeService.doWarn(tOWarn, noticeEntity);
        } catch (Exception e) {
            e.printStackTrace();
            message = "通知公告添加提醒失败！";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
}
