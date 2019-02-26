package com.kingtake.office.service.borrowbill;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.office.entity.borrowbill.TOBorrowBillEntity;

public interface TOBorrowBillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TOBorrowBillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TOBorrowBillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TOBorrowBillEntity t);

    /**
     * 借阅申请提交
     * 
     * @param tOBorrowBill
     */
    public void doSubmit(TOBorrowBillEntity tOBorrowBill);

    /**
     * 审核通过
     * 
     * @param borrowId
     * @param regIds
     */
    public void doPass(String borrowId, String regIds);

}
