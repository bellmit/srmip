package com.kingtake.project.service.impl.price;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.price.TPmContractPriceMasterEntity;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;

@Service("tPmContractPriceMasterService")
@Transactional
public class TPmContractPriceMasterServiceImpl extends CommonServiceImpl implements TPmContractPriceMasterServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractPriceMasterEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractPriceMasterEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractPriceMasterEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceMasterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceMasterEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceMasterEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPriceMasterEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{price_budget_id}",String.valueOf(t.getPriceBudgetId()));
 		sql  = sql.replace("#{price_budget_name}",String.valueOf(t.getPriceBudgetName()));
 		sql  = sql.replace("#{price_column}",String.valueOf(t.getPriceColumn()));
 		sql  = sql.replace("#{audit_column}",String.valueOf(t.getAuditColumn()));
 		sql  = sql.replace("#{valuation_column}",String.valueOf(t.getValuationColumn()));
 		sql  = sql.replace("#{price_diff_column}",String.valueOf(t.getPriceDiffColumn()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public List<TPmContractPriceMasterEntity> list(TPmContractPriceMasterEntity tPmContractPriceMaster) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT * FROM T_PM_CONTRACT_PRICE_MASTER WHERE T_P_ID = :tpId " +
					" START WITH PARENT IS NULL CONNECT BY PRIOR ID = PARENT";
		
		List<TPmContractPriceMasterEntity> list = commonDao.getSession().createSQLQuery(sql)
				.addEntity(TPmContractPriceMasterEntity.class)
				.setParameter("tpId", tPmContractPriceMaster.getTpId())
				.list();
		return list;
	}

	@Override
	public void updateParent(TPmContractPriceMasterEntity oldData, TPmContractPriceMasterEntity newData, String parentId) {
		if(StringUtil.isNotEmpty(parentId)){
			// 获得父节点
			TPmContractPriceMasterEntity parent = commonDao.get(TPmContractPriceMasterEntity.class, parentId);
			if(parent != null){
				parent.setPriceColumn(
						(parent.getPriceColumn() == null ? 0 : parent.getPriceColumn())
						+ (newData.getPriceColumn() == null ? 0 : newData.getPriceColumn())
						- (oldData.getPriceColumn() == null ? 0 : oldData.getPriceColumn()));
				parent.setAuditColumn(
						(parent.getAuditColumn() == null ? 0 : parent.getAuditColumn())
						+ (newData.getAuditColumn() == null ? 0 : newData.getAuditColumn())
						- (oldData.getAuditColumn() == null ? 0 : oldData.getAuditColumn()));
				parent.setValuationColumn(
						(parent.getValuationColumn() == null ? 0 : parent.getValuationColumn())
						+ (newData.getValuationColumn() == null ? 0 : newData.getValuationColumn())
						- (oldData.getValuationColumn() == null ? 0 : oldData.getValuationColumn()));
				parent.setPriceDiffColumn(parent.getPriceColumn() - parent.getValuationColumn());
				commonDao.updateEntitie(parent);
				
				if(StringUtil.isNotEmpty(parent.getParent())){
					updateParent(oldData, newData, parent.getParent());
				}
			}
		}
	}
	
	
	public void updateMaster(String tpId, String priceBudgetId, Double[] priceAudit){
		TPmContractPriceMasterEntity master = (TPmContractPriceMasterEntity) commonDao.getSession()
				.createCriteria(TPmContractPriceMasterEntity.class)
				.add(Restrictions.eq("tpId", tpId))
				.add(Restrictions.eq("priceBudgetId", priceBudgetId)).uniqueResult();
		TPmContractPriceMasterEntity oldData = new TPmContractPriceMasterEntity();
		oldData.setPriceColumn(master.getPriceColumn());
		oldData.setAuditColumn(master.getAuditColumn());
		oldData.setValuationColumn(master.getValuationColumn());
		oldData.setPriceDiffColumn(master.getPriceDiffColumn());
		
		master.setPriceColumn((master.getPriceColumn() == null ? 0 : master.getPriceColumn()) + priceAudit[0]);
		master.setAuditColumn((master.getAuditColumn() == null ? 0 : master.getAuditColumn()) + priceAudit[1]);
		// 差价栏=报价栏-计价栏
		master.setPriceDiffColumn(master.getPriceColumn()-
				(master.getValuationColumn() == null ? 0 : master.getValuationColumn()));
		
		commonDao.updateEntitie(master);
		
		if(StringUtil.isNotEmpty(master.getParent())){
			updateParent(oldData, master, master.getParent());
		}
	}
}