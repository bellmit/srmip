package com.kingtake.zscq.service.sq;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.zscq.entity.sq.TZJfjlEntity;
import com.kingtake.zscq.entity.sq.TZSqEntity;

public interface TZSqServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TZJfjlEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TZJfjlEntity t);

 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TZJfjlEntity t);

    /**
     * 保存受理通知书
     * 
     * @param request
     * 
     * @param tZSltzs
     */
    public void saveSq(TZSqEntity tzSq, HttpServletRequest request);

    /**
     * 删除缴费记录
     * 
     * @param jfjl
     */
    public void delJfjl(TZJfjlEntity jfjl);

}
