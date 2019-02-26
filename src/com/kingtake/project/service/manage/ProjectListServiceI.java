package com.kingtake.project.service.manage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;

import com.kingtake.project.entity.manage.TPmProjectEntity;

public interface ProjectListServiceI {
	
    /**
     * 根据key获取不同的项目列表
     * 
     * @param dataGrid
     * @param tPmProject
     * @param request
     * @return
     */
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request);

    /**
     * 获取待审核事项的数目
     * 
     * @return
     */
    public int getAuditCount(Map<String, String> param);
}
