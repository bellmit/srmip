package com.kingtake.office.service.leaderindicate;

import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.leaderindicate.TOLeaderIndicateEntity;

public interface TOLeaderIndicateServiceI extends CommonService {
	
 	@Override
    public <T> void delete(T entity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TOLeaderIndicateEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TOLeaderIndicateEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TOLeaderIndicateEntity t);

    /**
     * 发送
     * 
     * @param tOLeaderIndicate
     * @param receiverIds
     * @param receiverNames
     */
    public void doSubmit(TOLeaderIndicateEntity tOLeaderIndicate, String receiverIds, String receiverNames);

    /**
     * 接收
     * 
     * @param tOLeaderIndicate
     */
    public void doReceive(TOLeaderIndicateEntity tOLeaderIndicate);
}
