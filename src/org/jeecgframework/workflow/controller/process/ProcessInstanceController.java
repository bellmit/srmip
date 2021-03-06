package org.jeecgframework.workflow.controller.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.model.activiti.Variable;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * ???????????????????????????????????????
 * @author liujinghua
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/processInstanceController")
public class ProcessInstanceController {
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	protected RepositoryService repositoryService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskJeecgService taskJeecgService;
	
    @Autowired
    private SystemService systemService;

	/**
	 * easyui ???????????????????????????
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "runningProcessList")
	public ModelAndView runningProcessList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		return new ModelAndView("workflow/activiti/runninglist");
	}

	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(params = "myRunningProcessListDataGrid")
	public void myRunningProcessListDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String currentUserName = ResourceUtil.getSessionUserName().getUserName();
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().startedBy(currentUserName);
        List<HistoricProcessInstance> list = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc()//2015-08-31 zcq???????????????????????????
                .listPage((dataGrid.getPage() - 1) * dataGrid.getRows(), dataGrid.getRows());

	    StringBuffer rows = new StringBuffer();
		for(HistoricProcessInstance hi : list){
			String starttime = DateFormatUtils.format(hi.getStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endtime = hi.getEndTime()==null?"":DateFormatUtils.format(hi.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			
			long totalTimes = hi.getEndTime()==null?(Calendar.getInstance().getTimeInMillis()-hi.getStartTime().getTime()):(hi.getEndTime().getTime()-hi.getStartTime().getTime());
			
			long dayCount = totalTimes /(1000*60*60*24);//?????????
			long restTimes = totalTimes %(1000*60*60*24);//?????????????????????????????????
			long hourCount = restTimes/(1000*60*60);//??????
			restTimes = restTimes % (1000*60*60);
			long minuteCount = restTimes / (1000*60);
			
			String spendTimes = dayCount+"???"+hourCount+"??????"+minuteCount+"???";
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(hi.getProcessDefinitionId()).singleResult();
            String businessKey = hi.getBusinessKey();//??????id
            String sql = "select t.business_name from t_s_basebus t where t.id=?";
            List<Map<String, Object>> queryList = this.systemService.findForJdbc(sql, businessKey);
            String businessName = "";
            if (queryList.size() > 0) {
                Map<String, Object> result = queryList.get(0);
                businessName = (String) result.get("BUSINESS_NAME");//??????????????????
            }
            rows.append("{'id':" + hi.getId() + ",'prcocessDefinitionName':'"
                    + StringUtils.trimToEmpty(processDefinition.getName()) + "','businessName':'"
                    + StringUtils.trimToEmpty(businessName) + "','startUserId':'" + hi.getStartUserId()
                    + "','starttime':'" + starttime + "','endtime':'"
                    + endtime + "','spendTimes':'" + spendTimes + "','processDefinitionId':'"
                    + hi.getProcessDefinitionId() + "','processInstanceId':'" + hi.getId() + "'},");
		}
		
		
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+historicProcessInstanceQuery.count()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	

	/** 
     * ?????????????????????????????????
     * ???????????????????????????????????????????????????????????????
     * @param processInstanceId ????????????ID
     */
	@RequestMapping(params = "invalidProcess")
	@ResponseBody
	public AjaxJson invalidProcess(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		runtimeService.deleteProcessInstance(processInstanceId, "?????????????????????");
		String message = "??????????????????";
		j.setMsg(message);
		return j;
	}
	
	/**
     * ???????????????????????????????????????
     * ???????????????????????????????????????????????????????????????
     * @param processInstanceId ????????????ID
     */
	@RequestMapping(params = "callBackProcess")
	@ResponseBody
	public AjaxJson callBackProcess(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		
		runtimeService.deleteProcessInstance(processInstanceId, "?????????????????????");		
		String message = "??????????????????";
		j.setMsg(message);
		return j;
	}

	
	/**
	 * easyui ????????????
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "reassignInit")
	public ModelAndView reassignInit(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		return new ModelAndView("workflow/activiti/reassignInit");
	}
	
	/**
     * ??????????????????
     * @param processInstanceId ????????????ID
     */
	@RequestMapping(params = "reassign")
	@ResponseBody
	public AjaxJson reassign(@RequestParam("taskId") String taskId, @RequestParam("userName") String assignUserId,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Task  task = activitiService.getTask(taskId);		
		String currentUserName = ResourceUtil.getSessionUserName().getUserName();
		taskService.setOwner(task.getId(), currentUserName);
		taskService.setAssignee(task.getId(), assignUserId);
		String message = "????????????";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	@RequestMapping(params = "reassignUsers")
	public String reassignUsers() {
		return "workflow/activiti/reassignUsers";
	}
	
	/**
	 * ???????????????
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridUsers")
	public void datagridUsers(TSUser tsuser,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsuser, request.getParameterMap());
		this.userService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui ???????????????????????????
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "runningProcessDataGrid")
    public void runningProcessDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        /*
         * List<HistoricProcessInstance> historicProcessInstances = historyService
         * .createHistoricProcessInstanceQuery()i .unfinished().list();
         */
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();

        if (StringUtils.isNotEmpty(request.getParameter("startUserId"))) {
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedBy(request.getParameter("startUserId"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("processInstanceId"))) {
            historicProcessInstanceQuery = historicProcessInstanceQuery.processInstanceId(request
                    .getParameter("processInstanceId"));
        }

        String starttime_begin = request.getParameter("starttime_begin");//????????????
        String starttime_end = request.getParameter("starttime_end");//????????????
        if (StringUtils.isNotEmpty(starttime_begin)) {
            try {
                historicProcessInstanceQuery.startedAfter(DateUtils.parseDate(starttime_begin, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(starttime_end)) {
            try {
                historicProcessInstanceQuery.startedBefore(DateUtils.parseDate(starttime_end, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String endtime_begin = request.getParameter("endtime_begin");//????????????
        String endtime_end = request.getParameter("endtime_end");//????????????
        if (StringUtils.isNotEmpty(endtime_begin)) {
            try {
                historicProcessInstanceQuery.finishedAfter(DateUtils.parseDate(endtime_begin, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotEmpty(endtime_end)) {
            try {
                historicProcessInstanceQuery.finishedBefore(DateUtils.parseDate(endtime_end, "yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //TODO ??????treegrid
        List<HistoricProcessInstance> list = historicProcessInstanceQuery.unfinished()
                .orderByProcessInstanceStartTime().desc()
                .listPage((dataGrid.getPage() - 1) * dataGrid.getRows(), dataGrid.getRows());
        long count = historicProcessInstanceQuery.unfinished().count();
        StringBuffer rows = new StringBuffer();
        for (HistoricProcessInstance hi : list) {
            String starttime = DateFormatUtils.format(hi.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            String endtime = hi.getEndTime() == null ? "" : DateFormatUtils.format(hi.getEndTime(),
                    "yyyy-MM-dd HH:mm:ss");

            long totalTimes = hi.getEndTime() == null ? (Calendar.getInstance().getTimeInMillis() - hi.getStartTime()
                    .getTime()) : (hi.getEndTime().getTime() - hi.getStartTime().getTime());

            long dayCount = totalTimes / (1000 * 60 * 60 * 24);//?????????
            long restTimes = totalTimes % (1000 * 60 * 60 * 24);//?????????????????????????????????
            long hourCount = restTimes / (1000 * 60 * 60);//??????
            restTimes = restTimes % (1000 * 60 * 60);
            long minuteCount = restTimes / (1000 * 60);

            String spendTimes = dayCount + "???" + hourCount + "??????" + minuteCount + "???";
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(hi.getProcessDefinitionId()).singleResult();

            String isSuspended = "finished";
            String activityName = "";//??????????????????
            String activityUser = "";//?????????????????????
            String taskId = "";//??????ID
            if (hi.getEndTime() == null) {//endtime?????????????????????????????????
                ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(hi.getId())
                        .singleResult();
                List<Task> tasks = taskService.createTaskQuery().processInstanceId(hi.getId()).list();
                if (tasks.size() > 1) {
                    //update-begin--Author:zhangdaihao  Date:20140826 for???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????--------------------
                    isSuspended = "" + pi.isSuspended();
                    activityName = StringUtils.trimToEmpty(tasks.get(0).getName());
                    activityUser = StringUtils.trimToEmpty(tasks.get(0).getAssignee());
                    taskId = oConvertUtils.getString(tasks.get(0).getId());
                    rows.append("{'id':'" + hi.getId() + "','iconCls':'icon-comturn','state':'closed','text':'"
                            + processDefinition.getName() + "','_parentId':'','taskId':'" + taskId
                            + "','activityName':'" + activityName + "','activityUser':'','prcocessDefinitionName':'"
                            + processDefinition.getName() + "','startUserId':'" + hi.getStartUserId()
                            + "','starttime':'" + starttime + "','endtime':'" + endtime + "','spendTimes':'"
                            + spendTimes + "','isSuspended':'','processDefinitionId':'" + hi.getProcessDefinitionId()
                            + "','processInstanceId':'" + hi.getId() + "'},");
                    int i = 1;
                    for (Task task : tasks) {
                        isSuspended = "" + pi.isSuspended();
                        activityName = StringUtils.trimToEmpty(task.getName());
                        activityUser = StringUtils.trimToEmpty(task.getAssignee());
                        taskId = oConvertUtils.getString(task.getId());
                        rows.append("{'id':'taskid:" + hi.getId() + ":" + (i++) + "','iconCls':'icon-comturn','text':'"
                                + processDefinition.getName() + "','_parentId':'" + hi.getId() + "','taskId':'"
                                + taskId + "','activityName':'" + activityName + "','activityUser':'" + activityUser
                                + "','prcocessDefinitionName':'" + processDefinition.getName() + "','startUserId':'"
                                + hi.getStartUserId() + "','starttime':'" + starttime + "','endtime':'" + endtime
                                + "','spendTimes':'" + spendTimes + "','isSuspended':'" + isSuspended
                                + "','processDefinitionId':'" + hi.getProcessDefinitionId() + "','processInstanceId':'"
                                + hi.getId() + "'},");
                    }
                    //update-end--Author:zhangdaihao  Date:20140826 for???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????----------------------
                } else {
                    for (Task task : tasks) {
                        isSuspended = "" + pi.isSuspended();
                        activityName = StringUtils.trimToEmpty(task.getName());
                        activityUser = StringUtils.trimToEmpty(task.getAssignee());
                        taskId = oConvertUtils.getString(task.getId());
                        rows.append("{'id':'" + hi.getId() + "','text':'" + processDefinition.getName()
                                + "','iconCls':'icon-comturn','_parentId':'','taskId':'" + taskId
                                + "','activityName':'" + activityName + "','activityUser':'" + activityUser
                                + "','prcocessDefinitionName':'" + processDefinition.getName() + "','startUserId':'"
                                + hi.getStartUserId() + "','starttime':'" + starttime + "','endtime':'" + endtime
                                + "','spendTimes':'" + spendTimes + "','isSuspended':'" + isSuspended
                                + "','processDefinitionId':'" + hi.getProcessDefinitionId() + "','processInstanceId':'"
                                + hi.getId() + "'},");
                    }
                }
            }
        }

        String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");

        JSONObject jObject = JSONObject.fromObject("{'total':" + count + ",'rows':[" + rowStr + "]}");
        responseDatagrid(response, jObject);
    }

	/**
     * ?????????????????????????????????
     * @param processInstanceId ????????????ID
     */
	@RequestMapping(params = "suspend")
	@ResponseBody
	public AjaxJson suspend(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		runtimeService.suspendProcessInstanceById(processInstanceId);
		
		String message = "????????????";
		j.setMsg(message);
		return j;
	}
	
	/**
     * ???????????????????????????
     * @param processInstanceId ????????????ID
     */
	@RequestMapping(params = "restart")
	@ResponseBody
	public AjaxJson restart(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		runtimeService.activateProcessInstanceById(processInstanceId);
		
		String message = "????????????";
		j.setMsg(message);
		return j;
	}
	
	/**
     * ??????????????????????????????
     * @param processInstanceId ????????????ID
     */
	@RequestMapping(params = "close")
	@ResponseBody
	public AjaxJson close(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		//repositoryService.deleteDeployment(deploymentId, true);
		runtimeService.deleteProcessInstance(processInstanceId, "??????????????????");
		
		String message = "????????????";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * ??????????????????????????????
	 * @return
	 */
	@RequestMapping(params = "myRunningProcessList")
	public String myRunningProcessList() {
		return "workflow/activiti/myRunningProcessList";
	}
	
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping(params = "skipNodeInit")
	public ModelAndView skipNodeInit(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String taskId = request.getParameter("taskId");
		List taskList  = activitiService.getAllTaskNode(taskId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("taskList", taskList);
		return new ModelAndView("workflow/activiti/skipNodeInit");
	}
	
	/**
	 * ??????????????????
	 * @param taskId
	 * @param skipNode
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "skipNode")
	@ResponseBody
	public AjaxJson skipNode(@RequestParam("taskId") String taskId, @RequestParam("skipTaskNode") String skipNode,HttpServletRequest request,Variable var) throws Exception {
		AjaxJson j = new AjaxJson();
		ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
		//??????????????????
		Map<String, Object> map = var.getVariableMap(processHandle.getTpProcesspros());
		if("end".equals(skipNode)){
			taskJeecgService.endProcess(taskId);
		}else{
			taskJeecgService.goProcessTaskNode(taskId,skipNode,map);
		}
		String message = "????????????";
		j.setMsg(message);
		return j;
	}
	
	// -----------------------------------------------------------------------------------
	// ??????????????????????????????????????? (Add by Quainty)
	// -----------------------------------------------------------------------------------
	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		try {
			PrintWriter pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
