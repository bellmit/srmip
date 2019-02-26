package com.kingtake.project.service.appr;

import java.util.Map;


public class ApprFlowServiceContext {
    public Map<String, ApprFlowServiceI> serviceMap;

    /**
     * 获取key对应的service
     * 
     * @param key
     * @return
     */
    public ApprFlowServiceI getApprService(String key) {
        return serviceMap.get(key);
    }

    public Map<String, ApprFlowServiceI> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<String, ApprFlowServiceI> serviceMap) {
        this.serviceMap = serviceMap;
    }
}
