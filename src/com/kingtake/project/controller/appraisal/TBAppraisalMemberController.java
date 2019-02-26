package com.kingtake.project.controller.appraisal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyMemRelaEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalMemberEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartEntity;
import com.kingtake.project.service.appraisal.TBAppraisalMemberServiceI;

/**
 * @Title: Controller
 * @Description: T_B_APPRAISAL_MEMBER
 * @author onlineGenerator
 * @date 2015-09-09 09:44:46
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalMemberController")
public class TBAppraisalMemberController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBAppraisalMemberController.class);

    @Autowired
    private TBAppraisalMemberServiceI tBAppraisalMemberService;
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
     * T_B_APPRAISAL_MEMBER列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisalMember")
    public ModelAndView tBAppraisalMember(HttpServletRequest request) {
        request.setAttribute("applyId", request.getParameter("applyId"));
        return new ModelAndView("com/kingtake/project/appraisalmeeting/tBAppraisalMemberList");
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
    public void datagrid(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String applyId = request.getParameter("applyId");
        TBAppraisalApplyEntity apply = systemService.get(TBAppraisalApplyEntity.class, applyId);
        if (apply!=null) {
            CriteriaQuery rcq = new CriteriaQuery(TBAppraisalApplyMemRelaEntity.class);
            rcq.eq("tbId", apply.getId());
            rcq.add();
            List<TBAppraisalApplyMemRelaEntity> rlist = systemService.getListByCriteriaQuery(rcq, false);
            String[] mids = new String[rlist.size()];
            for (int i = 0; i < rlist.size(); i++) {
                mids[i] = rlist.get(i).getMemberId();
            }
            CriteriaQuery cq = new CriteriaQuery(TBAppraisalMemberEntity.class, dataGrid);
            //查询条件组装器
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMember,
                    request.getParameterMap());
            if(mids.length>0){
                cq.in("id", mids);
            }
            cq.add();
            this.tBAppraisalMemberService.getDataGridReturn(cq, true);
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAddAttendExpert")
    public ModelAndView goAddAttendExpert(HttpServletRequest req) {
        String meetingId = req.getParameter("meetingId");
        String departId = req.getParameter("departId");
        if (StringUtil.isNotEmpty(departId)) {
            TBAppraisalMeetingDepartEntity depart = systemService.get(TBAppraisalMeetingDepartEntity.class, departId);
            req.setAttribute("depart", depart);
        }
        req.setAttribute("meetingId", meetingId);
        return new ModelAndView("com/kingtake/project/appraisalmeeting/addMember");
    }

    /**
     * 删除T_B_APPRAISAL_MEMBER
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBAppraisalMember = systemService.getEntity(TBAppraisalMemberEntity.class, tBAppraisalMember.getId());
        message = "T_B_APPRAISAL_MEMBER删除成功";
        try {
            tBAppraisalMemberService.delete(tBAppraisalMember);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "T_B_APPRAISAL_MEMBER删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除T_B_APPRAISAL_MEMBER
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "T_B_APPRAISAL_MEMBER删除成功";
        try {
            for (String id : ids.split(",")) {
                TBAppraisalMemberEntity tBAppraisalMember = systemService.getEntity(TBAppraisalMemberEntity.class, id);
                tBAppraisalMemberService.delete(tBAppraisalMember);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "T_B_APPRAISAL_MEMBER删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加T_B_APPRAISAL_MEMBER
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "T_B_APPRAISAL_MEMBER添加成功";
        try {
            tBAppraisalMemberService.save(tBAppraisalMember);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "T_B_APPRAISAL_MEMBER添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新T_B_APPRAISAL_MEMBER
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "T_B_APPRAISAL_MEMBER更新成功";
        TBAppraisalMemberEntity t = tBAppraisalMemberService.get(TBAppraisalMemberEntity.class,
                tBAppraisalMember.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBAppraisalMember, t);
            tBAppraisalMemberService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "T_B_APPRAISAL_MEMBER更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * T_B_APPRAISAL_MEMBER新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalMember.getId())) {
            tBAppraisalMember = tBAppraisalMemberService.getEntity(TBAppraisalMemberEntity.class,
                    tBAppraisalMember.getId());
            req.setAttribute("tBAppraisalMemberPage", tBAppraisalMember);
        }
        return new ModelAndView("com/kingtake/test/tBAppraisalMember-add");
    }

    /**
     * T_B_APPRAISAL_MEMBER编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalMember.getId())) {
            tBAppraisalMember = tBAppraisalMemberService.getEntity(TBAppraisalMemberEntity.class,
                    tBAppraisalMember.getId());
            req.setAttribute("tBAppraisalMemberPage", tBAppraisalMember);
        }
        return new ModelAndView("com/kingtake/test/tBAppraisalMember-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/test/tBAppraisalMemberUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalMemberEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalMember,
                request.getParameterMap());
        List<TBAppraisalMemberEntity> tBAppraisalMembers = this.tBAppraisalMemberService.getListByCriteriaQuery(cq,
                false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "T_B_APPRAISAL_MEMBER");
        modelMap.put(NormalExcelConstants.CLASS, TBAppraisalMemberEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("T_B_APPRAISAL_MEMBER列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBAppraisalMembers);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBAppraisalMemberEntity tBAppraisalMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "T_B_APPRAISAL_MEMBER");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBAppraisalMemberEntity.class);
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
                List<TBAppraisalMemberEntity> listTBAppraisalMemberEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBAppraisalMemberEntity.class, params);
                for (TBAppraisalMemberEntity tBAppraisalMember : listTBAppraisalMemberEntitys) {
                    tBAppraisalMemberService.save(tBAppraisalMember);
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
     * 自动完成请求返回数据
     * 
     * @param request
     * @param responss
     */
    @RequestMapping(params = "getAutoList")
    public void getAutoList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
        String trem = StringUtil.getEncodePra(request.getParameter("q"));// 重新解析参数
        autocomplete.setTrem(trem);
        if (StringUtils.isNotBlank(trem)) {
            //        List autoList = systemService.getAutoList(autocomplete);
            String sql = "select distinct t.member_name memberName,t.member_position memberPosition,t.member_profession memberProfession,t.work_unit workUnit from t_b_appraisal_member t where t.member_name like '%"
                    + trem + "%'";
            List<Map<String, Object>> autoList = this.systemService.findForJdbc(sql, autocomplete.getCurPage(),
                    autocomplete.getMaxRows());
            JSONArray array = new JSONArray();
            for (Map<String, Object> map : autoList) {
                JSONObject json = new JSONObject();
                json.put("memberName", map.get("memberName"));
                json.put("memberPosition", map.get("memberPosition"));
                json.put("memberProfession", map.get("memberProfession"));
                json.put("workUnit", map.get("workUnit"));
                array.add(json);
            }
            try {
                JSONObject result = new JSONObject();
                result.put("rows", array);
                response.setContentType("application/json;charset=UTF-8");
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                response.getWriter().write(result.toJSONString());
                response.getWriter().flush();
                response.getWriter().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
