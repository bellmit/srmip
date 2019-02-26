package com.kingtake.project.service.impl.change;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.dao.IActivitiCommonDao;
import org.jeecgframework.workflow.model.activiti.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.change.TBPmChangeProjectnameEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.change.TBPmChangeProjectnameServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tBPmChangeProjectnameService")
@Transactional
public class TBPmChangeProjectnameServiceImpl extends CommonServiceImpl implements TBPmChangeProjectnameServiceI,
        ProjectListServiceI {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    private IActivitiCommonDao activitiCommonDao;

    @Override
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TBPmChangeProjectnameEntity) entity);
    }

    @Override
    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TBPmChangeProjectnameEntity) entity);
        return t;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TBPmChangeProjectnameEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doAddSql(TBPmChangeProjectnameEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doUpdateSql(TBPmChangeProjectnameEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    @Override
    public boolean doDelSql(TBPmChangeProjectnameEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TBPmChangeProjectnameEntity t) {
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{bpm_status}", String.valueOf(t.getBpmStatus()));
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{project_id}", String.valueOf(t.getProjectId()));
        sql = sql.replace("#{before_project_name}", String.valueOf(t.getBeforeProjectName()));
        sql = sql.replace("#{after_project_name}", String.valueOf(t.getAfterProjectName()));
        sql = sql.replace("#{change_reason}", String.valueOf(t.getChangeReason()));
        sql = sql.replace("#{change_according}", String.valueOf(t.getChangeAccording()));
        sql = sql.replace("#{change_time}", String.valueOf(t.getChangeTime()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        String businessCode = "changeProjectName";
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
            // String businessKey = processInstance.getBusinessKey();
            String businessKey = getBusinessKeyByTask(task);
            Class entityClass = MyClassLoader.getClassByScn("org.jeecgframework.workflow.pojo.base.TSBaseBusQuery");// 业务基类
            Object entityObj = getEntity(entityClass, businessKey);
            if (entityObj != null) {
                ReflectHelper reflectHelper = new ReflectHelper(entityObj);
                String bc = (String) reflectHelper.getMethodValue("businessCode");
                if (!businessCode.equals(bc)) {//如果业务编码不匹配，跳过.
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
     * 查询流程定义对象
     * 
     * @param processDefinitionId
     *            流程定义ID
     * @return
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
    }

    @Override
    public void deleteAddAttach(TBPmChangeProjectnameEntity t) {
        this.delAttachementByBid(t.getId());
        this.commonDao.delete(t);
    }
}