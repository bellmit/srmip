package com.kingtake.office.service.impl.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.CommonMessageServiceI;
import com.kingtake.office.entity.notice.TONoticeEntity;
import com.kingtake.office.entity.notice.TONoticeProjectRelaEntity;
import com.kingtake.office.entity.notice.TONoticeReceiveEntity;
import com.kingtake.office.entity.schedule.TOResponseEntity;
import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.entity.warn.TOWarnReceiveEntity;
import com.kingtake.office.service.notice.TONoticeServiceI;
import com.kingtake.office.service.warn.TOWarnServiceI;

@Service("tONoticeService")
@Transactional
public class TONoticeServiceImpl extends CommonServiceImpl implements TONoticeServiceI {
  @Autowired
  private CommonMessageServiceI commonMessageService;

    @Autowired
    private TOWarnServiceI tOWarnService;

  public <T> void delete(T entity) {
    super.delete(entity);
    // 执行删除操作配置的sql增强
    this.doDelSql((TONoticeEntity) entity);
  }

    public void addMain(TONoticeEntity tONotice, List<TONoticeReceiveEntity> tONoticeReceiveList) {
        // 保存主信息
        this.save(tONotice);
        StringBuffer receiverSb = new StringBuffer();
        /** 保存-通知公告子表 */
        for (TONoticeReceiveEntity tONoticeReceive : tONoticeReceiveList) {
            // 外键设置
            tONoticeReceive.setNoticeId(tONotice.getId());
            this.save(tONoticeReceive);
            if (receiverSb.length() > 0) {
                receiverSb.append(",");
            }
            receiverSb.append(tONoticeReceive.getReceiverId());
        }
        if (receiverSb.length() > 0) {
            String messageContent = "您有个新的通知公告！<a href=\"#\" style=\"color:skyblue;\" onclick='addTab(\"通知公告\",\"tONoticeController.do?tONoticeToMe\");'>"
                    + tONotice.getTitle() + "</a>";
            sendMessage(receiverSb.toString(), tONotice.getTitle(), messageContent);
        }
        // 执行新增操作配置的sql增强
        this.doAddSql(tONotice);
    }

    public void updateMain(TONoticeEntity tONotice, List<TONoticeReceiveEntity> tONoticeReceiveList) {
        // 保存主表信息
        this.saveOrUpdate(tONotice);
        // ===================================================================================
        // 获取参数
        Object id0 = tONotice.getId();
        // ===================================================================================
        // 1.查询出数据库的明细数据-通知公告子表
        String hql0 = "from TONoticeReceiveEntity where 1 = 1 AND nOTICE_ID = ? ";
        List<TONoticeReceiveEntity> tONoticeReceiveOldList = this.findHql(hql0, id0);
        // 2.筛选更新明细数据-通知公告子表
        for (TONoticeReceiveEntity oldE : tONoticeReceiveOldList) {
            boolean isUpdate = false;
            for (TONoticeReceiveEntity sendE : tONoticeReceiveList) {
                // 需要更新的明细数据-通知公告子表
                if (oldE.getId().equals(sendE.getId())) {
                    try {
                        MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
                        this.saveOrUpdate(oldE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BusinessException(e.getMessage());
                    }
                    isUpdate = true;
                    break;
                }
            }
            if (!isUpdate) {
                // 如果数据库存在的明细，前台没有传递过来则是删除-通知公告子表
                if ("0".equals(oldE.getReadFlag())) {//若未读，则删除
                    super.delete(oldE);
                }
            }

        }
        StringBuffer receiverSb = new StringBuffer();
        // 3.持久化新增的数据-通知公告子表
        for (TONoticeReceiveEntity tONoticeReceive : tONoticeReceiveList) {
            if (oConvertUtils.isEmpty(tONoticeReceive.getId())) {
                // 外键设置
                tONoticeReceive.setNoticeId(tONotice.getId());
                this.save(tONoticeReceive);
                if (receiverSb.length() > 0) {
                    receiverSb.append(",");
                }
                receiverSb.append(tONoticeReceive.getReceiverId());
            }
        }
        if (receiverSb.length() > 0) {
            String messageContent = "您有个新的通知公告！<a href=\"#\" style=\"color:skyblue;\" onclick='addTab(\"通知公告\",\"tONoticeController.do?tONoticeToMe\");'>"
                    + tONotice.getTitle() + "</a>";
            sendMessage(receiverSb.toString(), tONotice.getTitle(), messageContent);
        }
        // 执行更新操作配置的sql增强
        this.doUpdateSql(tONotice);
    }

  /**
   * 
   * @param sender
   * @param receiverId
   * @param title
   * @param content
   */
    private void sendMessage(String receiverIds, String title, String content) {
        String messageType = "通知公告";
        String messageTitle = title;
        String messageContent = content;
        commonMessageService.sendMessage(receiverIds, messageType, messageTitle, messageContent);
  }

  public void delMain(TONoticeEntity tONotice) {
    // 删除主表信息
    this.delete(tONotice);
    // ===================================================================================
    // 获取参数
    Object id0 = tONotice.getId();
    // ===================================================================================
    // 删除-通知公告子表
    String hql0 = "from TONoticeReceiveEntity where 1 = 1 AND nOTICE_ID = ? ";
    List<TONoticeReceiveEntity> tONoticeReceiveOldList = this.findHql(hql0, id0);
    this.deleteAllEntitie(tONoticeReceiveOldList);
  }

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  public boolean doAddSql(TONoticeEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  public boolean doUpdateSql(TONoticeEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  public boolean doDelSql(TONoticeEntity t) {
    return true;
  }

  /**
   * 替换sql中的变量
   * 
   * @param sql
   * @return
   */
  public String replaceVal(String sql, TONoticeEntity t) {
    sql = sql.replace("#{id}", String.valueOf(t.getId()));
    sql = sql.replace("#{sender_id}", String.valueOf(t.getSenderId()));
    sql = sql.replace("#{sender_name}", String.valueOf(t.getSenderName()));
    sql = sql.replace("#{send_time}", String.valueOf(t.getSendTime()));
    sql = sql.replace("#{title}", String.valueOf(t.getTitle()));
    sql = sql.replace("#{content}", String.valueOf(t.getContent()));
    // sql = sql.replace("#{proj_id}", String.valueOf(t.getProjId()));
    // sql = sql.replace("#{proj_name}", String.valueOf(t.getProjName()));
    sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
    return sql;
  }

    @Override
    public void updateNotice(TONoticeEntity tONotice, String receiverid) {
        try {
            TONoticeEntity tn = commonDao.get(TONoticeEntity.class, tONotice.getId());
            CriteriaQuery cq = new CriteriaQuery(TONoticeReceiveEntity.class);
            cq.eq("noticeId", tONotice.getId());
            cq.add();
            List<TONoticeReceiveEntity> tONoticeOldReceiveList = commonDao.getListByCriteriaQuery(cq, false);
            List<String> recIds = new ArrayList<String>();
            List<String> addIds = new ArrayList<String>();
            for (TONoticeReceiveEntity entity : tONoticeOldReceiveList) {
                recIds.add(entity.getReceiverId());
            }
            List<TONoticeReceiveEntity> tONoticeReceiveList = new ArrayList<TONoticeReceiveEntity>();
            if (StringUtil.isNotEmpty(receiverid)) {
                String[] receiverids = receiverid.split(",");
                for (String id : receiverids) {
                    if (recIds.contains(id)) {//若存在，则去掉
                        recIds.remove(id);
                        for (TONoticeReceiveEntity tmp : tONoticeOldReceiveList) {
                            if (id.equals(tmp.getReceiverId())) {
                                tONoticeReceiveList.add(tmp);
                                break;
                            }
                        }
                    } else {
                        addIds.add(id);
                    }
                }
            }
            for (String rid : addIds) {
                TONoticeReceiveEntity re = new TONoticeReceiveEntity();
                TSUser user = commonDao.get(TSUser.class, rid);
                re.setReceiverId(rid);
                re.setReceiverName(user.getRealName());
                re.setReadFlag("0");
                tONoticeReceiveList.add(re);
            }
            /*
             * for (String did : recIds) { CriteriaQuery recCq = new CriteriaQuery(TONoticeReceiveEntity.class);
             * recCq.eq("noticeId", tONotice.getId()); recCq.eq("receiverId", did); recCq.eq("readFlag", "0");
             * recCq.add(); List<TONoticeReceiveEntity> recTmps = this.commonDao.getListByCriteriaQuery(recCq, false);
             * if (recTmps.size() > 0) {//如果存在未读，则删除 for (TONoticeReceiveEntity tmp : recTmps) {
             * commonDao.deleteEntityById(TONoticeReceiveEntity.class, tmp.getId()); } } }
             */
            MyBeanUtils.copyBeanNotNull2Bean(tONotice, tn);
            this.updateMain(tn, tONoticeReceiveList);
        } catch (Exception e) {
            throw new BusinessException("更新通知公告失败！");
        }
    }

    @Override
    public void doResponse(TONoticeEntity tONotice, String resContent, String type) {
        tONotice = this.commonDao.get(TONoticeEntity.class, tONotice.getId());
            TOResponseEntity response = new TOResponseEntity();
            TSUser user = ResourceUtil.getSessionUserName();
            response.setResUserId(user.getId());
            response.setResUserName(user.getRealName());
            response.setResContent(resContent);
            response.setResTime(new Date());
        response.setResType("notice");
        String sourceId = tONotice.getId();
            response.setResSourceId(sourceId);
            this.commonDao.save(response);
            if ("receiver".equals(type)) {
            String messageTitle = tONotice.getTitle();
            String messageType = "通知公告";
            String messageContent = tONotice.getContent();
            commonMessageService.sendMessage(tONotice.getSenderId(), messageType, messageTitle, messageContent);
        }

    }

    @Override
    public void doWarn(TOWarnEntity tOWarn, TONoticeEntity noticeEntity) {
        List<TOWarnEntity> warnList = this.commonDao.findByProperty(TOWarnEntity.class, "sourceId",
                noticeEntity.getId());
        if (warnList.size() > 0) {
            for (TOWarnEntity wa : warnList) {
                this.commonDao.delete(wa);
            }
        }
        TSUser user = ResourceUtil.getSessionUserName();//获取当前登录人
        List<TONoticeReceiveEntity> receiveList = this.commonDao.findByProperty(TONoticeReceiveEntity.class,
                "noticeId",
                noticeEntity.getId());
        tOWarn.setSourceId(noticeEntity.getId());
        tOWarn.setWarnContent("通知公告提醒：" + noticeEntity.getContent());
        tOWarn.setCreateBy(user.getUserName());
        tOWarn.setCreateDate(new Date());
        tOWarn.setCreateName(user.getRealName());
        tOWarn.setWarnStatus("0");//默认为未完成
        List<TOWarnReceiveEntity> tOWarnReceiveList = new ArrayList<TOWarnReceiveEntity>();
        for (TONoticeReceiveEntity rec : receiveList) {
            TOWarnReceiveEntity receive = new TOWarnReceiveEntity();
            receive.setToWarn(tOWarn);
            receive.setReceiveUserid(rec.getReceiverId());
            receive.setReceiveUsername(rec.getReceiverName());
            tOWarnReceiveList.add(receive);
        }
        TOWarnReceiveEntity receive = new TOWarnReceiveEntity();
        receive.setToWarn(tOWarn);
        List<TSUser> userList = this.commonDao.findByProperty(TSUser.class, "userName", tOWarn.getCreateBy());
        if (userList.size() > 0) {
            TSUser u = userList.get(0);
            receive.setReceiveUserid(u.getId());
            receive.setReceiveUsername(u.getRealName());
        }
        tOWarnReceiveList.add(receive);
        this.tOWarnService.addMain(tOWarn, tOWarnReceiveList);
    }

    @Override
    public String deleteNotice(TONoticeEntity tONotice) {
        tONotice = commonDao.getEntity(TONoticeEntity.class, tONotice.getId());
        String message = "通知公告删除成功";
        try {
            CriteriaQuery cq = new CriteriaQuery(TONoticeReceiveEntity.class);
            cq.eq("noticeId", tONotice.getId());
            cq.eq("readFlag", SrmipConstants.YES);
            cq.add();
            List<TONoticeReceiveEntity> list = commonDao.getListByCriteriaQuery(cq, false);
            if (list.size() > 0) {
                message = "已有接收人员阅读，不能删除！";
            } else {
                this.delAttachementByBid(tONotice.getId());//删除附件
                CriteriaQuery pcq = new CriteriaQuery(TONoticeProjectRelaEntity.class);
                pcq.eq("noticeId", tONotice.getId());
                pcq.add();
                List<TONoticeProjectRelaEntity> plist = commonDao.getListByCriteriaQuery(pcq, false);
                if (plist.size() > 0) {
                    TONoticeProjectRelaEntity projectRela = plist.get(0);
                    commonDao.delete(projectRela);
                }
                List<TOWarnEntity> warnList = this.commonDao.findByProperty(TOWarnEntity.class, "sourceId",
                        tONotice.getId());
                for (TOWarnEntity tmp : warnList) {
                    this.commonDao.delete(tmp);//删除对应的提醒
                }
                delMain(tONotice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        return message;
    }

}