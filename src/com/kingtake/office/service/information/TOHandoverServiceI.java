package com.kingtake.office.service.information;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.information.TOHandoverEntity;

public interface TOHandoverServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOHandoverEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOHandoverEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOHandoverEntity t);

    /**
     * 提交交班材料
     * 
     * @param tOHandover
     */
    public void doSubmit(TOHandoverEntity tOHandover);
}
