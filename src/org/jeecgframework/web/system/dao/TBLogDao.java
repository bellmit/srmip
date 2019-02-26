package org.jeecgframework.web.system.dao;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.web.system.pojo.base.TBLog;


/**
 * 使用miniDao保存日志.
 * 
 * @author admin
 * 
 */
@MiniDao
public interface TBLogDao {

    /**
     * 插入记录
     * 
     * @param tbLog
     */
    @Arguments({ "tbLog" })
    void insert(TBLog tbLog);

}
