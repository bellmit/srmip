package com.kingtake.base.controller.projecttype;

import java.io.IOException;
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
import org.jeecgframework.core.common.model.json.ComboBox;
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
import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.base.entity.sideccatalog.TPmSidecatalogEntity;
import com.kingtake.base.entity.sideccatalog.TPmTypeCatalogRelaEntity;
import com.kingtake.base.service.projecttype.TBProjectTypeServiceI;
import com.kingtake.common.constant.ProjectConstant;

/**
 * @Title: Controller
 * @Description: 项目类型信息表
 * @author onlineGenerator
 * @date 2015-07-16 17:50:27
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBProjectTypeController")
public class TBProjectTypeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBProjectTypeController.class);

    @Autowired
    private TBProjectTypeServiceI tBProjectTypeService;
    @Autowired
    private SystemService systemService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @RequestMapping(params = "ProjectApprovalCombo")
    @ResponseBody
    public List<ComboBox> ProjectApprovalCombo(HttpServletRequest request) {
        List<ComboBox> comboBoxs = new ArrayList<ComboBox>();

        ComboBox comboBox1 = new ComboBox();
        comboBox1.setId(ProjectConstant.PROJECT_CONTRACT);
        comboBox1.setText("计划类");
        comboBoxs.add(comboBox1);

        ComboBox comboBox2 = new ComboBox();
        comboBox2.setId(ProjectConstant.PROJECT_PLAN);
        comboBox2.setText("合同类");
        comboBoxs.add(comboBox2);

        String approvalCode = request.getParameter("approvalCode");
        if (ProjectConstant.PROJECT_CONTRACT.equals(approvalCode)) {
            comboBox1.setSelected(true);
        } else {
            comboBox2.setSelected(true);
        }

        return comboBoxs;
    }

    /**
     * 项目类型信息表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBProjectType")
    public ModelAndView tBProjectType(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/projecttype/tBProjectTypeList");
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
    public void datagrid(TBProjectTypeEntity tBProjectType, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBProjectTypeEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBProjectType,
                request.getParameterMap());
        cq.add();
        this.tBProjectTypeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除项目类型信息表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBProjectTypeEntity tBProjectType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目类型信息表删除成功";
        try {
            this.tBProjectTypeService.delProjectType(tBProjectType);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目类型信息表删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除项目类型信息表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目类型信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBProjectTypeEntity tBProjectType = systemService.getEntity(TBProjectTypeEntity.class, id);
                tBProjectTypeService.delete(tBProjectType);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目类型信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加项目类型信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBProjectTypeEntity tBProjectType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目类型信息表添加成功";
        try {
        	tBProjectType.setValidFlag("1");
        	tBProjectType.setDeclareType("1");
            tBProjectTypeService.save(tBProjectType);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目类型信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新项目类型信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBProjectTypeEntity tBProjectType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目类型信息表更新成功";
        TBProjectTypeEntity t = tBProjectTypeService.get(TBProjectTypeEntity.class, tBProjectType.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBProjectType, t);
            tBProjectTypeService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目类型信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 项目类型信息表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBProjectTypeEntity tBProjectType, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBProjectType.getId())) {
            tBProjectType = tBProjectTypeService.getEntity(TBProjectTypeEntity.class, tBProjectType.getId());
            req.setAttribute("tBProjectTypePage", tBProjectType);
        }
        return new ModelAndView("com/kingtake/base/projecttype/tBProjectType-add");
    }

    /**
     * 项目类型信息表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBProjectTypeEntity tBProjectType, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBProjectType.getId())) {
            tBProjectType = tBProjectTypeService.getEntity(TBProjectTypeEntity.class, tBProjectType.getId());
            req.setAttribute("tBProjectTypePage", tBProjectType);
            if (ProjectConstant.PROJECT_PLAN.equals(tBProjectType.getApprovalCode())) {
                req.setAttribute("PROJECT_PLAN", true);
            }

            //String userId = tBProjectType.getOfficer();
            //            if (StringUtil.isNotEmpty(userId)) {
            //                TSUser user = systemService.get(TSUser.class, userId);
            //                if (user != null) {
            //                    req.setAttribute("office_realname", user.getRealName());
            //                }
            //            }
        }
        return new ModelAndView("com/kingtake/base/projecttype/tBProjectType-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/base/projecttype/tBProjectTypeUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBProjectTypeEntity tBProjectType, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBProjectTypeEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBProjectType,
                request.getParameterMap());
        List<TBProjectTypeEntity> tBProjectTypes = this.tBProjectTypeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目类型信息表");
        modelMap.put(NormalExcelConstants.CLASS, TBProjectTypeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目类型信息表列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBProjectTypes);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBProjectTypeEntity tBProjectType, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "项目类型信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBProjectTypeEntity.class);
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
                List<TBProjectTypeEntity> listTBProjectTypeEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBProjectTypeEntity.class, params);
                for (TBProjectTypeEntity tBProjectType : listTBProjectTypeEntitys) {
                    tBProjectTypeService.save(tBProjectType);
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

    @RequestMapping(params = "getProjectTypeList")
    @ResponseBody
    public JSONArray getProjectTypeList(HttpServletRequest request, HttpServletResponse response) {
        CriteriaQuery cq = new CriteriaQuery(TBProjectTypeEntity.class);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TBProjectTypeEntity> projectTypeList = this.systemService.getListByCriteriaQuery(cq, false);
        JSONArray array = new JSONArray();
        array = (JSONArray) JSONArray.toJSON(projectTypeList);
        return array;
    }

    @RequestMapping(params = "getProjectTypeListWithNull")
    @ResponseBody
    public JSONArray getProjectTypeListWithNull(HttpServletRequest request, HttpServletResponse response) {
        CriteriaQuery cq = new CriteriaQuery(TBProjectTypeEntity.class);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TBProjectTypeEntity> projectTypeList = this.systemService.getListByCriteriaQuery(cq, false);
        JSONArray array = new JSONArray();
        List<TBProjectTypeEntity> list1 = new ArrayList<TBProjectTypeEntity>();
        TBProjectTypeEntity nullProject = new TBProjectTypeEntity();
        nullProject.setId("");
        nullProject.setProjectTypeName("");
        list1.add(nullProject);
        list1.addAll(projectTypeList);
        array = (JSONArray) JSONArray.toJSON(list1);
        return array;
    }

    /**
     * 根据项目类型获取经费类型和立项形式以及负责机关参谋
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getFundType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFundType(HttpServletRequest request, TBProjectTypeEntity projectType,
            HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(projectType.getId())) {
            projectType = this.systemService.get(TBProjectTypeEntity.class, projectType.getId());
            TBFundsPropertyEntity fundProperty = this.systemService.get(TBFundsPropertyEntity.class,
                    projectType.getFundsPropertyId());
            map.put("fund", fundProperty);

            Map<String, String> approvalMap = new HashMap<String, String>();
            approvalMap.put("code", projectType.getApprovalCode());
            approvalMap.put("name", ProjectConstant.planContractMap.get(projectType.getApprovalCode()));
            map.put("approval", approvalMap);

            Map<String, String> officeMap = new HashMap<String, String>();
            //String officeStaff = projectType.getOfficer();
            //            if (StringUtil.isNotEmpty(officeStaff)) {
            //                officeMap.put("officeStaff", officeStaff);
            //
            //                TSUser user = systemService.get(TSUser.class, officeStaff);
            //                if (user != null) {
            //                    officeMap.put("officeStaffRealname", user.getRealName());
            //                }
            //            }
            map.put("office", officeMap);
        }
        return map;
    }
    /**
     * 选择项目类型跳转页面
     * @param request
     * @return
     */
    @RequestMapping(params = "goSelect")
    public ModelAndView goSelect(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/projecttype/tBProjectTypeSelectList");
    }

    /**
     * 选择项目类型跳转页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "getParentTypeList")
    @ResponseBody
    public List<ComboBox> getParentTypeList(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<ComboBox> comboboxList = new ArrayList<ComboBox>();
        CriteriaQuery cq = new CriteriaQuery(TBProjectTypeEntity.class);
        if (StringUtils.isNotEmpty(id)) {
            cq.notEq("id", id);
        }
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TBProjectTypeEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        
        for (TBProjectTypeEntity type : parentList) {
            ComboBox combo = new ComboBox();
            combo.setId(type.getId());
            combo.setText(type.getProjectTypeName());
            comboboxList.add(combo);
        }
        return comboboxList;

    }

    @RequestMapping(params = "getProjectTypeTree")
    @ResponseBody
    public JSONArray getProjectTypeTree(TBProjectTypeEntity projectType, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TBProjectTypeEntity.class);
        cq.isNull("parentType");
        cq.eq("validFlag", "1");
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TBProjectTypeEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TBProjectTypeEntity parent : parentList) {
            JSONObject pJson = getTreeGrid(parent);
            jsonArray.add(pJson);
        }
        return jsonArray;
    }

    /**
     * 获取树形列表
     * 
     * @param parent
     * @return
     */
    private JSONObject getTreeGrid(TBProjectTypeEntity parent) {
        JSONObject json = new JSONObject();
        json.put("id", parent.getId());
        json.put("projectTypeName", parent.getProjectTypeName());
        json.put("approvalCode", parent.getApprovalCode());
        json.put("fundsPropertyId", parent.getFundsPropertyId());
        json.put("sortCode", parent.getSortCode());
        json.put("memo", parent.getMemo());
        CriteriaQuery subCq = new CriteriaQuery(TBProjectTypeEntity.class);
        subCq.eq("parentType.id", parent.getId());
        subCq.eq("validFlag", "1");
        subCq.addOrder("sortCode", SortDirection.asc);
        subCq.add();
        List<TBProjectTypeEntity> subList = this.systemService.getListByCriteriaQuery(subCq, false);
        if (subList.size() > 0) {
            JSONArray array = new JSONArray();
            for (TBProjectTypeEntity sub : subList) {
                JSONObject subJson = getTreeGrid(sub);
                array.add(subJson);
            }
            json.put("children", array);
        }
        return json;
    }

    //初始化
    @RequestMapping(params = "initModule")
    @ResponseBody
    public AjaxJson initModule(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        systemService.executeSql("delete from t_pm_type_catalog_rela");
        List<TBProjectTypeEntity> projectTypes = this.systemService.loadAll(TBProjectTypeEntity.class);
        List<TPmSidecatalogEntity> sideCatalogs = this.systemService.loadAll(TPmSidecatalogEntity.class);
        List<TPmTypeCatalogRelaEntity> relaList = new ArrayList<TPmTypeCatalogRelaEntity>();
        for (TBProjectTypeEntity projectType : projectTypes) {
            for (TPmSidecatalogEntity side : sideCatalogs) {
                TPmTypeCatalogRelaEntity rela = new TPmTypeCatalogRelaEntity();
                rela.setTypeId(projectType.getId());
                if ("1".equals(projectType.getApprovalCode())) {//计划类
                    switch (side.getId()) {
                    case "40288aec529b8d0301529baa0dfb0008":
                        continue;
                    case "40288aec5257914e015257cc51930051":
                        continue;
                    case "40288aec529b8d0301529bb34a000010":
                        continue;
                    case "40288a285258eed2015259c9ac8f000d":
                        continue;
                    case "40288aec529b8d0301529b9ff74d0002":
                        continue;
                    case "40288aec5257914e015257c978fa0050":
                        continue;
                    case "40288aec529b8d0301529b9f16530001":
                        continue;
                    case "40288a85535e27b701535e58370d0000":
                        continue;
                    }
                } else {
                    switch (side.getId()) {
                    case "40288aec5257914e015257c978fa0050":
                        continue;
                    case "40288aec529b8d0301529b9f16530001":
                        continue;
                    case "40288aec5257914e015257c8a3e7004f":
                        continue;
                    case "40288aec529b8d0301529b9de8e90000":
                        continue;
                    case "40288a85535e27b701535e58370d0000":
                        continue;
                    }
                }
                rela.setCatalogId(side.getId());
                relaList.add(rela);
            }
        }
        this.systemService.batchSave(relaList);
        j.setMsg("数据初始化完成！");
        return j;
    }
}
