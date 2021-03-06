package com.kingtake.office.controller.sendbill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import org.jeecgframework.web.system.pojo.base.TSDepart;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SendBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;
import com.kingtake.office.entity.flow.TOFlowSendLogsEntity;
import com.kingtake.office.entity.receivebill.TOReceiveList;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.entity.sendbill.TOSendBillEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.office.service.sendReceive.TOSendReceiveRegServiceI;
import com.kingtake.office.service.sendbill.TOSendBillServiceI;
import com.kingtake.officeonline.entity.TOOfficeOnlineFilesEntity;

/**
 * @Title: Controller
 * @Description: ???????????????
 * @author onlineGenerator
 * @date 2015-08-06 16:16:31
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOSendBillController")
public class TOSendBillController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOSendBillController.class);

    @Autowired
    private TOSendBillServiceI tOSendBillService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TSFilesService tsFilesService;
    @Autowired
    private TOSendReceiveRegServiceI regService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * ????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tOSendBill")
    public ModelAndView tOSendBill(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBillList");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tOSendBillListToMe")
    public ModelAndView tOSendBillList_toMe(HttpServletRequest request) {
        request.setAttribute("operateStatus", request.getParameter("operateStatus"));
        request.setAttribute("registerType", request.getParameter("registerType"));
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBillListToMe");
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TOSendBillEntity tOSendBill, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int start = (page - 1) * rows + 1;
        int end = (page) * rows;
        TSUser user = ResourceUtil.getSessionUserName();
        Map<String, String> param = new HashMap<String, String>();
        param.put("userId", user.getId());
        String operateStatus = request.getParameter("operateStatus");
        param.put("operateStatus", operateStatus);
        String title = request.getParameter("SEND.TITLE");
        if (StringUtils.isNotEmpty(title)) {
            param.put("title", title);
        }
        String sendTypeCode = request.getParameter("SEND.TYPE.CODE");
        if (StringUtils.isNotEmpty(sendTypeCode)) {
            param.put("sendTypeCode", sendTypeCode);
        }
        try {
            List<Map<String, Object>> result = this.tOSendBillService.getSendBillList(param, start, end);
            for (Map<String, Object> map : result) {
                String contactId = (String) map.get("CONTACT_ID");
                if (StringUtils.isNotEmpty(contactId)) {
                    String mainId = contactId.split(",")[0];
                    map.put("mainContactId", mainId);
                }
            }
            Integer count = this.tOSendBillService.getSendBillCount(param);
            dataGrid.setResults(result);
            dataGrid.setTotal(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goFirstSend")
    public ModelAndView goFirstSend(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/sendbill/firstSendOrg");
    }

    @RequestMapping(params = "goOfficePage")
    public ModelAndView goOfficePage(HttpServletRequest req, TOSendBillEntity tOSendBill) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = systemService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
            req.setAttribute("tOSendBillPage", tOSendBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOSendBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/sendbill/onlyOfficePage");
    }

    @RequestMapping(params = "goTDOfficePage")
    public ModelAndView goTDOfficePage(HttpServletRequest req, TOSendBillEntity tOSendBill) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = systemService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
            req.setAttribute("tOSendBillPage", tOSendBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOSendBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/sendbill/TDOfficePage");
    }

    /**
     * ?????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOSendBill = systemService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
        message = "???????????????????????????";
        try {
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
            List<TOFlowReceiveLogsEntity> rlist = new ArrayList<TOFlowReceiveLogsEntity>();
            List<TOFlowSendLogsEntity> slist = new ArrayList<TOFlowSendLogsEntity>();
            rcq.eq("sendReceiveId", tOSendBill.getId());
            rcq.add();
            scq.eq("sendReceiveId", tOSendBill.getId());
            scq.add();
            rlist = systemService.getListByCriteriaQuery(rcq, false);
            slist = systemService.getListByCriteriaQuery(scq, false);
            for (TOFlowReceiveLogsEntity r : rlist) {
                systemService.delete(r);
            }
            for (TOFlowSendLogsEntity s : slist) {
                systemService.delete(s);
            }
            tOSendBillService.delete(tOSendBill);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TOSendBillEntity tOSendBill = systemService.getEntity(TOSendBillEntity.class, id);
                CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
                List<TOFlowReceiveLogsEntity> rlist = new ArrayList<TOFlowReceiveLogsEntity>();
                List<TOFlowSendLogsEntity> slist = new ArrayList<TOFlowSendLogsEntity>();
                rcq.eq("sendReceiveId", tOSendBill.getId());
                rcq.add();
                scq.eq("sendReceiveId", tOSendBill.getId());
                scq.add();
                rlist = systemService.getListByCriteriaQuery(rcq, false);
                slist = systemService.getListByCriteriaQuery(scq, false);
                for (TOFlowReceiveLogsEntity r : rlist) {
                    systemService.delete(r);
                }
                for (TOFlowSendLogsEntity s : slist) {
                    systemService.delete(s);
                }
                tOSendBillService.delete(tOSendBill);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            tOSendBillService.save(tOSendBill);
            if (StringUtil.isNotEmpty(tOSendBill.getRegId())) {
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, tOSendBill.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_FLOWING);
                systemService.updateEntitie(reg);
                regService.copyFile(reg.getId(), tOSendBill.getId(), "tOSendBill");
            }
            //???????????????
            firstSend(tOSendBill, request);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(tOSendBill);
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "recordNuclearDraft")
    @ResponseBody
    public AjaxJson recordNuclearDraft(HttpServletRequest req, String id) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TOSendBillEntity bill = systemService.get(TOSendBillEntity.class, id);
            TSUser user = ResourceUtil.getSessionUserName();
            bill.setNuclearDraftUserid(user.getId());
            bill.setNuclearDraftUsername(user.getRealName());
            bill.setNuclearDraftStatus(SrmipConstants.YES);
            bill.setNuclearDraftDepartId(user.getCurrentDepart().getId());
            bill.setNuclearDraftDepartName(user.getCurrentDepart().getDepartname());
            systemService.updateEntitie(bill);
            j.setMsg("???????????????");
        }
        return j;
    }

    /**
     * 
     * @param tOReceiveBill
     * @param request
     */

    public void firstSend(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        String receiverid = request.getParameter("receiverid");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        String realName = request.getParameter("realName");
        String leaderReview = request.getParameter("leaderReview");
        //????????????
        TOFlowSendLogsEntity send = new TOFlowSendLogsEntity();
        TSUser user = ResourceUtil.getSessionUserName();
        send.setSendReceiveId(tOSendBill.getId());
        send.setOperateDate(new Date());
        send.setOperateDepartid(user.getCurrentDepart().getId());
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        send.setOperateIp(ip);
        send.setOperateUserid(user.getId());
        send.setOperateUsername(user.getRealName());
        send.setSenderTip("?????????" + leaderReview);
        systemService.save(send);
        //????????????
        TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
        receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
        receive.setReceiveDepartid(departId);
        receive.setReceiveDepartname(departName);
        receive.setReceiveUserid(receiverid);
        receive.setReceiveUsername(realName);
        receive.setSendReceiveId(tOSendBill.getId());
        receive.setToFlowSendId(send.getId());
        receive.setValidFlag(SrmipConstants.YES);
        systemService.save(receive);
        //????????????????????????
        tOMessageService.sendMessage(receive.getReceiveUserid(), "??????????????????????????????????????????", "??????",
                "????????????????????????????????????????????????????????????->????????????????????????",
                user.getId());
    }

    /**
     * ?????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        TOSendBillEntity t = tOSendBillService.get(TOSendBillEntity.class, tOSendBill.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOSendBill, t);
            tOSendBillService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        TSUser user = ResourceUtil.getSessionUserName();
        req.setAttribute("user", user);
        String regid = req.getParameter("regid");
        if (StringUtil.isNotEmpty(regid)) {
            TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, regid);
            tOSendBill.setSecrityGrade(reg.getSecurityGrade());
            tOSendBill.setSendTitle(reg.getTitle());
            tOSendBill.setSendNum(reg.getFileNum());
            tOSendBill.setFileNumPrefix(reg.getFileNumPrefix());
            tOSendBill.setSendYear(reg.getFileNumYear());
            tOSendBill.setSendTypeCode(reg.getRegType());
            tOSendBill.setRegId(reg.getId());
            tOSendBill.setPrintNum(reg.getCount());//??????
            tOSendBill.setRegisterType(reg.getRegisterType());
            tOSendBill.setSendUnit(reg.getOffice());
            tOSendBill.setUndertakeUnitId(reg.getUndertakeDeptId());
            tOSendBill.setUndertakeUnitName(reg.getUndertakeDeptName());
            tOSendBill.setContactId(reg.getUndertakeContactId());
            tOSendBill.setContactName(reg.getUndertakeContactName());
            tOSendBill.setContactPhone(reg.getUndertakeTelephone());
            tOSendBill.setContentFileId(reg.getContentFileId());
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
        tOSendBill.setNuclearDraftStatus(SrmipConstants.NO);
        //tOSendBill.setContactId(user.getId());
        //tOSendBill.setContactName(user.getRealName());
        //tOSendBill.setContactPhone(user.getMobilePhone());
        tOSendBill.setDraftDate(new Date());
        //tOSendBill.setUndertakeUnitId(user.getCurrentDepart().getId());
        //tOSendBill.setUndertakeUnitName(user.getCurrentDepart().getDepartname());
        tOSendBill.setArchiveFlag(ReceiveBillConstant.BILL_FLOWING);
        req.setAttribute("tOSendBillPage", tOSendBill);
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBill-add");
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = tOSendBillService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            rcq.eq("sendReceiveId", tOSendBill.getId());
            rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
            rcq.eq("validFlag", SrmipConstants.YES);
            //??????????????????????????????
            PropertiesUtil prop = new PropertiesUtil("sendReceiveBill.properties");
            String orderBy = prop.readProperty("sendBill.receive.orderBy");
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
            List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            List<TOFlowReceiveLogsEntity> llist = new ArrayList<TOFlowReceiveLogsEntity>();
            List<TOFlowReceiveLogsEntity> olist = new ArrayList<TOFlowReceiveLogsEntity>();
            if (rlist.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (TOFlowReceiveLogsEntity receive : rlist) {
                    if (receive.getSuggestionType().equals(ReceiveBillConstant.LEADER_REVIEW)) {
                        llist.add(receive);
                    } else if (receive.getSuggestionType().equals(ReceiveBillConstant.OFFICE_REVIEW)) {
                        olist.add(receive);
                    } else {
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        sb.append(receive.getReceiveUsername());
                    }
                }
                tOSendBill.setNuclearDraftUsername(sb.toString());
                TOReceiveList slist = new TOReceiveList();
                slist.setLlist(llist);
                slist.setOlist(olist);
                req.setAttribute("hh", "\n");
                req.setAttribute("slist", slist);
            }
            req.setAttribute("tOSendBillPage", tOSendBill);
        }
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBill-update");
    }

    /**
     * ?????????????????????????????????
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "goSend")
    public ModelAndView goSend(HttpServletRequest req) {
        String id = req.getParameter("id");
        String rid = req.getParameter("rid");
        if (StringUtil.isNotEmpty(id)) {
            TOSendBillEntity tOSendBill = systemService.get(TOSendBillEntity.class, id);
            TOSendBillEntity tmp = new TOSendBillEntity();
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tOSendBill, tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (tmp.getContactId().contains(",")) {
                tmp.setContactId(getFirst(tmp.getContactId()));
                tmp.setUndertakeUnitId(getFirst(tmp.getUndertakeUnitId()));
                tmp.setUndertakeUnitName(getFirst(tmp.getUndertakeUnitName()));
                tmp.setContactName(getFirst(tmp.getContactName()));
            }
            req.setAttribute("tOReceiveBillPage", tmp);
        }
        req.setAttribute("id", id);
        req.setAttribute("rid", rid);
        return new ModelAndView("com/kingtake/office/sendbill/sendOrg");
    }

    /**
     * ???????????????????????????
     * 
     * @param tOSendBill
     * @param request
     * @return
     */
    @RequestMapping(params = "send")
    @ResponseBody
    public AjaxJson send(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String rid = request.getParameter("rid");
        String opinion = request.getParameter("opinion");
        String receiverid = request.getParameter("receiverid");
        String realName = request.getParameter("realName");
        String leaderReview = request.getParameter("leaderReview");
        String departId = request.getParameter("departId");
        String departName = request.getParameter("departName");
        message = "??????????????????????????????";
        try {
            TOSendBillEntity t = tOSendBillService.get(TOSendBillEntity.class, tOSendBill.getId());
            MyBeanUtils.copyBeanNotNull2Bean(tOSendBill, t);
            TSUser user = ResourceUtil.getSessionUserName();
            if (ReceiveBillConstant.AUDIT_PASS.equals(opinion)) {//??????
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
                send.setSendReceiveId(tOSendBill.getId());
                send.setOperateIp(ip);
                send.setOperateDepartid(user.getCurrentDepart().getId());
                send.setOperateDepartname(user.getCurrentDepart().getDepartname());
                send.setSenderTip("?????????" + leaderReview);
                systemService.save(send);
                TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
                receive.setToFlowSendId(send.getId());
                receive.setSendReceiveId(tOSendBill.getId());
                receive.setReceiveUserid(receiverid);
                receive.setReceiveDepartid(departId);
                receive.setValidFlag(SrmipConstants.YES);//????????????
                receive.setReceiveDepartname(departName);
                receive.setReceiveUsername(realName);
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
                systemService.save(receive);
                tOMessageService.sendMessage(receiverid, "???????????????????????????", "??????", "????????????????????????????????????\n???????????????[" + t.getSendTitle()
                        + "]\n??????????????????->?????????????????????", send.getOperateUserid());
            } else {//??????
                t.setBackUserid(user.getId());
                t.setBackUsername(user.getRealName());
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);//???????????????????????????
                systemService.updateEntitie(t);
                tOMessageService.sendMessage(t.getContactId(), "?????????[" + t.getSendTitle() + "]????????????", "??????",
                        "????????????" + user.getRealName() + "\n???????????????" + leaderReview, user.getId());
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, t.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_REBUT);
                systemService.updateEntitie(reg);
            }

            //?????????????????????????????????????????????
            TOFlowReceiveLogsEntity receiveLog = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receiveLog.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
            receiveLog.setOperateTime(new Date());
            receiveLog.setSuggestionCode(opinion);
            systemService.updateEntitie(receiveLog);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????????????????(???????????????)
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goFinish")
    public ModelAndView goFinish(HttpServletRequest req) {
        String id = req.getParameter("id");
        String rid = req.getParameter("rid");
        req.setAttribute("id", id);
        req.setAttribute("rid", rid);
        return new ModelAndView("com/kingtake/office/sendbill/finishOrg");
    }

    /**
     * ???????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goDocNumReg")
    public ModelAndView goDocNumReg(HttpServletRequest req) {
        String regId = req.getParameter("regId");
        TOSendReceiveRegEntity reg = this.systemService.get(TOSendReceiveRegEntity.class, regId);
        Map<String, String> map = tOSendBillService.getFileNum(reg);
        req.setAttribute("fileNumMap", map);
        return new ModelAndView("com/kingtake/office/sendbill/docNumReg");
    }

    /**
     * ???????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "doFinish")
    @ResponseBody
    public AjaxJson doFinish(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
            this.tOSendBillService.doFinish(req);
            j.setMsg("???????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("???????????????");
        }
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "doArchive")
    @ResponseBody
    public AjaxJson doArchive(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
            this.tOSendBillService.doArchive(req);
            j.setMsg("???????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("????????????");
        }
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @param tOSendBill
     * @param request
     * @return
     */
    @RequestMapping(params = "doSignIn")
    @ResponseBody
    public AjaxJson doSignIn(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????";
        try {
            tOSendBill = systemService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery cq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            cq.eq("sendReceiveId", tOSendBill.getId());
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
            tOSendBillService.save(tOSendBill);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = "???????????????";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @param tOSendBill
     * @param request
     * @return
     */
    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????";
        try {
            tOSendBill = systemService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
            tOSendBill.setNuclearDraftStatus(SrmipConstants.YES);
            tOSendBillService.updateEntitie(tOSendBill);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            message = "???????????????";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????bid???????????????????????????????????????????????????????????????
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
     * ????????????????????????????????????
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "ifEdit")
    @ResponseBody
    public AjaxJson ifEdit(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String editFlag = "0";//?????????
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = tOSendBillService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            if (!tOSendBill.getArchiveFlag().equals(ReceiveBillConstant.BILL_COMPLETE)
                    && user.getUserName().equals(tOSendBill.getCreateBy())) {//???????????????????????????????????????
                editFlag = "2";//??????????????????
                CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
                rcq.eq("sendReceiveId", tOSendBill.getId());
                rcq.eq("receiveUserid", user.getId());
                rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
                rcq.add();
                List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
                if (rlist.size() > 0) {
                    if (rlist.get(0).getOperateStatus().equals(ReceiveBillConstant.OPERATE_UNTREATED)) {//??????????????????????????????????????????????????????????????????????????????
                        editFlag = "1";//????????????????????????
                    }
                }
            } else {//??????????????????????????????
                if (!tOSendBill.getArchiveFlag().equals(ReceiveBillConstant.BILL_COMPLETE)
                        && req.getParameter("operateStatus").equals(ReceiveBillConstant.OPERATE_UNTREATED)) {//???????????????????????????????????????????????????
                    editFlag = "1";
                }
            }
        }
        j.setObj(editFlag);
        j.setSuccess(true);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "goOperate")
    public ModelAndView goOperate(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = systemService.get(TOSendBillEntity.class, tOSendBill.getId());
            req.setAttribute("tOSendBillPage", tOSendBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOSendBill.getContentFileId());
            req.setAttribute("file", file);
        }
        String rid = req.getParameter("rid");
        TOFlowReceiveLogsEntity receiveLog = this.systemService.get(TOFlowReceiveLogsEntity.class, rid);
        if (receiveLog != null && receiveLog.getSignInTime() == null) {
            receiveLog.setSignInTime(new Date());
            receiveLog.setSignInFlag("1");
            this.systemService.updateEntitie(receiveLog);
        }
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBill-operate");
    }

    /**
     * ???????????????tab???
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "goDetailTab")
    public ModelAndView goDetailTab(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        String regId = req.getParameter("regId");
        CriteriaQuery cq = new CriteriaQuery(TOSendBillEntity.class);
        cq.eq("regId", regId);
        cq.add();
        List<TOSendBillEntity> list = systemService.getListByCriteriaQuery(cq, false);
        req.setAttribute("list", list);
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBill-detailTab");
    }

    /**
     * ???????????????????????????
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = systemService.get(TOSendBillEntity.class, tOSendBill.getId());
            req.setAttribute("tOSendBillPage", tOSendBill);
            TOOfficeOnlineFilesEntity file = systemService.get(TOOfficeOnlineFilesEntity.class,
                    tOSendBill.getContentFileId());
            req.setAttribute("file", file);
        }
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBill-detail");
    }

    @RequestMapping(params = "doNothing")
    @ResponseBody
    public AjaxJson doNothing(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        return j;
    }

    /**
     * ?????????????????????
     * 
     * @param tOSendBill
     * @param request
     * @return
     */
    @RequestMapping(params = "doOperate")
    @ResponseBody
    public AjaxJson doOperate(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String suggestionFlag = request.getParameter("suggestionFlag");
        String suggestionType = request.getParameter("suggestionType");
        String suggestionContent = "";
        message = "???????????????????????????";
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOSendBill.getId());
        rcq.eq("receiveUserid", user.getId());
        rcq.eq("operateStatus", SrmipConstants.NO);
        rcq.eq("validFlag", SrmipConstants.YES);
        rcq.add();
        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        TOSendBillEntity t = tOSendBillService.get(TOSendBillEntity.class, tOSendBill.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOSendBill, t);
            if (SrmipConstants.NO.equals(suggestionFlag)) {
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);
                tOSendBillService.updateEntitie(t);
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
                ucq.eq("userName", tOSendBill.getCreateBy());
                ucq.add();
                TSUser createUser = (TSUser) systemService.getListByCriteriaQuery(ucq, false).get(0);
                tOMessageService.sendMessage(senderId + "," + createUser.getId(), "?????????????????????", "??????",
                        "???????????????[" + t.getSendTitle() + "]?????????\n?????????:" + receive.getReceiveUsername() + "\n????????????:"
                                + suggestionContent + "\n????????????:" + sdf.format(receive.getOperateTime()),
                        receive.getReceiveUserid());
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setObj(t);
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPresent")
    public ModelAndView goPresent(HttpServletRequest req) {
        String ifcirculate = req.getParameter("ifcirculate");
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("ifcirculate", ifcirculate);
        return new ModelAndView("com/kingtake/office/sendbill/presentOrg");
    }

    /**
     * ????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPresentExt")
    public ModelAndView goPresentExt(HttpServletRequest req) {
        //        String suggestionType = req.getParameter("suggestionType");
        String ifcirculate = req.getParameter("ifcirculate");
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("ifcirculate", ifcirculate);
        //        req.setAttribute("suggestionType", suggestionType);
        return new ModelAndView("com/kingtake/office/sendbill/presentOrgExt");
    }

    /**
     * ??????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "circulateOrgExt")
    public ModelAndView circulateOrgExt(HttpServletRequest req) {
        String suggestionType = req.getParameter("suggestionType");
        String ifcirculate = req.getParameter("ifcirculate");
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        req.setAttribute("ifcirculate", ifcirculate);
        req.setAttribute("suggestionType", suggestionType);
        return new ModelAndView("com/kingtake/office/sendbill/circulateOrgExt");
    }

    /**
     * ????????????????????????
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "goInput")
    public ModelAndView goInput(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = tOSendBillService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
            TSUser user = ResourceUtil.getSessionUserName();
            CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
            rcq.eq("sendReceiveId", tOSendBill.getId());
            rcq.eq("receiveUserid", user.getId());
            rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_UNTREATED);
            rcq.eq("validFlag", SrmipConstants.YES);
            rcq.add();
            List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            if (rlist.size() > 0) {
                String suggestionType = "";
                if (rlist.get(0).getSuggestionType().equals(SendBillConstant.OFFICE_REVIEW)) {
                    suggestionType = SendBillConstant.REVIEW_TRANSLATE.get(SendBillConstant.OFFICE_REVIEW);
                } else if (rlist.get(0).getSuggestionType().equals(SendBillConstant.LEADER_REVIEW)) {
                    suggestionType = SendBillConstant.REVIEW_TRANSLATE.get(SendBillConstant.LEADER_REVIEW);
                } else {
                    suggestionType = SendBillConstant.REVIEW_TRANSLATE.get(SendBillConstant.AUDIT_OPINION);
                }
                req.setAttribute("receive", rlist.get(0));
                req.setAttribute("suggestionType", suggestionType);
            }
        }
        return new ModelAndView("com/kingtake/office/sendbill/inputSuggestion");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/office/sendbill/tOSendBillUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOSendBillEntity tOSendBill, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {

        TSUser user = ResourceUtil.getSessionUserName();
        String operateStatus = request.getParameter("operateStatus");
        List<TOSendBillEntity> tOSendBills = systemService
                .getSession()
                .createCriteria(TOSendBillEntity.class)
                .add(Restrictions.eq("registerType", tOSendBill.getRegisterType()))
                .add(Restrictions.or(Restrictions.and(
                        Restrictions.eq("createBy", user.getUserName()),
                        (SrmipConstants.NO.equals(operateStatus) ? Restrictions.ne("archiveFlag",
                                ReceiveBillConstant.BILL_COMPLETE) : Restrictions.eq("archiveFlag",
                                ReceiveBillConstant.BILL_COMPLETE))), Restrictions.and((SrmipConstants.NO
                        .equals(operateStatus) ? Restrictions.eq("nuclearDraftStatus", SrmipConstants.NO)
                        : Restrictions.ne("nuclearDraftStatus", SrmipConstants.NO)), Restrictions.eq(
                        "nuclearDraftUserid", user.getId())))).list();

        //List<TOSendBillEntity> tOSendBills = this.tOSendBillService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "???????????????");
        modelMap.put(NormalExcelConstants.CLASS, TOSendBillEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tOSendBills);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TOSendBillEntity tOSendBill, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "???????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TOSendBillEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// ????????????????????????
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TOSendBillEntity> listTOSendBillEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TOSendBillEntity.class, params);
                for (TOSendBillEntity tOSendBill : listTOSendBillEntitys) {
                    tOSendBillService.save(tOSendBill);
                }
                j.setMsg("?????????????????????");
            } catch (Exception e) {
                j.setMsg("?????????????????????");
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

    /**
     * ??????????????????
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
     * 
     * @param rid
     *            ?????????id
     * @param uid
     *            ??????id
     * @return false-????????? true-?????????
     */
    public Boolean ifSignIn(String rid, String uid) {
        //        String sql = "select t.id from T_O_FLOW_RECEIVE_LOGS t where t.send_receive_id='" + rid
        //                + "' and t.receive_userid='" + uid + "' and t.sign_in_flag !='1'";
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
     * ??????????????????
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "getStepList")
    public ModelAndView getStepList(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        tOSendBill = tOSendBillService.getEntity(TOSendBillEntity.class, tOSendBill.getId());
        CriteriaQuery scq = new CriteriaQuery(TOFlowSendLogsEntity.class);
        scq.eq("sendReceiveId", tOSendBill.getId());
        scq.addOrder("operateDate", SortDirection.asc);
        scq.add();
        List<TOFlowSendLogsEntity> slist = systemService.getListByCriteriaQuery(scq, false);
        
        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        rcq.eq("sendReceiveId", tOSendBill.getId());
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
                operate = "??????";
            }else{
                operate = "??????";
            }
        }
        flowMap.put("operateTip", operate);
        flowMap.put("receiveTime", recLog.getSignInTime());
        stepList.add(flowMap);
        return new ModelAndView("com/kingtake/office/receivebill/tOReceiveStepBillList", "stepList", stepList);
    }
    
  //?????????????????????????????????
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
    
    //?????????????????????????????????
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
     * ?????????????????????
     * 
     * @param tOSendBill
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "exportDocByT")
    public String exportDocByT(TOSendBillEntity tOSendBill, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = systemService.get(TOSendBillEntity.class, tOSendBill.getId());
        }
        //        CriteriaQuery rcq = new CriteriaQuery(TOFlowReceiveLogsEntity.class);
        //        rcq.eq("sendReceiveId", tOSendBill.getId());
        //        rcq.eq("operateStatus", ReceiveBillConstant.OPERATE_TREATED);
        //        rcq.eq("validFlag", SrmipConstants.YES);
        //        //??????????????????????????????
        //        PropertiesUtil prop = new PropertiesUtil("sendReceiveBill.properties");
        //        String orderBy = prop.readProperty("sendBill.receive.orderBy");
        //        String[] orderBys = orderBy.split(",");
        //        String fieldName = "";
        //        SortDirection direction = null;
        //        for (int i = 0; i < orderBys.length; i++) {
        //            fieldName = orderBys[i].split("\\.")[0];
        //            if (orderBys[i].split("\\.")[1].equals("desc")) {
        //                direction = SortDirection.desc;
        //            } else {
        //                direction = SortDirection.asc;
        //            }
        //            rcq.addOrder(fieldName, direction);
        //        }
        //
        //        rcq.add();
        //        List<TOFlowReceiveLogsEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
        //        List<TOFlowReceiveLogsEntity> llist = new ArrayList<TOFlowReceiveLogsEntity>();
        //        List<TOFlowReceiveLogsEntity> olist = new ArrayList<TOFlowReceiveLogsEntity>();
        //        List<TOFlowReceiveLogsEntity> dlist = new ArrayList<TOFlowReceiveLogsEntity>();
        //        if (rlist.size() > 0) {
        //            for (TOFlowReceiveLogsEntity receive : rlist) {
        //                if (receive.getSuggestionType().equals(SendBillConstant.LEADER_REVIEW)) {
        //                    llist.add(receive);
        //                } else if (receive.getSuggestionType().equals(SendBillConstant.OFFICE_REVIEW)) {
        //                    olist.add(receive);
        //                } else {
        //                    dlist.add(receive);
        //                }
        //            }
        //        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(tOSendBill.getFileNumPrefix())) {
            map.put("qz", tOSendBill.getFileNumPrefix());
        }
        if (StringUtil.isNotEmpty(tOSendBill.getSendYear())) {
            map.put("nd", tOSendBill.getSendYear());
        }
        if (StringUtil.isNotEmpty(tOSendBill.getSendNum())) {
            map.put("wh", tOSendBill.getSendNum());
        }
        if (StringUtil.isNotEmpty(tOSendBill.getSendUnit())) {
            map.put("dw", tOSendBill.getSendUnit());
        }
        if (StringUtil.isNotEmpty(tOSendBill.getSendTypeCode())) {
            map.put("wz", ConvertDicUtil.getConvertDic("1", "GWZL", tOSendBill.getSendTypeCode()));
        }

        if (StringUtil.isNotEmpty(ReceiveBillConstant.GRADE_TRANSLATE.get(tOSendBill.getSecrityGrade()))) {
            map.put("mj", ReceiveBillConstant.GRADE_TRANSLATE.get(tOSendBill.getSecrityGrade()));
        }
        if (StringUtil.isNotEmpty(tOSendBill.getPrintNum())) {
            map.put("ys", tOSendBill.getPrintNum());
        }
        if (StringUtil.isNotEmpty(tOSendBill.getSendTitle())) {
            map.put("gwbt", tOSendBill.getSendTitle());
        }
        //????????????????????????????????????
        WordImageEntity presidentEntity = getSignatureBlob(tOSendBill, "sendBillPresident", request, 580, 180);
        if (presidentEntity != null) {
            map.put("xszps", presidentEntity);
        }
        //???????????????????????????????????????
        WordImageEntity departmentLeaderEntity = getSignatureBlob(tOSendBill, "sendBillDepartmentLeader", request, 580,
                180);
        if (departmentLeaderEntity != null) {
            map.put("jgbldps", departmentLeaderEntity);
        }
        //?????????????????????????????????
        WordImageEntity hgrEntity = getSignatureBlob(tOSendBill, "sendBillNuclearDraftUsername", request, 80, 40);
        if (hgrEntity != null) {
            map.put("hgr", hgrEntity);
        }
        //        if (llist.size() > 0) {
        //            StringBuffer sb = new StringBuffer();
        //            for (TOFlowReceiveLogsEntity r : llist) {
        //                sb.append(r.getReceiveUsername() + ":" + r.getSuggestionContent());
        //                sb.append("\n");
        //            }
        //            map.put("xszps", sb.toString());
        //        }
        //        if (olist.size() > 0) {
        //            StringBuffer sb = new StringBuffer();
        //            for (TOFlowReceiveLogsEntity r : olist) {
        //                sb.append(r.getReceiveUsername() + ":" + r.getSuggestionContent());
        //                sb.append("\n");
        //            }
        //            map.put("jgbldps", sb.toString());
        //        }
        //        if (dlist.size() > 0) {
        //            StringBuffer sb = new StringBuffer();
        //            for (TOFlowReceiveLogsEntity r : dlist) {
        //                if (sb.length() > 0) {
        //                    sb.append(",");
        //                }
        //                sb.append(r.getReceiveUsername());
        //            }
        //            map.put("hgr", sb.toString());
        //        }

        if (StringUtil.isNotEmpty(tOSendBill.getDraftExplain())) {
            map.put("ngsm", tOSendBill.getDraftExplain());
        }
        if (StringUtil.isNotEmpty(tOSendBill.getDraftDate())) {
            Calendar time = Calendar.getInstance();
            time.clear();
            time.setTime(tOSendBill.getDraftDate());
            map.put("n", time.get(Calendar.YEAR));
            map.put("y", time.get(Calendar.MONTH) + 1);
            map.put("r", time.get(Calendar.DAY_OF_MONTH));
        }
        if (StringUtil.isNotEmpty(tOSendBill.getUndertakeUnitName())) {
            map.put("cbdw", tOSendBill.getUndertakeUnitName());
        }
        /*
         * if (tOSendBill.getNuclearDraftStatus().equals(SrmipConstants.YES)) { map.put("hgr",
         * tOSendBill.getNuclearDraftUsername()); }
         */
        if (StringUtil.isNotEmpty(tOSendBill.getContactName())) {
            map.put("lxr", tOSendBill.getContactName());
        }
        if (StringUtil.isNotEmpty(tOSendBill.getContactPhone())) {
            map.put("t", tOSendBill.getContactPhone());
        }
        modelMap.put(TemplateWordConstants.FILE_NAME, "???????????????(" + tOSendBill.getSendTitle() + ")");
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        if (request.getParameter("flag").equals("1")) {//????????????1?????????????????????
            modelMap.put(TemplateWordConstants.URL, "export/template/fwcpd1.docx");
        } else {
            modelMap.put(TemplateWordConstants.URL, "export/template/fwcpd.docx");
        }
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;

    }

    /**
     * ??????????????????
     * 
     * @param tOSendBill
     * @param fieldName
     * @return
     */
    private WordImageEntity getSignatureBlob(TOSendBillEntity tOSendBill, String fieldName, HttpServletRequest request,
            int width, int height) {
        WordImageEntity entity = null;
        try {
            String parentPath = new File(request.getRealPath("/")).getParent();
            String fileName = tOSendBill.getId() + "_" + fieldName + ".jpg";
            File realFile = new File(parentPath + "/signature/" + fileName);
            if (realFile.exists()) {
                FileInputStream fos = new FileInputStream(realFile);
                byte[] mFileBody = IOUtils.toByteArray(fos);
                fos.close();
                entity = new WordImageEntity(mFileBody, width, height);
            }
        } catch (Exception e) {
            throw new BusinessException("????????????????????????", e);
        }
        return entity;
    }

    /**
     * ??????????????????????????????portal????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "sendReceiveBillListToMeForPortal")
    public ModelAndView sendReceiveBillListToMe(HttpServletRequest request) {
        request.setAttribute("operateStatus", request.getParameter("operateStatus"));
        request.setAttribute("registerType", request.getParameter("registerType"));
        return new ModelAndView("com/kingtake/office/sendbill/sendReceiveBillListToMeForPortal");
    }

    /**
     * ?????????portal????????????
     * 
     * @param tOSendBill
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "sendReceiveDatagridPortal")
    public void sendReceiveDatagridPortal(HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int start = (page - 1) * rows + 1;
        int end = (page) * rows;
        TSUser user = ResourceUtil.getSessionUserName();
        TSDepart depart = user.getCurrentDepart();
        Map<String, String> param = new HashMap<String, String>();
        param.put("userId", user.getId());
        //param.put("departId", depart.getId());
        List<Map<String, String>> results = this.tOSendBillService.getPortalList(param, start, end);
        Integer count = this.tOSendBillService.getPortalCount(param);
        dataGrid.setResults(results);
        dataGrid.setTotal(count);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ??????????????????
     * 
     * @param tOApproval
     * @param req
     * @return
     */
    @RequestMapping(params = "goSendReceiveReg")
    public ModelAndView goSendReceiveReg(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOSendBill.getId())) {
            tOSendBill = systemService.get(TOSendBillEntity.class, tOSendBill.getId());
            String regId = tOSendBill.getRegId();
            TOSendReceiveRegEntity sendReceiveReg = this.systemService.get(TOSendReceiveRegEntity.class, regId);
            req.setAttribute("tOSendReceiveRegPage", sendReceiveReg);
        }
        return new ModelAndView("com/kingtake/office/sendReceive/tOSendReceiveReg-update");
    }

    /**
     * ????????????????????????
     * 
     * @param tOSendBill
     * @param req
     * @return
     */
    @RequestMapping(params = "getFileNum")
    @ResponseBody
    public JSONObject getFileNum(TOSendBillEntity tOSendBill, HttpServletRequest req) {
        String regType = req.getParameter("regType");
        String undertakeDeptId = req.getParameter("undertakeDeptId");
        String isUnion = req.getParameter("isUnion");
        TOSendReceiveRegEntity reg = new TOSendReceiveRegEntity();
        reg.setRegType(regType);
        reg.setUndertakeDeptId(undertakeDeptId);
        reg.setIsUnion(isUnion);
        Map<String, String> fileNumMap = this.tOSendBillService.getFileNum(reg);
        JSONObject json = (JSONObject) JSON.toJSON(fileNumMap);
        return json;
    }

    /**
     * ???????????????????????????
     * 
     * @param tOSendBill
     * @param request
     * @return
     */
    @RequestMapping(params = "passReturn")
    @ResponseBody
    public AjaxJson passReturn(TOSendBillEntity tOSendBill, HttpServletRequest request) {
        TOSendBillEntity t = tOSendBillService.get(TOSendBillEntity.class, tOSendBill.getId());
        AjaxJson j = new AjaxJson();
        String rid = request.getParameter("rid");
        String opinion = request.getParameter("opinion");
        String receiverid = t.getContactId();
        String realName = t.getContactName();
        String departId = t.getUndertakeUnitId();
        String departName = t.getUndertakeUnitName();
        if (StringUtils.isNotEmpty(t.getContactId()) && t.getContactId().contains(",")) {
            receiverid = getFirst(t.getContactId());
            realName = getFirst(t.getContactName());
            departId = getFirst(t.getUndertakeUnitId());
            departName = getFirst(t.getUndertakeUnitName());
        }
        String leaderReview = request.getParameter("leaderReview");
        message = "??????????????????????????????";
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tOSendBill, t);
            TSUser user = ResourceUtil.getSessionUserName();
            if (ReceiveBillConstant.AUDIT_PASS.equals(opinion)) {//??????
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
                send.setSendReceiveId(tOSendBill.getId());
                send.setOperateIp(ip);
                send.setOperateDepartid(user.getCurrentDepart().getId());
                send.setOperateDepartname(user.getCurrentDepart().getDepartname());
                send.setSenderTip("?????????" + leaderReview);
                systemService.save(send);
                TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
                receive.setToFlowSendId(send.getId());
                receive.setSendReceiveId(tOSendBill.getId());
                receive.setReceiveUserid(receiverid);
                receive.setReceiveDepartid(departId);
                receive.setValidFlag(SrmipConstants.YES);//????????????
                receive.setReceiveDepartname(departName);
                receive.setReceiveUsername(realName);
                receive.setOperateStatus(ReceiveBillConstant.OPERATE_UNTREATED);
                systemService.save(receive);
                tOMessageService.sendMessage(receiverid, "???????????????????????????", "??????", "????????????????????????????????????\n???????????????[" + t.getSendTitle()
                        + "]\n??????????????????->?????????????????????", send.getOperateUserid());
            } else {//??????
                t.setBackUserid(user.getId());
                t.setBackUsername(user.getRealName());
                t.setArchiveFlag(ReceiveBillConstant.BILL_REBUT);//???????????????????????????
                systemService.updateEntitie(t);
                tOMessageService.sendMessage(t.getContactId(), "?????????[" + t.getSendTitle() + "]????????????", "??????",
                        "????????????" + user.getRealName() + "\n???????????????" + leaderReview, user.getId());
                TOSendReceiveRegEntity reg = systemService.get(TOSendReceiveRegEntity.class, t.getRegId());
                reg.setGenerationFlag(ReceiveBillConstant.BILL_REBUT);
                systemService.updateEntitie(reg);
            }

            //?????????????????????????????????????????????
            TOFlowReceiveLogsEntity receiveLog = systemService.get(TOFlowReceiveLogsEntity.class, rid);
            receiveLog.setOperateStatus(ReceiveBillConstant.OPERATE_TREATED);
            receiveLog.setOperateTime(new Date());
            receiveLog.setSuggestionCode(opinion);
            systemService.updateEntitie(receiveLog);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????
     * 
     * @param name
     * @return
     */
    private String getFirst(String name) {
        String res = "";
        if (StringUtils.isNotEmpty(name)) {
            String[] names = name.split(",");
            if (names.length > 0) {
                res = names[0];
            }
        }
        return res;
    }
}
