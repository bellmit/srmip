package com.kingtake.project.controller.incomeplan;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ContextHolderUtils;
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
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeplan.TPmFpje;
import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;
import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.incomeplan.TPmIncomePlanServiceI;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import sun.misc.BASE64Encoder;

/**
 * @Title: Controller
 * @Description: 计划下达
 * @author onlineGenerator
 * @date 2016-01-21 20:20:22
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmIncomePlanController")
public class TPmIncomePlanController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmIncomePlanController.class);

    @Autowired
    private TPmIncomePlanServiceI tBPmIncomePlanService;
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
     * 计划下达列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "incomePlanList")
    public ModelAndView incomePlanList(HttpServletRequest request) {
    	String stage = request.getParameter("stage");
    	request.setAttribute("stage", stage);
    	String projectPlanId = request.getParameter("id");
    	request.setAttribute("projectPlanId", projectPlanId);
        return new ModelAndView("com/kingtake/project/incomeplan/incomePlanListForDepartment2");
    }

    /**
     * 项目来款申请TAB页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBPmIncomeApplyList")
    public ModelAndView tBPmIncomeApplyListTab(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity projectEntity = systemService.get(TPmProjectEntity.class, projectId);
            String planContractFlag = projectEntity.getPlanContractFlag();
            String schoolCooperationFlag = "0";
            if(projectEntity.getParentPmProject() != null){                	
            	if(!StringUtil.isEmpty(projectEntity.getParentPmProject().getId())){
                	schoolCooperationFlag = "1";
                }
            }
            if (StringUtils.isNotEmpty(planContractFlag) || StringUtils.isNotEmpty(schoolCooperationFlag)) {
            	request.setAttribute("planContractFlag", planContractFlag);
            	request.setAttribute("schoolCooperationFlag", schoolCooperationFlag);
            	//计划类
            	if(planContractFlag.equals("1")){
            		return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyList");
            	}
                //校内协作
            	else if(schoolCooperationFlag == "1"){
                	return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyList");
                }else{
            		return new ModelAndView("com/kingtake/project/incomeapply/incomeApplyList-tab");
            	}

            }else{
            	return new ModelAndView("com/kingtake/project/incomeapply/incomeApplyList-tab");
            }
        }else{
        	return new ModelAndView("com/kingtake/project/incomeapply/incomeApplyList-tab");
        }        
    }
    
    /**
     * 项目来款 申请信息列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "incomeApplyList")
    public ModelAndView tBPmIncomeApplyList(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        String planContractFlag = request.getParameter("planContractFlag");
        if (StringUtils.isNotEmpty(planContractFlag)) {
        	request.setAttribute("planContractFlag",planContractFlag);
        }
        String schoolCooperationFlag = request.getParameter("schoolCooperationFlag");
        if (StringUtils.isNotEmpty(schoolCooperationFlag)) {
        	request.setAttribute("schoolCooperationFlag",schoolCooperationFlag);
        }
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyList");
    }

    /**
     * 项目来款 申请机关列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "incomeApplyTab")
    public ModelAndView incomeApplyTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/incomeapply/incomeApply-tab");
    }
    
    /**
     * 机关打印校内协作（计划类）单据页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "incomePlanListForDepartmentWord")
    public ModelAndView incomePlanListForDepartmentWord(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/incomeplan/incomePlanListForDepartmentWord");
    }

    /**
     * 项目来款 申请机关列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBPmIncomeApplyListForDepartment")
    public ModelAndView tBPmIncomeApplyListForDepartment(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
        }
        String type = request.getParameter("type");
        if ("1".equals(type)) {
            return new ModelAndView("com/kingtake/project/incomeapply/incomeApplyListAuditForDepartment");
        } else {
            return new ModelAndView("com/kingtake/project/incomeapply/incomeApplyListAuditedForDepartment");
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

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomePlanEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomePlan,
                request.getParameterMap());
        try {
        	String projectId = request.getParameter("projectId");
        	String projectPlanId = request.getParameter("projectPlanId");
        	String stage = request.getParameter("stage");
        	if(!StringUtil.isEmpty(projectId)){
        		cq.eq("project.id", projectId);
        	}
        	if(!StringUtil.isEmpty(projectPlanId)){
        		cq.eq("projectPlanId", projectPlanId);
        	}
        	if("sh".equals(stage)){
        		cq.ge("approvalStatus", "1");
        	}else if ("ys".equals(stage)){
        		cq.eq("approvalStatus", "2");
        		cq.eq("ysStatus", "0");
        	}
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmIncomePlanService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagridNew")
    public void datagridNew(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
    	String projectId = request.getParameter("projectId");
    	TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
    	if(!"1".equals(project.getLxStatus())) {
    		 String sql = "select id as \"id\" from t_pm_project where lx_status = ? and cxm = ?";
//    		 Map<String, Object> lxProject = this.systemService.findOneForJdbc(sql, "1", project.getCxm());
    		 Map<String, Object> lxProject = this.systemService.findForJdbc(sql, "1", project.getCxm()).get(0);
    		 
    		 if(lxProject != null) {
    			 projectId = lxProject.get("id").toString();
    		 }
    	}
    	String voucherNo = request.getParameter("documentNo");
    	String invoice_invoiceNum1 = request.getParameter("documentName");
    	 //计划类
        String sql =" select m.id as \"id\", m.document_No as \"documentNo\",m.document_Name as \"documentName\", t.JE as \"planAmount\",  m.document_Time as \"documentTime\"," + 
        		"  m.FUNDS_SUBJECT as \"fundsSubject\", m.ADUIT_STATUS as \"approvalStatus\", m.cw_status as \"cwStatus\","+ 
        		"  m.submit_username as \"createName\", m.submit_time as \"createDate\", m.audit_username as \"audit_username\", m.audit_time as \"audit_time\"" + 
        		"  from t_Pm_Project_plan m join t_pm_fpje t on t.JHWJID = m.id join t_pm_project p on t.XMID = p.id  where  t.XMID = '" + projectId + "'";
        if(StringUtils.isNotEmpty(voucherNo)) {
        	sql += " and m.document_No like '%"+voucherNo+"%' ";
        }
        if(StringUtils.isNotEmpty(invoice_invoiceNum1)) {
        	sql += " and m.document_Name like '%"+invoice_invoiceNum1+"%' ";
        }
        //计划类
        List list = this.systemService.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
        dataGrid.setResults(list);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 查询机关审核列表
     * 
     * @param tPmIncomeApply
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForDepartment")
    public void datagridForDepartment(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String type = request.getParameter("type");
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeApply,
                request.getParameterMap());
        try {
            cq.eq("checkUserId", user.getId());
            if ("1".equals(type)) {
                cq.eq("auditStatus", "1");//审核状态为1 已提交
            } else {
                cq.or(Restrictions.eq("auditStatus", "2"), Restrictions.eq("auditStatus", "3"));//审核通过或者审核不通过
            }
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmIncomePlanService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
    /**
     * 机构打印校内协作（计划类）单据页面
     * 
     * @param tPmIncomeApply
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForDepartmentWord")
    public void datagridForDepartmentWord(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {        
    	//TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TPmIncomePlanEntity.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomePlan,
                request.getParameterMap());
        try {
        	cq.isNotNull("parentPlanId");            
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmIncomePlanService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 计划下达新增和编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest req) {
    	//进入页面时判断是机关进入还是课题组，如果是机关则可以填写预留模块
//    	String type = req.getParameter("type");
//    	if("jg".equals(type)){
//    		req.setAttribute("ylFlag", "true");
//    	}    	
    	//判断是否显示经费分解，机关下达时不需显示，课题组填写经费分解和机关审核时要显示
    	String stage = req.getParameter("stage");
    	//下达步骤时
    	if("xd".equals(stage)){
    		req.setAttribute("jffjFlag", "false");
    	}else if("jffj".equals(stage)){
    		req.setAttribute("jffjFlag", "true");
    	}else if("yl".equals(stage)){
    		req.setAttribute("ylFlag", "true");
    	}
    	else if("ck".equals(stage)){
    		req.setAttribute("jffjFlag", "true");
    		req.setAttribute("ylFlag", "true");
    	}
        String projectId = req.getParameter("projectId");
        String hasSubprojectFlag = "false";
        if(StringUtils.isNotEmpty(projectId)){
        	TPmProjectEntity  entity = this.systemService.get(TPmProjectEntity.class, projectId);
        	req.setAttribute("planContractFlag", entity.getPlanContractFlag());
        	if (entity != null) {                
                if(entity.getParentPmProject() != null){                	
                	if(StringUtil.isEmpty(entity.getParentPmProject().getId())){
                    	req.setAttribute("schoolCooperationFlag", "0");
                    }else{
                    	req.setAttribute("schoolCooperationFlag", "1");
                    }
                }            
                req.setAttribute("planContractFlag", entity.getPlanContractFlag());
                List<TPmProjectEntity> subProjectList = this.systemService.findByProperty(TPmProjectEntity.class, "parentPmProject.id", entity.getId());
                if(subProjectList.size()>0){
                	hasSubprojectFlag = "true";
                	req.setAttribute("hasSubprojectFlag", "true");
                }
            }
        }        
        if (StringUtil.isNotEmpty(tPmIncomePlan.getId())) {
        	tPmIncomePlan = tBPmIncomePlanService.getEntity(TPmIncomePlanEntity.class, tPmIncomePlan.getId());
            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmIncomePlan.getProject().getId());
            if (project != null) {                
                if(project.getParentPmProject() != null){                	
                	if(StringUtil.isEmpty(project.getParentPmProject().getId())){
                    	req.setAttribute("schoolCooperationFlag", "0");
                    }else{
                    	req.setAttribute("schoolCooperationFlag", "1");
                    }
                }            
                req.setAttribute("planContractFlag", project.getPlanContractFlag());
//                tPmIncomePlan.setProjectId(project.getId());
                List<TPmProjectEntity> subProjectList = this.systemService.findByProperty(TPmProjectEntity.class, "parentPmProject.id", project.getId());
                if(subProjectList.size()>0){
                	hasSubprojectFlag = "true";
                	req.setAttribute("hasSubprojectFlag", "true");
                }
            }

        }
        req.setAttribute("tPmIncomePlanPage", tPmIncomePlan);
        return new ModelAndView("com/kingtake/project/incomeplan/tPmIncomePlanEdit");
    }

    /**
     * 保存计划下达信息
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "计划下达更新成功";        
        try {
        	//项目ID
        	String projectId = request.getParameter("projectId");
        	if(!StringUtil.isEmpty(projectId)){
        		TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, projectId);
        		tPmIncomePlan.setProject(projectEntity);
//        		tPmIncomePlan.getProject().setId(projectId);
        	}
        	//判断分配金额总数是否超过了总额
        	String projectPlanId = tPmIncomePlan.getProjectPlanId();
        	BigDecimal amountTotal = new BigDecimal(0);
        	if(!StringUtil.isEmpty(projectPlanId)){
        		TPmProjectPlanEntity projectPlanEntity = this.systemService.getEntity(TPmProjectPlanEntity.class, projectPlanId);
        		tPmIncomePlan.setDocumentNo(projectPlanEntity.getDocumentNo());
        		tPmIncomePlan.setDocumentName(projectPlanEntity.getDocumentName());
        		tPmIncomePlan.setDocumentTime(projectPlanEntity.getDocumentTime());
        		tPmIncomePlan.setPlanYear(projectPlanEntity.getPlanYear());
        		tPmIncomePlan.setFundsSubject(projectPlanEntity.getFundsSubject());
        		List<TPmIncomePlanEntity> incomePlanList= this.systemService.findByProperty(TPmIncomePlanEntity.class, "projectPlanId", projectPlanId);
        		for (TPmIncomePlanEntity tPmIncomePlanEntity : incomePlanList) {
        			amountTotal = amountTotal.add(tPmIncomePlanEntity.getPlanAmount());
				}
        		if(StringUtil.isEmpty(tPmIncomePlan.getId())){
        			amountTotal = amountTotal.add(tPmIncomePlan.getPlanAmount());
        		}        		
        		if(amountTotal.compareTo(projectPlanEntity.getAmount()) == 1){
        			j.setSuccess(false);
                    j.setMsg("保存计划下达失败，分配金额总数超过了计划下达总金额");
                    return j;
        		}
        	}
        	//计划下达信息：如果没有id则新增，有则保存
        	if(StringUtil.isEmpty(tPmIncomePlan.getId())){
        		//设为未审核状态
        		tPmIncomePlan.setApprovalStatus("0");
        		//设为未做预算状态
        		tPmIncomePlan.setYsStatus("0");
        		//经费来源设为计划下达
        		tPmIncomePlan.setFundsSources("1");
                this.tBPmIncomePlanService.save(tPmIncomePlan);
        	}else{
        		TPmIncomePlanEntity t = tBPmIncomePlanService.get(TPmIncomePlanEntity.class, tPmIncomePlan.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmIncomePlan, t);
                tBPmIncomePlanService.saveOrUpdate(t);
        	}
        	            
            String schoolCooperationListStr = request.getParameter("schoolCooperationListStr");
            List<TPmIncomePlanEntity> schoolCooperationList = new ArrayList<TPmIncomePlanEntity>();
            if (StringUtils.isNotEmpty(schoolCooperationListStr)) {
                JSONArray array = JSONArray.parseArray(schoolCooperationListStr);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject json = (JSONObject) array.get(i);
                    if(!json.getString("PLANAMOUNT").equals("") || json.getString("PLANAMOUNT") != null){
                    	BigDecimal planAmount = new BigDecimal(json.getString("PLANAMOUNT"));
                    	if(json.getString("INCOMEPLANID") != null){
                    		TPmIncomePlanEntity incomePlanEntity = this.systemService.getEntity(TPmIncomePlanEntity.class, json.getString("INCOMEPLANID"));
                    		incomePlanEntity.setPlanAmount(planAmount);
                    		incomePlanEntity.setPlanYear(tPmIncomePlan.getPlanYear());
                    		this.systemService.saveOrUpdate(incomePlanEntity);
                    	}else{
                    		TPmIncomePlanEntity incomePlanEntity = new TPmIncomePlanEntity(); 
                    		incomePlanEntity.setParentPlanId(tPmIncomePlan.getId());
                        	incomePlanEntity.setPlanAmount(planAmount);
                            TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, json.getString("PROJECTID"));
                            incomePlanEntity.setProject(projectEntity);
                            incomePlanEntity.setApprovalStatus("0");//审核状态初始化为0，未提交
                            //设为未做预算状态
                            incomePlanEntity.setYsStatus("0");
                            incomePlanEntity.setDocumentNo(tPmIncomePlan.getDocumentNo());
                            incomePlanEntity.setDocumentName(tPmIncomePlan.getDocumentName());
                            incomePlanEntity.setDocumentTime(tPmIncomePlan.getDocumentTime());
                            incomePlanEntity.setFundsSubject(tPmIncomePlan.getFundsSubject());
                            incomePlanEntity.setPlanYear(tPmIncomePlan.getPlanYear());
                            incomePlanEntity.setFundsSources("2");//校内协作
                            //生成二维码
                        	Long count = this.systemService.getCountForJdbc("select count(*) from T_PM_INCOME_APPLY where PARENT_APPLY_ID is not null ") + 1;
                        	count = count + this.systemService.getCountForJdbc("select count(*) from T_PM_INCOME_PLAN where PARENT_PLAN_ID is not null ");
                        	String barcodeNum = "0000000" + count;
                        	barcodeNum = barcodeNum.substring(barcodeNum.length() - 7, barcodeNum.length());
                        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                            Date date = new Date();
                            String currentYear = sdf.format(date);
                        	String barcode = "KX" + currentYear + barcodeNum;
                        	incomePlanEntity.setBarcode(barcode);
                            schoolCooperationList.add(incomePlanEntity);            
                    	}                    	            	                                            	                 
                    }                                         
                }
            }
            this.systemService.batchSave(schoolCooperationList);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("保存计划下达失败，原因：" + e.getMessage());
            return j;
        }
//        j.setObj(tPmIncomePlan);
        j.setMsg(message);
        return j;
    }

    /**
     * 删除计划下达信息
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "计划下达删除成功";
        try {
        	tBPmIncomePlanService.delete(tPmIncomePlan);
        } catch (Exception e) {
            e.printStackTrace();
            message = "计划下达删除失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 计划下达审核通过
     * 
     * @return
     */
    @RequestMapping(params = "doApproval")
    @ResponseBody
    public AjaxJson beforeAudit(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomePlan = systemService.getEntity(TPmIncomePlanEntity.class, tPmIncomePlan.getId());
        try {
            if (tPmIncomePlan.getAcademyAmount() == null || tPmIncomePlan.getUniversityAmount() == null || tPmIncomePlan.getDepartmentAmount() == null || tPmIncomePlan.getPerformanceAmount() == null) {
                message = "前先填写预留金额信息";
            } else if (tPmIncomePlan.getIndirectFunds() == null || tPmIncomePlan.getDirectFunds() == null) {
                message = "请先通知课题组填写经费分解信息";
            } else {
                tPmIncomePlan.setApprovalStatus("1");
                systemService.saveOrUpdate(tPmIncomePlan);
            	message = "审核通过";
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
        
    }

    /**
     * 计划下达申请审核
     * 
     * @return
     */
    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String opt = request.getParameter("opt");
        String userId = request.getParameter("userId");
        String realname = request.getParameter("realname");
        String deptId = request.getParameter("deptId");
        tPmIncomePlan = systemService.getEntity(TPmIncomePlanEntity.class, tPmIncomePlan.getId());
        try {
            if ("submit".equals(opt)) {
            	tPmIncomePlan.setCheckUserId(userId);
            	tPmIncomePlan.setCheckUserRealName(realname);
            	tPmIncomePlan.setCheckUserDeptId(deptId);
            	tPmIncomePlan.setSubmitTime(new Date());
                this.tBPmIncomePlanService.doSubmit(tPmIncomePlan);
                message = "计划下达提交成功";
            } else if ("pass".equals(opt)) {
            	tPmIncomePlan.setApprovalStatus("2");//auditStatus 为2 通过
            	tPmIncomePlan.setAuditTime(new Date());//审核时间
                this.tBPmIncomePlanService.saveOrUpdate(tPmIncomePlan);
                message = "计划下达审批通过";
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage()!=null){
                message = e.getMessage();
            }else{
                message = "计划下达审核失败";
            }
            j.setSuccess(false);
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
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/invoice/tPmIncomeApplyUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeApply,
                request.getParameterMap());
        List<TPmIncomeApplyEntity> tPmIncomeApplys = this.tBPmIncomePlanService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目发票信息");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeApplyEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目发票信息列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomeApplys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "项目来款申请信息");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmIncomeApplyEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

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
                List<TPmIncomeApplyEntity> listtPmIncomeApplyEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmIncomeApplyEntity.class, params);
                for (TPmIncomeApplyEntity tPmIncomeApply : listtPmIncomeApplyEntitys) {
                	tBPmIncomePlanService.save(tPmIncomeApply);
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

    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TPmIncomePlanEntity plan = this.systemService.get(TPmIncomePlanEntity.class,
                        id);
                req.setAttribute("plan", plan);
        }
        return new ModelAndView("com/kingtake/project/incomeplan/proposePage");
    }
    
    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            if (StringUtil.isNotEmpty(id)) {
                TPmIncomePlanEntity plan = systemService.get(TPmIncomePlanEntity.class, id);
                plan.setMsgText(msgText);
                plan.setApprovalStatus("3");//驳回
                plan.setAuditTime(new Date());
                this.tBPmIncomePlanService.doReject(plan);
            }
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "goFpje")
    public ModelAndView goFpje(HttpServletRequest req) {
        String id = req.getParameter("id");
        String projectPlanId = req.getParameter("projectPlanId");
        if (StringUtils.isNotEmpty(id)) {
        	String sql = "select * from t_pm_fpje where xmid='"+id+"' and jhwjid='"+projectPlanId+"'";
        	List<Map<String, Object>> list = systemService.findForJdbc(sql);
        	if(list.size()>0){
        		req.setAttribute("je", list.get(0).get("JE"));
        		req.setAttribute("id", list.get(0).get("ID"));
        		req.setAttribute("type", "1");
        	}
        }
        return new ModelAndView("com/kingtake/project/incomeplan/goFpje");
    }
    
    @RequestMapping(params = "doFpje")
    @ResponseBody
    public AjaxJson doFpje(TPmFpje fpje,HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
        	String type = req.getParameter("type");
        	if(type.equals("1")){
        		systemService.saveOrUpdate(fpje);
        	}else{
        		this.systemService.save(fpje);
        	}
        	this.tBPmIncomePlanService.sumMoney(fpje.getJhwjid(), fpje.getXmid());
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "addSelectLxProject")
    @ResponseBody
    public AjaxJson addSelectLxProject(TPmFpje fpje,HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
        	String type = req.getParameter("type");
        	fpje.setJe("0");
        	String hql = "select 1 from TPmFpje where jhwjid=? and xmid=?";
        	List list =this.systemService.findHql(hql, fpje.getJhwjid(),fpje.getXmid());
        	if(!list.isEmpty()){
        		j.setMsg(message);
                return j;
        	}
        	systemService.saveOrUpdate(fpje);
        	String sql = "update t_pm_project set jhid =? where id = ?";
        	this.systemService.executeSql(sql, fpje.getJhwjid(),fpje.getXmid());
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 获取意见
     * @param req
     * @return
     */
    @RequestMapping(params = "getPropose")
    @ResponseBody
    public JSONObject getPropose(HttpServletRequest req) {
        JSONObject json = new JSONObject();
        String id = req.getParameter("id");
        TPmIncomePlanEntity planEntity = this.systemService.get(TPmIncomePlanEntity.class, id);
        if (planEntity != null) {
            json.put("msg", planEntity.getMsgText());
        }
        return json;
    }
    
    /**
     * 新增校内协作经费
     * @param req
     * @return
     */
    @RequestMapping(params = "goSchoolCooperation")
    public ModelAndView goSchoolCooperation(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeSchoolCooperation-add");
    }
    
    /**
     * 提交审核前的信息检验，如果有必填信息未填则不能提交审核
     * 
     * @return
     */
    @RequestMapping(params = "beforeApproval")
    @ResponseBody
    public AjaxJson checkInfo(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomePlan = systemService.getEntity(TPmIncomePlanEntity.class, tPmIncomePlan.getId());
        try {
            if (tPmIncomePlan.getDirectFunds() == null) {
                message = "请先填写经费构成";
                j.setSuccess(false);
            } else if (tPmIncomePlan.getIndirectFunds() == null) {
            	message = "请先填写经费构成";
            	j.setSuccess(false);
            } 
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage()!=null){
                message = e.getMessage();
            }else{
                message = "计划下达申请审核失败";
            }
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
        
    }
    
    /**
     * 审核通过前的信息检验，如果有必填信息未填则不能提交审核
     * 
     * @return
     */
    @RequestMapping(params = "beforePass")
    @ResponseBody
    public AjaxJson beforePass(TPmIncomePlanEntity tPmIncomePlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomePlan = systemService.getEntity(TPmIncomePlanEntity.class, tPmIncomePlan.getId());
        try {
            if (tPmIncomePlan.getAcademyAmount() == null) {
                message = "请先填写预留金额";
                j.setSuccess(false);
            } else if (tPmIncomePlan.getDepartmentAmount() == null) {
            	message = "请先填写预留金额";
            	j.setSuccess(false);
            } else if (tPmIncomePlan.getPerformanceAmount() == null) {
            	message = "请先填写预留金额";
            	j.setSuccess(false);
            } else if (tPmIncomePlan.getUniversityAmount() == null) {
            	message = "请先填写预留金额";
            	j.setSuccess(false);
            } 
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage()!=null){
                message = e.getMessage();
            }else{
                message = "计划下达审核通过失败";
            }
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
        
    }
    
    /**
     * 获取子项目及其已分配金额
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "getSubprojectList")
    @ResponseBody
    public JSONArray getSubprojectList(HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        String planId = req.getParameter("planId");
        String sql = "";
        
        List<TPmIncomePlanEntity> incomePlanList = this.systemService.findByProperty(TPmIncomePlanEntity.class, "parentPlanId", planId);
        sql = "select pro.id AS projectId,pro.project_name AS projectName,'' AS incomePlanId,'' AS alPlanAmount from t_pm_project pro "
        		+ " where  pro.PARENT_PROJECT = '" + projectId + "' and pro.APPROVAL_STATUS = '1'";
        if(incomePlanList.size()>0){
        	sql = "select pro.id AS projectId,pro.project_name AS projectName,incomePlan.ID AS incomePlanId,incomePlan.Plan_Amount AS alPlanAmount from t_pm_project pro "
            		+ " left join t_pm_income_plan incomePlan on pro.id = incomePlan.t_p_id "
            		+ " where  pro.PARENT_PROJECT = '" + projectId + "' and pro.APPROVAL_STATUS = '1'";
        	sql = sql + " and incomePlan.parent_plan_id = '" + planId + "'";
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
	 * 导出word
	 * 
	 * @param request
	 * @param response
     * @throws java.io.IOException 
     * @throws TemplateException 
	 */
	@RequestMapping(params = "createXnxzWord")
	@ResponseBody
    public AjaxJson createXnxzWord(HttpServletRequest request,HttpServletResponse response) throws java.io.IOException, TemplateException {
	AjaxJson j = new AjaxJson();
		/** 初始化配置文件 **/
        Configuration configuration = new Configuration();
        /** 设置编码 **/
        configuration.setDefaultEncoding("utf-8");
        /** ftl文件是放在代码目录下的export\\template**/
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        String fileDirectory = classPath + "\\export\\template";
        /** 加载文件 **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** 加载模板 **/
        Template template = configuration.getTemplate("xnxzjfhbtzs.ftl");
        /** 读取需要打印的对象 **/
        String incomePlanId = request.getParameter("id");
        TPmIncomePlanEntity incomePlan = this.tBPmIncomePlanService.get(TPmIncomePlanEntity.class, incomePlanId);
        /** 准备数据 **/
        Map<String,Object> dataMap = new HashMap<>();

        /** 图片路径 **/
        String str = "";
        try  
	    {  
	      JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());  
	      //xx  
	      localJBarcode.setEncoder(Code39Encoder.getInstance());  
	      localJBarcode.setPainter(WideRatioCodedPainter.getInstance());  
	      localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());  
	      localJBarcode.setShowCheckDigit(false);
	      str = incomePlan.getBarcode();  
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(str);  
	      saveToPNG(localBufferedImage, "Code" + str + ".png");
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
        String imagePath = classPath + "exportWord\\Code" + str + ".png";
        /** 将图片转化为**/
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                in.close();
            }
        }
        /** 进行base64位编码 **/
        BASE64Encoder encoder = new BASE64Encoder();

        /** 图片数据**/
        dataMap.put("barcode",encoder.encode(data));
        /** 文字数据**/   
        TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, incomePlan.getProject().getId());
        dataMap.put("accountingCode", projectEntity.getAccountingCode() == null ? "" : projectEntity.getAccountingCode());
        dataMap.put("projectName", projectEntity.getProjectName() == null ? "" : projectEntity.getProjectName());
        dataMap.put("developerDepart", projectEntity.getDevDepart().getDepartname() == null ? "" : projectEntity.getDevDepart().getDepartname());
        dataMap.put("projectManager", projectEntity.getProjectManager() == null ? "" : projectEntity.getProjectManager());
        dataMap.put("applyAmount", incomePlan.getPlanAmount() == null ? "" : incomePlan.getPlanAmount());
        dataMap.put("createTime", incomePlan.getCreateDate() == null ? "" : incomePlan.getCreateDate());
        dataMap.put("universityAmount", incomePlan.getUniversityAmount() == null ? "" : incomePlan.getUniversityAmount());
        dataMap.put("academyAmount", incomePlan.getAcademyAmount() == null ? "" : incomePlan.getAcademyAmount());
        dataMap.put("departmentAmount", incomePlan.getDepartmentAmount() == null ? "" : incomePlan.getDepartmentAmount());
        dataMap.put("no", "1");
        dataMap.put("totalAmount",incomePlan.getPlanAmount() == null ? "" : incomePlan.getPlanAmount());
        
        /** 母项目信息**/   
        dataMap.put("pAccountingCode", projectEntity.getParentPmProject().getAccountingCode() == null ? "" : projectEntity.getParentPmProject().getAccountingCode());
        dataMap.put("pProjectName", projectEntity.getParentPmProject().getProjectName() == null ? "" : projectEntity.getParentPmProject().getProjectName());
        dataMap.put("pProjectManager", projectEntity.getParentPmProject().getProjectManager() == null ? "" : projectEntity.getParentPmProject().getProjectManager());
        dataMap.put("pDeveloperDepart", projectEntity.getParentPmProject().getDevDepart().getDepartname() == null ? "" : projectEntity.getParentPmProject().getDevDepart().getDepartname());
        Integer num = Integer.parseInt(incomePlan.getBarcode().substring(incomePlan.getBarcode().length() - 7, incomePlan.getBarcode().length()));
        dataMap.put("num", num.toString());

        /** 指定输出word文件的路径 **/
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String exportTime = dateFormat.format( now ); 
        String outFilePath = classPath + "\\exportWord\\xnxzJh" + exportTime + ".doc";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        try {
			template.process(dataMap,out);
			String FileName = "xnxzJh" + exportTime + ".doc";
			Map<String,Object> returnMap = new HashMap<>();
			returnMap.put("FileName", FileName);
			j.setAttributes(returnMap);
			j.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(out != null){
            out.close();
        }
        
        return j;
    }
    
    /**
     * 下载word
     * 
     * @return
     */
    @RequestMapping(params = "downloadWord")
    public void downloadWord(HttpServletRequest request,HttpServletResponse response) {
    	String fileName = request.getParameter("FileName");
        OutputStream out = null;
        InputStream templateIs = null;
        try {
        	String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");        	
        	
        	String path = classPath + "exportWord\\" + fileName;        	
            
            	templateIs = new FileInputStream(path);                
                fileName = POIPublicUtil
                        .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            out = response.getOutputStream();
            IOUtils.copy(templateIs, out);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("下载word失败", e);
        } finally {
            IOUtils.closeQuietly(templateIs);
            IOUtils.closeQuietly(out);
        }
    }  
    
    static void saveToPNG(BufferedImage paramBufferedImage, String paramString)  
	  {  
	    saveToFile(paramBufferedImage, paramString, "png");  
	  }  
	  
	  static void saveToGIF(BufferedImage paramBufferedImage, String paramString)  
	  {  
	    saveToFile(paramBufferedImage, paramString, "gif");  
	  }  
	  
	  static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2)  
	  {  
	    try  
	    {  
	      String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
	      String fileDirectory = classPath + "\\exportWord\\";
	      File file = new File(fileDirectory);
	      if (!file.exists()) {
	    	  file.mkdir();
	      }
	      FileOutputStream localFileOutputStream = new FileOutputStream(fileDirectory + paramString1);  
	      ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);  
	      localFileOutputStream.close();  
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
	  } 
}
