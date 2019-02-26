package com.kingtake.project.service.checkreport;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.checkreport.TPmCheckReportEntity;

public interface TPmCheckReportServiceI extends CommonService{
	
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
 	public boolean doAddSql(TPmCheckReportEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmCheckReportEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmCheckReportEntity t);

    /**
     * 审核
     * 
     * @param tPmCheckReport
     */
    public void doAudit(TPmCheckReportEntity tPmCheckReport);
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TPmCheckReportEntity t);
}
