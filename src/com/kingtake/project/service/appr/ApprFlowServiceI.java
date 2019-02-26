package com.kingtake.project.service.appr;


public interface ApprFlowServiceI {
    /**
     * 完成审批
     * @param id
     * @return
     */
    public void updateFinishFlag(String id) ;
    
    /**
     * 驳回调用的操作
     * @param id
     */
    public void doBack(String id);
    
    /**
     * 发送调用的操作
     * @param id
     */
    public void doPass(String id);
    
    /**
     * 获取业务名称
     * @param id
     * @return
     */
    public String getAppName(String id);
    
    /**
     * 
     * 发起人撤回
     * @param id
     */
    public void doCancel(String id);
    
}
