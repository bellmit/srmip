package com.kingtake.project.controller.resultreward;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
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

import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.appraisal.TBAppraisalMemberEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.resultreward.TBResultRewardEntity;
import com.kingtake.project.entity.resultreward.TBResultRewardFinishUserEntity;
import com.kingtake.project.service.resultreward.TBResultRewardServiceI;

/**
 * @Title: Controller
 * @Description: 成果奖励基本信息表
 * @author onlineGenerator
 * @date 2015-12-05 10:06:45
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBResultRewardController")
public class TBResultRewardController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBResultRewardController.class);

    @Autowired
    private TBResultRewardServiceI tBResultRewardService;
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
     * 成果奖励基本信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBResultReward")
    public ModelAndView tBResultReward(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            return new ModelAndView("com/kingtake/project/resultreward/tBResultRewardList");
        }
        //处理审批
        //0：未处理；1：已处理
        String type = request.getParameter("type");
        if (StringUtil.isNotEmpty(type)) {
            if ("0".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.NO);
            } else if ("1".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.YES);
            }
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_RECEIVE);
            request.setAttribute("YES", SrmipConstants.YES);
            request.setAttribute("NO", SrmipConstants.NO);
            return new ModelAndView("com/kingtake/project/resultreward/tBResultRewardList-receive");
        }
        return new ModelAndView("common/404.jsp");
    }

    /**
     * 项目基本信息中成果奖励列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBResultRewardInfo")
    public ModelAndView tBResultRewardInfo(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
            request.setAttribute("projectName", project.getProjectName());
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
        }
        return new ModelAndView("com/kingtake/project/resultreward/tBResultRewardListInfo");
    }

    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/resultreward/tBResultReward-receiveTab");
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
    public void datagrid(TBResultRewardEntity tBResultReward, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {
            String projectId = request.getParameter("projectId");
            CriteriaQuery cq = new CriteriaQuery(TBResultRewardEntity.class, dataGrid);
            //查询条件组装器
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBResultReward,
                    request.getParameterMap());
            try {
                //自定义追加查询条件
                cq.or(Restrictions.eq("mainSurceId", projectId),
                        Restrictions.like("sourceProjectIds", "%" + projectId + "%"));
                //                cq.like("sourceProjectIds", "%" + projectId + "%");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            cq.add();
            this.tBResultRewardService.getDataGridReturn(cq, true);
        } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批
            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();//获取参数

            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            StringBuffer resultSql = new StringBuffer();
            resultSql.append("SELECT APPR.ID AS APPR_ID,APPR.SOURCE_PROJECT_IDS AS SOURCEPROJECTIDS,");
            resultSql.append("APPR.FINISH_USERID AS FINISHUSERID,APPR.FINISH_USERNAME AS FINISHUSERNAME,");
            resultSql.append("APPR.FINISH_DEPARTID AS FINISHDEPARTID,APPR.FINISH_DEPARTNAME AS FINISHDEPARTNAME,");
            resultSql.append("APPR.SUMMARY,APPR.INNOVATION_POINT AS INNOVATIONPOINT,APPR.REWARD_NAME AS REWARDNAME,");
            resultSql.append("APPR.REPORT_REWARD_LEVEL AS REPORTREWARDLEVEL,APPR.REPORT_LEVEL AS REPORTLEVEL,");
            resultSql.append("APPR.TASK_SOURCE AS TASKSOURCE,APPR.INVESTED_AMOUNT AS INVESTEDAMOUNT,");
            resultSql.append("APPR.BEGIN_DATE AS BEGINDATE,APPR.END_DATE AS ENDDATE,APPR.CONTACTS AS CONTACTS,");
            resultSql.append("APPR.CONTACT_PHONE AS CONTACTPHONE,APPR.HGD_DEVOTE AS HGDDEVOTE,");

            resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
            resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
            resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

            String temp = "FROM T_B_RESULT_REWARD APPR, T_PM_APPR_SEND_LOGS SEND, "
                    + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                    + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                    + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n"
                    + "AND RECEIVE.RECEIVE_USERID = ?  ";

            if (SrmipConstants.YES.equals(operateStatus)) {
                //已处理
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                //未处理：需要是有效的
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                        + SrmipConstants.YES;
            }

            String rewardName = request.getParameter("rewardName");
            if (StringUtil.isNotEmpty(rewardName)) {
                temp += " AND APPR.REWARD_NAME LIKE '%" + rewardName + "%'";
            }
            //
            //            String contractName = request.getParameter("contract.name");
            //            if (StringUtil.isNotEmpty(contractName)) {
            //                temp += " AND APPR.CONTRACT_NAME LIKE '%" + contractName + "%'";
            //            }

            temp += "ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
            String[] params = new String[] { user.getId() };

            List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                    dataGrid.getPage(), dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除成果奖励基本信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBResultRewardEntity tBResultReward, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBResultReward = systemService.getEntity(TBResultRewardEntity.class, tBResultReward.getId());
        message = "成果奖励基本信息表删除成功";
        try {
            tBResultRewardService.deleteAddAttach(tBResultReward);
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果奖励基本信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除成果奖励基本信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成果奖励基本信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBResultRewardEntity tBResultReward = systemService.getEntity(TBResultRewardEntity.class, id);
                tBResultRewardService.deleteAddAttach(tBResultReward);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果奖励基本信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加成果奖励基本信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBResultRewardEntity tBResultReward, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成果奖励基本信息表添加成功";
        try {
            tBResultReward.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
            tBResultRewardService.save(tBResultReward);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果奖励基本信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新成果奖励基本信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBResultRewardEntity tBResultReward, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成果奖励基本信息表更新成功";
        try {
            if (StringUtil.isEmpty(tBResultReward.getId())) {
                tBResultReward.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
                systemService.save(tBResultReward);
            } else {
                TBResultRewardEntity t = tBResultRewardService.get(TBResultRewardEntity.class, tBResultReward.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tBResultReward, t);
                tBResultRewardService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "成果奖励基本信息表更新失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "getFinishUserByReward")
    @ResponseBody
    public JSONObject getMemberListByApply(TBResultRewardEntity tBResultReward, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject json = new JSONObject();
        CriteriaQuery cq = new CriteriaQuery(TBResultRewardFinishUserEntity.class);
        cq.eq("rewardId", tBResultReward.getId());
        cq.add();
        List<TBResultRewardFinishUserEntity> flist = systemService.getListByCriteriaQuery(cq, false);
        if (flist.size() > 0) {
            json.put("rows", flist);
            json.put("total", flist.size());
        } else {
            json.put("rows", new ArrayList<TBAppraisalMemberEntity>());
            json.put("total", 0);
        }
        return json;
    }

    /**
     * 成果奖励基本信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBResultRewardEntity tBResultReward, HttpServletRequest req) {

        String projectId = req.getParameter("projectId");
        TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
        tBResultReward.setRewardName(project.getProjectName());
        tBResultReward.setMainSurce(project.getProjectName());
        tBResultReward.setMainSurceId(projectId);
        //tBResultReward.setProjectType(project.getProjectType().getId());
        
        if (StringUtil.isNotEmpty(tBResultReward.getId())) {
            tBResultReward = tBResultRewardService.getEntity(TBResultRewardEntity.class, tBResultReward.getId());
        }
        if (StringUtils.isEmpty(tBResultReward.getAttachmentCode())) {
            tBResultReward.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tBResultReward.getAttachmentCode(),
                    "");
            tBResultReward.setAttachments(fileList);
        }
        req.setAttribute("tBResultRewardPage", tBResultReward);
        req.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/resultreward/tBResultReward-update");
    }
    

    /**
     * 成果奖励基本信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBResultRewardEntity tBResultReward, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBResultReward.getId())) {
            tBResultReward = tBResultRewardService.getEntity(TBResultRewardEntity.class, tBResultReward.getId());
            if (StringUtils.isEmpty(tBResultReward.getAttachmentCode())) {
                tBResultReward.setAttachmentCode(UUIDGenerator.generate());//自动生成编码
            } else {
                List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(
                        tBResultReward.getAttachmentCode(), "");
                tBResultReward.setAttachments(fileList);
            }
            req.setAttribute("tBResultRewardPage", tBResultReward);
        }
        return new ModelAndView("com/kingtake/project/resultreward/tBResultReward-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/resultreward/tBResultRewardUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBResultRewardEntity tBResultReward, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBResultRewardEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBResultReward,
                request.getParameterMap());
        List<TBResultRewardEntity> tBResultRewards = this.tBResultRewardService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "成果奖励基本信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBResultRewardEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("成果奖励基本信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName() + "  导出时间：" + DateUtils.formatDate2(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBResultRewards);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBResultRewardEntity tBResultReward, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "成果奖励基本信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBResultRewardEntity.class);
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
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TBResultRewardEntity> listTBResultRewardEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBResultRewardEntity.class, params);
                for (TBResultRewardEntity tBResultReward : listTBResultRewardEntitys) {
                    tBResultRewardService.save(tBResultReward);
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
    
    /**
     * 导出word
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportWord")
    public String exportWord(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        TBResultRewardEntity resultReward = this.systemService.get(TBResultRewardEntity.class, id);
        map.put("rewardName", resultReward.getRewardName() == null ? "" : resultReward.getRewardName());
        map.put("finishUsername", resultReward.getFinishUsername() == null ? "" : resultReward.getFinishUsername());
        String beginTime = resultReward.getBeginDate();
        String endTime = resultReward.getEndDate();
        String beginTimeStr = "";
        String endTimeStr = "";
        if(StringUtils.isNotEmpty(beginTime)){
            beginTimeStr = beginTime.replace("-", "年");
            beginTimeStr = beginTimeStr + "月";
        }
        if(StringUtils.isNotEmpty(endTime)){
            endTimeStr = endTime.replace("-", "年");
            endTimeStr = endTimeStr + "月";
        }
        String qzsj = beginTimeStr+"-"+endTimeStr;
        map.put("xmqzsj", qzsj);
        map.put("finishDepartname", resultReward.getFinishDepartname() == null ? "" : resultReward.getFinishDepartname());
        String reportRewardLevelStr = resultReward.convertGetReportRewardLevel();
        map.put("reportRewardLevel", reportRewardLevelStr == null ? "" : reportRewardLevelStr);
        String reportLevelStr = resultReward.convertGetReportLevel();
        map.put("reportLevel", reportLevelStr == null ? "" : reportLevelStr);
        map.put("taskSource", resultReward.getTaskSource() == null ? "" : resultReward.getTaskSource());
        StringBuffer sb = new StringBuffer();
        String projectTypeStr = "";
        if(StringUtils.isNotEmpty(resultReward.getProjectType())){
            String[] projectTypes = resultReward.getProjectType().split(",");
            for(String type:projectTypes){
                TBProjectTypeEntity pt = this.systemService.get(TBProjectTypeEntity.class, type);
                sb.append(pt.getProjectTypeName()).append(",");
            }
            if(StringUtils.isNotEmpty(sb.toString())){
                projectTypeStr = sb.substring(0,sb.length()-1);
            }
        }
        map.put("projectType", projectTypeStr);
        String appraisalTime = "";
        if(resultReward.getAppraisalTime()!=null){
            appraisalTime = DateUtils.formatDate(resultReward.getAppraisalTime(), "yyyy-MM-dd");
        }
        map.put("appraisalTime", appraisalTime);
        map.put("investedAmount", resultReward.getInvestedAmount() == null ? "" : resultReward.getInvestedAmount());
        String lxrdh = resultReward.getContacts()+"/"+resultReward.getContactPhone();
        map.put("lxrdh", lxrdh);
        map.put("summary", resultReward.getSummary()==null?"":resultReward.getSummary());
        modelMap.put(TemplateWordConstants.FILE_NAME, "科技成果奖励申请审批表_" + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "export/template/cgjlTemplate.docx");
        
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;                   
    }
    
    @RequestMapping(params = "checkIsAppr")
    @ResponseBody
    public JSONObject checkIsAppr(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("isAppr", "0");
        String apprId = request.getParameter("apprId");
        List<Map<String, Object>> apprList = this.getAppr(apprId);//查询课题组长
        if (apprList.size() > 0) {
            Map<String, Object> map = apprList.get(0);
            if (map.get("APPR_ID") != null) {
                json.put("isAppr", "1");
                json.put("receiveId", map.get("ID"));
            }
        }
        return json;
    }

    /**
     * 查询课题组长审批
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//获取参数
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT APPR.ID AS APPR_ID,APPR.SOURCE_PROJECT_IDS AS SOURCEPROJECTIDS,");
        resultSql.append("APPR.FINISH_USERID AS FINISHUSERID,APPR.FINISH_USERNAME AS FINISHUSERNAME,");
        resultSql.append("APPR.FINISH_DEPARTID AS FINISHDEPARTID,APPR.FINISH_DEPARTNAME AS FINISHDEPARTNAME,");
        resultSql.append("APPR.SUMMARY,APPR.INNOVATION_POINT AS INNOVATIONPOINT,APPR.REWARD_NAME AS REWARDNAME,");
        resultSql.append("APPR.REPORT_REWARD_LEVEL AS REPORTREWARDLEVEL,APPR.REPORT_LEVEL AS REPORTLEVEL,");
        resultSql.append("APPR.TASK_SOURCE AS TASKSOURCE,APPR.INVESTED_AMOUNT AS INVESTEDAMOUNT,");
        resultSql.append("APPR.BEGIN_DATE AS BEGINDATE,APPR.END_DATE AS ENDDATE,APPR.CONTACTS AS CONTACTS,");
        resultSql.append("APPR.CONTACT_PHONE AS CONTACTPHONE,APPR.HGD_DEVOTE AS HGDDEVOTE,");

        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_B_RESULT_REWARD APPR, T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID AND EXT.ID='0801' \n"
                + "AND RECEIVE.RECEIVE_USERID = ?  ";

        //未处理：需要是有效的
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                    + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };

        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
}
