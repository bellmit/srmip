package com.kingtake.common.job;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kingtake.base.service.rtx.TBSyncUserDeptTempServiceI;

public class SyncRtxJob {
    public Logger logger = Logger.getLogger(SyncRtxJob.class);

    @Resource
    private TBSyncUserDeptTempServiceI syncUserDeptTempService;

    // 轮询每60秒一次
    @Scheduled(cron = "0 0 0 1 * ?")
    public void excuteDc() {
        syncUserDeptTempService.syncRtxUserDept();
    }

}