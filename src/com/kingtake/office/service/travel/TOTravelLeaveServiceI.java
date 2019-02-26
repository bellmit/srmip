package com.kingtake.office.service.travel;
import java.util.List;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.travel.TOTravelLeaveEntity;
import com.kingtake.office.entity.travel.TOTravelLeavedetailEntity;

public interface TOTravelLeaveServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TOTravelLeaveEntity tOTravelLeave,
	        List<TOTravelLeavedetailEntity> tOTravelLeavedetailList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TOTravelLeaveEntity tOTravelLeave,
	        List<TOTravelLeavedetailEntity> tOTravelLeavedetailList);
	public void delMain (TOTravelLeaveEntity tOTravelLeave);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TOTravelLeaveEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TOTravelLeaveEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TOTravelLeaveEntity t);

}
