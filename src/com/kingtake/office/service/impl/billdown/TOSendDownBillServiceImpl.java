package com.kingtake.office.service.impl.billdown;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.TSFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.approval.TOApprovalEntity;
import com.kingtake.office.entity.billdown.TOSendDownBillEntity;
import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.entity.sendbill.TOSendBillEntity;
import com.kingtake.office.service.billdown.TOSendDownBillServiceI;

@Service("tOSendDownBillService")
@Transactional
public class TOSendDownBillServiceImpl extends CommonServiceImpl implements TOSendDownBillServiceI {

    @Autowired
    private TSFilesService tsFilesService;

    @Override
    public boolean doAddSql(TOSendDownBillEntity t) {
        return false;
    }

    @Override
    public boolean doUpdateSql(TOSendDownBillEntity t) {
        return false;
    }

    @Override
    public boolean doDelSql(TOSendDownBillEntity t) {
        return false;
    }

    @Override
    public void doSubmit(TOSendDownBillEntity tOSendDownBill, String receiverId, String receiverName, String showFlag) {
        String sourceId = null;
        TSUser user = ResourceUtil.getSessionUserName();
        if (StringUtils.isNotEmpty(tOSendDownBill.getId())) {
            tOSendDownBill = this.commonDao.get(TOSendDownBillEntity.class, tOSendDownBill.getId());
            sourceId = tOSendDownBill.getId();
            tOSendDownBill.setSendStatus("1");//已下达
            this.commonDao.updateEntitie(tOSendDownBill);
        }
        if (StringUtils.isNotEmpty(tOSendDownBill.getBillId())) {
            TOSendReceiveRegEntity reg = this.commonDao.get(TOSendReceiveRegEntity.class, tOSendDownBill.getBillId());
            if (!"1".equals(reg.getDownFlag())) {
                reg.setDownFlag("1");//设置已下达
                this.commonDao.updateEntitie(reg);
            }
        }
        String[] userIdArr = receiverId.split(",");
        String[] userNameArr = receiverName.split(",");
        String flowShow = null;
        String contentShow = null;
        String attachementShow = null;
        if(showFlag.indexOf("1")!=-1){
            flowShow = "1";
        }
        if (showFlag.indexOf("2") != -1) {
            contentShow = "1";
        }
        if (showFlag.indexOf("3") != -1) {
            attachementShow = "1";
        }
        for (int i = 0; i < userIdArr.length; i++) {
            String userId = userIdArr[i];
            TOSendDownBillEntity sendDown = getSendDownByReg(tOSendDownBill.getBillId(), user.getId(), userId);
            if (sendDown != null) {
                continue;
            }
            TOSendDownBillEntity tmp = new TOSendDownBillEntity();
            tmp.setSenderId(user.getId());
            tmp.setSenderName(user.getRealName());
            tmp.setSendTime(new Date());
            tmp.setReceiverId(userId);
            tmp.setReceiverName(userNameArr[i]);
            tmp.setBillId(tOSendDownBill.getBillId());
            tmp.setStatus("0");//未接收
            tmp.setFlowShow(flowShow);
            tmp.setContentShow(contentShow);
            tmp.setAttachementShow(attachementShow);
            tmp.setSendStatus("0");//初始化
            tmp.setSourceId(sourceId);
            this.commonDao.save(tmp);
        }
    }

    @Override
    public void addSendDown(String id) {
        TSUser user = ResourceUtil.getSessionUserName();
        TOSendDownBillEntity sendDown = new TOSendDownBillEntity();
        sendDown.setCreateDate(new Date());
        sendDown.setSenderId(user.getId());
        sendDown.setSenderName(user.getRealName());
        sendDown.setStatus("0");//待发送
        sendDown.setIsFirst("1");//第一级
        this.commonDao.save(sendDown);
        TOSendReceiveRegEntity reg = this.commonDao.get(TOSendReceiveRegEntity.class, id);
        List<TOSendBillEntity> sendBills = this.commonDao.findByProperty(TOSendBillEntity.class, "regId", reg.getId());
        List<TSFilesEntity> files  = null;
        String contentFileId = null;
        String sendTitle = null;
        String sendId = null;
        if(sendBills.size()>0){
        TOSendBillEntity sendBill = sendBills.get(0);
            files = tsFilesService.getFileListByBid(sendBill.getId());
            sendTitle = sendBill.getSendTitle();
            contentFileId = sendBill.getContentFileId();
            sendId = sendBill.getId();
        }else{
            List<TOApprovalEntity> approvalBills = this.commonDao.findByProperty(TOApprovalEntity.class, "regId", reg.getId());
            if(approvalBills.size()>0){
                TOApprovalEntity approval = approvalBills.get(0);
                files = tsFilesService.getFileListByBid(approval.getId());
                sendTitle = approval.getTitle();
                contentFileId = approval.getContentFileId();
                sendId = approval.getId();
            }else{
                throw new BusinessException("找不到对应的发文");
            }
        }
        if (files.size() > 0) {
            for (TSFilesEntity file : files) {
                TSFilesEntity tmp = new TSFilesEntity();
                try {
                    MyBeanUtils.copyBeanNotNull2Bean(file, tmp);
                    String noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + StringUtil.random(8);
                    String realPath = file.getRealpath();
                    String baseDir = new File(ContextHolderUtils.getRequest().getSession().getServletContext()
                            .getRealPath("/")).getParent()
                            + "/";
                    String oldPath = baseDir + realPath;
                    String newRealPath = realPath.substring(realPath.lastIndexOf("/") + 1, realPath.lastIndexOf("."));
                    String newPath = realPath.replace(newRealPath, noextfilename);
                    FileUtils.copyFile(new File(oldPath), new File(baseDir + newPath));
                    tmp.setRealpath(newPath);
                    tmp.setCreatedate(new Timestamp(new Date().getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tmp.setBid(sendDown.getId());
                tmp.setId(UUIDGenerator.generate());
                this.commonDao.save(tmp);
            }
        }
        sendDown.setContentFileId(contentFileId);
        sendDown.setBillId(sendId);
        sendDown.setTitle(sendTitle);
        sendDown.setBillCode(reg.getMergeFileNum());
        sendDown.setAttachementCode(sendDown.getId());
        this.commonDao.updateEntitie(sendDown);
    }

    @Override
    public void doReceive(TOSendDownBillEntity sendDownBill) {
        TOSendDownBillEntity t = commonDao.get(TOSendDownBillEntity.class, sendDownBill.getId());
        t.setStatus("1");
        t.setReceiveTime(new Date());
        commonDao.updateEntitie(t);
    }

    @Override
    public void doDelete(TOSendDownBillEntity tOSendDownBill) {
        tOSendDownBill = commonDao.getEntity(TOSendDownBillEntity.class, tOSendDownBill.getId());
        this.delAttachementByBid(tOSendDownBill.getId());
        this.commonDao.delete(tOSendDownBill);
    }

    /**
     * 根据登记查询发文下达
     * 
     * @param regId
     * @return
     */
    @Override
    public TOSendDownBillEntity getSendDownByReg(String regId, String senderId, String receiverId) {
        TOSendDownBillEntity sendDown = null;
        CriteriaQuery cq = new CriteriaQuery(TOSendDownBillEntity.class);
        cq.eq("billId", regId);
        if (StringUtils.isNotEmpty(senderId)) {
            cq.eq("senderId", senderId);
        }
        if (StringUtils.isNotEmpty(receiverId)) {
            cq.eq("receiverId", receiverId);
        }
        cq.add();
        List<TOSendDownBillEntity> sendDownList = this.commonDao.getListByCriteriaQuery(cq, false);
        if (sendDownList.size() > 0) {
            sendDown = sendDownList.get(0);
        }
        return sendDown;
    }

}