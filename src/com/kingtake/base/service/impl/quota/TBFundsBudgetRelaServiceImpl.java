package com.kingtake.base.service.impl.quota;
import com.kingtake.base.service.quota.TBFundsBudgetRelaServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kingtake.base.entity.quota.TBFundsBudgetRelaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tBFundsBudgetRelaService")
@Transactional
public class TBFundsBudgetRelaServiceImpl extends CommonServiceImpl implements TBFundsBudgetRelaServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBFundsBudgetRelaEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBFundsBudgetRelaEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBFundsBudgetRelaEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBFundsBudgetRelaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBFundsBudgetRelaEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBFundsBudgetRelaEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBFundsBudgetRelaEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{funds_property_code}",String.valueOf(t.getFundsPropertyCode()));
 		sql  = sql.replace("#{approval_budget_rela_id}",String.valueOf(t.getApprovalBudgetRelaId()));
 		sql  = sql.replace("#{million_up}",String.valueOf(t.getMillionUp()));
 		sql  = sql.replace("#{million_down}",String.valueOf(t.getMillionDown()));
 		sql  = sql.replace("#{memo}",String.valueOf(t.getMemo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}