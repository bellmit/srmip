package com.kingtake.office.service.notice;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.notice.TONoticeEntity;
import com.kingtake.office.entity.notice.TONoticeReceiveEntity;
import com.kingtake.office.entity.warn.TOWarnEntity;

public interface TONoticeServiceI extends CommonService {

    public <T> void delete(T entity);

    /**
     * 添加一对多
     * 
     */
    public void addMain(TONoticeEntity tONotice, List<TONoticeReceiveEntity> tONoticeReceiveList);

    /**
     * 修改一对多
     * 
     */
    public void updateMain(TONoticeEntity tONotice, List<TONoticeReceiveEntity> tONoticeReceiveList);

    public void delMain(TONoticeEntity tONotice);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TONoticeEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TONoticeEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TONoticeEntity t);

    /**
     * 更新
     * 
     * @param tONotice
     * @param receiverid
     */
    public void updateNotice(TONoticeEntity tONotice, String receiverid);

    /**
     * 回复
     * 
     * @param tOSchedule
     * @param resContent
     * @param type
     */
    public void doResponse(TONoticeEntity tONotice, String resContent, String type);

    /**
     * 添加提醒
     * 
     * @param tOWarn
     * @param noticeEntity
     */
    public void doWarn(TOWarnEntity tOWarn, TONoticeEntity noticeEntity);

    /**
     * 删除通知公告
     * 
     * @param tONotice
     * @return
     */
    public String deleteNotice(TONoticeEntity tONotice);

}
