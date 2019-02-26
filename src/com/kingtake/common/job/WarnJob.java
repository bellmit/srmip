package com.kingtake.common.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.ReflectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.base.entity.warnmessage.TBWarnEntity;
import com.kingtake.base.service.warnmessage.TBWarnServiceI;
import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.office.service.warn.TOWarnServiceI;

@Component
@Transactional
public class WarnJob {
    @Autowired
    private TOWarnServiceI tOWarnService;

    @Autowired
    private TBWarnServiceI tBWarnService;

    @Autowired
    private TOMessageServiceI tOMessageService;

    @Autowired
    private WarnService commonWarnService;

    @Autowired
    private WarnService messageWarnService;

    /**
     * 通用公共提醒
     */
    @Scheduled(cron = "0 * * * * ?")
    public void sendCommonWarn() {
        CriteriaQuery cq = new CriteriaQuery(TOWarnEntity.class);
        cq.eq("warnStatus", "0");
        cq.add();
        List<TOWarnEntity> warnList = this.tOWarnService.getListByCriteriaQuery(cq, false);
        for (TOWarnEntity entity : warnList) {
            if (checkWarn(entity)) {
                if (entity.getWarnWay().contains("1")) {//提醒方式为消息
                    commonWarnService.sendMessage(entity);
                }
                if (entity.getWarnWay().contains("2")) {//提醒方式为日程管理

                }
            }
        }
    }

    /**
     * 专用业务提醒
     */
    //@Scheduled(cron = "0 * * * * ?")
    public void sendMessageWarn() {
        List<TBWarnEntity> warnList = this.tBWarnService.loadAll(TBWarnEntity.class);
        for (TBWarnEntity entity : warnList) {
            if (checkWarn(entity)) {
                    messageWarnService.sendMessage(entity);
            }
        }
    }

    /**
     * 检查是否符合发送条件
     * 
     * @param entity
     * @return
     */
    private boolean checkWarn(Object entity) {
        ReflectHelper helper = new ReflectHelper(entity);
        Date beginDate = (Date) helper.getMethodValue("warnBeginTime");
        Date endDate = (Date) helper.getMethodValue("warnEndTime");
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate.setSeconds(59);
        Date now = new Date();
        if (now.before(beginDate) || now.after(endDate)) {
            return false;
        }
        String timePoint = (String) helper.getMethodValue("warnTimePoint");
        String warnFrequency = (String) helper.getMethodValue("warnFrequency");
        if ("3".equals(warnFrequency)) {
            Calendar cal = Calendar.getInstance();
            //如果是工作日提醒，而当前为周末，则返回
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                return false;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String nowTimeStr = sdf.format(now);
        if (nowTimeStr.equals(timePoint)) {//时和分相等才会触发
            return true;
        }
        return false;
    }

}
