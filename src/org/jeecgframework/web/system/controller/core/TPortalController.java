package org.jeecgframework.web.system.controller.core;

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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.PortalUserVo;
import org.jeecgframework.web.system.pojo.base.TPortalEntity;
import org.jeecgframework.web.system.pojo.base.TPortalLayoutEntity;
import org.jeecgframework.web.system.pojo.base.TPortalRoleEntity;
import org.jeecgframework.web.system.pojo.base.TPortalUserEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.TPortalServiceI;
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

/**
 * @Title: Controller
 * @Description: 代办配置表
 * @author onlineGenerator
 * @date 2015-05-29 11:54:05
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPortalController")
public class TPortalController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPortalController.class);

    @Autowired
    private TPortalServiceI tPortalService;
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
     * 代办配置表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPortal")
    public ModelAndView tPortal(HttpServletRequest request) {
        return new ModelAndView("system/portal/tPortalList");
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
    public void datagrid(TPortalEntity tPortal, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPortalEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPortal, request.getParameterMap());
        cq.addOrder("sort", SortDirection.asc);
        cq.add();
        this.tPortalService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除代办配置表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPortalEntity tPortal, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPortal = systemService.getEntity(TPortalEntity.class, tPortal.getId());
        message = "代办配置表删除成功";
        try {
            tPortalService.delete(tPortal);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "代办配置表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除代办配置表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "代办配置表删除成功";
        try {
            for (String id : ids.split(",")) {
                TPortalEntity tPortal = systemService.getEntity(TPortalEntity.class, id);
                tPortalService.delete(tPortal);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "代办配置表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加代办配置表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPortalEntity tPortal, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "代办配置表添加成功";
        try {
            tPortalService.save(tPortal);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "代办配置表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加代办配置表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "savePortalUser")
    @ResponseBody
    public AjaxJson savePortalUser(PortalUserVo portalUserVo, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "代办配置保存成功";
        try {
            tPortalService.savePortalSetting(portalUserVo);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setSuccess(true);
            j.setMsg(message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "代办配置保存失败";
            j.setSuccess(false);
            j.setMsg(message);
        }
        return j;
    }

    /**
     * 设置布局
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "setLayout")
    @ResponseBody
    public AjaxJson setLayout(String userName, String layoutId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "布局配置成功！";
        try {
            tPortalService.setLayout(userName, layoutId);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            j.setSuccess(true);
            j.setMsg(message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "布局配置失败";
            j.setSuccess(false);
            j.setMsg(message);
            throw new BusinessException(e.getMessage());
        }
        return j;
    }

    /**
     * 更新代办配置表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPortalEntity tPortal, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "代办配置表更新成功";
        TPortalEntity t = tPortalService.get(TPortalEntity.class, tPortal.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPortal, t);
            tPortalService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "代办配置表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 代办配置表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPortalEntity tPortal, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPortal.getId())) {
            tPortal = tPortalService.getEntity(TPortalEntity.class, tPortal.getId());
            req.setAttribute("tPortalPage", tPortal);
        }
        return new ModelAndView("system/portal/tPortal-add");
    }

    /**
     * 代办布局配置跳转
     * 
     * @return
     */
    @RequestMapping(params = "goSetLayout")
    public ModelAndView goSetLayout(String userName, HttpServletRequest req) {
        List<TPortalLayoutEntity> layoutList = tPortalService.getAllLayout();
        TPortalUserEntity portalUser = this.tPortalService.getPortalByUserName(userName);
        if (portalUser != null && portalUser.getLayoutEntity() != null) {
            req.setAttribute("selectedLayout", portalUser.getLayoutEntity().getId());
        }
        req.setAttribute("layoutList", layoutList);
        req.setAttribute("userName", userName);
        return new ModelAndView("system/portal/tPortal-layout");
    }

    /**
     * 代办配置表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPortalEntity tPortal, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPortal.getId())) {
            tPortal = tPortalService.getEntity(TPortalEntity.class, tPortal.getId());
            req.setAttribute("tPortalPage", tPortal);
        }
        return new ModelAndView("system/portal/tPortal-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("system/portal/tPortalUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPortalEntity tPortal, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPortalEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPortal, request.getParameterMap());
        List<TPortalEntity> tPortals = this.tPortalService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "代办配置表");
        modelMap.put(NormalExcelConstants.CLASS, TPortalEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("代办配置表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPortals);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPortalEntity tPortal, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "代办配置表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPortalEntity.class);
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
                List<TPortalEntity> listTPortalEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TPortalEntity.class, params);
                for (TPortalEntity tPortal : listTPortalEntitys) {
                    tPortalService.save(tPortal);
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
     * 获取待办列表
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getPortal", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getPortal(String userName, HttpServletRequest req, HttpServletResponse response) {
        JSONObject j = new JSONObject();
        TPortalUserEntity portalUserEntity = this.tPortalService.getPortalByUserName(userName);
        j.put("portalUser", portalUserEntity);
        logger.info("转换的json：" + j);
        return j;
    }

    /**
     * 获取待添加待办列表
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getAdddPortalList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getAddPortalList(String userName, HttpServletRequest req, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        List<PortalUserVo> portalList = this.tPortalService.getAddPortalList(userName);
        json.put("total", portalList.size());
        json.put("rows", portalList);
        logger.info("转换的json：" + json);
        return json;
    }

    /**
     * 添加待办
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "addPortal", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addPortal(String userName, String ids, HttpServletRequest req, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(ids)) {
                this.tPortalService.addPortal(userName, ids);
            }
            json.setSuccess(true);
            json.setMsg("添加待办成功!");
        } catch (Exception e) {
            logger.error(e);
            json.setSuccess(false);
            json.setMsg("添加待办失败," + e.getMessage());
        }
        return json;
    }

    /**
     * 跳转到角色列表
     * 
     * @return
     */
    @RequestMapping(params = "goRoleList")
    public ModelAndView goRoleList() {
        return new ModelAndView("system/portal/roleList");
    }

    /**
     * 跳转到portal设置列表
     * 
     * @return
     */
    @RequestMapping(params = "goRolePortalSet")
    public ModelAndView goRolePortalSet(HttpServletRequest req) {
        String roleId = req.getParameter("roleId");
        if (StringUtils.isNotEmpty(roleId)) {
            req.setAttribute("roleId", roleId);
        }
        CriteriaQuery cq = new CriteriaQuery(TPortalRoleEntity.class);
        cq.eq("roleId", roleId);
        cq.add();
        List<TPortalRoleEntity> entities = this.systemService.getListByCriteriaQuery(cq, false);
        if (entities.size() > 0) {
            String ids = "";
            for (TPortalRoleEntity entity : entities) {
                ids = ids + "," + entity.getPortalId();
            }
            if (StringUtils.isNotEmpty(ids)) {
                ids = ids.substring(1);
            }
            req.setAttribute("portalIds", ids);
        }
        return new ModelAndView("system/portal/rolePortalSet");
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
    public void dataList(TPortalEntity tPortal, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPortalEntity.class, dataGrid);
        cq.addOrder("sort", SortDirection.desc);
        cq.add();
        this.tPortalService.getDataGridReturn(cq, false);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 设置角色的portal
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "setRolePortal", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson setRolePortal(HttpServletRequest request, HttpServletResponse response) {
        String roleId = request.getParameter("roleId");
        String portals = request.getParameter("portals");
        AjaxJson json = new AjaxJson();
        try {
            tPortalService.setRolePortal(roleId, portals);
            json.setMsg("设置角色与待办成功！");
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("设置角色与待办失败，原因：" + e.getMessage());
        }
        return json;
    }
}
