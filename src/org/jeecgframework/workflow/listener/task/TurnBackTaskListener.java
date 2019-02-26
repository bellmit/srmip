/**  
 * @Title: TurnBackTaskListener.java
 * @date 2013-8-19 下午4:26:40
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;

/**
 * 驳回监听器
 * 将此监听器放在可以驳回的用户任务，事件：complete(任务完成)
 * 允许驳回到此任务节点，将此任务放入可驳回列表
 * @author	jeecg
 * @version	 1.0
 *
 */
public class TurnBackTaskListener implements TaskListener{

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

    /**
     * 流程驳回，则修改业务数据状态
     */
	public void notify(DelegateTask dt) {
        TaskJeecgService taskJeecgService = ApplicationContextUtil.getContext().getBean(TaskJeecgService.class);
        String bpmStatus = WorkFlowGlobals.BPM_BUS_STATUS_5;//驳回
        taskJeecgService.updateFormDataStatus(dt.getExecution(), bpmStatus);
	}

}
