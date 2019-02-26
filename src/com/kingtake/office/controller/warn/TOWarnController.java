package com.kingtake.office.controller.warn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.entity.warn.TOWarnReceiveEntity;
import com.kingtake.office.enumer.WarnFrequencyEnum;
import com.kingtake.office.enumer.WarnStatusEnum;
import com.kingtake.office.enumer.WarnTypeEnum;
import com.kingtake.office.enumer.WarnWayEnum;
import com.kingtake.office.page.warn.TOWarnPage;
import com.kingtake.office.service.warn.TOWarnServiceI;

/**
 * @Title: Controller
 * @Description: 公用提醒信息表
 * @author onlineGenerator
 * @date 2015-07-15 15:47:17
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOWarnController")
public class TOWarnController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TOWarnController.class);

    @Autowired
    private TOWarnServiceI tOWarnService;
    @Autowired
    private SystemService systemService;

    /**
     * 公用提醒信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tOWarn")
    public ModelAndView tOWarn(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/warn/tOWarnList");
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
    public void datagrid(TOWarnEntity tOWarn, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TOWarnEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOWarn);
        try {
            //自定义追加查询条件
        	cq.addOrder("warnStatus", SortDirection.asc);
        	cq.addOrder("warnBeginTime", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("warnStatus", SortDirection.asc);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tOWarnService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOWarnEntity tOWarn, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TOWarnEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOWarn, request.getParameterMap());
        List<TOWarnEntity> tPortalLayouts = this.tOWarnService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "公共信息提醒列表");
        modelMap.put(NormalExcelConstants.CLASS, TOWarnEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("公共信息提醒列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPortalLayouts);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 删除公用提醒信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TOWarnEntity tOWarn, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tOWarn = systemService.getEntity(TOWarnEntity.class, tOWarn.getId());
        String message = "公用提醒信息表删除成功";
        try {
            tOWarnService.delete(tOWarn);
        } catch (Exception e) {
            e.printStackTrace();
            message = "公用提醒信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 删除接收人信息
     * 
     * @return
     */
    @RequestMapping(params = "doDelDetail")
    @ResponseBody
    public AjaxJson doDelDetail(TOWarnReceiveEntity warnReceive, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        warnReceive = systemService.getEntity(TOWarnReceiveEntity.class, warnReceive.getId());
        String message = "公用提醒接收人表删除成功";
        try {
            systemService.delete(warnReceive);
        } catch (Exception e) {
            e.printStackTrace();
            message = "公用提醒接收人表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除公用提醒信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "公用提醒信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TOWarnEntity tOWarn = systemService.getEntity(TOWarnEntity.class, id);
                tOWarnService.delMain(tOWarn);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "公用提醒信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新公用提醒信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TOWarnEntity tOWarn, TOWarnPage tOWarnPage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        String message = null;
        try {
            if (StringUtils.isNotEmpty(tOWarn.getId())) {
                message = "更新成功";
                TOWarnEntity t = this.tOWarnService.get(TOWarnEntity.class, tOWarn.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOWarn, t);
                tOWarnService.updateEntitie(t);
            } else {
                message = "新增成功";
                TSUser user = ResourceUtil.getSessionUserName();
                TOWarnReceiveEntity receiveEntity = new TOWarnReceiveEntity();
                receiveEntity.setReceiveUserid(user.getId());
                receiveEntity.setReceiveUsername(user.getRealName());
                List<TOWarnReceiveEntity> receiveList = new ArrayList<TOWarnReceiveEntity>();
                receiveList.add(receiveEntity);
                if (StringUtils.isNotEmpty(tOWarn.getReceiveUserids())) {
                    String[] userIds = tOWarn.getReceiveUserids().split(",");
                    for (String userId : userIds) {
                        TOWarnReceiveEntity receiveTmp = new TOWarnReceiveEntity();
                        TSUser userTmp = this.systemService.get(TSUser.class, userId);
                        if (userTmp != null) {
                            receiveTmp.setReceiveUserid(userTmp.getId());
                            receiveTmp.setReceiveUsername(userTmp.getRealName());
                            receiveList.add(receiveTmp);
                        }
                    }
                }

                tOWarnService.addMain(tOWarn, receiveList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "新增/更新公用提醒信息表失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新公用提醒信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdateDetail")
    @ResponseBody
    public AjaxJson doAddUpdateDetail(TOWarnReceiveEntity tOWarnReceive, TOWarnPage tOWarnPage,
            HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = null;
        try {
            String warnId = request.getParameter("warnId");
            if (StringUtils.isNotEmpty(warnId)) {
                TOWarnEntity warnEntity = this.tOWarnService.get(TOWarnEntity.class, warnId);
                tOWarnReceive.setToWarn(warnEntity);
            }
            if (StringUtils.isNotEmpty(tOWarnReceive.getId())) {
                message = "更新成功";
                TOWarnReceiveEntity t = this.tOWarnService.get(TOWarnReceiveEntity.class, tOWarnReceive.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOWarnReceive, t);
                tOWarnService.updateEntitie(t);
            } else {
                message = "新增成功";
                String receiveUserids = request.getParameter("receiveUserids");
                String receiveUsernames = request.getParameter("receiveUsernames");
                if (StringUtils.isNotEmpty(receiveUserids) && StringUtils.isNotEmpty(receiveUsernames)) {
                    String[] userIds = receiveUserids.split(",");
                    String[] userNames = receiveUsernames.split(",");
                    for (int i = 0; i < userIds.length; i++) {
                        TOWarnReceiveEntity tmp = new TOWarnReceiveEntity();
                        MyBeanUtils.copyBeanNotNull2Bean(tOWarnReceive, tmp);
                        tmp.setReceiveUserid(userIds[i]);
                        tmp.setReceiveUsername(userNames[i]);
                        tOWarnService.save(tmp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "新增/更新公用提醒接收人表失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 公用提醒信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TOWarnEntity tOWarn, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOWarn.getId())) {
            tOWarn = tOWarnService.getEntity(TOWarnEntity.class, tOWarn.getId());
        } else {
            Calendar cal = Calendar.getInstance();
            Date begin = cal.getTime();
            cal.add(Calendar.DAY_OF_YEAR, 7);
            Date end = cal.getTime();
            tOWarn.setWarnBeginTime(begin);
            tOWarn.setWarnEndTime(end);
            tOWarn.setWarnType(WarnTypeEnum.PERSONAL_MATERS.getValue());
            tOWarn.setWarnStatus(WarnStatusEnum.UNFINISHIED.getValue());
            tOWarn.setWarnFrequency(WarnFrequencyEnum.ONCE.getValue());
            tOWarn.setWarnWay(WarnWayEnum.MESSAGE.getValue());
        }
        req.setAttribute("tOWarnPage", tOWarn);
        return new ModelAndView("com/kingtake/office/warn/tOWarn-update");
    }

    /**
     * 公用提醒信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goFinish")
    public ModelAndView goFinish(TOWarnReceiveEntity tOWarnReceive, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tOWarnReceive.getId())) {
            tOWarnReceive = tOWarnService.getEntity(TOWarnReceiveEntity.class, tOWarnReceive.getId());
            req.setAttribute("tOWarnReceivePage", tOWarnReceive);
        }
        String warnId = req.getParameter("warnId");
        if (StringUtils.isNotEmpty(warnId)) {
            req.setAttribute("warnId", warnId);
        }
        return new ModelAndView("com/kingtake/office/warn/tOWarnReceive-update");
    }

    /**
     * 公用提醒信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddReceiveUser")
    public ModelAndView goAddReceiveUser(TOWarnReceiveEntity tOWarnReceive, HttpServletRequest req) {
        String warnId = req.getParameter("warnId");
        if (StringUtils.isNotEmpty(warnId)) {
            req.setAttribute("warnId", warnId);
            CriteriaQuery cq = new CriteriaQuery(TOWarnReceiveEntity.class);
            cq.eq("toWarn.id", warnId);
            cq.add();
            List<TOWarnReceiveEntity> receiveList = this.systemService.getListByCriteriaQuery(cq, false);
            if (receiveList.size() > 0) {
                StringBuffer idSb = new StringBuffer();
                StringBuffer nameSb = new StringBuffer();
                for (TOWarnReceiveEntity entity : receiveList) {
                    idSb.append(entity.getReceiveUserid());
                    idSb.append(",");
                    nameSb.append(entity.getReceiveUsername());
                    nameSb.append(",");
                }
                req.setAttribute("receiveUserIds", idSb.toString());
                req.setAttribute("receiveUserNames", nameSb.toString());
            } else {
                TSUser user = ResourceUtil.getSessionUserName();
                req.setAttribute("receiveUserIds", user.getId());
                req.setAttribute("receiveUserNames", user.getUserName());
            }
        }
        return new ModelAndView("com/kingtake/office/warn/tOWarnReceiveAddReceiveUser");
    }

    /**
     * 公用提醒信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "doAddReceiveUser")
    @ResponseBody
    public AjaxJson doAddReceiveUser(TOWarnReceiveEntity tOWarnReceive, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String msg = "添加公共提醒接收成员成功";
        String warnId = req.getParameter("warnId");
        try {
            if (StringUtils.isNotEmpty(warnId)) {
                TOWarnEntity entity = this.systemService.get(TOWarnEntity.class, warnId);
                List<TOWarnReceiveEntity> receiveList = new ArrayList<TOWarnReceiveEntity>();
                String userIds = req.getParameter("receiveUserids");
                String userNames = req.getParameter("receiveUsernames");
                if (StringUtils.isNotEmpty(userIds)) {
                    String[] userIdsArr = userIds.split(",");
                    String[] userNamesArr = userNames.split(",");
                    for (int i = 0; i < userIdsArr.length; i++) {
                        TOWarnReceiveEntity tmp = new TOWarnReceiveEntity();
                        tmp.setReceiveUserid(userIdsArr[i]);
                        tmp.setReceiveUsername(userNamesArr[i]);
                        receiveList.add(tmp);
                    }
                }
                this.tOWarnService.updateMain(entity, receiveList);
            }
        } catch (Exception e) {
            logger.error("添加公共提醒接收成员失败", e);
            msg = "添加公共提醒接收成员失败";
        }
        j.setMsg(msg);
        return j;
    }

    /**
     * 加载明细列表[公用信息接收人表]
     * 
     * @return
     */
    @RequestMapping(params = "datagridReceiveList")
    public void datagridReceiveList(TOWarnEntity tOWarn, HttpServletRequest req, HttpServletResponse response,
            DataGrid datagrid) {
        //获取参数
        Object id0 = tOWarn.getId();
        //===================================================================================
        //查询-公用信息接收人表
        String hql0 = "from TOWarnReceiveEntity where 1 = 1 AND t_O_WARN_ID = ? ";
        try {
            List<TOWarnReceiveEntity> tOWarnReceiveEntityList = systemService.findHql(hql0, id0);
            datagrid.setResults(tOWarnReceiveEntityList);
            datagrid.setRows(tOWarnReceiveEntityList.size());
            TagUtil.datagrid(response, datagrid);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * 跳转到接收人列表
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveList")
    public ModelAndView goReceiveList(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            request.setAttribute("warnId", id);
        }
        return new ModelAndView("com/kingtake/office/warn/tOWarnReceiveList");
    }

    /**
     * 完成提醒
     * 
     * @param request
     */
    @RequestMapping(params = "finishWarn")
    @ResponseBody
    public AjaxJson finishWarn(HttpServletRequest request, TOWarnReceiveEntity receiveEntity) {
        String msg = "完成提醒成功！";
        AjaxJson json = new AjaxJson();
        try {
            this.tOWarnService.finishWarn(receiveEntity);
            json.setSuccess(true);
        } catch (Exception e) {
            logger.error("完成提醒失败", e);
            msg = "完成提醒失败";
            json.setSuccess(false);
        }
        json.setMsg(msg);
        return json;
    }

    /**
     * 公共提醒界面
     * 
     * @param request
     * @param receiveEntity
     * @return
     */
    @RequestMapping(params = "goCommonWarn")
    public ModelAndView goCommonWarn(HttpServletRequest request) {
        Calendar cal = Calendar.getInstance();
        Date begin = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, 7);
        Date end = cal.getTime();
        request.setAttribute("begin", begin);
        request.setAttribute("end", end);
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("receiveUserIds", user.getId());
        request.setAttribute("receiveUserNames", user.getRealName());
        String type = request.getParameter("type");
        if (StringUtils.isNotEmpty(type)) {
            request.setAttribute("type", type);
        }
        return new ModelAndView("com/kingtake/office/warn/commonWarn");
    }
}
