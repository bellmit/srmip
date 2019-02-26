package com.kingtake.office.service.impl.workpoint;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.office.entity.researchactivity.TOReceiveLogEntity;
import com.kingtake.office.entity.workpoint.TOWorkPointEntity;
import com.kingtake.office.service.workpoint.TOWorkPointServiceI;

@Service("tOWorkPointService")
@Transactional
public class TOWorkPointServiceImpl extends CommonServiceImpl implements TOWorkPointServiceI {

    @Autowired
    private CommonMessageServiceI commonMessageService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOWorkPointEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOWorkPointEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOWorkPointEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOWorkPointEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOWorkPointEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOWorkPointEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOWorkPointEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{time}",String.valueOf(t.getTime()));
 		sql  = sql.replace("#{work_content}",String.valueOf(t.getWorkContent()));
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
    public void doSubmit(TOWorkPointEntity tOWorkPoint) {
        try {
        TOWorkPointEntity t = commonDao.get(TOWorkPointEntity.class, tOWorkPoint.getId());
        // 防止更新时附件丢失
        MyBeanUtils.copyBeanNotNull2Bean(tOWorkPoint, t);
        //点击提交时，更改交班状态：1-已提交；状态变更时间：当前时间
        t.setSubmitFlag(SrmipConstants.SUBMIT_YES);
        t.setSubmitTime(DateUtils.getDate());
            commonDao.updateEntitie(t);

            TSUser user = ResourceUtil.getSessionUserName();
            String[] receiverIdArr = t.getReceiverId().split(",");
            String[] receiverNameArr = t.getReceiverName().split(",");
            for (int i = 0; i < receiverIdArr.length; i++) {
                String receiverId = receiverIdArr[i];
                String receiverName = receiverNameArr[i];
                TOReceiveLogEntity receiveLog = new TOReceiveLogEntity();
                receiveLog.setReceiveFlag("0");//初始值为未接收
                receiveLog.setReceiverId(receiverId);
                receiveLog.setReceiverName(receiverName);
                receiveLog.setResearchId(t.getId());
                receiveLog.setSendTime(new Date());
                receiveLog.setSenderId(user.getId());
                receiveLog.setSenderName(user.getRealName());
                receiveLog.setSendType(SrmipConstants.SEND_TYPE_SUMMARY);
                this.commonDao.save(receiveLog);
                String messageType = "工作要点";
                String messageTitle = "工作要点";
                String messageContent = "您有一条新的工作要点待接收！内容：" + t.getWorkContent() + "，发送人:" + receiveLog.getSenderName()
                        + "，发送时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                commonMessageService.sendMessage(receiveLog.getReceiverId(), messageType, messageTitle, messageContent);
            }
        } catch (Exception e) {
            throw new BusinessException("提交工作要点失败！", e);
        }
    }
}