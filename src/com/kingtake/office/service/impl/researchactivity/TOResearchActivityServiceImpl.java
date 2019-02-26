package com.kingtake.office.service.impl.researchactivity;

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
import com.kingtake.office.entity.researchactivity.TOResearchActivityEntity;
import com.kingtake.office.service.researchactivity.TOResearchActivityServiceI;

@Service("tOResearchActivityService")
@Transactional
public class TOResearchActivityServiceImpl extends CommonServiceImpl implements TOResearchActivityServiceI {

    @Autowired
    private CommonMessageServiceI commonMessageService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
        this.doDelSql((TOResearchActivityEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
        this.doAddSql((TOResearchActivityEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
        this.doUpdateSql((TOResearchActivityEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TOResearchActivityEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TOResearchActivityEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TOResearchActivityEntity t) {
	 	return true;
 	}

    @Override
    public void doSubmit(TOResearchActivityEntity tOResearchActivity, String receiverIds, String receiverNames) {
        TSUser user = ResourceUtil.getSessionUserName();
        String[] receiverIdArr = receiverIds.split(",");
        String[] receiverNameArr = receiverNames.split(",");
        tOResearchActivity = this.commonDao.get(TOResearchActivityEntity.class, tOResearchActivity.getId());
        tOResearchActivity.setSubmitFlag(SrmipConstants.SUBMIT_YES);
        tOResearchActivity.setSubmitTime(DateUtils.getDate());
        this.commonDao.updateEntitie(tOResearchActivity);
        for (int i = 0; i < receiverIdArr.length; i++) {
            CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
            cq.eq("researchId", tOResearchActivity.getId());
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
            receiveLog.setResearchId(tOResearchActivity.getId());
            receiveLog.setSendTime(new Date());
            receiveLog.setSenderId(user.getId());
            receiveLog.setSenderName(user.getRealName());
            receiveLog.setSendType(SrmipConstants.SEND_TYPE_RESEARCHACTIVITY);
            this.commonDao.save(receiveLog);
            String messageType = "科研动态";
            String messageTitle = tOResearchActivity.getTitle();
            String messageContent = "您有一条新的科研动态！标题：" + tOResearchActivity.getTitle() + ",发送人:"
                    + receiveLog.getSenderName()
 + ",发送时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            commonMessageService.sendMessage(receiveLog.getReceiverId(), messageType, messageTitle, messageContent);
        }
    }

    @Override
    public void doReceive(TOResearchActivityEntity tOResearchActivity) {
        TSUser user = ResourceUtil.getSessionUserName();
        tOResearchActivity = this.commonDao.get(TOResearchActivityEntity.class, tOResearchActivity.getId());
        CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
        cq.eq("researchId", tOResearchActivity.getId());
        cq.eq("receiverId", user.getId());
        cq.eq("sendType", SrmipConstants.SEND_TYPE_RESEARCHACTIVITY);
        cq.add();
        List<TOReceiveLogEntity> receiveLogList = this.commonDao.getListByCriteriaQuery(cq, false);
        if(receiveLogList.size()>0){
            for(TOReceiveLogEntity log :receiveLogList){
                log.setReceiveFlag("1");//设置为已接收
                log.setReceiveTime(new Date());
                this.commonDao.updateEntitie(log);
                String messageType = "科研动态";
                String messageTitle = tOResearchActivity.getTitle();
                String messageContent = "您发送的科研动态已被处理！标题：" + tOResearchActivity.getTitle() + ",接收人:"
                        + log.getReceiverName() + ",处理时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                commonMessageService.sendMessage(log.getSenderId(), messageType, messageTitle, messageContent);
            }
        }
        //检查是否还有未接收的记录
        CriteriaQuery logCq = new CriteriaQuery(TOReceiveLogEntity.class);
        logCq.eq("researchId", tOResearchActivity.getId());
        logCq.eq("receiveFlag", "0");
        logCq.eq("sendType", SrmipConstants.SEND_TYPE_RESEARCHACTIVITY);
        logCq.add();
        List<TOReceiveLogEntity> logList = this.commonDao.getListByCriteriaQuery(logCq, false);
        if (logList.size() == 0) {
            tOResearchActivity.setSubmitFlag("3");
            this.commonDao.updateEntitie(tOResearchActivity);
        }
    }
 	
}