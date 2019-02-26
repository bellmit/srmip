package com.kingtake.office.service.impl.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;
import com.kingtake.office.service.message.TOMessageServiceI;

@Service("tOMessageService")
@Transactional
public class TOMessageServiceImpl extends CommonServiceImpl implements TOMessageServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TOMessageEntity) entity);
    }

    public void addMain(TOMessageEntity tOMessage, List<TOMessageReadEntity> tOMessageReadList) {
        //保存主信息
        this.save(tOMessage);

        /** 保存-系统消息接收人 */
        for (TOMessageReadEntity tOMessageRead : tOMessageReadList) {
            //外键设置
            tOMessageRead.setToid(tOMessage.getId());
            this.save(tOMessageRead);
        }
        //执行新增操作配置的sql增强
        this.doAddSql(tOMessage);
    }

    public void updateMain(TOMessageEntity tOMessage, List<TOMessageReadEntity> tOMessageReadList) {
        //保存主表信息
        this.saveOrUpdate(tOMessage);
        //===================================================================================
        //获取参数
        Object id0 = tOMessage.getId();
        //===================================================================================
        //1.查询出数据库的明细数据-系统消息接收人
        String hql0 = "from TOMessageReadEntity where 1 = 1 AND t_O_ID = ? ";
        List<TOMessageReadEntity> tOMessageReadOldList = this.findHql(hql0, id0);
        //2.筛选更新明细数据-系统消息接收人
        for (TOMessageReadEntity oldE : tOMessageReadOldList) {
            boolean isUpdate = false;
            for (TOMessageReadEntity sendE : tOMessageReadList) {
                //需要更新的明细数据-系统消息接收人
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
                //如果数据库存在的明细，前台没有传递过来则是删除-系统消息接收人
                super.delete(oldE);
            }

        }
        //3.持久化新增的数据-系统消息接收人
        for (TOMessageReadEntity tOMessageRead : tOMessageReadList) {
            if (oConvertUtils.isEmpty(tOMessageRead.getId())) {
                //外键设置
                tOMessageRead.setToid(tOMessage.getId());
                this.save(tOMessageRead);
            }
        }
        //执行更新操作配置的sql增强
        this.doUpdateSql(tOMessage);
    }

    public void delMain(TOMessageEntity tOMessage) {
        //删除主表信息
        this.delete(tOMessage);
        //===================================================================================
        //获取参数
        Object id0 = tOMessage.getId();
        //===================================================================================
        //删除-系统消息接收人
        String hql0 = "from TOMessageReadEntity where 1 = 1 AND t_O_ID = ? ";
        List<TOMessageReadEntity> tOMessageReadOldList = this.findHql(hql0, id0);
        this.deleteAllEntitie(tOMessageReadOldList);
    }

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOMessageEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOMessageEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOMessageEntity t) {
        return true;
    }

    /**
     * 替换sql中的变量
     * 
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TOMessageEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{sender_id}", String.valueOf(t.getSenderId()));
        sql = sql.replace("#{sender_name}", String.valueOf(t.getSenderName()));
        sql = sql.replace("#{send_time}", String.valueOf(t.getSendTime()));
        sql = sql.replace("#{title}", String.valueOf(t.getTitle()));
        sql = sql.replace("#{content}", String.valueOf(t.getContent()));
        sql = sql.replace("#{del_flag}", String.valueOf(t.getDelFlag()));
        sql = sql.replace("#{del_time}", String.valueOf(t.getDelTime()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    public void sendMessage(String receiverids, String title, String type, String content, String senderId, String executeSql) {
        String[] rids = receiverids.split(",");
        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
        List<TSUser> ulist = new ArrayList<TSUser>();
        if (rids.length > 0) {
            CriteriaQuery rcq = new CriteriaQuery(TSUser.class);
            rcq.in("id", rids);
            rcq.add();
            ulist = this.getListByCriteriaQuery(rcq, false);
            for (TSUser user : ulist) {
                TOMessageReadEntity reader = new TOMessageReadEntity();
                reader.setReadFlag("0");
                reader.setDelFlag("0");
                reader.setShow("0");
                reader.setReceiverId(user.getId());
                reader.setReceiverName(user.getRealName());
                tOMessageReadList.add(reader);
            }
        }

        TOMessageEntity message = new TOMessageEntity();
        message.setContent(content);
        message.setTitle(title);
        message.setType(type);
        if (StringUtil.isNotEmpty(senderId)) {
            if (senderId.contains(",")) {
                senderId = senderId.split(",")[0];
            }
            message.setSenderId(senderId);
            message.setSenderName(this.get(TSUser.class, senderId).getRealName());
        } else {
            message.setSenderId("8a8ab0b246dc81120146dc8181950052");
            message.setSenderName("管理员");
        }
        message.setSendTime(new Date());
        if(StringUtils.isNotEmpty(executeSql)){
            message.setExecuteSql(executeSql);
        }
        this.addMain(message, tOMessageReadList);

    }

    /**
     * 逻辑删除
     */
    @Override
    public void doLogicDelete(String ids) {
        for (String id : ids.split(",")) {
            TOMessageEntity tOMessage = commonDao.getEntity(TOMessageEntity.class, id);
            tOMessage.setDelFlag("1");
            this.commonDao.updateEntitie(tOMessage);
        }
    }

    /**
     * 恢复
     */
    @Override
    public void doBack(String ids) {
        for (String id : ids.split(",")) {
            TOMessageEntity tOMessage = commonDao.getEntity(TOMessageEntity.class, id);
            tOMessage.setDelFlag("0");
            this.commonDao.updateEntitie(tOMessage);
        }
    }

    @Override
    public void sendMessage(String receiverids, String title, String type, String content, String senderId) {
        String[] rids = receiverids.split(",");
        List<TOMessageReadEntity> tOMessageReadList = new ArrayList<TOMessageReadEntity>();
        List<TSUser> ulist = new ArrayList<TSUser>();
        if (rids.length > 0) {
            CriteriaQuery rcq = new CriteriaQuery(TSUser.class);
            rcq.in("id", rids);
            rcq.add();
            ulist = this.getListByCriteriaQuery(rcq, false);
            for (TSUser user : ulist) {
                TOMessageReadEntity reader = new TOMessageReadEntity();
                reader.setReadFlag("0");
                reader.setDelFlag("0");
                reader.setShow("0");
                reader.setReceiverId(user.getId());
                reader.setReceiverName(user.getRealName());
                tOMessageReadList.add(reader);
            }
        }

        TOMessageEntity message = new TOMessageEntity();
        message.setContent(content);
        message.setTitle(title);
        message.setType(type);
        if (StringUtil.isNotEmpty(senderId)) {
            if (senderId.contains(",")) {
                senderId = senderId.split(",")[0];
            }
            message.setSenderId(senderId);
            message.setSenderName(this.get(TSUser.class, senderId).getRealName());
        } else {
            message.setSenderId("8a8ab0b246dc81120146dc8181950052");
            message.setSenderName("管理员");
        }
        message.setSendTime(new Date());
        this.addMain(message, tOMessageReadList);

    }
    
    @Override
    public void executeCallbackSql(TOMessageEntity message) {
        if (StringUtils.isNotEmpty(message.getExecuteSql())) {
            String[] sqls = message.getExecuteSql().split(";");
            for (String sql : sqls) {
                try {
                    this.commonDao.executeSql(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}