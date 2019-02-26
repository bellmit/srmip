package com.kingtake.office.service.impl.schedule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.office.entity.researchactivity.TOReceiveLogEntity;
import com.kingtake.office.entity.schedule.TOResponseEntity;
import com.kingtake.office.entity.schedule.TOScheduleEntity;
import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.entity.warn.TOWarnReceiveEntity;
import com.kingtake.office.service.schedule.TOScheduleServiceI;
import com.kingtake.office.service.warn.TOWarnServiceI;

@Service("tOScheduleService")
@Transactional
public class TOScheduleServiceImpl extends CommonServiceImpl implements TOScheduleServiceI {
    @Autowired
    private CommonMessageServiceI commonMessageService;
    @Autowired
    private TOWarnServiceI tOWarnService;

    public <T> void delete(T entity) {
        super.delete(entity);
        // 执行删除操作配置的sql增强
        this.doDelSql((TOScheduleEntity) entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        // 执行新增操作配置的sql增强
        this.doAddSql((TOScheduleEntity) entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        // 执行更新操作配置的sql增强
        this.doUpdateSql((TOScheduleEntity) entity);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOScheduleEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOScheduleEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOScheduleEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TOScheduleEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{user_id}", String.valueOf(t.getUserId()));
        sql = sql.replace("#{user_name}", String.valueOf(t.getUserName()));
        sql = sql.replace("#{begin_time}", String.valueOf(t.getBeginTime()));
        sql = sql.replace("#{end_time}", String.valueOf(t.getEndTime()));
        sql = sql.replace("#{title}", String.valueOf(t.getTitle()));
        sql = sql.replace("#{address}", String.valueOf(t.getAddress()));
        sql = sql.replace("#{content}", String.valueOf(t.getContent()));
        sql = sql.replace("#{relate_userid}", String.valueOf(t.getRelateUserid()));
        sql = sql.replace("#{relate_username}", String.valueOf(t.getRelateUsername()));
        sql = sql.replace("#{project_id}", String.valueOf(t.getProjectId()));
        sql = sql.replace("#{finished_flag}", String.valueOf(t.getFinishedFlag()));
        sql = sql.replace("#{schedule_type}", String.valueOf(t.getScheduleType()));
        sql = sql.replace("#{open_status}", String.valueOf(t.getOpenStatus()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    @Override
    public void doSend(TOScheduleEntity tOSchedule, String receiverIds, String receiverNames) {
        try {
            TSUser user = ResourceUtil.getSessionUserName();
            tOSchedule = this.commonDao.get(TOScheduleEntity.class, tOSchedule.getId());
            String[] receiverIdArr = receiverIds.split(",");
            String[] receiverNameArr = receiverNames.split(",");
            for (int i = 0; i < receiverNameArr.length; i++) {
                String receiverId = receiverIdArr[i];
                String receiverName = receiverNameArr[i];
                boolean flag = checkSend(tOSchedule.getId(), receiverId);
                if (flag) {//如果存在已发送且未接收的记录，则直接跳过
                    continue;
                }
                TOScheduleEntity tmpSchedule = new TOScheduleEntity();
                MyBeanUtils.copyBeanNotNull2Bean(tOSchedule, tmpSchedule);
                tmpSchedule.setId(null);
                tmpSchedule.setParentId(tOSchedule.getId());
                tmpSchedule.setUserId(receiverId);
                TSUser receiverUser = this.commonDao.get(TSUser.class, receiverId);
                tmpSchedule.setUserName(receiverUser.getUserName());
                tmpSchedule.setFinishedFlag("0");
                tmpSchedule.setCreateDate(new Date());
                tmpSchedule.setUpdateBy(null);
                tmpSchedule.setUpdateDate(null);
                tmpSchedule.setUpdateName(null);
                this.commonDao.save(tmpSchedule);//保存日程安排
                TOReceiveLogEntity logEntity = new TOReceiveLogEntity();
                logEntity.setResearchId(tOSchedule.getId());
                logEntity.setSenderId(user.getId());
                logEntity.setSenderName(user.getRealName());
                logEntity.setReceiverId(receiverId);
                logEntity.setReceiverName(receiverName);
                logEntity.setSendTime(new Date());
                logEntity.setReceiveFlag("0");
                logEntity.setSendType(SrmipConstants.SEND_TYPE_SCHEDULE);
                this.commonDao.save(logEntity);//保存发送记录
                String messageTitle = tmpSchedule.getTitle();
                String messageType = "日程安排";
                String messageContent = "您有个日程安排待接收！<a href=\"#\" style=\"color:skyblue;\" onclick='addTab(\"日程安排接收\",\"tOScheduleController.do?goSheduleList\");'>"
                        + tOSchedule.getTitle() + "</a>";
                commonMessageService.sendMessage(logEntity.getReceiverId(), messageType, messageTitle, messageContent);
            }
        } catch (Exception e) {
            throw new BusinessException("发送日程安排失败！", e);
        }
    }

    /**
     * 
     * 检查是否有已发送且未接收的记录.
     * 
     * @param researchId
     * @param userId
     * @return
     */
    private boolean checkSend(String researchId, String userId) {
        boolean flag = false;
        CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
        cq.eq("researchId", researchId);
        cq.eq("receiverId", userId);
        cq.eq("receiveFlag", "0");//未接收
        cq.add();
        List<TOReceiveLogEntity> logList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (logList.size() > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void doReceive(TOScheduleEntity tOSchedule) {
        TSUser user = ResourceUtil.getSessionUserName();
        tOSchedule = this.commonDao.get(TOScheduleEntity.class, tOSchedule.getId());
        CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
        cq.eq("researchId", tOSchedule.getParentId());
        cq.eq("receiverId", user.getId());
        cq.eq("sendType", SrmipConstants.SEND_TYPE_SCHEDULE);
        cq.add();
        List<TOReceiveLogEntity> receiveLogList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (receiveLogList.size() > 0) {
            for (TOReceiveLogEntity log : receiveLogList) {
                log.setReceiveFlag("1");//设置为已接收
                log.setReceiveTime(new Date());
                this.commonDao.updateEntitie(log);
                String messageType = "日程安排";
                String messageTitle = tOSchedule.getTitle();
                String messageContent = "您发送的日程安排已被接收！标题：" + tOSchedule.getTitle() + "，接收人:"
                        + log.getReceiverName()
                        + "，接收时间："
                        + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                commonMessageService.sendMessage(log.getSenderId(), messageType, messageTitle, messageContent);
            }
        }
        saveWarn(tOSchedule);//接收之后生成提醒
    }

    @Override
    public void saveSchedule(TOScheduleEntity tOSchedule) {
        try {
            if (StringUtil.isEmpty(tOSchedule.getId())) {
                TSUser user = ResourceUtil.getSessionUserName();
                tOSchedule.setUserId(user.getId());
                tOSchedule.setUserName(user.getUserName());
                this.commonDao.save(tOSchedule);
            } else {
                TOScheduleEntity t = this.commonDao.get(TOScheduleEntity.class, tOSchedule.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tOSchedule, t);
                this.commonDao.saveOrUpdate(t);
            }
            if (StringUtils.isNotEmpty(tOSchedule.getWarnTimePoint())) {//如果填了提醒时间点，则添加提醒
                saveWarn(tOSchedule);
            }
        } catch (Exception e) {
            throw new BusinessException("保存日程安排失败！", e);
        }
    }

    /**
     * 保存提醒
     * 
     * @param tOSchedule
     */
    private void saveWarn(TOScheduleEntity tOSchedule) {
        List<TOWarnEntity> warnList = this.commonDao.findByProperty(TOWarnEntity.class, "sourceId", tOSchedule.getId());
        if (warnList.size() > 0) {
            for (TOWarnEntity wa : warnList) {
                this.commonDao.delete(wa);
            }
        }
        TSUser user = ResourceUtil.getSessionUserName();
        TOWarnEntity warn = new TOWarnEntity();
        warn.setWarnBeginTime(tOSchedule.getBeginTime());
        warn.setWarnEndTime(tOSchedule.getEndTime());
        warn.setSourceId(tOSchedule.getId());
        warn.setWarnFrequency(tOSchedule.getWarnFrequency());
        warn.setWarnTimePoint(tOSchedule.getWarnTimePoint());
        warn.setWarnContent("日程安排提醒：" + tOSchedule.getContent());
        warn.setCreateBy(user.getUserName());
        warn.setCreateDate(new Date());
        warn.setCreateName(user.getRealName());
        warn.setWarnWay("1");//默认为消息提醒
        warn.setWarnStatus("0");//未完成
        List<TOWarnReceiveEntity> tOWarnReceiveList = new ArrayList<TOWarnReceiveEntity>();
        TOWarnReceiveEntity receive = new TOWarnReceiveEntity();
        receive.setToWarn(warn);
        receive.setReceiveUserid(user.getId());
        receive.setReceiveUsername(user.getRealName());
        tOWarnReceiveList.add(receive);
        this.tOWarnService.addMain(warn, tOWarnReceiveList);
    }

    @Override
    public void deleteSchedule(TOScheduleEntity tOSchedule) {
        List<TOWarnEntity> warnList = this.commonDao.findByProperty(TOWarnEntity.class, "sourceId", tOSchedule.getId());
        for (TOWarnEntity warn : warnList) {
            this.commonDao.delete(warn);
        }
        this.commonDao.delete(tOSchedule);
    }

    @Override
    public void doResponse(TOScheduleEntity tOSchedule, String resContent, String type) {
        tOSchedule = this.commonDao.get(TOScheduleEntity.class, tOSchedule.getId());
        TOResponseEntity response = new TOResponseEntity();
        TSUser user = ResourceUtil.getSessionUserName();
        response.setResUserId(user.getId());
        response.setResUserName(user.getRealName());
        response.setResContent(resContent);
        response.setResTime(new Date());
        response.setResType("schedule");
        String sourceId = null;
        if ("sender".equals(type)) {
            sourceId = tOSchedule.getId();
        } else {
            sourceId = tOSchedule.getParentId();
        }
        response.setResSourceId(sourceId);
        this.commonDao.save(response);
        if ("receiver".equals(type)) {
        TOScheduleEntity parentSchedule = this.commonDao.get(TOScheduleEntity.class, tOSchedule.getParentId());
        String messageTitle = tOSchedule.getTitle();
        String messageType = "日程安排";
        String messageContent = "您发送的日程安排已被回复！标题：" + tOSchedule.getTitle() + "，回复人:" + response.getResUserName()
                + "，回复时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        commonMessageService.sendMessage(parentSchedule.getUserId(), messageType, messageTitle, messageContent);
        }
    }

    @Override
    public void doLogicDelete(String ids) {
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            TOScheduleEntity tmp = this.commonDao.get(TOScheduleEntity.class, idArr[i]);
            tmp.setDeleteFlag("1");
            this.commonDao.updateEntitie(tmp);
            List<TOWarnEntity> warnList = this.commonDao.findByProperty(TOWarnEntity.class, "sourceId", idArr[i]);
            for (TOWarnEntity warn : warnList) {
                warn.setWarnStatus("2");//将关联的提醒置为作废状态
                this.commonDao.updateEntitie(warn);
            }
        }
    }

    @Override
    public void doFinish(TOScheduleEntity tOSchedule) {
        TOScheduleEntity t = commonDao.getEntity(TOScheduleEntity.class, tOSchedule.getId());
        t.setFinishedFlag(tOSchedule.getFinishedFlag());
        if ("1".equals(tOSchedule.getFinishedFlag())) {
            t.setColor("05fa15");//完成设置为绿色
        } else {
            t.setColor("2f96b4");//取消完成设置为蓝色
        }
        commonDao.updateEntitie(t);
        CriteriaQuery cq = new CriteriaQuery(TOWarnEntity.class);
        cq.eq("sourceId", tOSchedule.getId());
        cq.add();
        List<TOWarnEntity> warnList = this.commonDao.getListByCriteriaQuery(cq, false);
        for (TOWarnEntity warn : warnList) {
            warn.setWarnStatus("1");//已完成
            this.commonDao.updateEntitie(warn);
        }
    }

    /**
     * 恢复
     */
    @Override
    public void doBack(String ids) {
        String[] idArr = ids.split(",");
        for (int i = 0; i < idArr.length; i++) {
            TOScheduleEntity tmp = this.commonDao.get(TOScheduleEntity.class, idArr[i]);
            tmp.setDeleteFlag("0");
            this.commonDao.updateEntitie(tmp);
            List<TOWarnEntity> warnList = this.commonDao.findByProperty(TOWarnEntity.class, "sourceId", idArr[i]);
            for (TOWarnEntity warn : warnList) {
                warn.setWarnStatus("0");//将关联的提醒置为作废状态
                this.commonDao.updateEntitie(warn);
            }
        }
    }
}