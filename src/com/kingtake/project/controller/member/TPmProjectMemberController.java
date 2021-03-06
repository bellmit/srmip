package com.kingtake.project.controller.member;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.jeecgframework.web.system.pojo.base.TSDepart;
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

import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.service.member.TPmProjectMemberServiceI;

/**
 * @Title: Controller
 * @Description: ???????????????
 * @author onlineGenerator
 * @date 2015-07-09 17:34:25
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmProjectMemberController")
public class TPmProjectMemberController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmProjectMemberController.class);

    @Autowired
    private TPmProjectMemberServiceI tPmProjectMemberService;
    @Autowired
    private SystemService systemService;
    /**
     * ?????????service
     */
    @Autowired
    private TBCodeTypeServiceI codeTypeService;
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
    @RequestMapping(params = "tPmProjectMember")
    public ModelAndView tPmProjectMember(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/member/tPmProjectMemberList");
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
    public void datagrid(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectMemberEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectMember,
                request.getParameterMap());
        try {
            //???????????????????????????
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmProjectMemberService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ?????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProjectMember = systemService.getEntity(TPmProjectMemberEntity.class, tPmProjectMember.getId());
        message = "???????????????????????????";
        try {
            tPmProjectMemberService.delete(tPmProjectMember);
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
                TPmProjectMemberEntity tPmProjectMember = systemService.getEntity(TPmProjectMemberEntity.class, id);
                tPmProjectMemberService.delete(tPmProjectMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "findInfoById")
    @ResponseBody
    public AjaxJson findInfoById(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String uid = req.getParameter("uid");
        String orgid = req.getParameter("orgid");
        if (StringUtil.isNotEmpty(uid)) {
            TSUser user = systemService.get(TSUser.class, uid);
            if (user != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                if (user.getBirthday() != null) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String birthday = df.format(user.getBirthday());
                    map.put("birthday", birthday);
                }
                j.setAttributes(map);
                map.put("user", user);
                if (StringUtil.isNotEmpty(orgid)) {
                	TSDepart depart = systemService.get(TSDepart.class, orgid);
                    if (depart != null) {                        
                        map.put("departId", depart.getId());
                        map.put("departName", depart.getDepartname());
                        if(depart.getTSPDepart() != null)
                        {
                        	map.put("departPid", depart.getTSPDepart().getId());
                            map.put("departPname", depart.getTSPDepart().getDepartname());
                        }                        
                    }
                }
            }
        }        
        return j;
    }

    /**
     * ????????????????????????????????? ????????????true??????false
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "ifMemberExist")
    @ResponseBody
    public AjaxJson ifMemberExist(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        Boolean flag = false;
        String uid = req.getParameter("uid");
        String pid = req.getParameter("pid");
        String id = req.getParameter("id");
        if (StringUtil.isNotEmpty(uid) && StringUtil.isNotEmpty(pid)) {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT COUNT(0) FROM T_PM_PROJECT_MEMBER T WHERE T.T_P_ID =? AND T.USER_ID =? ");
            if (StringUtil.isNotEmpty(id)) {
                sb.append(" AND T.ID!= '" + id + "'");
            }
            String[] params = new String[] { pid, uid };
            Long count = systemService.getCountForJdbcParam(sb.toString(), params);
            flag = count.intValue() > 0 ? true : false;
        }
        j.setObj(flag);
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
    public AjaxJson doAdd(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            if (tPmProjectMember.getSuperUnitId() != null) {
                TSDepart superUnit = this.systemService.get(TSDepart.class, tPmProjectMember.getSuperUnitId());
                tPmProjectMember.setSuperUnit(superUnit);
            }
            if (tPmProjectMember.getUserId() != null) {
                TSUser user = this.systemService.get(TSUser.class, tPmProjectMember.getUserId());
                tPmProjectMember.setUser(user);
            }
            if (tPmProjectMember.getTpId() != null) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmProjectMember.getTpId());
                tPmProjectMember.setProject(project);
            }
            tPmProjectMemberService.save(tPmProjectMember);
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
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        TPmProjectMemberEntity t = tPmProjectMemberService.get(TPmProjectMemberEntity.class, tPmProjectMember.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmProjectMember, t);
            if (tPmProjectMember.getSuperUnitId() != null) {
                TSDepart superUnit = this.systemService.get(TSDepart.class, tPmProjectMember.getSuperUnitId());
                t.setSuperUnit(superUnit);
            }
            if (tPmProjectMember.getUserId() != null) {
                TSUser user = this.systemService.get(TSUser.class, tPmProjectMember.getUserId());
                t.setUser(user);
            }
            if (tPmProjectMember.getTpId() != null) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmProjectMember.getTpId());
                t.setProject(project);
            }
            tPmProjectMemberService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmProjectMember.getId())) {
            tPmProjectMember = tPmProjectMemberService
                    .getEntity(TPmProjectMemberEntity.class, tPmProjectMember.getId());
            req.setAttribute("tPmProjectMemberPage", tPmProjectMember);
        }
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/member/tPmProjectMember-add");
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmProjectMember.getId())) {
            tPmProjectMember = tPmProjectMemberService
                    .getEntity(TPmProjectMemberEntity.class, tPmProjectMember.getId());
            req.setAttribute("tPmProjectMemberPage", tPmProjectMember);
        }
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/member/tPmProjectMember-update");
    }

    /**
     * ?????????????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "selectMember")
    public ModelAndView selectMember(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/member/MemberList");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/member/tPmProjectMemberUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectMemberEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectMember,
                request.getParameterMap());
        List<TPmProjectMemberEntity> tPmProjectMembers = this.tPmProjectMemberService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "???????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectMemberEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjectMembers);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmProjectMemberEntity tPmProjectMember, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "???????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmProjectMemberEntity.class);
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
                List<TPmProjectMemberEntity> listTPmProjectMemberEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmProjectMemberEntity.class, params);
                for (TPmProjectMemberEntity tPmProjectMember : listTPmProjectMemberEntitys) {
                    tPmProjectMemberService.save(tPmProjectMember);
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

    @RequestMapping(params = "getContactPhone")
    @ResponseBody
    public AjaxJson getContactPhone(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        Map<String, String> userMap = new HashMap<String, String>();
        String uid = req.getParameter("uid");
        List<String> phoneList = new ArrayList<String>();
        if (StringUtil.isNotEmpty(uid)) {
            String[] uidArr = uid.split(",");
            for (String id : uidArr) {
                TSUser user = systemService.get(TSUser.class, id);
                if (StringUtils.isNotEmpty(user.getOfficePhone())) {
                    phoneList.add(user.getOfficePhone());
                } else if (StringUtils.isNotEmpty(user.getMobilePhone())) {
                    phoneList.add(user.getMobilePhone());
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        if (phoneList.size() > 0) {
            for (String str : phoneList) {
                sb.append(str).append(",");
            }
        }
        if (sb.length() > 0) {
            String phone = sb.toString();
            phone = phone.substring(0, phone.length() - 1);
            userMap.put("userContact", phone);
        } else {
            userMap.put("userContact", "");
        }
        j.setObj(userMap);
        return j;
    }

}
