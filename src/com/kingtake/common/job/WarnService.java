package com.kingtake.common.job;

import com.kingtake.base.entity.warnmessage.TBWarnEntity;
import com.kingtake.office.entity.warn.TOWarnEntity;

public interface WarnService {
    /**
     * 公共提醒发送消息方法
     * 
     * @param entity
     */
    public void sendMessage(TOWarnEntity entity);

    /**
     * 专用业务提醒
     * 
     * @param entity
     */
    public void sendMessage(TBWarnEntity entity);
}
