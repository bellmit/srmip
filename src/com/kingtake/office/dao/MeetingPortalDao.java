package com.kingtake.office.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface MeetingPortalDao {

    @Arguments({ "param", "start", "end" })
    List<Map<String, String>> getPortalList(Map<String, String> param, int start, int end);

    @Arguments("param")
    Integer getPortalCount(Map<String, String> param);

}
