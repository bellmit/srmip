package com.kingtake.office.dao;

import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;
import org.jeecgframework.minidao.annotation.Sql;

@MiniDao
public interface OfficialDao {

    /**
     * 获取未处理记录数
     * 
     * @param tableName
     *            表名
     * @param operateStatus
     *            处理状态
     * @param receiveUserid
     *            接收人
     * @param validFlag
     *            有效状态
     * @param archiveFlag
     *            主表状态
     * @return
     */
    @Sql("select count(distinct b.id) from ${map.tableName} b left join T_O_FLOW_RECEIVE_LOGS r on r.send_receive_id = b.id where (b.create_by = '${map.userName}' and b.archive_flag != '${map.archiveFlag}') or (r.receive_userid = '${map.receiveUserid}' and r.operate_status = '${map.operateStatus}' and r.VALID_FLAG = '${map.validFlag}' )  and b.register_type = '${map.registerType}'")
    @Arguments("map")
    Integer getUntreated1(Map map);

    @Sql("SELECT COUNT(0)  FROM ${map.tableName} T JOIN T_O_FLOW_RECEIVE_LOGS R ON T.ID = R.SEND_RECEIVE_ID AND R.OPERATE_STATUS = '${map.operateStatus}' AND R.RECEIVE_USERID='${map.receiveUserid}'  WHERE 1=1 ")
    @Arguments("map")
    Integer getUntreated(Map map);

    @Sql("select count(distinct b.id) from ${map.tableName} b left join T_O_FLOW_RECEIVE_LOGS r on r.send_receive_id = b.id where (b.create_by = '${map.userName}' and b.archive_flag != '${map.archiveFlag}') or (b.NUCLEAR_DRAFT_USERID = '${map.receiveUserid}' and b.NUCLEAR_DRAFT_STATUS = '${map.no}') or( r.receive_userid = '${map.receiveUserid}' and r.operate_status = '${map.operateStatus}' and r.VALID_FLAG = '${map.validFlag}') and b.register_type = '${map.registerType}'")
    @Arguments("map")
    Integer getUntreatedForSendBill(Map map);

}
