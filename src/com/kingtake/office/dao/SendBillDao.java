package com.kingtake.office.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface SendBillDao {

    @Arguments({ "param", "start", "end" })
    public List<Map<String, Object>> getSendBillList(Map<String, String> param, int start, int end);

    @Arguments("param")
    public Integer getSendBillCount(Map<String, String> param);

}
