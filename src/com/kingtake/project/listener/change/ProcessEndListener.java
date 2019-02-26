/**  
 * @Project: jeecg
 * @Title: ProcessEndListener.java
 * @Package com.oa.manager.workFlow.listener.execution
 * @date 2013-8-16 下午2:04:12
 * @Copyright: 2013 
 */
package com.kingtake.project.listener.change;

import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;
import org.springframework.context.ApplicationContext;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.change.TBPmChangeProjectmanagerEntity;
import com.kingtake.project.entity.change.TBPmChangeProjectnameEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;

/**
 * 
 * 类名：ProcessEndListener 功能：流程实例结束监听器 详细： 作者：jeecg 版本：1.0 日期：2013-8-16 下午2:04:12
 *
 */
public class ProcessEndListener implements ExecutionListener {

    /**
     * @Fields serialVersionUID :
     */

    private static final long serialVersionUID = 1L;
    private SystemService systemService;

    public void notify(DelegateExecution execution) throws Exception {
        ExecutionEntity entity = (ExecutionEntity) execution;
        boolean deleteFlag = entity.isDeleteRoot();
        TaskJeecgService taskJeecgService = ApplicationContextUtil.getContext().getBean(TaskJeecgService.class);
        if (deleteFlag) {
            taskJeecgService.updateFormDataStatus(execution, WorkFlowGlobals.BPM_BUS_STATUS_4);
            return;
        }
        ApplicationContext context = ApplicationContextUtil.getContext();
        systemService = (SystemService) context.getBean(SystemService.class);
        String applyUserId = (String) execution.getVariable("applyUserId");//获取流程发起人id
        String id = (String) execution.getVariable(WorkFlowGlobals.BPM_DATA_ID);
        String tableName = (String) execution.getVariable(WorkFlowGlobals.BPM_FORM_KEY);
        if ("t_b_pm_change_projectmanager".equals(tableName)) {//项目负责人变更
            TBPmChangeProjectmanagerEntity changeManager = systemService.get(TBPmChangeProjectmanagerEntity.class, id);
            TPmProjectEntity project = changeManager.getProject();
            CriteriaQuery cq = new CriteriaQuery(TPmProjectMemberEntity.class);
            cq.eq("project.id", project.getId());
            cq.eq("projectManager", SrmipConstants.YES);
            cq.add();
            List<TPmProjectMemberEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                TPmProjectMemberEntity member = new TPmProjectMemberEntity();
                member = list.get(0);
                systemService.delete(member);
            }
            TSUser muser = changeManager.getAfterProjectManager();
            CriteriaQuery cq2 = new CriteriaQuery(TPmProjectMemberEntity.class);
            cq2.eq("project.id", project.getId());
            cq2.eq("user.id", muser.getId());
            cq2.add();
            List<TPmProjectMemberEntity> list2 = systemService.getListByCriteriaQuery(cq2, false);
            if (list2.size() > 0) {//如果变更后负责人为项目组成员则直接将其变为负责人，否则新增一个负责人
                TPmProjectMemberEntity member = list2.get(0);
                member.setProjectManager(SrmipConstants.YES);
                systemService.updateEntitie(member);
            } else {
                TPmProjectMemberEntity member = new TPmProjectMemberEntity();
                member.setProject(project);
                member.setBirthday(muser.getBirthday());
                member.setName(muser.getRealName());
                member.setProjectManager(SrmipConstants.YES);
                member.setSex(muser.getSex());
                member.setPosition(muser.getDuty());
                member.setProjectName(project.getProjectName());
                member.setUserId(muser.getId());
                member.setUser(muser);
                systemService.save(member);
            }
            project.setProjectManagerId(muser.getId());
            project.setProjectManager(muser.getRealName());
            systemService.updateEntitie(project);
            changeManager.setChangeTime(new Date());
            systemService.updateEntitie(changeManager);

        } else if ("t_b_pm_change_projectname".equals(tableName)) {//项目名称变更
            TBPmChangeProjectnameEntity change = systemService.get(TBPmChangeProjectnameEntity.class, id);
            TPmProjectEntity project = systemService.get(TPmProjectEntity.class, change.getProjectId());
            project.setProjectName(change.getAfterProjectName());
            systemService.updateEntitie(project);
            change.setChangeTime(new Date());
            systemService.updateEntitie(change);
        }
        System.out.println("流程变量:" + execution.getVariables());
        //修改流程状态
        String bpmStatus = WorkFlowGlobals.BPM_BUS_STATUS_3;//完成
        taskJeecgService.updateFormDataStatus(execution, bpmStatus);
        //清空流程实例所有历史流程变量,任务变量
        HistoryService historyService = ApplicationContextUtil.getContext().getBean(HistoryService.class);

        List<HistoricVariableInstance> hvis = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(execution.getProcessInstanceId()).list();

        for (HistoricVariableInstance h : hvis) {
            //流程对应的表单页面，不清空
            if (!WorkFlowGlobals.BPM_FORM_CONTENT_URL.equals(h.getVariableName())) {
                ((HistoricVariableInstanceEntity) h).delete();
            }
        }
    }

}
