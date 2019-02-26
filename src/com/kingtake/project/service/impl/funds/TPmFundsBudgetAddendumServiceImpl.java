package com.kingtake.project.service.impl.funds;
import java.io.Serializable;
import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.funds.TPmFundsBudgetAddendumEntity;
import com.kingtake.project.entity.funds.TPmPlanFundsEntity;
import com.kingtake.project.service.funds.TPmFundsBudgetAddendumServiceI;

@Service("tPmFundsBudgetAddendumService")
@Transactional
public class TPmFundsBudgetAddendumServiceImpl extends CommonServiceImpl implements TPmFundsBudgetAddendumServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmFundsBudgetAddendumEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmFundsBudgetAddendumEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmFundsBudgetAddendumEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmFundsBudgetAddendumEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmFundsBudgetAddendumEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmFundsBudgetAddendumEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmFundsBudgetAddendumEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{pid}",String.valueOf(t.getPid()));
 		sql  = sql.replace("#{num}",String.valueOf(t.getNum()));
 		sql  = sql.replace("#{content}",String.valueOf(t.getContent()));
 		sql  = sql.replace("#{model}",String.valueOf(t.getModel()));
 		sql  = sql.replace("#{account}",String.valueOf(t.getAccount()));
 		sql  = sql.replace("#{price}",String.valueOf(t.getPrice()));
 		sql  = sql.replace("#{money}",String.valueOf(t.getMoney()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public void update(TPmFundsBudgetAddendumEntity tPmFundsBudgetAddendum) throws Exception {
		TPmFundsBudgetAddendumEntity t = commonDao.get(
				TPmFundsBudgetAddendumEntity.class, tPmFundsBudgetAddendum.getId());
		
		Double newMoney = tPmFundsBudgetAddendum.getMoney() == null ? 0 : tPmFundsBudgetAddendum.getMoney();
		Double oldMoney = t.getMoney() == null ? 0 : t.getMoney();
		updateParent(t.getPid(), oldMoney, newMoney);
		
		MyBeanUtils.copyBeanNotNull2Bean(tPmFundsBudgetAddendum, t);
		commonDao.saveOrUpdate(t);
		
	}

	@Override
	public void del(String id) {
		TPmFundsBudgetAddendumEntity addendum = commonDao.getEntity(TPmFundsBudgetAddendumEntity.class, id);

		Double newMoney = 0d;
		Double oldMoney = addendum.getMoney() == null ? 0 : addendum.getMoney();
		updateParent(addendum.getPid(), oldMoney, newMoney);
		
        commonDao.delete(addendum);
	}
	
	@Override
	public void updateParent(String apprId, Double oldMoney, Double newMoney){
		if(newMoney != oldMoney){
			TPmFundsBudgetAddendumEntity parent = (TPmFundsBudgetAddendumEntity) commonDao.getSession()
					.createCriteria(TPmFundsBudgetAddendumEntity.class)
					.add(Restrictions.eq("pid", apprId))
					.add(Restrictions.isNull("num")).uniqueResult();
			parent.setMoney((parent.getMoney() == null ? 0 : parent.getMoney()) + newMoney - oldMoney);
			commonDao.updateEntitie(parent);
		}
	}
}