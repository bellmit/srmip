package com.kingtake.project.service.change;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.change.TBPmProjectChangeEntity;
import com.kingtake.project.entity.change.TBPmProjectChangePropertyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;

public interface TBPmProjectChangeServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TBPmProjectChangeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TBPmProjectChangeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TBPmProjectChangeEntity t);

    /**
     * 生成项目变更信息
     * 
     * @param tPmProject
     * @return
     */
    public List<TBPmProjectChangePropertyEntity> createProjectChange(TPmProjectEntity tPmProject);

    public void saveProjectChange(TBPmProjectChangeEntity projectChange);

    /**
     * 审核通过之后，回写
     * 
     * @param changeId
     */
    public void changeProjectAfterAudit(String changeId);
}
