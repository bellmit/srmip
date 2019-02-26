package com.kingtake.office.service.billdown;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.billdown.TOSendDownBillEntity;

public interface TOSendDownBillServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TOSendDownBillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TOSendDownBillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TOSendDownBillEntity t);

    /**
     * 发文下达发送
     * 
     * @param tOSendDownBill
     * @param userIds
     * @param receiverName
     */
    public void doSubmit(TOSendDownBillEntity tOSendDownBill, String receiverId, String receiverName, String showFlag);

    /**
     * 新增发文下达
     * 
     * @param ids
     */
    public void addSendDown(String ids);

    /**
     * 接收下达发文
     * 
     * @param sendDownReceive
     */
    public void doReceive(TOSendDownBillEntity sendDownBill);

    /**
     * 删除下达发文
     * 
     * @param tOSendDownBill
     */
    public void doDelete(TOSendDownBillEntity tOSendDownBill);

    /**
     * 获取下达记录
     * 
     * @param regId
     * @return
     */
    public TOSendDownBillEntity getSendDownByReg(String regId, String senderId, String receiverId);

}
