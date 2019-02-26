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

import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.price.TPmContractPriceCoverEntity;
import com.kingtake.project.entity.price.TPmContractPriceMaterialEntity;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;
import com.kingtake.project.service.price.TPmContractPriceMaterialServiceI;

@Service("tPmContractPriceMaterialService")
@Transactional
public class TPmContractPriceMaterialServiceImpl extends CommonServiceImpl implements TPmContractPriceMaterialServiceI {
	@Autowired
	private TPmContractPriceMasterServiceI tPmContractPriceMasterService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractPriceMaterialEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractPriceMaterialEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractPriceMaterialEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceMaterialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceMaterialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceMaterialEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPriceMaterialEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{serial_num}",String.valueOf(t.getSerialNum()));
 		sql  = sql.replace("#{type_id}",String.valueOf(t.getTypeId()));
 		sql  = sql.replace("#{type_name}",String.valueOf(t.getTypeName()));
 		sql  = sql.replace("#{price_name}",String.valueOf(t.getPriceName()));
 		sql  = sql.replace("#{prod_model}",String.valueOf(t.getProdModel()));
 		sql  = sql.replace("#{calculate_unit}",String.valueOf(t.getCalculateUnit()));
 		sql  = sql.replace("#{supply_unit}",String.valueOf(t.getSupplyUnit()));
 		sql  = sql.replace("#{price_plan_num}",String.valueOf(t.getPricePlanNum()));
 		sql  = sql.replace("#{price_plan_unitprice}",String.valueOf(t.getPricePlanUnitprice()));
 		sql  = sql.replace("#{price_plan_amount}",String.valueOf(t.getPricePlanAmount()));
 		sql  = sql.replace("#{price_audit_num}",String.valueOf(t.getPriceAuditNum()));
 		sql  = sql.replace("#{price_audit_unitprice}",String.valueOf(t.getPriceAuditUnitprice()));
 		sql  = sql.replace("#{price_audit_amount}",String.valueOf(t.getPriceAuditAmount()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{add_child_flag}",String.valueOf(t.getAddChildFlag()));
 		sql  = sql.replace("#{parent_typeid}",String.valueOf(t.getParentTypeid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public List<TPmContractPriceMaterialEntity> list(TPmContractPriceMaterialEntity tPmContractPriceMaterial) {
		String sql = "SELECT * FROM T_PM_CONTRACT_PRICE_MATERIAL WHERE T_P_ID = :tpId " +
					" START WITH TYPE_ID = :typeId CONNECT BY PRIOR ID = PARENT_TYPEID";
		
		List<TPmContractPriceMaterialEntity> list = commonDao.getSession().createSQLQuery(sql)
				.addEntity(TPmContractPriceMaterialEntity.class)
				.setParameter("typeId", tPmContractPriceMaterial.getTypeId())
				.setParameter("tpId", tPmContractPriceMaterial.getTpId())
				.list();
		return list;
	}

	@Override
	public void updateParent(TPmContractPriceMaterialEntity oldData, TPmContractPriceMaterialEntity newData, String parentId) {
		// 获得父节点
		TPmContractPriceMaterialEntity parent = commonDao.get(TPmContractPriceMaterialEntity.class, parentId);
		if(parent != null){
			parent.setPricePlanAmount(
					(parent.getPricePlanAmount() == null ? 0 : parent.getPricePlanAmount())
					+ (newData.getPricePlanAmount() == null ? 0 : newData.getPricePlanAmount())
					- (oldData.getPricePlanAmount() == null ? 0 : oldData.getPricePlanAmount()));
			parent.setPriceAuditAmount(
					(parent.getPriceAuditAmount() == null ? 0 : parent.getPriceAuditAmount())
					+ (newData.getPriceAuditAmount() == null ? 0 : newData.getPriceAuditAmount())
					- (oldData.getPriceAuditAmount() == null ? 0 : oldData.getPriceAuditAmount()));
			commonDao.updateEntitie(parent);
			
			if(StringUtil.isNotEmpty(parent.getParentTypeid())){
				updateParent(oldData, newData, parent.getParentTypeid());
			}
		}
	}

	@Override
	public void update(TPmContractPriceMaterialEntity newData, String priceBudgetId, AjaxJson j) throws Exception {
		// 获得未更新之前的数据
		TPmContractPriceMaterialEntity oldData = commonDao.get(
				TPmContractPriceMaterialEntity.class, newData.getId());
		j.setObj(JSONHelper.bean2json(oldData));
		
		// 更新父辈数据
		this.updateParent(oldData, newData, oldData.getParentTypeid());
		
		// 更新主表:计价栏、审价栏
		Double[] priceAudit = new Double[]{
				(newData.getPricePlanAmount() == null ? 0 : newData.getPricePlanAmount())
						- (oldData.getPricePlanAmount() == null ? 0 : oldData.getPricePlanAmount()),
				(newData.getPriceAuditAmount() == null ? 0 : newData.getPriceAuditAmount()) 
					- (oldData.getPriceAuditAmount() == null ? 0 : oldData.getPriceAuditAmount())
		};
		tPmContractPriceMasterService.updateMaster(oldData.getTpId(), priceBudgetId, priceAudit);
		
		// 更新
		MyBeanUtils.copyBeanNotNull2Bean(newData, oldData);
		commonDao.saveOrUpdate(oldData);
		
	}

	/**
	 * 根据录入的原材料查询到对应的项目信息
	 * @return
	 */
	public TPmProjectEntity getProject(TPmContractPriceMaterialEntity material){
	  //取得对应的封面
	  TPmContractPriceCoverEntity cover = commonDao.get(TPmContractPriceCoverEntity.class, material.getTpId());
	  //取得对应的出账合同审批表
    TPmOutcomeContractApprEntity appr = commonDao.get(TPmOutcomeContractApprEntity.class, cover.getContractId());

	  return appr.getProject();
	}
	
	
	@Override
	public void del(String id, String priceBudgetId, AjaxJson j) {
		TPmContractPriceMaterialEntity oldData = commonDao.getEntity(TPmContractPriceMaterialEntity.class, id);
		
		// 更新父辈节点
		TPmContractPriceMaterialEntity newData = new TPmContractPriceMaterialEntity();
		newData.setPriceAuditAmount(0d);
		newData.setPriceAuditAmount(0d);
		this.updateParent(oldData, newData, oldData.getParentTypeid());
		j.setObj(JSONHelper.bean2json(newData));
		
		// 更新主表:计价栏、审价栏
		Double[] priceAudit = new Double[]{
				(newData.getPricePlanAmount() == null ? 0 : newData.getPricePlanAmount())
						- (oldData.getPricePlanAmount() == null ? 0 : oldData.getPricePlanAmount()),
				(newData.getPriceAuditAmount() == null ? 0 : newData.getPriceAuditAmount()) 
					- (oldData.getPriceAuditAmount() == null ? 0 : oldData.getPriceAuditAmount())
		};
		tPmContractPriceMasterService.updateMaster(oldData.getTpId(), priceBudgetId, priceAudit);
		
		// 删除
		commonDao.delete(oldData);
		
	}
	
	
}