package com.kingtake.project.controller.incomeapply;

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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jsp.webpage.workflow.listener.listener_jsp;
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
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
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

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.abatepay.TPmPayfirstEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeContractNodeRelaEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeRelApplyEntity;
import com.kingtake.project.entity.invoice.TBPmInvoiceEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.entity.tpmincomeallot.TPmIncomeAllotEntity;
import com.kingtake.project.service.incomeapply.TPmIncomeApplyServiceI;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Title: Controller
 * @Description: ????????????
 * @author onlineGenerator
 * @date 2016-01-21 20:20:22
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmIncomeApplyController")
public class TPmIncomeApplyController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmIncomeApplyController.class);

    @Autowired
    private TPmIncomeApplyServiceI tBPmIncomeApplyService;
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
     * ??????????????????TAB????????????
     * 
     * @return
     */
    @RequestMapping(params = "tBPmIncomeApplyList")
    public ModelAndView tBPmIncomeApplyListTab(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            TPmProjectEntity projectEntity = systemService.get(TPmProjectEntity.class, projectId);
            /*String planContractFlag = projectEntity.getPlanContractFlag();*/
            String planContractFlag = projectEntity.getLxyj();
            if(planContractFlag.equals("3")){
            	planContractFlag="2";
            }
            String schoolCooperationFlag = "0";
            if(projectEntity.getParentPmProject() != null){                	
            	if(!StringUtil.isEmpty(projectEntity.getParentPmProject().getId())){
                	schoolCooperationFlag = "1";
                }
            }
            if (StringUtils.isNotEmpty(planContractFlag) || StringUtils.isNotEmpty(schoolCooperationFlag)) {
            	request.setAttribute("planContractFlag", planContractFlag);
            	request.setAttribute("schoolCooperationFlag", schoolCooperationFlag);
            	//?????????
            	if(planContractFlag.equals("1")){
            		request.setAttribute("stage", "jffj");
            		return new ModelAndView("com/kingtake/project/incomeplan/incomePlanListForDepartment");
            	}
                //????????????
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
     * ???????????? ?????????????????? ????????????
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
     * ???????????? ?????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "incomeApplyTab")
    public ModelAndView incomeApplyTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/incomeapply/incomeApply-tab");
    }

    /**
     * ???????????? ?????????????????? ????????????
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
     * ??????????????????????????????  ????????????
     * 
     * @return
     */
    @RequestMapping(params = "incomeApplyXnxzWord")
    public ModelAndView incomeApplyXnxzWord(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyXnxzListForDepartment");
    }
    
    /**
     * ??????????????????????????????  ????????????
     * 
     * @return
     */
    @RequestMapping(params = "incomeApplyWord")
    public ModelAndView incomeApplyWord(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyListForDepartment");
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeApply,
                request.getParameterMap());
        try {
        	String stage = request.getParameter("stage");
        	if ("ys".equals(stage)){
        		cq.eq("auditStatus", "2");
        		cq.notEq("ysStatus", "1");
        	}
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmIncomeApplyService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @param tPmIncomeApply
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridForDepartment")
    public void datagridForDepartment(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        
    	String lxyj = request.getParameter("lxyj");//????????????
    	String type = request.getParameter("auditStatus");//?????????
    	type = StringUtils.isEmpty(type) ? "<>'0'" : "in ('"+type.replace(",", "','")+"')";
    	
    	String voucherNo = request.getParameter("voucherNo");
    	String invoice_invoiceNum1 = request.getParameter("invoice.invoiceNum1");
    	
        TSUser user = ResourceUtil.getSessionUserName();//???
        
        
        
        /*CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeApply, request.getParameterMap());
        try {
            cq.eq("checkUserId", user.getId());
            if ("1".equals(type)) {
                cq.eq("auditStatus", "1");//???????????????1 ?????????
            } else {
                cq.or(Restrictions.eq("auditStatus", "2"), Restrictions.eq("auditStatus", "3"));//?????????????????????????????????
            }
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmIncomeApplyService.getDataGridReturn(cq, true);*/
        
        //?????????
        String sql1 =" select a.ID as id, a.VOUCHER_NO as voucherNo, b.invoice_num1 as invoice_invoiceNum1, a.apply_amount as applyAmount, a.INCOME_TIME as incomeTime," + 
        		"        a.audit_status as auditStatus,a.cw_status as cwStatus,  a.MSG_TEXT as msgText , '2' as lxyj,  a.MSG_TEXT as audit_Msg,"+ 
        		"		 a.create_name as submit_username, a.create_date as submit_time, a.check_user_realname as audit_username, a.audit_time as audit_time" + 
        		"        from t_pm_income_apply  a left join t_b_pm_invoice b on a.INVOICE_ID=b.ID" + 
        		"        where a.CHECK_USER_ID='"+user.getId()+"' and a.audit_status " + type;
        if(StringUtils.isNotEmpty(voucherNo)) {
        	sql1+= " and a.VOUCHER_NO like '%"+voucherNo+"%' ";
        }
        if(StringUtils.isNotEmpty(invoice_invoiceNum1)) {
        	sql1+= " and b.invoice_num1 like '%"+invoice_invoiceNum1+"%' ";
        }
        
        //?????????
        String sql2 =" select m.id as id,m.document_No as voucherNo,m.document_Name as invoice_invoiceNum1, t.applyAmount as applyAmount,  m.document_Time as incomeTime," + 
        		"     m.ADUIT_STATUS as auditStatus, m.cw_status as cwStatus, submit_Msg as msgText , '1' as lxyj, m.audit_Msg as audit_Msg,"+ 
        		"	m.submit_username as submit_username, m.submit_time as submit_time, m.audit_username as audit_username, m.audit_time as audit_time" + 
        		"  from (" + 
        		"        select a.JHWJID,sum(a.je) as applyAmount from t_pm_fpje a, t_Pm_Project_plan b where a.JHWJID=b.ID and aduit_status " + type + 
        		"            and a.XMID in( select id from t_pm_project p where p.LXYJ in ('1','3') and p.Scbz<>'1') group by jhwjid" + 
        		"  ) t inner join t_Pm_Project_plan m on t.JHWJID = m.id  where m.receive_UseIds like '%"+user.getId()+"%'" + 
        		" and m.ADUIT_STATUS "+ type;
        if(StringUtils.isNotEmpty(voucherNo)) {
        	sql2+= " and m.document_No like '%"+voucherNo+"%' ";
        }
        if(StringUtils.isNotEmpty(invoice_invoiceNum1)) {
        	sql2+= " and m.document_Name like '%"+invoice_invoiceNum1+"%' ";
        }
        
        String sql = "";
        if("1".equals(lxyj)) { //?????????
        	sql = sql2;
        }else if("2".equals(lxyj)) { //?????????
        	sql = sql1;
        }else { //??????
        	sql = sql1 + " union " +sql2;
        }
        sql = "select * from (" + sql + "  ) hh order by hh.incomeTime desc ";
        
        //?????????
        List list = this.tBPmIncomeApplyService.findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
        //List list = this.tBPmIncomeApplyService.findForJdbcParam(sql, page, rows, objs);
        
        
        Long total = this.tBPmIncomeApplyService.getCountForJdbc(getCountSql(sql));
        dataGrid.setTotal(total.intValue());
        dataGrid.setResults(list);
        
        TagUtil.datagrid(response, dataGrid);
    }
    
    private String getCountSql(String sql) {
    	return "select count(1) from ("+sql+") t";
    }
    
    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagridForWord")
    public void datagridForWord(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeApply,
                request.getParameterMap());
        try {
        	cq.isNull("parentApplyId");
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmIncomeApplyService.getDataGridReturn(cq, true);
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

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagridForXnxzWord")
    public void datagridForXnxzWord(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class, dataGrid);
        // ?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeApply,
                request.getParameterMap());
        try {
        	cq.isNotNull("parentApplyId");
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBPmIncomeApplyService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ?????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goEdit")
    public ModelAndView goEdit(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest req) {
    	
    	String lxyj = req.getParameter("lxyj");
    	String url = "com/kingtake/project/incomeapply/";
    	if("1".equals(lxyj)) { //?????????
    		url += "tPmIncomeApplyEdit3";
    		
    		//????????????????????????
    		String jhid = req.getParameter("id");
    		String sql = " select m.id as \"id\",m.document_No as \"voucherNo\",m.document_Name as \"invoice_invoiceNum1\", t.applyAmount as \"applyAmount\",  m.document_Time as \"incomeTime\",  " + 
    				"        		     m.ADUIT_STATUS as \"auditStatus\",  '' as \"msgText\", plan_year as \"planYear\" " + 
    				"        		  from (  " + 
    				"        		        select a.JHWJID, sum(a.je) as applyAmount from t_pm_fpje a, t_Pm_Project_plan b where a.JHWJID=b.ID and a.JHWJID = ?  " + 
    				"        		            and a.XMID in( select id from t_pm_project p where p.LXYJ in ('1','3') and p.Scbz<>'1') group by jhwjid  " + 
    				"        		  ) t inner join t_Pm_Project_plan m on t.JHWJID = m.id";
    		
    		List<Map<String,Object>> list = this.systemService.findForJdbc(sql, jhid);
    		Map<String,Object> jh = list.get(0);
    		req.setAttribute("jh", jh);
    		
    		//???????????????????????????????????????
    		/*sql = "";
    		List xm = this.systemService.findForJdbc(sql, jhid);
    		req.setAttribute("xm", xm);*/
    		
    	}else { //?????????
    		url += "tPmIncomeApplyEdit2";
	    	//????????????????????????????????????????????????????????????????????????????????????????????????
	    	String type = req.getParameter("type");
	    	if("jg".equals(type)){
	    		req.setAttribute("ylFlag", "true");
	    	}    	
	    	String certificatelist = req.getParameter("certificatelist");
	        String balances = req.getParameter("balances");
	        String projectId = req.getParameter("projectId");
	        String incomeId = req.getParameter("incomeId");
	        String hasSubprojectFlag = "false";
	        if(StringUtils.isNotEmpty(incomeId) && incomeId.indexOf(",") == -1) {//????????????
	            TPmIncomeEntity  entity = this.systemService.get(TPmIncomeEntity.class, incomeId);
	            tPmIncomeApply.setVoucherNo(entity.getCertificate());
	            tPmIncomeApply.setApplyYear(entity.getIncomeYear());
	            tPmIncomeApply.setIncomeId(entity.getId());
	            tPmIncomeApply.setIncomeAmount(entity.getBalance());
	        }
	        
	        if(StringUtils.isNotEmpty(certificatelist)) {
	        	tPmIncomeApply.setVoucherNo(certificatelist);
	        	tPmIncomeApply.setIncomeAmount(BigDecimal.valueOf(Double.parseDouble(balances)));
	        }
	        
	        if(StringUtils.isNotEmpty(incomeId)) {
	        	req.setAttribute("incomeIds", incomeId);
	        }
	        
	        if(StringUtils.isNotEmpty(projectId)){
	        	TPmProjectEntity  entity = this.systemService.get(TPmProjectEntity.class, projectId);
	        	if (entity != null) {                
	                if(entity.getParentPmProject() != null){                	
	                	if(StringUtil.isEmpty(entity.getParentPmProject().getId())){
	                    	req.setAttribute("schoolCooperationFlag", "0");
	                    }else{
	                    	req.setAttribute("schoolCooperationFlag", "1");
	                    }
	                }            
	                req.setAttribute("planContractFlag", entity.getLxyj());
	                tPmIncomeApply.setProject(entity);
	                List<TPmProjectEntity> subProjectList = this.systemService.findByProperty(TPmProjectEntity.class, "parentPmProject.id", entity.getId());
	                if(subProjectList.size()>0){
	                	hasSubprojectFlag = "true";
	                	req.setAttribute("hasSubprojectFlag", "true");
	                }
	            }
	        }        
	        if (StringUtil.isNotEmpty(tPmIncomeApply.getId())) {
	            tPmIncomeApply = tBPmIncomeApplyService.getEntity(TPmIncomeApplyEntity.class, tPmIncomeApply.getId());
	            TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tPmIncomeApply.getProject().getId());
	            if (project != null) {                
	                if(project.getParentPmProject() != null){                	
	                	if(StringUtil.isEmpty(project.getParentPmProject().getId())){
	                    	req.setAttribute("schoolCooperationFlag", "0");
	                    }else{
	                    	req.setAttribute("schoolCooperationFlag", "1");
	                    }
	                }            
	                req.setAttribute("planContractFlag", project.getLxyj());
	                tPmIncomeApply.setProject(project);
	                List<TPmProjectEntity> subProjectList = this.systemService.findByProperty(TPmProjectEntity.class, "parentPmProject.id", project.getId());
	                if(subProjectList.size()>0){
	                	hasSubprojectFlag = "true";
	                	req.setAttribute("hasSubprojectFlag", "true");
	                }
	            }
	
	        }
	        //??????
	        if(StringUtils.isEmpty(tPmIncomeApply.getAttachmentCode())){
	            tPmIncomeApply.setAttachmentCode(UUIDGenerator.generate());
	        }else{
	            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tPmIncomeApply.getAttachmentCode(), "");
	            tPmIncomeApply.setCertificates(certificates);
	        }
	        if(tPmIncomeApply.getIncomeTime()==null){
	           tPmIncomeApply.setIncomeTime(new Date());
	        }
	        TSUser user = ResourceUtil.getSessionUserName();
	        if(StringUtils.isEmpty(tPmIncomeApply.getApplyUser())) {
	        	tPmIncomeApply.setApplyUser(user.getRealName());
	        	tPmIncomeApply.setApplyDept(user.getCurrentDepart().getDepartname());
	        }
	        req.setAttribute("tPmIncomeApplyPage", tPmIncomeApply);
    	}
        return new ModelAndView(url);
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "incomeAllotList")
    public ModelAndView incomeAllotList(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            req.setAttribute("projectId", projectId);
        }
        String select = req.getParameter("select");
        if (StringUtils.isNotEmpty(select)) {
            req.setAttribute("select", select);
        }
        String loadType = req.getParameter("loadType");
        if (StringUtils.isNotEmpty(loadType)) {
            req.setAttribute("loadType", loadType);
        }
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeAllotList");
    }
    
    /**
     * ?????????????????????????????????===lijun
     * 
     * @return
     */
    @RequestMapping(params = "incomeAllotList2")
    public ModelAndView incomeAllotList2(HttpServletRequest req) {
        String voucherNo = req.getParameter("voucherNo");
        if (StringUtils.isNotEmpty(voucherNo)) {
            req.setAttribute("voucherNo", voucherNo);
        }
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeAllotList2");
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "incomeInfo")
    public ModelAndView incomeInfo(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/incomeapply/incomeInfo");
    }

    /**
     * ???????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goIncomeInfoUpdate")
    public ModelAndView goIncomeInfoUpdate(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncomeAllot.getId())) {
            tPmIncomeAllot = systemService.getEntity(TPmIncomeAllotEntity.class, tPmIncomeAllot.getId());
            req.setAttribute("tPmIncomeAllotPage", tPmIncomeAllot);
            TPmIncomeEntity income = tPmIncomeAllot.getIncome();
            income.setBalance(tPmIncomeAllot.getAmount().add(income.getBalance()));//???????????????????????????????????????+????????????????????????
            req.setAttribute("income", income);
        }
        return new ModelAndView("com/kingtake/project/incomeapply/incomeInfoUpdate");
    }

    /**
     * ????????????????????????????????????
     * 
     * @param tPmIncomeAllot
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "incomeAllotDatagrid")
    public void incomeAllotDatagrid(TPmIncomeAllotEntity tPmIncomeAllot, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeAllotEntity.class, dataGrid);
        if (StringUtils.isNotEmpty(tPmIncomeAllot.getProjectId())) {
            cq.eq("projectId", tPmIncomeAllot.getProjectId());
        }
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeAllot,
                request.getParameterMap());
        try {
            cq.addOrder("createDate", SortDirection.desc);
            //???????????????????????????
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????????????????
     * 
     * @param tPmIncomeAllot
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "getInvoiceList")
    @ResponseBody
    public List<ComboBox> getInvoiceList(TBPmInvoiceEntity tPmInvoice, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBPmInvoiceEntity.class);
        if (StringUtils.isNotEmpty(tPmInvoice.getProjectId())) {
            cq.eq("projectId", tPmInvoice.getProjectId());
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TBPmInvoiceEntity> invoiceList = this.systemService.getListByCriteriaQuery(cq, false);
        List<ComboBox> comboboxList = new ArrayList<ComboBox>();
        for (TBPmInvoiceEntity entity : invoiceList) {
            ComboBox combobox = new ComboBox();
            combobox.setId(entity.getId());
            combobox.setText(entity.getInvoiceNum1());
            comboboxList.add(combobox);
        }
        return comboboxList;
    }
    
    //==========lijun????????????????????????????????????????????????????????????
    public void doSave_T_PM_INCOME_APPLY_LIST(String incomeApplyId, String voucherNo, String amount) {
    	systemService.updateBySqlString("delete from T_PM_INCOME_APPLY_LIST where INCOME_APPLY_ID = '" + incomeApplyId + "'");
        String[] cerArray = voucherNo.split(",");
        String cerList = "'-1'";
        for(int i=0; i<cerArray.length; i++) {
        	cerList += ", '" + cerArray[i] + "'";
        }
        double applyAmount = Double.parseDouble(amount);
        List<Map<String, Object>> tempList = systemService.findForJdbc("select t.CERTIFICATE, t.BALANCE from t_pm_income t where t.CERTIFICATE in (" + cerList + ")");
        for(int i=0; i<tempList.size(); i++) {
        	double dAmount = 0, tAmount = 0;
        	Map<String, Object> tempMap = tempList.get(i);
        	String CERTIFICATE = (String)tempMap.get("CERTIFICATE");
        	BigDecimal BALANCE = (BigDecimal)tempMap.get("BALANCE");
        	if(applyAmount <= BALANCE.doubleValue()) {
        		dAmount = BALANCE.doubleValue() - applyAmount;
        		tAmount = applyAmount;
        		applyAmount = 0;
        	}else {
        		applyAmount = applyAmount - BALANCE.doubleValue();
        		tAmount = BALANCE.doubleValue();
        		dAmount = 0;
        	}
        	String sql = "update t_pm_income set BALANCE = " + dAmount + " where CERTIFICATE = '" + CERTIFICATE + "'";
        	String sql2 = "insert into T_PM_INCOME_APPLY_LIST values ('" + incomeApplyId + "', '" + CERTIFICATE + "', " + tAmount + ", sysdate)";
        	systemService.updateBySqlString(sql);
        	systemService.updateBySqlString(sql2);
        }
    }
    
    /**
     * ????????????????????????????????????????????????????????????
     * @param incomeId
     * @param incomeapplyId
     */
    public void saveIncomeRelIncomeApply(String incomeId, String incomeapplyId) {
    	String[] incomeIdArr = incomeId.split(",");
    	int len = incomeIdArr.length;
    	for (int i = 0; i < len; i++) {
    		TPmIncomeRelApplyEntity entity = new TPmIncomeRelApplyEntity();
    		entity.setIncomeApplyId(incomeapplyId);
    		entity.setIncomeId(incomeIdArr[i]);
    		systemService.save(entity);
		}
    }

    /**
     * ???????????????????????????????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request) {
    	String sMount2 = request.getParameter("sMount2");  //????????????
    	tPmIncomeApply.setApplyAmount(BigDecimal.valueOf(Double.parseDouble(sMount2)));
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        String nodeListStr = request.getParameter("nodeListStr");
        List<TPmIncomeContractNodeRelaEntity> nodeList = new ArrayList<TPmIncomeContractNodeRelaEntity>();
        if (StringUtils.isNotEmpty(nodeListStr)) {
            JSONArray array = JSONArray.parseArray(nodeListStr);
            for (int i = 0; i < array.size(); i++) {
                JSONObject json = (JSONObject) array.get(i);
                TPmIncomeContractNodeRelaEntity node = new TPmIncomeContractNodeRelaEntity();
                node.setContractNodeId(json.getString("contractNodeId"));
                node.setNodeName(json.getString("nodeName"));
                node.setNodeAmount(new BigDecimal(json.getString("nodeAmount")));
                node.setRemark(json.getString("remark"));
                nodeList.add(node);
            }
        }   
        //????????????
        BigDecimal payFunds = new BigDecimal(0);
        String gdSql = "select id,pay_funds,FUNDS_STATUS from t_b_pm_payfirst where project_id=? and CW_STATUS='1' and to_number(BPM_STATUS) >=3 and FUNDS_STATUS<>2";
        List<Map<String,Object>> gdList = this.tBPmIncomeApplyService.findForJdbc(gdSql,tPmIncomeApply.getProject().getId());
        for(int i = 0 ; i < gdList.size() ; i++){
        	BigDecimal bdFunds = (BigDecimal)gdList.get(i).get("PAY_FUNDS");
        	payFunds = payFunds.add(bdFunds);
        }
    	if(payFunds.compareTo(BigDecimal.ZERO) > 0 ){
    		tPmIncomeApply.setPayfirstFunds(payFunds);
    	}
        try {
            this.tBPmIncomeApplyService.saveIncomeApply(tPmIncomeApply, nodeList);   
            doSave_T_PM_INCOME_APPLY_LIST(tPmIncomeApply.getId(), tPmIncomeApply.getVoucherNo(), sMount2); //??????????????????????????????????????????????????????
            String incomeIds = request.getParameter("incomeIds");
            if(StringUtil.isNotEmpty(incomeIds)) {
            	this.saveIncomeRelIncomeApply(incomeIds, tPmIncomeApply.getId());
            }
            
            String schoolCooperationListStr = request.getParameter("schoolCooperationListStr");
            List<TPmIncomeApplyEntity> schoolCooperationList = new ArrayList<TPmIncomeApplyEntity>();
            if (StringUtils.isNotEmpty(schoolCooperationListStr)) {
                JSONArray array = JSONArray.parseArray(schoolCooperationListStr);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject json = (JSONObject) array.get(i);
                    if(!json.getString("APPLYAMOUNT").equals("") || json.getString("APPLYAMOUNT") != null){
                    	if(json.containsKey("APPLYID") && json.get("APPLYID") != null){
                    		if(!json.getString("APPLYID").equals("") || json.getString("APPLYID") != null){
                    			TPmIncomeApplyEntity incomeApplyEntity = this.systemService.getEntity(TPmIncomeApplyEntity.class, json.getString("APPLYID"));
                        		incomeApplyEntity.setApplyAmount(new BigDecimal(json.getString("APPLYAMOUNT")));
                        		this.systemService.saveOrUpdate(incomeApplyEntity);
                    		}                    		
                    	}else{
                    		TPmIncomeApplyEntity incomeApplyEntity = new TPmIncomeApplyEntity();
                    		incomeApplyEntity.setParentApplyId(tPmIncomeApply.getId());
                        	incomeApplyEntity.setVoucherNo(tPmIncomeApply.getVoucherNo());
                        	incomeApplyEntity.setApplyYear(tPmIncomeApply.getApplyYear());
                        	incomeApplyEntity.setApplyAmount(new BigDecimal(json.getString("APPLYAMOUNT")));
                        	incomeApplyEntity.setIncomeAmount(tPmIncomeApply.getIncomeAmount());
                        	incomeApplyEntity.setIncomeTime(tPmIncomeApply.getIncomeTime());
                        	TPmProjectEntity projectEntity = this.systemService.get(TPmProjectEntity.class, json.getString("PROJECTID"));
                        	incomeApplyEntity.setProject(projectEntity);
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
                        	schoolCooperationList.add(incomeApplyEntity);                        	                        
                    	}                    	
                    }                                         
                }
                this.systemService.batchSave(schoolCooperationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("????????????????????????????????????" + e.getMessage());
            return j;
        }
        j.setObj(tPmIncomeApply);
        j.setMsg(message);
        return j;
    }

    //????????????????????????????????????????????????????????????????????????
    public void doDel_T_PM_INCOME_APPLY_LIST(String IncomeApplyId) {
        List<Map<String, Object>> tempList = systemService.findForJdbc("select t.CERTIFICATE, t.BALANCE from T_PM_INCOME_APPLY_LIST t where t.INCOME_APPLY_ID = '" + IncomeApplyId + "'");
        if(tempList != null && tempList.size()>0) {
        	for(int i=0; i<tempList.size(); i++) {
        		Map<String, Object> tempMap = tempList.get(i);
        		String CERTIFICATE = (String)tempMap.get("CERTIFICATE");
            	BigDecimal AMOUNT = (BigDecimal)tempMap.get("BALANCE");
        		String sql = "update t_pm_income set BALANCE = BALANCE + " + AMOUNT.doubleValue() + " where CERTIFICATE = '" + CERTIFICATE + "'";
            	systemService.updateBySqlString(sql);
        	}
        }
        //?????????????????????????????????????????????????????????????????????
        String deletesql = "delete from t_pm_income_rel_apply where INCOME_APPLY_ID = '" + IncomeApplyId + "'";
        systemService.updateBySqlString(deletesql);
    }
    
    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "????????????????????????";
        try {
        	TPmIncomeApplyEntity incomeApplyEntity = this.systemService.getEntity(TPmIncomeApplyEntity.class, tPmIncomeApply.getId());
            tBPmIncomeApplyService.deleteApply(tPmIncomeApply);
            
            doDel_T_PM_INCOME_APPLY_LIST(tPmIncomeApply.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "????????????????????????";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "beforeAudit")
    @ResponseBody
    public AjaxJson beforeAudit(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomeApply = systemService.getEntity(TPmIncomeApplyEntity.class, tPmIncomeApply.getId());
        try {
            if (tPmIncomeApply.getAcademyAmount() == null || /*tPmIncomeApply.getUniversityAmount() == null ||*/ tPmIncomeApply.getDepartmentAmount() == null || tPmIncomeApply.getPerformanceAmount() == null) {
                message = "???????????????????????????????????????????????????";
            } else {
            	message = "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
        
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String opt = request.getParameter("opt");
        String userId = request.getParameter("userId");
        String realname = request.getParameter("realname");
        String deptId = request.getParameter("deptId");
        
        String lxyj = request.getParameter("lxyj");
        String message = "????????????";
        try {
	        if("1".equals(lxyj)) {//?????????
	        	TSUser user = ResourceUtil.getSessionUserName();
	        	String id = request.getParameter("id");
	            String msgText = request.getParameter("msgText");
	            
            	String sql = "update t_Pm_Project_plan  set "
	    				+ "audit_Time=sysdate , audit_userId=?, audit_userName=?, audit_Msg=?,"
	    				+ "aduit_status=2 where id= ?";
            	
	    		this.systemService.executeSql(sql, user.getId(), user.getRealName(), msgText, id);
	        	
	        }else {//?????????
		        tPmIncomeApply = systemService.getEntity(TPmIncomeApplyEntity.class, tPmIncomeApply.getId());
		        
	            if ("submit".equals(opt)) {
	                tPmIncomeApply.setCheckUserId(userId);
	                tPmIncomeApply.setCheckUserRealName(realname);
	                tPmIncomeApply.setCheckUserDeptId(deptId);
	                tPmIncomeApply.setSubmitTime(new Date());
	                this.tBPmIncomeApplyService.doSubmit(tPmIncomeApply);
	            } else if ("pass".equals(opt)) {
	                tPmIncomeApply.setAuditStatus("2");//auditStatus ???2 ??????
	                tPmIncomeApply.setAuditTime(new Date());//????????????
	                this.tBPmIncomeApplyService.doAudit(tPmIncomeApply);
	            }
	        }
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage()!=null){
                message = e.getMessage();
            }else{
                message = "????????????";
            }
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
        
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmIncomeApplyEntity tPmIncomeApply = systemService.getEntity(TPmIncomeApplyEntity.class, id);
                tBPmIncomeApplyService.deleteAddAttach(tPmIncomeApply);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/invoice/tPmIncomeApplyUpload");
    }

    /**
     * ??????excel
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
        List<TPmIncomeApplyEntity> tPmIncomeApplys = this.tBPmIncomeApplyService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeApplyEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomeApplys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "????????????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
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
            MultipartFile file = entity.getValue();// ????????????????????????
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TPmIncomeApplyEntity> listtPmIncomeApplyEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmIncomeApplyEntity.class, params);
                for (TPmIncomeApplyEntity tPmIncomeApply : listtPmIncomeApplyEntitys) {
                    tBPmIncomeApplyService.save(tPmIncomeApply);
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
     * ???????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "addInvoice")
    public ModelAndView addInvoice(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtil.isNotEmpty(tBPmInvoice.getId())) {
            tBPmInvoice = this.systemService.getEntity(TBPmInvoiceEntity.class, tBPmInvoice.getId());
        }
        if (StringUtils.isNotEmpty(projectId)) {
            tBPmInvoice.setProjectId(projectId);
        }
        //??????
        if(StringUtils.isEmpty(tBPmInvoice.getAttachmentCode())){
            tBPmInvoice.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBPmInvoice.getAttachmentCode(), "");
            tBPmInvoice.setCertificates(certificates);
        }
        req.setAttribute("tBPmInvoicePage", tBPmInvoice);
        return new ModelAndView("com/kingtake/project/incomeapply/invoiceAdd");
    }

    /**
     * ?????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "selectContractNode")
    public ModelAndView selectContractNode(TBPmInvoiceEntity tBPmInvoice, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/incomeapply/selectContractNode");
    }

    /**
     * ??????????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "selectContractNodeList")
    @ResponseBody
    public JSONObject selectContractNodeList(HttpServletRequest req) {
    	String cType = req.getParameter("cType");   
        JSONObject json = new JSONObject();
        String projectId = req.getParameter("projectId");
        String sql = "select t.id contractId, t.contract_name contractName,n.id contractNodeId,n.node_name nodeName,"
        		+ "n.pay_amount payAmount from t_pm_income_contract_appr t join t_pm_contract_node n on t.id = n.in_out_contractid "
        		+ "where t.t_p_id = ? and t.finish_flag= ? ";
        if("RL".equals(cType)) {  //lijun????????????????????????????????????????????????????????????????????????????????????????????????
        	sql += " and n.id not in (select x.CONTRACT_NODE_ID from T_PM_INCOME_CONTRACT_NODE_RELA x)";
        }
     
        List<Map<String, Object>> dataList = this.systemService.findForJdbc(sql, new Object[] { projectId,
                ApprovalConstant.APPRSTATUS_FINISH });
        json.put("total", dataList.size());
        json.put("rows", dataList);
        return json;
    }

    /**
     * ??????????????????????????????
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "getNodeList")
    @ResponseBody
    public JSONObject getNodeList(HttpServletRequest req) {
        JSONObject json = new JSONObject();
        String incomeApplyId = req.getParameter("incomeApplyId");
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeContractNodeRelaEntity.class);
        cq.eq("incomeApplyId", incomeApplyId);
        cq.add();
        List<TPmIncomeContractNodeRelaEntity> incomeNodeList = this.systemService.getListByCriteriaQuery(cq, false);
        for (TPmIncomeContractNodeRelaEntity rela : incomeNodeList) {
            TPmContractNodeEntity node = this.systemService.get(TPmContractNodeEntity.class,
                    rela.getContractNodeId());
            rela.setContractId(node.getInOutContractid());
        }
        json.put("total", incomeNodeList.size());
        json.put("rows", incomeNodeList);
        return json;
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "getInvoice")
    @ResponseBody
    public JSONObject getInvoice(HttpServletRequest req) {
        JSONObject json = new JSONObject();
        String invoiceId = req.getParameter("invoiceId");
        TBPmInvoiceEntity invoice = this.systemService.get(TBPmInvoiceEntity.class, invoiceId);
        if (invoice != null) {
            json = (JSONObject) JSONObject.toJSON(invoice);
        }
        return json;
    }

    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TPmIncomeApplyEntity apply = this.systemService.get(TPmIncomeApplyEntity.class,
                        id);
                req.setAttribute("apply", apply);
        }
        return new ModelAndView("com/kingtake/project/appraisal/proposePage");
    }

    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        
        message = "???????????????";
        try {
        	TSUser user =  ResourceUtil.getSessionUserName();
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            String lxyj = req.getParameter("lxyj");
            
            if("1".equals(lxyj)) {
            	String sql = "update t_Pm_Project_plan  set "
	    				+ "audit_Time=sysdate , audit_userId=? , audit_userName=?, audit_Msg=?,"
	    				+ "aduit_status=3 where id= ?";
            	
	    		this.systemService.executeSql(sql, user.getId(), user.getRealName(), msgText, id);
            }else {
	            if (StringUtil.isNotEmpty(id)) {
	                TPmIncomeApplyEntity apply = systemService.get(TPmIncomeApplyEntity.class, id);
	                apply.setMsgText(msgText);
	                apply.setAuditStatus("3");//??????
	                apply.setAuditTime(new Date());
	                this.tBPmIncomeApplyService.doReject(apply);
	            }
                
                doDel_T_PM_INCOME_APPLY_LIST(id);   //lijun?????????????????????????????????????????????????????????
            }
        } catch (Exception e) {
            message = "???????????????";
            e.printStackTrace();
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * ????????????
     * @param req
     * @return
     */
    @RequestMapping(params = "getPropose")
    @ResponseBody
    public JSONObject getPropose(HttpServletRequest req) {
        JSONObject json = new JSONObject();
        String id = req.getParameter("id");
        TPmIncomeApplyEntity incomeEntity = this.systemService.get(TPmIncomeApplyEntity.class, id);
        if (incomeEntity != null) {
            json.put("msg", incomeEntity.getMsgText());
        }
        return json;
    }
    
    /**
     * ????????????????????????
     * @param req
     * @return
     */
    @RequestMapping(params = "goSchoolCooperation")
    public ModelAndView goSchoolCooperation(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeSchoolCooperation-add");
    }
    
    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "checkInfo")
    @ResponseBody
    public AjaxJson checkInfo(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomeApply = systemService.getEntity(TPmIncomeApplyEntity.class, tPmIncomeApply.getId());
        try {
        	/*
            if (tPmIncomeApply.getDirectFunds() == null) {
                message = "?????????????????????????????????????????????";
                j.setSuccess(false);
            } else if (tPmIncomeApply.getIndirectFunds() == null) {
            	message = "?????????????????????????????????????????????";
            	j.setSuccess(false);
            } else if (tPmIncomeApply.getPayfirstFunds() == null) {
            	message = "?????????????????????????????????????????????";
            	j.setSuccess(false);
            }
            */
        	j.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage()!=null){
                message = e.getMessage();
            }else{
                message = "????????????????????????";
            }
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
        
    }
    
    /**
     * ??????????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "checkPayfirst")
    @ResponseBody
    public AjaxJson checkPayfirst(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String payfirstId = request.getParameter("payfirstId");
        String payfirstFunds = request.getParameter("payfirstFunds");
        TPmPayfirstEntity payfirstEntity = systemService.getEntity(TPmPayfirstEntity.class, payfirstId);
        try {
        	BigDecimal payfirstBigDecimal = new BigDecimal(payfirstFunds);
        	int compareResult = payfirstBigDecimal.compareTo(payfirstEntity.getPayFunds());        	
        	if(compareResult > 0){
        		message = "??????????????????????????????????????????????????????";
        	}else{
        		message = "OK";
        	}        	
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage()!=null){
                message = e.getMessage();
            }
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
        
    }
    
    /**
	 * ??????word
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
		/** ????????????????????? **/
        Configuration configuration = new Configuration();
        /** ???????????? **/
        configuration.setDefaultEncoding("utf-8");
        /** ftl?????????????????????????????????export\\template**/
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        String fileDirectory = classPath + "\\export\\template";
        /** ???????????? **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** ???????????? **/
        Template template = configuration.getTemplate("xnxzjfhbtzs.ftl");
        /** ??????????????????????????? **/
        String incomeApplyId = request.getParameter("id");
        TPmIncomeApplyEntity incomeApply = this.tBPmIncomeApplyService.get(TPmIncomeApplyEntity.class, incomeApplyId);
        /** ???????????? **/
        Map<String,Object> dataMap = new HashMap<>();

        /** ???????????? **/
        String str = "";
        try  
	    {  
	      JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());  
	      //xx  
	      localJBarcode.setEncoder(Code39Encoder.getInstance());  
	      localJBarcode.setPainter(WideRatioCodedPainter.getInstance());  
	      localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());  
	      localJBarcode.setShowCheckDigit(false);
	      str = incomeApply.getBarcode();  
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(str);  
	      saveToPNG(localBufferedImage, "Code" + str + ".png");
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
        String imagePath = classPath + "exportWord\\Code" + str + ".png";
        /** ??????????????????**/
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
        /** ??????base64????????? **/
        BASE64Encoder encoder = new BASE64Encoder();

        /** ????????????**/
        dataMap.put("barcode",encoder.encode(data));
        /** ????????????**/   
        TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, incomeApply.getProject().getId());
        dataMap.put("accountingCode", projectEntity.getAccountingCode() == null ? "" : projectEntity.getAccountingCode());
        dataMap.put("projectName", projectEntity.getProjectName() == null ? "" : projectEntity.getProjectName());
        dataMap.put("developerDepart", projectEntity.getDevDepart().getDepartname() == null ? "" : projectEntity.getDevDepart().getDepartname());
        dataMap.put("projectManager", projectEntity.getProjectManager() == null ? "" : projectEntity.getProjectManager());
        dataMap.put("applyAmount", incomeApply.getApplyAmount() == null ? "" : incomeApply.getApplyAmount());
        dataMap.put("createTime", incomeApply.getCreateDate() == null ? "" : incomeApply.getCreateDate());
        dataMap.put("universityAmount", incomeApply.getUniversityAmount() == null ? "" : incomeApply.getUniversityAmount());
        dataMap.put("academyAmount", incomeApply.getAcademyAmount() == null ? "" : incomeApply.getAcademyAmount());
        dataMap.put("departmentAmount", incomeApply.getDepartmentAmount() == null ? "" : incomeApply.getDepartmentAmount());
        dataMap.put("no", "1");
        dataMap.put("totalAmount",incomeApply.getApplyAmount() == null ? "" : incomeApply.getApplyAmount());
        
        /** ???????????????**/   
        dataMap.put("pAccountingCode", projectEntity.getParentPmProject().getAccountingCode() == null ? "" : projectEntity.getParentPmProject().getAccountingCode());
        dataMap.put("pProjectName", projectEntity.getParentPmProject().getProjectName() == null ? "" : projectEntity.getParentPmProject().getProjectName());
        dataMap.put("pProjectManager", projectEntity.getParentPmProject().getProjectManager() == null ? "" : projectEntity.getParentPmProject().getProjectManager());
        dataMap.put("pDeveloperDepart", projectEntity.getParentPmProject().getDevDepart().getDepartname() == null ? "" : projectEntity.getParentPmProject().getDevDepart().getDepartname());
        Integer num = Integer.parseInt(incomeApply.getBarcode().substring(incomeApply.getBarcode().length() - 7, incomeApply.getBarcode().length()));
        dataMap.put("num", num.toString());

        /** ????????????word??????????????? **/
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//?????????????????????????????????
        String exportTime = dateFormat.format( now ); 
        String outFilePath = classPath + "\\exportWord\\xnxzHt" + exportTime + ".doc";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        try {
			template.process(dataMap,out);
			String FileName = "xnxzHt" + exportTime + ".doc";
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
	 * ??????word
	 * 
	 * @param request
	 * @param response
     * @throws java.io.IOException 
     * @throws TemplateException 
	 */
	@RequestMapping(params = "createWord")
	@ResponseBody
    public AjaxJson createWord(HttpServletRequest request,HttpServletResponse response) throws java.io.IOException, TemplateException {
	AjaxJson j = new AjaxJson();
		/** ????????????????????? **/
        Configuration configuration = new Configuration();
        /** ???????????? **/
        configuration.setDefaultEncoding("utf-8");
        /** ftl?????????????????????????????????export\\template**/
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        String fileDirectory = classPath + "\\export\\template";
        /** ???????????? **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** ???????????? **/
        Template template = configuration.getTemplate("dkfptzs.ftl");
        /** ??????????????????????????? **/
        String incomeApplyId = request.getParameter("id");
        TPmIncomeApplyEntity incomeApply = this.tBPmIncomeApplyService.get(TPmIncomeApplyEntity.class, incomeApplyId);
        /** ???????????? **/
        Map<String,Object> dataMap = new HashMap<>();

        /** ???????????? **/
        String str = "";
        try  
	    {  
	      JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());  
	      //xx  
	      localJBarcode.setEncoder(Code39Encoder.getInstance());  
	      localJBarcode.setPainter(WideRatioCodedPainter.getInstance());  
	      localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());  
	      localJBarcode.setShowCheckDigit(false);
	      str = incomeApply.getBarcode();  
	      BufferedImage localBufferedImage = localJBarcode.createBarcode(str);  
	      saveToPNG(localBufferedImage, "Code" + str + ".png");
	    }  
	    catch (Exception localException)  
	    {  
	      localException.printStackTrace();  
	    }  
        String imagePath = classPath + "exportWord\\Code" + str + ".png";
        /** ??????????????????**/
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
        /** ??????base64????????? **/
        BASE64Encoder encoder = new BASE64Encoder();

        /** ????????????**/
        dataMap.put("barcode",encoder.encode(data));
        /** ????????????**/   
        TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, incomeApply.getProject().getId());
        dataMap.put("accountingCode", projectEntity.getAccountingCode() == null ? "" : projectEntity.getAccountingCode());
        dataMap.put("projectName", projectEntity.getProjectName() == null ? "" : projectEntity.getProjectName());
        dataMap.put("developerDepart", projectEntity.getDevDepart().getDepartname() == null ? "" : projectEntity.getDevDepart().getDepartname());
        dataMap.put("projectManager", projectEntity.getProjectManager() == null ? "" : projectEntity.getProjectManager());
        dataMap.put("projectNo", projectEntity.getProjectNo() == null ? "" : projectEntity.getProjectNo());
        Integer num = Integer.parseInt(incomeApply.getBarcode().substring(incomeApply.getBarcode().length() - 7, incomeApply.getBarcode().length()));
        dataMap.put("num", num.toString());
        
        /** ????????????**/   
        dataMap.put("applyYear", incomeApply.getApplyYear() == null ? "" : incomeApply.getApplyYear());
        dataMap.put("createTime", incomeApply.getCreateDate() == null ? "" : incomeApply.getCreateDate());
        dataMap.put("voucherNo", incomeApply.getVoucherNo() == null ? "" : incomeApply.getVoucherNo());
        dataMap.put("incomeRemark", incomeApply.getIncomeRemark() == null ? "" : incomeApply.getIncomeRemark());
        dataMap.put("incomeAmount", incomeApply.getApplyAmount() == null ? "" : incomeApply.getApplyAmount());
        dataMap.put("totalAmount",incomeApply.getApplyAmount() == null ? "" : incomeApply.getApplyAmount());
        
        /** ????????????**/   
        TBPmInvoiceEntity invoiceEntity = this.systemService.get(TBPmInvoiceEntity.class, incomeApply.getInvoice().getId());
        if(invoiceEntity != null){
        	dataMap.put("invoiceYear", invoiceEntity.getInvoiceYear() == null ? "" : invoiceEntity.getInvoiceYear());
            dataMap.put("invoiceNum1", invoiceEntity.getInvoiceNum1() == null ? "" : invoiceEntity.getInvoiceNum1());
            dataMap.put("memo", invoiceEntity.getMemo() == null ? "" : invoiceEntity.getMemo());
            dataMap.put("invoiceAmount", invoiceEntity.getInvoiceAmount() == null ? "" : invoiceEntity.getInvoiceAmount());
            dataMap.put("invoiceTotal", invoiceEntity.getInvoiceAmount() == null ? "" : invoiceEntity.getInvoiceAmount());
        }                              

        /** ????????????word??????????????? **/
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//?????????????????????????????????
        String exportTime = dateFormat.format( now ); 
        String outFilePath = classPath + "\\exportWord\\dkrk" + exportTime + ".doc";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        try {
			template.process(dataMap,out);
			String FileName = "dkrk" + exportTime + ".doc";
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
     * ??????word
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
            throw new BusinessException("??????word??????", e);
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

    public static String getBarCodePic(String barcode){
        String classPath = ContextHolderUtils.getRequest().getSession().getServletContext().getRealPath("/");
        /** ???????????? **/
        String str = barcode;
        try
        {
            JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
            //xx
            localJBarcode.setEncoder(Code39Encoder.getInstance());
            localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
            localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
            localJBarcode.setShowCheckDigit(false);
            BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
            TPmIncomeApplyController.saveToPNG(localBufferedImage, "Code" + str + ".png");

            String imagePath = classPath + "exportWord\\Code" + str + ".png";
            /** ??????????????????**/
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
            /** ??????base64????????? **/
            BASE64Encoder encoder = new BASE64Encoder();

            /** ????????????**/
            return encoder.encode(data);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }

        return null;
    }

	  
	  public static void main(String[] args) {
		  BigDecimal a = new BigDecimal(100);
		  BigDecimal b = new BigDecimal(200);
		  double d = 100000000;
		  System.out.println(a.doubleValue());
	}
}
