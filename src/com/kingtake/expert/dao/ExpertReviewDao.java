package com.kingtake.expert.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface ExpertReviewDao {
    /**
     * 获取通过院系审核项目列表
     * 
     * @param start
     * @param end
     * @return
     */
    @Arguments({ "start", "end" ,"param"})
    public List<Map<String, Object>> getProjectList(int start, int end, Map<String, Object> param);

    /**
     * 获取通过院系审核项目数目
     * 
     * @return
     */
    @Arguments({ "param" })
    public Integer getProjectCount(Map<String, Object> param);

    /**
     * 获取需要处理的通过院系审核的项目
     * 
     * @return
     */
    public Integer getAuditProjectCount();
}
