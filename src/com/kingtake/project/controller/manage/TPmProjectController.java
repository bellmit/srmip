package com.kingtake.project.controller.manage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.ExportXmlServiceI;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;
import com.kingtake.project.entity.incomeplan.TPmFpje;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.service.incomeapply.TPmIncomeApplyServiceI;
import com.kingtake.project.service.manage.ProjectListServiceContext;
import com.kingtake.project.service.manage.TPmProjectServiceI;

/**
 * @Title: Controller
 * @Description: ?????????????????????
 * @author onlineGenerator
 * @date 2015-07-04 09:42:34
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmProjectController")
public class TPmProjectController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmProjectController.class);

    @Autowired
    private TPmProjectServiceI tPmProjectService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    @Autowired
    private ExportXmlServiceI exportXmlServiceI;
    @Autowired
    private TPmIncomeApplyServiceI tBPmIncomeApplyService;

    private String message;

    @Autowired
    private ProjectListServiceContext projectListServiceContext;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * ??????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmProject")
    public ModelAndView tPmProject(HttpServletRequest request) {
        String projectStatus = request.getParameter("projectStatus");
        if (StringUtils.isNotEmpty(projectStatus)) {
            request.setAttribute("projectStatus", projectStatus);
        }
        return new ModelAndView("com/kingtake/project/manage/tPmProjectList");
    }

    /**
     * ??????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "projectSelect")
    public ModelAndView projectSelect(HttpServletRequest request) {
        String excludeIds = request.getParameter("excludeIds");
        if (StringUtils.isNotEmpty(excludeIds)) {
            request.setAttribute("excludeIds", excludeIds);
        }
        String plan = request.getParameter("plan");
        if ("1".equals(plan)) {
            request.setAttribute("plan", plan);
        }
        String lx = request.getParameter("lx");
        if ("1".equals(lx)) {
            request.setAttribute("lx", lx);
        }
        String multiply = request.getParameter("multiply");
        if("1".equals(multiply)){
            request.setAttribute("checkbox", true);
        }else{
            request.setAttribute("checkbox", false);
        }
        return new ModelAndView("com/kingtake/project/manage/projectList");
    }

    /**
     * ??????????????????
     * 
     * @param tPmIncomeContractAppr
     * @param req
     * @return
     */
    @RequestMapping(params = "validformProjectNo")
    @ResponseBody
    public JSONObject validformProjectNo(TPmProjectEntity tPmProject,HttpServletRequest request,HttpServletResponse response) {
        String projectNo = tPmProject.getProjectNo();
        String projectNo2 = request.getParameter("projectNo");
        List<Map<String,Object>> list = this.tPmProjectService.findForJdbc(
        		"select ID,PROJECT_NAME from t_pm_project where CXM =?"
        		, projectNo);
        JSONObject result = new JSONObject();
        if(CollectionUtils.isEmpty(list)) {
        	result.put("status", false);
        	result.put("projectId","");
        	result.put("projectName", "");
        }else {
        	Map<String,Object> projectData = list.get(0);
        	result.put("status", true);
        	result.put("projectId", MapUtils.getString(projectData, "ID", ""));
        	result.put("projectName", MapUtils.getString(projectData, "PROJECT_NAME", ""));
        }
        return result;
    }

 
    @RequestMapping(params = "projectMultipleSelect")
    public ModelAndView projectMultipleSelect(HttpServletRequest request) {
        String mode = request.getParameter("mode");
        String all = request.getParameter("all");
        if (StringUtil.isNotEmpty(mode)) {
            request.setAttribute("mode", mode);
        }
        if (StringUtil.isNotEmpty(all)) {
            request.setAttribute("all", all);
        }
        return new ModelAndView("com/kingtake/project/manage/projectTree");
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectStateList")
    public ModelAndView tPmProjectStateList(HttpServletRequest request) {
        // ????????????:?????????
        request.setAttribute("applying", ProjectConstant.PROJECT_STATUS_APPLYING);
        // ????????????:????????????
        request.setAttribute("executing", ProjectConstant.PROJECT_STATUS_EXECUTING);
        //????????????????????????
        request.setAttribute("yes", ProjectConstant.APPROVAL_YES);
        //????????????????????????
        request.setAttribute("no", ProjectConstant.APPROVAL_NO);
        return new ModelAndView("com/kingtake/project/manage/tPmProjectStateList");
    }
    
    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectConfirmList")
    public ModelAndView tPmProjectConfirmList(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/manage/tPmProjectConfirmList");
    }
    
    /**
     * ????????????????????????tab???
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectConfirmListTab")
    public ModelAndView tPmProjectConfirmListTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/manage/tPmProjectConfirmTab");
    }
    
    /**
     * ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectLxTab")
    public ModelAndView tPmProjectLxTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/manage/tPmProjectLxTab");
    }

    /**
     * [??????]??????????????????
     * 
     * @param tPmProject
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridState")
    public void datagridState(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String approvalStatus = request.getParameter("approvalStatus");
        String typeId = request.getParameter("projectType.projectTypeName");
        String projectName = request.getParameter("projectName");
        String projectNo = request.getParameter("projectNo");
        String projectManager = request.getParameter("projectManager");
        String beginDate_begin = request.getParameter("beginDate_begin");
        String beginDate_end = request.getParameter("beginDate_end");
        String devDepart_departname = request.getParameter("devDepart.departname");
        String dutyDepart_departname = request.getParameter("dutyDepart.departname");
        String allFee_begin = request.getParameter("allFee_begin");
        String allFee_end = request.getParameter("allFee_end");
        String outsideNo = request.getParameter("outsideNo");
        String accountingCode = request.getParameter("accountingCode");
        String sourceUnit = request.getParameter("sourceUnit");
        String isFinish = request.getParameter("isFinish");
        String projectPlanId = request.getParameter("projectPlanId");
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        
        //?????????????????????
        //org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        if (StringUtil.isNotEmpty(projectNo)) {//????????????
            projectNo = projectNo.replace("*", "%");
            cq.like("projectNo", projectNo);
        }
        if (StringUtil.isNotEmpty(projectName)) {//????????????
            projectName = projectName.replace("*", "%");
            cq.like("projectName", projectName);
        }
        if (StringUtil.isNotEmpty(projectManager)) {//???????????????
            projectManager = projectManager.replace("*", "%");
            cq.like("projectManager", projectManager);
        }
        if (StringUtil.isNotEmpty(beginDate_begin)) {//????????????
            try {
                cq.ge("beginDate", DateUtils.parseDate(beginDate_begin, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtil.isNotEmpty(beginDate_end)) {//????????????
            try {
                cq.le("beginDate", DateUtils.parseDate(beginDate_end, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtil.isNotEmpty(typeId)) {//????????????
            cq.eq("projectType.id", typeId);
        }

        if (StringUtil.isNotEmpty(devDepart_departname)) {//????????????
            devDepart_departname = devDepart_departname.replace("*", "%");
            cq.createAlias("devDepart", "devDepart");
            cq.like("devDepart.departname", devDepart_departname);
        }
        if (StringUtil.isNotEmpty(dutyDepart_departname)) {//????????????
            dutyDepart_departname = dutyDepart_departname.replace("*", "%");
            cq.createAlias("dutyDepart", "dutyDepart");
            cq.like("dutyDepart.departname", dutyDepart_departname);
        }
        if (StringUtils.isNotEmpty(allFee_begin)) {//?????????
            cq.add(Restrictions.ge("allFee", new BigDecimal(allFee_begin)));
        }
        if (StringUtils.isNotEmpty(allFee_end)) {//?????????
            cq.add(Restrictions.le("allFee", new BigDecimal(allFee_end)));
        }
        if (StringUtil.isNotEmpty(outsideNo)) {//????????????
            outsideNo = outsideNo.replace("*", "%");
            cq.like("outsideNo", outsideNo);
        }
        if (StringUtil.isNotEmpty(accountingCode)) {//????????????
            accountingCode = accountingCode.replace("*", "%");
            cq.like("accountingCode", accountingCode);
        }
        if (StringUtil.isNotEmpty(sourceUnit)) {//????????????
            sourceUnit = sourceUnit.replace("*", "%");
            cq.like("sourceUnit", sourceUnit);
        }
        // ?????????????????????
        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);
        // ??????????????????
        //        List<TSDataRule> ruleList = (List<TSDataRule>) request.getAttribute(Globals.MENU_DATA_AUTHOR_RULES);
        //        if (ruleList != null && ruleList.size() > 0) {
        //            for (TSDataRule dataRule : ruleList) {
        //                HqlGenerateUtil.addRuleToCriteria(dataRule, String.class, cq);
        //            }
        //        }
        String lxStatus = request.getParameter("lxStatus");
        if (StringUtils.isNotEmpty(lxStatus)) {
        	cq.eq("lxStatus", lxStatus);
        }
        if ("0".equals(approvalStatus)) {//???????????????????????????????????????????????????
            /*cq.add(Restrictions.or(Restrictions.eq("approvalStatus", "0"), Restrictions.eq("approvalStatus", "2"),
                    Restrictions.and(Restrictions.isNull("approvalStatus"), Restrictions.eq("assignFlag", "1"))));*/
            cq.add(Restrictions.or(Restrictions.eq("auditStatus", "0"), Restrictions.eq("auditStatus", "1"),Restrictions.eq("auditStatus", "3"),Restrictions.isNull("auditStatus")));
            if(lxStatus==null){
            	cq.eq("officeStaff", user.getId());
            }
        } else if ("1".equals(approvalStatus)) {
            /*cq.eq("approvalStatus", "1");//??????????????????*/            
        	cq.eq("auditStatus", "2");
        }
        if (StringUtils.isNotEmpty(isFinish)) {
            if ("1".equals(isFinish)) {//?????????????????????
                cq.eq("projectStatus", ProjectConstant.PROJECT_STATUS_EVALUEATED);
            } else {
                cq.notEq("projectStatus", ProjectConstant.PROJECT_STATUS_EVALUEATED);
            }
        }
        cq.notEq("scbz", "1");
        //cq.addOrder("projectStatus", SortDirection.desc);
        //cq.addOrder("beginDate", SortDirection.desc);
        cq.addOrder("createDate", SortDirection.desc);
//        cq.addOrder("projectNo", SortDirection.desc);
        if (StringUtil.isNotEmpty(projectPlanId)) {
            cq.eq("jhid", projectPlanId);
        }
        
        /* add by gt
         * ?????????????????????????????????
         * ???????????? ????????????????????????????????????
         * ???????????????
         * */
        String userName = user.getUserName();
        
        if(!"admin".equals(userName)) {
	        //?????????????????? code
	        String usrRoleCodes = (String)request.getSession().getAttribute("currentRoleCodes"); 
	        usrRoleCodes = usrRoleCodes == null ? "":usrRoleCodes;
	        
	        List<Map<String,Object>> list = this.tPmProjectService.findForJdbc(
	        		"select USER_ID from t_s_user_org where org_id in(SELECT ID FROM t_s_depart START WITH ID=? CONNECT BY PRIOR ID=PARENTDEPARTID )"
	        		, user.getCurrentDepart().getId());
	        
	        String managerIds[] = new String[list.size()];
	        for(int i=0; i<list.size(); i++){
	        	managerIds[i] = (String)list.get(i).get("USER_ID");
	        }
	        
	        //????????????????????????????????????code???DEPARTMENT_LEADER
	        if(usrRoleCodes.contains("DPT_LEADER")) {
	        	cq.in("projectManagerId", managerIds);
	        	//Restrictions.sqlRestriction("");
	        	//cq.in("project_Manager_id", Restrictions.);
	        }else{
	        	cq.eq("projectManagerId", user.getId());
	        }
        }
        
        cq.add();
        this.tPmProjectService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
    @RequestMapping(params = "datagridState2")
    public void datagridState2(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String approvalStatus = request.getParameter("approvalStatus");
        String typeId = request.getParameter("projectType.projectTypeName");
        String projectName = request.getParameter("projectName");
        String projectNo = request.getParameter("projectNo");
        String projectManager = request.getParameter("projectManager");
        String beginDate_begin = request.getParameter("beginDate_begin");
        String beginDate_end = request.getParameter("beginDate_end");
        String devDepart_departname = request.getParameter("devDepart.departname");
        String dutyDepart_departname = request.getParameter("dutyDepart.departname");
        String allFee_begin = request.getParameter("allFee_begin");
        String allFee_end = request.getParameter("allFee_end");
        String outsideNo = request.getParameter("outsideNo");
        String accountingCode = request.getParameter("accountingCode");
        String sourceUnit = request.getParameter("sourceUnit");
        String isFinish = request.getParameter("isFinish");
        String projectPlanId = request.getParameter("projectPlanId");
        String lxyj = request.getParameter("lxyj");
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        
        //?????????????????????
        if(StringUtil.isNotEmpty(lxyj)) {//????????????
//        	cq.eq("lxyj", lxyj);
        	cq.in("lxyj", lxyj.split(","));
        }
        if (StringUtil.isNotEmpty(projectNo)) {//????????????
            projectNo = projectNo.replace("*", "%");
            cq.like("projectNo", projectNo);
        }
        if (StringUtil.isNotEmpty(projectName)) {//????????????
            projectName = projectName.replace("*", "%");
            cq.like("projectName", projectName);
        }
        if (StringUtil.isNotEmpty(projectManager)) {//???????????????
            projectManager = projectManager.replace("*", "%");
            cq.like("projectManager", projectManager);
        }
        if (StringUtil.isNotEmpty(beginDate_begin)) {//????????????
            try {
                cq.ge("beginDate", DateUtils.parseDate(beginDate_begin, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtil.isNotEmpty(beginDate_end)) {//????????????
            try {
                cq.le("beginDate", DateUtils.parseDate(beginDate_end, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtil.isNotEmpty(typeId)) {//????????????
            cq.eq("projectType.id", typeId);
        }

        if (StringUtil.isNotEmpty(devDepart_departname)) {//????????????
            devDepart_departname = devDepart_departname.replace("*", "%");
            cq.createAlias("devDepart", "devDepart");
            cq.like("devDepart.departname", devDepart_departname);
        }
        if (StringUtil.isNotEmpty(dutyDepart_departname)) {//????????????
            dutyDepart_departname = dutyDepart_departname.replace("*", "%");
            cq.createAlias("dutyDepart", "dutyDepart");
            cq.like("dutyDepart.departname", dutyDepart_departname);
        }
        if (StringUtils.isNotEmpty(allFee_begin)) {//?????????
            cq.add(Restrictions.ge("allFee", new BigDecimal(allFee_begin)));
        }
        if (StringUtils.isNotEmpty(allFee_end)) {//?????????
            cq.add(Restrictions.le("allFee", new BigDecimal(allFee_end)));
        }
        if (StringUtil.isNotEmpty(outsideNo)) {//????????????
            outsideNo = outsideNo.replace("*", "%");
            cq.like("outsideNo", outsideNo);
        }
        if (StringUtil.isNotEmpty(accountingCode)) {//????????????
            accountingCode = accountingCode.replace("*", "%");
            cq.like("accountingCode", accountingCode);
        }
        if (StringUtil.isNotEmpty(sourceUnit)) {//????????????
            sourceUnit = sourceUnit.replace("*", "%");
            cq.like("sourceUnit", sourceUnit);
        }
        // ?????????????????????
        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);
        String lxStatus = request.getParameter("lxStatus");
        if (StringUtils.isNotEmpty(lxStatus)) {
        	cq.eq("lxStatus", lxStatus);
        }
        if ("0".equals(approvalStatus)) {//???????????????????????????????????????????????????
            if(lxStatus==null){
            	cq.eq("officeStaff", user.getId());
            }
        } else if ("1".equals(approvalStatus)) {
        	cq.eq("auditStatus", "2");
        }
        if (StringUtils.isNotEmpty(isFinish)) {
            if ("1".equals(isFinish)) {//?????????????????????
                cq.eq("projectStatus", ProjectConstant.PROJECT_STATUS_EVALUEATED);
            } else {
                cq.notEq("projectStatus", ProjectConstant.PROJECT_STATUS_EVALUEATED);
            }
        }
        cq.notEq("scbz", "1");
        cq.addOrder("createDate", SortDirection.desc);
        
        List<Map<String, Object>> projectIds;
        Map <String,String> xmje = new HashMap<String,String>();
        if (StringUtil.isNotEmpty(projectPlanId)) {
        	String sql = " select f.xmid,f.je from t_pm_project_plan pp ";
        	sql = sql + " join t_pm_fpje f on f.jhwjid = pp.id ";
        	sql = sql + " where pp.id = ? ";
        	projectIds = this.systemService.findForJdbc(sql, projectPlanId);
        	Object[] keyvalue = new Object[projectIds.size()];
        	for (int i = 0; i < projectIds.size(); i++) {
        		Map<String, Object> map = projectIds.get(i);
        		String xmid = (String)map.get("XMID");
        		String je = (String)map.get("JE");
        		keyvalue[i] = xmid;
        		xmje.put(xmid, je);
			}
        	if(keyvalue.length > 0){
        		cq.in("id", keyvalue);
        	}else{
        		cq.eq("id", "-100");
        	}
        	
        }
        cq.add();
        this.tPmProjectService.getDataGridReturn(cq, true);
        
        /*List list = dataGrid.getResults();
        for(Object obj : list) {//budgetAmount
        	TPmProjectEntity m = (TPmProjectEntity)obj;
        	String je = (String)xmje.get( m.getId() );
        	je = StringUtils.isEmpty(je) ? "0" : je;
        	m.setBudgetAmount(new BigDecimal(je));
        }*/
        
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * ????????????????????????
     * 
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "getProjectType")
    @ResponseBody
    public JSONArray getDetailList(HttpServletRequest req, HttpServletResponse response) {
        String needAll = req.getParameter("needAll");
        JSONArray jsonArray = new JSONArray();
        if("true".equals(needAll)){
            JSONObject empty = new JSONObject();
            empty.put("id", "");
            empty.put("text", "?????????");
            jsonArray.add(empty);
        }
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
        String projectTypeId = req.getParameter("projectTypeId");
        if (StringUtils.isNotEmpty(projectTypeId)) {
            TBProjectTypeEntity projectType = this.systemService.get(TBProjectTypeEntity.class, projectTypeId);
            if(projectType!=null && "0".equals(projectType.getValidFlag())){
                JSONObject json = new JSONObject();
                json.put("id", projectType.getId());
                json.put("text", projectType.getProjectTypeName() + "(?????????)");
                jsonArray.add(json);
            }
        }
        return jsonArray;
    }

    /**
     * ??????????????????
     * 
     * @param parent
     * @return
     */
    private JSONObject getTreeGrid(TBProjectTypeEntity parent) {
        JSONObject json = new JSONObject();
        json.put("id", parent.getId());
        json.put("text", parent.getProjectTypeName());
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

    /**
     * ???????????????????????????????????????????????????
     * 
     * @param tPmProject
     * @param request
     * @return
     */
    @RequestMapping(params = "applyPass")
    @ResponseBody
    public AjaxJson applyPass(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProject = systemService.getEntity(TPmProjectEntity.class, tPmProject.getId());
        message = "????????????";
        try {
            TSUser user = ResourceUtil.getSessionUserName();
            String approvalStatus = request.getParameter("state");
            if ("1".equals(approvalStatus) && !"1".equals(tPmProject.getApprovalStatus())) {//????????????????????????????????????
                PrimaryGenerater generater = PrimaryGenerater.getInstance();
                String projectNo = generater.generaterProjectNo();//??????????????????
                tPmProject.setProjectNo(projectNo);
            }
            tPmProject.setApprovalStatus(approvalStatus);
            tPmProject.setApprovalUserid(user.getId());
            tPmProject.setApprovalDeptId(user.getCurrentDepart().getId());
            tPmProject.setApprovalDate(new Date());
            tPmProjectService.updateEntitie(tPmProject);
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @param tPmProject
     * @param request
     * @return
     */
    @RequestMapping(params = "changeApprovalState")
    @ResponseBody
    public AjaxJson changeApprovalState(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProject = systemService.getEntity(TPmProjectEntity.class, tPmProject.getId());
        message = "????????????";
        try {
            //modified by zhangls 2016-03-03
            //???????????????????????????????????????????????????????????????
            String state = request.getParameter("state");
            tPmProject.setApprovalStatus(state);
            tPmProject.setApprovalUserid(ResourceUtil.getSessionUserName().getId());
            tPmProject.setApprovalDate(new Date());
            tPmProjectService.updateEntitie(tPmProject);

            if (StringUtil.isNotEmpty(tPmProject.getProjectManagerId())) {
                TSUser user = ResourceUtil.getSessionUserName();
                if (state.equals(SrmipConstants.YES)) {
                    tOMessageService.sendMessage(tPmProject.getProjectManagerId(), "????????????????????????", "????????????",
                            "??????????????????[" + tPmProject.getProjectName() + "]????????????", user.getId());
                } else {
                    tOMessageService.sendMessage(tPmProject.getProjectManagerId(), "????????????????????????", "????????????",
                            "??????????????????[" + tPmProject.getProjectName() + "]????????????", user.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
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
    public void datagrid(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        cq.add();
        this.tPmProjectService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
    @RequestMapping(params = "datagridGd")
    public void datagridGd(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        cq.eq("gdStatus", "0");
        cq.add();
        this.tPmProjectService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridForContractCheck")
    public void datagridForContractCheck(TPmProjectEntity tPmProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        try {
            //???????????????????????????
            String sql = "SELECT T_P_ID FROM t_pm_income_contract_appr T ";
            List<String> list = systemService.findListbySql(sql);
            if (list != null && list.size() > 0) {
                cq.in("id", list.toArray());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmProjectService.getDataGridReturn(cq, true);
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

    @RequestMapping(params = "projectList")
    public void projectList(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        String lx = request.getParameter("lx");
        //cq.isNull("parentPmProject.id");
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        try {
            String excludeIds = request.getParameter("excludeIds");
            if (StringUtils.isNotEmpty(excludeIds)) {
                String[] ids = excludeIds.split(",");
                cq.add(Restrictions.not(Restrictions.in("id", ids)));
            }
            /*String plan = request.getParameter("plan");
            if ("1".equals(plan)) {     
            	cq.eq("planContractFlag", "1");//??????????????????               
            }*/
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.notEq("scbz", "1");
        if("1".equals(lx)){
          cq.isNotNull("projectNo");
          cq.eq("lxStatus", "1");
        }else{
          cq.isNull("projectNo");
          cq.eq("projectStatus", "01");
        }
        
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tPmProjectService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProject = systemService.getEntity(TPmProjectEntity.class, tPmProject.getId());
        message = "?????????????????????????????????";
        try {
            tPmProjectService.delete(tPmProject);
            //			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
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
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmProjectEntity tPmProject = systemService.getEntity(TPmProjectEntity.class, id);
                tPmProjectService.delete(tPmProject);
                //				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
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
    @RequestMapping(params = "doDelete")
    @ResponseBody
    public AjaxJson doDelete(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmProjectEntity tPmProject = systemService.getEntity(TPmProjectEntity.class, id);
                tPmProject.setScbz("1");
                tPmProjectService.saveOrUpdate(tPmProject);
                //systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
            
            //???????????????ID?????????????????????????????????????????????????????????
            String jhwjid = request.getParameter("jhwjid");
            if(StringUtils.isNotEmpty(jhwjid)) {
            	ids = "'"+ids.replace(",", "','")+"'";
            	String sql = "delete from t_pm_fpje where JHWJID= ? and XMID in("+ids+") ";
            	tPmProjectService.executeSql(sql, jhwjid);
            }
            
        } catch (Exception e) {
            //e.printStackTrace();
            message = "?????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            tPmProject.setProjectName(StringUtils.trim(tPmProject.getProjectName()));
            tPmProject.setPlanContractFlag(tPmProject.getLxyj());
            
            String xmbhSql = "select t.PROJECT_NO,lsh from T_PM_PROJECT t where lsh is not null order by to_number(lsh) desc";
            List<Map<String, Object>> numMapList = this.systemService.findForJdbc(xmbhSql);
            String mr = "0000";
            int lshTemp;
            if (numMapList!=null && numMapList.size() > 0 && numMapList.get(0).get("lsh")!="" && numMapList.get(0).get("lsh")!=null) {
                String lsh = (String) numMapList.get(0).get("lsh");
                lshTemp = Integer.parseInt(lsh);
                lshTemp++;
                if(lshTemp<10){
                	mr = "000"+lshTemp;
                }else if(lshTemp<100){
                	mr = "00"+lshTemp;
                }else if(lshTemp<1000){
                	mr = "0"+lshTemp;
                }else{
                	mr = String.valueOf(lshTemp);
                }
            }
        	tPmProject.setLsh(mr);
        	
            if("1".equals(tPmProject.getLxStatus())){
        		List<TBXmlbEntity> xmlb = this.systemService.findByProperty(TBXmlbEntity.class, "id", tPmProject.getXmlb().getId());
                tPmProject.setXmml(xmlb.get(0).getParentType().getXmlb());
                tPmProject.setZgdw(xmlb.get(0).getZgdw());
                tPmProject.setXmlx(xmlb.get(0).getXmlx());
                tPmProject.setXmxz(xmlb.get(0).getXmxz());
                tPmProject.setXmly(xmlb.get(0).getXmly());
                String xmlx = xmlb.get(0).getXmlx();
                String xmxz = xmlb.get(0).getXmxz();
                String xmly = xmlb.get(0).getXmly();
                String rwzd = tPmProject.getRwzd();
                String lxyj = tPmProject.getLxyj();
                String wybh = "";
                if(xmlb.get(0).getWybh()!=null){
                	wybh = xmlb.get(0).getWybh().toString();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String bd = sdf.format(tPmProject.getBeginDate()).substring(2, 4);
                String ed = sdf.format(tPmProject.getEndDate()).substring(2, 4);
                String xmbh="";
                xmbh = xmlx+xmxz+xmly+rwzd+wybh+lxyj+bd+ed+mr;
            	tPmProject.setProjectNo(xmbh);
        	}else{
        		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
                String dqsj = sdf2.format(new Date());
                tPmProject.setCxm(dqsj+mr);
        	}
            
            tPmProject.setAuditStatus("0");
            if(tPmProject.getGlxm()!=null && !tPmProject.getGlxm().getId().equals("")){
            	String sql = "select cxm from t_pm_project where id='"+tPmProject.getGlxm().getId()+"'";
                List<Map<String, Object>> list = systemService.findForJdbc(sql);
                if(list.size()>0 && list.get(0).get("CXM")!=null){
                	tPmProject.setCxm(list.get(0).get("cxm").toString());
                }
            }
            tPmProject.setScbz("0");
            if(StringUtils.isNotBlank(tPmProject.getProjectNo())  
            		&& (tPmProject.getGlxm()==null||tPmProject.getGlxm().getId().equals("") )) {
            	//??????????????????????????????????????????????????????????????????
            	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
                String dqsj = sdf2.format(new Date());
                tPmProject.setCxm(dqsj+mr);
                
                TPmProjectEntity tSbPmProject = createAndSaveSbProject(tPmProject,request);
                tPmProject.setCxm(tSbPmProject.getCxm());
                tPmProject.setGlxm(tSbPmProject);
            }
            tPmProject.getLxyj();//schoolCooperationFlag
            
            tPmProjectService.save(tPmProject);
            
            
            if(tPmProject.getLxStatus().equals("1")){
              TPmFpje fpje = new TPmFpje();
              fpje.setJhwjid(tPmProject.getJhid());
              fpje.setXmid(tPmProject.getId());
              //??????????????????
              /*if(tPmProject.getLxyj().equals("2") && tPmProject.getSchoolCooperationFlag().equals("1")){
            	  fpje.setJe(tPmProject.getAllFee().toString());
              }else{
            	  fpje.setJe("0");
              }*/
              fpje.setJe("0");
              
              systemService.save(fpje);
            }
            if(tPmProject.getLxStatus().equals("1")) {//????????????????????????????????????????????????????????????
            	String sql= "select ID from t_pm_project where LX_STATUS is null and project_no is null and CXM = ?";
            	Map<String, Object> map = tPmProjectService.findOneForJdbc(sql, tPmProject.getCxm());
        		if(map!=null && map.get("ID")!=null) {
        			TPmProjectEntity pProject = systemService.get(TPmProjectEntity.class, (String)map.get("ID"));
        			pProject.setProjectStatus("97");//???????????????
        			systemService.updateEntitie(pProject);
        		}	
            }
            if (StringUtil.isNotEmpty(tPmProject.getProjectManagerId())) {//????????????????????????????????????????????????
                TSUser user = ResourceUtil.getSessionUserName();
                TSUser muser = systemService.get(TSUser.class, tPmProject.getProjectManagerId());
                TPmProjectMemberEntity member = new TPmProjectMemberEntity();
                member.setProject(tPmProject);
                member.setBirthday(muser.getBirthday());
                member.setUser(muser);
                member.setName(muser.getRealName());
                member.setProjectManager(SrmipConstants.YES);
                member.setSex(muser.getSex());
                member.setPosition(muser.getDuty());
                member.setProjectName(tPmProject.getProjectName());
                if (StringUtil.isNotEmpty(request.getParameter("orgId"))) {
                    TSDepart depart = systemService.get(TSDepart.class, request.getParameter("orgId"));
                    member.setSuperUnit(depart);
                    member.setSuperUnitId(depart.getId());
                    member.setSuperUnitName(depart.getDepartname());
                }
                systemService.save(member);

            }
            if (tPmProject.getMergeFlag().equals(SrmipConstants.YES)) {
                String mergePId = request.getParameter("mergePId");
                String[] mergeids = mergePId.split(",");
                for (String mergeid : mergeids) {
                    TPmProjectEntity mergeProj = systemService.get(TPmProjectEntity.class, mergeid);
                    mergeProj.setMergeFlag(ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//????????????(0????????????1????????????????????????2????????????)
                    mergeProj.setMergeProject(tPmProject);
                    systemService.updateEntitie(mergeProj);
                }
            }
            //			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "?????????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        /*j.setObj(JSONHelper.bean2json(tPmProject));*/
        j.setObj(tPmProject);
        j.setMsg(message);
        return j;
    }
    
 
    private TPmProjectEntity createAndSaveSbProject(TPmProjectEntity tPmProject,HttpServletRequest request) {
    	TPmProjectEntity sbProject = (TPmProjectEntity)tPmProject.clone();
    	
    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String dqsj = sdf2.format(new Date());
        sbProject.setCxm(dqsj+tPmProject.getLsh());
        sbProject.setAuditStatus("2");//?????????
        sbProject.setProjectNo("");//??????????????????
        sbProject.setLxStatus(null);//?????????
        
        tPmProjectService.save(sbProject);
        if (StringUtil.isNotEmpty(sbProject.getProjectManagerId())) {//????????????????????????????????????????????????
            TSUser muser = systemService.get(TSUser.class, sbProject.getProjectManagerId());
            TPmProjectMemberEntity member = new TPmProjectMemberEntity();
            member.setProject(sbProject);
            member.setBirthday(muser.getBirthday());
            member.setUser(muser);
            member.setName(muser.getRealName());
            member.setProjectManager(SrmipConstants.YES);
            member.setSex(muser.getSex());
            member.setPosition(muser.getDuty());
            member.setProjectName(sbProject.getProjectName());
            if (StringUtil.isNotEmpty(request.getParameter("orgId"))) {
                TSDepart depart = systemService.get(TSDepart.class, request.getParameter("orgId"));
                member.setSuperUnit(depart);
                member.setSuperUnitId(depart.getId());
                member.setSuperUnitName(depart.getDepartname());
            }
            systemService.save(member);

        }
        if (sbProject.getMergeFlag().equals(SrmipConstants.YES)) {
            String mergePId = request.getParameter("mergePId");
            String[] mergeids = mergePId.split(",");
            for (String mergeid : mergeids) {
                TPmProjectEntity mergeProj = systemService.get(TPmProjectEntity.class, mergeid);
                mergeProj.setMergeFlag(ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//????????????(0????????????1????????????????????????2????????????)
                mergeProj.setMergeProject(sbProject);
                systemService.updateEntitie(mergeProj);
            }
        }
        return sbProject;
    }

    /**
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
    /**
     * @param tPmProject
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "?????????????????????????????????";
        try {
            tPmProject.setProjectName(StringUtils.trim(tPmProject.getProjectName()));
            if (StringUtils.isNotEmpty(tPmProject.getId())) {
                TPmProjectEntity t = tPmProjectService.get(TPmProjectEntity.class, tPmProject.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmProject, t);
                if ("2".equals(t.getApprovalStatus())) {//???????????????????????????
                    t.setApprovalStatus("0");
                }
                if(tPmProject.getGlxm()!=null && !tPmProject.getGlxm().getId().equals("")){
                	String sql = "select cxm from t_pm_project where id='"+tPmProject.getGlxm().getId()+"'";
                    List<Map<String, Object>> list = systemService.findForJdbc(sql);
                    if(list.size()>0 && list.get(0).get("CXM")!=null){
                    	t.setCxm(list.get(0).get("CXM").toString());
                    }
                }
                tPmProjectService.saveOrUpdate(t);
            } else {
                tPmProjectService.save(tPmProject);
                message = "?????????????????????????????????";
            }
          //TODO	??????
           /* if(tPmProject.getSchoolCooperationFlag().equals("1")){
            	TPmProjectEntity project = systemService.get(TPmProjectEntity.class, tPmProject.getId());
            	addXZIncomeApply(project);
            }*/
            
            if (tPmProject.getMergeFlag().equals(SrmipConstants.YES)) {
                String mergePId = request.getParameter("mergePId");
                String[] mergeids = mergePId.split(",");
                for (String mergeid : mergeids) {
                    TPmProjectEntity mergeProj = systemService.get(TPmProjectEntity.class, mergeid);
                    mergeProj.setMergeFlag(ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);//????????????(0????????????1????????????????????????2????????????)
                    mergeProj.setMergeProject(tPmProject);
                    systemService.updateEntitie(mergeProj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "???????????????????????????/????????????";
            throw new BusinessException(message, e);
        }
        j.setObj(tPmProject);
        j.setMsg(message);
        return j;
    }
    //??????
    private void addXZIncomeApply(TPmProjectEntity tPmProject){
    	//???????????????
    	TPmIncomeApplyEntity incomeApplyEntity = systemService.findUniqueByProperty(TPmIncomeApplyEntity.class, "project", tPmProject);
    	if(!StringUtil.isNotEmpty(incomeApplyEntity)){
    		incomeApplyEntity = new TPmIncomeApplyEntity();
    	}
    	//?????????
    	TPmProjectEntity parentProject = tPmProject.getParentPmProject();
    	parentProject = systemService.get(TPmProjectEntity.class, parentProject.getId());
    	
    	List<TPmIncomeApplyEntity> parentIncomeApplyEntityList = systemService.findByProperty(TPmIncomeApplyEntity.class, "project", parentProject);
    	if(parentIncomeApplyEntityList.size() > 0){
    		incomeApplyEntity.setParentApplyId(parentIncomeApplyEntityList.get(0).getId());
    	}
    	Date time = new Date();
    	incomeApplyEntity.setProject(tPmProject);
    	incomeApplyEntity.setApplyAmount(tPmProject.getBudgetAmount());
    	incomeApplyEntity.setVoucherNo("");
    	incomeApplyEntity.setApplyYear(time.getYear() + "");
    	incomeApplyEntity.setIncomeTime(time);
    	incomeApplyEntity.setIncomeAmount(tPmProject.getBudgetAmount());
    	incomeApplyEntity.setIncomeRemark("????????????");
    	incomeApplyEntity.setAuditStatus("0");//????????????????????????0????????????
    	//????????????????????????
    	incomeApplyEntity.setYsStatus("0");
    	//???????????????
    	Long count = this.tBPmIncomeApplyService.getCountForJdbc("select count(*) from T_PM_INCOME_APPLY where PARENT_APPLY_ID is not null ") + 1;
    	count = count + this.tBPmIncomeApplyService.getCountForJdbc("select count(*) from T_PM_INCOME_PLAN where PARENT_PLAN_ID is not null ");
    	String barcodeNum = "0000000" + count;
    	barcodeNum = barcodeNum.substring(barcodeNum.length() - 7, barcodeNum.length());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String currentYear = sdf.format(date);
    	String barcode = "KX" + currentYear + barcodeNum;
    	incomeApplyEntity.setBarcode(barcode);
    	systemService.saveOrUpdate(incomeApplyEntity);
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmProjectEntity tPmProject, HttpServletRequest req) {
        String assignFlag = req.getParameter("assignFlag");
        if ("1".equals(assignFlag)) {
            tPmProject.setAssignFlag("1");
        }
        String entryType = req.getParameter("entryType");
        if(StringUtil.isNotEmpty(entryType)){
        	req.setAttribute("entryType", entryType);
        }
        String type = req.getParameter("type");
        if(StringUtil.isNotEmpty(type)){
        	req.setAttribute("type", type);
        }
        String projectPlanId = req.getParameter("projectPlanId");
        if(StringUtil.isNotEmpty(projectPlanId)){
        	req.setAttribute("projectPlanId", projectPlanId);
        }
        if (StringUtil.isNotEmpty(tPmProject.getId())) {
        	tPmProject = tPmProjectService.getEntity(TPmProjectEntity.class, tPmProject.getId());
        }
        req.setAttribute("tPmProjectPage", tPmProject);
        return new ModelAndView("com/kingtake/project/manage/tPmProject-add");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmProjectEntity tPmProject, HttpServletRequest req) {
        // ????????????:?????????
        req.setAttribute("applying", ProjectConstant.PROJECT_STATUS_APPLYING);

        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = tPmProjectService.getEntity(TPmProjectEntity.class, tPmProject.getId());
            TPmProjectEntity parentProject = tPmProject.getParentPmProject();
            if (parentProject != null) {
                parentProject = systemService.get(TPmProjectEntity.class, parentProject.getId());
                tPmProject.setParentPmProject(parentProject);
            }
            req.setAttribute("tPmProjectPage", tPmProject);
            req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tPmProject.getPlanContractFlag()));
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectEntity.class);
            mcq.eq("mergeProject.id", tPmProject.getId());
            mcq.add();
            List<TPmProjectEntity> mergeProjList = systemService.getListByCriteriaQuery(mcq, false);
            req.setAttribute("mergeProjList", mergeProjList);

            String userId = tPmProject.getOfficeStaff();
            if (StringUtil.isNotEmpty(userId)) {
                TSUser user = systemService.get(TSUser.class, userId);
                if (user != null) {
                    req.setAttribute("officeStaffRealname", user.getRealName());
                }
            }

            String userId2 = tPmProject.getDepartStaff();
            if (StringUtil.isNotEmpty(userId2)) {
                TSUser user = systemService.get(TSUser.class, userId2);
                if (user != null) {
                    req.setAttribute("departStaffRealname", user.getRealName());
                }
            }

        }
        return new ModelAndView("com/kingtake/project/manage/tPmProject-update");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateForResearchGroup")
    public ModelAndView goUpdateForResearchGroup(TPmProjectEntity tPmProject, HttpServletRequest req) {
        // ????????????:?????????
        req.setAttribute("applying", ProjectConstant.PROJECT_STATUS_APPLYING);

        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = tPmProjectService.getEntity(TPmProjectEntity.class, tPmProject.getId());
            TPmProjectEntity parentProject = tPmProject.getParentPmProject();
            if (parentProject != null) {
                parentProject = systemService.get(TPmProjectEntity.class, parentProject.getId());
                tPmProject.setParentPmProject(parentProject);
            }
            req.setAttribute("tPmProjectPage", tPmProject);
            req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tPmProject.getPlanContractFlag()));
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectEntity.class);
            mcq.eq("mergeProject.id", tPmProject.getId());
            mcq.add();
            List<TPmProjectEntity> mergeProjList = systemService.getListByCriteriaQuery(mcq, false);
            req.setAttribute("mergeProjList", mergeProjList);

            String userId = tPmProject.getOfficeStaff();
            if (StringUtil.isNotEmpty(userId)) {
                TSUser user = systemService.get(TSUser.class, userId);
                if (user != null) {
                    req.setAttribute("officeStaffRealname", user.getRealName());
                }
            }

            String userId2 = tPmProject.getDepartStaff();
            if (StringUtil.isNotEmpty(userId2)) {
                TSUser user = systemService.get(TSUser.class, userId2);
                if (user != null) {
                    req.setAttribute("departStaffRealname", user.getRealName());
                }
            }

        }
        String load = req.getParameter("load");
        if(StringUtils.isNotEmpty(load)){
            req.setAttribute("load", load);
        }
     
        return new ModelAndView("com/kingtake/project/manage/tPmProject-updateForResearchGroup");
    }

    /**
     * ???????????????????????????????????????(??????)
     * 
     * @return
     */
    @RequestMapping(params = "goEditForResearchGroup")
    public ModelAndView goEditForResearchGroup(TPmProjectEntity tPmProject, HttpServletRequest req) {
    	//====================lijun????????????????????????????????????????????????????????????
    	if(req.getParameter("type") != null && "GL".equals(req.getParameter("type"))) {
    		String sql = "select t.ID from t_pm_project t where t.LX_STATUS = 1 and t.CXM = (select t2.CXM from t_pm_project t2 where t2.ID = ?)";
    		Map<String, Object> map = tPmProjectService.findOneForJdbc(sql, tPmProject.getId());
    		if(map!=null && map.get("ID")!=null) 
    			tPmProject.setId((String)map.get("ID"));
    		else
    			tPmProject.setId(null);
    	}
        // ????????????:?????????
        req.setAttribute("applying", ProjectConstant.PROJECT_STATUS_APPLYING);

        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = tPmProjectService.getEntity(TPmProjectEntity.class, tPmProject.getId());
            req.setAttribute("tPmProjectPage", tPmProject);
            req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tPmProject.getPlanContractFlag()));
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectEntity.class);
            mcq.eq("mergeProject.id", tPmProject.getId());
            mcq.add();
            List<TPmProjectEntity> mergeProjList = systemService.getListByCriteriaQuery(mcq, false);
            req.setAttribute("mergeProjList", mergeProjList);

            String userId = tPmProject.getOfficeStaff();
            if (StringUtil.isNotEmpty(userId)) {
                TSUser user = systemService.get(TSUser.class, userId);
                if (user != null) {
                    req.setAttribute("officeStaffRealname", user.getRealName());
                }
            }

            String userId2 = tPmProject.getDepartStaff();
            if (StringUtil.isNotEmpty(userId2)) {
                TSUser user = systemService.get(TSUser.class, userId2);
                if (user != null) {
                    req.setAttribute("departStaffRealname", user.getRealName());
                }
            }
        }
        String type = req.getParameter("type");
        if(StringUtils.isNotEmpty(type)){
            req.setAttribute("type", type);
        }
        req.setAttribute("operCode", req.getParameter("operCode"));
        return new ModelAndView("com/kingtake/project/manage/tPmProject-editForDepart");
    }
    
    /**
     * ???????????????????????????????????????(??????)
     * 
     * @return
     */
    @RequestMapping(params = "goEditByLx")
    public ModelAndView goEditByLx(TPmProjectEntity tPmProject, HttpServletRequest req) {
        // ????????????:?????????
        req.setAttribute("applying", ProjectConstant.PROJECT_STATUS_APPLYING);
        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = tPmProjectService.getEntity(TPmProjectEntity.class, tPmProject.getId());
            req.setAttribute("tPmProjectPage", tPmProject);
            req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tPmProject.getPlanContractFlag()));
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectEntity.class);
            mcq.eq("mergeProject.id", tPmProject.getId());
            mcq.add();
            List<TPmProjectEntity> mergeProjList = systemService.getListByCriteriaQuery(mcq, false);
            req.setAttribute("mergeProjList", mergeProjList);

            String userId = tPmProject.getOfficeStaff();
            if (StringUtil.isNotEmpty(userId)) {
                TSUser user = systemService.get(TSUser.class, userId);
                if (user != null) {
                    req.setAttribute("officeStaffRealname", user.getRealName());
                }
            }

            String userId2 = tPmProject.getDepartStaff();
            if (StringUtil.isNotEmpty(userId2)) {
                TSUser user = systemService.get(TSUser.class, userId2);
                if (user != null) {
                    req.setAttribute("departStaffRealname", user.getRealName());
                }
            }
        }
        req.setAttribute("operCode", req.getParameter("operCode"));
        return new ModelAndView("com/kingtake/project/manage/tPmProject-updateByLx");
    }

    /**
     * ???????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "checkProjectNo")
    @ResponseBody
    public AjaxJson checkProjectNo(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        Criteria criteria = systemService.getSession().createCriteria(TPmProjectEntity.class);
        criteria.add(Restrictions.eq("projectNo", tPmProject.getProjectNo()));
        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            criteria.add(Restrictions.ne("id", tPmProject.getId()));
        }
        List<TPmProjectEntity> list = criteria.list();
        if (list != null && list.size() > 0) {
            j.setSuccess(false);
        }

        return j;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
    	req.setAttribute("lxStatus", req.getParameter("lxStatus"));
    	req.setAttribute("projectPlanId", req.getParameter("projectPlanId"));
        return new ModelAndView("com/kingtake/project/manage/tPmProjectUpload");
    }

    /**
     * ??????excel
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
        modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("???????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjects);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    
    
    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsPmState")
    public String exportXlsPmState(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
    	
    	String approvalStatus = request.getParameter("approvalStatus");
        String typeId = request.getParameter("projectType.projectTypeName");
        String projectName = request.getParameter("projectName");
        String projectNo = request.getParameter("projectNo");
        String projectManager = request.getParameter("projectManager");
        String beginDate_begin = request.getParameter("beginDate_begin");
        String beginDate_end = request.getParameter("beginDate_end");
        String devDepart_departname = request.getParameter("devDepart.departname");
        String dutyDepart_departname = request.getParameter("dutyDepart.departname");
        String allFee_begin = request.getParameter("allFee_begin");
        String allFee_end = request.getParameter("allFee_end");
        String outsideNo = request.getParameter("outsideNo");
        String accountingCode = request.getParameter("accountingCode");
        String sourceUnit = request.getParameter("sourceUnit");
        String hideFinish = request.getParameter("hideFinish");
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);        
       
        
        //?????????????????????
        //org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        if (StringUtil.isNotEmpty(projectNo)) {//????????????
            projectNo = projectNo.replace("*", "%");
            cq.like("projectNo", projectNo);
        }
        if (StringUtil.isNotEmpty(projectName)) {//????????????
            projectName = projectName.replace("*", "%");
            cq.like("projectName", projectName);
        }
        if (StringUtil.isNotEmpty(projectManager)) {//???????????????
            projectManager = projectManager.replace("*", "%");
            cq.like("projectManager", projectManager);
        }
        if (StringUtil.isNotEmpty(beginDate_begin)) {//????????????
            try {
                cq.ge("beginDate", DateUtils.parseDate(beginDate_begin, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtil.isNotEmpty(beginDate_end)) {//????????????
            try {
                cq.le("beginDate", DateUtils.parseDate(beginDate_end, "yyyy-MM-dd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtil.isNotEmpty(typeId)) {//????????????
            cq.eq("projectType.id", typeId);
        }

        if (StringUtil.isNotEmpty(devDepart_departname)) {//????????????
            devDepart_departname = devDepart_departname.replace("*", "%");
            cq.createAlias("devDepart", "devDepart");
            cq.like("devDepart.departname", devDepart_departname);
        }
        if (StringUtil.isNotEmpty(dutyDepart_departname)) {//????????????
            dutyDepart_departname = dutyDepart_departname.replace("*", "%");
            cq.createAlias("dutyDepart", "dutyDepart");
            cq.like("dutyDepart.departname", dutyDepart_departname);
        }
        if (StringUtils.isNotEmpty(allFee_begin)) {//?????????
            cq.add(Restrictions.ge("allFee", new BigDecimal(allFee_begin)));
        }
        if (StringUtils.isNotEmpty(allFee_end)) {//?????????
            cq.add(Restrictions.le("allFee", new BigDecimal(allFee_end)));
        }
        if (StringUtil.isNotEmpty(outsideNo)) {//????????????
            outsideNo = outsideNo.replace("*", "%");
            cq.like("outsideNo", outsideNo);
        }
        if (StringUtil.isNotEmpty(accountingCode)) {//????????????
            accountingCode = accountingCode.replace("*", "%");
            cq.like("accountingCode", accountingCode);
        }
        if (StringUtil.isNotEmpty(sourceUnit)) {//????????????
            sourceUnit = sourceUnit.replace("*", "%");
            cq.like("sourceUnit", sourceUnit);
        }
        // ?????????????????????
        cq.notEq("mergeFlag", ProjectConstant.PROJECT_MERGE_FLAG_PASSIVE);
        // ??????????????????
        //        List<TSDataRule> ruleList = (List<TSDataRule>) request.getAttribute(Globals.MENU_DATA_AUTHOR_RULES);
        //        if (ruleList != null && ruleList.size() > 0) {
        //            for (TSDataRule dataRule : ruleList) {
        //                HqlGenerateUtil.addRuleToCriteria(dataRule, String.class, cq);
        //            }
        //        }
        if ("0".equals(approvalStatus)) {
            cq.or(Restrictions.eq("approvalStatus", "0"), Restrictions.eq("approvalStatus", "2"));//??????????????????
        } else if ("1".equals(approvalStatus)) {
            cq.eq("approvalStatus", "1");//??????????????????
        }
        if ("1".equals(hideFinish)) {//???????????????????????????
            cq.notEq("projectStatus", ProjectConstant.PROJECT_STATUS_EVALUEATED);
        }

        //cq.addOrder("projectStatus", SortDirection.desc);
        //cq.addOrder("beginDate", SortDirection.desc);
        cq.addOrder("createDate", SortDirection.desc);
        cq.addOrder("projectNo", SortDirection.desc);
        cq.add();

		List<TPmProjectEntity> tPmProjects = this.tPmProjectService.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "?????????????????????");
		modelMap.put(NormalExcelConstants.CLASS, TPmProjectEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,
				new ExportParams("???????????????????????????", "?????????:" + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjects);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
        
        
        
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
//    public String exportXlsByT(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
//            DataGrid dataGrid, ModelMap modelMap) {
//        modelMap.put(TemplateExcelConstants.FILE_NAME, "?????????????????????");
//        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
//        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
//        modelMap.put(TemplateExcelConstants.CLASS, TPmProjectEntity.class);
//        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
//        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
//    }
    
    public void exportXlsByT(HttpServletRequest request, HttpServletResponse response) {
    	tPmProjectService.downloadTemplate(request,response);
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
//            params.setTitleRows(2);
//            params.setHeadRows(1);
//            params.setNeedSave(true);
            try {
//                List<TPmProjectEntity> listTPmProjectEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
//                        TPmProjectEntity.class, params);
//                for (TPmProjectEntity tPmProject : listTPmProjectEntitys) {
//                    tPmProjectService.save(tPmProject);
//                }
            	String msg = "";
                HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
                String lxStatus = request.getParameter("lxStatus");
                String jhid = request.getParameter("projectPlanId");
                msg = tPmProjectService.importProjecjExcel(wb,lxStatus,jhid);
                j.setMsg(msg);
            } catch (Exception e) {
                j.setMsg("?????????????????????");
                e.printStackTrace();
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
     * ??????????????????
     * 
     * @param tPmProject
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForProject")
    public void datagridForProject(TPmProjectEntity tPmProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
        //?????????????????????
        cq.eq("approvalStatus", "1");//?????????????????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProject, request.getParameterMap());
        cq.add();
        this.tPmProjectService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "findInfoById")
    @ResponseBody
    public AjaxJson findInfoById(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = systemService.get(TPmProjectEntity.class, tPmProject.getId());
            Map<String, Object> project = new HashMap<String, Object>();
            project.put("id", tPmProject.getId());
            project.put("projectName", tPmProject.getProjectName());
            project.put("projectManager", tPmProject.getProjectManager());
            project.put("projectManagerId", tPmProject.getProjectManagerId());
            project.put("accountingCode", tPmProject.getAccountingCode());
            project.put("projectMgrDept", tPmProject.getDevDepart().getDepartname());
            j.setAttributes(project);
        }
        return j;
    }

    /**
     * ?????????????????????????????????
     * 
     * @param tPmProject
     * @param req
     * @return
     */
    @RequestMapping(params = "goResearchGroupConfirm")
    public ModelAndView goResearchGroupConfirm(TPmProjectEntity tPmProject, HttpServletRequest req) {
        req.setAttribute("projectId", tPmProject.getId());
        return new ModelAndView("com/kingtake/project/manage/researchGroupConfirm");
    }

    /**
     * ?????????????????????
     * 
     * @param tPmProject
     * @param request
     * @return
     */
    @RequestMapping(params = "researchGroupConfirm")
    @ResponseBody
    public AjaxJson researchGroupConfirm(TPmProjectEntity tPmProject, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProject = systemService.getEntity(TPmProjectEntity.class, tPmProject.getId());
        try {
            if (1==2) {
                message = "?????????????????????????????????????????????????????????????????????????????????????????????";
            } else {
                if (StringUtils.isEmpty(tPmProject.getConfirmBy())) {
                    TSUser user = ResourceUtil.getSessionUserName();
                    tPmProject.setConfirmBy(user.getId());
                    tPmProject.setConfirmDate(new Date());
                    tPmProject.setConfirmName(user.getRealName());
                    tPmProjectService.updateEntitie(tPmProject);
                    message = "????????????";
                    //???????????????????????????????????????????????????????????????  added by zhangls 2016-03-24 begin
                    if (StringUtils.isNotEmpty(tPmProject.getApprovalUserid())) {
                        tOMessageService.sendMessage(tPmProject.getApprovalUserid(), "???????????????????????????", "????????????",
                                "??????[" + tPmProject.getProjectName() + "]??????[" + tPmProject.getConfirmName() + "]?????????",
                                user.getId());
                    }
                    //???????????????????????????????????????????????????????????????  added by zhangls 2016-03-24 end
                } else {
                    message = "??????????????????" + tPmProject.getConfirmName() + "?????????????????????????????????";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, id);
            if (project != null) {
                req.setAttribute("msgText", project.getMsgText());
            }
        }
        return new ModelAndView("com/kingtake/project/plandraft/proposePage");
    }

    /**
     * ??????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String id = req.getParameter("id");
        String msgText = req.getParameter("msgText");
        try {
            this.tPmProjectService.doPropose(id,msgText);
            j.setMsg("???????????????????????????");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("???????????????????????????");
        }
        return j;
    }

    /**
     * ????????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdateUnapproval")
    public ModelAndView goUpdateUnapproval(TPmProjectEntity tPmProject, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmProject.getId())) {
            tPmProject = tPmProjectService.getEntity(TPmProjectEntity.class, tPmProject.getId());
            TPmProjectEntity parentProject = tPmProject.getParentPmProject();
            if (parentProject != null) {
                parentProject = systemService.get(TPmProjectEntity.class, parentProject.getId());
                tPmProject.setParentPmProject(parentProject);
            }
            req.setAttribute("tPmProjectPage", tPmProject);
            req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tPmProject.getPlanContractFlag()));
            CriteriaQuery mcq = new CriteriaQuery(TPmProjectEntity.class);
            mcq.eq("mergeProject.id", tPmProject.getId());
            mcq.add();
            List<TPmProjectEntity> mergeProjList = systemService.getListByCriteriaQuery(mcq, false);
            req.setAttribute("mergeProjList", mergeProjList);

            String userId = tPmProject.getOfficeStaff();
            if (StringUtil.isNotEmpty(userId)) {
                TSUser user = systemService.get(TSUser.class, userId);
                if (user != null) {
                    req.setAttribute("officeStaffRealname", user.getRealName());
                }
            }
            String userId2 = tPmProject.getDepartStaff();
            if (StringUtil.isNotEmpty(userId2)) {
                TSUser user = systemService.get(TSUser.class, userId2);
                if (user != null) {
                    req.setAttribute("departStaffRealname", user.getRealName());
                }
            }
        }
        return new ModelAndView("com/kingtake/project/manage/tPmProject-updateUnapproval");
    }

    @RequestMapping(params = "doSubmit")
    @ResponseBody
    public AjaxJson doSubmit(TPmProjectEntity tPmProject, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
            if (StringUtils.isNotEmpty(tPmProject.getId())) {
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmProject.getId());
                project.setApprovalStatus(ProjectConstant.APPROVAL_NO);//?????????
                this.systemService.updateEntitie(project);
                j.setMsg("?????????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("???????????????????????????");
        }
        return j;
    }

    /**
     * ?????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goCopy")
    public ModelAndView goCopy(TPmProjectEntity tPmProject, HttpServletRequest req) {
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmProject.getId());
        TPmProjectEntity tmp = new TPmProjectEntity();
        try {
            MyBeanUtils.copyBeanNotNull2Bean(project, tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tmp.setId(null);
        tmp.setProjectNo(null);
        tmp.setCreateBy(null);
        tmp.setCreateDate(null);
        tmp.setCreateName(null);
        tmp.setApprovalStatus(null);
        tmp.setApprovalDate(null);
        tmp.setApprovalDeptId(null);
        tmp.setApprovalUserid(null);
        tmp.setAssignFlag(null);
        tmp.setConfirmBy(null);
        tmp.setConfirmDate(null);
        tmp.setConfirmName(null);
        tmp.setMergeFlag(null);
        tmp.setMergeProject(null);
        tmp.setMsgText(null);
        tmp.setProjectStatus(null);
        tmp.setUpdateBy(null);
        tmp.setUpdateDate(null);
        tmp.setUpdateName(null);
        req.setAttribute("tPmProjectPage", tmp);
        req.setAttribute("planContractFlag", ProjectConstant.planContractMap.get(tmp.getPlanContractFlag()));
        String userId2 = tmp.getDepartStaff();
        if (StringUtil.isNotEmpty(userId2)) {
            TSUser user = systemService.get(TSUser.class, userId2);
            if (user != null) {
                req.setAttribute("departStaffRealname", user.getRealName());
            }
        }
        return new ModelAndView("com/kingtake/project/manage/tPmProject-add");
    }
    
    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "getAutoSourceUnitList")
    @ResponseBody
    public JSONArray getAutoSourceUnitList(HttpServletRequest request, HttpServletResponse response, Autocomplete autocomplete) {
        String trem = StringUtil.getEncodePra(request.getParameter("trem"));// ??????????????????
        
        String searchFields = autocomplete.getSearchField();
        String[] searchFieldArr = searchFields.split(",");
        
        String sql = "select * from T_B_CODE_DETAIL "
                   + " where 1!=1 ";
        
        if(StringUtils.isNotEmpty(trem)){
        	for (int i = 0; i < searchFieldArr.length; i++) {
        		sql += "or " + searchFieldArr[i] + " like '%" + trem + "%'";
            }        	
        }  
        
        sql += "and rownum < " + autocomplete.getMaxRows();
        
        List<Map<String, Object>> autoList = systemService.findForJdbc(sql);       
        
        JSONArray array = new JSONArray();        
        for (Map<String, Object> map : autoList) {
        	JSONObject json = new JSONObject();
            for(String key: map.keySet())
            {
            	json.put(key, map.get(key));                   
            }     
            array.add(json);
        }           
        return array;
    }
    
    /**
     * ?????????????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "getSubprojectList")
    @ResponseBody
    public JSONArray getSubprojectList(HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        String applyId = req.getParameter("applyId");
        String sql = "";
        
        /*if(StringUtil.isEmpty(applyId)){
        	sql = "select pro.id AS projectId,pro.project_name AS projectName from t_pm_project pro "                    
                    + " where pro.PARENT_PROJECT = '" + projectId + "' and pro.APPROVAL_STATUS = '1'";
        }else{
        	sql = "select pro.id AS projectId,pro.project_name AS projectName,incomeApp.Apply_Amount AS applyAmount,incomeApp.id AS applyId from t_pm_project pro "
                    + " left join t_pm_income_apply incomeApp on pro.id = incomeApp.t_p_id "
                    + " where pro.PARENT_PROJECT = '" + projectId + "' and pro.APPROVAL_STATUS = '1'";
        }*/
//        sql = "select pro.id AS projectId,pro.project_name AS projectName,nvl(incomeApp.Apply_Amount,0) AS applyAmountSum from t_pm_project pro "
//        		+ " left join   (select t_p_id,nvl(sum(apply_amount),0) as Apply_Amount from   t_pm_income_apply  group by t_p_id) incomeApp on pro.id = incomeApp.t_p_id "
//        		+ " where  pro.PARENT_PROJECT = '" + projectId + "' and pro.APPROVAL_STATUS = '1'";
        List<TPmIncomeApplyEntity> incomeApplyList = this.systemService.findByProperty(TPmIncomeApplyEntity.class, "parentApplyId", applyId);
        if(incomeApplyList.size()>0){
        	sql = "select pro.id AS projectId,pro.project_name AS projectName,incomeApp.Apply_Amount AS applyAmountSum,incomeApp.id AS APPLYID from t_pm_project pro "
                    + " left join t_pm_income_apply incomeApp on pro.id = incomeApp.t_p_id "
                    + " where  pro.PARENT_PROJECT = '" + projectId + "'and incomeApp.parent_apply_id = '"+ applyId +"' and pro.APPROVAL_STATUS = '1'";
        }else{
        	sql = "select pro.id AS projectId,pro.project_name AS projectName,'' AS applyAmountSum,'' AS APPLYID from t_pm_project pro "                    
                    + " where  pro.PARENT_PROJECT = '" + projectId + "' and pro.APPROVAL_STATUS = '1'";
        }      
                     
        List<Map<String, Object>> subprojectList = systemService.findForJdbc(sql);       
     
        JSONArray array = new JSONArray();        
        for (Map<String, Object> map : subprojectList) {
        	JSONObject json = new JSONObject();
        	for(String key: map.keySet())
        	{
        		json.put(key, map.get(key));                   
        	}     
        	array.add(json);
        }           
        return array;      
    }
    
    /**
     * ??????????????????????????? 
     * 
     * @return
     */
    @RequestMapping(params = "getXmlb")
    @ResponseBody
    public JSONArray getXmlb(TBXmlbEntity xmlb, HttpServletRequest request) {
        JSONArray jsonArray = new JSONArray();
        CriteriaQuery cq = new CriteriaQuery(TBXmlbEntity.class);
        cq.isNull("parentType");
        cq.eq("validFlag", "1");
        cq.addOrder("sortCode", SortDirection.asc);
        cq.add();
        List<TBXmlbEntity> parentList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TBXmlbEntity parent : parentList) {
            JSONObject pJson = getXmlbTree(parent);
            jsonArray.add(pJson);
        }
        return jsonArray;
    }
    
    /**
     * ??????????????????
     * 
     * @param parent
     * @return
     */
    private JSONObject getXmlbTree(TBXmlbEntity parent) {
        JSONObject json = new JSONObject();
        json.put("id", parent.getId());
        json.put("text", parent.getXmlb()==null?"":parent.getXmlb());
        json.put("sortCode", parent.getSortCode()==null?"":parent.getSortCode());
        CriteriaQuery subCq = new CriteriaQuery(TBXmlbEntity.class);
        subCq.eq("parentType.id", parent.getId());
        subCq.eq("validFlag", "1");
        subCq.addOrder("sortCode", SortDirection.asc);
        subCq.add();
        List<TBXmlbEntity> subList = this.systemService.getListByCriteriaQuery(subCq, false);
        if (subList.size() > 0) {
            JSONArray array = new JSONArray();
            for (TBXmlbEntity sub : subList) {
                JSONObject subJson = getXmlbTree(sub);
                array.add(subJson);
            }
            json.put("children", array);
        }
        return json;
    }
    
    /**
     * ????????????????????????tab????????????
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/manage/tPmProjectSpListTab");
    }
    
    /**
     * ????????????-??????
     * 
     * @param tPmProject
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridXmsp")
    public void datagridXmsp(TPmProjectEntity tPmProject, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();//????????????

        String temp = "select a.*,b.id as jl_id,c.id as sp_id from t_pm_project a,T_PM_APPR_SEND_LOGS b,T_PM_APPR_RECEIVE_LOGS c,T_PM_FORM_CATEGORY d "
        		+ " where a.id=b.appr_id and b.id=c.SEND_ID and c.SUGGESTION_TYPE=d.id "
        		+ " and c.receive_userid = '"+user.getId()+"' and c.operate_status=0  and c.valid_flag=1 ";
        String name = request.getParameter("name");
        if (StringUtil.isNotEmpty(name)) {
            temp += " and a.PROJECT_NAME LIKE '%" + name + "%'";
        }
        temp += "order by b.OPERATE_DATE DESC,c.OPERATE_TIME DESC";
        List<Map<String, Object>> result = systemService.findForJdbcParam(temp,dataGrid.getPage(), dataGrid.getRows());
        dataGrid.setResults(result);
        dataGrid.setTotal(result.size());

        TagUtil.datagrid(response, dataGrid);
    }
    
    //==================lijun??????????????????????????????????????????????????????????????????
    @RequestMapping(params = "checkProIsContract")
    @ResponseBody
    public String checkProIsContract(HttpServletRequest request, HttpServletResponse response) {
    	String pId = request.getParameter("pId");
    	String sql = "select FINISH_FLAG from t_pm_income_contract_appr where T_P_ID = '" + pId + "' and rownum <= 1";
    	Map<String, Object> map = systemService.findOneForJdbc(sql);
    	String json = "";
    	if(map == null) 
    		json = "{\"FINISH_FLAG\": \"0\"}";
    	else
    		json = "{\"FINISH_FLAG\": \"" + map.get("FINISH_FLAG") + "\"}";
    	return json;
    }
       
}
