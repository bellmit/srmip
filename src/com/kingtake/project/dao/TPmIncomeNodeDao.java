package com.kingtake.project.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface TPmIncomeNodeDao {
    @Arguments({ "start", "end", "param" })
    public List<Map<String, Object>> getProjectList(int start, int end, Map<String, Object> param);

    @Arguments({ "param" })
    public Integer getProjectCount(Map<String, Object> param);

    public Integer getAuditCount();
}
