package com.kingtake.project.service.impl.price;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.price.TPmContractPriceManageEntity;
import com.kingtake.project.service.price.TPmContractPriceManageServiceI;
import com.kingtake.project.service.price.TPmContractPriceMasterServiceI;

@Service("tPmContractPriceManageService")
@Transactional
public class TPmContractPriceManageServiceImpl extends CommonServiceImpl implements TPmContractPriceManageServiceI {
	@Autowired
	private TPmContractPriceMasterServiceI tPmContractPriceMasterService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmContractPriceManageEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmContractPriceManageEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmContractPriceManageEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmContractPriceManageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmContractPriceManageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmContractPriceManageEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmContractPriceManageEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{serial_num}",String.valueOf(t.getSerialNum()));
 		sql  = sql.replace("#{type_id}",String.valueOf(t.getTypeId()));
 		sql  = sql.replace("#{type_name}",String.valueOf(t.getTypeName()));
 		sql  = sql.replace("#{price_explain}",String.valueOf(t.getPriceExplain()));
 		sql  = sql.replace("#{price_amount}",String.valueOf(t.getPriceAmount()));
 		sql  = sql.replace("#{audit_amount}",String.valueOf(t.getAuditAmount()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{add_child_flag}",String.valueOf(t.getAddChildFlag()));
 		sql  = sql.replace("#{parent_typeid}",String.valueOf(t.getParentTypeid()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public List<TPmContractPriceManageEntity> list(TPmContractPriceManageEntity tPmContractPriceManage) {
		// 根节点
		String sql = "SELECT * FROM T_PM_CONTRACT_PRICE_MANAGE WHERE T_P_ID = :tpId " +
				" START WITH TYPE_ID = :typeId CONNECT BY PRIOR ID = PARENT_TYPEID";
	
		List<TPmContractPriceManageEntity> list = commonDao.getSession().createSQLQuery(sql)
				.addEntity(TPmContractPriceManageEntity.class)
				.setParameter("typeId", tPmContractPriceManage.getTypeId())
				.setParameter("tpId", tPmContractPriceManage.getTpId())
				.list();
		return list;
	}

	@Override
	public void updateParent(TPmContractPriceManageEntity oldData,
			TPmContractPriceManageEntity newData, String parentId) {
		// 获得父节点
		TPmContractPriceManageEntity parent = commonDao.get(TPmContractPriceManageEntity.class, parentId);
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
	public void update(TPmContractPriceManageEntity newData, String priceBudgetId, AjaxJson j) throws Exception {
		// 获得未更新之前的数据
		TPmContractPriceManageEntity oldData = commonDao.get(TPmContractPriceManageEntity.class, newData.getId());
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
		TPmContractPriceManageEntity oldData = commonDao.getEntity(TPmContractPriceManageEntity.class, id);
		// 更新父辈节点
		TPmContractPriceManageEntity newData = new TPmContractPriceManageEntity();
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