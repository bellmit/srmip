package com.kingtake.project.service.funds;
import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.funds.TPmPlanFundsEntity;

public interface TPmPlanFundsServiceI extends CommonService{
	
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
 	public boolean doAddSql(TPmPlanFundsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmPlanFundsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmPlanFundsEntity t);
 	
 	public void init(String apprId);
    
    public void initParent(String apprId);

    public void updateParentsMoney(String parent, double oldMoney, double newMoney);

    public void importExcel(HSSFWorkbook workbook, String tpId, String showType);

	public void update(TPmPlanFundsEntity tPmPlanFundsOne) throws Exception;
	
	public String updateAndReturn(TPmPlanFundsEntity tPmPlanFundsOne) throws Exception;
	
	public void del(String id);

}
