package com.kingtake.project.service.dbimport;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.dbimport.TPmDBImportEntity;

public interface TPmDBImportServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TPmDBImportEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TPmDBImportEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TPmDBImportEntity t);

    /**
     * 文件导入
     * 
     * @param request
     */
    public void importDB(HttpServletRequest request);

    /**
     * 删除
     * 
     * @param dbImport
     * @param request
     */
    public void delImport(TPmDBImportEntity dbImport, HttpServletRequest request);
    
    /**
     * 导出预算主表给财务
     * @param tPmProjectFundsAppr
     */
	public List exportXmlToYszb();

	/**
     * 导出预算明细给财务
     * @param tPmProjectFundsAppr
     */
	public List exportXmlToYsmx();

    /**
     * 导出到款主表信息
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToDkzb(String year);

	/**
     * 导出到款明细信息
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToDkmx(String year);
	
	/**
     * 导出到款发票明细信息
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToFpmx(String year, String projectId);

	/**
     * 导出校内协作
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToXnxz(String year);
	
	/**
     * 导出校内协作明细
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToXnmx(String year);
	
	/**
     * 导出校内协作明细查询合同
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToXnmxHt(String id);

	
	/**
     * 导出校内协作明细查询计划
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToXnmxJh(String id);
	
	/**
     * 导出垫支经费
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToDzjf(String year);
	
	/**
     * 导出垫支经费明细
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToDzjfmx(String year);

	
	/**
     * 导出调整预算
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToTzys();

	/**
     * 导出计划主表
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToJhzb(String year);

	/**
     * 导出计划明细
     * 
     * @param dbImport
     * @param request
     */
	public List exportXmlToJhmx(String year);

	/**
	 * 导出归垫明细
	 *
	 * @param dbImport
	 * @param request
	 */
	public List exportXmlToGdmx(String year);
	
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
	 * 查询外协合同信息
	 *
	 * @return
	 */
	public List selectOutCome2();
	
	/**
     * 查询外协合同支付节点信息
     * 
     * @return
     */
	public List selectOutComeNode();
	
	/**
     * 上传xml包.ZIP文件
     * 
     * @param request
     */
    public List importXml(HttpServletRequest request);


    /**
     * 导入xml包.ZIP文件
     * 
     * @param request
     */
	public void saveXml(List xmlList,Object cwnd);

	public int getMaxCwjhbh(String year);

	public Map validateFirst(List list);

}
