package com.kingtake.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rtx.RTXSvrApi;
import rtx.RTXSvrApiContainer;

import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.service.message.TOMessageServiceI;

@Service("commonMessageService")
@Transactional
public class CommonMessageServiceImpl extends CommonServiceImpl implements CommonMessageServiceI {

    @Autowired
    private TOMessageServiceI tOMessageService;

    private static final Log log = LogFactory.getLog(CommonMessageServiceImpl.class);

    @Override
    public void sendMessage(String userIds, String type, String title, String content) {
        TSUser user = ResourceUtil.getSessionUserName();
        TOMessageEntity message = new TOMessageEntity();
        message.setType(type);
        message.setSendTime(new Date());
        if (user != null) {
            message.setSenderId(user.getId());
            message.setSenderName(user.getRealName());
        }
        message.setTitle(title);
        message.setContent(content);
        String[] userIdArr = userIds.split(",");
        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
        for (String userId : userIdArr) {
            TOMessageReadEntity receiveEntity = new TOMessageReadEntity();
            receiveEntity.setReadFlag("0");
            receiveEntity.setDelFlag("0");
            receiveEntity.setShow("0");
            receiveEntity.setReceiverId(userId);
            TSUser receiveUser = this.commonDao.get(TSUser.class, userId);
            receiveEntity.setReceiverName(receiveUser.getRealName());
            tOMessageReadList.add(receiveEntity);
            try{
              sendMsgRtx(receiveUser.getUserName(), title, content);
            }catch(Exception e){
              e.printStackTrace();
            }
        }
        if (tOMessageReadList.size() > 0) {
            this.tOMessageService.addMain(message, tOMessageReadList);
        }
    }

    /**
     * 发送rtx提醒
     * 
     * @param userName
     * @param title
     * @param content
     */
    private void sendMsgRtx(String userName, String title, String content)throws Exception {
        RTXSvrApi rtxApi = RTXSvrApiContainer.getRtxApiInstance();
        try {
            rtxApi.sendNotify(userName, title, content, "0", "0");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送rtx提醒失败", e);
        }
    }

}