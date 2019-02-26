package com.kingtake.office.service.message;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.message.TOMessageEntity;
import com.kingtake.office.entity.message.TOMessageReadEntity;

public interface TOMessageServiceI extends CommonService {

    public <T> void delete(T entity);

    /**
     * 添加一对多
     * 
     */
    public void addMain(TOMessageEntity tOMessage, List<TOMessageReadEntity> tOMessageReadList);

    /**
     * 修改一对多
     * 
     */
    public void updateMain(TOMessageEntity tOMessage, List<TOMessageReadEntity> tOMessageReadList);

    public void delMain(TOMessageEntity tOMessage);

    /**
     * 默认按钮-sql增强-新增操作
     * 
     * @param id
     * @return
     */
    public boolean doAddSql(TOMessageEntity t);

    /**
     * 默认按钮-sql增强-更新操作
     * 
     * @param id
     * @return
     */
    public boolean doUpdateSql(TOMessageEntity t);

    /**
     * 默认按钮-sql增强-删除操作
     * 
     * @param id
     * @return
     */
    public boolean doDelSql(TOMessageEntity t);

    public void sendMessage(String receiverids, String title, String type, String content, String senderId, String executeSql);
    
    public void sendMessage(String receiverids, String title, String type, String content, String senderId);

    /**
     * 逻辑删除消息
     * 
     * @param ids
     */
    public void doLogicDelete(String ids);

    /**
     * 系统消息
     * 
     * @param ids
     */
    public void doBack(String ids);

    /**
     * 执行回调sql
     * @param message
     */
    public void executeCallbackSql(TOMessageEntity message);
}
