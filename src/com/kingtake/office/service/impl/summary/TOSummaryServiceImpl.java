package com.kingtake.office.service.impl.summary;

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
import com.kingtake.office.entity.researchactivity.TOReceiveLogEntity;
import com.kingtake.office.entity.summary.TOSummaryEntity;
import com.kingtake.office.service.summary.TOSummaryServiceI;

@Service("tOSummaryService")
@Transactional
public class TOSummaryServiceImpl extends CommonServiceImpl implements TOSummaryServiceI {

    @Autowired
    private CommonMessageServiceI commonMessageService;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
        this.doDelSql((TOSummaryEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
        this.doAddSql((TOSummaryEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
        this.doUpdateSql((TOSummaryEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TOSummaryEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TOSummaryEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TOSummaryEntity t) {
	 	return true;
 	}

    @Override
    public void doSubmit(TOSummaryEntity tOSummary, String receiverIds, String receiverNames) {
        TSUser user = ResourceUtil.getSessionUserName();
        String[] receiverIdArr = receiverIds.split(",");
        String[] receiverNameArr = receiverNames.split(",");
        tOSummary = this.commonDao.get(TOSummaryEntity.class, tOSummary.getId());
        tOSummary.setSubmitFlag(SrmipConstants.SUBMIT_YES);
        tOSummary.setSubmitTime(DateUtils.getDate());
        this.commonDao.updateEntitie(tOSummary);
        for (int i = 0; i < receiverIdArr.length; i++) {
            CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
            cq.eq("researchId", tOSummary.getId());
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
            receiveLog.setResearchId(tOSummary.getId());
            receiveLog.setSendTime(new Date());
            receiveLog.setSenderId(user.getId());
            receiveLog.setSenderName(user.getRealName());
            receiveLog.setSendType(SrmipConstants.SEND_TYPE_SUMMARY);
            this.commonDao.save(receiveLog);
            String messageTitle = tOSummary.getTitle();
            String messageType = "总结材料";
            String messageContent = "您有一条新的总结材料！标题：" + tOSummary.getTitle() + ",发送人:" + receiveLog.getSenderName()
                    + ",发送时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            commonMessageService.sendMessage(receiveLog.getReceiverId(), messageType, messageTitle, messageContent);
        }
    }

    @Override
    public void doReceive(TOSummaryEntity tOSummary) {
        TSUser user = ResourceUtil.getSessionUserName();
        tOSummary = this.commonDao.get(TOSummaryEntity.class, tOSummary.getId());
        CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
        cq.eq("researchId", tOSummary.getId());
        cq.eq("receiverId", user.getId());
        cq.eq("sendType", SrmipConstants.SEND_TYPE_SUMMARY);
        cq.add();
        List<TOReceiveLogEntity> receiveLogList = this.commonDao.getListByCriteriaQuery(cq, false);
        if(receiveLogList.size()>0){
            for(TOReceiveLogEntity log :receiveLogList){
                log.setReceiveFlag("1");//设置为已接收
                log.setReceiveTime(new Date());
                this.commonDao.updateEntitie(log);
                String messageType = "总结材料";
                String messageTitle = tOSummary.getTitle();
                String messageContent = "您发送的总结材料已被处理！标题：" + tOSummary.getTitle() + ",接收人:" + log.getReceiverName()
                        + ",处理时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                commonMessageService.sendMessage(log.getSenderId(), messageType, messageTitle, messageContent);
            }
        }
        //检查是否还有未接收的记录
        CriteriaQuery logCq = new CriteriaQuery(TOReceiveLogEntity.class);
        logCq.eq("researchId", tOSummary.getId());
        logCq.eq("receiveFlag", "0");
        logCq.eq("sendType", SrmipConstants.SEND_TYPE_SUMMARY);
        logCq.add();
        List<TOReceiveLogEntity> logList = this.commonDao.getListByCriteriaQuery(logCq, false);
        if (logList.size() == 0) {
            tOSummary.setSubmitFlag("3");
            this.commonDao.updateEntitie(tOSummary);
        }
    }

    /**
     * 隐藏
     */
    @Override
    public void doLogicDelete(String ids) {
        for (String id : ids.split(",")) {
            TOSummaryEntity summary = commonDao.getEntity(TOSummaryEntity.class, id);
            summary.setDelFlag("1");
            this.commonDao.updateEntitie(summary);
        }
    }

    /**
     * 恢复
     */
    @Override
    public void doBack(String ids) {
        for (String id : ids.split(",")) {
            TOSummaryEntity summary = commonDao.getEntity(TOSummaryEntity.class, id);
            summary.setDelFlag("0");
            this.commonDao.updateEntitie(summary);
        }
    }
 	
}