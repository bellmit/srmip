package org.jeecgframework.workflow.listener.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 监听可编辑节点.设置流程变量.
 * 
 * @author admin
 * 
 */
public class EditTaskListener implements TaskListener {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    public void notify(DelegateTask delegateTask) {
        delegateTask.setVariableLocal("editFlag", "1");
    }

}
