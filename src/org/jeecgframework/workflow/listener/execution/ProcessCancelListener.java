package org.jeecgframework.workflow.listener.execution;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.jeecgframework.workflow.common.WorkFlowGlobals;

/**
 * 
 * 流程作废监听
 * 
 */
public class ProcessCancelListener implements ExecutionListener {

    public ProcessCancelListener() {
    }

    /**
     * @Fields serialVersionUID :
     */
	
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {

        execution.setVariable(WorkFlowGlobals.VALID_FLAG, "0");

	}

}
