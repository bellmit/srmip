package org.jeecgframework.workflow.listener.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.springframework.context.ApplicationContext;

import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.service.message.TOMessageServiceI;

/**
 * 在流程节点审批完后，发送消息给申请人.
 * 
 * @author admin
 * 
 */
public class SendMessageToApplyUserTaskListener implements TaskListener {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    private SystemService systemService;
    private ActivitiDao activitiDao;
    private RepositoryService repositoryService;
    private TOMessageServiceI tOMessageService;

    public void notify(DelegateTask delegateTask) {
        String sendMsg = (String) delegateTask.getVariable("sendMsg");
        ApplicationContext context = ApplicationContextUtil.getContext();
        systemService = (SystemService) context.getBean(SystemService.class);
        tOMessageService = (TOMessageServiceI) context.getBean(TOMessageServiceI.class);
        activitiDao = (ActivitiDao) context.getBean(ActivitiDao.class);
        repositoryService = (RepositoryService) context.getBean(RepositoryService.class);
        if ("1".equals(sendMsg)) {
            sendMsgToApplyUser(delegateTask);
        }
    }

    private void sendMsgToApplyUser(DelegateTask delegateTask) {
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(delegateTask.getProcessDefinitionId());
        String processName = processDefinition.getName();//获取流程名称
        String applyUserName = (String) delegateTask.getVariable("applyUserId");
        String businessName = (String) delegateTask.getVariable(WorkFlowGlobals.BPM_BUSINESS_NAME);
        String taskNodeName = delegateTask.getName();
        TOMessageEntity message = new TOMessageEntity();
        message.setSendTime(new Date());
        TSBaseUser adminUser = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", "admin");
        if (adminUser != null) {
            message.setSenderId(adminUser.getId());
            message.setSenderName(adminUser.getRealName());
        }
        message.setTitle("流程变化提醒");
        StringBuffer content = new StringBuffer();
        content.append("流程[").append(processName).append("_").append(businessName).append("]已发生变化，流程节点[");
        content.append(taskNodeName).append("]").append("已审批完成");
        if (StringUtils.isNotEmpty(delegateTask.getAssignee())) {
            content.append("，审批人：").append(activitiDao.getUserRealName(delegateTask.getAssignee()));
        }
        String option = (String) delegateTask.getVariable("option");
        if (StringUtils.isNotEmpty(option)) {
            content.append("，审批结果是：").append(option);
        }
        String reason = (String) delegateTask.getVariable("reason");
        if (StringUtils.isNotEmpty(reason)) {
            content.append("，审批意见：").append(reason);
        }
        message.setContent(content.toString());
        String owner = applyUserName;//发起人
        TSBaseUser user = this.systemService.findUniqueByProperty(TSBaseUser.class, "userName", owner);
        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
        TOMessageReadEntity receiveEntity = new TOMessageReadEntity();
        receiveEntity.setReadFlag("0");
        receiveEntity.setDelFlag("0");
        receiveEntity.setShow("0");
        receiveEntity.setReceiverId(user.getId());
        receiveEntity.setReceiverName(user.getRealName());
        tOMessageReadList.add(receiveEntity);
        if (tOMessageReadList.size() > 0) {
            this.tOMessageService.addMain(message, tOMessageReadList);
        }
    }

}
