package com.kingtake.office.service.sendbill;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.sendReceive.TOSendReceiveRegEntity;
import com.kingtake.office.entity.sendbill.TOSendBillEntity;

public interface TOSendBillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOSendBillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOSendBillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOSendBillEntity t);

    /**
     * 查询收发文列表
     * 
     * @param param
     * @param start
     * @param end
     * @return
     */
    public List<Map<String, String>> getPortalList(Map<String, String> param, int start, int end);

    public Integer getPortalCount(Map<String, String> param);

    /**
     * 完成发文
     * 
     * @param req
     */
    public void doFinish(HttpServletRequest req);

    public void archive(TOSendBillEntity tOSendBill, HttpServletRequest request);

    public void doArchive(HttpServletRequest req);

    public List<Map<String, Object>> getSendBillList(Map<String, String> param, int start, int end);

    public Integer getSendBillCount(Map<String, String> param);

    /**
     * 获取公文编号
     * 
     * @param reg
     * @return
     */
    public Map<String, String> getFileNum(TOSendReceiveRegEntity reg);
}
