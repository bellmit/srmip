/**  
 * @Project: jeecg
 * @Title: ProcessEndListener.java
 * @Package com.oa.manager.workFlow.listener.execution
 * @date 2013-8-16 下午2:04:12
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.execution;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;



/**
 * 
 * 类名：ProcessEndListener
 * 功能：流程实例结束监听器
 * 详细：
 * 作者：jeecg
 * 版本：1.0
 * 日期：2013-8-16 下午2:04:12
 *
 */
public class ProcessDemoListener implements ExecutionListener {

	public ProcessDemoListener(){
		System.out.println("-------------init-----ProcessDemoListener-----------");
	}
	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("-------------do-----ProcessDemoListener-----------");
		
        String applyUserId = (String) execution.getVariable("applyUserId");//获取流程发起人id
		
        System.out.println("ceshi流程变量:" +applyUserId + execution.getVariables());
	}

}
