package com.kingtake.office.service.impl.information;
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
import com.kingtake.office.entity.information.TOHandoverEntity;
import com.kingtake.office.service.information.TOHandoverServiceI;

@Service("tOHandoverService")
@Transactional
public class TOHandoverServiceImpl extends CommonServiceImpl implements TOHandoverServiceI {
    
    @Autowired
    private CommonMessageServiceI commonMessageService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOHandoverEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOHandoverEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOHandoverEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOHandoverEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOHandoverEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOHandoverEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOHandoverEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{handover_id}",String.valueOf(t.getHandoverId()));
 		sql  = sql.replace("#{handover_name}",String.valueOf(t.getHandoverName()));
 		sql  = sql.replace("#{handover_time}",String.valueOf(t.getHandoverTime()));
 		sql  = sql.replace("#{handover_content}",String.valueOf(t.getHandoverContent()));
 		sql  = sql.replace("#{receiver}",String.valueOf(t.getReceiver()));
 		sql  = sql.replace("#{recieve_time}",String.valueOf(t.getRecieveTime()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{submit_flag}",String.valueOf(t.getSubmitFlag()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void doSubmit(TOHandoverEntity tOHandover) {
        try {
            TOHandoverEntity t = commonDao.get(TOHandoverEntity.class, tOHandover.getId());
            // 防止更新时附件丢失
            MyBeanUtils.copyBeanNotNull2Bean(tOHandover, t);
            //点击提交时，更改交班状态：1-已提交；状态变更时间：当前时间
            t.setSubmitFlag(SrmipConstants.SUBMIT_YES);
            t.setSubmitTime(DateUtils.getDate());
            commonDao.updateEntitie(t);
            
            TSUser user = ResourceUtil.getSessionUserName();
            String[] receiverIdArr = t.getReceiver().split(",");
            //            String[] receiverNameArr = t.getReceiverName().split(",");
            for (int i = 0; i < receiverIdArr.length; i++) {
                String receiverId = receiverIdArr[i];
                //                String receiverName = receiverNameArr[i];
                String messageType = "交班材料";
                String messageTitle = "交班材料";
                String messageContent = "您有一个交班材料待接收！内容：" + t.getHandoverContent() + "，发送人:" + user.getRealName()
                        + "，发送时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                commonMessageService.sendMessage(receiverId, messageType, messageTitle, messageContent);
            }
        } catch (Exception e) {
            throw new BusinessException("提交交班材料失败！");
        }
    }
}