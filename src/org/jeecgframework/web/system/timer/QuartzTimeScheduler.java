package org.jeecgframework.web.system.timer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jeecgframework.web.system.service.TSFilesService;
import org.springframework.stereotype.Component;

@Component
public class QuartzTimeScheduler {
    public Logger logger = Logger.getLogger(QuartzTimeScheduler.class);

    @Resource
    TSFilesService fileService;

    //轮询每秒一次
    //    @Scheduled(cron = "0/60 * * * * ?")
    public void excuteDc() {
        try {
            fileService.task();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
