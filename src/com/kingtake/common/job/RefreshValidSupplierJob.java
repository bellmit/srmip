package com.kingtake.common.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kingtake.project.service.supplier.TPmQualitySupplierServiceI;

@Component
public class RefreshValidSupplierJob {
    public Logger logger = Logger.getLogger(RefreshValidSupplierJob.class);

    @Autowired
    private TPmQualitySupplierServiceI tPmQualitySupplierService;

    // 每天零点执行一次
    @Scheduled(cron = "0 0 0 * * ?")
    public void excuteDc() {
        tPmQualitySupplierService.refreshValidFlag();
    }

}