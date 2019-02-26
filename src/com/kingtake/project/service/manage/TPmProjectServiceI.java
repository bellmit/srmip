package com.kingtake.project.service.manage;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.manage.TPmProjectEntity;

public interface TPmProjectServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmProjectEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmProjectEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmProjectEntity t);
 	/**
 	 * 课题组项目确认
 	 * @param projectId
 	 */
 	public void researchGroupConfirm(String projectId);

 	/**
 	 * 驳回
 	 * @param id
 	 * @param msgText
 	 */
    public void doPropose(String id, String msgText);
    
    /**
     * 下载导入模板
     * 
     * @param regesterType
     * @param response
     */
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response);
    
    /**
     * 导入excel
     * 
     * @param wb
     */
    public String importProjecjExcel(HSSFWorkbook wb,String lxStatus,String jhid);

    /**
     * 获取项目总经费，如果是计划类项目，则直接从项目基本信息获取，如果是合同类，则从进账合同取.
     * @param project
     * @return
     */
    public BigDecimal getAllFee(String projectId);
    
    /**
     * 项目基本信息导出EXCEL
     * 
     * @return
     */
    public Workbook exportProject();

    /**
     * 查询项目基本信息
     * 
     * @return
     */
	public List selectProject();
	
	/**
     * 查询外协合同信息
     * 
     * @return
     */
	public List selectOutCome();
	
	/**
     * 查询外协合同支付节点信息
     * 
     * @return
     */
	public List selectOutComeNode();
	
	/**
	 * 验证项目编码是否存在
	 * @param tableName
	 * @param codeField
	 * @param codeValue
	 * @return
	 */
 	public ValidForm validformCode(String tableName, String codeField, String codeValue);
 	

}
