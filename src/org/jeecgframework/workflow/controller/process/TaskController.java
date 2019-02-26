package org.jeecgframework.workflow.controller.process;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.pojo.base.TPProcessnode;
import org.jeecgframework.workflow.service.ActivitiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @ClassName: TaskController
 * @Description: 我的任务
 * @author scott
 * @date 2012-8-19 下午04:20:06
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/taskController")
public class TaskController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private HistoryService historyService; 
	@Autowired
	private ActivitiDao activitiDao;

	/**
	 * 跳转到我的任务列表(总任务列表)
	 */
	@RequestMapping(params = "goTaskListTab")
	public ModelAndView goTaskListTab(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-tab");
	}
	
	/**
	 * 跳转到我的任务列表- 我的任务（个人）
	 */
	@RequestMapping(params = "goMyTaskList")
	public ModelAndView goMyTaskList(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-person");
	}
	
	/**
	 * 跳转到我的任务列表 - 我的任务（角色组）需要签收
	 */
	@RequestMapping(params = "goGroupTaskList")
	public ModelAndView goGroupTaskList(HttpServletRequest request) {
		//TaskQuery userRoleTask = taskService.createTaskQuery().processDefinitionKey(getProcessDefKey()).taskCandidateUser(userId);
		//request.setAttribute("userRoleTask", userRoleTask);
		return new ModelAndView("workflow/task/taskList-group");
	}

	
	/**
	 * 跳转到我的任务列表- 我的任务（历史任务）
	 */
	@RequestMapping(params = "goHistoryTaskList")
	public ModelAndView goHistoryTaskList(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-history");
	}
	
	/**
	 * 跳转到我的任务处理页面
	 */
	@RequestMapping(params = "goTaskTab")
	public ModelAndView goTaskTab(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		//通过任务ID获取流程实例ID
		String processInstanceId = activitiService.getTask(taskId).getProcessInstanceId();
		request.setAttribute("processInstanceId", processInstanceId);
		return new ModelAndView("workflow/task/task-tab");
	}
	
	
	/**
	 * 跳转到我的任务处理页面
	 */
	@RequestMapping(params = "goProcessHisTab")
	public ModelAndView goProcessHisTab(HttpServletRequest request) {
		String processInstanceId = request.getParameter("processInstanceId");
		request.setAttribute("processInstanceId", processInstanceId);
		String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
        	request.setAttribute("businessCode", businessCode);
        }
		return new ModelAndView("workflow/task/process-his-tab");
	}
	
	/**
	 * 跳转到我的任务-附加页面
	 */
	@RequestMapping(params = "goTaskForm")
	public ModelAndView goTaskForm(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		Task  task = activitiService.getTask(taskId);
		String CONTENT_URL = null;//任务节点对应的单据页面
		//获取流程定义ID
		String insId = task.getProcessInstanceId();
		ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
		
		//首先判断节点里面，是否配置了对应的表单页面
	    CONTENT_URL = oConvertUtils.getString(processHandle.getTpProcessnode().getModelandview());
		if(oConvertUtils.isNotEmpty(CONTENT_URL)){
			//拼接参数
			CONTENT_URL = CONTENT_URL +"&id="+runtimeService.getVariable(insId, "id");
			//传递到页面
			//request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		}else{
			//如果流程节点没配置表单，则获取流程变量中的CONTENT_URL
		     CONTENT_URL = oConvertUtils.getString(runtimeService.getVariable(insId, WorkFlowGlobals.BPM_FORM_CONTENT_URL));
			if(oConvertUtils.isEmpty(CONTENT_URL)){
				//step.1 获取流程中的start节点配置的表单
				TPProcessnode startNode = activitiService.getProcessStartNode(taskId);
				if(startNode!=null){
					CONTENT_URL = startNode.getModelandview();
					//拼接参数
					CONTENT_URL = CONTENT_URL +"&id="+runtimeService.getVariable(insId, WorkFlowGlobals.BPM_DATA_ID);
					runtimeService.setVariable(insId,WorkFlowGlobals.BPM_FORM_CONTENT_URL, CONTENT_URL);
				}
			}
			//request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		}
		String processnodeId = "";
		if(processHandle.getTpProcessnode()!=null){
			processnodeId = processHandle.getTpProcessnode().getId();
		}
		if(task.getName()!=null && !"".equals(task.getName())){
			if("机关".equals(task.getName())){
				CONTENT_URL = CONTENT_URL+ "&processnodeId="+processnodeId+"&taskName=jgsh";
			}
		}else{
			CONTENT_URL = CONTENT_URL+ "&processnodeId="+processnodeId;
		}
        String editFlag = oConvertUtils.getString(taskService.getVariableLocal(taskId, "editFlag"));
        if (StringUtils.isNotEmpty(editFlag)) {
            CONTENT_URL = CONTENT_URL + "&editFlag=" + editFlag;
        }
		request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		return new ModelAndView("workflow/task/task-form");
	}
	
	/**
	 * 跳转到我的任务-附加页面
	 */
	@RequestMapping(params = "goProcessHisForm")
	public ModelAndView goProcessHisForm(HttpServletRequest request) {
		//获取流程定义ID
		String insId = request.getParameter("processInstanceId");
		//如果流程节点没配置表单，则获取流程变量中的CONTENT_URL
        String CONTENT_URL = activitiDao.getHisVarinst(WorkFlowGlobals.BPM_FORM_CONTENT_URL, insId);
        request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL + "&read=1");//zcq修改 2015-10-15 历史查看只读
		return new ModelAndView("workflow/task/task-form");
	}
	
	/**
	 * 跳转到我的任务-任务处理页面
	 */
	@RequestMapping(params = "goTaskOperate")
	public ModelAndView goTaskOperate(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		Task task = activitiService.getTask(taskId);
        ProcessDefinition processDef = activitiService.getProcessDefinition(task.getProcessDefinitionId());
		int fromIndex = 0;
		int toIndex = 0;
		//流程下一步节点操作
		List<Map> transList = activitiService.getOutTransitions(taskId);
		
		//判断一下任务节点后续的分支，是否只有一个，如果是且分支的路线中文名字没有的话，默认为提交
		if(transList.size()==1){
			for(Map t:transList){
				t.put("Transition", "确认提交");
			}
		}
		//流程审批日志
		List bpmLogList = activitiService.findBpmLogsByBpmID(task.getProcessInstanceId());
		if(bpmLogList.size()-3>0){
			fromIndex = bpmLogList.size() - 3;
			toIndex =  bpmLogList.size();
		}else{
			fromIndex = 0;
			toIndex =  bpmLogList.size();
		}
        List bpmLogNewList = bpmLogList.subList(fromIndex, toIndex);
		//处理意见信息
		request.setAttribute("bpmLogList", bpmLogList);
		request.setAttribute("taskId", taskId);
		request.setAttribute("taskName", task.getName());
		//当前任务
		request.setAttribute("task",task);
		//流程分支
		request.setAttribute("transitionList", transList);
        //下一步节点数目
        request.setAttribute("nextCodeCount", transList == null ? 0 : transList.size());
		request.setAttribute("bpmLogListCount",bpmLogList.size());
		request.setAttribute("bpmLogNewList", bpmLogNewList);
		request.setAttribute("bpmLogNewListCount", bpmLogNewList.size());
		//历史任务节点
		List<Map<String,Object>> histListNode = activitiService.getHistTaskNodeList(task.getProcessInstanceId());
		request.setAttribute("histListNode", histListNode);
		request.setAttribute("histListSize", histListNode.size());
        //发送消息
        StringBuffer sb = new StringBuffer();
        String businessName = (String) runtimeService.getVariable(task.getProcessInstanceId(),
                WorkFlowGlobals.BPM_BUSINESS_NAME);
        String applyUserId = (String) runtimeService.getVariable(task.getProcessInstanceId(), "applyUserId");
        sb.append("您有个流程[").append(processDef.getName()).append("_")
                .append(businessName).append("]需要办理，").append("申请人：")
                .append(activitiDao.getUserRealName(applyUserId)).append("，").append("请尽快处理。");
        request.setAttribute("message", sb.toString());
		return new ModelAndView("workflow/task/task-operate");
	}
	/**
	 * 跳转到我的任务-任务处理页面
	 */
	@RequestMapping(params = "goProcessHisOperate")
	public ModelAndView goProcessHisOperate(HttpServletRequest request) {
		String insId = request.getParameter("processInstanceId");
		int fromIndex = 0;
		int toIndex = 0;
		//流程审批日志
		List bpmLogList = activitiService.findBpmLogsByBpmID(insId);
		if(bpmLogList.size()-3>0){
			fromIndex = bpmLogList.size() - 3;
			toIndex =  bpmLogList.size();
		}else{
			fromIndex = 0;
			toIndex =  bpmLogList.size();
		}
		List bpmLogNewList = bpmLogList.subList(fromIndex, toIndex);
		request.setAttribute("bpmLogList", bpmLogList);
		request.setAttribute("bpmLogListCount",bpmLogList.size());
		request.setAttribute("bpmLogNewList", bpmLogNewList);
		request.setAttribute("bpmLogNewListCount", bpmLogNewList.size());
		return new ModelAndView("workflow/task/task-operate");
	}
	
	/**
	 * 跳转到我的任务-流程图
	 */
	@RequestMapping(params = "goTaskMap")
	public ModelAndView goTaskMap(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String mapUrl = "activitiController.do?openProcessPic&tag=task&taskId="+taskId;
		request.setAttribute("mapUrl", mapUrl);
		return new ModelAndView("workflow/task/task-map");
	}
	/**
	 * 待办任务列表-用户所有的任务
	 */

	@RequestMapping(params = "taskAllList")
	public void taskAllList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		List taskList = activitiService.findPriTodoTasks(user.getUserName(),request);
		Long taskSize = activitiService.countPriTodaoTask(user.getUserName(),request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 待办任务列表-组任务
	 */

	@RequestMapping(params = "taskGroupList")
	public void taskGroupList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		//获取当前用户角色集
		List<TSRoleUser> roles = systemService.findByProperty(TSRoleUser.class, "TSUser", user);
		List taskList = activitiService.findGroupTodoTasks(roles, request);
		Long taskSize = activitiService.countGroupTodoTasks(roles, request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);

	}
	/**
	 * 待办任务列表-历史任务
	 */

	@RequestMapping(params = "taskHistoryList")
	public void taskHistoryList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		List taskList = activitiService.findHistoryTasks(user.getUserName(),request);
		Long taskSize = activitiService.countHistoryTasks(user.getUserName(),request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);

	}
}
