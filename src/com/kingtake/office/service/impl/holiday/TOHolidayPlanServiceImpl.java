package com.kingtake.office.service.impl.holiday;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
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
import com.kingtake.office.entity.holiday.TOHolidayPlanEntity;
import com.kingtake.office.entity.researchactivity.TOReceiveLogEntity;
import com.kingtake.office.service.holiday.TOHolidayPlanServiceI;

@Service("tOHolidayPlanService")
@Transactional
public class TOHolidayPlanServiceImpl extends CommonServiceImpl implements TOHolidayPlanServiceI {

    @Autowired
    private CommonMessageServiceI commonMessageService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TOHolidayPlanEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TOHolidayPlanEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TOHolidayPlanEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOHolidayPlanEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOHolidayPlanEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOHolidayPlanEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TOHolidayPlanEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{holiday_code}",String.valueOf(t.getHolidayCode()));
 		sql  = sql.replace("#{dept_id}",String.valueOf(t.getDeptId()));
 		sql  = sql.replace("#{dept_name}",String.valueOf(t.getDeptName()));
 		sql  = sql.replace("#{plan_content}",String.valueOf(t.getPlanContent()));
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
    public void doSubmit(TOHolidayPlanEntity holidayPlan) {
        try {
            TOHolidayPlanEntity t = this.commonDao.get(TOHolidayPlanEntity.class, holidayPlan.getId());
            // 防止更新时附件丢失
            MyBeanUtils.copyBeanNotNull2Bean(holidayPlan, t);
            //点击提交时，更改交班状态：1-已提交；状态变更时间：当前时间
            t.setSubmitFlag(SrmipConstants.SUBMIT_YES);
            t.setSubmitTime(DateUtils.getDate());
            commonDao.updateEntitie(t);
            
            TSUser user = ResourceUtil.getSessionUserName();
            String[] receiverIdArr = t.getReceiverId().split(",");
            String[] receiverNameArr = t.getReceiverName().split(",");
            for (int i = 0; i < receiverIdArr.length; i++) {
                CriteriaQuery cq = new CriteriaQuery(TOReceiveLogEntity.class);
                cq.eq("researchId", holidayPlan.getId());
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
                receiveLog.setResearchId(t.getId());
                receiveLog.setSendTime(new Date());
                receiveLog.setSenderId(user.getId());
                receiveLog.setSenderName(user.getRealName());
                receiveLog.setSendType(SrmipConstants.SEND_TYPE_HOLIDAY);
                this.commonDao.save(receiveLog);
                String messageType = "假前工作计划";
                String messageTitle = t.getTitle();
                String messageContent = "您有一条新的假前工作计划待接收！内容：" + t.getPlanContent() + "，发送人:" + receiveLog.getSenderName()
                        + "，发送时间：" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                commonMessageService.sendMessage(receiveLog.getReceiverId(), messageType, messageTitle, messageContent);
            }
        } catch (Exception e) {
            throw new BusinessException("提交假前工作计划失败！", e);
        }
    }
}