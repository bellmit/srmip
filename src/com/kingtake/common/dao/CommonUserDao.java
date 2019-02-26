package com.kingtake.common.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface CommonUserDao {

    @Arguments({ "param", "start", "end" })
    public List<Map<String, Object>> getUserList(Map<String, Object> param, int start, int end);

    @Arguments("param")
    public Integer getUserCount(Map<String, Object> param);

}
