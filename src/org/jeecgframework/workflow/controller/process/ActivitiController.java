package org.jeecgframework.workflow.controller.process;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.PagerUtil;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.model.activiti.Variable;
import org.jeecgframework.workflow.model.diagram.HistoryProcessInstanceDiagramCmd;
import org.jeecgframework.workflow.model.diagram.HistoryProcessInstanceDiagramPositionCmd;
import org.jeecgframework.workflow.pojo.activiti.ActHiProcinst;
import org.jeecgframework.workflow.pojo.activiti.ActIdUser;
import org.jeecgframework.workflow.pojo.activiti.ActRuTask;
import org.jeecgframework.workflow.pojo.base.TPBpmFile;
import org.jeecgframework.workflow.pojo.base.TPBpmLog;
import org.jeecgframework.workflow.pojo.base.TPForm;
import org.jeecgframework.workflow.pojo.base.TPFormpro;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSPrjstatus;
import org.jeecgframework.workflow.pojo.base.TSTable;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.kingtake.solr.SolrOperate;

/**
 * 
 * @ClassName: ActivitiController
 * @Description: (流程引擎处理类)
 * @author jeecg
 * @date 2012-8-19 下午04:20:06
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/activitiController")
public class ActivitiController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	private ActivitiService activitiService;
	protected TaskService taskService;
	protected IdentityService identityService;
	private SystemService systemService;
	private String message;
	private ModelAndView modelAndView = null;
	private HistoryService historyService; 
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private ActivitiDao activitiDao;
	@Autowired
	private TaskJeecgService taskJeecgService;


	@Autowired
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Autowired
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Autowired
	public void setActivitiService(ActivitiService activitiService) {
		this.activitiService = activitiService;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	/**
	 * 流程列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "deploymentList")
	public ModelAndView deploymentList() {
		return new ModelAndView("workflow/deployment/deploymentList");
	}
	//update-begin--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程页面跳转-------------------
	
	/**
	 * 流程列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "deploymentListByProcesskey")
	public ModelAndView deploymentListByProcesskey(HttpServletRequest request) {
	    String processkey=request.getParameter("processkey");
	    if(StringUtils.isEmpty(processkey)){
	    	processkey = "";
	    }
	    request.setAttribute("processkey",processkey);
		return new ModelAndView("workflow/deployment/deploymentInProcesskeyList");
	}
	//update-end--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程页面跳转-------------------
	/**
	 * (流程定义列表 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "processDeploymentGrid")
	public void processDeploymentGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List<Object[]> objects = new ArrayList<Object[]>();
		List<ProcessDefinition> processDefinitionList  = activitiService.processDefinitionList();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			String deploymentId = processDefinition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
			objects.add(new Object[] { processDefinition, deployment });
		}
		dataGrid.setTotal(processDefinitionList.size());
		dataGrid.setResults(processDefinitionList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	//update-begin--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------
	/**
	 * (流程定义列表 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署)
	 * 获取发布的流程
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "processDeploymentInProcesskeyGrid")
	public void processDeploymentInProcesskeyGrid(@RequestParam("processkey") String processkey,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List<Object[]> objects = new ArrayList<Object[]>();
		List<ProcessDefinition> processDefinitionList = new ArrayList<ProcessDefinition>();
		if(StringUtils.isNotEmpty(processkey)){
			//根据流程id获取该流程发布的流程
			processDefinitionList = activitiService.processDefinitionListByProcesskey(processkey);
			for (ProcessDefinition processDefinition : processDefinitionList) {
				String deploymentId = processDefinition.getDeploymentId();
				Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
				objects.add(new Object[] { processDefinition, deployment });
			}
		}
		dataGrid.setTotal(processDefinitionList.size());
		dataGrid.setResults(processDefinitionList);
		TagUtil.datagrid(response, dataGrid);
	}
	//update-end--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------
	
	/**
	 * 流程部署页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "openDeployProcess")
	public ModelAndView openDeployProcess() {
		return new ModelAndView("workflow/deployment/deploypro");
	}

	@RequestMapping(params = "deployProcess", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson deployProcess(HttpServletRequest request) {
		UploadFile uploadFile = new UploadFile(request);
		AjaxJson j = new AjaxJson();
		Map<String, MultipartFile> fileMap = uploadFile.getMultipartRequest().getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			String fileName = file.getOriginalFilename();
			try {
				InputStream fileInputStream = file.getInputStream();
				String extension = FilenameUtils.getExtension(fileName);
				if (extension.equals("zip") || extension.equals("bar")) {
					ZipInputStream zip = new ZipInputStream(fileInputStream);
					repositoryService.createDeployment().addZipInputStream(zip).deploy();
				} else if (extension.equals("png")) {
					repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
				} else if (extension.indexOf("bpmn20.xml") != -1) {
					repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
				} else if (extension.equals("bpmn")) {
					/*
					 * bpmn扩展名特殊处理，转换为bpmn20.xml
					 */
					String baseName = FilenameUtils.getBaseName(fileName);
					repositoryService.createDeployment().addInputStream(baseName + ".bpmn20.xml", fileInputStream).deploy();
				} else {
					throw new ActivitiException("错误信息:不支改文件类型" + extension);
				}
			} catch (Exception e) {
				logger.error("错误信息:在部署过程中,文件输入流异常" + e.toString());
			}
		}

		return j;
	}

	/**
	 * 提交业务，启动流程
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "startBusProcess")
	@ResponseBody
	public AjaxJson startBusProcess(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));
			String busconfigId = oConvertUtils.getString(request.getParameter("busconfigKey"));
			String busTableId = oConvertUtils.getString(request.getParameter("busTableId"));
			 
			TSBusConfig tsBusbase = systemService.getEntity(TSBusConfig.class, busconfigId);
			if (tsBusbase != null) {
				Class entityClass = MyClassLoader.getClassByScn(tsBusbase.getTSTable().getEntityName());
				Object objbus = systemService.getEntity(entityClass, businessKey);
				TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", "doing");
				ReflectHelper reflectHelper = new ReflectHelper(objbus);
				TSPrjstatus busPrjstatus = (TSPrjstatus) reflectHelper.getMethodValue("TSPrjstatus");
				String objbusstate = busPrjstatus.getCode();
				if (!prjstatus.getCode().equals(objbusstate)) {
					Map<String, Object> variables = new HashMap<String, Object>();
					//-----------update-begin-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
					variables.put(WorkFlowGlobals.BPM_DATA_ID, businessKey);
		            variables.put(WorkFlowGlobals.BPM_FORM_KEY, busTableId);
		            variables.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, "cgFormBuildController.do?ftlForm&tableName=" + busTableId + "&mode=read&load=detail&id=" + businessKey);
		            //-----------update-end-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
					activitiService.startWorkflow(objbus, businessKey, variables, tsBusbase);
					reflectHelper.setMethodValue("TSPrjstatus", prjstatus);
					systemService.saveOrUpdate(objbus);
					message = "提交成功,已进入办理流程";
				} else {
					message = "已提交,正在办理中";
				}
			} else {
				if (busconfigId.equals("undefined")) {
					message = "busconfigKey参数未设置,请在业务列表设置参数";
				} else {
					message = "流程未关联,请在流程配置中配置业务";
				}

			}
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				message = "没有部署流程!,请在流程配置中部署流程";

			} else {
				message = "启动流程失败!,内部错误";

			}
		} catch (Exception e) {
			message = "启动流程失败!,内部错误";
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 提交业务，启动流程[onlinecoding模块]
	 * configId : 表名
	 * id : 业务数据ID
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "startOnlineProcess")
	@ResponseBody
	public AjaxJson startOnlineProcess(String configId,String id,HttpServletRequest request) {
		message = "启动流程,成功!";
		TSBusConfig tsBusbase =  systemService.findUniqueByProperty(TSBusConfig.class, "onlineId",configId);
		
		//获取业务数据，并且加载到流程变量中
		Map<String,Object> dataForm = dataBaseService.findOneForJdbc(configId, id);
		//-----------update-begin-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
		dataForm.put(WorkFlowGlobals.BPM_DATA_ID, id);
		//-----------update-end-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
		
		//--------------------------------------------------------------------------------------------------------------------
		//update-begin--Author:zhangdaihao  Date:20150713 for：判断流程发起节点有没有个性化设置页面
		//获取发起流程配置的表单地址
		String BPM_FORM_CONTENT_URL  = activitiDao.getProcessStartNodeView(tsBusbase.getTPProcess().getId());
		if(oConvertUtils.isEmpty(BPM_FORM_CONTENT_URL)){
			BPM_FORM_CONTENT_URL = "cgFormBuildController.do?ftlForm&tableName="+configId+"&mode=read&load=detail&id="+id;
		}
		dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, BPM_FORM_CONTENT_URL);
		//update-end--Author:zhangdaihao  Date:20150713 for：判断流程发起节点有没有个性化设置页面
		//--------------------------------------------------------------------------------------------------------------------
		
		
		dataForm.put(WorkFlowGlobals.BPM_FORM_KEY, configId);
		String create_by = dataForm.get(DataBaseConstant.CREATE_BY_DB).toString();//业务数据创建人
		String data_id = id;//online数据ID
		AjaxJson j = new AjaxJson();
		try {
			//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理
//			//获取onlinecoding表名
//			//1.加载出onlinecoding的表单数据（单表或主表）
//			//2.制定表单访问变量content_url,默认等于表单的查看页面
//			//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
//			activitiService.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
//			//4.修改onlinecoding表单的BPM业务流程状态
//			dataForm.put(WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.BPM_BUS_STATUS_2);
//			dataBaseService.updateTable(configId, id, dataForm);
//			//----------------------------------------------------------------
//			//5.往原来的流程设计里面设置数据
//			Map mp = new HashMap();
//			mp.put("id", id);
//			mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
//			mp.put("prjstateid", 2);
//			mp.put("busconfigid", tsBusbase.getId());
//			activitiDao.insert(mp);
			//----------------------------------------------------------------
			activitiService.startOnlineProcess(create_by, data_id, dataForm, tsBusbase);
			//update-end--Author:zhoujf  Date:20150804 for：启动流程事物处理
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				message = "没有部署流程!,请在流程配置中部署流程!";
			} else {
				message = "启动流程失败!请确认流程和表单是否关联!";
			}
		} catch (Exception e) {
			message = "启动流程失败!,请确认流程和表单是否关联!";
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 提交业务，启动流程[自定义开发页面]
	 * @param  tableName : 表名
	 * @param   id 		 : 业务数据ID
	 * @param   formUrl  : 表单URL
	 * @return
	 */
	@RequestMapping(params = "startUserDefinedProcess")
	@ResponseBody
	public AjaxJson startUserDefinedProcess(String tableName,String id,String formUrl,HttpServletRequest request) {
		message = "流程发起成功!";
		//获取业务数据，并且加载到流程变量中
		Map<String,Object> dataForm = dataBaseService.findOneForJdbc(tableName, id);
		
		//-----------update-begin-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
		dataForm.put(WorkFlowGlobals.BPM_DATA_ID, id);
		//-----------update-end-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
		dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, formUrl+"&id="+id);
		dataForm.put(WorkFlowGlobals.BPM_FORM_KEY, tableName);
		String create_by = dataForm.get(DataBaseConstant.CREATE_BY_DB).toString();//业务数据创建人
		String data_id = id;//online数据ID
		TSBusConfig tsBusbase =  systemService.findUniqueByProperty(TSBusConfig.class, "onlineId",tableName);
		AjaxJson j = new AjaxJson();
		try {
			//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理
//			//获取onlinecoding表名
//			//1.加载出onlinecoding的表单数据（单表或主表）
//			//2.制定表单访问变量content_url,默认等于表单的查看页面
//			//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
//			activitiService.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
//			//4.修改onlinecoding表单的BPM业务流程状态
//			String update_status_sql = "update "+tableName+" set bpm_status = " + WorkFlowGlobals.BPM_BUS_STATUS_2 +" where id="+"'"+id+"'";
//			systemService.executeSql(update_status_sql);
//			//----------------------------------------------------------------
//			//5.往原来的流程设计里面设置数据
//			Map<String,Object> mp = new HashMap<String,Object>();
//			//业务数据ID
//			mp.put("id", id);
//			//创建人
//			mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
//			//办理中
//			mp.put("prjstateid", 2);
//			//业务配置ID
//			mp.put("busconfigid", tsBusbase.getId());
//			activitiDao.insert(mp);
			//----------------------------------------------------------------
			activitiService.startUserDefinedProcess(create_by, data_id, dataForm, tsBusbase);
			//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				message = "没有部署流程!,请在流程配置中部署流程!";
			} else {
				message = "启动流程失败!请确认流程和表单是否关联!";
			}
		} catch (Exception e) {
			message = "启动流程失败!,请确认流程和表单是否关联!";
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	
    /**
     * 自定义启动流程方法
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "startProcess")
    @ResponseBody
    public AjaxJson startProcess(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            message = this.activitiService.startProcess(request);
        } catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                message = "没有部署流程!,请在流程配置中部署流程";
            } else {
                message = "启动流程失败!,内部错误:" + e.getMessage();
            }
        } catch (Exception e) {
            message = "启动流程失败!,内部错误";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }
	

	/**
	 * 签收任务
	 */
	@RequestMapping(params = "claim")
	@ResponseBody
	public AjaxJson claim(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String userId = ResourceUtil.getSessionUserName().getUserName().toString();
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		taskService.claim(taskId, userId);
		j.setMsg("签收完成");
		return j;
	}

	/**
	 * 委托别人办理
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goEntrust")
	public ModelAndView goEntrust(HttpServletRequest request) {
		return new ModelAndView("business/demobus/entruster");
	}
	
	/**
	 * 跳转委托人页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goEntrustAdd")
	public ModelAndView goEntrustAdd(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		return new ModelAndView("business/demobus/entruster-add");
	}
	
	/**
	 * 委托人列表填充
	 * @param actIdUser
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridEntruster")
	public void datagridEntruster(ActIdUser actIdUser, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ActIdUser.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, actIdUser, request.getParameterMap());
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 选择委托人
	 * 
	 */
	@RequestMapping(params = "doEntrust")
	@ResponseBody
	public AjaxJson doEntrust(ActIdUser actIdUser, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "委托成功!";
		String taskId = request.getParameter("taskId");
		try{
			ActRuTask ruTask = this.systemService.get(ActRuTask.class, taskId);
			ruTask.setAssignee(actIdUser.getId());
			this.systemService.saveOrUpdate(ruTask);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "委托失败!";
			throw new BusinessException(e.getMessage());
		}
		
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 流程图显示
	 */
	@RequestMapping(params = "openProcessPic")
	public ModelAndView openProcessPic(HttpServletRequest request) {
		String tag = oConvertUtils.getString(request.getParameter("tag"));
		if (tag.equals("task")) {
			String taskId = oConvertUtils.getString(request.getParameter("taskId"));// taskID
			Task task = activitiService.getTask(taskId);// 引擎任务对象
			String processInstanceId = task.getProcessInstanceId();
			ActivityImpl activityImpl = activitiService.getProcessMap(processInstanceId);
			request.setAttribute("activityImpl", activityImpl);
			request.setAttribute("processInstanceId", processInstanceId);

		} else if (tag.equals("deployment")) {
			request.setAttribute("resourceName", oConvertUtils.getString(request.getParameter("resourceName")));
			request.setAttribute("deploymentId", oConvertUtils.getString(request.getParameter("deploymentId")));
		} else if (tag.equals("project")) {
			String businessKey = oConvertUtils.getString(request.getParameter("businessKey"));// businessKey
			HistoricProcessInstance historicProcessInstance = activitiService.getHiProcInstByBusKey(businessKey);
			String processInstanceId = historicProcessInstance.getId();
			ActivityImpl activityImpl = activitiService.getProcessMap(processInstanceId);
			request.setAttribute("activityImpl", activityImpl);
			request.setAttribute("processInstanceId", processInstanceId);
		}
		request.setAttribute("tag", tag);
		return new ModelAndView("workflow/process/processPic");
	}

	/**
	 * 读取资源，通过流程ID
	 * 
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @param processInstanceId
	 *            流程实例ID
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "processPic")
	public void processPic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String processInstanceId = oConvertUtils.getString(request.getParameter("processInstanceId"));
		Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd( processInstanceId);
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine(); 
        InputStream is = processEngine.getManagementService().executeCommand(cmd);
        
        int len = 0;
        byte[] b = new byte[1024];

        while ((len = is.read(b, 0, 1024)) != -1) {
        	response.getOutputStream().write(b, 0, len);
        }
	}

	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param deploymentId
	 *            流程部署的ID
	 * @param resourceName
	 *            资源名称(foo.xml|foo.png)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "processPicByDeploy")
	public void processPicByDeploy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deploymentId = oConvertUtils.getString(request.getParameter("deploymentId"));
		String resourceName = oConvertUtils.getString(request.getParameter("resourceName"));
		InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	
	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param deploymentId
	 *            流程部署的ID
	 * @param resourceName
	 *            资源名称(foo.xml|foo.png)
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "processBpmXmlByDeploy")
	public void processBpmXmlByDeploy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deploymentId = oConvertUtils.getString(request.getParameter("deploymentId"));
		String resourceName = oConvertUtils.getString(request.getParameter("resourceName"));
		InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
		// 文件下载
		File file = new File(resourceName);
		String filename = file.getName();
		// 输出生成的zip文件
		// 清空response
		response.reset();
		// 设置response的Header
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Length", "" + file.length());
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename="
				+ new String(filename.getBytes("utf-8"), "ISO8859-1"));
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		while ((bytesRead = resourceAsStream.read(buffer, 0, 1024)) != -1) {
			toClient.write(buffer, 0, bytesRead);
		}
		//toClient.write(buffer);
		toClient.flush();
		toClient.close();
	}

	/**
	 * 待办任务列表
	 */

	@RequestMapping(params = "taskList")
	public void taskList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		String buscode = oConvertUtils.getString(request.getParameter("busCode"));
		TSTable table = systemService.findUniqueByProperty(TSTable.class, "entityKey", buscode);
		if (table != null) {
			List<TSBusConfig> tsBusConfigs = systemService.findByProperty(TSBusConfig.class, "TSTable.id", table.getId());
			List taskList = activitiService.findTodoTasks(user.getUserName(), tsBusConfigs);
			dataGrid.setTotal(taskList.size());
			dataGrid.setResults(taskList);
			TagUtil.datagrid(response, dataGrid);
		}

	}

	/**
	 * 打开流程办理页面[老式处理]
	 */
	@RequestMapping(params = "openProcessHandle")
	public ModelAndView openProcessHandle(HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));// taskID
		ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
		//TODO 待修改
		//节点下一步走向
		List<PvmTransition> pvmTransitionList = activitiService.getOutgoingTransitions(taskId);
		request.setAttribute("PvmTransition", pvmTransitionList);
		if (processHandle.getTpForm() != null) {
			TPForm tForm = processHandle.getTpForm(); // 表单对象
			// 根据表单id查询出表单的所有参数
			List<TPFormpro> formpros = tForm.getTPFormpros();
			request.setAttribute("formpros", formpros);
			request.setAttribute("action", tForm.getFormaction());
			modelAndView = new ModelAndView("workflow/processHandle/dynamicHandle");
		} else {
			String modelandview = oConvertUtils.getString(processHandle.getTpProcessnode().getModelandview(), "activitiController.do?processHandle");
			modelAndView = new ModelAndView(new RedirectView(modelandview), "taskId", taskId);
		}
		return modelAndView;
	}
	
	
	/**
	 * 打开流程办理页面
	 * [新的处理方式-TAB]
	 */
	@RequestMapping(params = "openNewProcessHandle")
	public ModelAndView openNewProcessHandle(HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));// taskID
		//TODO 待修改
		//节点下一步走向
		List<PvmTransition> pvmTransitionList = activitiService.getOutgoingTransitions(taskId);
		request.setAttribute("PvmTransition", pvmTransitionList);
		
		//update-begin--Author:zhangdaihao  Date:20140826 for：任务处理页面--------------------
//		if (processHandle.getTpForm() != null) {
//			TPForm tForm = processHandle.getTpForm(); // 表单对象
//			// 根据表单id查询出表单的所有参数
//			List<TPFormpro> formpros = tForm.getTPFormpros();
//			request.setAttribute("formpros", formpros);
//			request.setAttribute("action", tForm.getFormaction());
//			modelAndView = new ModelAndView("workflow/processHandle/dynamicHandle");
//		} else {
//			String modelandview = oConvertUtils.getString(processHandle.getTpProcessnode().getModelandview(), "activitiController.do?processHandle");
//			modelAndView = new ModelAndView(new RedirectView(modelandview), "taskId", taskId);
//		}
		modelAndView = new ModelAndView(new RedirectView("activitiController.do?processHandle"), "taskId", taskId);
		//update-end--Author:zhangdaihao  Date:20140826 for：任务处理页面----------------------
		return modelAndView;
	}

	/**
	 * 通用审批页面跳转
	 */
	@RequestMapping(params = "processHandle")
	public ModelAndView processHandle(HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		request.setAttribute("taskId", taskId);
		return new ModelAndView("workflow/processhandle/processHandle");
	}

	    /**
     * 任务处理[我的任务页面]
     * 
     * @param request
     * @param var
     *            流程变量Map
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "processComplete")
    @ResponseBody
    public AjaxJson processComplete(HttpServletRequest request, Variable var) throws Exception {
        AjaxJson j = new AjaxJson();
        String taskId = oConvertUtils.getString(request.getParameter("taskId"));
        //下一节点名称
        String nextnode = oConvertUtils.getString(request.getParameter("nextnode"));

        ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
        //模式类型（单/多分支模式/驳回模式）                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
        //下一步操作人
        String nextUser = oConvertUtils.getString(request.getParameter("nextuser"));
        //驳回时选择驳回到哪一节点
        //流程变量参数
        Map<String, Object> map = var.getVariableMap(processHandle.getTpProcesspros());
        boolean bflag = taskJeecgService.checkUserTaskIsHuiQian(taskId, nextnode);
        //判断如果下一步节点是会签节点的话，传入会签参数
        if (bflag) {
            if (oConvertUtils.isEmpty(nextUser)) {
                j.setSuccess(false);
                j.setMsg("提交失败，下一任务是会签节点，请选择会签人员");
                return j;
            }
            map.put(WorkFlowGlobals.ASSIGNEE_USER_ID_LIST, nextUser);
        }
        try {
            this.activitiService.processComplete(request, var, j);
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

	/**
	 * 删除部署的流程，级联删除流程实例
	 * 
	 * @param deploymentId
	 */
	@RequestMapping(params = "deleteProcess")
	@ResponseBody
	public AjaxJson deleteProcess(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String deploymentId = oConvertUtils.getString(request.getParameter("deploymentId"));
		String key = oConvertUtils.getString(request.getParameter("key"));// 流程唯一标示
		TPProcess process = systemService.findUniqueByProperty(TPProcess.class, "processkey", key);
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).processDefinitionKey(key).singleResult();
		List<ActHiProcinst> actHiProcinsts = systemService.findByProperty(ActHiProcinst.class, "procDefId", processDefinition.getId());
        if (actHiProcinsts == null || actHiProcinsts.size() <= 0) {
			repositoryService.deleteDeployment(deploymentId, true);
			process.setProcessstate(WorkFlowGlobals.Process_Deploy_NO);
			systemService.updateEntitie(process);// 还原流程为未发布状态
			message = "流程删除成功";
		} else {
			message = "流程跟业务已关联无法删除";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 挂起激活流程
	 */
	@RequestMapping(params = "setProcessState")
	@ResponseBody
	public AjaxJson setProcessState(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String state = oConvertUtils.getString(request.getParameter("state"));
		String processDefinitionId = oConvertUtils.getString(request.getParameter("processDefinitionId"));
		if (state.equals("active")) {
			repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
			message = "流程已激活";
		} else {
			repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
			message = "流程已挂起";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 获得引擎用户列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getUsers")
	@ResponseBody
	public void getUsers(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(User.class, dataGrid);
		List<User> userList = identityService.createUserQuery().list();
		dataGrid.setTotal(userList.size());
		dataGrid.setResults(userList);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 获得引擎角色列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "getGroups")
	@ResponseBody
	public void getGroups(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List<Group> groupList = identityService.createGroupQuery().list();
		dataGrid.setTotal(groupList.size());
		dataGrid.setResults(groupList);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 运行中的流程实例页面跳转
	 */
	@RequestMapping(params = "taskRunningList")
	public ModelAndView taskRunningList(HttpServletRequest request) {
		return new ModelAndView("business/demobus/taskRunningList");
	}
	
	/**
	 * 运行中的流程实例列表
	 */
	@RequestMapping(params = "taskRunningGrid")
	public void taskRunningGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		int allCounts = (int)runtimeService.createProcessInstanceQuery().count();
		int pageSize = dataGrid.getRows();// 每页显示数
		int curPageNO = PagerUtil.getcurPageNo(allCounts, dataGrid.getPage(),pageSize);// 当前页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().listPage(offset, pageSize);
		dataGrid.setResults(list);
		dataGrid.setTotal(list.size());
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 结束的流程实例页面跳转
	 */
	@RequestMapping(params = "taskFinishedList")
	public ModelAndView taskFinishedList(HttpServletRequest request) {
		return new ModelAndView("business/demobus/taskFinishedList");
	}
	
	/**
	 * 运行中的流程实例列表
	 */
	@RequestMapping(params = "taskFinishedGrid")
	public void taskFinishedGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		int allCounts = (int)historyService.createHistoricProcessInstanceQuery().count();
		int pageSize = dataGrid.getRows();// 每页显示数
		int curPageNO = PagerUtil.getcurPageNo(allCounts, dataGrid.getPage(),pageSize);// 当前页
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().finished().listPage(offset, pageSize);
		dataGrid.setResults(list);
		dataGrid.setTotal(allCounts);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 读取带跟踪的流程图片
	 * @throws Exception
	 */
	@RequestMapping(params = "traceImage")
    public void traceImage(@RequestParam("processInstanceId") String processInstanceId,
    		HttpServletResponse response) throws Exception {
    	
		Command<InputStream> cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine(); 
        InputStream is = processEngine.getManagementService().executeCommand(cmd);
        
        int len = 0;
        byte[] b = new byte[1024];

        while ((len = is.read(b, 0, 1024)) != -1) {
        	response.getOutputStream().write(b, 0, len);
        }
    }
	
	/**
	 * easyui 流程历史页面
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "viewProcessInstanceHistory")
	public ModelAndView viewProcessInstanceHistory(@RequestParam("processInstanceId") String processInstanceId,
			HttpServletRequest request, HttpServletResponse respone,Model model) {
		model.addAttribute("processInstanceId", processInstanceId);
		List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
		model.addAttribute(historicTasks);
		request.setAttribute("historicTasks", historicTasks);
		Command cmd = new HistoryProcessInstanceDiagramPositionCmd(processInstanceId);

		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		List is = (List) processEngine.getManagementService().executeCommand(cmd);
		request.setAttribute("listIs", is);
		String businessCode = request.getParameter("businessCode");
        if (StringUtils.isNotEmpty(businessCode)) {
        	model.addAttribute("businessCode", businessCode);
        }
		return new ModelAndView("workflow/activiti/viewProcessInstanceHistory");
	}
	
	/**
	 * easyui 流程历史数据获取
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "taskHistoryList")
	public void taskHistoryList(@RequestParam("processInstanceId") String processInstanceId,
			HttpServletRequest request, HttpServletResponse response,DataGrid dataGrid) {
		List<Map<String,Object>> startAndEndlist = activitiDao.getActHiActinst(processInstanceId);
        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc().list();
        String start_user_id = activitiDao.getProcessStartUserId(processInstanceId);
      //update-begin--Author:zhoujf  Date:20150715 for：结束节点处理人获取，多数据库兼容处理
        String end_user_id = "";
        List<Map<String,Object>> taskinstList = activitiDao.getProcessEndUserId(processInstanceId,1,1);
        if(taskinstList!=null){
        	Map<String,Object> map = taskinstList.get(0);
        	end_user_id = map.get("ASSIGNEE_")==null?"":(String)map.get("ASSIGNEE_");
        }
      //update-end--Author:zhoujf  Date:20150715 for：结束节点处理人获取，多数据库兼容处理
    	StringBuffer rows = new StringBuffer();
        
        //开启节点
        for (Map<String, Object> m : startAndEndlist) {
            if ("startEvent".equals(m.get("ACT_TYPE_"))) {
                rows.append("{'name':'" + m.get("act_name_") + "','processInstanceId':'" + m.get("EXECUTION_ID_")
                        + "','startTime':'" + covertDateStr(m.get("START_TIME_")) + "','endTime':'"
                        + covertDateStr(m.get("END_TIME_")) + "','assignee':'"
                        + taskJeecgService.getUserRealName(start_user_id) + "','deleteReason':'"
                        + StringUtils.trimToEmpty("已完成") + "'},");
            }
        }
        CriteriaQuery cq = new CriteriaQuery(TPBpmLog.class);
        cq.eq("bpm_id", processInstanceId);
        cq.addOrder("op_time", SortDirection.asc);
        cq.add();
        List<TPBpmLog> logList = this.systemService.getListByCriteriaQuery(cq, false);
        for (int i = 0; i < historicTasks.size(); i++) {
            HistoricTaskInstance hi = historicTasks.get(i);
        	String starttime = hi.getStartTime()==null?"":DateFormatUtils.format(hi.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            TPBpmLog log = null;
            if (i < logList.size()) {
                log = logList.get(i);
            }
        	String endtime = hi.getEndTime()==null?"":DateFormatUtils.format(hi.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            String opType = log == null ? "" : log.getOp_type();
			rows.append("{'name':'"+hi.getName()+"','processInstanceId':'"+hi.getProcessInstanceId() +"','startTime':'"+starttime+"','endTime':'"+endtime+"','assignee':'"+ taskJeecgService.getUserRealName(hi.getAssignee()) +"','deleteReason':'"+ ("completed".equals(StringUtils.trimToEmpty(hi.getDeleteReason()))?"已完成":StringUtils.trimToEmpty(hi.getDeleteReason())) +"','opType':'"+opType+"'},");
        	//System.out.println(hi.getName()+"@"+hi.getAssignee()+"@"+hi.getStartTime()+"@"+hi.getEndTime());
        }
        //结束节点
        for(Map<String,Object> m:startAndEndlist){
        	if("endEvent".equals(m.get("ACT_TYPE_"))){
                rows.append("{'name':'结束','processInstanceId':'" + m.get("EXECUTION_ID_")
                        + "','startTime':'" + m.get("START_TIME_") + "','endTime':'" + m.get("END_TIME_")
                        + "','assignee':'" + taskJeecgService.getUserRealName(end_user_id) + "','deleteReason':'"
                        + StringUtils.trimToEmpty("已完成") + "'},");
        	}
        }
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+(historicTasks.size()+startAndEndlist.size())+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
    /**
     * 日期格式转换
     * 
     * @param obj
     * @return
     */
    private String covertDateStr(Object obj) {
        String timeStr = "";
        if (obj != null) {
        Timestamp timestmp = (Timestamp) obj;
        Date time = new Date(timestmp.getTime());
            timeStr = DateUtils.date2Str(time, DateUtils.datetimeFormat);
        }
        return timeStr;
    }

	// -----------------------------------------------------------------------------------
	// 以下各函数可以提成共用部件 (Add by Quainty)
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

	
	/**
	 * easyui 待领任务页面
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "waitingClaimTask")
	public ModelAndView waitingClaimTask() {
		
		return new ModelAndView("workflow/activiti/waitingClaimTask");
	}
	
	/**
	 * easyui AJAX请求数据 待领任务
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "waitingClaimTaskDataGrid")
	public void waitingClaimTaskDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String userId = ResourceUtil.getSessionUserName().getUserName();
		TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userId).active().list();//.taskCandidateGroup("hr").active().list();
		
		StringBuffer rows = new StringBuffer();
		for(Task t : tasks){
			rows.append("{'name':'"+t.getName() +"','taskId':'"+t.getId()+"','processDefinitionId':'"+t.getProcessDefinitionId()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+tasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * easyui 待办任务页面
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "claimedTask")
	public ModelAndView claimedTask() {
		
		return new ModelAndView("workflow/activiti/claimedTask");
	}
	
	/**
	 * easyui AJAX请求数据 待办任务
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "claimedTaskDataGrid")
	public void claimedTaskDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String userId = ResourceUtil.getSessionUserName().getUserName();
		TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		
		StringBuffer rows = new StringBuffer();
		for(Task t : tasks){
			rows.append("{'name':'"+t.getName() +"','description':'"+t.getDescription()+"','taskId':'"+t.getId()+"','processDefinitionId':'"+t.getProcessDefinitionId()+"','processInstanceId':'"+t.getProcessInstanceId()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+tasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * easyui 已办任务页面
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "finishedTask")
	public ModelAndView finishedTask() {
		return new ModelAndView("workflow/activiti/finishedTask");
	}
	
	/**
	 * easyui AJAX请求数据 已办任务
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "finishedTaskDataGrid")
	public void finishedTask(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String userId = ResourceUtil.getSessionUserName().getUserName();
		List<HistoricTaskInstance> historicTasks = historyService
                .createHistoricTaskInstanceQuery().taskAssignee(userId)
                .finished().list();
		
		StringBuffer rows = new StringBuffer();
		for(HistoricTaskInstance t : historicTasks){
			rows.append("{'name':'"+t.getName() +"','description':'"+t.getDescription()+"','taskId':'"+t.getId()+"','processDefinitionId':'"+t.getProcessDefinitionId()+"','processInstanceId':'"+t.getProcessInstanceId()+"'},");
		}
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+historicTasks.size()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}

	/**
     * 签收任务
     * @param taskId
     */
	@RequestMapping(params = "claimTask")
	@ResponseBody
	public AjaxJson claimTask(@RequestParam("taskId") String taskId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		String userId = ResourceUtil.getSessionUserName().getUserName();
		
		TaskService taskService = processEngine.getTaskService();
        taskService.claim(taskId, userId);
		
		String message = "签收成功";
		j.setMsg(message);
		return j;
	}
	
	/**
	 *流程附件上传
	 * @param request
	 * @param response
	 * @param financeFile
	 * @return
	 */
	@RequestMapping(params = "saveBpmFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveBpmFiles(HttpServletRequest request, HttpServletResponse response, TPBpmFile tpbpmFile) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> attributes = new HashMap<String, Object>();
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		String bpmLogId = oConvertUtils.getString(request.getParameter("bpmlogId"));//资金ID
		if (oConvertUtils.isNotEmpty(fileKey)) {
			tpbpmFile.setId(fileKey);
			tpbpmFile = systemService.getEntity(TPBpmFile.class, fileKey);
		}
		TPBpmLog bpmlog = systemService.getEntity(TPBpmLog.class, bpmLogId);
		tpbpmFile.setBpmlog(bpmlog);
        tpbpmFile.setCreatedate(DateUtils.gettimestamp());
		tpbpmFile.setFile_name("");
		tpbpmFile.setPath("");
		UploadFile uploadFile = new UploadFile(request, tpbpmFile);
		uploadFile.setCusPath("files");
		uploadFile.setSwfpath("swfpath");
		uploadFile.setByteField(null);//不存二进制内容
		tpbpmFile = systemService.uploadFile(uploadFile);
		attributes.put("fileKey", tpbpmFile.getId());
		attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + tpbpmFile.getId());
		attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + tpbpmFile.getId());
		j.setMsg("处理成功");
		j.setAttributes(attributes);

        // 保存索引
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ID", tpbpmFile.getId());
        map.put("TITLE", tpbpmFile.getAttachmenttitle());
        map.put("PATH", tpbpmFile.getRealpath());
        map.put("EXTEND", tpbpmFile.getExtend());
        list.add(map);

        String realPath = new File(request.getRealPath("/")).getParent();
        SolrOperate.addFilesIndex(list, realPath);
		return j;
	}
}
