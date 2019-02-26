package com.kingtake.base.controller.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
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
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
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
import com.kingtake.base.entity.code.TBCodeDetailEntity;
import com.kingtake.base.entity.code.TBCodeTypeEntity;
import com.kingtake.base.page.code.TBCodeDetailPage;
import com.kingtake.base.page.code.TBCodeTypePage;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Controller
 * @Description: 基础标准代码类别表
 * @author onlineGenerator
 * @date 2015-06-11 11:12:05
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBCodeTypeController")
public class TBCodeTypeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBCodeTypeController.class);

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;
    @Autowired
    private SystemService systemService;

    /**
     * 基础标准代码类别表列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBCodeType")
    public ModelAndView tBCodeType(String codeType, HttpServletRequest request) {
        request.setAttribute("codeType", codeType);
        return new ModelAndView("com/kingtake/base/code/tBCodeTypeList");
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
    public void datagrid(TBCodeTypeEntity tBCodeType, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        try {
            CriteriaQuery cq = new CriteriaQuery(TBCodeTypeEntity.class, dataGrid);
            cq.eq("validFlag", "1");
            cq.eq("codeType", tBCodeType.getCodeType());
            // 查询条件组装器
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBCodeType);
            cq.addOrder("createDate", SortDirection.desc);
            cq.add();
            tBCodeTypeService.getDataGridReturn(cq, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除基础标准代码类别表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBCodeTypeEntity tBCodeType, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "基础标准代码类别表删除成功";
        try {
        	tBCodeType = systemService.get(TBCodeTypeEntity.class, tBCodeType.getId());
            tBCodeTypeService.deleteMain(tBCodeType);
            
            if(SrmipConstants.YES.equals(tBCodeType.getCodeType())){
            	SrmipConstants.dicResearchMap.remove(tBCodeType.getCode());
            }else{
            	SrmipConstants.dicStandardMap.remove(tBCodeType.getCode());
            }
            //            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "基础标准代码类别表删除失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除基础标准代码类别表
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "基础标准代码类别表删除成功";
        try {
            for (String id : ids.split(",")) {
                TBCodeTypeEntity tBCodeType = systemService.getEntity(TBCodeTypeEntity.class, id);
                tBCodeTypeService.deleteMain(tBCodeType);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
                
                if(SrmipConstants.YES.equals(tBCodeType.getCodeType())){
                	SrmipConstants.dicResearchMap.remove(tBCodeType.getCode());
                }else{
                	SrmipConstants.dicStandardMap.remove(tBCodeType.getCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "基础标准代码类别表删除失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新基础标准代码类别表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "saveCodeType")
    @ResponseBody
    public AjaxJson saveCodeType(TBCodeTypeEntity tBCodeType, TBCodeTypePage tBCodeTypePage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = null;
        try {
            if (StringUtils.isEmpty(tBCodeType.getId())) {
                message = "添加成功!";
                TBCodeTypeEntity entity = tBCodeTypeService.getCodeTypeByCode(tBCodeType);
                if (entity != null) {
                    throw new BusinessException("编码为[" + tBCodeType.getCode() + "]的代码类别已存在，不能重复添加！");
                }
                tBCodeTypeService.save(tBCodeType);

                if (SrmipConstants.YES.equals(tBCodeType.getCodeType())) {
                    SrmipConstants.dicResearchMap.put(tBCodeType.getCode(), null);
                } else {
                    SrmipConstants.dicStandardMap.put(tBCodeType.getCode(), null);
                }

            } else {
                message = "更新成功!";
                TBCodeTypeEntity queryCodeType = systemService.get(TBCodeTypeEntity.class, tBCodeType.getId());
                String oldCode = queryCodeType.getCode();
                queryCodeType.setCode(tBCodeType.getCode());
                queryCodeType.setName(tBCodeType.getName());
                tBCodeTypeService.updateEntitie(queryCodeType);

                if (!oldCode.equals(queryCodeType.getCode())) {
                    Map<String, Object> map;
                    if (SrmipConstants.YES.equals(tBCodeType.getCodeType())) {
                        map = SrmipConstants.dicResearchMap.remove(oldCode);
                        SrmipConstants.dicResearchMap.put(queryCodeType.getCode(), map);
                    } else {
                        map = SrmipConstants.dicStandardMap.remove(oldCode);
                        SrmipConstants.dicStandardMap.put(queryCodeType.getCode(), map);
                    }
                }
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            message = e.getMessage();
            j.setSuccess(false);
        } catch (Exception e) {
            e.printStackTrace();
            message = "保存基础标准代码类别失败!";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新基础标准代码类别表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "saveDetail")
    @ResponseBody
    public AjaxJson saveDetail(TBCodeDetailPage tBCodeDetailPage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = null;
        TBCodeDetailEntity parentCodeDetail = null;
        if (tBCodeDetailPage.getParentCode() != null) {
            parentCodeDetail = tBCodeTypeService.get(TBCodeDetailEntity.class, tBCodeDetailPage.getParentCode());
            if (parentCodeDetail == null) {
                throw new BusinessException("所属代码上级代码不存在!");
            }
            tBCodeDetailPage.setParentCodeDetail(parentCodeDetail);
        }

        if (StringUtils.isEmpty(tBCodeDetailPage.getId())) {
            TBCodeDetailEntity queryCodeDetail = tBCodeTypeService.findCodeDetailByCode(
                    tBCodeDetailPage.getCodeTypeId(), tBCodeDetailPage.getCode());
            if (queryCodeDetail != null) {
                message = "代码为[" + tBCodeDetailPage.getCode() + "]的参数值已存在，不能重复添加！";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            }
            message = "添加成功!";
            try {
            	TBCodeDetailEntity codeDetailEntity = new TBCodeDetailEntity();
                MyBeanUtils.copyBeanNotNull2Bean(tBCodeDetailPage, codeDetailEntity);
                tBCodeTypeService.save(codeDetailEntity);
                String parentId = codeDetailEntity.getCodeTypeId();
                TBCodeTypeEntity parent = systemService.get(TBCodeTypeEntity.class, parentId);
                Map<String, Object> map = null;
                if(SrmipConstants.YES.equals(parent.getCodeType())){
                	map = SrmipConstants.dicResearchMap.get(parent.getCode());
                	if(map == null){
                		map = new HashMap<String, Object>();
                		SrmipConstants.dicResearchMap.put(parent.getCode(), map);
                	}
                	map.put(codeDetailEntity.getCode(), codeDetailEntity.getName());
                	
                }else{
                	map = SrmipConstants.dicStandardMap.get(parent.getCode());
                	if(map != null){
                		map = new HashMap<String, Object>();
                		SrmipConstants.dicStandardMap.put(parent.getCode(), map);
                	}
                	map.put(codeDetailEntity.getCode(), codeDetailEntity.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = "添加基础标准代码参数值表失败!";
            }
        } else {
            message = "更新成功!";
            try {
                TBCodeDetailEntity codeDetailEntity = tBCodeTypeService.get(TBCodeDetailEntity.class,
                        tBCodeDetailPage.getId());
                String oldCode = codeDetailEntity.getCode();
                String name = codeDetailEntity.getName();
                MyBeanUtils.copyBeanNotNull2Bean(tBCodeDetailPage, codeDetailEntity);
                tBCodeTypeService.updateEntitie(codeDetailEntity);
                
                if(!oldCode.equals(codeDetailEntity.getCode()) || !name.equals(codeDetailEntity.getName())){
                	String parentId = codeDetailEntity.getCodeTypeId();
                    TBCodeTypeEntity parent = systemService.get(TBCodeTypeEntity.class, parentId);
                    Map<String, Object> map = null;
                    if(SrmipConstants.YES.equals(parent.getCodeType())){
                    	map = SrmipConstants.dicResearchMap.get(parent.getCode());
                    }else{
                    	map = SrmipConstants.dicStandardMap.get(parent.getCode());
                    }
                    map.remove(oldCode);
                	map.put(codeDetailEntity.getCode(), codeDetailEntity.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = "更新基础标准代码参数值表失败!";
            }
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 基础标准参数值表新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdateDetail")
    public ModelAndView goAddUpdateDetail(TBCodeDetailEntity tBCodeDetail, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBCodeDetail.getId())) {
            tBCodeDetail = tBCodeTypeService.getEntity(TBCodeDetailEntity.class, tBCodeDetail.getId());
        }
        // 查询上级代码
        CriteriaQuery cq = new CriteriaQuery(TBCodeDetailEntity.class);
        cq.eq("codeTypeId", tBCodeDetail.getCodeTypeId());
        cq.addOrder("sortFlag", SortDirection.asc);
        cq.eq("validFlag", "1");
        if (StringUtils.isNotEmpty(tBCodeDetail.getId())) {
            cq.notEq("id", tBCodeDetail.getId());
        }
        cq.add();
        List<TBCodeDetailEntity> codeDetailList = tBCodeTypeService.getListByCriteriaQuery(cq, false);
        req.setAttribute("parentDetailList", codeDetailList);
        req.setAttribute("tBCodeDetailPage", tBCodeDetail);
        return new ModelAndView("com/kingtake/base/code/tBCodeDetail");
    }

    /**
     * 基础标准代码类别表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TBCodeTypeEntity tBCodeType, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBCodeType.getId())) {
            tBCodeType = tBCodeTypeService.getEntity(TBCodeTypeEntity.class, tBCodeType.getId());
            req.setAttribute("tBCodeTypePage", tBCodeType);
        }
        if (StringUtils.isNotEmpty(tBCodeType.getCodeType())) {
            req.setAttribute("codeType", tBCodeType.getCodeType());
        }

        return new ModelAndView("com/kingtake/base/code/tBCodeType");
    }

    /**
     * 加载明细列表[基础标准代码参数值]
     * 
     * @return
     */
    @RequestMapping(params = "tBCodeDetailList")
    public ModelAndView tBCodeDetailList(TBCodeTypeEntity tBCodeType, HttpServletRequest req) {

        // ===================================================================================
        // 获取参数
        Object id0 = tBCodeType.getId();
        // ===================================================================================
        // 查询-基础标准代码参数值
        String hql0 = "from TBCodeDetailEntity where 1 = 1 AND codeTypeId = ? and validFlag=?";
        try {
            List<TBCodeDetailEntity> tBCodeDetailEntityList = systemService.findHql(hql0, id0, "1");
            req.setAttribute("tBCodeDetailList", tBCodeDetailEntityList);
            req.setAttribute("codeTypeId", id0);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/kingtake/base/code/tBCodeDetailList");
    }

    /**
     * 加载明细列表[基础标准代码参数值]
     * 
     * @return
     */
    @RequestMapping(params = "datagridDetailList")
    @ResponseBody
    public JSONArray datagridDetailList(TBCodeDetailEntity tBCodeDetail, HttpServletRequest req,
            HttpServletResponse response, TreeGrid treegrid) {
        // ===================================================================================
        // 获取参数
        String codeTypeId = tBCodeDetail.getCodeTypeId();
        //查询-基础标准代码参数值
        CriteriaQuery cq = new CriteriaQuery(TBCodeDetailEntity.class);
        if (treegrid.getId() != null) {
            cq.eq("parentCodeDetail.id", treegrid.getId());
        }
        if (treegrid.getId() == null) {
            cq.isNull("parentCodeDetail");
        }
        cq.eq("codeTypeId", codeTypeId);
        cq.eq("validFlag", "1");
        cq.addOrder("sortFlag", SortDirection.asc);
        cq.addOrder("code", SortDirection.asc);
        cq.add();
        List detailList = tBCodeTypeService.getListByCriteriaQuery(cq, false);
        TreeGridModel treeGridModel = new TreeGridModel();
        treeGridModel.setIcon("TSIcon_iconPath");
        treeGridModel.setIdField("id");
        treeGridModel.setTextField("code");
        treeGridModel.setParentId("parentCodeDetail.id");
        treeGridModel.setParentText("parentCodeDetail.code");
        treeGridModel.setChildList("codeDetails");
        treeGridModel.setOrder("sortFlag");
        treeGridModel.setSrc("validFlag");
        //        treeGridModel.setFunctionType("scaleFlag");
        treeGridModel.setCode("name");
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("codeTypeId", "codeTypeId");
        fieldMap.put("name", "name");
        fieldMap.put("memo", "memo");
        treeGridModel.setFieldMap(fieldMap);
        List<TreeGrid> treeGrids = systemService.treegrid(detailList, treeGridModel);

        JSONArray jsonArray = new JSONArray();
        for (TreeGrid treeGrid : treeGrids) {
            jsonArray.add(JSON.parse(treeGrid.toJson()));
        }
        return jsonArray;
    }

    @RequestMapping(params = "getDetailList")
    @ResponseBody
    public JSONArray getDetailList(HttpServletRequest req, HttpServletResponse response) {
        String codeTypeId = req.getParameter("codeTypeId");
        CriteriaQuery cq = new CriteriaQuery(TBCodeDetailEntity.class);
        cq.eq("codeTypeId", codeTypeId);
        cq.addOrder("sortFlag", SortDirection.asc);
        cq.add();
        List<TBCodeDetailEntity> list = systemService.getListByCriteriaQuery(cq, false);
        JSONArray array = (JSONArray) JSONArray.toJSON(list);
        return array;
    }

    /**
     * 删除基础标准代码类别表
     * 
     * @return
     */
    @RequestMapping(params = "doDelDetail")
    @ResponseBody
    public AjaxJson doDelDetail(TBCodeDetailEntity tBCodeDetail, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "基础标准代码参数值表删除成功";
        try {
        	tBCodeDetail = systemService.get(TBCodeDetailEntity.class, tBCodeDetail.getId());
            tBCodeTypeService.deleteDetail(tBCodeDetail);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            
            TBCodeTypeEntity parent = systemService.get(TBCodeTypeEntity.class, tBCodeDetail.getCodeTypeId());
            Map<String, Object> map = null;
            if(SrmipConstants.YES.equals(parent.getCodeType())){
            	map = SrmipConstants.dicResearchMap.get(parent.getCode());
            }else{
            	map = SrmipConstants.dicStandardMap.get(parent.getCode());
            }
            map.remove(tBCodeDetail.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            message = "基础标准代码类别表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(String codeType, HttpServletRequest req) {
        req.setAttribute("codeType", codeType);
        return new ModelAndView("com/kingtake/base/code/tBCodeType-upload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBCodeTypeEntity codeType, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBCodeTypeEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, codeType, request.getParameterMap());
        List<TBCodeTypeEntity> tPortalLayouts = tBCodeTypeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "标准代码类型列表");
        modelMap.put(NormalExcelConstants.CLASS, TBCodeTypeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("标准代码类型列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPortalLayouts);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBCodeTypeEntity codeTypeEntity, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        List<TBCodeTypeEntity> entities = new ArrayList<TBCodeTypeEntity>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createBy", "张三");
        modelMap.put(TemplateExcelConstants.FILE_NAME, "标准代码类别列表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("export/template/codeTypeTemplate.xls"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        modelMap.put(TemplateExcelConstants.CLASS, TBCodeTypeEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, entities);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(String codeType, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(2);
            params.setNeedSave(true);
            try {
                List<TBCodeTypeEntity> codeTypeEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TBCodeTypeEntity.class, params);
                for (TBCodeTypeEntity codeTypeEntity : codeTypeEntitys) {
                    codeTypeEntity.setCodeType(codeType);
                }
                tBCodeTypeService.saveCodeTypeForImport(codeTypeEntitys);
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
     * 获取组装下拉列表树形结构.
     * 
     * @param codeTypeEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getCodeTypeTree")
    @ResponseBody
    public List<ComboTree> getCodeTypeTree(TBCodeTypeEntity codeTypeEntity, HttpServletRequest request) {
        boolean lazy = true;
        String lazyParam = request.getParameter("lazy");
        if (StringUtils.isNotBlank(lazyParam)) {
            lazy = Boolean.valueOf(lazyParam);
        }
        List<TBCodeDetailEntity> codeDetailList = tBCodeTypeService.getCodeDetailByCodeType(codeTypeEntity);
        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        ComboTreeModel comboTreeModel = new ComboTreeModel("code", "name", "codeDetails");
        comboTrees = systemService.ComboTree(codeDetailList, comboTreeModel, null, lazy);
        return comboTrees;
    }

    /**
     * 获取组装下拉列表树形结构.
     * 
     * @param codeTypeEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getCodeTypeCombobox")
    @ResponseBody
    public List<ComboBox> getCodeTypeCombobox(TBCodeTypeEntity codeTypeEntity, ComboBox combobox,
            HttpServletRequest request) {
        List<TBCodeDetailEntity> codeDetailList = tBCodeTypeService.getCodeDetailByCodeType(codeTypeEntity);
        List<ComboBox> comboList = new ArrayList<ComboBox>();
        for (TBCodeDetailEntity codeDetail : codeDetailList) {
            ComboBox tmp = new ComboBox();
            tmp.setId(codeDetail.getCode());
            tmp.setText(codeDetail.getName());
            comboList.add(tmp);
        }
        return comboList;
    }

    /**
     * 获取组装下拉列表树形结构.
     * 
     * @param codeTypeEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getCodeTypeList")
    @ResponseBody
    public List<TBCodeDetailEntity> getCodeTypeList(TBCodeTypeEntity codeTypeEntity, ComboBox combobox,
            HttpServletRequest request) {
        List<TBCodeDetailEntity> codeDetailList = tBCodeTypeService.getCodeDetailByCodeType(codeTypeEntity);
        return codeDetailList;
    }

    /**
     * 根据代码code获得value
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "getValue")
    @ResponseBody
    public void getValue(HttpServletRequest request, HttpServletResponse response) {
        String codeType = request.getParameter("codeType").trim();
        String code = request.getParameter("code").trim();
        String key = request.getParameter("value").trim();
        Session session = systemService.getSession();
        TBCodeTypeEntity codeTypeEntity = (TBCodeTypeEntity) session.createCriteria(TBCodeTypeEntity.class)
                .add(Restrictions.eq("code", code)).add(Restrictions.eq("codeType", codeType)).uniqueResult();
        List<TBCodeDetailEntity> details = session.createCriteria(TBCodeDetailEntity.class)
                .add(Restrictions.eq("codeTypeId", codeTypeEntity.getId())).add(Restrictions.eq("code", key)).list();
        TagUtil.response(response, TagUtil.listtojson(new String[] { "name" }, details));
    }

    @RequestMapping(params = "typeCombo")
    @ResponseBody
    public List<Map<String, Object>> typeCombo(HttpServletRequest request, HttpServletResponse response) {    	
        String codeType = request.getParameter("codeType") == null ? "" : request.getParameter("codeType");
        String code = request.getParameter("code") == null ? "" : request.getParameter("code");
        String sql = "SELECT D.CODE, D.NAME FROM T_B_CODE_DETAIL D, T_B_CODE_TYPE T \n"
                + "WHERE D.CODE_TYPE_ID = T.ID \n" + "AND T.CODE_TYPE = ? AND T.CODE = ? AND D.VALID_FLAG = ?";
        List<Map<String, Object>> result = systemService.findForJdbc(sql, codeType, code, SrmipConstants.YES);
        return result;          
    }
    
    
    @RequestMapping(params = "typeComboTree")
    @ResponseBody
    public JSONArray typeComboTree(HttpServletRequest request, HttpServletResponse response) {  
        String codeType = request.getParameter("codeType") == null ? "" : request.getParameter("codeType");
        String code = request.getParameter("code") == null ? "" : request.getParameter("code");
        String sql = "SELECT D.CODE, D.NAME,D.PARENT_CODE FROM T_B_CODE_DETAIL D, T_B_CODE_TYPE T \n"
                + "WHERE D.CODE_TYPE_ID = T.ID \n" + "AND T.CODE_TYPE = ? AND T.CODE = ? AND D.VALID_FLAG = ? AND (PARENT_CODE IS NULL OR PARENT_CODE = '') ORDER BY D.CODE";
        List<Map<String, Object>> codeDetailParent = systemService.findForJdbc(sql, codeType, code, SrmipConstants.YES);
        
        sql = "SELECT D.CODE, D.NAME,D.PARENT_CODE FROM T_B_CODE_DETAIL D, T_B_CODE_TYPE T \n"
              + "WHERE D.CODE_TYPE_ID = T.ID \n" + "AND T.CODE_TYPE = ? AND T.CODE = ? AND D.VALID_FLAG = ? AND PARENT_CODE IS NOT NULL ORDER BY D.CODE";
        List<Map<String, Object>> codeDetailList = systemService.findForJdbc(sql, codeType, code, SrmipConstants.YES);
        
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> map : codeDetailParent) {
        	
        	TBCodeDetailEntity codedetailEntity = new TBCodeDetailEntity();
        	codedetailEntity.setCode((String)map.get("CODE"));
        	codedetailEntity.setName((String)map.get("NAME"));
        	
            JSONObject pJson = getTreeGrid(codedetailEntity,codeDetailList);
            jsonArray.add(pJson);
        }
        
                
        return jsonArray;
    }
    
    
    private JSONObject getTreeGrid(TBCodeDetailEntity parent,List<Map<String, Object>> codeDetailList) {
        JSONObject json = new JSONObject();
        json.put("id", parent.getCode());
        json.put("text", parent.getName());
        
        JSONArray array = new JSONArray();
        for (Map<String, Object> map : codeDetailList) {        	
        	if (parent.getCode().equals((String)map.get("PARENT_CODE")))
        	{
            	TBCodeDetailEntity codedetailEntity = new TBCodeDetailEntity();
            	codedetailEntity.setCode((String)map.get("CODE"));
            	codedetailEntity.setName((String)map.get("NAME"));
            	
            	
            	JSONObject subJson = getTreeGrid(codedetailEntity,codeDetailList);
                array.add(subJson);        	
        	}
        }
        if (array.size() > 0)
        {
        	json.put("children", array);
        }
                        
        return json;        
    }
    

    @RequestMapping(params = "getComboboxListByKey")
    public void getComboboxListByKey(HttpServletRequest request, HttpServletResponse response) {
        TBCodeTypeEntity codeTypeEntity = new TBCodeTypeEntity();
        String code = request.getParameter("code");
        String codeType = request.getParameter("codeType");
        String validFlag = request.getParameter("validFlag");
        if (StringUtils.isNotEmpty(code)) {
            codeTypeEntity.setCode(code);
        }
        if (StringUtils.isNotEmpty(codeType)) {
            codeTypeEntity.setCodeType(codeType);
        }
        if (StringUtils.isNotEmpty(validFlag)) {
            codeTypeEntity.setValidFlag(validFlag);
        }
        List<TBCodeDetailEntity> codeDetailList = this.tBCodeTypeService.getCodeByCodeType(codeTypeEntity);
        TagUtil.response(response, JSONHelper.collection2json(codeDetailList));
    }

}
