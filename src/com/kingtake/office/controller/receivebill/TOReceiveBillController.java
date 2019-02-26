package com.kingtake.office.controller.receivebill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.poi.word.entity.WordImageEntity;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.TSFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;
import com.kingtake.office.entity.flow.TOFlowSendLogsEntity;
import com.kingtake.office.entity.receivebill.TOReceiveBillEntity;
import com.kingtake.office.entity.receivebill.TOReceiveList;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.entity.sendbill.TOSendBillEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.office.service.receivebill.TOReceiveBillServiceI;
import com.kingtake.office.service.sendReceive.TOSendReceiveRegServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

/**
 * @Title: Controller
 * @Description: 收文阅批单信息表
 * @author onlineGenerator
 * @date 2015-07-17 15:43:38
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOReceiveBillController")
public class TOReceiveBillController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOReceiveBillController.class);

    @Autowired
    private TOReceiveBillServiceI tOReceiveBillService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    @Autowired
    private TSFilesService tsFilesService;
    @Autowired
    private TOSendReceiveRegServiceI regService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 收文阅批单信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOReceiveBill")
    public ModelAndView tOReceiveBill(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/receivebill/sendReceiveBill");
    }

    @RequestMapping(params = "tOReceiveBill2")
    public ModelAndView tOReceiveBill2(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBillList");
    }

    @RequestMapping(params = "goScheduleOfficial")
    public ModelAndView goBootStrap(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/receivebill/scheduleOfficial");
    }

    @RequestMapping(params = "awaitOfficial")
    public ModelAndView awaitOfficial(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/receivebill/sendReceiveBill");
    }

    @RequestMapping(params = "tOReceiveBillListTab")
    public ModelAndView tOReceiveBillListTab(HttpServletRequest request) {
        request.setAttribute("treated", ReceiveBillConstant.OPERATE_TREATED);
        request.setAttribute("untreated", ReceiveBillConstant.OPERATE_UNTREATED);
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBillListTab");
    }

    /**
     * 收文
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tOReceiveBillListToMe")
    public ModelAndView tOReceiveBillList_toMe(HttpServletRequest request) {
        request.setAttribute("operateStatus", request.getParameter("operateStatus"));
        request.setAttribute("registerType", request.getParameter("registerType"));
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBillList-toMe");
    }

    /**
     * easyui AJAX请求数据,当前登陆人发出的阅批单
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String operateStatus = request.getParameter("operateStatus");
        //            String registerType = request.getParameter("registerType");
        String title = request.getParameter("TITLE");
        String unitName = request.getParameter("RECEIVE.UNIT.NAME");
        String regType = request.getParameter("REG.TYPE");
        try {
            //自定义追加查询条件
            TSUser user = ResourceUtil.getSessionUserName();
            String sql = "SELECT T.FILE_NUM_PREFIX||'〔20'||T.FILE_NUM_YEAR||'〕'||T.BILL_NUM AS FILE_NUM,T.*,R.id AS RID ";
            String cql = "SELECT COUNT(0) ";
            StringBuffer sb = new StringBuffer();
            sb.append(" FROM T_O_RECEIVE_BILL T JOIN T_O_FLOW_RECEIVE_LOGS R ON T.ID = R.SEND_RECEIVE_ID AND R.OPERATE_STATUS = ? AND R.RECEIVE_USERID=?  WHERE 1=1 ");
            if (StringUtil.isNotEmpty(title)) {
                sb.append(" AND T.TITLE LIKE '%" + title + "%'");
            }
            if (StringUtil.isNotEmpty(unitName)) {
                sb.append(" AND T.RECEIVE_UNIT_NAME LIKE '%" + unitName + "%'");
            }
            if (StringUtil.isNotEmpty(regType)) {
                sb.append(" AND T.REG_TYPE = '" + regType + "'");
            }
            sb.append(" ORDER BY  T.CREATE_DATE DESC");
            String[] params = new String[] { operateStatus, user.getId()};
            List<Map<String, Object>> result = systemService.findForJdbcParam(sql + sb.toString(), dataGrid.getPage(),
                    dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(cql + sb.toString(), params);
            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除收文阅批单信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOReceiveBill = systemService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
        message = "收文阅批单信息表删除成功";
        try {
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
            List<TOFlowReceiveLogsEntity> rlist = new ArrayList<TOFlowReceiveLogsEntity>();
            List<TOFlowSendLogsEntity> slist = new ArrayList<TOFlowSendLogsEntity>();
            rcq.eq("sendReceiveId", tOReceiveBill.getId());
            rcq.add();
            scq.eq("sendReceiveId", tOReceiveBill.getId());
            scq.add();
            rlist = systemService.getListByCriteriaQuery(rcq, false);
            slist = systemService.getListByCriteriaQuery(scq, false);
            for (TOFlowReceiveLogsEntity r : rlist) {
                systemService.delete(r);
            }
            for (TOFlowSendLogsEntity s : slist) {
                systemService.delete(s);
            }
            tOReceiveBillService.delete(tOReceiveBill);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文阅批单信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 阅批单发送页面跳转
     * 
     * @param tOReceiveBill
     * @param req
     * @return
     */
    @RequestMapping(params = "goSend")
    public ModelAndView goSend(HttpServletRequest req) {
        String id = req.getParameter("id");
        String rid = req.getParameter("rid");
        if (StringUtil.isNotEmpty(id)) {
            TOReceiveBillEntity receiveBill = systemService.get(TOReceiveBillEntity.class, id);
            req.setAttribute("tOReceiveBillPage", receiveBill);
        }
        req.setAttribute("id", id);
        req.setAttribute("rid", rid);
        return new ModelAndView("com/kingtake/office/receivebill/sendOrg");
    }

    /**
     * 传阅页面跳转
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goCirculate")
    public ModelAndView goCirculate(HttpServletRequest req) {
        String suggestionType = req.getParameter("suggestionType");
        String ifcirculate = req.getParameter("ifcirculate");
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("ifcirculate", ifcirculate);
        req.setAttribute("suggestionType", suggestionType);
        return new ModelAndView("com/kingtake/office/receivebill/circulateOrg");
    }

    /**
     * 呈送上级页面跳转
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPresent")
    public ModelAndView goPresent(HttpServletRequest req) {
        //        String suggestionType = req.getParameter("suggestionType");
        String ifcirculate = req.getParameter("ifcirculate");
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("ifcirculate", ifcirculate);
        //        req.setAttribute("suggestionType", suggestionType);
        return new ModelAndView("com/kingtake/office/receivebill/presentOrg");
    }

    /**
     * Ajax请求，阅批单发送
     * 
     * @param tOReceiveBill
     * @param request
     * @return
     */
    @RequestMapping(params = "send")
    @ResponseBody
    public AjaxJson send(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String rid = request.getParameter("rid");
        String opinion = request.getParameter("opinion");
        String receiverid = request.getParameter("receiverid");
        String leaderReview = request.getParameter("leaderReview");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        message = "收文阅批单发送成功！";
        try {
            TOReceiveBillEntity t = tOReceiveBillService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
            MyBeanUtils.copyBeanNotNull2Bean(tOReceiveBill, t);
            TSUser user = ResourceUtil.getSessionUserName();
            if (ReceiveBillConstant.AUDIT_PASS.equals(opinion)) {//通过
                //            tOReceiveBill = systemService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
                TOFlowSendLogsEntity send = new TOFlowSendLogsEntity();
                send.setOperateUserid(user.getId());
                send.setOperateUsername(user.getRealName());
                send.setOperateDate(new Date());
                send.setFlowReceiveId(rid);
                String ip = "";
                if (request.getHeader("x-forwarded-for") == null) {
                    ip = request.getRemoteAddr();
                } else {
                    ip = request.getHeader("x-forwarded-for");
                }
                send.setSendReceiveId(tOReceiveBill.getId());
                send.setOperateIp(ip);
                send.setOperateDepartid(user.getCurrentDepart().getId());
                send.setOperateDepartname(user.getCurrentDepart().getDepartname());
                send.setSenderTip("通过，" + leaderReview);
                systemService.save(send);
                TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
                receive.setToFlowSendId(send.getId());
                receive.setSendReceiveId(tOReceiveBill.getId());
                TSUser receiver = systemService.get(TSUser.class, receiverid);
                receive.setReceiveUserid(receiver.getId());
                receive.setReceiveDepartid(departId);
                receive.setValidFlag(SrmipConstants.YES);//默认有效
                //            receive.setSignInFlag(SrmipConstants.NO);//签收状态默认为未签收
                receive.setReceiveDepartname(departName);
                receive.setReceiveUsername(receiver.getRealName());
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
                systemService.save(receive);
                //                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tOMessageService.sendMessage(receiverid, "新的阅批单消息", "收文", "您有新的收文阅批单需处理\n阅批单标题[" + t.getTitle()
                        + "]\n请到协同办公->收文管理中办理", send.getOperateUserid());
            } else {//驳回
                t.setBackUserId(user.getId());
                t.setBackUserName(user.getRealName());
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);//公文状态置为被驳回
                systemService.updateEntitie(t);
                tOMessageService.sendMessage(t.getContactId(), "阅批单[" + t.getTitle() + "]被驳回！", "收文",
                        "驳回人：" + user.getRealName() + "\n意见说明：" + leaderReview, user.getId());
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, t.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_REBUT);
                systemService.updateEntitie(reg);
            }

            //发送给下一步处理人即为办理完成
            TOFlowReceiveLogsEntity receiveLog = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receiveLog.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
            receiveLog.setOperateTime(new Date());
            receiveLog.setSuggestionCode(opinion);
            systemService.updateEntitie(receiveLog);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文阅批单发送失败！";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 查看当前接收人是否有未处理记录（三种公文公用）
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "checkReceive")
    @ResponseBody
    public AjaxJson checkReceive(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String id = req.getParameter("id");
        String receiverids = req.getParameter("receiverids");
        String departIds = req.getParameter("departIds");
        String[] rids = receiverids.split(",");
        String[] dids = departIds.split(",");
        for (int i = 0; i < rids.length; i++) {
            String[] params = new String[] { id, rids[i], dids[i] };
            String sql = "SELECT COUNT(0) FROM T_O_FLOW_RECEIVE_LOGS R WHERE R.SEND_RECEIVE_ID =? AND R.OPERATE_STATUS='0' AND R.RECEIVE_USERID=? AND R.RECEIVE_DEPARTID=?";
            Long count = systemService.getCountForJdbcParam(sql, params);
            if (count > 0) {
                j.setObj(false);
                return j;
            }
        }
        j.setObj(true);
        return j;
    }

    /**
     * Ajax请求，阅批单签收
     * 
     * @param tOReceiveBill
     * @param request
     * @return
     */
    @RequestMapping(params = "doSignIn")
    @ResponseBody
    public AjaxJson doSignIn(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "签收成功！";
        try {
            tOReceiveBill = systemService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            cq.eq("sendReceiveId", tOReceiveBill.getId());
            cq.eq("receiveUserid", user.getId());
            cq.eq("signInFlag", SrmipConstants.NO);
            cq.add();
            List<TOFlowReceiveLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TOFlowReceiveLogsEntity receive = list.get(0);
                receive.setSignInFlag(SrmipConstants.YES);
                receive.setSignInTime(new Date());
                systemService.updateEntitie(receive);
            }
            tOReceiveBillService.save(tOReceiveBill);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = "签收失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * Ajax请求，阅批单归档
     * 
     * @param tOReceiveBill
     * @param request
     * @return
     */
    @RequestMapping(params = "archive")
    @ResponseBody
    public AjaxJson archive(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "归档成功！";
        try {
            tOReceiveBill = systemService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            tOReceiveBill.setArchiveFlag(ReceiveBillConstant.BILL_COMPLETE);
            tOReceiveBill.setArchiveUserid(user.getId());
            tOReceiveBill.setArchiveUsername(user.getRealName());
            tOReceiveBill.setArchiveDate(new Date());
            tOReceiveBillService.save(tOReceiveBill);
            CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            cq.eq("sendReceiveId", tOReceiveBill.getId());
            cq.eq("operateStatus", SrmipConstants.NO);
            cq.add();
            List<TOFlowReceiveLogsEntity> list = systemService.getListByCriteriaQuery(cq, false);
            for (TOFlowReceiveLogsEntity re : list) {//归档时将未处理接收记录改为无效
                re.setValidFlag(SrmipConstants.NO);
                systemService.updateEntitie(re);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = "归档失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除收文阅批单信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "收文阅批单信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TOReceiveBillEntity tOReceiveBill = systemService.getEntity(TOReceiveBillEntity.class, id);
                CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
                List<TOFlowReceiveLogsEntity> rlist = new ArrayList<TOFlowReceiveLogsEntity>();
                List<TOFlowSendLogsEntity> slist = new ArrayList<TOFlowSendLogsEntity>();
                rcq.eq("sendReceiveId", tOReceiveBill.getId());
                rcq.add();
                scq.eq("sendReceiveId", tOReceiveBill.getId());
                scq.add();
                rlist = systemService.getListByCriteriaQuery(rcq, false);
                slist = systemService.getListByCriteriaQuery(scq, false);
                for (TOFlowReceiveLogsEntity r : rlist) {
                    systemService.delete(r);
                }
                for (TOFlowSendLogsEntity s : slist) {
                    systemService.delete(s);
                }
                tOReceiveBillService.delete(tOReceiveBill);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文阅批单信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加收文阅批单信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "收文阅批单信息表添加成功";
        try {
            this.tOReceiveBillService.addReceiveBill(tOReceiveBill, request);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文阅批单信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOReceiveBill);
        j.setMsg(message);
        return j;
    }



    /**
     * 更新收文阅批单信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "收文阅批单信息表更新成功";
        TOReceiveBillEntity t = tOReceiveBillService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOReceiveBill, t);
            tOReceiveBillService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文阅批单信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doNothing")
    @ResponseBody
    public AjaxJson doNothing(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        return j;
    }

    /**
     * Ajax请求，阅批单审批处理
     * 
     * @param tOReceiveBill
     * @param request
     * @return
     */
    @RequestMapping(params = "doOperate")
    @ResponseBody
    public AjaxJson doOperate(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String suggestionFlag = request.getParameter("suggestionFlag");
        String suggestionType = "";
        String suggestionContent = "";
        if (StringUtil.isNotEmpty(tOReceiveBill.getDutyOpinion())) {
            suggestionType = ReceiveBillConstant.DUTY_OPINION;
            suggestionContent = tOReceiveBill.getDutyOpinion();
        } else if (StringUtil.isNotEmpty(tOReceiveBill.getOfficeReview())) {
            suggestionType = ReceiveBillConstant.OFFICE_REVIEW;
            suggestionContent = tOReceiveBill.getOfficeReview();
        } else if (StringUtil.isNotEmpty(tOReceiveBill.getLeaderReview())) {
            suggestionType = ReceiveBillConstant.LEADER_REVIEW;
            suggestionContent = tOReceiveBill.getLeaderReview();
        }
        message = "收文阅批单处理成功";
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOReceiveBill.getId());
        rcq.eq("receiveUserid", user.getId());
        rcq.eq("operateStatus", SrmipConstants.NO);
        rcq.eq("validFlag", SrmipConstants.YES);
        rcq.add();
        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        TOReceiveBillEntity t = tOReceiveBillService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOReceiveBill, t);
            if (SrmipConstants.NO.equals(suggestionFlag)) {
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);
                tOReceiveBillService.updateEntitie(t);
            } else {
            }
            if (rlist.size() > 0) {
                TOFlowReceiveLogsEntity receive = rlist.get(0);
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
                receive.setOperateTime(new Date());
                receive.setSuggestionContent(suggestionContent);
                receive.setSuggestionType(suggestionType);
                receive.setSuggestionCode(suggestionFlag);
                systemService.updateEntitie(receive);
                String senderId = systemService.get(TOFlowSendLogsEntity.class, receive.getToFlowSendId())
                        .getOperateUserid();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                CriteriaQuery ucq = new CriteriaQuery(TSUser.class);
                ucq.eq("userName", tOReceiveBill.getCreateBy());
                ucq.add();
                TSUser createUser = (TSUser) systemService.getListByCriteriaQuery(ucq, false).get(0);
                tOMessageService.sendMessage(senderId + "," + createUser.getId(), "阅批单处理提醒", "收文",
                        "阅批单[" + t.getTitle() + "]已处理\n处理人:" + receive.getReceiveUsername() + "\n处理意见:"
                                + suggestionContent + "\n处理时间:" + sdf.format(receive.getOperateTime()),
                        receive.getReceiveUserid());
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文阅批单处理失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 根据业务id查看是否有附件
     * 
     * @param id
     * @param req
     * @return
     */
    @RequestMapping(params = "ifaccessory")
    @ResponseBody
    public AjaxJson ifaccessory(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        CriteriaQuery cq = new CriteriaQuery(TSFilesEntity.class);
        cq.eq("bid", id);
        cq.add();
        List<TSFilesEntity> flist = systemService.getListByCriteriaQuery(cq, false);
        if (flist.size() > 0) {
            j.setObj(SrmipConstants.YES);
        } else {
            j.setObj(SrmipConstants.NO);
        }
        return j;
    }

    /**
     * 收文阅批单信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        req.setAttribute("user", user);
        String regid = req.getParameter("regid");
        if (StringUtil.isNotEmpty(regid)) {
            TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, regid);
            tOReceiveBill.setSecrityGrade(reg.getSecurityGrade());
            tOReceiveBill.setTitle(reg.getTitle());
            tOReceiveBill.setBillNum(reg.getFileNum());//文号
            tOReceiveBill.setFileNumPrefix(reg.getFileNumPrefix());
            tOReceiveBill.setFileNumYear(reg.getFileNumYear());
            tOReceiveBill.setRegType(reg.getRegType());
            tOReceiveBill.setRegId(reg.getId());
            tOReceiveBill.setRegisterType(reg.getRegisterType());
            tOReceiveBill.setReceiveUnitName(reg.getOffice());
            tOReceiveBill.setDutyId(reg.getUndertakeDeptId());
            tOReceiveBill.setDutyName(reg.getUndertakeDeptName());
            tOReceiveBill.setContactId(reg.getUndertakeContactId());
            tOReceiveBill.setContactName(reg.getUndertakeContactName());
            tOReceiveBill.setContactTel(reg.getUndertakeTelephone());
            tOReceiveBill.setContentFileId(reg.getContentFileId());
            List<TSFilesEntity> files = tsFilesService.getFileListByBid(reg.getId());
            req.setAttribute("files", files);
            List<Map<String, Object>> list = regService.findModelByRegType(reg.getRegType());
            req.setAttribute("list", list);
            if (StringUtils.isNotEmpty(reg.getContentFileId())) {
                TOOfficeOnlineFilesEntity officeFile = systemService.get(TOOfficeOnlineFilesEntity.class,
                        reg.getContentFileId());
                req.setAttribute("officeFile", officeFile);
            }
        }
        tOReceiveBill.setRegisterId(user.getId());
        tOReceiveBill.setRegisterName(user.getRealName());
        tOReceiveBill.setRegisterTime(new Date());
        tOReceiveBill.setRegisterDepartId(user.getCurrentDepart().getId());
        tOReceiveBill.setRegisterDepartName(user.getCurrentDepart().getDepartname());
        //收文没有默认联系人及承办单位
        //        tOReceiveBill.setContactTel(user.getMobilePhone());
        //        tOReceiveBill.setContactId(user.getId());
        //        tOReceiveBill.setContactName(user.getRealName());
        //        tOReceiveBill.setDutyId(user.getCurrentDepart().getId());
        //        tOReceiveBill.setDutyName(user.getCurrentDepart().getDepartname());
        tOReceiveBill.setArchiveFlag(ReceiveBillConstant.BILL_FLOWING);
        req.setAttribute("tOReceiveBillPage", tOReceiveBill);
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBill-add");
    }

    @RequestMapping(params = "goDetailTab")
    public ModelAndView goDetailTab(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        String regId = req.getParameter("regId");
        CriteriaQuery cq = new CriteriaQuery(TOReceiveBillEntity.class);
        cq.eq("regId", regId);
        cq.add();
        List<TOReceiveBillEntity> list = systemService.getListByCriteriaQuery(cq, false);
        req.setAttribute("list", list);
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBill-detailTab");
    }

    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            tOReceiveBill = systemService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
            req.setAttribute("tOReceiveBillPage", tOReceiveBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOReceiveBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBill-detail");
    }

    /**
     * 收文阅批单信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            tOReceiveBill = tOReceiveBillService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            rcq.eq("sendReceiveId", tOReceiveBill.getId());
            rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
            rcq.eq("validFlag", SrmipConstants.YES);
            rcq.add();
            List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            List<TOFlowReceiveLogsEntity> llist = new ArrayList<TOFlowReceiveLogsEntity>();
            List<TOFlowReceiveLogsEntity> olist = new ArrayList<TOFlowReceiveLogsEntity>();
            List<TOFlowReceiveLogsEntity> dlist = new ArrayList<TOFlowReceiveLogsEntity>();
            if (rlist.size() > 0) {
                for (TOFlowReceiveLogsEntity receive : rlist) {
                    if (receive.getSuggestionType().equals(ReceiveBillConstant.LEADER_REVIEW)) {
                        llist.add(receive);
                    } else if (receive.getSuggestionType().equals(ReceiveBillConstant.OFFICE_REVIEW)) {
                        olist.add(receive);
                    } else {
                        dlist.add(receive);
                    }
                }
                TOReceiveList slist = new TOReceiveList();
                slist.setDlist(dlist);
                slist.setLlist(llist);
                slist.setOlist(olist);
                req.setAttribute("hh", "\n");
                req.setAttribute("slist", slist);
            }
            req.setAttribute("tOReceiveBillPage", tOReceiveBill);
        }
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBill-update");
    }

    /**
     * 阅批单审批处理页面跳转
     * 
     * @param tOReceiveBill
     * @param req
     * @return
     */
    @RequestMapping(params = "goOperate")
    public ModelAndView goOperate(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            tOReceiveBill = tOReceiveBillService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
            req.setAttribute("tOReceiveBillPage", tOReceiveBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOReceiveBill.getContentFileId());
            req.setAttribute("file", file);
        }
        String rid = req.getParameter("rid");
        TOFlowReceiveLogsEntity receiveLog = this.systemService.get(TOFlowReceiveLogsEntity.class, rid);
        if (receiveLog != null && receiveLog.getSignInTime() == null) {
            receiveLog.setSignInTime(new Date());
            receiveLog.setSignInFlag("1");
            this.systemService.updateEntitie(receiveLog);
        }
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBill-operate");
    }

    @RequestMapping(params = "doFinish")
    @ResponseBody
    public AjaxJson doFinish(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            String rid = req.getParameter("rid");
            tOReceiveBill = systemService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
            tOReceiveBill.setArchiveFlag(ReceiveBillConstant.BILL_COMPLETE);
            systemService.updateEntitie(tOReceiveBill);
            TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, tOReceiveBill.getRegId());
            reg.setGenerationFlag(ReceiveBillConstant.BILL_COMPLETE);
            systemService.updateEntitie(reg);
            TOFlowReceiveLogsEntity receive = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receive.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
            receive.setOperateTime(new Date());
            receive.setSuggestionCode("1");
            systemService.updateEntitie(receive);
            TSUser regCreater = systemService.findUniqueByProperty(TSUser.class, "userName", reg.getCreateBy());
            tOMessageService.sendMessage(regCreater.getId(), "公文[" + tOReceiveBill.getTitle() + "]完成提醒！", "收文", "公文["
                    + tOReceiveBill.getTitle() + "]已完成，请到协同办公->收文登记中进行归档操作！", tOReceiveBill.getContactId());
            j.setMsg("操作成功！");
        }

        return j;
    }

    @RequestMapping(params = "doArchive")
    @ResponseBody
    public AjaxJson doArchive(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String regId = req.getParameter("regId");
        TSUser user = ResourceUtil.getSessionUserName();
        if (StringUtil.isNotEmpty(regId)) {
            TOSendReceiveRegEntity tOSendReceiveReg = systemService.get(TOSendReceiveRegEntity.class, regId);
            CriteriaQuery cq = new CriteriaQuery(TOReceiveBillEntity.class);
            cq.eq("regId", regId);
            cq.eq("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
            cq.add();
            List<TOReceiveBillEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                tOReceiveBill = list.get(0);
                tOReceiveBill.setArchiveFlag(ReceiveBillConstant.BILL_ARCHIVED);
                tOReceiveBill.setArchiveDate(new Date());
                tOReceiveBill.setArchiveUserid(user.getId());
                tOReceiveBill.setArchiveUsername(user.getRealName());
                systemService.updateEntitie(tOReceiveBill);
                tOSendReceiveReg.setGenerationFlag(ReceiveBillConstant.BILL_ARCHIVED);
                tOSendReceiveReg.setArchiveUserid(user.getId());
                tOSendReceiveReg.setArchiveDate(new Date());
                tOSendReceiveReg.setArchiveUsername(user.getRealName());
                systemService.updateEntitie(tOSendReceiveReg);
            }

        }
        j.setMsg("归档成功！");
        return j;
    }

    /**
     * 
     * @param rid
     *            阅批单id
     * @param uid
     *            用户id
     * @return false-未签收 true-已签收
     */
    public Boolean ifSignIn(String rid, String uid) {
        CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        cq.eq("sendReceiveId", rid);
        cq.eq("receiveUserid", uid);
        cq.notEq("signInFlag", SrmipConstants.YES);
        cq.add();
        int count = systemService.getCountByCriteriaQuery(cq);
        if (count > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否签收
     * 
     * @param id
     * @param req
     * @return
     */
    @RequestMapping(params = "ifSignIn")
    @ResponseBody
    public AjaxJson ifSignIn(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String signInFlag = "1";
        TSUser user = ResourceUtil.getSessionUserName();
        if (!ifSignIn(id, user.getId())) {
            signInFlag = "0";
        }
        j.setObj(signInFlag);
        return j;
    }

    /**
     * 获取阅批单审批流程列表
     * 
     * @param tOReceiveBill
     * @param req
     * @return
     */
    @RequestMapping(params = "getStepList")
    public ModelAndView getStepList(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        tOReceiveBill = tOReceiveBillService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
        CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
        scq.eq("sendReceiveId", tOReceiveBill.getId());
        scq.addOrder("operateDate", SortDirection.asc);
        scq.add();
        List<TOFlowSendLogsEntity> slist = systemService.getListByCriteriaQuery(scq, false);
        
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOReceiveBill.getId());
        rcq.addOrder("operateTime", SortDirection.asc);
        rcq.add();
        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        TOFlowSendLogsEntity sendLog = slist.get(0);
        TOFlowReceiveLogsEntity recLog = null;
        List<Map<String, Object>> stepList = new ArrayList<Map<String, Object>>();
        while(sendLog!=null) {
            Map<String, Object> flowMap = new HashMap<String, Object>();
            flowMap.put("operateUserName", sendLog.getOperateUsername());
            flowMap.put("operateTime", sendLog.getOperateDate());
            flowMap.put("operateTip", sendLog.getSenderTip());
            recLog = getRecLogFromSend(sendLog,rlist);
            flowMap.put("receiveTime", recLog.getSignInTime());
            stepList.add(flowMap);
            sendLog = getSendLogFromRec(recLog, slist); 
        }
        Map<String, Object> flowMap = new HashMap<String, Object>();
        flowMap.put("operateUserName", recLog.getReceiveUsername());
        flowMap.put("operateTime", recLog.getOperateTime());
        String operate = "";
        if("1".equals(recLog.getOperateStatus())){
            if("1".equals(recLog.getSuggestionCode())){
                operate = "通过";
            }else{
                operate = "驳回";
            }
        }
        flowMap.put("operateTip", operate);
        flowMap.put("receiveTime", recLog.getSignInTime());
        stepList.add(flowMap);
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveStepBillList", "stepList", stepList);

    }
    
  //通过发送记录找接受记录
    private TOFlowReceiveLogsEntity getRecLogFromSend(TOFlowSendLogsEntity sendLog,List<TOFlowReceiveLogsEntity> rlist){
        TOFlowReceiveLogsEntity recLog = null;
        for(TOFlowReceiveLogsEntity tmp:rlist){
            if(sendLog.getId().equals(tmp.getToFlowSendId())){
                recLog = tmp;
                break;
            }
        }
        return recLog;
    }
    
    //通过接受记录找发送记录
    private TOFlowSendLogsEntity getSendLogFromRec(TOFlowReceiveLogsEntity recLog,List<TOFlowSendLogsEntity> slist){
        TOFlowSendLogsEntity sendLog = null;
        for(TOFlowSendLogsEntity tmp:slist){
            if(recLog.getId().equals(tmp.getFlowReceiveId())){
                sendLog = tmp;
                break;
            }
        }
        return sendLog;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveBillUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOReceiveBillEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOReceiveBill,
                request.getParameterMap());

        TSUser user = ResourceUtil.getSessionUserName();
        cq.eq("createBy", user.getUserName());

        String operateStatus = request.getParameter("operateStatus");
        // 未处理
        if (SrmipConstants.NO.equals(operateStatus)) {
            cq.notEq("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
        } else {
            cq.eq("archiveFlag", ReceiveBillConstant.BILL_COMPLETE);
        }
        cq.add();

        List<TOReceiveBillEntity> tOReceiveBills = this.tOReceiveBillService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "收文阅批单信息表");
        modelMap.put(NormalExcelConstants.CLASS, TOReceiveBillEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("收文阅批单信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tOReceiveBills);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "收文阅批单信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOReceiveBillEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * 阅批单导出
     * 
     * @param tOReceiveBill
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "exportDocByT")
    public String exportDocByT(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            tOReceiveBill = systemService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
        }
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOReceiveBill.getId());
        rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
        rcq.eq("validFlag", SrmipConstants.YES);
        //读取配置文件中的排序
        PropertiesUtil prop = new PropertiesUtil("sendReceiveBill.properties");
        String orderBy = prop.readProperty("receiveBill.receive.orderBy");
        String[] orderBys = orderBy.split(",");
        String fieldName = "";
        SortDirection direction = null;
        for (int i = 0; i < orderBys.length; i++) {
            fieldName = orderBys[i].split("\\.")[0];
            if (orderBys[i].split("\\.")[1].equals("desc")) {
                direction = SortDirection.desc;
            } else {
                direction = SortDirection.asc;
            }
            rcq.addOrder(fieldName, direction);
        }

        rcq.add();
        //        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        //        List<TOFlowReceiveLogsEntity> llist = new ArrayList<TOFlowReceiveLogsEntity>();
        //        List<TOFlowReceiveLogsEntity> olist = new ArrayList<TOFlowReceiveLogsEntity>();
        //        List<TOFlowReceiveLogsEntity> dlist = new ArrayList<TOFlowReceiveLogsEntity>();
        //        if (rlist.size() > 0) {
        //            for (TOFlowReceiveLogsEntity receive : rlist) {
        //                if (receive.getSuggestionType().equals(ReceiveBillConstant.LEADER_REVIEW)) {
        //                    llist.add(receive);
        //                } else if (receive.getSuggestionType().equals(ReceiveBillConstant.OFFICE_REVIEW)) {
        //                    olist.add(receive);
        //                } else {
        //                    dlist.add(receive);
        //                }
        //            }
        //        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(tOReceiveBill.getReceiveUnitName())) {
            map.put("dname", tOReceiveBill.getReceiveUnitName());
        }
        if (StringUtil.isNotEmpty(tOReceiveBill.getBillNum())) {
            map.put("bnum", tOReceiveBill.getFileNumPrefix() + "﹝20" + tOReceiveBill.getFileNumYear() + "﹞"
                    + tOReceiveBill.getBillNum());
        }

        if (StringUtil.isNotEmpty(ReceiveBillConstant.GRADE_TRANSLATE.get(tOReceiveBill.getSecrityGrade()))) {
            map.put("grade", ReceiveBillConstant.GRADE_TRANSLATE.get(tOReceiveBill.getSecrityGrade()));
        }
        if (StringUtil.isNotEmpty(tOReceiveBill.getTitle())) {
            map.put("title", tOReceiveBill.getTitle());
        }
        //查询校长审批电子签章内容
        WordImageEntity presidentEntity = getSignatureBlob(tOReceiveBill, "receiveBillpresident", request);
        if (presidentEntity != null) {
            map.put("leader", presidentEntity);
        }
        //查询机关部领导电子签章内容
        WordImageEntity departmentLeaderEntity = getSignatureBlob(tOReceiveBill, "receiveBilldepartmentLeader", request);
        if (departmentLeaderEntity != null) {
            map.put("office", departmentLeaderEntity);
        }
        //查询机关部领导电子签章内容
        WordImageEntity dutyOptionEntity = getSignatureBlob(tOReceiveBill, "receiceBillDutyOption", request);
        if (dutyOptionEntity != null) {
            map.put("dutyview", dutyOptionEntity);
        }
        //        if (llist.size() > 0) {
        //            StringBuffer sb = new StringBuffer();
        //            for (TOFlowReceiveLogsEntity r : llist) {
        //                sb.append(r.getReceiveUsername() + ":" + r.getSuggestionContent());
        //                sb.append("\n");
        //            }
        //            map.put("leader", sb.toString());
        //        }
        //        if (olist.size() > 0) {
        //            StringBuffer sb = new StringBuffer();
        //            for (TOFlowReceiveLogsEntity r : olist) {
        //                sb.append(r.getReceiveUsername() + ":" + r.getSuggestionContent());
        //                sb.append("\n");
        //            }
        //            map.put("office", sb.toString());
        //        }
        //        if (dlist.size() > 0) {
        //            StringBuffer sb = new StringBuffer();
        //            for (TOFlowReceiveLogsEntity r : dlist) {
        //                sb.append(r.getReceiveUsername() + ":" + r.getSuggestionContent());
        //                sb.append("\n");
        //            }
        //            map.put("dutyview", sb.toString());
        //        }
        if (StringUtil.isNotEmpty(tOReceiveBill.getDutyName())) {
            map.put("dutyname", tOReceiveBill.getDutyName());
        }
        if (StringUtil.isNotEmpty(tOReceiveBill.getContactName())) {
            map.put("cname", tOReceiveBill.getContactName());
        }
        if (StringUtil.isNotEmpty(tOReceiveBill.getContactTel())) {
            map.put("ctel", tOReceiveBill.getContactTel());
        }
        modelMap.put(TemplateWordConstants.FILE_NAME, "收文阅批单(" + tOReceiveBill.getTitle() + ")");
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        if (request.getParameter("flag").equals("1")) {//标志位为1时使用套打模板
            modelMap.put(TemplateWordConstants.URL, "export/template/swxpd1.docx");
        } else {
            modelMap.put(TemplateWordConstants.URL, "export/template/swxpd.docx");
        }
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;

    }

    /**
     * 获取电子签章
     * 
     * @param tOSendBill
     * @param fieldName
     * @return
     */
    private WordImageEntity getSignatureBlob(TOReceiveBillEntity tOReceiveBill, String fieldName,
            HttpServletRequest request) {
        WordImageEntity entity = null;
        try {
            String parentPath = new File(request.getRealPath("/")).getParent();
            String fileName = tOReceiveBill.getId() + "_" + fieldName + ".jpg";
            File realFile = new File(parentPath + "/signature/" + fileName);
            if (realFile.exists()) {
                FileInputStream fos = new FileInputStream(realFile);
                byte[] mFileBody = IOUtils.toByteArray(fos);
                fos.close();
                entity = new WordImageEntity(mFileBody, 580, 180);
            }
        } catch (Exception e) {
            throw new BusinessException("获取电子签章失败", e);
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TOReceiveBillEntity> listTOReceiveBillEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TOReceiveBillEntity.class, params);
                for (TOReceiveBillEntity tOReceiveBill : listTOReceiveBillEntitys) {
                    tOReceiveBillService.save(tOReceiveBill);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    @RequestMapping(params = "ifFileExists")
    @ResponseBody
    public AjaxJson ifFileExists(HttpServletRequest req, String relativePath) {
        AjaxJson j = new AjaxJson();
        j.setObj(tsFilesService.ifFileExists(relativePath));
        return j;
    }

    @RequestMapping(params = "goOfficePage")
    public ModelAndView goOfficePage(HttpServletRequest req, TOReceiveBillEntity tOReceiveBill) {
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            tOReceiveBill = tOReceiveBillService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
            req.setAttribute("tOReceiveBillPage", tOReceiveBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOReceiveBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/receivebill/onlyOfficePage");
    }

    @RequestMapping(params = "goTDOfficePage")
    public ModelAndView goTDOfficePage(HttpServletRequest req, TOReceiveBillEntity tOReceiveBill) {
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            tOReceiveBill = tOReceiveBillService.getEntity(TOReceiveBillEntity.class, tOReceiveBill.getId());
            req.setAttribute("tOReceiveBillPage", tOReceiveBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOReceiveBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/receivebill/TDOfficePage");
    }

    /**
     * 查看页面跳转
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goSendReceiveReg")
    public ModelAndView goSendReceiveReg(TOReceiveBillEntity tOReceiveBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOReceiveBill.getId())) {
            tOReceiveBill = systemService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
            String regId = tOReceiveBill.getRegId();
            TOSendReceiveRegEntity sendReceiveReg = this.systemService.get(TOSendReceiveRegEntity.class, regId);
            req.setAttribute("tOSendReceiveRegPage", sendReceiveReg);
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-update");
    }

    /**
     * 完成审批或返回修改
     * 
     * @param tOReceiveBill
     * @param request
     * @return
     */
    @RequestMapping(params = "passReturn")
    @ResponseBody
    public AjaxJson passReturn(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TOReceiveBillEntity t = tOReceiveBillService.get(TOReceiveBillEntity.class, tOReceiveBill.getId());
        String rid = request.getParameter("rid");
        String opinion = request.getParameter("opinion");
        String receiverid = t.getContactId();
        String leaderReview = request.getParameter("leaderReview");
        String departId = t.getDutyId();
        String departName = t.getDutyName();
        message = "收文阅批单发送成功！";
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOReceiveBill, t);
            TSUser user = ResourceUtil.getSessionUserName();
            if (ReceiveBillConstant.AUDIT_PASS.equals(opinion)) {//通过
                TOFlowSendLogsEntity send = new TOFlowSendLogsEntity();
                send.setOperateUserid(user.getId());
                send.setOperateUsername(user.getRealName());
                send.setOperateDate(new Date());
                send.setFlowReceiveId(rid);
                String ip = "";
                if (request.getHeader("x-forwarded-for") == null) {
                    ip = request.getRemoteAddr();
                } else {
                    ip = request.getHeader("x-forwarded-for");
                }
                send.setSendReceiveId(tOReceiveBill.getId());
                send.setOperateIp(ip);
                send.setOperateDepartid(user.getCurrentDepart().getId());
                send.setOperateDepartname(user.getCurrentDepart().getDepartname());
                send.setSenderTip("通过，" + leaderReview);
                systemService.save(send);
                TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
                receive.setToFlowSendId(send.getId());
                receive.setSendReceiveId(tOReceiveBill.getId());
                TSUser receiver = systemService.get(TSUser.class, receiverid);
                receive.setReceiveUserid(receiver.getId());
                receive.setReceiveDepartid(departId);
                receive.setValidFlag(SrmipConstants.YES);//默认有效
                //            receive.setSignInFlag(SrmipConstants.NO);//签收状态默认为未签收
                receive.setReceiveDepartname(departName);
                receive.setReceiveUsername(receiver.getRealName());
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
                systemService.save(receive);
                tOMessageService.sendMessage(receiverid, "新的阅批单消息", "收文", "您有新的收文阅批单需处理\n阅批单标题[" + t.getTitle()
                        + "]\n请到协同办公->收文管理中办理", send.getOperateUserid());
            } else {//驳回
                t.setBackUserId(user.getId());
                t.setBackUserName(user.getRealName());
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);//公文状态置为被驳回
                systemService.updateEntitie(t);
                tOMessageService.sendMessage(t.getContactId(), "阅批单[" + t.getTitle() + "]被驳回！", "收文",
                        "驳回人：" + user.getRealName() + "\n意见说明：" + leaderReview, user.getId());
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, t.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_REBUT);
                systemService.updateEntitie(reg);
            }

            //发送给下一步处理人即为办理完成
            TOFlowReceiveLogsEntity receiveLog = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receiveLog.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
            receiveLog.setOperateTime(new Date());
            receiveLog.setSuggestionCode(opinion);
            systemService.updateEntitie(receiveLog);
        } catch (Exception e) {
            e.printStackTrace();
            message = "收文阅批单发送失败！";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
}
