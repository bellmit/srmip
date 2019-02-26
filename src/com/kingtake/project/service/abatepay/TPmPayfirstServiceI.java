package com.kingtake.project.service.abatepay;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.abatepay.TPmPayfirstEntity;

public interface TPmPayfirstServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmPayfirstEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmPayfirstEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmPayfirstEntity t);

    /**
     * 保存垫资
     * 
     * @param tPmPayfirst
     */
    public void savePayFirst(TPmPayfirstEntity tPmPayfirst);
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TPmPayfirstEntity t);

	/**
	 * 进行归垫 新增数据库 add by liyangzhao 10.13
	 * @param map
	 * @return
	 */
	public Map addGdInfo(Map map);

	/**
	 * 归垫查询
	 * @param grid
	 */
	public List doSearchGDList(Map map);
}
