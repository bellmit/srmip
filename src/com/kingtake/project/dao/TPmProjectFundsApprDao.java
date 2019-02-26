package com.kingtake.project.dao;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface TPmProjectFundsApprDao {
    @Arguments({ "userId", "departId", "operateStatus", "validFlag" })
    public Integer getAuditCount(String userId, String departId, String operateStatus, String validFlag);

    /**
     * 查询计划类项目预算信息
     * 
     * @param tpId
     * @return
     */
    @Arguments({ "tpId" })
    public List<Map<String, Object>> getPlanFundsList(String tpId);

    /**
     * 查询和同类项目预算信息
     * 
     * @param tpId
     * @return
     */
    @Arguments({ "tpId" })
    public List<Map<String, Object>> getContractFundsList(String tpId);

    /**
     * 查询附表记录
     * 
     * @param tpId
     * @return
     */
    @Arguments({ "tpId" })
    public List<Map<String, Object>> getAddendumFundList(String tpId);
}
