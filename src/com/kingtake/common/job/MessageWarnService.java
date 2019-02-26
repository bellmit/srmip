package com.kingtake.common.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingtake.base.entity.warnmessage.TBWarnEntity;
import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.service.message.TOMessageServiceI;

@Service("messageWarnService")
public class MessageWarnService implements WarnService {
    @Autowired
    private SystemService systemService;

    @Autowired
    private TOMessageServiceI tOMessageService;

    //发送消息
    public void sendMessage(TBWarnEntity entity) {
        TOMessageEntity message = new TOMessageEntity();
        message.setSendTime(new Date());
        message.setSenderName(entity.getCreateName());
        TSBaseUser user = queryUser(entity.getCreateBy());
        if (user != null) {
            message.setSenderId(user.getId());
        }
        message.setTitle(entity.getWarnProperty().getBusinessname());
        StringBuffer sb = new StringBuffer();
        String dateStr = DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
        String title = entity.getWarnProperty().getBusinessname() + dateStr;
        String content = "提醒内容";
        if (StringUtils.isNotEmpty(entity.getWarnContent())) {
            content = entity.getWarnContent();
        }
        sb.append("<a href=\"#\" onclick='addTab(\"" + title + "\",\"" + entity.getWarnProperty().getUrl()
                + "\");' style=\"color:red\">" + content + "</a><br/>");
        message.setContent(sb.toString());
        String sql = entity.getWarnProperty().getSqlstr();
        List<Map<String, Object>> resultList = null;
        try {
            resultList = this.systemService.findForJdbc(sql);
        } catch (Exception e) {
            throw new BusinessException("查询提醒接收人失败", e);
        }
        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
        for (Map<String, Object> map : resultList) {
            TOMessageReadEntity receiveEntity = new TOMessageReadEntity();
            receiveEntity.setReadFlag("0");
            receiveEntity.setDelFlag("0");
            receiveEntity.setShow("0");
            String userId = (String) map.get("userId");
            if (StringUtils.isNotEmpty(userId)) {
            TSUser userTmp = this.systemService.get(TSUser.class, userId);
                receiveEntity.setReceiverId(userId);
                receiveEntity.setReceiverName(userTmp.getRealName());
            }
            receiveEntity.setToid(entity.getId());
            tOMessageReadList.add(receiveEntity);
        }

        if (tOMessageReadList.size() > 0) {
            this.tOMessageService.addMain(message, tOMessageReadList);
        }
    }

    /**
     * 根据用户名查找用户
     * 
     * @param userName
     * @return
     */
    private TSBaseUser queryUser(String userName) {
        TSBaseUser user = null;
        CriteriaQuery cq = new CriteriaQuery(TSBaseUser.class);
        cq.eq("userName", userName);
        cq.add();
        List<TSBaseUser> userList = this.tOMessageService.getListByCriteriaQuery(cq, false);
        if (userList.size() > 0) {
            user = userList.get(0);
        }
        return user;
    }

    @Override
    public void sendMessage(TOWarnEntity entity) {
        // TODO Auto-generated method stub

    }
}
