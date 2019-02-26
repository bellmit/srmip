package com.kingtake.project.service.manage;

import java.util.Map;

/**
 * 获取项目列表service容器
 * 
 * @author admin
 * 
 */
public class ProjectListServiceContext {
    
    public Map<String, ProjectListServiceI> serviceMap;

    /**
     * 获取key对应的service
     * 
     * @param key
     * @return
     */
    public ProjectListServiceI getProjectService(String key) {
        return serviceMap.get(key);
    }

    public Map<String, ProjectListServiceI> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<String, ProjectListServiceI> serviceMap) {
        this.serviceMap = serviceMap;
    }

}
