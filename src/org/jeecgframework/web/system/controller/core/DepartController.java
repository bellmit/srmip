package org.jeecgframework.web.system.controller.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.DepartService;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.base.service.rtx.TBSyncUserDeptTempServiceI;
import com.kingtake.common.constant.DepartConstant;
import com.kingtake.common.service.CommonUserServiceI;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.thesis.TBDepartThesisSecretCodeEntity;

/**
 * ?????????????????????
 * 
 * @author ?????????
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/departController")
public class DepartController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DepartController.class);
    private UserService userService;
    private SystemService systemService;
    private String message;
    @Autowired
    private DepartService departService;
    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;

    @Autowired
    private TBSyncUserDeptTempServiceI tBSyncUserDeptTempService;

    @Autowired
    private CommonUserServiceI commonUserService;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "depart")
    public ModelAndView depart(HttpServletRequest request, String scientificInstitutionFlag) {
        request.setAttribute("scientificInstitutionFlag", scientificInstitutionFlag);
        return new ModelAndView("system/depart/departList");
    }

    /**
     * easyuiAJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    // update-start--Author:zhangguoming Date:20140825 for??????????????????????????????????????????
    /**
     * ???????????????
     * <ul>
     * ?????????????????????????????????
     * <li>??????????????? ????????????</li>
     * </ul>
     * <ul>
     * ??????????????????????????????
     * <li>??????????????? ????????????</li>
     * </ul>
     * <ul>
     * ??????????????? ?????????????????? ??? ??????????????????
     * <li>?????? ????????????-?????? ??????</li>
     * <li>?????? ???????????? ??????</li>
     * </ul>
     * 
     * @return ?????????????????????
     */
    /*
     * @RequestMapping(params = "del")
     * 
     * @ResponseBody public AjaxJson del(TSDepart depart, HttpServletRequest request) { AjaxJson j = new AjaxJson();
     * depart = systemService.getEntity(TSDepart.class, depart.getId()); message =
     * MutiLangUtil.paramDelSuccess("common.department"); if (depart.getTSDeparts().size() == 0) { Long userCount =
     * systemService.getCountForJdbc("select count(1) from t_s_user_org where org_id='" + depart.getId() + "'"); if
     * (userCount == 0) { // ?????????????????????????????????????????????????????????????????? systemService.executeSql("delete from t_s_role_org where org_id=?",
     * depart.getId()); systemService.delete(depart);
     * 
     * systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO); } } else { message =
     * MutiLangUtil.paramDelFail("common.department"); }
     * 
     * j.setMsg(message); return j; }
     */

    // update-start--Author:zhangguoming Date:20140825 for??????????????????????????????????????????
    /**
     * ?????????????????????
     * <ul>
     * ?????????????????????????????????
     * <li>??????????????? ????????????</li>
     * </ul>
     * <ul>
     * ??????????????????????????????
     * <li>??????????????? ????????????</li>
     * </ul>
     * <ul>
     * ??????????????? ?????????????????? ??? ??????????????????
     * <li>?????? ????????????-?????? ??????</li>
     * <li>?????? ???????????? ??????</li>
     * </ul>
     * 
     * @return ?????????????????????
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(TSDepart depart, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        depart = systemService.getEntity(TSDepart.class, depart.getId());
        message = MutiLangUtil.paramDelSuccess("common.department,");
        if (depart.getTSDeparts() != null && depart.getTSDeparts().size() > 0) {
            List<TSDepart> subs = new ArrayList<TSDepart>();
            for (TSDepart sub : depart.getTSDeparts()) {
                if ("0".equals(sub.getValidFlag())) {
                    continue;
                }
                subs.add(sub);
            }
            depart.setTSDeparts(subs);
        }
        if (depart.getTSDeparts().size() == 0) {
            Long userCount = systemService.getCountForJdbc("select count(1) from t_s_user_org where org_id='"
                    + depart.getId() + "'");
            if (userCount == 0) { // ??????????????????????????????????????????????????????????????????
                //systemService.executeSql("delete from t_s_role_org where org_id=?", depart.getId());
                departService.deleteMain(depart);

                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            } else {
                message = "????????????????????????????????????";
            }
        } else {
            message = MutiLangUtil.paramDelFail("common.department,??????????????????????????????");
        }
        if (j.isSuccess()) {
            tBSyncUserDeptTempService.addTempDeleteDepart(depart);//???????????????????????????RTX?????????
        }
        j.setMsg(message);
        return j;
    }

    // update-end--Author:zhangguoming Date:20140825 for??????????????????????????????????????????

    public void upEntity(TSDepart depart) {
        List<TSUser> users = systemService.findByProperty(TSUser.class, "TSDepart.id", depart.getId());
        if (users.size() > 0) {
            for (TSUser tsUser : users) {
                // tsUser.setTSDepart(null);
                // systemService.saveOrUpdate(tsUser);
                systemService.delete(tsUser);
            }
        }
    }

    /**
     * ????????????
     * 
     * @param depart
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(TSDepart depart, HttpServletRequest request) {
        // ??????????????????
        String pid = request.getParameter("TSPDepart.id");
        if (pid.equals("")) {
            depart.setTSPDepart(null);
        }
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(depart.getId())) {
            message = MutiLangUtil.paramUpdSuccess("common.department");
            userService.saveOrUpdate(depart);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            message = MutiLangUtil.paramAddSuccess("common.department");
            userService.save(depart);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }

        j.setMsg(message);
        return j;
    }

    /*
     * @RequestMapping(params = "generateOrgCode")
     * 
     * @ResponseBody public AjaxJson generateOrgCode(HttpServletRequest req) { AjaxJson j = new AjaxJson(); String id =
     * StringUtil.getEncodePra(req.getParameter("id")); String pid = StringUtil.getEncodePra(req.getParameter("pid"));
     * String orgCode = systemService.generateOrgCode(id, pid); String floor = ""; j.setMsg(floor); return j; }
     */

    @RequestMapping(params = "add")
    public ModelAndView add(TSDepart depart, HttpServletRequest req) {
        // ??????????????????
        String pid = req.getParameter("TSPDepart.id");
        if (pid.equals("")) {
            depart.setTSPDepart(null);
        }

        String scientificInstitutionFlag = depart.getScientificInstitutionFlag();
        req.setAttribute("scientificInstitutionFlag", scientificInstitutionFlag);
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        req.setAttribute("departList", departList);

        // ??????if?????????????????????????????????
        // if (StringUtil.isNotEmpty(depart.getId())) {
        // TSDepart tspDepart = new TSDepart();
        // TSDepart tsDepart = new TSDepart();
        // depart = systemService.getEntity(TSDepart.class, depart.getId());
        // tspDepart.setId(depart.getId());
        // tspDepart.setDepartname(depart.getDepartname());
        // tsDepart.setTSPDepart(tspDepart);
        // req.setAttribute("depart", tsDepart);
        // }
        /*
         * if(!scientificInstitutionFlag.equals("1")){ req.setAttribute("pid", depart.getId()); }
         */
        if (depart.getTSPDepart() != null && depart.getTSPDepart().getId() != null) {
            depart.setTSPDepart((TSDepart) systemService.getEntity(TSDepart.class, depart.getTSPDepart().getId()));

            req.setAttribute("depart", depart);
        }
        // req.setAttribute("pid", depart.getId());

        List<String> codeIdList = new ArrayList<String>();
        req.setAttribute("codeIdList", JSON.toJSON(codeIdList));

        return new ModelAndView("system/depart/depart");
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "update")
    public ModelAndView update(TSDepart depart, HttpServletRequest req) {
        String scientificInstitutionFlag = depart.getScientificInstitutionFlag();
        req.setAttribute("scientificInstitutionFlag", scientificInstitutionFlag);

        List<TSDepart> departList = systemService.getList(TSDepart.class);
        req.setAttribute("departList", departList);
        if (StringUtil.isNotEmpty(depart.getId())) {
            depart = systemService.getEntity(TSDepart.class, depart.getId());
            req.setAttribute("depart", depart);
            //            if (StringUtils.isNotEmpty(depart.getSubjectDirectionCode())) {
            //                req.setAttribute("subjectDirectionCodeList", depart.getSubjectDirectionCode());
            //            } else {
            //                req.setAttribute("subjectDirectionCodeList", "[]");
            //            }
        }
        return new ModelAndView("system/depart/depart");
    }

    /**
     * ??????????????????
     * 
     * @param request
     * @param comboTree
     * @return
     */
    @RequestMapping(params = "setPFunction")
    @ResponseBody
    public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        if (null != request.getParameter("selfId")) {
            cq.notEq("id", request.getParameter("selfId"));
        }
        if (comboTree.getId() != null) {
            cq.eq("TSPDepart.id", comboTree.getId());
        }
        if (comboTree.getId() == null) {
            cq.isNull("TSPDepart");
        }
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        cq.addOrder("sortCode", SortDirection.asc);
        String scinceFlag = request.getParameter("scientificInstitutionFlag");
        if ((DepartConstant.ORGANIZE_INSTITU).equals(scinceFlag)) {
            cq.eq("scientificInstitutionFlag", DepartConstant.ORGANIZE_INSTITU);
        }
        cq.add();
        List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
        //??????????????????list
        for (TSDepart depart : departsList) {
        	setChildren(depart);
            /*if (depart.getTSDeparts() != null && depart.getTSDeparts().size() > 0) {
                List<TSDepart> subs = new ArrayList<TSDepart>();
                for (TSDepart sub : depart.getTSDeparts()) {
                    if (sub.getValidFlag().equals(DepartConstant.INVALID_FLAG)
                            || sub.getScientificInstitutionFlag().equals(DepartConstant.SCIENCE_INSTITU)) {
                        continue;
                    }
                    subs.add(sub);
                }
                depart.setTSDeparts(subs);
            }*/
        }
       
        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname", "TSDeparts");
        String lazy = request.getParameter("lazy");
        //??????????????????
        boolean recursive = false;
        if ("false".equals(lazy)) {
            recursive = true;
        }
        comboTrees = systemService.ComboTree(departsList, comboTreeModel, null, recursive);
        MutiLangUtil.setMutiTree(comboTrees);
        return comboTrees;
    }
    
    private void setChildren(TSDepart depart){
    	List<TSDepart> subs = systemService.getSession().createCriteria(TSDepart.class)
         		.add(Restrictions.eq("TSPDepart.id", depart.getId()))
         		.add(Restrictions.eq("validFlag", DepartConstant.VALID_FLAG))
         		.addOrder(Order.asc("sortCode")).list();
    	 if(subs != null && subs.size() > 0){
    		 depart.setTSDeparts(subs);
    		 
    		 for(int i = 0; i < subs.size(); i++){
    			 setChildren(subs.get(i));
    		 }
    	 }
    }
    
    /**
     * ???????????????????????????
     * 
     * @param request
     * @param response
     * @param treegrid
     * @return
     */
    @RequestMapping(params = "departgrid")
    @ResponseBody
    public Object departgrid(TSDepart tSDepart, HttpServletRequest request, HttpServletResponse response,
            TreeGrid treegrid) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        String scientificInstitutionFlag = tSDepart.getScientificInstitutionFlag();
        if ("yes".equals(request.getParameter("isSearch"))) {
            treegrid.setId(null);
            tSDepart.setId(null);
        }
        if (null != tSDepart.getDepartname()) {
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDepart);
        }
        if (treegrid.getId() != null) {
            cq.eq("TSPDepart.id", treegrid.getId());
        }
        if (treegrid.getId() == null) {
            if (!scientificInstitutionFlag.equals(DepartConstant.SCIENCE_INSTITU)) {
                cq.isNull("TSPDepart");
            }
        }
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        if ((DepartConstant.SCIENCE_INSTITU).equals(scientificInstitutionFlag)) {
            cq.eq("scientificInstitutionFlag", scientificInstitutionFlag);
        }
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TSDepart> departList = null;
        departList = systemService.getListByCriteriaQuery(cq, false);
        if (departList.size() == 0 && tSDepart.getDepartname() != null) {
            cq = new CriteriaQuery(TSDepart.class);
            TSDepart parDepart = new TSDepart();
            tSDepart.setTSPDepart(parDepart);
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDepart);
            departList = systemService.getListByCriteriaQuery(cq, false);
        }
        for (TSDepart depart : departList) {
            if (depart.getTSDeparts() != null && depart.getTSDeparts().size() > 0) {
                List<TSDepart> subs = new ArrayList<TSDepart>();
                for (TSDepart sub : depart.getTSDeparts()) {
                    if ((DepartConstant.INVALID_FLAG).equals(sub.getValidFlag())
                            || !scientificInstitutionFlag.equals(sub.getScientificInstitutionFlag())) {
                        continue;
                    }
                    subs.add(sub);
                }
                depart.setTSDeparts(subs);
            }
        }
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
        TreeGridModel treeGridModel = new TreeGridModel();
        treeGridModel.setTextField("departname");
        treeGridModel.setParentText("TSPDepart_departname");
        treeGridModel.setParentId("TSPDepart_id");
        treeGridModel.setSrc("description");
        treeGridModel.setIdField("id");
        treeGridModel.setOrder("sortCode");
        treeGridModel.setChildList("TSDeparts");
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("orgCode", "orgCode");
        /* fieldMap.put("orgType", "orgType"); */
        fieldMap.put("shortname", "shortname");
        fieldMap.put("subjectDirectionCode", "subjectDirectionCode");
        fieldMap.put("subjectDirectionName", "subjectDirectionName");
        fieldMap.put("validFlag", "validFlag");
        fieldMap.put("scientificInstitutionFlag", "scientificInstitutionFlag");
        fieldMap.put("manager_depart", "manager_depart");
        fieldMap.put("leader_official", "leader_official");
        treeGridModel.setFieldMap(fieldMap);
        treeGrids = systemService.treegrid(departList, treeGridModel);

        JSONArray jsonArray = new JSONArray();
        for (TreeGrid treeGrid : treeGrids) {
            jsonArray.add(JSON.parse(treeGrid.toJson()));
        }
        return jsonArray;
    }
    
    /**
     * ???????????????-combotree
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "getOrgTree")
    @ResponseBody
    public List<ComboTree> getOrgTree(HttpServletRequest request, ComboTree comboTree) {
        List<TSDepart> departList = null;
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        if (comboTree.getId() != null) {
            cq.eq("TSPDepart.id", comboTree.getId());
        }
        if (comboTree.getId() == null) {
            cq.isNull("TSPDepart");
        }
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        departList = systemService.getListByCriteriaQuery(cq, false);
        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname", "TSDeparts");
        comboTrees = systemService.ComboTree(departList, comboTreeModel, null, false);
        MutiLangUtil.setMutiTree(comboTrees);
        return comboTrees;
    }

    /**
     * ????????????????????????????????????
     * 
     * @param request
     * @param comboTree
     * @return
     */
    @RequestMapping(params = "getDepartGroup")
    @ResponseBody
    public JSONArray getDepartGroup(HttpServletRequest request, ComboTree comboTree) {
        JSONArray departArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        cq.isNull("TSPDepart");
        cq.eq("scientificInstitutionFlag", DepartConstant.ORGANIZE_INSTITU);
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
        if (departsList.size() > 0) {
            for (TSDepart sub : departsList) {
                JSONObject json = new JSONObject();
                json.put("id", sub.getId());
                json.put("text", sub.getDepartname());
                departArray.add(json);
                setChildren(sub, json);
            }
        }
        return departArray;

    }

    /**
     * ?????????????????????
     * 
     * @param depart
     */
    private void setChildren(TSDepart depart, JSONObject pJson) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
        cq.eq("scientificInstitutionFlag", DepartConstant.ORGANIZE_INSTITU);
        cq.eq("TSPDepart.id", depart.getId());
        cq.eq("validFlag", DepartConstant.VALID_FLAG);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
        if (departsList.size() > 0) {
            JSONArray arr = new JSONArray();
            depart.setTSDeparts(departsList);
            for (TSDepart sub : departsList) {
                JSONObject json = new JSONObject();
                json.put("id", sub.getId());
                json.put("text", sub.getDepartname());
                arr.add(json);
                setChildren(sub, json);
            }
            pJson.put("children", arr);
        } else {
            pJson.put("children", null);
        }
    }

    /**
     * ???????????????????????????
     * 
     * @param request
     * @param response
     * @param treegrid
     * @return
     */
    /*
     * @RequestMapping(params = "departgrid")
     * 
     * @ResponseBody public Object departgrid(TSDepart tSDepart, HttpServletRequest request, HttpServletResponse
     * response, TreeGrid treegrid) { CriteriaQuery cq = new CriteriaQuery(TSDepart.class); if
     * ("yes".equals(request.getParameter("isSearch"))) { treegrid.setId(null); tSDepart.setId(null); } if (null !=
     * tSDepart.getDepartname()) { org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDepart); }
     * if (treegrid.getId() != null) { cq.eq("TSPDepart.id", treegrid.getId()); } if (treegrid.getId() == null) {
     * cq.isNull("TSPDepart"); } cq.add(); List<TreeGrid> departList = null; departList =
     * systemService.getListByCriteriaQuery(cq, false); if (departList.size() == 0 && tSDepart.getDepartname() != null)
     * { cq = new CriteriaQuery(TSDepart.class); TSDepart parDepart = new TSDepart(); tSDepart.setTSPDepart(parDepart);
     * org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDepart); departList =
     * systemService.getListByCriteriaQuery(cq, false); } List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
     * TreeGridModel treeGridModel = new TreeGridModel(); treeGridModel.setTextField("departname");
     * treeGridModel.setParentText("TSPDepart_departname"); treeGridModel.setParentId("TSPDepart_id");
     * treeGridModel.setSrc("description"); treeGridModel.setIdField("id"); treeGridModel.setChildList("TSDeparts");
     * Map<String, Object> fieldMap = new HashMap<String, Object>(); fieldMap.put("orgCode", "orgCode");
     * fieldMap.put("orgType", "orgType"); treeGridModel.setFieldMap(fieldMap); treeGrids =
     * systemService.treegrid(departList, treeGridModel);
     * 
     * JSONArray jsonArray = new JSONArray(); for (TreeGrid treeGrid : treeGrids) {
     * jsonArray.add(JSON.parse(treeGrid.toJson())); } return jsonArray; }
     */

   

    // ----
    /**
     * ????????????: ?????????????????? ??? ?????? yiming.zhang ??? ?????? Dec 4, 2013-8:53:39 PM
     * 
     * @param request
     * @param departid
     * @return ??????????????? ModelAndView
     */
    @RequestMapping(params = "userList")
    public ModelAndView userList(HttpServletRequest request, String departid) {
        request.setAttribute("departid", departid);
        return new ModelAndView("system/depart/departUserList");
    }

    /**
     * ????????????: ????????????dataGrid ??? ?????? yiming.zhang ??? ?????? Dec 4, 2013-10:40:17 PM
     * 
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     *            ??????????????? void
     */
    @RequestMapping(params = "userDatagrid")
    public void userDatagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
        String departid = oConvertUtils.getString(request.getParameter("departid"));
        if (!StringUtil.isEmpty(departid)) {
            // update-start--Author:zhangguoming Date:20140825 for????????????????????????????????????????????????
            DetachedCriteria dc = cq.getDetachedCriteria();
            DetachedCriteria dcDepart = dc.createCriteria("userOrgList");
            dcDepart.add(Restrictions.eq("tsDepart.id", departid));
            // ???????????????????????????
            // DetachedCriteria dcDepart = dc.createAlias("userOrgList", "userOrg");
            // dcDepart.add(Restrictions.eq("userOrg.tsDepart.id", departid));
            // update-end--Author:zhangguoming Date:20140825 for????????????????????????????????????????????????
        }
        Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
        cq.in("status", userstate);
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    // ----

    // update-start--Author:zhangguoming Date:20140826 for?????????????????????
    /**
     * ???????????????-combotree
     * 
     * @param request
     * @return
     */
    /*
     * @RequestMapping(params = "getOrgTree")
     * 
     * @ResponseBody public List<ComboTree> getOrgTree(HttpServletRequest request) { // findHql????????????is null?????? //
     * List<TSDepart> departsList = systemService.findHql("from TSPDepart where TSPDepart.id is null"); List<TSDepart>
     * departsList = systemService.findByQueryString("from TSDepart t where TSPDepart.id is null "); List<ComboTree>
     * comboTrees = new ArrayList<ComboTree>(); ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname",
     * "TSDeparts"); comboTrees = systemService.ComboTree(departsList, comboTreeModel, null, true); return comboTrees; }
     */

    

    // update-end--Author:zhangguoming Date:20140826 for?????????????????????

    // update-start--Author:zhangguoming Date:20140826 for???????????????????????????????????????
    /**
     * ?????? ????????????????????? ????????? ??????
     * 
     * @param req
     *            request
     * @return ??????????????????
     */
    @RequestMapping(params = "goAddUserToOrg")
    public ModelAndView goAddUserToOrg(HttpServletRequest req) {
        req.setAttribute("orgId", req.getParameter("orgId"));
        return new ModelAndView("system/depart/noCurDepartUserList");
    }

    /**
     * ?????? ????????? ?????????????????????????????????
     * 
     * @param request
     *            request
     * @return ??????????????????
     */
    @RequestMapping(params = "addUserToOrgList")
    public void addUserToOrgList(TSUser user, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String orgId = request.getParameter("orgId");

        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        // ?????? ?????????????????????????????????
        CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
        subCq.setProjection(Property.forName("tsUser.id"));
        subCq.eq("tsDepart.id", orgId);
        subCq.add();

        cq.add(Property.forName("id").notIn(subCq.getDetachedCriteria()));
        cq.add();

        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ?????? ?????????????????????
     * 
     * @param req
     *            request
     * @return ??????????????????
     */
    @RequestMapping(params = "doAddUserToOrg")
    @ResponseBody
    public AjaxJson doAddUserToOrg(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        TSDepart depart = systemService.getEntity(TSDepart.class, req.getParameter("orgId"));
        saveOrgUserList(req, depart);
        message = MutiLangUtil.paramAddSuccess("common.user");
        // systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        j.setMsg(message);

        return j;
    }

    /**
     * ?????? ????????????-?????? ????????????
     * 
     * @param request
     *            request
     * @param depart
     *            depart
     */
    private void saveOrgUserList(HttpServletRequest request, TSDepart depart) {
        String orgIds = oConvertUtils.getString(request.getParameter("userIds"));

        List<TSUserOrg> userOrgList = new ArrayList<TSUserOrg>();
        List<String> userIdList = extractIdListByComma(orgIds);
        for (String userId : userIdList) {
            TSUser user = new TSUser();
            user.setId(userId);

            TSUserOrg userOrg = new TSUserOrg();
            userOrg.setTsUser(user);
            userOrg.setTsDepart(depart);

            userOrgList.add(userOrg);
            commonUserService.clearUserContact(userId);//??????????????????????????????????????????
        }
        if (!userOrgList.isEmpty()) {
            systemService.batchSave(userOrgList);
        }
    }

    // update-end--Author:zhangguoming Date:20140826 for????????????????????????????????????

    // update-start--Author:zhangguoming Date:20140827 for????????????????????? ??????????????????????????????????????????????????? ????????????
    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "departSelect")
    public String departSelect() {
        return "system/depart/departSelect";
    }

    @RequestMapping(params = "selectDepartTree")
    public String selectDepartTree(HttpServletRequest request, String scientificInstitutionFlag) {
        request.setAttribute("scientificInstitutionFlag", scientificInstitutionFlag);
        return "system/depart/selectDepartTree";
    }

    /**
     * ??????????????????
     * 
     * @param response
     *            response
     * @param dataGrid
     *            dataGrid
     */
    @RequestMapping(params = "departSelectDataGrid")
    public void datagridRole(TSDepart depart,HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, depart);
        cq.addOrder("orgCode", SortDirection.asc);
        cq.addOrder("sortCode", SortDirection.asc);
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    // update-end--Author:zhangguoming Date:20140827 for????????????????????? ??????????????????????????????????????????????????? ????????????

    /**
     * ??????????????????????????????????????????.
     *
     * @param codeTypeEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getOrgCombobox")
    @ResponseBody
    public List<ComboBox> getOrgCombobox(TSUser tsUser, ComboBox combobox, HttpServletRequest request) {
        List<TSDepart> departList = departService.getOrgByUserId(tsUser);
        List<ComboBox> comboList = new ArrayList<ComboBox>();
        for (TSDepart depart : departList) {
            ComboBox tmp = new ComboBox();
            tmp.setId(depart.getId());
            tmp.setText(depart.getDepartname());
            comboList.add(tmp);
        }
        return comboList;
    }

    /**
     * ????????????ID??????value
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "getValue")
    @ResponseBody
    public void getValue(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("value").trim();
        Session session = systemService.getSession();
        List<TSDepart> departs = session.createCriteria(TSDepart.class).add(Restrictions.eq("id", id)).list();
        String json = TagUtil.listtojson(new String[] { "departname" }, departs);
        json = json.replaceAll("departname", "name");
        System.out.println(json);
        TagUtil.response(response, json);
    }

    /**
     * ???????????????
     * 
     * @param request
     * @param comboTree
     * @return
     */
    @RequestMapping(params = "getDepartTree")
    @ResponseBody
    public List<ComboTree> getDepartTree(HttpServletRequest request, ComboTree comboTree) {
        String lazy = request.getParameter("lazy");
        List<ComboTree> treeList = this.departService.getDepartTree(lazy, comboTree);
        return treeList;
    }
    
    /**
     * ??????id??????????????????
     * 
     * @param request
     * @param comboTree
     * @return
     */
    @RequestMapping(params = "getBmlx")
    @ResponseBody
    public AjaxJson getBmlx(HttpServletRequest request, ComboTree comboTree) {
    	AjaxJson j = new AjaxJson();
        String id = request.getParameter("id");
        TSDepart depart = this.systemService.get(TSDepart.class, id);
        j.setMsg(depart.getBmlx());
        return j;
    }
    
    @RequestMapping(params = "romoveDepartUser")
    @ResponseBody
    public AjaxJson romoveDepartUser(HttpServletRequest req) {
    	AjaxJson j = new AjaxJson();
        String userId = req.getParameter("userId");
        String departId = req.getParameter("departId");
        List<TSUserOrg> list = systemService.getSession().createCriteria(TSUserOrg.class)
        	.add(Restrictions.eq("tsUser.id", userId))
        	.add(Restrictions.eq("tsDepart.id", departId)).list();
        for (TSUserOrg obj : list) {
            systemService.delete(obj);
        }
        j.setSuccess(true);
        j.setMsg("???????????????");
        return j;
    }

    /**
     * ?????????????????????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goThesisSecretCodePage")
    public ModelAndView goThesisSecretCodePage(HttpServletRequest request) {
        String departId = request.getParameter("departId");
        List<TBDepartThesisSecretCodeEntity> list =  this.systemService.findByProperty(TBDepartThesisSecretCodeEntity.class, "departId", departId);
        if (list.size() > 0) {
            TBDepartThesisSecretCodeEntity tmp = list.get(0);
            request.setAttribute("secretCode", tmp.getSecretCode());
        }
        return new ModelAndView("system/depart/departThesisSecretCode");
    }

    /**
     * ??????????????????
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "doSaveSecretCode")
    @ResponseBody
    public AjaxJson doSaveSecretCode(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            String departId = request.getParameter("departId");
            String secretCode = request.getParameter("secretCode");
            List<TBDepartThesisSecretCodeEntity> list = this.systemService.findByProperty(
                    TBDepartThesisSecretCodeEntity.class, "departId", departId);
            TBDepartThesisSecretCodeEntity entity = null;
            if (list.size() > 0) {
                entity = list.get(0);
                entity.setSecretCode(secretCode);
                this.systemService.updateEntitie(entity);
            } else {
                entity = new TBDepartThesisSecretCodeEntity();
                entity.setDepartId(departId);
                entity.setSecretCode(secretCode);
                this.systemService.save(entity);
            }
            j.setMsg("???????????????????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg("???????????????????????????????????????");
            j.setSuccess(false);
        }
        return j;
    }
}
