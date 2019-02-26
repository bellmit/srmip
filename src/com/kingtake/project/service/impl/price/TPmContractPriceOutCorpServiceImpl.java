package com.kingtake.project.service.impl.price;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.price.TPmContractPriceDesignEntity;
import com.kingtake.project.entity.price.TPmContractPriceOutCorpEntity;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;
import com.kingtake.project.service.price.TPmContractPriceOutCorpServiceI;

@Service("tPmContractPriceOutCorpService")
@Transactional
public class TPmContractPriceOutCorpServiceImpl extends CommonServiceImpl implements TPmContractPriceOutCorpServiceI {
	@Autowired
	private TPmContractPriceMasterServiceI tPmContractPriceMasterService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractPriceOutCorpEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractPriceOutCorpEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractPriceOutCorpEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceOutCorpEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceOutCorpEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceOutCorpEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPriceOutCorpEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{outside_name}",String.valueOf(t.getOutsideName()));
 		sql  = sql.replace("#{outside_quality}",String.valueOf(t.getOutsideQuality()));
 		sql  = sql.replace("#{outside_process_unit}",String.valueOf(t.getOutsideProcessUnit()));
 		sql  = sql.replace("#{price_number}",String.valueOf(t.getPriceNumber()));
 		sql  = sql.replace("#{price_unit_price}",String.valueOf(t.getPriceUnitPrice()));
 		sql  = sql.replace("#{price_amount}",String.valueOf(t.getPriceAmount()));
 		sql  = sql.replace("#{audit_number}",String.valueOf(t.getAuditNumber()));
 		sql  = sql.replace("#{audit_unit_price}",String.valueOf(t.getAuditUnitPrice()));
 		sql  = sql.replace("#{audit_amount}",String.valueOf(t.getAuditAmount()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{add_child_flag}",String.valueOf(t.getAddChildFlag()));
 		sql  = sql.replace("#{parent_typeid}",String.valueOf(t.getParentTypeid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public List<TPmContractPriceOutCorpEntity> list(TPmContractPriceOutCorpEntity tPmContractPriceOutCorp) {
		String sql = "SELECT * FROM T_PM_CONTRACT_PRICE_OUT_CORP WHERE T_P_ID = :tpId " +
				" START WITH TYPE_ID = :typeId CONNECT BY PRIOR ID = PARENT_TYPEID";
		
		List<TPmContractPriceOutCorpEntity> list = commonDao.getSession().createSQLQuery(sql)
				.addEntity(TPmContractPriceOutCorpEntity.class)
				.setParameter("typeId", tPmContractPriceOutCorp.getTypeId())
				.setParameter("tpId", tPmContractPriceOutCorp.getTpId())
				.list();
		return list;
	}

	@Override
	public void updateParent(TPmContractPriceOutCorpEntity oldData, TPmContractPriceOutCorpEntity newData, String parentId) {
		// 获得父节点
		TPmContractPriceOutCorpEntity parent = commonDao.get(TPmContractPriceOutCorpEntity.class, parentId);
		if(parent != null){
			parent.setPriceAmount(
					(parent.getPriceAmount() == null ? 0 : parent.getPriceAmount())
					+ (newData.getPriceAmount() == null ? 0 : newData.getPriceAmount())
					- (oldData.getPriceAmount() == null ? 0 : oldData.getPriceAmount()));
			parent.setAuditAmount(
					(parent.getAuditAmount() == null ? 0 : parent.getAuditAmount())
					+ (newData.getAuditAmount() == null ? 0 : newData.getAuditAmount())
					- (oldData.getAuditAmount() == null ? 0 : oldData.getAuditAmount()));
			commonDao.updateEntitie(parent);
			
			if(StringUtil.isNotEmpty(parent.getParentTypeid())){
				updateParent(oldData, newData, parent.getParentTypeid());
			}
		}
		
	}

	@Override
	public void update(TPmContractPriceOutCorpEntity newData, String priceBudgetId, AjaxJson j) throws Exception {
		// 获得未更新之前的数据
		TPmContractPriceOutCorpEntity oldData = commonDao.get(TPmContractPriceOutCorpEntity.class, newData.getId());
		j.setObj(JSONHelper.bean2json(oldData));
		
		// 更新父辈数据
		this.updateParent(oldData, newData, oldData.getParentTypeid());
		
		// 更新主表:计价栏、审价栏
		Double[] priceAudit = new Double[]{
				(newData.getPriceAmount() == null ? 0 : newData.getPriceAmount())
						- (oldData.getPriceAmount() == null ? 0 : oldData.getPriceAmount()),
				(newData.getAuditAmount() == null ? 0 : newData.getAuditAmount()) 
					- (oldData.getAuditAmount() == null ? 0 : oldData.getAuditAmount())
		};
		tPmContractPriceMasterService.updateMaster(oldData.getTpId(), priceBudgetId, priceAudit);
		
		// 更新
		MyBeanUtils.copyBeanNotNull2Bean(newData, oldData);
		commonDao.saveOrUpdate(oldData);
		
	}

	@Override
	public void del(String id, String priceBudgetId, AjaxJson j) {
		TPmContractPriceOutCorpEntity oldData = commonDao.getEntity(TPmContractPriceOutCorpEntity.class, id);
		// 更新父辈节点
		TPmContractPriceOutCorpEntity newData = new TPmContractPriceOutCorpEntity();
		newData.setPriceAmount(0d);
		newData.setAuditAmount(0d);
		this.updateParent(oldData, newData, oldData.getParentTypeid());
		j.setObj(JSONHelper.bean2json(newData));
		
		// 更新主表:计价栏、审价栏
		Double[] priceAudit = new Double[]{
				(newData.getPriceAmount() == null ? 0 : newData.getPriceAmount())
						- (oldData.getPriceAmount() == null ? 0 : oldData.getPriceAmount()),
				(newData.getAuditAmount() == null ? 0 : newData.getAuditAmount()) 
					- (oldData.getAuditAmount() == null ? 0 : oldData.getAuditAmount())
		};
		tPmContractPriceMasterService.updateMaster(oldData.getTpId(), priceBudgetId, priceAudit);
		
		// 删除
		commonDao.delete(oldData);
	}
}