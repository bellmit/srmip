/**  
 * @Project: jeecg
 * @Title: ProcessEndListener.java
 * @Package com.oa.manager.workFlow.listener.execution
 * @date 2013-8-16 下午2:04:12
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.execution;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;

import com.kingtake.project.service.change.TBPmProjectChangeServiceI;



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
public class ProcessEndListener implements ExecutionListener{

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
        ExecutionEntity entity = (ExecutionEntity) execution;
        boolean deleteFlag = entity.isDeleteRoot();
        //String applyUserId=(String)execution.getVariable("applyUserId");//获取流程发起人id
		
        //System.out.println("流程变量:" + execution.getVariables());
        Object validFlag = execution.getVariable(WorkFlowGlobals.VALID_FLAG);
		//修改流程状态
		TaskJeecgService taskJeecgService= ApplicationContextUtil.getContext().getBean(TaskJeecgService.class);
        String bpmStatus = WorkFlowGlobals.BPM_BUS_STATUS_3;//完成
        if (deleteFlag || "0".equals(validFlag)) {
            bpmStatus = WorkFlowGlobals.BPM_BUS_STATUS_4;//作废
        } else {
            String id = (String) execution.getVariable(WorkFlowGlobals.BPM_DATA_ID);
            String businessCode = (String) execution.getVariable(WorkFlowGlobals.BPM_BUSINESS_CODE);
            if ("projectChange".equals(businessCode)) {//如果是项目变更，则将变更回写
                TBPmProjectChangeServiceI projectChangeService = ApplicationContextUtil.getContext().getBean(
                        TBPmProjectChangeServiceI.class);
                projectChangeService.changeProjectAfterAudit(id);
            }
		}
        taskJeecgService.updateFormDataStatus(execution, bpmStatus);
		
		//清空流程实例所有历史流程变量,任务变量
		HistoryService historyService= ApplicationContextUtil.getContext().getBean(HistoryService.class);
		
		List<HistoricVariableInstance> hvis=historyService.createHistoricVariableInstanceQuery().processInstanceId(execution.getProcessInstanceId()).list();
		
		for(HistoricVariableInstance h:hvis){
			//流程对应的表单页面，不清空
			if(!WorkFlowGlobals.BPM_FORM_CONTENT_URL.equals(h.getVariableName())){
				((HistoricVariableInstanceEntity)h).delete();
			}
		}
	}

}
