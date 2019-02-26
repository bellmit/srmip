package org.jeecgframework.workflow.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.Query;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.dao.IActivitiCommonDao;
import org.jeecgframework.workflow.model.activiti.ActivitiCom;
import org.jeecgframework.workflow.model.activiti.Process;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.model.activiti.Variable;
import org.jeecgframework.workflow.model.activiti.WorkflowUtils;
import org.jeecgframework.workflow.pojo.activiti.ActHiProcinst;
import org.jeecgframework.workflow.pojo.activiti.ActHiTaskinst;
import org.jeecgframework.workflow.pojo.base.TPBpmLog;
import org.jeecgframework.workflow.pojo.base.TPForm;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TPProcessnode;
import org.jeecgframework.workflow.pojo.base.TPProcessnodeFunction;
import org.jeecgframework.workflow.pojo.base.TPProcesspro;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSPrjstatus;
import org.jeecgframework.workflow.pojo.base.TSTable;
import org.jeecgframework.workflow.service.ActivitiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.service.message.TOMessageServiceI;

@Service("activitiService")
@Transactional(rollbackFor = Exception.class)
public class ActivitiServiceImpl extends CommonServiceImpl implements ActivitiService {
    private static Logger logger = LoggerFactory.getLogger(ActivitiServiceImpl.class);

    private IdentityService identityService;
    private RuntimeService runtimeService;
    protected TaskService taskService;
    protected HistoryService historyService;
    protected RepositoryService repositoryService;
    protected String hql;
    @Autowired
    private TaskJeecgService taskJeecgService;
    private SystemService systemService;
    private IActivitiCommonDao activitiCommonDao;
    @Autowired
    private ActivitiDao activitiDao;
    @Autowired
    private DataBaseService dataBaseService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private TOMessageServiceI tOMessageService;

    @Autowired
    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Autowired
    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Resource
    public void setActivitiCommonDao(IActivitiCommonDao activitiCommonDao) {
        this.activitiCommonDao = activitiCommonDao;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * synToActiviti 同步用户到Activiti数据库
     */
    public void save(TSUser user, String roleidstr, Short synToActiviti) {
        if (WorkFlowGlobals.Activiti_Deploy_YES.equals(synToActiviti)) {
            String userId = user.getUserName();
            UserQuery userQuery = identityService.createUserQuery();
            List<User> activitiUsers = userQuery.userId(userId).list();
            if (activitiUsers.size() == 1) {
                updateActivitiData(user, roleidstr, activitiUsers.get(0));
            } else if (activitiUsers.size() > 1) {
                String errorMsg = "发现重复用户：id=" + userId;
                logger.error(errorMsg);
                throw new RuntimeException(errorMsg);
            } else {
                newActivitiUser(user, roleidstr);
            }
        }

    }

    private void newActivitiUser(TSUser user, String roleidstr) {
        String userId = user.getUserName();
        addActivitiGroup(roleidstr);
        // 添加用户
        saveActivitiUser(user);
        // 添加membership
        addMembershipToIdentify(roleidstr, userId);

    }

    private void addActivitiGroup(String roleidstr) {
        GroupQuery groupQuery = identityService.createGroupQuery();
        String[] roleIds = roleidstr.split(",");
        for (String string : roleIds) {
            TSRole role = commonDao.getEntity(TSRole.class, string);
            if (role != null) {
                List<Group> activitiGroups = groupQuery.groupId(role.getRoleCode()).list();
                if (activitiGroups.size() <= 0) {
                    saveActivitiGroup(role);
                }
            }

        }
    }

    /**
     * 同步角色到到Activiti数据库组表
     * 
     * @param role
     */
    private void saveActivitiGroup(TSRole role) {
        Group activitiGroup = identityService.newGroup(role.getRoleCode());
        activitiGroup.setId(role.getRoleCode());
        activitiGroup.setName(role.getRoleName());
        identityService.saveGroup(activitiGroup);
    }

    private void saveActivitiUser(TSUser user) {
        String userId = oConvertUtils.getString(user.getUserName());
        User activitiUser = identityService.newUser(userId);
        cloneAndSaveActivitiUser(user, activitiUser);

    }

    private void cloneAndSaveActivitiUser(TSUser user, User activitiUser) {
        activitiUser.setFirstName(user.getRealName());
        activitiUser.setLastName(user.getRealName());
        activitiUser.setPassword(user.getPassword());
        activitiUser.setEmail(user.getEmail());
        identityService.saveUser(activitiUser);

    }

    private void addMembershipToIdentify(String roleidstr, String userId) {
        String[] roleIds = roleidstr.split(",");
        for (String string : roleIds) {
            TSRole role = commonDao.getEntity(TSRole.class, string);
            logger.debug("add role to activit: {}", role);
            if (role != null) {
                identityService.createMembership(userId, role.getRoleCode());
            }
        }
    }

    private void updateActivitiData(TSUser user, String roleidstr, User activitiUser) {
        String[] roleIds = roleidstr.split(",");
        String userId = user.getUserName();
        if (roleIds.length > 0) {
            addActivitiGroup(roleidstr);
        }
        // 更新用户主体信息
        cloneAndSaveActivitiUser(user, activitiUser);
        // 删除用户的membership
        List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group : activitiGroups) {
            logger.debug("delete group from activit: {}", group);
            identityService.deleteMembership(userId, group.getId());
        }
        // 添加membership
        addMembershipToIdentify(roleidstr, userId);

    }

    /**
     * 同步删除用户和用户组
     * 
     * @param userId
     *            用户id
     */
    public void delete(TSUser user) {
        if (user == null) {
            logger.debug("删除用户异常");
        }
        // 同步删除Activiti User
        List<TSRoleUser> roleUserList = findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
        if (roleUserList.size() >= 1) {
            for (TSRoleUser tRoleUser : roleUserList) {
                TSRole role = tRoleUser.getTSRole();
                if (role != null) {
                    identityService.deleteMembership(user.getUserName(), role.getRoleCode());
                }

            }
        }
        // 同步删除Activiti User
        identityService.deleteUser(user.getUserName());
    }

    /**
     * 启动流程
     */
    public ProcessInstance startWorkflow(Object entity, String businessKey, Map<String, Object> variables,
            TSBusConfig tsBusbase) {
        ReflectHelper reflectHelper = new ReflectHelper(entity);
        TSUser tsUser = (TSUser) reflectHelper.getMethodValue("TSUser");// 获取创建人
        identityService.setAuthenticatedUserId(tsUser.getUserName());// 设置流程发起人
        //标准变量
        variables.put(WorkFlowGlobals.BPM_DATA_ID, businessKey);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(tsBusbase.getTPProcess()
                .getProcesskey(), businessKey, variables);
        return processInstance;
    }

    /**
     * 启动流程
     */
    public ProcessInstance startOnlineWorkflow(String create_by, String businessKey, Map<String, Object> variables,
            TSBusConfig tsBusbase) {
        identityService.setAuthenticatedUserId(create_by);// 设置流程发起人
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(tsBusbase.getTPProcess()
                .getProcesskey(), businessKey, variables);
        return processInstance;
    }

    //update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理(online表单流程)
    /**
     * 启动流程(online表单流程)
     * 
     * @param create_by
     * @param businessKey
     * @param variables
     * @param tsBusbase
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void startOnlineProcess(String create_by, String businessKey, Map<String, Object> dataForm,
            TSBusConfig tsBusbase) {
        //获取onlinecoding表名
        //1.加载出onlinecoding的表单数据（单表或主表）
        //2.制定表单访问变量content_url,默认等于表单的查看页面
        //3.通过onlinecoding表名,读取流程表单配置,获取流程实例
        String data_id = businessKey;
        this.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
        //4.修改onlinecoding表单的BPM业务流程状态
        dataForm.put(WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.BPM_BUS_STATUS_2);
        String configId = dataForm.get(WorkFlowGlobals.BPM_FORM_KEY).toString();
        dataBaseService.updateTable(configId, data_id, dataForm);
        //----------------------------------------------------------------
        //5.往原来的流程设计里面设置数据
        Map mp = new HashMap();
        mp.put("id", data_id);
        mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
        mp.put("prjstateid", 2);
        mp.put("busconfigid", tsBusbase.getId());
        mp.put("businessCode", "");
        mp.put("businessName", "");
        activitiDao.insert(mp);
    }

    //update-end--Author:zhoujf  Date:20150804 for：启动流程事物处理(online表单流程)

    //update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理(自定义开发表单流程)
    /**
     * 启动流程(自定义开发表单流程)
     * 
     * @param create_by
     * @param businessKey
     * @param variables
     * @param tsBusbase
     */
    public void startUserDefinedProcess(String create_by, String businessKey, Map<String, Object> dataForm,
            TSBusConfig tsBusbase) {
        //获取onlinecoding表名
        //1.加载出onlinecoding的表单数据（单表或主表）
        //2.制定表单访问变量content_url,默认等于表单的查看页面
        //3.通过onlinecoding表名,读取流程表单配置,获取流程实例
        String data_id = businessKey;
        this.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
        //4.修改onlinecoding表单的BPM业务流程状态
        String tableName = dataForm.get(WorkFlowGlobals.BPM_FORM_KEY).toString();
        String update_status_sql = "update " + tableName + " set bpm_status = " + WorkFlowGlobals.BPM_BUS_STATUS_2
                + " where id=" + "'" + data_id + "'";
        systemService.executeSql(update_status_sql);
        //----------------------------------------------------------------
        //5.往原来的流程设计里面设置数据
        Map<String, Object> mp = new HashMap<String, Object>();
        //业务数据ID
        mp.put("id", data_id);
        //创建人
        mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
        //办理中
        mp.put("prjstateid", 2);
        //业务配置ID
        mp.put("busconfigid", tsBusbase.getId());
        mp.put("businessCode", "");
        mp.put("businessName", "");
        activitiDao.insert(mp);
    }

    //update-end--Author:zhoujf  Date:20150804 for：启动流程事物处理(自定义开发表单流程)

    /**
     * 查询待办任务
     * 
     * @param userId
     *            用户ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List findTodoTasks(String userId, List<TSBusConfig> tsBusConfigs) {
        List results = new ArrayList();
        List<Task> tasks = new ArrayList<Task>();
        Map classMap = new HashMap<String, Object>();
        String busentity = "";
        List<Task> todoList;
        List<Task> unsignedTasks;
        if (tsBusConfigs.size() > 0) {
            for (TSBusConfig busConfig : tsBusConfigs) {
                try {
                    String processKey = busConfig.getTPProcess().getProcesskey();
                    busentity = busConfig.getTSTable().getEntityName();
                    // 根据当前人的ID查询
                    todoList = taskService.createTaskQuery().processDefinitionKey(processKey).taskAssignee(userId)
                            .orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();
                    // 根据当前人未签收的任务
                    unsignedTasks = taskService.createTaskQuery().processDefinitionKey(processKey)
                            .taskCandidateUser(userId).orderByTaskPriority().desc().orderByTaskCreateTime().desc()
                            .list();
                    // 合并
                    //先建立临时集合
                    List<Task> tempList = new ArrayList<Task>(0);
                    tempList.addAll(todoList);
                    tempList.addAll(unsignedTasks);
                    //将实体对应到任务列表
                    for (Task task : tempList) {
                        classMap.put(task.getId(), busentity);
                    }
                    tasks.addAll(tempList);
                } catch (Exception e) {
                    //异常情况继续下一次循环
                    logger.info(e.getMessage());
                    e.printStackTrace();
                }
            }
            // 根据流程的业务ID查询实体并关联
            for (Task task : tasks) {
                String processInstanceId = task.getProcessInstanceId();
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
                // String businessKey = processInstance.getBusinessKey();
                String businessKey = getBusinessKeyByTask(task);
                Class entityClass = MyClassLoader.getClassByScn((String) classMap.get(task.getId()));// 业务类
                Object entityObj = getEntity(entityClass, businessKey);
                if (entityObj != null) {
                    ReflectHelper reflectHelper = new ReflectHelper(entityObj);
                    Process process = (Process) reflectHelper.getMethodValue("Process");
                    process.setTask(task);
                    process.setProcessInstance(processInstance);
                    process.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
                    // reflectHelper.setMethodValue("Process", process);
                    results.add(entityObj);
                } else {
                    return tasks;
                }
            }
        }
        return results;
    }

    /**
     * 查询流程定义对象
     * 
     * @param processDefinitionId
     *            流程定义ID
     * @return
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    /**
     * 获取流程图跟踪信息
     * 
     * @param request
     * @return
     */
    public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
        Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例

        Object property = PropertyUtils.getProperty(execution, "activityId");
        String activityId = "";
        if (property != null) {
            activityId = property.toString();
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点

        List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
        for (ActivityImpl activity : activitiList) {

            boolean currentActiviti = false;
            String id = activity.getId();

            // 当前节点
            if (id.equals(activityId)) {
                currentActiviti = true;
            }

            Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance,
                    currentActiviti);

            activityInfos.add(activityImageInfo);
        }

        return activityInfos;

    }

    /**
     * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
     * 
     * @param activity
     * @param processInstance
     * @param currentActiviti
     * @return
     */
    private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
            boolean currentActiviti) throws Exception {
        Map<String, Object> vars = new HashMap<String, Object>();
        Map<String, Object> activityInfo = new HashMap<String, Object>();
        activityInfo.put("currentActiviti", currentActiviti);
        setPosition(activity, activityInfo);
        setWidthAndHeight(activity, activityInfo);

        Map<String, Object> properties = activity.getProperties();
        vars.put("任务类型", WorkflowUtils.parseToZhType(properties.get("type").toString()));

        ActivityBehavior activityBehavior = activity.getActivityBehavior();
        logger.debug("activityBehavior={}", activityBehavior);
        if (activityBehavior instanceof UserTaskActivityBehavior) {

            Task currentTask = null;

            /*
             * 当前节点的task
             */
            if (currentActiviti) {
                currentTask = getCurrentTaskInfo(processInstance);
            }

            /*
             * 当前任务的分配角色
             */
            UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
            TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
            Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
            if (!candidateGroupIdExpressions.isEmpty()) {

                // 任务的处理角色
                setTaskGroup(vars, candidateGroupIdExpressions);

                // 当前处理人
                if (currentTask != null) {
                    setCurrentTaskAssignee(vars, currentTask);
                }
            }
        }

        vars.put("节点说明", properties.get("documentation"));

        String description = activity.getProcessDefinition().getDescription();
        vars.put("描述", description);

        logger.debug("trace variables: {}", vars);
        activityInfo.put("vars", vars);
        return activityInfo;
    }

    private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
        String roles = "";
        for (Expression expression : candidateGroupIdExpressions) {
            String expressionText = expression.getExpressionText();
            if (expressionText.startsWith("$")) {
                expressionText = expressionText.replace("${insuranceType}", "life");
            }
            String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
            roles += roleName;
        }
        vars.put("任务所属角色", roles);
    }

    /**
     * 设置当前处理人信息
     * 
     * @param vars
     * @param currentTask
     */
    private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
        String assignee = currentTask.getAssignee();
        if (assignee != null) {
            User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
            String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
            vars.put("当前处理人", userInfo);
        }
    }

    /**
     * 获取当前节点信息
     * 
     * @param processInstance
     * @return
     */
    private Task getCurrentTaskInfo(ProcessInstance processInstance) {
        Task currentTask = null;
        try {
            String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
            logger.debug("current activity id: {}", activitiId);

            currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                    .taskDefinitionKey(activitiId).singleResult();
            logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

        } catch (Exception e) {
            logger.error("can not get property activityId from processInstance: {}", processInstance);
        }
        return currentTask;
    }

    /**
     * 设置宽度、高度属性
     * 
     * @param activity
     * @param activityInfo
     */
    private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
        activityInfo.put("width", activity.getWidth());
        activityInfo.put("height", activity.getHeight());
    }

    /**
     * 设置坐标位置
     * 
     * @param activity
     * @param activityInfo
     */
    private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
        activityInfo.put("x", activity.getX());
        activityInfo.put("y", activity.getY());
    }

    /**
     * 获取跟踪信息
     * 
     * @return
     * @throws Exception
     */
    public ActivityImpl getProcessMap(String processInstanceId) {
        Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
        if (execution == null) {
            return null;
        }
        Object property = null;
        try {
            property = PropertyUtils.getProperty(execution, "activityId");
        } catch (IllegalAccessException e) {
            logger.error("反射异常", e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String activityId = "";
        if (property != null) {
            activityId = property.toString();// 当前实例的执行到哪个节点
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
        ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
        String processDefinitionId = pdImpl.getId();// 流程标识
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        ActivityImpl actImpl = null;
        List<String> activitiIds = runtimeService.getActiveActivityIds(execution.getId());
        List<String> a = highLight(processInstanceId);
        List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
        // for(String activitiId : activitiIds){
        for (ActivityImpl activityImpl : activitiList) {
            String id = activityImpl.getId();
            if (id.equals(activityId)) {// 获得执行到那个节点
                actImpl = activityImpl;
                break;
            }
        }
        // }
        return actImpl;
    }

    /**
     * 获取高亮节点
     */
    public List<String> highLight(String processInstanceId) {
        List<String> highLihth = new ArrayList<String>();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        for (Execution execution : executions) {
            ExecutionEntity entity = (ExecutionEntity) runtimeService.createExecutionQuery()
                    .executionId(execution.getId()).singleResult();
            highLihth.add(entity.getActivityId());
        }
        return highLihth;

    }

    /**
     * 获取业务ID
     * 
     * @param taskId
     * @return
     */
    public String oldgetBusinessKeyByTask(Task task) {
        String businessKey = "";
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(task.getId()).singleResult();
        ExecutionEntity executionEntity = (ExecutionEntity) runtimeService.createExecutionQuery()
                .executionId(taskEntity.getExecutionId()).singleResult();
        if (executionEntity.getSuperExecutionId() == null) {
            businessKey = executionEntity.getBusinessKey();
        } else {
            ExecutionEntity executionEntity2 = (ExecutionEntity) runtimeService.createExecutionQuery()
                    .executionId(executionEntity.getSuperExecutionId()).singleResult();
            ExecutionEntity executionEntity3 = (ExecutionEntity) runtimeService.createExecutionQuery()
                    .executionId(executionEntity2.getParentId()).singleResult();
            businessKey = executionEntity3.getBusinessKey();
        }
        return businessKey;
    }

    /**
     * 获取业务ID
     * 
     * @param taskId
     * @return
     */
    public String getBusinessKeyByTask(Task task) {
        String businessKey = "";
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(task.getId()).singleResult();
        HistoricProcessInstance hiproins = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
        if (hiproins != null) {
            if (hiproins.getSuperProcessInstanceId() != null && hiproins.getBusinessKey() == null) {
                hiproins = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(hiproins.getSuperProcessInstanceId()).singleResult();
                businessKey = hiproins.getBusinessKey();
            } else {
                businessKey = hiproins.getBusinessKey();
            }
        }
        return businessKey;
    }

    /**
     * 根据业务ID获取HistoricProcessInstance对象
     * 
     * @param businessKey
     * @return
     */
    public HistoricProcessInstance getHiProcInstByBusKey(String businessKey) {
        HistoricProcessInstance hiproins = null;
        hiproins = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey)
                .singleResult();
        return hiproins;
    }

    /**
     * 根据父业务ID获取HistoricProcessInstance对象
     * 
     * @param businessKey
     * @return
     */
    public HistoricProcessInstance getHiProcInstByParprocInstId(String parprocInstId) {
        return historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(parprocInstId).singleResult();
    }

    /**
     * 根据父流程业务ID更新子流程的业务ID
     * 
     * @param parBusKey
     *            父流程业务ID
     * @param subBusKey
     *            子流程业务ID
     * @return
     */
    public void updateHiProcInstBusKey(String parBusKey, String subBusKey) {
        HistoricProcessInstance parhiproins = getHiProcInstByBusKey(parBusKey);
        HistoricProcessInstance subhiproins = getHiProcInstByParprocInstId(parhiproins.getId());
        if (subhiproins != null) {
            HistoricProcessInstanceEntity historicProcessInstanceEntity = (HistoricProcessInstanceEntity) subhiproins;
            ActHiProcinst actHiProcinst = getEntity(ActHiProcinst.class, historicProcessInstanceEntity.getId());
            actHiProcinst.setBusinessKey(subBusKey);
            updateEntitie(actHiProcinst);
        }

    }

    /**
     * 完成任务
     */
    public ActivitiCom complete(String taskId, Map<String, Object> map) {
        ActivitiCom activitiCom = new ActivitiCom();
        String msg = "";
        Boolean complete = false;
        try {
            //根据taskId获取businessKey
            String businessKey = getBusinessKeyByTask(taskId);

            //update-begin--Author:zhangdaihao  Date:20140825 for：任意节点跳转模式--------------------
            //taskService.complete(taskId, map);
            try {
                //用户手工指定节点
                String USER_SELECT_TASK_NODE = oConvertUtils.getString(map.get(WorkFlowGlobals.USER_SELECT_TASK_NODE));
                taskJeecgService.goProcessTaskNode(taskId, USER_SELECT_TASK_NODE, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //update-end--Author:zhangdaihao  Date:20140825 for：任意节点跳转模式----------------------
            //根据businessKey判断是否流程结束
            List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().finished()
                    .processInstanceBusinessKey(businessKey).list();
            if (list != null && list.size() == 1) {
                // 流程结束 根据id修改t_s_basebus的状态为结束(id即为businessKey)
                commonDao.updateBySqlString("update t_s_basebus set prjstateid = '3' where id ='" + businessKey + "'");
            }
            complete = true;
            msg = "办理成功";
        } catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                msg = "没有部署子流程!,请在流程配置中部署流程";
                complete = false;
                e.printStackTrace();
            } else {
                msg = "启动流程失败!,内部错误";
                complete = false;
                e.printStackTrace();

            }
        } catch (Exception e) {
            msg = "内部错误";
            complete = false;
            e.printStackTrace();
        }
        activitiCom.setComplete(complete);
        activitiCom.setMsg(msg);
        return activitiCom;
    }

    /**
     * 获取业务Id
     */
    public String getBusinessKeyByTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return getBusinessKeyByTask(task);
    }

    /**
     * 根据流程ID和当前流程环节标示获取当前环节对象
     */
    public TPProcessnode getTPProcessnode(String taskDefinitionKey, String processkey) {
        TPProcessnode processnode = null;
        hql = "from TPProcessnode t where t.TPProcess.processkey='" + processkey + "' and t.processnodecode='"
                + taskDefinitionKey + "'";
        List<TPProcessnode> processnodeList = commonDao.findByQueryString(hql);
        if (processnodeList.size() > 0) {
            processnode = processnodeList.get(0);
        }
        return processnode;
    }

    /**
     * 获取全部部署流程
     */
    @Transactional(readOnly = true)
    public List processDefinitionList() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

    //update-begin--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------
    /**
     * 获取流程processkey获取该流程下已经发布的流程
     */
    @Transactional(readOnly = true)
    public List processDefinitionListByProcesskey(String processkey) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionKey(processkey)
                .orderByProcessDefinitionVersion().desc().list();
    }

    //update-end--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------

    /**
     * 根据taskId获取task对象
     * 
     * @param taskId
     * @return
     */
    public Task getTask(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    /**
     * 根据taskId封装ProcessHandle对象
     * 
     * @param taskId
     * @return
     */
    public ProcessHandle getProcessHandle(String taskId) {
        ProcessHandle processHandle = new ProcessHandle();
        Task task = getTask(taskId);// 引擎任务对象
        String businessKey = getBusinessKeyByTask(taskId);// 业务主键
        String processDefinitionKey = getProcessDefinition(task.getProcessDefinitionId()).getKey();
        String taskDefinitionKey = task.getTaskDefinitionKey();// 节点key
        /** 根据流程名称得到流程对象 */
        TPProcess tpProcess = findUniqueByProperty(TPProcess.class, "processkey", processDefinitionKey);
        TPProcessnode tpProcessnode = getTPProcessnode(taskDefinitionKey, processDefinitionKey);
        TPForm tpForm = tpProcessnode.getTPForm(); // 表单对象
        List<TPProcesspro> tpProcesspros = tpProcessnode.getTPProcesspros();// 流程变量
        processHandle.setProcessDefinitionKey(processDefinitionKey);
        processHandle.setTaskDefinitionKey(taskDefinitionKey);
        processHandle.setBusinessKey(businessKey);
        processHandle.setTaskId(taskId);
        processHandle.setTpForm(tpForm);
        processHandle.setTpProcess(tpProcess);
        processHandle.setTpProcessnode(tpProcessnode);
        processHandle.setTpProcesspros(tpProcesspros);
        return processHandle;
    }

    /**
     * 根据taskId,获取根节点Start的信息
     * 
     * @param taskId
     * @return
     */
    public TPProcessnode getProcessStartNode(String taskId) {
        Task task = getTask(taskId);// 引擎任务对象
        String processDefinitionKey = getProcessDefinition(task.getProcessDefinitionId()).getKey();
        /** 根据流程名称得到流程对象 */
        TPProcessnode tpProcessnode = getTPProcessnode("start", processDefinitionKey);
        return tpProcessnode;
    }

    private static SqlSession getSqlSession() {
        ProcessEngineImpl processEngine = null;
        DbSqlSessionFactory dbSqlSessionFactory = (DbSqlSessionFactory) processEngine.getProcessEngineConfiguration()
                .getSessionFactories().get(DbSqlSession.class);
        SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }

    /**
     * 根据角色编码和状态值获取审批状态
     * 
     * @param prjstate
     * @param rolecode
     * @return
     */
    public TSPrjstatus getTBPrjstatusByCode(String prjstate, String rolecode) {
        TSPrjstatus tsPrjstatus = null;
        String[] rolecodes = rolecode.split(",");
        String search = "";
        for (int i = 0; i < rolecodes.length; i++) {
            search += "'" + rolecodes[i] + "'";
            if (i < rolecodes.length - 1) {
                search += ",";
            }

        }
        if (search != "") {
            List<TSPrjstatus> tbPrjstatuList = commonDao.findByQueryString("from TSPrjstatus p where p.code = '"
                    + prjstate + "' and p.executerole in(" + search + ")");
            if (tbPrjstatuList.size() > 0) {
                tsPrjstatus = tbPrjstatuList.get(0);
            }
        }

        return tsPrjstatus;
    }

    /**
     * 根据流程编码和业务类名获取业务配置类
     */
    public TSBusConfig getTSBusConfig(Class classname, String processkey) {
        TSBusConfig tsBusConfig = null;
        String hql = "from TSBusConfig where TSTable.entityName='" + classname.getName()
                + "' and TPProcess.processkey='" + processkey + "'";
        List<TSBusConfig> tsBusConfigList = commonDao.findByQueryString(hql);
        if (tsBusConfigList.size() > 0) {
            tsBusConfig = tsBusConfigList.get(0);
        }
        return tsBusConfig;

    }

    public <T> void batchDelete(Class<T> entityClass) {
        this.activitiCommonDao.batchDelete(entityClass);
    }

    /**
     * 查询待办任务-基础代码
     * 
     * @param isPri
     *            是否只查询用户私有的
     * @param id
     *            标识：username或者rolecode
     * @param tsBusConfigs
     * @return
     */
    private List findBaseTodoTasks(boolean isPri, String id, HttpServletRequest request) {
        List results = new ArrayList();
        List<Task> tasks = new ArrayList<Task>();
        List<Task> todoList;
        List<Task> unsignedTasks;
        //分页参数
        //Integer page = Integer.parseInt(request.getParameter("page"));
        //Integer rows = Integer.parseInt(request.getParameter("rows"));
        //Integer start = (page-1)*rows;
        //Integer end = page*rows-1;
        try {
            //建立临时集合
            List<Task> tempList = new ArrayList<Task>();
            if (isPri) {
                //-update-begin-----------author:zhangdaihao date:20140917 for:待选人模式下任务查询不到---------------------------------------------------
                //TODO 还得修改加分页
                TaskService taskService = processEngine.getTaskService();
                List<Task> todoListtemp = taskService.createTaskQuery().taskCandidateUser(id).orderByTaskCreateTime()
                        .desc().active().list();//.taskCandidateGroup("hr").active().list();
                tempList.addAll(todoListtemp);
                //-update-end-----------author:zhangdaihao date:20140917 for:待选人模式下任务查询不到---------------------------------------------------

                // 根据当前人的ID查询
                /*
                 * 注入查询条件
                 */
                TaskQuery tq = taskService.createTaskQuery().taskAssignee(id).orderByTaskCreateTime().desc()
                        .orderByTaskPriority().desc();
                tq = installQueryParam(tq, request);
                todoList = tq.list();
                tempList.addAll(todoList);

            } else {
                // 根据当前人所在的组查询
                /*
                 * 注入查询条件
                 */
                TaskQuery tq = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(id.split(",")))
                        .orderByTaskCreateTime().desc().orderByTaskPriority().desc();
                tq = installQueryParam(tq, request);
                unsignedTasks = tq.list();
                tempList.addAll(unsignedTasks);
            }
            tasks.addAll(tempList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            // String businessKey = processInstance.getBusinessKey();
            String businessKey = getBusinessKeyByTask(task);
            Class entityClass = MyClassLoader.getClassByScn("org.jeecgframework.workflow.pojo.base.TSBaseBusQuery");// 业务基类
            Object entityObj = getEntity(entityClass, businessKey);
            Object obj = null;
            try {
                obj = entityClass.newInstance();
                MyBeanUtils.copyBeanNotNull2Bean(entityObj, obj);
            } catch (Exception e) {
            }
            if (obj != null) {
                ReflectHelper reflectHelper = new ReflectHelper(obj);
                //				Process process = (Process) reflectHelper.getMethodValue("Process");
                Process process = new Process();
                process.setTask(task);
                process.setProcessInstance(processInstance);
                process.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
                reflectHelper.setMethodValue("Process", process);
                String userid = (String) reflectHelper.getMethodValue("userid");
                if (userid != null) {
                    TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName", userid);
                    if (baseUser != null) {
                        reflectHelper.setMethodValue("userRealName", baseUser.getRealName());
                    }
                }
                String assigneeUserId = task.getAssignee();
                if (assigneeUserId != null) {
                    TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName",
                            assigneeUserId);
                    if (baseUser != null) {
                        reflectHelper.setMethodValue("assigneeName", baseUser.getRealName());
                    }
                }
                results.add(obj);
            }
            //-update-begin-----------author:zhangdaihao date:20150506 for:屏蔽业务关联查询不到的情况，导致我的任务字段不对应---------------------------------------------------
            //			else {
            //				return tasks;
            //			}
            //-update-begin-----------author:zhangdaihao date:20150506 for:屏蔽业务关联查询不到的情况，导致我的任务字段不对应---------------------------------------------------
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    public List findPriTodoTasks(String userId, HttpServletRequest request) {
        return findBaseTodoTasks(true, userId, request);
    }

    public List findGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request) {
        StringBuilder roleIds = new StringBuilder();
        //用户所在的组可能有多个，需要合并
        for (TSRoleUser role : roles) {
            roleIds.append(role.getTSRole().getRoleCode()).append(",");
        }
        roleIds.deleteCharAt(roleIds.length() - 1);
        List resulttemp = findBaseTodoTasks(false, roleIds.toString(), request);
        return resulttemp;
    }

    public List findHistoryTasks(String userName, HttpServletRequest request) {
        //历史任务直接查询activiti的act_hi_taskinst表
        //分页参数
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        Integer start = (page - 1) * rows;
        /*
         * Query query = getSession().createQuery(installQueryParamH(
         * "from ActHiTaskinst o where o.duration>0 and o.assignee = ? order by startTime desc",request));
         * query.setFirstResult(start); query.setMaxResults(rows); query.setString(0, userName);
         * installQueryParamHV(query,request); List result = query.list();
         */
        String sql = "select o.id_ id,o.proc_def_id_ procDefId,d.name_ prodef_name,p.id_  proInsl_procInstId,o.name_ name,p.start_user_id_  proInsl_startUserId,o.assignee_ assignee,o.start_time_ startTime,o.end_time_ endTime,o.duration_ duration,b.business_name "
                + "from act_hi_taskinst o join act_hi_procinst p on o.proc_inst_id_ = p.id_ "
                + "join act_re_procdef d on d.id_ = o.proc_def_id_ join t_s_basebus b on b.id = p.business_key_ "
                + "where o.duration_ > 0 and o.assignee_ = '" + userName + "'";
        sql = sql + " order by o.START_TIME_ desc";
        List<Map<String, Object>> result = this.findForJdbc(sql, page, rows);
        ActHiTaskinst ins = new ActHiTaskinst();
        for (Map<String, Object> tmp : result) {
            if (tmp.containsKey("DURATION")) {
                String durationStr = ins.dealTimeFromNum(Long.valueOf(((BigDecimal) tmp.get("DURATION")).intValue()));
                tmp.put("durationStr", durationStr);
            }
        }
        return result;
    }

    public Long countPriTodaoTask(String userName, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("Process.processDefinition.id");
        String procName = request.getParameter("Process.processDefinition.name");
        Long size = 0L;
        // 根据当前人的ID查询
        TaskQuery tq = taskService.createTaskQuery().taskAssignee(userName).orderByTaskPriority().desc()
                .orderByTaskCreateTime().desc();
        installQueryParam(tq, request);
        size = tq.count();
        return size;
    }

    public Long countGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("Process.processDefinition.id");
        String procName = request.getParameter("Process.processDefinition.name");
        Long size = 0L;
        StringBuilder roleIds = new StringBuilder();
        //用户所在的组可能有多个，需要合并
        for (TSRoleUser role : roles) {
            roleIds.append(role.getTSRole().getRoleCode()).append(",");
        }
        roleIds.deleteCharAt(roleIds.length() - 1);
        // 根据当前组的ID查询
        TaskQuery tq = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(roleIds.toString().split(",")))
                .orderByTaskPriority().desc().orderByTaskCreateTime().desc();
        installQueryParam(tq, request);
        size = tq.count();
        return size;
    }

    public Long countHistoryTasks(String userName, HttpServletRequest request) {
        Map r = systemService
                .findOneForJdbc(
                        installCountH(
                                "select count(1) as hsize  from act_hi_taskinst o inner join act_re_procdef ap on ap.id_ = o.proc_def_id_ and o.duration_>0 and  o.assignee_ = ?",
                                request), installCountHv(userName, request));
        Long size = Long.parseLong(r.get("hsize").toString());
        return size;
    }

    /**
     * 拼装过滤条件
     * 
     * @param tq
     * @param request
     * @return
     */
    private TaskQuery installQueryParam(TaskQuery tq, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("Process.processDefinition.id");
        String procName = request.getParameter("Process.processDefinition.name");
        if (StringUtil.isNotEmpty(procDefId)) {
            tq = tq.processDefinitionId(procDefId);
        }
        if (StringUtil.isNotEmpty(procName)) {
            tq = tq.processDefinitionName(procName);
        }
        return tq;
    }

    /**
     * 拼装过滤条件
     * 
     * @param string
     * @param request
     * @return
     */
    private String installQueryParamH(String sql, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("procDefId");
        String procName = request.getParameter("prodef.name");
        StringBuilder s = new StringBuilder(sql);
        if (StringUtil.isNotEmpty(procDefId)) {
            s.append(" and o.procDefId = ? ");
        }
        if (StringUtil.isNotEmpty(procName)) {
            s.append(" and o.prodef.name = ? ");
        }
        return s.toString();
    }

    private void installQueryParamHV(Query query, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("procDefId");
        String procName = request.getParameter("prodef.name");
        if (StringUtil.isNotEmpty(procDefId)) {
            query.setParameter(1, procDefId);
        }
        if (StringUtil.isNotEmpty(procName)) {
            query.setParameter(2, procName);
        }
    }

    private String installCountH(String sql, HttpServletRequest request) {
        //查询条件
        String procDefId = request.getParameter("procDefId");
        String procName = request.getParameter("prodef.name");
        StringBuilder s = new StringBuilder(sql);
        if (StringUtil.isNotEmpty(procDefId)) {
            s.append(" and o.proc_def_id_ = ? ");
        }
        if (StringUtil.isNotEmpty(procName)) {
            s.append(" and ap.name_ = ? ");
        }
        return s.toString();
    }

    private Object[] installCountHv(String userName, HttpServletRequest request) {
        //查询条件
        List reList = new ArrayList(0);
        reList.add(userName);
        String procDefId = request.getParameter("procDefId");
        String procName = request.getParameter("prodef.name");
        if (StringUtil.isNotEmpty(procDefId)) {
            reList.add(procDefId);
        }
        if (StringUtil.isNotEmpty(procName)) {
            reList.add(procName);
        }
        return reList.toArray();
    }

    /**
     * 通过任务节点ID，获取当前节点出发的路径
     */
    public List<PvmTransition> getOutgoingTransitions(String taskId) {
        List<PvmTransition> outTransitions = null;
        Task task = getTask(taskId);
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(task.getProcessDefinitionId());
        List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例

        String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                .singleResult();
        String activitiId = execution.getActivityId();

        for (ActivityImpl activityImpl : activitiList) {
            String id = activityImpl.getId();
            if (activitiId.equals(id)) {
                System.out.println("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
                outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
                for (PvmTransition tr : outTransitions) {
                    System.out.println("线路名：" + tr.getProperty("name"));
                    PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
                    System.out.println("下一步任务任务：" + ac.getProperty("name"));
                }
                break;
            }
        }
        return outTransitions;
    }

    /**
     * 查询流程下审批意见
     */
    public List findBpmLogsByBpmID(String taskId) {
        hql = "from TPBpmLog t where t.bpm_id='" + taskId + "' order by op_time";
        List<TPBpmLog> logList = commonDao.findByQueryString(hql);
        return logList;
    }

    /**
     * 通过任务节点ID，获取当前节点出发的分支
     */
    public List getOutTransitions(String taskId) {
        List<PvmTransition> outTransitions = null;
        List<Map> trans = new ArrayList();
        Task task = getTask(taskId);
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(task.getProcessDefinitionId());
        List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例

        String excId = task.getExecutionId();
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                .singleResult();
        String activitiId = execution.getActivityId();

        for (ActivityImpl activityImpl : activitiList) {
            String id = activityImpl.getId();
            if (activitiId.equals(id)) {
                outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
                for (PvmTransition tr : outTransitions) {
                    Map m = new HashMap();
                    //获取分支线路的名字，如果名字为空则取线路ID
                    String name = (String) (oConvertUtils.isNotEmpty(tr.getProperty("name")) ? tr.getProperty("name")
                            : tr.getId());
                    m.put("Transition", name);
                    PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
                    m.put("nextnode", ac.getId());
                    trans.add(m);
                }
                break;
            }
        }
        return trans;
    }

    /**
     * 查询流程的历史任务节点
     */
    public List<Map<String, Object>> getHistTaskNodeList(String proceInsId) {
        ActivitiDao activitiDao = ApplicationContextUtil.getContext().getBean(ActivitiDao.class);
        List<Map<String, Object>> list = activitiDao.getHistTaskNodeList(proceInsId);
        return list;
    }

    /**
     * 查询流程的所有节点
     * 
     * @param taskId
     * @return
     */
    public List getAllTaskNode(String taskId) {
        List<Map> list = new ArrayList();
        Task task = getTask(taskId);
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(task.getProcessDefinitionId());
        List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
        for (ActivityImpl activityImpl : activitiList) {
            Map m = new HashMap();
            String id = activityImpl.getId();
            m.put("taskKey", id);
            String name = (String) activityImpl.getProperty("name");
            m.put("name", name);
            list.add(m);
        }
        return list;
    }

    /**
     * 根据流程节点ID 和 菜单Id 获取 具有操作权限的按钮Codes
     * 
     * @param nodeId
     * @param functionId
     * @return
     */
    public Set<String> getNodeOperaCodesByNodeIdAndFunctionId(String nodeId, String functionId) {
        Set<String> operationCodes = new HashSet<String>();
        TPProcessnode proNode = commonDao.get(TPProcessnode.class, nodeId);
        CriteriaQuery cq1 = new CriteriaQuery(TPProcessnodeFunction.class);
        cq1.eq("TPProcessnode.id", proNode.getId());
        cq1.eq("TSFunction.id", functionId);
        cq1.add();
        List<TPProcessnodeFunction> rFunctions = getListByCriteriaQuery(cq1, false);
        if (null != rFunctions && rFunctions.size() > 0) {
            TPProcessnodeFunction tsNodeFunction = rFunctions.get(0);
            if (null != tsNodeFunction.getOperation()) {
                String[] operationArry = tsNodeFunction.getOperation().split(",");
                for (int i = 0; i < operationArry.length; i++) {
                    operationCodes.add(operationArry[i]);
                }
            }
        }
        return operationCodes;
    }

    /**
     * 根据流程节点ID和菜单ID获取 具有操作权限的数据规则
     */
    public Set<String> getOperationCodesByNodeIdAndruleDataId(String nodeId, String functionId) {
        Set<String> operationCodes = new HashSet<String>();
        TPProcessnode proNode = commonDao.get(TPProcessnode.class, nodeId);
        CriteriaQuery cq1 = new CriteriaQuery(TPProcessnodeFunction.class);
        cq1.eq("TPProcessnode.id", proNode.getId());
        cq1.eq("TSFunction.id", functionId);
        cq1.add();
        List<TPProcessnodeFunction> rFunctions = getListByCriteriaQuery(cq1, false);
        if (null != rFunctions && rFunctions.size() > 0) {
            TPProcessnodeFunction tsNodeFunction = rFunctions.get(0);
            if (null != tsNodeFunction.getDataRule()) {
                String[] operationArry = tsNodeFunction.getDataRule().split(",");
                for (int i = 0; i < operationArry.length; i++) {
                    operationCodes.add(operationArry[i]);
                }
            }
        }
        return operationCodes;
    }

    /**
     * 启动流程，使用事务控制。
     * 
     * @param request
     * @return
     */
    @Override
    public String startProcess(HttpServletRequest request) {
        String message = null;
        String id = oConvertUtils.getString(request.getParameter("id"));
        String businessName = oConvertUtils.getString(request.getParameter("businessName"));
        String businessCode = oConvertUtils.getString(request.getParameter("businessCode"));
        String formUrl = oConvertUtils.getString(request.getParameter("formUrl"));
        String tableName = oConvertUtils.getString(request.getParameter("tableName"));
        TSTable tsTable = systemService.findUniqueByProperty(TSTable.class, "tableName", tableName);
        TSBusConfig tsBusbase = systemService.findUniqueByProperty(TSBusConfig.class, "TSTable.id", tsTable.getId());

        if (tsBusbase != null) {
            Class entityClass = MyClassLoader.getClassByScn(tsBusbase.getTSTable().getEntityName());
            Object objbus = systemService.getEntity(entityClass, id);
            ReflectHelper reflectHelper = new ReflectHelper(objbus);
            String bpmStatus = (String) reflectHelper.getMethodValue("bpmStatus");
            String createBy = (String) reflectHelper.getMethodValue("createBy");
            if ("1".equals(bpmStatus)) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put(WorkFlowGlobals.BPM_DATA_ID, id);//表主键
                variables.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, formUrl + "&id=" + id);//表单url
                variables.put(WorkFlowGlobals.BPM_FORM_KEY, tableName);//表名
                variables.put(WorkFlowGlobals.BPM_BUSINESS_NAME, businessName);//业务名称
                this.startOnlineWorkflow(createBy, id, variables, tsBusbase);
                String update_status_sql = "update " + tableName + " set bpm_status = "
                        + WorkFlowGlobals.BPM_BUS_STATUS_2 + " where id=" + "'" + id + "'";
                systemService.executeSql(update_status_sql);
                Map<String, Object> mp = new HashMap<String, Object>();
                mp.put("id", id);
                mp.put("userid", createBy);
                mp.put("businessCode", businessCode);
                mp.put("businessName", businessName);
                mp.put("prjstateid", 2);
                mp.put("busconfigid", tsBusbase.getId());
                activitiDao.insert(mp);
                message = "提交成功,已进入办理流程";
            } else {
                message = "已提交,正在办理中";
            }
        } else {
            message = "流程未关联实体,请在流程配置中配置业务";
        }
        return message;
    }

    /**
     * 完成流程
     * 
     * @param request
     * @param var
     * @param j
     * @return
     */
    @Override
    public AjaxJson processComplete(HttpServletRequest request, Variable var, AjaxJson j) {
        try {
            boolean sendMsgFlag = true;
            String taskId = oConvertUtils.getString(request.getParameter("taskId"));
            //下一节点名称
            String nextnode = oConvertUtils.getString(request.getParameter("nextnode"));
            //下一步节点的数目（小心歧义）
            Integer nextCodeCount = oConvertUtils.getInt(request.getParameter("nextCodeCount"));
            ProcessHandle processHandle = this.getProcessHandle(taskId);
            //模式类型（单/多分支模式/驳回模式）                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
            String model = oConvertUtils.getString(request.getParameter("model"));
            //下一步操作人
            String nextUser = oConvertUtils.getString(request.getParameter("nextuser"));
            //驳回时选择驳回到哪一节点
            String rejectNode = oConvertUtils.getString(request.getParameter("rejectModelNode"));
            //流程变量参数
            Map<String, Object> map = var.getVariableMap(processHandle.getTpProcesspros());
            Task task = this.getTask(taskId);
            String processInstanceId = task.getProcessInstanceId();
            boolean bflag = taskJeecgService.checkUserTaskIsHuiQian(taskId, nextnode);
            //map就是把流程变量，传入流程中
            String option = oConvertUtils.getString(request.getParameter("option"));
            String reason = oConvertUtils.getString(request.getParameter("reason"));
            TaskService taskService = processEngine.getTaskService();
            Map<String, Object> variables = taskService.getVariables(task.getId());
            if ("1".equals(model)) {
                //单分支模式
                if ("end".equals(nextnode)) {
                    taskJeecgService.endProcess(taskId);
                    sendMsgFlag = false;
                    //taskService.complete(taskId, map);
                } else {
                    if (oConvertUtils.isNotEmpty(nextUser)) {
                        map.put(WorkFlowGlobals.ASSIGNEE_USER_ID_LIST, nextUser);
                    }
                    if (nextCodeCount == 1) {
                        taskService.complete(taskId, map);
                    } else {
                        taskJeecgService.goProcessTaskNode(taskId, nextnode, map);
                    }
                    //如果下个节点不是会签节点，动态指派下一步处理人
                    if (!bflag) {
                        //会签情况下，该SQL会报错
                        String nextTskId = taskJeecgService.getTaskIdByProins(processInstanceId, nextnode);
                        if (oConvertUtils.isNotEmpty(nextUser)) {
                            map.put(WorkFlowGlobals.ASSIGNEE_USER_ID_LIST, nextUser);
                            if (nextUser.indexOf(",") < 0) {
                                //指定单个执行人
                                taskService.setAssignee(nextTskId, nextUser);
                            } else {
                                //指定多人
                                taskService.setAssignee(nextTskId, "");
                                taskService.addCandidateUser(nextTskId, nextUser);
                            }
                        }
                    }
                }
            } else if ("2".equals(model)) {
                //多分支模式
                taskService.complete(taskId, map);
            } else {
                //驳回模式
                taskJeecgService.goProcessTaskNode(taskId, rejectNode, map);
                String nextTskId = taskJeecgService.getTaskIdByProins(processInstanceId, rejectNode);
                if (oConvertUtils.isNotEmpty(nextUser)) {
                    map.put(WorkFlowGlobals.ASSIGNEE_USER_ID_LIST, nextUser);
                    if (nextUser.indexOf(",") < 0) {
                        //指定单个执行人
                        taskService.setAssignee(nextTskId, nextUser);
                    } else {
                        //指定多人
                        taskService.setAssignee(nextTskId, "");
                        taskService.addCandidateUser(nextTskId, nextUser);
                    }
                }
            }

            //每个task节点执行完成后，添加意见
            TPBpmLog tpbpmLog = new TPBpmLog();
            TSUser user = ResourceUtil.getSessionUserName();
            tpbpmLog.setBpm_id(task.getExecutionId());
            tpbpmLog.setTask_name(processHandle.getTpProcess().getProcessname());
            tpbpmLog.setTask_node(processHandle.getTpProcessnode().getProcessnodename());
            tpbpmLog.setOp_code(user.getUserKey());
            tpbpmLog.setOp_type(option);//保存操作按钮名称
            tpbpmLog.setOp_name(user.getRealName());
            tpbpmLog.setOp_time(DateUtils.gettimestamp());
            tpbpmLog.setMemo(reason);
            systemService.save(tpbpmLog);
            //map就是把流程变量，传入流程中
            String message = oConvertUtils.getString(request.getParameter("message"));//前台传入提示信息
            //            Map<String, Object> variables = task.getProcessVariables();
            if (variables.size() > 0) {
                map.putAll(variables);
            }
            map.put("option", option);
            map.put("reason", reason);
            map.put("nextnode", nextnode);
            map.put("executionId", task.getExecutionId());
            map.put("taskName", task.getName());
            TSUser currentUser = ResourceUtil.getSessionUserName();
            if (currentUser != null) {
                map.put("currentNode", currentUser.getUserName());
            }
            if (StringUtils.isNotEmpty(message)) {
                map.put("message", message);
            }
            String sendMsg = oConvertUtils.getString(request.getParameter("sendMsg"));
            if (StringUtils.isNotEmpty(sendMsg) && "1".equals(sendMsg) && sendMsgFlag) {
                sendMsgToNextUser(map);
            }
            j.setMsg("任务执行成功");
            j.setObj(tpbpmLog);
        } catch (Exception e) {
            throw new BusinessException("执行任务失败", e);
        }
        return j;
    }

    /**
     * 向下一个节点发消息
     * 
     * @param map
     */
    private void sendMsgToNextUser(Map<String, Object> map) {
        String nextnode = (String) map.get("nextnode");
        String taskName = (String) map.get("taskName");
        String executionId = (String) map.get("executionId");
        String currentNode = (String) map.get("currentNode");
        String nextUser = (String) map.get(WorkFlowGlobals.ASSIGNEE_USER_ID_LIST);
        String applyUserName = (String) map.get("applyUserId");
        String businessName = (String) map.get(WorkFlowGlobals.BPM_BUSINESS_NAME);
        String taskMessage = (String) map.get("message");
        List<TSBaseUser> userList = new ArrayList<TSBaseUser>();
        if (StringUtils.isNotEmpty(nextUser)) {
            String[] userArr = nextUser.split(",");
            for (String tmp : userArr) {
                TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", tmp);
                userList.add(queryUser);
            }
        } else {
            userList.addAll(findUser(nextnode, map));
        }
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId)
                .singleResult();
        if (instance == null) {
            return;
        }
        ProcessDefinition processDef = this.getProcessDefinition(instance.getProcessDefinitionId());
        TOMessageEntity message = new TOMessageEntity();
        message.setSendTime(new Date());
        TSBaseUser adminUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", currentNode);
        if (adminUser != null) {
            message.setSenderId(adminUser.getId());
            message.setSenderName(adminUser.getRealName());
        }
        message.setTitle("流程待办提醒");

        if (StringUtils.isNotBlank(taskMessage)) {
            message.setContent(taskMessage);
        } else {
            StringBuffer content = new StringBuffer();
            content.append("您有个流程[").append(processDef.getName()).append("_").append(businessName).append("]需要办理，")
                    .append("申请人：").append(activitiDao.getUserRealName(applyUserName)).append("，").append("请尽快处理。");
            message.setContent(content.toString());
        }
        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
        for (TSBaseUser userTmp : userList) {
            TOMessageReadEntity receiveEntity = new TOMessageReadEntity();
            receiveEntity.setReadFlag("0");
            receiveEntity.setDelFlag("0");
            receiveEntity.setShow("0");
            receiveEntity.setReceiverId(userTmp.getId());
            receiveEntity.setReceiverName(userTmp.getRealName());
            tOMessageReadList.add(receiveEntity);
        }
        if (tOMessageReadList.size() > 0) {
            this.tOMessageService.addMain(message, tOMessageReadList);
        }
    }

    /**
     * 查找用户
     * 
     * @param activityId
     * @param delegateTask
     * @return
     */

    private List<TSBaseUser> findUser(String activityId, Map<String, Object> map) {
        List<TSBaseUser> userList = new ArrayList<TSBaseUser>();
        try {
            String executionId = (String) map.get("executionId");
            ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId)
                    .singleResult();
            if (instance == null) {
                return userList;
            }
            ProcessDefinition processDef = this.getProcessDefinition(instance.getProcessDefinitionId());
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                    .getDeployedProcessDefinition(processDef.getId());
            Map<String, TaskDefinition> taskMap = processDefinition.getTaskDefinitions();
            TaskDefinition taskDefinition = taskMap.get(activityId);
            Set<Expression> candidateExpressions = taskDefinition.getCandidateGroupIdExpressions();
            Set<Expression> userExpressions = taskDefinition.getCandidateUserIdExpressions();
            Expression assigneeExpression = taskDefinition.getAssigneeExpression();
            if (candidateExpressions.size() > 0) {
                //备选角色
                for (Expression expression : candidateExpressions) {
                    TSRole role = this.systemService.findUniqueByProperty(TSRole.class, "roleCode",
                            expression.getExpressionText());
                    if (role != null) {
                        CriteriaQuery cq = new CriteriaQuery(TSRoleUser.class);
                        cq.eq("TSRole.id", role.getId());
                        cq.add();
                        List<TSRoleUser> roleUserList = this.systemService.getListByCriteriaQuery(cq, false);
                        if (roleUserList != null && roleUserList.size() > 0) {
                            for (TSRoleUser roleUser : roleUserList) {
                                userList.add(roleUser.getTSUser());
                            }
                        }
                    }
                }
            } else if (userExpressions.size() > 0) {
                //备选人员
                for (Expression expression : userExpressions) {
                    TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName",
                            expression.getExpressionText());
                    if (queryUser != null) {
                        userList.add(queryUser);
                    }
                }
            } else if (assigneeExpression != null) {
                //分配人
                String assignee = assigneeExpression.getExpressionText();
                if (assignee.contains("${")) {
                    String exp = assignee.replace("${", "").replace("}", "");
                    assignee = (String) map.get(exp);
                }
                TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", assignee);
                if (queryUser != null) {
                    userList.add(queryUser);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return userList;
    }

    /**
     * 根据业务关键字查询流程id
     * 
     * @param businessKey
     * @return
     */
    @Override
    public Map<String, Object> getProcessInstance(String id) {
        Map<String, Object> map = null;
        String sql = "select t.proc_inst_id_ processInstId,r.name_ taskName ,r.id_ taskId,r.assignee_ assignee from ACT_HI_PROCINST t "
                + "left join act_ru_task r on t.proc_inst_id_= r.proc_inst_id_ " + " where t.business_key_=?";
        List<Map<String, Object>> resultList = this.systemService.findForJdbc(sql, id);
        if (resultList.size() > 0) {
            map = resultList.get(0);
        }
        return map;
    }

    @Override
    public int getAuditCount(String businessCode) {
        TSUser user = ResourceUtil.getSessionUserName();
        String userName = user.getUserName();
        List results = new ArrayList();
        List<Task> tasks = new ArrayList<Task>();
        List<Task> todoList;
        try {
            //建立临时集合
            List<Task> tempList = new ArrayList<Task>();
            TaskService taskService = processEngine.getTaskService();
            List<Task> todoListtemp = taskService.createTaskQuery().taskCandidateUser(userName).orderByTaskCreateTime()
                    .desc().active().list();
            tempList.addAll(todoListtemp);
            // 根据当前人的ID查询
            TaskQuery tq = taskService.createTaskQuery().taskAssignee(userName).orderByTaskCreateTime().desc()
                    .orderByTaskPriority().desc();
            todoList = tq.list();
            tempList.addAll(todoList);

            tasks.addAll(tempList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            String businessKey = getBusinessKeyByTask(task);
            Class entityClass = MyClassLoader.getClassByScn("org.jeecgframework.workflow.pojo.base.TSBaseBusQuery");// 业务基类
            Object entityObj = getEntity(entityClass, businessKey);
            if (entityObj != null) {
                ReflectHelper reflectHelper = new ReflectHelper(entityObj);
                String bc = (String) reflectHelper.getMethodValue("businessCode");
                TSPrjstatus prjStatus = (TSPrjstatus) reflectHelper.getMethodValue("TSPrjstatus");
                if (!businessCode.equals(bc) || "5".equals(prjStatus.getStatus())) {//如果业务编码不匹配，跳过.
                    continue;
                }
                Process process = (Process) reflectHelper.getMethodValue("Process");
                process.setTask(task);
                process.setProcessInstance(processInstance);
                process.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
                String userid = (String) reflectHelper.getMethodValue("userid");
                if (userid != null) {
                    TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName", userid);
                    if (baseUser != null) {
                        reflectHelper.setMethodValue("userRealName", baseUser.getRealName());
                    }
                }
                String assigneeUserId = task.getAssignee();
                if (assigneeUserId != null) {
                    TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName",
                            assigneeUserId);
                    if (baseUser != null) {
                        reflectHelper.setMethodValue("assigneeName", baseUser.getRealName());
                    }
                }
                results.add(entityObj);
            }
        }
        return results.size();
    }

}
