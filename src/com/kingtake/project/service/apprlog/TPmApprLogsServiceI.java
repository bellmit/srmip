package com.kingtake.project.service.apprlog;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;

public interface TPmApprLogsServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	public Map<String, Object> doAddLogs(TPmApprSendLogsEntity tPmApprSendLogs, 
			String receiveUseIds, String receiveUseNames, 
			String receiveUseDepartIds, String receiveUseDepartNames,
			String rebutFlag);
 	
 	public Map<String, Object> doUpdateLog(TPmApprReceiveLogsEntity t, String rebutValidFlag);
 	
 	public TPmApprReceiveLogsEntity goAddGetBackLog(String apprType, String apprId);
 	
 	public void goAddGetOtherInfo(String apprType, String apprId, String handlerType, 
			TPmApprReceiveLogsEntity backLog, TSUser receiver, String ifStaff);

    /**
     * 
     * 获取待办数目
     * 
     * @param apprType
     * @return
     */
    public int getAuditCount(String apprType);

    /**
     * 将未处理的审批记录置为已完成
     * 
     * @param id
     */
    public void finishApprLogs(String id);

    /**
     * 驳回
     * @param request
     */
    public void doBack(HttpServletRequest request);

    /**
     * 通过
     * @param request
     */
    public void doPass(HttpServletRequest request);

    /**
     * 获取所有节点
     * @param recId
     * @param apprType
     * @return
     */
    public List<Map<String, Object>> getNodeLink(String recId, String apprType);

    /**
     * 完成审批
     * @param request
     * @return
     */
    public void finishAppr(HttpServletRequest request);


    /**
     * 撤销
     * @param recId
     */
    public void doCacel(String recId);

    /**
     * 获取驳回次数
     * @param apprId
     * @return
     */
    public int getRebutCount(String apprId);
 	
}
