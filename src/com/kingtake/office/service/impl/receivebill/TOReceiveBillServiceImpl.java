package com.kingtake.office.service.impl.receivebill;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.entity.flow.TOFlowReceiveLogsEntity;
import com.kingtake.office.entity.flow.TOFlowSendLogsEntity;
import com.kingtake.office.entity.receivebill.TOReceiveBillEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.office.service.receivebill.TOReceiveBillServiceI;
import com.kingtake.office.service.sendReceive.TOSendReceiveRegServiceI;

@Service("tOReceiveBillService")
@Transactional
public class TOReceiveBillServiceImpl extends CommonServiceImpl implements TOReceiveBillServiceI {

    @Autowired
    private TOSendReceiveRegServiceI regService;

    @Autowired
    private TOMessageServiceI tOMessageService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOReceiveBillEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOReceiveBillEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOReceiveBillEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOReceiveBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOReceiveBillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOReceiveBillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOReceiveBillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{receive_unit_id}",String.valueOf(t.getReceiveUnitId()));
 		sql  = sql.replace("#{receive_unit_name}",String.valueOf(t.getReceiveUnitName()));
 		sql  = sql.replace("#{bill_num}",String.valueOf(t.getBillNum()));
 		sql  = sql.replace("#{secrity_grade}",String.valueOf(t.getSecrityGrade()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{leader_review}",String.valueOf(t.getLeaderReview()));
 		sql  = sql.replace("#{office_review}",String.valueOf(t.getOfficeReview()));
 		sql  = sql.replace("#{duty_opinion}",String.valueOf(t.getDutyOpinion()));
 		sql  = sql.replace("#{duty_name}",String.valueOf(t.getDutyName()));
 		sql  = sql.replace("#{contact_id}",String.valueOf(t.getContactId()));
 		sql  = sql.replace("#{contact_name}",String.valueOf(t.getContactName()));
 		sql  = sql.replace("#{contact_tel}",String.valueOf(t.getContactTel()));
 		sql  = sql.replace("#{register_time}",String.valueOf(t.getRegisterTime()));
 		sql  = sql.replace("#{register_id}",String.valueOf(t.getRegisterId()));
 		sql  = sql.replace("#{register_name}",String.valueOf(t.getRegisterName()));
 		sql  = sql.replace("#{register_depart_id}",String.valueOf(t.getRegisterDepartId()));
 		sql  = sql.replace("#{register_depart_name}",String.valueOf(t.getRegisterDepartName()));
 		sql  = sql.replace("#{archive_flag}",String.valueOf(t.getArchiveFlag()));
 		sql  = sql.replace("#{archive_userid}",String.valueOf(t.getArchiveUserid()));
 		sql  = sql.replace("#{archive_username}",String.valueOf(t.getArchiveUsername()));
 		sql  = sql.replace("#{archive_date}",String.valueOf(t.getArchiveDate()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void addReceiveBill(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        commonDao.save(tOReceiveBill);
        TSUser user = ResourceUtil.getSessionUserName();
        if (StringUtil.isNotEmpty(tOReceiveBill.getRegId())) {
            TOSendReceiveRegEntity reg = commonDao.get(TOSendReceiveRegEntity.class, tOReceiveBill.getRegId());
            reg.setGenerationFlag(ReceiveBillConstant.BILL_FLOWING);
            commonDao.updateEntitie(reg);
            regService.copyFile(reg.getId(), tOReceiveBill.getId(), "tOReceiveBill");
            //            TOFlowSendLogsEntity sendLog = new TOFlowSendLogsEntity();
            //            sendLog.setSendReceiveId(tOReceiveBill.getId());
            //            sendLog.setOperateUsername(reg.getCreateName());
            //            sendLog.setOperateUserid(user.getId());
            //            sendLog.setOperateDate(new Date());
            //            sendLog.setSenderTip("登记");
            //            this.commonDao.save(sendLog);
            //            TOFlowReceiveLogsEntity receiveLog = new TOFlowReceiveLogsEntity();
            //            receiveLog.setSendReceiveId(tOReceiveBill.getId());
            //            receiveLog.setSignInTime(reg.getRegisterDate());
            //            receiveLog.setToFlowSendId(sendLog.getId());
            //            this.commonDao.save(receiveLog);
        }
        sendToContact(tOReceiveBill, request);
    }

    /**
     * 发送信息给接收人
     * 
     * @param tOReceiveBill
     * @param request
     */
    private void sendToContact(TOReceiveBillEntity tOReceiveBill, HttpServletRequest request) {
        //发送信息
        TOFlowSendLogsEntity send = new TOFlowSendLogsEntity();
        TSUser user = ResourceUtil.getSessionUserName();
        send.setSendReceiveId(tOReceiveBill.getId());
        send.setOperateDate(new Date());
        send.setOperateDepartid(user.getCurrentDepart().getId());
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        send.setOperateIp(ip);
        send.setOperateUserid(user.getId());
        send.setOperateUsername(user.getRealName());
        send.setSenderTip("登记");
        commonDao.save(send);
        //接收信息
        TOFlowReceiveLogsEntity receive = new TOFlowReceiveLogsEntity();
        receive.setOperateStatus(SrmipConstants.NO);
        receive.setReceiveDepartid(tOReceiveBill.getDutyId());
        receive.setReceiveDepartname(tOReceiveBill.getDutyName());
        receive.setReceiveUserid(tOReceiveBill.getContactId());
        receive.setReceiveUsername(tOReceiveBill.getContactName());
        receive.setSendReceiveId(tOReceiveBill.getId());
        receive.setToFlowSendId(send.getId());
        receive.setValidFlag(SrmipConstants.YES);
        TOSendReceiveRegEntity reg = commonDao.get(TOSendReceiveRegEntity.class, tOReceiveBill.getRegId());
        if (reg != null) {
            receive.setSignInTime(reg.getRegisterDate());
        }
        commonDao.save(receive);
        //发送消息给联系人
        tOMessageService.sendMessage(receive.getReceiveUserid(), "您有新的收文阅批单需要处理！", "收文",
                "您有新的收文阅批单需要处理！请到协同办公->收文管理中办理！", user.getId());
    }
}