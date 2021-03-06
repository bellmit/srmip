package com.kingtake.base.controller.budget;

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
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MutiLangUtil;
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
import com.kingtake.base.entity.budget.TBApprovalBudgetRelaEntity;
import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.base.service.budget.TBApprovalBudgetRelaServiceI;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;

/**
 * @Title: Controller
 * @Description: ???????????????
 * @author onlineGenerator
 * @date 2015-07-15 15:24:33
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBApprovalBudgetRelaController")
public class TBApprovalBudgetRelaController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBApprovalBudgetRelaController.class);

    @Autowired
    private TBApprovalBudgetRelaServiceI tBApprovalBudgetRelaService;
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
     * ????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBApprovalBudgetRela")
    public ModelAndView tBApprovalBudgetRela(HttpServletRequest request) {
        String projectType = request.getParameter("projectType");
        request.setAttribute("projectType", projectType);
        //?????????????????????????????????
        TBProjectTypeEntity tBProjectTypeEntity =systemService.getEntity(TBProjectTypeEntity.class, projectType);
        if(tBProjectTypeEntity != null && !tBProjectTypeEntity.getId().isEmpty())
          request.setAttribute("projectTypeName",tBProjectTypeEntity.getProjectTypeName());//??????????????????
        else
          request.setAttribute("projectTypeName", ProjectConstant.planContractMap.get(projectType));//???????????????????????????

        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRelaList");
    }
    
    /**
     * ????????????????????? ???????????? ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBApprovalBudgetRelaFromFund")
    public ModelAndView tBApprovalBudgetRelaFromFund(HttpServletRequest request) {
        String fundProperty = request.getParameter("fundProperty");
        request.setAttribute("fundProperty", fundProperty);
        
        TBFundsPropertyEntity tBFundsPropertyEntity =systemService.getEntity(TBFundsPropertyEntity.class, fundProperty);
        if(tBFundsPropertyEntity != null && !tBFundsPropertyEntity.getId().isEmpty())
          request.setAttribute("fundPropertyName",tBFundsPropertyEntity.getFundsName());//??????????????????

        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRelaList");
    }

    /**
     * ???????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBApprovalBudgetRelaTab")
    public ModelAndView tBApprovalBudgetRelaTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRelaTab");
    }

    /**
     * ??????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBApprovalCostCategoryTab")
    public ModelAndView tBApprovalCostCategoryTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/base/budget/tBApprovalCostCategoryTab");
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
    public void datagrid(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBApprovalBudgetRelaEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBApprovalBudgetRela,
                request.getParameterMap());
        try {
            //???????????????????????????
            String projectType = request.getParameter("projectType");
            cq.eq("projApproval", projectType);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBApprovalBudgetRelaService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridTree")
    @ResponseBody
    public Object datagridTree(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest request,
            HttpServletResponse response, TreeGrid treegrid) {
        //?????????????????????
        CriteriaQuery cq = new CriteriaQuery(TBApprovalBudgetRelaEntity.class);
        String projApproval = tBApprovalBudgetRela.getProjApproval();
        /*
         * if ("yes".equals(request.getParameter("isSearch"))) { treegrid.setId(null); tBApprovalBudgetRela.setId(null);
         * }
         */
        /*
         * if (tBApprovalBudgetRela.getBudgetNae() != null) {
         * org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBApprovalBudgetRela); }
         */
        if (treegrid.getId() != null) {
            cq.eq("TBApprovalBudgetRelaEntity.id", treegrid.getId());
        }
        if (treegrid.getId() == null) {
            cq.isNull("TBApprovalBudgetRelaEntity");
        }
        cq.eq("projApproval", projApproval);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TBApprovalBudgetRelaEntity> tBApprovalBudgetRelaList = systemService.getListByCriteriaQuery(cq, false);

        TreeGridModel treeGridModel = new TreeGridModel();

        treeGridModel.setIcon("TSIcon_iconPath");
        treeGridModel.setIdField("id");
        treeGridModel.setTextField("budgetNae");
        treeGridModel.setParentId("TBApprovalBudgetRelaEntity.id");
        treeGridModel.setParentText("TBApprovalBudgetRelaEntity.budgetNae");
        treeGridModel.setChildList("TBApprovalBudgetRelaEntitys");
        treeGridModel.setOrder("sortCode");
        treeGridModel.setSrc("memo");

        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("scaleFlag", "scaleFlag");
        fieldMap.put("addChildFlag", "addChildFlag");
        fieldMap.put("showFlag", "showFlag");
        treeGridModel.setFieldMap(fieldMap);

        List<TreeGrid> treeGrids = systemService.treegrid(tBApprovalBudgetRelaList, treeGridModel);
        JSONArray jsonArray = new JSONArray();
        for (TreeGrid treeGrid : treeGrids) {
            jsonArray.add(JSON.parse(treeGrid.toJson()));
        }
        return jsonArray;
    }

    /**
     * ?????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBApprovalBudgetRela = systemService.getEntity(TBApprovalBudgetRelaEntity.class, tBApprovalBudgetRela.getId());
        message = "???????????????????????????";
        try {
            tBApprovalBudgetRelaService.delete(tBApprovalBudgetRela);
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
                TBApprovalBudgetRelaEntity tBApprovalBudgetRela = systemService.getEntity(
                        TBApprovalBudgetRelaEntity.class, id);
                tBApprovalBudgetRelaService.delete(tBApprovalBudgetRela);
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
    public AjaxJson doAdd(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        try {
            String sortCode = getSortCode(tBApprovalBudgetRela);
            tBApprovalBudgetRela.setSortCode(sortCode);
            tBApprovalBudgetRelaService.save(tBApprovalBudgetRela);
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
    public AjaxJson doUpdate(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "???????????????????????????";
        TBApprovalBudgetRelaEntity t = tBApprovalBudgetRelaService.get(TBApprovalBudgetRelaEntity.class,
                tBApprovalBudgetRela.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tBApprovalBudgetRela, t);
            tBApprovalBudgetRelaService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????
     * 
     * @param id
     * @return
     */
    private String getSortCode(TBApprovalBudgetRelaEntity tBApprovalBudgetRela) {
        String sortCode = "01";
        String sql = null;
        String id = tBApprovalBudgetRela.getTBApprovalBudgetRelaEntity().getId();
        TBApprovalBudgetRelaEntity parentBudget = null;
        List<Map<String, Object>> list = null;
        if (StringUtils.isEmpty(id)) {
            sql = "select max(t.sort_code) sortCode from t_b_approval_budget_rela t where t.parent_id is null and t.proj_approval=?";
            list = this.systemService.findForJdbc(sql, tBApprovalBudgetRela.getProjApproval());
            if (list.size() > 0) {
                sortCode = (String) list.get(0).get("SORTCODE");
                if (StringUtils.isEmpty(sortCode)) {
                    sortCode = "01";
                } else {
                    int num = Integer.valueOf(sortCode);
                    num++;
                    String suffix = String.valueOf(num);
                    if (num < 10) {
                        suffix = "0" + num;
                    }
                    sortCode = suffix;
                }
            }

        } else {
            parentBudget = this.systemService.get(TBApprovalBudgetRelaEntity.class, id);
            String parentCode = parentBudget == null ? "" : parentBudget.getSortCode();
            sql = "select max(t.sort_code) sortCode from t_b_approval_budget_rela t where t.parent_id=?";
            list = this.systemService.findForJdbc(sql, id);
            if (list.size() > 0) {
                sortCode = (String) list.get(0).get("SORTCODE");
                if (StringUtils.isEmpty(sortCode)) {
                    sortCode = parentCode + "01";
                } else {
                    String code = sortCode.substring(sortCode.length() - 2, sortCode.length());
                    int num = Integer.valueOf(code);
                    num++;
                    String suffix = String.valueOf(num);
                    if (num < 10) {
                        suffix = "0" + num;
                    }
                    sortCode = parentCode + suffix;
                }
            }
        }
        return sortCode;
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBApprovalBudgetRela.getId())) {
            tBApprovalBudgetRela = tBApprovalBudgetRelaService.getEntity(TBApprovalBudgetRelaEntity.class,
                    tBApprovalBudgetRela.getId());
        } else {
            String projectType = tBApprovalBudgetRela.getProjApproval();
            if (StringUtil.isNotEmpty(projectType)) {
                //req.setAttribute("projectTypeName", ProjectConstant.planContractMap.get(projectType));
                //?????????????????????????????????
                TBProjectTypeEntity tBProjectTypeEntity = systemService.getEntity(TBProjectTypeEntity.class,projectType);
                if(tBProjectTypeEntity != null && !tBProjectTypeEntity.getId().isEmpty())
                  req.setAttribute("projectTypeName", tBProjectTypeEntity.getProjectTypeName());//??????????????????
                else
                  req.setAttribute("projectTypeName", ProjectConstant.planContractMap.get(projectType));//???????????????????????????
            }
        }
        req.setAttribute("tBApprovalBudgetRelaPage", tBApprovalBudgetRela);
        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRela-add");
    }
    
    /**
     * ????????????????????????????????????????????????????????????
     * 
     * @return
     */
   /* @RequestMapping(params = "goAddFromFund")
    public ModelAndView goAddFromFund(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBApprovalBudgetRela.getId())) {
            tBApprovalBudgetRela = tBApprovalBudgetRelaService.getEntity(TBApprovalBudgetRelaEntity.class,
                    tBApprovalBudgetRela.getId());
        } else {
            String fundProperty = tBApprovalBudgetRela.getProjApproval();
            if (StringUtil.isNotEmpty(fundProperty)) {
            	TBFundsPropertyEntity tBFundsPropertyEntity = systemService.getEntity(TBFundsPropertyEntity.class,fundProperty);
                if(tBFundsPropertyEntity != null && !tBFundsPropertyEntity.getId().isEmpty())
                  req.setAttribute("fundPropertyName", tBFundsPropertyEntity.getFundsName());//??????????????????
            }
        }
        req.setAttribute("tBApprovalBudgetRelaPage", tBApprovalBudgetRela);
        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRela-add");
    }*/
    
    /**
     * ????????????????????????????????????????????????????????????
     * 
     * @return
     */
    //2017???1???16??????
    @RequestMapping(params = "goAddFromFund")
    public ModelAndView goAddFromFund(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest req) {
    	String fundProperty=req.getParameter("fundProperty");
    	if(StringUtil.isNotEmpty(fundProperty))
    	{
    		 TBFundsPropertyEntity tBFundsPropertyEntity = systemService.getEntity(TBFundsPropertyEntity.class,fundProperty);
    	        if(tBFundsPropertyEntity != null && !tBFundsPropertyEntity.getId().isEmpty())
    	           req.setAttribute("fundPropertyName", tBFundsPropertyEntity.getFundsName());//??????????????????
    	        tBApprovalBudgetRela.setProjApproval(fundProperty);
    	}
        req.setAttribute("tBApprovalBudgetRelaPage", tBApprovalBudgetRela);
        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRela-add");
    }
    
    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBApprovalBudgetRela.getId())) {
            tBApprovalBudgetRela = tBApprovalBudgetRelaService.getEntity(TBApprovalBudgetRelaEntity.class,
                    tBApprovalBudgetRela.getId());
            String projectType = tBApprovalBudgetRela.getProjApproval();
            if (StringUtil.isNotEmpty(projectType)) {
                //req.setAttribute("projectTypeName", ProjectConstant.planContractMap.get(projectType));
                //?????????????????????????????????
                TBProjectTypeEntity tBProjectTypeEntity = systemService.getEntity(TBProjectTypeEntity.class, projectType);
                if(tBProjectTypeEntity != null && !tBProjectTypeEntity.getId().isEmpty())
                  req.setAttribute("projectTypeName", tBProjectTypeEntity.getProjectTypeName());//??????????????????
                else
                  req.setAttribute("projectTypeName", ProjectConstant.planContractMap.get(projectType));//???????????????????????????
            }
            req.setAttribute("tBApprovalBudgetRelaPage", tBApprovalBudgetRela);
        }
        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRela-update");
    }
    
    /**
     * ??????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateFromFund")
    public ModelAndView goUpdateFromFund(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBApprovalBudgetRela.getId())) {
            tBApprovalBudgetRela = tBApprovalBudgetRelaService.getEntity(TBApprovalBudgetRelaEntity.class,
                    tBApprovalBudgetRela.getId());
            String fundProperty = tBApprovalBudgetRela.getProjApproval();
            if (StringUtil.isNotEmpty(fundProperty)) {
            	TBFundsPropertyEntity tBFundsPropertyEntity = systemService.getEntity(TBFundsPropertyEntity.class, fundProperty);
                if(tBFundsPropertyEntity != null && !tBFundsPropertyEntity.getId().isEmpty())
                  req.setAttribute("fundPropertyName", tBFundsPropertyEntity.getFundsName());//??????????????????
            }
            req.setAttribute("tBApprovalBudgetRelaPage", tBApprovalBudgetRela);
        }
        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRela-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/base/budget/tBApprovalBudgetRelaUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBApprovalBudgetRelaEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBApprovalBudgetRela,
                request.getParameterMap());
        List<TBApprovalBudgetRelaEntity> tBApprovalBudgetRelas = this.tBApprovalBudgetRelaService
                .getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "???????????????");
        modelMap.put(NormalExcelConstants.CLASS, TBApprovalBudgetRelaEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("?????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBApprovalBudgetRelas);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBApprovalBudgetRelaEntity tBApprovalBudgetRela, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "???????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBApprovalBudgetRelaEntity.class);
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
                List<TBApprovalBudgetRelaEntity> listTBApprovalBudgetRelaEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBApprovalBudgetRelaEntity.class, params);
                for (TBApprovalBudgetRelaEntity tBApprovalBudgetRela : listTBApprovalBudgetRelaEntitys) {
                    tBApprovalBudgetRelaService.save(tBApprovalBudgetRela);
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
     * ?????????????????????-combotree
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "getApprovalBudgetRelaTree")
    @ResponseBody
    public List<ComboTree> getApprovalBudgetRelaTree(HttpServletRequest request, ComboTree comboTree) {
        List<TBApprovalBudgetRelaEntity> tBApprovalBudgetRelaList = null;
        String projApproval = request.getParameter("projApproval");
        CriteriaQuery cq = new CriteriaQuery(TBApprovalBudgetRelaEntity.class);
        if (comboTree.getId() != null) {
            cq.eq("TBApprovalBudgetRelaEntity.id", comboTree.getId());
        }
        if (comboTree.getId() == null) {
            cq.isNull("TBApprovalBudgetRelaEntity");
        }
        cq.eq("projApproval", projApproval);
        //        cq.eq("addChildFlag", SrmipConstants.YES);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        tBApprovalBudgetRelaList = systemService.getListByCriteriaQuery(cq, false);
        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        ComboTreeModel comboTreeModel = new ComboTreeModel("id", "budgetNae", "TBApprovalBudgetRelaEntitys");
        comboTrees = systemService.ComboTree(tBApprovalBudgetRelaList, comboTreeModel, null, true);

        ComboTree node = new ComboTree();
        node.setText("---???---");
        comboTrees.add(0, node);

        MutiLangUtil.setMutiTree(comboTrees);
        return comboTrees;
    }

    /**
     * ??????????????????????????????
     */
    @RequestMapping(params = "getBudgetCombobox")
    @ResponseBody
    public List<ComboBox> getBudgetCombobox(HttpServletRequest request, ComboTree comboTree) {
        String projectType = request.getParameter("projectType");
        CriteriaQuery cq = new CriteriaQuery(TBApprovalBudgetRelaEntity.class);
        if (StringUtil.isNotEmpty(projectType)) {
            cq.eq("projApproval", projectType);
        }
        cq.eq("scaleFlag", SrmipConstants.YES);
        cq.add();
        List<TBApprovalBudgetRelaEntity> tBApprovalBudgetRelas = systemService.getListByCriteriaQuery(cq, false);

        List<ComboBox> boxes = new ArrayList<ComboBox>();
        if (tBApprovalBudgetRelas != null && tBApprovalBudgetRelas.size() > 0) {
            for (TBApprovalBudgetRelaEntity tBApprovalBudgetRela : tBApprovalBudgetRelas) {
                ComboBox box = new ComboBox();
                box.setId(tBApprovalBudgetRela.getId());
                box.setText(tBApprovalBudgetRela.getBudgetNae());
                boxes.add(box);
            }
        }

        return boxes;
    }

    /**
     * ?????????????????????????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goProjectTypeInfoRela")
    public ModelAndView goProjectTypeInfoRela(HttpServletRequest request, ComboTree comboTree) {
        String typeId = request.getParameter("typeId");
        request.setAttribute("projectType", typeId);
        String sql = "select t.t_catalog_id catalogId from t_pm_type_catalog_rela t where t.t_type_id=?";
        List<Map<String, Object>> dataList = this.systemService.findForJdbc(sql, typeId);
        if (dataList.size() > 0) {
        StringBuffer sb = new StringBuffer();
        for (Map<String, Object> map : dataList) {
            sb.append(map.get("catalogId")).append(",");
        }
            String typeCatalogRelaStr = sb.toString();
            typeCatalogRelaStr = typeCatalogRelaStr.substring(0, typeCatalogRelaStr.length() - 1);
            request.setAttribute("typeCatalogRelaStr", typeCatalogRelaStr);
        }
        return new ModelAndView("com/kingtake/base/sideccatalog/sidecatalogProjectTypeRelaSet");
    }

    /**
     * ?????? ???????????????????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveProjectTypeInfoRela")
    @ResponseBody
    public AjaxJson saveProjectTypeInfoRela(HttpServletRequest request, ComboTree comboTree) {
        AjaxJson json = new AjaxJson();
        String catalogListStr = request.getParameter("catalogListStr");
        String projectTypeId = request.getParameter("id");
        try {
            if (StringUtils.isNotEmpty(catalogListStr)) {
                this.tBApprovalBudgetRelaService.saveProjectTypeInfoRela(projectTypeId, catalogListStr);
                json.setMsg("??????????????????????????????????????????");
            }
        } catch (Exception e) {
            json.setMsg("??????????????????????????????????????????");
            json.setSuccess(false);
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveCatalogRelaFromCopy")
    @ResponseBody
    public AjaxJson saveCatalogRelaFromCopy(HttpServletRequest request, ComboTree comboTree) {
      AjaxJson json = new AjaxJson();
      String fromProjectTypeId = request.getParameter("fromProjectTypeId");
      String projectTypeId = request.getParameter("projectTypeId");
      String typeCatalogRelaStr = "";
      try {
        if (StringUtils.isNotEmpty(fromProjectTypeId)) {
          typeCatalogRelaStr = this.tBApprovalBudgetRelaService.saveCatalogRelaFromCopy(projectTypeId, fromProjectTypeId);
          json.setMsg("?????????????????????");
          json.setObj(typeCatalogRelaStr);
        }
      } catch (Exception e) {
        json.setMsg("?????????????????????");
        json.setSuccess(false);
        e.printStackTrace();
      }
      return json;
    }
    
    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveApprovalBudgetFromCopy")
    @ResponseBody
    public AjaxJson saveApprovalBudgetFromCopy(HttpServletRequest request, ComboTree comboTree) {
      AjaxJson json = new AjaxJson();
      String fromProjectTypeId = request.getParameter("fromProjectTypeId");
      String id = request.getParameter("id");
      try {
        if (StringUtils.isNotEmpty(fromProjectTypeId)) {
          this.tBApprovalBudgetRelaService.saveApprovalBudgetFromCopy(id, fromProjectTypeId);
          json.setMsg("?????????????????????");
        }
      } catch (Exception e) {
        json.setMsg("?????????????????????");
        json.setSuccess(false);
        e.printStackTrace();
      }
      return json;
    }
    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "saveApprovalBudgetFromCopyFund")
    @ResponseBody
    public AjaxJson saveApprovalBudgetFromCopyFund(HttpServletRequest request, ComboTree comboTree) {
      AjaxJson json = new AjaxJson();
      String fromFundPropertyId = request.getParameter("fromFundPropertyId");
      String id = request.getParameter("id");
      try {
        if (StringUtils.isNotEmpty(fromFundPropertyId)) {
          this.tBApprovalBudgetRelaService.saveApprovalBudgetFromCopyFund(id, fromFundPropertyId);
          json.setMsg("?????????????????????");
        }
      } catch (Exception e) {
        json.setMsg("?????????????????????");
        json.setSuccess(false);
        e.printStackTrace();
      }
      return json;
    }
}
