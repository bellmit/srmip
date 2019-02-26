package com.kingtake.expert.controller.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.expert.entity.info.TErExpertEntity;
import com.kingtake.expert.entity.info.TErExpertUserEntity;
import com.kingtake.expert.entity.reviewmain.TErMainExpertRelaEntity;
import com.kingtake.expert.entity.reviewmain.TErMainTypeRelaEntity;
import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;
import com.kingtake.expert.entity.reviewproject.TErReviewProjectEntity;
import com.kingtake.expert.entity.reviewtype.TErReviewTypeEntity;
import com.kingtake.expert.page.info.TErExpertPage;
import com.kingtake.expert.service.info.TErExpertServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;

/**
 * @Title: Controller
 * @Description: 专家信息
 * @author onlineGenerator
 * @date 2015-07-13 19:47:51
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tErExpertController")
public class TErExpertController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TErExpertController.class);

    @Autowired
    private TErExpertServiceI tErExpertService;
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
     * 专家信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tErExpert")
    public ModelAndView tErExpert(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/expert/info/tErExpertList");
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
    public void datagrid(TErExpertEntity tErExpert, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TErExpertEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErExpert, request.getParameterMap());
        //cq.eq("expertValidFlag", "1");
        cq.addOrder("expertNum", SortDirection.asc);
        cq.add();
        this.tErExpertService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除专家信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TErExpertEntity tErExpert, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tErExpert = systemService.getEntity(TErExpertEntity.class, tErExpert.getId());
        message = "专家信息删除成功";
        try {
            List<TErMainExpertRelaEntity> relas = systemService.findByProperty(TErMainExpertRelaEntity.class,
                    "teExpertId", tErExpert.getId());
            if (relas.size() > 0) {
                message = "专家已经参与评审，不能删除！";
            }else{
                tErExpertService.delete(tErExpert);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "专家信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除专家信息
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "专家信息删除成功";
        try {
            for (String id : ids.split(",")) {
                TErExpertEntity tErExpert = systemService.getEntity(TErExpertEntity.class, id);
                tErExpertService.delete(tErExpert);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "专家信息删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加专家信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TErExpertUserEntity tErExpert, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "专家信息添加成功";
        try {
            //密码加密
            tErExpert.setUserPwd(PasswordUtil.encrypt(tErExpert.getUserPwd(), tErExpert.getUserName(),
                    PasswordUtil.getStaticSalt()));
            tErExpert.setExpertValidFlag(SrmipConstants.YES);//默认为有效
            tErExpertService.save(tErExpert);
        } catch (Exception e) {
            e.printStackTrace();
            message = "专家信息添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新专家信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TErExpertUserEntity tErExpert, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "专家信息更新成功";
        TErExpertUserEntity t = tErExpertService.get(TErExpertUserEntity.class, tErExpert.getId());
        try {
        	if(tErExpert.getUserPwd() != null){
        		tErExpert.setUserPwd(PasswordUtil.encrypt(t.getUserPwd(), t.getUserName(),
                         PasswordUtil.getStaticSalt()));
        	}
            MyBeanUtils.copyBeanNotNull2Bean(tErExpert, t);
            tErExpertService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "专家信息更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 专家信息新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TErExpertEntity tErExpert, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tErExpert.getId())) {
            tErExpert = tErExpertService.getEntity(TErExpertEntity.class, tErExpert.getId());
            req.setAttribute("tErExpertPage", tErExpert);
        }

        return new ModelAndView("com/kingtake/expert/info/tErExpert-add");
    }

    /**
     * 专家信息编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TErExpertEntity tErExpert, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tErExpert.getId())) {
            tErExpert = tErExpertService.getEntity(TErExpertEntity.class, tErExpert.getId());
            req.setAttribute("tErExpertPage", tErExpert);
        }
        return new ModelAndView("com/kingtake/expert/info/tErExpert-update");
    }

    @RequestMapping(params = "validformExpertNum")
    @ResponseBody
    public ValidForm validformExpertNum(TErExpertEntity tErExpert, HttpServletRequest req) {
        ValidForm v = new ValidForm();

        String inputVal = req.getParameter("param");

        if (StringUtil.isNotEmpty(inputVal)) {
            String sql = "select id from t_er_expert where expert_num = ?";
            if (StringUtil.isNotEmpty(tErExpert.getId())) {
                sql += " and id != '" + tErExpert.getId() + "'";
            }
            List<Map<String, Object>> results = systemService.findForJdbc(sql, inputVal);
            if (results != null && results.size() > 0) {
                v.setStatus("n");
                v.setInfo("编号已被使用，请重新输入！");
            }
        }

        return v;
    }

    @RequestMapping(params = "validformExpertUserName")
    @ResponseBody
    public ValidForm validformExpertUserName(TErExpertEntity tErExpert, HttpServletRequest req) {
        ValidForm v = new ValidForm();

        String inputVal = req.getParameter("param");

        if (StringUtil.isNotEmpty(inputVal)) {
            String sql = "select id from t_er_expert_user where user_name = ?";
            if (StringUtil.isNotEmpty(tErExpert.getId())) {
                sql += " and id != '" + tErExpert.getId() + "'";
            }
            List<Map<String, Object>> results = systemService.findForJdbc(sql, inputVal);
            if (results != null && results.size() > 0) {
                v.setStatus("n");
                v.setInfo("登录账号已被使用，请重新输入！");
            }
        }

        return v;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/expert/info/tErExpertUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TErExpertEntity tErExpert, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TErExpertEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tErExpert, request.getParameterMap());
        List<TErExpertEntity> tErExperts = this.tErExpertService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "专家信息");
        modelMap.put(NormalExcelConstants.CLASS, TErExpertEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("专家信息列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tErExperts);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TErExpertEntity tErExpert, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "专家信息");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TErExpertEntity.class);
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
                List<TErExpertEntity> listTErExpertEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TErExpertEntity.class, params);
                for (TErExpertEntity tErExpert : listTErExpertEntitys) {
                    tErExpertService.save(tErExpert);
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
     * 跳转到专家评审界面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goExpertReviewMgr")
    public ModelAndView goExpertReviewMgr(TErReviewMainEntity tErReviewMain, HttpServletRequest request,
            HttpServletResponse response) {
        if (StringUtils.isNotEmpty(tErReviewMain.getId())) {
            tErReviewMain = systemService.getEntity(TErReviewMainEntity.class, tErReviewMain.getId());
            CriteriaQuery projectCq = new CriteriaQuery(TErReviewProjectEntity.class);
            projectCq.eq("reviewMain.id", tErReviewMain.getId());
            projectCq.add();
            List<TErReviewProjectEntity> projectList = this.systemService.getListByCriteriaQuery(projectCq, false);
            StringBuffer projectSb = new StringBuffer();
            StringBuffer projectNameSb = new StringBuffer();
            for (TErReviewProjectEntity project : projectList) {
                projectSb.append(project.getProjectId()).append(",");
                TPmProjectEntity tmpProject = this.systemService.get(TPmProjectEntity.class, project.getProjectId());
                String projectName = tmpProject.getProjectName();
                projectNameSb.append(projectName).append(",");
            }
            String projectIds = projectSb.toString();
            String projectNames = projectNameSb.toString();
            if (projectIds.length() > 0) {
                projectIds = projectIds.substring(0, projectIds.length() - 1);
                projectNames = projectNames.substring(0, projectNames.length() - 1);
            }
            request.setAttribute("projectIds", projectIds);
            request.setAttribute("projectNames", projectNames);
            CriteriaQuery expCq = new CriteriaQuery(TErMainExpertRelaEntity.class);
            expCq.eq("teMainId", tErReviewMain.getId());
            expCq.add();
            List<TErMainExpertRelaEntity> expList = this.systemService.getListByCriteriaQuery(expCq, false);
            List<TErExpertEntity> expertList = new ArrayList<TErExpertEntity>();
            for (TErMainExpertRelaEntity relaEntity : expList) {
                TErExpertUserEntity expert = this.systemService.get(TErExpertUserEntity.class,
                        relaEntity.getTeExpertId());
                TErExpertUserEntity expertTmp = new TErExpertUserEntity();
                try {
                    PropertyUtils.copyProperties(expertTmp, expert);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                expertTmp.setUserPwd(PasswordUtil.decrypt(expertTmp.getUserPwd(), expertTmp.getUserName(),
                        PasswordUtil.getStaticSalt()));
                expertList.add(expertTmp);
            }
            if (expertList.size() > 0) {
                request.setAttribute("expertListStr", JSON.toJSONString(expertList));
            }
            CriteriaQuery reviewTypeCq = new CriteriaQuery(TErMainTypeRelaEntity.class);
            reviewTypeCq.eq("teMainId", tErReviewMain.getId());
            reviewTypeCq.add();
            List<TErMainTypeRelaEntity> typeRelaList = this.systemService.getListByCriteriaQuery(reviewTypeCq, false);
            List<String> typeList = new ArrayList<String>();
            for (TErMainTypeRelaEntity rela : typeRelaList) {
                typeList.add(rela.getTeTypeId());
            }
            if (typeList.size() > 0) {
                request.setAttribute("typeListStr", JSON.toJSONString(typeList));
            }
        }
        //附件
        if(StringUtils.isEmpty(tErReviewMain.getAttachmentCode())){
            tErReviewMain.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tErReviewMain.getAttachmentCode(), "");
            tErReviewMain.setAttachments(certificates);
        }
        request.setAttribute("tErReviewMainPage", tErReviewMain);
        CriteriaQuery cq = new CriteriaQuery(TErReviewTypeEntity.class);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TErReviewTypeEntity> list = this.tErExpertService.getListByCriteriaQuery(cq, false);
        request.setAttribute("reviewTypeList", list);
        String load = request.getParameter("load");
        if(StringUtils.isNotEmpty(load)){
            request.setAttribute("load", load);
        }
        return new ModelAndView("com/kingtake/expert/review/expertReviewAdd");
    }

    /**
     * 跳转到选择专家的界面
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "goSelectExpert")
    public ModelAndView goSelectExpert(HttpServletRequest request, HttpServletResponse response) {
        String excludeIds = request.getParameter("excludeIds");
        request.setAttribute("excludeIds", excludeIds);
        return new ModelAndView("com/kingtake/expert/review/selectExpert");
    }

    /**
     * 专家模糊匹配
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "autoExpertList")
    @ResponseBody
    public JSONArray autoExpertList(HttpServletRequest request, HttpServletResponse response) {
        String term = request.getParameter("term");
        CriteriaQuery cq = new CriteriaQuery(TErExpertEntity.class);
        cq.like("name", "%" + term + "%");
        cq.eq("expertValidFlag", "1");
        cq.add();
        List<TErExpertEntity> list = this.tErExpertService.getListByCriteriaQuery(cq, false);
        JSONArray array = new JSONArray();
        for (TErExpertEntity entity : list) {
            JSONObject json = new JSONObject();
            json.put("label", entity.getName());
            json.put("value", entity.getId());
            array.add(json);
        }
        return array;
    }

    /**
     * 获取单个专家 信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "getExpertById")
    @ResponseBody
    public TErExpertEntity getExpertById(HttpServletRequest request, HttpServletResponse response) {
        TErExpertPage page = new TErExpertPage();
        try {
            String id = request.getParameter("id");
            TErExpertEntity expert = this.tErExpertService.get(TErExpertEntity.class, id);
            PropertyUtils.copyProperties(page, expert);
            page.setUserPwd(PasswordUtil.decrypt(page.getUserPwd(), page.getUserName(), PasswordUtil.getStaticSalt()));
        } catch (Exception e) {
            throw new BusinessException("查询专家失败", e);
        }
        return page;
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "dataList")
    public void dataList(TErExpertUserEntity tErExpert, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TErExpertUserEntity.class, dataGrid);
        if (StringUtils.isNotEmpty(tErExpert.getName())) {
            cq.like("name", "%" + tErExpert.getName() + "%");
        }
        if (StringUtils.isNotEmpty(tErExpert.getSex())) {
            cq.eq("sex", tErExpert.getSex());
        }
        if (StringUtils.isNotEmpty(tErExpert.getExpertPosition())) {
            cq.eq("expertPosition", tErExpert.getExpertPosition());
        }
        if (StringUtils.isNotEmpty(tErExpert.getExpertProfession())) {
            cq.eq("expertProfession", tErExpert.getExpertProfession());
        }
        if (StringUtils.isNotEmpty(tErExpert.getExpertPraciticReq())) {
            cq.eq("expertPraciticReq", tErExpert.getExpertPraciticReq());
        }
        cq.eq("expertValidFlag", "1");
        cq.add();
        List<TErExpertPage> pageList = new ArrayList<TErExpertPage>();
        JSONObject json = new JSONObject();
        try {
            this.tErExpertService.getDataGridReturn(cq, false);
            List<TErExpertEntity> results = dataGrid.getResults();
            for (int i = 0; i < results.size(); i++) {
                TErExpertUserEntity tmp = (TErExpertUserEntity) results.get(i);
                TErExpertPage page = new TErExpertPage();
                PropertyUtils.copyProperties(page, tmp);
                page.setUserPwd(PasswordUtil.decrypt(page.getUserPwd(), page.getUserName(),
                        PasswordUtil.getStaticSalt()));
                pageList.add(page);
            }
            json.put("total", dataGrid.getTotal());
            json.put("rows", pageList);

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            PrintWriter pw = response.getWriter();
            pw.write(json.toJSONString());
            pw.flush();
        } catch (Exception e) {
            throw new BusinessException("查询专家信息失败", e);
        }
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "getInitList")
    public void getInitList(TErExpertEntity tErExpert, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TErExpertEntity.class, dataGrid);
        String ids = request.getParameter("ids");
        if (StringUtils.isNotEmpty(ids)) {
            cq.in("id", ids.split(","));
        }
        cq.add();
        List<TErExpertPage> pageList = new ArrayList<TErExpertPage>();
        JSONObject json = new JSONObject();
        try {
            this.tErExpertService.getDataGridReturn(cq, false);
            List<TErExpertEntity> results = dataGrid.getResults();
            for (int i = 0; i < results.size(); i++) {
                TErExpertEntity tmp = (TErExpertEntity) results.get(i);
                TErExpertPage page = new TErExpertPage();
                PropertyUtils.copyProperties(page, tmp);
                page.setUserPwd(PasswordUtil.decrypt(page.getUserPwd(), page.getUserName(),
                        PasswordUtil.getStaticSalt()));
                pageList.add(page);
            }
            json.put("total", dataGrid.getTotal());
            json.put("rows", pageList);

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            PrintWriter pw = response.getWriter();
            pw.write(json.toJSONString());
            pw.flush();
        } catch (Exception e) {
            throw new BusinessException("查询专家信息失败", e);
        }
    }

    /**
     * 重置密码
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "resetPwd")
    @ResponseBody
    public AjaxJson resetPwd(HttpServletRequest request, HttpServletResponse response) {
        String message = "成功重置密码为123456！";
        AjaxJson json = new AjaxJson();
        String ids = request.getParameter("ids");
        try {
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                TErExpertUserEntity entity = this.systemService.get(TErExpertUserEntity.class, id);
                if (entity != null) {
                    entity.setUserPwd(PasswordUtil.encrypt("123456", entity.getUserName(), PasswordUtil.getStaticSalt()));//默认密码123456
                    this.systemService.updateEntitie(entity);
                }
            }
        }
        } catch (Exception e) {
            throw new BusinessException("重置密码失败!", e);
        }
        json.setMsg(message);
        return json;
    }

}
