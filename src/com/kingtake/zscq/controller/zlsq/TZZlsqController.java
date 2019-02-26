package com.kingtake.zscq.controller.zlsq;
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

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.constant.ZlConstants;
import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.zscq.entity.sqwj.TZSqwjEntity;
import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;
import com.kingtake.zscq.service.zlsq.TZZlsqServiceI;



/**   
 * @Title: Controller
 * @Description: T_Z_ZLSQ
 * @author onlineGenerator
 * @date 2016-07-04 11:39:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tZZlsqController")
public class TZZlsqController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TZZlsqController.class);

	@Autowired
	private TZZlsqServiceI tZZlsqService;
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
     * 发明人专利申请界面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tZZlsq")
    public ModelAndView tZZlsqForFmr(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/zscq/zlsq/tZZlsqList");
	}
	
    /**
     * 机关专利申请界面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tZZlsqForDepart")
    public ModelAndView tZZlsqForDepart(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/zscq/zlsq/tZZlsqListForDepart");
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
	public void datagrid(TZZlsqEntity tZZlsq,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        TSUser user = ResourceUtil.getSessionUserName();
        String role = request.getParameter("role");
		CriteriaQuery cq = new CriteriaQuery(TZZlsqEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZZlsq, request.getParameterMap());
        if ("fmr".equals(role)) {
            cq.eq("createBy", user.getUserName());
        } else {
            cq.notEq("apprStatus", ApprovalConstant.APPRSTATUS_UNSEND);
        }
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tZZlsqService.getDataGridReturn(cq, true);
        List<TZZlsqEntity> results = dataGrid.getResults();
        for (TZZlsqEntity tmp : results) {
            TZSqwjEntity sqwj = this.systemService.findUniqueByProperty(TZSqwjEntity.class, "zlsqId", tmp.getId());
            if (sqwj != null) {
                tmp.setWjzt(sqwj.getApplyStatus());
            } else {
                tmp.setWjzt("0");
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除T_Z_ZLSQ
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TZZlsqEntity tZZlsq, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "专利申请删除成功";
		try{
            tZZlsqService.delZlsq(tZZlsq);
		}catch(Exception e){
			e.printStackTrace();
            message = "专利申请删除失败";
            j.setSuccess(false);
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除T_Z_ZLSQ
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "T_Z_ZLSQ删除成功";
		try{
			for(String id:ids.split(",")){
				TZZlsqEntity tZZlsq = systemService.getEntity(TZZlsqEntity.class, 
				id
				);
				tZZlsqService.delete(tZZlsq);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "T_Z_ZLSQ删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加T_Z_ZLSQ
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TZZlsqEntity tZZlsq, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "T_Z_ZLSQ添加成功";
		try{
			tZZlsqService.save(tZZlsq);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "T_Z_ZLSQ添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新T_Z_ZLSQ
	 * 
	 * @param ids
	 * @return
	 */
    @RequestMapping(params = "doUpdate", method = RequestMethod.POST)
	@ResponseBody
    public AjaxJson doUpdate(TZZlsqEntity tZZlsq, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "专利申请更新成功";
        try {
            if (StringUtils.isEmpty(tZZlsq.getId())) {
                tZZlsq.setApprStatus(ApprovalConstant.APPRSTATUS_UNSEND);//审批状态为未发送
                tZZlsq.setZlzt(ZlConstants.ZLZT_SQ);//专利状态为申请
                this.tZZlsqService.save(tZZlsq);
            } else {
                TZZlsqEntity t = tZZlsqService.get(TZZlsqEntity.class, tZZlsq.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tZZlsq, t);
                tZZlsqService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "专利申请更新失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
	

	/**
	 * T_Z_ZLSQ新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TZZlsqEntity tZZlsq, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tZZlsq.getId())) {
			tZZlsq = tZZlsqService.getEntity(TZZlsqEntity.class, tZZlsq.getId());
			req.setAttribute("tZZlsqPage", tZZlsq);
		}
        return new ModelAndView("com/kingtake/zscq/zlsq/tZZlsq-add");
	}
	/**
	 * T_Z_ZLSQ编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TZZlsqEntity tZZlsq, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZZlsq.getId())) {
            tZZlsq = tZZlsqService.getEntity(TZZlsqEntity.class, tZZlsq.getId());
        }
        if (StringUtils.isEmpty(tZZlsq.getFjbm())) {
            tZZlsq.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tZZlsq.getFjbm(), "");
            tZZlsq.setAttachments(fileList);
        }
        req.setAttribute("tZZlsqPage", tZZlsq);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/zlsq/tZZlsq-update");
    }
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/zscq/zlsq/tZZlsqUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TZZlsqEntity tZZlsq,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TZZlsqEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tZZlsq, request.getParameterMap());
		List<TZZlsqEntity> tZZlsqs = this.tZZlsqService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"T_Z_ZLSQ");
		modelMap.put(NormalExcelConstants.CLASS,TZZlsqEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("T_Z_ZLSQ列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tZZlsqs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TZZlsqEntity tZZlsq,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "T_Z_ZLSQ");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TZZlsqEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
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
				List<TZZlsqEntity> listTZZlsqEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),TZZlsqEntity.class,params);
				for (TZZlsqEntity tZZlsq : listTZZlsqEntitys) {
					tZZlsqService.save(tZZlsq);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
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
     * 审批tab页
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goApprReceiveTab")
    public ModelAndView goApprReceiveTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/zscq/zlsq/zlsqApprList-receiveTab");
    }

    /**
     * 任务书审核列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAuditDepartment")
    public ModelAndView goAuditDepartment(HttpServletRequest request) {
        //处理审批
        //0：未处理；1：已处理
        String type = request.getParameter("type");
        if (StringUtil.isNotEmpty(type)) {
            if ("0".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.NO);
            } else if ("1".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.YES);
            }
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_RECEIVE);
            request.setAttribute("YES", SrmipConstants.YES);
            request.setAttribute("NO", SrmipConstants.NO);
            return new ModelAndView("com/kingtake/zscq/zlsq/zlsqApprList-receive");
        }
        return new ModelAndView("common/404.jsp");

    }

    /**
     * easyui AJAX请求数据 审核列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "auditList")
    public void auditList(TOTravelLeaveEntity travelLeaveEntity, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批

            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();//获取参数

            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            StringBuffer resultSql = new StringBuffer();
            resultSql
                    .append("SELECT APPR.ID AS APPR_ID, APPR.gdh , APPR.wcdw ,APPR.lx ,APPR.mc ,APPR.fmr , APPR.lxr, APPR.lxrdh , APPR.Appr_Status auditStatus, ");
            resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, ");
            resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, ");
            resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT ");

            String temp = "FROM t_z_zlsq APPR,T_PM_APPR_SEND_LOGS SEND, "
                    + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT "
                    + "WHERE APPR.ID = SEND.APPR_ID  and SEND.ID = RECEIVE.SEND_ID "
                    + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID " + "AND RECEIVE.RECEIVE_USERID = ? ";

            if (SrmipConstants.YES.equals(operateStatus)) {
                //已处理
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                //未处理：需要是有效的
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                        + SrmipConstants.YES;
            }

            String mc = request.getParameter("mc");
            if (StringUtil.isNotEmpty(mc)) {
                temp += " AND APPR.mc LIKE '%" + mc + "%'";
            }
            String fmr = request.getParameter("fmr");
            if (StringUtil.isNotEmpty(fmr)) {
                temp += " AND APPR.fmr LIKE '%" + fmr + "%'";
            }

            temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
            String[] params = new String[] { user.getId() };

            List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                    dataGrid.getPage(), dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 跳转到机关管理界面
     * 
     * @return
     */
    @RequestMapping(params = "goDepartMain")
    public ModelAndView goDepartMain(TZZlsqEntity tZZlsq, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZZlsq.getId())) {
            tZZlsq = tZZlsqService.getEntity(TZZlsqEntity.class, tZZlsq.getId());
        }
        if (StringUtils.isEmpty(tZZlsq.getFjbm())) {
            tZZlsq.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tZZlsq.getFjbm(), "");
            tZZlsq.setAttachments(fileList);
        }
        req.setAttribute("tZZlsqPage", tZZlsq);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/zlsq/tZZlsq-depart-main");
    }

    /**
     * 跳转到发明人管理界面
     * 
     * @return
     */
    @RequestMapping(params = "goFmrMain")
    public ModelAndView goFmrMain(TZZlsqEntity tZZlsq, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tZZlsq.getId())) {
            tZZlsq = tZZlsqService.getEntity(TZZlsqEntity.class, tZZlsq.getId());
        }
        if (StringUtils.isEmpty(tZZlsq.getFjbm())) {
            tZZlsq.setFjbm(UUIDGenerator.generate());//自动生成编码
        } else {
            List<TSFilesEntity> fileList = this.systemService.getAttachmentByCode(tZZlsq.getFjbm(), "");
            tZZlsq.setAttachments(fileList);
        }
        req.setAttribute("tZZlsqPage", tZZlsq);
        String opt = req.getParameter("opt");
        if (StringUtils.isNotEmpty(opt)) {
            req.setAttribute("opt", "opt");
        }
        return new ModelAndView("com/kingtake/zscq/zlsq/tZZlsq-fmr-main");
    }

}
