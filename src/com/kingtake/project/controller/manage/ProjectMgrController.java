package com.kingtake.project.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.extend.datasource.DataSourceContextHolder;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.fund.TBFundsPropertyEntity;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.service.manage.ProjectListServiceContext;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.manage.TPmProjectServiceI;

/**
 * @Title: Controller
 * @Description: 项目管理控制类
 * @author onlineGenerator
 * @date 2015-06-11 11:12:05
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/projectMgrController")
public class ProjectMgrController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ProjectMgrController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private ProjectListServiceContext projectListServiceContext;
    
    @Autowired
    private TPmProjectServiceI tPmProjectService;

    /**
     * 项目管理-课题组页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "projectMgrForResearchGroup")
    public ModelAndView projectMgr(HttpServletRequest request) {
        String departFlag = request.getParameter("departFlag");
        request.setAttribute("departFlag", departFlag);
        return new ModelAndView("com/kingtake/project/manage/projectMgrForResearchGroup");
    }

    /**
     * 项目管理-机关页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "projectMgrForDepartment")
    public ModelAndView projectMgrForDepartment(HttpServletRequest request) {
    	
        //---------------------------根据数据权限查询------------------------------------------
        //        TSUser user = ResourceUtil.getSessionUserName();
        //        List<TPmProjectEntity> totalProjectList = new ArrayList<TPmProjectEntity>();
        //        List<TSDataRule> ruleList = (List<TSDataRule>) request.getAttribute(Globals.MENU_DATA_AUTHOR_RULES);
        //        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class);
        //        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
        //        for (TSDataRule dataRule : ruleList) {
        //            HqlGenerateUtil.addRuleToCriteria(dataRule, String.class, cq);
        //        }
        //        cq.add();
        //        List<TPmProjectEntity> projectList = this.systemService.getListByCriteriaQuery(cq, false);
        //        totalProjectList.addAll(projectList);
        //        List<String> idList = new ArrayList<String>();
        //        for (TPmProjectEntity entity : projectList) {
        //            idList.add(entity.getId());
        //        }
        //        CriteriaQuery memberCq = new CriteriaQuery(TPmProjectEntity.class);
        //        memberCq.eq("createBy", user.getUserName());
        //        memberCq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
        //        memberCq.add();
        //        List<TPmProjectEntity> myProjectList = this.systemService.getListByCriteriaQuery(memberCq, false);
        //
        //        //项目总数
        //        String totalIds = "";
        //        for (TPmProjectEntity tmp : totalProjectList) {
        //            totalIds = totalIds + "," + tmp.getId();
        //        }
        //        //我创建的项目
        //        String myProjectIds = "";
        //        for (TPmProjectEntity tmp : myProjectList) {
        //            myProjectIds = myProjectIds + "," + tmp.getId();
        //        }
        //        //完成的项目
        //        String finishedIds = "";
        //        int finishedNum = 0;
        //        for (TPmProjectEntity tmp : totalProjectList) {
        //            if (ProjectConstant.PROJECT_STATUS_FINISHED.equals(tmp.getProjectStatus())) {
        //                finishedIds = finishedIds + "," + tmp.getId();
        //                finishedNum++;
        //            }
        //        }
        //        //本部门项目
        //        TSDepart depart = user.getCurrentDepart();
        //        List<String> departIds = new ArrayList<String>();
        //        departIds.add(depart.getId());
        //        getDeptFamily(depart, departIds);
        //        CriteriaQuery deptCq = new CriteriaQuery(TPmProjectEntity.class);
        //        deptCq.in("devDepart.id", departIds.toArray());
        //        deptCq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
        //        deptCq.add();
        //        List<TPmProjectEntity> deptProjectList = this.systemService.getListByCriteriaQuery(deptCq, false);
        //        String departmentIds = "";
        //        for (TPmProjectEntity tmp : deptProjectList) {
        //            departmentIds = departmentIds + "," + tmp.getId();
        //        }
        //        request.setAttribute("totalNum", totalProjectList.size());
        //        request.setAttribute("totalIds", totalIds);
        //        request.setAttribute("myproject", myProjectList.size());
        //        request.setAttribute("myprojectIds", myProjectIds);
        //        request.setAttribute("finishedNum", finishedNum);
        //        request.setAttribute("finishedIds", finishedIds);
        //        request.setAttribute("departmentNum", deptProjectList.size());
        //        request.setAttribute("departmentIds", departmentIds);    
  
        return new ModelAndView("com/kingtake/project/manage/projectMgrForDepartment");
    }
    
    
    /**
     * 项目管理-统计查询跳转
     * 
     * @return
     */
    @RequestMapping(params = "projectMgrForQuery")
    public ModelAndView projectMgrForQuery(HttpServletRequest request) {    	        
  
        return new ModelAndView("com/kingtake/project/manage/projectMgrForQuery");
    }
    
    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        List<TPmProjectEntity> tPmProjects = this.tPmProjectService.getListByCriteriaQuery(cq, false);                     
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目基本信息表");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目基本信息表列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjects);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 递归获取部门及其子部门
     * 
     * @param depart
     * @param departIds
     */
    private void getDeptFamily(TSDepart depart, List<String> departIds) {
        CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class);
        deptCq.eq("TSPDepart.id", depart.getId());
        deptCq.add();
        List<TSDepart> departList = this.systemService.getListByCriteriaQuery(deptCq, false);
        if (departList != null && departList.size() > 0) {
            for (TSDepart subDept : departList) {
                departIds.add(subDept.getId());
                getDeptFamily(subDept, departIds);
            }
        }
    }

    /**
     * 获取未确认的项目
     * 
     * @return
     */
    @RequestMapping(params = "unApprovalProjectList")
    @ResponseBody
    public List<ComboTree> unApprovalProjectList(TPmProjectEntity projectEntity, HttpServletRequest request,
            HttpServletResponse response) {
        Set<String> projIdSet = new HashSet<String>();
        //获取当前登录用户
        TSUser user = ResourceUtil.getSessionUserName();
        //查询出项目成员为当前人的项目
        CriteriaQuery memberCq = new CriteriaQuery(TPmProjectMemberEntity.class);
        memberCq.eq("user.id", user.getId());
        memberCq.add();
        List<TPmProjectMemberEntity> members = this.systemService.getListByCriteriaQuery(memberCq, false);

        for (TPmProjectMemberEntity entity : members) {
        	if(entity.getProject()!=null) {
                projIdSet.add(entity.getProject().getId());
        	}
 
        }
        
        
        //查询出创建人为当前人的项目
        CriteriaQuery projCq = new CriteriaQuery(TPmProjectEntity.class);
        projCq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);
        projCq.eq("createBy", user.getUserName());
        projCq.or(Restrictions.isNull("approvalStatus"),Restrictions.ne("approvalStatus", ProjectConstant.APPROVAL_YES));
        projCq.add();
        List<TPmProjectEntity> projects = this.systemService.getListByCriteriaQuery(projCq, false);
        for (TPmProjectEntity entity : projects) {
            projIdSet.add(entity.getId());
        }

        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class);
        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, projectEntity, request.getParameterMap());
        if (projIdSet.size() > 0) {
            cq.in("id", projIdSet.toArray());
        } else {
            cq.in("id", new String[] { "id" });
        }
        
        //cq.or(Restrictions.ne("approvalStatus", "1"), Restrictions.isNull("approvalStatus"));//已确认  
        boolean auditStatus = "1".equals(request.getParameter("auditStatus"));
        if(auditStatus) {//待审批
        	cq.eq("auditStatus", "1"); 
        }else {//草稿箱
        	//Restrictions.eq("auditStatus", "1"),
        	cq.add(Restrictions.or(Restrictions.eq("auditStatus", "0"), Restrictions.eq("auditStatus", "3"),Restrictions.isNull("auditStatus")));
        }
        cq.isNull("lxStatus");//未立项的
        
        String projectName = request.getParameter("pName");
        if(StringUtils.isNotEmpty(projectName)) {
        	cq.like("projectName", "%"+projectName+"%");
        }
        
        //cq.notEq("auditStatus", "2");
        cq.addOrder("createDate", SortDirection.desc);
        cq.notEq("scbz", "1");
        cq.add();
        List<TPmProjectEntity> projectList = this.systemService.getListByCriteriaQuery(cq, false);
        List<ComboTree> treeList = new ArrayList<ComboTree>();
        if (projectList.size() > 0) {
            ComboTree tree = new ComboTree();
            tree.setId("treeId");
            tree.setText(auditStatus?"提交待审批项目":"草稿待提交项目");
            tree.setState("open");
            List<ComboTree> childrenTree = new ArrayList<ComboTree>();
            for (TPmProjectEntity child : projectList) {
                ComboTree childTree = new ComboTree();
                childTree.setId(child.getId());
                childTree.setText(child.getProjectName());
                childTree.setState("open");
                if (ProjectConstant.PROJECT_PLAN.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeP1PM");
                } else if (ProjectConstant.PROJECT_CONTRACT.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeC1PM");
                }
                childrenTree.add(childTree);
            }
            tree.setChildren(childrenTree);
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * 获取项目树
     * 
     * @return
     */
    @RequestMapping(params = "projectTree")
    @ResponseBody
    public List<ComboTree> projectTree(TPmProjectEntity projectEntity, HttpServletRequest request,
            HttpServletResponse response) {
        Set<String> projIdSet = new HashSet<String>();
        String ids = request.getParameter("ids");
        if (StringUtils.isEmpty(ids)) { 
            //获取当前登录用户
            TSUser user = ResourceUtil.getSessionUserName();
            String all = request.getParameter("all");
            //查询出项目成员为当前人的项目
            CriteriaQuery memberCq = new CriteriaQuery(TPmProjectMemberEntity.class);
            if (StringUtil.isNotEmpty(all) && all.equals("true")) {

            } else {
                memberCq.eq("user.id", user.getId());
            }
            memberCq.add();
            List<TPmProjectMemberEntity> members = this.systemService.getListByCriteriaQuery(memberCq, false);

            for (TPmProjectMemberEntity entity : members) {
                projIdSet.add(entity.getProject().getId());
            }
            //查询出创建人为当前人的项目
//            CriteriaQuery projCq = new CriteriaQuery(TPmProjectEntity.class);
//            projCq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
//            if (StringUtil.isNotEmpty(all) && all.equals("true")) {
//
//            } else {
//                projCq.eq("createBy", user.getUserName());
//            }
//            projCq.or(Restrictions.isNull("approvalStatus"),
//                    Restrictions.eq("approvalStatus", ProjectConstant.APPROVAL_YES));
//            projCq.add();
//            List<TPmProjectEntity> projects = this.systemService.getListByCriteriaQuery(projCq, false);
//            for (TPmProjectEntity entity : projects) {
//                projIdSet.add(entity.getId());
//            }
        } else {
            String[] idArr = ids.split(",");
            projIdSet.addAll(Arrays.asList(idArr));
        }
        logger.info("projIdSet:"+projIdSet.toString());
        String sortKey = request.getParameter("sortKey");
        //1、计划合同标志  2、年度  3、类别  4、性质
        Map<String, Map<String, String>> valueMap = new HashMap<String, Map<String, String>>();
        if ("1".equals(sortKey)) {
            valueMap.put("planContractFlag", ProjectConstant.planContractMap);
        } else if ("3".equals(sortKey)) {
            List<TBProjectTypeEntity> projectTypeList = this.systemService
                    .findByQueryString("from TBProjectTypeEntity");
            Map<String, String> tMap = new HashMap<String, String>();
            for (TBProjectTypeEntity propertyType : projectTypeList) {
                tMap.put(propertyType.getId(), propertyType.getProjectTypeName());
            }
            valueMap.put("projectType", tMap);

        } else if ("4".equals(sortKey)) {
            List<TBFundsPropertyEntity> fundsList = this.systemService.findByQueryString("from TBFundsPropertyEntity");
            Map<String, String> tMap = new HashMap<String, String>();
            for (TBFundsPropertyEntity fund : fundsList) {
                tMap.put(fund.getId(), fund.getFundsName());
            }
            valueMap.put("feeType", tMap);
        }

        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class);
        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, projectEntity,
                request.getParameterMap());
        if (projIdSet.size() > 0) {
            cq.in("id", projIdSet.toArray());
        } else {
            cq.in("id", new String[] { "id" });
        }
        /*cq.eq("approvalStatus", "1");//已确认*/
        cq.eq("auditStatus", "2");//已确认
        cq.isNull("lxStatus");//未立项的
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmProjectEntity> oprojectList = this.systemService.getListByCriteriaQuery(cq, false);
        List<TPmProjectEntity> projectList = new ArrayList<TPmProjectEntity>();
        for (TPmProjectEntity entity : oprojectList) {//过滤掉项目类型为空的数据
            if (entity.getXmlb() != null) {
                projectList.add(entity);
            }
        }
        Map<String, List<TPmProjectEntity>> treeMap = new HashMap<String, List<TPmProjectEntity>>();
        String sortValue = null;
        for (TPmProjectEntity project : projectList) {
            /*if ("1".equals(sortKey)) {
                sortValue = project.getPlanContractFlag();
                sortValue = valueMap.get("planContractFlag").get(sortValue);
            } else if ("2".equals(sortKey)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                sortValue = sdf.format(project.getBeginDate());
            } else if ("3".equals(sortKey)) {
                sortValue = project.getProjectType().getId();
                sortValue = valueMap.get("projectType").get(sortValue);
            } else if ("4".equals(sortKey)) {
                sortValue = project.getFeeType().getId();
                sortValue = valueMap.get("feeType").get(sortValue);
            }*/
            
            sortValue = project.getXmlx();
        	if(sortValue.equals("1")){
        		sortValue="横向";
        	}else if(sortValue.equals("2")){
        		sortValue="纵向";
        	}else if(sortValue.equals("3")){
        		sortValue="自主立项项目";
        	}else if(sortValue.equals("4")){
        		sortValue="校内协作项目";
        	}
            
            if (treeMap.containsKey(sortValue)) {
                List<TPmProjectEntity> tmpList = treeMap.get(sortValue);
                tmpList.add(project);
            } else {
                List<TPmProjectEntity> tmpList = new ArrayList<TPmProjectEntity>();
                tmpList.add(project);
                treeMap.put(sortValue, tmpList);
            }
        }
        Set<Entry<String, List<TPmProjectEntity>>> entrySet = treeMap.entrySet();
        Iterator it = entrySet.iterator();
        List<ComboTree> treeList = new ArrayList<ComboTree>();
        while (it.hasNext()) {
            Entry<String, List<TPmProjectEntity>> entry = (Entry<String, List<TPmProjectEntity>>) it.next();
            ComboTree tree = new ComboTree();
            tree.setId("treeId" + entry.getKey());
            tree.setText(entry.getKey());
            //tree.setState("closed");
            tree.setState("open");
            if (entry.getKey() == ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_PLAN)) {
                tree.setIconCls("icon-treePPM");
            } else if (entry.getKey() == ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_CONTRACT)) {
                tree.setIconCls("icon-treeCPM");
            }
            List<ComboTree> childrenTree = new ArrayList<ComboTree>();
            for (TPmProjectEntity child : entry.getValue()) {
                ComboTree childTree = new ComboTree();
                childTree.setId(child.getId());
                
                childTree.setState("open");
                if (ProjectConstant.PROJECT_PLAN.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeP1PM");
                } else if (ProjectConstant.PROJECT_CONTRACT.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeC1PM");
                }  
                
                
                //===============================lijun对已审批项目树列表中的已立项项目进行颜色标示
                CriteriaQuery cq_lj = new CriteriaQuery(TPmProjectEntity.class);
                cq_lj.eq("lxStatus", "1");
                cq_lj.eq("cxm", child.getCxm());
                cq_lj.add();
                int ct_lj = this.systemService.getCountByCriteriaQuery(cq_lj);
                //System.out.println("==================" + child.getCxm() + "===========" + ct_lj);
                childTree.setText(child.getProjectName());
                if(ct_lj == 1) 
                	childTree.setIconCls("xtjk_jk");
                	//childTree.setText("<span style=\"color:blue;\">" + child.getProjectName() + "</span>");
                
                
                //设置计划合同标志
                Map<String, Object> flagMap = new HashMap<String, Object>();
                flagMap.put("xmlx", child.getXmlx());
                childTree.setAttributes(flagMap);
                childrenTree.add(childTree);
            }
            tree.setChildren(childrenTree);
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * 跳转到课题组项目管理主界面
     * 
     * @return
     */
    @RequestMapping(params = "goProjectResearchMain")
    public ModelAndView goProjectResearchMain(ModelMap modelMap, HttpServletRequest request,
            HttpServletResponse response) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
        TSUser user = ResourceUtil.getSessionUserName();
        String roles = "";
        if (user != null) {
            List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
            for (TSRoleUser ru : rUsers) {
                TSRole role = ru.getTSRole();
                roles += role.getRoleName() + ",";
            }
            if (roles.length() > 0) {
                roles = roles.substring(0, roles.length() - 1);
            }
            modelMap.put("roleName", roles);
            modelMap.put("userName", user.getUserName());
            modelMap.put("realName", user.getRealName());
            modelMap.put("currentOrgName", ClientManager.getInstance().getClient().getUser().getCurrentDepart()
                    .getDepartname());
        }
        String departFlag = request.getParameter("departFlag");
        if (StringUtils.isNotEmpty(departFlag)) {
            request.setAttribute("departFlag", departFlag);
        }
        return new ModelAndView("com/kingtake/project/manage/project_main_research");

    }

    /**
     * 跳转到机关项目管理主界面
     * 
     * @return
     */
    @RequestMapping(params = "goProjectDepartmentMain")
    public ModelAndView goProjectDepartmentMain(ModelMap modelMap, HttpServletRequest request,
            HttpServletResponse response) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
        TSUser user = ResourceUtil.getSessionUserName();
        String roles = "";
        if (user != null) {
            List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
            for (TSRoleUser ru : rUsers) {
                TSRole role = ru.getTSRole();
                roles += role.getRoleName() + ",";
            }
            if (roles.length() > 0) {
                roles = roles.substring(0, roles.length() - 1);
            }
            modelMap.put("roleName", roles);
            modelMap.put("userName", user.getUserName());
            modelMap.put("currentOrgName", ClientManager.getInstance().getClient().getUser().getCurrentDepart()
                    .getDepartname());
        }
        return new ModelAndView("com/kingtake/project/manage/project_main_department");

    }

    /**
     * 获取旋转菜单
     * 
     * @return
     */
    @RequestMapping(params = "getPresenterMenu")
    @ResponseBody
    public JSONObject getPresenterMenu() {
        List<Map<String, Object>> countMapList = new ArrayList<Map<String, Object>>();
        String xmsbDesc = getFunctionByBusinessCode("projectDepartment_sb", countMapList);//立项
        String xmzxDesc = getFunctionByBusinessCode("projectDepartment_zx", countMapList);//立项
        String xmlxDesc = getFunctionByBusinessCode("projectDepartment_lx", countMapList);//计划
        String ysjtDesc = getFunctionByBusinessCode("projectDepartment_ysjt", countMapList);//执行
        String cgjdDesc = getFunctionByBusinessCode("projectDepartment_cgjd", countMapList);//控制
        JSONObject json = new JSONObject();
        json.put("projectDepartment_sb", xmsbDesc);
        json.put("projectDepartment_zx", xmzxDesc);
        json.put("projectDepartment_lx", xmlxDesc);
        json.put("projectDepartment_ysjt", ysjtDesc);
        json.put("projectDepartment_cgjd", cgjdDesc);

        if (countMapList.size() > 0) {
            StringBuffer countSb = new StringBuffer();
            int size = countMapList.size();
            int limit = 4;
            int pageSize = size % limit == 0 ? (size / limit) : (size / limit + 1);
            for (int i = 0; i < pageSize; i++) {
            	countSb.append("<tr>");
            	for (int j = i * limit; j < (i + 1) * limit && j < size; j++) {
            		Map<String, Object> map = countMapList.get(j);
            		 countSb.append("<td width='25%' bgcolor=\"#F4F4F4\" style=\"cursor: pointer;padding:10px;\" onclick=addTab(\""
                             + map.get("name") + "\",\"" + map.get("url") + "\",\"default\");>");
                     countSb.append(map.get("name"));
                     countSb.append("&nbsp;&nbsp;");
                     countSb.append("<font>" + map.get("count") + "</font>");
                     countSb.append("</td>");
            	}
            	countSb.append("</tr>");
			}
            json.put("countStr", countSb.toString());
            /*for (Map<String, Object> map : countMapList) {
                countSb.append("<tr><td bgcolor=\"#F4F4F4\" style=\"cursor: pointer;\" onclick=addTab(\""
                        + map.get("name") + "\",\"" + map.get("url") + "\",\"default\");>");
                countSb.append(map.get("name"));
                countSb.append(":&nbsp;&nbsp;");
                countSb.append("<font color=\"red\" style=\"font-weight: bold;font-size:30px;\">" + map.get("count")
                        + "</font>");
                countSb.append("</td></tr>");
                json.put("countStr", countSb.toString());
            }*/
        }
        return json;
    }

    /**
     * 根据业务编码获取菜单
     * 
     * @param businessCode
     * @return
     */
    private String getFunctionByBusinessCode(String businessCode, List<Map<String, Object>> auditCountMapList) {
        TSUser user = ResourceUtil.getSessionUserName();
        String sql = "select distinct f.functionname, f.functionurl, f.functionorder "
                + "from t_s_base_user u join t_s_role_user ru on u.id = ru.userid "
                + "join t_s_role r on ru.roleid = r.id join t_s_role_function rf on rf.roleid = r.id "
                + "join t_s_function f on rf.functionid = f.id where u.username = ? " + "and f.business_code = ? "
                + "order by to_number(f.functionorder) asc";
        List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql, new Object[] { user.getUserName(),
                businessCode });
        StringBuffer sb = new StringBuffer();
        for (Map<String, Object> map : resultList) {
            String functionName = (String) map.get("functionname");
            //屏蔽流程修改后的代办环节
            if(delFlag(functionName)){
            	continue;
            }
            
            String functionUrl = (String) map.get("functionurl");
            //根据不同的配置，查询书待办数目
            Integer count = 0;
            String key = getAuditKey(functionUrl);
            if (StringUtils.isNotEmpty(key)) {
                ProjectListServiceI service = projectListServiceContext.getProjectService(key);
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("key", key);
                count = service.getAuditCount(paramMap);
                if (count > 0) {
                    Map<String, Object> countMap = new HashMap<String, Object>();
                    countMap.put("name", functionName);
                    countMap.put("count", count);
                    countMap.put("url", functionUrl);
                    auditCountMapList.add(countMap);
                }
            }
            sb.append("<li ");
            if (StringUtils.isNotEmpty(functionUrl)) {
                sb.append("onclick=\"addTab('" + functionName + "', '" + functionUrl + "', 'default')\");");
            }
            sb.append(">");
            sb.append(functionName);
            if (count > 0) {
                sb.append("<span ");
                sb.append("class=\"count\">");
                sb.append(count);
                sb.append("</span>");
            }
            sb.append("</li>");
        }
        return sb.toString();
    }

    private boolean delFlag(String pName){
    	boolean flag = false;
    	if("申报书评审".equalsIgnoreCase(pName)
    			||"申报需求评审".equalsIgnoreCase(pName)
    			||"计划草案拟制".equalsIgnoreCase(pName)
    	        ||"计划草案审批".equalsIgnoreCase(pName)    
    	        ||"进账合同正本确认".equalsIgnoreCase(pName)
    	        ||"下发通知".equalsIgnoreCase(pName)
    	        ||"任务书审核".equalsIgnoreCase(pName)
    			||"开题报告审批".equalsIgnoreCase(pName)
    			||"项目节点验收审批".equalsIgnoreCase(pName)
    	        ||"出账合同正本确认".equalsIgnoreCase(pName)
    	        ||"采购计划拟制".equalsIgnoreCase(pName)
    			||"出账合同节点考核".equalsIgnoreCase(pName)
    	        ||"中期检查报告审核".equalsIgnoreCase(pName)
    	        ||"减免审批".equalsIgnoreCase(pName)
    			){
    		flag = true;
        }
    	return flag;
    }
    /**
     * 获取审核的key
     * 
     * @return
     */
    private String getAuditKey(String url) {
        String keyStr = "";
        if (StringUtils.isBlank(url)) {
            return keyStr;
        }
        int index = url.indexOf("&");
        if (index != -1) {
            String paramStr = url.substring(index);
            String[] params = paramStr.split("&");
            for (String param : params) {
                if (param.indexOf("key=") != -1) {
                    keyStr = param.replace("key=", "");
                    break;
                }
            }
        }
        return keyStr;
    }

    /**
     * 获取待办审批
     * 
     * @return
     */
    @RequestMapping(params = "getDepartmentForPortal")
    public ModelAndView getDepartmentForPortal() {
        return new ModelAndView("com/kingtake/project/manage/projectDepartmentForPortal");
    }

    /**
     * 获取项目选择的项目树
     * 
     * @return
     */
    @RequestMapping(params = "projectSelectTree")
    @ResponseBody
    public List<ComboTree> projectSelectTree(TPmProjectEntity projectEntity, HttpServletRequest request,
            HttpServletResponse response) {
        Set<String> projIdSet = new HashSet<String>();
        String ids = request.getParameter("ids");
        if (StringUtils.isEmpty(ids)) {
            //获取当前登录用户
            TSUser user = ResourceUtil.getSessionUserName();
            String all = request.getParameter("all");
            //查询出项目成员为当前人的项目
            CriteriaQuery memberCq = new CriteriaQuery(TPmProjectMemberEntity.class);
            if (StringUtil.isNotEmpty(all) && all.equals("true")) {

            } else {
                memberCq.eq("user.id", user.getId());
            }
            memberCq.add();
            List<TPmProjectMemberEntity> members = this.systemService.getListByCriteriaQuery(memberCq, false);

            for (TPmProjectMemberEntity entity : members) {
                projIdSet.add(entity.getProject().getId());
            }
            //查询出创建人为当前人的项目
            CriteriaQuery projCq = new CriteriaQuery(TPmProjectEntity.class);
            projCq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
            if (StringUtil.isNotEmpty(all) && all.equals("true")) {

            } else {
                projCq.eq("createBy", user.getUserName());
            }
//            projCq.or(Restrictions.isNull("approvalStatus"),
//                    Restrictions.eq("approvalStatus", ProjectConstant.APPROVAL_YES));
            projCq.add();
            List<TPmProjectEntity> projects = this.systemService.getListByCriteriaQuery(projCq, false);
            for (TPmProjectEntity entity : projects) {
                projIdSet.add(entity.getId());
            }
            List<String> dutyProjectList = getProjectByDudy();//管辖范围的项目
            if (dutyProjectList.size() > 0) {
                projIdSet.addAll(dutyProjectList);
            }
        } else {
            String[] idArr = ids.split(",");
            projIdSet.addAll(Arrays.asList(idArr));
        }
        String sortKey = request.getParameter("sortKey");
        //1、计划合同标志  2、年度  3、类别  4、性质
        Map<String, Map<String, String>> valueMap = new HashMap<String, Map<String, String>>();
        if ("1".equals(sortKey)) {
            valueMap.put("planContractFlag", ProjectConstant.planContractMap);
        } else if ("3".equals(sortKey)) {
            List<TBProjectTypeEntity> projectTypeList = this.systemService
                    .findByQueryString("from TBProjectTypeEntity");
            Map<String, String> tMap = new HashMap<String, String>();
            for (TBProjectTypeEntity propertyType : projectTypeList) {
                tMap.put(propertyType.getId(), propertyType.getProjectTypeName());
            }
            valueMap.put("projectType", tMap);

        } else if ("4".equals(sortKey)) {
            List<TBFundsPropertyEntity> fundsList = this.systemService.findByQueryString("from TBFundsPropertyEntity");
            Map<String, String> tMap = new HashMap<String, String>();
            for (TBFundsPropertyEntity fund : fundsList) {
                tMap.put(fund.getId(), fund.getFundsName());
            }
            valueMap.put("feeType", tMap);
        }

        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class);
        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, projectEntity,
                request.getParameterMap());
        if (projIdSet.size() > 0) {
            cq.in("id", projIdSet.toArray());
        } else {
            cq.in("id", new String[] { "id" });
        }
        cq.eq("approvalStatus", "1");//已确认
//        cq.eq("planContractFlag", "1");//已确认
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmProjectEntity> oprojectList = this.systemService.getListByCriteriaQuery(cq, false);
        List<TPmProjectEntity> projectList = new ArrayList<TPmProjectEntity>();
        for (TPmProjectEntity entity : oprojectList) {//过滤掉项目类型为空的数据
            if (entity.getProjectType() != null) {
                projectList.add(entity);
            }
        }
        Map<String, List<TPmProjectEntity>> treeMap = new HashMap<String, List<TPmProjectEntity>>();
        String sortValue = null;
        for (TPmProjectEntity project : projectList) {
            if ("1".equals(sortKey)) {
                sortValue = project.getPlanContractFlag();
                sortValue = valueMap.get("planContractFlag").get(sortValue);
            } else if ("2".equals(sortKey)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                sortValue = sdf.format(project.getBeginDate());
            } else if ("3".equals(sortKey)) {
                sortValue = project.getProjectType().getId();
                sortValue = valueMap.get("projectType").get(sortValue);
            } else if ("4".equals(sortKey)) {
                sortValue = project.getFeeType().getId();
                sortValue = valueMap.get("feeType").get(sortValue);
            }
            if (treeMap.containsKey(sortValue)) {
                List<TPmProjectEntity> tmpList = treeMap.get(sortValue);
                tmpList.add(project);
            } else {
                List<TPmProjectEntity> tmpList = new ArrayList<TPmProjectEntity>();
                tmpList.add(project);
                treeMap.put(sortValue, tmpList);
            }
        }
        Set<Entry<String, List<TPmProjectEntity>>> entrySet = treeMap.entrySet();
        Iterator it = entrySet.iterator();
        List<ComboTree> treeList = new ArrayList<ComboTree>();
        while (it.hasNext()) {
            Entry<String, List<TPmProjectEntity>> entry = (Entry<String, List<TPmProjectEntity>>) it.next();
            ComboTree tree = new ComboTree();
            tree.setId("treeId" + entry.getKey());
            tree.setText(entry.getKey());
            tree.setState("closed");
            if (entry.getKey() == ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_PLAN)) {
                tree.setIconCls("icon-treePPM");
            } else if (entry.getKey() == ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_CONTRACT)) {
                tree.setIconCls("icon-treeCPM");
            }
            List<ComboTree> childrenTree = new ArrayList<ComboTree>();
            for (TPmProjectEntity child : entry.getValue()) {
                ComboTree childTree = new ComboTree();
                childTree.setId(child.getId());
                childTree.setText(child.getProjectName());
                childTree.setState("open");
                if (ProjectConstant.PROJECT_PLAN.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeP1PM");
                } else if (ProjectConstant.PROJECT_CONTRACT.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeC1PM");
                }
                //设置计划合同标志
                Map<String, Object> flagMap = new HashMap<String, Object>();
                flagMap.put("planContractFlag", child.getPlanContractFlag());
                childTree.setAttributes(flagMap);
                childrenTree.add(childTree);
            }
            tree.setChildren(childrenTree);
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * 获取项目选择的项目树
     * 
     * @return
     */
    @RequestMapping(params = "projectSelectTreeByCxmSbr")
    @ResponseBody
    public List<ComboTree> projectSelectTreeByCxmSbr(TPmProjectEntity projectEntity, HttpServletRequest request,
            HttpServletResponse response) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class);
        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//排除被合并项目
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, projectEntity,
                request.getParameterMap());
        cq.add(Restrictions.or(Restrictions.eq("approvalStatus", "0"),
                Restrictions.and(Restrictions.isNull("approvalStatus"), Restrictions.eq("assignFlag", "1"))));
        cq.addOrder("createDate", SortDirection.desc);
        cq.isNotNull("xmlb");
        cq.add();
        List<TPmProjectEntity> projectList = this.systemService.getListByCriteriaQuery(cq, false);
        Map<String, List<TPmProjectEntity>> treeMap = new HashMap<String, List<TPmProjectEntity>>();
        String sortValue = null;
        for (TPmProjectEntity project : projectList) {
        	sortValue = project.getXmlx();
        	if(sortValue.equals("1")){
        		sortValue="横向";
        	}else if(sortValue.equals("2")){
        		sortValue="纵向";
        	}else if(sortValue.equals("3")){
        		sortValue="自主立项项目";
        	}else if(sortValue.equals("4")){
        		sortValue="校内协作项目";
        	}
            if (treeMap.containsKey(sortValue)) {
                List<TPmProjectEntity> tmpList = treeMap.get(sortValue);
                tmpList.add(project);
            } else {
                List<TPmProjectEntity> tmpList = new ArrayList<TPmProjectEntity>();
                tmpList.add(project);
                treeMap.put(sortValue, tmpList);
            }
        }
        Set<Entry<String, List<TPmProjectEntity>>> entrySet = treeMap.entrySet();
        Iterator it = entrySet.iterator();
        List<ComboTree> treeList = new ArrayList<ComboTree>();
        while (it.hasNext()) {
            Entry<String, List<TPmProjectEntity>> entry = (Entry<String, List<TPmProjectEntity>>) it.next();
            ComboTree tree = new ComboTree();
            tree.setId("treeId" + entry.getKey());
            tree.setText(entry.getKey());
            tree.setState("closed");
            if (entry.getKey() == ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_PLAN)) {
                tree.setIconCls("icon-treePPM");
            } else if (entry.getKey() == ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_CONTRACT)) {
                tree.setIconCls("icon-treeCPM");
            }
            List<ComboTree> childrenTree = new ArrayList<ComboTree>();
            for (TPmProjectEntity child : entry.getValue()) {
                ComboTree childTree = new ComboTree();
                childTree.setId(child.getId());
                childTree.setText(child.getProjectName());
                childTree.setState("open");
                if (ProjectConstant.PROJECT_PLAN.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeP1PM");
                } else if (ProjectConstant.PROJECT_CONTRACT.equals(child.getPlanContractFlag())) {
                    childTree.setIconCls("icon-treeC1PM");
                }
                //设置计划合同标志
                Map<String, Object> flagMap = new HashMap<String, Object>();
                flagMap.put("planContractFlag", child.getPlanContractFlag());
                childTree.setAttributes(flagMap);
                childrenTree.add(childTree);
            }
            tree.setChildren(childrenTree);
            treeList.add(tree);
        }
        return treeList;
    }
    
    /**
     * 根据管辖范围查询项目
     * 
     * @return
     */
    private List<String> getProjectByDudy() {
        List<String> dutyProjectIdList = new ArrayList<String>();
        TSUser user = ResourceUtil.getSessionUserName();
        HashSet<TSDepart> subDepartList = new LinkedHashSet<TSDepart>();
        CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class);
        deptCq.like("leaderOfficialId", "%" + user.getId() + "%");
        deptCq.add();
        List<TSDepart> departs = this.systemService.getListByCriteriaQuery(deptCq, false);
        if (departs.size() == 0) {
            return dutyProjectIdList;
        }
        List<TSDepart> allDept = this.systemService.loadAll(TSDepart.class);//查出所有
        for (TSDepart deptTmp : departs) {
            getSubDeptList(deptTmp, allDept, subDepartList);
        }
        List<String> dutyDeptIdList = new ArrayList<String>();
        for (TSDepart dutyDept : subDepartList) {
            dutyDeptIdList.add(dutyDept.getId());
        }
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class);
        cq.in("dutyDepart.id", dutyDeptIdList.toArray());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmProjectEntity> projectList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TPmProjectEntity tmp : projectList) {
            dutyProjectIdList.add(tmp.getId());
        }
        return dutyProjectIdList;
    }

    /**
     * 获取子部门
     * 
     * @param pDepart
     * @return
     */
    private void getSubDeptList(TSDepart pDepart, List<TSDepart> allDept, HashSet<TSDepart> subDepartList) {
        subDepartList.add(pDepart);
        for (TSDepart tmp : allDept) {
            if (tmp.getTSPDepart() != null && pDepart.getId().equals(tmp.getTSPDepart().getId())) {
                getSubDeptList(tmp, allDept, subDepartList);
            }
        }
    }

}
