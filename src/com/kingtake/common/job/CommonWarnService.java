package com.kingtake.common.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingtake.base.entity.warnmessage.TBWarnEntity;
import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.entity.warn.TOWarnReceiveEntity;
import com.kingtake.office.service.message.TOMessageServiceI;

@Service("commonWarnService")
public class CommonWarnService implements WarnService {
    @Autowired
    private TOMessageServiceI tOMessageService;

    @Autowired
    private SystemService systemService;

    //发送消息
    public void sendMessage(TOWarnEntity entity) {
        TOMessageEntity message = new TOMessageEntity();
        message.setSendTime(new Date());
        message.setSenderName(entity.getCreateName());
        TSBaseUser user = queryUser(entity.getCreateBy());
        if (user != null) {
            message.setSenderId(user.getId());
        }

        message.setTitle("公共提醒");
        message.setType("公共提醒");
        String content = "提醒内容";
        if (StringUtils.isNotEmpty(entity.getWarnContent())) {
            content = entity.getWarnContent();
        }
        message.setContent(content);
        List<TOWarnReceiveEntity> receiveList = entity.getReceiveList();
        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
        for (TOWarnReceiveEntity receive : receiveList) {
            if ("0".equals(receive.getFinishFlag()) || receive.getFinishFlag() == null) {
                TOMessageReadEntity receiveEntity = new TOMessageReadEntity();
                receiveEntity.setReadFlag("0");
                receiveEntity.setDelFlag("0");
                receiveEntity.setShow("0");
                receiveEntity.setReceiverId(receive.getReceiveUserid());
                receiveEntity.setReceiverName(receive.getReceiveUsername());
                receiveEntity.setToid(entity.getId());
                tOMessageReadList.add(receiveEntity);
            }
        }
        if (tOMessageReadList.size() > 0) {
            this.tOMessageService.addMain(message, tOMessageReadList);
            if ("1".equals(entity.getWarnFrequency())) {//如果只提醒一次，则提醒完之后要将状态设置为已完成
                entity.setWarnStatus("1");
                this.systemService.updateEntitie(entity);
            }
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
    public void sendMessage(TBWarnEntity entity) {
        // TODO Auto-generated method stub
        
    }
}
