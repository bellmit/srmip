package org.jeecgframework.workflow.listener.task;

import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.springframework.context.ApplicationContext;

import com.kingtake.common.constant.ProjectConstant;

/**
 * 申报书被院系审核通过了
 * 
 * @author admin
 * 
 */
public class DepartAuditedTaskListener implements TaskListener {

    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    private SystemService systemService;

    public void notify(DelegateTask delegateTask) {
        ApplicationContext context = ApplicationContextUtil.getContext();
        systemService = (SystemService) context.getBean(SystemService.class);
        String tableName = (String) delegateTask.getVariable(WorkFlowGlobals.BPM_FORM_KEY);
        String id = (String) delegateTask.getVariable(WorkFlowGlobals.BPM_DATA_ID);
        List<Map<String, Object>> dataMap = systemService.findForJdbc("select depart_audited from " + tableName
                + " where id=?", id);//查出数据状态
        if (dataMap.size() > 0) {//如果已经修改为已审核了，则直接返回.
            String departAuditStatus = (String) dataMap.get(0).get("depart_audited");
            if (StringUtils.isNotEmpty(departAuditStatus) && "1".equals(departAuditStatus)) {
                return;
            }
        }
        TSUser user = ResourceUtil.getSessionUserName();

        String sql = "update " + tableName + " t set t.depart_audited="
                + ProjectConstant.PROJECT_DECLARE_DEPART_AUDITED
                + ",t.update_by=?,t.update_name=?,t.update_date=sysdate where t.id=?";
        Integer count = systemService.executeSql(sql, new Object[] { user.getUserName(), user.getRealName(), id });
        if (count == null || count == 0) {
            throw new BusinessException("更新项目申报书院系审核状态失败!");
        }
    }

}
