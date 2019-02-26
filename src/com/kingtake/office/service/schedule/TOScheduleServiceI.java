package com.kingtake.office.service.schedule;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.schedule.TOScheduleEntity;

public interface TOScheduleServiceI extends CommonService {

    public <T> void delete(T entity);

    public <T> Serializable save(T entity);

    public <T> void saveOrUpdate(T entity);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOScheduleEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOScheduleEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOScheduleEntity t);

    /**
     * 发送
     */
    public void doSend(TOScheduleEntity tOSchedule, String receiverIds, String receiverNames);

    /**
     * 接收
     * 
     * @param tOSchedule
     */
    public void doReceive(TOScheduleEntity tOSchedule);

    /**
     * 保存日程安排
     * 
     * @param tOSchedule
     */
    public void saveSchedule(TOScheduleEntity tOSchedule);

    /**
     * 
     * 删除日程安排
     * 
     * @param tOSchedule
     */
    public void deleteSchedule(TOScheduleEntity tOSchedule);

    /**
     * 回复日程安排
     * 
     * @param tOSchedule
     * @param resContent
     */
    public void doResponse(TOScheduleEntity tOSchedule, String resContent, String type);

    /**
     * 隐藏日程安排
     * 
     * @param ids
     */
    public void doLogicDelete(String ids);

    /**
     * 日程安排完成
     * 
     * @param tOSchedule
     */
    public void doFinish(TOScheduleEntity tOSchedule);

    /**
     * 恢复日程安排
     * 
     * @param ids
     */
    public void doBack(String ids);
}
