package com.kingtake.office.service.warn;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.warn.TOWarnEntity;
import com.kingtake.office.entity.warn.TOWarnReceiveEntity;

public interface TOWarnServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TOWarnEntity tOWarn,
	        List<TOWarnReceiveEntity> tOWarnReceiveList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TOWarnEntity tOWarn,
	        List<TOWarnReceiveEntity> tOWarnReceiveList);
	public void delMain (TOWarnEntity tOWarn);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOWarnEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOWarnEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOWarnEntity t);

    /**
     * 完成公共提醒
     * 
     * @param receiveEntity
     */
    public void finishWarn(TOWarnReceiveEntity receiveEntity);
}
