package com.kingtake.project.service.declare;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;

import com.kingtake.project.entity.declare.TPmDeclareEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;

public interface TPmDeclareServiceI extends CommonService{
	
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
 	public boolean doAddSql(TPmDeclareEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmDeclareEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmDeclareEntity t);

    public List findGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request);

    public Long countGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request);

    public List findHistoryTasks(String userName, HttpServletRequest request);

    public Long countHistoryTasks(String userName, HttpServletRequest request);

    public List findPriTodoTasks(String userName, HttpServletRequest request);

    /**
     * 保存项目基本信息和人员信息到申报书和关联的成员信息表中
     * @param projectId
     * @return
     */
    public TPmDeclareEntity firstSave(String projectId) ;

    /**
     * 
     * 重新提交
     * 
     * @param planDetail
     */
    public void doResubmit(TPmPlanDetailEntity planDetail);

    /**
     * 获取申报信息的流转状态
     * 
     * @param bpmStatus
     * @param planStatus
     * @return
     */
    public String getDeclareStatus(String bpmStatus, String planStatus);
    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TPmDeclareEntity t);
    
    /**
     * 项目基本信息导出EXCEL
     * 
     * @return
     */
    public Workbook exportProject(String id);
}
