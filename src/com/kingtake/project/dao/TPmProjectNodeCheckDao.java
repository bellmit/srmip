package com.kingtake.project.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface TPmProjectNodeCheckDao {
    @Arguments({ "start", "end", "param" })
    public List<Map<String, Object>> getProjectNodeApprReceiveList(int start, int end, Map<String, Object> param);

    @Arguments({ "param" })
    public Integer getProjectNodeCheckCount(Map<String, Object> param);
}

