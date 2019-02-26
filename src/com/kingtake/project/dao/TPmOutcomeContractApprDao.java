package com.kingtake.project.dao;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface TPmOutcomeContractApprDao {
    @Arguments({ "userId", "departId", "operateStatus", "validFlag" })
    public Integer getAuditCount(String userId, String departId, String operateStatus, String validFlag);
}
