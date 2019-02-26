package org.jeecgframework.workflow.listener.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;

import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.service.message.TOMessageServiceI;

/**
 * 在流程节点审批完后，发送消息给下一个节点审批人.
 * 
 * @author admin
 * 
 */
public class SendMessageTaskToNextListener implements TaskListener {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    private SystemService systemService;
    private ActivitiDao activitiDao;
    private RepositoryService repositoryService;
    private TOMessageServiceI tOMessageService;

    public void notify(DelegateTask delegateTask) {
        ApplicationContext context = ApplicationContextUtil.getContext();
        systemService = (SystemService) context.getBean(SystemService.class);
        tOMessageService = (TOMessageServiceI) context.getBean(TOMessageServiceI.class);
        activitiDao = (ActivitiDao) context.getBean(ActivitiDao.class);
        repositoryService = (RepositoryService) context.getBean(RepositoryService.class);
        if (doSendMessage(delegateTask)) {
            sendMsgToNextUser(delegateTask);
        }
    }

    //发送消息给下一个节点审核人员
    //    private void sendMsgToNextUser(DelegateTask delegateTask) {
    //        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
    //                .getDeployedProcessDefinition(delegateTask.getProcessDefinitionId());
    //        String processName = processDefinition.getName();//获取流程名称
    //        String applyUserName = (String) delegateTask.getVariable("applyUserId");
    //        String businessName = (String) delegateTask.getVariable(WorkFlowGlobals.BPM_BUSINESS_NAME);
    //        List<TSBaseUser> userList = new ArrayList<TSBaseUser>();
    //        Set<IdentityLink> idLinkSet = delegateTask.getCandidates();//获取待选角色
    //        if (idLinkSet.size() > 0) {
    //            for (IdentityLink idLink : idLinkSet) {
    //                if (IdentityLinkType.CANDIDATE.equals(idLink.getType())) {
    //                    if (StringUtils.isNotEmpty(idLink.getGroupId())) {//待选角色不为空
    //                        TSRole role = this.systemService.findUniqueByProperty(TSRole.class, "roleCode",
    //                                idLink.getGroupId());
    //                        if (role != null) {
    //                            CriteriaQuery cq = new CriteriaQuery(TSRoleUser.class);
    //                            cq.eq("TSRole.id", role.getId());
    //                            cq.add();
    //                            List<TSRoleUser> roleUserList = this.systemService.getListByCriteriaQuery(cq, false);
    //                            if (roleUserList != null && roleUserList.size() > 0) {
    //                                for (TSRoleUser roleUser : roleUserList) {
    //                                    userList.add(roleUser.getTSUser());
    //                                }
    //                            }
    //                        }
    //                    } else if (StringUtils.isNotEmpty(idLink.getUserId())) {//待选人员不为空
    //                        String[] userArr = idLink.getUserId().split(",");
    //                        for (String u : userArr) {
    //                            TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class,
    //                                    "userName", u);
    //                            userList.add(queryUser);
    //                        }
    //                    }
    //                }
    //            }
    //        } else if (StringUtils.isNotEmpty(delegateTask.getAssignee())) {//处理人员
    //            TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName",
    //                    delegateTask.getAssignee());
    //            userList.add(queryUser);
    //        }
    //        TOMessageEntity message = new TOMessageEntity();
    //        message.setSendTime(new Date());
    //        TSBaseUser adminUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", "admin");
    //        if (adminUser != null) {
    //            message.setSenderId(adminUser.getId());
    //            message.setSenderName(adminUser.getRealName());
    //        }
    //        message.setTitle("流程待办提醒");
    //        StringBuffer content = new StringBuffer();
    //        content.append("您有个流程[").append(processName).append("_").append(businessName).append("]需要办理，").append("申请人：")
    //                .append(activitiDao.getUserRealName(applyUserName)).append("，").append("请尽快处理。");
    //        message.setContent(content.toString());
    //        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
    //        for (TSBaseUser userTmp : userList) {
    //            TOMessageReadEntity receiveEntity = new TOMessageReadEntity();
    //            receiveEntity.setReadFlag("0");
    //            receiveEntity.setDelFlag("0");
    //            receiveEntity.setShow("0");
    //            receiveEntity.setReceiverId(userTmp.getId());
    //            receiveEntity.setReceiverName(userTmp.getRealName());
    //            tOMessageReadList.add(receiveEntity);
    //        }
    //        if (tOMessageReadList.size() > 0) {
    //            this.tOMessageService.addMain(message, tOMessageReadList);
    //        }
    //    }

    private void sendMsgToNextUser(DelegateTask delegateTask) {
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(delegateTask.getProcessDefinitionId());
        String processName = processDefinition.getName();//获取流程名称
        String nextnode = (String) delegateTask.getVariable("nextnode");
        String currentNode = (String) delegateTask.getVariable("currentNode");
        String nextUser = (String) delegateTask.getVariable(WorkFlowGlobals.ASSIGNEE_USER_ID_LIST);
        String applyUserName = (String) delegateTask.getVariable("applyUserId");
        String businessName = (String) delegateTask.getVariable(WorkFlowGlobals.BPM_BUSINESS_NAME);
        String taskMessage = (String) delegateTask.getVariable("message");
        List<TSBaseUser> userList = new ArrayList<TSBaseUser>();
        if (StringUtils.isNotEmpty(nextUser)) {
            String[] userArr = nextUser.split(",");
            for (String tmp : userArr) {
                TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", tmp);
                userList.add(queryUser);
            }
        } else {
            userList.addAll(findUser(nextnode, delegateTask));
        }
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
            content.append("您有个流程[").append(processName).append("_").append(businessName).append("]需要办理，")
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
    private List<TSBaseUser> findUser(String activityId, DelegateTask delegateTask) {
        List<TSBaseUser> userList = new ArrayList<TSBaseUser>();
        try {
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                    .getDeployedProcessDefinition(delegateTask.getProcessDefinitionId());
            Map<String, TaskDefinition> taskMap = processDefinition.getTaskDefinitions();
            TaskDefinition taskDefinition = taskMap.get(activityId);
            Set<Expression> candidateExpressions = taskDefinition.getCandidateGroupIdExpressions();
            Set<Expression> userExpressions = taskDefinition.getCandidateUserIdExpressions();
            Expression assigneeExpression = taskDefinition.getAssigneeExpression();
            if (candidateExpressions.size() > 0) {//备选角色
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
            } else if (userExpressions.size() > 0) {//备选人员
                for (Expression expression : userExpressions) {
                    String[] array = expression.getExpressionText().split(",");
                    for (String u : array) {
                        TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName",
                                expression.getExpressionText());
                        userList.add(queryUser);
                    }
                }
            } else if (assigneeExpression != null) {//分配人
                String assignee = assigneeExpression.getExpressionText();
                if (assignee.contains("${")) {
                    String exp = assignee.replace("${", "").replace("}", "");
                    assignee = (String) delegateTask.getVariable(exp);
                }
                TSBaseUser queryUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", assignee);
                userList.add(queryUser);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return userList;
    }

    /**
     * 是否发送消息
     * 
     * @param delegateTask
     * @return
     */
    private boolean doSendMessage(DelegateTask delegateTask) {
        String sendMsg = (String) delegateTask.getVariable("sendMsg");
        if ("1".equals(sendMsg)) {
            return true;
        }
        return false;
    }
}
