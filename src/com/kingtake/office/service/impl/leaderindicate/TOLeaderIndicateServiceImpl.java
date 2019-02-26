package com.kingtake.office.service.impl.leaderindicate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.office.entity.leaderindicate.TOLeaderIndicateEntity;
import com.kingtake.office.entity.researchactivity.TOReceiveLogEntity;
import com.kingtake.office.service.leaderindicate.TOLeaderIndicateServiceI;

@Service("tOLeaderIndicateService")
@Transactional
public class TOLeaderIndicateServiceImpl extends CommonServiceImpl implements TOLeaderIndicateServiceI {

    @Autowired
    private CommonMessageServiceI commonMessageService;
	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
        this.doDelSql((TOLeaderIndicateEntity) entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
        this.doAddSql((TOLeaderIndicateEntity) entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
        this.doUpdateSql((TOLeaderIndicateEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    @Override
    public boolean doAddSql(TOLeaderIndicateEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    @Override
    public boolean doUpdateSql(TOLeaderIndicateEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    @Override
    public boolean doDelSql(TOLeaderIndicateEntity t) {
	 	return true;
 	}

    @Override
    public void doSubmit(TOLeaderIndicateEntity tOLeaderIndicate, String receiverIds, String receiverNames) {
        TSUser user = ResourceUtil.getSessionUserName();
        String[] receiverIdArr = receiverIds.split(",");
        String[] receiverNameArr = receiverNames.split(",");
        tOLeaderIndicate = this.commonDao.get(TOLeaderIndicateEntity.class, tOLeaderIndicate.getId());
        tOLeaderIndicate.setSubmitFlag(SrmipConstants.SUBMIT_YES);
        tOLeaderIndicate.setSubmitTime(DateUtils.getDate());
        this.commonDao.updateEntitie(tOLeaderIndicate);
        for (int i = 0; i < receiverIdArr.length; i++) {
            CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
            cq.eq("researchId", tOLeaderIndicate.getId());
            cq.eq("senderId", user.getId());
            cq.eq("receiverId", receiverIdArr[i]);
            cq.add();
            List<TOReceiveLogEntity> receiveLogs = this.commonDao.getListByCriteriaQuery(cq, false);
            if (receiveLogs.size() > 0) {
                continue;
            }
            String receiverId = receiverIdArr[i];
            String receiverName = receiverNameArr[i];
            TOReceiveLogEntity receiveLog = new TOReceiveLogEntity();
            receiveLog.setReceiveFlag("0");//初始值为未接收
            receiveLog.setReceiverId(receiverId);
            receiveLog.setReceiverName(receiverName);
            receiveLog.setResearchId(tOLeaderIndicate.getId());
            receiveLog.setSendTime(new Date());
            receiveLog.setSenderId(user.getId());
            receiveLog.setSenderName(user.getRealName());
            receiveLog.setSendType(SrmipConstants.SEND_TYPE_LEADERINDICATE);
            this.commonDao.save(receiveLog);
            String messageTitle = tOLeaderIndicate.getTitle();
            String messageType = "校首长批示";
            String messageContent = "您有一条新的校首长批示！标题：" + tOLeaderIndicate.getTitle() + "，发送人："
                    + receiveLog.getSenderName() + "，发送时间："
                    + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            commonMessageService.sendMessage(receiveLog.getReceiverId(), messageType, messageTitle, messageContent);
        }
    }

    @Override
    public void doReceive(TOLeaderIndicateEntity tOLeaderIndicate) {
        TSUser user = ResourceUtil.getSessionUserName();
        tOLeaderIndicate = this.commonDao.get(TOLeaderIndicateEntity.class, tOLeaderIndicate.getId());
        CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
        cq.eq("researchId", tOLeaderIndicate.getId());
        cq.eq("receiverId", user.getId());
        cq.eq("sendType", SrmipConstants.SEND_TYPE_LEADERINDICATE);
        cq.add();
        List<TOReceiveLogEntity> receiveLogList = this.commonDao.getListByCriteriaQuery(cq, false);
        if(receiveLogList.size()>0){
            for(TOReceiveLogEntity log :receiveLogList){
                log.setReceiveFlag("1");//设置为已接收
                log.setReceiveTime(new Date());
                this.commonDao.updateEntitie(log);
                String messageTitle = tOLeaderIndicate.getTitle();
                String messageType = "校首长批示";
                String messageContent = "您发送的校首长批示已被处理！标题：" + tOLeaderIndicate.getTitle() + "，接收人:"
                        + log.getReceiverName() + "，处理时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                commonMessageService.sendMessage(log.getSenderId(), messageType, messageTitle, messageContent);
            }
        }
        //检查是否还有未接收的记录
        CriteriaQuery logCq = new CriteriaQuery(TOReceiveLogEntity.class);
        logCq.eq("researchId", tOLeaderIndicate.getId());
        logCq.eq("receiveFlag", "0");
        logCq.eq("sendType", SrmipConstants.SEND_TYPE_LEADERINDICATE);
        logCq.add();
        List<TOReceiveLogEntity> logList = this.commonDao.getListByCriteriaQuery(logCq, false);
        if (logList.size() == 0) {
            tOLeaderIndicate.setSubmitFlag("3");
            this.commonDao.updateEntitie(tOLeaderIndicate);
        }
    }
 	
}