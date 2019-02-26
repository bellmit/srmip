package com.kingtake.office.controller.travel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.office.entity.travel.TOTravelLeavedetailEntity;
import com.kingtake.office.page.travel.TOTravelLeavePage;
import com.kingtake.office.service.travel.TOTravelLeaveServiceI;

import oracle.sql.DATE;


/**   
 * @Title: Controller
 * @Description: 差旅-人员请假信息表
 * @author onlineGenerator
 * @date 2015-07-07 11:24:20
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tOTravelLeaveController")
public class TOTravelLeaveController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOTravelLeaveController.class);

	@Autowired
	private TOTravelLeaveServiceI tOTravelLeaveService;
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
	 * 差旅-人员请假信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tOTravelLeave")
	public ModelAndView tOTravelLeave(HttpServletRequest request) {
		return new ModelAndView("com/kingtake/office/travel/tOTravelLeaveList");
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
	public void datagrid(TOTravelLeaveEntity tOTravelLeave,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		TSUser user = ResourceUtil.getSessionUserName();
//		CriteriaQuery cq = new CriteriaQuery(TOTravelLeaveEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOTravelLeave, request.getParameterMap());
//        cq.or(Restrictions.eq("leaveId", user.getId()), Restrictions.eq("createBy", user.getUserName()));//创建人或者请假人                
//		cq.add();
//		
//		this.tOTravelLeaveService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		
		TSUser user = ResourceUtil.getSessionUserName();
		StringBuffer countSql = new StringBuffer("SELECT COUNT(0) ");
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT a.ID,a.PROJECT_ID,c.PROJECT_NAME as projectName,a.LEAVE_ID,a.LEAVE_NAME as leaveName,a.DEPART_ID,a.DEPART_NAME as departName,  \n");
        resultSql.append("a.LEAVE_TIME as leaveTime,a.DUTY,a.UNIT_OPINION,a.OPINION_USERID,a.OPINION_USERNAME,a.OPINION_DATE as opinionDate,  \n");
        resultSql.append("a.DEPART_INSTRUC,a.INSTRUC_USERID,a.INSTRUC_USERNAME,a.INSTRUC_DATE,a.BACK_LEAVE_STATE, \n");
        resultSql.append("a.BACK_LEAVE_DATE as backLeaveDate,a.REMARK,a.REISSUED_FLAG as reissuedFlag,a.REISSUED_TIME,a.REISSUED_REASON,a.TITLE,a.CREATE_BY, \n");
        resultSql.append("a.CREATE_NAME,a.CREATE_DATE,a.UPDATE_BY,a.UPDATE_NAME,a.UPDATE_DATE,a.APPR_STATUS as apprStatus,a.APPR_FINISH_USER_NAME,a.APPR_FINISH_USER_ID,a.APPR_FINISH_TIME, \n");
        resultSql.append("(CASE WHEN a.LEAVE_ID='"+ user.getId() +"' OR a.CREATE_BY='"+user.getUserName()+"' THEN '1' ELSE '0' END) as editStatus  \n");
                       
        String temp = "FROM T_O_TRAVEL_LEAVE a "
        			+ "LEFT JOIN T_PM_PROJECT c ON a.PROJECT_ID=c.ID "
        			+ "WHERE (a.LEAVE_ID=? OR a.CREATE_BY=? OR a.ID in (select T_O_ID from T_O_TRAVEL_LEAVEDETAIL where WITH_PEOPLE_ID like ?)) ";
        
        String leaveName = request.getParameter("leaveName");                
        if (StringUtil.isNotEmpty(leaveName)) {
            temp += " AND (a.LEAVE_NAME LIKE '%" + leaveName + "%' OR a.ID in (select T_O_ID from T_O_TRAVEL_LEAVEDETAIL where WITH_PEOPLE_NAME like '%"+ leaveName +"%'))";
        }
        
        String projectName = request.getParameter("projectName");
        if (StringUtil.isNotEmpty(projectName)) {
            temp += " AND c.PROJECT_NAME LIKE '%" + projectName + "%'";
        }                
        
        String leaveTime_begin = request.getParameter("leaveTime_begin");
        if (StringUtil.isNotEmpty(leaveTime_begin)) {
            temp += " AND a.LEAVE_TIME >= to_date('" + leaveTime_begin + "','yyyy-mm-dd')";
        }     
        
        String leaveTime_end = request.getParameter("leaveTime_end");
        if (StringUtil.isNotEmpty(leaveTime_end)) {
            temp += " AND a.LEAVE_TIME <= to_date('" + leaveTime_end + "','yyyy-mm-dd')";
        }     
                
        temp += " ORDER BY a.LEAVE_TIME DESC";

        String[] params = new String[] { user.getId(), user.getUserName(),"%"+user.getId()+"%" };
        
		
        List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                dataGrid.getPage(), dataGrid.getRows(), params);
        
        Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

        dataGrid.setResults(result);
        dataGrid.setTotal(count.intValue());
				
        TagUtil.datagrid(response, dataGrid);	
			
		
	}

	/**
	 * 删除差旅-人员请假信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TOTravelLeaveEntity tOTravelLeave, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tOTravelLeave = systemService.getEntity(TOTravelLeaveEntity.class, tOTravelLeave.getId());
		String message = "差旅-人员请假信息表删除成功";
		try{
			tOTravelLeaveService.delMain(tOTravelLeave);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "差旅-人员请假信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除差旅-人员请假信息表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "差旅-人员请假信息表删除成功";
		try{
			for(String id:ids.split(",")){
				TOTravelLeaveEntity tOTravelLeave = systemService.getEntity(TOTravelLeaveEntity.class,
				id
				);
				tOTravelLeaveService.delMain(tOTravelLeave);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "差旅-人员请假信息表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
     * 保存差旅-人员请假信息表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TOTravelLeaveEntity tOTravelLeave,TOTravelLeavePage tOTravelLeavePage, HttpServletRequest request) {
        List<TOTravelLeavedetailEntity> tOTravelLeavedetailList =  tOTravelLeavePage.getTOTravelLeavedetailList();
        AjaxJson j = new AjaxJson();
        //如果差旅id不为空，则更新
        if(StringUtil.isNotEmpty(tOTravelLeave.getId())){
            try{
                TOTravelLeaveEntity d = systemService.get(TOTravelLeaveEntity.class, tOTravelLeave.getId());
                // 防止更新时附件丢失
                MyBeanUtils.copyBeanNotNull2Bean(tOTravelLeave, d);
                d.setApprStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                tOTravelLeaveService.updateMain(d, tOTravelLeavedetailList);
                message = MutiLangUtil.paramUpdSuccess("更新差旅-人员请假信息表成功");
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }catch(Exception e){
                e.printStackTrace();
                message = "更新差旅-人员请假信息表失败";
                throw new BusinessException(e.getMessage());
            }
        }else{
            try{
                tOTravelLeave.setApprStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                tOTravelLeaveService.addMain(tOTravelLeave, tOTravelLeavedetailList);
                message = MutiLangUtil.paramUpdSuccess("差旅-人员请假信息表添加成功");
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }catch(Exception e){
                e.printStackTrace();
                message = "差旅-人员请假信息表添加失败";
                throw new BusinessException(e.getMessage());
            }
        }
        j.setObj(tOTravelLeave.getId());
        j.setMsg(message);
        return j;
    }

	/**
	 * 差旅-人员请假信息表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TOTravelLeaveEntity tOTravelLeave, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOTravelLeave.getId())) {
			tOTravelLeave = tOTravelLeaveService.getEntity(TOTravelLeaveEntity.class, tOTravelLeave.getId());
			req.setAttribute("tOTravelLeavePage", tOTravelLeave);
		}else{
		    tOTravelLeave.setLeaveTime( DateUtils.getDate());
		    tOTravelLeave.setOpinionDate( DateUtils.getDate());
		    tOTravelLeave.setBackLeaveDate( DateUtils.getDate());
		    tOTravelLeave.setInstrucDate(DateUtils.getDate());
		    req.setAttribute("tOTravelLeavePage", tOTravelLeave);
		    
		    TSUser user = ResourceUtil.getSessionUserName();		    
		    tOTravelLeave.setLeaveId(user.getId());
		    tOTravelLeave.setLeaveName(user.getRealName());
		    
		    TSDepart depart = user.getCurrentDepart();
		    tOTravelLeave.setDepartId(depart.getId());
		    tOTravelLeave.setDepartName(depart.getDepartname());
		    
		}
		return new ModelAndView("com/kingtake/office/travel/tOTravelLeave");
	}
	
	
	/**
	 * 差旅-人员请假信息表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOTravelLeaveEntity tOTravelLeave, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOTravelLeave.getId())) {
			tOTravelLeave = tOTravelLeaveService.getEntity(TOTravelLeaveEntity.class, tOTravelLeave.getId());
			/*TPmProjectEntity tPmProjectEntity =new TPmProjectEntity();
			String projectId = tOTravelLeave.getProjectId();
			if(StringUtil.isNotEmpty(projectId)){
			    tPmProjectEntity = systemService.getEntity(TPmProjectEntity.class, projectId);
			    tOTravelLeave.setProjectName(tPmProjectEntity.getProjectName());
			}*/
			if(tOTravelLeave.getProject() != null){
				tOTravelLeave.setProjectName(tOTravelLeave.getProject().getProjectName());
			}
			req.setAttribute("tOTravelLeavePage", tOTravelLeave);
		}
		return new ModelAndView("com/kingtake/office/travel/tOTravelLeave");
	}
	
	
	/**
	 * 加载明细列表[差旅-人员请假详细信息表]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tOTravelLeavedetailList")
	public ModelAndView tOTravelLeavedetailList(TOTravelLeaveEntity tOTravelLeave, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tOTravelLeave.getId();
		//===================================================================================
		//查询-差旅-人员请假详细信息表
	    String hql0 = "from TOTravelLeavedetailEntity where 1 = 1 AND t_O_ID = ? ";
	    try{
	    	List<TOTravelLeavedetailEntity> tOTravelLeavedetailEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tOTravelLeavedetailList", tOTravelLeavedetailEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/kingtake/office/travel/tOTravelLeavedetailList");
	}
	
	/**
     * 新增时人员选择页面跳转
     * 
     * @param request
     * @param noticeid
     * @return
     */
    @RequestMapping(params = "userList")
    public ModelAndView userList(HttpServletRequest request, String leaveid) {
        request.setAttribute("leaveid", leaveid);
        return new ModelAndView("com/kingtake/office/travel/userSelect");
    }
    
    /**
     * 请假单人员数据筛选
     * 
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     */
    /*@RequestMapping(params = "addUserDatagrid")
    @ResponseBody
    public JSONObject addUserDatagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden };
        cq.in("status", userstate);

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TSUser> resultList = dataGrid.getResults();
        List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
        for(TSUser u : resultList){
            Map<String, String> usermap = new HashMap<String, String>();
            usermap.put("id", u.getId());
            usermap.put("userName", u.getUserName());
            usermap.put("realName", u.getRealName());
            usermap.put("departid", u.getRealName());
            usermap.put("departname", u.getUserOrgList());
            
            userList.add(usermap);
        }
        JSONObject json = new JSONObject();
        json.put("userList", userList);
        return json;
    }*/
    @RequestMapping(params = "addUserDatagrid")
    @ResponseBody
    public void addUserDatagrid(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        // 查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden };
        cq.in("status", userstate);

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    
    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOTravelLeaveEntity toTravelLeaveEntity,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        
        TSUser user = ResourceUtil.getSessionUserName();
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT a.LEAVE_NAME as leaveName,a.DEPART_NAME as departName,  \n");
        resultSql.append("a.LEAVE_TIME as leaveTime,a.DUTY as duty,a.UNIT_OPINION as unitOpinion,a.OPINION_USERNAME as opinionUsername,a.OPINION_DATE as opinionDate,  \n");
        resultSql.append("a.DEPART_INSTRUC as departInstruc,a.INSTRUC_USERNAME as instrucUsername,a.INSTRUC_DATE as instrucDate,a.BACK_LEAVE_STATE as backLeaveState, \n");
        resultSql.append("a.BACK_LEAVE_DATE as backLeaveDate,a.REMARK as remark,a.REISSUED_FLAG as reissuedFlag,a.REISSUED_TIME as reissuedTime,a.REISSUED_REASON as reissuedReason,a.TITLE as title \n");        
                       
        String temp = "FROM T_O_TRAVEL_LEAVE a "
        			+ "LEFT JOIN T_PM_PROJECT c ON a.PROJECT_ID=c.ID "
        			+ "WHERE (a.LEAVE_ID=? OR a.CREATE_BY=? OR a.ID in (select T_O_ID from T_O_TRAVEL_LEAVEDETAIL where WITH_PEOPLE_ID like ?)) ";
        
        String leaveName = request.getParameter("leaveName");                
        if (StringUtil.isNotEmpty(leaveName)) {
            temp += " AND (a.LEAVE_NAME LIKE '%" + leaveName + "%' OR a.ID in (select T_O_ID from T_O_TRAVEL_LEAVEDETAIL where WITH_PEOPLE_NAME like '%"+ leaveName +"%'))";
        }
        
        String projectName = request.getParameter("projectName");
        if (StringUtil.isNotEmpty(projectName)) {
            temp += " AND c.PROJECT_NAME LIKE '%" + projectName + "%'";
        }                
        
        String leaveTime_begin = request.getParameter("leaveTime_begin");
        if (StringUtil.isNotEmpty(leaveTime_begin)) {
            temp += " AND a.LEAVE_TIME >= to_date('" + leaveTime_begin + "','yyyy-mm-dd')";
        }     
        
        String leaveTime_end = request.getParameter("leaveTime_end");
        if (StringUtil.isNotEmpty(leaveTime_end)) {
            temp += " AND a.LEAVE_TIME <= to_date('" + leaveTime_end + "','yyyy-mm-dd')";
        }     
                
        temp += " ORDER BY a.LEAVE_TIME DESC";

        String[] params = new String[] { user.getId(), user.getUserName(),"%"+user.getId()+"%" };
        		
        List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                dataGrid.getPage(), dataGrid.getRows(), params);
        
        
        List<TOTravelLeaveEntity> toLeaveEntities = new ArrayList<TOTravelLeaveEntity>();        
		if (result.size() > 0) {
			for (Map<String, Object> map : result) {
				TOTravelLeaveEntity receiveEntity = new TOTravelLeaveEntity();


				receiveEntity.setLeaveName((String)map.get("leaveName"));
				receiveEntity.setDepartName((String)map.get("departName"));
				receiveEntity.setLeaveTime((Date)map.get("leaveTime"));
				receiveEntity.setDuty((String)map.get("duty"));
				receiveEntity.setUnitOpinion((String)map.get("unitOpinion"));											
				receiveEntity.setOpinionUsername((String)map.get("opinionUsername"));
				receiveEntity.setOpinionDate((Date)map.get("opinionDate"));
				receiveEntity.setDepartInstruc((String)map.get("departInstruc"));
				receiveEntity.setInstrucUsername((String)map.get("instrucUsername"));				
				receiveEntity.setInstrucDate((Date)map.get("instrucDate"));
				receiveEntity.setBackLeaveState((String)map.get("backLeaveState"));
				receiveEntity.setBackLeaveDate((Date)map.get("backLeaveDate"));
				receiveEntity.setRemark((String)map.get("remark"));
				receiveEntity.setReissuedFlag((String)map.get("reissuedFlag"));
				receiveEntity.setReissuedTime((Date)map.get("reissuedTime"));
				receiveEntity.setReissuedReason((String)map.get("reissuedReason"));
				receiveEntity.setTitle((String)map.get("title"));
				
				
				toLeaveEntities.add(receiveEntity);
			}
		}
                                     
        
        
        //List<TOTravelLeaveEntity> toLeaveEntities = this.tOTravelLeaveService.getListByCriteriaQuery(cq,false);
        modelMap.put(NormalExcelConstants.FILE_NAME,"差旅-人员请假详细信息表");
        modelMap.put(NormalExcelConstants.CLASS,TOTravelLeaveEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("差旅-人员请假详细信息表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
            "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST,toLeaveEntities);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    

    /**
     * 审批tab页
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goApprReceiveTab")
    public ModelAndView goApprReceiveTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/office/travel/travelApprList-receiveTab");
    }

    /**
     * 任务书审核列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "travelAuditDepartment")
    public ModelAndView travelAuditDepartment(HttpServletRequest request) {
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
            return new ModelAndView("com/kingtake/office/travel/travelApprList-receive");
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
            HttpServletResponse response,
            DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批

            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();//获取参数

            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            StringBuffer resultSql = new StringBuffer();
            resultSql
                    .append("SELECT APPR.ID AS APPR_ID, APPR.PROJECT_ID AS PROJECTID, APPR.Leave_Id leaveId,APPR.LEAVE_NAME leaveName,APPR.LEAVE_TIME leaveTime,APPR.DEPART_ID departId, APPR.Depart_Name departName, APPR.Appr_Status auditStatus, ");
            resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, ");
            resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, ");
            resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT ");

            String temp = "FROM t_o_travel_leave APPR,T_PM_APPR_SEND_LOGS SEND, "
                    + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT "
                    + "WHERE APPR.ID = SEND.APPR_ID  and SEND.ID = RECEIVE.SEND_ID "
                    + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID "
                    + "AND RECEIVE.RECEIVE_USERID = ? ";

            if (SrmipConstants.YES.equals(operateStatus)) {
                //已处理
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                //未处理：需要是有效的
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                        + SrmipConstants.YES;
            }

            String leaveName = request.getParameter("leaveName");
            if (StringUtil.isNotEmpty(leaveName)) {
                temp += " AND APPR.leave_name LIKE '%" + leaveName + "%'";
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
}
