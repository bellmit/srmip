package com.kingtake.zscq.service.zlsq;
import java.io.Serializable;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.zscq.entity.zlsq.TZZlsqEntity;

public interface TZZlsqServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TZZlsqEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TZZlsqEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TZZlsqEntity t);

    

    /**
     * 删除专利申请
     * 
     * @param tZZlsq
     */
    public void delZlsq(TZZlsqEntity tZZlsq);
}
