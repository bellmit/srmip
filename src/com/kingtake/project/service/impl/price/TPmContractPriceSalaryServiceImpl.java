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

import com.kingtake.project.entity.price.TPmContractPriceSalaryEntity;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;
import com.kingtake.project.service.price.TPmContractPriceSalaryServiceI;

@Service("tPmContractPriceSalaryService")
@Transactional
public class TPmContractPriceSalaryServiceImpl extends CommonServiceImpl implements TPmContractPriceSalaryServiceI {
	@Autowired
	private TPmContractPriceMasterServiceI tPmContractPriceMasterService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractPriceSalaryEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractPriceSalaryEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractPriceSalaryEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceSalaryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceSalaryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceSalaryEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPriceSalaryEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{serial_num}",String.valueOf(t.getSerialNum()));
 		sql  = sql.replace("#{unit_depart}",String.valueOf(t.getUnitDepart()));
 		sql  = sql.replace("#{work_content}",String.valueOf(t.getWorkContent()));
 		sql  = sql.replace("#{price_plan_people}",String.valueOf(t.getPricePlanPeople()));
 		sql  = sql.replace("#{price_plan_days}",String.valueOf(t.getPricePlanDays()));
 		sql  = sql.replace("#{price_plan_cost}",String.valueOf(t.getPricePlanCost()));
 		sql  = sql.replace("#{price_plan_amount}",String.valueOf(t.getPricePlanAmount()));
 		sql  = sql.replace("#{audit_plan_people}",String.valueOf(t.getAuditPlanPeople()));
 		sql  = sql.replace("#{audit_plan_days}",String.valueOf(t.getAuditPlanDays()));
 		sql  = sql.replace("#{audit_plan_cost}",String.valueOf(t.getAuditPlanCost()));
 		sql  = sql.replace("#{audit_plan_amount}",String.valueOf(t.getAuditPlanAmount()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public List<TPmContractPriceSalaryEntity> list(TPmContractPriceSalaryEntity tPmContractPriceSalary) {
		String sql = "SELECT * FROM T_PM_CONTRACT_PRICE_SALARY WHERE T_P_ID = :tpId " +
				" START WITH TYPE_ID = :typeId CONNECT BY PRIOR ID = PARENT_TYPEID";
		
		List<TPmContractPriceSalaryEntity> list = commonDao.getSession().createSQLQuery(sql)
				.addEntity(TPmContractPriceSalaryEntity.class)
				.setParameter("typeId", tPmContractPriceSalary.getTypeId())
				.setParameter("tpId", tPmContractPriceSalary.getTpId())
				.list();
		return list;
	}

	@Override
	public void updateParent(TPmContractPriceSalaryEntity oldData, TPmContractPriceSalaryEntity newData, String parentId) {
		// 获得父节点
		TPmContractPriceSalaryEntity parent = commonDao.get(TPmContractPriceSalaryEntity.class, parentId);
		if(parent != null){
			parent.setPricePlanAmount(
					(parent.getPricePlanAmount() == null ? 0 : parent.getPricePlanAmount())
					+ (newData.getPricePlanAmount() == null ? 0 : newData.getPricePlanAmount())
					- (oldData.getPricePlanAmount() == null ? 0 : oldData.getPricePlanAmount()));
			parent.setAuditPlanAmount(
					(parent.getAuditPlanAmount() == null ? 0 : parent.getAuditPlanAmount())
					+ (newData.getAuditPlanAmount() == null ? 0 : newData.getAuditPlanAmount())
					- (oldData.getAuditPlanAmount() == null ? 0 : oldData.getAuditPlanAmount()));
			commonDao.updateEntitie(parent);
			
			if(StringUtil.isNotEmpty(parent.getParentTypeid())){
				updateParent(oldData, newData, parent.getParentTypeid());
			}
		}
	}

	@Override
	public void update(TPmContractPriceSalaryEntity newData, String priceBudgetId, AjaxJson j) throws Exception {
		// 获得未更新之前的数据
		TPmContractPriceSalaryEntity oldData = commonDao.get(TPmContractPriceSalaryEntity.class, newData.getId());
		j.setObj(JSONHelper.bean2json(oldData));
		
		// 更新父辈数据
		this.updateParent(oldData, newData, oldData.getParentTypeid());
		
		// 更新主表:计价栏、审价栏
		Double[] priceAudit = new Double[]{
				(newData.getPricePlanAmount() == null ? 0 : newData.getPricePlanAmount())
						- (oldData.getPricePlanAmount() == null ? 0 : oldData.getPricePlanAmount()),
				(newData.getAuditPlanAmount() == null ? 0 : newData.getAuditPlanAmount()) 
					- (oldData.getAuditPlanAmount() == null ? 0 : oldData.getAuditPlanAmount())
		};
		tPmContractPriceMasterService.updateMaster(oldData.getTpId(), priceBudgetId, priceAudit);
		
		// 更新
		MyBeanUtils.copyBeanNotNull2Bean(newData, oldData);
		commonDao.saveOrUpdate(oldData);
	}

	@Override
	public void del(String id, String priceBudgetId, AjaxJson j) {
		TPmContractPriceSalaryEntity oldData = commonDao.getEntity(TPmContractPriceSalaryEntity.class, id);
		// 更新父辈节点
		TPmContractPriceSalaryEntity newData = new TPmContractPriceSalaryEntity();
		newData.setPricePlanAmount(0d);
		newData.setAuditPlanAmount(0d);
		this.updateParent(oldData, newData, oldData.getParentTypeid());
		j.setObj(JSONHelper.bean2json(newData));
		
		// 更新主表:计价栏、审价栏
		Double[] priceAudit = new Double[]{
				(newData.getPricePlanAmount() == null ? 0 : newData.getPricePlanAmount())
						- (oldData.getPricePlanAmount() == null ? 0 : oldData.getPricePlanAmount()),
				(newData.getAuditPlanAmount() == null ? 0 : newData.getAuditPlanAmount()) 
					- (oldData.getAuditPlanAmount() == null ? 0 : oldData.getAuditPlanAmount())
		};
		tPmContractPriceMasterService.updateMaster(oldData.getTpId(), priceBudgetId, priceAudit);
		
		// 删除
		commonDao.delete(oldData);
	}
}