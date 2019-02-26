package com.kingtake.zscq.service.sctzs;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.zscq.entity.sctzs.TZSctzsEntity;

public interface TZSctzsServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TZSctzsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TZSctzsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TZSctzsEntity t);

    /**
     * 保存审查通知书
     * 
     * @param opt
     * @param tZSctzs
     */
    public void saveSctzs(String opt, TZSctzsEntity tZSctzs);

    /**
     * 删除
     * 
     * @param tZSctzs
     */
    public void delSctzs(TZSctzsEntity tZSctzs);


}
