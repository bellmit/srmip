package com.kingtake.project.service.declare.army;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.declare.army.TBPmDeclareArmyResearchEntity;

public interface TBPmDeclareArmyResearchServiceI extends CommonService{
	
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
 	public boolean doAddSql(TBPmDeclareArmyResearchEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBPmDeclareArmyResearchEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBPmDeclareArmyResearchEntity t);
 	
 	/**
 	 * 新建项目申报书
 	 * @param projectId
 	 */
 	public TBPmDeclareArmyResearchEntity firstSave(String projectId);
 	/**
     * 删除业务数据同时删除附件
     */
 	public void deleteAddAttach(TBPmDeclareArmyResearchEntity t);
}
