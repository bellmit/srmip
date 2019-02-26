package com.kingtake.office.service.impl.borrowbill;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.office.entity.borrowbill.TOBorrowBillEntity;
import com.kingtake.office.entity.borrowbill.TOBorrowRecBillEntity;
import com.kingtake.office.entity.receivebill.TOReceiveBillEntity;
import com.kingtake.office.service.borrowbill.TOBorrowBillServiceI;

@Service("tOBorrowBillService")
@Transactional
public class TOBorrowBillServiceImpl extends CommonServiceImpl implements TOBorrowBillServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
        this.doDelSql((TOBorrowBillEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
        this.doAddSql((TOBorrowBillEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
        this.doUpdateSql((TOBorrowBillEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
    public boolean doAddSql(TOBorrowBillEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
    public boolean doUpdateSql(TOBorrowBillEntity t) {
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
    public boolean doDelSql(TOBorrowBillEntity t) {
	 	return true;
 	}

    /**
     * 提交借阅申请
     */
    @Override
    public void doSubmit(TOBorrowBillEntity tOBorrowBill) {
        tOBorrowBill = this.commonDao.get(TOBorrowBillEntity.class, tOBorrowBill.getId());
        tOBorrowBill.setAuditStatus("1");
        this.commonDao.updateEntitie(tOBorrowBill);
    }

    @Override
    public void doPass(String borrowId, String regIds) {
        TOBorrowBillEntity borrow = this.commonDao.get(TOBorrowBillEntity.class, borrowId);
        borrow.setAuditStatus("2");//审核通过
        borrow.setBorrowBeginTime(new Date());//借阅开始时间为审批通过时间
        this.commonDao.updateEntitie(borrow);
        String[] idArr = regIds.split(",");
        for (String id : idArr) {
            CriteriaQuery cq = new CriteriaQuery(TOReceiveBillEntity.class);
            cq.eq("regId", id);
            cq.eq("archiveFlag", "3");//已归档
            cq.add();
            List<TOReceiveBillEntity> recBillList = this.commonDao.getListByCriteriaQuery(cq, false);
            if (recBillList.size() > 0) {
                TOReceiveBillEntity bill = recBillList.get(0);
                TOBorrowRecBillEntity borrowRecEntity = new TOBorrowRecBillEntity();
                borrowRecEntity.setBorrowId(borrowId);
                borrowRecEntity.setRecId(bill.getId());
                borrowRecEntity.setContentFileId(bill.getContentFileId());
                this.commonDao.save(borrowRecEntity);
            }
        }
    }
 	

}