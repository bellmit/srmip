package com.kingtake.zscq.service.zlzz;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.zscq.entity.zlzz.TZZlzzEntity;

public interface TZZlzzServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TZZlzzEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TZZlzzEntity t);

 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TZZlzzEntity t);

    /**
     * 保存终止状态
     * 
     * @param zlzz
     */
    public void saveZlzz(TZZlzzEntity zlzz);

}
