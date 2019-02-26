package com.kingtake.zscq.service.zsdj;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import com.kingtake.zscq.entity.zsdj.TZZsdjEntity;

public interface TZZsdjServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TZZsdjEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TZZsdjEntity t); 
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TZZsdjEntity t);

    /**
     * 保存证书登记
     * 
     * @param tZSltzs
     */
    public void saveZsdj(TZZsdjEntity tZZsdj);

}
